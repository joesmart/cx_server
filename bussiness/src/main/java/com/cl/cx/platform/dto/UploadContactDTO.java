package com.cl.cx.platform.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class UploadContactDTO {
    private String imsi;
    private String flag;
    private String content;
    private List<ContactPeopleInfoDTO> contactPeopleInfos = new ArrayList<ContactPeopleInfoDTO>();
}
