/**
 * 统一订单服务接口
 * 功能概述：定义统一订单相关的业务逻辑方法，包括订单创建、查询、状态更新、支付处理等
 */
// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入统一订单实体类
import com.icss.xihu.model.UnifiedOrder;

// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 统一订单服务接口
 * 功能概述：定义统一订单相关的业务逻辑方法，包括订单创建、查询、状态更新、支付处理等
 */
// 统一订单服务接口，定义统一订单相关的业务逻辑方法
public interface UnifiedOrderService {

    /**
     * 创建统一订单
     * 功能概述：创建新的统一订单，生成订单号，向数据库中插入订单记录
     * @param {UnifiedOrder} order - 统一订单对象
     * @return {String} 返回订单号
     */
    // 创建统一订单方法，接收统一订单对象参数，返回订单号
    String createOrder(UnifiedOrder order);

    /**
     * 根据订单号获取订单
     * 功能概述：根据订单号查询指定统一订单的详细信息
     * @param {String} orderNo - 订单号
     * @return {UnifiedOrder} 返回订单对象，如果不存在则返回null
     */
    // 根据订单号获取订单方法，接收订单号参数，返回订单对象
    UnifiedOrder getOrderByOrderNo(String orderNo);

    /**
     * 根据用户ID获取订单列表
     * 功能概述：根据用户编号查询该用户的所有统一订单信息，支持按订单类型和支付状态筛选
     * @param {Integer} userId - 用户编号
     * @param {String} orderType - 订单类型（可选，用于筛选订单类型）
     * @param {Integer} paymentStatus - 支付状态（可选，用于筛选支付状态）
     * @return {List<UnifiedOrder>} 返回该用户的所有订单列表
     */
    // 根据用户ID获取订单列表方法，接收用户编号、订单类型和支付状态参数，返回该用户的所有订单列表
    List<UnifiedOrder> getOrdersByUserId(Integer userId, String orderType, Integer paymentStatus);

    /**
     * 更新订单支付状态
     * 功能概述：根据订单号更新统一订单的支付状态和支付方式
     * @param {String} orderNo - 订单号
     * @param {Integer} paymentStatus - 支付状态（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
     * @param {String} paymentMethod - 支付方式（如"微信"、"支付宝"等）
     * @return {boolean} 返回是否更新成功（true-成功，false-失败）
     */
    // 更新订单支付状态方法，接收订单号、支付状态和支付方式参数，返回是否更新成功
    boolean updatePaymentStatus(String orderNo, Integer paymentStatus, String paymentMethod);

    /**
     * 取消订单
     * 功能概述：根据订单号取消统一订单（仅当订单状态为待支付时才能取消）
     * @param {String} orderNo - 订单号
     * @return {boolean} 返回是否取消成功（true-成功，false-失败）
     */
    // 取消订单方法，接收订单号参数，返回是否取消成功
    boolean cancelOrder(String orderNo);

    /**
     * 删除订单
     * 功能概述：根据订单号和用户编号从数据库中物理删除统一订单记录
     * @param {String} orderNo - 订单号
     * @param {Integer} userId - 用户编号
     * @return {boolean} 返回是否删除成功（true-成功，false-失败）
     */
    // 删除订单方法，接收订单号和用户编号参数，返回是否删除成功
    boolean deleteOrder(String orderNo, Integer userId);

    /**
     * 获取订单统计
     * 功能概述：根据用户编号统计该用户的订单信息（总订单数、待支付数、已支付数、已取消数等）
     * @param {Integer} userId - 用户编号
     * @return {Map<String, Object>} 返回订单统计结果（包含总订单数、待支付数、已支付数、已取消数等）
     */
    // 获取订单统计方法，接收用户编号参数，返回订单统计结果
    Map<String, Object> getOrderStats(Integer userId);

    /**
     * 处理支付(统一支付处理逻辑)
     * 功能概述：处理统一订单的支付，更新统一订单状态，同步更新各模块订单状态
     * @param {String} orderNo - 订单号
     * @param {String} paymentMethod - 支付方式（如"微信"、"支付宝"等）
     * @return {boolean} 返回是否支付成功（true-成功，false-失败）
     */
    // 处理支付方法（统一支付处理逻辑），接收订单号和支付方式参数，返回是否支付成功
    boolean processPayment(String orderNo, String paymentMethod);
}
