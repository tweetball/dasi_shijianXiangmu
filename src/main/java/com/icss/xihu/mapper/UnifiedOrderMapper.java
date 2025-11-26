/**
 * 统一订单Mapper接口
 * 功能概述：定义统一订单相关的数据库操作方法，包括订单创建、查询、状态更新、统计等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入统一订单实体类
import com.icss.xihu.model.UnifiedOrder;
// 导入MyBatis的所有注解
import org.apache.ibatis.annotations.*;

// 导入List集合接口
import java.util.List;

/**
 * 统一订单Mapper
 * 功能概述：定义统一订单相关的数据库操作方法，包括订单创建、查询、状态更新、统计等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 统一订单Mapper接口，定义统一订单相关的数据库操作方法
public interface UnifiedOrderMapper {

    /**
     * 创建统一订单
     * 功能概述：向数据库中插入一条统一订单记录
     * @param {UnifiedOrder} order - 统一订单对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入订单信息
    @Insert("INSERT INTO unified_order (order_no, user_id, order_type, module_order_id, " +
            "order_title, order_description, total_amount, payment_status) " +
            "VALUES (#{orderNo}, #{userId}, #{orderType}, #{moduleOrderId}, " +
            "#{orderTitle}, #{orderDescription}, #{totalAmount}, #{paymentStatus})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给order对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 创建统一订单方法，接收统一订单对象参数，返回插入的记录数
    int insertOrder(UnifiedOrder order);

    /**
     * 根据订单号查询订单
     * 功能概述：根据订单号从数据库中查询指定统一订单的信息
     * @param {String} orderNo - 订单号
     * @return {UnifiedOrder} 返回订单对象，如果不存在则返回null
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM unified_order WHERE order_no = #{orderNo}")
    // 根据订单号查询订单方法，接收订单号参数，返回订单对象
    UnifiedOrder findByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据用户ID获取订单列表
     * 功能概述：根据用户编号从数据库中查询该用户的所有统一订单信息，支持按订单类型和支付状态筛选，按创建时间倒序排列
     * @param {Integer} userId - 用户编号
     * @param {String} orderType - 订单类型（可选，用于筛选订单类型）
     * @param {Integer} paymentStatus - 支付状态（可选，用于筛选支付状态）
     * @return {List<UnifiedOrder>} 返回该用户的所有订单列表
     */
    // 根据用户ID获取订单列表方法，接收用户编号、订单类型和支付状态参数，返回该用户的所有订单列表
    // 使用MyBatis的Select注解和动态SQL（<script>标签），根据条件动态生成SQL语句
    @Select("<script>" +
            "SELECT * FROM unified_order WHERE user_id = #{userId} " +  // 按用户编号查询
            "<if test='orderType != null and orderType != \"\"'>" +    // 如果订单类型不为空
            "AND order_type = #{orderType} " +                          // 按订单类型筛选
            "</if>" +                                                   // 条件判断结束
            "<if test='paymentStatus != null'>" +                      // 如果支付状态不为空
            "AND payment_status = #{paymentStatus} " +                 // 按支付状态筛选
            "</if>" +                                                   // 条件判断结束
            "ORDER BY create_time DESC" +                               // 按创建时间倒序排列
            "</script>")                                                // 动态SQL结束
    // 根据用户ID获取订单列表方法，接收用户编号、订单类型和支付状态参数，返回该用户的所有订单列表
    List<UnifiedOrder> findByUserId(@Param("userId") Integer userId,
                                    @Param("orderType") String orderType,
                                    @Param("paymentStatus") Integer paymentStatus);

    /**
     * 更新订单支付状态
     * 功能概述：根据订单号更新统一订单的支付状态、支付方式和支付时间
     * @param {String} orderNo - 订单号
     * @param {Integer} paymentStatus - 支付状态（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
     * @param {String} paymentMethod - 支付方式
     * @param {LocalDateTime} paymentTime - 支付时间
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新订单支付状态、支付方式和支付时间
    @Update("UPDATE unified_order SET payment_status = #{paymentStatus}, " +
            "payment_method = #{paymentMethod}, payment_time = #{paymentTime}, " +
            "update_time = NOW() WHERE order_no = #{orderNo}")
    // 更新订单支付状态方法，接收订单号、支付状态、支付方式和支付时间参数，返回更新的记录数
    int updatePaymentStatus(@Param("orderNo") String orderNo,
                           @Param("paymentStatus") Integer paymentStatus,
                           @Param("paymentMethod") String paymentMethod,
                           @Param("paymentTime") java.time.LocalDateTime paymentTime);

    /**
     * 取消订单
     * 功能概述：根据订单号取消统一订单（仅当订单状态为待支付时才能取消）
     * @param {String} orderNo - 订单号
     * @return {int} 返回更新的记录数（通常为1，如果订单状态不是待支付则返回0）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新订单状态为已取消（仅当订单状态为待支付时）
    @Update("UPDATE unified_order SET payment_status = 2, update_time = NOW() " +
            "WHERE order_no = #{orderNo} AND payment_status = 0")
    // 取消订单方法，接收订单号参数，返回更新的记录数
    int cancelOrder(@Param("orderNo") String orderNo);

    /**
     * 删除订单(物理删除)
     * 功能概述：根据订单号和用户编号从数据库中物理删除统一订单记录
     * @param {String} orderNo - 订单号
     * @param {Integer} userId - 用户编号
     * @return {int} 返回删除的记录数（通常为1）
     */
    // 使用MyBatis的Delete注解，直接在方法上编写SQL删除语句，物理删除订单记录
    @Delete("DELETE FROM unified_order WHERE order_no = #{orderNo} AND user_id = #{userId}")
    // 删除订单方法，接收订单号和用户编号参数，返回删除的记录数
    int deleteOrder(@Param("orderNo") String orderNo, @Param("userId") Integer userId);

    /**
     * 获取订单统计
     * 功能概述：根据用户编号从数据库中统计该用户的订单信息（总订单数、待支付数、已支付数、已取消数）
     * @param {Integer} userId - 用户编号
     * @return {Map<String, Object>} 返回订单统计结果（包含总订单数、待支付数、已支付数、已取消数）
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用COUNT和SUM函数统计订单信息
    @Select("SELECT COUNT(*) as total, " +
            "SUM(CASE WHEN payment_status = 0 THEN 1 ELSE 0 END) as unpaid, " +
            "SUM(CASE WHEN payment_status = 1 THEN 1 ELSE 0 END) as paid, " +
            "SUM(CASE WHEN payment_status = 2 THEN 1 ELSE 0 END) as cancelled " +
            "FROM unified_order WHERE user_id = #{userId}")
    // 获取订单统计方法，接收用户编号参数，返回订单统计结果
    java.util.Map<String, Object> getOrderStats(@Param("userId") Integer userId);
}
