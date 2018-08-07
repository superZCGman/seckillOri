package com.limai.user.dao;

import com.limai.user.model.Role;

import java.util.List;

public interface RoleDao{
    List<Role> selectRolesByUserId(Integer userId);
}