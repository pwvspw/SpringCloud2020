package com.atguigu.springcloud.config;

import com.atguigu.springcloud.executor.VisiableThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 标注：@Configuration + @EnableAsync，表示这是一个线程池的配置类
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {

        log.info("start asyncServiceExecutor");

        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();

        // 核心线程数
        executor.setCorePoolSize(5);

        // 最大线程数
        executor.setMaxPoolSize(5);

        // 配置队列大小
        executor.setQueueCapacity(99999);

        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");

        // 配置拒绝策略(CallerRunsPolicy: 不在新线程中执行任务，而是由调用者所在的线程来执行)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;

    }

}
