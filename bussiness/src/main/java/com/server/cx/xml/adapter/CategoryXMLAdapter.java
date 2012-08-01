package com.server.cx.xml.adapter;

import com.server.cx.entity.cx.Category;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CategoryXMLAdapter extends XmlAdapter<Long, Category> {


    @Override
    public Category unmarshal(Long v) throws Exception {
        Category category = new Category();
        category.setId(v);
        return category;
    }

    @Override
    public Long marshal(Category v) throws Exception {
        return v.getId();
    }

}
