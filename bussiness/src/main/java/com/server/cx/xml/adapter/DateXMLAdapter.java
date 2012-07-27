package com.server.cx.xml.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateXMLAdapter extends XmlAdapter<String, Date> {
    String pattern = "yyyy-MM-dd HH:mm:ss.S";

    @Override
    public Date unmarshal(String v) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = formatter.parse(v);
        return date;
    }

    @Override
    public String marshal(Date v) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String date = formatter.format(v);
        return date;
    }


}
