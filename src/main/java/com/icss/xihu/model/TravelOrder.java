/**
 * 旅游订单实体类
 * 功能概述：封装旅游订单信息，对应数据库中的travel_order表，包含订单的基本信息、门票信息、金额等
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
 * 旅游订单实体类
 * 功能概述：封装旅游订单信息，对应数据库中的travel_order表，包含订单的基本信息、门票信息、金额等
 */
// 旅游订单实体类，封装旅游订单信息
public class TravelOrder {
    // 订单编号，对应数据库中的id字段，主键，自增
    private Integer id;
    // 订单号，对应数据库中的order_no字段，订单的唯一标识号
    private String orderNo;
    // 用户编号，对应数据库中的user_id字段，关联用户表的id字段（外键）
    private Integer userId;
    // 景点编号，对应数据库中的attraction_id字段，关联景点表的id字段（外键）
    private Integer attractionId;
    // 景点名称，对应数据库中的attraction_name字段，景点的名称
    private String attractionName;
    // 门票类型，对应数据库中的ticket_type字段，门票的类型（如"成人票"、"儿童票"等）
    private String ticketType;
    // 门票单价，对应数据库中的ticket_price字段，门票的单价（元）
    private BigDecimal ticketPrice;
    // 门票数量，对应数据库中的ticket_count字段，购买的门票数量
    private Integer ticketCount;
    // 订单总金额，对应数据库中的total_amount字段，订单的总金额（元）= 单价 × 数量
    private BigDecimal totalAmount;
    // 预约游玩日期，对应数据库中的visit_date字段，预约的游玩日期
    private LocalDate visitDate;
    // 联系人姓名，对应数据库中的contact_name字段，联系人的姓名
    private String contactName;
    // 联系电话，对应数据库中的contact_phone字段，联系人的电话
    private String contactPhone;
    // 订单状态，对应数据库中的order_status字段，订单的状态（0-待支付，1-已支付，2-已取消，3-已完成）
    private Integer orderStatus;
    // 支付时间，对应数据库中的payment_time字段，订单的支付时间
    private LocalDateTime paymentTime;
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
    public TravelOrder() {
    }

    /**
     * 获取订单编号
     * 功能概述：返回订单的唯一标识ID
     * @return {Integer} 返回订单编号
     */
    // Getters and Setters
    // 获取订单编号方法，返回订单的唯一标识ID
    public Integer getId() {
        // 返回订单编号
        return id;
    }

