package com.skalyter.mytraveljournal.database;

import com.skalyter.mytraveljournal.model.User;

public interface IUserDao {

    User getUser(String email);
    long insertUser(User user);
}
