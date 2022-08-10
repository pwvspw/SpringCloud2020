package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
public class PaymentController {

    @Resource
    PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {

        int i = paymentService.create(payment);

        log.info("插入结果：{}", i);

        if (i > 0) {

            return new CommonResult<>(200, "插入数据库成功", payment);

        } else {

            return new CommonResult<>(400, "插入数据库失败", null);

        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentService.getPaymentById(id);

        if (payment != null) {

            return new CommonResult<>(200, "查询成功", payment);

        } else {

            return new CommonResult<>(404, "没有对应记录，查询ID：" + id, null);

        }

    }

}
