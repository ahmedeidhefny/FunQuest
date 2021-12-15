package com.helloWorldTech.funQuest.data.local.shardPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.helloWorldTech.funQuest.Constants;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public void saveLang(String lang) {
        mPrefs.edit().putString(Constants.SP_LANGUAGE, lang).apply();
    }

    @Override
    public String getLang() {
        return mPrefs.getString(Constants.SP_LANGUAGE, "");
    }

    @Override
    public void saveToken(String token) {
        deleteToken();
        mPrefs.edit().putString(Constants.SP_TOKEN, token).apply();
    }

    @Override
    public String getToken() {
        return mPrefs.getString(Constants.SP_TOKEN, "");
    }

    @Override
    public void deleteToken() {
        mPrefs.edit().putString(Constants.SP_TOKEN, "").apply();
    }

    @Override
    public void saveUserId(int id) {
        deleteUserId();
        mPrefs.edit().putInt(Constants.SP_USER_ID, id).apply();
    }

    @Override
    public void deleteUserId() {
        mPrefs.edit().putInt(Constants.SP_USER_ID, 0).apply();
    }

    @Override
    public int getUserId() {
        return mPrefs.getInt(Constants.SP_USER_ID, 0);
    }

    @Override
    public void saveLoginStatus(Boolean isLogin) {
        mPrefs.edit().putBoolean(Constants.IS_LOGIN, isLogin).apply();
    }

    @Override
    public Boolean getLoginStatus() {
        return mPrefs.getBoolean(Constants.IS_LOGIN, false);
    }

    @Override
    public void saveUserPhone(String phone) {
        deleteUserPhone();
        mPrefs.edit().putString(Constants.SP_USER_PHONE, phone).apply();
    }

    @Override
    public void deleteUserPhone() {
        mPrefs.edit().putString(Constants.SP_USER_PHONE, "").apply();
    }

    @Override
    public String getUserPhone() {
        return mPrefs.getString(Constants.SP_USER_PHONE, "");
    }

    @Override
    public void saveUserImage(String image) {
        deleteUserImage();
        mPrefs.edit().putString(Constants.SP_USER_IMAGE, image).apply();
    }

    @Override
    public void deleteUserImage() {
        mPrefs.edit().putString(Constants.SP_USER_IMAGE, "").apply();
    }

    @Override
    public String getUserImage() {
        return mPrefs.getString(Constants.SP_USER_IMAGE, "");
    }

}
