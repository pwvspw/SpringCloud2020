package com.atguigu.springcloud.service;

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

}
