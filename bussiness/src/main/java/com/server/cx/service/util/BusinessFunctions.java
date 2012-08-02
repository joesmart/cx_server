package com.server.cx.service.util;

import com.cl.cx.platform.dto.Action;
import com.cl.cx.platform.dto.CategoryItem;
import com.cl.cx.platform.dto.CollectedGraphicInfoItem;
import com.cl.cx.platform.dto.GraphicInfoItem;
import com.google.common.base.Function;
import com.server.cx.dto.*;
import com.server.cx.entity.cx.*;
import com.server.cx.service.cx.impl.BasicService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 上午11:30
 * FileName:BusinessFunctions
 */
@Component
public class BusinessFunctions extends BasicService {

    public Function<MGraphicStoreMode, UserCXInfo> mGraphicStoreModeTransformToUserCXInfo() {
        return new Function<MGraphicStoreMode, UserCXInfo>() {
            @Override
            public UserCXInfo apply(MGraphicStoreMode intput) {
                return intput.convertMGraphicStoreModeToUserCXInfo();
            }

        };
    }

    public Function<UserFavorites, CollectedGraphicInfoItem> userFavoriteTransformToCollectedGraphicInfoItem(final String imsi) {
        return new Function<UserFavorites, CollectedGraphicInfoItem>() {
            @Override
            public CollectedGraphicInfoItem apply(@Nullable UserFavorites input) {
                CollectedGraphicInfoItem item = new CollectedGraphicInfoItem();
                GraphicInfo graphicInfo = input.getGraphicInfo();
                item.setFavoriteId(input.getId());
                item.setId(input.getId());
                item.setName(graphicInfo.getName());
                item.setSignature(graphicInfo.getSignature());
                item.setDownloadNumber(String.valueOf(graphicInfo.getUseCount()));
                item.setAuditPassed(true);
                item.setPrice(graphicInfo.getPrice());
                if (graphicInfo.getPrice() > 0.0F) {
                    item.setPurchased(false);
                }
                item.setCollected(true);
                item.setLevel(graphicInfo.getLevel());
                if (graphicInfo.getGraphicResources().size() > 0) {
                    item.setThumbnailPath(imageShowURL + graphicInfo.getGraphicResources().get(0).getResourceId() + "&" + thumbnailSize);
                    item.setSourceImagePath(imageShowURL + graphicInfo.getGraphicResources().get(0).getResourceId());
                }
                item.setHref(baseHostAddress + restURL + imsi + "/myCollections/" + input.getId());
                Action action = actionBuilder.buildGraphicItemAction(imsi);
                item.setAction(action);
                return item;
            }
        };
    }

    public Function<UserInfo, String> getUserPhoneNoFunction(){
        return new Function<UserInfo, String>() {
            @Override
            public String apply(UserInfo input) {
                return input.getPhoneNo();
            }
        };
    }

    public Function<Category, CategoryItem> categoryTransformToCategoryItem(final String imsi){
        return  new Function<Category, CategoryItem>() {
            @Override
            public CategoryItem apply(@javax.annotation.Nullable Category input) {
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.setName(input.getName());
                categoryItem.setDescription(input.getDescription());
                categoryItem.setDownloadNumber(String.valueOf(input.getDownloadNum()));
                categoryItem.setGraphicURL(imageShowURL+input.getGraphicResourceId());
                categoryItem.setHref(baseHostAddress + restURL + imsi + "/categories/" + input.getId());
                Action action = actionBuilder.buildCategoriesAction(imsi,input.getId());
                categoryItem.setAction(action);
                return categoryItem;
            }
        };
    }

    public Function<GraphicInfo, GraphicInfoItem> graphicInfoTransformToGraphicInfoItem(final String imsi){
        return new Function<GraphicInfo, GraphicInfoItem>() {
            @Override
            public GraphicInfoItem apply(@Nullable GraphicInfo input) {
                GraphicInfoItem graphicInfoItem = new GraphicInfoItem();
                graphicInfoItem.setId(input.getId());
                graphicInfoItem.setName(input.getName());
                graphicInfoItem.setSignature(input.getSignature());
                graphicInfoItem.setDownloadNumber(String.valueOf(input.getUseCount()));
                graphicInfoItem.setAuditPassed(true);
                graphicInfoItem.setPrice(input.getPrice());
                if (input.getPrice() > 0.0F) {
                    graphicInfoItem.setPurchased(false);
                }
                graphicInfoItem.setCollected(false);
                graphicInfoItem.setLevel(input.getLevel());
                if (input.getGraphicResources().size() > 0) {
                    graphicInfoItem.setThumbnailPath(imageShowURL + input.getGraphicResources().get(0).getResourceId() + "&" + thumbnailSize);
                    graphicInfoItem.setSourceImagePath(imageShowURL + input.getGraphicResources().get(0).getResourceId());
                }
                graphicInfoItem.setHref(baseHostAddress + restURL + imsi + "/graphicInfos/" + input.getId());
                Action action = actionBuilder.buildGraphicItemAction(imsi);
                graphicInfoItem.setAction(action);
                return graphicInfoItem;
            }
        };
    }

    public Function cxInfoTransformToUserCXInfo(final UserCXInfo userCXInfo, final String imsi){
        return  new Function<CXInfo, UserCXInfo>() {
            @Override
            public UserCXInfo apply(CXInfo cxInfo) {
                if (userCXInfo != null && userCXInfo.getCxInfo() != null && userCXInfo.getCxInfo().getId().equals(cxInfo.getId())) {
                    userCXInfo.setImsi(imsi);
                    return userCXInfo;
                }
                UserCXInfo userCXInfo = new UserCXInfo();
                userCXInfo.setType(3);
                userCXInfo.setModeType(5);
                userCXInfo.setImsi(imsi);
                userCXInfo.setCxInfo(cxInfo);
                userCXInfo.setStartHour(0);
                userCXInfo.setEndHour(24);
                userCXInfo.setAuditPass(true);
                return userCXInfo;
            }
        };
    }
}

