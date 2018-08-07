package com.limai.user.model;

import java.io.Serializable;

public class Action implements Serializable {
    private Integer id;

    private String actionName;

    private String actionPermission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    public String getActionPermission() {
        return actionPermission;
    }

    public void setActionPermission(String actionPermission) {
        this.actionPermission = actionPermission == null ? null : actionPermission.trim();
    }
}