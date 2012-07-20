package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.*;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.entity.cx.Signature;
import com.server.cx.entity.cx.UserCXInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXInfoManagerService;
import com.server.cx.service.cx.UserCXInfoManagerService;
import com.server.cx.util.StringUtil;
import com.server.cx.xml.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service("userCXInfoManagerService")
@Transactional
public class UserCXInfoManagerServiceImpl implements UserCXInfoManagerService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserCXInfoDao userCXInfoDao;
    
    @Autowired
    private GenericUserCXInfoDao genericUserCXInfoDao;
    @Autowired
    private GenericUserInfoDao genericUserInfoDao;
    @Autowired
    private GenericCXInfoDao genericCXInfoDao;
    @Autowired
    private CXInfoManagerService cxInfoManagerService;
    @Autowired
    private GenericSignatureDao genericSignatureDao;
    @Autowired
    private SignatureDao signatureDao;
    
    private boolean hasRemovedTheSameUserCXInfo;
    
    public UserCXInfoManagerServiceImpl(){
        
    }
    
    @Override
    public String dealWithUserCXInfoAdding(Result result,String serverPath) throws SystemException {
        
        List<UserCXInfo> userCXInfos = result.getUserCXInfos();
        
        if(userCXInfos==null || userCXInfos.size() <1){
            return StringUtil.generateXMLResultString(Constants.NULL_INPUT_FLAG, "无数据输入");
        }
        
        UserCXInfo userCXInfo = userCXInfos.get(0);
        
        String imsi = userCXInfo.getImsi();
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        
        //TODO 如果用户信息为空的话提醒注册:
        if(userInfo == null){
            //TODO return 用户未注册.
            return StringUtil.generateXMLResultString(Constants.USER_DATA_ERROR_FLAG, "用户未注册");
        }
        
        removeTheSameUserCXInfo(userInfo.getId(),userCXInfo);
        
        Long userCXInfoId = userCXInfo.getId();
        
        //UserCXInfoModeCount modeCount =  userInfo.getModeCount();
        //Integer modelType = userCXInfo.getModeType();
        
        //WORKAROUND 因为XMLAdapter 暂时无法inject DAO bean 所以移到 service 层 去save signature;
        updateSignatureOfTheUserCXInfo(userCXInfo);
        
        //用户自定义的用户设定彩像.
        userCXInfo.setType(3);
        
        CXInfo cxInfo = null;
        if( userCXInfoId == null || userCXInfoId == 0){
            //一般模式允许添加
            //commonModeSpecialDealwith(userCXInfo, userInfo);
            //全新的用户彩像信息
            //addTheCountOfModelCount(modelType,modeCount);
            int count = userCXInfoDao.getUserCXInfosCountByModetype(userInfo.getId(), userCXInfo.getModeType());
            judgeUserCxInfoModtypeCount(userCXInfo.getModeType(),count,1);
            cxInfo = dealWithCXInfo(serverPath, userCXInfo);
            setUpUserCXInfoRelationShip(userCXInfo, userInfo, cxInfo);
            updateUserCXInfoTimeStamp(userCXInfo);
            userCXInfo.setId(null);
            genericUserCXInfoDao.save(userCXInfo);
        }
        //这是一次彩像的编辑操作
        else{
            //排除如果数据已经被
            boolean isUserCXInfoExists = genericUserCXInfoDao.exists(userCXInfo.getId());
            if(isUserCXInfoExists){
                int oldModType = userCXInfoDao.getCurrentUserCXInfoModeType(userCXInfo.getId());
                //WORKAROUND 因为一般模式只能有一个,编辑的时候,
                if(oldModType != userCXInfo.getModeType()){
                    int oldModeTypeCount = userCXInfoDao.getUserCXInfosCountByModetype(userInfo.getId(),oldModType);
                    int newModeTypeCount = userCXInfoDao.getUserCXInfosCountByModetype(userInfo.getId(),userCXInfo.getModeType());
                    judgeUserCxInfoModtypeCount(oldModType, oldModeTypeCount, -1);
                    judgeUserCxInfoModtypeCount(userCXInfo.getModeType(), newModeTypeCount, 1);
                }

                cxInfo = dealWithCXInfo(serverPath, userCXInfo);
                setUpUserCXInfoRelationShip(userCXInfo, userInfo, cxInfo);
                updateUserCXInfoTimeStamp(userCXInfo);
                genericUserCXInfoDao.save(userCXInfo);
            }else{
                return StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据不存在");
            }
            
        }
        Long cxInfoId = cxInfo.getId();
        userCXInfoId = userCXInfo.getId();
        
        String dealResult = ""; 
        if(hasRemovedTheSameUserCXInfo){
            dealResult = StringUtil.generateAddUserCXInfoSuccesfulXMLResultString(Constants.SUCCESS_FLAG, "数据已经更新",cxInfoId,userCXInfoId); 
        }else{
            dealResult = StringUtil.generateAddUserCXInfoSuccesfulXMLResultString(Constants.SUCCESS_FLAG, "操作成功",cxInfoId,userCXInfoId);
        }
        return dealResult;
    }

    private void updateSignatureOfTheUserCXInfo(UserCXInfo userCXInfo) throws SystemException {
        Signature signature = userCXInfo.getSignature();
        if(signature != null && signature.getId() == null){
            Signature tempSiganture = signatureDao.findSignatureByContent(signature.getContent());
            if(tempSiganture == null){
                genericSignatureDao.save(signature);
            }else{
                userCXInfo.setSignature(tempSiganture);
            }
        }
    }

    private void removeTheSameUserCXInfo(Long userId,UserCXInfo userCXInfo) throws SystemException{
        List<Long> ids = userCXInfoDao.getIdOfTheSameUserCXInfo(userId, userCXInfo);
        hasRemovedTheSameUserCXInfo = false;
        for(Long id:ids){
            if(!id.equals(userCXInfo.getId())){
                genericUserCXInfoDao.delete(id);
                hasRemovedTheSameUserCXInfo = true;
            }
        }
    }
    
    
    private void judgeUserCxInfoModtypeCount(int modelType,int userCXinfoCount, int countunit) throws SystemException {
        //TODO need to refactor, violate the DRY
        Integer tempInt = userCXinfoCount;
        
        String modeTypeName = "";
        boolean hasError = false;
        int limitNumber = 0;
        
        
        if(modelType == Constants.TIME_SPAN_MODE){
            modeTypeName = "时间段模式";
            if(tempInt+countunit> Constants.TIME_SPAN_MODE_MAX_COUNT){
                hasError = true;
                limitNumber = Constants.TIME_SPAN_MODE_MAX_COUNT;
            }
            
        }
        
        if(modelType == Constants.SPECIAL_PHONENO_MODE){
            modeTypeName = "特定号码模式";
            if(tempInt+countunit> Constants.SPECIAL_PHONENO_MODE_MAX_COUNT){
                hasError = true;
                limitNumber = Constants.SPECIAL_PHONENO_MODE_MAX_COUNT;
            }
        }
        
        if(modelType == Constants.STATUS_MODE){
            modeTypeName = "状态模式";
            if(tempInt+countunit > Constants.STATUS_MODE_MAX_COUNT){
                hasError = true;
                limitNumber = Constants.STATUS_MODE_MAX_COUNT;
            }
        }
        
        if(tempInt+countunit<0){
            throw new CXServerBussinessException( new Exception("the time span mode count is over the limit "),modeTypeName+"个数已经为空");
        }
        
        if(hasError){
            throw new CXServerBussinessException( new Exception("the special phoneNO mode count is over the limit "),modeTypeName+"个数已经超过限额"+limitNumber+"个");
        }
    }

    /**
     * 把userCXInfo 关联上 cxinfo 和 userInfo;
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param userCXInfo
     * @param userInfo
     * @param cxInfo
     */
    private void setUpUserCXInfoRelationShip(UserCXInfo userCXInfo, UserInfo userInfo, CXInfo cxInfo) {
        userCXInfo.setUserInfo(userInfo);
        userCXInfo.setCxInfo(null);
        List<CXInfo> cxInfos = userCXInfo.getCxInfos();
        if(cxInfos == null){
            cxInfos = Lists.newArrayList();
        }
        cxInfos.add(cxInfo);
        userCXInfo.setCxInfos(cxInfos);
    }

    /**
     * 处理 CXInfo对象.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param serverPath
     * @param userCXInfo
     * @return
     * @throws com.server.cx.exception.SystemException
     */
    private CXInfo dealWithCXInfo(String serverPath, UserCXInfo userCXInfo) throws SystemException {
        CXInfo cxInfo;
        cxInfo = userCXInfo.getCxInfo();
        //这是一个CX实体更新的操作.
        //由于使用本地彩像,服务端要生成对应的彩像信息.
        if(cxInfo.getId() == 0){
            cxInfo.setIsServer(-1);
            cxInfoManagerService.addNewCXInfo(serverPath, cxInfo);
        }else{
           Long cxInfoId = cxInfo.getId();
           cxInfo = genericCXInfoDao.findOne(cxInfoId);
        }
        return cxInfo;
    }

    /**
     *更新 彩像关联关系时间戳. 
     * @param userCXInfo
     */
    private void updateUserCXInfoTimeStamp(UserCXInfo userCXInfo) {
        Date date = userCXInfo.getTimeStamp();
        if(date == null){
            userCXInfo.setTimeStamp(new Date());
        }
    }

  

    //TODO hard code need to fix
    /* (non-Javadoc)
     * @see com.server.cx.service.CXManagerService#deleteUserCXInfo(int, java.lang.String)
     */
    @Override
    public String deleteUserCXInfo(Long id, String imsi) throws SystemException {
        //String result = Preconditions.checkNotNull(imsi,"imsi为空");
        String dealwithResult = "";
        UserCXInfo userCXInfo = genericUserCXInfoDao.findOne(id);
        
        if(userCXInfo != null){
            //排除默认彩像
            if(-1!=userCXInfo.getModeType()){
                UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
                genericUserCXInfoDao.delete(userCXInfo);
                dealwithResult = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "操作成功"); 
                genericUserInfoDao.save(userInfo);
            }else{
                dealwithResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "该彩像不是用户设定的彩像");
            }
            
        }else{
            dealwithResult = StringUtil.generateXMLResultString("ERROR", "数据不存在");
        }
        
        return dealwithResult;
    }

    @Override
    public List<UserCXInfo> retrieveUserCXInfos(Map<String, String> params) {
        String imsi = params.get(Constants.IMSI_STR);
        List<UserCXInfo> list = userCXInfoDao.loadUserCXInfoByImsi(imsi);
        CXInfo cxInfo = null;
        List<CXInfo> cxInfos;
        int size = -1;
        for(UserCXInfo tempUserCXInfo:list){
            cxInfos = tempUserCXInfo.getCxInfos();
            size = cxInfos.size();
            cxInfo = cxInfos.get(new Random().nextInt(size));
            tempUserCXInfo.setCxInfo(cxInfo);
        }
        return list;
    }

}
