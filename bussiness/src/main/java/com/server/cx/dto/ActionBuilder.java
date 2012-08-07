package com.server.cx.dto;

import com.cl.cx.platform.dto.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:27
 * FileName:ActionBuilder
 */
@Component
public class ActionBuilder {
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


    private Action action ;

    public  ActionBuilder action(){
        action = new Action();
        return  this;
    }
    public  ActionBuilder zoneInURL(String url){
        action.setZoneInURL(url);
        return this;
    }

    public ActionBuilder zoneOutURL(String url){
        action.setZoneOutURL(url);
        return this;
    }

    public ActionBuilder collectURL(String url){
        action.setCollectURL(url);
        return this;
    }

    public ActionBuilder useURL(String url){
        action.setUseURL(url);
        return  this;
    }
    public ActionBuilder purchaseURL(String url){
        action.setPurchaseURL(url);
        return  this;
    }

    public ActionBuilder removeURL(String url){
        action.setRemoveURL(url);
        return this;
    }

    public ActionBuilder disableURL(String url){
        action.setDisableURL(url);
        return this;
    }
    
    public ActionBuilder recommendUrl(String url) {
        action.setRecommendUrl(url);
        return this;
    }
    
    public ActionBuilder hotUrl(String url) {
        action.setHotUrl(url);
        return this;
    }
    
    public ActionBuilder categoryUrl(String url) {
        action.setCategoryUrl(url);
        return this;
    }
    
    public ActionBuilder mGraphicsUrl(String url) {
        action.setMGraphicsUrl(url);
        return this;
    }
    
    public ActionBuilder statusUrl(String url) {
        action.setStatusUrl(url);
        return this;
    }
    
    public ActionBuilder holidaiesUrl(String url) {
        action.setHolidaiesUrl(url);
        return this;
    }
    
    public ActionBuilder customMGraphicsUrl(String url) {
        action.setCustomMGraphicsUrl(url);
        return this;
    }
    
    public ActionBuilder versionUrl(String url) {
        action.setVersionUrl(url);
        return this;
    }
    
    public ActionBuilder suggestionUrl(String url) {
        action.setSuggestionUrl(url);
        return this;
    }
    
    public ActionBuilder callUrl(String url) {
        action.setCallUrl(url);
        return this;
    }
    
    public ActionBuilder collectionsUrl(String url) {
        action.setCollectionsUrl(url);
        return this;
    }
    
    public ActionBuilder registerUrl(String url) {
        action.setRegisterUrl(url);
        return this;
    }
    
    

    public Action build(){
        return action;
    }
    
    public Action buildGraphicItemAction(String imsi){
        return  action()
                .collectURL(baseHostAddress + restURL + imsi + "/myCollections")
                .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
                .useURL(baseHostAddress + restURL + imsi + "/userCommonMGraphics")
                .build();
    }

    public  Action buildCategoriesAction(String imsi,Long categoryId){
        return  action()
                .zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId)
                .zoneOutURL(baseHostAddress + restURL + imsi + "/categories")
                .build();
    }

    public Action buildUserFavoriteItemAction(String imsi,String userFavoriteId){
        return  action()
                .removeURL(baseHostAddress + restURL + imsi + "/myCollections/"+userFavoriteId)
                .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
                .useURL(baseHostAddress + restURL + imsi + "/userCommonMGraphics")
                .build();
    }
    
    public Action buildUrlActions(String imsi) {
        ActionBuilder actionBuilder = action();
        actionBuilder.recommendUrl(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true");
        actionBuilder.hotUrl(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true");
        actionBuilder.categoryUrl(baseHostAddress + restURL + imsi + "/categories");
        actionBuilder.mGraphicsUrl("");
        actionBuilder.statusUrl(baseHostAddress + restURL + imsi + "/statusTypes");
        actionBuilder.holidaiesUrl(baseHostAddress + restURL + imsi + "/holidayTypes");
        actionBuilder.customMGraphicsUrl("");
        actionBuilder.versionUrl(baseHostAddress + restURL + "upgrade");
        actionBuilder.suggestionUrl(baseHostAddress + restURL + "suggestion/" + imsi);
        actionBuilder.callUrl("");
        actionBuilder.collectionsUrl(baseHostAddress + restURL + imsi + "/collections");
        actionBuilder.registerUrl("");
        return actionBuilder.build();
    }
    
    public Action buildUrlActions() {
        String replaceImsi = "none";
        Action action = buildUrlActions(replaceImsi);
        hiddenCustomActionsForNoImsi(action);
        return action;
    }
    
    private void hiddenCustomActionsForNoImsi(Action action) {
        action.setCollectionsUrl(null);
        action.setCallUrl(null);
        action.setMGraphicsUrl(null);
    }
    

}
