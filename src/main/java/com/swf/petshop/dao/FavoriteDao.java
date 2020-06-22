package com.swf.petshop.dao;


import com.swf.petshop.domain.Favorite;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;

/**
 * 收藏类dao接口
 */
public interface FavoriteDao {
    /**
     * 根据宠物id和用户id查询对应的宠物和收藏用户
     */
    @Select("select * from tab_favorite where rid=#{rid} and uid=#{uid}")
    @Results({
            @Result(property = "pet", column = "rid",
                    one = @One(select = "com.swf.petshop.dao.PetDao.findByRid", fetchType = FetchType.EAGER)),
            @Result(property = "user", column = "uid",
                    one = @One(select = "com.swf.petshop.dao.UserDao.findByUid", fetchType = FetchType.EAGER))
    })
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 查询宠物的收藏次数
     * @param rid
     * @return
     */
    @Select("select count(*) from tab_favorite where rid=#{rid}")
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param date
     * @param uid
     * @return
     */
    @Insert("insert into tab_favorite values(#{rid},#{date},#{uid})")
    int add(int rid, Date date, int uid);

    /**
     * 根据用户id查询用户收藏列表中的宠物id们
     * @param uid
     * @return
     */
    @Select(("select rid from tab_favorite WHERE uid=#{uid}"))
    List<Integer> findByUid(int uid);
}
