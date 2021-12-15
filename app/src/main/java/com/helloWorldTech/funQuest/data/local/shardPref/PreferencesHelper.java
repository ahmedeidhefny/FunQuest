package com.helloWorldTech.funQuest.data.local.shardPref;


public interface PreferencesHelper {

    void saveLang (String lang);

    String getLang ();

    void saveToken (String token);

    String getToken ();

    void  deleteToken ();

    void saveUserId (int id);

    void deleteUserId();

    int getUserId ();

    void saveLoginStatus (Boolean isLogin);

    Boolean getLoginStatus ();

    void saveUserPhone (String phone);

    void deleteUserPhone();

    String getUserPhone ();

    void saveUserImage (String phone);

    void deleteUserImage();

    String getUserImage ();

}
