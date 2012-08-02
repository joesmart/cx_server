package com.server.cx.webservice.rs.server;

import com.server.cx.entity.account.User;
import com.server.cx.service.account.AccountManager;
import com.server.cx.webservice.WsConstants;
import com.server.cx.webservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.mapper.BeanMapper;
import org.springside.modules.rest.RsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * User资源的REST服务.
 *
 * @author calvin
 */
@Path("/users")
public class UserResouceService {

    private AccountManager accountManager;

    @Context
    private UriInfo uriInfo;

    /**
     * 获取用户信息.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + WsConstants.CHARSET})
    public UserDTO getUser(@PathParam("id") Long id) {
        try {
            User entity = accountManager.getUser(id);

            if (entity == null) {
                String message = "用户不存在(id:" + id + ")";
                throw RsResponse.buildException(Status.NOT_FOUND, message);
            }

            return BeanMapper.map(entity, UserDTO.class);
        } catch (RuntimeException e) {
            throw RsResponse.buildDefaultException(e);
        }
    }

    @GET
    @Path("/name/{name}")
    public String test2(@PathParam("name") String name) {
        System.out.println("name = " + name);
        return "hello world";
    }

    /**
     * 查询用户, 请求数据为URL中的请求参数, 返回用户列表.
     */
    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + WsConstants.CHARSET})
    public List<UserDTO> searchUser(@QueryParam("loginName") String loginName, @QueryParam("name") String name) {
        /*try {
              List<User> entityList = accountManager.searchUser(loginName, name);

              return BeanMapper.mapList(entityList, UserDTO.class);
          } catch (RuntimeException e) {
              throw RsResponse.buildDefaultException(e);
          }*/
        return null;
    }

    /**
     * 创建用户, 请求数据为POST过来的JSON/XML格式编码的DTO, 返回表示所创建用户的URI.
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + WsConstants.CHARSET})
    public Response createUser(UserDTO user) {
        //转换并创建用户
        /*try {
              User userEntity = BeanMapper.map(user, User.class);

              Long id = accountManager.saveUser(userEntity);

              URI createdUri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
              return Response.created(createdUri).build();
          } catch (ConstraintViolationException e) {
              String message = StringUtils.join(BeanValidators.extractPropertyAndMessage(e), "\n");
              throw RsResponse.buildException(Status.BAD_REQUEST, message);
          } catch (DataIntegrityViolationException e) {
              String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
              throw RsResponse.buildException(Status.BAD_REQUEST, message);
          } catch (RuntimeException e) {
              throw RsResponse.buildDefaultException(e);
          }*/
        return null;
    }

    @Autowired
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
}
