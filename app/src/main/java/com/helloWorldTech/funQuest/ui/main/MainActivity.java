package com.helloWorldTech.funQuest.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityMainBinding;
import com.helloWorldTech.funQuest.ui.award.AwardFragment;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.ui.history.HistoryFragment;
import com.helloWorldTech.funQuest.ui.home.HomeFragment;
import com.helloWorldTech.funQuest.ui.more.MoreFragment;
import com.helloWorldTech.funQuest.ui.notifications.NotificationsFragment;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public ActivityMainBinding binding;

    private HomeViewModel homeViewModel;
    private UserViewModel userViewModel;

    private String token = "", language = "";

    //handle back pressed values
    private int selectedFragmentId = -1;
    private Fragment selectedFragment = null;
    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initViewModels();
        initUIAndAction();
        initData();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();

        // we have only one fragment left so we would close the application with this back
        if (manager.getBackStackEntryCount() == 1) {
            Log.i(TAG, "nothing on backStack, calling super");

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finishAffinity();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Utils.showToast(getString(R.string.doubleClick), MainActivity.this);

            // this handler to delay the message and give time for user to take action to exit
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else if (manager.getBackStackEntryCount() > 1) {
            super.onBackPressed();

            selectedFragment = manager.findFragmentById(R.id.content_frame);
            if (selectedFragment instanceof MoreFragment) {
                binding.bottomNavigation.getMenu().getItem(0).setChecked(true);
                selectedFragmentId = R.id.menuMore;
            } else if (selectedFragment instanceof AwardFragment) {
                binding.bottomNavigation.getMenu().getItem(1).setChecked(true);
                selectedFragmentId = R.id.menuAward;
            } else if (selectedFragment instanceof HomeFragment) {
                binding.bottomNavigation.getMenu().getItem(2).setChecked(true);
                selectedFragmentId = R.id.menuHome;
            } else if (selectedFragment instanceof NotificationsFragment) {
                binding.bottomNavigation.getMenu().getItem(3).setChecked(true);
                selectedFragmentId = R.id.menuNotifications;
            } else if (selectedFragment instanceof HistoryFragment) {
                binding.bottomNavigation.getMenu().getItem(4).setChecked(true);
                selectedFragmentId = R.id.menuChallenge;
            }

        }


    }

    private void initViewModels() {
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

    }

    private void initUIAndAction() {

        binding.toolbarHeaderWithSearch.ivChat.setOnClickListener(r -> {
            navigationController.navigateToChatActivity(this);
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        if (selectedFragmentId != R.id.menuHome) {
                            selectedFragmentId = R.id.menuHome;
                            //selectedFragment = new HomeFragment();
                            setupFragment(new HomeFragment());
                            // viewFragment(new HomeFragment(), FRAGMENT_HOME);
                        }

                        return true;

                    case R.id.menuAward:
                        if (selectedFragmentId != R.id.menuAward) {
                            selectedFragmentId = R.id.menuAward;
                            //selectedFragment = new RestaurantFragment();
                            setupFragment(new AwardFragment());
                            //viewFragment(new RestaurantFragment(), FRAGMENT_OTHER);
                        }

                        return true;

                    case R.id.menuMore:
                        if (selectedFragmentId != R.id.menuMore) {
                            selectedFragmentId = R.id.menuMore;
                            //selectedFragment = new OffersFragment();
                            setupFragment(new MoreFragment());
                            //viewFragment(new OffersFragment(), FRAGMENT_OTHER);
                        }

                        return true;

                    case R.id.menuChallenge:
                        if (selectedFragmentId != R.id.menuChallenge) {
                            selectedFragmentId = R.id.menuChallenge;
                            //selectedFragment = new UserProfileFragment();
                            setupFragment(new HistoryFragment());
                            //viewFragment(new UserProfileFragment(), FRAGMENT_OTHER);
                        }

                        return true;

                    case R.id.menuNotifications:
                        if (selectedFragmentId != R.id.menuNotifications) {
                            selectedFragmentId = R.id.menuNotifications;
                            // selectedFragment = new SideMenuFragment();
                            setupFragment(new NotificationsFragment());
                            //viewFragment(new SideMenuFragment(), FRAGMENT_OTHER);
                        }
                        return true;

                }

                return false;
            }
        });
    }


    private void initData() {
        token = Config.TOKEN_TYPE + pref.getToken();
        language = Utils.getLanguage(pref);

        //get Home data from data api
        getHomeTitles();

        //binding.bottomNavigation.getMenu().getItem(2);
        binding.bottomNavigation.getMenu().findItem(R.id.menuHome).setChecked(true);
        setupFragment(new HomeFragment());

        getUser();

    }

    private void getUser() {

        userViewModel.getLoginUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                if (userEntity.getData().getUser() != null) {
                    binding.toolbarHeaderWithSearch.tvName.setText(
                            getString(R.string.label_welcome) + " " + userEntity.getData().getUser().getName());
                    binding.toolbarHeaderWithSearch.tvSubName.setText(
                            getString(R.string.label_last_visit) + " " + userEntity.getData().getLastLogin());

                    Glide.with(MainActivity.this)
                            .load(Config.BASE_IMAGE_URl + userEntity.getData().getUser().getImage())
                            .placeholder(R.drawable.unknown_user)
                            .error(R.drawable.unknown_user)
                            .into(binding.toolbarHeaderWithSearch.profileImage);
                }
            }
        });

    }

    private void getHomeTitles() {

        homeViewModel.getLocalAppData().observe(this, new Observer<AppDataResponse>() {
            @Override
            public void onChanged(AppDataResponse result) {
                //Toast.makeText(MainActivity.this, "" + result.getData().getHome().getTitle(), Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(result.getData().getSplash().getTitle()))
                    binding.toolbarHeaderWithSearch.includeDefaultText.tvHomeTitle.setText(result.getData().getHome().getTitle());

                if (!TextUtils.isEmpty(result.getData().getSplash().getDescription()))
                    binding.toolbarHeaderWithSearch.includeDefaultText.tvHomeDescription.setText(result.getData().getHome().getDescription());
            }
        });

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
        Utils.log("HomeToken: " + token);

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

        if (data.getGame() != null) {
            // Update the data
            binding.toolbarHeaderWithSearch.includeDefaultText.lytDefault.setVisibility(View.GONE);
            binding.toolbarHeaderWithSearch.includeLiveChallenge.lytLiveGame.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(Config.BASE_IMAGE_URl + data.getGame().getImage())
                    .into(binding.toolbarHeaderWithSearch.includeLiveChallenge.ivBackgroundImage);

            binding.toolbarHeaderWithSearch.includeLiveChallenge.tvCity.setText(data.getGame().getCityName()
                    + " - " + data.getGame().getPlaceName());
            binding.toolbarHeaderWithSearch.includeLiveChallenge.tvTile.setText(data.getGame().getName());
            binding.toolbarHeaderWithSearch.includeLiveChallenge.tvTime.setText(data.getGame().getDuration());


        } else {
            binding.toolbarHeaderWithSearch.includeDefaultText.lytDefault.setVisibility(View.VISIBLE);
            binding.toolbarHeaderWithSearch.includeLiveChallenge.lytLiveGame.setVisibility(View.GONE);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}