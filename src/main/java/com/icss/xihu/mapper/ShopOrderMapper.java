// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入购物订单实体类
import com.icss.xihu.model.ShopOrder;
// 导入购物订单详情实体类
import com.icss.xihu.model.ShopOrderDetail;
// 导入MyBatis的所有注解
import org.apache.ibatis.annotations.*;

// 导入List集合接口
import java.util.List;

/**
 * 购物订单Mapper接口
 * 功能概述：定义购物订单相关的数据库操作方法，包括订单插入、查询、更新，以及订单详情的增删改查
 */
// 标识该接口为MyBatis Mapper接口，会被MyBatis扫描并注册
@Mapper
// 购物订单Mapper接口，定义购物订单相关的数据库操作方法
public interface ShopOrderMapper {

    /**
     * 创建购物订单
     * 功能概述：插入新的购物订单到数据库，包括订单号、用户ID、总金额、收货地址、收货人信息等
     * @param order 购物订单对象
     * @return 受影响的行数
     */
    // 创建购物订单方法，接收购物订单对象参数
    // 配置自动生成主键，将生成的主键值设置到order对象的id属性中
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入购物订单到数据库
    @Insert("INSERT INTO shop_order (order_no, user_id, total_amount, shipping_address, " +
            "shipping_phone, shipping_name, order_status) " +
            "VALUES (#{orderNo}, #{userId}, #{totalAmount}, #{shippingAddress}, " +
            "#{shippingPhone}, #{shippingName}, #{orderStatus})")
    // 插入订单数据，返回受影响的行数
    int insertOrder(ShopOrder order);

    /**
     * 创建订单详情
     * 功能概述：插入购物订单详情到数据库，记录订单的商品信息（商品ID、名称、价格、数量等）
     * @param detail 购物订单详情对象
     * @return 受影响的行数
     */
    // 创建订单详情方法，接收购物订单详情对象参数
    @Insert("INSERT INTO shop_order_detail (order_no, product_id, product_name, " +
            "product_price, quantity, subtotal) " +
            "VALUES (#{orderNo}, #{productId}, #{productName}, #{productPrice}, " +
            "#{quantity}, #{subtotal})")
    // 插入订单详情到数据库，返回受影响的行数
    int insertOrderDetail(ShopOrderDetail detail);

    /**
     * 根据订单号查询订单
     * 功能概述：根据订单号查询购物订单详细信息
     * @param orderNo 订单号
     * @return 购物订单对象，不存在返回null
     */
    // 根据订单号查询订单方法，接收订单号参数
    @Select("SELECT * FROM shop_order WHERE order_no = #{orderNo}")
    // 根据订单号查询订单，返回购物订单对象
    ShopOrder findByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 根据订单ID查询订单
     * 功能概述：根据订单ID查询购物订单详细信息
     * @param orderId 订单ID
     * @return 购物订单对象，不存在返回null
     */
    // 根据订单ID查询订单方法，接收订单ID参数
    @Select("SELECT * FROM shop_order WHERE id = #{orderId}")
    // 根据订单ID查询订单，返回购物订单对象
    ShopOrder findById(@Param("orderId") Integer orderId);

    /**
     * 根据订单号查询订单详情
     * 功能概述：根据订单号查询购物订单的商品详情列表
     * @param orderNo 订单号
     * @return 购物订单详情列表
     */
    // 根据订单号查询订单详情方法，接收订单号参数
    @Select("SELECT * FROM shop_order_detail WHERE order_no = #{orderNo}")
    // 根据订单号查询订单详情，返回购物订单详情列表
    List<ShopOrderDetail> findDetailsByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 根据订单ID查询订单详情（兼容方法，通过订单ID查找订单号再查询详情）
     * 功能概述：根据订单ID查询购物订单的商品详情列表，通过关联查询实现（用于兼容旧代码）
     * @param orderId 订单ID
     * @return 购物订单详情列表
     */
    // 根据订单ID查询订单详情方法（兼容方法），接收订单ID参数
    @Select("SELECT sod.* FROM shop_order_detail sod " +
            "INNER JOIN shop_order so ON sod.order_no = so.order_no " +
            "WHERE so.id = #{orderId}")
    // 根据订单ID查询订单详情，返回购物订单详情列表
    List<ShopOrderDetail> findDetailsByOrderId(@Param("orderId") Integer orderId);

    /**
     * 更新订单支付状态（根据订单号）
     * 功能概述：根据订单号更新购物订单的支付状态，用于支付成功后更新订单状态
     * 注意：数据库表中没有 payment_time 字段，如果需要支付时间，可以从 unified_order 表中获取
     * @param orderNo 订单号
     * @param orderStatus 订单状态（0=待支付，1=已支付，2=已取消，3=已完成）
     * @return 受影响的行数
     */
    // 更新订单支付状态方法，接收订单号和订单状态参数
    @Update("UPDATE shop_order SET order_status = #{orderStatus}, " +
            "update_time = NOW() WHERE order_no = #{orderNo}")
    // 更新订单支付状态，返回受影响的行数
    int updatePaymentStatus(@Param("orderNo") String orderNo,
                           @Param("orderStatus") Integer orderStatus);

    /**
     * 根据订单ID更新状态
     * 功能概述：根据订单ID更新购物订单的状态（待支付、已支付、已取消、已完成等）
     * @param orderId 订单ID
     * @param status 订单状态（0=待支付，1=已支付，2=已取消，3=已完成）
     * @return 受影响的行数
     */
    // 根据订单ID更新状态方法，接收订单ID和订单状态参数
    @Update("UPDATE shop_order SET order_status = #{status}, update_time = NOW() WHERE id = #{orderId}")
    // 更新订单状态，返回受影响的行数
    int updateStatusById(@Param("orderId") Integer orderId, @Param("status") Integer status);
    
    /**
     * 更新订单总金额
     * 功能概述：更新购物订单的总金额，用于购物车添加商品时更新金额
     * @param orderId 订单ID
     * @param totalAmount 新的总金额
     * @return 受影响的行数
     */
    // 更新订单总金额方法，接收订单ID和总金额参数
    @Update("UPDATE shop_order SET total_amount = #{totalAmount}, update_time = NOW() WHERE id = #{orderId}")
    // 更新订单总金额，返回受影响的行数
    int updateTotalAmount(@Param("orderId") Integer orderId, @Param("totalAmount") java.math.BigDecimal totalAmount);
    
    /**
     * 更新统一订单号（关联统一订单）
     * 功能概述：更新购物订单关联的统一订单号，建立与统一订单的关联关系
     * @param orderId 订单ID
     * @param unifiedOrderNo 统一订单号
     * @return 受影响的行数
     */
    // 更新统一订单号方法，接收订单ID和统一订单号参数
    @Update("UPDATE shop_order SET unified_order_no = #{unifiedOrderNo} WHERE id = #{orderId}")
    // 更新统一订单号，返回受影响的行数
    int updateUnifiedOrderNo(@Param("orderId") Integer orderId, @Param("unifiedOrderNo") String unifiedOrderNo);
}

