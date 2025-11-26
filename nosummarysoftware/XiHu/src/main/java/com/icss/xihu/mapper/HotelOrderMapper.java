package com.icss.xihu.mapper;

import com.icss.xihu.model.HotelOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotelOrderMapper {
    // 插入订单数据
    int insert(HotelOrder order);

    // 根据用户ID查询订单列表
    List<HotelOrder> selectByUserId(@Param("userId") String userId);

    // 更新订单状态
    int updateStatus(@Param("orderId") Long orderId, @Param("status") Integer status);
}