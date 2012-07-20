package com.server.cx.util;


public class DataUtil {
	private static DataUtil instance;
	public static Object object = new Object();

	public static DataUtil getInstance() {
		if (instance == null) {
			instance = new DataUtil();
		}
		return instance;
	}

	public static void addToLogFile(String msg){
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream("d://cx/imageId.txt", true);
//			String message = "\r\n" + new Date().toLocaleString() + "-----------" + msg;
//			fos.write(message.getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				fos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
	
//	public String addBody(String result){
//		return 
//	}
	
	public static boolean checkNull(String s) {
		if (s == null || s.equals("") || s.equals("null")) {
			return true;
		}
		return false;
	}
}
