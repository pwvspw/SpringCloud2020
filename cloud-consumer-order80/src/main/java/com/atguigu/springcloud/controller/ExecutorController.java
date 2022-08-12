package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutorController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async/test")
    public String asyncTest() {

        asyncService.executeAsync();

        return "async execute end...";
    }
}
