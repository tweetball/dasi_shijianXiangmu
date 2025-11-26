// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;

/**
 * 重新设计的统一订单模型
 * 功能概述：表示系统中的统一订单信息，用于整合各业务模块的订单（美食、酒店、购物、旅游、缴费等）
 */
// 统一订单新实体类，对应数据库中的unified_order表
public class UnifiedOrderNew {
    // 订单ID，主键，自增
    private Integer id;
    // 统一订单号，唯一标识，格式：UO + 类型前缀 + 时间戳 + 随机数
    private String orderNo;
    // 用户ID，关联user表的id字段
    private Integer userId;
    // 订单类型，FOOD=美食，HOTEL=酒店，SHOPPING=购物，TRAVEL=旅游，PAYMENT=缴费
    private String orderType;
    // 模块订单ID，关联各业务模块的订单ID（如shop_order.id、hotel_order.id等）
    private Integer moduleOrderId;
    // 订单标题，用于显示
    private String orderTitle;
    // 订单描述，用于显示订单详细信息
    private String orderDescription;
    // 订单总金额，使用BigDecimal确保精度
    private BigDecimal totalAmount;
    // 支付状态，0=待支付，1=已支付，2=已取消，3=已完成
    private Integer paymentStatus;
    // 支付方式，微信、支付宝、银行卡等
    private String paymentMethod;
    // 支付时间，记录订单支付完成的时间
    private LocalDateTime paymentTime;
    // 创建时间，记录订单创建时间
    private LocalDateTime createTime;
    // 更新时间，记录订单最后更新时间
    private LocalDateTime updateTime;
    
    // 状态常量
    // 待支付状态常量，值为0
    public static final int STATUS_UNPAID = 0;
    // 已支付状态常量，值为1
    public static final int STATUS_PAID = 1;
    // 已取消状态常量，值为2
    public static final int STATUS_CANCELLED = 2;
    // 已完成状态常量，值为3
    public static final int STATUS_COMPLETED = 3;
    
    // 订单类型常量
    // 美食订单类型常量，值为"FOOD"
    public static final String TYPE_FOOD = "FOOD";
    // 酒店订单类型常量，值为"HOTEL"
    public static final String TYPE_HOTEL = "HOTEL";
    // 购物订单类型常量，值为"SHOPPING"
    public static final String TYPE_SHOPPING = "SHOPPING";
    // 旅游订单类型常量，值为"TRAVEL"
    public static final String TYPE_TRAVEL = "TRAVEL";
    // 缴费订单类型常量，值为"PAYMENT"
    public static final String TYPE_PAYMENT = "PAYMENT";
    
    /**
     * 无参构造方法
     * 功能概述：创建空的统一订单对象，用于MyBatis等框架的反序列化
     */
    // 无参构造方法，用于创建空的统一订单对象
    public UnifiedOrderNew() {}
    
    /**
     * 有参构造方法
     * 功能概述：创建统一订单对象，设置订单号、用户ID、订单类型、订单标题、总金额，并初始化支付状态为待支付
     * @param orderNo 统一订单号
     * @param userId 用户ID
     * @param orderType 订单类型
     * @param orderTitle 订单标题
     * @param totalAmount 订单总金额
     */
    // 有参构造方法，接收订单号、用户ID、订单类型、订单标题和总金额参数
    public UnifiedOrderNew(String orderNo, Integer userId, String orderType, 
                          String orderTitle, BigDecimal totalAmount) {
        // 设置订单号
        this.orderNo = orderNo;
        // 设置用户ID
        this.userId = userId;
        // 设置订单类型
        this.orderType = orderType;
        // 设置订单标题
        this.orderTitle = orderTitle;
        // 设置订单总金额
        this.totalAmount = totalAmount;
        // 设置支付状态为待支付（STATUS_UNPAID = 0）
        this.paymentStatus = STATUS_UNPAID;
    }
    
    /**
     * 获取状态文本
     * 功能概述：根据支付状态返回对应的中文文本，用于前端显示
     * @return 状态文本（待支付、已支付、已取消、已完成、未知状态）
     */
    // 获取状态文本方法，返回状态文本
    public String getStatusText() {
        // 判断支付状态是否为空
        if (paymentStatus == null) {
            // 如果为空，返回"未知状态"
            return "未知状态";
        }
        // 根据支付状态返回对应的中文文本
        switch (paymentStatus) {
            // 待支付状态，返回"待支付"
            case STATUS_UNPAID: return "待支付";
            // 已支付状态，返回"已支付"
            case STATUS_PAID: return "已支付";
            // 已取消状态，返回"已取消"
            case STATUS_CANCELLED: return "已取消";
            // 已完成状态，返回"已完成"
            case STATUS_COMPLETED: return "已完成";
            // 默认情况，返回"未知状态"
            default: return "未知状态";
        }
    }
    
