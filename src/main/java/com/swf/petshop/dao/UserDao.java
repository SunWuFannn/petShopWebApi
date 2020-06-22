package com.swf.petshop.dao;

import com.swf.petshop.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户类dao接口
 */
public interface UserDao {
    /**
     * 根据用户名查询数据库用户名是否已经注册
     * @param username
     * @return
     */
    @Select("select * from tab_user where username=#{username}")
    List<User> findByUserName(String username);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    @Insert("insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) " +
            "values(#{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    int save(User user);

    /**
     * 更新激活后用户状态
     * @param user
     * @return
     */
    @Update("update tab_user set status='Y' where uid=#{uid}")
    int updateStatus(User user);

    /**
     * 根据激活码查询数据库
     * @param code
     * @return
     */
    @Select("select * from tab_user where code=#{code}")
    List<User> findByCode(String code);


    /**
     * 根据用户id查询数据库
     * @param uid
     * @return
     */
    @Select(("select * from tab_user where uid=#{uid}"))
    User findByUid(int uid);
}
