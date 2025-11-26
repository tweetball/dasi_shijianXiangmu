/**
 * 餐厅评价实体类
 * 功能概述：封装餐厅评价信息，对应数据库中的restaurant_review表，包含评价的基本信息、评分、内容等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 餐厅评价实体类
 * 功能概述：封装餐厅评价信息，对应数据库中的restaurant_review表，包含评价的基本信息、评分、内容等
 */
// 餐厅评价实体类，封装餐厅评价信息
public class RestaurantReview {
    // 评价编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 餐厅编号，对应数据库中的restaurant_id字段，关联餐厅表的id字段（外键）
    private Long restaurantId;
    // 用户编号，对应数据库中的user_id字段，关联用户表的id字段（外键）
    private Integer userId;
    // 用户名，对应数据库中的username字段，评价用户的用户名
    private String username;
    // 用户头像，对应数据库中的user_avatar字段，评价用户的头像URL路径
    private String userAvatar;
    // 评分，对应数据库中的rating字段，评价的评分（0-5分）
    private BigDecimal rating;
    // 评价内容，对应数据库中的content字段，评价的详细内容
    private String content;
    // 评价图片，对应数据库中的images字段，评价的图片URL路径（多个图片用逗号分隔）
    private String images;
    // 创建时间，对应数据库中的create_time字段，评价的创建时间
    private LocalDateTime createTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的评价对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的评价对象
    public RestaurantReview() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个评价对象并设置基本属性
     * @param {Long} restaurantId - 餐厅编号
     * @param {Integer} userId - 用户编号
     * @param {String} username - 用户名
     * @param {String} userAvatar - 用户头像
     * @param {BigDecimal} rating - 评分
     * @param {String} content - 评价内容
     * @param {String} images - 评价图片
     */
    // 有参构造函数，创建一个评价对象并设置基本属性
    public RestaurantReview(Long restaurantId, Integer userId, String username, 
                           String userAvatar, BigDecimal rating, String content, String images) {
        // 将传入的餐厅编号赋值给当前对象的restaurantId字段
        this.restaurantId = restaurantId;
        // 将传入的用户编号赋值给当前对象的userId字段
        this.userId = userId;
        // 将传入的用户名赋值给当前对象的username字段
        this.username = username;
        // 将传入的用户头像赋值给当前对象的userAvatar字段
        this.userAvatar = userAvatar;
        // 将传入的评分赋值给当前对象的rating字段
        this.rating = rating;
        // 将传入的评价内容赋值给当前对象的content字段
        this.content = content;
        // 将传入的评价图片赋值给当前对象的images字段
        this.images = images;
    }

    /**
     * 获取评价编号
     * 功能概述：返回评价的唯一标识ID
     * @return {Long} 返回评价编号
     */
    // Getter和Setter方法
    // 获取评价编号方法，返回评价的唯一标识ID
    public Long getId() {
        // 返回评价编号
        return id;
    }

