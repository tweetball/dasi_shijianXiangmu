/**
 * 旅游订单服务实现类
 * 功能概述：实现TravelOrderService接口，提供旅游订单相关的业务逻辑实现，包括订单创建、查询、状态更新等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入旅游订单Mapper接口
import com.icss.xihu.mapper.TravelOrderMapper;
// 导入旅游订单实体类
import com.icss.xihu.model.TravelOrder;
// 导入旅游订单服务接口
import com.icss.xihu.service.TravelOrderService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

// 导入List集合接口
import java.util.List;

/**
 * 旅游订单服务实现
 * 功能概述：实现TravelOrderService接口，提供旅游订单相关的业务逻辑实现，包括订单创建、查询、状态更新等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 旅游订单服务实现类，实现TravelOrderService接口
public class TravelOrderServiceImpl implements TravelOrderService {

    // 自动注入旅游订单Mapper，Spring容器会自动查找并注入TravelOrderMapper的实现类
    @Autowired
    // 旅游订单Mapper对象，用于调用数据库操作方法
    private TravelOrderMapper travelOrderMapper;

    /**
     * 创建旅游订单
     * 功能概述：调用Mapper层方法，向数据库中插入一条旅游订单记录
     * @param {TravelOrder} order - 旅游订单对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 重写接口中的createOrder方法
    @Override
    // 创建旅游订单方法，接收旅游订单对象参数，返回插入的记录数
    public int createOrder(TravelOrder order) {
        // 调用Mapper层的insertOrder方法，将订单信息插入数据库，返回插入的记录数
        return travelOrderMapper.insertOrder(order);
    }

    /**
     * 根据订单号获取订单
     * 功能概述：调用Mapper层方法，根据订单号查询指定旅游订单的详细信息
     * @param {String} orderNo - 订单号
     * @return {TravelOrder} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的getOrderByOrderNo方法
    @Override
    // 根据订单号获取订单方法，接收订单号参数，返回订单对象
    public TravelOrder getOrderByOrderNo(String orderNo) {
        // 调用Mapper层的findByOrderNo方法，根据订单号查询订单信息并返回
        return travelOrderMapper.findByOrderNo(orderNo);
    }

    /**
     * 根据用户ID获取订单列表
     * 功能概述：调用Mapper层方法，根据用户编号查询该用户的所有旅游订单信息
     * @param {Integer} userId - 用户编号
     * @return {List<TravelOrder>} 返回该用户的所有订单列表
     */
    // 重写接口中的getOrdersByUserId方法
    @Override
    // 根据用户ID获取订单列表方法，接收用户编号参数，返回该用户的所有订单列表
    public List<TravelOrder> getOrdersByUserId(Integer userId) {
        // 调用Mapper层的findByUserId方法，根据用户编号查询该用户的所有订单信息并返回
        return travelOrderMapper.findByUserId(userId);
    }

    /**
     * 更新订单状态
     * 功能概述：调用Mapper层方法，根据订单编号更新旅游订单的状态
     * @param {Integer} orderId - 订单编号
     * @param {Integer} status - 订单状态（0-待支付，1-已支付，2-已取消，3-已完成）
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 重写接口中的updateOrderStatus方法
    @Override
    // 更新订单状态方法，接收订单编号和订单状态参数，返回更新的记录数
    public int updateOrderStatus(Integer orderId, Integer status) {
        // 调用Mapper层的updateStatusById方法，根据订单编号和订单状态更新订单信息，返回更新的记录数
        return travelOrderMapper.updateStatusById(orderId, status);
    }
}
