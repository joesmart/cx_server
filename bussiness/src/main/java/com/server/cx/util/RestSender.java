package com.server.cx.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.server.cx.config.JAXBContextResolver;
import com.server.cx.dto.CXInfo;
import com.server.cx.dto.Result;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class RestSender {
    private ClientConfig cc;
    private Client client;
    private WebResource webResource;

    public RestSender(String url) {
        cc = new DefaultClientConfig();
        cc.getClasses().add(JAXBContextResolver.class);
        client = Client.create();
        webResource = client.resource(url);
    }

    public List<CXInfo> getStatusCXInfos() {
        Result result = webResource.accept(MediaType.APPLICATION_XML).get(Result.class);
        return result.getCxinfos();
    }

    public Map<String, CXInfo> getCXInfoMap(List<String> idList) {
        String ids = Joiner.on(":").join(idList);
        Result result = webResource.queryParam("ids", ids).accept(MediaType.APPLICATION_XML).get(Result.class);
        return result.getCxinfoMap();
    }

    public CXInfo uploadCXInfo(InputStream inputStream, CXInfo cxInfo, String imsi, Long fileLength) {
        FormDataMultiPart part = createUploadCXInfoData(inputStream, cxInfo, imsi, fileLength);
        Result result =
                webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_XML).post(Result.class, part);
        if (result.getCxinfos() != null && result.getCxinfos().size() > 0) {
            return result.getCxinfos().get(0);
        }
        return null;
    }

    private FormDataMultiPart createUploadCXInfoData(InputStream inputStream, CXInfo cxInfo, String imsi, Long fileLength) {
        FormDataMultiPart part = new FormDataMultiPart();

        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("file").build(), inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if (!Strings.isNullOrEmpty(cxInfo.getName()))
            part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("name").build(), cxInfo.getName()));
        if (!Strings.isNullOrEmpty(cxInfo.getSignature()))
            part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("signature").build(), cxInfo.getSignature()));
        if (!Strings.isNullOrEmpty(cxInfo.getFileName()))
            part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("fileName").build(), cxInfo.getFileName()));
        if (!Strings.isNullOrEmpty(cxInfo.getFileType()))
            part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("fileType").build(), cxInfo.getFileType()));
        if (!Strings.isNullOrEmpty(imsi))
            part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("imsi").build(), imsi));
        String length = String.valueOf(fileLength);
        if (!Strings.isNullOrEmpty(length))
            part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("fileLength").build(), length));
        String special = String.valueOf(false);
        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("special").build(), special));
        String recommended = String.valueOf(false);
        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("recommended").build(), recommended));
        String deadline = "0";
        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("deadline").build(), deadline));
        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("type").build(), "3"));
        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("graphicTypeId").build(), ""));
        part.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("price").build(), "0"));
        return part;
    }
}
