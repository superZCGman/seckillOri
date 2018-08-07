package com.limai.user.dao;

import com.limai.user.model.User;

import java.util.List;

public interface UserDao {
    List<User> selectAllUsers();

    User selectUserByName(String userName);
}