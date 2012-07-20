package com.server.cx.thumbnail;

import com.google.common.base.CharMatcher;
import com.google.common.io.Files;
import com.server.cx.constants.Constants;
import com.server.cx.util.FileUtil;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图像数据的缩略策略类,负责缩略jpg,jpeg,bmp,png文件.
 */
public class ImageThumbnailStrategy implements ThumbnailStrategy {

    private String storeDirectory ;
    private File sourceFile = null;
    @Override
    public String generate(String filename) {
        sourceFile = new File(filename);
        CharMatcher matcher = CharMatcher.is('.');
        int index = matcher.lastIndexIn(filename);
        String suffix = filename.substring(index+1).toLowerCase();
       
        try {
            File thumbnailFile = File.createTempFile(FileUtil.generateUniqueFileName(), "."+suffix, new File(this.storeDirectory));
            Dimension d = this.getSize(filename);
            int w = (int)d.getWidth();
            int h = (int)d.getHeight();
            int width = Constants.WIDTH;
            int height = Constants.HEIGHT;
            if (w <= width && h <= height) {
                Files.copy(new File(filename), new File(this.storeDirectory+File.separator+thumbnailFile.getName()));
            } else {
                //同比缩放
                if (w > width || h > height) {
                    if(w * height > h * width){
                        height = h * width / w;
                    }else{
                        width = w * height / h;
                    }
                } else {
                    width = w;
                    height = h;
                }
            }
            
            
            Thumbnails.of(sourceFile).size(width,height).outputFormat(suffix).toFile(thumbnailFile);
            return thumbnailFile.getName();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    private Dimension getSize(String filename) {
        // TODO Auto-generated method stub
        try {
            BufferedImage image =  ImageIO.read(new File(filename));
            return new Dimension(image.getWidth(), image.getHeight());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setStoreDirectory(String storeDirectory) {
        this.storeDirectory = storeDirectory;
    }

    @Override
    public long getSize() {
        
        return 0;
    }

    @Override
    public void setSourceFileSize(Long size) {
        
    }

}
