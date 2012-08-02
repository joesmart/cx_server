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

    public ActionBuilder userURL(String url){
        action.setUseURL(url);
        return  this;
    }
    public ActionBuilder purchaseURL(String url){
        action.setPurchaseURL(url);
        return  this;
    }
    public Action build(){
        return action;
    }

    public Action buildGraphicItemAction(String imsi){
        return  action()
                .collectURL(baseHostAddress + restURL + imsi + "/myCollections")
                .purchaseURL(baseHostAddress + restURL + imsi + "/myPurchasedImages")
                .userURL(baseHostAddress + restURL + imsi + "/myGraphicInfos")
                .build();
    }

    public  Action buildCategoriesAction(String imsi,Long categoryId){
        return  action()
                .zoneInURL(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId)
                .zoneOutURL(baseHostAddress + restURL + imsi + "/categories")
                .build();
    }

}
