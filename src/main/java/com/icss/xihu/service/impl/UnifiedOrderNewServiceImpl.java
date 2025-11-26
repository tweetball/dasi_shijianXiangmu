// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入所有Mapper接口
import com.icss.xihu.mapper.*;
// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入统一订单新服务接口
import com.icss.xihu.service.UnifiedOrderNewService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解
import org.springframework.stereotype.Service;
// 导入Spring的事务注解
import org.springframework.transaction.annotation.Transactional;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 重新设计的统一订单服务实现
 * 功能概述：实现统一订单相关的业务逻辑，包括订单创建、查询、支付、取消、删除等，确保各模块订单状态同步
 */
// 标识该类为Spring服务类，会被Spring容器扫描并注册为Bean
@Service
// 统一订单新服务实现类，实现UnifiedOrderNewService接口
public class UnifiedOrderNewServiceImpl implements UnifiedOrderNewService {

    // 自动注入统一订单新Mapper，Spring容器会自动查找并注入UnifiedOrderNewMapper的实现类
    @Autowired
    // 统一订单新Mapper对象，用于调用数据库操作方法
    private UnifiedOrderNewMapper unifiedOrderNewMapper;
    
    // 自动注入酒店订单Mapper，Spring容器会自动查找并注入HotelOrderMapper的实现类
    @Autowired
    // 酒店订单Mapper对象，用于调用酒店订单相关的数据库操作方法
    private HotelOrderMapper hotelOrderMapper;
    
    // 自动注入购物订单Mapper，Spring容器会自动查找并注入ShopOrderMapper的实现类
    @Autowired
    // 购物订单Mapper对象，用于调用购物订单相关的数据库操作方法
    private ShopOrderMapper shopOrderMapper;
    
    // 自动注入旅游订单Mapper，Spring容器会自动查找并注入TravelOrderMapper的实现类
    @Autowired
    // 旅游订单Mapper对象，用于调用旅游订单相关的数据库操作方法
    private TravelOrderMapper travelOrderMapper;
    
    // 自动注入缴费Mapper，Spring容器会自动查找并注入PaymentMapper的实现类
    @Autowired
    // 缴费Mapper对象，用于调用缴费相关的数据库操作方法
    private PaymentMapper paymentMapper;
    
    // 自动注入餐厅Mapper，Spring容器会自动查找并注入RestaurantMapper的实现类
    @Autowired
    // 餐厅Mapper对象，用于调用餐厅订单相关的数据库操作方法
    private RestaurantMapper restaurantMapper;

    /**
     * 创建统一订单
     * 功能概述：创建新的统一订单，生成订单号，关联模块订单，设置订单信息
     */
    // 实现创建统一订单方法
    @Override
    // 开启事务，确保订单创建的原子性
    @Transactional
    // 创建统一订单方法，接收用户ID、订单类型、模块订单ID、订单标题、订单描述和总金额参数
    public String createOrder(Integer userId, String orderType, Integer moduleOrderId,
                            String orderTitle, String orderDescription, BigDecimal totalAmount) {
        // 生成订单号
        // 调用generateOrderNo方法生成唯一的统一订单号
        String orderNo = generateOrderNo(orderType);
        
        // 创建订单对象
        // 创建统一订单新对象
        UnifiedOrderNew order = new UnifiedOrderNew();
        // 设置订单号
        order.setOrderNo(orderNo);
        // 设置用户ID
        order.setUserId(userId);
        // 设置订单类型
        order.setOrderType(orderType);
        // 设置模块订单ID（关联各业务模块的订单ID）
        order.setModuleOrderId(moduleOrderId);
        // 设置订单标题
        order.setOrderTitle(orderTitle);
        // 设置订单描述
        order.setOrderDescription(orderDescription);
        // 设置订单总金额
        order.setTotalAmount(totalAmount);
        // 设置支付状态为待支付（STATUS_UNPAID = 0）
        order.setPaymentStatus(UnifiedOrderNew.STATUS_UNPAID);
        
        // 插入订单
        // 调用统一订单新Mapper的insertOrder方法，将订单保存到数据库，返回受影响的行数
        int result = unifiedOrderNewMapper.insertOrder(order);
        // 判断订单是否创建成功（受影响行数大于0表示成功）
        if (result > 0) {
            // 返回订单号
            return orderNo;
        }
        // 订单创建失败，返回null
        return null;
    }

