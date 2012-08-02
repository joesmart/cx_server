package com.server.cx.servlet;

import com.server.cx.constants.Constants;
import com.server.cx.dto.Result;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserCXInfoManagerService;
import com.server.cx.util.StringUtil;
import com.server.cx.xml.util.XMLUnmarshallUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Joey@smart
 * @Package com.server.cx.servlet
 * @ClassName: UserCXInfoAddServlet.java
 * @Description: 彩像添加和更新servlet类.
 * @date 10:48:38 AM Oct 28, 2011 2011
 */
public class UserCXInfoAddServlet extends HttpServlet {
    private static final long serialVersionUID = -4302719561778080755L;
    private Logger logger = LoggerFactory.getLogger(UserCXInfoAddServlet.class);

    @Autowired
    private UserCXInfoManagerService userCXInfoManagerService;
    private XMLUnmarshallUtil xmlUnmarshallUtil;

    public void init(ServletConfig config) throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决客户端乱码问题
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setHeader("CharacterSet", "utf-8");

        // 需要把http输入流进行转换 解决 jxab xml解析的premature 问题.
        byte[] contentBytes = changeToByteArrayFromtheInputStream(req.getInputStream());

        String dealResult = "";
        if (contentBytes.length < 10) {
            dealResult = StringUtil.generateXMLResultString(Constants.NULL_INPUT_FLAG, "无数据输入");
        } else {
            ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(contentBytes);
            String xml = new String(contentBytes);
            logger.info(xml);

            // 无数据输入时的异常
            xmlUnmarshallUtil = new XMLUnmarshallUtil(Result.class);
            Result xmlConvertResult = xmlUnmarshallUtil.unmarshallXMLFileResult(byteArrayInput);
            if (xmlConvertResult == null) {
                dealResult = StringUtil.generateXMLResultString(Constants.NULL_INPUT_FLAG, "无数据输入");
            } else {
                String serverPath = req.getSession().getServletContext().getRealPath("/");
                try {
                    dealResult = userCXInfoManagerService.dealWithUserCXInfoAdding(xmlConvertResult);
                } catch (SystemException e) {
                    dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, e.getLocalMessage());
                } catch (Exception e) {
                    SystemException systemexception = new CXServerBusinessException(e, "系统内部错误");
                    dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, systemexception.getLocalMessage());
                }
            }
        }

        contentBytes = null;

        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
        if (dealResult == null || "".equals(dealResult)) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            logger.info("response data = " + dealResult);
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            out.write(dealResult);
        }
    }

    private byte[] changeToByteArrayFromtheInputStream(InputStream inputStream) throws IOException {
        InputStream input = inputStream;
        byte[] bytes = new byte[4096];
        ByteArrayOutputStream byteArrayOutPut = new ByteArrayOutputStream();
        int readResult = 0;
        while ((readResult = input.read(bytes)) != -1) {
            byteArrayOutPut.write(bytes, 0, readResult);
        }
        bytes = null;
        byte[] contentBytes = byteArrayOutPut.toByteArray();
        return contentBytes;
    }

}
