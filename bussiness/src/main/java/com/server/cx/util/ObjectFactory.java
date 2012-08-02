package com.server.cx.util;

import com.server.cx.dto.UploadContactDTO;
import com.server.cx.dto.VersionInfoDTO;

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
}
