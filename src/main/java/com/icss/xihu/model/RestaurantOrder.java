/**
 * 餐厅订单实体类
 * 功能概述：封装餐厅订单信息，对应数据库中的restaurant_order表，包含订单的基本信息、预订信息、金额等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDate类，用于表示日期
import java.time.LocalDate;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 餐厅订单实体类
 * 功能概述：封装餐厅订单信息，对应数据库中的restaurant_order表，包含订单的基本信息、预订信息、金额等
 */
// 餐厅订单实体类，封装餐厅订单信息
public class RestaurantOrder {
    // 订单编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 订单号，对应数据库中的order_no字段，订单的唯一标识号
    private String orderNo;
    // 用户编号，对应数据库中的user_id字段，关联用户表的id字段（外键）
    private Integer userId;
    // 餐厅编号，对应数据库中的restaurant_id字段，关联餐厅表的id字段（外键）
    private Long restaurantId;
    // 餐厅名称，对应数据库中的restaurant_name字段，餐厅的名称
    private String restaurantName;
    // 预订日期，对应数据库中的booking_date字段，预订的日期
    private LocalDate bookingDate;
    // 预订时间，对应数据库中的booking_time字段，预订的时间（如"12:00"）
    private String bookingTime;
    // 用餐人数，对应数据库中的people_count字段，用餐的人数
    private Integer peopleCount;
    // 联系人姓名，对应数据库中的contact_name字段，联系人的姓名
    private String contactName;
    // 联系人电话，对应数据库中的contact_phone字段，联系人的电话
    private String contactPhone;
    // 特殊要求，对应数据库中的special_requirements字段，订单的特殊要求
    private String specialRequirements;
    // 预估金额，对应数据库中的estimated_amount字段，订单的预估金额（元）
    private BigDecimal estimatedAmount;
    // 订单总金额，对应数据库中的total_amount字段，订单的总金额（元），用于数据库total_amount字段
    private BigDecimal totalAmount;
    // 统一订单号，对应数据库中的unified_order_no字段，关联统一订单表的unified_order_no字段（外键）
    private String unifiedOrderNo;
    // 订单状态，对应数据库中的order_status字段，订单的状态（0-待支付，1-已支付，2-已取消，3-已完成）
    private Integer orderStatus;
    // 创建时间，对应数据库中的create_time字段，订单的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，订单的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的订单对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的订单对象
    public RestaurantOrder() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个订单对象并设置基本属性
     * @param {String} orderNo - 订单号
     * @param {Integer} userId - 用户编号
     * @param {Long} restaurantId - 餐厅编号
     * @param {String} restaurantName - 餐厅名称
     * @param {LocalDate} bookingDate - 预订日期
     * @param {String} bookingTime - 预订时间
     * @param {Integer} peopleCount - 用餐人数
     * @param {String} contactName - 联系人姓名
     * @param {String} contactPhone - 联系人电话
     * @param {String} specialRequirements - 特殊要求
     * @param {BigDecimal} estimatedAmount - 预估金额
     */
    // 有参构造函数，创建一个订单对象并设置基本属性
    public RestaurantOrder(String orderNo, Integer userId, Long restaurantId, 
                          String restaurantName, LocalDate bookingDate, String bookingTime, 
                          Integer peopleCount, String contactName, String contactPhone, 
                          String specialRequirements, BigDecimal estimatedAmount) {
        // 将传入的订单号赋值给当前对象的orderNo字段
        this.orderNo = orderNo;
        // 将传入的用户编号赋值给当前对象的userId字段
        this.userId = userId;
        // 将传入的餐厅编号赋值给当前对象的restaurantId字段
        this.restaurantId = restaurantId;
        // 将传入的餐厅名称赋值给当前对象的restaurantName字段
        this.restaurantName = restaurantName;
        // 将传入的预订日期赋值给当前对象的bookingDate字段
        this.bookingDate = bookingDate;
        // 将传入的预订时间赋值给当前对象的bookingTime字段
        this.bookingTime = bookingTime;
        // 将传入的用餐人数赋值给当前对象的peopleCount字段
        this.peopleCount = peopleCount;
        // 将传入的联系人姓名赋值给当前对象的contactName字段
        this.contactName = contactName;
        // 将传入的联系人电话赋值给当前对象的contactPhone字段
        this.contactPhone = contactPhone;
        // 将传入的特殊要求赋值给当前对象的specialRequirements字段
        this.specialRequirements = specialRequirements;
        // 将传入的预估金额赋值给当前对象的estimatedAmount字段
        this.estimatedAmount = estimatedAmount;
        // 将订单状态设置为0（待支付）
        this.orderStatus = 0; // 0=待支付
    }

