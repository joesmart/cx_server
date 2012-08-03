package com.cl.cx.platform.dto;

import lombok.Data;

import java.util.List;

@Data
public class UploadContactDTO {
    private List<ContactPeopleInfoDTO> contactPeopleInfos;
    private String imsi;
    private String flag;
    private String content;
}
