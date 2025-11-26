/**
 * 酒店评价实体类
 * 功能概述：封装酒店评价信息，对应数据库中的hotel_review表，包含评价的基本信息、评分、内容等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 酒店评价实体类
 * 功能概述：封装酒店评价信息，对应数据库中的hotel_review表，包含评价的基本信息、评分、内容等
 */
// 酒店评价实体类，封装酒店评价信息
public class HotelReview {
    // 评价编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 酒店编号，对应数据库中的hotel_id字段，关联酒店表的id字段（外键）
    private Integer hotelId;
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
    public HotelReview() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个评价对象并设置基本属性
     * @param {Integer} hotelId - 酒店编号
     * @param {Integer} userId - 用户编号
     * @param {String} username - 用户名
     * @param {String} userAvatar - 用户头像
     * @param {BigDecimal} rating - 评分
     * @param {String} content - 评价内容
     * @param {String} images - 评价图片
     */
    // 有参构造函数，创建一个评价对象并设置基本属性
    public HotelReview(Integer hotelId, Integer userId, String username, String userAvatar, 
                      BigDecimal rating, String content, String images) {
        this.hotelId = hotelId;
        this.userId = userId;
        this.username = username;
        this.userAvatar = userAvatar;
        this.rating = rating;
        this.content = content;
        this.images = images;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

