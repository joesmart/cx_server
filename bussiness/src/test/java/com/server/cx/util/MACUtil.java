package com.server.cx.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MACUtil {

	private static String SHAKEY = "1L1TSGICHWW7VNYETYGJT1OKHRTKUNKV";

	private String sha1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    MessageDigest md = MessageDigest.getInstance("SHA-1");
	    md.update(text.getBytes("utf-8"), 0, text.length());
	    byte[] sha1hash = md.digest();
	    return convertToHex(sha1hash);
	}

	public String convertToHex(byte[] data) {
	    StringBuilder buf = new StringBuilder();
	    for (int i = 0; i < data.length; i++) {
	        int halfbyte = (data[i] >>> 4) & 0x0F;
	        int two_halfs = 0;
	        do {
	            if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	                buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
	        } while (two_halfs++ < 1);
	    }
	    return buf.toString().toUpperCase();
	}
	
	
	public String getAppIntReqMac(String functionId,String userIdentify,String deviceIdentify,String reqTime){
		
		String source = concatStrings(functionId,userIdentify,deviceIdentify,reqTime,SHAKEY);
		try {
			return sha1(source);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAppIntResMac(String functionId,String userIdentify,String deviceIdentify,String reqTime,String resTime){
		
		String source = concatStrings(functionId,userIdentify,deviceIdentify,reqTime,resTime,SHAKEY);
		
		try {
			return sha1(source);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String concatStrings(String... strings){
		String concat = "";
		for(String param:strings){
			concat = concat+param;
		}
		
		return concat;
	}

}
