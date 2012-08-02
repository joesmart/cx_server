package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:10
 * FileName:CategoryService
 */
public interface CategoryService {
    public abstract DataPage queryAllCategoryData(String imsi);
}
