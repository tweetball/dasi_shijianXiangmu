// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入MyBatis的所有注解
import org.apache.ibatis.annotations.*;

// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 重新设计的统一订单Mapper接口
 * 功能概述：定义统一订单相关的数据库操作方法，包括订单插入、查询、更新、删除，以及订单统计等
 */
// 标识该接口为MyBatis Mapper接口，会被MyBatis扫描并注册
@Mapper
// 统一订单新Mapper接口，定义统一订单相关的数据库操作方法
public interface UnifiedOrderNewMapper {

    /**
     * 创建统一订单
     * 功能概述：插入新的统一订单到数据库，包括订单号、用户ID、订单类型、模块订单ID、订单标题、订单描述、总金额、支付状态等
     * @param order 统一订单对象
     * @return 受影响的行数
     */
    // 创建统一订单方法，接收统一订单对象参数
    // 配置自动生成主键，将生成的主键值设置到order对象的id属性中
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入统一订单到数据库
    @Insert("INSERT INTO unified_order (order_no, user_id, order_type, module_order_id, " +
            "order_title, order_description, total_amount, payment_status) " +
            "VALUES (#{orderNo}, #{userId}, #{orderType}, #{moduleOrderId}, " +
            "#{orderTitle}, #{orderDescription}, #{totalAmount}, #{paymentStatus})")
    // 插入订单数据，返回受影响的行数
    int insertOrder(UnifiedOrderNew order);

    /**
     * 根据订单号查询订单
     * 功能概述：根据统一订单号查询订单详细信息
     * @param orderNo 统一订单号
     * @return 统一订单对象，不存在返回null
     */
    // 根据订单号查询订单方法，接收订单号参数
    @Select("SELECT * FROM unified_order WHERE order_no = #{orderNo}")
    // 根据订单号查询订单，返回统一订单对象
    UnifiedOrderNew findByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据用户ID获取订单列表
     * 功能概述：根据用户ID查询订单列表，支持按订单类型和支付状态筛选，使用动态SQL实现
     * @param userId 用户ID
     * @param orderType 订单类型（可选，null表示查询所有类型）
     * @param paymentStatus 支付状态（可选，null表示查询所有状态）
     * @return 统一订单列表
     */
    // 根据用户ID获取订单列表方法，接收用户ID、订单类型和支付状态参数（均为可选）
    // 使用动态SQL，根据参数是否为空动态拼接查询条件
    @Select("<script>" +
            "SELECT * FROM unified_order WHERE user_id = #{userId} " +
            "<if test='orderType != null and orderType != \"\"'>" +
            "AND order_type = #{orderType} " +
            "</if>" +
            "<if test='paymentStatus != null'>" +
            "AND payment_status = #{paymentStatus} " +
            "</if>" +
            "ORDER BY create_time DESC" +
            "</script>")
    // 根据用户ID查询订单列表，返回统一订单列表
    List<UnifiedOrderNew> findByUserId(@Param("userId") Integer userId,
                                      @Param("orderType") String orderType,
                                      @Param("paymentStatus") Integer paymentStatus);

    /**
     * 更新订单支付状态
     * 功能概述：更新统一订单的支付状态、支付方式和支付时间，用于支付成功后更新订单信息
     * @param orderNo 统一订单号
     * @param paymentStatus 支付状态（0=待支付，1=已支付，2=已取消，3=已完成）
     * @param paymentMethod 支付方式（微信、支付宝、银行卡等）
     * @param paymentTime 支付时间
     * @return 受影响的行数
     */
    // 更新订单支付状态方法，接收订单号、支付状态、支付方式和支付时间参数
    @Update("UPDATE unified_order SET payment_status = #{paymentStatus}, " +
            "payment_method = #{paymentMethod}, payment_time = #{paymentTime}, " +
            "update_time = NOW() WHERE order_no = #{orderNo}")
    // 更新订单支付状态，返回受影响的行数
    int updatePaymentStatus(@Param("orderNo") String orderNo,
                           @Param("paymentStatus") Integer paymentStatus,
                           @Param("paymentMethod") String paymentMethod,
                           @Param("paymentTime") LocalDateTime paymentTime);

    /**
     * 取消订单
     * 功能概述：取消统一订单，更新订单状态为已取消（2），只能取消待支付（0）状态的订单
     * @param orderNo 统一订单号
     * @return 受影响的行数
     */
    // 取消订单方法，接收订单号参数
    @Update("UPDATE unified_order SET payment_status = 2, update_time = NOW() " +
            "WHERE order_no = #{orderNo} AND payment_status = 0")
    // 取消订单，返回受影响的行数
    int cancelOrder(@Param("orderNo") String orderNo);

    /**
     * 删除订单
     * 功能概述：删除统一订单，只能删除属于该用户的订单
     * @param orderNo 统一订单号
     * @param userId 用户ID（用于验证订单归属）
     * @return 受影响的行数
     */
    // 删除订单方法，接收订单号和用户ID参数
    @Delete("DELETE FROM unified_order WHERE order_no = #{orderNo} AND user_id = #{userId}")
    // 删除订单，返回受影响的行数
    int deleteOrder(@Param("orderNo") String orderNo, @Param("userId") Integer userId);

