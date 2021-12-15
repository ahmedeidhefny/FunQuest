package com.helloWorldTech.funQuest.ui.home;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.databinding.FragmentHomeBinding;
import com.helloWorldTech.funQuest.ui.base.BaseFragment;
import com.helloWorldTech.funQuest.util.AutoClearedValue;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;


public class HomeFragment extends BaseFragment {

    //private final DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private HomeViewModel homeViewModel;

    @VisibleForTesting
    private AutoClearedValue<FragmentHomeBinding> binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_more, container, false);
        FragmentHomeBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return binding.get().getRoot();
    }


    @Override
    protected void initUIAndActions() {
        binding.get().lytAward.setOnClickListener(clicked->{
            //navigationController.navigateToAfterSplashActivity();
        });

        binding.get().lytJoinChallenge.setOnClickListener(clicked->{
            navigationController.navigateToCitiesActivity(getActivity());
        });

        binding.get().lytCreateChallenge.setOnClickListener(clicked->{
            navigationController.navigateToAddChallengeActivity(getActivity());
        });

        binding.get().lytHelp.setOnClickListener(clicked->{
            navigationController.navigateToFAGActivity(getActivity());
        });

        binding.get().lytTechSupport.setOnClickListener(clicked->{
            navigationController.navigateToChatActivity(getActivity());
        });


    }

    @Override
    protected void initViewModels() {

    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

        if (getActivity().findViewById(R.id.lyt_main_headr) != null) {
            getActivity().findViewById(R.id.lyt_main_headr).setVisibility(View.VISIBLE);
        }

    }
}