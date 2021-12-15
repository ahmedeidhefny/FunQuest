package com.helloWorldTech.funQuest;

/**
 * @author Islam Elshnawey
 * @date 4/9/20
 * <p>
 * is.elshnawey@gmail.com
 **/
public class Constants {

    public static final String VERIFICATION_CODE = "verification_code";
    public static final String USER_MOBILE = "mobile";
    public static final String ARABIC_VALUE = "ar";
    public static final String ENGLISH_VALUE = "en";

    public static final String BOOK_NUMBER = "book_number";

    public static final String SP_SETTINGS_NAME = "settings";
    public static final String SP_LANGUAGE = "language";
    public static final String SP_TOKEN = "token";
    public static final String SP_USER_ID = "userId";
    public static final String IS_LOGIN = "isLogin";
    public static final String SP_USER_PHONE = "phone";
    public static final String SP_USER_IMAGE= "image";
    public static final String SP_ADDRESS_ID = "addressId";
    public static final String SP_CATEGORY_ID = "categoryId";
    public static final String SP_GROUP_ID = "groupId";
    public static final String SP_VENDOR_CARTS_ID = "vendorCartsId";
    public static final String SP_SEARCH_TYPE_ID = "searchTypeId";
    public static final String SP_SEARCH_TYPE_NAME = "searchTypeName";
    public static final String SP_FILTER_LIST_JSON = "filterList";

    //Payment Way Code
    public static final int CASH_PAYMENT_CODE = 1 ;
    public static final int CREDIT_PAYMENT_CODE = 2;
    public static final int WALLET_PAYMENT_CODE = 3 ;

    //HOME SIDE MENU CODE
    public static final int CALL_US_CODE = 101 ;
    public static final int LANGUAGE_CODE = 102;
    public static final int SHARE_APP_CODE = 103 ;
    public static final int RATE_APP_CODE = 104 ;

    //HOME SIDE MENU TYPE
    public static final String MENU_SERVER_TYPE = "server";
    public static final String MENU_LOCAL_TYPE = "local";

    //OFFER TYPE
    public static final String OFFER_TYPE_TYPE = "type";
    public static final String OFFER_CATEGORY_TYPE = "category";
    public static final String OFFER_VENDOR_TYPE = "vendor";
    public static final String OFFER_PRODUCT_TYPE = "product";


    //Order current Status
    public static final String NEW_STATUS =  "new" ;
    public static final String ACCEPT_STATUS = "accept";
    public static final String READY_TO_DELIVER_STATUS = "ready_to_deliver" ;
    public static final String IN_WAY_STATUS =  "in_way" ;

    //Order completed Status
    public static final String DELIVERED_STATUS = "delivered";
    public static final String REJECTED_STATUS = "rejected" ;
    public static final String CANCELLED_STATUS = "canceled" ;
    public static final String PAGE_ID = "page_id";
    public static final String PAGE_TITLE = "page_title";

    //Calculate Product Type
    public static final String CALCULATE_ALL_TYPE = "all";
    public static final String CALCULATE_PRODUCT_TYPE = "product" ;
    public static final String CALCULATE_OPTIONS_TYPE = "option" ;


    //ShardPreferences name
    public static String PREF_NAME = "sunnah__pref";

    //social media
    public final static String SOCIAL_GOOGLE = "google";
    public final static String SOCIAL_FACEBOOK = "facebook";
    public final static String SOCIAL_TWITTER = "twitter";
    public final static String SOCIAL_APPLE = "apple";


    // Intents
    public static String IN_CURRENT_LOCATION = "current_location";
    public static String IN_CHOOSE_LOCATION = "choose_location";
    public static String IN_EDIT_LOCATION_OBJ = "locationObj";
    public static String IN_PRODUCT_OBJ = "productObj";
    public static String IN_ACTION_TYPE = "actionType";
    public static String IN_FROM_CHECKOUT = "fromCheckout";
    public static String IN_LAT = "lat";
    public static String IN_LNG = "lng";
    public static String IN_IS_ADD_CURRERNT = "isAddCurrent";
    public static String IN_IS_ADD_MAP = "isAddMap";
    public static String IN_IS_CHECKOUT_ADD_MAP = "isCheckoutAddMap";
    public static String IN_IS_EDIT = "isEdit";
    public static String IN_IS_CHECKOUT_EDIT = "isCheckoutEdit";
    public static String IN_OPEN_HOME = "openHome";
    public static String IN_TYPE_ID = "typeId";
    public static String IN_CITY_ID = "cityId";
    public static String IN_PLACE_ID = "placeId";
    public static String IN_GAME_ID = "gameId";
    public static String IN_ORDER_ID = "orderId";
    public static String IN_FAG_OBJ = "fagObj";
    public static String IN_CHECKOUT_OBJ = "checkoutObj";
    public static String IN_TOTAL_AMOUNT = "totalAmount";
    public static String IN_SEARCH_INTERFACE_OBJ = "searchInterface";
    public static String IN_WALLET_BALANCE = "balance";


    //region OrderStatus
    public final static String ORDER_STATUS_NEW = "new"; //1
    public final static String ORDER_STATUS_ACCEPT = "accept"; //2
    public final static String ORDER_STATUS_IN_WAY = "in_way"; //3
    public final static String ORDER_STATUS_READY_TO_DELIVER = "ready_to_deliver"; //3


}
