/**
 * 模拟短信服务实现类
 * 功能概述：实现SmsService接口，提供模拟短信发送功能，用于开发和测试环境，不会真正发送短信，会在控制台打印短信内容
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入短信服务接口
import com.icss.xihu.service.SmsService;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

/**
 * 模拟短信服务实现
 * 用于开发和测试环境，不会真正发送短信
 * 会在控制台打印短信内容，模拟真实发送过程
 * 功能概述：实现SmsService接口，提供模拟短信发送功能，用于开发和测试环境，不会真正发送短信，会在控制台打印短信内容
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 模拟短信服务实现类，实现SmsService接口
public class MockSmsServiceImpl implements SmsService {

    /**
     * 发送验证码短信（模拟）
     * 功能概述：模拟发送验证码短信，包括模拟发送延迟、打印短信内容到控制台、返回发送成功结果
     * @param {String} phone - 手机号
     * @param {String} code - 验证码
     * @param {String} type - 短信类型（register注册、login登录、reset重置密码等）
     * @return {boolean} 返回发送结果（true-成功，false-失败）
     */
    // 重写接口中的sendSmsCode方法
    @Override
    // 发送验证码短信方法（模拟），接收手机号、验证码和短信类型参数，返回发送结果
    public boolean sendSmsCode(String phone, String code, String type) {
        // 使用try-catch捕获异常
        try {
            // 模拟短信发送延迟
            // 使当前线程休眠500毫秒，模拟短信发送的网络延迟
            Thread.sleep(500);
            
            // 打印模拟短信内容
            // 打印分隔线到控制台
            System.out.println("=====================================");
            // 打印模拟短信发送标识到控制台
            System.out.println("【游市生活】模拟短信发送");
            // 打印手机号码到控制台
            System.out.println("手机号码: " + phone);
            // 打印验证码到控制台
            System.out.println("验证码: " + code);
            // 打印短信类型描述到控制台（通过getTypeDescription方法获取类型描述）
            System.out.println("短信类型: " + getTypeDescription(type));
            // 打印短信内容到控制台（通过buildSmsContent方法构建短信内容）
            System.out.println("短信内容: " + buildSmsContent(code, type));
            // 打印发送时间到控制台（当前时间）
            System.out.println("发送时间: " + java.time.LocalDateTime.now());
            // 打印分隔线到控制台
            System.out.println("=====================================");
            
            // 模拟发送成功
            // 返回true表示发送成功
            return true;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印模拟短信发送失败信息到控制台
            System.err.println("模拟短信发送失败: " + e.getMessage());
            // 返回false表示发送失败
            return false;
        }
    }

    /**
     * 发送通知短信（模拟）
     * 功能概述：模拟发送通知短信，包括模拟发送延迟、打印通知内容到控制台、返回发送成功结果
     * @param {String} phone - 手机号
     * @param {String} message - 消息内容
     * @return {boolean} 返回发送结果（true-成功，false-失败）
     */
    // 重写接口中的sendNotification方法
    @Override
    // 发送通知短信方法（模拟），接收手机号和消息内容参数，返回发送结果
    public boolean sendNotification(String phone, String message) {
        // 使用try-catch捕获异常
        try {
            // 模拟短信发送延迟
            // 使当前线程休眠500毫秒，模拟短信发送的网络延迟
            Thread.sleep(500);
            
            // 打印模拟短信内容
            // 打印分隔线到控制台
            System.out.println("=====================================");
            // 打印模拟通知短信标识到控制台
            System.out.println("【游市生活】模拟通知短信");
            // 打印手机号码到控制台
            System.out.println("手机号码: " + phone);
            // 打印通知内容到控制台
            System.out.println("通知内容: " + message);
            // 打印发送时间到控制台（当前时间）
            System.out.println("发送时间: " + java.time.LocalDateTime.now());
            // 打印分隔线到控制台
            System.out.println("=====================================");
            
            // 返回true表示发送成功
            return true;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印模拟通知短信发送失败信息到控制台
            System.err.println("模拟通知短信发送失败: " + e.getMessage());
            // 返回false表示发送失败
            return false;
        }
    }
    
    /**
     * 构建短信内容（私有方法）
     * 功能概述：根据验证码和短信类型构建完整的短信内容，包含验证码、操作类型、有效期等信息
     * @param {String} code - 验证码
     * @param {String} type - 短信类型（register注册、login登录、reset重置密码等）
     * @return {String} 返回构建的短信内容字符串
     */
    // 私有方法，构建短信内容
    private String buildSmsContent(String code, String type) {
        // 调用getTypeDescription方法获取类型描述（如"注册"、"登录"、"重置密码"等）
        String action = getTypeDescription(type);
        // 使用String.format方法格式化短信内容，包含操作类型、验证码、有效期等信息
        return String.format("【游市生活】您的%s验证码是：%s，有效期5分钟，请勿泄露给他人。如非本人操作，请忽略此短信。", action, code);
    }
    
    /**
     * 获取类型描述（私有方法）
     * 功能概述：根据短信类型返回对应的中文描述，用于构建短信内容
     * @param {String} type - 短信类型（register注册、login登录、reset重置密码等）
     * @return {String} 返回类型的中文描述（如"注册"、"登录"、"重置密码"、"验证"等）
     */
    // 私有方法，获取类型描述
    private String getTypeDescription(String type) {
        // 根据短信类型，使用switch语句返回对应的中文描述
        switch (type) {
            // 如果类型为"register"，返回"注册"
            case "register":
                return "注册";
            // 如果类型为"login"，返回"登录"
            case "login":
                return "登录";
            // 如果类型为"reset"，返回"重置密码"
            case "reset":
                return "重置密码";
            // 默认情况，返回"验证"
            default:
                return "验证";
        }
    }
}
