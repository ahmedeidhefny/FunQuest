package com.helloWorldTech.funQuest.ui.splashes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;

import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.databinding.ActivityAfterSplashBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;

public class AfterSplashActivity extends BaseActivity {

    private ActivityAfterSplashBinding binding;
    private HomeViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_after_splash);
        getSplashTitlesData();

        binding.btnStart.setOnClickListener(result->{
            navigationController.navigateToLoginActivity(AfterSplashActivity.this);
        });

        binding.helpLayout.setOnClickListener(result->{
            navigationController.navigateToFAGActivity(AfterSplashActivity.this);
        });

        binding.tvTerms.setPaintFlags(binding.tvTerms.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    private void getSplashTitlesData() {
        splashViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);

        splashViewModel.getLocalAppData().observe(this, new Observer<AppDataResponse>() {
            @Override
            public void onChanged(AppDataResponse result) {
                if (!TextUtils.isEmpty(result.getData().getSplash().getTitle()))
                    binding.tvSplashTitle.setText(result.getData().getSplash().getTitle());

                if (!TextUtils.isEmpty(result.getData().getSplash().getDescription()))
                    binding.tvSplashDescription.setText(result.getData().getSplash().getDescription());
            }
        });
    }
}