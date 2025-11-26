// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入酒店订单实体类
import com.icss.xihu.model.HotelOrder;
// 导入酒店订单详情实体类
import com.icss.xihu.model.HotelOrderDetail;

// 导入List集合接口
import java.util.List;

/**
 * 酒店订单服务接口
 * 功能概述：定义酒店订单相关的业务逻辑方法，包括订单创建、查询、取消、确认、订单详情管理等
 */
// 酒店订单服务接口，定义酒店订单相关的业务逻辑方法
public interface HotelOrderService {
    /**
     * 新增订单
     * 功能概述：创建新的酒店订单，如果订单号未设置则自动生成
     * @param order 酒店订单对象
     * @return 受影响的行数
     */
    // 新增订单方法，接收酒店订单对象参数
    int createOrder(HotelOrder order);
    
    /**
     * 根据用户ID查询订单列表
     * 功能概述：根据用户ID查询该用户的所有酒店订单列表
     * @param userId 用户ID（字符串类型）
     * @return 酒店订单列表
     */
    // 根据用户ID查询订单列表方法，接收用户ID参数
    List<HotelOrder> getOrdersByUserId(String userId);

    /**
     * 根据订单ID查询订单
     * 功能概述：根据订单ID查询酒店订单详细信息
     * @param orderId 订单ID
     * @return 酒店订单对象，不存在返回null
     */
    // 根据订单ID查询订单方法，接收订单ID参数
    HotelOrder getOrderById(Integer orderId);

    /**
     * 取消订单
     * 功能概述：取消酒店订单，更新订单状态为已取消
     * @param orderId 订单ID（Long类型）
     * @return 受影响的行数
     */
    // 取消订单方法，接收订单ID参数
    int cancelOrder(Long orderId);

    /**
     * 确认订单
     * 功能概述：确认酒店订单，更新订单状态为已支付/已确认
     * @param orderId 订单ID（Long类型）
     * @return 受影响的行数
     */
    // 确认订单方法，接收订单ID参数
    int makeSureOrder(Long orderId);

    /**
     * 创建订单详情
     * 功能概述：创建酒店订单详情，记录订单的详细信息（酒店、房型、入住退房日期等）
     * @param detail 酒店订单详情对象
     * @return 受影响的行数
     */
    // 创建订单详情方法，接收酒店订单详情对象参数
    int createOrderDetail(HotelOrderDetail detail);

    /**
     * 根据订单号查询订单详情
     * 功能概述：根据订单号查询酒店订单的详细信息列表
     * @param orderNo 订单号
     * @return 酒店订单详情列表
     */
    // 根据订单号查询订单详情方法，接收订单号参数
    List<HotelOrderDetail> getOrderDetailsByOrderNo(String orderNo);

    /**
     * 更新统一订单号
     * 功能概述：更新酒店订单关联的统一订单号，建立与统一订单的关联关系
     * @param orderId 订单ID
     * @param unifiedOrderNo 统一订单号
     * @return 受影响的行数
     */
    // 更新统一订单号方法，接收订单ID和统一订单号参数
    int updateUnifiedOrderNo(Integer orderId, String unifiedOrderNo);

    /**
     * 生成订单号
     * 功能概述：生成唯一的酒店订单号，格式为：HOTEL + 时间戳 + 随机数
     * @return 酒店订单号
     */
    // 生成订单号方法，返回订单号字符串
    String generateOrderNo();
} 