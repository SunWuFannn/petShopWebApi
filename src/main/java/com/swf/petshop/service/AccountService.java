package com.swf.petshop.service;

import java.util.List;

/**
 * 账户类业务层接口
 */
public interface AccountService {
    /**
     * 收付款操作
     * @param sourceUid
     * @param targetSid
     * @param money
     * @return
     */
    boolean peyMoney(int sourceUid, int targetSid, double money) throws Exception;
}
