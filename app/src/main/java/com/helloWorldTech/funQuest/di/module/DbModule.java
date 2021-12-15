package com.helloWorldTech.funQuest.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.helloWorldTech.funQuest.data.AppDatabase;
import com.helloWorldTech.funQuest.data.local.dao.AppDataDao;
import com.helloWorldTech.funQuest.data.local.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    /**
     * The method returns the Database object
     */
    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "fun_quest.db")
                .allowMainThreadQueries().build();
    }


    @Provides
    @Singleton
    UserDao provideUserDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Provides
    @Singleton
    AppDataDao provideAppDataDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.appDataDao();
    }


}
