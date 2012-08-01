package com.server.cx.service.cx.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserFavoritesDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dto.CXInfo;
import com.server.cx.entity.cx.UserFavorites;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserFavoritesService;
import com.server.cx.util.RestSender;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.UserFavoritesUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("userFavoritesService")
@Transactional
public class UserFavoritesServiceImpl implements UserFavoritesService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    UserFavoritesDao userFavoritesDao;
    @Autowired
    @Qualifier("cxinfosQueryIdRestSender")
    private RestSender restSender;

    private String dealResult = "";
    private List<UserFavorites> userFavoritesList;
    private List<String> resourceIdsList;

    @Override
    public String addNewUserFavorites(String imsi, String cxInfoId) throws SystemException {

        // TODO use the throws exceptions to resolve this kind issue;
        // defense
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(cxInfoId);
        // TODO cxInfoId 的数据输入验证需要修改
        /*
        * if (!ValidationUtil.isDigit(cxInfoId)){ dealResult =
        * StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据输入有误"); return dealResult; }
        */
        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }

        String userId = userInfo.getId();
        boolean isAlreandAddedInUserFavorites = userFavoritesDao.isAlreadAddedInUserFavorites(userId, cxInfoId);
        if (isAlreandAddedInUserFavorites) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户已经收藏该彩像");
            return dealResult;
        }

        Integer totalCountOfUserFavorites = userFavoritesDao.getUserFavoritesTotalCount(userId);
        if (totalCountOfUserFavorites >= Constants.TOTAL_USERFAVORITES_COUNT) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户收藏已经超出限制的"
                    + Constants.TOTAL_USERFAVORITES_COUNT + "条!");
            return dealResult;
        }

        UserFavorites userFavorites = new UserFavorites();
        userFavorites.setUser(userInfo);
        userFavoritesDao.save(userFavorites);

        Result xmlResult = new Result();
        List<UserFavorites> userFavoritesList = Lists.newArrayList();
        userFavoritesList.add(userFavorites);
        xmlResult.setFlag(Constants.SUCCESS_FLAG);
        xmlResult.setContent("彩像收藏成功");
        xmlResult.setUserFavorites(userFavoritesList);
        dealResult = StringUtil.generateXMLResultFromObject(xmlResult);
        return dealResult;
    }

    @Override
    public String deleteUserFavorites(String imsi, String userFavoritesId) {

        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(userFavoritesId);
        if (!ValidationUtil.isMultiUserFavoritesId(userFavoritesId)) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据输入有误");
            return dealResult;
        }

        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }

        String splitString = ",";

        List<Long> userFavoritesIdLongList = UserFavoritesUtil.convertDigitStrignIntoLongList(userFavoritesId, splitString);
        //TODO Send multi delete request need to refactor to resolve this kind issue By Joe
        for (Long id : userFavoritesIdLongList) {
            userFavoritesDao.delete(id);
        }
        dealResult = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "用户移除收藏成功");
        return dealResult;
    }

    @Override
    public String getAllUserFavorites(String imsi, String typeId, String requestPage, String pageSize) {
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(typeId);
        Preconditions.checkNotNull(requestPage);
        Preconditions.checkNotNull(pageSize);

        if (!ValidationUtil.isDigit(typeId) || !ValidationUtil.isDigit(requestPage) || !ValidationUtil.isDigit(requestPage)) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "数据输入有误");
            return dealResult;
        }

        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            return dealResult;
        }
//TODO need fix here since the UserInfo Id change to String typ By Joesmart
        userFavoritesList =
                userFavoritesDao.getAllUserFavorites(userInfo.getId(), Integer.parseInt(requestPage),
                        Integer.parseInt(pageSize));

        getResourceIdList();
        makeupUserFavoritesList(resourceIdsList);

        Result xmlResult = new Result();
        xmlResult.setFlag(Constants.SUCCESS_FLAG);
        xmlResult.setUserFavorites(userFavoritesList);
        dealResult = StringUtil.generateXMLResultFromObject(xmlResult);
        return dealResult;
    }

    public void getResourceIdList() {
        resourceIdsList = Lists.transform(userFavoritesList, new Function<UserFavorites, String>() {
            @Override
            public String apply(UserFavorites userFavorites) {
                return "";
            }
        });
    }

    public void makeupUserFavoritesList(List<String> resourceIdsList) {
        if (resourceIdsList != null && resourceIdsList.size() > 0) {
            //TODO 需要把URL地址移动到公共位置.
            Map<String, CXInfo> cxInfosMap = restSender.getCXInfoMap(resourceIdsList);

            if (cxInfosMap != null) {

            }
        }
    }
}
