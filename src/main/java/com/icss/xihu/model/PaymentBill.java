/**
 * 缴费账单实体类
 * 功能概述：封装缴费账单信息，对应数据库中的payment_bill表，包含账单的基本信息、金额、状态等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入BigDecimal类，用于精确的十进制数值计算
import java.math.BigDecimal;
// 导入LocalDate类，用于表示日期
import java.time.LocalDate;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;

/**
 * 缴费账单实体类
 * 功能概述：封装缴费账单信息，对应数据库中的payment_bill表，包含账单的基本信息、金额、状态等
 */
// 缴费账单实体类，封装缴费账单信息
public class PaymentBill {
    // 账单编号，对应数据库中的id字段，主键，自增
    private Integer id;
    // 用户编号，对应数据库中的user_id字段，关联用户表的id字段（外键）
    private Integer userId;
    // 账户编号，对应数据库中的account_id字段，关联账户表的id字段（外键）
    private Integer accountId;
    // 账单号，对应数据库中的bill_number字段，账单的唯一标识号
    private String billNumber;
    // 缴费类型编号，对应数据库中的payment_type_id字段，关联缴费类型表的id字段（外键）
    private Integer paymentTypeId;
    // 账单金额，对应数据库中的bill_amount字段，账单的金额（元）
    private BigDecimal billAmount;
    // 到期日期，对应数据库中的due_date字段，账单的到期日期
    private LocalDate dueDate;
    // 账单周期，对应数据库中的bill_period字段，账单的周期（如"2024年1月"）
    private String billPeriod;
    // 账单状态，对应数据库中的bill_status字段，账单的状态（0-未缴费，1-已缴费，2-逾期）
    private Integer billStatus;
    // 已缴金额，对应数据库中的paid_amount字段，已缴纳的金额（元）
    private BigDecimal paidAmount;
    // 缴费时间，对应数据库中的paid_time字段，账单的缴费时间
    private LocalDateTime paidTime;
    // 创建时间，对应数据库中的create_time字段，账单的创建时间
    private LocalDateTime createTime;
    // 更新时间，对应数据库中的update_time字段，账单的最后更新时间
    private LocalDateTime updateTime;
    
    // 关联信息
    // 账户名称，关联账户表的name字段，账户的名称
    private String accountName;
    // 缴费类型名称，关联缴费类型表的name字段，缴费类型的名称
    private String paymentTypeName;
    // 缴费类型图标，关联缴费类型表的icon字段，缴费类型图标的URL路径
    private String paymentTypeIcon;
    // 账户号码，关联账户表的account_number字段，账户的号码
    private String accountNumber;

    /**
     * 无参构造函数
     * 功能概述：创建一个空的账单对象
     */
    // 构造函数
    // 无参构造函数，创建一个空的账单对象
    public PaymentBill() {}

    /**
     * 有参构造函数
     * 功能概述：创建一个账单对象并设置基本属性
     * @param {Integer} userId - 用户编号
     * @param {Integer} accountId - 账户编号
     * @param {String} billNumber - 账单号
     * @param {Integer} paymentTypeId - 缴费类型编号
     * @param {BigDecimal} billAmount - 账单金额
     * @param {LocalDate} dueDate - 到期日期
     * @param {String} billPeriod - 账单周期
     */
    // 有参构造函数，创建一个账单对象并设置基本属性
    public PaymentBill(Integer userId, Integer accountId, String billNumber, Integer paymentTypeId, 
                      BigDecimal billAmount, LocalDate dueDate, String billPeriod) {
        // 将传入的用户编号赋值给当前对象的userId字段
        this.userId = userId;
        // 将传入的账户编号赋值给当前对象的accountId字段
        this.accountId = accountId;
        // 将传入的账单号赋值给当前对象的billNumber字段
        this.billNumber = billNumber;
        // 将传入的缴费类型编号赋值给当前对象的paymentTypeId字段
        this.paymentTypeId = paymentTypeId;
        // 将传入的账单金额赋值给当前对象的billAmount字段
        this.billAmount = billAmount;
        // 将传入的到期日期赋值给当前对象的dueDate字段
        this.dueDate = dueDate;
        // 将传入的账单周期赋值给当前对象的billPeriod字段
        this.billPeriod = billPeriod;
        // 将账单状态设置为0（未缴费）
        this.billStatus = 0;
        // 将已缴金额设置为0
        this.paidAmount = BigDecimal.ZERO;
    }

