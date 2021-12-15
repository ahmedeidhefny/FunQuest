package com.helloWorldTech.funQuest.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.AwardsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HistoryApiResponse;
import com.helloWorldTech.funQuest.databinding.FragmentHistoryBinding;
import com.helloWorldTech.funQuest.ui.award.AwardFragment;
import com.helloWorldTech.funQuest.ui.base.BaseFragment;
import com.helloWorldTech.funQuest.util.AutoClearedValue;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.GameViewModel;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends BaseFragment {

    @VisibleForTesting
    private AutoClearedValue<FragmentHistoryBinding> binding;

    private static final String TAG = AwardFragment.class.getSimpleName();

    private GameViewModel viewModel;
    private HistoryAdapter adapter;

    private int currentPage = 1, lastPage = 1, PAGING_LIMIT = 0, totalListItems = 0;
    private final List<HistoryApiResponse.Items> items = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHistoryBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return binding.get().getRoot();
    }
    @Override
    protected void initUIAndActions() {

        binding.get().rcvHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastPosition == adapter.getItemCount() - 1) {

                        if (!viewModel.forceEndLoading) {

                            if (currentPage <= lastPage) {
                                if (connectivity.isConnected()) {
                                    viewModel.loadingDirection = Utils.LoadingDirection.bottom;
                                    viewModel.offset = viewModel.offset + PAGING_LIMIT;
                                    currentPage++;
                                    getHistory();
                                } else {
                                    // no Internet Connection
                                    showMessage(getString(R.string.massage_no_internet));
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void initViewModels() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(GameViewModel.class);
    }

    @Override
    protected void initAdapters() {
        adapter = new HistoryAdapter(getActivity(),
                award -> handleOnItemClicked(award),
                items);
        binding.get().rcvHistory.setAdapter(adapter);

        if (viewModel.loadingDirection == Utils.LoadingDirection.top) {

            if (binding.get().rcvHistory != null) {

                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        binding.get().rcvHistory.getLayoutManager();

                if (layoutManager != null) {
                    layoutManager.scrollToPosition(0);
                }
            }
        }


    }

    private void handleOnItemClicked(HistoryApiResponse.Items game) {
        //navigationController.navigateToGameDetailsActivity(this, game.getId());
    }


    @Override
    protected void initData() {

        if (getActivity().findViewById(R.id.lyt_main_headr) != null) {
            getActivity().findViewById(R.id.lyt_main_headr).setVisibility(View.GONE);
        }

        binding.get().toolbarHeaderWithSearch.tvName.setText("Game History");
        binding.get().toolbarHeaderWithSearch.ivArrowBack.setVisibility(View.GONE);

        Glide.with(this)
                .load(Config.BASE_IMAGE_URl + pref.getUserImage())
                .placeholder(R.drawable.unknown_user)
                .error(R.drawable.unknown_user)
                .into(binding.get().toolbarHeaderWithSearch.profileImage);

        lastPage = currentPage;

    }

    @Override
    public void onStart() {
        super.onStart();
        getHistory();
    }

    private void getHistory() {
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
        viewModel.getErrorStatus().removeObservers(this);
        viewModel.getLoadingState().removeObservers(this);
        viewModel.setHistory(token, Config.API_KEY, language, currentPage);
        viewModel.getHistoryResponseLiveData().observe(this, result -> {
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
                        navigationController.navigateToLoginActivity(getActivity());
                    } else if (code == Config.API_VALIDATION_CODE) {
                    } else if (code == Config.API_NOT_VERIFY_ACCOUNT_CODE) {
                        navigationController.navigateToOtpActivity(getActivity(), "");
                    }
                } else {
                    //showMessage(msg);
                    if (code == Config.API_SUCCESS_CODE) {
                        PopulateUi(result.getData().getGames());
                    }

                }
                showLoading(false);
                viewModel.setLoadingState(false);
            } else {
                if (viewModel.offset > 1) {
                    viewModel.forceEndLoading = true;
                }
            }
        });

    }

    private void PopulateUi(HistoryApiResponse.Games data) {

        lastPage = data.getLastPage();
        //currentPage = data.getGames().getCurrentPage();
        PAGING_LIMIT = data.getPerPage();
        totalListItems = data.getTotal();
        int count = 1;

        if (data.getItems() != null && !data.getItems().isEmpty()) {
            binding.get().tvNotFound.setVisibility(View.GONE);

            List<HistoryApiResponse.Items> awardList = data.getItems();
            //showMessage("size " + orders.size());

            if (this.items.size() >= totalListItems) {
                viewModel.forceEndLoading = true;
                return;
            }

            if (data.getItems().size() < PAGING_LIMIT) {
                viewModel.forceEndLoading = true;
            }

            this.items.addAll(awardList);
            //showOrdersUi();
            adapter.replace(this.items);
            count++;

        } else {
            if (count == 1) {
                Utils.log(TAG + "Empty");
                //showNoItemCartsUi();
                binding.get().tvNotFound.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    protected void observeLoadStatus() {
        super.observeLoadStatus();
        Utils.log("Loading");
        viewModel.setLoadingState(true);

        if (viewModel.forceEndLoading) {
            viewModel.forceEndLoading = false;
        }

        viewModel.getLoadingState().observe(this,
                isLoading -> showLoading(isLoading));
    }

    @Override
    protected void observerErrorStatus() {
        super.observerErrorStatus();
        Utils.log("Error");
        viewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("ErrorHome", error);
                        Utils.log(error.getMessage());
                    }
                    viewModel.setLoadingState(false);
                    viewModel.forceEndLoading = true;
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