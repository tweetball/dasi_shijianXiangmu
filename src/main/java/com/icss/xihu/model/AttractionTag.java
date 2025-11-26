/**
 * 景点标签实体类
 * 功能概述：封装景点标签信息，对应数据库中的attraction_tag表，包含标签的基本信息、颜色等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 景点标签实体类
 * 功能概述：封装景点标签信息，对应数据库中的attraction_tag表，包含标签的基本信息、颜色等
 */
// 景点标签实体类，封装景点标签信息
public class AttractionTag {
    // 标签编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 标签名称，对应数据库中的name字段，标签的名称（如"热门"、"推荐"等）
    private String name;
    // 标签颜色，对应数据库中的color字段，标签的颜色（如"#FF0000"）
    private String color;
    // 标签描述，对应数据库中的description字段，标签的描述信息
    private String description;
    // 状态，对应数据库中的status字段，标签的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，标签的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，标签的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的标签对象
     */
    // 无参构造函数，创建一个空的标签对象
    public AttractionTag() {}

    /**
     * 获取标签编号
     * 功能概述：返回标签的唯一标识ID
     * @return {Long} 返回标签编号
     */
    // 获取标签编号方法，返回标签的唯一标识ID
    public Long getId() {
        // 返回标签编号
        return id;
    }

    /**
     * 设置标签编号
     * 功能概述：设置标签的唯一标识ID
     * @param {Long} id - 标签编号
     */
    // 设置标签编号方法，接收标签编号参数
    public void setId(Long id) {
        // 将传入的标签编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取标签名称
     * 功能概述：返回标签的名称
     * @return {String} 返回标签名称
     */
    // 获取标签名称方法，返回标签的名称
    public String getName() {
        // 返回标签名称
        return name;
    }

    /**
     * 设置标签名称
     * 功能概述：设置标签的名称
     * @param {String} name - 标签名称
     */
    // 设置标签名称方法，接收标签名称参数
    public void setName(String name) {
        // 将传入的标签名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取标签颜色
     * 功能概述：返回标签的颜色
     * @return {String} 返回标签颜色
     */
    // 获取标签颜色方法，返回标签的颜色
    public String getColor() {
        // 返回标签颜色
        return color;
    }

    /**
     * 设置标签颜色
     * 功能概述：设置标签的颜色
     * @param {String} color - 标签颜色
     */
    // 设置标签颜色方法，接收标签颜色参数
    public void setColor(String color) {
        // 将传入的标签颜色赋值给当前对象的color字段
        this.color = color;
    }

    /**
     * 获取标签描述
     * 功能概述：返回标签的描述信息
     * @return {String} 返回标签描述
     */
    // 获取标签描述方法，返回标签的描述信息
    public String getDescription() {
        // 返回标签描述
        return description;
    }

    /**
     * 设置标签描述
     * 功能概述：设置标签的描述信息
     * @param {String} description - 标签描述
     */
    // 设置标签描述方法，接收标签描述参数
    public void setDescription(String description) {
        // 将传入的标签描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取状态
     * 功能概述：返回标签的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回标签的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置标签的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回标签的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回标签的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置标签的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回标签的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回标签的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置标签的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }
}
