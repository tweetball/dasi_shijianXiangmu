/**
 * 地址实体类
 * 功能概述：封装地址信息，对应数据库中的address表，包含地址的基本信息、位置信息、层级关系等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;
// 导入Lombok的Data注解，用于自动生成getter/setter、toString、equals、hashCode等方法
import lombok.Data;

/**
 * 地址实体类
 * 功能概述：封装地址信息，对应数据库中的address表，包含地址的基本信息、位置信息、层级关系等
 */
// 使用Lombok的Data注解，自动生成getter/setter、toString、equals、hashCode等方法
@Data
// 地址实体类，封装地址信息
public class Address {
    // 地址编号，对应数据库中的id字段，主键，自增
    private Long id;
    // 地址名称，对应数据库中的name字段，地址的名称
    private String name;
    // 完整地址，对应数据库中的full_address字段，地址的完整地址
    private String fullAddress;
    // 省份，对应数据库中的province字段，地址所在的省份名称
    private String province;
    // 城市，对应数据库中的city字段，地址所在的城市名称
    private String city;
    // 区县，对应数据库中的district字段，地址所在的区县名称
    private String district;
    // 街道，对应数据库中的street字段，地址所在的街道名称
    private String street;
    // 详细地址，对应数据库中的detail_address字段，地址的详细地址
    private String detailAddress;
    // 邮政编码，对应数据库中的postal_code字段，地址的邮政编码
    private String postalCode;
    // 经度，对应数据库中的longitude字段，地址的经度坐标
    private BigDecimal longitude;
    // 纬度，对应数据库中的latitude字段，地址的纬度坐标
    private BigDecimal latitude;
    // 联系电话，对应数据库中的phone字段，地址的联系电话
    private String phone;
    // 分类，对应数据库中的category字段，地址的分类（如"景点"、"酒店"等）
    private String category;
    // 描述，对应数据库中的description字段，地址的描述信息
    private String description;
    // 状态，对应数据库中的status字段，地址的状态（0-禁用，1-启用）
    private Integer status;
    // 创建时间，对应数据库中的create_time字段，地址的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，地址的最后更新时间
    private LocalDateTime updateTime;
    // 上级地址ID，对应数据库中的parent_id字段，关联上级地址的ID
    private Integer parentId;
    // 地址级别，对应数据库中的level字段，地址的级别（1-省，2-市，3-区）
    private int level;
    
    // 数据库字段映射
    // 父级ID，对应数据库中的pid字段，父级地址的ID（字符串类型）
    private String pid;
    // 深度，对应数据库中的deep字段，地址的层级深度
    private String deep;
    // 扩展名称，对应数据库中的ext_name字段，地址的扩展名称
    private String extName;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的地址对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的地址对象
    public Address() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个地址对象并设置基本属性
     * @param {String} name - 地址名称
     * @param {String} fullAddress - 完整地址
     * @param {String} category - 分类
     */
    // 有参构造函数，创建一个地址对象并设置基本属性
    public Address(String name, String fullAddress, String category) {
        // 将传入的地址名称赋值给当前对象的name字段
        this.name = name;
        // 将传入的完整地址赋值给当前对象的fullAddress字段
        this.fullAddress = fullAddress;
        // 将传入的分类赋值给当前对象的category字段
        this.category = category;
        // 将状态设置为1（启用）
        this.status = 1;
    }

    /**
     * 获取地址编号
     * 功能概述：返回地址的唯一标识ID
     * @return {Long} 返回地址编号
     */
    // Getter和Setter方法
    // 获取地址编号方法，返回地址的唯一标识ID
    public Long getId() {
        // 返回地址编号
        return id;
    }

