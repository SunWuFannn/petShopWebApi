package com.swf.petshop.service.impl;

import com.swf.petshop.dao.UserDao;
import com.swf.petshop.domain.User;
import com.swf.petshop.service.UserService;
import com.swf.petshop.util.MailUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 用户类业务层实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean registUser(User user) {
        //如果用户名已经存在，那么返回false，不然返回true
        List<User> byName = userDao.findByUserName(user.getName());
        if (byName==null||byName.size()==0){
            //用户不存在，保存用户，返回true
            user.setCode(UUID.randomUUID().toString().replace("-",""));
            user.setStatus("N");
            userDao.save(user);
            //发送邮件
            String content = "<a href='http://localhost:8080/user/active?code=" + user.getCode() + "'>点击激活邮箱</a>";
            MailUtils.sendMail(user.getEmail(), content, "激活邮件");
            return true;
        }else {
            //用户存在，返回false
            return false;
        }
    }

    /**
     * 激活邮箱
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        List<User> byCode = userDao.findByCode(code);
        if (byCode==null||byCode.size()==0){
            //激活码不存在
            return false;
        }else {
            //激活码存在，激活用户
            userDao.updateStatus(byCode.get(0));
            return true;
        }
    }

    /**
     * 通过用户名查找
     * @param userName
     * @return
     */
    @Override
    public List<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}
