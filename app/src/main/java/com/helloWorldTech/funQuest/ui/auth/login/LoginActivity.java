package com.helloWorldTech.funQuest.ui.auth.login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;
import com.helloWorldTech.funQuest.databinding.ActivityLoginBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.ui.splashes.AfterSplashActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

public class LoginActivity extends BaseActivity {

    //region Variables

    private static final String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;

    private UserViewModel userViewModel;

    private String mobile, password, token = "", language;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        initViewModels();
        initUiAndActions();
        initData();
    }

    private void initUiAndActions() {

        binding.tvForgetPassword.setOnClickListener(result ->
                navigationController.navigateToForgetPasswordActivity(LoginActivity.this)
        );

        binding.tvTerms.setOnClickListener(result -> {
            navigationController.navigateToFAGActivity(LoginActivity.this);
        });

        binding.btnLogin.setOnClickListener(view -> pushLoginBtn());

        binding.btnRegister.setOnClickListener(view -> {
            navigationController.navigateToRegisterActivity(LoginActivity.this);
            //navigationController.navigateToOtpActivity(LoginActivity.this, mobile);
        });

        binding.tvForgetPassword.setOnClickListener(view -> {
            navigationController.navigateToForgetPasswordActivity(LoginActivity.this);
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
    }

    private void initViewModels() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }

    protected void initData() {
        token = Config.API_KEY;
        language = Utils.getLanguage(pref);
        binding.tvTerms.setPaintFlags(binding.tvTerms.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        userViewModel.getUserLoginStatus().removeObservers(this);
        userViewModel.getUserLoginStatus().observe(this, result -> {

            if (result != null) {
                Utils.log("Login Got Data :: " + result.message + result.toString());

                switch (result.status) {
                    case LOADING:
                        // Loading State
                        // Data are from Local DB
                        Utils.log("Status LOADING ");

                        if (Utils.loadingDialog == null) {
                            showLoading(true);
                        }

                        break;
                    case SUCCESS:
                        // Success State
                        // Data are from Server
                        Utils.log("Status SUCCESS ");
                        //Get New Data...
                        if (result.data != null) {
                            if (!result.data.getStatus()) {

                                if (result.data.getCode() == Config.API_IN_VALID_TOKEN
                                        || result.data.getCode() == Config.API_ACCOUNT_STOPPED_CODE) {

                                } else if (result.data.getCode() == Config.API_VALIDATION_CODE) {
                                    if (result.data != null &&  result.data.getData() != null &&
                                            result.data.getData().getValidationErrors() != null) {
                                        PopulateLoginErrorUi(result.data.getData().getValidationErrors());
                                    }
                                } else if (result.data.getCode() == Config.API_NOT_VERIFY_ACCOUNT_CODE) {
                                    navigationController.navigateToOtpActivity(this, this.mobile);
                                }
                                showMessage(result.data.getMessage());
                            } else {
                                if (result.data.getCode() == Config.API_SUCCESS_CODE) {
                                    Utils.log(TAG + "Data:: " + result.message + result.toString());

                                    PopulateLoginErrorUi(null);
                                    String userToken = result.data.getData().getToken();
                                    Utils.log(userToken);
                                    pref.saveToken(userToken);

                                    pref.saveLoginStatus(true);
                                    pref.saveUserPhone(mobile);
                                    pref.saveUserImage(result.data.getData().getUser().getImage());
                                    pref.saveUserId(result.data.getData().getUser().getId());
                                    this.navigationController.navigateToMainActivity(LoginActivity.this);
                                }
                                showMessage(result.data.getMessage());

                            }

                        } else {
                            Utils.log("Login Activity: Cannot Get Data From DB ");
                        }

                        // hide loading bar
                        showLoading(false);
                        userViewModel.setLoadingState(false);
                        break;
                    case ERROR:
                        // Error State
                        Utils.log("Status ERROR ");
                        // hide loading bar
                        showLoading(false);
                        userViewModel.setLoadingState(false);
                        break;
                    default:
                        // Default
                        userViewModel.setLoadingState(false);
                        showLoading(false);
                        break;
                }

            } else {

                // Init Object or Empty Data
                Utils.log("Login Empty Data");
                showLoading(false);
                userViewModel.setLoadingState(false);
            }
        });

    }

    private void PopulateLoginErrorUi(UserEntity.ValidationErrors validationErrors) {
        if (validationErrors != null) {

            if (validationErrors.getMobile() != null) {
                binding.tilPhone.setError(validationErrors.getMobile());
            }

            if (validationErrors.getPassword() != null) {
                binding.tilPassword.setError(validationErrors.getPassword());
            }

        } else {
            binding.tilPassword.setError(null);
            binding.tilPhone.setError(null);

            binding.etPassword.setError(null);
            binding.etPhone.setError(null);
        }
    }

    private void pushLoginBtn() {

        if (connectivity.isConnected()) {

            this.mobile = binding.etPhone.getText().toString().trim();
            this.password = binding.etPassword.getText().toString();

            Utils.log("mobile " + mobile);
            Utils.log("Password " + password);

            // if (isValidate()) {
            if (!userViewModel.isLoading) {
                doLoginSubmit(this.mobile, this.password);
            }
            // }

        } else {

            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }


    }

    private void doLoginSubmit(String mobile, String password) {

        // TODO run progress bar
        showLoading(true);
        userViewModel.isLoading = true;
        userViewModel.setUserLogin(mobile, password, token, language);

    }


    @Override
    protected void observeLoadStatus() {
        super.observeLoadStatus();
        Utils.log("Loading");
        userViewModel.setLoadingState(true);
        userViewModel.getLoadingState().observe(this,
                isLoading -> showLoading(isLoading));
    }

    @Override
    protected void observerErrorStatus() {
        super.observerErrorStatus();
        Utils.log("Error");
        userViewModel.getErrorStatus().observe(this, error -> {
            if (error != null) {
                Utils.errorLog("Error", error);
                Utils.log(error.getMessage());
            }
            userViewModel.setLoadingState(false);
            showLoading(false);
        });
    }

    public void showLoading(Boolean state) {
        if (state) {
            Utils.showCustomLoadingDialog(this);
        } else {
            Utils.hideCustomLoadingDialog();
        }
    }


}