    /**
     * 获取订单编号
     * 功能概述：返回订单的唯一标识ID
     * @return {Long} 返回订单编号
     */
    // Getter和Setter方法
    // 获取订单编号方法，返回订单的唯一标识ID
    public Long getId() {
        // 返回订单编号
        return id;
    }

    /**
     * 设置订单编号
     * 功能概述：设置订单的唯一标识ID
     * @param {Long} id - 订单编号
     */
    // 设置订单编号方法，接收订单编号参数
    public void setId(Long id) {
        // 将传入的订单编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取订单号
     * 功能概述：返回订单的唯一标识号
     * @return {String} 返回订单号
     */
    // 获取订单号方法，返回订单的唯一标识号
    public String getOrderNo() {
        // 返回订单号
        return orderNo;
    }

    /**
     * 设置订单号
     * 功能概述：设置订单的唯一标识号
     * @param {String} orderNo - 订单号
     */
    // 设置订单号方法，接收订单号参数
    public void setOrderNo(String orderNo) {
        // 将传入的订单号赋值给当前对象的orderNo字段
        this.orderNo = orderNo;
    }

    /**
     * 获取用户编号
     * 功能概述：返回订单所属的用户编号
     * @return {Integer} 返回用户编号
     */
    // 获取用户编号方法，返回订单所属的用户编号
    public Integer getUserId() {
        // 返回用户编号
        return userId;
    }

    /**
     * 设置用户编号
     * 功能概述：设置订单所属的用户编号
     * @param {Integer} userId - 用户编号
     */
    // 设置用户编号方法，接收用户编号参数
    public void setUserId(Integer userId) {
        // 将传入的用户编号赋值给当前对象的userId字段
        this.userId = userId;
    }

    /**
     * 获取餐厅编号
     * 功能概述：返回订单所属的餐厅编号
     * @return {Long} 返回餐厅编号
     */
    // 获取餐厅编号方法，返回订单所属的餐厅编号
    public Long getRestaurantId() {
        // 返回餐厅编号
        return restaurantId;
    }

    /**
     * 设置餐厅编号
     * 功能概述：设置订单所属的餐厅编号
     * @param {Long} restaurantId - 餐厅编号
     */
    // 设置餐厅编号方法，接收餐厅编号参数
    public void setRestaurantId(Long restaurantId) {
        // 将传入的餐厅编号赋值给当前对象的restaurantId字段
        this.restaurantId = restaurantId;
    }

    /**
     * 获取餐厅名称
     * 功能概述：返回餐厅的名称
     * @return {String} 返回餐厅名称
     */
    // 获取餐厅名称方法，返回餐厅的名称
    public String getRestaurantName() {
        // 返回餐厅名称
        return restaurantName;
    }

    /**
     * 设置餐厅名称
     * 功能概述：设置餐厅的名称
     * @param {String} restaurantName - 餐厅名称
     */
    // 设置餐厅名称方法，接收餐厅名称参数
    public void setRestaurantName(String restaurantName) {
        // 将传入的餐厅名称赋值给当前对象的restaurantName字段
        this.restaurantName = restaurantName;
    }

    /**
     * 获取预订日期
     * 功能概述：返回预订的日期
     * @return {LocalDate} 返回预订日期
     */
    // 获取预订日期方法，返回预订的日期
    public LocalDate getBookingDate() {
        // 返回预订日期
        return bookingDate;
    }

    /**
     * 设置预订日期
     * 功能概述：设置预订的日期
     * @param {LocalDate} bookingDate - 预订日期
     */
    // 设置预订日期方法，接收预订日期参数
    public void setBookingDate(LocalDate bookingDate) {
        // 将传入的预订日期赋值给当前对象的bookingDate字段
        this.bookingDate = bookingDate;
    }

    /**
     * 获取预订时间
     * 功能概述：返回预订的时间
     * @return {String} 返回预订时间
     */
    // 获取预订时间方法，返回预订的时间
    public String getBookingTime() {
        // 返回预订时间
        return bookingTime;
    }

    /**
     * 设置预订时间
     * 功能概述：设置预订的时间
     * @param {String} bookingTime - 预订时间
     */
    // 设置预订时间方法，接收预订时间参数
    public void setBookingTime(String bookingTime) {
        // 将传入的预订时间赋值给当前对象的bookingTime字段
        this.bookingTime = bookingTime;
    }

    /**
     * 获取用餐人数
     * 功能概述：返回用餐的人数
     * @return {Integer} 返回用餐人数
     */
    // 获取用餐人数方法，返回用餐的人数
    public Integer getPeopleCount() {
        // 返回用餐人数
        return peopleCount;
    }

    /**
     * 设置用餐人数
     * 功能概述：设置用餐的人数
     * @param {Integer} peopleCount - 用餐人数
     */
    // 设置用餐人数方法，接收用餐人数参数
    public void setPeopleCount(Integer peopleCount) {
        // 将传入的用餐人数赋值给当前对象的peopleCount字段
        this.peopleCount = peopleCount;
    }

    /**
     * 获取联系人姓名
     * 功能概述：返回联系人的姓名
     * @return {String} 返回联系人姓名
     */
    // 获取联系人姓名方法，返回联系人的姓名
    public String getContactName() {
        // 返回联系人姓名
        return contactName;
    }

    /**
     * 设置联系人姓名
     * 功能概述：设置联系人的姓名
     * @param {String} contactName - 联系人姓名
     */
    // 设置联系人姓名方法，接收联系人姓名参数
    public void setContactName(String contactName) {
        // 将传入的联系人姓名赋值给当前对象的contactName字段
        this.contactName = contactName;
    }

    /**
     * 获取联系人电话
     * 功能概述：返回联系人的电话
     * @return {String} 返回联系人电话
     */
    // 获取联系人电话方法，返回联系人的电话
    public String getContactPhone() {
        // 返回联系人电话
        return contactPhone;
    }

    /**
     * 设置联系人电话
     * 功能概述：设置联系人的电话
     * @param {String} contactPhone - 联系人电话
     */
    // 设置联系人电话方法，接收联系人电话参数
    public void setContactPhone(String contactPhone) {
        // 将传入的联系人电话赋值给当前对象的contactPhone字段
        this.contactPhone = contactPhone;
    }

    /**
     * 获取特殊要求
     * 功能概述：返回订单的特殊要求
     * @return {String} 返回特殊要求
     */
    // 获取特殊要求方法，返回订单的特殊要求
    public String getSpecialRequirements() {
        // 返回特殊要求
        return specialRequirements;
    }

    /**
     * 设置特殊要求
     * 功能概述：设置订单的特殊要求
     * @param {String} specialRequirements - 特殊要求
     */
    // 设置特殊要求方法，接收特殊要求参数
    public void setSpecialRequirements(String specialRequirements) {
        // 将传入的特殊要求赋值给当前对象的specialRequirements字段
        this.specialRequirements = specialRequirements;
    }

    /**
     * 获取预估金额
     * 功能概述：返回订单的预估金额
     * @return {BigDecimal} 返回预估金额（元）
     */
    // 获取预估金额方法，返回订单的预估金额
    public BigDecimal getEstimatedAmount() {
        // 返回预估金额
        return estimatedAmount;
    }

    /**
     * 设置预估金额
     * 功能概述：设置订单的预估金额
     * @param {BigDecimal} estimatedAmount - 预估金额（元）
     */
    // 设置预估金额方法，接收预估金额参数
    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        // 将传入的预估金额赋值给当前对象的estimatedAmount字段
        this.estimatedAmount = estimatedAmount;
    }

