package com.swf.petshop.dao;

import com.swf.petshop.domain.PetImg;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 宠物图片列表dao接口
 */
public interface PetImgDao {
    /**
     * 根据宠物的id查询商品对应的图片集合
     * @param rid
     * @return
     */
    @Select("select * from tab_petimg where rid=#{rid}")
    List<PetImg> findByRid(int rid);
}
