/**
 * 旅游酒店实体类
 * 功能概述：封装旅游酒店信息，对应数据库中的travel_hotel表，包含酒店的基本信息、位置信息、价格等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 旅游酒店实体类
 * 功能概述：封装旅游酒店信息，对应数据库中的travel_hotel表，包含酒店的基本信息、位置信息、价格等
 */
// 旅游酒店实体类，封装旅游酒店信息
public class TravelHotel {
    // 酒店编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 目的地代码，对应数据库中的destination_code字段，关联旅游目的地表的code字段（外键）
    private String destinationCode;
    // 酒店名称，对应数据库中的name字段，酒店的名称
    private String name;
    // 星级，对应数据库中的star_level字段，酒店的星级（1-5星）
    private Integer starLevel;
    // 价格，对应数据库中的price字段，酒店的价格（元/晚）
    private BigDecimal price;
    // 地址，对应数据库中的address字段，酒店的详细地址
    private String address;
    // 电话，对应数据库中的phone字段，酒店的联系电话
    private String phone;
    // 设施，对应数据库中的facilities字段，酒店的设施介绍
    private String facilities;
    // 图片URL，对应数据库中的image_url字段，酒店图片的URL路径
    private String imageUrl;
    // 评分，对应数据库中的rating字段，酒店的用户评分（0-5分）
    private BigDecimal rating;
    // 描述，对应数据库中的description字段，酒店的详细描述
    private String description;
    // 经度，对应数据库中的longitude字段，酒店的经度坐标
    private BigDecimal longitude;
    // 纬度，对应数据库中的latitude字段，酒店的纬度坐标
    private BigDecimal latitude;
    // 排序顺序，对应数据库中的sort_order字段，酒店的排序顺序
    private Integer sortOrder;
    // 状态，对应数据库中的status字段，酒店的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，酒店的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，酒店的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的酒店对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的酒店对象
    public TravelHotel() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个酒店对象并设置基本属性
     * @param {String} destinationCode - 目的地代码
     * @param {String} name - 酒店名称
     * @param {Integer} starLevel - 星级
     * @param {BigDecimal} price - 价格
     */
    // 有参构造函数，创建一个酒店对象并设置基本属性
    public TravelHotel(String destinationCode, String name, Integer starLevel, BigDecimal price) {
        // 将传入的目的地代码赋值给当前对象的destinationCode字段
        this.destinationCode = destinationCode;
        // 将传入的酒店名称赋值给当前对象的name字段
        this.name = name;
        // 将传入的星级赋值给当前对象的starLevel字段
        this.starLevel = starLevel;
        // 将传入的价格赋值给当前对象的price字段
        this.price = price;
    }

    /**
     * 获取酒店编号
     * 功能概述：返回酒店的唯一标识ID
     * @return {Long} 返回酒店编号
     */
    // Getter和Setter方法
    // 获取酒店编号方法，返回酒店的唯一标识ID
    public Long getId() {
        // 返回酒店编号
        return id;
    }

