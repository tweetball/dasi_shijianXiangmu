package com.icss.xihu.service;

import com.icss.xihu.model.HotelOrder;

import java.util.List;

public interface HotelOrderService {
    // 新增订单
    int createOrder(HotelOrder order);
    // 根据用户ID查询订单列表
    List<HotelOrder> getOrdersByUserId(String userId);

    // 取消订单
    int cancelOrder(Long orderId);

    //确认订单
    int makeSureOrder(Long orderId);
}