    /**
     * 根据订单号获取订单
     * 功能概述：调用Mapper层方法，根据统一订单号查询指定统一订单的详细信息
     * @param {String} orderNo - 统一订单号
     * @return {UnifiedOrderNew} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的getOrderByOrderNo方法
    @Override
    // 根据订单号获取订单方法，接收统一订单号参数，返回订单对象
    public UnifiedOrderNew getOrderByOrderNo(String orderNo) {
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息并返回
        return unifiedOrderNewMapper.findByOrderNo(orderNo);
    }

    /**
     * 根据用户ID获取订单列表
     * 功能概述：调用Mapper层方法，根据用户编号查询该用户的所有统一订单信息，支持按订单类型和支付状态筛选
     * @param {Integer} userId - 用户编号
     * @param {String} orderType - 订单类型（可选，用于筛选订单类型）
     * @param {Integer} paymentStatus - 支付状态（可选，用于筛选支付状态）
     * @return {List<UnifiedOrderNew>} 返回该用户的所有订单列表
     */
    // 重写接口中的getOrdersByUserId方法
    @Override
    // 根据用户ID获取订单列表方法，接收用户编号、订单类型和支付状态参数，返回该用户的所有订单列表
    public List<UnifiedOrderNew> getOrdersByUserId(Integer userId, String orderType, Integer paymentStatus) {
        // 调用统一订单新Mapper的findByUserId方法，根据用户编号、订单类型和支付状态查询该用户的所有订单信息并返回
        return unifiedOrderNewMapper.findByUserId(userId, orderType, paymentStatus);
    }

    /**
     * 处理支付
     * 功能概述：处理订单支付，更新统一订单状态，同步更新各模块订单状态，确保数据一致性
     */
    // 实现处理支付方法
    @Override
    // 开启事务，确保支付处理的原子性
    @Transactional
    // 处理支付方法，接收订单号和支付方式参数
    public boolean processPayment(String orderNo, String paymentMethod) {
        // 1. 检查订单是否存在且可支付
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrderNew order = unifiedOrderNewMapper.findByOrderNo(orderNo);
        // 判断订单是否存在且可以支付
        if (order == null || !order.canPay()) {
            // 如果订单不存在或不可支付，返回false
            return false;
        }
        
        // 获取当前时间作为支付时间
        LocalDateTime paymentTime = LocalDateTime.now();
        
        // 2. 更新统一订单支付状态
        // 调用统一订单新Mapper的updatePaymentStatus方法，更新订单支付状态为已支付
        int result = unifiedOrderNewMapper.updatePaymentStatus(
            orderNo,  // 订单号
            UnifiedOrderNew.STATUS_PAID,  // 支付状态为已支付（STATUS_PAID = 1）
            paymentMethod,  // 支付方式
            paymentTime  // 支付时间
        );
        
        // 判断订单状态是否更新成功
        if (result <= 0) {
            // 如果更新失败，返回false
            return false;
        }
        
        // 3. 根据订单类型更新各模块订单状态（关键：确保数据一致性）
        // 调用updateModuleOrderPaymentStatus方法，同步更新各模块订单的支付状态
        updateModuleOrderPaymentStatus(order, paymentMethod, paymentTime);
        
        // 返回true表示支付成功
        return true;
    }
    
