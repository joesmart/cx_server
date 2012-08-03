package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dto.CXInfo;
import com.server.cx.dto.Result;
import com.server.cx.dto.UserCXInfo;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserCXInfoManagerService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.Base64Encoder;
import com.server.cx.util.RestSender;
import com.server.cx.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userCXInfoManagerService")
@Transactional
public class UserCXInfoManagerServiceImpl implements UserCXInfoManagerService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;

    @Autowired
    @Qualifier("cxinfosUploadRestSender")
    private RestSender restSender;

    @Autowired
    private BusinessFunctions businessFunctions;

    private boolean hasRemovedTheSameUserCXInfo;
    private UserCommonMGraphic userCommonMGraphic;

    public UserCXInfoManagerServiceImpl() {

    }

    @Override
    public String dealWithUserCXInfoAdding(Result result) throws SystemException {

        List<UserCXInfo> userCXInfos = result.getUserCXInfos();

        if (userCXInfos == null || userCXInfos.size() < 1) {
            return StringUtil.generateXMLResultString(Constants.NULL_INPUT_FLAG, "无数据输入");
        }

        UserCXInfo userCXInfo = userCXInfos.get(0);

        String imsi = userCXInfo.getImsi();
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        // 如果用户信息为空的话提醒注册:
        if (userInfo == null) {
            return StringUtil.generateXMLResultString(Constants.USER_DATA_ERROR_FLAG, "用户未注册");
        }

        makeupMGraphicStoreMode(userCXInfo, userInfo);

        // removeTheSameUserCXInfo(userInfo.getId(),userCXInfo);

        String mgraphicId = userCommonMGraphic.getId();

        // 用户自定义的用户设定彩像.
        if (mgraphicId == null || "".equals(mgraphicId)) {
            userCommonMGraphic.setId(null);
            CXInfo cxInfo = userCXInfo.getCxInfo();
            dealWithCXInfo(cxInfo, imsi);
            userCommonMGraphicDao.save(userCommonMGraphic);
        }
        // 这是一次彩像的编辑操作
        else {
            // 排除如果数据已经被
            UserCommonMGraphic existsUserCommonMGraphic = userCommonMGraphicDao.findOne(userCommonMGraphic.getId());
            if (existsUserCommonMGraphic != null) {
                existsUserCommonMGraphic.setName(userCommonMGraphic.getName());
                existsUserCommonMGraphic.setSignature(userCommonMGraphic.getSignature());
                updateUserCXInfoTimeStamp(existsUserCommonMGraphic);
                userCommonMGraphicDao.save(existsUserCommonMGraphic);
            } else {
                return StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据不存在");
            }

        }

        String dealResult = "";

        return dealResult;
    }

    public void makeupMGraphicStoreMode(UserCXInfo userCXInfo, UserInfo userInfo) {
        userCommonMGraphic = new UserCommonMGraphic();
        userCommonMGraphic.setId(userCXInfo.getId());
        userCommonMGraphic.setModifyTime(new Date());
        userCommonMGraphic.setUserInfo(userInfo);
        userCommonMGraphic.setModeType(userCXInfo.getModeType());

        CXInfo cxInfo = userCXInfo.getCxInfo();
        if (cxInfo != null) {
            userCommonMGraphic.setName(cxInfo.getName());
            if (cxInfo.getSignature() != null && !"".equals(cxInfo.getSignature())) {
                userCommonMGraphic.setSignature(cxInfo.getSignature());
            }
        }
    }

    private void judgeUserCxInfoModtypeCount(int modelType, int userCXinfoCount, int countunit) throws SystemException {
        // TODO need to refactor, violate the DRY
        Integer tempInt = userCXinfoCount;

        String modeTypeName = "";
        boolean hasError = false;
        int limitNumber = 0;

        if (modelType == Constants.TIME_SPAN_MODE) {
            modeTypeName = "时间段模式";
            if (tempInt + countunit > Constants.TIME_SPAN_MODE_MAX_COUNT) {
                hasError = true;
                limitNumber = Constants.TIME_SPAN_MODE_MAX_COUNT;
            }

        }

        if (modelType == Constants.SPECIAL_PHONENO_MODE) {
            modeTypeName = "特定号码模式";
            if (tempInt + countunit > Constants.SPECIAL_PHONENO_MODE_MAX_COUNT) {
                hasError = true;
                limitNumber = Constants.SPECIAL_PHONENO_MODE_MAX_COUNT;
            }
        }

        if (modelType == Constants.STATUS_MODE) {
            modeTypeName = "状态模式";
            if (tempInt + countunit > Constants.STATUS_MODE_MAX_COUNT) {
                hasError = true;
                limitNumber = Constants.STATUS_MODE_MAX_COUNT;
            }
        }

        if (tempInt + countunit < 0) {
            throw new CXServerBusinessException(new Exception("the time span mode count is over the limit "), modeTypeName
                    + "个数已经为空");
        }

        if (hasError) {
            throw new CXServerBusinessException(new Exception("the special phoneNO mode count is over the limit "),
                    modeTypeName + "个数已经超过限额" + limitNumber + "个");
        }
    }

    /**
     * 处理 CXInfo对象.
     * <p/>
     * If necessary, describe how it does and how to use it.
     * </P>
     *
     * @param cxInfo
     * @param imsi
     * @throws com.server.cx.exception.SystemException
     *
     */
    private void dealWithCXInfo(CXInfo cxInfo, String imsi) throws SystemException {
        // TODO 添加图片到业务服务器.
        // 从手机本地添加
        if (cxInfo.getId() == null || "".equals(cxInfo.getId())) {
            if (cxInfo.getFileData() != null) {
                byte[] contents = Base64Encoder.decode(cxInfo.getFileData(), Base64Encoder.DEFAULT);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(contents);
                Long fileLength = (long) contents.length;
                cxInfo = restSender.uploadCXInfo(inputStream, cxInfo, imsi, fileLength);
            }
        }
        userCommonMGraphic.setName(cxInfo.getName());
        userCommonMGraphic.setSignature(cxInfo.getSignature());
        userCommonMGraphic.setModifyTime(new Date());
    }

    /**
     * 更新 彩像关联关系时间戳.
     */
    private void updateUserCXInfoTimeStamp(UserCommonMGraphic mgraphic) {
        Date date = mgraphic.getModifyTime();
        if (date == null) {
            mgraphic.setModifyTime(date);
        }
    }

    @Override
    public String deleteMGraphicStoreMode(String id, String imsi) throws SystemException {
        String dealwithResult = "";
        UserCommonMGraphic userCommonMGraphic = userCommonMGraphicDao.findOne(id);
        if (userCommonMGraphic != null) {


        } else {
            dealwithResult = StringUtil.generateXMLResultString("ERROR", "数据不存在");
        }

        return dealwithResult;
    }

    @Override
    public List<UserCXInfo> retrieveUserCXInfos(Map<String, String> params) {
        String imsi = params.get(Constants.IMSI_STR);
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        List<UserCommonMGraphic> list = userCommonMGraphicDao.getAllMGraphicStoreModeByUserId(userInfo.getId());

        List<UserCXInfo> userCXInfos = Lists.transform(list, businessFunctions.mGraphicStoreModeTransformToUserCXInfo());

        return userCXInfos;
    }

}
