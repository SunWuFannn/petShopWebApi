package com.swf.petshop.dao;

import com.swf.petshop.domain.Pet;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.FetchType;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Map;

/**
 * 宠物类dao接口
 */
public interface PetDao {

    /**
     * 根据cid，start,pageSize，rname查询当前页显示的集合，分页查询
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @SelectProvider(type = PetProvider.class, method = "findByPage")
    List<Pet> findByPage(@Param("cid") int cid, @Param("start") int start, @Param("pageSize") int pageSize, @Param("rname") String rname);

    /**
     * 根据cid和rname查询总记录数，模糊查询
     * @param cid
     * @param rname
     * @return
     */
    @SelectProvider(type = PetProvider.class, method = "findTotalCount")
    int findTotalCount(@Param("cid") int cid, @Param("rname") String rname);

    /**
     * 根据rid查询具体的宠物信息
     * @param rid
     * @return
     */
    @Select("select * from tab_pet where rid=#{rid}")
    @Results({
            @Result(property = "count", column = "rid",
                    one = @One(select = "com.swf.petshop.dao.FavoriteDao.findCountByRid", fetchType = FetchType.EAGER)),
            @Result(property = "category", column = "cid",
                    one = @One(select = "com.swf.petshop.dao.CategoryDao.findByCid", fetchType = FetchType.EAGER)),
            @Result(property = "seller", column = "sid",
                    one = @One(select = "com.swf.petshop.dao.SellerDao.findBySid", fetchType = FetchType.EAGER)),
            @Result(property = "petImgList", column = "rid",
                    many = @Many(select = "com.swf.petshop.dao.PetImgDao.findByRid", fetchType = FetchType.LAZY))
    })
    Pet findByRid(int rid);


    class PetProvider {
        /**
         * 根据cid，start,pageSize，rname查询当前页显示的集合，分页查询
         * @param para
         * @return
         */
        public String findByPage(Map<String, Object> para) {
            return new SQL() {{
                SELECT("*");
                FROM("tab_pet");
                if (Strings.isNotEmpty((String) para.get("cid"))) {
                    WHERE("cid=" + para.get("cid"));
                }
                if (Strings.isNotEmpty((String) para.get("rname"))) {
                    WHERE("rname=" + para.get("rname"));
                }
                LIMIT((String) para.get("start"));
                OFFSET((String) para.get("pageSize"));
            }}.toString();
        }

        /**
         * 根据cid和rname查询总记录数，模糊查询
         * @param para
         * @return
         */
        public String findTotalCount(Map<String, Object> para) {
            return new SQL() {{
                SELECT("count(*)");
                FROM("tab_pet");
                if (Strings.isNotEmpty((String) para.get("cid"))) {
                    WHERE("cid=" + para.get("cid"));
                }
                if (Strings.isNotEmpty((String) para.get("rname"))) {
                    WHERE("rname like %" + para.get("rname") + "%");
                }
            }}.toString();
        }
    }

}