    /**
     * 设置地址编号
     * 功能概述：设置地址的唯一标识ID
     * @param {Long} id - 地址编号
     */
    // 设置地址编号方法，接收地址编号参数
    public void setId(Long id) {
        // 将传入的地址编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取地址名称
     * 功能概述：返回地址的名称
     * @return {String} 返回地址名称
     */
    // 获取地址名称方法，返回地址的名称
    public String getName() {
        // 返回地址名称
        return name;
    }

    /**
     * 设置地址名称
     * 功能概述：设置地址的名称
     * @param {String} name - 地址名称
     */
    // 设置地址名称方法，接收地址名称参数
    public void setName(String name) {
        // 将传入的地址名称赋值给当前对象的name字段
        this.name = name;
    }

    /**
     * 获取完整地址
     * 功能概述：返回地址的完整地址
     * @return {String} 返回完整地址
     */
    // 获取完整地址方法，返回地址的完整地址
    public String getFullAddress() {
        // 返回完整地址
        return fullAddress;
    }

    /**
     * 设置完整地址
     * 功能概述：设置地址的完整地址
     * @param {String} fullAddress - 完整地址
     */
    // 设置完整地址方法，接收完整地址参数
    public void setFullAddress(String fullAddress) {
        // 将传入的完整地址赋值给当前对象的fullAddress字段
        this.fullAddress = fullAddress;
    }

    /**
     * 获取省份
     * 功能概述：返回地址所在的省份名称
     * @return {String} 返回省份名称
     */
    // 获取省份方法，返回地址所在的省份名称
    public String getProvince() {
        // 返回省份名称
        return province;
    }

    /**
     * 设置省份
     * 功能概述：设置地址所在的省份名称
     * @param {String} province - 省份名称
     */
    // 设置省份方法，接收省份名称参数
    public void setProvince(String province) {
        // 将传入的省份名称赋值给当前对象的province字段
        this.province = province;
    }

    /**
     * 获取城市
     * 功能概述：返回地址所在的城市名称
     * @return {String} 返回城市名称
     */
    // 获取城市方法，返回地址所在的城市名称
    public String getCity() {
        // 返回城市名称
        return city;
    }

    /**
     * 设置城市
     * 功能概述：设置地址所在的城市名称
     * @param {String} city - 城市名称
     */
    // 设置城市方法，接收城市名称参数
    public void setCity(String city) {
        // 将传入的城市名称赋值给当前对象的city字段
        this.city = city;
    }

    /**
     * 获取区县
     * 功能概述：返回地址所在的区县名称
     * @return {String} 返回区县名称
     */
    // 获取区县方法，返回地址所在的区县名称
    public String getDistrict() {
        // 返回区县名称
        return district;
    }

    /**
     * 设置区县
     * 功能概述：设置地址所在的区县名称
     * @param {String} district - 区县名称
     */
    // 设置区县方法，接收区县名称参数
    public void setDistrict(String district) {
        // 将传入的区县名称赋值给当前对象的district字段
        this.district = district;
    }

    /**
     * 获取街道
     * 功能概述：返回地址所在的街道名称
     * @return {String} 返回街道名称
     */
    // 获取街道方法，返回地址所在的街道名称
    public String getStreet() {
        // 返回街道名称
        return street;
    }

    /**
     * 设置街道
     * 功能概述：设置地址所在的街道名称
     * @param {String} street - 街道名称
     */
    // 设置街道方法，接收街道名称参数
    public void setStreet(String street) {
        // 将传入的街道名称赋值给当前对象的street字段
        this.street = street;
    }

    /**
     * 获取详细地址
     * 功能概述：返回地址的详细地址
     * @return {String} 返回详细地址
     */
    // 获取详细地址方法，返回地址的详细地址
    public String getDetailAddress() {
        // 返回详细地址
        return detailAddress;
    }

    /**
     * 设置详细地址
     * 功能概述：设置地址的详细地址
     * @param {String} detailAddress - 详细地址
     */
    // 设置详细地址方法，接收详细地址参数
    public void setDetailAddress(String detailAddress) {
        // 将传入的详细地址赋值给当前对象的detailAddress字段
        this.detailAddress = detailAddress;
    }

    /**
     * 获取邮政编码
     * 功能概述：返回地址的邮政编码
     * @return {String} 返回邮政编码
     */
    // 获取邮政编码方法，返回地址的邮政编码
    public String getPostalCode() {
        // 返回邮政编码
        return postalCode;
    }

    /**
     * 设置邮政编码
     * 功能概述：设置地址的邮政编码
     * @param {String} postalCode - 邮政编码
     */
    // 设置邮政编码方法，接收邮政编码参数
    public void setPostalCode(String postalCode) {
        // 将传入的邮政编码赋值给当前对象的postalCode字段
        this.postalCode = postalCode;
    }

    /**
     * 获取经度
     * 功能概述：返回地址的经度坐标
     * @return {BigDecimal} 返回经度
     */
    // 获取经度方法，返回地址的经度坐标
    public BigDecimal getLongitude() {
        // 返回经度
        return longitude;
    }

    /**
     * 设置经度
     * 功能概述：设置地址的经度坐标
     * @param {BigDecimal} longitude - 经度
     */
    // 设置经度方法，接收经度参数
    public void setLongitude(BigDecimal longitude) {
        // 将传入的经度赋值给当前对象的longitude字段
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     * 功能概述：返回地址的纬度坐标
     * @return {BigDecimal} 返回纬度
     */
    // 获取纬度方法，返回地址的纬度坐标
    public BigDecimal getLatitude() {
        // 返回纬度
        return latitude;
    }

    /**
     * 设置纬度
     * 功能概述：设置地址的纬度坐标
     * @param {BigDecimal} latitude - 纬度
     */
    // 设置纬度方法，接收纬度参数
    public void setLatitude(BigDecimal latitude) {
        // 将传入的纬度赋值给当前对象的latitude字段
        this.latitude = latitude;
    }

    /**
     * 获取联系电话
     * 功能概述：返回地址的联系电话
     * @return {String} 返回联系电话
     */
    // 获取联系电话方法，返回地址的联系电话
    public String getPhone() {
        // 返回联系电话
        return phone;
    }

    /**
     * 设置联系电话
     * 功能概述：设置地址的联系电话
     * @param {String} phone - 联系电话
     */
    // 设置联系电话方法，接收联系电话参数
    public void setPhone(String phone) {
        // 将传入的联系电话赋值给当前对象的phone字段
        this.phone = phone;
    }

    /**
     * 获取分类
     * 功能概述：返回地址的分类
     * @return {String} 返回分类
     */
    // 获取分类方法，返回地址的分类
    public String getCategory() {
        // 返回分类
        return category;
    }

    /**
     * 设置分类
     * 功能概述：设置地址的分类
     * @param {String} category - 分类
     */
    // 设置分类方法，接收分类参数
    public void setCategory(String category) {
        // 将传入的分类赋值给当前对象的category字段
        this.category = category;
    }

    /**
     * 获取描述
     * 功能概述：返回地址的描述信息
     * @return {String} 返回描述
     */
    // 获取描述方法，返回地址的描述信息
    public String getDescription() {
        // 返回描述
        return description;
    }

    /**
     * 设置描述
     * 功能概述：设置地址的描述信息
     * @param {String} description - 描述
     */
    // 设置描述方法，接收描述参数
    public void setDescription(String description) {
        // 将传入的描述赋值给当前对象的description字段
        this.description = description;
    }

    /**
     * 获取状态
     * 功能概述：返回地址的状态
     * @return {Integer} 返回状态（0-禁用，1-启用）
     */
    // 获取状态方法，返回地址的状态
    public Integer getStatus() {
        // 返回状态
        return status;
    }

    /**
     * 设置状态
     * 功能概述：设置地址的状态
     * @param {Integer} status - 状态（0-禁用，1-启用）
     */
    // 设置状态方法，接收状态参数
    public void setStatus(Integer status) {
        // 将传入的状态赋值给当前对象的status字段
        this.status = status;
    }

    /**
     * 获取创建时间
     * 功能概述：返回地址的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回地址的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置地址的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回地址的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回地址的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置地址的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 获取上级地址ID
     * 功能概述：返回上级地址的ID
     * @return {Integer} 返回上级地址ID
     */
    // 获取上级地址ID方法，返回上级地址的ID
    public Integer getParentId() {
        // 返回上级地址ID
        return parentId;
    }

    /**
     * 设置上级地址ID
     * 功能概述：设置上级地址的ID
     * @param {Integer} parentId - 上级地址ID
     */
    // 设置上级地址ID方法，接收上级地址ID参数
    public void setParentId(Integer parentId) {
        // 将传入的上级地址ID赋值给当前对象的parentId字段
        this.parentId = parentId;
    }

    /**
     * 获取地址级别
     * 功能概述：返回地址的级别
     * @return {int} 返回地址级别（1-省，2-市，3-区）
     */
    // 获取地址级别方法，返回地址的级别
    public int getLevel() {
        // 返回地址级别
        return level;
    }

    /**
     * 设置地址级别
     * 功能概述：设置地址的级别
     * @param {int} level - 地址级别（1-省，2-市，3-区）
     */
    // 设置地址级别方法，接收地址级别参数
    public void setLevel(int level) {
        // 将传入的地址级别赋值给当前对象的level字段
        this.level = level;
    }
    
    /**
     * 获取父级ID
     * 功能概述：返回父级地址的ID（字符串类型）
     * @return {String} 返回父级ID
     */
    // 获取父级ID方法，返回父级地址的ID（字符串类型）
    public String getPid() {
        // 返回父级ID
        return pid;
    }

    /**
     * 设置父级ID
     * 功能概述：设置父级地址的ID（字符串类型）
     * @param {String} pid - 父级ID
     */
    // 设置父级ID方法，接收父级ID参数
    public void setPid(String pid) {
        // 将传入的父级ID赋值给当前对象的pid字段
        this.pid = pid;
    }

    /**
     * 获取深度
     * 功能概述：返回地址的层级深度
     * @return {String} 返回深度
     */
    // 获取深度方法，返回地址的层级深度
    public String getDeep() {
        // 返回深度
        return deep;
    }

    /**
     * 设置深度
     * 功能概述：设置地址的层级深度
     * @param {String} deep - 深度
     */
    // 设置深度方法，接收深度参数
    public void setDeep(String deep) {
        // 将传入的深度赋值给当前对象的deep字段
        this.deep = deep;
    }

    /**
     * 获取扩展名称
     * 功能概述：返回地址的扩展名称
     * @return {String} 返回扩展名称
     */
    // 获取扩展名称方法，返回地址的扩展名称
    public String getExtName() {
        // 返回扩展名称
        return extName;
    }

    /**
     * 设置扩展名称
     * 功能概述：设置地址的扩展名称
     * @param {String} extName - 扩展名称
     */
    // 设置扩展名称方法，接收扩展名称参数
    public void setExtName(String extName) {
        // 将传入的扩展名称赋值给当前对象的extName字段
        this.extName = extName;
    }

    /**
     * 重写toString方法
     * 功能概述：将地址对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回地址对象的字符串表示
     */
    // 重写Object类的toString方法，返回地址对象的字符串表示
    @Override
    // toString方法，将地址对象转换为字符串格式
    public String toString() {
        // 返回地址对象的字符串表示，包含主要字段的值
        return "Address{" +
                "id=" + id +                                    // 地址编号
                ", name='" + name + '\'' +                     // 地址名称
                ", fullAddress='" + fullAddress + '\'' +        // 完整地址
                ", province='" + province + '\'' +              // 省份
                ", city='" + city + '\'' +                      // 城市
                ", district='" + district + '\'' +             // 区县
                ", parentId=" + parentId +                     // 上级地址ID
                ", level=" + level +                           // 地址级别
                '}';
    }
}
