package com.server.cx.service.cx.impl;

import com.google.common.io.ByteStreams;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.FileMeta;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.UserCommonMGraphicService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.business.JerseyResourceUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class UserCommonMGraphicServiceImpl implements UserCommonMGraphicService {
    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;
    
    @Autowired
    @Qualifier("fileUploadUrl")
    private String fileUploadUrl;

    @Transactional(readOnly=false)
    @Override
    public void addFileStreamToResourceServer(String imsi, InputStream fileStream) throws IOException {
        FileMeta fileMeta = uploadToResourceServer(imsi, fileStream);
        GraphicInfo graphicInfo = businessFunctions.fileMetaTransformToGraphicInfo().apply(fileMeta);
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        if(userInfo !=null){
            graphicInfo.setOwner(userInfo.getId());
            UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
            userCommonMGraphic.setUserInfo(userInfo);
            userCommonMGraphic.setGraphicInfo(graphicInfo);
            userCommonMGraphic.setCommon(true); //没有设置使用对象
            userCommonMGraphicDao.save(userCommonMGraphic);
        }
    }


    private FileMeta uploadToResourceServer(String imsi, InputStream stream) throws IOException {
        FormDataMultiPart mp = new FormDataMultiPart();
        byte[] arrays = ByteStreams.toByteArray(stream);
        long fileSize = arrays.length;
        if(fileSize <=1){
            throw new CXServerBusinessException("上传到数据流为空!");
        }
        String fileName = imsi+ new Date().getTime();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(arrays);
        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file")
                .fileName(fileName).size(fileSize).build(),inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        mp.bodyPart(inputStreamBody);
        JerseyResourceUtil.getClient().addFilter(
                new HTTPBasicAuthFilter(Constants.RESOURCE_SERVER_USERNAME, Constants.RESOURCE_SERVER_PASSWORD));
        WebResource webResource = JerseyResourceUtil.getClient().resource(fileUploadUrl);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, mp);
        GenericType<List<FileMeta>> genericType = new GenericType<List<FileMeta>>() {};
        List<FileMeta> imageResourceList = response.getEntity(genericType);
        if(imageResourceList != null && !imageResourceList.isEmpty()) {
            return imageResourceList.get(0);
        }
        return null;
    }

}
