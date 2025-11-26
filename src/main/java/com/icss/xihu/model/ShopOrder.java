// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;
// 导入List集合接口
import java.util.List;

/**
 * 购物订单实体类
 * 功能概述：表示系统中的购物订单信息，包括订单号、用户ID、总金额、收货地址、收货人信息、订单状态等
 */
// 购物订单实体类，对应数据库中的shop_order表
public class ShopOrder {
    // 订单ID，主键，自增
    private Integer id;
    // 订单号，唯一标识，格式：SHOP + 时间戳 + 随机数
    private String orderNo;
    // 用户ID，关联user表的id字段
    private Integer userId;
    // 订单总金额，使用BigDecimal确保精度
    private BigDecimal totalAmount;
    // 收货地址，用于商品配送
    private String shippingAddress;
    // 联系电话，用于商品配送联系
    private String shippingPhone;
    // 收货人姓名，用于商品配送
    private String shippingName;
    // 订单状态，0=待支付，1=已支付，2=已取消，3=已完成
    private Integer orderStatus;
    // 统一订单号，关联unified_order表的order_no字段
    private String unifiedOrderNo;
    // 支付时间，记录订单支付完成的时间
    private LocalDateTime paymentTime;
    // 创建时间，记录订单创建时间
    private LocalDateTime createTime;
    // 更新时间，记录订单最后更新时间
    private LocalDateTime updateTime;

    // 订单详情列表
    // 购物订单详情列表，包含订单的商品信息（商品ID、名称、价格、数量等）
    private List<ShopOrderDetail> orderDetails;

    /**
     * 无参构造方法
     * 功能概述：创建空的购物订单对象，用于MyBatis等框架的反序列化
     */
    // 构造函数，创建空的购物订单对象
    public ShopOrder() {
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
     * 获取收货地址
     * @return 收货地址
     */
    // 获取收货地址方法，返回收货地址
    public String getShippingAddress() {
        // 返回收货地址
        return shippingAddress;
    }

    /**
     * 设置收货地址
     * @param shippingAddress 收货地址
     */
    // 设置收货地址方法，接收收货地址参数
    public void setShippingAddress(String shippingAddress) {
        // 设置收货地址
        this.shippingAddress = shippingAddress;
    }

    /**
     * 获取联系电话
     * @return 联系电话
     */
    // 获取联系电话方法，返回联系电话
    public String getShippingPhone() {
        // 返回联系电话
        return shippingPhone;
    }

    /**
     * 设置联系电话
     * @param shippingPhone 联系电话
     */
    // 设置联系电话方法，接收联系电话参数
    public void setShippingPhone(String shippingPhone) {
        // 设置联系电话
        this.shippingPhone = shippingPhone;
    }

    /**
     * 获取收货人姓名
     * @return 收货人姓名
     */
    // 获取收货人姓名方法，返回收货人姓名
    public String getShippingName() {
        // 返回收货人姓名
        return shippingName;
    }

    /**
     * 设置收货人姓名
     * @param shippingName 收货人姓名
     */
    // 设置收货人姓名方法，接收收货人姓名参数
    public void setShippingName(String shippingName) {
        // 设置收货人姓名
        this.shippingName = shippingName;
    }

    /**
     * 获取订单状态
     * @return 订单状态（0=待支付，1=已支付，2=已取消，3=已完成）
     */
    // 获取订单状态方法，返回订单状态
    public Integer getOrderStatus() {
        // 返回订单状态
        return orderStatus;
    }

    /**
     * 设置订单状态
     * @param orderStatus 订单状态（0=待支付，1=已支付，2=已取消，3=已完成）
     */
    // 设置订单状态方法，接收订单状态参数
    public void setOrderStatus(Integer orderStatus) {
        // 设置订单状态
        this.orderStatus = orderStatus;
    }

    /**
     * 获取统一订单号
     * @return 统一订单号
     */
    // 获取统一订单号方法，返回统一订单号
    public String getUnifiedOrderNo() {
        // 返回统一订单号
        return unifiedOrderNo;
    }

    /**
     * 设置统一订单号
     * @param unifiedOrderNo 统一订单号
     */
    // 设置统一订单号方法，接收统一订单号参数
    public void setUnifiedOrderNo(String unifiedOrderNo) {
        // 设置统一订单号
        this.unifiedOrderNo = unifiedOrderNo;
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
     * 获取订单详情列表
     * @return 订单详情列表
     */
    // 获取订单详情列表方法，返回订单详情列表
    public List<ShopOrderDetail> getOrderDetails() {
        // 返回订单详情列表
        return orderDetails;
    }

    /**
     * 设置订单详情列表
     * @param orderDetails 订单详情列表
     */
    // 设置订单详情列表方法，接收订单详情列表参数
    public void setOrderDetails(List<ShopOrderDetail> orderDetails) {
        // 设置订单详情列表
        this.orderDetails = orderDetails;
    }
}

