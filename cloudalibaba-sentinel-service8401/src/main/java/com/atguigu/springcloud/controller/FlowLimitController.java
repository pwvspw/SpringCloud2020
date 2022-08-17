package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "flow limit test A";
    }

    @GetMapping("/testB")
    public String testB() {
        return "flow limit test B";
    }

    @GetMapping("/testC")
    public int testC() {
        return 10 / 0;
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "fallback")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "test hot key, p1 = " + p1 + ", p2 = " + p2;
    }

    public String fallback(String p1, String p2, BlockException exception) {
        return "Trigger the circuit breaker, this is the custom method";
    }

}
