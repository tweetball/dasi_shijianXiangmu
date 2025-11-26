/**
 * 旅游推荐实体类
 * 功能概述：封装旅游推荐信息，对应数据库中的travel_recommendation表，包含推荐的基本信息、分类、评分等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 旅游推荐实体类
 * 功能概述：封装旅游推荐信息，对应数据库中的travel_recommendation表，包含推荐的基本信息、分类、评分等
 */
// 旅游推荐实体类，封装旅游推荐信息
public class TravelRecommendation {
    // 推荐编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 目的地代码，对应数据库中的destination_code字段，关联旅游目的地表的code字段（外键）
    private String destinationCode;
    // 推荐标题，对应数据库中的title字段，推荐的标题
    private String title;
    // 推荐内容，对应数据库中的content字段，推荐的详细内容
    private String content;
    // 推荐分类，对应数据库中的category字段，推荐的分类（景点、美食、购物、交通等）
    private String category;
    // 图片URL，对应数据库中的image_url字段，推荐图片的URL路径
    private String imageUrl;
    // 评分，对应数据库中的rating字段，推荐的评分（0-5分）
    private BigDecimal rating;
    // 价格范围，对应数据库中的price_range字段，推荐的价格范围（如"人均50-100元"）
    private String priceRange;
    // 地址，对应数据库中的address字段，推荐的地址
    private String address;
    // 电话，对应数据库中的phone字段，推荐的联系电话
    private String phone;
    // 开放时间，对应数据库中的opening_hours字段，推荐的开放时间
    private String openingHours;
    // 标签，对应数据库中的tags字段，推荐的标签（多个标签用逗号分隔）
    private String tags;
    // 排序顺序，对应数据库中的sort_order字段，推荐的排序顺序
    private Integer sortOrder;
    // 状态，对应数据库中的status字段，推荐的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，推荐的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，推荐的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的推荐对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的推荐对象
    public TravelRecommendation() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个推荐对象并设置基本属性
     * @param {String} destinationCode - 目的地代码
     * @param {String} title - 推荐标题
     * @param {String} content - 推荐内容
     * @param {String} category - 推荐分类
     */
    // 有参构造函数，创建一个推荐对象并设置基本属性
    public TravelRecommendation(String destinationCode, String title, String content, String category) {
        // 将传入的目的地代码赋值给当前对象的destinationCode字段
        this.destinationCode = destinationCode;
        // 将传入的推荐标题赋值给当前对象的title字段
        this.title = title;
        // 将传入的推荐内容赋值给当前对象的content字段
        this.content = content;
        // 将传入的推荐分类赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取推荐编号
     * 功能概述：返回推荐的唯一标识ID
     * @return {Long} 返回推荐编号
     */
    // Getter和Setter方法
    // 获取推荐编号方法，返回推荐的唯一标识ID
    public Long getId() {
        // 返回推荐编号
        return id;
    }

    /**
     * 设置推荐编号
     * 功能概述：设置推荐的唯一标识ID
     * @param {Long} id - 推荐编号
     */
    // 设置推荐编号方法，接收推荐编号参数
    public void setId(Long id) {
        // 将传入的推荐编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取目的地代码
     * 功能概述：返回关联的目的地代码
     * @return {String} 返回目的地代码
     */
    // 获取目的地代码方法，返回关联的目的地代码
    public String getDestinationCode() {
        // 返回目的地代码
        return destinationCode;
    }

    /**
     * 设置目的地代码
     * 功能概述：设置关联的目的地代码
     * @param {String} destinationCode - 目的地代码
     */
    // 设置目的地代码方法，接收目的地代码参数
    public void setDestinationCode(String destinationCode) {
        // 将传入的目的地代码赋值给当前对象的destinationCode字段
        this.destinationCode = destinationCode;
    }

    /**
     * 获取推荐标题
     * 功能概述：返回推荐的标题
     * @return {String} 返回推荐标题
     */
    // 获取推荐标题方法，返回推荐的标题
    public String getTitle() {
        // 返回推荐标题
        return title;
    }

    /**
     * 设置推荐标题
     * 功能概述：设置推荐的标题
     * @param {String} title - 推荐标题
     */
    // 设置推荐标题方法，接收推荐标题参数
    public void setTitle(String title) {
        // 将传入的推荐标题赋值给当前对象的title字段
        this.title = title;
    }

    /**
     * 获取推荐内容
     * 功能概述：返回推荐的详细内容
     * @return {String} 返回推荐内容
     */
    // 获取推荐内容方法，返回推荐的详细内容
    public String getContent() {
        // 返回推荐内容
        return content;
    }

    /**
     * 设置推荐内容
     * 功能概述：设置推荐的详细内容
     * @param {String} content - 推荐内容
     */
    // 设置推荐内容方法，接收推荐内容参数
    public void setContent(String content) {
        // 将传入的推荐内容赋值给当前对象的content字段
        this.content = content;
    }

    /**
     * 获取推荐分类
     * 功能概述：返回推荐的分类
     * @return {String} 返回推荐分类
     */
    // 获取推荐分类方法，返回推荐的分类
    public String getCategory() {
        // 返回推荐分类
        return category;
    }

    /**
     * 设置推荐分类
     * 功能概述：设置推荐的分类
     * @param {String} category - 推荐分类
     */
    // 设置推荐分类方法，接收推荐分类参数
    public void setCategory(String category) {
        // 将传入的推荐分类赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取图片URL
     * 功能概述：返回推荐图片的URL路径
     * @return {String} 返回图片URL
     */
    // 获取图片URL方法，返回推荐图片的URL路径
    public String getImageUrl() {
        // 返回图片URL
        return imageUrl;
    }

    /**
     * 设置图片URL
     * 功能概述：设置推荐图片的URL路径
     * @param {String} imageUrl - 图片URL
     */
    // 设置图片URL方法，接收图片URL参数
    public void setImageUrl(String imageUrl) {
        // 将传入的图片URL赋值给当前对象的imageUrl字段
        this.imageUrl = imageUrl;
    }

    /**
     * 获取评分
     * 功能概述：返回推荐的评分
     * @return {BigDecimal} 返回评分（0-5分）
     */
    // 获取评分方法，返回推荐的评分
    public BigDecimal getRating() {
        // 返回评分
        return rating;
    }

    /**
     * 设置评分
     * 功能概述：设置推荐的评分
     * @param {BigDecimal} rating - 评分（0-5分）
     */
    // 设置评分方法，接收评分参数
    public void setRating(BigDecimal rating) {
        // 将传入的评分赋值给当前对象的rating字段
        this.rating = rating;
    }

    /**
     * 获取价格范围
     * 功能概述：返回推荐的价格范围
     * @return {String} 返回价格范围
     */
    // 获取价格范围方法，返回推荐的价格范围
    public String getPriceRange() {
        // 返回价格范围
        return priceRange;
    }

    /**
     * 设置价格范围
     * 功能概述：设置推荐的价格范围
     * @param {String} priceRange - 价格范围
     */
    // 设置价格范围方法，接收价格范围参数
    public void setPriceRange(String priceRange) {
        // 将传入的价格范围赋值给当前对象的priceRange字段
        this.priceRange = priceRange;
    }

    /**
     * 获取地址
     * 功能概述：返回推荐的地址
     * @return {String} 返回地址
     */
    // 获取地址方法，返回推荐的地址
    public String getAddress() {
        // 返回地址
        return address;
    }

    /**
     * 设置地址
     * 功能概述：设置推荐的地址
     * @param {String} address - 地址
     */
    // 设置地址方法，接收地址参数
    public void setAddress(String address) {
        // 将传入的地址赋值给当前对象的address字段
        this.address = address;
    }

    /**
     * 获取电话
     * 功能概述：返回推荐的联系电话
     * @return {String} 返回电话
     */
    // 获取电话方法，返回推荐的联系电话
    public String getPhone() {
        // 返回电话
        return phone;
    }

    /**
     * 设置电话
     * 功能概述：设置推荐的联系电话
     * @param {String} phone - 电话
     */
    // 设置电话方法，接收电话参数
    public void setPhone(String phone) {
        // 将传入的电话赋值给当前对象的phone字段
        this.phone = phone;
    }

    /**
     * 获取开放时间
     * 功能概述：返回推荐的开放时间
     * @return {String} 返回开放时间
     */
    // 获取开放时间方法，返回推荐的开放时间
    public String getOpeningHours() {
        // 返回开放时间
        return openingHours;
    }

    /**
     * 设置开放时间
     * 功能概述：设置推荐的开放时间
     * @param {String} openingHours - 开放时间
     */
    // 设置开放时间方法，接收开放时间参数
    public void setOpeningHours(String openingHours) {
        // 将传入的开放时间赋值给当前对象的openingHours字段
        this.openingHours = openingHours;
    }

    /**
     * 获取标签
     * 功能概述：返回推荐的标签（多个标签用逗号分隔）
     * @return {String} 返回标签
     */
    // 获取标签方法，返回推荐的标签（多个标签用逗号分隔）
    public String getTags() {
        // 返回标签
        return tags;
    }

    /**
     * 设置标签
     * 功能概述：设置推荐的标签（多个标签用逗号分隔）
     * @param {String} tags - 标签
     */
    // 设置标签方法，接收标签参数
    public void setTags(String tags) {
        // 将传入的标签赋值给当前对象的tags字段
        this.tags = tags;
    }

    /**
     * 获取排序顺序
     * 功能概述：返回推荐的排序顺序
     * @return {Integer} 返回排序顺序
     */
    // 获取排序顺序方法，返回推荐的排序顺序
    public Integer getSortOrder() {
        // 返回排序顺序
        return sortOrder;
    }

    /**
     * 设置排序顺序
     * 功能概述：设置推荐的排序顺序
     * @param {Integer} sortOrder - 排序顺序
     */
    // 设置排序顺序方法，接收排序顺序参数
    public void setSortOrder(Integer sortOrder) {
        // 将传入的排序顺序赋值给当前对象的sortOrder字段
        this.sortOrder = sortOrder;
    }

    /**
     * 获取状态
     * 功能概述：返回推荐的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回推荐的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置推荐的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回推荐的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回推荐的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置推荐的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回推荐的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回推荐的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置推荐的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将推荐对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回推荐对象的字符串表示
     */
    // 重写Object类的toString方法，返回推荐对象的字符串表示
    @Override
    // toString方法，将推荐对象转换为字符串格式
    public String toString() {
        // 返回推荐对象的字符串表示，包含主要字段的值
        return "TravelRecommendation{" +
                "id=" + id +                                    // 推荐编号
                ", destinationCode='" + destinationCode + '\'' +  // 目的地代码
                ", title='" + title + '\'' +                     // 推荐标题
                ", category='" + category + '\'' +               // 推荐分类
                ", rating=" + rating +                           // 评分
                '}';
    }
}
