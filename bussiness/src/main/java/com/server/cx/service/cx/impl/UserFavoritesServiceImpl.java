package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.UserFavoritesDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.entity.cx.UserFavorites;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserFavoritesService;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.UserFavoritesUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userFavoritesService")
@Transactional
public class UserFavoritesServiceImpl implements UserFavoritesService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private GenericDaoHibernate<CXInfo, Long> genericCXInfoDao;
    @Autowired
    GenericDaoHibernate<UserFavorites,Long> genericUserFavoritesDao;
    @Autowired
    private UserFavoritesDao userFavoritesDao;
   
    private String dealResult = "";
    
    @Override
    public String addNewUserFavorites(String imsi,String cxInfoId) throws SystemException {
        
        //TODO use the throws exceptions to resolve this kind issue;
        //defense
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(cxInfoId);
        if (!ValidationUtil.isDigit(cxInfoId)){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据输入有误");
            return dealResult;
        }
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if(null == userInfo){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }
        
        CXInfo cxInfo = genericCXInfoDao.getById(Long.parseLong(cxInfoId));
        if(cxInfo == null){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "该彩像彩像已经被删除");
            return dealResult;
        }
        Long cxId = cxInfo.getId();
        Long userId = userInfo.getId();
        boolean isAlreandAddedInUserFavorites = userFavoritesDao.isAlreadAddedInUserFavorites(userId, cxId);
        if(isAlreandAddedInUserFavorites){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户已经收藏该彩像");
            return dealResult;
        }
        
        Integer totalCountOfUserFavorites = userFavoritesDao.getUserFavoritesTotalCount(userId);
        if(totalCountOfUserFavorites>=Constants.TOTAL_USERFAVORITES_COUNT){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户收藏已经超出限制的"+Constants.TOTAL_USERFAVORITES_COUNT+ "条!");
            return dealResult;
        }
        
        UserFavorites userFavorites = new UserFavorites();
        userFavorites.setUser(userInfo);
        userFavorites.setCxInfo(cxInfo);
        genericUserFavoritesDao.save(userFavorites);
        
        Result xmlResult = new Result();
        List<UserFavorites> userFavoritesList = Lists.newArrayList();
        userFavoritesList.add(userFavorites);
        xmlResult.setFlag(Constants.SUCCESS_FLAG);
        xmlResult.setContent("彩像收藏成功");
        xmlResult.setUserFavorites(userFavoritesList);
        dealResult = StringUtil.generateXMLResultFromObject(xmlResult);
        //dealResult = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "彩像收藏成功");
        return dealResult;
    }

    @Override
    public String deleteUserFavorites(String imsi, String userFavoritesId) {
        
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(userFavoritesId);
        if (!ValidationUtil.isMultiUserFavoritesId(userFavoritesId)){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据输入有误");
            return dealResult;
        }
        
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if(null == userInfo){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }
        
        String splitString = ",";
        
        List<Long> userFavoritesIdLongList = UserFavoritesUtil.convertDigitStrignIntoLongList(userFavoritesId, splitString);
        
        genericUserFavoritesDao.removeAll(userFavoritesIdLongList);
        dealResult = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "用户移除收藏成功");
        return dealResult;
    }

    @Override
    public String getAllUserFavorites(String imsi, String typeId, String requestPage, String pageSize) {
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(typeId);
        Preconditions.checkNotNull(requestPage);
        Preconditions.checkNotNull(pageSize);
        
        if (!ValidationUtil.isDigit(typeId) || !ValidationUtil.isDigit(requestPage) || !ValidationUtil.isDigit(requestPage)){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据输入有误");
            return dealResult;
        }
        
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if(null == userInfo){
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }
        
        List<UserFavorites> userFavoritesList =userFavoritesDao.getAllUserFavorites(userInfo.getId(), typeId, Integer.parseInt(requestPage), Integer.parseInt(pageSize));

        Result  xmlResult= new Result();
        xmlResult.setFlag(Constants.SUCCESS_FLAG);
        xmlResult.setUserFavorites(userFavoritesList);
        dealResult = StringUtil.generateXMLResultFromObject(xmlResult);
        return dealResult;
    }


}
