// 定义包路径，标识该类属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入Gson类，用于JSON序列化和反序列化
import com.google.gson.Gson;
// 导入JsonArray类，用于处理JSON数组
import com.google.gson.JsonArray;
// 导入JsonObject类，用于处理JSON对象
import com.google.gson.JsonObject;
// 导入Spark AI配置类
import com.icss.xihu.config.SparkAIConfig;
// 导入OkHttp的WebSocket相关类
import okhttp3.*;
// 导入NotNull注解，用于标记非空参数
import org.jetbrains.annotations.NotNull;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解
import org.springframework.stereotype.Service;

// 导入Mac类，用于HMAC加密
import javax.crypto.Mac;
// 导入SecretKeySpec类，用于密钥规范
import javax.crypto.spec.SecretKeySpec;
// 导入URL类，用于URL解析
import java.net.URL;
// 导入StandardCharsets类，用于字符编码
import java.nio.charset.StandardCharsets;
// 导入SimpleDateFormat类，用于日期格式化
import java.text.SimpleDateFormat;
// 导入Java工具类
import java.util.*;
// 导入CountDownLatch类，用于线程同步
import java.util.concurrent.CountDownLatch;
// 导入TimeUnit类，用于时间单位
import java.util.concurrent.TimeUnit;

/**
 * 讯飞Spark AI服务（WebSocket协议）
 * 功能概述：实现与讯飞Spark AI的WebSocket通信，包括鉴权、消息发送、响应解析、Markdown清理等
 */
// 标识该类为Spring服务类，会被Spring容器扫描并注册为Bean
@Service
// 讯飞Spark AI服务类，实现与讯飞Spark AI的WebSocket通信
public class SparkAIService {

    // 自动注入Spark AI配置，Spring容器会自动查找并注入SparkAIConfig的实现类
    @Autowired
    // Spark AI配置对象，用于获取API密钥、URL等配置信息
    private SparkAIConfig sparkAIConfig;

    // 创建Gson对象，用于JSON序列化和反序列化
    private final Gson gson = new Gson();
    // 创建OkHttpClient对象，用于WebSocket连接，设置读取超时时间为60秒
    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * 调用讯飞Spark API（WebSocket）
     * 功能概述：通过WebSocket协议与讯飞Spark AI进行通信，发送用户消息并接收AI响应，包括鉴权、连接建立、消息发送、响应解析等
     */
    // 调用讯飞Spark API方法，接收用户消息参数，可能抛出异常
    public String chat(String userMessage) throws Exception {
        // 判断Spark AI服务是否启用
        if (!sparkAIConfig.isEnabled()) {
            // 如果未启用，抛出运行时异常
            throw new RuntimeException("Spark AI服务未启用");
        }

        // 构建鉴权URL（使用https协议，因为URL类不支持wss协议）
        // 从配置中获取WebSocket URL
        String hostUrl = sparkAIConfig.getWsUrl();
        // 如果配置的是wss://，先转换为https://用于鉴权
        if (hostUrl.startsWith("wss://")) {
            // 将wss://替换为https://
            hostUrl = hostUrl.replace("wss://", "https://");
        }
        // 调用getAuthUrl方法生成带鉴权参数的URL
        String authUrl = getAuthUrl(hostUrl, sparkAIConfig.getApiKey(), sparkAIConfig.getApiSecret());
        // 将https://转换为wss://用于WebSocket连接
        String wsUrl = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        
        // 用于接收响应的结果
        // 创建StringBuilder对象，用于拼接AI响应的内容
        final StringBuilder responseContent = new StringBuilder();
        // 创建CountDownLatch对象，用于等待WebSocket响应完成（初始计数为1）
        final CountDownLatch latch = new CountDownLatch(1);
        // 创建boolean数组，用于标记是否发生错误（使用数组是为了在匿名内部类中可以修改）
        final boolean[] isError = {false};
        // 创建String数组，用于存储错误消息（使用数组是为了在匿名内部类中可以修改）
        final String[] errorMessage = {null};

        // 创建WebSocket请求
        // 使用Request.Builder构建WebSocket请求，URL为带鉴权参数的WebSocket URL
        Request request = new Request.Builder().url(wsUrl).build();
        // 创建WebSocket连接，并设置WebSocket监听器处理连接事件
        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            /**
             * WebSocket连接打开时的回调方法
             * 功能概述：当WebSocket连接成功建立时，构建请求JSON并发送给AI服务
             */
            // 重写onOpen方法，处理WebSocket连接打开事件
            @Override
            // WebSocket连接打开时的回调方法，接收WebSocket对象和响应对象
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                // 调用父类的onOpen方法
                super.onOpen(webSocket, response);
                // 打印连接成功信息到控制台
                System.out.println("WebSocket连接已建立");
                
                // 构建请求JSON
                // 使用try-catch捕获异常
                try {
                    // 调用buildRequestJson方法构建请求JSON对象
                    JsonObject requestJson = buildRequestJson(userMessage);
                    // 打印请求JSON到控制台
                    System.out.println("发送请求: " + requestJson.toString());
                    // 通过WebSocket发送请求JSON字符串
                    webSocket.send(requestJson.toString());
                // 捕获所有异常
                } catch (Exception e) {
                    // 打印异常堆栈信息
                    e.printStackTrace();
                    // 标记发生错误
                    isError[0] = true;
                    // 设置错误消息
                    errorMessage[0] = "构建请求失败: " + e.getMessage();
                    // 减少CountDownLatch计数，通知等待线程
                    latch.countDown();
                }
            }

