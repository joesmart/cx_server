package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.spec.MGraphicSpecifications;
import com.server.cx.dao.cx.spec.UserCommonMGraphicSpecifications;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HistoryMGraphicService;
import com.server.cx.service.cx.MGraphicService;
import com.server.cx.service.cx.QueryMGraphicService;
import com.server.cx.service.cx.UserSubscribeGraphicItemService;
import com.server.cx.service.util.BusinessFunctions;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:59
 * FileName:MGraphicServiceImpl
 */
@Service(value = "mgraphicService")
@Transactional
public class MGraphicServiceImpl extends CheckAndHistoryMGraphicService implements MGraphicService,QueryMGraphicService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;
    @Autowired
    private HistoryMGraphicService historyMGraphicService;
    @Autowired
    private MGraphicDao mGraphicDao;
    
    @Autowired
    private UserSubscribeGraphicItemService userSubscribeGraphicItemService;

    @Autowired
    private BusinessFunctions businessFunctions;

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
        userCommonMGraphic.setGraphicInfo(graphicInfo);
        userCommonMGraphic.setUserInfo(userInfo);
        userCommonMGraphic.setCommon(true);

        if(mGraphicDTO.getPhoneNos() != null && mGraphicDTO.getPhoneNos().size() > 0){
            userCommonMGraphic.setCommon(false);
            userCommonMGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
            userCommonMGraphic.setPriority(4);
        }
        updateMGraphicNameAndSignature(mGraphicDTO, userCommonMGraphic);
        userCommonMGraphicDao.save(userCommonMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    @Override
    public OperationResult create(String imsi, Boolean isImmediate, MGraphicDTO mGraphicDTO, Boolean subscribe) throws RuntimeException {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        
        if(subscribe) {
            userSubscribeGraphicItemService.subscribeGraphicItem(imsi, mGraphicDTO.getGraphicInfoId());
        } else {
            userSubscribeGraphicItemService.checkUserSubscribeGraphicItem(userInfo, mGraphicDTO.getGraphicInfoId());
        }
        mGraphicDTO.setSubscribe(true);
        
        historyPreviousMGraphic();
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("createUserCommonMGraphic", Constants.SUCCESS_FLAG);
    }

    private void historyPreviousMGraphic() {
        List<UserCommonMGraphic> previousUserCommonMGraphics = userCommonMGraphicDao.findByUserInfoAndModeTypeAndCommon(userInfo, 2, true);
        for (UserCommonMGraphic userCommonMGraphic : previousUserCommonMGraphics) {
            historyPreviousUserCommonMGraphic(userCommonMGraphic);
            userCommonMGraphicDao.delete(userCommonMGraphic);
        }

    }

    @Override
    public OperationResult edit(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);
        userSubscribeGraphicItemService.checkUserSubscribeGraphicItem(userInfo, mGraphicDTO.getGraphicInfoId());
        
        UserCommonMGraphic mGraphic = userCommonMGraphicDao.findOne(mGraphicDTO.getId());
        if (mGraphicDTO.getPhoneNos() == null || mGraphicDTO.getPhoneNos().size() == 0) {
            historyPreviousMGraphic();
            mGraphic.setPhoneNos(null);
            mGraphic.setPriority(3);
            mGraphic.setCommon(true);
            updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userCommonMGraphicDao.save(mGraphic);
        } else {
            Long dataRowNumber = userCommonMGraphicDao.count(UserCommonMGraphicSpecifications.userCommonMGraphicCount(userInfo));
            if (dataRowNumber >= 5) {
                throw new CXServerBusinessException("指定号码用户设置彩像最多允许5个");
            }
            mGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
            mGraphic.setModeType(2);
            mGraphic.setPriority(4);
            mGraphic.setCommon(false);
            updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userCommonMGraphicDao.save( mGraphic);
        }


        return new OperationResult("editUserCommonMGraphic", Constants.SUCCESS_FLAG);
    }

    @Override
    public OperationResult disable(String imsi, String userCommonMGraphicId) {
        Preconditions.checkNotNull(imsi, "imsi为空");
        Preconditions.checkNotNull(userCommonMGraphicId, "指定对象不存在");
        checkAndSetUserInfoExists(imsi);
        UserCommonMGraphic userCommonMGraphic = userCommonMGraphicDao.findOne(userCommonMGraphicId);
        if (userCommonMGraphic != null) {
            historyPreviousUserCommonMGraphic(userCommonMGraphic);
            userCommonMGraphicDao.delete(userCommonMGraphic);
        }
        return new OperationResult("disableUserCommonMGraphic", Constants.SUCCESS_FLAG);
    }

    @Override
    public DataPage queryUserMGraphic(String imsi) {
        Preconditions.checkNotNull(imsi, "imsi 不能为空");
        checkAndSetUserInfoExists(imsi);
        List<MGraphic> mGraphics = mGraphicDao.findAll(MGraphicSpecifications.userMGraphic(userInfo), new Sort(new Sort.Order(Sort.Direction.ASC, "modeType"), new Sort.Order(Sort.Direction.DESC, "createdOn")));
        List<DataItem> mGraphicDataItems = Lists.transform(mGraphics, businessFunctions.mGraphicTransformToDataItem(imsi, "mGraphics") );
        List<DataItem> historyMGraphicDataItems = historyMGraphicService.queryHistoryMGraphicsByUserInfo(userInfo);
        List<DataItem> dataItems = Lists.newArrayListWithCapacity(mGraphicDataItems.size() + historyMGraphicDataItems.size());
        dataItems.addAll(mGraphicDataItems);
        dataItems.addAll(historyMGraphicDataItems);

        DataPage dataPage = new DataPage();
        dataPage.setItems(dataItems);
        dataPage.setOffset(0);
        dataPage.setLimit(dataItems.size());
        dataPage.setTotal(1);
        dataPage.setHref(basicService.generateMGraphicsURL(imsi));
        return dataPage;
    }
    
}
