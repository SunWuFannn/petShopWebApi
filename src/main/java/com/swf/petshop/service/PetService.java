package com.swf.petshop.service;

import com.swf.petshop.domain.PageBean;
import com.swf.petshop.domain.Pet;

/**
 * 宠物类业务层接口
 */
public interface PetService {
    /**
     * 分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Pet> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 查询商品详细信息
     * @param rid
     * @return
     */
    Pet findOne(int rid);

}
