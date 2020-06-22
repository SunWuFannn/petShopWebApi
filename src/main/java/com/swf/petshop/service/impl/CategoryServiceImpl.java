package com.swf.petshop.service.impl;

import com.swf.petshop.dao.CategoryDao;
import com.swf.petshop.domain.Category;
import com.swf.petshop.service.CategoryService;
import com.swf.petshop.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 分类业务层实现类
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryDao categoryDao;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //先从redis中查询有没有缓存
        Set<Object> set = redisUtil.zGet("category", 0, -1);
        List<Category> categoryList = null;
        if (set == null || set.size() == 0) {
            //redis中没有缓存，则从数据库中查找之后存入redis缓存
            categoryList = categoryDao.findAll();
            for (Category category : categoryList) {
                redisUtil.zSet("category", category.getCname(), category.getCid());
            }
        } else {
            //redis中有缓存，直接取出返回
            categoryList = new ArrayList<>();
            for (Object o : set) {
                Category category = (Category) o;
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
