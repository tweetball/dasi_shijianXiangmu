// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 重新设计的统一订单服务接口
 * 功能概述：定义统一订单相关的业务逻辑方法，包括订单创建、查询、支付、取消、删除等
 */
// 统一订单新服务接口，定义统一订单相关的业务逻辑方法
public interface UnifiedOrderNewService {

    /**
     * 创建统一订单
     * 功能概述：创建新的统一订单，生成订单号，关联模块订单，设置订单信息
     * @param userId 用户ID
     * @param orderType 订单类型（FOOD、HOTEL、SHOPPING、TRAVEL、PAYMENT）
     * @param moduleOrderId 模块订单ID（对应各业务模块的订单ID）
     * @param orderTitle 订单标题
     * @param orderDescription 订单描述
     * @param totalAmount 订单总金额
     * @return 统一订单号，失败返回null
     */
    // 创建统一订单方法，接收用户ID、订单类型、模块订单ID、订单标题、订单描述和总金额参数
    String createOrder(Integer userId, String orderType, Integer moduleOrderId,
                     String orderTitle, String orderDescription, BigDecimal totalAmount);

    /**
     * 根据订单号获取订单
     * 功能概述：根据统一订单号查询订单详细信息
     * @param orderNo 统一订单号
     * @return 统一订单对象，不存在返回null
     */
    // 根据订单号获取订单方法，接收订单号参数
    UnifiedOrderNew getOrderByOrderNo(String orderNo);

    /**
     * 根据用户ID获取订单列表
     * 功能概述：根据用户ID查询订单列表，支持按订单类型和支付状态筛选
     * @param userId 用户ID
     * @param orderType 订单类型（可选，null表示查询所有类型）
     * @param paymentStatus 支付状态（可选，null表示查询所有状态）
     * @return 订单列表
     */
    // 根据用户ID获取订单列表方法，接收用户ID、订单类型和支付状态参数（均为可选）
    List<UnifiedOrderNew> getOrdersByUserId(Integer userId, String orderType, Integer paymentStatus);

    /**
     * 处理支付
     * 功能概述：处理订单支付，更新统一订单状态，同步更新各模块订单状态
     * @param orderNo 统一订单号
     * @param paymentMethod 支付方式（微信、支付宝、银行卡等）
     * @return 是否支付成功
     */
    // 处理支付方法，接收订单号和支付方式参数
    boolean processPayment(String orderNo, String paymentMethod);

    /**
     * 取消订单
     * 功能概述：取消订单，更新统一订单状态，同步取消各模块订单
     * @param orderNo 统一订单号
     * @param userId 用户ID（用于验证订单归属）
     * @return 是否取消成功
     */
    // 取消订单方法，接收订单号和用户ID参数
    boolean cancelOrder(String orderNo, Integer userId);

    /**
     * 删除订单
     * 功能概述：删除订单，仅删除统一订单记录（不删除模块订单）
     * @param orderNo 统一订单号
     * @param userId 用户ID（用于验证订单归属）
     * @return 是否删除成功
     */
    // 删除订单方法，接收订单号和用户ID参数
    boolean deleteOrder(String orderNo, Integer userId);

    /**
     * 获取订单统计
     * 功能概述：获取用户的订单统计数据，包括订单总数、各状态订单数量等
     * @param userId 用户ID
     * @return 订单统计数据Map
     */
    // 获取订单统计方法，接收用户ID参数
    Map<String, Object> getOrderStats(Integer userId);

    /**
     * 检查订单是否可以支付
     * 功能概述：检查订单是否存在且处于待支付状态
     * @param orderNo 统一订单号
     * @return 是否可以支付
     */
    // 检查订单是否可以支付方法，接收订单号参数
    boolean canPay(String orderNo);

    /**
     * 检查订单是否可以取消
     * 功能概述：检查订单是否存在、属于该用户且可以取消
     * @param orderNo 统一订单号
     * @param userId 用户ID
     * @return 是否可以取消
     */
    // 检查订单是否可以取消方法，接收订单号和用户ID参数
    boolean canCancel(String orderNo, Integer userId);

    /**
     * 检查订单是否可以删除
     * 功能概述：检查订单是否存在、属于该用户且可以删除
     * @param orderNo 统一订单号
     * @param userId 用户ID
     * @return 是否可以删除
     */
    // 检查订单是否可以删除方法，接收订单号和用户ID参数
    boolean canDelete(String orderNo, Integer userId);

    /**
     * 生成订单号
     * 功能概述：根据订单类型生成唯一的统一订单号
     * @param orderType 订单类型
     * @return 统一订单号（格式：UO + 类型前缀 + 时间戳 + 随机数）
     */
    // 生成订单号方法，接收订单类型参数
    String generateOrderNo(String orderType);

    /**
     * 根据模块订单ID和订单类型查找待支付订单（用于避免重复创建订单）
     * 功能概述：根据模块订单ID和订单类型查找是否存在待支付的统一订单，用于避免重复创建
     * @param moduleOrderId 模块订单ID
     * @param orderType 订单类型
     * @return 待支付订单对象，不存在返回null
     */
    // 根据模块订单ID和订单类型查找待支付订单方法，接收模块订单ID和订单类型参数
    UnifiedOrderNew findUnpaidOrderByModuleOrderId(Integer moduleOrderId, String orderType);
    
    /**
     * 根据用户ID和订单类型查找待支付订单（用于购物车多次结算时复用订单）
     * 功能概述：根据用户ID和订单类型查找是否存在待支付的统一订单，用于购物车多次结算时复用订单
     * @param userId 用户ID
     * @param orderType 订单类型
     * @return 待支付订单对象，不存在返回null
     */
    // 根据用户ID和订单类型查找待支付订单方法，接收用户ID和订单类型参数
    UnifiedOrderNew findUnpaidOrderByUserIdAndType(Integer userId, String orderType);
    
    /**
     * 更新订单总金额
     * 功能概述：更新统一订单的总金额（用于购物车添加商品时更新金额）
     * @param orderNo 统一订单号
     * @param totalAmount 新的总金额
     * @return 是否更新成功
     */
    // 更新订单总金额方法，接收订单号和总金额参数
    boolean updateTotalAmount(String orderNo, BigDecimal totalAmount);
    
    /**
     * 更新模块订单ID（当订单已存在但关联了新的模块订单时）
     * 功能概述：更新统一订单关联的模块订单ID（用于购物车添加商品时更新关联）
     * @param orderNo 统一订单号
     * @param moduleOrderId 新的模块订单ID
     * @return 是否更新成功
     */
    // 更新模块订单ID方法，接收订单号和模块订单ID参数
    boolean updateModuleOrderId(String orderNo, Integer moduleOrderId);
}
