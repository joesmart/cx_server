package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.StatusTypeDao;
import com.server.cx.dao.cx.UserStatusMGraphicDao;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.UserStatusMGraphic;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.MGraphicService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 上午9:52
 * FileName:StatusMGraphicServiceImpl
 */

@Service(value = "statusMGraphicService")
@Transactional
public class StatusMGraphicServiceImpl extends CheckAndHistoryMGraphicService implements MGraphicService {

    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;

    @Autowired
    private StatusTypeDao statusTypeDao;

    @Override
    public OperationResult create(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        historyPreviousUserCommonMGraphic();
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("createUserStatusMGraphic", "success");
    }

    @Override
    public OperationResult edit(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);

        UserStatusMGraphic mGraphic = userStatusMGraphicDao.findOne(mGraphicDTO.getId());
        updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
        userStatusMGraphicDao.save(mGraphic);

        return new OperationResult("editUserStatusMGraphic", "success");
    }

    @Override
    public OperationResult disable(String imsi, String mgraphicId) {
        Preconditions.checkNotNull(imsi, "imsi为空");
        Preconditions.checkNotNull(mgraphicId, "指定对象不存在");
        checkAndSetUserInfoExists(imsi);
        UserStatusMGraphic userCommonMGraphic = userStatusMGraphicDao.findOne(mgraphicId);
        if (userCommonMGraphic != null) {
            historyPreviousUserCommonMGraphic(userCommonMGraphic);
            userStatusMGraphicDao.delete(userCommonMGraphic);
        }
        return new OperationResult("disableUserCommonMGraphic", "success");
    }

    private void historyPreviousUserCommonMGraphic() {
        List<UserStatusMGraphic> previousUserCommonMGraphics = userStatusMGraphicDao.findByUserInfoAndModeType(userInfo, 5);
        for (UserStatusMGraphic statusMGraphic : previousUserCommonMGraphics) {
            userStatusMGraphicDao.delete(statusMGraphic);
        }
    }

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        Preconditions.checkNotNull(mGraphicDTO.getStatusType(), "StatusType未提供");
        StatusType statusType = statusTypeDao.findOne(mGraphicDTO.getStatusType());
        deletePreviousMGraphic(statusType);
        UserStatusMGraphic userStatusMGraphic = new UserStatusMGraphic();
        userStatusMGraphic.setGraphicInfo(graphicInfo);
        userStatusMGraphic.setUserInfo(userInfo);
        userStatusMGraphic.setStatusType(statusType);
        userStatusMGraphic.setValidDate(LocalDate.now().toDate());
        updateMGraphicNameAndSignature(mGraphicDTO, userStatusMGraphic);
        userStatusMGraphicDao.save(userStatusMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    private void deletePreviousMGraphic(StatusType statusType) {
        List<UserStatusMGraphic> previousMGraphics = userStatusMGraphicDao.findByValidDateAndStatusTypeAndUserInfo(LocalDate.now().toDate(),statusType,userInfo);
        userStatusMGraphicDao.delete(previousMGraphics);
    }
}
