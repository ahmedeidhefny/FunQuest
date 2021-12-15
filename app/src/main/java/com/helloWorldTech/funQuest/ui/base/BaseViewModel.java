package com.helloWorldTech.funQuest.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.helloWorldTech.funQuest.util.Utils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class BaseViewModel extends ViewModel {

    public Utils.LoadingDirection loadingDirection = Utils.LoadingDirection.none;

    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
    private final MutableLiveData<Throwable> onError = new  MutableLiveData<>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public int offset = 0;

    public int page = 1;

    public boolean forceEndLoading = false;
    public boolean isLoading = false;


    //region For loading status
    public void setLoadingState(Boolean state) {
        isLoading = state;
        loadingState.setValue(state);
    }

    //region For loading status
    public void setError(Throwable error) {
        onError.setValue(error);
    }

    public MutableLiveData<Throwable> getErrorStatus() {
        return onError;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable!= null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }


    //endregion
}
