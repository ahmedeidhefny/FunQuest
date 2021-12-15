package com.helloWorldTech.funQuest.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.helloWorldTech.funQuest.data.local.Converters;
import com.helloWorldTech.funQuest.data.local.dao.AppDataDao;
import com.helloWorldTech.funQuest.data.local.dao.UserDao;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;


@Database(entities = {
        UserEntity.class,
        AppDataResponse.class
}, version = 1, exportSchema = false)

@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract AppDataDao appDataDao();

}