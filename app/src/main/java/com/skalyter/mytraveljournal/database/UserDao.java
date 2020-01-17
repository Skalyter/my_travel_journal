package com.skalyter.mytraveljournal.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.skalyter.mytraveljournal.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user u WHERE u.email=:email")
    User getUser(String email);

    @Insert
    void insertUser(User user);
}
