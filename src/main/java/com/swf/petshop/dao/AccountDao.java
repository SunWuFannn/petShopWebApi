package com.swf.petshop.dao;

import com.swf.petshop.domain.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 账户类dao接口
 */
public interface AccountDao {
    /**
     * 收付款操作
     */
    @Update("update tab_account set money=#{money} where uid=#{uid}")
    int payMoney(int uid, double money);

    /**
     * 通过uid找到账户
     */
    @Select("select * from tab_account where uid=#{uid}")
    Account findByUid(int uid);

    /**
     * 通过sid找到账户
     */
    @Select("select * from tab_account where sid=#{sid}")
    Account findBySid(int sid);
}
