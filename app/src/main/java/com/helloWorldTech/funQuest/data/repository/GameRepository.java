package com.helloWorldTech.funQuest.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.helloWorldTech.funQuest.AppExecutors;
import com.helloWorldTech.funQuest.data.AppDatabase;
import com.helloWorldTech.funQuest.data.NetworkBoundResource;
import com.helloWorldTech.funQuest.data.Resource;
import com.helloWorldTech.funQuest.data.local.dao.AppDataDao;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.remote.api.ApiResponse;
import com.helloWorldTech.funQuest.data.remote.api.ApiService;
import com.helloWorldTech.funQuest.data.remote.modelResponse.AwardsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.CreateChallengeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GameDetailsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GamesApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HistoryApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.MissionsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.NotificationsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.util.Utils;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GameRepository extends BaseRepository {

    ApiService apiService;
    AppDataDao appDataDao;

    /**
     * Constructor of PSRepository
     *
     * @param apiService   API Service Instance
     * @param appExecutors Executors Instance
     * @param db           Panacea-Soft DB
     */
    @Inject
    protected GameRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db) {
        super(apiService, appExecutors, db);
        this.apiService = apiService;
        this.appDataDao = db.appDataDao();
    }


    public Observable<GamesApiResponse> getGameList(String token, String apiKey, int placeId, String lang, int page) {
        return apiService.getGameList(token, apiKey, placeId, lang, page);
    }

    public Observable<HistoryApiResponse> getHistory(String token, String apiKey, String lang, int page) {
        return apiService.getHistory(token, apiKey, lang, page);
    }

    public Observable<GameDetailsApiResponse> getGameDetails(String token, String apiKey, int gameId, String lang) {
        return apiService.getGameDetails(token, apiKey, gameId, lang);
    }

    public Observable<MissionsApiResponse> getMission(String token, String apiKey, int gameId, String lang) {
        return apiService.getMission(token, apiKey, gameId, lang);
    }

    public Observable<CreateChallengeApiResponse> createChallenge(String token, String apiKey, int cityId, int placeId, int teamCount, String email, String date, String lang) {
        return apiService.createChallenge(token, apiKey, cityId, placeId, teamCount, date, email, lang);
    }

}
