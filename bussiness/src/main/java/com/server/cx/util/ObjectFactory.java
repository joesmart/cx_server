package com.server.cx.util;

import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.UploadContactDTO;
import com.cl.cx.platform.dto.VersionInfoDTO;

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

    public static UploadContactDTO buildUploadContactDTO(String flag, String content) {
        UploadContactDTO uploadContactDTO = new UploadContactDTO();
        uploadContactDTO.setFlag(flag);
        uploadContactDTO.setContent(content);
        return uploadContactDTO;
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
}
