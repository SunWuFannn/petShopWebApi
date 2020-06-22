package com.swf.petshop.service.impl;

import com.swf.petshop.dao.FavoriteDao;
import com.swf.petshop.dao.PetDao;
import com.swf.petshop.domain.Favorite;
import com.swf.petshop.domain.Pet;
import com.swf.petshop.service.FavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 宠物类别业务层实现类
 */
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteDao favoriteDao;
    @Resource
    private PetDao petDao;

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite dao = favoriteDao.findByRidAndUid(rid, uid);
        return dao != null;
    }

    /**
     * 添加收藏
     * @param rid
     * @param date
     * @param uid
     * @return
     */
    @Override
    public int add(int rid, Date date, int uid) {
        return favoriteDao.add(rid, date, uid);
    }

    /**
     * 我的收藏
     * @param uid
     * @return
     */
    @Override
    public List<Pet> find(int uid) {
        // 先查出用户收藏的宠物们的id
        List<Integer> rids = favoriteDao.findByUid(uid);
        // 如果为空，说明用户没有收藏任何东西，直接返回null
        if (rids == null || rids.size() == 0) {
            return null;
        } else {
        // 如果不为空，则通过宠物的id们查出宠物的具体详细信息，然后返回一个list集合
            List<Pet> pets = new ArrayList<>();
            //通过rid找到对应的宠物
            for (Integer rid : rids) {
                Pet one = petDao.findByRid(rid);
                pets.add(one);
            }
            return pets;
        }
    }
}
