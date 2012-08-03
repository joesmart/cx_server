package com.server.cx.service.cx;

import com.cl.cx.platform.dto.VersionInfoDTO;

public interface VersionInfoService {
    public VersionInfoDTO checkIsTheLatestVersion(String imsi, String version);
}
