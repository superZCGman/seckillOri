package com.limai.user.dao;


import com.limai.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    List<User> selectAllUsers();

    public User selectUserById(Integer id);

    public void updateUserById(Integer id);

    public void deleteUserById(Integer id);

    public void insertUser(User user);
}
