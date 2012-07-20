package com.server.cx.http;

public class HttpConstants {
    public static class URL {
        public static final String WCE_PATH_UPDATE = "/tce/bcs/update";
        public static final String WCE_PATH_REGISTER = "/tce/bcs/3.0/registration";
        public static final String WCE_PATH_CONFIGURATION = "/tce/bcs/3.0/registration/{identity}/configuration";
        public static final String WCE_PATH_EVENTS = "/tce/bcs/3.0/registration/{identity}/events?timeout=240";
        public static final String WCE_PATH_GET_MYPRESENCE = "/tce/bcs/3.0/registration/{identity}/presence";
        public static final String WCE_PATH_SET_MYPRESENCE = "/tce/bcs/3.0/registration/{identity}/presence";
        public static final String WCE_PATH_GET_USERPRESENCE = "/tce/bcs/3.0/registration/{identity}/presence/user/{uri}";
        public static final String WCE_PATH_LOOKUP = "/tce/bcs/3.0/registration/{identity}/directory/lookup";
        public static final String WCE_PATH_SEARCH = "/tce/bcs/3.0/registration/{identity}/directory/search";
        public static final String WCE_PATH_UNREGISTER = "/tce/bcs/3.0/registration/{identity}"; // sign
        // out
        public static final String WCE_PATH_SPECIFIC_CONFIGURATION = "/tce/bcs/3.0/registration/{identity}/configuration/{key}";

        // Group URL
        public static final String WCE_PATH_GET_GROUPS = "/tce/bcs/3.0/registration/{identity}/groups";
        public static final String WCE_PATH_ADD_GROUP = "/tce/bcs/3.0/registration/{identity}/groups";
        public static final String WCE_PATH_RENAME_GROUP = "/tce/bcs/3.0/registration/{identity}/groups/{groupid}";
        public static final String WCE_PATH_REMOVE_GROUP = "/tce/bcs/3.0/registration/{identity}/groups/{groupid}";
        public static final String WCE_PATH_GET_CONTACTS = "/tce/bcs/3.0/registration/{identity}/groups/{groupid}/contacts";
        public static final String WCE_PATH_ADD_CONTACT = "/tce/bcs/3.0/registration/{identity}/groups/{groupid}/contacts";
        public static final String WCE_PATH_REMOVE_CONTACT = "/tce/bcs/3.0/registration/{identity}/groups/{groupid}/contacts/{uri}";

        // Chat URL
        public static final String WCE_PATH_CREATE_CHAT = "/tce/bcs/3.0/registration/{identity}/im/chat";
        public static final String WCE_PATH_ACCEPT_CHAT = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}";
        public static final String WCE_PATH_SEND_MESSAGE = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}/message";
        public static final String WCE_PATH_SEND_COMPOSING = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}";
        public static final String WCE_PATH_END_CHAT = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}";
        public static final String WCE_PATH_CREATE_FILE = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}/filemedia";
        public static final String WCE_PATH_ACCEPT_FILE = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}/filemedia/{fileMediaId}";
        public static final String WCE_PATH_SEND_FILE = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}/filemedia/{fileMediaId}/data";
        public static final String WCE_PATH_END_FILE = "/tce/bcs/3.0/registration/{identity}/im/chat/{chatid}/filemedia/{fileMediaId}";
        public static final String WCE_PATH_Calendar = "/services/calendar";
    }

    public static class Method {
        public static final String WCE_METHOD_GET = "GET";
        public static final String WCE_METHOD_POST = "POST";
        public static final String WCE_METHOD_PUT = "PUT";
        public static final String WCE_METHOD_DELETE = "DELETE";
    }

    public static final String ERROR_NO_NETWORK = "ERROR_NO_NETWORK";
    public static final String ERROR_TIMEOUT = "ERROR_TIMEOUT";
    public static final String ERROR_UNAUTHORIZED = "ERROR_UNAUTHORIZED";
    public static final String ERROR_BAD_REQUEST = "ERROR_BAD_REQUEST";
    public static final String ERROR_FORBIDDEN = "ERROR_FORBIDDEN";
    public static final String ERROR_NOT_FOUND = "ERROR_NOT_FOUND";
    public static final String ERROR_CONFLICT = "ERROR_CONFLICT";
    public static final String ERROR_INTERNAL = "ERROR_INTERNAL";
    public static final String ERROR_INVALID_INPUT = "ERROR_INVALID_INPUT";
    public static final String ERROR_MALFORMED_JSON = "ERROR_MALFORMED_JSON";
    public static final String ERROR_UNKNOWN = "ERROR_UNKNOWN";
    public static final String ERROR_USER_SET_OFFINE = "ERROR_USER_SET_OFFINE";
    public static final String ERROR_SUCCESS = "ERROR_SUCCESS";

    // event name
    public static final String REGISTRATION_CHANGED_EVENT = "ns2:registrationChangedEvent";
    public static final String POLLING_PRESENCE_EVENT = "ns2:pollingPresenceEvent";
    public static final String PRESENCE_CHANGED_EVENT = "ns2:presenceChangedEvent";
    public static final String CONTACT_CHANGED_EVENT = "ns2:contactChangedEvent";
    public static final String IMCHAT_EVENT = "ns2:imChatEvent";
    public static final String IMMESSAGE_EVENT = "ns2:imMessageEvent";
    public static final String IMFILEMEDIA_EVENT = "ns2:imFileMediaEvent";
    public static final String GROUP_CHANGED_EVENT = "ns2:groupChangedEvent";

    // Event polling intervals
    public static final int EVENT_POLL_INTERVAL_STATE_BACKGROUND = 40;
    public static final int EVENT_POLL_INTERVAL_STATE_WORKING = 10;
    public static final int EVENT_POLL_INTERVAL_STATE_REGISTERING = 5;
    public static final int EVENT_POLL_INTERVAL_STATE_CHAT = 5;

    // States
    public static final int STATE_UNREGISTERED = 0;
    public static final int STATE_CHECK_UPDATE = 1;
    public static final int STATE_REGISTRATION_INITIATED = 2;
    public static final int STATE_REREGISTRATION_INITIATED = 3; // deprecated
                                                                // status
    public static final int STATE_REGISTERING = 4;
    public static final int STATE_REGISTERED = 5;
    public static final int STATE_WORKING = 6;
    public static final int STATE_CHAT = 7;

    // event notificaiton
    public static final String EVENT_NOTIFICATION_INCOMING = "Incoming";
    public static final String EVENT_NOTIFICATION_ESTABLISHED = "Established";
    public static final String EVENT_NOTIFICATION_ENDED = "Ended";
    public static final String EVENT_NOTIFICATION_RECEIVED = "Received";
    public static final String EVENT_NOTIFICATION_SENT_FAILED = "SentFailed";
    public static final String EVENT_NOTIFICATION_PROGRESSCHANGED = "ProgressChanged";

    
    //end reason
    public static final String END_REASON_FILE_COMPLETED = "FileCompleted";
    public static final String END_REASON_REMOTE_SHUTDOWN = "RemoteShutdown";
    public static final String END_REASON_CANCELED = "Cancelled";
    public static final String END_REASON_REJECTED = "Rejected";
    public static final String END_REASON_TIME_OUT = "Timeout";
    public static final String END_REASON_OTHER = "Other";
    public static final String END_REASON_TAKE_OVER = "TakeOver";
    public static final String END_REASON_SENT_FAILED = "SentFailed";
  
    
    public static final int NO_TIMEOUT = -1;
    
    public static final String BASE_URL = "http://10.90.3.93:8080/bussiness/pim/menu/1.0/";

}
