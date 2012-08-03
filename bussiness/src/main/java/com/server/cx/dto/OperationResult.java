package com.server.cx.dto;

import lombok.Data;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:51
 * FileName:OperationResult
 */
@Data
public class OperationResult {
    private String name;
    private String dealResult;
    public OperationResult(String name,String dealResult){
        this.name = name;
        this.dealResult = dealResult;
    }
}
