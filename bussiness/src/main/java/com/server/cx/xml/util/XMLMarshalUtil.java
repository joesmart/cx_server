package com.server.cx.xml.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLMarshalUtil {

    private JAXBContext jaxbContext;
    private Marshaller marshaller;
    private Object object;

    public XMLMarshalUtil(Object covertObject){
        try {
            object = covertObject;
            jaxbContext = JAXBContext.newInstance(covertObject.getClass());
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    public XMLMarshalUtil(Class<?> clazz){
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    public String writeOut(Object o){
       String result = "";
       this.object = o;
       result = writeOut();
       return result;
    }
    
    public String writeOut(){
        ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream(4096);
        String result = "";
        try {
            marshaller.marshal(object, byteOutPut);
            result = byteOutPut.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }finally{
            try {
                byteOutPut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public void setJaxbContext(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;
    }


    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }


    public void setObject(Object object) {
        this.object = object;
    }
    
}