    /**
     * 设置订单编号
     * 功能概述：设置订单的唯一标识ID
     * @param {Integer} id - 订单编号
     */
    // 设置订单编号方法，接收订单编号参数
    public void setId(Integer id) {
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
     * 获取景点编号
     * 功能概述：返回订单所属的景点编号
     * @return {Integer} 返回景点编号
     */
    // 获取景点编号方法，返回订单所属的景点编号
    public Integer getAttractionId() {
        // 返回景点编号
        return attractionId;
    }

    /**
     * 设置景点编号
     * 功能概述：设置订单所属的景点编号
     * @param {Integer} attractionId - 景点编号
     */
    // 设置景点编号方法，接收景点编号参数
    public void setAttractionId(Integer attractionId) {
        // 将传入的景点编号赋值给当前对象的attractionId字段
        this.attractionId = attractionId;
    }

    /**
     * 获取景点名称
     * 功能概述：返回景点的名称
     * @return {String} 返回景点名称
     */
    // 获取景点名称方法，返回景点的名称
    public String getAttractionName() {
        // 返回景点名称
        return attractionName;
    }

    /**
     * 设置景点名称
     * 功能概述：设置景点的名称
     * @param {String} attractionName - 景点名称
     */
    // 设置景点名称方法，接收景点名称参数
    public void setAttractionName(String attractionName) {
        // 将传入的景点名称赋值给当前对象的attractionName字段
        this.attractionName = attractionName;
    }

    /**
     * 获取门票类型
     * 功能概述：返回门票的类型
     * @return {String} 返回门票类型
     */
    // 获取门票类型方法，返回门票的类型
    public String getTicketType() {
        // 返回门票类型
        return ticketType;
    }

    /**
     * 设置门票类型
     * 功能概述：设置门票的类型
     * @param {String} ticketType - 门票类型
     */
    // 设置门票类型方法，接收门票类型参数
    public void setTicketType(String ticketType) {
        // 将传入的门票类型赋值给当前对象的ticketType字段
        this.ticketType = ticketType;
    }

    /**
     * 获取门票单价
     * 功能概述：返回门票的单价
     * @return {BigDecimal} 返回门票单价（元）
     */
    // 获取门票单价方法，返回门票的单价
    public BigDecimal getTicketPrice() {
        // 返回门票单价
        return ticketPrice;
    }

    /**
     * 设置门票单价
     * 功能概述：设置门票的单价
     * @param {BigDecimal} ticketPrice - 门票单价（元）
     */
    // 设置门票单价方法，接收门票单价参数
    public void setTicketPrice(BigDecimal ticketPrice) {
        // 将传入的门票单价赋值给当前对象的ticketPrice字段
        this.ticketPrice = ticketPrice;
    }

    /**
     * 获取门票数量
     * 功能概述：返回购买的门票数量
     * @return {Integer} 返回门票数量
     */
    // 获取门票数量方法，返回购买的门票数量
    public Integer getTicketCount() {
        // 返回门票数量
        return ticketCount;
    }

    /**
     * 设置门票数量
     * 功能概述：设置购买的门票数量
     * @param {Integer} ticketCount - 门票数量
     */
    // 设置门票数量方法，接收门票数量参数
    public void setTicketCount(Integer ticketCount) {
        // 将传入的门票数量赋值给当前对象的ticketCount字段
        this.ticketCount = ticketCount;
    }

    /**
     * 获取订单总金额
     * 功能概述：返回订单的总金额（单价 × 数量）
     * @return {BigDecimal} 返回订单总金额（元）
     */
    // 获取订单总金额方法，返回订单的总金额（单价 × 数量）
    public BigDecimal getTotalAmount() {
        // 返回订单总金额
        return totalAmount;
    }

    /**
     * 设置订单总金额
     * 功能概述：设置订单的总金额（单价 × 数量）
     * @param {BigDecimal} totalAmount - 订单总金额（元）
     */
    // 设置订单总金额方法，接收订单总金额参数
    public void setTotalAmount(BigDecimal totalAmount) {
        // 将传入的订单总金额赋值给当前对象的totalAmount字段
        this.totalAmount = totalAmount;
    }

    /**
     * 获取预约游玩日期
     * 功能概述：返回预约的游玩日期
     * @return {LocalDate} 返回预约游玩日期
     */
    // 获取预约游玩日期方法，返回预约的游玩日期
    public LocalDate getVisitDate() {
        // 返回预约游玩日期
        return visitDate;
    }

    /**
     * 设置预约游玩日期
     * 功能概述：设置预约的游玩日期
     * @param {LocalDate} visitDate - 预约游玩日期
     */
    // 设置预约游玩日期方法，接收预约游玩日期参数
    public void setVisitDate(LocalDate visitDate) {
        // 将传入的预约游玩日期赋值给当前对象的visitDate字段
        this.visitDate = visitDate;
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
     * 获取联系电话
     * 功能概述：返回联系人的电话
     * @return {String} 返回联系电话
     */
    // 获取联系电话方法，返回联系人的电话
    public String getContactPhone() {
        // 返回联系电话
        return contactPhone;
    }

    /**
     * 设置联系电话
     * 功能概述：设置联系人的电话
     * @param {String} contactPhone - 联系电话
     */
    // 设置联系电话方法，接收联系电话参数
    public void setContactPhone(String contactPhone) {
        // 将传入的联系电话赋值给当前对象的contactPhone字段
        this.contactPhone = contactPhone;
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
     * 获取支付时间
     * 功能概述：返回订单的支付时间
     * @return {LocalDateTime} 返回支付时间
     */
    // 获取支付时间方法，返回订单的支付时间
    public LocalDateTime getPaymentTime() {
        // 返回支付时间
        return paymentTime;
    }

    /**
     * 设置支付时间
     * 功能概述：设置订单的支付时间
     * @param {LocalDateTime} paymentTime - 支付时间
     */
    // 设置支付时间方法，接收支付时间参数
    public void setPaymentTime(LocalDateTime paymentTime) {
        // 将传入的支付时间赋值给当前对象的paymentTime字段
        this.paymentTime = paymentTime;
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
}