    /**
     * 设置酒店编号
     * 功能概述：设置酒店的唯一标识ID
     * @param {Long} id - 酒店编号
     */
    // 设置酒店编号方法，接收酒店编号参数
    public void setId(Long id) {
        // 将传入的酒店编号赋值给当前对象的id字段
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
     * 获取酒店名称
     * 功能概述：返回酒店的名称
     * @return {String} 返回酒店名称
     */
    // 获取酒店名称方法，返回酒店的名称
    public String getName() {
        // 返回酒店名称
        return name;
    }

    /**
     * 设置酒店名称
     * 功能概述：设置酒店的名称
     * @param {String} name - 酒店名称
     */
    // 设置酒店名称方法，接收酒店名称参数
    public void setName(String name) {
        // 将传入的酒店名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取星级
     * 功能概述：返回酒店的星级
     * @return {Integer} 返回星级（1-5星）
     */
    // 获取星级方法，返回酒店的星级
    public Integer getStarLevel() {
        // 返回星级
        return starLevel;
    }

    /**
     * 设置星级
     * 功能概述：设置酒店的星级
     * @param {Integer} starLevel - 星级（1-5星）
     */
    // 设置星级方法，接收星级参数
    public void setStarLevel(Integer starLevel) {
        // 将传入的星级赋值给当前对象的starLevel字段
        this.starLevel = starLevel;
    }

    /**
     * 获取价格
     * 功能概述：返回酒店的价格
     * @return {BigDecimal} 返回价格（元/晚）
     */
    // 获取价格方法，返回酒店的价格
    public BigDecimal getPrice() {
        // 返回价格
        return price;
    }

    /**
     * 设置价格
     * 功能概述：设置酒店的价格
     * @param {BigDecimal} price - 价格（元/晚）
     */
    // 设置价格方法，接收价格参数
    public void setPrice(BigDecimal price) {
        // 将传入的价格赋值给当前对象的price字段
        this.price = price;
    }

    /**
     * 获取地址
     * 功能概述：返回酒店的详细地址
     * @return {String} 返回地址
     */
    // 获取地址方法，返回酒店的详细地址
    public String getAddress() {
        // 返回地址
        return address;
    }

    /**
     * 设置地址
     * 功能概述：设置酒店的详细地址
     * @param {String} address - 地址
     */
    // 设置地址方法，接收地址参数
    public void setAddress(String address) {
        // 将传入的地址赋值给当前对象的address字段
        this.address = address;
    }

    /**
     * 获取电话
     * 功能概述：返回酒店的联系电话
     * @return {String} 返回电话
     */
    // 获取电话方法，返回酒店的联系电话
    public String getPhone() {
        // 返回电话
        return phone;
    }

    /**
     * 设置电话
     * 功能概述：设置酒店的联系电话
     * @param {String} phone - 电话
     */
    // 设置电话方法，接收电话参数
    public void setPhone(String phone) {
        // 将传入的电话赋值给当前对象的phone字段
        this.phone = phone;
    }

    /**
     * 获取设施
     * 功能概述：返回酒店的设施介绍
     * @return {String} 返回设施
     */
    // 获取设施方法，返回酒店的设施介绍
    public String getFacilities() {
        // 返回设施
        return facilities;
    }

    /**
     * 设置设施
     * 功能概述：设置酒店的设施介绍
     * @param {String} facilities - 设施
     */
    // 设置设施方法，接收设施参数
    public void setFacilities(String facilities) {
        // 将传入的设施赋值给当前对象的facilities字段
        this.facilities = facilities;
    }

    /**
     * 获取图片URL
     * 功能概述：返回酒店图片的URL路径
     * @return {String} 返回图片URL
     */
    // 获取图片URL方法，返回酒店图片的URL路径
    public String getImageUrl() {
        // 返回图片URL
        return imageUrl;
    }

    /**
     * 设置图片URL
     * 功能概述：设置酒店图片的URL路径
     * @param {String} imageUrl - 图片URL
     */
    // 设置图片URL方法，接收图片URL参数
    public void setImageUrl(String imageUrl) {
        // 将传入的图片URL赋值给当前对象的imageUrl字段
        this.imageUrl = imageUrl;
    }

    /**
     * 获取评分
     * 功能概述：返回酒店的用户评分
     * @return {BigDecimal} 返回评分（0-5分）
     */
    // 获取评分方法，返回酒店的用户评分
    public BigDecimal getRating() {
        // 返回评分
        return rating;
    }

    /**
     * 设置评分
     * 功能概述：设置酒店的用户评分
     * @param {BigDecimal} rating - 评分（0-5分）
     */
    // 设置评分方法，接收评分参数
    public void setRating(BigDecimal rating) {
        // 将传入的评分赋值给当前对象的rating字段
        this.rating = rating;
    }

    /**
     * 获取描述
     * 功能概述：返回酒店的详细描述
     * @return {String} 返回描述
     */
    // 获取描述方法，返回酒店的详细描述
    public String getDescription() {
        // 返回描述
        return description;
    }

    /**
     * 设置描述
     * 功能概述：设置酒店的详细描述
     * @param {String} description - 描述
     */
    // 设置描述方法，接收描述参数
    public void setDescription(String description) {
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取经度
     * 功能概述：返回酒店的经度坐标
     * @return {BigDecimal} 返回经度
     */
    // 获取经度方法，返回酒店的经度坐标
    public BigDecimal getLongitude() {
        // 返回经度
        return longitude;
    }

    /**
     * 设置经度
     * 功能概述：设置酒店的经度坐标
     * @param {BigDecimal} longitude - 经度
     */
    // 设置经度方法，接收经度参数
    public void setLongitude(BigDecimal longitude) {
        // 将传入的经度赋值给当前对象的longitude字段
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     * 功能概述：返回酒店的纬度坐标
     * @return {BigDecimal} 返回纬度
     */
    // 获取纬度方法，返回酒店的纬度坐标
    public BigDecimal getLatitude() {
        // 返回纬度
        return latitude;
    }

    /**
     * 设置纬度
     * 功能概述：设置酒店的纬度坐标
     * @param {BigDecimal} latitude - 纬度
     */
    // 设置纬度方法，接收纬度参数
    public void setLatitude(BigDecimal latitude) {
        // 将传入的纬度赋值给当前对象的latitude字段
        this.latitude = latitude;
    }

    /**
     * 获取排序顺序
     * 功能概述：返回酒店的排序顺序
     * @return {Integer} 返回排序顺序
     */
    // 获取排序顺序方法，返回酒店的排序顺序
    public Integer getSortOrder() {
        // 返回排序顺序
        return sortOrder;
    }

    /**
     * 设置排序顺序
     * 功能概述：设置酒店的排序顺序
     * @param {Integer} sortOrder - 排序顺序
     */
    // 设置排序顺序方法，接收排序顺序参数
    public void setSortOrder(Integer sortOrder) {
        // 将传入的排序顺序赋值给当前对象的sortOrder字段
        this.sortOrder = sortOrder;
    }

    /**
     * 获取状态
     * 功能概述：返回酒店的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回酒店的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置酒店的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回酒店的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回酒店的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置酒店的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回酒店的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回酒店的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置酒店的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 功能概述：将酒店对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回酒店对象的字符串表示
     */
    // 重写Object类的toString方法，返回酒店对象的字符串表示
    @Override
    // toString方法，将酒店对象转换为字符串格式
    public String toString() {
        // 返回酒店对象的字符串表示，包含主要字段的值
        return "TravelHotel{" +
                "id=" + id +                                    // 酒店编号
                ", destinationCode='" + destinationCode + '\'' +  // 目的地代码
                ", name='" + name + '\'' +                      // 酒店名称
                ", starLevel=" + starLevel +                     // 星级
                ", price=" + price +                            // 价格
                ", rating=" + rating +                           // 评分
                '}';
    }
}
