package com.swf.petshop.dao;

import com.swf.petshop.domain.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户类dao接口
 */
public interface CategoryDao {

    /**
     * 查询所有分类
     * @return
     */
    @Select("select * from tab_category")
    List<Category> findAll();

    /**
     * 根据cid查询分类
     * @return
     */
    @Select("select * from tab_category where cid=#{cid}")
    Category findByCid(int cid);

}
