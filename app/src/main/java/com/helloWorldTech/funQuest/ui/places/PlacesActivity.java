package com.helloWorldTech.funQuest.ui.places;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityPlacesBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.ui.main.MainActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public ActivityPlacesBinding binding;

    private HomeViewModel homeViewModel;
    private String token = "", language = "";
    private int cityId = 0;

    private MostPlacesAdapter mostPlacesAdapter;
    private PlacesAdapter placesAdapter;

    private List<PlacesApiResponse.Places> places = new ArrayList<>();
    private List<PlacesApiResponse.MostPlaces> mostPlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_places);

        initViewModels();
        initUIAndAction();
        initAdapters();
        initData();
    }

    private void initViewModels() {
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
    }

    private void initAdapters() {
        mostPlacesAdapter = new MostPlacesAdapter(this,
                mostPlace -> handleOnItemClicked(mostPlace),
                mostPlaces);
        binding.rcvPopularPlaces.setAdapter(mostPlacesAdapter);

        placesAdapter = new PlacesAdapter(this,
                place -> handleOnItemClicked(place),
                places);
        binding.rcvPlaces.setAdapter(placesAdapter);
    }

    private void handleOnItemClicked(PlacesApiResponse.MostPlaces place) {
        navigationController.navigateToChallengesListActivity(this, place.getId());
    }

    private void handleOnItemClicked(PlacesApiResponse.Places place) {
        navigationController.navigateToChallengesListActivity(this, place.getId());
    }

    private void initUIAndAction() {
        binding.toolbarHeaderWithSearch.ivArrowBack.setOnClickListener(view->{
            onBackPressed();
        });

    }

    private void initData() {
        token = Config.TOKEN_TYPE + pref.getToken();
        language = Utils.getLanguage(pref);

        binding.toolbarHeaderWithSearch.tvName.setText("Join to Challenge - Places");

        Glide.with(this)
                .load(Config.BASE_IMAGE_URl + pref.getUserImage())
                .placeholder(R.drawable.unknown_user)
                .error(R.drawable.unknown_user)
                .into(binding.toolbarHeaderWithSearch.profileImage);

        if (getIntent() != null){
            cityId = getIntent().getIntExtra(Constants.IN_CITY_ID,0);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (connectivity.isConnected()) {
            observePlacesData();
            observeLoadStatus();
            observerErrorStatus();
        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }


    private void observePlacesData() {
        homeViewModel.getErrorStatus().removeObservers(this);
        homeViewModel.getLoadingState().removeObservers(this);
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
                        PopulateUi(result.getData());
                    }

                }
                showLoading(false);
                homeViewModel.setLoadingState(false);
            }
        });

    }

    private void PopulateUi(PlacesApiResponse.Data data) {

        if (data.getMostPlaces() != null && data.getMostPlaces().size() >0) {
            binding.tvNotMostPlaces.setVisibility(View.GONE);
            mostPlacesAdapter.replace(data.getMostPlaces());
        } else {
            binding.tvNotMostPlaces.setVisibility(View.VISIBLE);
        }

        if (data.getPlaces() != null && data.getPlaces().size() >0) {
            binding.tvNotFoundPlaces.setVisibility(View.GONE);
            placesAdapter.replace(data.getPlaces());
        } else {
            binding.tvNotFoundPlaces.setVisibility(View.VISIBLE);
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