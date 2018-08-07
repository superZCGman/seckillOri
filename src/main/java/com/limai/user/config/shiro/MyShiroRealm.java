package com.limai.user.config.shiro;

import com.limai.user.model.Action;
import com.limai.user.model.Role;
import com.limai.user.model.User;
import com.limai.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author itw_zhangcg
 * @version 1.0
 * @className MyShiroRealm
 * @description TODO
 * @date 2018/8/3 10:56
 **/
public class MyShiroRealm extends AuthorizingRealm{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("开始权限配置！");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principalCollection.getPrimaryPrincipal();
        Integer userId = user.getId();
        List<Role> roles = userService.queryRolesForUser(userId);

        for(Role role:roles){
            authorizationInfo.addRole(role.getRoleName());
            List<Action> actions = userService.queryActionsForUser(role.getId());
            for(Action action:actions){
                authorizationInfo.addStringPermission(action.getActionPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("进入认证！");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = token.getUsername();
        User user = userService.queryUserByName(userName);
        if (user == null){
            throw new AccountException("账号或密码不正确！");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                getName()
        );
        return authenticationInfo;
    }
}
