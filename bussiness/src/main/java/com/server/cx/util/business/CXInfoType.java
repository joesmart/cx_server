package com.server.cx.util.business;

public enum CXInfoType {

    PICTURE("picture","1"),
    VIDEO("video","2"),
    AMINATION("Animation","3"),
    UNKNOW("unknow","4");
    
    private final String name;
    private final String value;
    
    CXInfoType(String name,String value){
        this.name = name;
        this.value = value;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public CXInfoType getType(String fileExtentionType){
        if("gif".equalsIgnoreCase(fileExtentionType)){
            return CXInfoType.AMINATION;
        }
        if("jpg".equalsIgnoreCase(fileExtentionType)
            ||"jpeg".equalsIgnoreCase(fileExtentionType)
            ||"png".equalsIgnoreCase(fileExtentionType)
            ||"bmp".equalsIgnoreCase(fileExtentionType)){
            return CXInfoType.PICTURE;
        }
        if("3gp".equalsIgnoreCase(fileExtentionType)||"mp4".equalsIgnoreCase(fileExtentionType)){
            return CXInfoType.VIDEO;
        }
        return null;
    }
}
