package com.swf.petshop.service;

import com.swf.petshop.domain.Category;

import java.util.List;

/**
 * 分类业务层接口
 */
public interface CategoryService {

    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAll();

}
