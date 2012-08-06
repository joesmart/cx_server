package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dto.OperationResult;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.service.cx.UserCommonMGraphicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
        saveOrUpdateUserCommonMGraphic(mGraphicDTO, userCommonMGraphic);
    }

    @Transactional(readOnly = false)
    private void saveOrUpdateUserCommonMGraphic(MGraphicDTO mGraphicDTO, UserCommonMGraphic userCommonMGraphic) {
        userCommonMGraphic.setActive(true);
        userCommonMGraphic.setName(getGraphicInfoName(mGraphicDTO));
        userCommonMGraphic.setGraphicInfo(graphicInfo);
        userCommonMGraphic.setUserInfo(userInfo);
        userCommonMGraphic.setSignature(mGraphicDTO.getSignature());
        userCommonMGraphicDao.save(userCommonMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    @Override
    public OperationResult createUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) throws RuntimeException{
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNull(mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        UserCommonMGraphic previousUserCommonMGraphic = userCommonMGraphicDao.findByUserInfoAndActive(userInfo,true);
        if(previousUserCommonMGraphic != null){
            historyPreviousUserCommonMGraphic(previousUserCommonMGraphic);
            userCommonMGraphicDao.delete(previousUserCommonMGraphic);
        }
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("createUserCommonMGraphic","success");
    }

    @Override
    public OperationResult editUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNull(mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);
        Preconditions.checkNotNull(mGraphicDTO.getModeType(),"类型模式不允许为空");

        if(2 == mGraphicDTO.getModeType()){
            UserCommonMGraphic userCommonMGraphic = userCommonMGraphicDao.findOne(mGraphicDTO.getId());
            saveOrUpdateUserCommonMGraphic(mGraphicDTO,userCommonMGraphic);
        }

        return new OperationResult("editUserCommonMGraphic","success");
    }

    @Override
    public OperationResult disableUserCommonMGraphic(String imsi, String userCommonMGraphicId) {
        Preconditions.checkNotNull(imsi,"imsi为空");
        Preconditions.checkNotNull(userCommonMGraphicId, "指定对象不存在");
        checkAndSetUserInfoExists(imsi);
        UserCommonMGraphic userCommonMGraphic = userCommonMGraphicDao.findOne(userCommonMGraphicId);
        if(userCommonMGraphic != null){
            historyPreviousUserCommonMGraphic(userCommonMGraphic);
            userCommonMGraphicDao.delete(userCommonMGraphic);
        }
        return new OperationResult("disableUserCommonMGraphic","success");
    }
}
