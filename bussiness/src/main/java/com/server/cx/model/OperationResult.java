package com.server.cx.model;

import com.cl.cx.platform.dto.Actions;
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
    private Actions actions;
    public OperationResult(String name,String dealResult){
        this.name = name;
        this.dealResult = dealResult;
    }

    public OperationResult(String name,String dealResult,Actions actions){
        this.name = name;
        this.dealResult = dealResult;
        this.actions = actions;
    }
}
