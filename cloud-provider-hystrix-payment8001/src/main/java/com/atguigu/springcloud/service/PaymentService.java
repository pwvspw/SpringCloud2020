package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常访问，一切OK
     */
    public String paymentInfoOk(Integer id) {
        return Thread.currentThread().getName() + "\tid：" + id + "\tpayment info ok～～～";
    }

    /**
     * 超时访问，触发服务降级
     */
    @HystrixCommand(fallbackMethod = "timeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfoTimeout(Integer id) {
        try {
            int second = 5;
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName() + "\tid：" + id + "\tpayment info time out!!!";
    }

    public String timeOutHandler() {
        return "调用支付接口超时或异常，当前线程池名字：" + Thread.currentThread().getName();
    }


    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage" ,value = "60")
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("*********** id 不能为负数");
        }
        String serialNo = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t调用成功\t流水号：" + serialNo;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "ID：" + id + "\tID不能为负数，请稍后再试！";
    }

}
