package com.server.cx.xml.adapter;

import com.server.cx.entity.cx.Type;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TypeXMLAdapter extends XmlAdapter<Long, Type> {


    public Type unmarshal(Long v) throws Exception {
        Type type = new Type();
        type.setName("unknow");
        type.setId(v);
        return type;
    }

    @Override
    public Long marshal(Type v) throws Exception {
        return v.getId();
    }

}
