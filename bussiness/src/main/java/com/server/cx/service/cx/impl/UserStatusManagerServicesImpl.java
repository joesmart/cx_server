package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.SignatureDao;
import com.server.cx.dao.cx.UserCXInfoDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.*;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserStatusManagerService;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.StatusType;
import com.server.cx.util.business.UserStatusUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("userStatusManagerService")
@Transactional
public class UserStatusManagerServicesImpl implements UserStatusManagerService {

    @Autowired
    UserInfoDao userinfoDao;
    
    @Autowired
    UserCXInfoDao userCXInfoDao;
    
    @Autowired
    SignatureDao signatureDao;
    
    @Autowired
    GenericDaoHibernate<Signature, Long> genericSignatureDao;
    
    @Autowired
    GenericDaoHibernate<UserCXInfo, Long> genericUserCXInfoDao;

    @Autowired
    GenericDaoHibernate<UserInfo, Long> genericUserInfoDao;
    
    @Autowired
    GenericDaoHibernate<UserStatus,Long> genericUserStatusDao;

    private UserInfo userInfo;
    private Signature signature;
    private UserCXInfo userCXInfo;
    
    @Override
    public String addNewUserStatus(String imsi, String type, String signatureContent, String validTime) throws SystemException {
            
        ValidationUtil.checkParametersNotNull(imsi,type,signatureContent,validTime);
        validateUserStatusType(type);
        
        userInfo = userinfoDao.getUserInfoByImsi(imsi);
        Preconditions.checkNotNull(userInfo);
        
        UserStatus userStatus = saveOrUpdateUserStatus(type, signatureContent, validTime);
    
        return convertCurrentUsertStatusToXMLString(userStatus,userStatus.getUserCXInfo());

    }

    private UserStatus saveOrUpdateUserStatus(String type, String signatureContent, String validTime)
        throws SystemException {
        UserStatus userStatus = userInfo.getUserStatus();
        
        getSignatureByContent(signatureContent);
        
        Timestamp begingTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = UserStatusUtil.getStatusUserCXInfo(begingTime, validTime);
        Integer statusType = Integer.parseInt(type);
        StatusType status = StatusType.getStatusTypeByTypeNumber(statusType);
        
        if(userStatus == null){
            userStatus = new UserStatus(status.getName(),statusType,begingTime,endTime);
            userStatus.setValidTime(validTime);
            
            userCXInfo = new UserCXInfo();
            makeUpUserCXInfo(status);
            setupRelationship(userStatus);
            genericUserStatusDao.persist(userStatus);
            
        }else{
            userStatus.setBegingTime(begingTime);
            userStatus.setEndTime(endTime);
            userStatus.setName(status.getName());
            userStatus.setType(statusType);
            userStatus.setValidTime(validTime);
            userCXInfo = userStatus.getUserCXInfo();
            if(userCXInfo == null){
                userCXInfo = new UserCXInfo();
            }
            makeUpUserCXInfo(status);
            setupRelationship(userStatus);
            genericUserInfoDao.merge(userInfo);
        }
        return userStatus;
    }

    private void setupRelationship(UserStatus userStatus) {
        userStatus.setUserCXInfo(userCXInfo);
        userStatus.setSignature(signature);
        userStatus.setUser(userInfo);
        userInfo.setUserStatus(userStatus);
    }

    private void makeUpUserCXInfo(StatusType status) {
        userCXInfo.setType(2);
        userCXInfo.setModeType(-1);
        userCXInfo.setStartTime(0);
        userCXInfo.setEndTime(24);
        userCXInfo.setName(status.getName());
        userCXInfo.setUserInfo(userInfo);
        userCXInfo.setStatusType(status.getType());
        userCXInfo.setImsi(userInfo.getImsi());
        Date currentTime = new Date(System.currentTimeMillis());
        userCXInfo.setAddTime(currentTime);
        userCXInfo.setTimeStamp(currentTime);
        userCXInfo.setSignature(signature);
        
        List<CXInfo> cxInfos = Lists.newArrayList();
        StatusPackage statusPackage = userInfo.getStatusPackage();
        CXInfo cxInfo = statusPackage.getCXInfoByStatusType(status);
        cxInfos.add(cxInfo);
        
        userCXInfo.setCxInfos(cxInfos);
    }

    private void getSignatureByContent(String signatureContent) throws SystemException {
        signature = signatureDao.findSignatureByContent(signatureContent);
        if(signature == null){
            signature = new Signature();
            signature.setContent(signatureContent);
            genericSignatureDao.save(signature);
        }
    }

