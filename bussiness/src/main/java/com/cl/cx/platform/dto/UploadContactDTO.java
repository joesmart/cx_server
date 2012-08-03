package com.cl.cx.platform.dto;

import com.server.cx.dto.ContactPeopleInfo;
import lombok.Data;

import java.util.List;

@Data
public class UploadContactDTO {
    private List<ContactPeopleInfo> contactPeopleInfos;
    private String imsi;
    private String flag;
    private String content;
}
