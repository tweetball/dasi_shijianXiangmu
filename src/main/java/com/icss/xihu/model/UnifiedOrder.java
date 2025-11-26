/**
 * 统一订单实体类
 * 功能概述：封装统一订单信息，对应数据库中的unified_order表，包含订单的基本信息、支付信息等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 统一订单实体类
 * 功能概述：封装统一订单信息，对应数据库中的unified_order表，包含订单的基本信息、支付信息等
 */
// 统一订单实体类，封装统一订单信息
public class UnifiedOrder {
    // 订单编号，对应数据库中的id字段，主键，自增
    private Integer id;
    // 订单号，对应数据库中的order_no字段，订单的唯一标识号
    private String orderNo;
    // 用户编号，对应数据库中的user_id字段，关联用户表的id字段（外键）
    private Integer userId;
    // 订单类型，对应数据库中的order_type字段，订单的类型（FOOD/HOTEL/SHOPPING/TRAVEL/PAYMENT）
    private String orderType;
    // 模块订单编号，对应数据库中的module_order_id字段，关联各模块原订单的id字段（外键）
    private Integer moduleOrderId;
    // 订单标题，对应数据库中的order_title字段，订单的标题
    private String orderTitle;
    // 订单描述，对应数据库中的order_description字段，订单的描述信息
    private String orderDescription;
    // 订单总金额，对应数据库中的total_amount字段，订单的总金额（元）
    private BigDecimal totalAmount;
    // 支付状态，对应数据库中的payment_status字段，订单的支付状态（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
    private Integer paymentStatus;
    // 支付方式，对应数据库中的payment_method字段，订单的支付方式（如"支付宝"、"微信"等）
    private String paymentMethod;
    // 支付时间，对应数据库中的payment_time字段，订单的支付时间
    private LocalDateTime paymentTime;
    // 创建时间，对应数据库中的create_time字段，订单的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，订单的最后更新时间
    private LocalDateTime updateTime;

