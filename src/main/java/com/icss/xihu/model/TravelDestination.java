/**
 * 旅游目的地实体类
 * 功能概述：封装旅游目的地信息，对应数据库中的travel_destination表，包含目的地的基本信息、位置信息、层级关系等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 旅游目的地实体类
 * 功能概述：封装旅游目的地信息，对应数据库中的travel_destination表，包含目的地的基本信息、位置信息、层级关系等
 */
// 旅游目的地实体类，封装旅游目的地信息
public class TravelDestination {
    // 目的地编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 目的地名称，对应数据库中的name字段，目的地的名称
    private String name;
    // 目的地代码，对应数据库中的code字段，目的地的唯一代码
    private String code;
    // 目的地级别，对应数据库中的level字段，目的地的级别（1-省份，2-城市）
    private Integer level;
    // 父级代码，对应数据库中的parent_code字段，父级目的地的代码
    private String parentCode;
    // 经度，对应数据库中的longitude字段，目的地的经度坐标
    private BigDecimal longitude;
    // 纬度，对应数据库中的latitude字段，目的地的纬度坐标
    private BigDecimal latitude;
    // 描述，对应数据库中的description字段，目的地的描述信息
    private String description;
    // 图片URL，对应数据库中的image_url字段，目的地图片的URL路径
    private String imageUrl;
    // 状态，对应数据库中的status字段，目的地的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，目的地的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，目的地的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的目的地对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的目的地对象
    public TravelDestination() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个目的地对象并设置基本属性
     * @param {String} name - 目的地名称
     * @param {String} code - 目的地代码
     * @param {Integer} level - 目的地级别
     * @param {String} parentCode - 父级代码
     * @param {String} description - 描述
     */
    // 有参构造函数，创建一个目的地对象并设置基本属性
    public TravelDestination(String name, String code, Integer level, String parentCode, String description) {
        // 将传入的目的地名称赋值给当前对象的name字段
        this.name = name;
        // 将传入的目的地代码赋值给当前对象的code字段
        this.code = code;
        // 将传入的目的地级别赋值给当前对象的level字段
        this.level = level;
        // 将传入的父级代码赋值给当前对象的parentCode字段
        this.parentCode = parentCode;
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取目的地编号
     * 功能概述：返回目的地的唯一标识ID
     * @return {Long} 返回目的地编号
     */
    // Getter和Setter方法
    // 获取目的地编号方法，返回目的地的唯一标识ID
    public Long getId() {
        // 返回目的地编号
        return id;
    }

    /**
     * 设置目的地编号
     * 功能概述：设置目的地的唯一标识ID
     * @param {Long} id - 目的地编号
     */
    // 设置目的地编号方法，接收目的地编号参数
    public void setId(Long id) {
        // 将传入的目的地编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取目的地名称
     * 功能概述：返回目的地的名称
     * @return {String} 返回目的地名称
     */
    // 获取目的地名称方法，返回目的地的名称
    public String getName() {
        // 返回目的地名称
        return name;
    }

    /**
     * 设置目的地名称
     * 功能概述：设置目的地的名称
     * @param {String} name - 目的地名称
     */
    // 设置目的地名称方法，接收目的地名称参数
    public void setName(String name) {
        // 将传入的目的地名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取目的地代码
     * 功能概述：返回目的地的唯一代码
     * @return {String} 返回目的地代码
     */
    // 获取目的地代码方法，返回目的地的唯一代码
    public String getCode() {
        // 返回目的地代码
        return code;
    }

    /**
     * 设置目的地代码
     * 功能概述：设置目的地的唯一代码
     * @param {String} code - 目的地代码
     */
    // 设置目的地代码方法，接收目的地代码参数
    public void setCode(String code) {
        // 将传入的目的地代码赋值给当前对象的code字段
        this.code = code;
    }

    /**
     * 获取目的地级别
     * 功能概述：返回目的地的级别
     * @return {Integer} 返回目的地级别（1-省份，2-城市）
     */
    // 获取目的地级别方法，返回目的地的级别
    public Integer getLevel() {
        // 返回目的地级别
        return level;
    }

    /**
     * 设置目的地级别
     * 功能概述：设置目的地的级别
     * @param {Integer} level - 目的地级别（1-省份，2-城市）
     */
    // 设置目的地级别方法，接收目的地级别参数
    public void setLevel(Integer level) {
        // 将传入的目的地级别赋值给当前对象的level字段
        this.level = level;
    }

    /**
     * 获取父级代码
     * 功能概述：返回父级目的地的代码
     * @return {String} 返回父级代码
     */
    // 获取父级代码方法，返回父级目的地的代码
    public String getParentCode() {
        // 返回父级代码
        return parentCode;
    }

    /**
     * 设置父级代码
     * 功能概述：设置父级目的地的代码
     * @param {String} parentCode - 父级代码
     */
    // 设置父级代码方法，接收父级代码参数
    public void setParentCode(String parentCode) {
        // 将传入的父级代码赋值给当前对象的parentCode字段
        this.parentCode = parentCode;
    }

    /**
     * 获取经度
     * 功能概述：返回目的地的经度坐标
     * @return {BigDecimal} 返回经度
     */
    // 获取经度方法，返回目的地的经度坐标
    public BigDecimal getLongitude() {
        // 返回经度
        return longitude;
    }

    /**
     * 设置经度
     * 功能概述：设置目的地的经度坐标
     * @param {BigDecimal} longitude - 经度
     */
    // 设置经度方法，接收经度参数
    public void setLongitude(BigDecimal longitude) {
        // 将传入的经度赋值给当前对象的longitude字段
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     * 功能概述：返回目的地的纬度坐标
     * @return {BigDecimal} 返回纬度
     */
    // 获取纬度方法，返回目的地的纬度坐标
    public BigDecimal getLatitude() {
        // 返回纬度
        return latitude;
    }

    /**
     * 设置纬度
     * 功能概述：设置目的地的纬度坐标
     * @param {BigDecimal} latitude - 纬度
     */
    // 设置纬度方法，接收纬度参数
    public void setLatitude(BigDecimal latitude) {
        // 将传入的纬度赋值给当前对象的latitude字段
        this.latitude = latitude;
    }

    /**
     * 获取描述
     * 功能概述：返回目的地的描述信息
     * @return {String} 返回描述
     */
    // 获取描述方法，返回目的地的描述信息
    public String getDescription() {
        // 返回描述
        return description;
    }

    /**
     * 设置描述
     * 功能概述：设置目的地的描述信息
     * @param {String} description - 描述
     */
    // 设置描述方法，接收描述参数
    public void setDescription(String description) {
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取图片URL
     * 功能概述：返回目的地图片的URL路径
     * @return {String} 返回图片URL
     */
    // 获取图片URL方法，返回目的地图片的URL路径
    public String getImageUrl() {
        // 返回图片URL
        return imageUrl;
    }

    /**
     * 设置图片URL
     * 功能概述：设置目的地图片的URL路径
     * @param {String} imageUrl - 图片URL
     */
    // 设置图片URL方法，接收图片URL参数
    public void setImageUrl(String imageUrl) {
        // 将传入的图片URL赋值给当前对象的imageUrl字段
        this.imageUrl = imageUrl;
    }

    /**
     * 获取状态
     * 功能概述：返回目的地的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回目的地的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置目的地的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回目的地的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回目的地的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置目的地的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回目的地的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回目的地的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置目的地的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将目的地对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回目的地对象的字符串表示
     */
    // 重写Object类的toString方法，返回目的地对象的字符串表示
    @Override
    // toString方法，将目的地对象转换为字符串格式
    public String toString() {
        // 返回目的地对象的字符串表示，包含主要字段的值
        return "TravelDestination{" +
                "id=" + id +                                    // 目的地编号
                ", name='" + name + '\'' +                     // 目的地名称
                ", code='" + code + '\'' +                      // 目的地代码
                ", level=" + level +                            // 目的地级别
                ", parentCode='" + parentCode + '\'' +          // 父级代码
                ", description='" + description + '\'' +         // 描述
                '}';
    }
}
