package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.mapper.AccountMapper;
import com.atguigu.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 账户业务实现类
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 扣减账户余额
     */
    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("------->account-service中扣减账户余额开始");
        //模拟超时异常，全局事务回滚
        //暂停几秒钟线程
        // try {
        //     TimeUnit.SECONDS.sleep(20);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        accountMapper.decrease(userId, money);
        log.info("------->account-service中扣减账户余额结束");
    }

}
