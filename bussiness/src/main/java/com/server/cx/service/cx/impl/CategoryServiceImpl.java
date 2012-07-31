package com.server.cx.service.cx.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.CategoryDao;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dto.Action;
import com.server.cx.dto.CategoryItem;
import com.server.cx.dto.DataPage;
import com.server.cx.entity.cx.Category;
import com.server.cx.service.cx.CategoryService;
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

    @Override
    @Transactional(readOnly = true)
    public DataPage queryAllCategoryData(final String imsi) {
        List<Category> categoryList = Lists.newArrayList(categoryDao.findAll());
        final String baseHref = baseHostAddress + restURL + imsi + "/categories";
        List<CategoryItem> categoryItemList = Lists.transform(categoryList, new Function<Category, CategoryItem>() {
            @Override
            public CategoryItem apply(@javax.annotation.Nullable Category input) {
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.setName(input.getName());
                categoryItem.setDescription(input.getDescription());
                categoryItem.setDownloadNumber(String.valueOf(input.getDownloadNum()));
                categoryItem.setHref(baseHostAddress + restURL + imsi + "/categories/" + input.getId());
                Action action = actionBuilder.buildCategoriesAction(imsi,input.getId());
                categoryItem.setAction(action);
                return categoryItem;
            }
        });

        DataPage dataPage = new DataPage();
        dataPage.setOffset(0);
        dataPage.setLimit(categoryItemList.size());
        dataPage.setTotal(1);
        dataPage.setHref(baseHref);
        dataPage.setItems(categoryItemList);
        return dataPage;
    }


}
