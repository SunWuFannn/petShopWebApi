package com.swf.petshop.domain;

import java.io.Serializable;

/**
 * 账户类
 */
public class Account implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int uid;
    private Double money;
    private int sid;

    /**
     * 无参构造方法
     */
    public Account() {
    }

    /**
     * 有参构造方法
     * @param uid
     * @param money
     * @param sid
     */
    public Account(int uid, Double money, int sid) {
        this.uid = uid;
        this.money = money;
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
