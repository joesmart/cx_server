package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 上午10:22
 * FileName:SMSDTO
 */
@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class SMSDTO {
    private String content;
    private List<String> phoneNos;
}
