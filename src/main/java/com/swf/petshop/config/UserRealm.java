package com.swf.petshop.config;

import com.swf.petshop.domain.User;
import com.swf.petshop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Realm对象
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        //设置权限，通过当前用户查询对应的权限数据库查出对应的权限，然后设置权限
         info.addStringPermission(""/*currentUser.查询数据库*/);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //连接真实的数据库
        List<User> list = userService.findByUserName(userToken.getUsername());
        if (list == null || list.size() == 0) {
        //数据库不存在这个人
            return null;
        }
        //有这个人就密码认证，如果登录成功就将这个user对象传递给授权功能
        return new SimpleAuthenticationInfo(list.get(0), list.get(0).getPassword(), "");
    }

    /**
     * 设置自定义加密方式
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        MyCredentialsMatcher myCredentialsMatcher = new MyCredentialsMatcher();
        super.setCredentialsMatcher(myCredentialsMatcher);
    }
}
