/**
 * 景点实体类
 * 功能概述：封装景点信息，对应数据库中的attraction表，包含景点的基本信息、位置信息、评分、价格等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;
// 导入List集合接口
import java.util.List;

/**
 * 景点实体类
 * 功能概述：封装景点信息，对应数据库中的attraction表，包含景点的基本信息、位置信息、评分、价格等
 */
// 景点实体类，封装景点信息
public class Attraction {
    // 景点编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 景点名称，对应数据库中的name字段，景点的名称
    private String name;
    // 分类编号，对应数据库中的category_id字段，关联景点分类表
    private Long categoryId;
    // 省份，对应数据库中的province字段，景点所在的省份名称
    private String province;
    // 城市，对应数据库中的city字段，景点所在的城市名称
    private String city;
    // 区县，对应数据库中的district字段，景点所在的区县名称
    private String district;
    // 完整地址，对应数据库中的full_address字段，景点的完整地址
    private String fullAddress;
    // 经度，对应数据库中的longitude字段，景点的经度坐标
    private BigDecimal longitude;
    // 纬度，对应数据库中的latitude字段，景点的纬度坐标
    private BigDecimal latitude;
    // 评分，对应数据库中的rating字段，景点的用户评分（0-5分）
    private BigDecimal rating;
    // 价格范围，对应数据库中的price_range字段，景点的门票价格范围
    private String priceRange;
    // 开放时间，对应数据库中的opening_hours字段，景点的开放时间
    private String openingHours;
    // 联系电话，对应数据库中的phone字段，景点的联系电话
    private String phone;
    // 官方网站，对应数据库中的website字段，景点的官方网站URL
    private String website;
    // 描述，对应数据库中的description字段，景点的详细描述
    private String description;
    // 特色，对应数据库中的features字段，景点的特色介绍
    private String features;
    // 旅游提示，对应数据库中的tips字段，景点的旅游提示信息
    private String tips;
    // 最佳季节，对应数据库中的best_season字段，景点的最佳游览季节
    private String bestSeason;
    // 游览时长，对应数据库中的visit_duration字段，建议的游览时长
    private String visitDuration;
    // 交通信息，对应数据库中的traffic_info字段，景点的交通信息
    private String trafficInfo;
    // 门票信息，对应数据库中的ticket_info字段，景点的门票信息
    private String ticketInfo;
    // 浏览次数，对应数据库中的view_count字段，景点的浏览次数
    private Integer viewCount;
    // 点赞次数，对应数据库中的like_count字段，景点的点赞次数
    private Integer likeCount;
    // 推荐分数，对应数据库中的recommend_score字段，景点的推荐分数
    private Integer recommendScore;
    // 排序顺序，对应数据库中的sort_order字段，景点的排序顺序
    private Integer sortOrder;
    // 状态，对应数据库中的status字段，景点的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，景点的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，景点的最后更新时间
    private LocalDateTime updateTime;

    // 关联属性
    // 景点分类，关联景点分类表，一个景点属于一个分类
    private AttractionCategory category;
    // 景点图片列表，关联景点图片表，一个景点包含多张图片（一对多关系）
    private List<AttractionImage> images;
    // 景点标签列表，关联景点标签表，一个景点包含多个标签（一对多关系）
    private List<AttractionTag> tags;
    // 景点推荐列表，关联景点推荐表，一个景点包含多个推荐（一对多关系）
    private List<AttractionRecommendation> recommendations;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的景点对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的景点对象
    public Attraction() {}

    /**
     * 获取景点编号
     * 功能概述：返回景点的唯一标识ID
     * @return {Long} 返回景点编号
     */
    // Getter和Setter方法
    // 获取景点编号方法，返回景点的唯一标识ID
    public Long getId() {
        // 返回景点编号
        return id;
    }

