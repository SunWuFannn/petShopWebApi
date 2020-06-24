package com.swf.petshop.domain;

import java.io.Serializable;

/**
 * 宠物图片实体类
 */
public class PetImg implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int rgid;//宠物图片id
    private int rid;//宠物商品id
    private String bigPic;//详情宠物大图
    private String smallPic;//详情宠物小图

    /**
     * 无参构造方法
     */
    public PetImg() {
    }

    /**
     * 有参构造方法
     * @param rgid
     * @param rid
     * @param bigPic
     * @param smallPic
     */
    public PetImg(int rgid, int rid, String bigPic, String smallPic) {
        this.rgid = rgid;
        this.rid = rid;
        this.bigPic = bigPic;
        this.smallPic = smallPic;
    }

    public int getRgid() {
        return rgid;
    }

    public void setRgid(int rgid) {
        this.rgid = rgid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
