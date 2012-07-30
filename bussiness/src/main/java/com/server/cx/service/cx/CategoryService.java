package com.server.cx.service.cx;

import com.server.cx.entity.cx.Category;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:10
 * FileName:CategoryService
 */
public interface CategoryService {
    public abstract List<Category> queryAllCategoryData();
}
