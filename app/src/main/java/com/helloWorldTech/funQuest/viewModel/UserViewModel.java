package com.helloWorldTech.funQuest.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.helloWorldTech.funQuest.data.Resource;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;
import com.helloWorldTech.funQuest.data.remote.api.ApiResponseAuth;
import com.helloWorldTech.funQuest.data.remote.modelResponse.EditProfileApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.ProfileApiResponse;
import com.helloWorldTech.funQuest.data.repository.UserRepository;
import com.helloWorldTech.funQuest.ui.base.BaseViewModel;
import com.helloWorldTech.funQuest.util.AbsentLiveData;
import com.helloWorldTech.funQuest.util.Utils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class UserViewModel extends BaseViewModel {

    //region Variables

    private final UserRepository userRepository;

    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    // for Login
    private final LiveData<Resource<UserEntity>> doUserLoginData;
    private MutableLiveData<TmpDataHolder> doUserLoginObj = new MutableLiveData<>();

    //for register
    private final MutableLiveData<ApiResponseAuth> responseRegisterLiveData = new MutableLiveData<>();

    //for verify account
    private final MutableLiveData<ApiResponseAuth> responseVerifyAccountLiveData = new MutableLiveData<>();

    //for Resend Code
    private final MutableLiveData<ApiResponseAuth> responseResendActiveCodeLiveData = new MutableLiveData<>();

    //for ForgetPassword
    private final MutableLiveData<ApiResponseAuth> responseForgetPasswordLiveData = new MutableLiveData<>();

    //for ResetPassword
    private final MutableLiveData<ApiResponseAuth> responseResetPasswordLiveData = new MutableLiveData<>();

    //for ResendForgetCode
    private final MutableLiveData<ApiResponseAuth> responseResendForgetCodeLiveData = new MutableLiveData<>();

    //for Get Profile
    private final MutableLiveData<ProfileApiResponse> responseGetProfileLiveData = new MutableLiveData<>();

    //for Edit Profile
    private final MutableLiveData<EditProfileApiResponse> responseEditProfileLiveData = new MutableLiveData<>();

    //endregion

    @Inject
    public UserViewModel(UserRepository repository) {
        this.userRepository = repository;

        // Login User
        doUserLoginData = Transformations.switchMap(doUserLoginObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("UserViewModel : doUserLoginData");
            return userRepository.doLogin(obj.token, obj.language, obj.phone, obj.password);
        });
    }

    //region User

    public LiveData<UserEntity> getLoginUser(){
        return userRepository.getUser();
    }

    public void updateLoginUser(UserEntity user){
         userRepository.updateUser(user);
    }

    public void deleteUser(){
        userRepository.deleteAllUser();
    }

    public void setUserLogin(String mobile, String password, String token, String language) {
        setLoadingState(true);
        TmpDataHolder temp = new TmpDataHolder();
        temp.phone = mobile;
        temp.password = password;
        temp.token = token;
        temp.language = language;
        this.doUserLoginObj.setValue(temp);
    }

    public LiveData<Resource<UserEntity>> getUserLoginStatus() {
        return doUserLoginData;
    }

    public MutableLiveData<ApiResponseAuth> getResponseRegisterLiveData() {
        return responseRegisterLiveData;
    }

    public void doUserRegister(String token, String lang, String mobile, String password, String confirmPassword, String username, String ageRange) {
        responseRegisterLiveData.setValue(null);

        getCompositeDisposable().add(this.userRepository.doRegister(token, lang, mobile, password, confirmPassword, username, ageRange)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseRegisterLiveData.setValue(ApiResponseAuth.loading()))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> responseRegisterLiveData.setValue(ApiResponseAuth.success(result)),
                        throwable -> responseRegisterLiveData.setValue(ApiResponseAuth.error(throwable))
                ));

    }

    public MutableLiveData<ApiResponseAuth> getResponseVerifyAccountLiveData() {
        return responseVerifyAccountLiveData;
    }

    public void doVerifyAccount(String token, String lang, String mobile, String code) {

        responseVerifyAccountLiveData.setValue(null);
        getCompositeDisposable().add(this.userRepository.verifyAccount(token, lang, mobile, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseVerifyAccountLiveData.setValue(ApiResponseAuth.loading()))
                .subscribe(
                        result -> responseVerifyAccountLiveData.setValue(ApiResponseAuth.success(result)),
                        throwable -> responseVerifyAccountLiveData.setValue(ApiResponseAuth.error(throwable))
                ));

    }

    public MutableLiveData<ApiResponseAuth> getResponseResendActiveCodeLiveData() {
        return responseResendActiveCodeLiveData;
    }

    public void doResendActivationCode(String token, String lang, String mobile) {
        responseResendActiveCodeLiveData.setValue(null);
        getCompositeDisposable().add(this.userRepository.resendActivationCode(token, lang, mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseResendActiveCodeLiveData.setValue(ApiResponseAuth.loading()))
                .subscribe(
                        result -> responseResendActiveCodeLiveData.setValue(ApiResponseAuth.success(result)),
                        throwable -> responseResendActiveCodeLiveData.setValue(ApiResponseAuth.error(throwable))
                ));

    }

    public MutableLiveData<ApiResponseAuth> getResponseForgetPasswordLiveData() {
        return responseForgetPasswordLiveData;
    }

    public void doForgetPassword(String token, String lang, String mobile) {

        responseForgetPasswordLiveData.setValue(null);
        getCompositeDisposable().add(this.userRepository.forgetPassword(token, lang, mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(
                        (d) -> responseForgetPasswordLiveData.setValue(ApiResponseAuth.loading()))
                .subscribe(
                        result -> responseForgetPasswordLiveData.setValue(ApiResponseAuth.success(result)),
                        throwable -> responseForgetPasswordLiveData.setValue(ApiResponseAuth.error(throwable))
                ));

    }

    public MutableLiveData<ApiResponseAuth> getResponseResetPasswordLiveData() {
        return responseResetPasswordLiveData;
    }

    public void doResetPassword(String token, String lang, String mobile, String password, String confirmPassword, String code) {

        responseResetPasswordLiveData.setValue(null);
        getCompositeDisposable().add(this.userRepository.resetPassword(token, lang, mobile, password, confirmPassword, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(
                        (d) -> responseResetPasswordLiveData.setValue(ApiResponseAuth.loading()))
                .subscribe(
                        result -> responseResetPasswordLiveData.setValue(ApiResponseAuth.success(result)),
                        throwable -> responseResetPasswordLiveData.setValue(ApiResponseAuth.error(throwable))
                ));

    }

    public MutableLiveData<ApiResponseAuth> getResponseResendForgetCodeLiveData() {
        return responseResendForgetCodeLiveData;
    }

    public void doResendForgetCode(String token, String lang, String mobile) {

        responseResendForgetCodeLiveData.setValue(null);
        getCompositeDisposable().add(this.userRepository.resendForgetCode(token, lang, mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(
                        (d) -> responseResendForgetCodeLiveData.setValue(ApiResponseAuth.loading()))
                .subscribe(
                        result -> responseResendForgetCodeLiveData.setValue(ApiResponseAuth.success(result)),
                        throwable -> responseResendForgetCodeLiveData.setValue(ApiResponseAuth.error(throwable))
                ));

    }

    public MutableLiveData<ProfileApiResponse> getProfileResponse() {
        return responseGetProfileLiveData;
    }

    public void loadProfile(String token, String apiKey, String lang) {

        responseGetProfileLiveData.setValue(null);
        getCompositeDisposable().add(this.userRepository.getProfileData(token,apiKey, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(
                        s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> responseGetProfileLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<EditProfileApiResponse> getEditProfileResponse() {
        return responseEditProfileLiveData;
    }

    public void loadEditProfile(String token, String apiKey, RequestBody name, RequestBody email, MultipartBody.Part image, RequestBody birthOfDate, String lang) {
        responseEditProfileLiveData.setValue(null);

        getCompositeDisposable().add(this.userRepository.editProfile(token, apiKey, name, email, image, birthOfDate, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(
                        s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> responseEditProfileLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    //endregion

    class TmpDataHolder {

        public String phone = "";
        public String password = "";
        public String token = "";
        public String language = "";

    }
}
