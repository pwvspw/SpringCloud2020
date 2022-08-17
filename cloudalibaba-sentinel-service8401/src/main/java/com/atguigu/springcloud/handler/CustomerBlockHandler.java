package com.atguigu.springcloud.handler;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {
    
    public CommonResult<Payment> customerHandleException() {
        return new CommonResult<>(888, "this is customer handle exception");
    }
    
}
