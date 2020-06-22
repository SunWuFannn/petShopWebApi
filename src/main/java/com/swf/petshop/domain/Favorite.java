package com.swf.petshop.domain;

import java.io.Serializable;

/**
 * 收藏实体类
 */
public class Favorite implements Serializable {
    private Pet pet;//宠物对象
    private String date;//收藏时间
    private User user;//所属用户

    /**
     * 无参构造方法
     */
    public Favorite() {
    }

    /**
     * 有参构造方法
     * @param pet
     * @param date
     * @param user
     */
    public Favorite(Pet pet, String date, User user) {
            this.pet = pet;
            this.date = date;
            this.user = user;
    }

    public Pet getRoute() {
        return pet;
    }

    public void setRoute(Pet pet) {
        this.pet = pet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
