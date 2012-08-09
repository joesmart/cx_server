package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.dao.cx.UserSpecialMGraphicDao;
import com.server.cx.dao.cx.spec.UserSpecialMGraphicSpecifications;
import com.server.cx.model.OperationResult;
import com.server.cx.entity.cx.UserSpecialMGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.UserSpecialMGraphicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 下午1:56
 * FileName:UserSpecialMGraphicServiceImpl
 */
@Service("userSpecialMGraphicService")
@Transactional
public class UserSpecialMGraphicServiceImpl extends MGraphicService implements UserSpecialMGraphicService {

    @Autowired
    private UserSpecialMGraphicDao userSpecialMGraphicDao;

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserSpecialMGraphic userSpecialMGraphic = new UserSpecialMGraphic();
        saveOrUpdateUserCommonMGraphic(mGraphicDTO, userSpecialMGraphic);
    }

    @Transactional(readOnly = false)
    private void saveOrUpdateUserCommonMGraphic(MGraphicDTO mGraphicDTO, UserSpecialMGraphic userSpecialMGraphic) {
        userSpecialMGraphic.setActive(true);
        userSpecialMGraphic.setName(getGraphicInfoName(mGraphicDTO));
        userSpecialMGraphic.setGraphicInfo(graphicInfo);
        userSpecialMGraphic.setUserInfo(userInfo);
        userSpecialMGraphic.setSignature(mGraphicDTO.getSignature());
        userSpecialMGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
        userSpecialMGraphicDao.save(userSpecialMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    @Override
    public OperationResult createUserSpecialMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNotNull(mGraphicDTO);

        Long dataRowNumber = userSpecialMGraphicDao.count(UserSpecialMGraphicSpecifications.userSpecialMGraphicCount(userInfo));
        if(dataRowNumber>=5){
            throw new CXServerBusinessException("特定号码用户设置最多允许5个");
        }
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);

        return new OperationResult("createUserSpecialMGraphic","success");
    }

    @Override
    public OperationResult editUserSpecialMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNotNull(mGraphicDTO);
        return null;
    }

    @Override
    public OperationResult disableUserSpecialMGraphic(String imsi, String userCommonMGraphicId) {
        return null;
    }
}
