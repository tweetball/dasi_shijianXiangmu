package com.icss.xihu.service.impl;

import com.icss.xihu.mapper.HotelOrderMapper;
import com.icss.xihu.model.HotelOrder;
import com.icss.xihu.service.HotelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelOrderServiceImpl implements HotelOrderService {

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Override
    public int createOrder(HotelOrder order) {
        return hotelOrderMapper.insert(order);
    }

    @Override
    public List<HotelOrder> getOrdersByUserId(String userId) {
        return hotelOrderMapper.selectByUserId(userId);
    }

    @Override
    public int cancelOrder(Long orderId) {
        // 更新订单状态为2（已取消）
        return hotelOrderMapper.updateStatus(orderId, 2);
    }

    @Override
    public int makeSureOrder(Long orderId) {
        return hotelOrderMapper.updateStatus(orderId, 1);
    }
}