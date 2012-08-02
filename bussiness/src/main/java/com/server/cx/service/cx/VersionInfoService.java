package com.server.cx.service.cx;

import com.server.cx.dto.VersionInfoDTO;

public interface VersionInfoService {
    public VersionInfoDTO checkIsTheLatestVersion(String imsi, String version);
}
