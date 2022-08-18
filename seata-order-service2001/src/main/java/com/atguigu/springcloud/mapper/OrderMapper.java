package com.atguigu.springcloud.mapper;

import com.atguigu.springcloud.entities.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     */
    void create(Order order);

    /**
     * 修改订单状态
     */
    void update(@Param("id") Long id, @Param("status") Integer status);

}
