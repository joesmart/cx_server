package com.server.cx.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.exec.ExecuteException;

public class HttpTask implements Runnable {
    public static final String TAG = HttpTask.class.getName();

    public static String respAuthHeader;

    private String url;

    private int connCode = -1;

    private Command command;

    public static String sessionInfo;
    private HttpURLConnection conn;

    public static boolean loginSucess;

    public HttpTask(Command command) {
        super();
        this.command = command;
    }

    @Override
    public void run() {
        beforeExecute();
        connCode = -1;
        URL mUrl;
        System.out.println("---> into run");
        conn = null;
        OutputStreamWriter osw = null;
        Map<String, List<String>> headerFields = null;
        try {
            if (url == null) {
                url = command.getUrl();
            }
            mUrl = new URL(url);
            System.out.println("URL-----------" + url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            			conn.setRequestMethod(command.getMethod());
            conn.setConnectTimeout(20000);
            HttpURLConnection.setFollowRedirects(false);
            Map<String, String> headers = command.getHeader();
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
            // if body not null send body message
            String postBody = command.getPostBody();
//            Log.d(TAG, "into---[HttpTask]postBody:" + command.getPostBody());
            if (postBody != null) {
                osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(postBody);
                osw.flush();
            }
            
            // if file is not null send file here
            conn.connect();
            connCode = conn.getResponseCode();
            System.out.println("connCode = " + connCode);
            headerFields = conn.getHeaderFields();
            handleResponse(conn, headerFields);
        } catch (Exception e) {
            handleResponseErrorCode();
        }
        afterExecute();
    }

    /**
     * 
     * <p> Title: handleResponse </p> <p> Description: handleResponse </p>
     * 
     * @param conn
     * @param headers
     * @throws IOException
     */
    private void handleResponse(HttpURLConnection conn, Map<String, List<String>> headers) {
        if (connCode >=200 && connCode < 400) {
            handleResponseCode(conn);
        } else {
            handleResponseErrorCode();
        }

    }

    /**
     * 
     * <p> Title: handleResponseCode </p> <p> Description: handleResponseCode </p>
     * 
     * @param conn
     * @throws IOException
     */
    private void handleResponseCode(HttpURLConnection conn) {
        InputStreamReader ois = null;
        BufferedReader br = null;
        try {
            ois = new InputStreamReader(conn.getInputStream());
            br = new BufferedReader(ois);
            String inputLine = null;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            conn.disconnect();
            System.out.println("result = " + sb.toString());
        } catch (Exception e) {
        	e.printStackTrace();
            handleResponseErrorCode();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        conn = null;
    }

    /**
     * 使用try.. catch，防止在handleResponseDefault方法中出异常的时候，handleResponseDefault被调用两次
     */
    private void handleResponseErrorCode() {
        try {
//        	InputStreamReader ois = null;
//            BufferedReader br = null;
//            try {
//                ois = new InputStreamReader(conn.getInputStream());
//                br = new BufferedReader(ois);
//                String inputLine = null;
//                StringBuffer sb = new StringBuffer();
//                while ((inputLine = br.readLine()) != null) {
//                    sb.append(inputLine);
//                }
//                conn.disconnect();
//                System.out.println("result = " + sb.toString());
//            } catch(Exception e) {
//            	e.printStackTrace();
//            }
            if (command.getHandler() != null) {
                command.getHandler().handleResponseDefault(connCode, null);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 
     * <p> Title: beforeExecute </p> <p> Description: beforeExecute </p>
     */
    public void beforeExecute() {
    }

    /**
     * 
     * <p> Title: afterExecute </p> <p> Description: afterExecute </p>
     */
    public void afterExecute() {
    }

}