    /**
     * 获取类型文本
     * 功能概述：根据订单类型返回对应的中文文本，用于前端显示
     * @return 类型文本（美食外卖、酒店预订、购物商城、景点旅游、生活缴费、未知类型）
     */
    // 获取类型文本方法，返回类型文本
    public String getTypeText() {
        // 根据订单类型返回对应的中文文本
        switch (orderType) {
            // 美食订单类型，返回"美食外卖"
            case TYPE_FOOD: return "美食外卖";
            // 酒店订单类型，返回"酒店预订"
            case TYPE_HOTEL: return "酒店预订";
            // 购物订单类型，返回"购物商城"
            case TYPE_SHOPPING: return "购物商城";
            // 旅游订单类型，返回"景点旅游"
            case TYPE_TRAVEL: return "景点旅游";
            // 缴费订单类型，返回"生活缴费"
            case TYPE_PAYMENT: return "生活缴费";
            // 默认情况，返回"未知类型"
            default: return "未知类型";
        }
    }
    
    /**
     * 检查是否可以支付
     * 功能概述：检查订单是否处于待支付状态，只有待支付状态的订单才能支付
     * @return 是否可以支付（true=可以支付，false=不能支付）
     */
    // 检查是否可以支付方法，返回是否可以支付
    public boolean canPay() {
        // 判断支付状态不为空且为待支付状态（STATUS_UNPAID = 0）
        return paymentStatus != null && paymentStatus == STATUS_UNPAID;
    }
    
    /**
     * 检查是否可以取消
     * 功能概述：检查订单是否处于待支付状态，只有待支付状态的订单才能取消
     * @return 是否可以取消（true=可以取消，false=不能取消）
     */
    // 检查是否可以取消方法，返回是否可以取消
    public boolean canCancel() {
        // 判断支付状态不为空且为待支付状态（STATUS_UNPAID = 0）
        return paymentStatus != null && paymentStatus == STATUS_UNPAID;
    }
    
    /**
     * 检查是否可以删除
     * 功能概述：检查订单是否处于已取消状态，只有已取消状态的订单才能删除
     * @return 是否可以删除（true=可以删除，false=不能删除）
     */
    // 检查是否可以删除方法，返回是否可以删除
    public boolean canDelete() {
        // 判断支付状态不为空且为已取消状态（STATUS_CANCELLED = 2）
        return paymentStatus != null && paymentStatus == STATUS_CANCELLED;
    }
    
    // Getters and Setters
    /**
     * 获取订单ID
     * @return 订单ID
     */
    // 获取订单ID方法，返回订单ID
    public Integer getId() {
        // 返回订单ID
        return id;
    }
    
    /**
     * 设置订单ID
     * @param id 订单ID
     */
    // 设置订单ID方法，接收订单ID参数
    public void setId(Integer id) {
        // 设置订单ID
        this.id = id;
    }
    
    /**
     * 获取统一订单号
     * @return 统一订单号
     */
    // 获取统一订单号方法，返回统一订单号
    public String getOrderNo() {
        // 返回统一订单号
        return orderNo;
    }
    
    /**
     * 设置统一订单号
     * @param orderNo 统一订单号
     */
    // 设置统一订单号方法，接收统一订单号参数
    public void setOrderNo(String orderNo) {
        // 设置统一订单号
        this.orderNo = orderNo;
    }
    
    /**
     * 获取用户ID
     * @return 用户ID
     */
    // 获取用户ID方法，返回用户ID
    public Integer getUserId() {
        // 返回用户ID
        return userId;
    }
    
    /**
     * 设置用户ID
     * @param userId 用户ID
     */
    // 设置用户ID方法，接收用户ID参数
    public void setUserId(Integer userId) {
        // 设置用户ID
        this.userId = userId;
    }
    
    /**
     * 获取订单类型
     * @return 订单类型（FOOD、HOTEL、SHOPPING、TRAVEL、PAYMENT）
     */
    // 获取订单类型方法，返回订单类型
    public String getOrderType() {
        // 返回订单类型
        return orderType;
    }
    
    /**
     * 设置订单类型
     * @param orderType 订单类型（FOOD、HOTEL、SHOPPING、TRAVEL、PAYMENT）
     */
    // 设置订单类型方法，接收订单类型参数
    public void setOrderType(String orderType) {
        // 设置订单类型
        this.orderType = orderType;
    }
    
