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
    
    public ActionBuilder graphicInfoRecommendUrl(String url) {
        action.setGraphicInfoRecommendUrl(url);
        return this;
    }
    
    public ActionBuilder graphicInfoHotUrl(String url) {
        action.setGraphicInfoHotUrl(url);
        return this;
    }
    
    public ActionBuilder graphicInfoByCategoryUrl(String url) {
        action.setGraphicInfoByCategoryUrl(url);
        return this;
    }
    
    public ActionBuilder myCXListUrl(String url) {
        action.setMyCXListUrl(url);
        return this;
    }
    
    public ActionBuilder statusPackageListUrl(String url) {
        action.setStatusPackageListUrl(url);
        return this;
    }
    
    public ActionBuilder holidayPackageListUrl(String url) {
        action.setHolidayPackageListUrl(url);
        return this;
    }
    
    public ActionBuilder themePackageListUrl(String url) {
        action.setThemePackageListUrl(url);
        return this;
    }
    
    public ActionBuilder versionUpdateUrl(String url) {
        action.setVersionUpdateUrl(url);
        return this;
    }
    
    public ActionBuilder suggestionUrl(String url) {
        action.setSuggestionUrl(url);
        return this;
    }
    
    public ActionBuilder phoneCallUrl(String url) {
        action.setPhoneCallUrl(url);
        return this;
    }
    
    public ActionBuilder userCollectListUrl(String url) {
        action.setUserCollectListUrl(url);
        return this;
    }
    
    public ActionBuilder userRegisterUrl(String url) {
        action.setUserRegisterUrl(url);
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
        actionBuilder.graphicInfoRecommendUrl(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true");
        actionBuilder.graphicInfoHotUrl(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true");
        actionBuilder.graphicInfoByCategoryUrl(baseHostAddress + restURL + imsi + "/categories");
        actionBuilder.myCXListUrl("");
        actionBuilder.statusPackageListUrl(baseHostAddress + restURL + imsi + "/statusTypes");
        actionBuilder.holidayPackageListUrl(baseHostAddress + restURL + imsi + "/holidayTypes");
        actionBuilder.themePackageListUrl("");
        actionBuilder.versionUpdateUrl(baseHostAddress + restURL + "upgrade");
        actionBuilder.suggestionUrl(baseHostAddress + restURL + "suggestion/" + imsi);
        actionBuilder.phoneCallUrl("");
        actionBuilder.userCollectListUrl(baseHostAddress + restURL + imsi + "/myCollections ");
        actionBuilder.userRegisterUrl("");
        return actionBuilder.build();
    }
    
    public Action buildUrlActions() {
        String replaceImsi = "none";
        return buildUrlActions(replaceImsi);
    }

}
