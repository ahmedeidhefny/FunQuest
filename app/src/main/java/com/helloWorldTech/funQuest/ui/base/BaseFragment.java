package com.helloWorldTech.funQuest.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.shardPref.PreferencesHelper;
import com.helloWorldTech.funQuest.di.Injectable;
import com.helloWorldTech.funQuest.util.Connectivity;
import com.helloWorldTech.funQuest.util.NavigationController;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;



public abstract class BaseFragment extends Fragment implements Injectable {

    @Inject
    protected Connectivity connectivity;

    @Inject
    protected PreferencesHelper pref;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected NavigationController navigationController;


    private BaseActivity mActivity;
    private Dialog mProgressDialog;

    private boolean isFadeIn = false;
    protected String cameraType;
    protected String language;
    protected String token;

    //region Override Methods


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mActivity != null) {
            //Tools.setSystemBarColorPrimaryDark(mActivity);
        }

        token = Config.TOKEN_TYPE + pref.getToken() ;

        initViewModels();

        initUIAndActions();

        initAdapters();

        initData();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            this.mActivity = activity;
            this.language = this.mActivity.language ;
        }
    }



    //endregion


    //region Methods


    protected abstract void initUIAndActions();

    protected abstract void initViewModels();

    protected abstract void initAdapters();

    protected abstract void initData();

    protected void fadeIn(View view) {

        if (!isFadeIn) {
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            isFadeIn = true; // Fade in will do only one time.
        }
    }

    protected void observerErrorStatus() {
    }

    protected void observeLoadStatus() {
    }

    //endregion


    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }


    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

}
