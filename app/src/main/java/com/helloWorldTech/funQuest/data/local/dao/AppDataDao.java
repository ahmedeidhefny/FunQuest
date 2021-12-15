package com.helloWorldTech.funQuest.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;

@Dao
public interface AppDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppDataResponse appData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(AppDataResponse appData);

    @Query("DELETE FROM AppDataResponse")
    void delete();

    @Query("SELECT * FROM AppDataResponse")
    LiveData<AppDataResponse> getData();
}
