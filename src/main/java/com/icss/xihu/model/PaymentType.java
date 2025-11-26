/**
 * 缴费类型实体类
 * 功能概述：封装缴费类型信息，对应数据库中的payment_type表，包含缴费类型的基本信息、图标等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 缴费类型实体类
 * 功能概述：封装缴费类型信息，对应数据库中的payment_type表，包含缴费类型的基本信息、图标等
 */
// 缴费类型实体类，封装缴费类型信息
public class PaymentType {
    // 类型编号，对应数据库中的id字段，主键，自增
    private Integer id;
    // 类型名称，对应数据库中的type_name字段，缴费类型的名称（如"水费"、"电费"等）
    private String typeName;
    // 类型代码，对应数据库中的type_code字段，缴费类型的唯一代码
    private String typeCode;
    // 图标，对应数据库中的icon字段，缴费类型图标的URL路径
    private String icon;
    // 描述，对应数据库中的description字段，缴费类型的描述信息
    private String description;
    // 状态，对应数据库中的status字段，缴费类型的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，缴费类型的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，缴费类型的最后更新时间
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的缴费类型对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的缴费类型对象
    public PaymentType() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个缴费类型对象并设置基本属性
     * @param {String} typeName - 类型名称
     * @param {String} typeCode - 类型代码
     * @param {String} icon - 图标
     * @param {String} description - 描述
     */
    // 有参构造函数，创建一个缴费类型对象并设置基本属性
    public PaymentType(String typeName, String typeCode, String icon, String description) {
        // 将传入的类型名称赋值给当前对象的typeName字段
        this.typeName = typeName;
        // 将传入的类型代码赋值给当前对象的typeCode字段
        this.typeCode = typeCode;
        // 将传入的图标赋值给当前对象的icon字段
        this.icon = icon;
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
        // 将状态设置为1（启用）
        this.status = 1;
    }

    /**
     * 获取类型编号
     * 功能概述：返回类型的唯一标识ID
     * @return {Integer} 返回类型编号
     */
    // Getter和Setter方法
    // 获取类型编号方法，返回类型的唯一标识ID
    public Integer getId() {
        // 返回类型编号
        return id;
    }

    /**
     * 设置类型编号
     * 功能概述：设置类型的唯一标识ID
     * @param {Integer} id - 类型编号
     */
    // 设置类型编号方法，接收类型编号参数
    public void setId(Integer id) {
        // 将传入的类型编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取类型名称
     * 功能概述：返回缴费类型的名称
     * @return {String} 返回类型名称
     */
    // 获取类型名称方法，返回缴费类型的名称
    public String getTypeName() {
        // 返回类型名称
        return typeName;
    }

    /**
     * 设置类型名称
     * 功能概述：设置缴费类型的名称
     * @param {String} typeName - 类型名称
     */
    // 设置类型名称方法，接收类型名称参数
    public void setTypeName(String typeName) {
        // 将传入的类型名称赋值给当前对象的typeName字段
        this.typeName = typeName;
    }

    /**
     * 获取类型代码
     * 功能概述：返回缴费类型的唯一代码
     * @return {String} 返回类型代码
     */
    // 获取类型代码方法，返回缴费类型的唯一代码
    public String getTypeCode() {
        // 返回类型代码
        return typeCode;
    }

    /**
     * 设置类型代码
     * 功能概述：设置缴费类型的唯一代码
     * @param {String} typeCode - 类型代码
     */
    // 设置类型代码方法，接收类型代码参数
    public void setTypeCode(String typeCode) {
        // 将传入的类型代码赋值给当前对象的typeCode字段
        this.typeCode = typeCode;
    }

    /**
     * 获取图标
     * 功能概述：返回缴费类型图标的URL路径
     * @return {String} 返回图标URL
     */
    // 获取图标方法，返回缴费类型图标的URL路径
    public String getIcon() {
        // 返回图标URL
        return icon;
    }

    /**
     * 设置图标
     * 功能概述：设置缴费类型图标的URL路径
     * @param {String} icon - 图标URL
     */
    // 设置图标方法，接收图标URL参数
    public void setIcon(String icon) {
        // 将传入的图标URL赋值给当前对象的icon字段
        this.icon = icon;
    }

    /**
     * 获取描述
     * 功能概述：返回缴费类型的描述信息
     * @return {String} 返回描述
     */
    // 获取描述方法，返回缴费类型的描述信息
    public String getDescription() {
        // 返回描述
        return description;
    }

    /**
     * 设置描述
     * 功能概述：设置缴费类型的描述信息
     * @param {String} description - 描述
     */
    // 设置描述方法，接收描述参数
    public void setDescription(String description) {
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取状态
     * 功能概述：返回缴费类型的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回缴费类型的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置缴费类型的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回缴费类型的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回缴费类型的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置缴费类型的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回缴费类型的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回缴费类型的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置缴费类型的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }
}
