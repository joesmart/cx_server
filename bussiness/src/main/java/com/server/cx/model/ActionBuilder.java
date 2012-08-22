package com.server.cx.model;

import com.cl.cx.platform.dto.Action;
import com.cl.cx.platform.dto.Actions;
import com.server.cx.service.cx.impl.BasicService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: yanjianzou Date: 12-7-30 Time: 下午3:27 FileName:ActionBuilder
 */
@Component
public class ActionBuilder {
    @Autowired
    @Setter @Getter
    private BasicService basicService;

    private Actions actions;

    public ActionBuilder actions() {
        actions = new Actions();
        return this;
    }

    public ActionBuilder zoneInURL(String url) {
        Action action = new Action(url, "GET");
        actions.setZoneInURL(action);
        return this;
    }

    public ActionBuilder zoneOutURL(String url) {
        Action action = new Action(url, "GET");
        actions.setZoneOutURL(action);
        return this;
    }

    public ActionBuilder collectURL(String url) {
        Action action = new Action(url, "POST");
        actions.setCollectURL(action);
        return this;
    }

    public ActionBuilder useURL(String url) {
        Action action = new Action(url, "POST");
        actions.setUseURL(action);
        return this;
    }

    public ActionBuilder purchaseURL(String url) {
        Action action = new Action(url, "POST");
        actions.setPurchaseURL(action);
        return this;
    }

    public ActionBuilder removeURL(String url) {
        Action action = new Action(url, "DELETE");
        actions.setRemoveURL(action);
        return this;
    }

    public ActionBuilder disableURL(String url) {
        Action action = new Action(url, "DELETE");
        actions.setDisableURL(action);
        return this;
    }

