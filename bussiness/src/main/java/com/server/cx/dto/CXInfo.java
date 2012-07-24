package com.server.cx.dto;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "signature", "name", "type", "price", "fileName", "fileType", "fileData", "resourceId","count","deadline"})
public class CXInfo {
  private String id;
  private String signature;
  private String name;
  private String type;
  private Double price;
  private String fileName;
  private String fileType;
  private String fileData;
  private String resourceId;
  private Integer count;
  private Long deadline;

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFileData() {
    return fileData;
  }

  public void setFileData(String fileData) {
    this.fileData = fileData;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Long getDeadline() {
    return deadline;
  }

  public void setDeadline(Long deadline) {
    this.deadline = deadline;
  }
  
}
