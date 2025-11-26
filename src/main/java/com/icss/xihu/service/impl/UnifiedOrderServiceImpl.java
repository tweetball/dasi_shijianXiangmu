/**
 * 统一订单服务实现类
 * 功能概述：实现UnifiedOrderService接口，提供统一订单相关的业务逻辑实现，包括订单创建、查询、状态更新、支付处理等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入所有Mapper接口（使用通配符导入）
import com.icss.xihu.mapper.*;
// 导入统一订单实体类
import com.icss.xihu.model.UnifiedOrder;
// 导入统一订单服务接口
import com.icss.xihu.service.UnifiedOrderService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;
// 导入Spring的事务注解，用于声明式事务管理
import org.springframework.transaction.annotation.Transactional;

// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;
// 导入UUID类，用于生成唯一标识符
import java.util.UUID;

/**
 * 统一订单服务实现
 * 功能概述：实现UnifiedOrderService接口，提供统一订单相关的业务逻辑实现，包括订单创建、查询、状态更新、支付处理等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 统一订单服务实现类，实现UnifiedOrderService接口
public class UnifiedOrderServiceImpl implements UnifiedOrderService {

    // 自动注入统一订单Mapper，Spring容器会自动查找并注入UnifiedOrderMapper的实现类
    @Autowired
    // 统一订单Mapper对象，用于调用数据库操作方法
    private UnifiedOrderMapper unifiedOrderMapper;

    // 自动注入购物订单Mapper，Spring容器会自动查找并注入ShopOrderMapper的实现类
    @Autowired
    // 购物订单Mapper对象，用于调用数据库操作方法
    private ShopOrderMapper shopOrderMapper;

    // 自动注入旅游订单Mapper，Spring容器会自动查找并注入TravelOrderMapper的实现类
    @Autowired
    // 旅游订单Mapper对象，用于调用数据库操作方法
    private TravelOrderMapper travelOrderMapper;

    // 自动注入酒店订单Mapper，Spring容器会自动查找并注入HotelOrderMapper的实现类
    @Autowired
    // 酒店订单Mapper对象，用于调用数据库操作方法
    private HotelOrderMapper hotelOrderMapper;

    // 自动注入缴费Mapper，Spring容器会自动查找并注入PaymentMapper的实现类
    @Autowired
    // 缴费Mapper对象，用于调用数据库操作方法
    private PaymentMapper paymentMapper;

    /**
     * 创建统一订单
     * 功能概述：创建新的统一订单，生成订单号，设置支付状态为待支付，向数据库中插入订单记录
     * @param {UnifiedOrder} order - 统一订单对象
     * @return {String} 返回订单号
     */
    // 重写接口中的createOrder方法
    @Override
    // 创建统一订单方法，接收统一订单对象参数，返回订单号
    public String createOrder(UnifiedOrder order) {
        // 生成订单号
        // 调用generateOrderNo方法，根据订单类型生成唯一的订单号
        String orderNo = generateOrderNo(order.getOrderType());
        // 将生成的订单号设置到订单对象中
        order.setOrderNo(orderNo);
        // 设置支付状态为待支付（0）
        order.setPaymentStatus(0); // 待支付
        
        // 调用Mapper层的insertOrder方法，将订单信息插入数据库
        unifiedOrderMapper.insertOrder(order);
        // 返回订单号
        return orderNo;
    }

    /**
     * 根据订单号获取订单
     * 功能概述：调用Mapper层方法，根据订单号查询指定统一订单的详细信息，并设置状态文本
     * @param {String} orderNo - 订单号
     * @return {UnifiedOrder} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的getOrderByOrderNo方法
    @Override
    // 根据订单号获取订单方法，接收订单号参数，返回订单对象
    public UnifiedOrder getOrderByOrderNo(String orderNo) {
        // 调用Mapper层的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrder order = unifiedOrderMapper.findByOrderNo(orderNo);
        // 如果订单不为空，设置状态文本
        if (order != null) {
            // 设置状态文本
            // 调用UnifiedOrder的静态方法getStatusText，根据支付状态码获取状态文本，并设置到订单对象中
            order.setPaymentStatusText(UnifiedOrder.getStatusText(order.getPaymentStatus()));
        }
        // 返回订单对象
        return order;
    }

    /**
     * 根据用户ID获取订单列表
     * 功能概述：调用Mapper层方法，根据用户编号查询该用户的所有统一订单信息，支持按订单类型和支付状态筛选，并为每个订单设置状态文本
     * @param {Integer} userId - 用户编号
     * @param {String} orderType - 订单类型（可选，用于筛选订单类型）
     * @param {Integer} paymentStatus - 支付状态（可选，用于筛选支付状态）
     * @return {List<UnifiedOrder>} 返回该用户的所有订单列表
     */
    // 重写接口中的getOrdersByUserId方法
    @Override
    // 根据用户ID获取订单列表方法，接收用户编号、订单类型和支付状态参数，返回该用户的所有订单列表
    public List<UnifiedOrder> getOrdersByUserId(Integer userId, String orderType, Integer paymentStatus) {
        // 调用Mapper层的findByUserId方法，根据用户编号、订单类型和支付状态查询该用户的所有订单信息
        List<UnifiedOrder> orders = unifiedOrderMapper.findByUserId(userId, orderType, paymentStatus);
        // 为每个订单设置状态文本
        // 遍历订单列表，为每个订单设置状态文本
        for (UnifiedOrder order : orders) {
            // 调用UnifiedOrder的静态方法getStatusText，根据支付状态码获取状态文本，并设置到订单对象中
            order.setPaymentStatusText(UnifiedOrder.getStatusText(order.getPaymentStatus()));
        }
        // 返回订单列表
        return orders;
    }

    /**
     * 更新订单支付状态
     * 功能概述：根据订单号更新统一订单的支付状态和支付方式，如果支付状态为已支付则设置支付时间（使用事务管理）
     * @param {String} orderNo - 订单号
     * @param {Integer} paymentStatus - 支付状态（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
     * @param {String} paymentMethod - 支付方式（如"微信"、"支付宝"等）
     * @return {boolean} 返回是否更新成功（true-成功，false-失败）
     */
    // 重写接口中的updatePaymentStatus方法
    @Override
    // 使用Spring的事务注解，确保方法在事务中执行，如果发生异常则回滚
    @Transactional
    // 更新订单支付状态方法，接收订单号、支付状态和支付方式参数，返回是否更新成功
    public boolean updatePaymentStatus(String orderNo, Integer paymentStatus, String paymentMethod) {
        // 如果支付状态为已支付（1），则设置支付时间为当前时间，否则设置为null
        LocalDateTime paymentTime = paymentStatus == 1 ? LocalDateTime.now() : null;
        // 调用Mapper层的updatePaymentStatus方法，更新订单支付状态、支付方式和支付时间
        int result = unifiedOrderMapper.updatePaymentStatus(orderNo, paymentStatus, paymentMethod, paymentTime);
        // 如果更新记录数大于0，返回true表示更新成功，否则返回false
        return result > 0;
    }

    /**
     * 取消订单
     * 功能概述：根据订单号取消统一订单（仅当订单状态为待支付时才能取消），并同步取消各模块订单（使用事务管理）
     * @param {String} orderNo - 订单号
     * @return {boolean} 返回是否取消成功（true-成功，false-失败）
     */
    // 重写接口中的cancelOrder方法
    @Override
    // 使用Spring的事务注解，确保方法在事务中执行，如果发生异常则回滚
    @Transactional
    // 取消订单方法，接收订单号参数，返回是否取消成功
    public boolean cancelOrder(String orderNo) {
        // 调用Mapper层的cancelOrder方法，取消统一订单（仅当订单状态为待支付时才能取消）
        int result = unifiedOrderMapper.cancelOrder(orderNo);
        // 如果取消成功（更新记录数大于0），同步取消各模块订单
        if (result > 0) {
            // 同步取消各模块订单
            // 调用Mapper层的findByOrderNo方法，根据订单号查询统一订单信息
            UnifiedOrder order = unifiedOrderMapper.findByOrderNo(orderNo);
            // 如果订单不为空，调用cancelModuleOrder方法，取消各模块订单
            if (order != null) {
                cancelModuleOrder(order);
            }
            // 返回true表示取消成功
            return true;
        }
        // 如果取消失败，返回false
        return false;
    }

    /**
     * 删除订单
     * 功能概述：调用Mapper层方法，根据订单号和用户编号从数据库中物理删除统一订单记录
     * @param {String} orderNo - 订单号
     * @param {Integer} userId - 用户编号
     * @return {boolean} 返回是否删除成功（true-成功，false-失败）
     */
    // 重写接口中的deleteOrder方法
    @Override
    // 删除订单方法，接收订单号和用户编号参数，返回是否删除成功
    public boolean deleteOrder(String orderNo, Integer userId) {
        // 调用Mapper层的deleteOrder方法，根据订单号和用户编号物理删除订单记录
        int result = unifiedOrderMapper.deleteOrder(orderNo, userId);
        // 如果删除记录数大于0，返回true表示删除成功，否则返回false
        return result > 0;
    }

    /**
     * 获取订单统计
     * 功能概述：调用Mapper层方法，根据用户编号统计该用户的订单信息（总订单数、待支付数、已支付数、已取消数等）
     * @param {Integer} userId - 用户编号
     * @return {Map<String, Object>} 返回订单统计结果（包含总订单数、待支付数、已支付数、已取消数等）
     */
    // 重写接口中的getOrderStats方法
    @Override
    // 获取订单统计方法，接收用户编号参数，返回订单统计结果
    public Map<String, Object> getOrderStats(Integer userId) {
        // 调用Mapper层的getOrderStats方法，根据用户编号统计该用户的订单信息并返回
        return unifiedOrderMapper.getOrderStats(userId);
    }

    /**
     * 处理支付(统一支付处理逻辑)
     * 功能概述：处理统一订单的支付，更新统一订单状态，同步更新各模块订单状态（使用事务管理）
     * @param {String} orderNo - 订单号
     * @param {String} paymentMethod - 支付方式（如"微信"、"支付宝"等）
     * @return {boolean} 返回是否支付成功（true-成功，false-失败）
     */
    // 重写接口中的processPayment方法
    @Override
    // 使用Spring的事务注解，确保方法在事务中执行，如果发生异常则回滚
    @Transactional
    // 处理支付方法（统一支付处理逻辑），接收订单号和支付方式参数，返回是否支付成功
    public boolean processPayment(String orderNo, String paymentMethod) {
        // 1. 查询统一订单
        // 调用Mapper层的findByOrderNo方法，根据订单号查询统一订单信息
        UnifiedOrder order = unifiedOrderMapper.findByOrderNo(orderNo);
        // 如果订单不存在或已支付，返回false
        if (order == null || order.getPaymentStatus() != 0) {
            return false; // 订单不存在或已支付
        }

        // 获取当前时间作为支付时间
        LocalDateTime paymentTime = LocalDateTime.now();

        // 2. 更新统一订单状态
        // 调用Mapper层的updatePaymentStatus方法，更新统一订单状态为已支付（1），设置支付方式和支付时间
        unifiedOrderMapper.updatePaymentStatus(orderNo, 1, paymentMethod, paymentTime);

        // 3. 根据订单类型更新各模块订单状态
        // 调用updateModuleOrderPaymentStatus方法，根据订单类型更新各模块订单状态
        updateModuleOrderPaymentStatus(order, paymentMethod, paymentTime);

        // 返回true表示支付成功
        return true;
    }

    /**
     * 更新各模块订单支付状态（私有方法）
     * 功能概述：根据统一订单的订单类型，更新对应模块的订单支付状态
     * @param {UnifiedOrder} order - 统一订单对象
     * @param {String} paymentMethod - 支付方式（如"微信"、"支付宝"等）
     * @param {LocalDateTime} paymentTime - 支付时间
     */
    // 更新各模块订单支付状态方法（私有方法），接收统一订单对象、支付方式和支付时间参数
    private void updateModuleOrderPaymentStatus(UnifiedOrder order, String paymentMethod, LocalDateTime paymentTime) {
        // 根据订单类型，使用switch语句更新对应模块的订单支付状态
        switch (order.getOrderType()) {
            case "SHOPPING":  // 如果订单类型为购物订单
                // 查询购物订单详情
                // 将统一订单号中的"UO"替换为"SO"，查询购物订单信息
                var shopOrder = shopOrderMapper.findByOrderNo(order.getOrderNo().replace("UO", "SO"));
                // 如果购物订单不为空，更新购物订单支付状态
                if (shopOrder != null) {
                    // 注意：updatePaymentStatus 方法已移除 paymentTime 参数（数据库表中没有 payment_time 字段）
                    // 调用Mapper层的updatePaymentStatus方法，更新购物订单支付状态为已支付（1）
                    shopOrderMapper.updatePaymentStatus(shopOrder.getOrderNo(), 1);
                }
                break;
            case "TRAVEL":  // 如果订单类型为旅游订单
                // 查询旅游订单详情
                // 将统一订单号中的"UO"替换为"TO"，查询旅游订单信息
                var travelOrder = travelOrderMapper.findByOrderNo(order.getOrderNo().replace("UO", "TO"));
                // 如果旅游订单不为空，更新旅游订单支付状态
                if (travelOrder != null) {
                    // 调用Mapper层的updatePaymentStatus方法，更新旅游订单支付状态为已支付（1），设置支付时间
                    travelOrderMapper.updatePaymentStatus(travelOrder.getOrderNo(), 1, paymentTime);
                }
                break;
            case "HOTEL":  // 如果订单类型为酒店订单
                // 更新酒店订单状态
                // 调用Mapper层的updateStatusByOrderNo方法，根据模块订单编号更新酒店订单状态为已支付（1）
                hotelOrderMapper.updateStatusByOrderNo(order.getModuleOrderId(), 1);
                break;
            case "PAYMENT":  // 如果订单类型为缴费订单
                // 更新缴费账单状态
                // 调用Mapper层的updatePaymentBillStatus方法，根据模块订单编号更新缴费账单状态为已缴费（1），设置缴费时间
                paymentMapper.updatePaymentBillStatus(order.getModuleOrderId(), 1, paymentTime);
                break;
            case "FOOD":  // 如果订单类型为美食订单
                // 美食订单暂时保留原有逻辑,后续再集成
                break;
        }
    }

    /**
     * 取消各模块订单（私有方法）
     * 功能概述：根据统一订单的订单类型，取消对应模块的订单
     * @param {UnifiedOrder} order - 统一订单对象
     */
    // 取消各模块订单方法（私有方法），接收统一订单对象参数
    private void cancelModuleOrder(UnifiedOrder order) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 根据订单类型，使用switch语句取消对应模块的订单
        switch (order.getOrderType()) {
            case "SHOPPING":  // 如果订单类型为购物订单
                // 将统一订单号中的"UO"替换为"SO"，查询购物订单信息
                var shopOrder = shopOrderMapper.findByOrderNo(order.getOrderNo().replace("UO", "SO"));
                // 如果购物订单不为空，更新购物订单支付状态为已取消（2）
                if (shopOrder != null) {
                    // 注意：updatePaymentStatus 方法已移除 paymentTime 参数（数据库表中没有 payment_time 字段）
                    // 调用Mapper层的updatePaymentStatus方法，更新购物订单支付状态为已取消（2）
                    shopOrderMapper.updatePaymentStatus(shopOrder.getOrderNo(), 2);
                }
                break;
            case "TRAVEL":  // 如果订单类型为旅游订单
                // 将统一订单号中的"UO"替换为"TO"，查询旅游订单信息
                var travelOrder = travelOrderMapper.findByOrderNo(order.getOrderNo().replace("UO", "TO"));
                // 如果旅游订单不为空，更新旅游订单支付状态为已取消（2），设置支付时间
                if (travelOrder != null) {
                    // 调用Mapper层的updatePaymentStatus方法，更新旅游订单支付状态为已取消（2），设置支付时间
                    travelOrderMapper.updatePaymentStatus(travelOrder.getOrderNo(), 2, now);
                }
                break;
            case "HOTEL":  // 如果订单类型为酒店订单
                // 调用Mapper层的updateStatusByOrderNo方法，根据模块订单编号更新酒店订单状态为已取消（2）
                hotelOrderMapper.updateStatusByOrderNo(order.getModuleOrderId(), 2);
                break;
        }
    }

    /**
     * 生成订单号（私有方法）
     * 功能概述：根据订单类型生成唯一的统一订单号，格式为：UO + 订单类型首字母 + 时间戳 + 8位UUID
     * @param {String} orderType - 订单类型（SHOPPING/TRAVEL/HOTEL/PAYMENT/FOOD）
     * @return {String} 返回订单号字符串
     */
    // 生成订单号方法（私有方法），接收订单类型参数，返回订单号字符串
    private String generateOrderNo(String orderType) {
        // 订单号前缀：UO（Unified Order）
        String prefix = "UO"; // Unified Order
        // 获取当前时间戳（毫秒数），转换为字符串
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 生成UUID，取前8位并转换为大写
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        // 拼接订单号：前缀 + 订单类型首字母 + 时间戳 + UUID，并返回
        return prefix + orderType.substring(0, 1) + timestamp + uuid;
    }
}
