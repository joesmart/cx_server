package com.server.cx.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.server.cx.constants.Constants;
import com.server.cx.thumbnail.ImageThumbnailStrategy;
import com.server.cx.thumbnail.ThumbnailStrategy;

public class FileUtil {
    
    private Map<String,ThumbnailStrategy> thumbnailMap;

    private String targetFileDirectory;
    private String thumbnailFileDirectory;

    private String baseServerFileURL;
    

    public FileUtil(){
        thumbnailMap = Maps.newHashMap();
        ThumbnailStrategy imageStrategy = new ImageThumbnailStrategy();
        //ThumbnailStrategy gifStrategy = new GifThumbnailStrategy();
       // ThumbnailStrategy videoStrategy = new VideoThumbnailStrategy();
        
      //  thumbnailMap.put("3gp", videoStrategy);
      //  thumbnailMap.put("mp4", videoStrategy);
        
        thumbnailMap.put("jpg", imageStrategy);
        thumbnailMap.put("png", imageStrategy);
        thumbnailMap.put("bmp", imageStrategy);
        thumbnailMap.put("jpeg", imageStrategy);
        thumbnailMap.put("gif", imageStrategy);
        baseServerFileURL = "/CXServer/"+Constants.BASE_FILE_STORE_PATH+"/";
    }
    

    //TODO 一个方法做了两件事情,需要 refactor!!! :joesmart
    public Map<String,String>  storeCXFileData(String fileName,final String fileType,String fileData,String serverPath){

        Preconditions.checkNotNull(fileType);

        Map<String,String> resultMap = new HashMap<String,String>();
        String storePath = serverPath+Constants.BASE_FILE_STORE_PATH+File.separator+fileType+File.separator;
       
        createAllDirectory(storePath);
        
        try {
            File directoryFile = new File(this.targetFileDirectory);
            File targetFile = File.createTempFile(generateUniqueFileName(), "."+fileType, directoryFile);
            byte[] bytes = Base64Encoder.decode(fileData, Base64Encoder.DEFAULT);
            Files.write(bytes, targetFile);
            String newFileName = targetFile.getName();
            
            if(newFileName != null && !"".equals(newFileName)){
                resultMap.put("filePath", baseServerFileURL + fileType + "/" + newFileName);
            }
            
            ThumbnailStrategy strategy = this.thumbnailMap.get(fileType.toLowerCase());
            if(strategy != null){
                strategy.setStoreDirectory(this.thumbnailFileDirectory);
                strategy.setSourceFileSize(targetFile.length());
                String thumbnailFileName = strategy.generate(targetFile.getPath());
                
                if(thumbnailFileName != null && !"".equals(thumbnailFileName)){
                    resultMap.put("thumbnailFilePath", baseServerFileURL + fileType + "/thumbnail/" + thumbnailFileName);
                }
            }else{
                
                if("3gp".equalsIgnoreCase(fileType) || "mp4".equalsIgnoreCase(fileType)){
                    resultMap.put("thumbnailFilePath", baseServerFileURL + fileType + "/thumbnail/video_background.jpg");
                }
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
	}

    public static String generateUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString();
        CharMatcher dash  = CharMatcher.is('-');
        newFileName = newFileName.substring(dash.lastIndexIn(newFileName)+1);
        return newFileName;
    }
    
    private void createAllDirectory(String directoryPath){
        String filePath = directoryPath;
        
        try {
            File tempTargetFileParentDir = new File(filePath+File.separator+"redme");
            if(!tempTargetFileParentDir.exists()){
                Files.createParentDirs(tempTargetFileParentDir);
            }
            this.targetFileDirectory = tempTargetFileParentDir.getParent();
            
            File tempThumbnailFileParentDir = new File(filePath+File.separator+"thumbnail"+File.separator+"readme");
            if(!tempThumbnailFileParentDir.exists()){
                Files.createParentDirs(tempThumbnailFileParentDir);
            }
            this.thumbnailFileDirectory = tempThumbnailFileParentDir.getParent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
