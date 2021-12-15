package com.helloWorldTech.funQuest.ui.fag;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityHelpQuestBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HelpQuestActivity extends BaseActivity {


    private static final String TAG = HelpQuestActivity.class.getSimpleName();

    public ActivityHelpQuestBinding binding;

    private HomeViewModel homeViewModel;
    private String token = "", language = "";

    private FagAdapter fagAdapter;
    private List<FagApiResponse.Faq> faqs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help_quest);

        initViewModels();
        initUIAndAction();
        initAdapters();
        initData();
    }

    private void initViewModels() {
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
    }

    private void initAdapters() {
        fagAdapter = new FagAdapter(this,
                faq -> handleOnItemClicked(faq),
                faqs);
        binding.rcvFag.setAdapter(fagAdapter);

    }

    private void handleOnItemClicked(FagApiResponse.Faq fag) {
        navigationController.navigateToFAGActivity(this, fag);
    }

    private void initUIAndAction() {

        binding.toolbarHeader.ivArrowBack.setOnClickListener(view->{
            onBackPressed();
        });

    }

    private void initData() {
        token = Config.TOKEN_TYPE + pref.getToken();
        language = Utils.getLanguage(pref);

        binding.toolbarHeader.tvName.setText(getString(R.string.label_help));

        if (!pref.getLoginStatus() || pref.getUserImage().isEmpty()){
            binding.toolbarHeader.profileImage.setVisibility(View.GONE);
        }else {
            binding.toolbarHeader.profileImage.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(Config.BASE_IMAGE_URl + pref.getUserImage())
                    .placeholder(R.drawable.unknown_user)
                    .error(R.drawable.unknown_user)
                    .into(binding.toolbarHeader.profileImage);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (connectivity.isConnected()) {
            observeData();
            observeLoadStatus();
            observerErrorStatus();
        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }


    private void observeData() {
        homeViewModel.getErrorStatus().removeObservers(this);
        homeViewModel.getLoadingState().removeObservers(this);
        homeViewModel.setFAG(token, Config.API_KEY, language);
        homeViewModel.getFagResponseLiveData().observe(this, result -> {
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

    private void PopulateUi(FagApiResponse.Data data) {

        if (data.getFaq() != null && data.getFaq().size() >0) {
            binding.tvNotFoundOffers.setVisibility(View.GONE);
            fagAdapter.replace(data.getFaq());
        } else {
            binding.tvNotFoundOffers.setVisibility(View.VISIBLE);
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