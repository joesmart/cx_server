// Copyright (c) 2009-2012 CIeNET Ltd. All rights reserved.

package com.server.cx.http;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Briefly describe what this class does.
 */
public class Command {

    private String method;
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private PlatformHandler handler;
    private boolean isPollingEvent = false;
    private String postBody;
    private String identity = "";
    private String username = "";
    private String password = "";
    private String url;
    private String path;
    private String type;
    private String uri;
    private boolean authorize = false;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Command() {
        addParameter("Accept", "application/json");
        addParameter("Content-Type", "application/json;charset=UTF-8");
    }

    public Command(String url, String path, String method, PlatformHandler handler) {
        this.url = url;
        this.path = path;
        this.method = method;
        this.handler = handler;
    }

    public void addParameter(final String key, final String value) {
       
        headers.put(key, value);
    }

    
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        if(method == null)
            return null;
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public PlatformHandler getHandler() {
        return handler;
    }

    public void setHandler(PlatformHandler handler) {
        this.handler = handler;
    }

    public boolean isPollingEvent() {
        return isPollingEvent;
    }

    public void setPollingEvent(boolean isPollingEvent) {
        this.isPollingEvent = isPollingEvent;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addHeader(String key, String value) {
        addParameter(key, value);
    }
    public Map<String, String> getHeader() {
        return headers;
    }

    public void setBody(String body) {
        postBody = body;
    }
    
}
