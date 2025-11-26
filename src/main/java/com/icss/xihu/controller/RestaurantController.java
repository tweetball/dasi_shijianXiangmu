// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入餐厅实体类
import com.icss.xihu.model.Restaurant;
// 导入餐厅订单实体类
import com.icss.xihu.model.RestaurantOrder;
// 导入餐厅评价实体类
import com.icss.xihu.model.RestaurantReview;
// 导入餐厅菜单实体类
import com.icss.xihu.model.RestaurantMenu;
// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入用户实体类
import com.icss.xihu.model.User;
// 导入餐厅Mapper接口
import com.icss.xihu.mapper.RestaurantMapper;
// 导入餐厅服务接口
import com.icss.xihu.service.RestaurantService;
// 导入统一订单新服务接口
import com.icss.xihu.service.UnifiedOrderNewService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的模型对象，用于向视图传递数据
import org.springframework.ui.Model;
// 导入Spring MVC的请求映射相关注解
import org.springframework.web.bind.annotation.*;

// 导入HTTP会话对象
import jakarta.servlet.http.HttpSession;
// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入HashMap集合类
import java.util.HashMap;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 餐厅控制器
 * 功能概述：处理餐厅相关的HTTP请求，包括餐厅查询、菜单展示、订单创建、评价管理等
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/food"
@RequestMapping("/food")
// 餐厅控制器类，处理餐厅相关的HTTP请求
public class RestaurantController {

    // 自动注入餐厅服务，Spring容器会自动查找并注入RestaurantService的实现类
    @Autowired
    // 餐厅服务对象，用于调用餐厅相关的业务逻辑
    private RestaurantService restaurantService;

    // 自动注入统一订单新服务，Spring容器会自动查找并注入UnifiedOrderNewService的实现类
    @Autowired
    // 统一订单新服务对象，用于调用统一订单相关的业务逻辑
    private UnifiedOrderNewService unifiedOrderNewService;
    
    // 自动注入餐厅Mapper，Spring容器会自动查找并注入RestaurantMapper的实现类
    @Autowired
    // 餐厅Mapper对象，用于直接调用数据库操作方法
    private RestaurantMapper restaurantMapper;

