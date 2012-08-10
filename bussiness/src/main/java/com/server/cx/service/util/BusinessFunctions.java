package com.server.cx.service.util;

import com.cl.cx.platform.dto.Actions;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Function;
import com.server.cx.entity.cx.*;
import com.server.cx.model.CXInfo;
import com.server.cx.model.UserCXInfo;
import com.server.cx.service.cx.impl.BasicService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * User: yanjianzou Date: 12-8-2 Time: 上午11:30 FileName:BusinessFunctions
 */
@Component
public class BusinessFunctions extends BasicService {

    public Function<UserCommonMGraphic, UserCXInfo> mGraphicStoreModeTransformToUserCXInfo() {
        return new Function<UserCommonMGraphic, UserCXInfo>() {
            @Override
            public UserCXInfo apply(UserCommonMGraphic intput) {
                return intput.convertMGraphicStoreModeToUserCXInfo();
            }

        };
    }

    public Function<UserFavorites, DataItem> userFavoriteTransformToCollectedGraphicInfoItem(final String imsi) {
        return new Function<UserFavorites, DataItem>() {
            @Override
            public DataItem apply(@Nullable UserFavorites input) {
                DataItem item = new DataItem();
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
                    item.setThumbnailPath(imageShowURL + graphicInfo.getGraphicResources().get(0).getResourceId() + "&"
                            + thumbnailSize);
                    item.setSourceImagePath(imageShowURL + graphicInfo.getGraphicResources().get(0).getResourceId());
                }
                item.setHref(baseHostAddress + restURL + imsi + "/myCollections/" + input.getId());
                if(!"none".equals(imsi)){
                    Actions actions = actionBuilder.buildUserFavoriteItemAction(imsi, input.getId());
                    item.setActions(actions);
                }

                return item;
            }
        };
    }

    public Function<UserInfo, String> getUserPhoneNoFunction() {
        return new Function<UserInfo, String>() {
            @Override
            public String apply(UserInfo input) {
                return input.getPhoneNo();
            }
        };
    }

    public Function<Category, DataItem> categoryTransformToCategoryItem(final String imsi) {
        return new Function<Category, DataItem>() {
            @Override
            public DataItem apply(@javax.annotation.Nullable Category input) {
                DataItem categoryItem = new DataItem();
                categoryItem.setName(input.getName());
                categoryItem.setDescription(input.getDescription());
                categoryItem.setDownloadNumber(String.valueOf(input.getDownloadNum()));
                categoryItem.setGraphicURL(imageShowURL + input.getGraphicResourceId());
                categoryItem.setHref(baseHostAddress + restURL + imsi + "/categories/" + input.getId());
                if(!"none".equals(imsi)){
                    Actions actions = actionBuilder.buildCategoriesAction(imsi, input.getId());
                    categoryItem.setActions(actions);
                }
                return categoryItem;
            }
        };
    }

    public Function<GraphicInfo, DataItem> graphicInfoTransformToGraphicInfoItem(final String imsi, final List<String> graphicIds) {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                DataItem item = new DataItem();
                item.setId(input.getId());
                item.setName(input.getName());
                item.setSignature(input.getSignature());
                item.setDownloadNumber(String.valueOf(input.getUseCount()));
                item.setAuditPassed(true);
                item.setPrice(input.getPrice());
                if (input.getPrice() > 0.0F) {
                    item.setPurchased(false);
                }
                item.setLevel(input.getLevel());
                if (input.getGraphicResources().size() > 0) {
                    item.setThumbnailPath(imageShowURL + input.getGraphicResources().get(0).getResourceId()
                            + "&" + thumbnailSize);
                    item.setSourceImagePath(imageShowURL
                            + input.getGraphicResources().get(0).getResourceId());
                }
                item.setHref(baseHostAddress + restURL + imsi + "/graphicInfos/" + input.getId());
                if (!"none".equals(imsi)) {
                    Actions actions = actionBuilder.buildGraphicItemAction(imsi);
                    item.setActions(actions);
                }
                if(graphicIds != null){
                    if(graphicIds.contains(input.getId())){
                        item.setCollected(true);
                    }else{
                        item.setCollected(false);
                    }
                }
                return item;
            }
        };
    }

    public Function cxInfoTransformToUserCXInfo(final UserCXInfo userCXInfo, final String imsi) {
        return new Function<CXInfo, UserCXInfo>() {
            @Override
            public UserCXInfo apply(CXInfo cxInfo) {
                if (userCXInfo != null && userCXInfo.getCxInfo() != null
                        && userCXInfo.getCxInfo().getId().equals(cxInfo.getId())) {
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

    public Function<Contacts, ContactInfoDTO> contactsTransformToContactInfoDTO() {
        return new Function<Contacts, ContactInfoDTO>() {
            @Override
            public ContactInfoDTO apply(@javax.annotation.Nullable Contacts input) {
                ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
                contactInfoDTO.setContactName(input.getName());
                contactInfoDTO.setPhoneNo(input.getPhoneNo());
                return contactInfoDTO;
            }
        };
    }

    public Function<HolidayType, DataItem> holidayTypeTransformToDataItem() {
        return new Function<HolidayType, DataItem>() {
            @Override
            public DataItem apply(@Nullable HolidayType input) {
                DataItem dataItem = new DataItem();
                dataItem.setId(String.valueOf(input.getId()));
                dataItem.setName(input.getName());
                dataItem.setLevel(input.getLevel());
                dataItem.setGraphicURL(imageShowURL + input.getGraphicResourceId());
                dataItem.setDownloadNumber(String.valueOf(input.getDownloadNum().intValue()));
                //TODO 这边接口未完成，需要根据imsi查出具体用户是否使用该节日包, 暂时全部返回false
                dataItem.setHasUsed(false);
                return dataItem;
            }
        };
    }

    public Function<StatusType, DataItem> statusTypeTransformToDataItem() {
        return new Function<StatusType, DataItem>() {
        	@Override
        	public DataItem apply(@Nullable StatusType input) {
              DataItem dataItem = new DataItem();
              dataItem.setId(String.valueOf(input.getId()));
              dataItem.setName(input.getName());
              dataItem.setGraphicURL(imageShowURL + input.getGraphicResourceId());
              //TODO 这边接口未完成，需要根据imsi查出具体用户是否使用该状态包, 暂时全部返回false
              dataItem.setHasUsed(false);
              return dataItem;
        	}
        };
    }


    public Function<MGraphic, DataItem> mGraphicTransformToDataItem(final String imsi) {
        return new Function<MGraphic, DataItem>() {
            @Override
            public DataItem apply(@Nullable MGraphic input) {
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setSignature(input.getSignature());
                dataItem.setId(input.getGraphicInfo().getId());
                dataItem.setMGraphicId(input.getId());
                dataItem.setLevel(input.getGraphicInfo().getLevel());
                dataItem.setModeType(input.getModeType());
                if (2 == input.getModeType()) {
                    dataItem.setPhoneNos(((UserSpecialMGraphic) input).getPhoneNos());
                }
                GraphicInfo graphicInfo = input.getGraphicInfo();
                if (graphicInfo.getGraphicResources().size() > 0) {
                    dataItem.setThumbnailPath(imageShowURL + graphicInfo.getGraphicResources().get(0).getResourceId() + "&"
                            + thumbnailSize);
                    dataItem.setSourceImagePath(imageShowURL + graphicInfo.getGraphicResources().get(0).getResourceId());
                }
                dataItem.setInUsing(true);
                dataItem.setActions(actionBuilder.buildMGraphicActions(imsi, input.getId()));
                return dataItem;
            }
        };
    }

    public Function<HistoryMGraphic, DataItem> historyMGraphicTransformToDataItem(final String imsi) {
        return new Function<HistoryMGraphic, DataItem>() {
            @Override
            public DataItem apply(@Nullable HistoryMGraphic input) {
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setSignature(input.getSignature());
                dataItem.setId(input.getGraphicInfo().getId());
                dataItem.setMGraphicId(input.getId());
                dataItem.setLevel(input.getGraphicInfo().getLevel());
                dataItem.setModeType(input.getModeType());
                dataItem.setInUsing(false);
                dataItem.setActions(actionBuilder.buildHistoryMGraphicActions(imsi, input.getId()));
                return dataItem;
            }
        };
    }

    public Function<GraphicInfo, DataItem> statusGraphicInfoTransformToDataItem() {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setDownloadNumber(String.valueOf(input.getUseCount()));
                dataItem.setId(input.getId());
                if(input.getGraphicResources() != null && !input.getGraphicResources().isEmpty()) {
                    GraphicResource graphicResource = input.getGraphicResources().get(0);
                    if(graphicResource != null) {
                        dataItem.setGraphicURL(imageShowURL + graphicResource.getResourceId());
                    }
                }
                return dataItem;
            }
        };
    }

}
