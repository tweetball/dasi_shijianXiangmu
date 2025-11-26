# 游市生活 (XiHu) - 本地生活服务平台

## 项目简介

游市生活是一个专业的本地生活服务平台，提供住宿、美食、出行、购物、生活缴费等一站式服务。平台采用Spring Boot框架开发，集成了AI智能助手功能，为用户提供便捷的本地生活服务体验。

## 技术栈

- **后端框架**: Spring Boot 3.3.12
- **数据库**: MySQL 8.0+
- **ORM框架**: MyBatis 3.0.4
- **模板引擎**: Thymeleaf
- **Java版本**: JDK 21
- **构建工具**: Maven
- **AI服务**: 讯飞Spark AI (WebSocket协议)
- **HTTP客户端**: OkHttp 4.12.0
- **JSON处理**: Gson

## 项目结构

```
XiHu/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/icss/xihu/
│   │   │       ├── config/              # 配置类
│   │   │       │   └── SparkAIConfig.java
│   │   │       ├── controller/          # 控制器层
│   │   │       │   ├── AIChatController.java      # AI聊天控制器
│   │   │       │   ├── HotelController.java        # 酒店控制器
│   │   │       │   ├── RestaurantController.java   # 餐厅控制器
│   │   │       │   ├── ShopController.java         # 购物控制器
│   │   │       │   ├── TravelController.java       # 旅游控制器
│   │   │       │   ├── UnifiedPaymentNewController.java  # 统一支付控制器
│   │   │       │   ├── UserController.java         # 用户控制器
│   │   │       │   └── MainController.java         # 主控制器
│   │   │       ├── mapper/              # MyBatis映射器
│   │   │       │   ├── HotelMapper.java
│   │   │       │   ├── RestaurantMapper.java
│   │   │       │   ├── ShopOrderMapper.java
│   │   │       │   ├── TravelMapper.java
│   │   │       │   └── ...
│   │   │       ├── model/              # 实体类
│   │   │       │   ├── Hotel.java
│   │   │       │   ├── HotelOrder.java
│   │   │       │   ├── Restaurant.java
│   │   │       │   ├── ShopOrder.java
│   │   │       │   ├── UnifiedOrderNew.java
│   │   │       │   └── ...
│   │   │       ├── service/            # 服务层
│   │   │       │   ├── impl/           # 服务实现类
│   │   │       │   ├── HotelService.java
│   │   │       │   ├── RestaurantService.java
│   │   │       │   ├── ShopOrderService.java
│   │   │       │   ├── SparkAIService.java  # AI服务
│   │   │       │   └── ...
│   │   │       └── util/               # 工具类
│   │   └── resources/
│   │       ├── application.properties  # 应用配置
│   │       ├── mapper/                 # MyBatis XML映射文件
│   │       ├── templates/             # Thymeleaf模板
│   │       │   ├── index.html         # 首页
│   │       │   ├── hotelIndex.html    # 酒店列表页
│   │       │   ├── hotelInfo.html     # 酒店详情页
│   │       │   ├── foodIndex.html     # 美食列表页
│   │       │   ├── shoppingIndex.html # 购物列表页
│   │       │   ├── travelIndex.html   # 旅游列表页
│   │       │   ├── paymentCheckoutNew.html  # 支付页面
│   │       │   └── ...
│   │       └── static/                 # 静态资源
│   │           ├── css/               # 样式文件
│   │           ├── js/                # JavaScript文件
│   │           └── img/               # 图片资源
│   └── test/                          # 测试代码
├── pom.xml                            # Maven配置文件
└── README.md                          # 项目说明文档
```

## 核心功能模块

### 1. 住宿服务 (Hotel)
- 酒店列表展示
- 酒店详情查看
- 酒店预订功能
- 订单管理

### 2. 美食服务 (Restaurant/Food)
- 餐厅列表展示
- 餐厅详情查看
- 外卖点餐功能
- 订单管理

### 3. 出行服务 (Travel)
- 景点推荐
- 景点详情查看
- 门票预订
- 旅游路线规划

### 4. 购物服务 (Shop)
- 商品列表展示
- 商品详情查看
- 购物车功能
- 订单管理

### 5. 统一支付系统 (UnifiedPayment)
- 多模块订单统一支付
- 支付方式选择（微信、支付宝、银行卡）
- 订单状态管理
- 支付记录查询

### 6. 用户管理 (User)
- 用户注册/登录
- 个人信息管理
- 订单中心
- 地址管理

### 7. AI智能助手 (AIChat)
- 集成讯飞Spark AI
- 智能问答功能
- 服务推荐
- 订单查询辅助

## 环境要求

- **JDK**: 21 或更高版本
- **Maven**: 3.6+ 
- **MySQL**: 8.0+ 
- **IDE**: IntelliJ IDEA / Eclipse (推荐IntelliJ IDEA)

