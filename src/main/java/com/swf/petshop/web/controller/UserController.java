package com.swf.petshop.web.controller;

import com.swf.petshop.domain.ResultInfo;
import com.swf.petshop.domain.User;
import com.swf.petshop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户类控制器
 */
@RestController("userController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 注册功能
     * @param user
     * @param req
     * @return
     */
    @PostMapping("/regist")
    public ResultInfo register(User user, HttpServletRequest req){
        ResultInfo resultInfo = new ResultInfo();
        //校验验证码
        String check = req.getParameter("check");
        String checkcode = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次
        req.getSession().removeAttribute("CHECKCODE_SERVER");
        //如果验证码错误直接返回
        if (checkcode == null || !checkcode.equalsIgnoreCase(check)) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败！验证码错误");
            return resultInfo;
        }
        //验证成功就进行注册
        //对用户注册的密码进行加密处理存入数据库，盐值是用户名
        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
        SimpleHash simpleHash = new SimpleHash("MD5", user.getPassword(),salt,1);
        user.setPassword(simpleHash.toString());
        boolean flag = userService.registUser(user);
        if (flag){
            //注册成功
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("注册成功！");
            //将激活码写入消息队列，routekey为用户的id，交换机为active.direct
            rabbitTemplate.convertAndSend("active.direct",user.getUid()+"", user.getCode());
        }else {
            //注册失败，用户名重复
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败！用户名重复");
        }
        //返回注册结果信息给前端
        return resultInfo;
    }

    /**
     * 激活功能
     * @param uid
     */
    @RequestMapping("/active")
    public String active(int uid){
        //通过uid作为routekey获取消息队列中对应的激活码
        String code = (String) rabbitTemplate.receiveAndConvert(uid + "");
        if (code==null){
            //激活失败，跳转激活失败页面
            return "active_error";
        }
        boolean active = userService.active(code);
        if (active){
            //激活成功后销毁对应的消息队列
            rabbitTemplate.destroy();
            //激活成功，跳转登录页面
            return "login";
        }else {
            //激活失败，跳转激活失败页面
            return "active_error";
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResultInfo login(User user, boolean rememberMe) {
        ResultInfo resultInfo = new ResultInfo();
        //获取当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据以及是否自动登录
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //设置是否自动登录
        token.setRememberMe(rememberMe);
        //登录
        try {
            subject.login(token);
            resultInfo.setFlag(true);
            User loginUser = userService.findByUserName(user.getUsername()).get(0);
            //未激活状态
            if ("N".equals(loginUser.getStatus())){
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("请先激活再登录！");
            }
            //激活且成功登录，将user存入session
            subject.getSession().setAttribute("user",loginUser);
        }catch (UnknownAccountException e){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名不存在！");
        }catch (IncorrectCredentialsException e){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("密码错误！");
        }
        //返回登录结果信息给前端
        return resultInfo;
    }

    /**
     * 用户退出
     * @return
     */
    @RequestMapping("/logout")
    public void exit(){
        //获取当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        //清楚session中的user
        subject.getSession().removeAttribute("user");
        //退出
        subject.logout();
    }

    /**
     * 获取登录用户
     * @return
     */
    @RequestMapping("/findOne")
<<<<<<< HEAD
    public User findOne(@SessionAttribute("user") User user){
=======
    public User findOne(@ModelAttribute("user") User user){
>>>>>>> 091e061364dde48dff42813294ce11e48f04d32c
        // 将user返回
        return user;
    }
}
