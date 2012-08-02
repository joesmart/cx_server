package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.dao.cx.CategoryDao;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.cl.cx.platform.dto.CategoryItem;
import com.cl.cx.platform.dto.DataPage;
import com.server.cx.entity.cx.Category;
import com.server.cx.service.cx.CategoryService;
import com.server.cx.service.util.BusinessFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:19
 * FileName:CategoryServiceImpl
 */
@Service("categoryService")
@Transactional
public class CategoryServiceImpl extends BasicService implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Override
    @Transactional(readOnly = true)
    public DataPage queryAllCategoryData(final String imsi) {
        List<Category> categoryList = Lists.newArrayList(categoryDao.findAll());
        final String baseHref = baseHostAddress + restURL + imsi + "/categories";
        List<CategoryItem> categoryItemList = Lists.transform(categoryList, businessFunctions.categoryTransformToCategoryItem(imsi));

        DataPage dataPage = new DataPage();
        dataPage.setOffset(0);
        dataPage.setLimit(categoryItemList.size());
        dataPage.setTotal(1);
        dataPage.setHref(baseHref);
        dataPage.setItems(categoryItemList);
        return dataPage;
    }


}
