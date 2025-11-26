/**
 * 餐厅实体类
 * 功能概述：封装餐厅信息，对应数据库中的restaurant表，包含餐厅的基本信息、位置信息、评分等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 餐厅实体类
 * 功能概述：封装餐厅信息，对应数据库中的restaurant表，包含餐厅的基本信息、位置信息、评分等
 */
// 餐厅实体类，封装餐厅信息
public class Restaurant {
    // 餐厅编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 餐厅名称，对应数据库中的name字段，餐厅的名称
    private String name;
    // 餐厅分类，对应数据库中的category字段，餐厅的分类（如"中餐"、"西餐"等）
    private String category;
    // 餐厅电话，对应数据库中的tel字段，餐厅的联系电话
    private String tel;
    // 餐厅封面，对应数据库中的img字段，餐厅封面图片的URL路径
    private String img;
    // 餐厅图片，对应数据库中的images字段，餐厅图片的URL路径（多个图片用逗号分隔）
    private String images;
    // 餐厅描述，对应数据库中的description字段，餐厅的详细描述
    private String description;
    // 餐厅地址，对应数据库中的address字段，餐厅的详细地址
    private String address;
    // 省份，对应数据库中的province字段，餐厅所在的省份名称
    private String province;
    // 城市，对应数据库中的city字段，餐厅所在的城市名称
    private String city;
    // 区县，对应数据库中的district字段，餐厅所在的区县名称
    private String district;
    // 价格范围，对应数据库中的price_range字段，餐厅的价格范围（如"人均50-100元"）
    private String priceRange;
    // 餐厅评分，对应数据库中的rating字段，餐厅的用户评分（0-5分）
    private BigDecimal rating;
    // 营业时间，对应数据库中的opening_hours字段，餐厅的营业时间
    private String openingHours;
    // 餐厅特色，对应数据库中的features字段，餐厅的特色介绍
    private String features;
    // 餐厅状态，对应数据库中的status字段，餐厅的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，餐厅的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，餐厅的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的餐厅对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的餐厅对象
    public Restaurant() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个餐厅对象并设置基本属性
     * @param {String} name - 餐厅名称
     * @param {String} category - 餐厅分类
     * @param {String} tel - 餐厅电话
     * @param {String} img - 餐厅封面
     * @param {String} images - 餐厅图片
     * @param {String} description - 餐厅描述
     * @param {String} address - 餐厅地址
     * @param {String} province - 省份
     * @param {String} city - 城市
     * @param {String} district - 区县
     * @param {String} priceRange - 价格范围
     * @param {BigDecimal} rating - 餐厅评分
     * @param {String} openingHours - 营业时间
     * @param {String} features - 餐厅特色
     */
    // 有参构造函数，创建一个餐厅对象并设置基本属性
    public Restaurant(String name, String category, String tel, String img, String images, 
                     String description, String address, String province, String city, 
                     String district, String priceRange, BigDecimal rating, 
                     String openingHours, String features) {
        // 将传入的餐厅名称赋值给当前对象的name字段
        this.name = name;
        // 将传入的餐厅分类赋值给当前对象的category字段
        this.category = category;
        // 将传入的餐厅电话赋值给当前对象的tel字段
        this.tel = tel;
        // 将传入的餐厅封面赋值给当前对象的img字段
        this.img = img;
        // 将传入的餐厅图片赋值给当前对象的images字段
        this.images = images;
        // 将传入的餐厅描述赋值给当前对象的description字段
        this.description = description;
        // 将传入的餐厅地址赋值给当前对象的address字段
        this.address = address;
        // 将传入的省份赋值给当前对象的province字段
        this.province = province;
        // 将传入的城市赋值给当前对象的city字段
        this.city = city;
        // 将传入的区县赋值给当前对象的district字段
        this.district = district;
        // 将传入的价格范围赋值给当前对象的priceRange字段
        this.priceRange = priceRange;
        // 将传入的餐厅评分赋值给当前对象的rating字段
        this.rating = rating;
        // 将传入的营业时间赋值给当前对象的openingHours字段
        this.openingHours = openingHours;
        // 将传入的餐厅特色赋值给当前对象的features字段
        this.features = features;
        // 将状态设置为1（启用）
        this.status = 1;
    }

    /**
     * 获取餐厅编号
     * 功能概述：返回餐厅的唯一标识ID
     * @return {Long} 返回餐厅编号
     */
    // Getter和Setter方法
    // 获取餐厅编号方法，返回餐厅的唯一标识ID
    public Long getId() {
        // 返回餐厅编号
        return id;
    }