    // 关联字段(用于展示)
    // 用户名，关联用户表的name字段，用户的名称
    private String userName;
    // 支付状态文本，根据支付状态转换的文本描述（如"待支付"、"已支付"等）
    private String paymentStatusText;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的订单对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的订单对象
    public UnifiedOrder() {
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
     * 获取订单类型
     * 功能概述：返回订单的类型
     * @return {String} 返回订单类型（FOOD/HOTEL/SHOPPING/TRAVEL/PAYMENT）
     */
    // 获取订单类型方法，返回订单的类型
    public String getOrderType() {
        // 返回订单类型
        return orderType;
    }

    /**
     * 设置订单类型
     * 功能概述：设置订单的类型
     * @param {String} orderType - 订单类型（FOOD/HOTEL/SHOPPING/TRAVEL/PAYMENT）
     */
    // 设置订单类型方法，接收订单类型参数
    public void setOrderType(String orderType) {
        // 将传入的订单类型赋值给当前对象的orderType字段
        this.orderType = orderType;
    }

    /**
     * 获取模块订单编号
     * 功能概述：返回关联的模块订单编号
     * @return {Integer} 返回模块订单编号
     */
    // 获取模块订单编号方法，返回关联的模块订单编号
    public Integer getModuleOrderId() {
        // 返回模块订单编号
        return moduleOrderId;
    }

    /**
     * 设置模块订单编号
     * 功能概述：设置关联的模块订单编号
     * @param {Integer} moduleOrderId - 模块订单编号
     */
    // 设置模块订单编号方法，接收模块订单编号参数
    public void setModuleOrderId(Integer moduleOrderId) {
        // 将传入的模块订单编号赋值给当前对象的moduleOrderId字段
        this.moduleOrderId = moduleOrderId;
    }

    /**
     * 获取订单标题
     * 功能概述：返回订单的标题
     * @return {String} 返回订单标题
     */
    // 获取订单标题方法，返回订单的标题
    public String getOrderTitle() {
        // 返回订单标题
        return orderTitle;
    }

    /**
     * 设置订单标题
     * 功能概述：设置订单的标题
     * @param {String} orderTitle - 订单标题
     */
    // 设置订单标题方法，接收订单标题参数
    public void setOrderTitle(String orderTitle) {
        // 将传入的订单标题赋值给当前对象的orderTitle字段
        this.orderTitle = orderTitle;
    }

    /**
     * 获取订单描述
     * 功能概述：返回订单的描述信息
     * @return {String} 返回订单描述
     */
    // 获取订单描述方法，返回订单的描述信息
    public String getOrderDescription() {
        // 返回订单描述
        return orderDescription;
    }

    /**
     * 设置订单描述
     * 功能概述：设置订单的描述信息
     * @param {String} orderDescription - 订单描述
     */
    // 设置订单描述方法，接收订单描述参数
    public void setOrderDescription(String orderDescription) {
        // 将传入的订单描述赋值给当前对象的orderDescription字段
        this.orderDescription = orderDescription;
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
     * 获取支付状态
     * 功能概述：返回订单的支付状态
     * @return {Integer} 返回支付状态（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
     */
    // 获取支付状态方法，返回订单的支付状态
    public Integer getPaymentStatus() {
        // 返回支付状态
        return paymentStatus;
    }

    /**
     * 设置支付状态
     * 功能概述：设置订单的支付状态
     * @param {Integer} paymentStatus - 支付状态（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
     */
    // 设置支付状态方法，接收支付状态参数
    public void setPaymentStatus(Integer paymentStatus) {
        // 将传入的支付状态赋值给当前对象的paymentStatus字段
        this.paymentStatus = paymentStatus;
    }

    /**
     * 获取支付方式
     * 功能概述：返回订单的支付方式
     * @return {String} 返回支付方式
     */
    // 获取支付方式方法，返回订单的支付方式
    public String getPaymentMethod() {
        // 返回支付方式
        return paymentMethod;
    }

    /**
     * 设置支付方式
     * 功能概述：设置订单的支付方式
     * @param {String} paymentMethod - 支付方式
     */
    // 设置支付方式方法，接收支付方式参数
    public void setPaymentMethod(String paymentMethod) {
        // 将传入的支付方式赋值给当前对象的paymentMethod字段
        this.paymentMethod = paymentMethod;
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

    /**
     * 获取用户名
     * 功能概述：返回关联的用户名称
     * @return {String} 返回用户名
     */
    // 获取用户名方法，返回关联的用户名称
    public String getUserName() {
        // 返回用户名
        return userName;
    }

    /**
     * 设置用户名
     * 功能概述：设置关联的用户名称
     * @param {String} userName - 用户名
     */
    // 设置用户名方法，接收用户名参数
    public void setUserName(String userName) {
        // 将传入的用户名赋值给当前对象的userName字段
        this.userName = userName;
    }

    /**
     * 获取支付状态文本
     * 功能概述：返回根据支付状态转换的文本描述
     * @return {String} 返回支付状态文本
     */
    // 获取支付状态文本方法，返回根据支付状态转换的文本描述
    public String getPaymentStatusText() {
        // 返回支付状态文本
        return paymentStatusText;
    }

    /**
     * 设置支付状态文本
     * 功能概述：设置根据支付状态转换的文本描述
     * @param {String} paymentStatusText - 支付状态文本
     */
    // 设置支付状态文本方法，接收支付状态文本参数
    public void setPaymentStatusText(String paymentStatusText) {
        // 将传入的支付状态文本赋值给当前对象的paymentStatusText字段
        this.paymentStatusText = paymentStatusText;
    }

    /**
     * 根据状态码获取状态文本（静态方法）
     * 功能概述：根据支付状态码返回对应的文本描述
     * @param {Integer} status - 支付状态码（0-待支付，1-已支付，2-已取消，3-已完成，4-已退款）
     * @return {String} 返回状态文本（"待支付"、"已支付"、"已取消"、"已完成"、"已退款"、"未知"）
     */
    // 根据状态码获取状态文本方法（静态方法），根据支付状态码返回对应的文本描述
    public static String getStatusText(Integer status) {
        // 如果状态码为空，返回"未知"
        if (status == null) return "未知";
        // 根据状态码返回对应的文本描述
        switch (status) {
            case 0:
                // 如果状态码为0（待支付），返回"待支付"
                return "待支付";
            case 1:
                // 如果状态码为1（已支付），返回"已支付"
                return "已支付";
            case 2:
                // 如果状态码为2（已取消），返回"已取消"
                return "已取消";
            case 3:
                // 如果状态码为3（已完成），返回"已完成"
                return "已完成";
            case 4:
                // 如果状态码为4（已退款），返回"已退款"
                return "已退款";
            default:
                // 如果状态码未知，返回"未知"
                return "未知";
        }
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
        // 返回订单对象的字符串表示，包含主要字段的值
        return "UnifiedOrder{" +
                "id=" + id +                                    // 订单编号
                ", orderNo='" + orderNo + '\'' +               // 订单号
                ", userId=" + userId +                          // 用户编号
                ", orderType='" + orderType + '\'' +            // 订单类型
                ", orderTitle='" + orderTitle + '\'' +          // 订单标题
                ", totalAmount=" + totalAmount +                 // 订单总金额
                ", paymentStatus=" + paymentStatus +             // 支付状态
                ", createTime=" + createTime +                   // 创建时间
                '}';
    }
}
