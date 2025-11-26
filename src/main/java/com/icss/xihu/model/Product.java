/**
 * 商品实体类
 * 功能概述：封装商品信息，对应数据库中的product表，包含商品的基本信息、价格、库存、详情等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 商品实体类
 * 功能概述：封装商品信息，对应数据库中的product表，包含商品的基本信息、价格、库存、详情等
 */
// 商品实体类，封装商品信息
public class Product {
    // 商品编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 商品名称，对应数据库中的name字段，商品的名称
    private String name;
    // 商品价格，对应数据库中的price字段，商品的价格（元）
    private BigDecimal price;
    // 商品封面，对应数据库中的cover字段，商品封面图片的URL路径
    private String cover;
    // 商品图片，对应数据库中的images字段，商品图片的URL路径（多个图片用逗号分隔）
    private String images;
    // 商品库存，对应数据库中的stock字段，商品的库存数量
    private Integer stock;
    // 商品标签，对应数据库中的tags字段，商品的标签（多个标签用逗号分隔）
    private String tags;
    // 商品分类，对应数据库中的category字段，商品的分类
    private String category;
    // 商品状态，对应数据库中的status字段，商品的状态（0-下架，1-上架）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，商品的创建时间
    private LocalDateTime createTime;
    // 已售数量，对应数据库中的sold_count字段，商品的已售数量
    private Integer soldCount;
    // 商品品牌，对应数据库中的brand字段，商品的品牌
    private String brand;
    // 详细描述，对应数据库中的detailed_description字段，商品的详细描述
    private String detailedDescription;
    // 商品规格，对应数据库中的specifications字段，商品的规格信息
    private String specifications;
    // 商品特色，对应数据库中的features字段，商品的特色介绍
    private String features;
    // 使用指南，对应数据库中的usage_guide字段，商品的使用指南
    private String usageGuide;
    // 保修信息，对应数据库中的warranty_info字段，商品的保修信息
    private String warrantyInfo;
    // 配送信息，对应数据库中的shipping_info字段，商品的配送信息
    private String shippingInfo;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的商品对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的商品对象
    public Product() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个商品对象并设置基本属性
     * @param {String} name - 商品名称
     * @param {BigDecimal} price - 商品价格
     * @param {String} cover - 商品封面
     * @param {String} category - 商品分类
     */
    // 有参构造函数，创建一个商品对象并设置基本属性
    public Product(String name, BigDecimal price, String cover, String category) {
        // 将传入的商品名称赋值给当前对象的name字段
        this.name = name;
        // 将传入的商品价格赋值给当前对象的price字段
        this.price = price;
        // 将传入的商品封面赋值给当前对象的cover字段
        this.cover = cover;
        // 将传入的商品分类赋值给当前对象的category字段
        this.category = category;
        // 将库存设置为999（默认值）
        this.stock = 999;
        // 将状态设置为1（上架）
        this.status = 1;
        // 将已售数量设置为0（默认值）
        this.soldCount = 0;
    }

    /**
     * 获取商品编号
     * 功能概述：返回商品的唯一标识ID
     * @return {Long} 返回商品编号
     */
    // Getter和Setter方法
    // 获取商品编号方法，返回商品的唯一标识ID
    public Long getId() {
        // 返回商品编号
        return id;
    }

