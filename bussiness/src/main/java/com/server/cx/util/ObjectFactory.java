package com.server.cx.util;

import com.server.cx.dto.Result;

public class ObjectFactory {
    public static Result buildVersionInfoResult(String flag, String content) {
        Result result = new Result();
        result.setFlag(flag);
        result.setContent(content);
        return result;
    }

    public static Result buildVersionInfoResult(String flag, String content, String forceUpdate, String url) {
        Result result = buildVersionInfoResult(flag, content);
        result.setForceUpdate(forceUpdate);
        result.setUrl(url);
        return result;
    }
}
