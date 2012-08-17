package com.server.cx.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.Action;
import com.cl.cx.platform.dto.Actions;

/**
 * User: yanjianzou Date: 12-7-30 Time: 下午3:27 FileName:ActionBuilder
 */
@Component
public class ActionBuilder {
    @Autowired
    @Qualifier("baseHostAddress")
    protected String baseHostAddress;

    @Autowired
    @Qualifier("restURL")
    protected String restURL;

    @Autowired
    @Qualifier("imageShowURL")
    protected String imageShowURL;

    @Autowired
    @Qualifier("thumbnailSize")
    protected String thumbnailSize;

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

    public ActionBuilder collectionsUrl(String url, String method) {
        Action action = new Action(url, method);
        actions.setCollectionsURL(action);
        return this;
    }

    public ActionBuilder registerUrl(String url) {
        Action action = new Action(url, "POST");
        actions.setRegisterURL(action);
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
        return actions().collectURL(baseHostAddress + restURL + imsi + "/myCollections")
            .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
            .useURL(baseHostAddress + restURL + imsi + "/mGraphics").build();
    }

    public Actions buildHolidayMGraphicItemAction(String imsi) {
        return actions().collectURL(baseHostAddress + restURL + imsi + "/myCollections")
            .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
            .useURL(baseHostAddress + restURL + imsi + "/holidayMGraphics").build();
    }
    
    public Actions buildStatusMGraphicItemAction(String imsi) {
        return actions().collectURL(baseHostAddress + restURL + imsi + "/myCollections")
            .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
            .useURL(baseHostAddress + restURL + imsi + "/statusMGraphics").build();
    }

    public Actions buildHolidayMGraphicItemEditAction(String imsi, String mGraphicId) {
        return actions().collectURL(baseHostAddress + restURL + imsi + "/myCollections")
            .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
            .editURL(baseHostAddress + restURL + imsi + "/holidayMGraphics/" + mGraphicId)
            .removeURL(baseHostAddress + restURL + imsi + "/holidayMGraphics/" + mGraphicId).build();
    }

    public Actions buildHolidayMGraphicItemCreatedAction(String imsi, String mGraphicId) {
        return actions()
                .removeURL(baseHostAddress + restURL + imsi + "/holidayMGraphics/" + mGraphicId)
                .build();
    }

    public Actions buildStatusMGraphicItemEditAction(String imsi, String mGraphicId) {
        return actions().collectURL(baseHostAddress + restURL + imsi + "/myCollections")
            .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
            .editURL(baseHostAddress + restURL + imsi + "/statusMGraphics/" + mGraphicId)
            .removeURL(baseHostAddress + restURL + imsi + "/statusMGraphics/" + mGraphicId).build();
    }

    public Actions buildStatusMGraphicItemCreatedAction(String imsi, String mGraphicId) {
        return actions()
                .removeURL(baseHostAddress + restURL + imsi + "/statusMGraphics/" + mGraphicId)
                .build();
    }

    public Actions buildCategoriesAction(String imsi, Long categoryId) {
        return actions().zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId)
            .zoneOutURL(baseHostAddress + restURL + imsi + "/categories").build();
    }

    public Actions buildHolidayTypeAction(String imsi, Long holidayTypeId) {
        return actions()
            .zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?holidayTypeId=" + holidayTypeId)
            .zoneOutURL(baseHostAddress + restURL + imsi + "/holidayTypes")
            .useURL(baseHostAddress + restURL + imsi + "/holidayMGraphics?immediate=true")
            .build();
    }

    public Actions buildHolidayTypeHasUsedActions(String imsi, Long holidayTypeId,String mgraphicId) {
        return actions()
                .zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?holidayTypeId=" + holidayTypeId)
                .zoneOutURL(baseHostAddress + restURL + imsi + "/holidayTypes")
                .useURL(baseHostAddress + restURL + imsi + "/holidayMGraphics?immediate=true")
                .removeURL(baseHostAddress + restURL + imsi + "/holidayMGraphics/"+mgraphicId)
                .build();
    }
    
    public Actions buildStatusTypeAction(String imsi, Long statusTypeId) {
        return actions()
               .zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?statusTypeId=" + statusTypeId)
               .zoneOutURL(baseHostAddress + restURL + imsi + "/statusTypes")
               .useURL(baseHostAddress + restURL + imsi + "/statusMGraphics?immediate=true")
               .build();
    }

    public Actions buildStatusTypeHasUsedActions(String imsi,Long statusTypeId,String mgraphicId){
        return actions()
                .zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?statusTypeId=" + statusTypeId)
                .zoneOutURL(baseHostAddress + restURL + imsi + "/statusTypes")
                .useURL(baseHostAddress + restURL + imsi + "/statusMGraphics?immediate=true")
                .removeURL(baseHostAddress + restURL + imsi + "/statusMGraphics/"+mgraphicId)
                .build();
    }

    public Actions buildUserFavoriteItemAction(String imsi, String userFavoriteId) {
        return actions().removeURL(baseHostAddress + restURL + imsi + "/myCollections/" + userFavoriteId)
            .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
            .useURL(baseHostAddress + restURL + imsi + "/mGraphics").build();
    }
    
    public Actions buildUrlActions(String imsi) {
        return actions().recommendUrl(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true")
            .hotUrl(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true")
            .categoryUrl(baseHostAddress + restURL + imsi + "/categories")
            .mGraphicsUrl(baseHostAddress + restURL + imsi + "/mGraphics")
            .statusUrl(baseHostAddress + restURL + imsi + "/statusTypes")
            .holidaysUrl(baseHostAddress + restURL + imsi + "/holidayTypes")
            .customMGraphicsUrl(baseHostAddress + restURL + imsi + "/customMGraphics")
            .versionUrl(baseHostAddress + restURL + "upgrade")
            .suggestionUrl(baseHostAddress + restURL + imsi + "/suggestion")
            .callUrl(baseHostAddress + restURL + imsi + "/callings")
            .collectionsUrl(baseHostAddress + restURL + imsi + "/myCollections", "GET")
            .inviteFriendsURL(baseHostAddress + restURL + imsi + "/sms")
            .registerUrl(baseHostAddress + restURL + "register")
            .getContactsURL(baseHostAddress + restURL + imsi + "/contacts")
            .uploadCommonMGraphicURL(baseHostAddress + restURL + imsi + "/userCommonMGraphic/upload" )
            .uploadContactsURL(baseHostAddress + restURL + imsi + "/contacts").build();
            
    }

    public Actions buildUrlActions() {
        String replaceImsi = "none";
        Actions actions = buildUrlActions(replaceImsi);
        hiddenCustomActionsForNoImsi(actions);
        return actions;
    }

    private void hiddenCustomActionsForNoImsi(Actions actions) {
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
                .editURL(baseHostAddress+restURL+imsi+"/"+conditions+"/" +id)
                .removeURL(baseHostAddress+restURL+imsi+ "/"+conditions+"/" +id)
                .build();
    }

    public Actions buildHistoryMGraphicActions(String imsi, String id) {
        return actions().useURL(baseHostAddress + restURL + imsi + "/mGraphics")
            .removeURL(baseHostAddress + restURL + imsi + "/historyMGraphics/" + id).build();
    }

}
