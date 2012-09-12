package com.cl.cx.platform.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class BasicDataItem {
    private String name;
    private Integer price;
    private Integer level;
    private Integer downloadNum;
    private String resourceId;
    private String fileName;
    private String fileType;
    private Boolean auditPassed;
    private String sourcePath;
    private String thumbnailPath;
    private String signature;
    private String description;
    private String specialNo;
    private Integer priority;
    private Integer modelType;
}
