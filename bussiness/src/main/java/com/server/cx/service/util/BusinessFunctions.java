package com.server.cx.service.util;

import com.cl.cx.platform.dto.Actions;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.server.cx.entity.cx.*;
import com.server.cx.service.cx.impl.BasicService;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * User: yanjianzou Date: 12-8-2 Time: 上午11:30 FileName:BusinessFunctions
 */
@Component
public class BusinessFunctions extends BasicService {

    public Function<UserFavorites, DataItem> userFavoriteTransformToCollectedGraphicInfoItem(final String imsi) {
        return new Function<UserFavorites, DataItem>() {
            @Override
            public DataItem apply(@Nullable UserFavorites input) {
                GraphicInfo graphicInfo = input.getGraphicInfo();

                DataItem item = generateBasicDataItem(graphicInfo, imsi, null);

                item.setCollected(true);
                item.setFavoriteId(input.getId());
                item.setHref(baseHostAddress + restURL + imsi + "/myCollections/" + input.getId());
                if (!"none".equals(imsi)) {
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
                if (!"none".equals(imsi)) {
                    Actions actions = actionBuilder.buildCategoriesAction(imsi, input.getId());
                    categoryItem.setActions(actions);
                }
                return categoryItem;
            }
        };
    }

    public Function<GraphicInfo, DataItem> graphicInfoTransformToGraphicInfoItem(final String imsi,
                                                                                 final List<String> graphicIds,
                                                                                 final Map<String, ? extends MGraphic> usedGraphicInfos,
                                                                                 final ActionNames actionNames) {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                DataItem item = generateBasicDataItem(input, imsi, graphicIds);
                item.setHref(baseHostAddress + restURL + imsi + "/graphicInfos/" + input.getId());

                //TODO too mush duties need to refactor by Zou YanJian
                if (!"none".equals(imsi)) {
                    Actions actions = null;
                    if (ActionNames.MGRAPHIC_ACTION.equals(actionNames)) {
                        actions = actionBuilder.buildGraphicItemAction(imsi);
                        item.setActions(actions);
                        item.setInUsing(false);
                    }
                    if (ActionNames.HOLIDAY_MGRAPHIC_ACTION.equals(actionNames)) {
                        actions = actionBuilder.buildHolidayMGraphicItemAction(imsi);
                        item.setActions(actions);
                        item.setInUsing(false);
                    }

                    if (ActionNames.STATUS_MGRAPHIC_ACTION.equals(actionNames)) {
                        actions = actionBuilder.buildStatusMGraphicItemAction(imsi);
                        item.setActions(actions);
                    }

                    if (usedGraphicInfos != null) {
                        MGraphic mgraphic = usedGraphicInfos.get(input.getId());
                        if (mgraphic!= null ) {
                            String id = mgraphic.getId();
                            item.setMGraphicId(id);
                            item.setName(mgraphic.getName());
                            item.setSignature(mgraphic.getSignature());
                            item.setInUsing(true);
                            if (ActionNames.HOLIDAY_MGRAPHIC_ACTION.equals(actionNames)) {
                                actions = actionBuilder.buildHolidayMGraphicItemEditAction(imsi, id);
                                item.setActions(actions);
                            } else if (ActionNames.STATUS_MGRAPHIC_ACTION.equals(actionNames)) {
                                actions = actionBuilder.buildStatusMGraphicItemEditAction(imsi, id);
                                item.setActions(actions);
                            }

                        }
                    }
                }

                return item;
            }
        };
    }

    public Function<GraphicInfo, DataItem> graphicInfoTransformToGraphicInfoItemAndContainMGraphicInfo(final String imsi,
                                                                                                       final List<String> collectedGraphicInfos,
                                                                                                       final Map<String, String> usedGraphicInfos,
                                                                                                       final ActionNames actionNames) {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                DataItem item = generateBasicDataItem(input, imsi, collectedGraphicInfos);
                item.setHref(baseHostAddress + restURL + imsi + "/graphicInfos/" + input.getId());

                if (!"none".equals(imsi)) {
                    Actions actions = null;
                    if (ActionNames.MGRAPHIC_ACTION.equals(actionNames)) {
                        actions = actionBuilder.buildGraphicItemAction(imsi);
                        item.setActions(actions);
                    }
                    if (ActionNames.HOLIDAY_MGRAPHIC_ACTION.equals(actionNames)) {
                        actions = actionBuilder.buildHolidayMGraphicItemAction(imsi);
                        item.setActions(actions);
                    }
                }

                return item;
            }
        };
    }

    private DataItem generateBasicDataItem(GraphicInfo input, String imsi, List<String> graphicIds) {
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
            GraphicResource graphicResource = input.getGraphicResources().get(0);
            item.setThumbnailPath(imageShowURL + graphicResource.getResourceId() + "&" + thumbnailSize);
            item.setSourceImagePath(imageShowURL + graphicResource.getResourceId());
            item.setMediaType(graphicResource.getType());
        }

        HolidayType holidayType = input.getHolidayType();
        if(holidayType!= null){
            item.setHolidayType(holidayType.getId());
        }
        StatusType statusType = input.getStatusType();
        if(statusType != null){
            item.setHolidayType(statusType.getId());
        }
        item.setInUsing(false);

        if (graphicIds != null) {
            if (graphicIds.contains(input.getId())) {
                item.setCollected(true);
            } else {
                item.setCollected(false);
            }
        }
        return item;
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

    public Function<HolidayType, DataItem> holidayTypeTransformToDataItem(final String imsi,
                                                                          final List<HolidayType> holidayTypes,
                                                                          final Map<Long, UserHolidayMGraphic> userHolidayMGraphicMap) {
        return new Function<HolidayType, DataItem>() {
            @Override
            public DataItem apply(@Nullable HolidayType input) {
                DataItem dataItem = new DataItem();
                dataItem.setId(String.valueOf(input.getId()));
                dataItem.setName(input.getName());
                dataItem.setLevel(input.getLevel());
                dataItem.setGraphicURL(imageShowURL + input.getGraphicResourceId());
                dataItem.setDownloadNumber(String.valueOf(input.getDownloadNum().intValue()));
                Actions actions = null;
                if (!"none".equals(imsi)) {
                    actions = actionBuilder.buildHolidayTypeAction(imsi, input.getId());
                    dataItem.setActions(actions);
                }
                if (holidayTypes != null && holidayTypes.size() > 0) {
                    boolean hasUsed = holidayTypes.contains(input);
                    dataItem.setHasUsed(hasUsed);
                    if(hasUsed){
                        UserHolidayMGraphic userHolidayMGraphic = userHolidayMGraphicMap.get(input.getId());
                        actions = actionBuilder.buildHolidayTypeHasUsedActions(imsi, input.getId(), userHolidayMGraphic.getId());
                        dataItem.setActions(actions);
                    }
                } else {
                    dataItem.setHasUsed(false);
                }

                return dataItem;
            }
        };
    }

    public Function<StatusType, DataItem> statusTypeTransformToDataItem(final String imsi,
                                                                        final List<StatusType> statusTypes,
                                                                        final Map<Long, UserStatusMGraphic> userStatusMGraphicMap) {
        return new Function<StatusType, DataItem>() {
            @Override
            public DataItem apply(@Nullable StatusType input) {
                DataItem dataItem = new DataItem();
                dataItem.setId(String.valueOf(input.getId()));
                dataItem.setName(input.getName());
                dataItem.setGraphicURL(imageShowURL + input.getGraphicResourceId());
                Actions actions = null;
                if (!"none".equals(imsi)) {
                    actions = actionBuilder.buildStatusTypeAction(imsi, input.getId());
                    dataItem.setActions(actions);
                }
                if (statusTypes != null && statusTypes.size() > 0) {
                    boolean hasUsed = statusTypes.contains(input);
                    dataItem.setHasUsed(hasUsed);
                    if(hasUsed){
                        UserStatusMGraphic userStatusMGraphic = userStatusMGraphicMap.get(input.getId());
                        actions = actionBuilder.buildStatusTypeHasUsedActions(imsi,input.getId(),userStatusMGraphic.getId());
                        dataItem.setActions(actions);
                    }
                } else {
                    dataItem.setHasUsed(false);
                }

                return dataItem;
            }
        };
    }


    public Function<MGraphic, DataItem> mGraphicTransformToDataItem(final String imsi, final String conditions) {
        return new Function<MGraphic, DataItem>() {
            @Override
            public DataItem apply(@Nullable MGraphic input) {
                DataItem dataItem = generateBasicMGraphicDataItem(input);
                dataItem.setActions(actionBuilder.buildMGraphicActions(imsi, input.getId(), conditions));
                return dataItem;
            }
        };
    }

    private DataItem generateBasicMGraphicDataItem(MGraphic input) {
        DataItem dataItem = new DataItem();
        dataItem.setName(input.getName());
        dataItem.setSignature(input.getSignature());
        dataItem.setId(input.getGraphicInfo().getId());
        dataItem.setMGraphicId(input.getId());
        dataItem.setLevel(input.getGraphicInfo().getLevel());
        dataItem.setModeType(input.getModeType());
        if(input instanceof UserCommonMGraphic){
            dataItem.setPhoneNos(((UserCommonMGraphic) input).getPhoneNos());
        }

        if(input instanceof  UserCustomMGraphic){
            if(((UserCustomMGraphic) input).getBegin() !=null && ((UserCustomMGraphic) input).getBegin().equals(LocalDate.parse("1900-1-1"))){
                ((UserCustomMGraphic) input).setBegin(null);
                ((UserCustomMGraphic) input).setEnd(null);
            }
        }

        if(input instanceof UserCustomMGraphic){
            dataItem.setBegin(((UserCustomMGraphic) input).getBegin());
            dataItem.setEnd(((UserCustomMGraphic) input).getEnd());
        }

        GraphicInfo graphicInfo = input.getGraphicInfo();

        if (graphicInfo.getGraphicResources().size() > 0) {
            GraphicResource graphicResource = graphicInfo.getGraphicResources().get(0);
            dataItem.setThumbnailPath(imageShowURL + graphicResource.getResourceId()
                    + "&" + thumbnailSize);
            dataItem.setSourceImagePath(imageShowURL
                    + graphicResource.getResourceId());
            dataItem.setMediaType(graphicResource.getType());
        }
        dataItem.setInUsing(true);
        return dataItem;
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

                GraphicInfo graphicInfo = input.getGraphicInfo();
                if (graphicInfo.getGraphicResources().size() > 0) {
                    GraphicResource graphicResource = graphicInfo.getGraphicResources().get(0);
                    dataItem.setThumbnailPath(imageShowURL + graphicResource.getResourceId() + "&" + thumbnailSize);
                    dataItem.setSourceImagePath(imageShowURL + graphicResource.getResourceId());
                    dataItem.setMediaType(graphicResource.getType());
                }
                input.getGraphicInfo().getGraphicResources().get(0);
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
                if (input.getGraphicResources() != null && !input.getGraphicResources().isEmpty()) {
                    GraphicResource graphicResource = input.getGraphicResources().get(0);
                    if (graphicResource != null) {
                        dataItem.setGraphicURL(imageShowURL + graphicResource.getResourceId());
                    }
                }
                return dataItem;
            }
        };
    }

    public Function<GraphicInfo, DataItem> holidayGraphicInfoTransformToDataItem() {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setDownloadNumber(String.valueOf(input.getUseCount()));
                dataItem.setId(input.getId());
                dataItem.setLevel(input.getHolidayType().getLevel());
                if (input.getGraphicResources() != null && !input.getGraphicResources().isEmpty()) {
                    GraphicResource graphicResource = input.getGraphicResources().get(0);
                    if (graphicResource != null) {
                        dataItem.setGraphicURL(imageShowURL + graphicResource.getResourceId());
                        dataItem.setMediaType(graphicResource.getType());
                    }
                }
                return dataItem;
            }
        };
    }

    public Function<UserHolidayMGraphic, HolidayType> userHolidayMGraphicTransformToHolidayType() {
        return new Function<UserHolidayMGraphic, HolidayType>() {
            @Override
            public HolidayType apply(@Nullable UserHolidayMGraphic input) {
                return input.getHolidayType();
            }
        };
    }

    public Function<UserStatusMGraphic, StatusType> userStatusMGraphicTransformToStatusType() {
        return new Function<UserStatusMGraphic, StatusType>() {
            @Override
            public StatusType apply(@Nullable UserStatusMGraphic input) {
                return input.getStatusType();
            }
        };
    }
    
    public Function<FileMeta, GraphicInfo> fileMetaTransformToGraphicInfo() {
        return new Function<FileMeta, GraphicInfo>() {
            @Override
            public GraphicInfo apply(@Nullable FileMeta input) {
                System.out.println("input = " + input);
                GraphicResource graphicResource = new GraphicResource();
                graphicResource.setResourceId(input.getResourceId());
                
                GraphicInfo graphicInfo = new GraphicInfo();
                graphicInfo.setName(input.getName());
                List<GraphicResource> graphicResources = Lists.newArrayList();
                graphicResources.add(graphicResource);
                graphicInfo.setGraphicResources(graphicResources);
                return graphicInfo;
            }
        };
    }
}
