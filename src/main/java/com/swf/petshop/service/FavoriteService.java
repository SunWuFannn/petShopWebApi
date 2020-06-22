package com.swf.petshop.service;

import com.swf.petshop.domain.Pet;

import java.util.Date;
import java.util.List;

/**
 * 收藏类业务层接口
 */
public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param date
     * @param uid
     * @return
     */
    int add(int rid, Date date, int uid);

    /**
     * 我的收藏
     * @param uid
     * @return
     */
    List<Pet> find(int uid);

}
