/**
 * 真实短信服务实现类
 * 功能概述：实现SmsService接口，提供真实短信发送功能，支持集成阿里云、腾讯云、华为云等短信服务提供商
 * 
 * 这个类展示了如何集成真实的短信服务提供商
 * 支持的短信服务商包括：阿里云短信、腾讯云短信、华为云短信等
 * 
 * 使用步骤：
 * 1. 注册短信服务商账号
 * 2. 获取AccessKey、SecretKey等认证信息
 * 3. 添加相应的SDK依赖到pom.xml
 * 4. 配置短信模板
 * 5. 修改application.properties添加配置
 * 6. 取消注释下面的代码并实现具体逻辑
 * 
 * 注意：此实现默认被注释，需要根据实际选择的短信服务商进行配置
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入短信服务接口
import com.icss.xihu.service.SmsService;
// 导入Spring的配置值注解，用于从配置文件中读取值
import org.springframework.beans.factory.annotation.Value;
// 导入Spring的主服务注解，用于指定优先使用的服务实现
import org.springframework.context.annotation.Primary;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

/**
 * 真实短信服务实现示例
 * 
 * 这个类展示了如何集成真实的短信服务提供商
 * 支持的短信服务商包括：阿里云短信、腾讯云短信、华为云短信等
 * 
 * 使用步骤：
 * 1. 注册短信服务商账号
 * 2. 获取AccessKey、SecretKey等认证信息
 * 3. 添加相应的SDK依赖到pom.xml
 * 4. 配置短信模板
 * 5. 修改application.properties添加配置
 * 6. 取消注释下面的代码并实现具体逻辑
 * 
 * 注意：此实现默认被注释，需要根据实际选择的短信服务商进行配置
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean（当前被注释）
//@Service
// 使用Spring的主服务注解，取消注释后，此实现将优先于MockSmsServiceImpl被使用（当前被注释）
//@Primary  // 取消注释后，此实现将优先于MockSmsServiceImpl被使用
// 真实短信服务实现类，实现SmsService接口
public class RealSmsServiceImpl implements SmsService {

    // 从配置文件读取短信服务配置
    // 使用@Value注解从配置文件中读取sms.accessKey配置值，如果不存在则使用空字符串作为默认值
    @Value("${sms.accessKey:}")
    // 短信服务的访问密钥（AccessKey），用于身份认证
    private String accessKey;
    
    // 使用@Value注解从配置文件中读取sms.secretKey配置值，如果不存在则使用空字符串作为默认值
    @Value("${sms.secretKey:}")
    // 短信服务的密钥（SecretKey），用于身份认证
    private String secretKey;
    
    // 使用@Value注解从配置文件中读取sms.signName配置值，如果不存在则使用"游市生活"作为默认值
    @Value("${sms.signName:游市生活}")
    // 短信签名名称，用于标识短信发送方
    private String signName;
    
    // 使用@Value注解从配置文件中读取sms.templateCode.register配置值，如果不存在则使用空字符串作为默认值
    @Value("${sms.templateCode.register:}")
    // 注册验证码短信模板代码
    private String registerTemplateCode;
    
    // 使用@Value注解从配置文件中读取sms.templateCode.login配置值，如果不存在则使用空字符串作为默认值
    @Value("${sms.templateCode.login:}")
    // 登录验证码短信模板代码
    private String loginTemplateCode;
    
    // 使用@Value注解从配置文件中读取sms.templateCode.reset配置值，如果不存在则使用空字符串作为默认值
    @Value("${sms.templateCode.reset:}")
    // 重置密码验证码短信模板代码
    private String resetTemplateCode;

    /**
     * 发送验证码短信（真实短信服务）
     * 功能概述：通过真实的短信服务提供商发送验证码短信，支持阿里云、腾讯云等短信服务商
     * @param {String} phone - 手机号
     * @param {String} code - 验证码
     * @param {String} type - 短信类型（register注册、login登录、reset重置密码等）
     * @return {boolean} 返回发送结果（true-成功，false-失败）
     */
    // 重写接口中的sendSmsCode方法
    @Override
    // 发送验证码短信方法（真实短信服务），接收手机号、验证码和短信类型参数，返回发送结果
    public boolean sendSmsCode(String phone, String code, String type) {
        // 使用try-catch捕获异常
        try {
            // 以下是阿里云短信服务的集成示例
            // 需要在pom.xml中添加阿里云短信SDK依赖：
            /*
            <dependency>
                <groupId>com.aliyun</groupId>
                <artifactId>dysmsapi20170525</artifactId>
                <version>2.0.24</version>
            </dependency>
            */
            
            /*
            // 1. 创建短信客户端
            // 创建阿里云短信服务配置对象
            Config config = new Config()
                .setAccessKeyId(accessKey)        // 设置访问密钥ID
                .setAccessKeySecret(secretKey)    // 设置访问密钥Secret
                .setEndpoint("dysmsapi.aliyuncs.com");  // 设置服务端点

            // 创建阿里云短信服务客户端对象
            Client client = new Client(config);

            // 2. 构建请求
            // 创建发送短信请求对象
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)           // 设置接收短信的手机号
                .setSignName(signName)            // 设置短信签名
                .setTemplateCode(getTemplateCode(type))  // 设置短信模板代码（根据类型获取）
                .setTemplateParam("{\"code\":\"" + code + "\"}");  // 设置模板参数（验证码）

            // 3. 发送短信
            // 调用客户端发送短信方法，发送短信请求，返回发送短信响应对象
            SendSmsResponse response = client.sendSms(sendSmsRequest);

            // 4. 判断发送结果
            // 判断响应体中的状态码是否为"OK"（"OK"表示发送成功）
            if ("OK".equals(response.getBody().getCode())) {
                // 打印短信发送成功信息到控制台
                System.out.println("短信发送成功: " + phone + " - " + code);
                // 返回true表示发送成功
                return true;
            } else {
                // 打印短信发送失败信息到控制台（包含错误消息）
                System.err.println("短信发送失败: " + response.getBody().getMessage());
                // 返回false表示发送失败
                return false;
            }
            */
            
            // 腾讯云短信服务示例
            /*
            // 需要在pom.xml中添加腾讯云短信SDK依赖：
            <dependency>
                <groupId>com.tencentcloudapi</groupId>
                <artifactId>tencentcloud-sdk-java</artifactId>
                <version>3.1.423</version>
            </dependency>
            
            // 实例化一个认证对象
            // 创建腾讯云认证对象，传入访问密钥ID和访问密钥Secret
            Credential cred = new Credential(accessKey, secretKey);
            
            // 实例化一个client选项
            // 创建客户端配置对象
            ClientProfile clientProfile = new ClientProfile();
            // 设置HTTP配置对象
            clientProfile.setHttpProfile(new HttpProfile());
            
            // 实例化要请求产品的client对象
            // 创建腾讯云短信服务客户端对象，传入认证对象、地域和客户端配置
            SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
            
            // 实例化一个请求对象
            // 创建发送短信请求对象
            SendSmsRequest req = new SendSmsRequest();
            // 设置接收短信的手机号数组
            req.setPhoneNumberSet(new String[]{phone});
            // 设置短信应用ID
            req.setSmsSdkAppId("短信应用ID");
            // 设置短信模板ID（根据类型获取）
            req.setTemplateId(getTemplateCode(type));
            // 设置模板参数数组（验证码）
            req.setTemplateParamSet(new String[]{code});
            // 设置短信签名
            req.setSignName(signName);
            
            // 返回的resp是一个SendSmsResponse的实例
            // 调用客户端发送短信方法，发送短信请求，返回发送短信响应对象
            SendSmsResponse resp = client.SendSms(req);
            // 判断响应中的发送状态码是否为"Ok"（"Ok"表示发送成功），返回判断结果
            return "Ok".equals(resp.getSendStatusSet()[0].getCode());
            */
            
            // 当前返回false，因为没有实际配置短信服务
            // 打印真实短信服务未配置提示信息到控制台
            System.out.println("真实短信服务未配置，请按照注释说明进行配置");
            // 返回false表示发送失败（因为未配置真实短信服务）
            return false;
            
        // 捕获所有异常
        } catch (Exception e) {
            // 打印短信发送异常信息到控制台
            System.err.println("短信发送异常: " + e.getMessage());
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示发送失败
            return false;
        }
    }

    /**
     * 发送通知短信（真实短信服务）
     * 功能概述：通过真实的短信服务提供商发送通知短信，可以使用自定义模板或直接发送文本消息
     * @param {String} phone - 手机号
     * @param {String} message - 消息内容
     * @return {boolean} 返回发送结果（true-成功，false-失败）
     */
    // 重写接口中的sendNotification方法
    @Override
    // 发送通知短信方法（真实短信服务），接收手机号和消息内容参数，返回发送结果
    public boolean sendNotification(String phone, String message) {
        // 实现通知短信发送逻辑
        // 这里可以使用自定义模板或直接发送文本消息
        // 打印真实通知短信服务未配置提示信息到控制台（包含手机号和消息内容）
        System.out.println("真实通知短信服务未配置: " + phone + " - " + message);
        // 返回false表示发送失败（因为未配置真实短信服务）
        return false;
    }
    
    /**
     * 根据类型获取短信模板代码（私有方法）
     * 功能概述：根据短信类型返回对应的短信模板代码，用于发送不同类型的验证码短信
     * @param {String} type - 短信类型（register注册、login登录、reset重置密码等）
     * @return {String} 返回对应的短信模板代码
     */
    // 私有方法，根据类型获取短信模板代码
    private String getTemplateCode(String type) {
        // 根据短信类型，使用switch语句返回对应的短信模板代码
        switch (type) {
            // 如果类型为"register"，返回注册验证码短信模板代码
            case "register":
                return registerTemplateCode;
            // 如果类型为"login"，返回登录验证码短信模板代码
            case "login":
                return loginTemplateCode;
            // 如果类型为"reset"，返回重置密码验证码短信模板代码
            case "reset":
                return resetTemplateCode;
            // 默认情况，返回注册验证码短信模板代码
            default:
                return registerTemplateCode;
        }
    }
}

/*
=== 配置文件示例 (application.properties) ===

# 阿里云短信配置
sms.accessKey=your_access_key
sms.secretKey=your_secret_key
sms.signName=游市生活
sms.templateCode.register=SMS_123456789
sms.templateCode.login=SMS_123456790
sms.templateCode.reset=SMS_123456791

# 腾讯云短信配置
# sms.accessKey=your_secret_id
# sms.secretKey=your_secret_key
# sms.signName=游市生活
# sms.templateCode.register=123456
# sms.templateCode.login=123457
# sms.templateCode.reset=123458

=== 短信模板示例 ===

注册验证码模板：
【游市生活】您的注册验证码是：${code}，有效期5分钟，请勿泄露给他人。

登录验证码模板：
【游市生活】您的登录验证码是：${code}，有效期5分钟，请勿泄露给他人。

重置密码验证码模板：
【游市生活】您的重置密码验证码是：${code}，有效期5分钟，请勿泄露给他人。

*/
