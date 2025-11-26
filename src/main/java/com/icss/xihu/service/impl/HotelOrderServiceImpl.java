// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入酒店订单Mapper接口
import com.icss.xihu.mapper.HotelOrderMapper;
// 导入酒店订单实体类
import com.icss.xihu.model.HotelOrder;
// 导入酒店订单详情实体类
import com.icss.xihu.model.HotelOrderDetail;
// 导入酒店订单服务接口
import com.icss.xihu.service.HotelOrderService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解
import org.springframework.stereotype.Service;

// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;
// 导入DateTimeFormatter类，用于日期时间格式化
import java.time.format.DateTimeFormatter;
// 导入List集合接口
import java.util.List;
// 导入Random类，用于生成随机数
import java.util.Random;

/**
 * 酒店订单服务实现类
 * 功能概述：实现酒店订单相关的业务逻辑，包括订单创建、查询、取消、确认、订单详情管理等
 */
// 标识该类为Spring服务类，会被Spring容器扫描并注册为Bean
@Service
// 酒店订单服务实现类，实现HotelOrderService接口
public class HotelOrderServiceImpl implements HotelOrderService {

    // 自动注入酒店订单Mapper，Spring容器会自动查找并注入HotelOrderMapper的实现类
    @Autowired
    // 酒店订单Mapper对象，用于调用数据库操作方法
    private HotelOrderMapper hotelOrderMapper;

    /**
     * 新增订单
     * 功能概述：创建新的酒店订单，如果订单号未设置则自动生成
     */
    // 实现新增订单方法
    @Override
    // 新增订单方法，接收酒店订单对象参数
    public int createOrder(HotelOrder order) {
        // 生成订单号（如果未设置）
        // 判断订单号是否为空或空字符串
        if (order.getOrderNo() == null || order.getOrderNo().isEmpty()) {
            // 如果订单号未设置，调用generateOrderNo方法生成订单号
            String orderNo = generateOrderNo();
            // 设置订单号到订单对象中
            order.setOrderNo(orderNo);
        }
        // 调用酒店订单Mapper的insert方法，将订单保存到数据库，返回受影响的行数
        return hotelOrderMapper.insert(order);
    }

    /**
     * 根据用户ID查询订单列表
     * 功能概述：根据用户ID查询该用户的所有酒店订单列表
     */
    // 实现根据用户ID查询订单列表方法
    @Override
    // 根据用户ID查询订单列表方法，接收用户ID参数
    public List<HotelOrder> getOrdersByUserId(String userId) {
        // 调用酒店订单Mapper的selectByUserId方法，根据用户ID查询订单列表
        return hotelOrderMapper.selectByUserId(userId);
    }

    /**
     * 根据订单ID查询订单
     * 功能概述：根据订单ID查询酒店订单详细信息
     */
    // 实现根据订单ID查询订单方法
    @Override
    // 根据订单ID查询订单方法，接收订单ID参数
    public HotelOrder getOrderById(Integer orderId) {
        // 调用酒店订单Mapper的findById方法，根据订单ID查询订单信息
        return hotelOrderMapper.findById(orderId);
    }

    /**
     * 取消订单
     * 功能概述：取消酒店订单，更新订单状态为已取消
     */
    // 实现取消订单方法
    @Override
    // 取消订单方法，接收订单ID参数
    public int cancelOrder(Long orderId) {
        // 更新订单状态为2（已取消）
        // 调用酒店订单Mapper的updateStatus方法，更新订单状态为已取消（2），返回受影响的行数
        return hotelOrderMapper.updateStatus(orderId, 2);
    }

    /**
     * 确认订单
     * 功能概述：确认酒店订单，更新订单状态为已支付/已确认
     */
    // 实现确认订单方法
    @Override
    // 确认订单方法，接收订单ID参数
    public int makeSureOrder(Long orderId) {
        // 调用酒店订单Mapper的updateStatus方法，更新订单状态为已支付/已确认（1），返回受影响的行数
        return hotelOrderMapper.updateStatus(orderId, 1);
    }

    /**
     * 创建订单详情
     * 功能概述：创建酒店订单详情，记录订单的详细信息（酒店、房型、入住退房日期等）
     */
    // 实现创建订单详情方法
    @Override
    // 创建订单详情方法，接收酒店订单详情对象参数
    public int createOrderDetail(HotelOrderDetail detail) {
        // 调用酒店订单Mapper的insertOrderDetail方法，将订单详情保存到数据库，返回受影响的行数
        return hotelOrderMapper.insertOrderDetail(detail);
    }

    /**
     * 根据订单号查询订单详情
     * 功能概述：根据订单号查询酒店订单的详细信息列表
     */
    // 实现根据订单号查询订单详情方法
    @Override
    // 根据订单号查询订单详情方法，接收订单号参数
    public List<HotelOrderDetail> getOrderDetailsByOrderNo(String orderNo) {
        // 调用酒店订单Mapper的findDetailsByOrderNo方法，根据订单号查询订单详情列表
        return hotelOrderMapper.findDetailsByOrderNo(orderNo);
    }

    /**
     * 更新统一订单号
     * 功能概述：更新酒店订单关联的统一订单号，建立与统一订单的关联关系
     */
    // 实现更新统一订单号方法
    @Override
    // 更新统一订单号方法，接收订单ID和统一订单号参数
    public int updateUnifiedOrderNo(Integer orderId, String unifiedOrderNo) {
        // 调用酒店订单Mapper的updateUnifiedOrderNo方法，更新统一订单号，返回受影响的行数
        return hotelOrderMapper.updateUnifiedOrderNo(orderId, unifiedOrderNo);
    }

    /**
     * 生成订单号
     * 功能概述：生成唯一的酒店订单号，格式为：HOTEL + 时间戳 + 随机数
     */
    // 实现生成订单号方法
    @Override
    // 生成订单号方法，返回订单号字符串
    public String generateOrderNo() {
        // 生成格式：HOTEL + 时间戳 + 随机数
        // 获取当前时间并格式化为字符串（格式：yyyyMMddHHmmss）
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 生成0-9999之间的随机数，格式化为4位数字字符串（不足4位前面补0）
        String random = String.format("%04d", new Random().nextInt(10000));
        // 拼接订单号：HOTEL + 时间戳 + 随机数
        return "HOTEL" + timestamp + random;
    }
} 