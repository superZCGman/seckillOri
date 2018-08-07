package com.limai.user.dao;

import com.limai.user.model.Action;

import java.util.List;

public interface ActionDao {
    List<Action> selectActionsByRoleId(Integer roleId);
}