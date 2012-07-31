package com.server.cx.service.cx.impl;

import com.server.cx.dto.ActionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:41
 * FileName:BasicService
 */
public class BasicService {
    @Autowired
    @Qualifier("baseHostAddress")
    protected   String baseHostAddress;

    @Autowired
    @Qualifier("restURL")
    protected String restURL;

    @Autowired
    @Qualifier("imageShowURL")
    protected String imageShowURL;

    @Autowired
    @Qualifier("thumbnailSize")
    protected String thumbnailSize;

    @Autowired
    protected ActionBuilder actionBuilder;
}
