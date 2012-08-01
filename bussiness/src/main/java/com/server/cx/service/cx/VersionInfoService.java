package com.server.cx.service.cx;

import com.server.cx.dto.Result;

public interface VersionInfoService {
    public Result checkIsTheLatestVersion(String imsi, String version);
}
