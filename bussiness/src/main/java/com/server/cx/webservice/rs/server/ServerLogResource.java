package com.server.cx.webservice.rs.server;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * User: yanjianzou
 * Date: 9/26/12
 * Time: 2:03 PM
 */

@Component
@Path("log")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ServerLogResource {

    private Integer currentLineNumber = 0;

    private String logFilePath;
    @GET
    public Response getLog(@DefaultValue("300")@QueryParam("line")Long line,@Context HttpHeaders headers,@Context HttpServletRequest request,@Context HttpServletResponse response){
        response.setHeader("Content-Type","application/json; charset=UTF-8");
        response.setHeader("Accept-Charset","UTF-8");

        try {
            String hostIp = request.getServerName();
            int port = request.getServerPort();
            if(hostIp.indexOf("localhost")>=0){
                logFilePath = "F:/bussiness.log";
            }else if(hostIp.indexOf("10.90.3.")>=0){
                if(port == 38183){
                    logFilePath = "/home/pi88dian88/apache-tomcat-7.0.23/bin/logs/bussiness.log";
                }else  if(port == 9080){
                    logFilePath = "/home/pi88dian88/apache-tomcat-7.0.23_bak/bin/logs/bussiness.log";
                }else{
                    return Response.ok("Not Support Server").build();
                }
            }else if(hostIp.indexOf("111.1.17.82")>=0){
                if(port == 38183){
                    logFilePath = "/usr/local/tomcat/apache-tomcat-7.0.23/bin/logs/bussiness.log";
                }else{
                    return Response.ok("Not Support Server").build();
                }
            }
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(logFilePath));
            lineNumberReader.skip(Long.MAX_VALUE);
            currentLineNumber = lineNumberReader.getLineNumber();
            lineNumberReader = new LineNumberReader(new FileReader(logFilePath));
            Long skipLine = currentLineNumber - line < 0?0: currentLineNumber -line;
            lineNumberReader.skip(skipLine);

            String lineString = "Server log....\r\n";
            StringBuilder builder = new StringBuilder();
            while (lineString != null){
                builder.append(lineString);
                lineString = lineNumberReader.readLine();
            }
            lineNumberReader.close();
            response.getWriter().write(builder.toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok().build();
    }
}
