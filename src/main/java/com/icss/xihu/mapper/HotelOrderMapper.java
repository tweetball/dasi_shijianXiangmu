// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入酒店订单实体类
import com.icss.xihu.model.HotelOrder;
// 导入酒店订单详情实体类
import com.icss.xihu.model.HotelOrderDetail;
// 导入MyBatis的所有注解
import org.apache.ibatis.annotations.*;

// 导入List集合接口
import java.util.List;

/**
 * 酒店订单Mapper接口
 * 功能概述：定义酒店订单相关的数据库操作方法，包括订单插入、查询、更新，以及订单详情的增删改查
 */
// 标识该接口为MyBatis Mapper接口，会被MyBatis扫描并注册
@Mapper
// 酒店订单Mapper接口，定义酒店订单相关的数据库操作方法
public interface HotelOrderMapper {
    /**
     * 插入订单数据
     * 功能概述：插入新的酒店订单到数据库，包括酒店ID、房间ID、用户ID、入住退房日期、联系人信息等
     * @param order 酒店订单对象
     * @return 受影响的行数
     */
    // 插入订单数据方法，接收酒店订单对象参数
    // 配置自动生成主键，将生成的主键值设置到order对象的id属性中
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入酒店订单到数据库
    @Insert("INSERT INTO hotel_order (hid, rid, uid, rname, indate, outdate, guests, name, tel, notes, status, price, order_no) " +
            "VALUES (#{hid}, #{rid}, #{uid}, #{rname}, #{indate}, #{outdate}, #{guests}, #{name}, #{tel}, #{notes}, #{status}, #{price}, #{orderNo})")
    // 插入订单数据，返回受影响的行数
    int insert(HotelOrder order);

    /**
     * 根据用户ID查询订单列表
     * 功能概述：根据用户ID查询该用户的所有酒店订单列表（使用XML映射文件实现）
     * @param userId 用户ID（字符串类型）
     * @return 酒店订单列表
     */
    // 根据用户ID查询订单列表方法，接收用户ID参数（使用XML映射文件实现）
    List<HotelOrder> selectByUserId(@Param("userId") String userId);

    /**
     * 根据订单ID查询订单
     * 功能概述：根据订单ID查询酒店订单详细信息
     * @param orderId 订单ID
     * @return 酒店订单对象，不存在返回null
     */
    // 根据订单ID查询订单方法，接收订单ID参数
    @Select("SELECT * FROM hotel_order WHERE id = #{orderId}")
    // 根据订单ID查询订单，返回酒店订单对象
    HotelOrder findById(@Param("orderId") Integer orderId);

    /**
     * 更新订单状态
     * 功能概述：更新酒店订单的状态（待支付、已支付、已取消等）（使用XML映射文件实现）
     * @param orderId 订单ID（Long类型）
     * @param status 订单状态（0=待支付，1=已支付，2=已取消）
     * @return 受影响的行数
     */
    // 更新订单状态方法，接收订单ID和订单状态参数（使用XML映射文件实现）
    int updateStatus(@Param("orderId") Long orderId, @Param("status") Integer status);

    /**
     * 根据订单ID更新状态（用于统一支付）
     * 功能概述：根据订单ID更新酒店订单状态，用于统一支付系统（使用XML映射文件实现）
     * @param orderId 订单ID（Integer类型）
     * @param status 订单状态（0=待支付，1=已支付，2=已取消）
     * @return 受影响的行数
     */
    // 根据订单ID更新状态方法，接收订单ID和订单状态参数（使用XML映射文件实现）
    int updateStatusByOrderNo(@Param("orderId") Integer orderId, @Param("status") Integer status);

    /**
     * 更新统一订单号
     * 功能概述：更新酒店订单关联的统一订单号，建立与统一订单的关联关系
     * @param orderId 订单ID
     * @param unifiedOrderNo 统一订单号
     * @return 受影响的行数
     */
    // 更新统一订单号方法，接收订单ID和统一订单号参数
    @Update("UPDATE hotel_order SET unified_order_no = #{unifiedOrderNo} WHERE id = #{orderId}")
    // 更新统一订单号，返回受影响的行数
    int updateUnifiedOrderNo(@Param("orderId") Integer orderId, @Param("unifiedOrderNo") String unifiedOrderNo);

    /**
     * 创建订单详情
     * 功能概述：插入酒店订单详情到数据库，记录订单的详细信息（酒店、房型、入住退房日期、价格等）
     * @param detail 酒店订单详情对象
     * @return 受影响的行数
     */
    // 创建订单详情方法，接收酒店订单详情对象参数
    @Insert("INSERT INTO hotel_order_detail (order_no, hotel_id, hotel_name, room_type, check_in_date, check_out_date, nights, guests, room_price, subtotal) " +
            "VALUES (#{orderNo}, #{hotelId}, #{hotelName}, #{roomType}, #{checkInDate}, #{checkOutDate}, #{nights}, #{guests}, #{roomPrice}, #{subtotal})")
    // 插入订单详情到数据库，返回受影响的行数
    int insertOrderDetail(HotelOrderDetail detail);

    /**
     * 根据订单号查询订单详情
     * 功能概述：根据订单号查询酒店订单的详细信息列表
     * @param orderNo 订单号
     * @return 酒店订单详情列表
     */
    // 根据订单号查询订单详情方法，接收订单号参数
    @Select("SELECT * FROM hotel_order_detail WHERE order_no = #{orderNo}")
    // 根据订单号查询订单详情，返回酒店订单详情列表
    List<HotelOrderDetail> findDetailsByOrderNo(@Param("orderNo") String orderNo);
} 