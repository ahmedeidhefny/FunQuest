package com.helloWorldTech.funQuest.ui.challenge;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GamesApiResponse;
import com.helloWorldTech.funQuest.databinding.ActivityGameListBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.GameViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends BaseActivity {

    private static final String TAG = GameListActivity.class.getSimpleName();

    public ActivityGameListBinding binding;

    private GameViewModel gameViewModel;
    private String token = "", language = "";
    private int placeId = 0;

    private GamesAdapter gamesAdapter;

    private int currentPage = 1, lastPage = 1, PAGING_LIMIT = 0, totalListItems = 0;
    private List<GamesApiResponse.Items> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_list);

        initViewModels();
        initUIAndAction();
        initAdapters();
        initData();
    }

    private void initViewModels() {
        gameViewModel = new ViewModelProvider(this, viewModelFactory).get(GameViewModel.class);
    }

    private void initAdapters() {
        gamesAdapter = new GamesAdapter(this,
                game -> handleOnItemClicked(game),
                games);
        binding.rcvChallenges.setAdapter(gamesAdapter);

        if (gameViewModel.loadingDirection == Utils.LoadingDirection.top) {

            if (binding.rcvChallenges != null) {

                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        binding.rcvChallenges.getLayoutManager();

                if (layoutManager != null) {
                    layoutManager.scrollToPosition(0);
                }
            }
        }
    }

    private void handleOnItemClicked(GamesApiResponse.Items game) {
        navigationController.navigateToGameDetailsActivity(this, game.getId());
    }

    private void initUIAndAction() {

        binding.toolbarHeader.ivArrowBack.setOnClickListener(view->{
            onBackPressed();
        });

        binding.rcvChallenges.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastPosition == gamesAdapter.getItemCount() - 1) {

                        if (!gameViewModel.forceEndLoading) {

                            if (currentPage <= lastPage) {
                                if (connectivity.isConnected()) {
                                    gameViewModel.loadingDirection = Utils.LoadingDirection.bottom;
                                    gameViewModel.offset = gameViewModel.offset + PAGING_LIMIT;
                                    currentPage++;
                                    getGames();
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

    private void initData() {
        token = Config.TOKEN_TYPE + pref.getToken();
        language = Utils.getLanguage(pref);
        lastPage = currentPage;

        binding.toolbarHeader.tvName.setText("Join to Game");

        if (getIntent() != null) {
            placeId = getIntent().getIntExtra(Constants.IN_PLACE_ID, 0);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        getGames();
    }

    private void getGames() {
        if (connectivity.isConnected()) {
            observeGamesData();
            observeLoadStatus();
            observerErrorStatus();
        } else {
            // no Internet Connection
            showMessage(getString(R.string.massage_no_internet));
        }
    }


    private void observeGamesData() {
        gameViewModel.getErrorStatus().removeObservers(this);
        gameViewModel.getLoadingState().removeObservers(this);
        gameViewModel.setGame(token, Config.API_KEY, placeId, language, currentPage);
        gameViewModel.getGameResponseLiveData().observe(this, result -> {
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
                    //showMessage(msg);
                    if (code == Config.API_SUCCESS_CODE) {
                        PopulateUi(result.getData());
                    }

                }
                showLoading(false);
                gameViewModel.setLoadingState(false);
            } else {
                if (gameViewModel.offset > 1) {
                    gameViewModel.forceEndLoading = true;
                }
            }
        });

    }

    private void PopulateUi(GamesApiResponse.Data data) {

        if (data.getCity() != null) {
            binding.toolbarHeader.tvSubName.setVisibility(View.VISIBLE);
            binding.toolbarHeader.tvSubName.setText(data.getCity().getName());

            if (data.getPlace() != null) {
                binding.toolbarHeader.tvSubName.append(" - " + data.getPlace().getName());
            }
        }

        lastPage = data.getGames().getLastPage();
        //currentPage = data.getGames().getCurrentPage();
        PAGING_LIMIT = data.getGames().getPerPage();
        totalListItems = data.getGames().getTotal();
        int count = 1;

        if (data.getGames().getItems() != null && !data.getGames().getItems().isEmpty()) {
            binding.tvNotFoundOffers.setVisibility(View.GONE);

            List<GamesApiResponse.Items> gameList = data.getGames().getItems();
            //showMessage("size " + orders.size());

            if (games.size() >= totalListItems) {
                gameViewModel.forceEndLoading = true;
                return;
            }

            if (data.getGames().getItems().size() < PAGING_LIMIT) {
                gameViewModel.forceEndLoading = true;
            }

            games.addAll(gameList);
            //showOrdersUi();
            gamesAdapter.replace(games);
            count++;

        } else {
            if (count == 1) {
                Utils.log(TAG + "Empty");
                //showNoItemCartsUi();
                binding.tvNotFoundOffers.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    protected void observeLoadStatus() {
        super.observeLoadStatus();
        Utils.log("Loading");
        gameViewModel.setLoadingState(true);

        if (gameViewModel.forceEndLoading) {
            gameViewModel.forceEndLoading = false;
        }

        gameViewModel.getLoadingState().observe(this,
                isLoading -> showLoading(isLoading));
    }

    @Override
    protected void observerErrorStatus() {
        super.observerErrorStatus();
        Utils.log("Error");
        gameViewModel.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        Utils.errorLog("ErrorHome", error);
                        Utils.log(error.getMessage());
                    }
                    gameViewModel.setLoadingState(false);
                    gameViewModel.forceEndLoading = true;
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