package com.server.cx.util;

import com.cl.cx.platform.dto.*;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.entity.cx.*;
import com.server.cx.util.business.AuditStatus;

import java.util.List;

public class ObjectFactory {
    public static VersionInfoDTO buildVersionInfoDTO(String flag, String content) {
        VersionInfoDTO versionInfoDTO = new VersionInfoDTO();
        versionInfoDTO.setFlag(flag);
        versionInfoDTO.setContent(content);
        return versionInfoDTO;
    }

    public static VersionInfoDTO buildVersionInfoDTO(String flag, String content, String forceUpdate, String url) {
        VersionInfoDTO versionInfoDTO = buildVersionInfoDTO(flag, content);
        versionInfoDTO.setForceUpdate(forceUpdate);
        versionInfoDTO.setUrl(url);
        return versionInfoDTO;
    }

    public static OperationDescription buildOperationDescription(int statusCode, String actionName) {
        return buildOperationDescription(statusCode, actionName, null);
    }

    public static OperationDescription buildOperationDescription(int statusCode, String actionName, String message) {
        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setDealResult(Constants.SUCCESS_FLAG);
        operationDescription.setStatusCode(statusCode);
        operationDescription.setActionName(actionName);
        operationDescription.setDevelopMessage(message);
        return operationDescription;
    }

    public static RegisterOperationDescription buildRegisterOperationDescription(int statusCode, String actionName) {
        return buildRegisterOperationDescription(statusCode, actionName, null);
    }

    public static RegisterOperationDescription buildRegisterOperationDescription(int errorCode, String actionName,
                                                                                 String flag) {
        RegisterOperationDescription operationDescription = new RegisterOperationDescription();
        operationDescription.setDealResult(Constants.SUCCESS_FLAG);
        operationDescription.setStatusCode(errorCode);
        operationDescription.setActionName(actionName);
        operationDescription.setDevelopMessage(flag);
        return operationDescription;
    }

    public static RegisterOperationDescription buildErrorRegisterOperationDescription(int errorCode, String actionName,
                                                                                      String flag) {
        RegisterOperationDescription operationDescription = new RegisterOperationDescription();
        operationDescription.setDealResult(Constants.ERROR_FLAG);
        operationDescription.setErrorCode(errorCode);
        operationDescription.setActionName(actionName);
        operationDescription.setErrorMessage(flag);
        return operationDescription;
    }

    public static RegisterOperationDescription buildErrorRegisterOperationDescription(int errorCode, String actionName) {
        return buildErrorRegisterOperationDescription(errorCode, actionName, null);
    }

    public static OperationDescription buildErrorOperationDescription(int errorCode, String actionName, String flag) {
        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setDealResult(Constants.ERROR_FLAG);
        operationDescription.setErrorCode(errorCode);
        operationDescription.setActionName(actionName);
        operationDescription.setErrorMessage(flag);
        return operationDescription;
    }

    public static OperationDescription buildErrorOperationDescription(int errorCode, String actionName) {
        return buildErrorOperationDescription(errorCode, actionName, null);
    }

    public static List<SuggestionDTO> buildAllSuggestionDTOFromSuggestion(List<Suggestion> suggestions) {
        if (suggestions == null || suggestions.isEmpty())
            return null;

        List<SuggestionDTO> suggestionDTOs = Lists.newArrayList();
        for (Suggestion suggestion : suggestions) {
            SuggestionDTO suggestionDTO = new SuggestionDTO();
            suggestionDTO.setContent(suggestion.getContent());
            suggestionDTOs.add(suggestionDTO);
        }
        return suggestionDTOs;
    }

    public static UserSubscribeRecord buildUserCXCoinIncomeRecord(UserInfo userInfo, Double income, String description) {
        UserSubscribeRecord userSubscribeRecord = new UserSubscribeRecord();
        userSubscribeRecord.setUserInfo(userInfo);
        userSubscribeRecord.setDescription(description);
        userSubscribeRecord.setIncome(income);
        return userSubscribeRecord;
    }

    public static UserSubscribeRecord buildUserSubscribeRecord(UserSubscribeType userSubscribeType) {
        UserSubscribeRecord userSubscribeRecord = new UserSubscribeRecord();
        userSubscribeRecord.setSubscribeType(userSubscribeType.getSubscribeType());
        userSubscribeRecord.setUserInfo(userSubscribeType.getUserInfo());
        userSubscribeRecord.setDescription("订购");
        return userSubscribeRecord;
    }

    public static UserSubscribeRecord buildUserCancelSubscribeRecord(UserSubscribeType userSubscribeType) {
        UserSubscribeRecord userSubscribeRecord = new UserSubscribeRecord();
        userSubscribeRecord.setSubscribeType(userSubscribeType.getSubscribeType());
        userSubscribeRecord.setUserInfo(userSubscribeType.getUserInfo());
        userSubscribeRecord.setDescription("取消订购");
        return userSubscribeRecord;
    }

    public static UserSubscribeRecord buildUserGraphicItemSubscribeRecord(UserInfo userInfo) {
        UserSubscribeRecord userSubscribeRecord = new UserSubscribeRecord();
        //TODO
        //        userInfo.setGraphicInfo()
        userSubscribeRecord.setUserInfo(userInfo);
        userSubscribeRecord.setDescription("订购");
        return userSubscribeRecord;
    }

    public static OperationDescription buildOperationDescription(int statusCode, String actionName, String flag,
                                                                 Actions actions) {
        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setStatusCode(statusCode);
        operationDescription.setActionName(actionName);
        operationDescription.setDealResult(flag);
        operationDescription.setActions(actions);
        return operationDescription;
    }

    public static GraphicInfo buildGraphicInfoFromDataItem(DataItem dataItem) {
        GraphicResource graphicResource = new GraphicResource();
        List<GraphicResource> graphicResources = Lists.newArrayList();
        graphicResources.add(graphicResource);

        GraphicInfo graphicInfo = new GraphicInfo();
        graphicInfo.setName(dataItem.getName());
        graphicInfo.setPrice(Double.valueOf(dataItem.getPrice()));
        graphicInfo.setLevel(dataItem.getLevel());
        graphicInfo.setGraphicResources(graphicResources);
        graphicResource.setGraphicInfo(graphicInfo);

        graphicInfo.getGraphicResources().get(0).setResourceId(dataItem.getSourceImagePath());
        graphicInfo.getGraphicResources().get(0).setType(dataItem.getMediaType());
        graphicInfo.getGraphicResources().get(0).setAuditStatus(AuditStatus.valueOf(dataItem.getAuditStatus()));
        return graphicInfo;
    }
}
