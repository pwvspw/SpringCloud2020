package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    PaymentService paymentService;

    @Resource
    DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {

        int i = paymentService.create(payment);

        log.info("插入返回结果：{}", i);

        if (i > 0) {

            return new CommonResult<>(200, "插入数据库成功，服务端口号：" + serverPort, payment);

        } else {

            return new CommonResult<>(400, "插入数据库失败", null);

        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentService.getPaymentById(id);

        if (payment != null) {

            return new CommonResult<>(200, "查询成功，服务端口号：" + serverPort, payment);

        } else {

            return new CommonResult<>(404, "没有对应记录，查询ID：" + id, null);

        }

    }

    @GetMapping("/payment/discovery")
    public Object discovery() {

        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId() + "\t" + instance.getUri() + "\t" + instance.getHost() + "\t" + instance.getPort());
        }

        return this.discoveryClient;

    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

}
