package com.limai.user.model;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String firstName;

    private String lastName;

    private String gender;
}