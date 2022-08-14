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


    /**
     * 涉及到断路器的3个重要参数：
     *
     * 1、快照时间窗：断路器确定是否要打开，需要统计一些请求和错误数据，统计的时间范围就是快照时间窗，默认10秒
     *
     * 2、请求总数阈值：在快照时间窗内，必须满足请求总数达到阈值，才有资格进行熔断，默认为20。
     *    意味着在10秒内，如果该hystrix命令调用次数不超过20次，即使所有请求都超时或请求失败，断路器都不会打开
     *
     * 3、错误百分比阈值：当请求总数在快照时间窗口内超过了阈值，比如发生了30次请求，如果在这30次调用中，有15次发生了异常，
     *    也就是超过了50%的错误百分比，在默认设置50%阈值的情况下，断路器将会打开
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 开启断路器
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间范围10秒
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数超过了峰值，断路器会从关闭状态切换到打开状态
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少后跳闸
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
