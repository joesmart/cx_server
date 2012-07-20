package com.server.cx.xml.util;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import com.server.cx.xml.Result;

public class XMLUnmarshallUtil {

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    
    public XMLUnmarshallUtil(Class<?> c){
        try {
            jaxbContext = JAXBContext.newInstance(c);
            unmarshaller = jaxbContext.createUnmarshaller();
            //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    
    public Result unmarshallXMLFile(File file){
     
        Result xmlResult = null;
     
        try {
            JAXBElement<Result> root = unmarshaller.unmarshal(new StreamSource(file), Result.class);
            xmlResult = root.getValue();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return xmlResult;
    }
 
    public Result unmarshallXMLFileResult(InputStream input){
        Result xmlResult = null;
        
        try {
            JAXBElement<Result> root = unmarshaller.unmarshal(new StreamSource(input), Result.class);
            xmlResult = root.getValue();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return xmlResult;
    }
    
}
