/**
 * 短信服务接口
 * 功能概述：定义短信相关的业务逻辑方法，包括发送验证码短信、发送通知短信等
 */
// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

/**
 * 短信服务接口
 * 功能概述：定义短信相关的业务逻辑方法，包括发送验证码短信、发送通知短信等
 * 用于发送各种类型的短信验证码
 */
// 短信服务接口，定义短信相关的业务逻辑方法
public interface SmsService {
    
    /**
     * 发送验证码短信
     * 功能概述：根据手机号、验证码和短信类型发送验证码短信
     * @param {String} phone - 手机号
     * @param {String} code - 验证码
     * @param {String} type - 短信类型（register注册、login登录、reset重置密码等）
     * @return {boolean} 返回发送结果（true-成功，false-失败）
     */
    // 发送验证码短信方法，接收手机号、验证码和短信类型参数，返回发送结果
    boolean sendSmsCode(String phone, String code, String type);
    
    /**
     * 发送通知短信
     * 功能概述：根据手机号和消息内容发送通知短信
     * @param {String} phone - 手机号
     * @param {String} message - 消息内容
     * @return {boolean} 返回发送结果（true-成功，false-失败）
     */
    // 发送通知短信方法，接收手机号和消息内容参数，返回发送结果
    boolean sendNotification(String phone, String message);
}
