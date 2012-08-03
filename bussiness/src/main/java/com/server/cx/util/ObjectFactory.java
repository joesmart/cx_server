package com.server.cx.util;

import java.util.List;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.SuggestionDTO;
import com.cl.cx.platform.dto.UploadContactDTO;
import com.cl.cx.platform.dto.VersionInfoDTO;
import com.google.common.collect.Lists;
import com.server.cx.entity.cx.Suggestion;

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

    public static OperationDescription buildOperationDescription(int statusCode, String actionName, String flag) {
        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setStatusCode(statusCode);
        operationDescription.setActionName(actionName);
        operationDescription.setDealResult(flag);
        return operationDescription;
    }

    public static OperationDescription buildErrorOperationDescription(int errorCode, String actionName, String flag) {
        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setErrorCode(errorCode);
        operationDescription.setActionName(actionName);
        operationDescription.setDealResult(flag);
        return operationDescription;
    }

    public static List<SuggestionDTO> buildAllSuggestionDTOFromSuggestion(List<Suggestion> suggestions) {
        if(suggestions == null || suggestions.isEmpty()) 
            return null;
        
        List<SuggestionDTO> suggestionDTOs = Lists.newArrayList();
        for(Suggestion suggestion : suggestions) {
            SuggestionDTO suggestionDTO = new SuggestionDTO();
            suggestionDTO.setContent(suggestion.getContent());
            suggestionDTOs.add(suggestionDTO);
        }
        return suggestionDTOs;
    }
}
