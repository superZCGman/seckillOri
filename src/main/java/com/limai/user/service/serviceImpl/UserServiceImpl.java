package com.limai.user.service.serviceImpl;

import com.limai.user.dao.UserDao;
import com.limai.user.model.User;
import org.springframework.stereotype.Service;
import com.limai.user.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserDao userDao;

    @Override
    public List<User> queryUsers() {
        return userDao.selectAllUsers();
    }
}
