package com.server.cx.service.cx.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.server.cx.dao.cx.UserFavoritesDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: yanjianzou Date: 12-7-30 Time: 下午5:41 FileName:BasicService
 */
@Component(value = "basicService")
@Data
public class BasicService {
    @Autowired
    @Qualifier("baseHostAddress")
    private String baseHostAddress;

    @Autowired
    @Qualifier("restURL")
    private String restURL;

    @Autowired
    @Qualifier("imageShowURL")
    private String imageShowURL;

    @Autowired
    @Qualifier("thumbnailSize")
    private String thumbnailSize;

    //TODO User Favorites Cache should not put here by Zou YanJian
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserFavoritesDao userFavoritesDao;

    public BasicService() {

    }

    public BasicService(String baseHostAddress, String restURL, String imageShowURL, String thumbnailSize) {
        this.baseHostAddress = baseHostAddress;
        this.restURL = restURL;
        this.imageShowURL = imageShowURL;
        this.thumbnailSize = thumbnailSize;
    }

    @Getter
    LoadingCache<String, List<String>> userCollectionsCache = CacheBuilder.newBuilder().maximumSize(100)
        .expireAfterAccess(5, TimeUnit.SECONDS).build(new CacheLoader<String, List<String>>() {
            @Override
            public List<String> load(String key) throws Exception {
                if (key == null)
                    return null;
                UserInfo userInfo = userInfoDao.findByImsi(key);
                return userFavoritesDao.getGraphicIdListByUserInfo(userInfo);
            }
        });

    public String getBaseURL() {
        return baseHostAddress + restURL;
    }

    public String generateURL(String imsi, String condition) {
        return getBaseURL() + imsi + condition;
    }

    public String generateSuggestionSubmitURL(String imsi) {
        return generateURL(imsi, "/suggestion");
    }

    public String generateUserDIYMGraphicUploadURL(String imsi) {
        return generateURL(imsi, "/diyGraphics/upload");
    }

    public String generateContactsUploadURL(String imsi) {
        return generateURL(imsi, "/contacts");
    }

    public String generateMobileRegisterURL() {
        return getBaseURL() + "register";
    }

    public String generateMobileRegisterUpdateURL(String imsi) {
        return getBaseURL() + "register/" + imsi;
    }

    public String generateVersionVisitURL() {
        return getBaseURL() + "upgrade";
    }

    public String generateInviteFriendsURL(String imsi) {
        return generateURL(imsi, "/sms");
    }

    public String generateCallingURL(String imsi) {
        return generateURL(imsi, "/callings");
    }

    public String generateCustomMGraphicsVisitURL(String imsi) {
        return generateURL(imsi, "/customMGraphics");
    }

    public String generateCustomMGraphicsOperateURL(String imsi, String mgraphicId) {
        return generateURL(imsi, "/customMGraphics/" + mgraphicId);
    }

    public String generateCategoriesVisitURL(String imsi) {
        return generateURL(imsi, "/categories");
    }

    public String generateCategoriesOperateURL(String imsi, Long categoryId) {
        return generateURL(imsi, "/categories/" + categoryId);
    }

    public String generateHotGraphicInfosVisitURL(String imsi) {
        return generateURL(imsi, "/graphicInfos?hot=true");
    }

    public String generateGraphicInfosOperateURL(String imsi, String id) {
        return generateURL(imsi, "/graphicInfos/" + id);
    }

    public String generateRecommendGraphicInfoVisitURL(String imsi) {
        return generateURL(imsi, "/graphicInfos?recommend=true");
    }

    public String generateHolidayTypeGraphicInfosVisitURL(String imsi, Long holidayTypeId) {
        return generateURL(imsi, "/graphicInfos?holidayTypeId=" + holidayTypeId);
    }

    public String generateGraphicInfosZoneInURL(String imsi, Long categoryId) {
        return generateURL(imsi, "/graphicInfos?categoryId=" + categoryId);
    }

    public String generateHolidayTypesVisitURL(String imsi) {
        return generateURL(imsi, "/holidayTypes");
    }

    public String generateHistoryMGraphicRemoveURL(String imsi, String id) {
        return getBaseURL() + imsi + "/historyMGraphics/" + id;
    }

    public String generateMGraphicsURL(String imsi) {
        return generateURL(imsi, "/mGraphics");
    }