    /**
     * 获取账单编号
     * 功能概述：返回账单的唯一标识ID
     * @return {Integer} 返回账单编号
     */
    // Getter和Setter方法
    // 获取账单编号方法，返回账单的唯一标识ID
    public Integer getId() {
        // 返回账单编号
        return id;
    }

    /**
     * 设置账单编号
     * 功能概述：设置账单的唯一标识ID
     * @param {Integer} id - 账单编号
     */
    // 设置账单编号方法，接收账单编号参数
    public void setId(Integer id) {
        // 将传入的账单编号赋值给当前对象的id字段
        this.id = id;
    }

    /**
     * 获取用户编号
     * 功能概述：返回账单所属的用户编号
     * @return {Integer} 返回用户编号
     */
    // 获取用户编号方法，返回账单所属的用户编号
    public Integer getUserId() {
        // 返回用户编号
        return userId;
    }

    /**
     * 设置用户编号
     * 功能概述：设置账单所属的用户编号
     * @param {Integer} userId - 用户编号
     */
    // 设置用户编号方法，接收用户编号参数
    public void setUserId(Integer userId) {
        // 将传入的用户编号赋值给当前对象的userId字段
        this.userId = userId;
    }

    /**
     * 获取账户编号
     * 功能概述：返回账单所属的账户编号
     * @return {Integer} 返回账户编号
     */
    // 获取账户编号方法，返回账单所属的账户编号
    public Integer getAccountId() {
        // 返回账户编号
        return accountId;
    }

    /**
     * 设置账户编号
     * 功能概述：设置账单所属的账户编号
     * @param {Integer} accountId - 账户编号
     */
    // 设置账户编号方法，接收账户编号参数
    public void setAccountId(Integer accountId) {
        // 将传入的账户编号赋值给当前对象的accountId字段
        this.accountId = accountId;
    }

    /**
     * 获取账单号
     * 功能概述：返回账单的唯一标识号
     * @return {String} 返回账单号
     */
    // 获取账单号方法，返回账单的唯一标识号
    public String getBillNumber() {
        // 返回账单号
        return billNumber;
    }

    /**
     * 设置账单号
     * 功能概述：设置账单的唯一标识号
     * @param {String} billNumber - 账单号
     */
    // 设置账单号方法，接收账单号参数
    public void setBillNumber(String billNumber) {
        // 将传入的账单号赋值给当前对象的billNumber字段
        this.billNumber = billNumber;
    }

    /**
     * 获取缴费类型编号
     * 功能概述：返回账单所属的缴费类型编号
     * @return {Integer} 返回缴费类型编号
     */
    // 获取缴费类型编号方法，返回账单所属的缴费类型编号
    public Integer getPaymentTypeId() {
        // 返回缴费类型编号
        return paymentTypeId;
    }

    /**
     * 设置缴费类型编号
     * 功能概述：设置账单所属的缴费类型编号
     * @param {Integer} paymentTypeId - 缴费类型编号
     */
    // 设置缴费类型编号方法，接收缴费类型编号参数
    public void setPaymentTypeId(Integer paymentTypeId) {
        // 将传入的缴费类型编号赋值给当前对象的paymentTypeId字段
        this.paymentTypeId = paymentTypeId;
    }

    /**
     * 获取账单金额
     * 功能概述：返回账单的金额
     * @return {BigDecimal} 返回账单金额（元）
     */
    // 获取账单金额方法，返回账单的金额
    public BigDecimal getBillAmount() {
        // 返回账单金额
        return billAmount;
    }

    /**
     * 设置账单金额
     * 功能概述：设置账单的金额
     * @param {BigDecimal} billAmount - 账单金额（元）
     */
    // 设置账单金额方法，接收账单金额参数
    public void setBillAmount(BigDecimal billAmount) {
        // 将传入的账单金额赋值给当前对象的billAmount字段
        this.billAmount = billAmount;
    }

    /**
     * 获取到期日期
     * 功能概述：返回账单的到期日期
     * @return {LocalDate} 返回到期日期
     */
    // 获取到期日期方法，返回账单的到期日期
    public LocalDate getDueDate() {
        // 返回到期日期
        return dueDate;
    }

