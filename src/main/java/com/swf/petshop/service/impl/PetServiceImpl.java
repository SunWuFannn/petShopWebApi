package com.swf.petshop.service.impl;

import com.swf.petshop.dao.PetDao;
import com.swf.petshop.domain.PageBean;
import com.swf.petshop.domain.Pet;
import com.swf.petshop.service.PetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 宠物类业务层实现类
 */
@Service("petService")
public class PetServiceImpl implements PetService {
    @Resource
    private PetDao petDao;

    /**
     * 分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Pet> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // 创建一个PageBean对象，设置对象的各个属性，最后返回这个对象
        PageBean<Pet> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        int start = (currentPage - 1) * pageSize;
        pageBean.setList(petDao.findByPage(cid, start, pageSize, rname));
        pageBean.setTotalCount(petDao.findTotalCount(cid, rname));
        pageBean.setTotalPage(pageBean.getTotalCount() % pageSize == 0 ? pageBean.getTotalCount() / pageSize : (pageBean.getTotalCount() / pageSize) + 1);
        return pageBean;
    }

    /**
     * 查询商品详细信息
     * @param rid
     * @return
     */
    @Override
    public Pet findOne(int rid) {
        //根据rid查询具体的宠物信息
        return petDao.findByRid(rid);
    }
}
