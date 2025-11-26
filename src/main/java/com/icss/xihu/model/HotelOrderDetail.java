// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDate类，用于处理日期
import java.time.LocalDate;
// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;

/**
 * 酒店订单详情实体类
 * 功能概述：表示系统中的酒店订单详情信息，包括酒店信息、房型信息、入住退房日期、价格等
 */
// 酒店订单详情实体类，对应数据库中的hotel_order_detail表
public class HotelOrderDetail {
    // 订单详情ID，主键，自增
    private Integer id;
    // 订单号，关联hotel_order表的order_no字段
    private String orderNo;
    // 酒店ID，关联hotel表的id字段
    private Integer hotelId;
    // 酒店名称，用于显示
    private String hotelName;
    // 房型名称，用于显示
    private String roomType;
    // 入住日期，格式：yyyy-MM-dd
    private LocalDate checkInDate;
    // 退房日期，格式：yyyy-MM-dd
    private LocalDate checkOutDate;
    // 入住天数，根据入住和退房日期计算
    private Integer nights;
    // 入住人数，记录订单的入住人数
    private Integer guests;
    // 房型单价，使用BigDecimal确保精度
    private BigDecimal roomPrice;
    // 小计，房型单价 * 入住天数，使用BigDecimal确保精度
    private BigDecimal subtotal;
    // 创建时间，记录订单详情创建的时间
    private LocalDateTime createTime;

    /**
     * 无参构造方法
     * 功能概述：创建空的酒店订单详情对象，用于MyBatis等框架的反序列化
     */
    // 构造函数，创建空的酒店订单详情对象
    public HotelOrderDetail() {
    }

    // Getters and Setters
    /**
     * 获取订单详情ID
     * @return 订单详情ID
     */
    // 获取订单详情ID方法，返回订单详情ID
    public Integer getId() {
        // 返回订单详情ID
        return id;
    }

    /**
     * 设置订单详情ID
     * @param id 订单详情ID
     */
    // 设置订单详情ID方法，接收订单详情ID参数
    public void setId(Integer id) {
        // 设置订单详情ID
        this.id = id;
    }

    /**
     * 获取订单号
     * @return 订单号
     */
    // 获取订单号方法，返回订单号
    public String getOrderNo() {
        // 返回订单号
        return orderNo;
    }

    /**
     * 设置订单号
     * @param orderNo 订单号
     */
    // 设置订单号方法，接收订单号参数
    public void setOrderNo(String orderNo) {
        // 设置订单号
        this.orderNo = orderNo;
    }

    /**
     * 获取酒店ID
     * @return 酒店ID
     */
    // 获取酒店ID方法，返回酒店ID
    public Integer getHotelId() {
        // 返回酒店ID
        return hotelId;
    }

    /**
     * 设置酒店ID
     * @param hotelId 酒店ID
     */
    // 设置酒店ID方法，接收酒店ID参数
    public void setHotelId(Integer hotelId) {
        // 设置酒店ID
        this.hotelId = hotelId;
    }

    /**
     * 获取酒店名称
     * @return 酒店名称
     */
    // 获取酒店名称方法，返回酒店名称
    public String getHotelName() {
        // 返回酒店名称
        return hotelName;
    }

    /**
     * 设置酒店名称
     * @param hotelName 酒店名称
     */
    // 设置酒店名称方法，接收酒店名称参数
    public void setHotelName(String hotelName) {
        // 设置酒店名称
        this.hotelName = hotelName;
    }

    /**
     * 获取房型名称
     * @return 房型名称
     */
    // 获取房型名称方法，返回房型名称
    public String getRoomType() {
        // 返回房型名称
        return roomType;
    }

    /**
     * 设置房型名称
     * @param roomType 房型名称
     */
    // 设置房型名称方法，接收房型名称参数
    public void setRoomType(String roomType) {
        // 设置房型名称
        this.roomType = roomType;
    }

    /**
     * 获取入住日期
     * @return 入住日期
     */
    // 获取入住日期方法，返回入住日期
    public LocalDate getCheckInDate() {
        // 返回入住日期
        return checkInDate;
    }

    /**
     * 设置入住日期
     * @param checkInDate 入住日期
     */
    // 设置入住日期方法，接收入住日期参数
    public void setCheckInDate(LocalDate checkInDate) {
        // 设置入住日期
        this.checkInDate = checkInDate;
    }

    /**
     * 获取退房日期
     * @return 退房日期
     */
    // 获取退房日期方法，返回退房日期
    public LocalDate getCheckOutDate() {
        // 返回退房日期
        return checkOutDate;
    }

    /**
     * 设置退房日期
     * @param checkOutDate 退房日期
     */
    // 设置退房日期方法，接收退房日期参数
    public void setCheckOutDate(LocalDate checkOutDate) {
        // 设置退房日期
        this.checkOutDate = checkOutDate;
    }

    /**
     * 获取入住天数
     * @return 入住天数
     */
    // 获取入住天数方法，返回入住天数
    public Integer getNights() {
        // 返回入住天数
        return nights;
    }

    /**
     * 设置入住天数
     * @param nights 入住天数
     */
    // 设置入住天数方法，接收入住天数参数
    public void setNights(Integer nights) {
        // 设置入住天数
        this.nights = nights;
    }

    /**
     * 获取入住人数
     * @return 入住人数
     */
    // 获取入住人数方法，返回入住人数
    public Integer getGuests() {
        // 返回入住人数
        return guests;
    }

    /**
     * 设置入住人数
     * @param guests 入住人数
     */
    // 设置入住人数方法，接收入住人数参数
    public void setGuests(Integer guests) {
        // 设置入住人数
        this.guests = guests;
    }

    /**
     * 获取房型单价
     * @return 房型单价
     */
    // 获取房型单价方法，返回房型单价
    public BigDecimal getRoomPrice() {
        // 返回房型单价
        return roomPrice;
    }

    /**
     * 设置房型单价
     * @param roomPrice 房型单价
     */
    // 设置房型单价方法，接收房型单价参数
    public void setRoomPrice(BigDecimal roomPrice) {
        // 设置房型单价
        this.roomPrice = roomPrice;
    }

    /**
     * 获取小计
     * @return 小计（房型单价 * 入住天数）
     */
    // 获取小计方法，返回小计
    public BigDecimal getSubtotal() {
        // 返回小计
        return subtotal;
    }

    /**
     * 设置小计
     * @param subtotal 小计（房型单价 * 入住天数）
     */
    // 设置小计方法，接收小计参数
    public void setSubtotal(BigDecimal subtotal) {
        // 设置小计
        this.subtotal = subtotal;
    }

    /**
     * 获取创建时间
     * @return 创建时间
     */
    // 获取创建时间方法，返回创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 设置创建时间
        this.createTime = createTime;
    }
}