    /**
     * 设置到期日期
     * 功能概述：设置账单的到期日期
     * @param {LocalDate} dueDate - 到期日期
     */
    // 设置到期日期方法，接收到期日期参数
    public void setDueDate(LocalDate dueDate) {
        // 将传入的到期日期赋值给当前对象的dueDate字段
        this.dueDate = dueDate;
    }

    /**
     * 获取账单周期
     * 功能概述：返回账单的周期
     * @return {String} 返回账单周期
     */
    // 获取账单周期方法，返回账单的周期
    public String getBillPeriod() {
        // 返回账单周期
        return billPeriod;
    }

    /**
     * 设置账单周期
     * 功能概述：设置账单的周期
     * @param {String} billPeriod - 账单周期
     */
    // 设置账单周期方法，接收账单周期参数
    public void setBillPeriod(String billPeriod) {
        // 将传入的账单周期赋值给当前对象的billPeriod字段
        this.billPeriod = billPeriod;
    }

    /**
     * 获取账单状态
     * 功能概述：返回账单的状态
     * @return {Integer} 返回账单状态（0-未缴费，1-已缴费，2-逾期）
     */
    // 获取账单状态方法，返回账单的状态
    public Integer getBillStatus() {
        // 返回账单状态
        return billStatus;
    }

    /**
     * 设置账单状态
     * 功能概述：设置账单的状态
     * @param {Integer} billStatus - 账单状态（0-未缴费，1-已缴费，2-逾期）
     */
    // 设置账单状态方法，接收账单状态参数
    public void setBillStatus(Integer billStatus) {
        // 将传入的账单状态赋值给当前对象的billStatus字段
        this.billStatus = billStatus;
    }

    /**
     * 获取已缴金额
     * 功能概述：返回已缴纳的金额
     * @return {BigDecimal} 返回已缴金额（元）
     */
    // 获取已缴金额方法，返回已缴纳的金额
    public BigDecimal getPaidAmount() {
        // 返回已缴金额
        return paidAmount;
    }

    /**
     * 设置已缴金额
     * 功能概述：设置已缴纳的金额
     * @param {BigDecimal} paidAmount - 已缴金额（元）
     */
    // 设置已缴金额方法，接收已缴金额参数
    public void setPaidAmount(BigDecimal paidAmount) {
        // 将传入的已缴金额赋值给当前对象的paidAmount字段
        this.paidAmount = paidAmount;
    }

    /**
     * 获取缴费时间
     * 功能概述：返回账单的缴费时间
     * @return {LocalDateTime} 返回缴费时间
     */
    // 获取缴费时间方法，返回账单的缴费时间
    public LocalDateTime getPaidTime() {
        // 返回缴费时间
        return paidTime;
    }

    /**
     * 设置缴费时间
     * 功能概述：设置账单的缴费时间
     * @param {LocalDateTime} paidTime - 缴费时间
     */
    // 设置缴费时间方法，接收缴费时间参数
    public void setPaidTime(LocalDateTime paidTime) {
        // 将传入的缴费时间赋值给当前对象的paidTime字段
        this.paidTime = paidTime;
    }

