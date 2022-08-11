package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLoadBalancer implements LoadBalancer {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> instances) {

        int index = getIncrement() % instances.size();

        return instances.get(index);
    }

    /**
     * 负载均衡算法：轮询
     * 用到了自旋锁
     */
    public final int getIncrement() {

        int current;
        int next;

        // do {
        //
        //     current = this.atomicInteger.get();
        //
        //     next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        //
        // } while (!atomicInteger.compareAndSet(current, next));

        for (;;) {

            current = this.atomicInteger.get();

            next = current >= Integer.MAX_VALUE ? 0 : current + 1;

            if (atomicInteger.compareAndSet(current, next)) {
                return next;
            }

        }

    }

}
