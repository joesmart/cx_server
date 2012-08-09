package com.server.cx.webservice.rs.server;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dto.Result;
import com.server.cx.dto.UserCXInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.service.cx.UserCXInfoManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/status")
public class StatusUserCXInfoResources {

    @Autowired
    private UserCXInfoManagerService userCXInfoManagerService;
    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response add(Result result) {
        Result dealResult = new Result();
        try {
            UserCXInfo userCXInfo = result.getUserCXInfos().get(0);
            UserInfo userinfo = userInfoDao.getUserInfoByImsi(userCXInfo.getImsi());
            //TODO need fix here since the UserInfo Id change to String typ By Joesmart
            //userCommonMGraphicDao.deleteUserAllStatus(userinfo.getId());
            String xmlString = userCXInfoManagerService.dealWithUserCXInfoAdding(result);
            return Response.ok(xmlString).build();
        } catch (Exception e) {
            dealResult = new Result();
            dealResult.setFlag(Constants.ERROR_FLAG);
            dealResult.setContent(e.getMessage());
            return Response.ok(dealResult).build();
        }
    }

}
