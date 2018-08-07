package com.limai.user.service.serviceImpl;

import com.limai.user.dao.ActionDao;
import com.limai.user.dao.RoleDao;
import com.limai.user.dao.UserDao;
import com.limai.user.model.Action;
import com.limai.user.model.Role;
import com.limai.user.model.User;
import org.springframework.stereotype.Service;
import com.limai.user.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserDao userDao;

    @Resource
    private ActionDao actionDao;

    @Resource
    private RoleDao roleDao;

    @Override
    public List<User> queryUsers() {
        return userDao.selectAllUsers();
    }

    @Override
    public List<Action> queryActionsForUser(Integer roleId) {
        List<Action> actions = actionDao.selectActionsByRoleId(roleId);
        if(actions.isEmpty()||actions==null) {
            return null;
        }
        return actions;
    }

    @Override
    public List<Role> queryRolesForUser(Integer userId){
        List<Role> roles = roleDao.selectRolesByUserId(userId);
        if(roles.isEmpty()||roles==null){
            return null;
        }
        return roles;
    }

    @Override
    public User queryUserByName(String name) {
        User user = userDao.selectUserByName(name);
        if(user==null){
            return null;
        }
        return user;
    }
}
