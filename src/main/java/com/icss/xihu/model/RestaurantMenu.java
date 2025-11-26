/**
 * 餐厅菜单实体类
 * 功能概述：封装餐厅菜单信息，对应数据库中的restaurant_menu表，包含菜品的基本信息、价格等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 餐厅菜单实体类
 * 功能概述：封装餐厅菜单信息，对应数据库中的restaurant_menu表，包含菜品的基本信息、价格等
 */
// 餐厅菜单实体类，封装餐厅菜单信息
public class RestaurantMenu {
    // 菜单编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 餐厅编号，对应数据库中的restaurant_id字段，关联餐厅表的id字段（外键）
    private Long restaurantId;
    // 菜品分类，对应数据库中的category字段，菜品的分类（如"热菜"、"凉菜"等）
    private String category;
    // 菜品名称，对应数据库中的name字段，菜品的名称
    private String name;
    // 菜品描述，对应数据库中的description字段，菜品的详细描述
    private String description;
    // 菜品价格，对应数据库中的price字段，菜品的价格（元）
    private BigDecimal price;
    // 菜品图片，对应数据库中的image字段，菜品图片的URL路径
    private String image;
    // 是否招牌菜，对应数据库中的is_signature字段，是否为招牌菜（true-是，false-否）
    private Boolean isSignature;
    // 是否可用，对应数据库中的is_available字段，菜品是否可用（true-可用，false-不可用）
    private Boolean isAvailable;
    // 创建时间，对应数据库中的create_time字段，菜品的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，菜品的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的菜单对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的菜单对象
    public RestaurantMenu() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个菜单对象并设置基本属性
     * @param {Long} restaurantId - 餐厅编号
     * @param {String} category - 菜品分类
     * @param {String} name - 菜品名称
     * @param {String} description - 菜品描述
     * @param {BigDecimal} price - 菜品价格
     * @param {String} image - 菜品图片
     * @param {Boolean} isSignature - 是否招牌菜
     * @param {Boolean} isAvailable - 是否可用
     */
    // 有参构造函数，创建一个菜单对象并设置基本属性
    public RestaurantMenu(Long restaurantId, String category, String name, 
                         String description, BigDecimal price, String image, 
                         Boolean isSignature, Boolean isAvailable) {
        // 将传入的餐厅编号赋值给当前对象的restaurantId字段
        this.restaurantId = restaurantId;
        // 将传入的菜品分类赋值给当前对象的category字段
        this.category = category;
        // 将传入的菜品名称赋值给当前对象的name字段
        this.name = name;
        // 将传入的菜品描述赋值给当前对象的description字段
        this.description = description;
        // 将传入的菜品价格赋值给当前对象的price字段
        this.price = price;
        // 将传入的菜品图片赋值给当前对象的image字段
        this.image = image;
        // 将传入的是否招牌菜赋值给当前对象的isSignature字段
        this.isSignature = isSignature;
        // 将传入的是否可用赋值给当前对象的isAvailable字段
        this.isAvailable = isAvailable;
    }

    /**
     * 获取菜单编号
     * 功能概述：返回菜单的唯一标识ID
     * @return {Long} 返回菜单编号
     */
    // Getter和Setter方法
    // 获取菜单编号方法，返回菜单的唯一标识ID
    public Long getId() {
        // 返回菜单编号
        return id;
    }

