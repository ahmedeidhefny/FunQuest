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
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.databinding.ActivityForgetPasswordBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

public class ForgetPasswordActivity extends BaseActivity {

    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    private ActivityForgetPasswordBinding binding;
    private UserViewModel userViewModel;
    private String mobile = "", language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);

        initUiAndActions();
        initViewModels();
        initData();
    }

    private void initViewModels() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }


    private void initUiAndActions() {
        binding.btnSend.setOnClickListener(view -> {
            pushConfirmBtn();
        });

        binding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPhone.setError(null);
                binding.tilPhone.setError(null);
            }
        });
    }


    private void initData() {
        language = Utils.getLanguage(pref);
    }

    private void pushConfirmBtn() {
        if (connectivity.isConnected()) {

            mobile = binding.etPhone.getText().toString();
            if (!userViewModel.isLoading) {
                doSubmit();
            }

        } else {

            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }


    //region Forget Password

    /**
     * Submit
     */
    private void doSubmit() {

        userViewModel.getResponseForgetPasswordLiveData().removeObservers(this);
        userViewModel.doForgetPassword(Config.API_KEY, language, mobile);
        userViewModel.getResponseForgetPasswordLiveData().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    // Loading State
                    Utils.log(TAG + " Status LOADING ");

                    showLoading(true);
                    userViewModel.setLoadingState(true);

                    break;
                case SUCCESS:
                    onSuccessResult(result.data);

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;
                case ERROR:
                    // Error State
                    Utils.log(TAG + " Status ERROR ");
                    Utils.log(TAG + " Response Error: " + result.error);

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);

                    break;
                default:
                    // Default

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);
                    break;

            }
        });

    }

    private void onSuccessResult(JsonElement data) {
        if (!data.isJsonNull()) {

            Utils.log(TAG + " registerResponse= " + data.toString());

            JsonObject mainJsonObject = data.getAsJsonObject();
            boolean status = mainJsonObject.get("status").getAsBoolean();
            int serverCode = mainJsonObject.get("code").getAsInt();
            String msg = mainJsonObject.get("message").getAsString();

            if (!status) {
                Utils.log(TAG + " Status False");
                showMessage(msg);

                if (serverCode == Config.API_IN_VALID_TOKEN
                        || serverCode == Config.API_NOT_VERIFY_ACCOUNT_CODE
                        || serverCode == Config.API_ACCOUNT_STOPPED_CODE) {
                } else if (serverCode == Config.API_VALIDATION_CODE){
                    JsonObject dataJsonObject = mainJsonObject.get("data").getAsJsonObject();
                    JsonObject VErrorJsonObject = dataJsonObject.get("validation_errors").getAsJsonObject();
                    PopulateErrorUi(VErrorJsonObject);
                }
            } else {
                Utils.log(TAG + " Status True");
                if (serverCode == Config.API_SUCCESS_CODE) {
                    PopulateErrorUi(null);
                    Utils.showToast(msg, this);
                    navigationController.navigateToResetPasswordActivity(this, mobile);
                } else {
                    Utils.showToast(msg, this);
                    Utils.log(TAG + " Status is true but not code 200");
                }

            }

        } else {
            Utils.log(TAG + " Empty Register Data");
        }
    }

    private void PopulateErrorUi(JsonObject vErrorJsonObject) {
        if (vErrorJsonObject != null) {
            if (!vErrorJsonObject.isJsonNull()) {
                if (vErrorJsonObject.has("mobile")) {
                    String mobile = vErrorJsonObject.get("mobile").getAsString();
                    if (!TextUtils.isEmpty(mobile)) {
                        binding.tilPhone.setError(mobile);
                    }
                }
            }
        } else {
            binding.tilPhone.setError(null);
            binding.etPhone.setError(null);
        }
    }

    public void showLoading(Boolean state) {
        if (state) {
            Utils.showCustomLoadingDialog(this);
        } else {
            Utils.hideCustomLoadingDialog();
        }
    }

    //endregion
}