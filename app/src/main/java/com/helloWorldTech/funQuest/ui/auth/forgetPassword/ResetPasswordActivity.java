package com.helloWorldTech.funQuest.ui.auth.forgetPassword;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.databinding.ActivityResetPasswordBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

public class ResetPasswordActivity extends BaseActivity {

    private static final String TAG = ResetPasswordActivity.class.getSimpleName();
    private ActivityResetPasswordBinding binding;
    private UserViewModel userViewModel;
    private String mobile = "", getLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);

        initViewModels();
        initUIAndActions();
        initData();
    }

    private void initViewModels() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }


    private void initUIAndActions() {

        binding.btnSend.setOnClickListener(view -> {
            pushConfirmBtn();
        });

        binding.tvReSendCode.setOnClickListener(view -> {
            pushResendCodeBtn();
        });


        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etCode.setError(null);
                binding.etCode.setError(null);
            }
        });

        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPassword.setError(null);
                binding.tilPassword.setError(null);
            }
        });

        binding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etConfirmPassword.setError(null);
                binding.tilConfirmPassword.setError(null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initData() {
        getLanguage = Utils.getLanguage(pref);
        if (getIntent() != null) {
            if (getIntent().hasExtra(Constants.USER_MOBILE)) {
                mobile = getIntent().getExtras().getString(Constants.USER_MOBILE);
            }
        }
    }

    //region reset password
    private void pushConfirmBtn() {
        if (connectivity.isConnected()) {

            String code = binding.etCode.getText().toString();
            String password = binding.etPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();

            if (!userViewModel.isLoading) {
                doSubmit(password, confirmPassword, code);
            }

        } else {

            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    /**
     * Submit
     */
    private void doSubmit(String password, String confirmPassword, String code) {
        userViewModel.getResponseResetPasswordLiveData().removeObservers(this);
        userViewModel.doResetPassword(Config.API_KEY, getLanguage, this.mobile, password, confirmPassword, code);
        userViewModel.getResponseResetPasswordLiveData().observe(this, result -> {
            switch (result.status) {
                case LOADING:

                    // Loading State
                    Utils.log(TAG + " Status LOADING ");

                    showLoading(true);
                    userViewModel.setLoadingState(true);

                    break;
                case SUCCESS:
                    onSuccessResetPasswordResult(result.data);

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;
                case ERROR:
                    // Error State
                    Utils.log(TAG + " Status ERROR ");
                    Utils.log(TAG + " Error: " + result.error);

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;
                default:
                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;

            }
        });
    }

    private void onSuccessResetPasswordResult(JsonElement jsonData) {
        if (!jsonData.isJsonNull()) {

            Utils.log(TAG + " Response= " + jsonData.toString());

            JsonObject object = jsonData.getAsJsonObject();
            int serverCode = object.get("code").getAsInt();
            String msg = object.get("message").getAsString();
            boolean status = object.get("status").getAsBoolean();

            if (!status) {
                showMessage(msg);
                if (serverCode == Config.API_IN_VALID_TOKEN
                        || serverCode == Config.API_ACCOUNT_STOPPED_CODE) {
                } else if (serverCode == Config.API_VALIDATION_CODE) {
                    JsonObject dataJsonObject = object.get("data").getAsJsonObject();
                    JsonObject VErrorJsonObject = dataJsonObject.get("validation_errors").getAsJsonObject();
                    PopulateResetPasswordErrorUi(VErrorJsonObject);
                }
            } else {
                showMessage(msg);
                if (serverCode == Config.API_SUCCESS_CODE) {
                    PopulateResetPasswordErrorUi(null);
                    navigationController.navigateToLoginActivity(this);
                }
            }

        } else {
            Utils.log(TAG + " Empty Verify Code Account Data");
        }
    }

    private void PopulateResetPasswordErrorUi(JsonObject vErrorJsonObject) {
        if (vErrorJsonObject != null) {
            if (!vErrorJsonObject.isJsonNull()) {

                if (vErrorJsonObject.has("mobile") && vErrorJsonObject != null) {
                    String mobile = vErrorJsonObject.get("mobile").getAsString();
                    if (!TextUtils.isEmpty(mobile)) {
                        showMessage(mobile);
                    }
                }

                if (vErrorJsonObject.has("password")) {
                    String password = vErrorJsonObject.get("password").getAsString();
                    if (!TextUtils.isEmpty(password)) {
                        binding.tilPassword.setError(password);
                    }


                    if (vErrorJsonObject.has("code")) {
                        String code = vErrorJsonObject.get("code").getAsString();
                        if (!TextUtils.isEmpty(code)) {
                            binding.tilCode.setError(code);
                        }
                    }
                }
            }
        } else {
            binding.etPassword.setError(null);
            binding.tilPassword.setError(null);

            binding.etCode.setError(null);
            binding.tilCode.setError(null);

            binding.etConfirmPassword.setError(null);
            binding.tilConfirmPassword.setError(null);
        }
    }

    //endregion

    //region Resend Code
    private void pushResendCodeBtn() {
        if (connectivity.isConnected()) {
            if (!userViewModel.isLoading) {
                resendVerifyCode();
            }

        } else {
            // no Internet Connection
            Utils.showToast(getString(R.string.massage_no_internet), this);
        }
    }

    private void resendVerifyCode() {

        userViewModel.getResponseResendForgetCodeLiveData().removeObservers(this);
        userViewModel.doResendForgetCode(Config.API_KEY, getLanguage, this.mobile);
        userViewModel.getResponseResendForgetCodeLiveData().observe(this, result -> {
            switch (result.status) {
                case LOADING:

                    // Loading State
                    Utils.log(TAG + " Status LOADING ");

                    showLoading(true);
                    userViewModel.setLoadingState(true);

                    break;
                case SUCCESS:
                    onSuccessResendCodeResult(result.data);

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;
                case ERROR:
                    // Error State
                    Utils.log(TAG + " Status ERROR ");
                    Utils.log(TAG + " Error: " + result.error);

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;
                default:
                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;

            }
        });
    }

    private void onSuccessResendCodeResult(JsonElement jsonData) {
        if (!jsonData.isJsonNull()) {

            Utils.log(TAG + " ResendCodeResponse= " + jsonData.toString());

            JsonObject object = jsonData.getAsJsonObject();
            int serverCode = object.get("code").getAsInt();
            String msg = object.get("message").getAsString();
            boolean status = object.get("status").getAsBoolean();

            if (!status) {
                showMessage(msg);

                if (serverCode == Config.API_VALIDATION_CODE) {
                    JsonObject dataJsonObject = object.get("data").getAsJsonObject();
                    JsonObject VErrorJsonObject = dataJsonObject.get("validation_errors").getAsJsonObject();
                    PopulateResendCodeErrorUi(VErrorJsonObject);
                }
            } else {
                showMessage(msg);
                if (serverCode == Config.API_SUCCESS_CODE) {
                    //Utils.showToast(msg, this);
                    // PopulateResendCodeErrorUi(null);
                }
            }

        } else {
            Utils.log(TAG + " Empty Verify Code Account Data");
        }
    }

    private void PopulateResendCodeErrorUi(JsonObject vErrorJsonObject) {
        if (!vErrorJsonObject.isJsonNull()) {
            if (vErrorJsonObject.has("mobile") && vErrorJsonObject != null) {
                String mobile = vErrorJsonObject.get("mobile").getAsString();
                if (!TextUtils.isEmpty(mobile)) {
                    showMessage(mobile);
                }
            }
        }
    }
    //endregion

    public void showLoading(Boolean state) {
        if (state) {
            Utils.showCustomLoadingDialog(this);
        } else {
            Utils.hideCustomLoadingDialog();
        }
    }

}