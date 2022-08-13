package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfoOk(Integer id) {
        return "Attention!!! Attention!!! payment info ok is down !!!";
    }

    @Override
    public String paymentInfoTimeout(Integer id) {
        return "Attention!!! Attention!!! payment info timeout is down !!!";
    }

}