    /**
     * 设置评价编号
     * 功能概述：设置评价的唯一标识ID
     * @param {Long} id - 评价编号
     */
    // 设置评价编号方法，接收评价编号参数
    public void setId(Long id) {
        // 将传入的评价编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取餐厅编号
     * 功能概述：返回评价所属的餐厅编号
     * @return {Long} 返回餐厅编号
     */
    // 获取餐厅编号方法，返回评价所属的餐厅编号
    public Long getRestaurantId() {
        // 返回餐厅编号
        return restaurantId;
    }

    /**
     * 设置餐厅编号
     * 功能概述：设置评价所属的餐厅编号
     * @param {Long} restaurantId - 餐厅编号
     */
    // 设置餐厅编号方法，接收餐厅编号参数
    public void setRestaurantId(Long restaurantId) {
        // 将传入的餐厅编号赋值给当前对象的restaurantId字段
        this.restaurantId = restaurantId;
    }

    /**
     * 获取用户编号
     * 功能概述：返回评价所属的用户编号
     * @return {Integer} 返回用户编号
     */
    // 获取用户编号方法，返回评价所属的用户编号
    public Integer getUserId() {
        // 返回用户编号
        return userId;
    }

    /**
     * 设置用户编号
     * 功能概述：设置评价所属的用户编号
     * @param {Integer} userId - 用户编号
     */
    // 设置用户编号方法，接收用户编号参数
    public void setUserId(Integer userId) {
        // 将传入的用户编号赋值给当前对象的userId字段
        this.userId = userId;
    }

    /**
     * 获取用户名
     * 功能概述：返回评价用户的用户名
     * @return {String} 返回用户名
     */
    // 获取用户名方法，返回评价用户的用户名
    public String getUsername() {
        // 返回用户名
        return username;
    }

    /**
     * 设置用户名
     * 功能概述：设置评价用户的用户名
     * @param {String} username - 用户名
     */
    // 设置用户名方法，接收用户名参数
    public void setUsername(String username) {
        // 将传入的用户名赋值给当前对象的username字段
        this.username = username;
    }

    /**
     * 获取用户头像
     * 功能概述：返回评价用户的头像URL路径
     * @return {String} 返回用户头像URL
     */
    // 获取用户头像方法，返回评价用户的头像URL路径
    public String getUserAvatar() {
        // 返回用户头像URL
        return userAvatar;
    }

    /**
     * 设置用户头像
     * 功能概述：设置评价用户的头像URL路径
     * @param {String} userAvatar - 用户头像URL
     */
    // 设置用户头像方法，接收用户头像URL参数
    public void setUserAvatar(String userAvatar) {
        // 将传入的用户头像URL赋值给当前对象的userAvatar字段
        this.userAvatar = userAvatar;
    }

    /**
     * 获取评分
     * 功能概述：返回评价的评分
     * @return {BigDecimal} 返回评分（0-5分）
     */
    // 获取评分方法，返回评价的评分
    public BigDecimal getRating() {
        // 返回评分
        return rating;
    }

    /**
     * 设置评分
     * 功能概述：设置评价的评分
     * @param {BigDecimal} rating - 评分（0-5分）
     */
    // 设置评分方法，接收评分参数
    public void setRating(BigDecimal rating) {
        // 将传入的评分赋值给当前对象的rating字段
        this.rating = rating;
    }

    /**
     * 获取评价内容
     * 功能概述：返回评价的详细内容
     * @return {String} 返回评价内容
     */
    // 获取评价内容方法，返回评价的详细内容
    public String getContent() {
        // 返回评价内容
        return content;
    }

    /**
     * 设置评价内容
     * 功能概述：设置评价的详细内容
     * @param {String} content - 评价内容
     */
    // 设置评价内容方法，接收评价内容参数
    public void setContent(String content) {
        // 将传入的评价内容赋值给当前对象的content字段
        this.content = content;
    }

    /**
     * 获取评价图片
     * 功能概述：返回评价的图片URL路径（多个图片用逗号分隔）
     * @return {String} 返回评价图片URL
     */
    // 获取评价图片方法，返回评价的图片URL路径（多个图片用逗号分隔）
    public String getImages() {
        // 返回评价图片URL
        return images;
    }

    /**
     * 设置评价图片
     * 功能概述：设置评价的图片URL路径（多个图片用逗号分隔）
     * @param {String} images - 评价图片URL
     */
    // 设置评价图片方法，接收评价图片URL参数
    public void setImages(String images) {
        // 将传入的评价图片URL赋值给当前对象的images字段
        this.images = images;
    }

    /**
     * 获取创建时间
     * 功能概述：返回评价的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回评价的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置评价的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将评价对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回评价对象的字符串表示
     */
    // 重写Object类的toString方法，返回评价对象的字符串表示
    @Override
    // toString方法，将评价对象转换为字符串格式
    public String toString() {
        // 返回评价对象的字符串表示，包含所有字段的值
        return "RestaurantReview{" +
                "id=" + id +                                    // 评价编号
                ", restaurantId=" + restaurantId +              // 餐厅编号
                ", userId=" + userId +                          // 用户编号
                ", username='" + username + '\'' +              // 用户名
                ", userAvatar='" + userAvatar + '\'' +          // 用户头像
                ", rating=" + rating +                           // 评分
                ", content='" + content + '\'' +                 // 评价内容
                ", images='" + images + '\'' +                   // 评价图片
                ", createTime=" + createTime +                   // 创建时间
                '}';
    }
}
