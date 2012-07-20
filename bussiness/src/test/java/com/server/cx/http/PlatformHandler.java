package com.server.cx.http;


public interface PlatformHandler {
    void handleResponseDefault(int code, Object obj);
    public abstract void handleResponse(String code, Object obj);
}
