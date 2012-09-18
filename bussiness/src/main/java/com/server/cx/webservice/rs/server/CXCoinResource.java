package com.server.cx.webservice.rs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.CXCoinService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;

@Component
@Path("/{imsi}/cxCoin")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CXCoinResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CXCoinResource.class);
    @Autowired
    private CXCoinService cxCoinService;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Autowired
    private ActionBuilder actionBuilder;

    @Path("account")
    @GET
    public Response getCXCoinAccount(@PathParam("imsi") String imsi) {
        LOGGER.info("Into getCXCoinAccount imsi = " + imsi);
        try {
            ValidationUtil.checkParametersNotNull(imsi);
            CXCoinAccount cxCoinAccount = cxCoinService.getCXCoinAccount(imsi);
            CXCoinAccountDTO cxCoinAccountDTO = businessFunctions.cxCoinAccountToCXCoinAccountDTO()
                .apply(cxCoinAccount);
            return Response.ok(cxCoinAccountDTO).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getCXCoinAccount", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("register")
    @POST
    public Response register(@PathParam("imsi") String imsi, CXCoinAccountDTO coinAccountDTO) {
        LOGGER.info("Into getCXCoinAccount imsi = " + imsi);
        LOGGER.info("Into getCXCoinAccount coinAccountDTO = " + coinAccountDTO);
        try {
            ValidationUtil.checkParametersNotNull(imsi, coinAccountDTO.getName(), coinAccountDTO.getPassword());
            OperationDescription operationDescription = cxCoinService.register(imsi, coinAccountDTO);
            operationDescription.setActions(actionBuilder.buildCXCoinRegisteredURL(imsi));
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "register", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("login")
    @POST
    public Response login(@PathParam("imsi") String imsi, CXCoinAccountDTO coinAccountDTO) {
        LOGGER.info("Into getCXCoinAccount imsi = " + imsi);
        LOGGER.info("Into getCXCoinAccount coinAccountDTO = " + coinAccountDTO);
        try {
            ValidationUtil.checkParametersNotNull(imsi, coinAccountDTO.getName(), coinAccountDTO.getPassword());
            OperationDescription operationDescription = cxCoinService.login(imsi, coinAccountDTO);
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "register", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("records")
    @GET
    public Response getUserCXCoinRecords(@PathParam("imsi") String imsi,
                                         @DefaultValue("0") @QueryParam("offset") Integer offset,
                                         @DefaultValue("20") @QueryParam("limit") Integer limit,
                                         @QueryParam("name") String name, @QueryParam("password") String password) {
        LOGGER.info("Into getCXCoinAccount imsi = " + imsi);
        LOGGER.info("offset = " + offset);
        LOGGER.info("limit = " + limit);
        LOGGER.info("name = " + name);
        LOGGER.info("password = " + password);
        try {
            ValidationUtil.checkParametersNotNull(imsi, name, password);
            DataPage dataPage = cxCoinService.getUserCXCoinRecords(name, password, imsi, offset, limit);
            return Response.ok(dataPage).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "getUserSubscribeRecords", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("consume")
    @PUT
    public Response consumeCXCoin(@PathParam("imsi") String imsi, CXCoinAccountDTO coinAccountDTO) {
        LOGGER.info("Into getCXCoinAccount imsi = " + imsi);
        LOGGER.info("Into getCXCoinAccount coinAccountDTO = " + coinAccountDTO);
        try {
            ValidationUtil.checkParametersNotNull(imsi, coinAccountDTO.getName(), coinAccountDTO.getPassword());
            CXCoinAccount cxCoinAccount = cxCoinService.consumeCXCoin(imsi, coinAccountDTO);
            CXCoinAccountDTO cxCoinAccountDTO = businessFunctions.cxCoinAccountToCXCoinAccountDTO()
                .apply(cxCoinAccount);
            return Response.ok(cxCoinAccountDTO).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "register", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("purchase")
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public String purchaseCXCoin(@PathParam("imsi") String imsi, @Context HttpServletRequest request,
                                 @Context HttpServletResponse response) throws IOException {
        LOGGER.info("Into purchaseCXCoin imsi = " + imsi);
        Map map = request.getParameterMap();
        String sign = (String) ((Object[]) map.get("sign"))[0];
        String verifyData = getVerifyData(map);
        LOGGER.info("sign = " + sign);
        LOGGER.info("verifyData = " + verifyData);
        boolean verified = false;
        return "success";

        //        try {
        //            verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        PrintWriter out = response.getWriter();
        //        if (verified) {
        //            out.print("success");
        //        } else {
        //            System.out.println("����֧����ϵͳ֪ͨ��֤ǩ��ʧ�ܣ����飡");
        //            out.print("fail");
        //        }
    }

    @SuppressWarnings("unchecked")
    private String getVerifyData(Map map) {
        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
        return "notify_data=" + notify_data;
    }

    public String getResponseMessage(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
