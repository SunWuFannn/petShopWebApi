package com.swf.petshop.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 我的密码加密类，用于shiro传入的密码进行加密
 */
public  class MyCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //进行加密，参数分别为加密方法名，进行加密的密码，盐值取值，加密次数
        Object tokenCredentials = new SimpleHash("md5", userToken.getPassword(), userToken.getUsername(), 1).toHex();
        //取出数据库存储密码
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return equals(tokenCredentials, accountCredentials);
    }
}
