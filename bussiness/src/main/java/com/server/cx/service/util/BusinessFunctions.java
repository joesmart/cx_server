package com.server.cx.service.util;

import com.cl.cx.platform.dto.Actions;
import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Function;
import com.server.cx.entity.cx.*;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.impl.BasicService;
import com.server.cx.util.DateUtil;
import com.server.cx.util.business.AuditStatus;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * User: yanjianzou Date: 12-8-2 Time: 上午11:30 FileName:BusinessFunctions
 */
@Component
public class BusinessFunctions {

    @Autowired
    private BasicService basicService;
    @Autowired
    private ActionBuilder actionBuilder;

    public Function<UserFavorites, DataItem> userFavoriteTransformToCollectedGraphicInfoItem(final String imsi) {
        return new Function<UserFavorites, DataItem>() {
            @Override
            public DataItem apply(@Nullable UserFavorites input) {
                if (input == null)
                    return null;
                GraphicInfo graphicInfo = input.getGraphicInfo();

                DataItem item = generateBasicDataItem(graphicInfo, imsi, null);

                item.setCollected(true);
                item.setFavoriteId(input.getId());

                item.setHref(basicService.generateMyCollectionsOperateURL(imsi, input.getId()));
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
                if (input == null)
                    return null;
                DataItem categoryItem = new DataItem();
                categoryItem.setName(input.getName());
                categoryItem.setDescription(input.getDescription());
                categoryItem.setDownloadNumber(String.valueOf(input.getDownloadNum()));
                categoryItem.setGraphicURL(basicService.imageURL(input.getGraphicResourceId()));
                categoryItem.setHref(basicService.generateCategoriesOperateURL(imsi, input.getId()));
                Actions actions = actionBuilder.buildCategoriesAction(imsi, input.getId());
                categoryItem.setActions(actions);
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
                if (input == null)
                    return null;
                DataItem item = generateBasicDataItem(input, imsi, graphicIds);
                item.setHref(basicService.generateGraphicInfosOperateURL(imsi, input.getId()));

                //TODO too mush duties need to refactor by Zou YanJian
                if (!"none".equals(imsi)) {
                    Actions actions;
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
                        if (mgraphic != null) {
                            String id = mgraphic.getId();
                            item.setMGraphicId(id);
                            item.setName(mgraphic.getName());
                            item.setSignature(mgraphic.getSignature());
                            item.setInUsing(true);
                            if (ActionNames.HOLIDAY_MGRAPHIC_ACTION.equals(actionNames)) {
                                if(mgraphic instanceof  UserHolidayMGraphic){
                                    item.setPhoneNos(((UserHolidayMGraphic) mgraphic).getPhoneNos());
                                }
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
                if (input == null)
                    return null;
                DataItem item = generateBasicDataItem(input, imsi, collectedGraphicInfos);
                item.setHref(basicService.generateGraphicInfosOperateURL(imsi, input.getId()));

                if (!"none".equals(imsi)) {
                    Actions actions;
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
        item.setAuditStatus(AuditStatus.PASSED.toString());
        item.setPrice((int)(input.getPrice().doubleValue()));
        if (input.getPrice() > 0.0F) {
            item.setPurchased(false);
        }

        item.setLevel(input.getLevel());
        setUpSourceAndThumbnailImagePath(item, input);

        HolidayType holidayType = input.getHolidayType();
        if (holidayType != null) {
            item.setHolidayType(holidayType.getId());
        }
        StatusType statusType = input.getStatusType();
        if (statusType != null) {
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
                if (input == null)
                    return null;
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
                if (input == null)
                    return null;
                DataItem dataItem = new DataItem();
                dataItem.setId(String.valueOf(input.getId()));
                dataItem.setName(input.getName());
                dataItem.setLevel(input.getLevel());
                dataItem.setGraphicURL(basicService.imageURL(input.getGraphicResourceId()));
                dataItem.setDownloadNumber(String.valueOf(input.getDownloadNum().intValue()));
                Actions actions;
                if (!"none".equals(imsi)) {
                    actions = actionBuilder.buildHolidayTypeAction(imsi, input.getId());
                    dataItem.setActions(actions);
                }
                if (holidayTypes != null && holidayTypes.size() > 0) {
                    boolean hasUsed = holidayTypes.contains(input);
                    dataItem.setHasUsed(hasUsed);
                    if (hasUsed) {
                        UserHolidayMGraphic userHolidayMGraphic = userHolidayMGraphicMap.get(input.getId());
                        actions = actionBuilder.buildHolidayTypeHasUsedActions(imsi, input.getId(),
                            userHolidayMGraphic.getId());
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
                if (input == null)
                    return null;
                DataItem dataItem = new DataItem();
                dataItem.setId(String.valueOf(input.getId()));
                dataItem.setName(input.getName());
                dataItem.setGraphicURL(basicService.imageURL(input.getGraphicResourceId()));
                Actions actions;
                if (!"none".equals(imsi)) {
                    actions = actionBuilder.buildStatusTypeAction(imsi, input.getId());
                    dataItem.setActions(actions);
                }
                if (statusTypes != null && statusTypes.size() > 0) {
                    boolean hasUsed = statusTypes.contains(input);
                    dataItem.setHasUsed(hasUsed);
                    if (hasUsed) {
                        UserStatusMGraphic userStatusMGraphic = userStatusMGraphicMap.get(input.getId());
                        actions = actionBuilder.buildStatusTypeHasUsedActions(imsi, input.getId(),
                            userStatusMGraphic.getId());
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
                if (input == null)
                    return null;
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
        if (input.getGraphicResource() != null) {
            dataItem.setId(input.getGraphicResource().getId());
        }
        dataItem.setMGraphicId(input.getId());
        dataItem.setSubScribe(input.getSubscribe());
        dataItem.setModeType(input.getModeType());
        if (input instanceof UserCommonMGraphic) {
            dataItem.setPhoneNos(((UserCommonMGraphic) input).getPhoneNos());
        }

        if (input instanceof UserCustomMGraphic) {
            if (((UserCustomMGraphic) input).getBegin() != null
                && ((UserCustomMGraphic) input).getBegin().getTime() == (LocalDate.parse("1900-1-1").toDate().getTime())) {
                ((UserCustomMGraphic) input).setBegin(null);
                ((UserCustomMGraphic) input).setEnd(null);
            } else {
                dataItem.setBegin(((UserCustomMGraphic) input).getBegin());
                dataItem.setEnd(((UserCustomMGraphic) input).getEnd());
            }
        }
        setUpSourceAndThumbnailImagePathFromGraphicResource(dataItem, input.getGraphicResource());
        dataItem.setInUsing(true);
        return dataItem;
    }

    private void setUpSourceAndThumbnailImagePath(DataItem dataItem, GraphicInfo graphicInfo) {
        List<GraphicResource> graphicResources = graphicInfo.getGraphicResources();
        if (graphicResources.size() > 0) {
            GraphicResource graphicResource = graphicResources.get(0);
            setUpSourceAndThumbnailImagePathFromGraphicResource(dataItem, graphicResource);
        }
    }

    private void setUpSourceAndThumbnailImagePathFromGraphicResource(DataItem dataItem, GraphicResource graphicResource) {
        if (graphicResource == null)
            return;
        dataItem.setThumbnailPath(basicService.thumbnailImageURL(graphicResource.getResourceId()));
        dataItem.setSourceImagePath(basicService.imageURL(graphicResource.getResourceId()));
        dataItem.setMediaType(graphicResource.getType());
        dataItem.setResourceType(graphicResource.getType());
    }

    public Function<HistoryMGraphic, DataItem> historyMGraphicTransformToDataItem(final String imsi) {
        return new Function<HistoryMGraphic, DataItem>() {
            @Override
            public DataItem apply(@Nullable HistoryMGraphic input) {
                if (input == null)
                    return null;
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setSignature(input.getSignature());
                dataItem.setId(input.getGraphicInfo().getId());
                dataItem.setMGraphicId(input.getId());
                dataItem.setLevel(input.getGraphicInfo().getLevel());
                dataItem.setModeType(input.getModeType());
                dataItem.setInUsing(false);
                dataItem.setPhoneNos(input.getPhoneNos());

                GraphicInfo graphicInfo = input.getGraphicInfo();
                setUpSourceAndThumbnailImagePath(dataItem, graphicInfo);
                dataItem.setActions(actionBuilder.buildHistoryMGraphicActions(imsi, input.getId()));
                return dataItem;
            }
        };
    }

    public Function<GraphicInfo, DataItem> statusGraphicInfoTransformToDataItem() {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                if (input == null)
                    return null;
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setDownloadNumber(String.valueOf(input.getUseCount()));
                dataItem.setId(input.getId());
                setUpGraphicURL(dataItem, input);
                return dataItem;
            }
        };
    }

    public Function<GraphicInfo, DataItem> holidayGraphicInfoTransformToDataItem() {
        return new Function<GraphicInfo, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicInfo input) {
                if (input == null)
                    return null;
                DataItem dataItem = new DataItem();
                dataItem.setName(input.getName());
                dataItem.setDownloadNumber(String.valueOf(input.getUseCount()));
                dataItem.setId(input.getId());
                dataItem.setLevel(input.getHolidayType().getLevel());
                setUpGraphicURL(dataItem, input);
                return dataItem;
            }
        };
    }

    private void setUpGraphicURL(DataItem dataItem, GraphicInfo input) {
        if (input.getGraphicResources() != null && !input.getGraphicResources().isEmpty()) {
            GraphicResource graphicResource = input.getGraphicResources().get(0);
            if (graphicResource != null) {
                dataItem.setGraphicURL(basicService.imageURL(graphicResource.getResourceId()));
                dataItem.setMediaType(graphicResource.getType());
                dataItem.setResourceType(graphicResource.getType());
            }
        }
    }

    public Function<UserHolidayMGraphic, HolidayType> userHolidayMGraphicTransformToHolidayType() {
        return new Function<UserHolidayMGraphic, HolidayType>() {
            @Override
            public HolidayType apply(@Nullable UserHolidayMGraphic input) {
                if(input == null)
                    return null;
                return input.getHolidayType();
            }
        };
    }

    public Function<UserStatusMGraphic, StatusType> userStatusMGraphicTransformToStatusType() {
        return new Function<UserStatusMGraphic, StatusType>() {
            @Override
            public StatusType apply(@Nullable UserStatusMGraphic input) {
                if (input == null)
                    return null;
                return input.getStatusType();
            }
        };
    }

    public Function<FileMeta, GraphicResource> fileMetaTransformToGraphicInfo(final UserDiyGraphic userDiyGraphic) {
        return new Function<FileMeta, GraphicResource>() {
            @Override
            public GraphicResource apply(@Nullable FileMeta input) {
                if (input == null)
                    return null;
                GraphicResource graphicResource = new GraphicResource();
                graphicResource.setResourceId(input.getResourceId());
                if (CheckStatusDesc.CHECKING.equals(input.getCheckStatusDesc())) {
                    graphicResource.setAuditStatus(AuditStatus.CHECKING);
                }
                //TODO need dynamic determine the File extend file type. by Zou YanJian.
                graphicResource.setType("jpg");
                graphicResource.setDiyGraphic(userDiyGraphic);
                return graphicResource;
            }
        };
    }

    public Function<SubscribeType, UserSubscribeType> holidaySubscribeTypeTransformToUserSubscribeType(final UserInfo userInfo) {
        return new Function<SubscribeType, UserSubscribeType>() {
            @Override
            public UserSubscribeType apply(@Nullable SubscribeType input) {
                UserSubscribeType userSubscribeType = new UserSubscribeType();
                userSubscribeType.setUserInfo(userInfo);
                userSubscribeType.setSubscribeType(input);
                return userSubscribeType;
            }
        };
    }

    public Function<GraphicResource, DataItem> transformDiyGraphicToDataItem(final String imsi,
                                                                             final UserDiyGraphic userDiyGraphic) {

        return new Function<GraphicResource, DataItem>() {
            @Override
            public DataItem apply(@Nullable GraphicResource input) {
                if(input == null){
                    return  null;
                }
                DataItem dataItem = new DataItem();
                dataItem.setName(userDiyGraphic.getName());
                dataItem.setSignature(userDiyGraphic.getSignature());
                dataItem.setId(input.getId());
                dataItem.setResourceType(input.getType());
                dataItem.setAuditStatus(input.getAuditStatus().toString());

                dataItem.setMediaType(input.getType());
                setUpSourceAndThumbnailImagePathFromGraphicResource(dataItem, input);
                if(AuditStatus.PASSED.equals(input.getAuditStatus())){
                    dataItem.setActions(actionBuilder.buildUserDIYGraphicActions(imsi, input.getId()));
                }
                if(AuditStatus.UNPASS.equals(input.getAuditStatus())){
                    dataItem.setActions(actionBuilder.buildUserDIYGraphicRemoveActions(imsi, input.getId()));
                }
                if(AuditStatus.CHECKING.equals(input.getAuditStatus())){
                    dataItem.setActions(null);
                }
                return dataItem;
            }
        };
    }

    public Function<CXCoinAccount, CXCoinAccountDTO> cxCoinAccountToCXCoinAccountDTO() {
        return new Function<CXCoinAccount, CXCoinAccountDTO>() {
            @Override
            public CXCoinAccountDTO apply(@Nullable CXCoinAccount input) {
                CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
                cxCoinAccountDTO.setName(input.getName());
                cxCoinAccountDTO.setCoin(input.getCoin());
                return cxCoinAccountDTO;
            }
        };
    }

    public Function<? super UserSubscribeRecord, ? extends DataItem> subscribeRecordTransferormToDataItem() {
        return new Function<UserSubscribeRecord, DataItem>() {
            @Override
            public DataItem apply(@Nullable UserSubscribeRecord input) {
                if (input.getIncome() != null || input.getExpenses() != null) {
                    DataItem dataItem = new DataItem();
                    dataItem.setTime(DateUtil.formateDate(input.getCreatedOn()));
                    if (input.getIncome() != null) {
                        dataItem.setCxCoin(String.valueOf(input.getIncome()));
                    } else {
                        dataItem.setCxCoin(String.valueOf(input.getExpenses() * -1));
                    }
                    return dataItem;
                }
                return null;
            }
        };
    }
}