    /**
     * 设置餐厅编号
     * 功能概述：设置餐厅的唯一标识ID
     * @param {Long} id - 餐厅编号
     */
    // 设置餐厅编号方法，接收餐厅编号参数
    public void setId(Long id) {
        // 将传入的餐厅编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取餐厅名称
     * 功能概述：返回餐厅的名称
     * @return {String} 返回餐厅名称
     */
    // 获取餐厅名称方法，返回餐厅的名称
    public String getName() {
        // 返回餐厅名称
        return name;
    }

    /**
     * 设置餐厅名称
     * 功能概述：设置餐厅的名称
     * @param {String} name - 餐厅名称
     */
    // 设置餐厅名称方法，接收餐厅名称参数
    public void setName(String name) {
        // 将传入的餐厅名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取餐厅分类
     * 功能概述：返回餐厅的分类
     * @return {String} 返回餐厅分类
     */
    // 获取餐厅分类方法，返回餐厅的分类
    public String getCategory() {
        // 返回餐厅分类
        return category;
    }

    /**
     * 设置餐厅分类
     * 功能概述：设置餐厅的分类
     * @param {String} category - 餐厅分类
     */
    // 设置餐厅分类方法，接收餐厅分类参数
    public void setCategory(String category) {
        // 将传入的餐厅分类赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取餐厅电话
     * 功能概述：返回餐厅的联系电话
     * @return {String} 返回餐厅电话
     */
    // 获取餐厅电话方法，返回餐厅的联系电话
    public String getTel() {
        // 返回餐厅电话
        return tel;
    }

    /**
     * 设置餐厅电话
     * 功能概述：设置餐厅的联系电话
     * @param {String} tel - 餐厅电话
     */
    // 设置餐厅电话方法，接收餐厅电话参数
    public void setTel(String tel) {
        // 将传入的餐厅电话赋值给当前对象的tel字段
        this.tel = tel;
    }

    /**
     * 获取餐厅封面
     * 功能概述：返回餐厅封面图片的URL路径
     * @return {String} 返回餐厅封面URL
     */
    // 获取餐厅封面方法，返回餐厅封面图片的URL路径
    public String getImg() {
        // 返回餐厅封面URL
        return img;
    }

    /**
     * 设置餐厅封面
     * 功能概述：设置餐厅封面图片的URL路径
     * @param {String} img - 餐厅封面URL
     */
    // 设置餐厅封面方法，接收餐厅封面URL参数
    public void setImg(String img) {
        // 将传入的餐厅封面URL赋值给当前对象的img字段
        this.img = img;
    }

    /**
     * 获取餐厅图片
     * 功能概述：返回餐厅图片的URL路径（多个图片用逗号分隔）
     * @return {String} 返回餐厅图片URL
     */
    // 获取餐厅图片方法，返回餐厅图片的URL路径（多个图片用逗号分隔）
    public String getImages() {
        // 返回餐厅图片URL
        return images;
    }

    /**
     * 设置餐厅图片
     * 功能概述：设置餐厅图片的URL路径（多个图片用逗号分隔）
     * @param {String} images - 餐厅图片URL
     */
    // 设置餐厅图片方法，接收餐厅图片URL参数
    public void setImages(String images) {
        // 将传入的餐厅图片URL赋值给当前对象的images字段
        this.images = images;
    }

    /**
     * 获取餐厅描述
     * 功能概述：返回餐厅的详细描述
     * @return {String} 返回餐厅描述
     */
    // 获取餐厅描述方法，返回餐厅的详细描述
    public String getDescription() {
        // 返回餐厅描述
        return description;
    }

    /**
     * 设置餐厅描述
     * 功能概述：设置餐厅的详细描述
     * @param {String} description - 餐厅描述
     */
    // 设置餐厅描述方法，接收餐厅描述参数
    public void setDescription(String description) {
        // 将传入的餐厅描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取餐厅地址
     * 功能概述：返回餐厅的详细地址
     * @return {String} 返回餐厅地址
     */
    // 获取餐厅地址方法，返回餐厅的详细地址
    public String getAddress() {
        // 返回餐厅地址
        return address;
    }

    /**
     * 设置餐厅地址
     * 功能概述：设置餐厅的详细地址
     * @param {String} address - 餐厅地址
     */
    // 设置餐厅地址方法，接收餐厅地址参数
    public void setAddress(String address) {
        // 将传入的餐厅地址赋值给当前对象的address字段
        this.address = address;
    }

    /**
     * 获取省份
     * 功能概述：返回餐厅所在的省份名称
     * @return {String} 返回省份名称
     */
    // 获取省份方法，返回餐厅所在的省份名称
    public String getProvince() {
        // 返回省份名称
        return province;
    }

    /**
     * 设置省份
     * 功能概述：设置餐厅所在的省份名称
     * @param {String} province - 省份名称
     */
    // 设置省份方法，接收省份名称参数
    public void setProvince(String province) {
        // 将传入的省份名称赋值给当前对象的province字段
        this.province = province;
    }

    /**
     * 获取城市
     * 功能概述：返回餐厅所在的城市名称
     * @return {String} 返回城市名称
     */
    // 获取城市方法，返回餐厅所在的城市名称
    public String getCity() {
        // 返回城市名称
        return city;
    }

    /**
     * 设置城市
     * 功能概述：设置餐厅所在的城市名称
     * @param {String} city - 城市名称
     */
    // 设置城市方法，接收城市名称参数
    public void setCity(String city) {
        // 将传入的城市名称赋值给当前对象的city字段
        this.city = city;
    }

    /**
     * 获取区县
     * 功能概述：返回餐厅所在的区县名称
     * @return {String} 返回区县名称
     */
    // 获取区县方法，返回餐厅所在的区县名称
    public String getDistrict() {
        // 返回区县名称
        return district;
    }

    /**
     * 设置区县
     * 功能概述：设置餐厅所在的区县名称
     * @param {String} district - 区县名称
     */
    // 设置区县方法，接收区县名称参数
    public void setDistrict(String district) {
        // 将传入的区县名称赋值给当前对象的district字段
        this.district = district;
    }

    /**
     * 获取价格范围
     * 功能概述：返回餐厅的价格范围
     * @return {String} 返回价格范围
     */
    // 获取价格范围方法，返回餐厅的价格范围
    public String getPriceRange() {
        // 返回价格范围
        return priceRange;
    }

    /**
     * 设置价格范围
     * 功能概述：设置餐厅的价格范围
     * @param {String} priceRange - 价格范围
     */
    // 设置价格范围方法，接收价格范围参数
    public void setPriceRange(String priceRange) {
        // 将传入的价格范围赋值给当前对象的priceRange字段
        this.priceRange = priceRange;
    }

    /**
     * 获取餐厅评分
     * 功能概述：返回餐厅的用户评分
     * @return {BigDecimal} 返回餐厅评分（0-5分）
     */
    // 获取餐厅评分方法，返回餐厅的用户评分
    public BigDecimal getRating() {
        // 返回餐厅评分
        return rating;
    }

    /**
     * 设置餐厅评分
     * 功能概述：设置餐厅的用户评分
     * @param {BigDecimal} rating - 餐厅评分（0-5分）
     */
    // 设置餐厅评分方法，接收餐厅评分参数
    public void setRating(BigDecimal rating) {
        // 将传入的餐厅评分赋值给当前对象的rating字段
        this.rating = rating;
    }

    /**
     * 获取营业时间
     * 功能概述：返回餐厅的营业时间
     * @return {String} 返回营业时间
     */
    // 获取营业时间方法，返回餐厅的营业时间
    public String getOpeningHours() {
        // 返回营业时间
        return openingHours;
    }

    /**
     * 设置营业时间
     * 功能概述：设置餐厅的营业时间
     * @param {String} openingHours - 营业时间
     */
    // 设置营业时间方法，接收营业时间参数
    public void setOpeningHours(String openingHours) {
        // 将传入的营业时间赋值给当前对象的openingHours字段
        this.openingHours = openingHours;
    }

    /**
     * 获取餐厅特色
     * 功能概述：返回餐厅的特色介绍
     * @return {String} 返回餐厅特色
     */
    // 获取餐厅特色方法，返回餐厅的特色介绍
    public String getFeatures() {
        // 返回餐厅特色
        return features;
    }

    /**
     * 设置餐厅特色
     * 功能概述：设置餐厅的特色介绍
     * @param {String} features - 餐厅特色
     */
    // 设置餐厅特色方法，接收餐厅特色参数
    public void setFeatures(String features) {
        // 将传入的餐厅特色赋值给当前对象的features字段
        this.features = features;
    }

    /**
     * 获取餐厅状态
     * 功能概述：返回餐厅的状态
     * @return {Integer} 返回餐厅状态（0-禁用，1-启用）
     */
    // 获取餐厅状态方法，返回餐厅的状态
    public Integer getStatus() {
        // 返回餐厅状态
        return status;
    }

    /**
     * 设置餐厅状态
     * 功能概述：设置餐厅的状态
     * @param {Integer} status - 餐厅状态（0-禁用，1-启用）
     */
    // 设置餐厅状态方法，接收餐厅状态参数
    public void setStatus(Integer status) {
        // 将传入的餐厅状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回餐厅的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回餐厅的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置餐厅的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回餐厅的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回餐厅的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置餐厅的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将餐厅对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回餐厅对象的字符串表示
     */
    // 重写Object类的toString方法，返回餐厅对象的字符串表示
    @Override
    // toString方法，将餐厅对象转换为字符串格式
    public String toString() {
        // 返回餐厅对象的字符串表示，包含所有字段的值
        return "Restaurant{" +
                "id=" + id +                                    // 餐厅编号
                ", name='" + name + '\'' +                     // 餐厅名称
                ", category='" + category + '\'' +             // 餐厅分类
                ", tel='" + tel + '\'' +                       // 餐厅电话
                ", img='" + img + '\'' +                       // 餐厅封面
                ", images='" + images + '\'' +                 // 餐厅图片
                ", description='" + description + '\'' +       // 餐厅描述
                ", address='" + address + '\'' +               // 餐厅地址
                ", province='" + province + '\'' +             // 省份
                ", city='" + city + '\'' +                      // 城市
                ", district='" + district + '\'' +             // 区县
                ", priceRange='" + priceRange + '\'' +          // 价格范围
                ", rating=" + rating +                          // 餐厅评分
                ", openingHours='" + openingHours + '\'' +      // 营业时间
                ", features='" + features + '\'' +              // 餐厅特色
                ", status=" + status +                          // 餐厅状态
                ", createTime=" + createTime +                  // 创建时间
                ", updateTime=" + updateTime +                   // 更新时间
                '}';
    }
}
