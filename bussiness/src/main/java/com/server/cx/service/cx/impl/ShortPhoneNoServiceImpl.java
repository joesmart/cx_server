package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.GenericShortPhoneNoDao;
import com.server.cx.dao.cx.ShortPhoneNoDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.ShortPhoneNo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.ShortPhoneNoService;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.Result;
import com.server.cx.xml.UserSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("shortPhoneNoService")
@Transactional
public class ShortPhoneNoServiceImpl implements ShortPhoneNoService {

    @Autowired
    GenericShortPhoneNoDao genericShortPhoneNoDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private ShortPhoneNoDao shortPhoneNoDao;
    
    private String dealResult;
    
    @Override
    public String addNewShorePhoneNo(String imsi, String shortPhoneNos) throws SystemException {
        
        Preconditions.checkNotNull(imsi);
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        
        if(null == userInfo){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }
        shortPhoneNoDao.deleteAllOldShortPhonNos(userInfo.getId());
        //没有shortPhoneNos为空时
        if(shortPhoneNos == null || "".equals(shortPhoneNos)){
            return StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "用短号吗信息清除成功");
        }
        List<String> shortPhoneNoList = ValidationUtil.split(shortPhoneNos, ",");
        
        for(String tempShortPhoneNo:shortPhoneNoList){
            ShortPhoneNo shortPhoneNo = new ShortPhoneNo();
            shortPhoneNo.setShortPhoneNo(tempShortPhoneNo);
            shortPhoneNo.setUser(userInfo);
            genericShortPhoneNoDao.save(shortPhoneNo);
        }
        dealResult = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "用户短号码添加成功");
        return dealResult;
    }

    @Override
    public String retriveAllShortPhoneNos(String imsi) throws SystemException {
        Preconditions.checkNotNull(imsi);
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        List<ShortPhoneNo> shortPhoneNoObjects = userInfo.getShortPhoneNoList();

        List<String> shortPhoneNos = Lists.newArrayList();
        for(ShortPhoneNo tempShortPhoneNo: shortPhoneNoObjects){
            shortPhoneNos.add(tempShortPhoneNo.getShortPhoneNo());
        }
            
        String shortPhoneNo = ValidationUtil.join(shortPhoneNos, ",");
        
        UserSetting userSetting = new UserSetting();
        userSetting.setImsi(imsi);
        userSetting.setShortPhoneNos(shortPhoneNo);
        
        Result result = new Result();
        result.setFlag(Constants.SUCCESS_FLAG);
        result.setContent("用户短号检索成功");
        result.setUserSetting(userSetting);
        
        return StringUtil.generateXMLResultFromObject(result);
    }

    
}
