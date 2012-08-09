package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.SignatureDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.model.CXInfo;
import com.server.cx.model.Result;
import com.server.cx.model.UserCXInfo;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserStatusManagerService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.RestSender;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.util.XMLMarshalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userStatusManagerService")
@Transactional
public class UserStatusManagerServicesImpl implements UserStatusManagerService {

    @Autowired
    SignatureDao signatureDao;
    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    @Qualifier("statusRestSender")
    private RestSender restSender;

    @Autowired
    private BusinessFunctions businessFunctions;

    private UserInfo userInfo;
    private UserCXInfo userCXInfo;

    // TODO 移动到 添加我的彩像接口.
    @Override
    public String addNewUserStatus(String imsi, String type, String signatureContent, String validTime)
            throws SystemException {

        ValidationUtil.checkParametersNotNull(imsi, type, signatureContent, validTime);

        userInfo = userInfoDao.getUserInfoByImsi(imsi);
        Preconditions.checkNotNull(userInfo);
        return "";
    }

    @Override
    public String retriveAllStatusMGraphic(final String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi);
        isUserExists(imsi);
        setCurrentUserStatusMGraphicStoreMode();
        List<CXInfo> cxInfos = requestCXInfoResourceData();
        List<UserCXInfo> userCXInfos = convertCXInfosToUserCXInfo(imsi, cxInfos);
        String xmlresult = gnerateXMLResult(userCXInfos);
        return xmlresult;
    }

    private List<UserCXInfo> convertCXInfosToUserCXInfo(final String imsi, List<CXInfo> cxInfos) {
        List<UserCXInfo> userCXInfos = Lists.transform(cxInfos,businessFunctions.cxInfoTransformToUserCXInfo(userCXInfo,imsi));
        return userCXInfos;
    }

    private void setCurrentUserStatusMGraphicStoreMode() {
        userCXInfo = null;
        userCXInfo = getCurrentValidStatusUserCXInfo();
    }

    @Override
    public UserCXInfo getCurrentValidStatusUserCXInfo() throws SystemException {
        if (userInfo != null) {
            //TODO need fix here since the UserInfo Id change to String typ By Joesmart
            UserCommonMGraphic mgraphicUserCommon = userCommonMGraphicDao.getMGraphicStoreModeByModeType(userInfo.getId(), 5);
            if (mgraphicUserCommon != null) {
                return mgraphicUserCommon.convertMGraphicStoreModeToUserCXInfo();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private String gnerateXMLResult(List<UserCXInfo> userCXInfos) {
        Result result = new Result();
        result.setFlag(Constants.SUCCESS_FLAG);
        result.setContent("用户状态列表");
        result.setUserCXInfos(userCXInfos);
        XMLMarshalUtil xmlMarshalUtil = new XMLMarshalUtil(Result.class);
        String xmlresult = xmlMarshalUtil.writeOut(result);
        return xmlresult;
    }

    @Override
    public void isUserExists(String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi);
        userInfo = userInfoDao.getUserInfoByImsi(imsi);
        Preconditions.checkNotNull(userInfo, "用户未注册");
    }

    @Override
    public String getCurrentUserStatus(String imsi) throws SystemException {

        ValidationUtil.checkParametersNotNull(imsi);
        isUserExists(imsi);
        String result = StringUtil.generateXMLResultString("USERSTATUS_NOT_EXISTS", "用户设置的状态不存在");
        return result;
    }

    @Override
    public String deletCurrentUserStatus(String imsi, String userCXInfoId, String cxInfoId) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi, userCXInfoId, cxInfoId);
        isUserExists(imsi);
        userCommonMGraphicDao.delete(userCXInfoId);
        List<CXInfo> cxInfos = requestCXInfoResourceData();
        List<UserCXInfo> userCXInfos = convertCXInfosToUserCXInfo(imsi, cxInfos);
        String xmlresult = gnerateXMLResult(userCXInfos);
        return xmlresult;
    }

    /**
     * 请求资源服务器数据.
     *
     * @return
     */
    private List<CXInfo> requestCXInfoResourceData() {
        List<CXInfo> cxInfos = restSender.getStatusCXInfos();
        return cxInfos;
    }

}