    /**
     * 设置菜单编号
     * 功能概述：设置菜单的唯一标识ID
     * @param {Long} id - 菜单编号
     */
    // 设置菜单编号方法，接收菜单编号参数
    public void setId(Long id) {
        // 将传入的菜单编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取餐厅编号
     * 功能概述：返回菜单所属的餐厅编号
     * @return {Long} 返回餐厅编号
     */
    // 获取餐厅编号方法，返回菜单所属的餐厅编号
    public Long getRestaurantId() {
        // 返回餐厅编号
        return restaurantId;
    }

    /**
     * 设置餐厅编号
     * 功能概述：设置菜单所属的餐厅编号
     * @param {Long} restaurantId - 餐厅编号
     */
    // 设置餐厅编号方法，接收餐厅编号参数
    public void setRestaurantId(Long restaurantId) {
        // 将传入的餐厅编号赋值给当前对象的restaurantId字段
        this.restaurantId = restaurantId;
    }

    /**
     * 获取菜品分类
     * 功能概述：返回菜品的分类
     * @return {String} 返回菜品分类
     */
    // 获取菜品分类方法，返回菜品的分类
    public String getCategory() {
        // 返回菜品分类
        return category;
    }

    /**
     * 设置菜品分类
     * 功能概述：设置菜品的分类
     * @param {String} category - 菜品分类
     */
    // 设置菜品分类方法，接收菜品分类参数
    public void setCategory(String category) {
        // 将传入的菜品分类赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取菜品名称
     * 功能概述：返回菜品的名称
     * @return {String} 返回菜品名称
     */
    // 获取菜品名称方法，返回菜品的名称
    public String getName() {
        // 返回菜品名称
        return name;
    }

    /**
     * 设置菜品名称
     * 功能概述：设置菜品的名称
     * @param {String} name - 菜品名称
     */
    // 设置菜品名称方法，接收菜品名称参数
    public void setName(String name) {
        // 将传入的菜品名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取菜品描述
     * 功能概述：返回菜品的详细描述
     * @return {String} 返回菜品描述
     */
    // 获取菜品描述方法，返回菜品的详细描述
    public String getDescription() {
        // 返回菜品描述
        return description;
    }

    /**
     * 设置菜品描述
     * 功能概述：设置菜品的详细描述
     * @param {String} description - 菜品描述
     */
    // 设置菜品描述方法，接收菜品描述参数
    public void setDescription(String description) {
        // 将传入的菜品描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取菜品价格
     * 功能概述：返回菜品的价格
     * @return {BigDecimal} 返回菜品价格（元）
     */
    // 获取菜品价格方法，返回菜品的价格
    public BigDecimal getPrice() {
        // 返回菜品价格
        return price;
    }

    /**
     * 设置菜品价格
     * 功能概述：设置菜品的价格
     * @param {BigDecimal} price - 菜品价格（元）
     */
    // 设置菜品价格方法，接收菜品价格参数
    public void setPrice(BigDecimal price) {
        // 将传入的菜品价格赋值给当前对象的price字段
        this.price = price;
    }

    /**
     * 获取菜品图片
     * 功能概述：返回菜品图片的URL路径
     * @return {String} 返回菜品图片URL
     */
    // 获取菜品图片方法，返回菜品图片的URL路径
    public String getImage() {
        // 返回菜品图片URL
        return image;
    }

    /**
     * 设置菜品图片
     * 功能概述：设置菜品图片的URL路径
     * @param {String} image - 菜品图片URL
     */
    // 设置菜品图片方法，接收菜品图片URL参数
    public void setImage(String image) {
        // 将传入的菜品图片URL赋值给当前对象的image字段
        this.image = image;
    }

    /**
     * 获取是否招牌菜
     * 功能概述：返回是否为招牌菜
     * @return {Boolean} 返回是否招牌菜（true-是，false-否）
     */
    // 获取是否招牌菜方法，返回是否为招牌菜
    public Boolean getIsSignature() {
        // 返回是否招牌菜
        return isSignature;
    }

    /**
     * 设置是否招牌菜
     * 功能概述：设置是否为招牌菜
     * @param {Boolean} isSignature - 是否招牌菜（true-是，false-否）
     */
    // 设置是否招牌菜方法，接收是否招牌菜参数
    public void setIsSignature(Boolean isSignature) {
        // 将传入的是否招牌菜赋值给当前对象的isSignature字段
        this.isSignature = isSignature;
    }

    /**
     * 获取是否可用
     * 功能概述：返回菜品是否可用
     * @return {Boolean} 返回是否可用（true-可用，false-不可用）
     */
    // 获取是否可用方法，返回菜品是否可用
    public Boolean getIsAvailable() {
        // 返回是否可用
        return isAvailable;
    }

    /**
     * 设置是否可用
     * 功能概述：设置菜品是否可用
     * @param {Boolean} isAvailable - 是否可用（true-可用，false-不可用）
     */
    // 设置是否可用方法，接收是否可用参数
    public void setIsAvailable(Boolean isAvailable) {
        // 将传入的是否可用赋值给当前对象的isAvailable字段
        this.isAvailable = isAvailable;
    }

    /**
     * 获取创建时间
     * 功能概述：返回菜品的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回菜品的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置菜品的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回菜品的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回菜品的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置菜品的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将菜单对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回菜单对象的字符串表示
     */
    // 重写Object类的toString方法，返回菜单对象的字符串表示
    @Override
    // toString方法，将菜单对象转换为字符串格式
    public String toString() {
        // 返回菜单对象的字符串表示，包含所有字段的值
        return "RestaurantMenu{" +
                "id=" + id +                                    // 菜单编号
                ", restaurantId=" + restaurantId +              // 餐厅编号
                ", category='" + category + '\'' +              // 菜品分类
                ", name='" + name + '\'' +                      // 菜品名称
                ", description='" + description + '\'' +       // 菜品描述
                ", price=" + price +                            // 菜品价格
                ", image='" + image + '\'' +                     // 菜品图片
                ", isSignature=" + isSignature +                // 是否招牌菜
                ", isAvailable=" + isAvailable +                 // 是否可用
                ", createTime=" + createTime +                   // 创建时间
                ", updateTime=" + updateTime +                   // 更新时间
                '}';
    }
}
