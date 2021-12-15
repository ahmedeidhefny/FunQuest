package com.helloWorldTech.funQuest.ui.auth.register;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.databinding.ActivityRegisterBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private UserViewModel userViewModel;
    private HomeViewModel homeViewModel;
    private ActivityRegisterBinding binding;

    private List<String> ageRangeListStrings = new ArrayList<>();
    private List<AppDataResponse.AgeRange> ageRangeList = new ArrayList<>();

    private String phone, password, confirmPassword, name, ageRange, language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        initViewModels();
        initUIAndActions(binding);
        initData();
    }

    private void initViewModels() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
    }

    private void initUIAndActions(ActivityRegisterBinding binding) {
        binding.btnRegister.setOnClickListener(v -> {
            pushRegisterBtn();
        });

        binding.btnLogin.setOnClickListener(v -> {
            navigationController.navigateToLoginActivity(RegisterActivity.this);
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

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etName.setError(null);
                binding.tilName.setError(null);
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

        binding.spAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //AppDataResponse.AgeRange ageRangeObj = (AppDataResponse.AgeRange) parent.getItemAtPosition(position);
                TextView textView = (TextView) (parent.getSelectedView());
                textView.setTextColor(getResources().getColor(R.color.white));
                ageRange = ageRangeList.get(position).getKey();
                //showMessage(ageRange);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.helpLayout.setOnClickListener(v->{
            navigationController.navigateToFAGActivity(this);
        });

    }

    private void getLocalAgeRange() {
        homeViewModel.getLocalAppData().observe(this, result -> {

            List<AppDataResponse.AgeRange> list = result.getData().getAgeRange();
            if ( list != null) {
                this.ageRangeList = list;
                for (int i = 0; i < list.size(); i++) {
                    ageRangeListStrings.add(i, list.get(i).getValue());
                }

                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter adapter = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, ageRangeListStrings);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //Setting the ArrayAdapter data on the Spinner
                binding.spAge.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        navigationController.navigateToLoginActivity(this);
    }

    private void initData() {
        language = Utils.getLanguage(pref);
        binding.tvTerms.setPaintFlags(binding.tvTerms.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        getLocalAgeRange();
    }

    // region user registration
    private void pushRegisterBtn() {
        if (connectivity.isConnected()) {

            this.phone = binding.etPhone.getText().toString().trim();
            this.password = binding.etPassword.getText().toString().trim();
            this.confirmPassword = binding.etConfirmPassword.getText().toString().trim();
            this.name = binding.etName.getText().toString();

            if (!userViewModel.isLoading) {
                doRegisterSubmit();
            }

        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    /**
     * Submit register
     */
    private void doRegisterSubmit() {
        userViewModel.getResponseRegisterLiveData().removeObservers(this);
        userViewModel.getLoadingState().removeObservers(this);
        userViewModel.getErrorStatus().removeObservers(this);

        userViewModel.doUserRegister(Config.API_KEY, language, this.phone, this.password, this.confirmPassword, this.name, this.ageRange);
        userViewModel.getResponseRegisterLiveData().observe(this, result -> {
            switch (result.status) {
                case LOADING:

                    // Loading State
                    Utils.log(TAG + " Status LOADING ");

                    showLoading(true);
                    userViewModel.setLoadingState(true);

                    break;
                case SUCCESS:
                    onSuccessRegisterResult(result.data);

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
                    // Default

                    // hide loading bar
                    showLoading(false);
                    userViewModel.setLoadingState(false);
                    break;

            }
        });

    }

    private void onSuccessRegisterResult(JsonElement data) {
        if (!data.isJsonNull()) {

            Utils.log(TAG + " registerResponse= " + data.toString());

            JsonObject mainJsonObject = data.getAsJsonObject();
            boolean status = mainJsonObject.get("status").getAsBoolean();
            int serverCode = mainJsonObject.get("code").getAsInt();
            String msg = mainJsonObject.get("message").getAsString();

            if (!status) {
                Utils.log(TAG + " Status False");
                if (serverCode == Config.API_IN_VALID_TOKEN
                        || serverCode == Config.API_ACCOUNT_STOPPED_CODE) {
                } else if (serverCode == Config.API_VALIDATION_CODE) {
                    JsonObject dataJsonObject = mainJsonObject.get("data").getAsJsonObject();
                    JsonObject VErrorJsonObject = dataJsonObject.get("validation_errors").getAsJsonObject();
                    PopulateRegisterErrorUi(VErrorJsonObject);

                } else if (serverCode == Config.API_NOT_VERIFY_ACCOUNT_CODE) {
                    navigationController.navigateToOtpActivity(this, this.phone);
                }
                Utils.showToast(msg, this);

            } else {
                Utils.log(TAG + " Status True");
                if (serverCode == Config.API_SUCCESS_CODE) {
                    PopulateRegisterErrorUi(null);
                    Utils.showToast(msg, this);
                    navigationController.navigateToOtpActivity(RegisterActivity.this, this.phone);
                } else {
                    Utils.showToast(msg, this);
                    Utils.log(TAG + " Status is true but not code 200");
                }

            }

        } else {
            Utils.log(TAG + " Empty Register Data");
        }
    }

    private void PopulateRegisterErrorUi(JsonObject vErrorJsonObject) {
        if (!vErrorJsonObject.isJsonNull() && vErrorJsonObject != null) {
            if (!vErrorJsonObject.isJsonNull()) {
                if (vErrorJsonObject.has("mobile")) {
                    String mobile = vErrorJsonObject.get("mobile").getAsString();
                    if (!TextUtils.isEmpty(mobile)) {
                        binding.tilPhone.setError(mobile);
                    }
                }

                if (vErrorJsonObject.has("password")) {
                    String password = vErrorJsonObject.get("password").getAsString();
                    if (!TextUtils.isEmpty(password)) {
                        binding.tilPassword.setError(password);
                    }
                }

                if (vErrorJsonObject.has("name")) {
                    String name = vErrorJsonObject.get("name").getAsString();
                    if (!TextUtils.isEmpty(name)) {
                        binding.tilName.setError(name);
                    }
                }


                if (vErrorJsonObject.has("age_range")) {
                    String age_range = vErrorJsonObject.get("age_range").getAsString();
                    if (!TextUtils.isEmpty(age_range)) {
                        binding.tilAge.setError(age_range);
                    }
                }
            }

        } else {

            binding.tilName.setError(null);
            binding.tilName.setError(null);
            binding.tilPassword.setError(null);
            binding.tilPhone.setError(null);
            binding.tilConfirmPassword.setError(null);

            binding.etName.setError(null);
            binding.etPassword.setError(null);
            binding.etPhone.setError(null);
            binding.etConfirmPassword.setError(null);
        }
    }

    //endregion

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
        userViewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("Error", error);
                        Utils.log(error.getMessage());
                        Utils.showToast(error.getMessage(), this);
                    }
                });
        userViewModel.setLoadingState(false);
        showLoading(false);
    }

    public void showLoading(Boolean state) {
        if (state) {
            Utils.showCustomLoadingDialog(this);
        } else {
            Utils.hideCustomLoadingDialog();
        }
    }

}