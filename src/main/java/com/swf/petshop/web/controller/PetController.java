package com.swf.petshop.web.controller;

import com.swf.petshop.domain.PageBean;
import com.swf.petshop.domain.Pet;
import com.swf.petshop.domain.User;
import com.swf.petshop.service.FavoriteService;
import com.swf.petshop.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * 宠物类控制器
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private FavoriteService favoriteService;

    /**
     * 分页查询
     * @param currentPageStr
     * @param pageSizeStr
     * @param cidStr
     * @param rname
     * @return
     */
    @RequestMapping("/limit")
    public PageBean<Pet> pageQuery(String currentPageStr, String pageSizeStr, String cidStr, String rname) {
        rname = new String(rname.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //处理参数
        int cid = 0;    //类别id
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            //如果cid传入则为传入值，否则为0
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;    //当前页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            //如果cid传入则为传入值，否则为1
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;   //每页显示条数
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            //如果cid传入则为传入值，否则为10
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 10;
        }
        //调用service进行分页查询
        PageBean<Pet> pageBean = petService.pageQuery(cid, currentPage, pageSize, rname);
        //将数据返回
        return pageBean;
    }

    /**
     * 查询商品详细信息
     * @param rid
     * @return
     */
    @RequestMapping("/one")
    public Pet findOne(int rid) {
        //调用service
        Pet pet = petService.findOne(rid);
        //写回数据
        return pet;
    }

    /**
     * 判断是否收藏
     * @param rid
     * @param user
     * @return
     */
    @RequestMapping("/isFavorite")
    public boolean isFavorite(int rid, User user) {
        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }
        //调用favoriteservice
        return favoriteService.isFavorite(rid, uid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param user
     * @return
     */
    @RequestMapping("/addFavorite")
    public int addFavorite(int rid, User user) {
        if (user == null) {
            return -1;
        }
        int uid = user.getUid();
        Date date = new Date();
        //调用service
        return favoriteService.add(rid, date, uid);
    }

    /**
     * 我的收藏
     * @param user
     * @return
     */
    @RequestMapping("/myFavorite")
    public List<Pet> myFavorite(User user) {
        //获取当前登录用户
        if (user == null) {
            return null;
        }
        int uid = user.getUid();
        //调用service
        return favoriteService.find(uid);
    }

}
