package com.server.cx.util;

import com.server.cx.constants.Constants;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.*;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.util.XMLMarshalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class ServiceChooseUtil {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private CallingService callingService;
    @Autowired
    private UserCXInfoManagerService userCXInfoManagerService;
    @Autowired
    private SmsMessageService smsMessageService;
    @Autowired
    private VersionInfoService versionInfoService;
    @Autowired
    private UserFavoritesService userFavoriteService;
    @Autowired
    private UserStatusManagerService userStatusManagerService;

    private XMLParser xmlParser = XMLParser.getInstance();

    public ServiceChooseUtil() {
    }

    public String chooseService(String requestUrl, HttpServletRequest req, HttpServletResponse resp, String data) {
        // get parameter from body
        // ??receive parameter?? 从xml中获取
        String result = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "服务器内部错误");
        try {

            Map<String, String> mapParams = getAllRequestParmeters(requestUrl, req, data);
            // 用户注册
            if (requestUrl.equals(Constants.REGISTER_URL)) {
//                result = registerService.registe(mapParams);
            } else if (requestUrl.equals(Constants.UPDATE_CLIENT_URL)) {
                // 升级
                String imsi = mapParams.get(Constants.IMSI_STR);
                String version = mapParams.get("version");
//                result = versionInfoService.checkIsTheLatestVersion(imsi, version);
            } else if (requestUrl.equals(Constants.CALLING_USERCXINFO_URL)) {
                // 区分主叫和被叫用户.
                // 通话时获取主叫用户或者被叫用户的彩像信息
//                result = callingService.getCallingMGraphic(mapParams, null);
            } else if (requestUrl.equals(Constants.INVITE_FRIENDS_URL)) {
                // 邀请好友
                String imsi = mapParams.get(Constants.IMSI_STR);
                String phoneNos = mapParams.get(Constants.PHONE_NO_STR);
                result = smsMessageService.inviteFriends(imsi, phoneNos);
            } else if (requestUrl.equals(Constants.ATTENTION_URL)) {

            } else if (requestUrl.equals(Constants.CX_CLIENT_ABOUT_URL)) {
                // 关于
            } else if (requestUrl.equals(Constants.GET_USERCXINFO_URL)) {
                // 获取我的彩像列表
                result = retrieveUserCXInfos(resp, mapParams, result);
            } else if (requestUrl.equals(Constants.GET_ALL_CXINFODATA)) {
                // 浏览彩像数据
                // result = browserAllData(mapParams);
            } else if (requestUrl.equals(Constants.DELETE_USERCXINFO)) {
                // 删除用户设定的彩像.
                result = deleteUserCXInfo(mapParams);
            } else if (requestUrl.equals(Constants.ADD_NEW_USER_FAVORITES)) {
                // 添加用户收藏
                String imsi = mapParams.get(Constants.IMSI_STR);
                String cxInfoId = mapParams.get(Constants.CXINFO_ID);
//                result = userFavoriteService.addNewUserFavorites(imsi, cxInfoId);
            } else if (requestUrl.equals(Constants.DELETE_USER_FAVORITES)) {
                // 删除用户收藏
                String imsi = mapParams.get(Constants.IMSI_STR);
                String userFavoritesId = mapParams.get(Constants.USER_FAVORITES_ID);
//                result = userFavoriteService.deleteUserFavorites(imsi, userFavoritesId);

            } else if (requestUrl.equals(Constants.BROSWER_USER_FAVORITES)) {
                // 浏览用户收藏
                String imsi = mapParams.get(Constants.IMSI_STR);
                String requestPage = mapParams.get(Constants.REQUEST_PAGE);
                String requestPageSize = mapParams.get(Constants.REQUEST_PAGE_SIZE);
                String typeId = mapParams.get(Constants.TYPE_ID);
//                result = userFavoriteService.getAllUserFavorites(imsi, typeId, requestPage, requestPageSize);
            } else if (requestUrl.equals(Constants.ADD_NEW_USERSTATUS_USERCXINFO)) {
                // TODO 添加一个新的状态用户设定彩像.
                String imsi = mapParams.get(Constants.IMSI_STR);
                String type = mapParams.get(Constants.TYPE);
                String signature = mapParams.get(Constants.SIGNATURE_STR);
                String validTime = mapParams.get(Constants.VALIDTIME_STR);
                result = userStatusManagerService.addNewUserStatus(imsi, type, signature, validTime);
            } else if (requestUrl.equals(Constants.RETRIVE_USERSTATUS_USERCXINFO_BY_STATUS)) {
                // TODO 返回状态列表对象
                String imsi = mapParams.get(Constants.IMSI_STR);
                result = userStatusManagerService.retriveAllStatusMGraphic(imsi);
            } else if (requestUrl.equals(Constants.GET_CURRENT_USERSTATUS)) {
                // 获取当前用户设定的彩像
                String imsi = mapParams.get(Constants.IMSI_STR);
                result = userStatusManagerService.getCurrentUserStatus(imsi);
            } else if (requestUrl.equals(Constants.DELETE_CURRENTUSER_STATUS)) {
                // 删除当前用户设定的彩像
                String imsi = mapParams.get(Constants.IMSI_STR);
                result = userStatusManagerService.deletCurrentUserStatus(imsi, null, null);
            } else {
                result = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "服务器无内容返回");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } catch (SystemException e) {
            result = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, e.getLocalMessage());
        } catch (Exception e) {
            SystemException systemexception = new CXServerBusinessException(e, "系统内部错误");
            result = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, systemexception.getLocalMessage());
        }
        return result;
    }

    /**
     * 删除用户指定的彩像(该彩像必须为用户自己的彩像)
     *
     * @param mapParams
     * @return
     * @throws com.server.cx.exception.SystemException
     *
     */
    private String deleteUserCXInfo(Map<String, String> mapParams) throws SystemException {
        String imsi = mapParams.get(Constants.IMSI_STR);
        String userCXInfoId = mapParams.get(Constants.ID_STR);

        ValidationUtil.checkParametersNotNull(userCXInfoId, imsi);

        String result = userCXInfoManagerService.deleteMGraphicStoreMode(userCXInfoId, imsi);

        return result;
    }

    private String retrieveUserCXInfos(HttpServletResponse resp, Map<String, String> mapParams, String result) {
        // 返回 系统 URL
        String imsi = mapParams.get(Constants.IMSI_STR);
        List<com.server.cx.dto.UserCXInfo> resultList = Collections.emptyList();
        if (imsi != null && !"".equals(imsi)) {
            resultList = userCXInfoManagerService.retrieveUserCXInfos(mapParams);
            com.server.cx.dto.Result xmlResult = new com.server.cx.dto.Result();

            if (resultList != null && resultList.size() > 0) {
                xmlResult.setFlag(Constants.SUCCESS_FLAG);
                xmlResult.setUserCXInfos(resultList);
                XMLMarshalUtil marshalUtil = new XMLMarshalUtil(xmlResult);
                result = marshalUtil.writeOut();
            } else {
                result = StringUtil.generateXMLResultString(Constants.DATA_NOTFOUND_FLAG, "数据未找到");

            }

        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        return result;
    }

    /**
     * 从客户端请求中获取所有的参数数据.
     * <p/>
     * If necessary, describe how it does and how to use it.
     * </P>
     *
     * @param requestUrl
     * @param req
     * @param data
     * @return
     */
    private Map<String, String> getAllRequestParmeters(String requestUrl, HttpServletRequest req, String data) {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        if (data != null && !data.equals("")) {
            xmlParser.parser(data, mapParams, requestUrl);
        }
        // get parameter from url
        Enumeration<String> enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = req.getParameter(name);
            mapParams.put(name, value);
        }
        // TODO common error need to fix
        Set<String> keys = mapParams.keySet();
        for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
            String key = iter.next();
            String value = mapParams.get(key);
            System.out.println(key + " =  " + value);
        }
        // check the phone number format
        String phoneNo = mapParams.get(Constants.PHONE_NO_STR);
        if (checkNull(phoneNo) != true) {
            phoneNo = phoneNo.replace("-", "");
            mapParams.put(Constants.PHONE_NO_STR, phoneNo);

        }
        String cx_phoneNo = mapParams.get(Constants.CXINFO_PHONE_NO_STR);
        if (checkNull(cx_phoneNo) != true) {
            cx_phoneNo = cx_phoneNo.replace("-", "");
            mapParams.put(Constants.CXINFO_PHONE_NO_STR, cx_phoneNo);
        }

        return mapParams;
    }

    public int getModeFromUrl(StringBuffer url) {
        int modeIndex = url.indexOf("/set/mode/");
        // 10 -- length of string /set/mode
        int modeEndIndex = url.indexOf("/", modeIndex + 10);
        System.out.println(url.substring(modeIndex + 10, modeEndIndex));
        return Integer.parseInt(url.substring(modeIndex + 10, modeEndIndex));
    }

    public String getTypeFromUrl(StringBuffer url) {
        int startIndex = url.indexOf("/type/");
        return url.substring(startIndex + 6);

    }

    public boolean checkNull(String s) {
        if (s == null || s.equals("") || s.equals("null")) {
            return true;
        }
        return false;
    }
}
