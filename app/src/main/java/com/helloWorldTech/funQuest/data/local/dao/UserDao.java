package com.helloWorldTech.funQuest.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.helloWorldTech.funQuest.data.local.entity.UserEntity;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserEntity user);

    @Query("SELECT * FROM UserEntity")
    LiveData<UserEntity> getUser();

    @Query("SELECT * FROM UserEntity WHERE id == :id")
    LiveData<UserEntity> getUser(int id);

    @Query("DELETE FROM UserEntity")
    void deleteUser();


}
