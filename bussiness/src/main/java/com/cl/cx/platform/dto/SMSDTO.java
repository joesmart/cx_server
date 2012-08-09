package com.cl.cx.platform.dto;

import lombok.Data;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 上午10:22
 * FileName:SMSDTO
 */
@Data
public class SMSDTO {
    private String content;
    private List<String> phoneNos;
}
