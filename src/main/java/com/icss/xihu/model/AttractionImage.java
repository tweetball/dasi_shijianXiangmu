/**
 * 景点图片实体类
 * 功能概述：封装景点图片信息，对应数据库中的attraction_image表，包含图片的基本信息、是否封面等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 景点图片实体类
 * 功能概述：封装景点图片信息，对应数据库中的attraction_image表，包含图片的基本信息、是否封面等
 */
// 景点图片实体类，封装景点图片信息
public class AttractionImage {
    // 图片编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 景点编号，对应数据库中的attraction_id字段，关联景点表的id字段（外键）
    private Long attractionId;
    // 图片URL，对应数据库中的image_url字段，图片的URL路径
    private String imageUrl;
    // 图片标题，对应数据库中的image_title字段，图片的标题
    private String imageTitle;
    // 图片描述，对应数据库中的image_description字段，图片的描述信息
    private String imageDescription;
    // 是否封面，对应数据库中的is_cover字段，是否为封面图片（0-否，1-是）
    private Integer isCover;
    // 排序顺序，对应数据库中的sort_order字段，图片的排序顺序
    private Integer sortOrder;
    // 状态，对应数据库中的status字段，图片的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，图片的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，图片的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的图片对象
     */
    // 无参构造函数，创建一个空的图片对象
    public AttractionImage() {}

    /**
     * 获取图片编号
     * 功能概述：返回图片的唯一标识ID
     * @return {Long} 返回图片编号
     */
    // 获取图片编号方法，返回图片的唯一标识ID
    public Long getId() {
        // 返回图片编号
        return id;
    }

    /**
     * 设置图片编号
     * 功能概述：设置图片的唯一标识ID
     * @param {Long} id - 图片编号
     */
    // 设置图片编号方法，接收图片编号参数
    public void setId(Long id) {
        // 将传入的图片编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取景点编号
     * 功能概述：返回图片所属的景点编号
     * @return {Long} 返回景点编号
     */
    // 获取景点编号方法，返回图片所属的景点编号
    public Long getAttractionId() {
        // 返回景点编号
        return attractionId;
    }

    /**
     * 设置景点编号
     * 功能概述：设置图片所属的景点编号
     * @param {Long} attractionId - 景点编号
     */
    // 设置景点编号方法，接收景点编号参数
    public void setAttractionId(Long attractionId) {
        // 将传入的景点编号赋值给当前对象的attractionId字段
        this.attractionId = attractionId;
    }

    /**
     * 获取图片URL
     * 功能概述：返回图片的URL路径
     * @return {String} 返回图片URL
     */
    // 获取图片URL方法，返回图片的URL路径
    public String getImageUrl() {
        // 返回图片URL
        return imageUrl;
    }

    /**
     * 设置图片URL
     * 功能概述：设置图片的URL路径
     * @param {String} imageUrl - 图片URL
     */
    // 设置图片URL方法，接收图片URL参数
    public void setImageUrl(String imageUrl) {
        // 将传入的图片URL赋值给当前对象的imageUrl字段
        this.imageUrl = imageUrl;
    }

    /**
     * 获取图片标题
     * 功能概述：返回图片的标题
     * @return {String} 返回图片标题
     */
    // 获取图片标题方法，返回图片的标题
    public String getImageTitle() {
        // 返回图片标题
        return imageTitle;
    }

    /**
     * 设置图片标题
     * 功能概述：设置图片的标题
     * @param {String} imageTitle - 图片标题
     */
    // 设置图片标题方法，接收图片标题参数
    public void setImageTitle(String imageTitle) {
        // 将传入的图片标题赋值给当前对象的imageTitle字段
        this.imageTitle = imageTitle;
    }

    /**
     * 获取图片描述
     * 功能概述：返回图片的描述信息
     * @return {String} 返回图片描述
     */
    // 获取图片描述方法，返回图片的描述信息
    public String getImageDescription() {
        // 返回图片描述
        return imageDescription;
    }

    /**
     * 设置图片描述
     * 功能概述：设置图片的描述信息
     * @param {String} imageDescription - 图片描述
     */
    // 设置图片描述方法，接收图片描述参数
    public void setImageDescription(String imageDescription) {
        // 将传入的图片描述赋值给当前对象的imageDescription字段
        this.imageDescription = imageDescription;
    }

    /**
     * 获取是否封面
     * 功能概述：返回是否为封面图片
     * @return {Integer} 返回是否封面（0-否，1-是）
     */
    // 获取是否封面方法，返回是否为封面图片
    public Integer getIsCover() {
        // 返回是否封面
        return isCover;
    }

    /**
     * 设置是否封面
     * 功能概述：设置是否为封面图片
     * @param {Integer} isCover - 是否封面（0-否，1-是）
     */
    // 设置是否封面方法，接收是否封面参数
    public void setIsCover(Integer isCover) {
        // 将传入的是否封面赋值给当前对象的isCover字段
        this.isCover = isCover;
    }

    /**
     * 获取排序顺序
     * 功能概述：返回图片的排序顺序
     * @return {Integer} 返回排序顺序
     */
    // 获取排序顺序方法，返回图片的排序顺序
    public Integer getSortOrder() {
        // 返回排序顺序
        return sortOrder;
    }

    /**
     * 设置排序顺序
     * 功能概述：设置图片的排序顺序
     * @param {Integer} sortOrder - 排序顺序
     */
    // 设置排序顺序方法，接收排序顺序参数
    public void setSortOrder(Integer sortOrder) {
        // 将传入的排序顺序赋值给当前对象的sortOrder字段
        this.sortOrder = sortOrder;
    }

    /**
     * 获取状态
     * 功能概述：返回图片的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回图片的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置图片的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回图片的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回图片的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置图片的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回图片的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回图片的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置图片的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }
}
