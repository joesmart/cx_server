package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.UserCustomMGraphicDao;
import com.server.cx.dao.cx.spec.UserCustomMGraphicSpecifications;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserCustomMGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.MGraphicService;
import com.server.cx.service.cx.QueryMGraphicService;
import com.server.cx.service.util.BusinessFunctions;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 下午1:20
 * FileName:UserCustomMGraphicServiceImpl
 */
@Service(value = "customMGraphicService")
@Transactional
public class CustomMGraphicServiceImpl extends CheckAndHistoryMGraphicService implements MGraphicService,QueryMGraphicService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomMGraphicServiceImpl.class);

    @Autowired
    private UserCustomMGraphicDao userCustomMGraphicDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Autowired
    private BasicService basicService;

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        checkPreviousMGraphicCount();
        UserCustomMGraphic userCustomMGraphic = new UserCustomMGraphic();
        userCustomMGraphic.setGraphicInfo(graphicInfo);
        userCustomMGraphic.setUserInfo(userInfo);
        userCustomMGraphic.setCommon(true);
        userCustomMGraphic.setPhoneNos(null);
        if (mGraphicDTO.getPhoneNos() != null && mGraphicDTO.getPhoneNos().size()>0){
            userCustomMGraphic.setPriority(10);
            userCustomMGraphic.setCommon(false);
            userCustomMGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
        }
        if(mGraphicDTO.getBegin()!=null&&mGraphicDTO.getEnd()!=null){
            DateTime begin = new DateTime(mGraphicDTO.getBegin().getTime());
            DateTime end = new DateTime(mGraphicDTO.getEnd().getTime());
            if(begin.compareTo(end) > 0){
                throw  new CXServerBusinessException("开始时间大于结束时间");
            }
            userCustomMGraphic.setBegin(mGraphicDTO.getBegin());
            userCustomMGraphic.setEnd(mGraphicDTO.getEnd());
        }
        updateMGraphicNameAndSignature(mGraphicDTO, userCustomMGraphic);
        userCustomMGraphicDao.save(userCustomMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    private void checkPreviousMGraphicCount() {
        Long recordCount =userCustomMGraphicDao.count(UserCustomMGraphicSpecifications.userCustomMGraphic(userInfo));
        if(recordCount >= 5){
            throw new CXServerBusinessException("自定义包彩像设置最多不超过五个");
        }
    }

    @Override
    public OperationResult create(String imsi, MGraphicDTO mGraphicDTO) throws RuntimeException {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("createUserHolidayMGraphic", "success");
    }


    @Override
    public OperationResult edit(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);

        UserCustomMGraphic mGraphic = userCustomMGraphicDao.findOne(mGraphicDTO.getId());
        if (mGraphicDTO.getPhoneNos() == null || mGraphicDTO.getPhoneNos().size() == 0) {
            mGraphic.setPhoneNos(null);
            mGraphic.setCommon(true);
            mGraphic.setPriority(9);
            updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userCustomMGraphicDao.save(mGraphic);
        } else {
            mGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
            mGraphic.setPriority(10);
            mGraphic.setCommon(false);
            updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userCustomMGraphicDao.save(mGraphic);
        }
        return new OperationResult("editUserCommonMGraphic", "success");
    }

    @Override
    public OperationResult disable(String imsi, String mgraphicId) {
        Preconditions.checkNotNull(imsi, "imsi为空");
        Preconditions.checkNotNull(mgraphicId, "指定对象不存在");
        checkAndSetUserInfoExists(imsi);
        UserCustomMGraphic mGraphic = userCustomMGraphicDao.findOne(mgraphicId);
        if (mGraphic != null) {
            userCustomMGraphicDao.delete(mGraphic);
        }
        return new OperationResult("deleteHolidayMGraphic", "success");
    }

    @Override
    public DataPage queryUserMGraphic(String imsi) {
        Preconditions.checkNotNull(imsi, "imsi 不能为空");
        checkAndSetUserInfoExists(imsi);
        List<UserCustomMGraphic> mGraphics = userCustomMGraphicDao.findAll(UserCustomMGraphicSpecifications.userCustomMGraphic(userInfo), new Sort(new Sort.Order(Sort.Direction.ASC, "modeType"), new Sort.Order(Sort.Direction.DESC, "createdOn")));
        String conditions = "customMGraphics";
        List<DataItem> mGraphicDataItems = Lists.transform(mGraphics, businessFunctions.mGraphicTransformToDataItem(imsi, conditions));

        DataPage dataPage = new DataPage();
        dataPage.setItems(mGraphicDataItems);
        dataPage.setOffset(0);
        dataPage.setLimit(mGraphicDataItems.size());
        dataPage.setTotal(1);
        dataPage.setHref(basicService.baseHostAddress + basicService.restURL + imsi + "/"+conditions);
        return dataPage;
    }

    @Override
    protected void updateMGraphicNameAndSignature(MGraphicDTO mGraphicDTO, MGraphic mgraphic) {
        if(StringUtils.isEmpty(mGraphicDTO.getName())){
            mgraphic.setName("自定义");
        }else {
            mgraphic.setName(mGraphicDTO.getName());
        }
        mgraphic.setSignature(mGraphicDTO.getSignature());
    }
}
