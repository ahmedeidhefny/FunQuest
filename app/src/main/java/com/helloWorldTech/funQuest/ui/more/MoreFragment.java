package com.helloWorldTech.funQuest.ui.more;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.ProfileApiResponse;
import com.helloWorldTech.funQuest.databinding.FragmentHomeBinding;
import com.helloWorldTech.funQuest.databinding.FragmentMoreBinding;
import com.helloWorldTech.funQuest.ui.base.BaseFragment;
import com.helloWorldTech.funQuest.util.AutoClearedValue;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

public class MoreFragment extends BaseFragment {

    private UserViewModel userViewModel;

    private ProfileApiResponse.Data profileResponseData = null;
    private Boolean isEdit = false;

    @VisibleForTesting
    private AutoClearedValue<FragmentMoreBinding> binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMoreBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return binding.get().getRoot();
    }

    @Override
    protected void initUIAndActions() {

        binding.get().includeContent.lytAbout.setOnClickListener(view -> {
            //navigationController.na
        });

        binding.get().includeContent.lytCall.setOnClickListener(view -> {
            //navigationController.na
        });

        binding.get().includeContent.lytHelp.setOnClickListener(view -> {
            navigationController.navigateToFAGActivity(requireActivity());
        });

        binding.get().includeContent.lytRate.setOnClickListener(view -> {
            Utils.rateAction(MoreFragment.this.getActivity());
        });

        binding.get().includeContent.lytShare.setOnClickListener(view -> {
            Utils.shareAction(MoreFragment.this.getActivity());
        });

        binding.get().tvCancel.setOnClickListener(view -> {
            visibleMainContent();
        });

        binding.get().tvLogout.setOnClickListener(view -> {
            //Toast.makeText(this, getString(R.string.massage_not_supported), Toast.LENGTH_SHORT).show();
            logout();
        });

        binding.get().lytEdit.setOnClickListener(view -> {
            //visibleEditContent();
            //PopulateOldDataToUi();
        });

        binding.get().ivEditImageProfile.setOnClickListener(view -> {
            // Toast.makeText(this, getString(R.string.massage_not_supported), Toast.LENGTH_SHORT).show();
            //editProfileImage();
        });

    }

    private void logout() {
        pref.saveLoginStatus(false);
        pref.deleteToken();
        pref.deleteUserPhone();
        pref.deleteUserId();
        pref.deleteUserImage();

        userViewModel.deleteUser();
        navigationController.navigateToLoginActivity(getActivity());
    }

    private void visibleMainContent() {
        /*binding.get().tvCancel.setVisibility(View.GONE);
        binding.get().lytEdit.setVisibility(View.VISIBLE);
        binding.get().includeHeaderData.ivEditImageProfile.setVisibility(View.GONE);
        binding.get().includeContent.lytRoot.setVisibility(View.VISIBLE);
        binding.get().includeEditProfile.lytRoot.setVisibility(View.GONE);*/
    }

    @Override
    protected void initViewModels() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

        binding.get().toolbarHeaderWithSearch.tvName.setText("Profile");
        binding.get().toolbarHeaderWithSearch.ivArrowBack.setVisibility(View.GONE);

        if (getActivity().findViewById(R.id.lyt_main_headr) != null) {
            getActivity().findViewById(R.id.lyt_main_headr).setVisibility(View.GONE);
        }

        if (connectivity.isConnected()) {
            if (!userViewModel.isLoading) {
                observeProfileData();
                observeLoadStatus();
                observerErrorStatus();
            }

        } else {
            // no Internet Connection
           showMessage(getString(R.string.massage_no_internet));
        }

    }

    private void observeProfileData() {
        //userViewModel.getErrorStatus().removeObservers(this);
        //userViewModel.getLoadingState().removeObservers(this);

        userViewModel.loadProfile(token, Config.API_KEY,  language);
        userViewModel.getProfileResponse().observe(this, result -> {
            Utils.log("Success");
            if (result != null) {

                boolean status = result.getStatus();
                int code = result.getCode();
                String msg = result.getMessage();

                if (!status) {
                    showMessage(msg);
                    if (code == Config.API_IN_VALID_TOKEN
                            || code == Config.API_UNAUTHENTICATED_CODE
                            || code == Config.API_ACCOUNT_STOPPED_CODE) {
                        navigationController.navigateToLoginActivity(getActivity());
                    } else if (code == Config.API_VALIDATION_CODE) {
                    }
                } else {
                    //showMessage(msg);
                    if (code == Config.API_SUCCESS_CODE) {
                        PopulateUi(result.getData());
                    }

                }
                userViewModel.setLoadingState(false);
            }
        });

    }

    private void PopulateUi(ProfileApiResponse.Data data) {
        if (data != null) {
            isEdit = false;
            profileResponseData = data;
            binding.get().tvUsername.setText(data.getName());
            binding.get().tvMobile.setText(data.getEmail());

            Glide.with(this)
                    .load(Config.BASE_IMAGE_URl + data.getImage())
                    .placeholder(R.drawable.unknown_user)
                    .error(R.drawable.unknown_user)
                    .into(binding.get().ivProfileImage);

        }

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
        userViewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("ErrorProfile", error);
                        Utils.log(error.getMessage());
                    }
                    userViewModel.setLoadingState(false);
                    showLoading(false);
                });
    }

    public void showLoading(Boolean state) {
        if (state) {
            Utils.showCustomLoadingDialog(getActivity());
        } else {
            Utils.hideCustomLoadingDialog();
        }
    }


}