## 快速开始

### 1. 数据库配置

1. 创建MySQL数据库：
```sql
CREATE DATABASE xihu CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入数据库脚本（如果有）：
```bash
mysql -u root -p xihu < database.sql
```

3. 修改数据库配置：
编辑 `src/main/resources/application.properties` 文件，修改以下配置：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/xihu?useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=123456
```

### 2. AI服务配置（可选）

如果需要使用AI聊天功能，需要配置讯飞Spark AI：
编辑 `src/main/resources/application.properties` 文件：
```properties
spark.ai.enabled=true
spark.ai.appid=your_appid
spark.ai.api-secret=your_api_secret
spark.ai.api-key=your_api_key
spark.ai.ws-url=https://spark-api.xf-yun.com/v1/x1
spark.ai.domain=spark-x
```

如果不配置AI服务，系统会自动使用模拟响应。

### 3. 编译项目

```bash
# 清理并编译项目
mvn clean compile

# 或者跳过测试编译
mvn clean compile -DskipTests
```

### 4. 运行项目

#### 方式一：使用Maven命令运行
```bash
mvn spring-boot:run
```

#### 方式二：使用IDE运行
1. 打开项目
2. 找到 `com.icss.xihu.XiHuApplication` 主类
3. 右键选择 "Run" 或 "Debug"

#### 方式三：打包后运行
```bash
# 打包项目
mvn clean package -DskipTests

# 运行jar包
java -jar target/XiHu-0.0.1-SNAPSHOT.jar
```

### 5. 访问应用

启动成功后，访问以下地址：
- **首页**: http://localhost:8080/
- **酒店服务**: http://localhost:8080/hc/f2
- **美食服务**: http://localhost:8080/food
- **购物服务**: http://localhost:8080/shopping
- **旅游服务**: http://localhost:8080/travel
- **订单中心**: http://localhost:8080/unified-new/orders
- **用户登录**: http://localhost:8080/user/login

## 配置说明

### 应用配置 (application.properties)

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| server.port | 服务端口 | 8080 |
| spring.datasource.url | 数据库连接URL | - |
| spring.datasource.username | 数据库用户名 | root |
| spring.datasource.password | 数据库密码 | 123456 |
| spark.ai.enabled | 是否启用AI服务 | true |
| spark.ai.appid | AI服务APPID | - |
| spark.ai.api-secret | AI服务API密钥 | - |
| spark.ai.api-key | AI服务API Key | - |

## 数据库表结构

主要数据表：
- `user` - 用户表
- `hotel` - 酒店表
- `hotel_order` - 酒店订单表
- `hotel_order_detail` - 酒店订单详情表
- `restaurant` - 餐厅表
- `restaurant_order` - 餐厅订单表
- `shop_order` - 购物订单表
- `shop_order_detail` - 购物订单详情表
- `travel_order` - 旅游订单表
- `unified_order` - 统一订单表
- `payment_bills` - 缴费账单表
- `product` - 商品表
- `attractions` - 景点表

## 开发规范

### 代码结构
- **Controller层**: 处理HTTP请求，参数验证
- **Service层**: 业务逻辑处理
- **Mapper层**: 数据库操作
- **Model层**: 实体类定义

### 命名规范
- 类名：大驼峰命名（PascalCase）
- 方法名：小驼峰命名（camelCase）
- 常量：全大写下划线分隔（UPPER_SNAKE_CASE）
- 数据库表/字段：下划线命名（snake_case）

### 注释规范
- 类和方法需要添加JavaDoc注释
- 复杂业务逻辑需要添加行内注释

## 常见问题

### 1. 端口被占用
如果8080端口被占用，可以修改 `application.properties` 中的 `server.port` 配置。

### 2. 数据库连接失败
- 检查MySQL服务是否启动
- 检查数据库用户名密码是否正确
- 检查数据库是否存在

### 3. AI服务不可用
- 检查AI服务配置是否正确
- 如果配置错误，系统会自动使用模拟响应
- 可以在日志中查看详细错误信息

### 4. 编译错误
- 确保JDK版本为21
- 确保Maven版本为3.6+
- 清理并重新编译：`mvn clean compile`

## 项目特色

1. **统一订单系统**: 整合多个业务模块的订单，统一管理和支付
2. **AI智能助手**: 集成讯飞Spark AI，提供智能问答和服务推荐
3. **响应式设计**: 支持多种设备访问
4. **模块化设计**: 各业务模块独立，便于维护和扩展

## 后续开发计划

- [ ] 添加更多支付方式
- [ ] 优化AI助手响应速度
- [ ] 添加更多业务模块
- [ ] 性能优化
- [ ] 安全加固

## 许可证

本项目仅供学习和研究使用。

## 联系方式

如有问题或建议，请联系项目维护者。

---

**注意**: 首次运行前请确保数据库已创建并配置正确，否则应用将无法启动。

