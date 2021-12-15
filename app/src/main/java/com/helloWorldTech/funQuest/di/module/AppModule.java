package com.helloWorldTech.funQuest.di.module;

import android.app.Application;
import android.content.Context;

import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.data.local.shardPref.AppPreferencesHelper;
import com.helloWorldTech.funQuest.data.local.shardPref.PreferenceInfo;
import com.helloWorldTech.funQuest.data.local.shardPref.PreferencesHelper;
import com.helloWorldTech.funQuest.util.Connectivity;
import com.helloWorldTech.funQuest.util.NavigationController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Module
public class AppModule {

    @Singleton
    @Provides
    Connectivity provideConnectivity(Application app) {
        return new Connectivity(app);
    }

    @Singleton
    @Provides
    NavigationController provideNavigationController() {
        return new NavigationController();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }


    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

}
