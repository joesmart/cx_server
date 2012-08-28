package com.server.cx.service.cx.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.Actions;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.UserHolidayMGraphicDao;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HolidayService;
import com.server.cx.service.cx.HolidayTypeService;
import com.server.cx.service.cx.MGraphicService;
import com.server.cx.service.cx.UserSubscribeGraphicItemService;

@Service(value = "holidayMGraphicService")
@Transactional
public class HolidayMGraphicServiceImpl extends CheckAndHistoryMGraphicService implements MGraphicService {

    @Autowired
    private UserHolidayMGraphicDao userHolidayMGraphicDao;

    @Autowired
    private HolidayTypeService holidayTypeService;

    @Autowired
    private HolidayTypeDao holidayTypeDao;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private UserSubscribeGraphicItemService userSubscribeGraphicItemService;

    private String createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        Preconditions.checkNotNull(mGraphicDTO.getHolidayType(), "holidayType未提供");
        HolidayType holidayType = holidayTypeDao.findOne(mGraphicDTO.getHolidayType());
        deletePreviousHolidayMGraphic(holidayType);
        UserHolidayMGraphic holidayMGraphic = new UserHolidayMGraphic();
        holidayMGraphic.setGraphicInfo(graphicInfo);
        holidayMGraphic.setUserInfo(userInfo);
        holidayMGraphic.setCommon(true);
        holidayMGraphic.setSubscribe(mGraphicDTO.getSubscribe());
        holidayMGraphic.setPhoneNos(null);
        holidayMGraphic.setHolidayType(holidayType);
        if (mGraphicDTO.getPhoneNos() != null && mGraphicDTO.getPhoneNos().size() > 0) {
            holidayMGraphic.setPriority(8);
            holidayMGraphic.setCommon(false);
            holidayMGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
        }
        Date appropriateDate = holidayService.getAppropriateHolidayDate(holidayType);
        holidayMGraphic.setHoliday(appropriateDate);
        updateMGraphicNameAndSignature(mGraphicDTO, holidayMGraphic);
        userHolidayMGraphicDao.save(holidayMGraphic);
        graphicInfoService.updateGraphicInfoUseCount(graphicInfo);
        return holidayMGraphic.getId();
    }

    private void deletePreviousHolidayMGraphic(HolidayType holidayType) {
        //同一个节日只允许 设置一个 节日彩像.
        List<UserHolidayMGraphic> historyHolidayMGraphics = userHolidayMGraphicDao.findByUserInfoAndHolidayType(
            userInfo, holidayType);
        for (UserHolidayMGraphic userHolidayMGraphic : historyHolidayMGraphics) {
            userHolidayMGraphicDao.delete(userHolidayMGraphic);
        }
    }

    @Override
    public OperationResult create(String imsi, Boolean isImmediate, MGraphicDTO mGraphicDTO, Boolean subscribe) throws RuntimeException {
        checkParameters(imsi, mGraphicDTO);
        checkAndSetUserInfoExists(imsi);

        if(subscribe) {
            userSubscribeGraphicItemService.subscribeGraphicItem(imsi, mGraphicDTO.getGraphicInfoId());
        } else {
            userSubscribeGraphicItemService.checkUserSubscribeGraphicItem(userInfo, mGraphicDTO.getGraphicInfoId());
        }
        mGraphicDTO.setSubscribe(true);

        if (isImmediate) {
            graphicInfo = holidayTypeService.getFirstChild(mGraphicDTO.getHolidayType());
            mGraphicDTO.setGraphicInfoId(graphicInfo.getId());
        }
        checkMGraphicIdMustBeNotExists(mGraphicDTO);

        historyPreviousUserCommonMGraphic();
        String mgraphicId = createAndSaveNewUserCommonMGraphic(mGraphicDTO);
        OperationResult operationResult = new OperationResult("createUserHolidayMGraphic", Constants.SUCCESS_FLAG);
        if (isImmediate) {
            Actions actions = actionBuilder.buildHolidayMGraphicItemCreatedAction(imsi, mgraphicId);
            operationResult.setActions(actions);
        }
        return operationResult;
    }

    private void historyPreviousUserCommonMGraphic() {
        List<UserHolidayMGraphic> previousUserCommonMGraphics = userHolidayMGraphicDao
            .findByUserInfoAndModeTypeAndCommon(userInfo, 4, true);
        userHolidayMGraphicDao.delete(previousUserCommonMGraphics);
    }

    @Override
    public OperationResult edit(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        mGraphicIdMustBeExists(mGraphicDTO);
        userSubscribeGraphicItemService.checkUserSubscribeGraphicItem(userInfo, mGraphicDTO.getGraphicInfoId());
        
        UserHolidayMGraphic mGraphic = userHolidayMGraphicDao.findOne(mGraphicDTO.getId());
        if (mGraphicDTO.getPhoneNos() == null || mGraphicDTO.getPhoneNos().size() == 0) {
            mGraphic.setPhoneNos(null);
            mGraphic.setCommon(true);
            mGraphic.setPriority(7);
            updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userHolidayMGraphicDao.save(mGraphic);
        } else {
            mGraphic.setPhoneNos(mGraphicDTO.getPhoneNos());
            mGraphic.setPriority(8);
            mGraphic.setCommon(false);
            updateMGraphicNameAndSignature(mGraphicDTO, mGraphic);
            userHolidayMGraphicDao.save(mGraphic);
        }
        return new OperationResult("editUserCommonMGraphic", Constants.SUCCESS_FLAG);
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
        return new OperationResult("deleteHolidayMGraphic", Constants.SUCCESS_FLAG);
    }

}
