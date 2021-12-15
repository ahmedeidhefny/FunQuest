package com.helloWorldTech.funQuest;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.helloWorldTech.funQuest.di.AppInjector;
import com.hitchbug.library.core.Hitchbug;
import com.hitchbug.library.core.investigation.AppInfo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class App extends MultiDexApplication implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppInjector.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppInfo appInfo = new AppInfo("n/a",BuildConfig.APPLICATION_ID,"6",
                BuildConfig.VERSION_NAME ,BuildConfig.VERSION_CODE);
        new Hitchbug.Builder(this, Config.HITCHBUG_APP_KEY,appInfo ).build();

    }
}
