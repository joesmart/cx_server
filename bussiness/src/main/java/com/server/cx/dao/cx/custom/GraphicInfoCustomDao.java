package com.server.cx.dao.cx.custom;

import org.springframework.data.domain.Page;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 上午9:40
 * FileName:GraphicInfoCustomDao
 */
public interface GraphicInfoCustomDao {
    public abstract Page findGraphicInfoByCategoryType(int offset,int limit,Long categoryId);
}