    public ActionBuilder recommendUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setRecommendURL(action);
        return this;
    }

    public ActionBuilder hotUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setHotURL(action);
        return this;
    }

    public ActionBuilder categoryUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setCategoryURL(action);
        return this;
    }

    public ActionBuilder mGraphicsUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setMgraphicsURL(action);
        return this;
    }

    public ActionBuilder statusUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setStatusURL(action);
        return this;
    }

    public ActionBuilder holidaysUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setHolidaysURL(action);
        return this;
    }

    public ActionBuilder customMGraphicsUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setCustomMGraphicsURL(action);
        return this;
    }

    public ActionBuilder versionUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setVersionURL(action);
        return this;
    }

    public ActionBuilder suggestionUrl(String url) {
        Action action = new Action(url, "POST");
        actions.setSuggestionURL(action);
        return this;
    }

    public ActionBuilder callUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setCallURL(action);
        return this;
    }

    public ActionBuilder collectionsUrl(String url) {
        Action action = new Action(url, "GET");
        actions.setCollectionsURL(action);
        return this;
    }

    public ActionBuilder registerUrl(String url) {
        Action action = new Action(url, "POST");
        actions.setRegisterURL(action);
        return this;
    }

    public ActionBuilder registerUpDateURL(String url){
        Action action = new Action(url, "PUT");
        actions.setUpdateUserInfoURL(action);
        return this;
    }

    public ActionBuilder editURL(String url) {
        Action action = new Action(url, "PUT");
        actions.setEditURL(action);
        return this;
    }

    public ActionBuilder inviteFriendsURL(String url) {
        Action action = new Action(url, "POST");
        actions.setInviteFriendsURL(action);
        return this;
    }

    public ActionBuilder uploadContactsURL(String url) {
        Action action = new Action(url, "POST");
        actions.setUploadContactsURL(action);
        return this;
    }
    
    public ActionBuilder getContactsURL(String url) {
        Action action = new Action(url, "GET");
        actions.setGetContactsURL(action);
        return this;
    }
    
    private ActionBuilder uploadCommonMGraphicURL(String url) {
        Action action = new Action(url, "POST");
        actions.setUploadCommonMGraphicURL(action);
        return this;
    }

    public Actions build() {
        return actions;
    }

    public Actions buildGraphicItemAction(String imsi) {
        return actions().collectURL(basicService.generateMyCollectionsVisitURL(imsi))
            .purchaseURL(basicService.generateImagePurchaseURL(imsi))
            .useURL(basicService.generateMGraphicsURL(imsi)).build();
    }

    public Actions buildHolidayMGraphicItemAction(String imsi) {
        return actions().collectURL(basicService.generateMyCollectionsVisitURL(imsi))
            .purchaseURL(basicService.generateImagePurchaseURL(imsi))
            .useURL(basicService.generateHolidayMGraphicUseURL(imsi)).build();
    }

    public Actions buildStatusMGraphicItemAction(String imsi) {
        return actions().collectURL(basicService.generateMyCollectionsVisitURL(imsi))
            .purchaseURL(basicService.generateImagePurchaseURL(imsi))
            .useURL(basicService.generateStatusMGraphicUseURL(imsi)).build();
    }

    public Actions buildHolidayMGraphicItemEditAction(String imsi, String mGraphicId) {
        return actions().collectURL(basicService.generateMyCollectionsVisitURL(imsi))
            .purchaseURL(basicService.generateImagePurchaseURL(imsi))
            .editURL(basicService.generateHolidayMGraphicRemoveURL(imsi, mGraphicId))
            .removeURL(basicService.generateHolidayMGraphicRemoveURL(imsi, mGraphicId)).build();
    }

    public Actions buildHolidayMGraphicItemCreatedAction(String imsi, String mGraphicId) {
        return actions()
                .removeURL(basicService.generateHolidayMGraphicRemoveURL(imsi, mGraphicId))
                .build();
    }

    public Actions buildStatusMGraphicItemEditAction(String imsi, String mGraphicId) {
        return actions().collectURL(basicService.generateMyCollectionsVisitURL(imsi))
            .purchaseURL(basicService.generateImagePurchaseURL(imsi))
            .editURL(basicService.generateStatusMGraphicRemoveURL(imsi, mGraphicId))
            .removeURL(basicService.generateStatusMGraphicRemoveURL(imsi, mGraphicId)).build();
    }

    public Actions buildStatusMGraphicItemCreatedAction(String imsi, String mGraphicId) {
        return actions()
                .removeURL(basicService.generateStatusMGraphicRemoveURL(imsi, mGraphicId))
                .build();
    }

    public Actions buildCategoriesAction(String imsi, Long categoryId) {
        return actions().zoneInURL(basicService.generateGraphicInfosZoneInURL(imsi, categoryId))
            .zoneOutURL(basicService.generateCategoriesVisitURL(imsi)).build();
    }

    public Actions buildHolidayTypeAction(String imsi, Long holidayTypeId) {
        return actions()
            .zoneInURL(basicService.generateHolidayTypeGraphicInfosVisitURL(imsi, holidayTypeId))
            .zoneOutURL(basicService.generateHolidayTypesVisitURL(imsi))
            .useURL(basicService.generateImmediateUseHolidayMGraphicURL(imsi))
            .build();
    }

    public Actions buildHolidayTypeHasUsedActions(String imsi, Long holidayTypeId,String mgraphicId) {
        return actions()
                .zoneInURL(basicService.generateHolidayTypeGraphicInfosVisitURL(imsi, holidayTypeId))
                .zoneOutURL(basicService.generateHolidayTypesVisitURL(imsi))
                .useURL(basicService.generateImmediateUseHolidayMGraphicURL(imsi))
                .removeURL(basicService.generateHolidayMGraphicRemoveURL(imsi, mgraphicId))
                .build();
    }

    public Actions buildStatusTypeAction(String imsi, Long statusTypeId) {
        return actions()
               .zoneInURL(basicService.generateStatusTypeGraphicInfosVisitURL(imsi, statusTypeId))
               .zoneOutURL(basicService.generateStatusTypeVisitURL(imsi))
               .useURL(basicService.generateImmediateUseStatusMGraphicURL(imsi))
               .build();
    }

    public Actions buildStatusTypeHasUsedActions(String imsi,Long statusTypeId,String mgraphicId){
        return actions()
                .zoneInURL(basicService.generateStatusTypeGraphicInfosVisitURL(imsi, statusTypeId))
                .zoneOutURL(basicService.generateStatusTypeVisitURL(imsi))
                .useURL(basicService.generateImmediateUseStatusMGraphicURL(imsi))
                .removeURL(basicService.generateStatusMGraphicRemoveURL(imsi, mgraphicId))
                .build();
    }

    public Actions buildUserFavoriteItemAction(String imsi, String userFavoriteId) {
        return actions().removeURL(basicService.generateMyCollectionsOperateURL(imsi, userFavoriteId))
            .purchaseURL(basicService.generateImagePurchaseURL(imsi))
            .useURL(basicService.generateMGraphicsURL(imsi)).build();
    }

    public Actions buildUserOperableActions(String imsi) {
        return actions().recommendUrl(basicService.generateRecommendGraphicInfoVisitURL(imsi))
            .hotUrl(basicService.generateHotGraphicInfosVisitURL(imsi))
            .categoryUrl(basicService.generateCategoriesVisitURL(imsi))
            .mGraphicsUrl(basicService.generateMGraphicsURL(imsi))
            .statusUrl(basicService.generateStatusTypeVisitURL(imsi))
            .holidaysUrl(basicService.generateHolidayTypesVisitURL(imsi))
            .customMGraphicsUrl(basicService.generateCustomMGraphicsVisitURL(imsi))
            .versionUrl(basicService.generateVersionVisitURL())
            .suggestionUrl(basicService.generateSuggestionSubmitURL(imsi))
            .callUrl(basicService.generateCallingURL(imsi))
            .collectionsUrl(basicService.generateMyCollectionsVisitURL(imsi))
            .inviteFriendsURL(basicService.generateInviteFriendsURL(imsi))
            .registerUrl(basicService.generateMobileRegisterURL())
            .registerUpDateURL(basicService.generateMobileRegisterUpdateURL(imsi))
            .getContactsURL(basicService.generateContactsUploadURL(imsi))
            .uploadCommonMGraphicURL(basicService.generateUserDIYMGraphicUploadURL(imsi))
            .uploadContactsURL(basicService.generateContactsUploadURL(imsi)).build();
            
    }

    public Actions buildAnonymousActions() {
        String replaceImsi = "none";
        Actions actions = buildUserOperableActions(replaceImsi);
        hiddenTheNeedRegisteredActions(actions);
        return actions;
    }

    private void hiddenTheNeedRegisteredActions(Actions actions) {
        actions.setCollectionsURL(null);
        actions.setCallURL(null);
        actions.setMgraphicsURL(null);
        actions.setSuggestionURL(null);
        actions.setCustomMGraphicsURL(null);
        actions.setInviteFriendsURL(null);
        actions.setUploadContactsURL(null);
        actions.setGetContactsURL(null);
        actions.setUploadCommonMGraphicURL(null);
    }



    public Actions buildMGraphicActions(String imsi, String id, String conditions){
        return  actions()
                .editURL(basicService.getBaseURL()+imsi+"/"+conditions+"/" +id)
                .removeURL(basicService.getBaseURL()+imsi+ "/"+conditions+"/" +id)
                .build();
    }

    public Actions buildHistoryMGraphicActions(String imsi, String id) {
        return actions().useURL(basicService.generateMGraphicsURL(imsi))
            .removeURL(basicService.generateHistoryMGraphicRemoveURL(imsi, id))
            .build();
    }

}
