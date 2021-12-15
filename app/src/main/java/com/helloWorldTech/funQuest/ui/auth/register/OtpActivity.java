package com.helloWorldTech.funQuest.ui.auth.register;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.databinding.ActivityOtpBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

public class OtpActivity extends BaseActivity {

    private static final String TAG = OtpActivity.class.getSimpleName();
    ActivityOtpBinding binding;
    private UserViewModel userViewModel;
    private String codeIntent = "", mobile = "0186655", language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

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

        binding.pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tilCode.setError(null);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        navigationController.navigateToRegisterActivity(OtpActivity.this);
    }

    private void initData() {

        language = Utils.getLanguage(pref);

        if (getIntent() != null) {
            if (getIntent().hasExtra(Constants.USER_MOBILE)) {
                //codeIntent = getIntent().getExtras().getString(Constants.VERIFICATION_CODE);
                mobile = getIntent().getExtras().getString(Constants.USER_MOBILE);
                Utils.log(TAG + " Mobile: " + mobile + "/Code: " + codeIntent);
            }
        }

        pushResendCodeBtn();

    }

    private void startCounter() {
        binding.tvReSendCode.setVisibility(View.GONE);
        binding.tvTimer.setVisibility(View.VISIBLE);

        new CountDownTimer(60000, 1000) {

            @SuppressLint("StringFormatInvalid")
            public void onTick(long millisUntilFinished) {
                binding.tvTimer.setText(getResources().getString(
                        R.string.label_enter_send_code,
                        String.valueOf(millisUntilFinished / 1000)));
            }

            public void onFinish() {
                binding.tvReSendCode.setVisibility(View.VISIBLE);
                binding.tvTimer.setVisibility(View.GONE);
            }

        }.start();
    }

    // region OTP
    private void pushConfirmBtn() {
        if (connectivity.isConnected()) {

            String code = binding.pinView.getText().toString();
            Utils.log(TAG + " Code: " + code);

            if (!userViewModel.isLoading) {
                doSubmit(code);
            }

        } else {

            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    /**
     * Submit OTP
     */
    private void doSubmit(String verifyCode) {

        userViewModel.getResponseVerifyAccountLiveData().removeObservers(this);
        userViewModel.getLoadingState().removeObservers(this);
        userViewModel.getErrorStatus().removeObservers(this);

        userViewModel.doVerifyAccount(Config.API_KEY, language, this.mobile, verifyCode);
        userViewModel.getResponseVerifyAccountLiveData().observe(this, result -> {
            switch (result.status) {
                case LOADING:

                    // Loading State
                    Utils.log(TAG + " Status LOADING ");

                    showLoading(true);
                    userViewModel.setLoadingState(true);

                    break;
                case SUCCESS:
                    onSuccessOTPResult(result.data);

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

    //onSuccessOTPResult
    public void onSuccessOTPResult(JsonElement data) {
        if (!data.isJsonNull()) {

            Utils.log(TAG + " OTPResponse= " + data.toString());

            JsonObject mainJsonObject = data.getAsJsonObject();
            boolean status = mainJsonObject.get("status").getAsBoolean();
            int serverCode = mainJsonObject.get("code").getAsInt();
            String msg = mainJsonObject.get("message").getAsString();

            if (!status) {
                showMessage(msg);
                if (serverCode == Config.API_IN_VALID_TOKEN
                        || serverCode == Config.API_ACCOUNT_STOPPED_CODE) {
                } else if (serverCode == Config.API_VALIDATION_CODE) {
                    JsonObject dataJsonObject = mainJsonObject.get("data").getAsJsonObject();
                    JsonObject VErrorJsonObject = dataJsonObject.get("validation_errors").getAsJsonObject();
                    PopulateErrorUi(VErrorJsonObject);
                } else if (serverCode == Config.API_SUCCESS_CODE) { //"Account Already verified"
                    navigationController.navigateToLoginActivity(this);
                }

            } else {
                if (serverCode == Config.API_SUCCESS_CODE) {
                    //PopulateErrorUi(null);
                    showMessage(msg);
                    navigationController.navigateToLoginActivity(this);
                } else {
                    Utils.showToast(msg, this);
                    Utils.log(TAG + " Status is true but not code 200");
                }

            }

        } else {
            Utils.log(TAG + "Empty Verify Account Data");
        }

    }

    private void PopulateErrorUi(JsonObject vErrorJsonObject) {
        Utils.log(TAG + " VErrorJsonObject= " + vErrorJsonObject);

        if (!vErrorJsonObject.isJsonNull() && vErrorJsonObject != null) {
            if (vErrorJsonObject.has("code")) {
                String code = vErrorJsonObject.get("code").getAsString();
                if (!TextUtils.isEmpty(code)) {
                    binding.tilCode.setError(mobile);
                }
            }

            if (vErrorJsonObject.has("mobile")) {
                String mobile = vErrorJsonObject.get("mobile").getAsString();
                if (!TextUtils.isEmpty(mobile)) {
                    binding.tilCode.setError(mobile);
                }
            }
        } else {
            binding.tilCode.setError(null);
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
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    private void resendVerifyCode() {

        userViewModel.getResponseResendActiveCodeLiveData().removeObservers(this);
        userViewModel.getLoadingState().removeObservers(this);
        userViewModel.getErrorStatus().removeObservers(this);

        userViewModel.doResendActivationCode(Config.API_KEY, language, this.mobile);
        userViewModel.getResponseResendActiveCodeLiveData().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    // Loading State
                    Utils.log(TAG + " Status LOADING ");

                    //show loading
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
                    Utils.log(TAG + " Login Response Error: " + result.error);

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

            Utils.log("OTPResendCodeResponse= " + jsonData.toString());

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
                    PopulateResendCodeErrorUi(VErrorJsonObject);
                }
            } else {
                if (serverCode == Config.API_SUCCESS_CODE) {
                    showMessage(msg);
                    startCounter();
                    // PopulateResendCodeErrorUi(null);
                } else {
                    showMessage(msg);
                    Utils.log(TAG + " Status is true but not code 200");
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