// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入购物订单Mapper接口
import com.icss.xihu.mapper.ShopOrderMapper;
// 导入购物订单实体类
import com.icss.xihu.model.ShopOrder;
// 导入购物订单详情实体类
import com.icss.xihu.model.ShopOrderDetail;
// 导入购物订单服务接口
import com.icss.xihu.service.ShopOrderService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解
import org.springframework.stereotype.Service;
// 导入Spring的事务注解
import org.springframework.transaction.annotation.Transactional;

// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;
// 导入DateTimeFormatter类，用于日期时间格式化
import java.time.format.DateTimeFormatter;
// 导入List集合接口
import java.util.List;
// 导入Random类，用于生成随机数
import java.util.Random;

/**
 * 购物订单服务实现类
 * 功能概述：实现购物订单相关的业务逻辑，包括订单创建、查询、订单详情管理、状态更新等
 */
// 标识该类为Spring服务类，会被Spring容器扫描并注册为Bean
@Service
// 购物订单服务实现类，实现ShopOrderService接口
public class ShopOrderServiceImpl implements ShopOrderService {

    // 自动注入购物订单Mapper，Spring容器会自动查找并注入ShopOrderMapper的实现类
    @Autowired
    // 购物订单Mapper对象，用于调用数据库操作方法
    private ShopOrderMapper shopOrderMapper;

    /**
     * 创建购物订单
     * 功能概述：创建新的购物订单，如果订单号未设置则自动生成
     */
    // 实现创建购物订单方法
    @Override
    // 开启事务，确保订单创建的原子性
    @Transactional
    // 创建购物订单方法，接收购物订单对象参数
    public int createOrder(ShopOrder order) {
        // 生成订单号（如果未设置）
        // 判断订单号是否为空或空字符串
        if (order.getOrderNo() == null || order.getOrderNo().isEmpty()) {
            // 如果订单号未设置，调用generateOrderNo方法生成订单号
            String orderNo = generateOrderNo();
            // 设置订单号到订单对象中
            order.setOrderNo(orderNo);
        }
        // 调用购物订单Mapper的insertOrder方法，将订单保存到数据库，返回受影响的行数
        return shopOrderMapper.insertOrder(order);
    }

    /**
     * 创建订单详情
     * 功能概述：创建购物订单详情，记录订单的商品信息（商品ID、名称、价格、数量等）
     */
    // 实现创建订单详情方法
    @Override
    // 创建订单详情方法，接收购物订单详情对象参数
    public int createOrderDetail(ShopOrderDetail detail) {
        // 调用购物订单Mapper的insertOrderDetail方法，将订单详情保存到数据库，返回受影响的行数
        return shopOrderMapper.insertOrderDetail(detail);
    }

    /**
     * 根据订单号获取订单
     * 功能概述：根据订单号查询购物订单详细信息
     */
    // 实现根据订单号获取订单方法
    @Override
    // 根据订单号获取订单方法，接收订单号参数
    public ShopOrder getOrderByOrderNo(String orderNo) {
        // 调用购物订单Mapper的findByOrderNo方法，根据订单号查询订单信息
        return shopOrderMapper.findByOrderNo(orderNo);
    }
    
    /**
     * 根据订单ID获取订单
     * 功能概述：根据订单ID查询购物订单详细信息
     */
    // 实现根据订单ID获取订单方法
    @Override
    // 根据订单ID获取订单方法，接收订单ID参数
    public ShopOrder getOrderById(Integer orderId) {
        // 调用购物订单Mapper的findById方法，根据订单ID查询订单信息
        return shopOrderMapper.findById(orderId);
    }

    /**
     * 根据订单号获取订单详情
     * 功能概述：根据订单号查询购物订单的商品详情列表
     */
    // 实现根据订单号获取订单详情方法
    @Override
    // 根据订单号获取订单详情方法，接收订单号参数
    public List<ShopOrderDetail> getOrderDetailsByOrderNo(String orderNo) {
        // 调用购物订单Mapper的findDetailsByOrderNo方法，根据订单号查询订单详情列表
        return shopOrderMapper.findDetailsByOrderNo(orderNo);
    }
    
    /**
     * 根据订单ID获取订单详情（兼容方法）
     * 功能概述：根据订单ID查询购物订单的商品详情列表，用于兼容旧代码
     */
    // 实现根据订单ID获取订单详情方法（兼容方法）
    @Override
    // 根据订单ID获取订单详情方法（兼容方法），接收订单ID参数
    public List<ShopOrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        // 调用购物订单Mapper的findDetailsByOrderId方法，根据订单ID查询订单详情列表
        return shopOrderMapper.findDetailsByOrderId(orderId);
    }

    /**
     * 更新订单状态
     * 功能概述：更新购物订单的状态（待支付、已支付、已取消、已完成等）
     */
    // 实现更新订单状态方法
    @Override
    // 更新订单状态方法，接收订单ID和订单状态参数
    public int updateOrderStatus(Integer orderId, Integer status) {
        // 调用购物订单Mapper的updateStatusById方法，更新订单状态，返回受影响的行数
        return shopOrderMapper.updateStatusById(orderId, status);
    }
    
    /**
     * 更新订单总金额
     * 功能概述：更新购物订单的总金额（用于购物车添加商品时更新金额）
     */
    // 实现更新订单总金额方法
    @Override
    // 更新订单总金额方法，接收订单ID和总金额参数
    public int updateTotalAmount(Integer orderId, java.math.BigDecimal totalAmount) {
        // 调用购物订单Mapper的updateTotalAmount方法，更新订单总金额，返回受影响的行数
        return shopOrderMapper.updateTotalAmount(orderId, totalAmount);
    }
    
    /**
     * 生成订单号
     * 功能概述：生成唯一的购物订单号，格式为：SHOP + 时间戳 + 随机数
     */
    // 实现生成订单号方法
    @Override
    // 生成订单号方法，返回订单号字符串
    public String generateOrderNo() {
        // 生成格式：SHOP + 时间戳 + 随机数
        // 获取当前时间并格式化为字符串（格式：yyyyMMddHHmmss）
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 生成0-9999之间的随机数，格式化为4位数字字符串（不足4位前面补0）
        String random = String.format("%04d", new Random().nextInt(10000));
        // 拼接订单号：SHOP + 时间戳 + 随机数
        return "SHOP" + timestamp + random;
    }
}
