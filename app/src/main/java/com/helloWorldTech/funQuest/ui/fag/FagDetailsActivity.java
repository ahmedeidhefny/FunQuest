package com.helloWorldTech.funQuest.ui.fag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityFagDetailsBinding;
import com.helloWorldTech.funQuest.databinding.ActivityHelpQuestBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FagDetailsActivity extends BaseActivity {

    private static final String TAG = FagDetailsActivity.class.getSimpleName();

    public ActivityFagDetailsBinding binding;

    private FagApiResponse.Faq faq = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fag_details);

        initUIAndAction();
        initData();
    }

    private void initData() {

        binding.toolbarHeader.tvName.setText(getString(R.string.label_help));

        if (!pref.getLoginStatus() || pref.getUserImage().isEmpty()) {
            binding.toolbarHeader.profileImage.setVisibility(View.GONE);
        } else {
            binding.toolbarHeader.profileImage.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(Config.BASE_IMAGE_URl + pref.getUserImage())
                    .placeholder(R.drawable.unknown_user)
                    .error(R.drawable.unknown_user)
                    .into(binding.toolbarHeader.profileImage);
        }

        if (getIntent() != null) {
            faq = (FagApiResponse.Faq) getIntent().getSerializableExtra(Constants.IN_FAG_OBJ);

            if (faq != null) {
                binding.tvTitle.setText(faq.getTitle());
                binding.tvDesc.setText(faq.getContent());

                if (!TextUtils.isEmpty(faq.getImage())) {
                    binding.ivImage.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(Config.BASE_IMAGE_URl + faq.getImage())
                            .placeholder(R.drawable.bg_help)
                            .error(R.drawable.bg_help)
                            .into(binding.ivImage);
                } else {
                    binding.ivImage.setVisibility(View.GONE);
                }


            }

        }
    }

    private void initUIAndAction() {
        binding.toolbarHeader.ivArrowBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

}
