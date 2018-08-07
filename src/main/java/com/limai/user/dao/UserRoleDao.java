package com.limai.user.dao;

import com.limai.user.model.UserRole;
import com.limai.user.model.UserRoleKey;

public interface UserRoleDao {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(UserRoleKey key);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}