package com.server.cx.xml.adapter;

import com.server.cx.entity.cx.Signature;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SignatureXMLAdapter extends XmlAdapter<String, Signature> {
    
    @Override
    public Signature unmarshal(String v) throws Exception {
        Signature signature = new Signature();
        signature.setContent(v);
        return signature;
    }

    @Override
    public String marshal(Signature v) throws Exception {
        return v.getContent();
    }

}
