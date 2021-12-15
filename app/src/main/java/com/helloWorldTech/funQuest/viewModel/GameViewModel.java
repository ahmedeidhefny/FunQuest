package com.helloWorldTech.funQuest.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.helloWorldTech.funQuest.data.Resource;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.AwardsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.CreateChallengeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GameDetailsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GamesApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HistoryApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.MissionsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.NotificationsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.data.repository.GameRepository;
import com.helloWorldTech.funQuest.ui.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ahmed Eid Hefny
 * @date 8/2/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class GameViewModel extends BaseViewModel {

    private GameRepository gameRepository;

    //Game
    private MutableLiveData<GamesApiResponse> gameResponseLiveData = new MutableLiveData<>();

    //Game Details
    private MutableLiveData<GameDetailsApiResponse> gameDetailsResponseLiveData = new MutableLiveData<>();

    //Missions
    private MutableLiveData<MissionsApiResponse> missionResponseLiveData = new MutableLiveData<>();


    //HISTORY
    private MutableLiveData<HistoryApiResponse> historyResponseLiveData = new MutableLiveData<>();


    //Create Challenge
    private MutableLiveData<CreateChallengeApiResponse> CreateChallengeResponseLiveData = new MutableLiveData<>();



    @Inject
    public GameViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

    }

    //region Game

    public void setGame(String token, String apiKey, int placeId,String lang, int page) {

        gameResponseLiveData.setValue(null);

        getCompositeDisposable().add(gameRepository.getGameList(token, apiKey, placeId, lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> gameResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<GamesApiResponse> getGameResponseLiveData() {
        return gameResponseLiveData;
    }

    //endregion

    //region Create Challenge

    public void setCreateChallenge(String token, String apiKey, int cityId, int placeId, int countTeam,String date,String email,String lang) {

        CreateChallengeResponseLiveData.setValue(null);

        getCompositeDisposable().add(gameRepository.createChallenge(token, apiKey,cityId, placeId, countTeam, email, date, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> CreateChallengeResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<CreateChallengeApiResponse> getCreateChallengeResponseLiveData() {
        return CreateChallengeResponseLiveData;
    }

    //endregion

    //region History

    public void setHistory(String token, String apiKey,String lang, int page) {

        historyResponseLiveData.setValue(null);

        getCompositeDisposable().add(gameRepository.getHistory(token, apiKey, lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> historyResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<HistoryApiResponse> getHistoryResponseLiveData() {
        return historyResponseLiveData;
    }

    //endregion

    //region Game Details

    public void setGameDetails(String token, String apiKey, int gameId, String lang) {

        gameDetailsResponseLiveData.setValue(null);

        getCompositeDisposable().add(gameRepository.getGameDetails(token, apiKey, gameId, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> gameDetailsResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<GameDetailsApiResponse> getGameDetailsResponseLiveData() {
        return gameDetailsResponseLiveData;
    }

    //endregion

    //region Mission

    public void setMission(String token, String apiKey, int gameId, String lang) {

        missionResponseLiveData.setValue(null);

        getCompositeDisposable().add(gameRepository.getMission(token, apiKey, gameId, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> missionResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<MissionsApiResponse> getMissionResponseLiveData() {
        return missionResponseLiveData;
    }

    //endregion

}
