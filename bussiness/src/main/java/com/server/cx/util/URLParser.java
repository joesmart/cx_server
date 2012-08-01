package com.server.cx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.server.cx.constants.Constants;

public class URLParser {
    private static URLParser instance;

    public static URLParser getInstance() {
        if (instance == null) {
            instance = new URLParser();
        }
        return instance;
    }

    public String parse(String url, String method) {
        int startIndex = url.indexOf(Constants.PREFIX_STR) + Constants.PREFIX_STR.length();
        int endIndex;
        if (url.contains("?")) {
            endIndex = url.indexOf('?');
        } else {
            endIndex = url.length();
        }
        return url.substring(startIndex, endIndex);
    }

    public String getRequestData(InputStream inputStream) throws IOException {
        StringBuffer sb = null;
        BufferedReader br = null;
        String requestData = "";
        // receive body data
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            sb = new StringBuffer();
            String inputLine = null;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            requestData = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            br.close();
            sb = null;
            br = null;
        }
        return requestData;
    }

}
