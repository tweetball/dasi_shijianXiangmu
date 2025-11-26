/**
 * 旅游订单服务接口
 * 功能概述：定义旅游订单相关的业务逻辑方法，包括订单创建、查询、状态更新等
 */
// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入旅游订单实体类
import com.icss.xihu.model.TravelOrder;

// 导入List集合接口
import java.util.List;

/**
 * 旅游订单服务接口
 * 功能概述：定义旅游订单相关的业务逻辑方法，包括订单创建、查询、状态更新等
 */
// 旅游订单服务接口，定义旅游订单相关的业务逻辑方法
public interface TravelOrderService {
    
    /**
     * 创建旅游订单
     * 功能概述：创建新的旅游订单，向数据库中插入订单记录
     * @param {TravelOrder} order - 旅游订单对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 创建旅游订单方法，接收旅游订单对象参数，返回插入的记录数
    int createOrder(TravelOrder order);
    
    /**
     * 根据订单号获取订单
     * 功能概述：根据订单号查询指定旅游订单的详细信息
     * @param {String} orderNo - 订单号
     * @return {TravelOrder} 返回订单对象，如果不存在则返回null
     */
    // 根据订单号获取订单方法，接收订单号参数，返回订单对象
    TravelOrder getOrderByOrderNo(String orderNo);
    
    /**
     * 根据用户ID获取订单列表
     * 功能概述：根据用户编号查询该用户的所有旅游订单信息
     * @param {Integer} userId - 用户编号
     * @return {List<TravelOrder>} 返回该用户的所有订单列表
     */
    // 根据用户ID获取订单列表方法，接收用户编号参数，返回该用户的所有订单列表
    List<TravelOrder> getOrdersByUserId(Integer userId);
    
    /**
     * 更新订单状态
     * 功能概述：根据订单编号更新旅游订单的状态
     * @param {Integer} orderId - 订单编号
     * @param {Integer} status - 订单状态（0-待支付，1-已支付，2-已取消，3-已完成）
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 更新订单状态方法，接收订单编号和订单状态参数，返回更新的记录数
    int updateOrderStatus(Integer orderId, Integer status);
}
