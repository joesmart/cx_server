package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.CXInfoDao;
import com.server.cx.dao.cx.UserCXInfoDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.entity.cx.UserCXInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserStatus;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXCallingManagerService;
import com.server.cx.util.DataUtil;
import com.server.cx.util.business.UserCXInfoUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.Result;
import com.server.cx.xml.util.XMLMarshalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("cxCallingManagerService")
@Transactional
public class CXCallingManagerServiceImpl implements CXCallingManagerService {

    @Autowired
    private UserCXInfoDao userCXInfoDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private CXInfoDao cxInfoDao;

    public CXCallingManagerServiceImpl() {
    }

    @Override
    public String getCallingCXInfo(Map<String, String> paramsMap) throws SystemException {
        String userPhoneNo = paramsMap.get(Constants.PHONE_NO_STR);

        Result xmlResult = new Result();
        List<UserCXInfo> list = null;
        if (!DataUtil.checkNull(userPhoneNo)) {
            list = getUserCXInfoInCall(paramsMap);
            if (list != null && list.size() > 0) {
                xmlResult.setFlag(Constants.SUCCESS_FLAG);
                xmlResult.setUserCXInfos(list);
            } else {
                xmlResult.setFlag(Constants.DATA_NOTFOUND_FLAG);
                xmlResult.setContent("数据找不到!");
            }
        } else {
            xmlResult.setFlag(Constants.ERROR_FLAG);
            xmlResult.setContent("用户手机号码为空!");
        }
        XMLMarshalUtil marshalUtil = new XMLMarshalUtil(xmlResult);
        String result = marshalUtil.writeOut();
        return result;
    }

    private List<UserCXInfo> getUserCXInfoInCall(Map<String, String> params) throws SystemException {
        List<UserCXInfo> resultList = getTheCanllingCXInfoList(params);
        if (resultList != null && resultList.size() > 0) {
            return resultList;
        }
        return null;
    }

    //WORKAROUND 临时解决方案,需要重构
    private List<UserCXInfo> getTheCanllingCXInfoList(Map<String, String> params) throws SystemException {
        UserCXInfo userCXInfo = null;
        String callerPhoneNo = params.get(Constants.PHONE_NO_STR);
        String myselfPhoneNo = "";
        String imsi = params.get(Constants.IMSI_STR);

        Preconditions.checkNotNull(callerPhoneNo);
        Preconditions.checkNotNull(imsi);
        UserInfo callerUserInfo = null;
        if (!ValidationUtil.isPhoneNo(callerPhoneNo)) {
            throw new InvalidParameterException("用户手机号码格式不对");
        }
        UserInfo myselfInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (myselfInfo == null) {
            throw new InvalidParameterException("用户未注册");
        }

        //针对10086 指定的特别号码
        if("10086".equalsIgnoreCase(callerPhoneNo)){
        	userCXInfo = userCXInfoDao.getDefaulModeUserCXInfo();
        	List<CXInfo> list = cxInfoDao.getCMCCCXInfos();
        	if(list.size() > 0)
        		userCXInfo.setCxInfos(list);
        	
        	generateARandomCXInfo(userCXInfo);
        	return generateResultByUserCXInfo(userCXInfo);
        }
        
        
        //根据短号查询,有多个返回值的话就返回默认彩像.
        if (ValidationUtil.isShortPhoneNo(callerPhoneNo)) {
            List<UserInfo> userInfos = userInfoDao.getUserInfoByShortPhoneNo(callerPhoneNo);
            if (userInfos == null || userInfos.size() > 1 || userInfos.size() == 0) {
                userCXInfo = userCXInfoDao.getDefaulModeUserCXInfo();
                generateARandomCXInfo(userCXInfo);
                return generateResultByUserCXInfo(userCXInfo);
            } else {
                callerUserInfo = userInfos.get(0);
            }
        } else {
            callerUserInfo = userInfoDao.getUserInfoByPhoneNo(callerPhoneNo);
        }
        
        
        //找不到该呼叫用户,说明该呼叫的用户未开通该业务.
        if (callerUserInfo == null) {
            userCXInfo = userCXInfoDao.getDefaulModeUserCXInfo();
            generateARandomCXInfo(userCXInfo);
            return generateResultByUserCXInfo(userCXInfo);
        }
        //返回状态彩像.
        UserStatus userStatus = callerUserInfo.getUserStatus();
        if(userStatus != null ){
            if (ValidationUtil.isCanEnableCurrentUserStatus(userStatus.getBegingTime(), userStatus.getEndTime())) {
                UserCXInfo statusUserCXInfo = userStatus.getUserCXInfo();
                if(statusUserCXInfo != null){
                    generateARandomCXInfo(statusUserCXInfo);
                    statusUserCXInfo.setSignature(userStatus.getSignature());
                    return generateResultByUserCXInfo(statusUserCXInfo);
                }
            }
            
        }

        myselfPhoneNo = myselfInfo.getPhoneNo();

        //寻找特定号码.加时间模式
        List<UserCXInfo> callerUserCXInfos = userCXInfoDao.getAllUserCXInfosByUserId(callerUserInfo.getId());
        userCXInfo = filterOutTheUserCXInfo(callerUserCXInfos, myselfPhoneNo);
        

        if (userCXInfo == null) {
            userCXInfo = userCXInfoDao.getDefaulModeUserCXInfo();
        }
        generateARandomCXInfo(userCXInfo);
        return generateResultByUserCXInfo(userCXInfo);
    }

    public void generateARandomCXInfo(UserCXInfo usercxinfo) {
        //为了保持传输的协议一致
        usercxinfo.setCxInfo(usercxinfo.getRandomUserCXInfo());
    }

    private UserCXInfo filterOutTheUserCXInfo(List<UserCXInfo> userCXInfos, String specialPhoneNo) {
        UserCXInfo result = null;
        Map<String,UserCXInfo> map = Maps.newHashMap();
        int maxPriority = 0;
        int tempPriority = 0;
        if(userCXInfos == null || userCXInfos.size() ==0 ){
            return null;
        }else{
            
            for(UserCXInfo tempUserCXInfo:userCXInfos){
                tempPriority = UserCXInfoUtil.getPrioritrNumber(tempUserCXInfo,specialPhoneNo);
                if(tempPriority>= maxPriority){
                    maxPriority = tempPriority;
                }
                map.put(String.valueOf(tempPriority), tempUserCXInfo);
            }
        }
        if(maxPriority ==1 ) return null;
        
        //没有设定一般模式并且时间模式和特定号码模式都超出时间限制
        result = map.get(String.valueOf(maxPriority));
        if(maxPriority-1 == result.getModeType()){
            return null;
        }
        
        return result;
    }

    private List<UserCXInfo> generateResultByUserCXInfo(UserCXInfo userCXInfo) {
        List<UserCXInfo> result;
        result = null;
        result = new ArrayList<UserCXInfo>();
        result.add(userCXInfo);
        return result;
    }

}