    /**
     * 获取订单总金额
     * 功能概述：返回订单的总金额
     * @return {BigDecimal} 返回订单总金额（元）
     */
    // 获取订单总金额方法，返回订单的总金额
    public BigDecimal getTotalAmount() {
        // 返回订单总金额
        return totalAmount;
    }

    /**
     * 设置订单总金额
     * 功能概述：设置订单的总金额
     * @param {BigDecimal} totalAmount - 订单总金额（元）
     */
    // 设置订单总金额方法，接收订单总金额参数
    public void setTotalAmount(BigDecimal totalAmount) {
        // 将传入的订单总金额赋值给当前对象的totalAmount字段
        this.totalAmount = totalAmount;
    }

    /**
     * 获取统一订单号
     * 功能概述：返回关联的统一订单号
     * @return {String} 返回统一订单号
     */
    // 获取统一订单号方法，返回关联的统一订单号
    public String getUnifiedOrderNo() {
        // 返回统一订单号
        return unifiedOrderNo;
    }

    /**
     * 设置统一订单号
     * 功能概述：设置关联的统一订单号
     * @param {String} unifiedOrderNo - 统一订单号
     */
    // 设置统一订单号方法，接收统一订单号参数
    public void setUnifiedOrderNo(String unifiedOrderNo) {
        // 将传入的统一订单号赋值给当前对象的unifiedOrderNo字段
        this.unifiedOrderNo = unifiedOrderNo;
    }

