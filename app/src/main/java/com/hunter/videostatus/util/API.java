package com.hunter.videostatus.util;

public class API {
    public static final String BASEURL = "http://vidstatus.in/vidstatus/api";
    public static final String LATESTSTATUS = "http://vidstatus.in/vidstatus/api/api.php?req=statuslist&statustype=videostatus";
    public static final String LATESTSTATUSDPIMAGES = "http://vidstatus.in/vidstatus/api/api.php?req=statuslist&statustype=imagestatus&";
    public static final String LATESTSTATUSTEXT = "http://vidstatus.in/vidstatus/api/api.php?req=statuslist&statustype=textstatus&";
    public static final String LIKE_DISLIKE_VIDEO = "http://vidstatus.in/vidstatus/api/api.php?req=statuslike";
    public static final String MORE_APP = "http://vidstatus.in/vidstatus/api/api.php?req=moreapp";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String POPULARATUSDPIMAGES = "http://vidstatus.in/vidstatus/api/api.php?req=popularstatus&statustype=imagestatus&";
    public static final String POPULARATUSTEXT = "http://vidstatus.in/vidstatus/api/api.php?req=popularstatus&statustype=textstatus&";
    public static final String POPULARSTATUS = "http://vidstatus.in/vidstatus/api/api.php?req=popularstatus&statustype=videostatus";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String RELATEDSTATUS = "http://vidstatus.in/vidstatus/api/api.php?req=relatedstatus&statustype=videostatus";
    public static final String SEARCHSTATUS = "http://vidstatus.in/vidstatus/api/api.php?req=searchstatus&statustype=videostatus";
    public static final String SHARED_PREF = "ah_firebase_videostatustext";
    public static final String TOPIC_GLOBAL = "global";
    public static final String URL_CATEGORY = "http://vidstatus.in/vidstatus/api/api.php?req=cactegory&statustype=";
    public static final String URL_CATEGORYDPIMAGESTATUSLIST = "http://vidstatus.in/vidstatus/api/api.php?req=statuslist&statustype=imagestatus";
    public static final String URL_CATEGORYTEXTSTATTUSLIST = "http://vidstatus.in/vidstatus/api/api.php?req=statuslist&statustype=textstatus";
    public static final String URL_CATEGORYVIDEOLIST = "http://vidstatus.in/vidstatus/api/api.php?req=statuslist&statustype=videostatus";
    public static final String VIDEODOWNLOAD = "http://vidstatus.in/vidstatus/api/api.php?req=downlodsstatus&statustype=videostatus";
    public static final String VIDEOSHARE = "http://vidstatus.in/vidstatus/api/api.php?req=sharestatus&statustype=videostatus";
    public static final String VIDEOVIEW = "http://vidstatus.in/vidstatus/api/api.php?req=viewstatus&statustype=videostatus";
}