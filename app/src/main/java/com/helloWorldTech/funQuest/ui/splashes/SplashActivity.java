package com.helloWorldTech.funQuest.ui.splashes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.databinding.ActivitySplashBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;

import java.util.Locale;

import static android.content.ContentValues.TAG;

public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private HomeViewModel splashViewModel;
    private String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initUI();
    }

    private void initUI() {
        showLoading(false);
        getLanguage();

        binding.btnArabic.setOnClickListener(view->{
            lang = Constants.ARABIC_VALUE;
            pref.saveLang(lang);
            setLocalization(lang);
            delay();
        });

        binding.btnEnglish.setOnClickListener(view->{
            lang = Constants.ENGLISH_VALUE;
            pref.saveLang(lang);
            setLocalization(lang);
            delay();
        });
    }

    //CheckLanguage region
    private void getLanguage() {
        if (pref.getLang().equalsIgnoreCase(Constants.ARABIC_VALUE)) {
            lang = Constants.ARABIC_VALUE;

        } else {
            lang = Constants.ENGLISH_VALUE;
        }
        setLocalization(lang);
    }

    private void setLocalization(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

    }
    //endregion

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void delay() {
        showLoading(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAppData();
                //checkLoginStatus();
            }
        }, 3000);
    }

    private void checkLoginStatus() {
        Boolean isLogin = pref.getLoginStatus();
        Utils.log("loginStatus: " + isLogin);

        if (!isLogin) {
            navigationController.navigateToAfterSplashActivity(SplashActivity.this);
            if (TextUtils.isEmpty(pref.getLang())) {
                //navigationController.navigateToAfterSplashActivity(SplashActivity.this);
            } else {
                //navigationController.navigateToLoginActivity(SplashActivity.this);
            }
        } else {
            navigationController.navigateToMainActivity(SplashActivity.this);
        }
    }

    private void getAppData() {
        if (connectivity.isConnected()) {
            loadData(Config.API_KEY);
        } else {
            showMessage(R.string.massage_no_internet);
        }
    }

    private void loadData(String token) {

        splashViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);

        splashViewModel.setAppData(token, lang);
        splashViewModel.isLoading = true;

        //splashViewModel.getAppData().removeObservers(this);
        splashViewModel.getAppData().observe(this, result -> {
            if (result != null) {

                switch (result.status) {
                    case LOADING:
                        // Loading State
                        // Data are from Local DB
                        Utils.log("Status LOADING ");
                        showLoading(true);

                        break;
                    case SUCCESS:
                        // Success State
                        // Data are from Server
                        Utils.log("Status SUCCESS ");

                        //Get New Data...
                        if (result.data != null) {
                            if (!result.data.getStatus()) {
                                if (result.data.getCode() == Config.API_IN_VALID_TOKEN
                                        || result.data.getCode() == Config.API_ACCOUNT_STOPPED_CODE
                                        || result.data.getCode() == Config.API_VALIDATION_CODE
                                        || result.data.getCode() == Config.API_UNAUTHENTICATED_CODE
                                ) {
                                    showMessage(result.data.getMessage());
                                }
                            } else {
                                if (result.data.getCode() == Config.API_SUCCESS_CODE) {
                                    //Toast.makeText(this, "" + result.data.getMessage(), Toast.LENGTH_SHORT).show();
                                    Utils.log("Splash: " + result.data.getData());
                                    checkLoginStatus();
                                } else {
                                    Utils.log(TAG + " Status is true but not code 200");
                                }
                            }
                        }

                        // hide loading bar
                        showLoading(false);
                        splashViewModel.setLoadingState(false);
                        break;
                    case ERROR:
                        // Error State
                        Utils.log("Status ERROR ");

                        splashViewModel.setLoadingState(false);

                        // hide loading bar
                        showLoading(false);

                        break;
                    default:
                        // Default
                        splashViewModel.setLoadingState(false);

                        // hide loading bar
                        showLoading(false);

                        break;
                }

            } else {

                // Init Object or Empty Data
                Utils.log("APP Empty Data");

                // hide loading bar
                showLoading(false);
            }
        });

    }

    @Override
    protected void observeLoadStatus() {
        super.observeLoadStatus();
        Utils.log("Loading");
        splashViewModel.getLoadingState().observe(this,
                isLoading -> showLoading(isLoading));
    }

    @Override
    protected void observerErrorStatus() {
        super.observerErrorStatus();
        Utils.log("Error");
        splashViewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("Error", error);
                        Utils.log(error.getMessage());
                        showLoading(false);
                    }
                });
    }


    public void showLoading(Boolean state) {
        if (state) {
            binding.includeProgressBar.lytLoadingRoot.setVisibility(View.VISIBLE);
            binding.includeProgressBar.progressBar.show();
        } else {
            binding.includeProgressBar.lytLoadingRoot.setVisibility(View.INVISIBLE);
            binding.includeProgressBar.progressBar.hide();
        }
    }
}