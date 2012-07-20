package com.server.cx.http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HttpUtil {


    private static final String TAG = "HttpUtil";

    public static void challenge(Command command, String strAuthenticate) {
        String authHeader = createAuthenticateString(strAuthenticate, command);
        // if (!"".equals(WCECommand.identity)) {
        // this.setUrl(this.getUrl().replace("/" + WCECommand.identity, ""));
        // }
        command.addParameter("WCE-Authorization", authHeader);
        command.addParameter("Authorization", authHeader);

        HttpService.getInstance().putTask(command);
    }
    

    public static String createAuthenticateString(String strWWWAuthenticate,
            Command command) {

        String realm = "";
        String qop = "";
        String nonce = "";
        String opaque = "";

        String[] propertyList = strWWWAuthenticate.split(",");
        for (int i = 0; i < propertyList.length; ++i) {
            String[] property = propertyList[i].split("=");
            String propertyName = property[0];
            String propertyValue = property[1];

            if (propertyName.indexOf("realm") != -1) {
                realm = propertyValue.replace("\"", "");
            }
            if (propertyName.indexOf("qop") != -1) {
                qop = propertyValue.replace("\"", "");
            }
            if (propertyName.indexOf("nonce") != -1) {
                nonce = propertyValue.replace("\"", "");
            }
            if (propertyName.indexOf("opaque") != -1) {
                opaque = propertyValue.replace("\"", "");
            }
        }

        String nc = "00000001";
        String cnonce = createNonce();

        String HA1 = getMD5String(command.getUsername() + ":" + realm + ":"
                + command.getPassword());
        String HA2 = getMD5String(command.getMethod() + ":" + command.getPath());
        String response = getMD5String(HA1 + ":" + nonce + ":" + nc + ":"
                + cnonce + ":" + qop + ":" + HA2);

        String respAuthHeader = "Digest username='" + command.getUsername()
                + "', realm='" + realm + "', nonce='" + nonce + "', uri='"
                + command.getPath() + "', qop='" + qop + "', algorithm='MD5'"
                + ", nc=" + nc + ", cnonce='" + cnonce + "', response='"
                + response + "', opaque='" + opaque + "'";
        return respAuthHeader.replace("'", "\"");
    }

    private static String createNonce() {
        String chars = "0123456789abcdef";
        int string_length = 32;
        String randomstring = "";

        for (int i = 0; i < string_length; i++) {
            int rnum = (int) Math.floor(Math.random() * (chars.length()));
            randomstring += chars.substring(rnum, rnum + 1);
        }

        return randomstring;
    }

    private static MessageDigest md5 = null;

    private static String getMD5String(String str) {

        try {
            if (md5 == null) {
                md5 = MessageDigest.getInstance("MD5");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        md5.update(str.getBytes());
        byte[] b = md5.digest();
        int j;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            j = b[i];
            if (j < 0) {
                j += 256;
            }
            if (j < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(j));
        }
        return "" + sb;
    }
    
    public static String getJSONStringfy(String value){
        return "\""+value+"\"";
    }
    
    public static String getErrorCode(int httpCode) {
        String error = HttpConstants.ERROR_UNKNOWN;

        if (true) {
            try {
                switch (httpCode) {
                case 0:
                    error = HttpConstants.ERROR_NO_NETWORK;

                    break;
                case 401:
                    error = HttpConstants.ERROR_UNAUTHORIZED;
                    break;

                case 400:
                    error = HttpConstants.ERROR_BAD_REQUEST;
                    break;

                case 403:
                    error = HttpConstants.ERROR_FORBIDDEN;
                    break;

                case 404:
                    error = HttpConstants.ERROR_NOT_FOUND;
                    break;

                case 409:
                    error = HttpConstants.ERROR_CONFLICT;
                    break;

                case 500:
                    error = HttpConstants.ERROR_INTERNAL;
                    break;

                default:
                    error = HttpConstants.ERROR_UNKNOWN;
                    break;
                }
            } catch (Exception e) {
                error = HttpConstants.ERROR_UNKNOWN;
            }
        }
        return error;
    }

}
