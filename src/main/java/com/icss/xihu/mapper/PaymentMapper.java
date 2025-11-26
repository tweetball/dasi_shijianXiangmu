/**
 * 生活缴费Mapper接口
 * 功能概述：定义生活缴费相关的数据库操作方法，包括缴费类型查询、账单查询、状态更新等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入缴费账单实体类
import com.icss.xihu.model.PaymentBill;
// 导入缴费类型实体类
import com.icss.xihu.model.PaymentType;
// 导入MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Param注解，用于方法参数映射
import org.apache.ibatis.annotations.Param;

// 导入List集合接口
import java.util.List;

/**
 * 生活缴费Mapper接口
 * 功能概述：定义生活缴费相关的数据库操作方法，包括缴费类型查询、账单查询、状态更新等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 生活缴费Mapper接口，定义生活缴费相关的数据库操作方法
public interface PaymentMapper {
    
    /**
     * 获取所有缴费类型
     * 功能概述：从数据库中查询所有缴费类型信息
     * @return {List<PaymentType>} 返回所有缴费类型的列表
     */
    // 获取所有缴费类型方法，返回所有缴费类型的列表（SQL在XML映射文件中定义）
    List<PaymentType> getAllPaymentTypes();
    
    /**
     * 根据用户ID获取缴费账单
     * 功能概述：根据用户编号从数据库中查询该用户的所有缴费账单信息
     * @param {Integer} userId - 用户编号
     * @return {List<PaymentBill>} 返回该用户的所有账单列表
     */
    // 根据用户ID获取缴费账单方法，接收用户编号参数，返回该用户的所有账单列表（SQL在XML映射文件中定义）
    List<PaymentBill> getBillsByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据用户ID和状态获取缴费账单
     * 功能概述：根据用户编号和账单状态从数据库中查询该用户指定状态的所有缴费账单信息
     * @param {Integer} userId - 用户编号
     * @param {Integer} status - 账单状态（0-未缴费，1-已缴费，2-逾期）
     * @return {List<PaymentBill>} 返回该用户指定状态的所有账单列表
     */
    // 根据用户ID和状态获取缴费账单方法，接收用户编号和账单状态参数，返回该用户指定状态的所有账单列表（SQL在XML映射文件中定义）
    List<PaymentBill> getBillsByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);
    
    /**
     * 根据账单ID获取账单详情
     * 功能概述：根据账单编号从数据库中查询指定缴费账单的详细信息
     * @param {Integer} billId - 账单编号
     * @return {PaymentBill} 返回账单对象，如果不存在则返回null
     */
    // 根据账单ID获取账单详情方法，接收账单编号参数，返回账单对象（SQL在XML映射文件中定义）
    PaymentBill getBillById(@Param("billId") Integer billId);
    
    /**
     * 更新账单状态
     * 功能概述：根据账单编号更新缴费账单的状态、已缴金额和缴费时间
     * @param {Integer} billId - 账单编号
     * @param {Integer} status - 账单状态（0-未缴费，1-已缴费，2-逾期）
     * @param {BigDecimal} paidAmount - 已缴金额
     * @param {LocalDateTime} paidTime - 缴费时间
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 更新账单状态方法，接收账单编号、账单状态、已缴金额和缴费时间参数，返回更新的记录数（SQL在XML映射文件中定义）
    int updateBillStatus(@Param("billId") Integer billId, @Param("status") Integer status, 
                        @Param("paidAmount") java.math.BigDecimal paidAmount, 
                        @Param("paidTime") java.time.LocalDateTime paidTime);

    /**
     * 根据账单ID更新支付状态(用于统一支付)
     * 功能概述：根据账单编号更新缴费账单的支付状态和缴费时间，用于统一支付系统
     * @param {Integer} billId - 账单编号
     * @param {Integer} status - 账单状态（0-未缴费，1-已缴费，2-逾期）
     * @param {LocalDateTime} paidTime - 缴费时间
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 根据账单ID更新支付状态方法（用于统一支付），接收账单编号、账单状态和缴费时间参数，返回更新的记录数（SQL在XML映射文件中定义）
    int updatePaymentBillStatus(@Param("billId") Integer billId, @Param("status") Integer status,
                               @Param("paidTime") java.time.LocalDateTime paidTime);
    
    /**
     * 创建缴费记录
     * 功能概述：向数据库中插入一条缴费记录，记录缴费的详细信息
     * @param {Integer} userId - 用户编号
     * @param {Integer} billId - 账单编号
     * @param {String} paymentMethod - 支付方式
     * @param {BigDecimal} paymentAmount - 支付金额
     * @param {String} transactionId - 交易号
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 创建缴费记录方法，接收用户编号、账单编号、支付方式、支付金额和交易号参数，返回插入的记录数（SQL在XML映射文件中定义）
    int createPaymentRecord(@Param("userId") Integer userId, @Param("billId") Integer billId,
                           @Param("paymentMethod") String paymentMethod, @Param("paymentAmount") java.math.BigDecimal paymentAmount,
                           @Param("transactionId") String transactionId);
    
    /**
     * 获取用户缴费统计
     * 功能概述：根据用户编号从数据库中统计该用户的缴费信息（如总账单数、已缴费数、未缴费数等）
     * @param {Integer} userId - 用户编号
     * @return {Map<String, Object>} 返回缴费统计结果（包含总账单数、已缴费数、未缴费数等）
     */
    // 获取用户缴费统计方法，接收用户编号参数，返回缴费统计结果（SQL在XML映射文件中定义）
    java.util.Map<String, Object> getPaymentStats(@Param("userId") Integer userId);
    
    /**
     * 自动更新过期账单状态
     * 功能概述：将状态为0（待缴费）且到期日期已过的账单自动更新为2（逾期）
     * @param {Integer} userId - 用户编号（可选，如果为null则更新所有用户的过期账单）
     * @return {int} 返回更新的记录数
     */
    // 自动更新过期账单状态方法，接收用户编号参数（可选），返回更新的记录数（SQL在XML映射文件中定义）
    int updateOverdueBills(@Param("userId") Integer userId);
}
