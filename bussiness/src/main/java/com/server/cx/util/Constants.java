package com.server.cx.util;

public class Constants {
	public static final String CURRENT_CATEGORY_ID = "current_category_id";
	public static final int STATE_FREE = 0;
	public static final int STATE_BUSY = 1;
	public static final int MAX_FAVORITE_SIZE = 10;
	
	public static class STRING {
		public static final String TABLE_NOT_FOUND = "table not found";
		public static final String TABLE_HAS_USED = "table has been used";
		public static final String TABLE_STATE_ALEADY_FREE = "table state has aleady free";
		public static final String NOT_FOUND_DISHBYID = "can not found dish by dish parameter";
		public static final String NOT_FOUND_DISH = "can not found dish";
		public static final String NOT_FOUND_CATEGORY = "can not found category";
		public static final String NOT_FOUND_TASTE = "can not found taste";
		public static final String NOT_FOUND_ORDER_RECORD = "can not found order record";
	}
	
	public final static String CATEGORY_LIST = "categoryList";
	public final static String DISH_CATEGORY = "dishCategory";
	public static final int SESSION_MAX_TIME = 60 * 60;
}
