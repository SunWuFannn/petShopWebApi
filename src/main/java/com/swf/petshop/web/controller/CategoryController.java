package com.swf.petshop.web.controller;

import com.swf.petshop.domain.Category;
import com.swf.petshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类控制器
 */
@RestController("categoryController")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有宠物分类
     * @return
     */
    @RequestMapping("/categories")
    public List<Category> findAll() {
        List<Category> categories = categoryService.findAll();
        return categories;
    }

}