    /**
     * 美食首页
     * 功能概述：处理美食首页的请求，支持关键词搜索和分类筛选，返回餐厅列表、菜系、城市和统计数据
     */
    // 处理美食首页请求，映射路径"/food"，只接受GET请求
    @GetMapping
    // 美食首页处理方法，接收关键词和分类筛选参数（均为可选）
    public String foodIndex(Model model,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "category", required = false) String category) {
        // 获取餐厅列表
        // 声明餐厅列表变量
        List<Restaurant> restaurants;
        // 判断关键词是否不为空
        if (keyword != null && !keyword.trim().isEmpty()) {
            // 如果关键词不为空，调用餐厅服务的searchRestaurants方法，根据关键词搜索餐厅
            restaurants = restaurantService.searchRestaurants(keyword);
        // 判断分类是否不为空
        } else if (category != null && !category.trim().isEmpty()) {
            // 如果分类不为空，调用餐厅服务的getRestaurantsByCategory方法，根据分类查询餐厅
            restaurants = restaurantService.getRestaurantsByCategory(category);
        // 如果关键词和分类都为空
        } else {
            // 调用餐厅服务的findAllRestaurants方法，获取所有餐厅列表
            restaurants = restaurantService.findAllRestaurants();
        }
        // 将餐厅列表添加到模型中，供前端模板使用，键名为"restaurants"
        model.addAttribute("restaurants", restaurants);
        
        // 获取所有菜系
        // 调用餐厅服务的getAllCategories方法获取所有菜系列表
        List<String> categories = restaurantService.getAllCategories();
        // 将菜系列表添加到模型中，供前端模板使用，键名为"categories"
        model.addAttribute("categories", categories);
        
        // 获取所有城市
        // 调用餐厅服务的getAllCities方法获取所有城市列表
        List<String> cities = restaurantService.getAllCities();
        // 将城市列表添加到模型中，供前端模板使用，键名为"cities"
        model.addAttribute("cities", cities);
        
        // 获取菜系统计数据（用于词云）
        // 调用餐厅服务的getCategoryStatistics方法获取菜系统计数据
        List<Map<String, Object>> categoryStats = restaurantService.getCategoryStatistics();
        // 将菜系统计数据添加到模型中，供前端模板使用，键名为"categoryStats"
        model.addAttribute("categoryStats", categoryStats);
        
        // 返回视图名称"foodIndex"，Thymeleaf会自动查找templates/foodIndex.html
        return "foodIndex";
    }

    /**
     * 餐厅列表（支持筛选）
     * 功能概述：处理餐厅列表页面的请求，支持多种筛选条件（关键词、分类、城市、省份、价格区间、评分），返回餐厅列表视图
     */
    // 处理餐厅列表请求，映射路径"/list"，只接受GET请求
    @GetMapping("/list")
    // 餐厅列表处理方法，接收多种筛选参数（均为可选）
    public String restaurantList(@RequestParam(required = false) String category,
                                @RequestParam(required = false) String city,
                                @RequestParam(required = false) String province,
                                @RequestParam(required = false) String priceRange,
                                @RequestParam(required = false) Double minRating,
                                @RequestParam(required = false) String keyword,
                                Model model) {
        // 声明餐厅列表变量
        List<Restaurant> restaurants;
        
        // 根据筛选条件查询餐厅
        // 判断关键词是否不为空
        if (keyword != null && !keyword.trim().isEmpty()) {
            // 如果关键词不为空，调用餐厅服务的searchRestaurants方法，根据关键词搜索餐厅
            restaurants = restaurantService.searchRestaurants(keyword);
        // 判断分类是否不为空
        } else if (category != null && !category.trim().isEmpty()) {
            // 如果分类不为空，调用餐厅服务的getRestaurantsByCategory方法，根据分类查询餐厅
            restaurants = restaurantService.getRestaurantsByCategory(category);
        // 判断城市是否不为空
        } else if (city != null && !city.trim().isEmpty()) {
            // 如果城市不为空，调用餐厅服务的getRestaurantsByCity方法，根据城市查询餐厅
            restaurants = restaurantService.getRestaurantsByCity(city);
        // 判断省份是否不为空
        } else if (province != null && !province.trim().isEmpty()) {
            // 如果省份不为空，调用餐厅服务的getRestaurantsByProvince方法，根据省份查询餐厅
            restaurants = restaurantService.getRestaurantsByProvince(province);
        // 判断价格区间是否不为空
        } else if (priceRange != null && !priceRange.trim().isEmpty()) {
            // 如果价格区间不为空，调用餐厅服务的getRestaurantsByPriceRange方法，根据价格区间查询餐厅
            restaurants = restaurantService.getRestaurantsByPriceRange(priceRange);
        // 判断最低评分是否不为空
        } else if (minRating != null) {
            // 如果最低评分不为空，调用餐厅服务的getRestaurantsByRating方法，根据最低评分查询餐厅
            restaurants = restaurantService.getRestaurantsByRating(minRating);
        // 如果所有筛选条件都为空
        } else {
            // 调用餐厅服务的findAllRestaurants方法，获取所有餐厅列表
            restaurants = restaurantService.findAllRestaurants();
        }
        
        // 将餐厅列表添加到模型中，供前端模板使用，键名为"restaurants"
        model.addAttribute("restaurants", restaurants);
        // 将菜系列表添加到模型中，供前端模板使用，键名为"categories"
        model.addAttribute("categories", restaurantService.getAllCategories());
        // 将城市列表添加到模型中，供前端模板使用，键名为"cities"
        model.addAttribute("cities", restaurantService.getAllCities());
        // 将省份列表添加到模型中，供前端模板使用，键名为"provinces"
        model.addAttribute("provinces", restaurantService.getAllProvinces());
        
        // 返回视图名称"foodIndex"，Thymeleaf会自动查找templates/foodIndex.html
        return "foodIndex";
    }

    /**
     * 餐厅详情
     * 功能概述：处理餐厅详情页面的请求，根据餐厅ID查询餐厅信息、评价、菜单等，返回餐厅详情视图
     */
    // 处理餐厅详情请求，映射路径"/detail/{id}"，使用路径变量
    @GetMapping("/detail/{id}")
    // 餐厅详情处理方法，接收餐厅ID路径变量
    public String restaurantDetail(@PathVariable Long id, Model model) {
        // 调用餐厅服务的getRestaurantById方法，根据餐厅ID查询餐厅信息
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        // 判断餐厅是否存在
        if (restaurant == null) {
            // 如果餐厅不存在，重定向到美食首页
            return "redirect:/food";
        }
        
        // 将餐厅信息添加到模型中，供前端模板使用，键名为"restaurant"
        model.addAttribute("restaurant", restaurant);
        
        // 获取餐厅评价
        // 调用餐厅服务的getRestaurantReviews方法，根据餐厅ID查询餐厅评价列表
        List<RestaurantReview> reviews = restaurantService.getRestaurantReviews(id);
        // 将评价列表添加到模型中，供前端模板使用，键名为"reviews"
        model.addAttribute("reviews", reviews);
        
        // 获取评价统计
        // 调用餐厅服务的getRestaurantRatingStats方法，根据餐厅ID查询评价统计数据
        Map<String, Object> ratingStats = restaurantService.getRestaurantRatingStats(id);
        // 将评价统计数据添加到模型中，供前端模板使用，键名为"ratingStats"
        model.addAttribute("ratingStats", ratingStats);
        
        // 获取餐厅菜单
        // 调用餐厅服务的getRestaurantMenu方法，根据餐厅ID查询餐厅菜单列表
        List<RestaurantMenu> menu = restaurantService.getRestaurantMenu(id);
        // 将菜单列表添加到模型中，供前端模板使用，键名为"menu"
        model.addAttribute("menu", menu);
        
        // 获取菜单分类
        // 调用餐厅服务的getRestaurantMenuCategories方法，根据餐厅ID查询菜单分类列表
        List<String> menuCategories = restaurantService.getRestaurantMenuCategories(id);
        // 将菜单分类列表添加到模型中，供前端模板使用，键名为"menuCategories"
        model.addAttribute("menuCategories", menuCategories);
        
        // 获取特色菜单
        // 调用餐厅服务的getRestaurantSignatureMenu方法，根据餐厅ID查询特色菜单列表
        List<RestaurantMenu> signatureMenu = restaurantService.getRestaurantSignatureMenu(id);
        // 将特色菜单列表添加到模型中，供前端模板使用，键名为"signatureMenu"
        model.addAttribute("signatureMenu", signatureMenu);
        
        // 返回视图名称"foodDetail"，Thymeleaf会自动查找templates/foodDetail.html
        return "foodDetail";
    }

    /**
     * 创建订单
     * 功能概述：处理餐厅订单创建请求，需要用户登录，创建餐厅订单并返回订单号
     */
    // 处理创建订单请求，映射路径"/order/create"，只接受POST请求
    @PostMapping("/order/create")
    // 返回JSON格式的响应体
    @ResponseBody
    // 创建订单处理方法，接收餐厅订单对象和会话对象
    public Map<String, Object> createOrder(@RequestBody RestaurantOrder order, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch捕获异常
        try {
            // 从session获取用户ID
            // 从会话中获取用户ID（旧版方式）
            Integer userId = (Integer) session.getAttribute("userId");
            // 判断用户ID是否为空
            if (userId == null) {
                // 如果用户ID为空，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "请先登录");
                // 返回结果
                return result;
            }
            
            // 设置订单的用户ID
            order.setUserId(userId);
            
            // 创建订单
            // 调用餐厅服务的createOrder方法创建订单，返回订单号
            String orderNo = restaurantService.createOrder(order);
            
            // 设置返回结果为成功
            result.put("success", true);
            // 设置成功消息
            result.put("message", "预订成功");
            // 返回订单号
            result.put("orderNo", orderNo);
            
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "预订失败：" + e.getMessage());
        }
        
        // 返回结果
        return result;
    }

    /**
     * 外卖下单（购物车提交）
     * 功能概述：处理外卖订单创建请求，包括验证用户登录、创建餐厅订单、创建订单详情、创建统一订单并建立关联关系
     */
    // 处理外卖下单请求，映射路径"/order/takeout"，只接受POST请求
    @PostMapping("/order/takeout")
    // 返回JSON格式的响应体
    @ResponseBody
    // 外卖下单处理方法，接收请求体中的订单数据和会话对象
    public Map<String, Object> takeoutOrder(@RequestBody Map<String, Object> payload, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        // 使用try-catch捕获异常
        try {
            // 1. 获取用户信息
            // 从会话中获取当前登录的用户对象
            User user = (User) session.getAttribute("user");
            // 判断用户是否已登录
            if (user == null) {
                // 兼容旧的方式
                // 从会话中获取用户ID（旧版方式）
                Integer userId = (Integer) session.getAttribute("userId");
                // 判断用户ID是否为空
                if (userId == null) {
                    // 如果用户ID也为空，设置返回结果为失败
                    result.put("success", false);
                    // 设置错误消息
                    result.put("message", "请先登录");
                    // 返回结果
                    return result;
                }
                // 创建新的User对象，并设置用户ID
                user = new User();
                user.setId(userId);
            }
            
            // 2. 校验必要字段
            // 从请求体中获取餐厅ID
            Object restaurantId = payload.get("restaurantId");
            // 从请求体中获取菜品列表
            Object items = payload.get("items");
            // 从请求体中获取总金额
            Object totalAmount = payload.get("totalAmount");
            // 从请求体中获取餐厅名称
            Object restaurantName = payload.get("restaurantName");
            // 从请求体中获取配送地址
            Object address = payload.get("address");
            // 从请求体中获取联系人姓名
            Object contactName = payload.get("contactName");
            // 从请求体中获取联系人电话
            Object contactPhone = payload.get("contactPhone");
            
            // 判断必要字段是否完整
            if (restaurantId == null || items == null || totalAmount == null) {
                // 如果必要字段不完整，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "参数不完整");
                // 返回结果
                return result;
            }
            
            // 3. 先创建美食模块订单（RestaurantOrder）
            // 创建餐厅订单对象
            RestaurantOrder restaurantOrder = new RestaurantOrder();
            // 设置用户ID
            restaurantOrder.setUserId(user.getId());
            // 设置餐厅ID（将对象转换为Long类型）
            restaurantOrder.setRestaurantId(Long.parseLong(restaurantId.toString()));
            // 设置餐厅名称（如果为空则使用默认值）
            restaurantOrder.setRestaurantName(restaurantName != null ? restaurantName.toString() : "美食餐厅");
            // 将总金额转换为BigDecimal类型
            BigDecimal orderAmount = new BigDecimal(totalAmount.toString());
            // 设置预估金额
            restaurantOrder.setEstimatedAmount(orderAmount);
            // 设置总金额（数据库必填字段）
            restaurantOrder.setTotalAmount(orderAmount);
            // 设置订单状态为0（待支付）
            restaurantOrder.setOrderStatus(0);
            // 设置联系人姓名（如果为空则使用空字符串）
            restaurantOrder.setContactName(contactName != null ? contactName.toString() : "");
            // 设置联系人电话（如果为空则使用空字符串）
            restaurantOrder.setContactPhone(contactPhone != null ? contactPhone.toString() : "");
            // 设置特殊要求（配送地址，如果为空则使用空字符串）
            restaurantOrder.setSpecialRequirements(address != null ? "配送地址: " + address.toString() : "");
            // 外卖订单不需要 peopleCount，设置默认值为 1（避免计算预估金额时出错）
            restaurantOrder.setPeopleCount(1);
            
            // 生成订单号并创建订单
            // 调用餐厅服务的createOrder方法创建订单
            restaurantService.createOrder(restaurantOrder);
            // 获取插入后的订单ID
            Long restaurantOrderId = restaurantOrder.getId();
            
            // 判断订单ID是否有效
            if (restaurantOrderId == null) {
                // 如果订单ID无效，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "美食订单创建失败");
                // 返回结果
                return result;
            }
            
            // 3.1 创建订单详情（记录点了什么菜品）
            // 抑制未检查类型转换警告
            @SuppressWarnings("unchecked")
            // 从请求体中获取菜品列表，并转换为List<Map<String, Object>>类型
            List<Map<String, Object>> itemsList = (List<Map<String, Object>>) items;
            // 遍历菜品列表，创建订单详情
            for (Map<String, Object> item : itemsList) {
                // 获取菜品ID对象
                Object menuIdObj = item.get("menuId");
                // 获取菜品名称对象
                Object menuNameObj = item.get("menuName");
                // 获取菜品价格对象
                Object menuPriceObj = item.get("menuPrice");
                // 获取菜品数量对象
                Object quantityObj = item.get("quantity");
                
                // 判断菜品信息是否完整
                if (menuIdObj == null || menuNameObj == null || menuPriceObj == null || quantityObj == null) {
                    // 如果信息不完整，跳过该项
                    continue;
                }
                
                // 创建餐厅订单详情对象
                com.icss.xihu.model.RestaurantOrderDetail detail = new com.icss.xihu.model.RestaurantOrderDetail();
                // 设置订单ID（将Long类型转换为int类型）
                detail.setOrderId(restaurantOrderId.intValue());
                // 设置菜品ID（将对象转换为int类型）
                detail.setMenuId(Integer.parseInt(menuIdObj.toString()));
                // 设置菜品名称（将对象转换为字符串）
                detail.setMenuName(menuNameObj.toString());
                // 将价格对象转换为double类型
                double price = Double.parseDouble(menuPriceObj.toString());
                // 将数量对象转换为int类型
                int quantity = Integer.parseInt(quantityObj.toString());
                // 设置菜品单价（转换为BigDecimal类型）
                detail.setMenuPrice(java.math.BigDecimal.valueOf(price));
                // 设置菜品数量
                detail.setQuantity(quantity);
                // 设置菜品小计（价格 * 数量，转换为BigDecimal类型）
                detail.setSubtotal(java.math.BigDecimal.valueOf(price * quantity));
                
                // 调用餐厅Mapper的insertOrderDetail方法创建订单详情
                restaurantMapper.insertOrderDetail(detail);
            }
            
            // 4. 每次结算都创建新的统一订单（不从购物车复用旧订单）
            // 构建订单标题，格式为"餐厅名称+外卖订单"
            String orderTitle = restaurantName != null ? restaurantName.toString() + "外卖订单" : "美食外卖订单";
            // 设置订单描述
            String orderDescription = "美食外卖订单 - " + (restaurantName != null ? restaurantName.toString() : "美食");
            
            // 调用统一订单新服务的createOrder方法创建统一订单
            String unifiedOrderNo = unifiedOrderNewService.createOrder(
                user.getId(),  // 用户ID
                "FOOD",  // 订单类型为美食订单
                restaurantOrderId.intValue(),  // 模块订单ID，即餐厅订单的ID
                orderTitle,  // 订单标题
                orderDescription,  // 订单描述
                orderAmount  // 订单金额
            );
            
            // 判断统一订单是否创建成功
            if (unifiedOrderNo == null) {
                // 如果创建失败，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "统一订单创建失败");
                // 返回结果
                return result;
            }
            
            // 6. 更新餐厅订单的统一订单号（建立关联）
            // 调用餐厅Mapper的updateUnifiedOrderNo方法，将统一订单号关联到餐厅订单
            restaurantMapper.updateUnifiedOrderNo(restaurantOrderId, unifiedOrderNo);
            
            // 设置返回结果为成功
            result.put("success", true);
            // 返回统一订单号
            result.put("orderNo", unifiedOrderNo);
            // 返回支付页面的重定向URL，包含统一订单号参数
            result.put("redirectUrl", "/unified-new/payment/checkout?orderNo=" + unifiedOrderNo);
            // 返回结果
            return result;
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "系统错误: " + e.getMessage());
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
            // 返回结果
            return result;
        }
    }

    /**
     * 我的订单
     * 功能概述：处理用户订单列表页面的请求，需要用户登录，根据用户ID查询订单列表并返回视图
     */
    // 处理我的订单请求，映射路径"/myOrders"，只接受GET请求
    @GetMapping("/myOrders")
    // 我的订单处理方法，需要登录才能访问
    public String myOrders(HttpSession session, Model model) {
        // 从会话中获取用户ID（旧版方式）
        Integer userId = (Integer) session.getAttribute("userId");
        // 判断用户ID是否为空
        if (userId == null) {
            // 如果用户ID为空，重定向到登录页面
            return "redirect:/login";
        }
        
        // 调用餐厅服务的getUserOrders方法，根据用户ID查询订单列表
        List<RestaurantOrder> orders = restaurantService.getUserOrders(userId);
        // 将订单列表添加到模型中，供前端模板使用，键名为"orders"
        model.addAttribute("orders", orders);
        
        // 返回视图名称"myFoodOrders"，Thymeleaf会自动查找templates/myFoodOrders.html
        return "myFoodOrders";
    }

    /**
     * 取消订单
     * 功能概述：处理订单取消请求，需要用户登录，验证订单归属，调用服务层取消订单
     */
    // 处理取消订单请求，映射路径"/order/cancel"，只接受POST请求
    @PostMapping("/order/cancel")
    // 返回JSON格式的响应体
    @ResponseBody
    // 取消订单处理方法，接收订单ID参数
    public Map<String, Object> cancelOrder(@RequestParam Long orderId, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch捕获异常
        try {
            // 从会话中获取用户ID（旧版方式）
            Integer userId = (Integer) session.getAttribute("userId");
            // 判断用户ID是否为空
            if (userId == null) {
                // 如果用户ID为空，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "请先登录");
                // 返回结果
                return result;
            }
            
            // 验证订单是否属于当前用户
            // 调用餐厅服务的getOrderById方法，根据订单ID查询订单信息
            RestaurantOrder order = restaurantService.getOrderById(orderId);
            // 判断订单是否存在且属于当前用户
            if (order == null || !order.getUserId().equals(userId)) {
                // 如果订单不存在或不属于当前用户，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "订单不存在或无权限");
                // 返回结果
                return result;
            }
            
            // 取消订单
            // 调用餐厅服务的cancelOrder方法取消订单，返回是否成功
            boolean success = restaurantService.cancelOrder(orderId);
            
            // 判断取消是否成功
            if (success) {
                // 设置返回结果为成功
                result.put("success", true);
                // 设置成功消息
                result.put("message", "订单已取消");
            // 取消失败
            } else {
                // 设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "取消失败");
            }
            
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "取消失败：" + e.getMessage());
        }
        
        // 返回结果
        return result;
    }

    /**
     * 搜索餐厅
     * 功能概述：处理餐厅搜索请求，根据关键词搜索餐厅，返回餐厅列表视图
     */
    // 处理搜索餐厅请求，映射路径"/search"，只接受GET请求
    @GetMapping("/search")
    // 搜索餐厅处理方法，接收关键词参数
    public String searchRestaurants(@RequestParam String keyword, Model model) {
        // 调用餐厅服务的searchRestaurants方法，根据关键词搜索餐厅列表
        List<Restaurant> restaurants = restaurantService.searchRestaurants(keyword);
        // 将餐厅列表添加到模型中，供前端模板使用，键名为"restaurants"
        model.addAttribute("restaurants", restaurants);
        // 将关键词添加到模型中，供前端模板使用，键名为"keyword"
        model.addAttribute("keyword", keyword);
        // 将菜系列表添加到模型中，供前端模板使用，键名为"categories"
        model.addAttribute("categories", restaurantService.getAllCategories());
        // 将城市列表添加到模型中，供前端模板使用，键名为"cities"
        model.addAttribute("cities", restaurantService.getAllCities());
        
        // 返回视图名称"foodIndex"，Thymeleaf会自动查找templates/foodIndex.html
        return "foodIndex";
    }

    /**
     * 添加评价
     * 功能概述：处理餐厅评价添加请求，需要用户登录，创建餐厅评价并返回结果
     */
    // 处理添加评价请求，映射路径"/review/add"，只接受POST请求
    @PostMapping("/review/add")
    // 返回JSON格式的响应体
    @ResponseBody
    // 添加评价处理方法，接收餐厅评价对象和会话对象
    public Map<String, Object> addReview(@RequestBody RestaurantReview review, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch捕获异常
        try {
            // 从会话中获取用户ID（旧版方式）
            Integer userId = (Integer) session.getAttribute("userId");
            // 判断用户ID是否为空
            if (userId == null) {
                // 如果用户ID为空，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "请先登录");
                // 返回结果
                return result;
            }
            
            // 设置评价的用户ID
            review.setUserId(userId);
            
            // 从session获取用户信息
            // 从会话中获取用户名（旧版方式）
            String username = (String) session.getAttribute("username");
            // 设置评价的用户名
            review.setUsername(username);
            
            // 调用餐厅服务的addReview方法添加评价，返回是否成功
            boolean success = restaurantService.addReview(review);
            
            // 判断添加是否成功
            if (success) {
                // 设置返回结果为成功
                result.put("success", true);
                // 设置成功消息
                result.put("message", "评价提交成功");
            // 添加失败
            } else {
                // 设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "评价提交失败");
            }
            
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "评价提交失败：" + e.getMessage());
        }
        
        // 返回结果
        return result;
    }

    /**
     * 获取菜系统计数据（用于词云）
     * 功能概述：获取所有菜系的统计数据，用于词云可视化，返回JSON格式数据
     */
    // 处理获取菜系统计数据请求，映射路径"/statistics/categories"，只接受GET请求
    @GetMapping("/statistics/categories")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取菜系统计数据处理方法，返回菜系统计数据列表
    public List<Map<String, Object>> getCategoryStatistics() {
        // 调用餐厅服务的getCategoryStatistics方法获取菜系统计数据
        return restaurantService.getCategoryStatistics();
    }

    /**
     * 获取餐厅评价（分页）
     * 功能概述：根据餐厅ID获取餐厅评价列表，支持分页，返回JSON格式数据
     */
    // 处理获取餐厅评价请求，映射路径"/reviews/{restaurantId}"，使用路径变量
    @GetMapping("/reviews/{restaurantId}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取餐厅评价处理方法，接收餐厅ID路径变量和分页参数（页码和每页大小）
    public Map<String, Object> getRestaurantReviews(@PathVariable Long restaurantId,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch捕获异常
        try {
            // 调用餐厅服务的getRestaurantReviewsWithPagination方法，根据餐厅ID和分页参数查询评价列表
            List<RestaurantReview> reviews = restaurantService.getRestaurantReviewsWithPagination(restaurantId, page, size);
            // 调用餐厅服务的getRestaurantReviewCount方法，根据餐厅ID查询评价总数
            int totalCount = restaurantService.getRestaurantReviewCount(restaurantId);
            
            // 设置返回结果为成功
            result.put("success", true);
            // 将评价列表添加到返回结果中
            result.put("reviews", reviews);
            // 设置评价总数
            result.put("totalCount", totalCount);
            // 设置当前页码
            result.put("currentPage", page);
            // 设置每页大小
            result.put("pageSize", size);
            // 设置总页数（向上取整）
            result.put("totalPages", (int) Math.ceil((double) totalCount / size));
            
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "获取评价失败：" + e.getMessage());
        }
        
        // 返回结果
        return result;
    }
}
