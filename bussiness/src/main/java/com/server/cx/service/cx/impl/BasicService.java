package com.server.cx.service.cx.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.server.cx.dao.cx.UserFavoritesDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.model.ActionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:41
 * FileName:BasicService
 */
@Component(value = "basicService")
public class BasicService {
    @Autowired
    @Qualifier("baseHostAddress")
    protected   String baseHostAddress;

    @Autowired
    @Qualifier("restURL")
    protected String restURL;

    @Autowired
    @Qualifier("imageShowURL")
    protected String imageShowURL;

    @Autowired
    @Qualifier("thumbnailSize")
    protected String thumbnailSize;

    @Autowired
    protected ActionBuilder actionBuilder;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserFavoritesDao userFavoritesDao;

    LoadingCache<String, List<String>> userCollectionsCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(5, TimeUnit.SECONDS).build(new CacheLoader<String, List<String>>() {
                @Override
                public List<String> load(String key) throws Exception {
                    if(key == null) return null;
                    UserInfo userInfo = userInfoDao.findByImsi(key);
                    List<String> graphicIdList = userFavoritesDao.getGraphicIdListByUserInfo(userInfo);
                    return graphicIdList;
                }
            });
}
