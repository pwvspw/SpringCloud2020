package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Async("asyncServiceExecutor")
    @Override
    public void executeAsync() {

        log.info("start execute async");

        try {
            log.info("thread sleep 3 second...");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("end execute async");

    }

}