    /**
     * 更新各模块订单支付状态
     * 功能概述：通过订单类型和模块订单ID定位并更新对应的模块订单支付状态，确保数据一致性
     * @param order 统一订单对象（包含 orderType 和 moduleOrderId）
     * @param paymentMethod 支付方式
     * @param paymentTime 支付时间
     */
    // 私有方法，更新各模块订单支付状态
    private void updateModuleOrderPaymentStatus(UnifiedOrderNew order, String paymentMethod, LocalDateTime paymentTime) {
        // 判断模块订单ID是否为空
        if (order.getModuleOrderId() == null) {
            // 如果没有模块订单ID，说明该订单不关联具体业务订单（如直接创建的统一订单）
            // 直接返回，不处理
            return;
        }
        
        // 获取模块订单ID
        Integer moduleOrderId = order.getModuleOrderId();
        // 获取订单类型
        String orderType = order.getOrderType();
        
        // 使用try-catch捕获异常
        try {
            // 根据订单类型更新对应的模块订单状态
            switch (orderType) {
                // 酒店订单类型
                case UnifiedOrderNew.TYPE_HOTEL:
                    // 更新酒店订单状态：moduleOrderId 是酒店订单ID
                    // 调用酒店订单Mapper的updateStatusByOrderNo方法，更新订单状态为已支付（1）
                    hotelOrderMapper.updateStatusByOrderNo(moduleOrderId, 1);
                    // 跳出switch语句
                    break;
                    
                // 购物订单类型
                case UnifiedOrderNew.TYPE_SHOPPING:
                    // 更新购物订单状态：moduleOrderId 是购物订单ID
                    // 调用购物订单Mapper的updateStatusById方法，更新订单状态为已支付（1）
                    shopOrderMapper.updateStatusById(moduleOrderId, 1);
                    // 跳出switch语句
                    break;
                    
                // 旅游订单类型
                case UnifiedOrderNew.TYPE_TRAVEL:
                    // 更新旅游订单状态：moduleOrderId 是旅游订单ID
                    // 调用旅游订单Mapper的updateStatusById方法，更新订单状态为已支付（1）
                    travelOrderMapper.updateStatusById(moduleOrderId, 1);
                    // 跳出switch语句
                    break;
                    
                // 缴费订单类型
                case UnifiedOrderNew.TYPE_PAYMENT:
                    // 更新缴费账单状态：moduleOrderId 是缴费账单ID
                    // 调用缴费Mapper的updatePaymentBillStatus方法，更新账单状态为已缴费（1），并设置支付时间
                    paymentMapper.updatePaymentBillStatus(moduleOrderId, 1, paymentTime);
                    // 跳出switch语句
                    break;
                    
                // 美食订单类型
                case UnifiedOrderNew.TYPE_FOOD:
                    // 更新美食订单状态：moduleOrderId 是美食订单ID
                    // RestaurantOrder 使用 Integer orderStatus，1=已支付
                    // 调用餐厅Mapper的updateOrderStatus方法，更新订单状态为已支付（1）
                    restaurantMapper.updateOrderStatus(moduleOrderId.longValue(), 1);
                    // 跳出switch语句
                    break;
                    
                // 默认情况
                default:
                    // 未知订单类型，不处理
                    // 跳出switch语句
                    break;
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 记录日志，但不抛出异常，避免影响统一订单状态更新
            // 打印错误信息到控制台
            System.err.println("更新模块订单状态失败: orderType=" + orderType + ", moduleOrderId=" + moduleOrderId);
            // 打印异常堆栈信息
            e.printStackTrace();
            // 注意：这里不抛出异常，因为统一订单已经更新成功
            // 如果模块订单更新失败，需要后续通过补偿机制处理
        }
    }

    /**
     * 取消订单
     * 功能概述：取消订单，更新统一订单状态，同步取消各模块订单，确保数据一致性
     */
    // 实现取消订单方法
    @Override
    // 开启事务，确保取消订单的原子性
    @Transactional
    // 取消订单方法，接收订单号和用户ID参数
    public boolean cancelOrder(String orderNo, Integer userId) {
        // 1. 检查订单是否存在且属于该用户
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrderNew order = unifiedOrderNewMapper.findByOrderNo(orderNo);
        // 判断订单是否存在且属于该用户
        if (order == null || !order.getUserId().equals(userId)) {
            // 如果订单不存在或不属于该用户，返回false
            return false;
        }
        
        // 2. 检查是否可以取消
        // 调用订单对象的canCancel方法，判断订单是否可以取消
        if (!order.canCancel()) {
            // 如果订单不可取消，返回false
            return false;
        }
        
        // 3. 取消统一订单
        // 调用统一订单新Mapper的cancelOrder方法，取消订单，返回受影响的行数
        int result = unifiedOrderNewMapper.cancelOrder(orderNo);
        // 判断订单是否取消成功
        if (result <= 0) {
            // 如果取消失败，返回false
            return false;
        }
        
        // 4. 同步取消各模块订单（确保数据一致性）
        // 调用cancelModuleOrder方法，同步取消各模块订单
        cancelModuleOrder(order);
        
        // 返回true表示取消成功
        return true;
    }
    
    /**
     * 取消各模块订单
     * 功能概述：通过订单类型和模块订单ID定位并取消对应的模块订单，确保数据一致性
     * @param order 统一订单对象（包含 orderType 和 moduleOrderId）
     */
    // 私有方法，取消各模块订单
    private void cancelModuleOrder(UnifiedOrderNew order) {
        // 判断模块订单ID是否为空
        if (order.getModuleOrderId() == null) {
            // 如果模块订单ID为空，直接返回，不处理
            return;
        }
        
        // 获取模块订单ID
        Integer moduleOrderId = order.getModuleOrderId();
        // 获取订单类型
        String orderType = order.getOrderType();
        
        // 使用try-catch捕获异常
        try {
            // 根据订单类型取消对应的模块订单
            switch (orderType) {
                // 酒店订单类型
                case UnifiedOrderNew.TYPE_HOTEL:
                    // 取消酒店订单：moduleOrderId 是酒店订单ID
                    // 调用酒店订单Mapper的updateStatusByOrderNo方法，更新订单状态为已取消（2）
                    hotelOrderMapper.updateStatusByOrderNo(moduleOrderId, 2);
                    // 跳出switch语句
                    break;
                    
                // 购物订单类型
                case UnifiedOrderNew.TYPE_SHOPPING:
                    // 取消购物订单：moduleOrderId 是购物订单ID
                    // 调用购物订单Mapper的updateStatusById方法，更新订单状态为已取消（2）
                    shopOrderMapper.updateStatusById(moduleOrderId, 2);
                    // 跳出switch语句
                    break;
                    
                // 旅游订单类型
                case UnifiedOrderNew.TYPE_TRAVEL:
                    // 取消旅游订单：moduleOrderId 是旅游订单ID
                    // 调用旅游订单Mapper的updateStatusById方法，更新订单状态为已取消（2）
                    travelOrderMapper.updateStatusById(moduleOrderId, 2);
                    // 跳出switch语句
                    break;
                    
                // 缴费订单类型
                case UnifiedOrderNew.TYPE_PAYMENT:
                    // 缴费账单一般不支持取消，如果需要可以在这里处理
                    // 跳出switch语句
                    break;
                    
                // 美食订单类型
                case UnifiedOrderNew.TYPE_FOOD:
                    // 取消美食订单：moduleOrderId 是美食订单ID
                    // RestaurantOrder 使用 Integer orderStatus，2=已取消
                    // 调用餐厅Mapper的updateOrderStatus方法，更新订单状态为已取消（2）
                    restaurantMapper.updateOrderStatus(moduleOrderId.longValue(), 2);
                    // 跳出switch语句
                    break;
                    
                // 默认情况
                default:
                    // 未知订单类型，不处理
                    // 跳出switch语句
                    break;
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 打印错误信息到控制台
            System.err.println("取消模块订单失败: orderType=" + orderType + ", moduleOrderId=" + moduleOrderId);
            // 打印异常堆栈信息
            e.printStackTrace();
        }
    }

    /**
     * 删除订单
     * 功能概述：根据订单号和用户编号从数据库中物理删除统一订单记录，检查订单是否存在、是否属于该用户、是否可以删除（使用事务管理）
     * @param {String} orderNo - 统一订单号
     * @param {Integer} userId - 用户编号
     * @return {boolean} 返回是否删除成功（true-成功，false-失败）
     */
    // 重写接口中的deleteOrder方法
    @Override
    // 使用Spring的事务注解，确保方法在事务中执行，如果发生异常则回滚
    @Transactional
    // 删除订单方法，接收订单号和用户编号参数，返回是否删除成功
    public boolean deleteOrder(String orderNo, Integer userId) {
        // 检查订单是否存在且属于该用户
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrderNew order = unifiedOrderNewMapper.findByOrderNo(orderNo);
        // 如果订单不存在或不属于该用户，返回false
        if (order == null || !order.getUserId().equals(userId)) {
            return false;
        }
        
        // 检查是否可以删除
        // 调用订单对象的canDelete方法，判断订单是否可以删除
        if (!order.canDelete()) {
            // 如果订单不可删除，返回false
            return false;
        }
        
        // 删除订单
        // 调用统一订单新Mapper的deleteOrder方法，根据订单号和用户编号物理删除订单记录，返回删除的记录数
        int result = unifiedOrderNewMapper.deleteOrder(orderNo, userId);
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
        // 调用统一订单新Mapper的getOrderStats方法，根据用户编号统计该用户的订单信息并返回
        return unifiedOrderNewMapper.getOrderStats(userId);
    }

    /**
     * 检查订单是否可以支付
     * 功能概述：调用Mapper层方法，根据订单号查询订单信息，判断订单是否可以支付
     * @param {String} orderNo - 统一订单号
     * @return {boolean} 返回是否可以支付（true-可以支付，false-不可以支付）
     */
    // 重写接口中的canPay方法
    @Override
    // 检查订单是否可以支付方法，接收订单号参数，返回是否可以支付
    public boolean canPay(String orderNo) {
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrderNew order = unifiedOrderNewMapper.findByOrderNo(orderNo);
        // 如果订单不为空且可以支付，返回true，否则返回false
        return order != null && order.canPay();
    }

    /**
     * 检查订单是否可以取消
     * 功能概述：调用Mapper层方法，根据订单号查询订单信息，判断订单是否存在、是否属于该用户、是否可以取消
     * @param {String} orderNo - 统一订单号
     * @param {Integer} userId - 用户编号
     * @return {boolean} 返回是否可以取消（true-可以取消，false-不可以取消）
     */
    // 重写接口中的canCancel方法
    @Override
    // 检查订单是否可以取消方法，接收订单号和用户编号参数，返回是否可以取消
    public boolean canCancel(String orderNo, Integer userId) {
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrderNew order = unifiedOrderNewMapper.findByOrderNo(orderNo);
        // 如果订单不为空且属于该用户且可以取消，返回true，否则返回false
        return order != null && order.getUserId().equals(userId) && order.canCancel();
    }

    /**
     * 检查订单是否可以删除
     * 功能概述：调用Mapper层方法，根据订单号查询订单信息，判断订单是否存在、是否属于该用户、是否可以删除
     * @param {String} orderNo - 统一订单号
     * @param {Integer} userId - 用户编号
     * @return {boolean} 返回是否可以删除（true-可以删除，false-不可以删除）
     */
    // 重写接口中的canDelete方法
    @Override
    // 检查订单是否可以删除方法，接收订单号和用户编号参数，返回是否可以删除
    public boolean canDelete(String orderNo, Integer userId) {
        // 调用统一订单新Mapper的findByOrderNo方法，根据订单号查询订单信息
        UnifiedOrderNew order = unifiedOrderNewMapper.findByOrderNo(orderNo);
        // 如果订单不为空且属于该用户且可以删除，返回true，否则返回false
        return order != null && order.getUserId().equals(userId) && order.canDelete();
    }

    /**
     * 生成订单号
     * 功能概述：根据订单类型生成唯一的统一订单号，格式为：UO + 类型前缀 + 时间戳 + 随机数
     */
    // 实现生成订单号方法
    @Override
    // 生成订单号方法，接收订单类型参数
    public String generateOrderNo(String orderType) {
        // 生成格式: UO + 类型前缀 + 时间戳 + 随机数
        // 调用getTypePrefix方法获取订单类型前缀
        String prefix = getTypePrefix(orderType);
        // 获取当前时间戳（毫秒）
        long timestamp = System.currentTimeMillis();
        // 生成0-999之间的随机数
        int random = (int) (Math.random() * 1000);
        // 格式化订单号：UO + 类型前缀 + 13位时间戳 + 3位随机数
        return String.format("UO%s%013d%03d", prefix, timestamp, random);
    }

    /**
     * 获取订单类型前缀
     * 功能概述：根据订单类型返回对应的前缀字符串，用于生成订单号
     */
    // 私有方法，获取订单类型前缀
    private String getTypePrefix(String orderType) {
        // 根据订单类型返回对应的前缀
        switch (orderType) {
            // 美食订单类型，返回"FOOD"
            case UnifiedOrderNew.TYPE_FOOD: return "FOOD";
            // 酒店订单类型，返回"HOTEL"
            case UnifiedOrderNew.TYPE_HOTEL: return "HOTEL";
            // 购物订单类型，返回"SHOP"
            case UnifiedOrderNew.TYPE_SHOPPING: return "SHOP";
            // 旅游订单类型，返回"TRAVEL"
            case UnifiedOrderNew.TYPE_TRAVEL: return "TRAVEL";
            // 缴费订单类型，返回"PAY"
            case UnifiedOrderNew.TYPE_PAYMENT: return "PAY";
            // 默认情况，返回"UNKNOWN"
            default: return "UNKNOWN";
        }
    }

    /**
     * 根据模块订单ID和订单类型查找未支付订单
     * 功能概述：调用Mapper层方法，根据模块订单编号和订单类型查询未支付的统一订单信息
     * @param {Integer} moduleOrderId - 模块订单编号
     * @param {String} orderType - 订单类型
     * @return {UnifiedOrderNew} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的findUnpaidOrderByModuleOrderId方法
    @Override
    // 根据模块订单ID和订单类型查找未支付订单方法，接收模块订单编号和订单类型参数，返回订单对象
    public UnifiedOrderNew findUnpaidOrderByModuleOrderId(Integer moduleOrderId, String orderType) {
        // 如果模块订单编号或订单类型为空，返回null
        if (moduleOrderId == null || orderType == null) {
            return null;
        }
        // 调用统一订单新Mapper的findUnpaidOrderByModuleOrderId方法，根据模块订单编号和订单类型查询未支付的统一订单信息并返回
        return unifiedOrderNewMapper.findUnpaidOrderByModuleOrderId(moduleOrderId, orderType);
    }
    
    /**
     * 根据用户ID和订单类型查找未支付订单
     * 功能概述：调用Mapper层方法，根据用户编号和订单类型查询未支付的统一订单信息
     * @param {Integer} userId - 用户编号
     * @param {String} orderType - 订单类型
     * @return {UnifiedOrderNew} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的findUnpaidOrderByUserIdAndType方法
    @Override
    // 根据用户ID和订单类型查找未支付订单方法，接收用户编号和订单类型参数，返回订单对象
    public UnifiedOrderNew findUnpaidOrderByUserIdAndType(Integer userId, String orderType) {
        // 如果用户编号或订单类型为空，返回null
        if (userId == null || orderType == null) {
            return null;
        }
        // 调用统一订单新Mapper的findUnpaidOrderByUserIdAndType方法，根据用户编号和订单类型查询未支付的统一订单信息并返回
        return unifiedOrderNewMapper.findUnpaidOrderByUserIdAndType(userId, orderType);
    }
    
    /**
     * 更新订单总金额
     * 功能概述：调用Mapper层方法，根据订单号更新统一订单的总金额
     * @param {String} orderNo - 统一订单号
     * @param {BigDecimal} totalAmount - 订单总金额
     * @return {boolean} 返回是否更新成功（true-成功，false-失败）
     */
    // 重写接口中的updateTotalAmount方法
    @Override
    // 更新订单总金额方法，接收订单号和总金额参数，返回是否更新成功
    public boolean updateTotalAmount(String orderNo, BigDecimal totalAmount) {
        // 调用统一订单新Mapper的updateTotalAmount方法，根据订单号和总金额更新订单总金额，返回更新的记录数
        int result = unifiedOrderNewMapper.updateTotalAmount(orderNo, totalAmount);
        // 如果更新记录数大于0，返回true表示更新成功，否则返回false
        return result > 0;
    }
    
    /**
     * 更新模块订单ID
     * 功能概述：调用Mapper层方法，根据订单号更新统一订单的模块订单编号
     * @param {String} orderNo - 统一订单号
     * @param {Integer} moduleOrderId - 模块订单编号
     * @return {boolean} 返回是否更新成功（true-成功，false-失败）
     */
    // 重写接口中的updateModuleOrderId方法
    @Override
    // 更新模块订单ID方法，接收订单号和模块订单编号参数，返回是否更新成功
    public boolean updateModuleOrderId(String orderNo, Integer moduleOrderId) {
        // 调用统一订单新Mapper的updateModuleOrderId方法，根据订单号和模块订单编号更新订单的模块订单编号，返回更新的记录数
        int result = unifiedOrderNewMapper.updateModuleOrderId(orderNo, moduleOrderId);
        // 如果更新记录数大于0，返回true表示更新成功，否则返回false
        return result > 0;
    }
}
