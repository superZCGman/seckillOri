package com.limai.user.dao;

import com.limai.user.model.RoleAction;

public interface RoleActionDao {
    int insert(RoleAction record);

    int insertSelective(RoleAction record);
}