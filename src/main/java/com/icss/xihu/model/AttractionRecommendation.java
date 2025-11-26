/**
 * 景点推荐实体类
 * 功能概述：封装景点推荐信息，对应数据库中的attraction_recommendation表，包含推荐的基本信息、推荐理由、评分等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 景点推荐实体类
 * 功能概述：封装景点推荐信息，对应数据库中的attraction_recommendation表，包含推荐的基本信息、推荐理由、评分等
 */
// 景点推荐实体类，封装景点推荐信息
public class AttractionRecommendation {
    // 推荐编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 景点编号，对应数据库中的attraction_id字段，关联景点表的id字段（外键）
    private Long attractionId;
    // 推荐类型，对应数据库中的recommend_type字段，推荐的类型（如"热门推荐"、"季节推荐"等）
    private String recommendType;
    // 推荐理由，对应数据库中的recommend_reason字段，推荐的理由
    private String recommendReason;
    // 推荐分数，对应数据库中的recommend_score字段，推荐的分数（0-100分）
    private Integer recommendScore;
    // 目标受众，对应数据库中的target_audience字段，推荐的目标受众（如"家庭游"、"情侣游"等）
    private String targetAudience;
    // 推荐季节，对应数据库中的season字段，推荐的季节（如"春季"、"夏季"等）
    private String season;
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
    // 无参构造函数，创建一个空的推荐对象
    public AttractionRecommendation() {}

    /**
     * 获取推荐编号
     * 功能概述：返回推荐的唯一标识ID
     * @return {Long} 返回推荐编号
     */
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
     * 获取景点编号
     * 功能概述：返回推荐所属的景点编号
     * @return {Long} 返回景点编号
     */
    // 获取景点编号方法，返回推荐所属的景点编号
    public Long getAttractionId() {
        // 返回景点编号
        return attractionId;
    }

    /**
     * 设置景点编号
     * 功能概述：设置推荐所属的景点编号
     * @param {Long} attractionId - 景点编号
     */
    // 设置景点编号方法，接收景点编号参数
    public void setAttractionId(Long attractionId) {
        // 将传入的景点编号赋值给当前对象的attractionId字段
        this.attractionId = attractionId;
    }

    /**
     * 获取推荐类型
     * 功能概述：返回推荐的类型
     * @return {String} 返回推荐类型
     */
    // 获取推荐类型方法，返回推荐的类型
    public String getRecommendType() {
        // 返回推荐类型
        return recommendType;
    }

    /**
     * 设置推荐类型
     * 功能概述：设置推荐的类型
     * @param {String} recommendType - 推荐类型
     */
    // 设置推荐类型方法，接收推荐类型参数
    public void setRecommendType(String recommendType) {
        // 将传入的推荐类型赋值给当前对象的recommendType字段
        this.recommendType = recommendType;
    }

    /**
     * 获取推荐理由
     * 功能概述：返回推荐的理由
     * @return {String} 返回推荐理由
     */
    // 获取推荐理由方法，返回推荐的理由
    public String getRecommendReason() {
        // 返回推荐理由
        return recommendReason;
    }

    /**
     * 设置推荐理由
     * 功能概述：设置推荐的理由
     * @param {String} recommendReason - 推荐理由
     */
    // 设置推荐理由方法，接收推荐理由参数
    public void setRecommendReason(String recommendReason) {
        // 将传入的推荐理由赋值给当前对象的recommendReason字段
        this.recommendReason = recommendReason;
    }

    /**
     * 获取推荐分数
     * 功能概述：返回推荐的分数
     * @return {Integer} 返回推荐分数（0-100分）
     */
    // 获取推荐分数方法，返回推荐的分数
    public Integer getRecommendScore() {
        // 返回推荐分数
        return recommendScore;
    }

    /**
     * 设置推荐分数
     * 功能概述：设置推荐的分数
     * @param {Integer} recommendScore - 推荐分数（0-100分）
     */
    // 设置推荐分数方法，接收推荐分数参数
    public void setRecommendScore(Integer recommendScore) {
        // 将传入的推荐分数赋值给当前对象的recommendScore字段
        this.recommendScore = recommendScore;
    }

    /**
     * 获取目标受众
     * 功能概述：返回推荐的目标受众
     * @return {String} 返回目标受众
     */
    // 获取目标受众方法，返回推荐的目标受众
    public String getTargetAudience() {
        // 返回目标受众
        return targetAudience;
    }

    /**
     * 设置目标受众
     * 功能概述：设置推荐的目标受众
     * @param {String} targetAudience - 目标受众
     */
    // 设置目标受众方法，接收目标受众参数
    public void setTargetAudience(String targetAudience) {
        // 将传入的目标受众赋值给当前对象的targetAudience字段
        this.targetAudience = targetAudience;
    }

    /**
     * 获取推荐季节
     * 功能概述：返回推荐的季节
     * @return {String} 返回推荐季节
     */
    // 获取推荐季节方法，返回推荐的季节
    public String getSeason() {
        // 返回推荐季节
        return season;
    }

    /**
     * 设置推荐季节
     * 功能概述：设置推荐的季节
     * @param {String} season - 推荐季节
     */
    // 设置推荐季节方法，接收推荐季节参数
    public void setSeason(String season) {
        // 将传入的推荐季节赋值给当前对象的season字段
        this.season = season;
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
}
