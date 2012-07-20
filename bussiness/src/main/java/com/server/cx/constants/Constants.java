package com.server.cx.constants;

public class Constants {
	
	public static final String PREFIX_STR = "/cx/1.0/";
	
	public static final String CX_INFO_STR = "cxinfo";
	public static final String PHONE_NO_STR = "phoneNo";
	public static final String TIME_STAMP_STR = "timeStamp";
	
	public static final String IMAGE_ID_STR = "cx_imageId";
	public static final String CXINFO_PHONE_NO_STR = "cx_phoneNo";
	
	//服务器接口地址 being
	public static final String REGISTER_URL = "registration";
	public static final String CALLING_USERCXINFO_URL = "registration/call";

	public static final String GET_USERCXINFO_URL = "registration/lookup/cx"; 
	public static final String GET_SIGNATURES_URL = "registration/get/signatures"; 
	
	public static final String ATTENTION_URL = "registration/attention"; 
	public static final String PHONEBOOK_UPLOAD_URL = "registration/phonebook/upload"; 
	public static final String PHONEBOOK_DOWNLOAD_URL = "registration/phonebook/download"; 
	public static final String CX_CLIENT_ABOUT_URL = "registration/about"; 
	public static final String UPDATE_CLIENT_URL = "upgrade";
	public static final String DOWNLOAD_CLIENT_URL = "download";
	
	public static final String INVITE_FRIENDS_URL = "inviteFriends"; 
	public static final String GET_ALL_CXINFODATA= "browserall";
	public static final String DELETE_USERCXINFO = "deleteUserCXInfo";
	
	//收藏接口相关的URL
	public static final String ADD_NEW_USER_FAVORITES = "addNewUserFavorites";
	public static final String DELETE_USER_FAVORITES = "deleteUserFavorites";
	public static final String BROSWER_USER_FAVORITES = "browserUserFavorites";
	public static final String ADD_SHORT_PHONENO = "addNewShortPhoneNO";
	public static final String RETRIVE_USER_SHORTPHONENOS = "retriveUserShortPhoneNos";
	
	//状态设置相关的URL
	public static final String ADD_NEW_USERSTATUS_USERCXINFO = "addNewUserStatus";
	public static final String RETRIVE_USERSTATUS_USERCXINFO_BY_STATUS ="retriveUserStatusUserCXInfo";
	public static final String GET_CURRENT_USERSTATUS="getCurrentUserStatus";
	public static final String DELETE_CURRENTUSER_STATUS = "deleteCurrentUserStatus";
	
	//服务器接口地址 end
	public static final String TYPE_ID = "typeId";
	public static final int DEFAULT_SIZE = 10;
	public static final String REQUEST_PAGE = "requestPage";
	public static final String REQUEST_PAGE_SIZE = "requestPageSize";

	public static final String TYPE_3GP = "3gp";
	public static final String TYPE_GIF = "gif";
	
	public static final String FLAG_STR = "flag";
	public static final String TYPE = "type";
	public static final String CONTACT_STR = "Contact";
    public static final String IMSI_STR = "imsi";
    public static final String CXINFO_ID = "cxInfoId";
    public static final String USER_FAVORITES_ID = "userFavoritesId";
    public static final String ID_STR="id";
    public static final String SHORTPHONE_NOS = "shortPhoneNos";
    
	public static final String BASE_FILE_STORE_PATH = "files";
	
	//彩像接口通信交互标记位:
	public static final String SUCCESS_FLAG="SUCCESS";
	public static final String ERROR_FLAG="ERROR";
	public static final String LATEST_DATA_FLAG="LATEST_DATA";
	public static final String DATA_NOTFOUND_FLAG="NODATA_FOUND";
	public static final String NULL_INPUT_FLAG="NULL_INPUT";
	public static final String USER_DATA_ERROR_FLAG="USER_DATA_ERROR";
	public static final String USER_REGISTERED_FLAG="USER_REGISTERED";
	
	public static final String APP_IS_NEWEST = "APP_IS_UP_TO_DATE";
	public static final String SERVER_HAVE_NEWVERION= "SERVER_HAVE_NEWVERION"; 
	
	
	//异常定义文件
	public static final String SERVER_RUNTIME_DATA_STORE_ERROR="server.runtime.exception.datastore";
	public static final String SERVER_RUNTIME_DATA_UPDATE_ERROR="server.runtime.exception.dataupdate";
	public static final String SERVER_RUNTIME_DATA_DELETE_ERROR="server.runtime.exception.datadelete";
	public static final String SERVER_RUNTIME_DATA_QUERY_ERROR="server.runtime.exception.dataquery";
    
	//短信发送号码
	public static final String FROMMOBILE = "13312123112";
	//短信内容
	public static final String SMS_CONTENT="你的好友(?)正在使用手机彩像服务" +
	                                      "非常好玩，可以定制个性的通话图像，" +
			                              "只需一秒钟就可以，一定要推荐给您!";
    public static final Integer TOTAL_USERFAVORITES_COUNT = 20;
    
    //模式类型
    public static final int COMMON_MODE = 1;
    public static final int TIME_SPAN_MODE = 2;
    public static final int SPECIAL_PHONENO_MODE = 3;
    public static final int STATUS_MODE = 4;
    public static final int COMMON_MODE_MAX_COUNT = 1;
    public static final int TIME_SPAN_MODE_MAX_COUNT = 3;
    public static final int SPECIAL_PHONENO_MODE_MAX_COUNT=5;
    public static final int STATUS_MODE_MAX_COUNT=5;
    
    //缩略图的宽度和高度
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final String VALIDTIME_STR = "validTime";
    public static final String SIGNATURE_STR = "signature";
}
