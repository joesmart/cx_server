package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.UserHolidayMGraphicDao;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HolidayMGraphicService;
import com.server.cx.service.cx.HolidayService;
import com.server.cx.service.util.BusinessFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(value = "holidayMGraphicService")
@Transactional
public class HolidayMGraphicServiceImpl extends CheckAndHistoryMGraphicService implements HolidayMGraphicService {

    @Autowired
    private UserHolidayMGraphicDao userHolidayMGraphicDao;

    @Autowired
    private HolidayTypeDao holidayTypeDao;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private BusinessFunctions businessFunctions;

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        HolidayType holidayType = holidayTypeDao.findOne(mGraphicDTO.getHolidayType());
        deletePreviousHolidayMGraphic(holidayType);
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
        Date appropriateDate = holidayService.getAppropriateHolidayDate(holidayType);
        holidayMGraphic.setHoliday(appropriateDate);
        updateUserCommonMGraphicNameAndSignature(mGraphicDTO, holidayMGraphic);
        userHolidayMGraphicDao.save(holidayMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
    }

    private void deletePreviousHolidayMGraphic(HolidayType holidayType) {
        //同一个节日只允许 设置一个 节日彩像.
        List<UserHolidayMGraphic> historyHolidayMGraphics = userHolidayMGraphicDao.findByUserInfoAndHolidayType(userInfo,holidayType);
        for(UserHolidayMGraphic userHolidayMGraphic:historyHolidayMGraphics){
            userHolidayMGraphicDao.delete(userHolidayMGraphic);
        }
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
        UserHolidayMGraphic holidayMGraphic = userHolidayMGraphicDao.findOne(mgraphicId);
        if (holidayMGraphic != null) {
            userHolidayMGraphicDao.delete(holidayMGraphic);
        }
        return new OperationResult("deleteHolidayMGraphic", "success");
    }

}