    /**
     * 设置景点编号
     * 功能概述：设置景点的唯一标识ID
     * @param {Long} id - 景点编号
     */
    // 设置景点编号方法，接收景点编号参数
    public void setId(Long id) {
        // 将传入的景点编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取景点名称
     * 功能概述：返回景点的名称
     * @return {String} 返回景点名称
     */
    // 获取景点名称方法，返回景点的名称
    public String getName() {
        // 返回景点名称
        return name;
    }

    /**
     * 设置景点名称
     * 功能概述：设置景点的名称
     * @param {String} name - 景点名称
     */
    // 设置景点名称方法，接收景点名称参数
    public void setName(String name) {
        // 将传入的景点名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取分类编号
     * 功能概述：返回景点所属的分类编号
     * @return {Long} 返回分类编号
     */
    // 获取分类编号方法，返回景点所属的分类编号
    public Long getCategoryId() {
        // 返回分类编号
        return categoryId;
    }

    /**
     * 设置分类编号
     * 功能概述：设置景点所属的分类编号
     * @param {Long} categoryId - 分类编号
     */
    // 设置分类编号方法，接收分类编号参数
    public void setCategoryId(Long categoryId) {
        // 将传入的分类编号赋值给当前对象的categoryId字段
        this.categoryId = categoryId;
    }

    /**
     * 获取省份
     * 功能概述：返回景点所在的省份名称
     * @return {String} 返回省份名称
     */
    // 获取省份方法，返回景点所在的省份名称
    public String getProvince() {
        // 返回省份名称
        return province;
    }

    /**
     * 设置省份
     * 功能概述：设置景点所在的省份名称
     * @param {String} province - 省份名称
     */
    // 设置省份方法，接收省份名称参数
    public void setProvince(String province) {
        // 将传入的省份名称赋值给当前对象的province字段
        this.province = province;
    }

    /**
     * 获取城市
     * 功能概述：返回景点所在的城市名称
     * @return {String} 返回城市名称
     */
    // 获取城市方法，返回景点所在的城市名称
    public String getCity() {
        // 返回城市名称
        return city;
    }

    /**
     * 设置城市
     * 功能概述：设置景点所在的城市名称
     * @param {String} city - 城市名称
     */
    // 设置城市方法，接收城市名称参数
    public void setCity(String city) {
        // 将传入的城市名称赋值给当前对象的city字段
        this.city = city;
    }

    /**
     * 获取区县
     * 功能概述：返回景点所在的区县名称
     * @return {String} 返回区县名称
     */
    // 获取区县方法，返回景点所在的区县名称
    public String getDistrict() {
        // 返回区县名称
        return district;
    }

    /**
     * 设置区县
     * 功能概述：设置景点所在的区县名称
     * @param {String} district - 区县名称
     */
    // 设置区县方法，接收区县名称参数
    public void setDistrict(String district) {
        // 将传入的区县名称赋值给当前对象的district字段
        this.district = district;
    }

    /**
     * 获取完整地址
     * 功能概述：返回景点的完整地址
     * @return {String} 返回完整地址
     */
    // 获取完整地址方法，返回景点的完整地址
    public String getFullAddress() {
        // 返回完整地址
        return fullAddress;
    }

    /**
     * 设置完整地址
     * 功能概述：设置景点的完整地址
     * @param {String} fullAddress - 完整地址
     */
    // 设置完整地址方法，接收完整地址参数
    public void setFullAddress(String fullAddress) {
        // 将传入的完整地址赋值给当前对象的fullAddress字段
        this.fullAddress = fullAddress;
    }

    /**
     * 获取经度
     * 功能概述：返回景点的经度坐标
     * @return {BigDecimal} 返回经度
     */
    // 获取经度方法，返回景点的经度坐标
    public BigDecimal getLongitude() {
        // 返回经度
        return longitude;
    }

    /**
     * 设置经度
     * 功能概述：设置景点的经度坐标
     * @param {BigDecimal} longitude - 经度
     */
    // 设置经度方法，接收经度参数
    public void setLongitude(BigDecimal longitude) {
        // 将传入的经度赋值给当前对象的longitude字段
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     * 功能概述：返回景点的纬度坐标
     * @return {BigDecimal} 返回纬度
     */
    // 获取纬度方法，返回景点的纬度坐标
    public BigDecimal getLatitude() {
        // 返回纬度
        return latitude;
    }

    /**
     * 设置纬度
     * 功能概述：设置景点的纬度坐标
     * @param {BigDecimal} latitude - 纬度
     */
    // 设置纬度方法，接收纬度参数
    public void setLatitude(BigDecimal latitude) {
        // 将传入的纬度赋值给当前对象的latitude字段
        this.latitude = latitude;
    }

    /**
     * 获取评分
     * 功能概述：返回景点的用户评分
     * @return {BigDecimal} 返回评分（0-5分）
     */
    // 获取评分方法，返回景点的用户评分
    public BigDecimal getRating() {
        // 返回评分
        return rating;
    }

    /**
     * 设置评分
     * 功能概述：设置景点的用户评分
     * @param {BigDecimal} rating - 评分（0-5分）
     */
    // 设置评分方法，接收评分参数
    public void setRating(BigDecimal rating) {
        // 将传入的评分赋值给当前对象的rating字段
        this.rating = rating;
    }

    /**
     * 获取价格范围
     * 功能概述：返回景点的门票价格范围
     * @return {String} 返回价格范围
     */
    // 获取价格范围方法，返回景点的门票价格范围
    public String getPriceRange() {
        // 返回价格范围
        return priceRange;
    }

    /**
     * 设置价格范围
     * 功能概述：设置景点的门票价格范围
     * @param {String} priceRange - 价格范围
     */
    // 设置价格范围方法，接收价格范围参数
    public void setPriceRange(String priceRange) {
        // 将传入的价格范围赋值给当前对象的priceRange字段
        this.priceRange = priceRange;
    }

    /**
     * 获取开放时间
     * 功能概述：返回景点的开放时间
     * @return {String} 返回开放时间
     */
    // 获取开放时间方法，返回景点的开放时间
    public String getOpeningHours() {
        // 返回开放时间
        return openingHours;
    }

    /**
     * 设置开放时间
     * 功能概述：设置景点的开放时间
     * @param {String} openingHours - 开放时间
     */
    // 设置开放时间方法，接收开放时间参数
    public void setOpeningHours(String openingHours) {
        // 将传入的开放时间赋值给当前对象的openingHours字段
        this.openingHours = openingHours;
    }

    /**
     * 获取联系电话
     * 功能概述：返回景点的联系电话
     * @return {String} 返回联系电话
     */
    // 获取联系电话方法，返回景点的联系电话
    public String getPhone() {
        // 返回联系电话
        return phone;
    }

    /**
     * 设置联系电话
     * 功能概述：设置景点的联系电话
     * @param {String} phone - 联系电话
     */
    // 设置联系电话方法，接收联系电话参数
    public void setPhone(String phone) {
        // 将传入的联系电话赋值给当前对象的phone字段
        this.phone = phone;
    }

    /**
     * 获取官方网站
     * 功能概述：返回景点的官方网站URL
     * @return {String} 返回官方网站URL
     */
    // 获取官方网站方法，返回景点的官方网站URL
    public String getWebsite() {
        // 返回官方网站URL
        return website;
    }

    /**
     * 设置官方网站
     * 功能概述：设置景点的官方网站URL
     * @param {String} website - 官方网站URL
     */
    // 设置官方网站方法，接收官方网站URL参数
    public void setWebsite(String website) {
        // 将传入的官方网站URL赋值给当前对象的website字段
        this.website = website;
    }

    /**
     * 获取描述
     * 功能概述：返回景点的详细描述
     * @return {String} 返回描述
     */
    // 获取描述方法，返回景点的详细描述
    public String getDescription() {
        // 返回描述
        return description;
    }

    /**
     * 设置描述
     * 功能概述：设置景点的详细描述
     * @param {String} description - 描述
     */
    // 设置描述方法，接收描述参数
    public void setDescription(String description) {
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取特色
     * 功能概述：返回景点的特色介绍
     * @return {String} 返回特色
     */
    // 获取特色方法，返回景点的特色介绍
    public String getFeatures() {
        // 返回特色
        return features;
    }

    /**
     * 设置特色
     * 功能概述：设置景点的特色介绍
     * @param {String} features - 特色
     */
    // 设置特色方法，接收特色参数
    public void setFeatures(String features) {
        // 将传入的特色赋值给当前对象的features字段
        this.features = features;
    }

    /**
     * 获取旅游提示
     * 功能概述：返回景点的旅游提示信息
     * @return {String} 返回旅游提示
     */
    // 获取旅游提示方法，返回景点的旅游提示信息
    public String getTips() {
        // 返回旅游提示
        return tips;
    }

    /**
     * 设置旅游提示
     * 功能概述：设置景点的旅游提示信息
     * @param {String} tips - 旅游提示
     */
    // 设置旅游提示方法，接收旅游提示参数
    public void setTips(String tips) {
        // 将传入的旅游提示赋值给当前对象的tips字段
        this.tips = tips;
    }

    /**
     * 获取最佳季节
     * 功能概述：返回景点的最佳游览季节
     * @return {String} 返回最佳季节
     */
    // 获取最佳季节方法，返回景点的最佳游览季节
    public String getBestSeason() {
        // 返回最佳季节
        return bestSeason;
    }

    /**
     * 设置最佳季节
     * 功能概述：设置景点的最佳游览季节
     * @param {String} bestSeason - 最佳季节
     */
    // 设置最佳季节方法，接收最佳季节参数
    public void setBestSeason(String bestSeason) {
        // 将传入的最佳季节赋值给当前对象的bestSeason字段
        this.bestSeason = bestSeason;
    }

    /**
     * 获取游览时长
     * 功能概述：返回建议的游览时长
     * @return {String} 返回游览时长
     */
    // 获取游览时长方法，返回建议的游览时长
    public String getVisitDuration() {
        // 返回游览时长
        return visitDuration;
    }

    /**
     * 设置游览时长
     * 功能概述：设置建议的游览时长
     * @param {String} visitDuration - 游览时长
     */
    // 设置游览时长方法，接收游览时长参数
    public void setVisitDuration(String visitDuration) {
        // 将传入的游览时长赋值给当前对象的visitDuration字段
        this.visitDuration = visitDuration;
    }

    /**
     * 获取交通信息
     * 功能概述：返回景点的交通信息
     * @return {String} 返回交通信息
     */
    // 获取交通信息方法，返回景点的交通信息
    public String getTrafficInfo() {
        // 返回交通信息
        return trafficInfo;
    }

    /**
     * 设置交通信息
     * 功能概述：设置景点的交通信息
     * @param {String} trafficInfo - 交通信息
     */
    // 设置交通信息方法，接收交通信息参数
    public void setTrafficInfo(String trafficInfo) {
        // 将传入的交通信息赋值给当前对象的trafficInfo字段
        this.trafficInfo = trafficInfo;
    }

    /**
     * 获取门票信息
     * 功能概述：返回景点的门票信息
     * @return {String} 返回门票信息
     */
    // 获取门票信息方法，返回景点的门票信息
    public String getTicketInfo() {
        // 返回门票信息
        return ticketInfo;
    }

    /**
     * 设置门票信息
     * 功能概述：设置景点的门票信息
     * @param {String} ticketInfo - 门票信息
     */
    // 设置门票信息方法，接收门票信息参数
    public void setTicketInfo(String ticketInfo) {
        // 将传入的门票信息赋值给当前对象的ticketInfo字段
        this.ticketInfo = ticketInfo;
    }

    /**
     * 获取浏览次数
     * 功能概述：返回景点的浏览次数
     * @return {Integer} 返回浏览次数
     */
    // 获取浏览次数方法，返回景点的浏览次数
    public Integer getViewCount() {
        // 返回浏览次数
        return viewCount;
    }

    /**
     * 设置浏览次数
     * 功能概述：设置景点的浏览次数
     * @param {Integer} viewCount - 浏览次数
     */
    // 设置浏览次数方法，接收浏览次数参数
    public void setViewCount(Integer viewCount) {
        // 将传入的浏览次数赋值给当前对象的viewCount字段
        this.viewCount = viewCount;
    }

    /**
     * 获取点赞次数
     * 功能概述：返回景点的点赞次数
     * @return {Integer} 返回点赞次数
     */
    // 获取点赞次数方法，返回景点的点赞次数
    public Integer getLikeCount() {
        // 返回点赞次数
        return likeCount;
    }

    /**
     * 设置点赞次数
     * 功能概述：设置景点的点赞次数
     * @param {Integer} likeCount - 点赞次数
     */
    // 设置点赞次数方法，接收点赞次数参数
    public void setLikeCount(Integer likeCount) {
        // 将传入的点赞次数赋值给当前对象的likeCount字段
        this.likeCount = likeCount;
    }

    /**
     * 获取推荐分数
     * 功能概述：返回景点的推荐分数
     * @return {Integer} 返回推荐分数
     */
    // 获取推荐分数方法，返回景点的推荐分数
    public Integer getRecommendScore() {
        // 返回推荐分数
        return recommendScore;
    }

    /**
     * 设置推荐分数
     * 功能概述：设置景点的推荐分数
     * @param {Integer} recommendScore - 推荐分数
     */
    // 设置推荐分数方法，接收推荐分数参数
    public void setRecommendScore(Integer recommendScore) {
        // 将传入的推荐分数赋值给当前对象的recommendScore字段
        this.recommendScore = recommendScore;
    }

    /**
     * 获取排序顺序
     * 功能概述：返回景点的排序顺序
     * @return {Integer} 返回排序顺序
     */
    // 获取排序顺序方法，返回景点的排序顺序
    public Integer getSortOrder() {
        // 返回排序顺序
        return sortOrder;
    }

    /**
     * 设置排序顺序
     * 功能概述：设置景点的排序顺序
     * @param {Integer} sortOrder - 排序顺序
     */
    // 设置排序顺序方法，接收排序顺序参数
    public void setSortOrder(Integer sortOrder) {
        // 将传入的排序顺序赋值给当前对象的sortOrder字段
        this.sortOrder = sortOrder;
    }

    /**
     * 获取状态
     * 功能概述：返回景点的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回景点的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置景点的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回景点的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回景点的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置景点的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回景点的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回景点的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置景点的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    // 关联属性的getter/setter
    /**
     * 获取景点分类
     * 功能概述：返回景点所属的分类对象
     * @return {AttractionCategory} 返回分类对象
     */
    // 获取景点分类方法，返回景点所属的分类对象
    public AttractionCategory getCategory() {
        // 返回分类对象
        return category;
    }

    /**
     * 设置景点分类
     * 功能概述：设置景点所属的分类对象
     * @param {AttractionCategory} category - 分类对象
     */
    // 设置景点分类方法，接收分类对象参数
    public void setCategory(AttractionCategory category) {
        // 将传入的分类对象赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取景点图片列表
     * 功能概述：返回景点的所有图片列表
     * @return {List<AttractionImage>} 返回图片列表
     */
    // 获取景点图片列表方法，返回景点的所有图片列表
    public List<AttractionImage> getImages() {
        // 返回图片列表
        return images;
    }

    /**
     * 设置景点图片列表
     * 功能概述：设置景点的所有图片列表
     * @param {List<AttractionImage>} images - 图片列表
     */
    // 设置景点图片列表方法，接收图片列表参数
    public void setImages(List<AttractionImage> images) {
        // 将传入的图片列表赋值给当前对象的images字段
        this.images = images;
    }

    /**
     * 获取景点标签列表
     * 功能概述：返回景点的所有标签列表
     * @return {List<AttractionTag>} 返回标签列表
     */
    // 获取景点标签列表方法，返回景点的所有标签列表
    public List<AttractionTag> getTags() {
        // 返回标签列表
        return tags;
    }

    /**
     * 设置景点标签列表
     * 功能概述：设置景点的所有标签列表
     * @param {List<AttractionTag>} tags - 标签列表
     */
    // 设置景点标签列表方法，接收标签列表参数
    public void setTags(List<AttractionTag> tags) {
        // 将传入的标签列表赋值给当前对象的tags字段
        this.tags = tags;
    }

    /**
     * 获取景点推荐列表
     * 功能概述：返回景点的所有推荐列表
     * @return {List<AttractionRecommendation>} 返回推荐列表
     */
    // 获取景点推荐列表方法，返回景点的所有推荐列表
    public List<AttractionRecommendation> getRecommendations() {
        // 返回推荐列表
        return recommendations;
    }

    /**
     * 设置景点推荐列表
     * 功能概述：设置景点的所有推荐列表
     * @param {List<AttractionRecommendation>} recommendations - 推荐列表
     */
    // 设置景点推荐列表方法，接收推荐列表参数
    public void setRecommendations(List<AttractionRecommendation> recommendations) {
        // 将传入的推荐列表赋值给当前对象的recommendations字段
        this.recommendations = recommendations;
    }

    /**
     * 重写toString方法
     * 功能概述：将景点对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回景点对象的字符串表示
     */
    // 重写Object类的toString方法，返回景点对象的字符串表示
    @Override
    // toString方法，将景点对象转换为字符串格式
    public String toString() {
        // 返回景点对象的字符串表示，包含主要字段的值
        return "Attraction{" +
                "id=" + id +                                    // 景点编号
                ", name='" + name + '\'' +                     // 景点名称
                ", province='" + province + '\'' +              // 省份
                ", city='" + city + '\'' +                      // 城市
                ", rating=" + rating +                          // 评分
                ", recommendScore=" + recommendScore +          // 推荐分数
                '}';
    }
}
