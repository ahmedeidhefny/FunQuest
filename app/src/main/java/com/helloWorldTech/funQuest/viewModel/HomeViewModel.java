package com.helloWorldTech.funQuest.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.helloWorldTech.funQuest.data.Resource;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.AwardsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.NotificationsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.data.repository.HomeRepository;
import com.helloWorldTech.funQuest.ui.base.BaseViewModel;
import com.helloWorldTech.funQuest.util.AbsentLiveData;
import com.helloWorldTech.funQuest.util.Utils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ahmed Eid Hefny
 * @date 8/2/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class                                                                                                                                        HomeViewModel extends BaseViewModel {

    private HomeRepository homeRepository;

    // Home
    private MutableLiveData<HomeApiResponse> homeResponseLiveData = new MutableLiveData<>();

    //Award
    private MutableLiveData<AwardsApiResponse> awardResponseLiveData = new MutableLiveData<>();

    //Notifications
    private MutableLiveData<NotificationsApiResponse>  notificationsResponseLiveData = new MutableLiveData<>();

    //Places
    private MutableLiveData<PlacesApiResponse>  placesResponseLiveData = new MutableLiveData<>();

    //FAG
    private MutableLiveData<FagApiResponse> fagResponseLiveData = new MutableLiveData<>();



    // for AppData
    private final LiveData<Resource<AppDataResponse>> appDataResponseLiveData;
    private MutableLiveData<TmpDataHolder> doAppDataObj = new MutableLiveData<>();


    @Inject
    public HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;

        // Login User
        appDataResponseLiveData = Transformations.switchMap(doAppDataObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("HomeViewModel : GetAppData");
            return homeRepository.getAppData(obj.token, obj.language);
        });
    }

    //region Home

    public void setHome(String apiKey, String token, String lang) {

        homeResponseLiveData.setValue(null);

        getCompositeDisposable().add(homeRepository.getDataHome( apiKey, token, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> homeResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<HomeApiResponse> getHomeResponseLiveData() {
        return homeResponseLiveData;
    }

    //endregion

    //region Award

    public void setAward(String token, String apiKey, String lang, int page) {

        awardResponseLiveData.setValue(null);

        getCompositeDisposable().add(homeRepository.getAwards(token, apiKey, lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> awardResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<AwardsApiResponse> getAwardResponseLiveData() {
        return awardResponseLiveData;
    }

    //endregion

    //region FAG

    public void setFAG(String token, String apiKey, String lang) {

        fagResponseLiveData.setValue(null);

        getCompositeDisposable().add(homeRepository.getFAG(token, apiKey, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> fagResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<FagApiResponse> getFagResponseLiveData() {
        return fagResponseLiveData;
    }

    //endregion

    //region Places

    public void setPlaces(String token, String apiKey, int cityId, String lang) {

        placesResponseLiveData.setValue(null);

        getCompositeDisposable().add(homeRepository.getPlaces(token, apiKey, cityId, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> placesResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<PlacesApiResponse> getPlacesResponseLiveData() {
        return placesResponseLiveData;
    }

    //endregion

    //region Notifications

    public void setNotifications(String token, String apiKey, String lang, int page) {

        notificationsResponseLiveData.setValue(null);

        getCompositeDisposable().add(homeRepository.getNotifications(token, apiKey, lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> setLoadingState(true))
                .doAfterTerminate(() -> setLoadingState(false))
                .subscribe(
                        result -> notificationsResponseLiveData.setValue(result),
                        throwable -> setError(throwable)
                ));

    }

    public MutableLiveData<NotificationsApiResponse> getNotificationsResponse() {
        return notificationsResponseLiveData;
    }

    //endregion

    //region Data App
    public LiveData<Resource<AppDataResponse>> getAppData() {
        return appDataResponseLiveData;
    }

    public LiveData<AppDataResponse> getLocalAppData() {
        return homeRepository.getAppData();
    }

    public void setAppData(String token, String language) {
        setLoadingState(true);
        TmpDataHolder temp = new TmpDataHolder();
        temp.token = token;
        temp.language = language;
        this.doAppDataObj.setValue(temp);
    }

    class TmpDataHolder {
        public String token = "";
        public String language = "";
    }
    //endregion


}
