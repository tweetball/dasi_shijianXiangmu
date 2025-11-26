// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入购物订单实体类
import com.icss.xihu.model.ShopOrder;
// 导入购物订单详情实体类
import com.icss.xihu.model.ShopOrderDetail;

// 导入List集合接口
import java.util.List;

/**
 * 购物订单服务接口
 * 功能概述：定义购物订单相关的业务逻辑方法，包括订单创建、查询、订单详情管理、状态更新等
 */
// 购物订单服务接口，定义购物订单相关的业务逻辑方法
public interface ShopOrderService {
    
    /**
     * 创建购物订单
     * 功能概述：创建新的购物订单，如果订单号未设置则自动生成
     * @param order 购物订单对象
     * @return 受影响的行数
     */
    // 创建购物订单方法，接收购物订单对象参数
    int createOrder(ShopOrder order);
    
    /**
     * 创建订单详情
     * 功能概述：创建购物订单详情，记录订单的商品信息（商品ID、名称、价格、数量等）
     * @param detail 购物订单详情对象
     * @return 受影响的行数
     */
    // 创建订单详情方法，接收购物订单详情对象参数
    int createOrderDetail(ShopOrderDetail detail);
    
    /**
     * 根据订单号获取订单
     * 功能概述：根据订单号查询购物订单详细信息
     * @param orderNo 订单号
     * @return 购物订单对象，不存在返回null
     */
    // 根据订单号获取订单方法，接收订单号参数
    ShopOrder getOrderByOrderNo(String orderNo);
    
    /**
     * 根据订单ID获取订单
     * 功能概述：根据订单ID查询购物订单详细信息
     * @param orderId 订单ID
     * @return 购物订单对象，不存在返回null
     */
    // 根据订单ID获取订单方法，接收订单ID参数
    ShopOrder getOrderById(Integer orderId);
    
    /**
     * 根据订单号获取订单详情
     * 功能概述：根据订单号查询购物订单的商品详情列表
     * @param orderNo 订单号
     * @return 购物订单详情列表
     */
    // 根据订单号获取订单详情方法，接收订单号参数
    List<ShopOrderDetail> getOrderDetailsByOrderNo(String orderNo);
    
    /**
     * 根据订单ID获取订单详情（兼容方法）
     * 功能概述：根据订单ID查询购物订单的商品详情列表，用于兼容旧代码
     * @param orderId 订单ID
     * @return 购物订单详情列表
     */
    // 根据订单ID获取订单详情方法（兼容方法），接收订单ID参数
    List<ShopOrderDetail> getOrderDetailsByOrderId(Integer orderId);
    
    /**
     * 更新订单状态
     * 功能概述：更新购物订单的状态（待支付、已支付、已取消、已完成等）
     * @param orderId 订单ID
     * @param status 订单状态（0=待支付，1=已支付，2=已取消，3=已完成）
     * @return 受影响的行数
     */
    // 更新订单状态方法，接收订单ID和订单状态参数
    int updateOrderStatus(Integer orderId, Integer status);
    
    /**
     * 更新订单总金额
     * 功能概述：更新购物订单的总金额（用于购物车添加商品时更新金额）
     * @param orderId 订单ID
     * @param totalAmount 新的总金额
     * @return 受影响的行数
     */
    // 更新订单总金额方法，接收订单ID和总金额参数
    int updateTotalAmount(Integer orderId, java.math.BigDecimal totalAmount);
    
    /**
     * 生成订单号
     * 功能概述：生成唯一的购物订单号，格式为：SHOP + 时间戳 + 随机数
     * @return 购物订单号
     */
    // 生成订单号方法，返回订单号字符串
    String generateOrderNo();
}
