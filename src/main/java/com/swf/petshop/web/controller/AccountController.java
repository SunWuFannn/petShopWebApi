package com.swf.petshop.web.controller;

import com.swf.petshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户类操作控制器
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 收付款操作
     * @param sourceUid
     * @param targetSid
     * @param money
     * @return
     */
    @RequestMapping("/pay")
    public boolean payMoney(int sourceUid, int targetSid, double money) {
        try {
            accountService.peyMoney(sourceUid, targetSid, money);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("支付过程中出错，请重新支付");
        }
    }
}
