package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.MessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;

@EnableBinding(Source.class) // 可以理解为是一个消息发送管道的定义
@Slf4j
public class MessageProviderImpl implements MessageProvider {

    /**
     * 消息的发送管道
     */
    @Resource
    private MessageChannel output;

    @Override
    public String send() {

        String serial = IdUtil.fastUUID();

        // 创建消息
        Message<String> build = MessageBuilder.withPayload(serial).build();

        // 发送消息
        output.send(build);

        log.info("message provider send serial: {}", serial);

        return serial;
    }

}
