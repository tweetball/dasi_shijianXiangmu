// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入缴费账单实体类
import com.icss.xihu.model.PaymentBill;
// 导入缴费类型实体类
import com.icss.xihu.model.PaymentType;

// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 生活缴费服务接口
 * 功能概述：定义生活缴费相关的业务逻辑方法，包括缴费类型查询、账单查询、缴费处理、统计等
 */
// 生活缴费服务接口，定义生活缴费相关的业务逻辑方法
public interface PaymentService {
    
    /**
     * 获取所有缴费类型
     * 功能概述：获取所有可用的缴费类型列表（水费、电费、燃气费等）
     * @return 缴费类型列表
     */
    // 获取所有缴费类型方法，返回缴费类型列表
    List<PaymentType> getAllPaymentTypes();
    
    /**
     * 根据用户ID获取缴费账单
     * 功能概述：根据用户ID查询该用户的所有缴费账单列表
     * @param userId 用户ID
     * @return 缴费账单列表
     */
    // 根据用户ID获取缴费账单方法，接收用户ID参数
    List<PaymentBill> getBillsByUserId(Integer userId);
    
    /**
     * 根据用户ID和状态获取缴费账单
     * 功能概述：根据用户ID和缴费状态查询该用户的缴费账单列表
     * @param userId 用户ID
     * @param status 缴费状态（0=待缴费，1=已缴费）
     * @return 缴费账单列表
     */
    // 根据用户ID和状态获取缴费账单方法，接收用户ID和缴费状态参数
    List<PaymentBill> getBillsByUserIdAndStatus(Integer userId, Integer status);
    
    /**
     * 根据账单ID获取账单详情
     * 功能概述：根据账单ID查询缴费账单详细信息
     * @param billId 账单ID
     * @return 缴费账单对象，不存在返回null
     */
    // 根据账单ID获取账单详情方法，接收账单ID参数
    PaymentBill getBillById(Integer billId);
    
    /**
     * 处理缴费
     * 功能概述：处理缴费账单的支付，更新账单状态和支付信息
     * @param billId 账单ID
     * @param paymentMethod 支付方式（微信、支付宝、银行卡等）
     * @param transactionId 交易流水号
     * @return 是否缴费成功
     */
    // 处理缴费方法，接收账单ID、支付方式和交易流水号参数
    boolean processPayment(Integer billId, String paymentMethod, String transactionId);
    
    /**
     * 获取用户缴费统计
     * 功能概述：获取用户的缴费统计数据，包括账单总数、已缴费数量、待缴费数量等
     * @param userId 用户ID
     * @return 缴费统计数据Map
     */
    // 获取用户缴费统计方法，接收用户ID参数
    Map<String, Object> getPaymentStats(Integer userId);
}
