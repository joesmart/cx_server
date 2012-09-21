package com.server.cx.webservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.cl.cx.platform.dto.CXCoinNotfiyDataDTO;
import com.server.cx.entity.cx.CXCoinNotfiyData;
import com.server.cx.service.cx.CXCoinService;
import com.server.cx.service.util.BusinessFunctions;

public class RSANotifyReceiver extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSANotifyReceiver.class);
    private static final long serialVersionUID = 7216412938937049671L;

    @Autowired
    private BusinessFunctions bussineFunctions;

    @Autowired
    private CXCoinService cxCoinService;

    @SuppressWarnings({"rawtypes"})
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        try {
            LOGGER.info("Into RSANotifyReceiver doPost bussineFunctions = " + bussineFunctions);
            Map map = request.getParameterMap();
            LOGGER.info("map = " + map);
            String sign = (String) ((Object[]) map.get("sign"))[0];
            LOGGER.info("sign = " + sign);
            String verifyData = getVerifyData(map);
            LOGGER.info("verifyData = " + verifyData);
            boolean verified = false;
            try {
                verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC);
            } catch (Exception e) {
                e.printStackTrace();
            }
            handleNotifyData(getNotfiyData(map));
            out = response.getWriter();
            if (verified) {
                out.print("success");
            } else {
                out.print("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (JAXBException e) {
            e.printStackTrace();
            out.print("fail");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void handleNotifyData(String notfiyData) throws JAXBException {
        CXCoinNotfiyData cxCoinNotfiyData = parseNotifyData(notfiyData);
        cxCoinService.handleCXCoinPurchaseCallback(cxCoinNotfiyData);
    }

    private CXCoinNotfiyData parseNotifyData(String notfiyData) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CXCoinNotfiyDataDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CXCoinNotfiyDataDTO cxCoinNotfiyDataDTO = (CXCoinNotfiyDataDTO) unmarshaller.unmarshal(new StringReader(
            notfiyData));
        CXCoinNotfiyData cxCoinNotfiyData = bussineFunctions.transferCXCoinNotfiyDataDTOToCXCoinNotifyData().apply(
            cxCoinNotfiyDataDTO);
        return cxCoinNotfiyData;
    }

    @SuppressWarnings({"rawtypes"})
    private String getNotfiyData(Map map) {
        return (String) ((Object[]) map.get("notify_data"))[0];
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @SuppressWarnings({"rawtypes"})
    private String getVerifyData(Map map) {
        return "notify_data=" + getNotfiyData(map);
    }
}