    /**
     * 获取订单状态
     * 功能概述：返回订单的状态
     * @return {Integer} 返回订单状态（0-待支付，1-已支付，2-已取消，3-已完成）
     */
    // 获取订单状态方法，返回订单的状态
    public Integer getOrderStatus() {
        // 返回订单状态
        return orderStatus;
    }

    /**
     * 设置订单状态
     * 功能概述：设置订单的状态
     * @param {Integer} orderStatus - 订单状态（0-待支付，1-已支付，2-已取消，3-已完成）
     */
    // 设置订单状态方法，接收订单状态参数
    public void setOrderStatus(Integer orderStatus) {
        // 将传入的订单状态赋值给当前对象的orderStatus字段
        this.orderStatus = orderStatus;
    }

    /**
     * 获取创建时间
     * 功能概述：返回订单的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回订单的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置订单的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回订单的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回订单的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置订单的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将订单对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回订单对象的字符串表示
     */
    // 重写Object类的toString方法，返回订单对象的字符串表示
    @Override
    // toString方法，将订单对象转换为字符串格式
    public String toString() {
        // 返回订单对象的字符串表示，包含所有字段的值
        return "RestaurantOrder{" +
                "id=" + id +                                    // 订单编号
                ", orderNo='" + orderNo + '\'' +               // 订单号
                ", userId=" + userId +                         // 用户编号
                ", restaurantId=" + restaurantId +              // 餐厅编号
                ", restaurantName='" + restaurantName + '\'' +  // 餐厅名称
                ", bookingDate=" + bookingDate +                 // 预订日期
                ", bookingTime='" + bookingTime + '\'' +         // 预订时间
                ", peopleCount=" + peopleCount +                 // 用餐人数
                ", contactName='" + contactName + '\'' +         // 联系人姓名
                ", contactPhone='" + contactPhone + '\'' +       // 联系人电话
                ", specialRequirements='" + specialRequirements + '\'' +  // 特殊要求
                ", estimatedAmount=" + estimatedAmount +        // 预估金额
                ", totalAmount=" + totalAmount +                 // 订单总金额
                ", unifiedOrderNo='" + unifiedOrderNo + '\'' +  // 统一订单号
                ", orderStatus=" + orderStatus +                  // 订单状态
                ", createTime=" + createTime +                   // 创建时间
                ", updateTime=" + updateTime +                   // 更新时间
                '}';
    }
}
