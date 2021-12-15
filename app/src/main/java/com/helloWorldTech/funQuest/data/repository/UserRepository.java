package com.helloWorldTech.funQuest.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.helloWorldTech.funQuest.AppExecutors;
import com.helloWorldTech.funQuest.data.AppDatabase;
import com.helloWorldTech.funQuest.data.NetworkBoundResource;
import com.helloWorldTech.funQuest.data.Resource;
import com.helloWorldTech.funQuest.data.local.dao.UserDao;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;
import com.helloWorldTech.funQuest.data.remote.api.ApiResponse;
import com.helloWorldTech.funQuest.data.remote.api.ApiService;
import com.helloWorldTech.funQuest.data.remote.modelResponse.EditProfileApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.ProfileApiResponse;
import com.helloWorldTech.funQuest.util.AbsentLiveData;
import com.helloWorldTech.funQuest.util.Utils;
import com.google.gson.JsonElement;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/

@Singleton
public class UserRepository extends BaseRepository {

    private final UserDao userDao;

    @Inject
    UserRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db, UserDao userDao) {
        super(apiService, appExecutors, db);
        this.userDao = userDao;
    }

    //region LOGIN

    /**
     * Function to login
     *
     * @param mobile   User Mobile
     * @param password User Password
     * @return Login User Data
     */
    public LiveData<Resource<UserEntity>> doLogin(String token, String lang, String mobile, String password) {

        Utils.log("Do Login : " + mobile + " & " + password + " & " + token);

        return new NetworkBoundResource<UserEntity, UserEntity>(appExecutors) {

            String userId = "";

            @Override
            protected void saveCallResult(@NonNull UserEntity user) {

                Utils.log("4- SaveCallResult of doLogin.");

                try {
                    db.runInTransaction(() -> {

                        // set User id
                        if (!user.getStatus()) {
                            userId = "0";
                        } else {
                            userId = String.valueOf(user.getData().getUser().getId());
                        }

                        // clear user login data
                        userDao.deleteUser();

                        // insert user data
                        userDao.insert(user);

                        //dataManager.createUser(user);

                    });
                } catch (Exception ex) {
                    Utils.errorLog("Login Error at ", ex);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity user) {
                Utils.log("2- shouldFetch of doLogin.");
                //for user login, always should fetch
                return true;

            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                Utils.log("1- Load User Login data from database.");

                if (userId == null || userId.equals("")) {
                    return AbsentLiveData.create();
                }

                return userDao.getUser();

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserEntity>> createCall() {
                Utils.log("3- Call API Service to do user login.");
                return apiService.postUserLogin(token, lang, mobile, password);
            }

            @Override
            protected void onFetchFailed(String message) {
                Utils.log("Fetch Failed in doLogin.");
            }
        }.asLiveData();
    }

    public LiveData<UserEntity> getUser() {
        return userDao.getUser();
    }


    public void deleteAllUser() {
        userDao.deleteUser();
    }

    public void updateUser(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    public Observable<JsonElement> doRegister(String token, String lang, String mobile, String password, String confirmPassword, String username, String ageRange) {
        return apiService.postUserRegister(token, mobile, username, password, confirmPassword, lang, ageRange);
    }

    public Observable<JsonElement> verifyAccount(String token, String lang, String mobile, String code) {
        return apiService.postVerifyAccount(token, lang, mobile, code);
    }

    public Observable<JsonElement> resendActivationCode(String token, String lang, String mobile) {
        return apiService.postResendActivationCode(token, lang, mobile);
    }

    public Observable<JsonElement> forgetPassword(String token, String lang, String mobile) {
        return apiService.postForgetPassword(token, lang, mobile);
    }

    public Observable<JsonElement> resetPassword(String token, String lang, String mobile, String password, String confirmPassword, String code) {
        return apiService.postResetPassword(token, lang, mobile, code, password, confirmPassword);
    }

    public Observable<JsonElement> resendForgetCode(String token, String lang, String mobile) {
        return apiService.postResendForgetCode(token, lang, mobile);
    }

    public Observable<ProfileApiResponse> getProfileData(String token, String apiKey, String lang) {
        return apiService.getProfile(token, apiKey, lang);
    }

    public Observable<EditProfileApiResponse> editProfile(String token, String apiKey, RequestBody name, RequestBody email, MultipartBody.Part image, RequestBody birthOfDate, String lang) {
        return apiService.editProfile(token, apiKey, name, email, image, birthOfDate, lang);
    }
    //endregion

}