            /**
             * WebSocket接收到消息时的回调方法
             * 功能概述：当接收到AI响应消息时，解析JSON并提取内容，检查是否完成
             */
            // 重写onMessage方法，处理WebSocket接收到消息事件
            @Override
            // WebSocket接收到消息时的回调方法，接收WebSocket对象和消息文本
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                // 打印接收到的消息到控制台
                System.out.println("收到消息: " + text);
                
                // 使用try-catch捕获异常
                try {
                    // 使用Gson将JSON字符串解析为JsonParse对象
                    JsonParse jsonParse = gson.fromJson(text, JsonParse.class);
                    
                    // 检查错误
                    // 判断响应头中的错误码是否为0（0表示成功）
                    if (jsonParse.header.code != 0) {
                        // 如果错误码不为0，标记发生错误
                        isError[0] = true;
                        // 设置错误消息，包含错误码和会话ID
                        errorMessage[0] = "错误码: " + jsonParse.header.code + ", SID: " + jsonParse.header.sid;
                        // 减少CountDownLatch计数，通知等待线程
                        latch.countDown();
                        // 返回，不再处理后续内容
                        return;
                    }
                    
                    // 提取内容
                    // 判断响应负载、选择项和文本内容是否存在
                    if (jsonParse.payload != null && jsonParse.payload.choices != null 
                            && jsonParse.payload.choices.text != null) {
                        // 遍历所有文本项
                        for (Text textItem : jsonParse.payload.choices.text) {
                            // 判断文本项的内容是否存在
                            if (textItem.content != null) {
                                // 将文本内容追加到响应内容中
                                responseContent.append(textItem.content);
                            }
                        }
                    }
                    
                    // 检查是否完成（status == 2表示完成）
                    // 判断响应头中的状态是否为2（2表示响应完成）
                    if (jsonParse.header.status == 2) {
                        // 打印响应完成信息到控制台
                        System.out.println("响应完成");
                        // 减少CountDownLatch计数，通知等待线程响应已完成
                        latch.countDown();
                    }
                // 捕获所有异常
                } catch (Exception e) {
                    // 打印异常堆栈信息
                    e.printStackTrace();
                    // 标记发生错误
                    isError[0] = true;
                    // 设置错误消息
                    errorMessage[0] = "解析响应失败: " + e.getMessage();
                    // 减少CountDownLatch计数，通知等待线程
                    latch.countDown();
                }
            }

            /**
             * WebSocket连接失败时的回调方法
             * 功能概述：当WebSocket连接失败时，记录错误信息并通知等待线程
             */
            // 重写onFailure方法，处理WebSocket连接失败事件
            @Override
            // WebSocket连接失败时的回调方法，接收WebSocket对象、异常对象和响应对象
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
                // 调用父类的onFailure方法
                super.onFailure(webSocket, t, response);
                // 打印连接失败信息到控制台
                System.err.println("WebSocket连接失败: " + t.getMessage());
                // 判断响应对象是否存在
                if (response != null) {
                    // 打印响应码到控制台
                    System.err.println("响应码: " + response.code());
                }
                // 标记发生错误
                isError[0] = true;
                // 设置错误消息
                errorMessage[0] = "WebSocket连接失败: " + t.getMessage();
                // 减少CountDownLatch计数，通知等待线程
                latch.countDown();
            }

            /**
             * WebSocket正在关闭时的回调方法
             * 功能概述：当WebSocket正在关闭时，记录关闭信息
             */
            // 重写onClosing方法，处理WebSocket正在关闭事件
            @Override
            // WebSocket正在关闭时的回调方法，接收WebSocket对象、关闭码和关闭原因
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                // 调用父类的onClosing方法
                super.onClosing(webSocket, code, reason);
                // 打印正在关闭信息到控制台
                System.out.println("WebSocket正在关闭: " + code + " - " + reason);
            }

            /**
             * WebSocket已关闭时的回调方法
             * 功能概述：当WebSocket已关闭时，记录关闭信息
             */
            // 重写onClosed方法，处理WebSocket已关闭事件
            @Override
            // WebSocket已关闭时的回调方法，接收WebSocket对象、关闭码和关闭原因
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                // 调用父类的onClosed方法
                super.onClosed(webSocket, code, reason);
                // 打印已关闭信息到控制台
                System.out.println("WebSocket已关闭: " + code + " - " + reason);
            }
        });

        // 等待响应完成（最多等待60秒）
        // 使用CountDownLatch等待响应完成，最多等待60秒
        boolean completed = latch.await(60, TimeUnit.SECONDS);
        
        // 关闭WebSocket连接
        // 关闭WebSocket连接，关闭码为1000（正常关闭），关闭原因为"正常关闭"
        webSocket.close(1000, "正常关闭");

        // 判断是否在超时时间内完成
        if (!completed) {
            // 如果未在超时时间内完成，抛出运行时异常
            throw new RuntimeException("等待响应超时");
        }

        // 判断是否发生错误
        if (isError[0]) {
            // 如果发生错误，抛出运行时异常，包含错误消息
            throw new RuntimeException(errorMessage[0]);
        }

        // 判断响应内容是否为空
        if (responseContent.length() == 0) {
            // 如果响应内容为空，抛出运行时异常
            throw new RuntimeException("未收到有效响应");
        }

        // 清理Markdown格式，转换为纯文本
        // 调用cleanMarkdown方法清理响应内容中的Markdown格式，转换为纯文本
        String cleanedResponse = cleanMarkdown(responseContent.toString());
        // 返回清理后的响应内容
        return cleanedResponse;
    }

    /**
     * 清理Markdown格式，转换为纯文本
     * 功能概述：移除AI响应中的Markdown格式标记，转换为纯文本格式，包括粗体、斜体、代码块、标题、列表、链接等
     */
    // 私有方法，清理Markdown格式
    private String cleanMarkdown(String text) {
        // 判断文本是否为空
        if (text == null || text.isEmpty()) {
            // 如果为空，直接返回
            return text;
        }
        
        // 移除Markdown格式标记
        // 创建清理后的文本变量
        String cleaned = text;
        
        // 移除粗体 **text** 或 __text__
        // 使用正则表达式移除粗体标记，保留文本内容
        cleaned = cleaned.replaceAll("\\*\\*(.*?)\\*\\*", "$1");
        // 使用正则表达式移除下划线粗体标记，保留文本内容
        cleaned = cleaned.replaceAll("__(.*?)__", "$1");
        
        // 移除斜体 *text* 或 _text_
        // 使用正则表达式移除斜体标记，保留文本内容
        cleaned = cleaned.replaceAll("\\*(.*?)\\*", "$1");
        // 使用正则表达式移除下划线斜体标记，保留文本内容
        cleaned = cleaned.replaceAll("_(.*?)_", "$1");
        
        // 移除代码块 ```code``` 或 `code`
        // 使用正则表达式移除多行代码块标记
        cleaned = cleaned.replaceAll("```[\\s\\S]*?```", "");
        // 使用正则表达式移除单行代码标记，保留代码内容
        cleaned = cleaned.replaceAll("`([^`]+)`", "$1");
        
        // 移除标题标记 # ## ###
        // 使用正则表达式移除标题标记，保留标题文本
        cleaned = cleaned.replaceAll("^#{1,6}\\s+(.*)$", "$1");
        
        // 移除列表标记 - * +
        // 使用正则表达式移除无序列表标记
        cleaned = cleaned.replaceAll("^[-*+]\\s+", "");
        // 使用正则表达式移除有序列表标记
        cleaned = cleaned.replaceAll("^\\d+\\.\\s+", "");
        
        // 移除链接 [text](url)
        // 使用正则表达式移除链接标记，保留链接文本
        cleaned = cleaned.replaceAll("\\[([^\\]]+)\\]\\([^\\)]+\\)", "$1");
        
        // 移除图片 ![alt](url)
        // 使用正则表达式移除图片标记
        cleaned = cleaned.replaceAll("!\\[([^\\]]*)\\]\\([^\\)]+\\)", "");
        
        // 移除引用 > text
        // 使用正则表达式移除引用标记
        cleaned = cleaned.replaceAll("^>\\s+", "");
        
        // 移除水平线 --- 或 ***
        // 使用正则表达式移除水平线标记
        cleaned = cleaned.replaceAll("^[-*]{3,}$", "");
        
        // 清理多余的空行（保留单个换行）
        // 使用正则表达式将3个或更多连续换行替换为2个换行
        cleaned = cleaned.replaceAll("\\n{3,}", "\n\n");
        
        // 清理首尾空白
        // 使用trim方法移除首尾空白字符
        cleaned = cleaned.trim();
        
        // 返回清理后的文本
        return cleaned;
    }

    /**
     * 构建请求JSON（包含丰富的system prompt和few-shot examples）
     * 功能概述：构建发送给AI服务的JSON请求，包括请求头、参数配置、系统提示词、示例对话和用户消息
     */
    // 私有方法，构建请求JSON
    private JsonObject buildRequestJson(String userMessage) {
        // 创建请求JSON对象
        JsonObject requestJson = new JsonObject();

        // Header
        // 创建请求头JSON对象
        JsonObject header = new JsonObject();
        // 设置应用ID，从配置中获取
        header.addProperty("app_id", sparkAIConfig.getAppid());
        // 设置用户ID，生成随机UUID并截取前10位
        header.addProperty("uid", UUID.randomUUID().toString().substring(0, 10));

        // Parameter
        // 创建参数JSON对象
        JsonObject parameter = new JsonObject();
        // 创建聊天参数JSON对象
        JsonObject chat = new JsonObject();
        // 设置模型域，从配置中获取
        chat.addProperty("domain", sparkAIConfig.getDomain());
        // 设置温度参数，控制输出的随机性（0.7表示中等随机性）
        chat.addProperty("temperature", 0.7);
        // 设置最大令牌数，限制响应的最大长度
        chat.addProperty("max_tokens", 4096);
        // 将聊天参数添加到参数对象中
        parameter.add("chat", chat);

        // Payload - 构建包含system、user、assistant的对话历史
        // 创建负载JSON对象
        JsonObject payload = new JsonObject();
        // 创建消息JSON对象
        JsonObject message = new JsonObject();
        // 创建文本数组，用于存储对话历史
        JsonArray text = new JsonArray();
        
        // System Prompt - 详细的系统提示词
        // 创建系统消息JSON对象
        JsonObject systemMsg = new JsonObject();
        // 设置角色为"system"
        systemMsg.addProperty("role", "system");
        // 设置内容为系统提示词，调用buildSystemPrompt方法生成
        systemMsg.addProperty("content", buildSystemPrompt());
        // 将系统消息添加到文本数组中
        text.add(systemMsg);
        
        // Few-shot Examples - 添加多个示例对话
        // 调用addFewShotExamples方法添加示例对话到文本数组中
        addFewShotExamples(text);
        
        // User Message - 当前用户消息
        // 创建用户消息JSON对象
        JsonObject userMsg = new JsonObject();
        // 设置角色为"user"
        userMsg.addProperty("role", "user");
        // 设置内容为用户消息
        userMsg.addProperty("content", userMessage);
        // 将用户消息添加到文本数组中
        text.add(userMsg);

        // 将文本数组添加到消息对象中
        message.add("text", text);
        // 将消息对象添加到负载对象中
        payload.add("message", message);

        // 组合
        // 将请求头添加到请求JSON对象中
        requestJson.add("header", header);
        // 将参数添加到请求JSON对象中
        requestJson.add("parameter", parameter);
        // 将负载添加到请求JSON对象中
        requestJson.add("payload", payload);

        // 返回构建好的请求JSON对象
        return requestJson;
    }

    /**
     * 构建系统提示词
     */
    private String buildSystemPrompt() {
        return "你是游市生活平台的AI智能助手，专门帮助用户解答关于本地生活服务的各种问题。\n\n" +
                "【平台介绍】\n" +
                "游市生活是一个专业的本地生活服务平台，提供以下五大核心服务：\n" +
                "1. 住宿服务：提供酒店、民宿预订，支持多种房型选择，包括豪华海景房、商务套房等\n" +
                "2. 美食服务：汇聚当地特色餐厅和美食，支持外卖配送，包含川菜、粤菜、湘菜等多种菜系\n" +
                "3. 出行服务：提供旅游景点推荐、门票预订、旅游路线规划，覆盖全国各省市景点\n" +
                "4. 购物服务：本地优质商户商品，支持在线购买和配送\n" +
                "5. 生活缴费：水电费、燃气费、话费等各类生活缴费服务\n\n" +
                "【数据库表结构】\n" +
                "平台使用以下主要数据表：\n" +
                "- hotel_order: 酒店订单表，包含订单号、酒店ID、房型、入住退房日期、价格等\n" +
                "- restaurant_order: 餐厅订单表，包含订单号、餐厅ID、总金额、配送地址、订单类型等\n" +
                "- shop_order: 购物订单表，包含订单号、用户ID、总金额、收货地址、订单状态等\n" +
                "- travel_order: 旅游订单表，包含订单号、景点ID、门票类型、数量、预约日期等\n" +
                "- unified_order: 统一订单表，整合所有模块订单，包含订单类型、支付状态、支付方式等\n" +
                "- payment_bills: 缴费账单表，包含账单类型、金额、缴费状态等\n" +
                "- hotel: 酒店信息表，包含酒店名称、地址、评分、图片等\n" +
                "- restaurant: 餐厅信息表，包含餐厅名称、菜系、城市、评分等\n" +
                "- attractions: 景点信息表，包含景点名称、地址、分类、评分等\n" +
                "- product: 商品信息表，包含商品名称、价格、库存等\n\n" +
                "【订单状态说明】\n" +
                "- 0: 待支付\n" +
                "- 1: 已支付\n" +
                "- 2: 已取消\n" +
                "- 3: 已完成\n\n" +
                "【回答要求】\n" +
                "1. 必须使用中文回答，语言要友好、专业、易懂\n" +
                "2. 禁止使用Markdown格式（如**粗体**、`代码`、#标题、-列表等），只使用纯文本\n" +
                "3. 不要使用任何格式标记，直接使用普通的中文文本回答\n" +
                "4. 根据用户问题，提供准确、详细的解答\n" +
                "5. 涉及订单、支付等问题时，要说明具体的操作步骤\n" +
                "6. 推荐服务时，要结合平台的实际功能\n" +
                "7. 如果用户询问不明确，要主动询问更多信息以便提供更好的帮助\n" +
                "8. 回答要简洁明了，避免冗长，但要包含关键信息\n" +
                "9. 对于技术性问题，要用通俗易懂的语言解释\n" +
                "10. 对于问候（你好、hello等），要友好地介绍自己和平台服务\n" +
                "11. 对于关键词查询（酒店、美食、出行、购物等），要提供相应的服务介绍和引导\n" +
                "12. 对于订单和支付相关问题，要提供具体的操作指导和解决方案\n\n" +
                "【服务特色】\n" +
                "- 支持在线预订和支付\n" +
                "- 提供订单管理和查询功能\n" +
                "- 支持多种支付方式（微信、支付宝、银行卡）\n" +
                "- 7×24小时客服支持\n" +
                "- 专业的服务团队，细致入微的服务标准";
    }

    /**
     * 添加Few-shot Examples（示例对话）
     */
    private void addFewShotExamples(JsonArray text) {
        // 示例1：住宿服务咨询
        JsonObject example1User = new JsonObject();
        example1User.addProperty("role", "user");
        example1User.addProperty("content", "我想预订酒店，有什么推荐吗？");
        text.add(example1User);
        
        JsonObject example1Assistant = new JsonObject();
        example1Assistant.addProperty("role", "assistant");
        example1Assistant.addProperty("content", "您好！我们平台提供丰富的酒店选择，包括豪华海景房、商务套房等多种房型。您可以访问住宿服务页面查看详细信息。请告诉我您的入住日期、退房日期、入住人数和预算范围，我可以为您推荐合适的酒店。");
        text.add(example1Assistant);
        
        // 示例2：美食服务咨询
        JsonObject example2User = new JsonObject();
        example2User.addProperty("role", "user");
        example2User.addProperty("content", "附近有什么好吃的餐厅？");
        text.add(example2User);
        
        JsonObject example2Assistant = new JsonObject();
        example2Assistant.addProperty("role", "assistant");
        example2Assistant.addProperty("content", "我们平台汇聚了当地各种特色餐厅，包括川菜、粤菜、湘菜等多种菜系。您可以浏览美食服务页面，或者告诉我您的口味偏好、预算范围和所在城市，我可以为您推荐合适的餐厅。我们还支持外卖配送服务哦！");
        text.add(example2Assistant);
        
        // 示例3：订单查询
        JsonObject example3User = new JsonObject();
        example3User.addProperty("role", "user");
        example3User.addProperty("content", "如何查看我的订单？");
        text.add(example3User);
        
        JsonObject example3Assistant = new JsonObject();
        example3Assistant.addProperty("role", "assistant");
        example3Assistant.addProperty("content", "您可以在顶部导航栏点击\"我的订单\"查看所有订单信息。订单状态包括：待支付、已支付、已取消、已完成。您可以查看订单详情、支付状态、订单金额等信息。如果需要帮助，请告诉我您的订单号。");
        text.add(example3Assistant);
        
        // 示例4：支付问题
        JsonObject example4User = new JsonObject();
        example4User.addProperty("role", "user");
        example4User.addProperty("content", "订单支付失败怎么办？");
        text.add(example4User);
        
        JsonObject example4Assistant = new JsonObject();
        example4Assistant.addProperty("role", "assistant");
        example4Assistant.addProperty("content", "如果订单支付失败，请先检查：1. 网络连接是否正常；2. 支付账户余额是否充足；3. 银行卡是否有效。如果问题仍然存在，您可以重新尝试支付，或者联系我们的客服热线400-888-8888。我们支持微信、支付宝、银行卡等多种支付方式。");
        text.add(example4Assistant);
        
        // 示例5：出行服务咨询
        JsonObject example5User = new JsonObject();
        example5User.addProperty("role", "user");
        example5User.addProperty("content", "我想去旅游，有什么景点推荐？");
        text.add(example5User);
        
        JsonObject example5Assistant = new JsonObject();
        example5Assistant.addProperty("role", "assistant");
        example5Assistant.addProperty("content", "我们平台提供全国各地的旅游景点推荐和门票预订服务。您可以访问出行服务页面，选择您想去的省份和城市，查看当地的景点信息。请告诉我您想去的地方、旅游时间和偏好（比如自然风光、历史文化、主题乐园等），我可以为您推荐合适的景点和旅游路线。");
        text.add(example5Assistant);
        
        // 示例6：购物服务咨询
        JsonObject example6User = new JsonObject();
        example6User.addProperty("role", "user");
        example6User.addProperty("content", "我想买东西，怎么下单？");
        text.add(example6User);
        
        JsonObject example6Assistant = new JsonObject();
        example6Assistant.addProperty("role", "assistant");
        example6Assistant.addProperty("content", "您可以访问购物服务页面，浏览商品并添加到购物车。选择好商品后，点击结算，填写收货地址和联系电话，确认订单信息后即可支付。我们支持在线支付和货到付款。订单创建后，您可以在\"我的订单\"中查看订单状态和物流信息。");
        text.add(example6Assistant);
        
        // 示例7：生活缴费咨询
        JsonObject example7User = new JsonObject();
        example7User.addProperty("role", "user");
        example7User.addProperty("content", "可以交水电费吗？");
        text.add(example7User);
        
        JsonObject example7Assistant = new JsonObject();
        example7Assistant.addProperty("role", "assistant");
        example7Assistant.addProperty("content", "可以的！我们平台提供生活缴费服务，包括水电费、燃气费、话费等各类缴费。您可以访问生活缴费页面，选择缴费类型，输入您的账户信息，查询账单后即可在线支付。支付成功后，缴费记录会保存在您的账户中，方便您随时查看。");
        text.add(example7Assistant);
        
        // 示例8：订单取消
        JsonObject example8User = new JsonObject();
        example8User.addProperty("role", "user");
        example8User.addProperty("content", "如何取消订单？");
        text.add(example8User);
        
        JsonObject example8Assistant = new JsonObject();
        example8Assistant.addProperty("role", "assistant");
        example8Assistant.addProperty("content", "如果订单状态为\"待支付\"，您可以直接取消订单。如果订单已支付，取消订单可能需要根据具体情况处理。您可以在\"我的订单\"页面找到对应订单，点击取消按钮。如果遇到问题，请联系客服。请注意，已支付的订单取消后，退款会在1-3个工作日内原路返回。");
        text.add(example8Assistant);
        
        // 示例9：平台功能介绍
        JsonObject example9User = new JsonObject();
        example9User.addProperty("role", "user");
        example9User.addProperty("content", "你们平台都有什么功能？");
        text.add(example9User);
        
        JsonObject example9Assistant = new JsonObject();
        example9Assistant.addProperty("role", "assistant");
        example9Assistant.addProperty("content", "游市生活平台提供五大核心服务：1. 住宿服务 - 酒店民宿预订；2. 美食服务 - 餐厅推荐和外卖配送；3. 出行服务 - 景点推荐和门票预订；4. 购物服务 - 本地商品在线购买；5. 生活缴费 - 水电燃气话费等缴费。我们还提供统一的订单管理、多种支付方式、7×24小时客服支持等服务。");
        text.add(example9Assistant);
        
        // 示例10：帮助请求
        JsonObject example10User = new JsonObject();
        example10User.addProperty("role", "user");
        example10User.addProperty("content", "我需要帮助");
        text.add(example10User);
        
        JsonObject example10Assistant = new JsonObject();
        example10Assistant.addProperty("role", "assistant");
        example10Assistant.addProperty("content", "我很乐意帮助您！请告诉我您遇到的具体问题，比如：订单问题、支付问题、服务咨询等。我会尽力为您解答。您也可以访问帮助中心，或者联系我们的客服热线400-888-8888，客服会为您提供专业的帮助。");
        text.add(example10Assistant);
        
        // 示例11：问候（来自模拟回复）
        JsonObject example11User = new JsonObject();
        example11User.addProperty("role", "user");
        example11User.addProperty("content", "你好");
        text.add(example11User);
        
        JsonObject example11Assistant = new JsonObject();
        example11Assistant.addProperty("role", "assistant");
        example11Assistant.addProperty("content", "您好！我是游市生活的AI助手，很高兴为您服务。我可以帮您解答关于住宿、美食、出行、购物等方面的问题。有什么可以帮您的吗？");
        text.add(example11Assistant);
        
        // 示例12：住宿服务（来自模拟回复）
        JsonObject example12User = new JsonObject();
        example12User.addProperty("role", "user");
        example12User.addProperty("content", "酒店");
        text.add(example12User);
        
        JsonObject example12Assistant = new JsonObject();
        example12Assistant.addProperty("role", "assistant");
        example12Assistant.addProperty("content", "关于住宿服务，我们平台提供精选的优质酒店和民宿。您可以访问住宿服务页面查看详细信息，或者告诉我您的具体需求，我可以为您推荐合适的住宿选择。");
        text.add(example12Assistant);
        
        // 示例13：美食服务（来自模拟回复）
        JsonObject example13User = new JsonObject();
        example13User.addProperty("role", "user");
        example13User.addProperty("content", "餐厅");
        text.add(example13User);
        
        JsonObject example13Assistant = new JsonObject();
        example13Assistant.addProperty("role", "assistant");
        example13Assistant.addProperty("content", "美食服务是我们平台的一大特色！我们汇聚了当地各种特色餐厅和美食。您可以浏览美食服务页面，或者告诉我您的口味偏好，我可以为您推荐合适的餐厅。");
        text.add(example13Assistant);
        
        // 示例14：出行服务（来自模拟回复）
        JsonObject example14User = new JsonObject();
        example14User.addProperty("role", "user");
        example14User.addProperty("content", "旅游");
        text.add(example14User);
        
        JsonObject example14Assistant = new JsonObject();
        example14Assistant.addProperty("role", "assistant");
        example14Assistant.addProperty("content", "出行服务可以帮助您规划旅游路线、查看景点信息等。您可以访问出行服务页面，或者告诉我您想去的地方，我可以为您提供相关的旅游建议。");
        text.add(example14Assistant);
        
        // 示例15：购物服务（来自模拟回复）
        JsonObject example15User = new JsonObject();
        example15User.addProperty("role", "user");
        example15User.addProperty("content", "购物");
        text.add(example15User);
        
        JsonObject example15Assistant = new JsonObject();
        example15Assistant.addProperty("role", "assistant");
        example15Assistant.addProperty("content", "购物服务提供本地优质商户的商品。您可以浏览购物服务页面，或者告诉我您想购买什么，我可以为您推荐相关的商品和商家。");
        text.add(example15Assistant);
        
        // 示例16：订单支付（来自模拟回复）
        JsonObject example16User = new JsonObject();
        example16User.addProperty("role", "user");
        example16User.addProperty("content", "订单");
        text.add(example16User);
        
        JsonObject example16Assistant = new JsonObject();
        example16Assistant.addProperty("role", "assistant");
        example16Assistant.addProperty("content", "关于订单和支付，您可以在顶部导航栏的\"我的订单\"中查看所有订单信息。如果遇到支付问题，请告诉我具体情况，我会尽力帮助您解决。");
        text.add(example16Assistant);
        
        // 示例17：帮助问题（来自模拟回复）
        JsonObject example17User = new JsonObject();
        example17User.addProperty("role", "user");
        example17User.addProperty("content", "问题");
        text.add(example17User);
        
        JsonObject example17Assistant = new JsonObject();
        example17Assistant.addProperty("role", "assistant");
        example17Assistant.addProperty("content", "我很乐意帮助您！游市生活平台提供住宿、美食、出行、购物、生活缴费等服务。您可以询问任何相关问题，我会尽力为您解答。");
        text.add(example17Assistant);
        
        // 示例18：通用回复（来自模拟回复）
        JsonObject example18User = new JsonObject();
        example18User.addProperty("role", "user");
        example18User.addProperty("content", "介绍一下平台");
        text.add(example18User);
        
        JsonObject example18Assistant = new JsonObject();
        example18Assistant.addProperty("role", "assistant");
        example18Assistant.addProperty("content", "感谢您的提问！作为游市生活的AI助手，我主要可以帮助您了解平台的各种服务，包括住宿、美食、出行、购物等。如果您有具体的问题，请告诉我，我会尽力为您解答。");
        text.add(example18Assistant);
    }

    /**
     * 鉴权方法（生成带鉴权参数的URL）
     * 功能概述：根据讯飞Spark API的鉴权要求，生成带鉴权参数的URL，包括时间戳、HMAC-SHA256签名等
     */
    // 私有方法，生成带鉴权参数的URL，可能抛出异常
    private String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        // 创建URL对象，解析主机URL
        URL url = new URL(hostUrl);
        
        // 生成时间戳（RFC1123格式）
        // 创建SimpleDateFormat对象，使用RFC1123格式（EEE, dd MMM yyyy HH:mm:ss z）
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        // 设置时区为GMT（格林威治标准时间）
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        // 格式化当前日期为RFC1123格式字符串
        String date = format.format(new Date());
        
        // 构建签名字符串（注意：WebSocket使用GET方法）
        // 构建待签名的字符串，包含主机、日期和请求行
        String preStr = "host: " + url.getHost() + "\n" + 
                       "date: " + date + "\n" + 
                       "GET " + url.getPath() + " HTTP/1.1";
        
        // HMAC-SHA256签名
        // 获取HMAC-SHA256算法的Mac实例
        Mac mac = Mac.getInstance("HmacSHA256");
        // 创建密钥规范，使用API密钥的UTF-8字节数组
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        // 初始化Mac对象，使用密钥规范
        mac.init(spec);
        // 对签名字符串进行HMAC-SHA256加密，得到字节数组
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // 将字节数组进行Base64编码，得到签名字符串
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        
        // 构建authorization字符串
        // 构建授权字符串，包含API密钥、算法、头部和签名
        String authorization = String.format(
            "api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
            apiKey, "hmac-sha256", "host date request-line", sha
        );
        
        // 构建带鉴权参数的URL
        // 解析URL并构建新的URL，添加鉴权参数
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath()))
                .newBuilder()
                // 添加authorization查询参数，将授权字符串进行Base64编码
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                // 添加date查询参数
                .addQueryParameter("date", date)
                // 添加host查询参数
                .addQueryParameter("host", url.getHost())
                // 构建最终的URL
                .build();

        // 返回带鉴权参数的URL字符串
        return httpUrl.toString();
    }

    /**
     * 响应解析类
     * 功能概述：用于解析AI服务返回的JSON响应，包括响应头、负载、选择项和文本内容
     */
    // 静态内部类，用于解析JSON响应
    static class JsonParse {
        // 响应头对象，包含错误码、状态和会话ID
        Header header;
        // 响应负载对象，包含选择项
        Payload payload;
    }

    /**
     * 响应头类
     * 功能概述：存储响应头信息，包括错误码、状态和会话ID
     */
    // 静态内部类，表示响应头
    static class Header {
        // 错误码，0表示成功，非0表示失败
        int code;
        // 状态码，2表示响应完成
        int status;
        // 会话ID，用于标识本次会话
        String sid;
    }

    /**
     * 响应负载类
     * 功能概述：存储响应负载信息，包含选择项列表
     */
    // 静态内部类，表示响应负载
    static class Payload {
        // 选择项对象，包含文本列表
        Choices choices;
    }

    /**
     * 选择项类
     * 功能概述：存储选择项信息，包含文本列表
     */
    // 静态内部类，表示选择项
    static class Choices {
        // 文本列表，包含多个文本项
        List<Text> text;
    }

    /**
     * 文本类
     * 功能概述：存储文本信息，包括角色和内容
     */
    // 静态内部类，表示文本项
    static class Text {
        // 角色，如"user"、"assistant"等
        String role;
        // 文本内容，AI的响应内容
        String content;
    }
}
