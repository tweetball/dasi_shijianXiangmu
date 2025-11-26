/**
 * 景点分类实体类
 * 功能概述：封装景点分类信息，对应数据库中的attraction_category表，包含分类的基本信息、图标等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 景点分类实体类
 * 功能概述：封装景点分类信息，对应数据库中的attraction_category表，包含分类的基本信息、图标等
 */
// 景点分类实体类，封装景点分类信息
public class AttractionCategory {
    // 分类编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 分类名称，对应数据库中的name字段，分类的名称（如"自然风光"、"人文历史"等）
    private String name;
    // 分类代码，对应数据库中的code字段，分类的唯一代码
    private String code;
    // 分类描述，对应数据库中的description字段，分类的描述信息
    private String description;
    // 图标URL，对应数据库中的icon_url字段，分类图标的URL路径
    private String iconUrl;
    // 排序顺序，对应数据库中的sort_order字段，分类的排序顺序
    private Integer sortOrder;
    // 状态，对应数据库中的status字段，分类的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，分类的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，分类的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的分类对象
     */
    // 无参构造函数，创建一个空的分类对象
    public AttractionCategory() {}

    /**
     * 获取分类编号
     * 功能概述：返回分类的唯一标识ID
     * @return {Long} 返回分类编号
     */
    // 获取分类编号方法，返回分类的唯一标识ID
    public Long getId() {
        // 返回分类编号
        return id;
    }

    /**
     * 设置分类编号
     * 功能概述：设置分类的唯一标识ID
     * @param {Long} id - 分类编号
     */
    // 设置分类编号方法，接收分类编号参数
    public void setId(Long id) {
        // 将传入的分类编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取分类名称
     * 功能概述：返回分类的名称
     * @return {String} 返回分类名称
     */
    // 获取分类名称方法，返回分类的名称
    public String getName() {
        // 返回分类名称
        return name;
    }

    /**
     * 设置分类名称
     * 功能概述：设置分类的名称
     * @param {String} name - 分类名称
     */
    // 设置分类名称方法，接收分类名称参数
    public void setName(String name) {
        // 将传入的分类名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取分类代码
     * 功能概述：返回分类的唯一代码
     * @return {String} 返回分类代码
     */
    // 获取分类代码方法，返回分类的唯一代码
    public String getCode() {
        // 返回分类代码
        return code;
    }

    /**
     * 设置分类代码
     * 功能概述：设置分类的唯一代码
     * @param {String} code - 分类代码
     */
    // 设置分类代码方法，接收分类代码参数
    public void setCode(String code) {
        // 将传入的分类代码赋值给当前对象的code字段
        this.code = code;
    }

    /**
     * 获取分类描述
     * 功能概述：返回分类的描述信息
     * @return {String} 返回分类描述
     */
    // 获取分类描述方法，返回分类的描述信息
    public String getDescription() {
        // 返回分类描述
        return description;
    }

    /**
     * 设置分类描述
     * 功能概述：设置分类的描述信息
     * @param {String} description - 分类描述
     */
    // 设置分类描述方法，接收分类描述参数
    public void setDescription(String description) {
        // 将传入的分类描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取图标URL
     * 功能概述：返回分类图标的URL路径
     * @return {String} 返回图标URL
     */
    // 获取图标URL方法，返回分类图标的URL路径
    public String getIconUrl() {
        // 返回图标URL
        return iconUrl;
    }

    /**
     * 设置图标URL
     * 功能概述：设置分类图标的URL路径
     * @param {String} iconUrl - 图标URL
     */
    // 设置图标URL方法，接收图标URL参数
    public void setIconUrl(String iconUrl) {
        // 将传入的图标URL赋值给当前对象的iconUrl字段
        this.iconUrl = iconUrl;
    }

    /**
     * 获取排序顺序
     * 功能概述：返回分类的排序顺序
     * @return {Integer} 返回排序顺序
     */
    // 获取排序顺序方法，返回分类的排序顺序
    public Integer getSortOrder() {
        // 返回排序顺序
        return sortOrder;
    }

    /**
     * 设置排序顺序
     * 功能概述：设置分类的排序顺序
     * @param {Integer} sortOrder - 排序顺序
     */
    // 设置排序顺序方法，接收排序顺序参数
    public void setSortOrder(Integer sortOrder) {
        // 将传入的排序顺序赋值给当前对象的sortOrder字段
        this.sortOrder = sortOrder;
    }

    /**
     * 获取状态
     * 功能概述：返回分类的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回分类的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置分类的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回分类的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回分类的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置分类的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回分类的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回分类的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置分类的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }
}
