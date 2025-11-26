// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;

/**
 * 购物订单详情实体类
 * 功能概述：表示系统中的购物订单详情信息，包括商品信息、价格、数量等
 */
// 购物订单详情实体类，对应数据库中的shop_order_detail表
public class ShopOrderDetail {
    // 订单详情ID，主键，自增
    private Integer id;
    // 订单号，关联shop_order表的order_no字段
    private String orderNo;
    // 商品ID，关联product表的id字段
    private Integer productId;
    // 商品名称，用于显示
    private String productName;
    // 商品单价，使用BigDecimal确保精度
    private BigDecimal productPrice;
    // 购买数量，记录订单的商品数量
    private Integer quantity;
    // 小计，商品单价 * 购买数量，使用BigDecimal确保精度
    private BigDecimal subtotal;
    // 创建时间，记录订单详情创建的时间
    private LocalDateTime createTime;

    /**
     * 无参构造方法
     * 功能概述：创建空的购物订单详情对象，用于MyBatis等框架的反序列化
     */
    // 构造函数，创建空的购物订单详情对象
    public ShopOrderDetail() {
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
     * 获取商品ID
     * @return 商品ID
     */
    // 获取商品ID方法，返回商品ID
    public Integer getProductId() {
        // 返回商品ID
        return productId;
    }

    /**
     * 设置商品ID
     * @param productId 商品ID
     */
    // 设置商品ID方法，接收商品ID参数
    public void setProductId(Integer productId) {
        // 设置商品ID
        this.productId = productId;
    }

    /**
     * 获取商品名称
     * @return 商品名称
     */
    // 获取商品名称方法，返回商品名称
    public String getProductName() {
        // 返回商品名称
        return productName;
    }

    /**
     * 设置商品名称
     * @param productName 商品名称
     */
    // 设置商品名称方法，接收商品名称参数
    public void setProductName(String productName) {
        // 设置商品名称
        this.productName = productName;
    }

    /**
     * 获取商品单价
     * @return 商品单价
     */
    // 获取商品单价方法，返回商品单价
    public BigDecimal getProductPrice() {
        // 返回商品单价
        return productPrice;
    }

    /**
     * 设置商品单价
     * @param productPrice 商品单价
     */
    // 设置商品单价方法，接收商品单价参数
    public void setProductPrice(BigDecimal productPrice) {
        // 设置商品单价
        this.productPrice = productPrice;
    }

    /**
     * 获取购买数量
     * @return 购买数量
     */
    // 获取购买数量方法，返回购买数量
    public Integer getQuantity() {
        // 返回购买数量
        return quantity;
    }

    /**
     * 设置购买数量
     * @param quantity 购买数量
     */
    // 设置购买数量方法，接收购买数量参数
    public void setQuantity(Integer quantity) {
        // 设置购买数量
        this.quantity = quantity;
    }

    /**
     * 获取小计
     * @return 小计（商品单价 * 购买数量）
     */
    // 获取小计方法，返回小计
    public BigDecimal getSubtotal() {
        // 返回小计
        return subtotal;
    }

    /**
     * 设置小计
     * @param subtotal 小计（商品单价 * 购买数量）
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

