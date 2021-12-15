package com.helloWorldTech.funQuest.ui.cities;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityCitiesBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends BaseActivity {

    private static final String TAG = CitiesActivity.class.getSimpleName();

    public ActivityCitiesBinding binding;

    private HomeViewModel homeViewModel;
    private String token = "", language = "";

    private MostCitiesAdapter mostCityAdapter;
    private CitiesAdapter citiesAdapter;

    private List<HomeApiResponse.Cities> cities = new ArrayList<>();
    private List<HomeApiResponse.MostCities> mostCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cities);

        initViewModels();
        initUIAndAction();
        initAdapters();
        initData();
    }

    private void initViewModels() {
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
    }

    private void initAdapters() {
        mostCityAdapter = new MostCitiesAdapter(this,
                mostCity -> handleOnItemClicked(mostCity),
                mostCities);
        binding.rcvPopularCities.setAdapter(mostCityAdapter);

        citiesAdapter = new CitiesAdapter(this,
                city -> handleOnItemClicked(city),
                cities);
        binding.rcvCites.setAdapter(citiesAdapter);
    }

    private void handleOnItemClicked(HomeApiResponse.MostCities mostCity) {
        navigationController.navigateToPlacesActivity(this, mostCity.getId());
    }

    private void handleOnItemClicked(HomeApiResponse.Cities city) {
        navigationController.navigateToPlacesActivity(this, city.getId());
    }

    private void initUIAndAction() {
        binding.toolbarHeaderWithSearch.ivArrowBack.setOnClickListener(view->{
            onBackPressed();
        });

    }

    private void initData() {
        token = Config.TOKEN_TYPE + pref.getToken();
        language = Utils.getLanguage(pref);

        binding.toolbarHeaderWithSearch.tvName.setText("Join to Challenge - Cities");


        Glide.with(this)
                .load(Config.BASE_IMAGE_URl + pref.getUserImage())
                .placeholder(R.drawable.unknown_user)
                .error(R.drawable.unknown_user)
                .into(binding.toolbarHeaderWithSearch.profileImage);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (connectivity.isConnected()) {
            observeHomeData();
            observeLoadStatus();
            observerErrorStatus();
        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }


    private void observeHomeData() {
        homeViewModel.getErrorStatus().removeObservers(this);
        homeViewModel.getLoadingState().removeObservers(this);
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
                showLoading(false);
                homeViewModel.setLoadingState(false);
            }
        });

    }

    private void PopulateUi(HomeApiResponse.Data data) {

        if (data.getMostCities() != null && data.getMostCities().size() > 0) {
            binding.tvNotMostCity.setVisibility(View.GONE);
            mostCityAdapter.replace(data.getMostCities());
        } else {
            binding.tvNotMostCity.setVisibility(View.VISIBLE);
        }

        if (data.getCities() != null && data.getCities().size() > 0) {
            binding.tvNotCity.setVisibility(View.GONE);
            citiesAdapter.replace(data.getCities());
        } else {
            binding.tvNotCity.setVisibility(View.VISIBLE);
        }
    }

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
        homeViewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("ErrorHome", error);
                        Utils.log(error.getMessage());
                    }
                    homeViewModel.setLoadingState(false);
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
