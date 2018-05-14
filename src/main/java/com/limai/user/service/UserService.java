package com.limai.user.service;

import com.limai.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserService {
    public List<User> queryUsers();
}
