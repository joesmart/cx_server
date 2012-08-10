package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 上午10:58
 * FileName:RegisterDTO
 */
@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class RegisterDTO {
    private String imsi;
}
