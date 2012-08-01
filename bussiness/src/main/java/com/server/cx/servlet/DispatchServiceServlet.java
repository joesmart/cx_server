package com.server.cx.servlet;

import com.server.cx.util.ServiceChooseUtil;
import com.server.cx.util.URLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DispatchServiceServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    private static final long serialVersionUID = 1L;
    private URLParser urlParser = URLParser.getInstance();

    @Autowired
    private ServiceChooseUtil serviceChooseUtil;// = ServiceChooseUtil.getInstance();

    private Logger logger = LoggerFactory.getLogger(DispatchServiceServlet.class);

    // no cx_id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setHeader("CharacterSet", "utf-8");

        String url = req.getRequestURL().toString();
        logger.info("url = " + url);

        // receive parameter & decide to enter into service
        String requestData = urlParser.getRequestData(req.getInputStream());
        if (requestData == null || "".equals(requestData)) {
            return;
        }
        // receive get url data
        logger.info("request data = " + requestData);
        String method = req.getMethod();
        String requestUrl = urlParser.parse(url, method);
        String result = serviceChooseUtil.chooseService(requestUrl, req, resp, requestData);

        // socket connection fail
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
        if (result == null || result.equals("")) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            logger.info("response data = " + result);
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            out.write(result);
        }
    }

    @Override
    public void destroy() {
        /*
        * SessionFactoryUtil.close(); FileOutputStream fos = null; try { // fos = new
        * FileOutputStream("d://cx/imageId.txt", true); // String str =
        * StringUtil.convertDateToString(new Date()); // fos.write(("\r\n" + str +
        * "-------------servlet has been destroyed ").getBytes()); } catch (Exception e) {
        * e.printStackTrace(); } finally { try { fos.close(); } catch (IOException e) {
        * e.printStackTrace(); } }
        */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
