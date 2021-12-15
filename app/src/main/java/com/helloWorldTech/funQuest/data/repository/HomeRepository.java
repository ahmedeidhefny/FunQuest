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
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.NotificationsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.util.Utils;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeRepository extends BaseRepository {

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
    protected HomeRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db) {
        super(apiService, appExecutors, db);
        this.apiService = apiService;
        this.appDataDao = db.appDataDao();
    }


    public LiveData<Resource<AppDataResponse>> getAppData(String token, String lang) {

        return new NetworkBoundResource<AppDataResponse, AppDataResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull AppDataResponse appDataResponse) {

                Utils.log("4- SaveCallResult of App Data.");

                try {
                    db.runInTransaction(() -> {

                        // clear user login data
                        appDataDao.delete();

                        // insert user data
                        appDataDao.insert(appDataResponse);


                    });
                } catch (Exception ex) {
                    Utils.errorLog("Login Error at ", ex);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable AppDataResponse user) {
                Utils.log("2- shouldFetch.");
                //for user login, always should fetch
                return true;
            }

            @NonNull
            @Override
            protected LiveData<AppDataResponse> loadFromDb() {
                Utils.log("1- Load User  data from database.");

                return appDataDao.getData();

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AppDataResponse>> createCall() {
                Utils.log("3- Call API Service.");
                return apiService.getAppData(token, lang);
            }

            @Override
            protected void onFetchFailed(String message) {
                Utils.log("Fetch Failed.");
            }
        }.asLiveData();
    }

    public LiveData<AppDataResponse> getAppData() {
        return appDataDao.getData();
    }

    public Observable<HomeApiResponse> getDataHome(String apiKey, String token, String lang) {
        return apiService.getHomeData(apiKey, token, lang);
    }


    public Observable<NotificationsApiResponse> getNotifications(String token, String apiKey, String lang, int page) {
        return apiService.getNotifications(token, apiKey, lang, page);
    }

    public Observable<AwardsApiResponse> getAwards(String token, String apiKey, String lang, int page) {
        return apiService.getAwards(token, apiKey, lang, page);
    }

    public Observable<FagApiResponse> getFAG(String token, String apiKey, String lang) {
        return apiService.getFAG(token, apiKey, lang);
    }


    public Observable<PlacesApiResponse> getPlaces(String token, String apiKey, int cityId, String lang) {
        return apiService.getPlaces(token, apiKey, cityId, lang);
    }

}
