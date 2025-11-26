/**
 * 旅游订单Mapper接口
 * 功能概述：定义旅游订单相关的数据库操作方法，包括订单创建、查询、状态更新等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入旅游订单实体类
import com.icss.xihu.model.TravelOrder;
// 导入MyBatis的所有注解
import org.apache.ibatis.annotations.*;

// 导入List集合接口
import java.util.List;

/**
 * 旅游订单Mapper
 * 功能概述：定义旅游订单相关的数据库操作方法，包括订单创建、查询、状态更新等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 旅游订单Mapper接口，定义旅游订单相关的数据库操作方法
public interface TravelOrderMapper {

    /**
     * 创建旅游订单
     * 功能概述：向数据库中插入一条旅游订单记录
     * @param {TravelOrder} order - 旅游订单对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入订单信息
    @Insert("INSERT INTO travel_order (order_no, user_id, attraction_id, attraction_name, " +
            "ticket_type, ticket_price, ticket_count, total_amount, visit_date, " +
            "contact_name, contact_phone, order_status) " +
            "VALUES (#{orderNo}, #{userId}, #{attractionId}, #{attractionName}, " +
            "#{ticketType}, #{ticketPrice}, #{ticketCount}, #{totalAmount}, #{visitDate}, " +
            "#{contactName}, #{contactPhone}, #{orderStatus})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给order对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 创建旅游订单方法，接收旅游订单对象参数，返回插入的记录数
    int insertOrder(TravelOrder order);

    /**
     * 根据订单号查询订单
     * 功能概述：根据订单号从数据库中查询指定旅游订单的信息
     * @param {String} orderNo - 订单号
     * @return {TravelOrder} 返回订单对象，如果不存在则返回null
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM travel_order WHERE order_no = #{orderNo}")
    // 根据订单号查询订单方法，接收订单号参数，返回订单对象
    TravelOrder findByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 更新订单支付状态
     * 功能概述：根据订单号更新旅游订单的支付状态和支付时间
     * @param {String} orderNo - 订单号
     * @param {Integer} orderStatus - 订单状态
     * @param {LocalDateTime} paymentTime - 支付时间
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新订单支付状态和支付时间
    @Update("UPDATE travel_order SET order_status = #{orderStatus}, " +
            "payment_time = #{paymentTime}, update_time = NOW() WHERE order_no = #{orderNo}")
    // 更新订单支付状态方法，接收订单号、订单状态和支付时间参数，返回更新的记录数
    int updatePaymentStatus(@Param("orderNo") String orderNo,
                           @Param("orderStatus") Integer orderStatus,
                           @Param("paymentTime") java.time.LocalDateTime paymentTime);

    /**
     * 根据用户ID查询订单列表
     * 功能概述：根据用户编号从数据库中查询该用户的所有旅游订单信息，按创建时间倒序排列
     * @param {Integer} userId - 用户编号
     * @return {List<TravelOrder>} 返回该用户的所有订单列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按用户编号查询，按创建时间倒序排列
    @Select("SELECT * FROM travel_order WHERE user_id = #{userId} ORDER BY create_time DESC")
    // 根据用户ID查询订单列表方法，接收用户编号参数，返回该用户的所有订单列表
    List<TravelOrder> findByUserId(@Param("userId") Integer userId);

    /**
     * 根据订单ID更新状态
     * 功能概述：根据订单编号更新旅游订单的状态
     * @param {Integer} orderId - 订单编号
     * @param {Integer} status - 订单状态
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新订单状态和更新时间
    @Update("UPDATE travel_order SET order_status = #{status}, update_time = NOW() WHERE id = #{orderId}")
    // 根据订单ID更新状态方法，接收订单编号和订单状态参数，返回更新的记录数
    int updateStatusById(@Param("orderId") Integer orderId, @Param("status") Integer status);
}
