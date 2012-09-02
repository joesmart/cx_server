package com.server.cx.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: ZouYanjian
 * Date: 8/31/12
 * Time: 5:40 PM
 * FileName:HashCodeTest
 */
public class HashCodeTest {

    @Test
    public void test() throws NoSuchAlgorithmException, IOException {
//        byte[] filesBytes =Files.readAllBytes(FileSystems.getDefault().getPath("D:\\手机图片整合\\精品图库\\酷炫汽车\\4309002.png"));
        byte[] filesBytes =Files.readAllBytes(FileSystems.getDefault().getPath("D:\\手机图片整合\\节日祝福\\中秋节快乐.jpg"));
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(filesBytes);
        byte[] sha1hash  = md.digest();
        MACUtil macUtil = new MACUtil();
        String abcd = macUtil.convertToHex(sha1hash);
        System.out.println(abcd);
    }
}
