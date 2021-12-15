package com.helloWorldTech.funQuest;

public class Config {

    /**
     * API Version
     * For your app, you need to change according based on your apip version
     */
    public static String API_VERSION = "v1/";

    /**
     * API KEY
     */
    public static String API_KEY = "5rkYT26RQeYJC2vd4PPS";

    public static String HITCHBUG_APP_KEY = "bnFLbHNpVEVDREJGZmxDc3Jrb093cWI3MXl1RVVqenp3cmJUcXdNbkdVWHd5MmlySzl5dXdhU0FFU3oxbzFUV2VLVThvcVFpNXlpSXBaZUpJUTI1V2E4QWlSMWZ4cXlLUnVxSFRVcXVVTGhLWFJpckxXRkVUMjVo";

    /**
     * BASE URL
     */
    public static String BASE_URl = "https://app.thefunquest.com/api/";

    /**
     * BASE IMAGE URL
     */
    public static String BASE_IMAGE_URl = "";

    /**
     * Token Type
     */
    public static String TOKEN_TYPE = "Bearer ";

    public static int API_SUCCESS_CODE = 200; //Success Request
    public static int API_VALIDATION_CODE = 400 ; //Validation error or Wrong Request
    public static int API_UNAUTHENTICATED_CODE = 401 ; //UnAuthenticated
    public static int API_IN_VALID_TOKEN = 302 ; // InValid Api-Key
    public static int API_NOT_VERIFY_ACCOUNT_CODE = 308 ;
    public static int API_ACCOUNT_STOPPED_CODE = 309 ;


    /**
     * APP Setting
     * Set false, your app is production
     * It will turn off the logging Process
     */
    public static boolean IS_DEVELOPMENT = true;

    /**
     * Loading Limit Count Setting
     */
    public static int NOTI_LIST_COUNT = 30;

    public static int PAGING_LIMIT = 20;

    /**
     * Price Format
     * Need to change according to your format that you need
     * E.g.
     * ",###.00"   => 2,555.00
     * "###.00"    => 2555.00
     * ".00"       => 2555.00
     * ",###"      => 2555
     * ",###,0"    => 2555.0
     */
    public static final String DECIMAL_PLACES_FORMAT = "#.###";

    /**
     * Region playstore
     */
    public static String PLAYSTORE_MARKET_URL_FIX = "market://details?id=";
    public static String PLAYSTORE_HTTP_URL_FIX = "http://play.google.com/store/apps/details?id=";

    /**
     * Image Cache and Loading
     */
    public static int IMAGE_CACHE_LIMIT = 250; // Mb
    public static boolean PRE_LOAD_FULL_IMAGE = false;

    /**
     * Policy Url
     */
    public static String POLICY_URL = "";

    /**
     * URI Authority File
     */
    public static String AUTHORITYFILE = ".fileprovider";

}
