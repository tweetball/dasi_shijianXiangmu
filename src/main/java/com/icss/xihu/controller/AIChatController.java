// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入Spark AI服务接口
import com.icss.xihu.service.SparkAIService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的请求映射相关注解
import org.springframework.web.bind.annotation.*;

// 导入HashMap集合类
import java.util.HashMap;
// 导入Map接口
import java.util.Map;

/**
 * AI聊天控制器
 * 提供AI大模型聊天接口
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/ai"
@RequestMapping("/ai")
// AI聊天控制器类，处理AI相关的HTTP请求
public class AIChatController {

    // 自动注入Spark AI服务，required=false表示如果找不到该Bean也不会报错
    @Autowired(required = false)
    // Spark AI服务对象，用于调用AI服务
    private SparkAIService sparkAIService;

    /**
     * AI聊天接口
     * 支持多种AI大模型（可配置）
     */
    // 处理POST请求，路径为"/chat"
    @PostMapping("/chat")
    // 返回JSON格式的响应体，而不是视图
    @ResponseBody
    // AI聊天处理方法，接收用户消息并返回AI回复
    public Map<String, Object> chat(@RequestBody Map<String, String> request) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch捕获异常
        try {
            // 从请求体中获取用户消息内容
            String message = request.get("message");
            // 判断消息是否为空或只包含空白字符
            if (message == null || message.trim().isEmpty()) {
                // 设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "消息内容不能为空");
                // 返回结果
                return result;
            }

            // 调用AI大模型API（这里使用模拟响应，实际需要配置具体的AI服务）
            // 调用callAIModel方法获取AI回复
            String aiResponse = callAIModel(message);
            
            // 设置返回结果为成功
            result.put("success", true);
            // 设置AI回复消息
            result.put("message", aiResponse);
            
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "AI服务暂时不可用，请稍后再试：" + e.getMessage());
        }
        
        // 返回结果Map
        return result;
    }

    /**
     * 调用AI大模型
     * 优先使用讯飞Spark AI，如果不可用则使用模拟响应
     */
    // 私有方法，调用AI模型获取回复
    private String callAIModel(String userMessage) {
        // 使用try-catch捕获异常
        try {
            // 优先使用讯飞Spark AI
            // 判断Spark AI服务是否已注入
            if (sparkAIService != null) {
                // 使用try-catch捕获Spark AI调用异常
                try {
                    // 调用Spark AI服务的chat方法获取回复
                    return sparkAIService.chat(userMessage);
                // 捕获Spark AI调用异常
                } catch (Exception e) {
                    // 打印错误信息到控制台
                    System.err.println("Spark AI调用失败，使用模拟响应: " + e.getMessage());
                    // 打印异常堆栈信息
                    e.printStackTrace();
                    // 如果Spark AI调用失败，降级到模拟响应
                    // 调用模拟响应方法
                    return generateMockResponse(userMessage);
                }
            // 如果没有配置Spark AI服务
            } else {
                // 如果没有配置Spark AI服务，使用模拟响应
                // 调用模拟响应方法
                return generateMockResponse(userMessage);
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 抛出运行时异常
            throw new RuntimeException("AI服务调用失败", e);
        }
    }

    /**
     * 生成模拟响应（用于演示）
     * 实际使用时应该替换为真实的AI API调用
     */
    // 私有方法，根据用户消息生成模拟回复
    private String generateMockResponse(String userMessage) {
        // 简单的关键词匹配，生成基础回复
        // 将用户消息转换为小写，便于匹配
        String lowerMessage = userMessage.toLowerCase();
        
        // 判断消息中是否包含"你好"或"hello"
        if (lowerMessage.contains("你好") || lowerMessage.contains("hello")) {
            // 返回问候语回复
            return "您好！我是游市生活的AI助手，很高兴为您服务。我可以帮您解答关于住宿、美食、出行、购物等方面的问题。有什么可以帮您的吗？";
        // 判断消息中是否包含"酒店"或"住宿"
        } else if (lowerMessage.contains("酒店") || lowerMessage.contains("住宿")) {
            // 返回住宿服务相关回复
            return "关于住宿服务，我们平台提供精选的优质酒店和民宿。您可以访问住宿服务页面查看详细信息，或者告诉我您的具体需求，我可以为您推荐合适的住宿选择。";
        // 判断消息中是否包含"美食"或"餐厅"
        } else if (lowerMessage.contains("美食") || lowerMessage.contains("餐厅")) {
            // 返回美食服务相关回复
            return "美食服务是我们平台的一大特色！我们汇聚了当地各种特色餐厅和美食。您可以浏览美食服务页面，或者告诉我您的口味偏好，我可以为您推荐合适的餐厅。";
        // 判断消息中是否包含"出行"或"旅游"
        } else if (lowerMessage.contains("出行") || lowerMessage.contains("旅游")) {
            // 返回出行服务相关回复
            return "出行服务可以帮助您规划旅游路线、查看景点信息等。您可以访问出行服务页面，或者告诉我您想去的地方，我可以为您提供相关的旅游建议。";
        // 判断消息中是否包含"购物"
        } else if (lowerMessage.contains("购物")) {
            // 返回购物服务相关回复
            return "购物服务提供本地优质商户的商品。您可以浏览购物服务页面，或者告诉我您想购买什么，我可以为您推荐相关的商品和商家。";
        // 判断消息中是否包含"订单"或"支付"
        } else if (lowerMessage.contains("订单") || lowerMessage.contains("支付")) {
            // 返回订单支付相关回复
            return "关于订单和支付，您可以在顶部导航栏的\"我的订单\"中查看所有订单信息。如果遇到支付问题，请告诉我具体情况，我会尽力帮助您解决。";
        // 判断消息中是否包含"帮助"或"问题"
        } else if (lowerMessage.contains("帮助") || lowerMessage.contains("问题")) {
            // 返回帮助相关回复
            return "我很乐意帮助您！游市生活平台提供住宿、美食、出行、购物、生活缴费等服务。您可以询问任何相关问题，我会尽力为您解答。";
        // 如果都不匹配，返回默认回复
        } else {
            // 返回默认回复
            return "感谢您的提问！作为游市生活的AI助手，我主要可以帮助您了解平台的各种服务，包括住宿、美食、出行、购物等。如果您有具体的问题，请告诉我，我会尽力为您解答。";
        }
    }

    /**
     * 健康检查接口
     */
    // 处理GET请求，路径为"/health"
    @GetMapping("/health")
    // 返回JSON格式的响应体
    @ResponseBody
    // 健康检查方法，用于检查AI服务是否正常运行
    public Map<String, Object> health() {
        // 创建结果Map对象
        Map<String, Object> result = new HashMap<>();
        // 设置状态为"ok"
        result.put("status", "ok");
        // 设置服务名称为"AI Chat Service"
        result.put("service", "AI Chat Service");
        // 返回结果
        return result;
    }
}

