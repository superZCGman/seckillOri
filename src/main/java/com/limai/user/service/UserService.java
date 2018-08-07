package com.limai.user.service;

import com.limai.user.model.Action;
import com.limai.user.model.Role;
import com.limai.user.model.User;

import java.util.List;

public interface UserService {
    List<User> queryUsers();

    List<Action> queryActionsForUser(Integer roleId);

    List<Role> queryRolesForUser(Integer userId);

    User queryUserByName(String name);
}
