package com.cl.cx.platform.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class CXCoinAccountDTO {
    private String name;
    private String password;
    private Double coin;
}
