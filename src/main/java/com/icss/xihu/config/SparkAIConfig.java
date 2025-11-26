// 定义包路径，标识该类属于com.icss.xihu.config包
package com.icss.xihu.config;

// 导入Spring Boot的配置属性绑定注解
import org.springframework.boot.context.properties.ConfigurationProperties;
// 导入Spring的配置类注解
import org.springframework.context.annotation.Configuration;

/**
 * 讯飞Spark AI配置类
 * 用于读取application.properties中spark.ai开头的配置项
 */
// 标识该类为Spring配置类，会被Spring容器扫描并加载
@Configuration
// 绑定配置属性，前缀为"spark.ai"，会自动读取application.properties中的spark.ai.*配置
@ConfigurationProperties(prefix = "spark.ai")
// 讯飞Spark AI配置类，封装AI服务的配置信息
public class SparkAIConfig {
    
    // AI服务是否启用的标志，默认为true
    private boolean enabled = true;
    // 讯飞Spark AI的应用ID
    private String appid;
    // 讯飞Spark AI的API密钥
    private String apiSecret;
    // 讯飞Spark AI的API Key
    private String apiKey;
    // WebSocket服务的URL地址
    private String wsUrl;
    // AI模型的域名，默认为"spark-x"
    private String domain = "spark-x";

    // 获取AI服务是否启用的状态
    public boolean isEnabled() {
        // 返回enabled字段的值
        return enabled;
    }

    // 设置AI服务是否启用
    public void setEnabled(boolean enabled) {
        // 将参数值赋给enabled字段
        this.enabled = enabled;
    }

    // 获取应用ID
    public String getAppid() {
        // 返回appid字段的值
        return appid;
    }

    // 设置应用ID
    public void setAppid(String appid) {
        // 将参数值赋给appid字段
        this.appid = appid;
    }

    // 获取API密钥
    public String getApiSecret() {
        // 返回apiSecret字段的值
        return apiSecret;
    }

    // 设置API密钥
    public void setApiSecret(String apiSecret) {
        // 将参数值赋给apiSecret字段
        this.apiSecret = apiSecret;
    }

    // 获取API Key
    public String getApiKey() {
        // 返回apiKey字段的值
        return apiKey;
    }

    // 设置API Key
    public void setApiKey(String apiKey) {
        // 将参数值赋给apiKey字段
        this.apiKey = apiKey;
    }

    // 获取WebSocket服务URL
    public String getWsUrl() {
        // 返回wsUrl字段的值
        return wsUrl;
    }

    // 设置WebSocket服务URL
    public void setWsUrl(String wsUrl) {
        // 将参数值赋给wsUrl字段
        this.wsUrl = wsUrl;
    }

    // 获取AI模型域名
    public String getDomain() {
        // 返回domain字段的值
        return domain;
    }

    // 设置AI模型域名
    public void setDomain(String domain) {
        // 将参数值赋给domain字段
        this.domain = domain;
    }
}

