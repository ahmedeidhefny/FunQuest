package com.helloWorldTech.funQuest.ui.challenge;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.CreateChallengeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityAddChallengeBinding;
import com.helloWorldTech.funQuest.databinding.ActivityRegisterBinding;
import com.helloWorldTech.funQuest.ui.auth.register.RegisterActivity;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.GameViewModel;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddChallengeActivity extends BaseActivity {

    private static final String TAG = AddChallengeActivity.class.getSimpleName();
    private GameViewModel gameViewModel;
    private HomeViewModel homeViewModel;
    private ActivityAddChallengeBinding binding;

    private List<HomeApiResponse.Cities> citiesList = new ArrayList<>();
    private List<PlacesApiResponse.Places> placedList = new ArrayList<>();

    private List<String> citiesListStrings = new ArrayList<>();
    private List<String> placedListStrings = new ArrayList<>();

    private String date = "", email = "", token = "", language = "";
    private int placeId , cityId , teamCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_challenge);

        initViewModels();
        initUIAndActions();
        initData();
    }

    private void initViewModels() {
        gameViewModel = new ViewModelProvider(this, viewModelFactory).get(GameViewModel.class);
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
    }

    private void initUIAndActions() {


        binding.toolbarHeaderWithSearch.ivArrowBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.tvDate.setOnClickListener(result -> {
            binding.tilDate.setError(null);
            showDateCalender();
        });

        binding.btnCreate.setOnClickListener(result -> {
            pushRegisterBtn();
        });


        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etEmail.setError(null);
                binding.tilEmail.setError(null);
            }
        });

        binding.etTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etTeamNumber.setError(null);
                binding.tilTeamNumber.setError(null);
            }
        });

        binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.tilCity.setError(null);
                //AppDataResponse.AgeRange ageRangeObj = (AppDataResponse.AgeRange) parent.getItemAtPosition(position);
                TextView textView = (TextView) (parent.getSelectedView());
                textView.setTextColor(getResources().getColor(R.color.white));
                cityId = citiesList.get(position).getId();
                getPlaces();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.tilPlace.setError(null);
                TextView textView = (TextView) (parent.getSelectedView());
                textView.setTextColor(getResources().getColor(R.color.white));
                placeId = placedList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initData() {
        language = Utils.getLanguage(pref);
        token = Config.TOKEN_TYPE + pref.getToken();

        binding.toolbarHeaderWithSearch.tvName.setText(getString(R.string.label_createChallege));


        Glide.with(this)
                .load(Config.BASE_IMAGE_URl + pref.getUserImage())
                .placeholder(R.drawable.unknown_user)
                .error(R.drawable.unknown_user)
                .into(binding.toolbarHeaderWithSearch.profileImage);

        showLoading(true);

        getCities();

        //getPlaces();
    }

    //region Cities
    private void getCities() {
        if (connectivity.isConnected()) {
            observeHomeData();
            observerErrorStatus();
        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    private void observeHomeData() {
        Utils.log("HomeToken: " + token);

        homeViewModel.setHome(Config.API_KEY, token, language);
        homeViewModel.getHomeResponseLiveData().observe(this, result -> {
            Utils.log("Success");
            if (result != null) {
                String msg = result.getMessage();
                boolean status = result.getStatus();
                int code = result.getCode();

                if (!status) {
                    showMessage(msg);
                    if (code == Config.API_IN_VALID_TOKEN
                            || code == Config.API_UNAUTHENTICATED_CODE
                            || code == Config.API_ACCOUNT_STOPPED_CODE) {
                        navigationController.navigateToLoginActivity(this);
                    } else if (code == Config.API_VALIDATION_CODE) {
                    } else if (code == Config.API_NOT_VERIFY_ACCOUNT_CODE) {
                        navigationController.navigateToOtpActivity(this, "");
                    }
                } else {
                    if (code == Config.API_SUCCESS_CODE) {
                        PopulateUi(result.getData());
                    }

                }
            }
        });

    }

    private void PopulateUi(HomeApiResponse.Data data) {
        if (data.getCities() != null && data.getCities().size() > 0) {
            citiesList = data.getCities();
            setCitiesAdapter();
        }
    }

    private void setCitiesAdapter() {

        if (citiesList != null) {
            for (int i = 0; i < citiesList.size(); i++) {
                citiesListStrings.add(i, citiesList.get(i).getName());
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, citiesListStrings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Setting the ArrayAdapter data on the Spinner
            binding.spCity.setAdapter(adapter);
        }

    }
    //endregion

    //region Places
    private void getPlaces() {
        if (connectivity.isConnected()) {
            observePlacesData();
            observerErrorStatus();
        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    private void observePlacesData() {
        Utils.log("HomeToken: " + token);

        homeViewModel.setPlaces(token, Config.API_KEY, cityId, language);
        homeViewModel.getPlacesResponseLiveData().observe(this, result -> {
            Utils.log("Success");
            if (result != null) {
                String msg = result.getMessage();
                boolean status = result.getStatus();
                int code = result.getCode();

                if (!status) {
                    showMessage(msg);
                    if (code == Config.API_IN_VALID_TOKEN
                            || code == Config.API_UNAUTHENTICATED_CODE
                            || code == Config.API_ACCOUNT_STOPPED_CODE) {
                        navigationController.navigateToLoginActivity(this);
                    } else if (code == Config.API_VALIDATION_CODE) {
                    } else if (code == Config.API_NOT_VERIFY_ACCOUNT_CODE) {
                        navigationController.navigateToOtpActivity(this, "");
                    }
                } else {
                    if (code == Config.API_SUCCESS_CODE) {
                        PopulatePlacesUi(result.getData());
                    }

                }
                showLoading(false);
                homeViewModel.setLoadingState(false);
            }
        });

    }

    private void PopulatePlacesUi(PlacesApiResponse.Data data) {
        if (data.getPlaces() != null && data.getPlaces().size() > 0) {
            placedList = data.getPlaces();
            setPlacesAdapter();
        }
    }

    private void setPlacesAdapter() {

        if (placedList != null) {
            for (int i = 0; i < placedList.size(); i++) {
                placedListStrings.add(i, placedList.get(i).getName());
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, placedListStrings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Setting the ArrayAdapter data on the Spinner
            binding.spPlace.setAdapter(adapter);
        }

    }

    //endregion

    //region show Date Picker
    private void showDateCalender() {

        int mYear, mMonth, mDay;

        String oldDate = binding.tvDate.getText().toString();

        if (!TextUtils.isEmpty(oldDate) && oldDate != null) {
            Calendar cal = covertStringToCalender(oldDate);
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

        } else {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddChallengeActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        String date = getFormattedDateSimple(date_ship_millis);
                        binding.tvDate.setText(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static Calendar covertStringToCalender(String oldDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(oldDate);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return newFormat.format(new Date(dateTime));
    }
    //endregion


    // region user registration
    private void pushRegisterBtn() {
        if (connectivity.isConnected()) {

            this.email = binding.etEmail.getText().toString().trim();
            this.date = binding.tvDate.getText().toString().trim();
            String teamCountStr = binding.etTeamNumber.getText().toString();
            if (!TextUtils.isEmpty(teamCountStr))
                this.teamCount = Integer.parseInt(teamCountStr);

            if (!gameViewModel.isLoading) {
                doRegisterSubmit();
                observeLoadStatus();
                observerErrorStatus();
            }

        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }

    private void doRegisterSubmit() {
        gameViewModel.getCreateChallengeResponseLiveData().removeObservers(this);
        gameViewModel.getLoadingState().removeObservers(this);
        gameViewModel.getErrorStatus().removeObservers(this);

        gameViewModel.setCreateChallenge(token, Config.API_KEY, this.cityId, this.placeId, this.teamCount, this.date, this.email, this.language);
        gameViewModel.getCreateChallengeResponseLiveData().observe(this, result -> {
            Utils.log("Success");
            if (result != null) {
                String msg = result.getMessage();
                boolean status = result.getStatus();
                int code = result.getCode();

                showMessage(msg);
                if (!status) {
                    if (code == Config.API_IN_VALID_TOKEN
                            || code == Config.API_UNAUTHENTICATED_CODE
                            || code == Config.API_ACCOUNT_STOPPED_CODE) {
                        navigationController.navigateToLoginActivity(this);
                    } else if (code == Config.API_VALIDATION_CODE) {
                        PopulateRegisterErrorUi(result.getData().getValidationErrors());
                    } else if (code == Config.API_NOT_VERIFY_ACCOUNT_CODE) {
                        navigationController.navigateToOtpActivity(this, "");
                    }
                } else {
                    if (code == Config.API_SUCCESS_CODE) {
                        PopulateRegisterErrorUi(null);
                        navigationController.navigateToMainActivity(this);
                        // PopulatePlacesUi(result.getData());
                    }

                }
                showLoading(false);
                homeViewModel.setLoadingState(false);
            }

        });

    }


    private void PopulateRegisterErrorUi(CreateChallengeApiResponse.ValidationErrors validation_errors) {
        if (validation_errors != null) {
            if (validation_errors.getEmail() != null) {
                binding.tilEmail.setError(validation_errors.getEmail());
            }

            if (validation_errors.getTeamCount() != null) {
                binding.tilTeamNumber.setError(validation_errors.getTeamCount());
            }

            if (validation_errors.getCityId() != null) {
                binding.tilCity.setError(validation_errors.getCityId());
            }

            if (validation_errors.getPlaceId() != null) {
                binding.tilCity.setError(validation_errors.getPlaceId());
            }

            if (validation_errors.getDateTime() != null) {
                binding.tilDate.setError(validation_errors.getDateTime());
            }

        } else {
            binding.tilDate.setError(null);
            binding.tilPlace.setError(null);
            binding.tilCity.setError(null);
            binding.tilTeamNumber.setError(null);
            binding.tilEmail.setError(null);

            binding.etTeamNumber.setError(null);
            binding.etEmail.setError(null);

        }
    }

    //endregion

    @Override
    protected void observeLoadStatus() {
        super.observeLoadStatus();
        Utils.log("Loading");
        homeViewModel.setLoadingState(true);
        homeViewModel.getLoadingState().observe(this,
                isLoading -> showLoading(isLoading));
    }

    @Override
    protected void observerErrorStatus() {
        super.observerErrorStatus();
        Utils.log("Error");
        gameViewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("Error", error);
                        Utils.log(error.getMessage());
                        Utils.showToast(error.getMessage(), this);
                    }
                });
        gameViewModel.setLoadingState(false);
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