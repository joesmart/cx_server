package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.UserHolidayMGraphicDao;
import com.server.cx.dao.cx.spec.UserCommonMGraphicSpecifications;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HistoryMGraphicService;
import com.server.cx.service.cx.HolidayMGraphicService;
import com.server.cx.service.util.BusinessFunctions;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-10
 * Time: 下午1:45
 * FileName:HolidayMGraphicServiceImpl
 */
public class HolidayMGraphicServiceImpl extends CheckAndHistoryMGraphicService implements HolidayMGraphicService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private UserHolidayMGraphicDao userHolidayMGraphicDao;

    @Autowired
    private HolidayTypeDao holidayTypeDao;

    @Autowired
    private HistoryMGraphicService historyMGraphicService;
    @Autowired
    private MGraphicDao mGraphicDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserHolidayMGraphic holidayMGraphic = new UserHolidayMGraphic();
        holidayMGraphic.setGraphicInfo(graphicInfo);
        holidayMGraphic.setUserInfo(userInfo);
        holidayMGraphic.setCommon(true);
        holidayMGraphic.setPhoneNos(null);
        if (mGraphicDTO.getPhoneNos() != null && mGraphicDTO.getPhoneNos().size()>0){
            holidayMGraphic.setPriority(8);
            holidayMGraphic.setCommon(false);
            holidayMGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
        }
        HolidayType holidayType = holidayTypeDao.findOne(mGraphicDTO.getHolidayType());
        Date currentDate = LocalDate.now().toDate();

        updateUserCommonMGraphicNameAndSignature(mGraphicDTO, holidayMGraphic);
        userHolidayMGraphicDao.save(holidayMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    @Transactional(readOnly = false)
    private void updateUserCommonMGraphicNameAndSignature(MGraphicDTO mGraphicDTO, MGraphic userCommonMGraphic) {
        userCommonMGraphic.setName(getGraphicInfoName(mGraphicDTO));
        userCommonMGraphic.setSignature(mGraphicDTO.getSignature());
    }

    @Override
    public OperationResult create(String imsi, MGraphicDTO mGraphicDTO) throws RuntimeException {
        checkAndInitializeContext(imsi, mGraphicDTO);
        checkMGraphicDTOPhoneNosMustBeNull(mGraphicDTO);
        checkMGraphicIdMustBeNotExists(mGraphicDTO);
        historyPreviousUserCommonMGraphic();
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("createUserCommonMGraphic", "success");
    }

    private void historyPreviousUserCommonMGraphic() {
        List<UserHolidayMGraphic> previousUserCommonMGraphics = userHolidayMGraphicDao.findByUserInfoAndModeTypeAndCommon(userInfo, 4, true);
        for (UserHolidayMGraphic holidayMGraphic : previousUserCommonMGraphics) {
            historyPreviousUserCommonMGraphic(holidayMGraphic);
            userHolidayMGraphicDao.delete(holidayMGraphic);
        }

    }

    @Override
    public OperationResult edit(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);

        UserHolidayMGraphic mGraphic = userHolidayMGraphicDao.findOne(mGraphicDTO.getId());
        if (mGraphicDTO.getPhoneNos() == null || mGraphicDTO.getPhoneNos().size() == 0) {
            mGraphic.setPhoneNos(null);
            mGraphic.setCommon(true);
            mGraphic.setPriority(7);
            updateUserCommonMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userHolidayMGraphicDao.save(mGraphic);
        } else {
            Long dataRowNumber = userHolidayMGraphicDao.count(UserCommonMGraphicSpecifications.userHolidayMGraphicSpecification(userInfo));
            if (dataRowNumber >= 5) {
                throw new CXServerBusinessException("指定号码用户设置彩像最多允许5个");
            }

            mGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
            mGraphic.setPriority(8);
            mGraphic.setCommon(false);
            updateUserCommonMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userHolidayMGraphicDao.save(mGraphic);
        }


        return new OperationResult("editUserCommonMGraphic", "success");
    }

    @Override
    public OperationResult disable(String imsi, String mgraphicId) {
        Preconditions.checkNotNull(imsi, "imsi为空");
        Preconditions.checkNotNull(mgraphicId, "指定对象不存在");
        checkAndSetUserInfoExists(imsi);
        UserHolidayMGraphic userCommonMGraphic = userHolidayMGraphicDao.findOne(mgraphicId);
        if (userCommonMGraphic != null) {
            historyPreviousUserCommonMGraphic(userCommonMGraphic);
            userHolidayMGraphicDao.delete(userCommonMGraphic);
        }
        return new OperationResult("disableUserCommonMGraphic", "success");
    }

}
