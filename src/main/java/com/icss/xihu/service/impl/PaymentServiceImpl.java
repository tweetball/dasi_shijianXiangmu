/**
 * 生活缴费服务实现类
 * 功能概述：实现PaymentService接口，提供生活缴费相关的业务逻辑实现，包括缴费类型查询、账单查询、缴费处理、统计等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入缴费Mapper接口
import com.icss.xihu.mapper.PaymentMapper;
// 导入缴费账单实体类
import com.icss.xihu.model.PaymentBill;
// 导入缴费类型实体类
import com.icss.xihu.model.PaymentType;
// 导入缴费服务接口
import com.icss.xihu.service.PaymentService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;
// 导入Spring的事务注解，用于声明式事务管理
import org.springframework.transaction.annotation.Transactional;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 生活缴费服务实现类
 * 功能概述：实现PaymentService接口，提供生活缴费相关的业务逻辑实现，包括缴费类型查询、账单查询、缴费处理、统计等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 生活缴费服务实现类，实现PaymentService接口
public class PaymentServiceImpl implements PaymentService {
    
    // 自动注入缴费Mapper，Spring容器会自动查找并注入PaymentMapper的实现类
    @Autowired
    // 缴费Mapper对象，用于调用数据库操作方法
    private PaymentMapper paymentMapper;
    
    /**
     * 获取所有缴费类型
     * 功能概述：调用Mapper层方法，查询所有可用的缴费类型信息
     * @return {List<PaymentType>} 返回所有缴费类型的列表
     */
    // 重写接口中的getAllPaymentTypes方法
    @Override
    // 获取所有缴费类型方法，返回所有缴费类型的列表
    public List<PaymentType> getAllPaymentTypes() {
        // 调用Mapper层的getAllPaymentTypes方法，查询所有缴费类型信息并返回
        return paymentMapper.getAllPaymentTypes();
    }
    
    /**
     * 根据用户ID获取缴费账单
     * 功能概述：调用Mapper层方法，根据用户编号查询该用户的所有缴费账单信息
     * 注意：过期账单状态由定时任务自动更新，不在此处更新以提高查询性能
     * @param {Integer} userId - 用户编号
     * @return {List<PaymentBill>} 返回该用户的所有账单列表
     */
    // 重写接口中的getBillsByUserId方法
    @Override
    // 根据用户ID获取缴费账单方法，接收用户编号参数，返回该用户的所有账单列表
    public List<PaymentBill> getBillsByUserId(Integer userId) {
        // 调用Mapper层的getBillsByUserId方法，根据用户编号查询该用户的所有账单信息并返回
        // 注意：过期账单状态由定时任务自动更新，不在此处更新以提高查询性能
        return paymentMapper.getBillsByUserId(userId);
    }
    
    /**
     * 根据用户ID和状态获取缴费账单
     * 功能概述：调用Mapper层方法，根据用户编号和缴费状态查询该用户指定状态的所有缴费账单信息
     * 注意：过期账单状态由定时任务自动更新，不在此处更新以提高查询性能
     * @param {Integer} userId - 用户编号
     * @param {Integer} status - 缴费状态（0-待缴费，1-已缴费，2-逾期）
     * @return {List<PaymentBill>} 返回该用户指定状态的所有账单列表
     */
    // 重写接口中的getBillsByUserIdAndStatus方法
    @Override
    // 根据用户ID和状态获取缴费账单方法，接收用户编号和缴费状态参数，返回该用户指定状态的所有账单列表
    public List<PaymentBill> getBillsByUserIdAndStatus(Integer userId, Integer status) {
        // 调用Mapper层的getBillsByUserIdAndStatus方法，根据用户编号和缴费状态查询该用户指定状态的所有账单信息并返回
        // 注意：过期账单状态由定时任务自动更新，不在此处更新以提高查询性能
        return paymentMapper.getBillsByUserIdAndStatus(userId, status);
    }
    
    /**
     * 根据账单ID获取账单详情
     * 功能概述：调用Mapper层方法，根据账单编号查询指定缴费账单的详细信息
     * @param {Integer} billId - 账单编号
     * @return {PaymentBill} 返回账单对象，如果不存在则返回null
     */
    // 重写接口中的getBillById方法
    @Override
    // 根据账单ID获取账单详情方法，接收账单编号参数，返回账单对象
    public PaymentBill getBillById(Integer billId) {
        // 调用Mapper层的getBillById方法，根据账单编号查询账单信息并返回
        return paymentMapper.getBillById(billId);
    }
    
    /**
     * 处理缴费
     * 功能概述：处理缴费账单的支付，更新账单状态和支付信息，创建缴费记录（使用事务管理）
     * @param {Integer} billId - 账单编号
     * @param {String} paymentMethod - 支付方式（微信、支付宝、银行卡等）
     * @param {String} transactionId - 交易流水号
     * @return {boolean} 返回是否缴费成功（true-成功，false-失败）
     */
    // 重写接口中的processPayment方法
    @Override
    // 使用Spring的事务注解，确保方法在事务中执行，如果发生异常则回滚
    @Transactional
    // 处理缴费方法，接收账单编号、支付方式和交易流水号参数，返回是否缴费成功
    public boolean processPayment(Integer billId, String paymentMethod, String transactionId) {
        // 使用try-catch块捕获异常
        try {
            // 获取账单信息
            // 调用Mapper层的getBillById方法，根据账单编号查询账单信息
            PaymentBill bill = paymentMapper.getBillById(billId);
            // 如果账单不存在或已缴费，返回false
            if (bill == null || bill.getBillStatus() == 1) {
                return false; // 账单不存在或已缴费
            }
            
            // 更新账单状态
            // 调用Mapper层的updateBillStatus方法，更新账单状态为已缴费（1），设置缴费金额和缴费时间
            int updateResult = paymentMapper.updateBillStatus(
                billId,                    // 账单编号
                1,                         // 已缴费状态
                bill.getBillAmount(),      // 缴费金额
                LocalDateTime.now()        // 缴费时间
            );
            
            // 如果更新成功（更新记录数大于0），创建缴费记录
            if (updateResult > 0) {
                // 创建缴费记录
                // 调用Mapper层的createPaymentRecord方法，创建缴费记录，记录缴费的详细信息
                paymentMapper.createPaymentRecord(
                    bill.getUserId(),      // 用户编号
                    billId,                // 账单编号
                    paymentMethod,         // 支付方式
                    bill.getBillAmount(),  // 缴费金额
                    transactionId          // 交易流水号
                );
                // 返回true表示缴费成功
                return true;
            }
            
            // 如果更新失败，返回false
            return false;
        } catch (Exception e) {
            // 如果发生异常，打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示缴费失败
            return false;
        }
    }
    
    /**
     * 获取用户缴费统计
     * 功能概述：调用Mapper层方法，根据用户编号统计该用户的缴费信息（总账单数、已缴费数、待缴费数等）
     * 注意：过期账单状态由定时任务自动更新，不在此处更新以提高查询性能
     * @param {Integer} userId - 用户编号
     * @return {Map<String, Object>} 返回缴费统计结果（包含总账单数、已缴费数、待缴费数等）
     */
    // 重写接口中的getPaymentStats方法
    @Override
    // 获取用户缴费统计方法，接收用户编号参数，返回缴费统计结果
    public Map<String, Object> getPaymentStats(Integer userId) {
        // 调用Mapper层的getPaymentStats方法，根据用户编号统计该用户的缴费信息并返回
        // 注意：过期账单状态由定时任务自动更新，不在此处更新以提高查询性能
        return paymentMapper.getPaymentStats(userId);
    }
}
