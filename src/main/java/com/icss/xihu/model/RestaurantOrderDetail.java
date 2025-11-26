/**
 * 餐饮订单详情实体类
 * 功能概述：封装餐饮订单详情信息，对应数据库中的restaurant_order_detail表，包含订单详情的菜品信息、数量、价格等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 餐饮订单详情实体类
 * 功能概述：封装餐饮订单详情信息，对应数据库中的restaurant_order_detail表，包含订单详情的菜品信息、数量、价格等
 */
// 餐饮订单详情实体类，封装餐饮订单详情信息
public class RestaurantOrderDetail {
    // 详情编号，对应数据库中的id字段，主键，自增
    private Integer id;
    // 订单ID，对应数据库中的order_id字段，关联餐厅订单表的id字段（外键）
    private Integer orderId;
    // 菜品ID，对应数据库中的menu_id字段，关联餐厅菜单表的id字段（外键）
    private Integer menuId;
    // 菜品名称，对应数据库中的menu_name字段，菜品的名称
    private String menuName;
    // 菜品单价，对应数据库中的menu_price字段，菜品的单价（元）
    private BigDecimal menuPrice;
    // 购买数量，对应数据库中的quantity字段，购买的数量
    private Integer quantity;
    // 小计，对应数据库中的subtotal字段，小计金额（元）= 单价 × 数量
    private BigDecimal subtotal;
    // 创建时间，对应数据库中的create_time字段，详情的创建时间
    private LocalDateTime createTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的订单详情对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的订单详情对象
    public RestaurantOrderDetail() {
    }

    /**
     * 获取详情编号
     * 功能概述：返回详情的唯一标识ID
     * @return {Integer} 返回详情编号
     */
    // Getters and Setters
    // 获取详情编号方法，返回详情的唯一标识ID
    public Integer getId() {
        // 返回详情编号
        return id;
    }

    /**
     * 设置详情编号
     * 功能概述：设置详情的唯一标识ID
     * @param {Integer} id - 详情编号
     */
    // 设置详情编号方法，接收详情编号参数
    public void setId(Integer id) {
        // 将传入的详情编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取订单ID
     * 功能概述：返回关联的订单ID
     * @return {Integer} 返回订单ID
     */
    // 获取订单ID方法，返回关联的订单ID
    public Integer getOrderId() {
        // 返回订单ID
        return orderId;
    }

    /**
     * 设置订单ID
     * 功能概述：设置关联的订单ID
     * @param {Integer} orderId - 订单ID
     */
    // 设置订单ID方法，接收订单ID参数
    public void setOrderId(Integer orderId) {
        // 将传入的订单ID赋值给当前对象的orderId字段
        this.orderId = orderId;
    }

    /**
     * 获取菜品ID
     * 功能概述：返回关联的菜品ID
     * @return {Integer} 返回菜品ID
     */
    // 获取菜品ID方法，返回关联的菜品ID
    public Integer getMenuId() {
        // 返回菜品ID
        return menuId;
    }

    /**
     * 设置菜品ID
     * 功能概述：设置关联的菜品ID
     * @param {Integer} menuId - 菜品ID
     */
    // 设置菜品ID方法，接收菜品ID参数
    public void setMenuId(Integer menuId) {
        // 将传入的菜品ID赋值给当前对象的menuId字段
        this.menuId = menuId;
    }

    /**
     * 获取菜品名称
     * 功能概述：返回菜品的名称
     * @return {String} 返回菜品名称
     */
    // 获取菜品名称方法，返回菜品的名称
    public String getMenuName() {
        // 返回菜品名称
        return menuName;
    }

    /**
     * 设置菜品名称
     * 功能概述：设置菜品的名称
     * @param {String} menuName - 菜品名称
     */
    // 设置菜品名称方法，接收菜品名称参数
    public void setMenuName(String menuName) {
        // 将传入的菜品名称赋值给当前对象的menuName字段
        this.menuName = menuName;
    }

    /**
     * 获取菜品单价
     * 功能概述：返回菜品的单价
     * @return {BigDecimal} 返回菜品单价（元）
     */
    // 获取菜品单价方法，返回菜品的单价
    public BigDecimal getMenuPrice() {
        // 返回菜品单价
        return menuPrice;
    }

    /**
     * 设置菜品单价
     * 功能概述：设置菜品的单价
     * @param {BigDecimal} menuPrice - 菜品单价（元）
     */
    // 设置菜品单价方法，接收菜品单价参数
    public void setMenuPrice(BigDecimal menuPrice) {
        // 将传入的菜品单价赋值给当前对象的menuPrice字段
        this.menuPrice = menuPrice;
    }

    /**
     * 获取购买数量
     * 功能概述：返回购买的数量
     * @return {Integer} 返回购买数量
     */
    // 获取购买数量方法，返回购买的数量
    public Integer getQuantity() {
        // 返回购买数量
        return quantity;
    }

    /**
     * 设置购买数量
     * 功能概述：设置购买的数量
     * @param {Integer} quantity - 购买数量
     */
    // 设置购买数量方法，接收购买数量参数
    public void setQuantity(Integer quantity) {
        // 将传入的购买数量赋值给当前对象的quantity字段
        this.quantity = quantity;
    }

    /**
     * 获取小计
     * 功能概述：返回小计金额（单价 × 数量）
     * @return {BigDecimal} 返回小计金额（元）
     */
    // 获取小计方法，返回小计金额（单价 × 数量）
    public BigDecimal getSubtotal() {
        // 返回小计金额
        return subtotal;
    }

    /**
     * 设置小计
     * 功能概述：设置小计金额（单价 × 数量）
     * @param {BigDecimal} subtotal - 小计金额（元）
     */
    // 设置小计方法，接收小计金额参数
    public void setSubtotal(BigDecimal subtotal) {
        // 将传入的小计金额赋值给当前对象的subtotal字段
        this.subtotal = subtotal;
    }

    /**
     * 获取创建时间
     * 功能概述：返回详情的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回详情的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置详情的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }
}