    /**
     * 设置商品编号
     * 功能概述：设置商品的唯一标识ID
     * @param {Long} id - 商品编号
     */
    // 设置商品编号方法，接收商品编号参数
    public void setId(Long id) {
        // 将传入的商品编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取商品名称
     * 功能概述：返回商品的名称
     * @return {String} 返回商品名称
     */
    // 获取商品名称方法，返回商品的名称
    public String getName() {
        // 返回商品名称
        return name;
    }

    /**
     * 设置商品名称
     * 功能概述：设置商品的名称
     * @param {String} name - 商品名称
     */
    // 设置商品名称方法，接收商品名称参数
    public void setName(String name) {
        // 将传入的商品名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取商品价格
     * 功能概述：返回商品的价格
     * @return {BigDecimal} 返回商品价格（元）
     */
    // 获取商品价格方法，返回商品的价格
    public BigDecimal getPrice() {
        // 返回商品价格
        return price;
    }

    /**
     * 设置商品价格
     * 功能概述：设置商品的价格
     * @param {BigDecimal} price - 商品价格（元）
     */
    // 设置商品价格方法，接收商品价格参数
    public void setPrice(BigDecimal price) {
        // 将传入的商品价格赋值给当前对象的price字段
        this.price = price;
    }

    /**
     * 获取商品封面
     * 功能概述：返回商品封面图片的URL路径
     * @return {String} 返回商品封面URL
     */
    // 获取商品封面方法，返回商品封面图片的URL路径
    public String getCover() {
        // 返回商品封面URL
        return cover;
    }

    /**
     * 设置商品封面
     * 功能概述：设置商品封面图片的URL路径
     * @param {String} cover - 商品封面URL
     */
    // 设置商品封面方法，接收商品封面URL参数
    public void setCover(String cover) {
        // 将传入的商品封面URL赋值给当前对象的cover字段
        this.cover = cover;
    }

    /**
     * 获取商品图片
     * 功能概述：返回商品图片的URL路径（多个图片用逗号分隔）
     * @return {String} 返回商品图片URL
     */
    // 获取商品图片方法，返回商品图片的URL路径（多个图片用逗号分隔）
    public String getImages() {
        // 返回商品图片URL
        return images;
    }

    /**
     * 设置商品图片
     * 功能概述：设置商品图片的URL路径（多个图片用逗号分隔）
     * @param {String} images - 商品图片URL
     */
    // 设置商品图片方法，接收商品图片URL参数
    public void setImages(String images) {
        // 将传入的商品图片URL赋值给当前对象的images字段
        this.images = images;
    }

    /**
     * 获取商品库存
     * 功能概述：返回商品的库存数量
     * @return {Integer} 返回库存数量
     */
    // 获取商品库存方法，返回商品的库存数量
    public Integer getStock() {
        // 返回库存数量
        return stock;
    }

    /**
     * 设置商品库存
     * 功能概述：设置商品的库存数量
     * @param {Integer} stock - 库存数量
     */
    // 设置商品库存方法，接收库存数量参数
    public void setStock(Integer stock) {
        // 将传入的库存数量赋值给当前对象的stock字段
        this.stock = stock;
    }

    /**
     * 获取商品标签
     * 功能概述：返回商品的标签（多个标签用逗号分隔）
     * @return {String} 返回商品标签
     */
    // 获取商品标签方法，返回商品的标签（多个标签用逗号分隔）
    public String getTags() {
        // 返回商品标签
        return tags;
    }

    /**
     * 设置商品标签
     * 功能概述：设置商品的标签（多个标签用逗号分隔）
     * @param {String} tags - 商品标签
     */
    // 设置商品标签方法，接收商品标签参数
    public void setTags(String tags) {
        // 将传入的商品标签赋值给当前对象的tags字段
        this.tags = tags;
    }

    /**
     * 获取商品分类
     * 功能概述：返回商品的分类
     * @return {String} 返回商品分类
     */
    // 获取商品分类方法，返回商品的分类
    public String getCategory() {
        // 返回商品分类
        return category;
    }

    /**
     * 设置商品分类
     * 功能概述：设置商品的分类
     * @param {String} category - 商品分类
     */
    // 设置商品分类方法，接收商品分类参数
    public void setCategory(String category) {
        // 将传入的商品分类赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取商品状态
     * 功能概述：返回商品的状态
     * @return {Integer} 返回商品状态（0-下架，1-上架）
     */
    // 获取商品状态方法，返回商品的状态
    public Integer getStatus() {
        // 返回商品状态
        return status;
    }

    /**
     * 设置商品状态
     * 功能概述：设置商品的状态
     * @param {Integer} status - 商品状态（0-下架，1-上架）
     */
    // 设置商品状态方法，接收商品状态参数
    public void setStatus(Integer status) {
        // 将传入的商品状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回商品的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回商品的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置商品的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取已售数量
     * 功能概述：返回商品的已售数量
     * @return {Integer} 返回已售数量
     */
    // 获取已售数量方法，返回商品的已售数量
    public Integer getSoldCount() {
        // 返回已售数量
        return soldCount;
    }

    /**
     * 设置已售数量
     * 功能概述：设置商品的已售数量
     * @param {Integer} soldCount - 已售数量
     */
    // 设置已售数量方法，接收已售数量参数
    public void setSoldCount(Integer soldCount) {
        // 将传入的已售数量赋值给当前对象的soldCount字段
        this.soldCount = soldCount;
    }

    /**
     * 获取商品品牌
     * 功能概述：返回商品的品牌
     * @return {String} 返回商品品牌
     */
    // 获取商品品牌方法，返回商品的品牌
    public String getBrand() {
        // 返回商品品牌
        return brand;
    }

    /**
     * 设置商品品牌
     * 功能概述：设置商品的品牌
     * @param {String} brand - 商品品牌
     */
    // 设置商品品牌方法，接收商品品牌参数
    public void setBrand(String brand) {
        // 将传入的商品品牌赋值给当前对象的brand字段
        this.brand = brand;
    }

    /**
     * 获取详细描述
     * 功能概述：返回商品的详细描述
     * @return {String} 返回详细描述
     */
    // 获取详细描述方法，返回商品的详细描述
    public String getDetailedDescription() {
        // 返回详细描述
        return detailedDescription;
    }

    /**
     * 设置详细描述
     * 功能概述：设置商品的详细描述
     * @param {String} detailedDescription - 详细描述
     */
    // 设置详细描述方法，接收详细描述参数
    public void setDetailedDescription(String detailedDescription) {
        // 将传入的详细描述赋值给当前对象的detailedDescription字段
        this.detailedDescription = detailedDescription;
    }

    /**
     * 获取商品规格
     * 功能概述：返回商品的规格信息
     * @return {String} 返回商品规格
     */
    // 获取商品规格方法，返回商品的规格信息
    public String getSpecifications() {
        // 返回商品规格
        return specifications;
    }

    /**
     * 设置商品规格
     * 功能概述：设置商品的规格信息
     * @param {String} specifications - 商品规格
     */
    // 设置商品规格方法，接收商品规格参数
    public void setSpecifications(String specifications) {
        // 将传入的商品规格赋值给当前对象的specifications字段
        this.specifications = specifications;
    }

    /**
     * 获取商品特色
     * 功能概述：返回商品的特色介绍
     * @return {String} 返回商品特色
     */
    // 获取商品特色方法，返回商品的特色介绍
    public String getFeatures() {
        // 返回商品特色
        return features;
    }

    /**
     * 设置商品特色
     * 功能概述：设置商品的特色介绍
     * @param {String} features - 商品特色
     */
    // 设置商品特色方法，接收商品特色参数
    public void setFeatures(String features) {
        // 将传入的商品特色赋值给当前对象的features字段
        this.features = features;
    }

    /**
     * 获取使用指南
     * 功能概述：返回商品的使用指南
     * @return {String} 返回使用指南
     */
    // 获取使用指南方法，返回商品的使用指南
    public String getUsageGuide() {
        // 返回使用指南
        return usageGuide;
    }

    /**
     * 设置使用指南
     * 功能概述：设置商品的使用指南
     * @param {String} usageGuide - 使用指南
     */
    // 设置使用指南方法，接收使用指南参数
    public void setUsageGuide(String usageGuide) {
        // 将传入的使用指南赋值给当前对象的usageGuide字段
        this.usageGuide = usageGuide;
    }

    /**
     * 获取保修信息
     * 功能概述：返回商品的保修信息
     * @return {String} 返回保修信息
     */
    // 获取保修信息方法，返回商品的保修信息
    public String getWarrantyInfo() {
        // 返回保修信息
        return warrantyInfo;
    }

    /**
     * 设置保修信息
     * 功能概述：设置商品的保修信息
     * @param {String} warrantyInfo - 保修信息
     */
    // 设置保修信息方法，接收保修信息参数
    public void setWarrantyInfo(String warrantyInfo) {
        // 将传入的保修信息赋值给当前对象的warrantyInfo字段
        this.warrantyInfo = warrantyInfo;
    }

    /**
     * 获取配送信息
     * 功能概述：返回商品的配送信息
     * @return {String} 返回配送信息
     */
    // 获取配送信息方法，返回商品的配送信息
    public String getShippingInfo() {
        // 返回配送信息
        return shippingInfo;
    }

    /**
     * 设置配送信息
     * 功能概述：设置商品的配送信息
     * @param {String} shippingInfo - 配送信息
     */
    // 设置配送信息方法，接收配送信息参数
    public void setShippingInfo(String shippingInfo) {
        // 将传入的配送信息赋值给当前对象的shippingInfo字段
        this.shippingInfo = shippingInfo;
    }

    /**
     * 重写toString方法
     * 功能概述：将商品对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回商品对象的字符串表示
     */
    // 重写Object类的toString方法，返回商品对象的字符串表示
    @Override
    // toString方法，将商品对象转换为字符串格式
    public String toString() {
        // 返回商品对象的字符串表示，包含所有字段的值
        return "Product{" +
                "id=" + id +                                    // 商品编号
                ", name='" + name + '\'' +                     // 商品名称
                ", price=" + price +                           // 商品价格
                ", cover='" + cover + '\'' +                  // 商品封面
                ", images='" + images + '\'' +                 // 商品图片
                ", stock=" + stock +                           // 商品库存
                ", tags='" + tags + '\'' +                     // 商品标签
                ", category='" + category + '\'' +             // 商品分类
                ", status=" + status +                         // 商品状态
                ", createTime=" + createTime +                  // 创建时间
                ", soldCount=" + soldCount +                   // 已售数量
                ", brand='" + brand + '\'' +                   // 商品品牌
                ", detailedDescription='" + detailedDescription + '\'' +  // 详细描述
                ", specifications='" + specifications + '\'' +  // 商品规格
                ", features='" + features + '\'' +             // 商品特色
                ", usageGuide='" + usageGuide + '\'' +         // 使用指南
                ", warrantyInfo='" + warrantyInfo + '\'' +     // 保修信息
                ", shippingInfo='" + shippingInfo + '\'' +     // 配送信息
                '}';
    }
}
