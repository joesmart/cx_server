package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserSpecialMGraphicDao;
import com.server.cx.dao.cx.spec.MGraphicSpecifications;
import com.server.cx.dao.cx.spec.UserSpecialMGraphicSpecifications;
import com.server.cx.dto.OperationResult;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserSpecialMGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.UserCommonMGraphicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:59
 * FileName:UserCommonMGraphicServiceImpl
 */
@Service(value = "userCommonMGraphicService")
@Transactional
public class UserCommonMGraphicServiceImpl extends MGraphicService implements UserCommonMGraphicService {

    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;
    @Autowired
    private UserSpecialMGraphicDao userSpecialMGraphicDao;
    @Autowired
    private MGraphicDao mGraphicDao;

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
        userCommonMGraphic.setGraphicInfo(graphicInfo);
        userCommonMGraphic.setUserInfo(userInfo);
        updateUserCommonMGraphicNameAndSignature(mGraphicDTO, userCommonMGraphic);
        userCommonMGraphicDao.save(userCommonMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    @Transactional(readOnly = false)
    private void updateUserCommonMGraphicNameAndSignature(MGraphicDTO mGraphicDTO, UserCommonMGraphic userCommonMGraphic) {
        userCommonMGraphic.setActive(true);
        userCommonMGraphic.setName(getGraphicInfoName(mGraphicDTO));
        userCommonMGraphic.setSignature(mGraphicDTO.getSignature());
    }

    @Override
    public OperationResult createUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) throws RuntimeException {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNull(mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        historyPreviousUserCommonMGraphic();
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("createUserCommonMGraphic", "success");
    }

    private void historyPreviousUserCommonMGraphic() {
        UserCommonMGraphic previousUserCommonMGraphic = userCommonMGraphicDao.findByUserInfoAndActive(userInfo, true);
        if (previousUserCommonMGraphic != null) {
            historyPreviousUserCommonMGraphic(previousUserCommonMGraphic);
            userCommonMGraphicDao.delete(previousUserCommonMGraphic);
        }
    }

    @Override
    public OperationResult editUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNull(mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);

        UserCommonMGraphic mGraphic = userCommonMGraphicDao.findOne(mGraphicDTO.getId());
        if (mGraphicDTO.getPhoneNos() == null || mGraphicDTO.getPhoneNos().size() == 0) {
            ((UserSpecialMGraphic) mGraphic).setPhoneNos(null);
            ((UserSpecialMGraphic) mGraphic).setModeType(1);
            ((UserSpecialMGraphic) mGraphic).setPriority(3);
            historyPreviousUserCommonMGraphic();
            updateUserCommonMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userCommonMGraphicDao.save(mGraphic);
        } else {
            Long dataRowNumber = userSpecialMGraphicDao.count(UserSpecialMGraphicSpecifications.userSpecialMGraphicCount(userInfo));
            if (dataRowNumber >= 5) {
                throw new CXServerBusinessException("指定号码用户设置彩像最多允许5个");
            }
            ((UserSpecialMGraphic) mGraphic).setPhoneNos(mGraphicDTO.getPhoneNos());
            ((UserSpecialMGraphic) mGraphic).setModeType(2);
            ((UserSpecialMGraphic) mGraphic).setPriority(4);
            updateUserCommonMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userSpecialMGraphicDao.save((UserSpecialMGraphic) mGraphic);
        }


        return new OperationResult("editUserCommonMGraphic", "success");
    }

    @Override
    public OperationResult disableUserCommonMGraphic(String imsi, String userCommonMGraphicId) {
        Preconditions.checkNotNull(imsi, "imsi为空");
        Preconditions.checkNotNull(userCommonMGraphicId, "指定对象不存在");
        checkAndSetUserInfoExists(imsi);
        UserCommonMGraphic userCommonMGraphic = userCommonMGraphicDao.findOne(userCommonMGraphicId);
        if (userCommonMGraphic != null) {
            historyPreviousUserCommonMGraphic(userCommonMGraphic);
            userCommonMGraphicDao.delete(userCommonMGraphic);
        }
        return new OperationResult("disableUserCommonMGraphic", "success");
    }

    @Override
    public List<DataItem> getUserMGraphic(String imsi) {
        Preconditions.checkNotNull(imsi,"imsi 不能为空");
        checkAndSetUserInfoExists(imsi);
        List<MGraphic> mGraphics = mGraphicDao.findAll(MGraphicSpecifications.userMGraphic(userInfo),new Sort(new Sort.Order(Sort.Direction.ASC,"modeType"),new Sort.Order(Sort.Direction.DESC,"createdOn")));
        List<DataItem> dataItemList = Lists.transform(mGraphics,new Function<MGraphic, DataItem>() {
            @Override
            public DataItem apply(@Nullable MGraphic input) {
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setSignature(input.getSignature());
                dataItem.setId(input.getGraphicInfo().getId());
                dataItem.setMGraphicId(input.getId());
                dataItem.setLevel(input.getGraphicInfo().getLevel());
                dataItem.setModeType(input.getModeType());
                if(2 == input.getModeType()){
                    dataItem.setPhoneNos(((UserSpecialMGraphic)input).getPhoneNos());
                }
                dataItem.setInUsing(true);
                return dataItem;
            }
        });
        return dataItemList;
    }
}