    private void validateUserStatusType(String type) throws InvalidParameterException {
        if(!ValidationUtil.isDigit(type)){
            throw new InvalidParameterException("状态类型不是数字类型");
        }
       
        if(!StatusType.isValidStatusType(Integer.parseInt(type))){
            throw new InvalidParameterException("传入的状态类型不是有效的状态类型");
        }
    }

    @Override
    public String retriveUserStatusUserCXInfoByStatus(String imsi, String type) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi,type);
        validateUserStatusType(type);
        
        isUserExists(imsi);
        String result = "";
        Integer statusType = Integer.parseInt(type);
        StatusType status = StatusType.getStatusTypeByTypeNumber(statusType);
        StatusPackage statuspackage = userInfo.getStatusPackage();
        if(statuspackage != null){
            List<CXInfo> cxInfos = Lists.newArrayList();
            CXInfo cxInfo = statuspackage.getCXInfoByStatusType(status);
            cxInfos.add(cxInfo);
            
            Result xmlResult = new Result();
            xmlResult.setFlag(Constants.SUCCESS_FLAG);
            xmlResult.setCxInfos(cxInfos);
            xmlResult.setUserStatus(type);
            result = StringUtil.generateXMLResultFromObject(xmlResult);
        }else{
            result = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "操作失败");
        }
        return result;
    }

    private void isUserExists(String imsi) {
        userInfo = userinfoDao.getUserInfoByImsi(imsi);
        Preconditions.checkNotNull(userInfo);
    }

    @Override
    public String getCurrentUserStatus(String imsi) throws SystemException {
        
        ValidationUtil.checkParametersNotNull(imsi);
        isUserExists(imsi);
        String result = StringUtil.generateXMLResultString("USERSTATUS_NOT_EXISTS", "用户设置的状态不存在");
        UserStatus userstatus = userInfo.getUserStatus();
        if(userstatus != null){
            if (ValidationUtil.isCanEnableCurrentUserStatus(userstatus.getBegingTime(), userstatus.getEndTime())) {
                UserCXInfo userCXInfo = userstatus.getUserCXInfo();
                if(userCXInfo == null){
                    return result;
                }
                userCXInfo.setSignature(userstatus.getSignature());
                result = convertCurrentUsertStatusToXMLString(userstatus, userCXInfo);
                return result;
            }else{
                userInfo.setUserStatus(null);
                userstatus.setUser(null);
                genericUserStatusDao.delet(userstatus);
                return StringUtil.generateXMLResultString("CURRENTUSERSTATUS_IS_INVALID", "上一个设定的状态已经失效");
            }
            
        }else{
            //TODO 没有用户状态返回空用户用户状态
            return result; 
        }
    }

    private String convertCurrentUsertStatusToXMLString(UserStatus userstatus, UserCXInfo userCXInfo) {
        String result;
        Result xmlResult = new Result();
        xmlResult.setFlag(Constants.SUCCESS_FLAG);
        xmlResult.setUserStatus(String.valueOf( userstatus.getType() ) );
        xmlResult.setUserStatusValidTime(userstatus.getValidTime());
        String beingTimeString = StringUtil.convertTimeStampToString(userstatus.getBegingTime());
        String endTimeString = StringUtil.convertTimeStampToString(userstatus.getEndTime());
        xmlResult.setBeginTime(beingTimeString);
        xmlResult.setEndTime(endTimeString);
        
        generateARandomCXInfo(userCXInfo);
        
        List<UserCXInfo> userCXInfos = Lists.newArrayList();
        userCXInfos.add(userCXInfo);
        xmlResult.setUserCXInfos(userCXInfos);
        result = StringUtil.generateXMLResultFromObject(xmlResult);
        return result;
    }

    @Override
    public String deletCurrentUserStatus(String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi);
        isUserExists(imsi);
        String result = StringUtil.generateXMLResultString("USERSTATUS_NOT_EXISTS", "用户设置的状态不存在");
        UserStatus userstatus = userInfo.getUserStatus();
        
        if(userstatus != null){
           UserCXInfo userCXInfo = userstatus.getUserCXInfo();
           if(userCXInfo != null){
              genericUserCXInfoDao.delet(userCXInfo);
              userstatus.setUserCXInfo(null);
              genericUserInfoDao.merge(userInfo);
              result = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "删除成功");
           }
        }
        
        return result;
    }

    public void generateARandomCXInfo(UserCXInfo usercxinfo) {
        //为了保持传输的协议一致
        usercxinfo.setCxInfo(usercxinfo.getRandomUserCXInfo());
    }
}