    /**
     * 获取订单统计
     * 功能概述：获取用户的订单统计数据，包括订单总数、待支付数量、已支付数量、已取消数量、已完成数量
     * @param userId 用户ID
     * @return 订单统计数据Map（包含total、unpaid、paid、cancelled、completed等字段）
     */
    // 获取订单统计方法，接收用户ID参数
    @Select("SELECT COUNT(*) as total, " +
            "SUM(CASE WHEN payment_status = 0 THEN 1 ELSE 0 END) as unpaid, " +
            "SUM(CASE WHEN payment_status = 1 THEN 1 ELSE 0 END) as paid, " +
            "SUM(CASE WHEN payment_status = 2 THEN 1 ELSE 0 END) as cancelled, " +
            "SUM(CASE WHEN payment_status = 3 THEN 1 ELSE 0 END) as completed " +
            "FROM unified_order WHERE user_id = #{userId}")
    // 获取订单统计，返回订单统计数据Map
    Map<String, Object> getOrderStats(@Param("userId") Integer userId);

    /**
     * 检查订单是否存在
     * 功能概述：根据订单号检查订单是否存在，用于订单号唯一性验证
     * @param orderNo 统一订单号
     * @return 订单数量（大于0表示存在）
     */
    // 检查订单是否存在方法，接收订单号参数
    @Select("SELECT COUNT(*) FROM unified_order WHERE order_no = #{orderNo}")
    // 检查订单是否存在，返回订单数量
    int existsByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据用户ID和订单类型获取订单数量
     * 功能概述：根据用户ID和订单类型统计该用户的订单数量
     * @param userId 用户ID
     * @param orderType 订单类型（FOOD、HOTEL、SHOPPING、TRAVEL、PAYMENT）
     * @return 订单数量
     */
    // 根据用户ID和订单类型获取订单数量方法，接收用户ID和订单类型参数
    @Select("SELECT COUNT(*) FROM unified_order WHERE user_id = #{userId} AND order_type = #{orderType}")
    // 获取订单数量，返回订单数量
    int countByUserIdAndType(@Param("userId") Integer userId, @Param("orderType") String orderType);

    /**
     * 根据模块订单ID和订单类型查找待支付订单
     * 功能概述：根据模块订单ID和订单类型查找待支付订单，用于避免重复创建订单
     * @param moduleOrderId 模块订单ID（对应各业务模块的订单ID）
     * @param orderType 订单类型（FOOD、HOTEL、SHOPPING、TRAVEL、PAYMENT）
     * @return 统一订单对象，不存在返回null
     */
    // 根据模块订单ID和订单类型查找待支付订单方法，接收模块订单ID和订单类型参数
    @Select("SELECT * FROM unified_order WHERE module_order_id = #{moduleOrderId} " +
            "AND order_type = #{orderType} AND payment_status = 0 " +
            "ORDER BY create_time DESC LIMIT 1")
    // 查找待支付订单，返回统一订单对象
    UnifiedOrderNew findUnpaidOrderByModuleOrderId(@Param("moduleOrderId") Integer moduleOrderId,
                                                    @Param("orderType") String orderType);
    
    /**
     * 根据用户ID和订单类型查找待支付订单（用于购物车多次结算时复用订单）
     * 功能概述：根据用户ID和订单类型查找待支付订单，用于购物车多次结算时复用订单
     * @param userId 用户ID
     * @param orderType 订单类型（FOOD、HOTEL、SHOPPING、TRAVEL、PAYMENT）
     * @return 统一订单对象，不存在返回null
     */
    // 根据用户ID和订单类型查找待支付订单方法，接收用户ID和订单类型参数
    @Select("SELECT * FROM unified_order WHERE user_id = #{userId} " +
            "AND order_type = #{orderType} AND payment_status = 0 " +
            "ORDER BY create_time DESC LIMIT 1")
    // 查找待支付订单，返回统一订单对象
    UnifiedOrderNew findUnpaidOrderByUserIdAndType(@Param("userId") Integer userId,
                                                    @Param("orderType") String orderType);
    
    /**
     * 更新订单总金额
     * 功能概述：更新统一订单的总金额，用于购物车添加商品时更新金额
     * @param orderNo 统一订单号
     * @param totalAmount 新的总金额
     * @return 受影响的行数
     */
    // 更新订单总金额方法，接收订单号和总金额参数
    @Update("UPDATE unified_order SET total_amount = #{totalAmount}, update_time = NOW() " +
            "WHERE order_no = #{orderNo}")
    // 更新订单总金额，返回受影响的行数
    int updateTotalAmount(@Param("orderNo") String orderNo, @Param("totalAmount") java.math.BigDecimal totalAmount);
    
    /**
     * 更新模块订单ID（当订单已存在但关联了新的模块订单时）
     * 功能概述：更新统一订单关联的模块订单ID，用于购物车添加商品时更新关联
     * @param orderNo 统一订单号
     * @param moduleOrderId 新的模块订单ID
     * @return 受影响的行数
     */
    // 更新模块订单ID方法，接收订单号和模块订单ID参数
    @Update("UPDATE unified_order SET module_order_id = #{moduleOrderId}, update_time = NOW() " +
            "WHERE order_no = #{orderNo}")
    // 更新模块订单ID，返回受影响的行数
    int updateModuleOrderId(@Param("orderNo") String orderNo, @Param("moduleOrderId") Integer moduleOrderId);
}