    /**
     * 获取创建时间
     * 功能概述：返回账单的创建时间
     * @return {LocalDateTime} 返回创建时间
     */
    // 获取创建时间方法，返回账单的创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * 功能概述：设置账单的创建时间
     * @param {LocalDateTime} createTime - 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 将传入的创建时间赋值给当前对象的createTime字段
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 功能概述：返回账单的最后更新时间
     * @return {LocalDateTime} 返回更新时间
     */
    // 获取更新时间方法，返回账单的最后更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * 功能概述：设置账单的最后更新时间
     * @param {LocalDateTime} updateTime - 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 将传入的更新时间赋值给当前对象的updateTime字段
        this.updateTime = updateTime;
    }

    /**
     * 获取账户名称
     * 功能概述：返回关联的账户名称
     * @return {String} 返回账户名称
     */
    // 获取账户名称方法，返回关联的账户名称
    public String getAccountName() {
        // 返回账户名称
        return accountName;
    }

    /**
     * 设置账户名称
     * 功能概述：设置关联的账户名称
     * @param {String} accountName - 账户名称
     */
    // 设置账户名称方法，接收账户名称参数
    public void setAccountName(String accountName) {
        // 将传入的账户名称赋值给当前对象的accountName字段
        this.accountName = accountName;
    }

    /**
     * 获取缴费类型名称
     * 功能概述：返回关联的缴费类型名称
     * @return {String} 返回缴费类型名称
     */
    // 获取缴费类型名称方法，返回关联的缴费类型名称
    public String getPaymentTypeName() {
        // 返回缴费类型名称
        return paymentTypeName;
    }

    /**
     * 设置缴费类型名称
     * 功能概述：设置关联的缴费类型名称
     * @param {String} paymentTypeName - 缴费类型名称
     */
    // 设置缴费类型名称方法，接收缴费类型名称参数
    public void setPaymentTypeName(String paymentTypeName) {
        // 将传入的缴费类型名称赋值给当前对象的paymentTypeName字段
        this.paymentTypeName = paymentTypeName;
    }

    /**
     * 获取缴费类型图标
     * 功能概述：返回关联的缴费类型图标URL路径
     * @return {String} 返回缴费类型图标URL
     */
    // 获取缴费类型图标方法，返回关联的缴费类型图标URL路径
    public String getPaymentTypeIcon() {
        // 返回缴费类型图标URL
        return paymentTypeIcon;
    }

    /**
     * 设置缴费类型图标
     * 功能概述：设置关联的缴费类型图标URL路径
     * @param {String} paymentTypeIcon - 缴费类型图标URL
     */
    // 设置缴费类型图标方法，接收缴费类型图标URL参数
    public void setPaymentTypeIcon(String paymentTypeIcon) {
        // 将传入的缴费类型图标URL赋值给当前对象的paymentTypeIcon字段
        this.paymentTypeIcon = paymentTypeIcon;
    }

    /**
     * 获取账户号码
     * 功能概述：返回关联的账户号码
     * @return {String} 返回账户号码
     */
    // 获取账户号码方法，返回关联的账户号码
    public String getAccountNumber() {
        // 返回账户号码
        return accountNumber;
    }

    /**
     * 设置账户号码
     * 功能概述：设置关联的账户号码
     * @param {String} accountNumber - 账户号码
     */
    // 设置账户号码方法，接收账户号码参数
    public void setAccountNumber(String accountNumber) {
        // 将传入的账户号码赋值给当前对象的accountNumber字段
        this.accountNumber = accountNumber;
    }

    // 业务方法
    /**
     * 判断是否逾期
     * 功能概述：判断账单是否逾期（状态为2或状态为0且到期日期已过）
     * @return {boolean} 返回是否逾期（true-逾期，false-未逾期）
     */
    // 判断是否逾期方法，判断账单是否逾期（状态为2或状态为0且到期日期已过）
    public boolean isOverdue() {
        // 返回账单状态为2（逾期）或账单状态为0（未缴费）且到期日期早于当前日期
        return billStatus == 2 || (billStatus == 0 && dueDate.isBefore(LocalDate.now()));
    }

    /**
     * 判断是否已缴费
     * 功能概述：判断账单是否已缴费（状态为1）
     * @return {boolean} 返回是否已缴费（true-已缴费，false-未缴费）
     */
    // 判断是否已缴费方法，判断账单是否已缴费（状态为1）
    public boolean isPaid() {
        // 返回账单状态为1（已缴费）
        return billStatus == 1;
    }

    /**
     * 获取状态文本
     * 功能概述：根据账单状态返回对应的文本描述
     * @return {String} 返回状态文本（"待缴费"、"已缴费"、"逾期"、"未知"）
     */
    // 获取状态文本方法，根据账单状态返回对应的文本描述
    public String getStatusText() {
        // 根据账单状态返回对应的文本描述
        switch (billStatus) {
            case 0:
                // 如果账单状态为0（未缴费），判断是否逾期，返回"逾期"或"待缴费"
                return isOverdue() ? "逾期" : "待缴费";
            case 1:
                // 如果账单状态为1（已缴费），返回"已缴费"
                return "已缴费";
            case 2:
                // 如果账单状态为2（逾期），返回"逾期"
                return "逾期";
            default:
                // 如果账单状态未知，返回"未知"
                return "未知";
        }
    }
}
