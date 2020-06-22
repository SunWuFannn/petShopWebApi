package com.swf.petshop.service.impl;

import com.swf.petshop.dao.AccountDao;
import com.swf.petshop.domain.Account;
import com.swf.petshop.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 账户类业务层接口实现类
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    /**
     * 收付款操作
     *
     * @param sourceUid
     * @param targetSid
     * @param money
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public boolean peyMoney(int sourceUid, int targetSid, double money) throws Exception {
        if (money < 0) return false;
        Account accountPay = accountDao.findByUid(sourceUid);
        accountPay.setMoney(accountPay.getMoney() - money);
        accountDao.payMoney(accountPay.getUid(), accountPay.getMoney());
        Account accountGet = accountDao.findBySid(targetSid);
        accountGet.setMoney(accountGet.getMoney() + money);
        accountDao.payMoney(accountGet.getUid(), accountGet.getMoney());
        return true;
    }
}
