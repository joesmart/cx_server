package com.cl.cx.platform.dto;

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
public class MGraphicDTO {
    private String id;
    private String graphicInfoId;
    private String name;
    private String signature;
    private List<String> phoneNos;
    private Date begin;
    private Date end;
}
