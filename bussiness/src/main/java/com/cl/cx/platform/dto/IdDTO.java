package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 下午1:52
 * FileName:IdDTO
 */
@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class IdDTO {
    public IdDTO(){
        ids = Lists.newArrayList();
    }
    private String id;
    private List<String> ids;
}
