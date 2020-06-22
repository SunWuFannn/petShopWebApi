package com.swf.petshop.dao;


import com.swf.petshop.domain.Seller;
import org.apache.ibatis.annotations.Select;

/**
 * 销售商类dao接口
 */
public interface SellerDao {
    /**
     * 根据销售商id查询商家信息
     * @param sid
     * @return
     */
    @Select("select * from tab_seller where sid=#{sid}")
    Seller findBySid(int sid);
}
