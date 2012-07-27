package com.server.cx.util;

import org.dom4j.Element;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Blob;

// bean --> xml
public class XMLReflector {
    @SuppressWarnings("rawtypes")
    private Class sourceClass;
    private BeanInfo beanInfo;
    private String name;
    private String result = "";

    public void setHeader(String head) {
        result = head;
    }

    @SuppressWarnings("rawtypes")
    public XMLReflector(Class sourceClass, String name) throws Exception {
        this.sourceClass = sourceClass;
        this.name = name;
        beanInfo = Introspector.getBeanInfo(sourceClass);
        int len = beanInfo.getPropertyDescriptors().length;
        System.out.println(len);
    }


    @SuppressWarnings("unused")
    private String getProp(Object o, PropertyDescriptor pd, Element cxinfo) throws Exception {
        StringBuffer propValue = new StringBuffer("");
        Method m = pd.getReadMethod();
        Element node = null;
        if (checkMethod(m)) {
            Object ret = m.invoke(o);
            if (null == ret) {
                // propValue.append("<" + pd.getName() + "/>");
                cxinfo.addElement(pd.getName());
            } else {
                // propValue.append("<" + pd.getName() + ">");
                String s = pd.getName();
                System.out.println("pd name is  = " + pd.getName());
                node = cxinfo.addElement(pd.getName());
                if (m.getName().equals("getImage")) {
                    node.setText(convertBlobToString(ret));
                    // propValue.append(convertBlobToString(ret));
                } else {
                    if (!checkNull(ret)) {
                        // propValue.append(ret.toString());
                        node.setText(ret.toString());
                    }
                }
                // propValue.append("</" + pd.getName() + ">");
            }
            // return propValue.toString();
        }
        return propValue.toString();
    }

    private boolean checkMethod(Method m) {
        System.out.println(m.getName());
        String[] methods = {"getClass", "getUserInfoId", "getImsi", "getAddTime"};
        for (int i = 0; i < methods.length; i++) {
            if (m.getName().equals(methods[i])) {
                return false;
            }
        }
        return true;
    }

    private String convertBlobToString(Object ret) {
        if (ret instanceof Blob) {
            Blob blob = (Blob) ret;
            try {
                // System.out.println("blob size = " +
                // blob.getBinaryStream().available());
                return convertStreamToString(blob.getBinaryStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";

    }

    private String convertStreamToString(InputStream is) {
        byte[] b;
        try {
            b = new byte[is.available()];
            is.read(b);
            return Base64Encoder.encodeToString(b, Base64Encoder.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkNull(Object ret) {
        if (ret == null || ret.equals("") || ret.equals("null")) {
            return true;
        }
        return false;
    }
}