    public String generateDiyMGraphicUseURL(String imsi) {
        return generateURL(imsi, "/diyGraphics");
    }

    public String generateHolidayMGraphicRemoveURL(String imsi, String mgraphicId) {
        return getBaseURL() + imsi + "/holidayMGraphics/" + mgraphicId;
    }

    public String generateImmediateUseHolidayMGraphicURL(String imsi) {
        return generateURL(imsi, "/holidayMGraphics?immediate=true");
    }

    public String generateImmediateUseStatusMGraphicURL(String imsi) {
        return generateURL(imsi, "/statusMGraphics?immediate=true");
    }

    public String generateStatusTypeVisitURL(String imsi) {
        return generateURL(imsi, "/statusTypes");
    }

    public String generateStatusTypeGraphicInfosVisitURL(String imsi, Long statusTypeId) {
        return getBaseURL() + imsi + "/graphicInfos?statusTypeId=" + statusTypeId;
    }

    public String generateHolidayMGraphicUseURL(String imsi) {
        return generateURL(imsi, "/holidayMGraphics");
    }

    public String generateStatusMGraphicUseURL(String imsi) {
        return generateURL(imsi, "/statusMGraphics");
    }

    public String generateImagePurchaseURL(String imsi) {
        return generateURL(imsi, "/myPurchasedImages");
    }

    public String generateMyCollectionsVisitURL(String imsi) {
        return generateURL(imsi, "/myCollections");
    }

    public String generateMyCollectionsOperateURL(String imsi, String userFavoriteId) {
        return generateURL(imsi, "/myCollections/" + userFavoriteId);
    }

    public String generateStatusMGraphicRemoveURL(String imsi, String mGraphicId) {
        return generateURL(imsi, "/statusMGraphics/" + mGraphicId);
    }

    public String generateSubscribeURL(String imsi) {
        return generateURL(imsi, "/holidayTypes");
    }

    public String imageURL(String resourceId) {
        return this.imageShowURL + resourceId;
    }

    public String thumbnailImageURL(String resourceId) {
        return this.imageShowURL + resourceId + "&" + this.thumbnailSize;
    }

    public String generateHolidaySubscribeGraphicItemURL(String imsi, Boolean isImmediate) {
        return generateURL(imsi, "/holidayMGraphics?subscribe=true&&immediate=" + isImmediate);
    }

    public String generateStatusSubscribeGraphicItemURL(String imsi, Boolean isImmediate) {
        return generateURL(imsi, "/statusMGraphics?subscribe=true&&immediate=" + isImmediate);
    }

    public String generateCustomSubscribeGraphicItemURL(String imsi) {
        return generateURL(imsi, "/customMGraphics?subscribe=true");
    }

    public String generateSubscribeGraphicItem(String imsi) {
        return generateURL(imsi, "/mGraphics?subscribe=true");
    }

    public String generateCancelSubscribeHolidayURL(String imsi) {
        return generateURL(imsi, "/subscribe?type=holiday");
    }

    public String generateCancelSubscribeStatusURL(String imsi) {
        return generateURL(imsi, "/subscribe?type=status");
    }

    public String generateCancelSubscribeCustomURL(String imsi) {
        return generateURL(imsi, "/subscribe?type=custom");
    }

    public String generateUserDIYGraphicDeleteURL(String imsi, String id) {
        return generateURL(imsi, "/diyGraphics/" + id);
    }

    public String generateSubscribeFunctionType(String imsi, String type) {
        return generateURL(imsi, "/subscribe?type=" + type);
    }

    public String generateCXCoinAccountURL(String imsi) {
        return generateURL(imsi, "/cxCoin/account");
    }

    public String generateCXCoinConsumeURL(String imsi) {
        return generateURL(imsi, "/cxCoin/consume");
    }

    public String generateCXCoinRecordsURL(String imsi) {
        return generateURL(imsi, "/cxCoin/records");
    }

    public String generateCXCoinRegisterURL(String imsi) {
        return generateURL(imsi, "/cxCoin/register");
    }

    public String generateCXCoinPurchaseURL() {
        return getBaseURL() + "cxCoin/purchase";
    }

    public String generateCXCoinConfirmPurchaseURL(String imsi) {
        return generateURL(imsi, "/cxCoin/confirmPurchase");
    }
}
