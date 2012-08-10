package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:41
 * FileName:MGraphicDTO
 */
@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class MGraphicDTO {
    private String id;
    private String graphicInfoId;
    private String name;
    private String signature;
    private List<String> phoneNos;
    private Date begin;
    private Date end;
    private Integer modeType;
}