    /**
     * 获取模块订单ID
     * @return 模块订单ID
     */
    // 获取模块订单ID方法，返回模块订单ID
    public Integer getModuleOrderId() {
        // 返回模块订单ID
        return moduleOrderId;
    }
    
    /**
     * 设置模块订单ID
     * @param moduleOrderId 模块订单ID
     */
    // 设置模块订单ID方法，接收模块订单ID参数
    public void setModuleOrderId(Integer moduleOrderId) {
        // 设置模块订单ID
        this.moduleOrderId = moduleOrderId;
    }
    
    /**
     * 获取订单标题
     * @return 订单标题
     */
    // 获取订单标题方法，返回订单标题
    public String getOrderTitle() {
        // 返回订单标题
        return orderTitle;
    }
    
    /**
     * 设置订单标题
     * @param orderTitle 订单标题
     */
    // 设置订单标题方法，接收订单标题参数
    public void setOrderTitle(String orderTitle) {
        // 设置订单标题
        this.orderTitle = orderTitle;
    }
    
    /**
     * 获取订单描述
     * @return 订单描述
     */
    // 获取订单描述方法，返回订单描述
    public String getOrderDescription() {
        // 返回订单描述
        return orderDescription;
    }
    
    /**
     * 设置订单描述
     * @param orderDescription 订单描述
     */
    // 设置订单描述方法，接收订单描述参数
    public void setOrderDescription(String orderDescription) {
        // 设置订单描述
        this.orderDescription = orderDescription;
    }
    
    /**
     * 获取订单总金额
     * @return 订单总金额
     */
    // 获取订单总金额方法，返回订单总金额
    public BigDecimal getTotalAmount() {
        // 返回订单总金额
        return totalAmount;
    }
    
    /**
     * 设置订单总金额
     * @param totalAmount 订单总金额
     */
    // 设置订单总金额方法，接收订单总金额参数
    public void setTotalAmount(BigDecimal totalAmount) {
        // 设置订单总金额
        this.totalAmount = totalAmount;
    }
    
    /**
     * 获取支付状态
     * @return 支付状态（0=待支付，1=已支付，2=已取消，3=已完成）
     */
    // 获取支付状态方法，返回支付状态
    public Integer getPaymentStatus() {
        // 返回支付状态
        return paymentStatus;
    }
    
    /**
     * 设置支付状态
     * @param paymentStatus 支付状态（0=待支付，1=已支付，2=已取消，3=已完成）
     */
    // 设置支付状态方法，接收支付状态参数
    public void setPaymentStatus(Integer paymentStatus) {
        // 设置支付状态
        this.paymentStatus = paymentStatus;
    }
    
    /**
     * 获取支付方式
     * @return 支付方式（微信、支付宝、银行卡等）
     */
    // 获取支付方式方法，返回支付方式
    public String getPaymentMethod() {
        // 返回支付方式
        return paymentMethod;
    }
    
    /**
     * 设置支付方式
     * @param paymentMethod 支付方式（微信、支付宝、银行卡等）
     */
    // 设置支付方式方法，接收支付方式参数
    public void setPaymentMethod(String paymentMethod) {
        // 设置支付方式
        this.paymentMethod = paymentMethod;
    }
    
    /**
     * 获取支付时间
     * @return 支付时间
     */
    // 获取支付时间方法，返回支付时间
    public LocalDateTime getPaymentTime() {
        // 返回支付时间
        return paymentTime;
    }
    
    /**
     * 设置支付时间
     * @param paymentTime 支付时间
     */
    // 设置支付时间方法，接收支付时间参数
    public void setPaymentTime(LocalDateTime paymentTime) {
        // 设置支付时间
        this.paymentTime = paymentTime;
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
    
    /**
     * 获取更新时间
     * @return 更新时间
     */
    // 获取更新时间方法，返回更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }
    
    /**
     * 设置更新时间
     * @param updateTime 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 设置更新时间
        this.updateTime = updateTime;
    }
    
    /**
     * 重写toString方法
     * 功能概述：返回统一订单对象的字符串表示，用于日志输出和调试
     * @return 统一订单对象的字符串表示
     */
    // 重写toString方法，返回统一订单对象的字符串表示
    @Override
    // toString方法，返回统一订单对象的字符串表示
    public String toString() {
        // 返回统一订单对象的字符串表示，包含ID、订单号、用户ID、订单类型、订单标题、总金额和支付状态
        return "UnifiedOrderNew{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", orderType='" + orderType + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", totalAmount=" + totalAmount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
