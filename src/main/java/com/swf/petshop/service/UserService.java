package com.swf.petshop.service;

import com.swf.petshop.domain.User;

import java.util.List;

/**
 * 用户类业务层接口
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean registUser(User user);

    /**
     * 激活邮箱
     * @param code
     * @return
     */
    boolean active(String code);


    /**
     * 通过用户名查找
     * @param userName
     * @return
     */
    List<User> findByUserName(String userName);
}
