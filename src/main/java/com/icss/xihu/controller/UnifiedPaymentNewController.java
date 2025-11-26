// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入用户实体类
import com.icss.xihu.model.User;
// 导入统一订单新服务接口
import com.icss.xihu.service.UnifiedOrderNewService;
// 导入酒店订单服务接口
import com.icss.xihu.service.HotelOrderService;
// 导入购物订单服务接口
import com.icss.xihu.service.ShopOrderService;
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
 * 重新设计的统一支付控制器
 * 功能概述：处理统一支付相关的HTTP请求，包括订单中心、订单列表、支付结算、订单管理等
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/unified-new"
@RequestMapping("/unified-new")
// 统一支付新控制器类，处理统一支付相关的HTTP请求
public class UnifiedPaymentNewController {

    // 自动注入统一订单新服务，Spring容器会自动查找并注入UnifiedOrderNewService的实现类
    @Autowired
    // 统一订单新服务对象，用于调用统一订单相关的业务逻辑
    private UnifiedOrderNewService unifiedOrderNewService;

    // 自动注入购物订单服务，Spring容器会自动查找并注入ShopOrderService的实现类
    @Autowired
    // 购物订单服务对象，用于调用购物订单相关的业务逻辑
    private ShopOrderService shopOrderService;

    // 自动注入酒店订单服务，Spring容器会自动查找并注入HotelOrderService的实现类
    @Autowired
    // 酒店订单服务对象，用于调用酒店订单相关的业务逻辑
    private HotelOrderService hotelOrderService;

    /**
     * 订单中心页面
     * 功能概述：处理订单中心页面的请求，需要用户登录，返回订单中心视图
     */
    // 处理订单中心页面请求，映射路径"/orders"，只接受GET请求
    @GetMapping("/orders")
    // 订单中心页面处理方法，需要登录才能访问
    public String orderCenter(HttpSession session, Model model) {
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，重定向到登录页面，并传递重定向参数
            return "redirect:/user/login?redirect=/unified-new/orders";
        }
        // 返回视图名称"orderCenterNew"，Thymeleaf会自动查找templates/orderCenterNew.html
        return "orderCenterNew";
    }

    /**
     * 获取订单列表
     * 功能概述：根据用户ID、订单类型和支付状态查询订单列表，支持筛选，返回JSON格式数据
     */
    // 处理获取订单列表请求，映射路径"/order/list"，只接受GET请求
    @GetMapping("/order/list")
    // 返回JSON格式的响应体，而不是视图
    @ResponseBody
    // 获取订单列表处理方法，接收订单类型和支付状态筛选参数（均为可选）
    public Map<String, Object> getOrderList(@RequestParam(required = false) String orderType,
                                           @RequestParam(required = false) Integer paymentStatus,
                                           HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();

        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误码
            result.put("code", "NOT_LOGIN");
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 调用统一订单新服务的getOrdersByUserId方法，根据用户ID、订单类型和支付状态查询订单列表
            List<UnifiedOrderNew> orders = unifiedOrderNewService.getOrdersByUserId(
                user.getId(), orderType, paymentStatus);
            
            // 设置返回结果为成功
            result.put("success", true);
            // 将订单列表添加到返回结果中
            result.put("data", orders);
            // 设置订单总数
            result.put("total", orders.size());
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "获取订单列表失败: " + e.getMessage());
        }

        // 返回结果
        return result;
    }

    /**
     * 创建订单
     * 功能概述：处理创建统一订单的请求，需要用户登录，创建新的统一订单并返回订单号
     */
    // 处理创建订单请求，映射路径"/order/create"，只接受POST请求
    @PostMapping("/order/create")
    // 返回JSON格式的响应体
    @ResponseBody
    // 创建订单处理方法，接收订单类型、标题、描述和总金额参数
    public Map<String, Object> createOrder(@RequestParam String orderType,
                                         @RequestParam String orderTitle,
                                         @RequestParam String orderDescription,
                                         @RequestParam BigDecimal totalAmount,
                                         HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();

        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 调用统一订单新服务的createOrder方法创建订单，moduleOrderId为null表示独立创建的统一订单
            String orderNo = unifiedOrderNewService.createOrder(
                user.getId(), orderType, null, orderTitle, orderDescription, totalAmount);
            
            // 判断订单是否创建成功（订单号不为null表示成功）
            if (orderNo != null) {
                // 设置返回结果为成功
                result.put("success", true);
                // 返回订单号
                result.put("orderNo", orderNo);
                // 设置成功消息
                result.put("message", "订单创建成功");
            // 订单创建失败
            } else {
                // 设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "订单创建失败");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "订单创建失败: " + e.getMessage());
        }

        // 返回结果
        return result;
    }

    // 自动注入餐厅Mapper，Spring容器会自动查找并注入RestaurantMapper的实现类
    @Autowired
    // 餐厅Mapper对象，用于直接调用数据库操作方法
    private com.icss.xihu.mapper.RestaurantMapper restaurantMapper;

    /**
     * 支付结算页面
     * 功能概述：处理支付结算页面的请求，需要用户登录，验证订单归属和状态，根据订单类型获取订单详情并传递给视图
     */
    // 处理支付结算页面请求，映射路径"/payment/checkout"，只接受GET请求
    @GetMapping("/payment/checkout")
    // 支付结算页面处理方法，接收订单号参数
    public String checkoutPage(@RequestParam String orderNo, HttpSession session, Model model) {
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，重定向到登录页面，并传递重定向参数和订单号
            return "redirect:/user/login?redirect=/unified-new/payment/checkout?orderNo=" + orderNo;
        }

        // 根据订单号查询统一订单信息
        UnifiedOrderNew order = unifiedOrderNewService.getOrderByOrderNo(orderNo);
        // 判断订单是否存在
        if (order == null) {
            // 如果订单不存在，重定向到订单中心页面，并传递错误信息
            return "redirect:/unified-new/orders?error=订单不存在";
        }

        // 验证订单归属（安全处理 null 值）
        // 判断订单的用户ID是否为空或与当前登录用户ID不匹配
        if (order.getUserId() == null || !order.getUserId().equals(user.getId())) {
            // 如果订单不属于当前用户，重定向到订单中心页面，并传递错误信息
            return "redirect:/unified-new/orders?error=无权访问该订单";
        }

        // 检查订单状态（允许查看已支付的订单详情，也允许从订单中心选择待支付订单进行支付）
        // 判断订单的支付状态是否为空
        if (order.getPaymentStatus() == null) {
            // 如果订单状态异常，重定向到订单中心页面，并传递错误信息
            return "redirect:/unified-new/orders?error=订单状态异常";
        }
        
        // 如果订单已支付，也允许查看详情（从订单中心进入）
        // 如果订单待支付，允许支付（从购物车或订单中心进入）

        // 将订单信息添加到模型中，供前端模板使用，键名为"order"
        model.addAttribute("order", order);
        
        // 根据订单类型获取订单详情（只对购物和美食订单显示详情列表）
        // 关键：确保用户、订单类型、模块订单ID、商品一一对应
        // 确保其他订单类型不会显示商品清单
        // 判断订单是否有模块订单ID（关联到具体的业务订单）
        if (order.getModuleOrderId() != null) {
            // 判断订单类型是否为购物订单
            if ("SHOPPING".equals(order.getOrderType())) {
                // 购物订单：获取商品列表
                // 关键验证：确保用户、订单类型、模块订单ID、商品一一对应
                // 1. 通过 moduleOrderId 直接查询 shop_order
                // 调用购物订单服务的getOrderById方法，根据模块订单ID查询购物订单
                com.icss.xihu.model.ShopOrder shopOrder = shopOrderService.getOrderById(order.getModuleOrderId());
                // 判断购物订单是否存在
                if (shopOrder == null) {
                    // 如果找不到对应的 shop_order，说明数据不一致
                    // 将订单详情设置为null，不显示详情列表
                    model.addAttribute("orderDetails", null);
                    // 将详情类型设置为null
                    model.addAttribute("detailType", null);
                // 购物订单存在
                } else {
                    // 2. 验证 shop_order.id 是否等于 unified_order.module_order_id
                    // 判断购物订单ID是否与统一订单的模块订单ID一致
                    if (!order.getModuleOrderId().equals(shopOrder.getId())) {
                        // 数据不一致：shop_order.id 不等于 unified_order.module_order_id
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 验证购物订单的用户ID是否与当前用户ID一致
                    } else if (shopOrder.getUserId() == null || !shopOrder.getUserId().equals(user.getId())) {
                        // 3. 验证 shop_order.user_id 是否等于当前用户ID
                        // 数据不一致：shop_order 不属于当前用户
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 验证购物订单的统一订单号是否与当前统一订单号一致
                    } else if (!orderNo.equals(shopOrder.getUnifiedOrderNo())) {
                        // 4. 验证 shop_order.unified_order_no 是否等于 unified_order.order_no
                        // 数据不一致：shop_order 关联的不是当前统一订单
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 所有验证通过
                    } else {
                        // 所有验证通过，获取商品列表（使用订单号查询）
                        // 调用购物订单服务的getOrderDetailsByOrderNo方法，根据订单号查询订单详情列表
                        java.util.List<com.icss.xihu.model.ShopOrderDetail> shopDetails = 
                            shopOrderService.getOrderDetailsByOrderNo(shopOrder.getOrderNo());
                        // 判断订单详情列表是否不为空
                        if (shopDetails != null && !shopDetails.isEmpty()) {
                            // 将订单详情列表添加到模型中，供前端模板使用，键名为"orderDetails"
                            model.addAttribute("orderDetails", shopDetails);
                            // 将详情类型设置为"SHOPPING"，供前端模板使用，键名为"detailType"
                            model.addAttribute("detailType", "SHOPPING");
                        // 订单详情列表为空
                        } else {
                            // 将订单详情设置为null，不显示详情列表
                            model.addAttribute("orderDetails", null);
                            // 将详情类型设置为null
                            model.addAttribute("detailType", null);
                        }
                    }
                }
            // 判断订单类型是否为美食订单
            } else if ("FOOD".equals(order.getOrderType())) {
                // 美食订单：获取菜品列表
                // 关键验证：确保用户、订单类型、模块订单ID、商品一一对应
                // 1. 通过 moduleOrderId 查询 restaurant_order
                // 调用餐厅Mapper的findOrderById方法，根据模块订单ID查询餐厅订单
                com.icss.xihu.model.RestaurantOrder restaurantOrder = 
                    restaurantMapper.findOrderById(order.getModuleOrderId().longValue());
                // 判断餐厅订单是否存在
                if (restaurantOrder == null) {
                    // 如果找不到对应的 restaurant_order，说明数据不一致
                    // 将订单详情设置为null，不显示详情列表
                    model.addAttribute("orderDetails", null);
                    // 将详情类型设置为null
                    model.addAttribute("detailType", null);
                // 餐厅订单存在
                } else {
                    // 2. 验证 restaurant_order.id 是否等于 unified_order.module_order_id
                    // 判断餐厅订单ID是否与统一订单的模块订单ID一致
                    if (!order.getModuleOrderId().equals(restaurantOrder.getId().intValue())) {
                        // 数据不一致
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 验证餐厅订单的用户ID是否与当前用户ID一致
                    } else if (restaurantOrder.getUserId() == null || !restaurantOrder.getUserId().equals(user.getId())) {
                        // 3. 验证 restaurant_order.user_id 是否等于当前用户ID
                        // 数据不一致：restaurant_order 不属于当前用户
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 验证餐厅订单的统一订单号是否与当前统一订单号一致
                    } else if (!orderNo.equals(restaurantOrder.getUnifiedOrderNo())) {
                        // 4. 验证 restaurant_order.unified_order_no 是否等于 unified_order.order_no
                        // 数据不一致：restaurant_order 关联的不是当前统一订单
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 所有验证通过
                    } else {
                        // 所有验证通过，获取菜品列表
                        // 调用餐厅Mapper的findDetailsByOrderId方法，根据订单ID查询订单详情列表
                        java.util.List<com.icss.xihu.model.RestaurantOrderDetail> restaurantDetails = 
                            restaurantMapper.findDetailsByOrderId(order.getModuleOrderId());
                        // 判断订单详情列表是否不为空
                        if (restaurantDetails != null && !restaurantDetails.isEmpty()) {
                            // 将订单详情列表添加到模型中，供前端模板使用，键名为"orderDetails"
                            model.addAttribute("orderDetails", restaurantDetails);
                            // 将详情类型设置为"FOOD"，供前端模板使用，键名为"detailType"
                            model.addAttribute("detailType", "FOOD");
                        // 订单详情列表为空
                        } else {
                            // 将订单详情设置为null，不显示详情列表
                            model.addAttribute("orderDetails", null);
                            // 将详情类型设置为null
                            model.addAttribute("detailType", null);
                        }
                    }
                }
            // 判断订单类型是否为酒店订单
            } else if ("HOTEL".equals(order.getOrderType())) {
                // 酒店订单：获取订单详情
                // 关键验证：确保用户、订单类型、模块订单ID、商品一一对应
                // 1. 通过 moduleOrderId 查询 hotel_order
                // 调用酒店订单服务的getOrderById方法，根据模块订单ID查询酒店订单
                com.icss.xihu.model.HotelOrder hotelOrder = 
                    hotelOrderService.getOrderById(order.getModuleOrderId());
                // 判断酒店订单是否存在
                if (hotelOrder == null) {
                    // 如果找不到对应的 hotel_order，说明数据不一致
                    // 将订单详情设置为null，不显示详情列表
                    model.addAttribute("orderDetails", null);
                    // 将详情类型设置为null
                    model.addAttribute("detailType", null);
                // 酒店订单存在
                } else {
                    // 2. 验证 hotel_order.id 是否等于 unified_order.module_order_id
                    // 判断酒店订单ID是否与统一订单的模块订单ID一致
                    if (!order.getModuleOrderId().equals(hotelOrder.getId())) {
                        // 数据不一致：hotel_order.id 不等于 unified_order.module_order_id
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 验证酒店订单的用户ID是否与当前用户ID一致
                    } else if (hotelOrder.getUid() != user.getId()) {
                        // 3. 验证 hotel_order.uid 是否等于当前用户ID
                        // 数据不一致：hotel_order 不属于当前用户
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 验证酒店订单的统一订单号是否与当前统一订单号一致
                    } else if (!orderNo.equals(hotelOrder.getUnifiedOrderNo())) {
                        // 4. 验证 hotel_order.unified_order_no 是否等于 unified_order.order_no
                        // 数据不一致：hotel_order 关联的不是当前统一订单
                        // 将订单详情设置为null，不显示详情列表
                        model.addAttribute("orderDetails", null);
                        // 将详情类型设置为null
                        model.addAttribute("detailType", null);
                    // 所有验证通过
                    } else {
                        // 所有验证通过，获取订单详情（使用订单号查询）
                        // 调用酒店订单服务的getOrderDetailsByOrderNo方法，根据订单号查询订单详情列表
                        java.util.List<com.icss.xihu.model.HotelOrderDetail> hotelDetails = 
                            hotelOrderService.getOrderDetailsByOrderNo(hotelOrder.getOrderNo());
                        // 判断订单详情列表是否不为空
                        if (hotelDetails != null && !hotelDetails.isEmpty()) {
                            // 将订单详情列表添加到模型中，供前端模板使用，键名为"orderDetails"
                            model.addAttribute("orderDetails", hotelDetails);
                            // 将详情类型设置为"HOTEL"，供前端模板使用，键名为"detailType"
                            model.addAttribute("detailType", "HOTEL");
                        // 订单详情列表为空
                        } else {
                            // 将订单详情设置为null，不显示详情列表
                            model.addAttribute("orderDetails", null);
                            // 将详情类型设置为null
                            model.addAttribute("detailType", null);
                        }
                    }
                }
            // 其他订单类型
            } else {
                // 其他订单类型（旅游、生活缴费）：不显示商品清单
                // 将订单详情设置为null，不显示详情列表
                model.addAttribute("orderDetails", null);
                // 将详情类型设置为null
                model.addAttribute("detailType", null);
            }
        // 如果没有模块订单ID
        } else {
            // 如果没有 moduleOrderId，确保不显示
            // 将订单详情设置为null，不显示详情列表
            model.addAttribute("orderDetails", null);
            // 将详情类型设置为null
            model.addAttribute("detailType", null);
        }
        
        // 返回视图名称"paymentCheckoutNew"，Thymeleaf会自动查找templates/paymentCheckoutNew.html
        return "paymentCheckoutNew";
    }

    /**
     * 处理支付
     * 功能概述：处理订单支付请求，需要用户登录，验证订单归属，调用服务层完成支付处理
     */
    // 处理支付请求，映射路径"/payment/process"，只接受POST请求
    @PostMapping("/payment/process")
    // 返回JSON格式的响应体
    @ResponseBody
    // 处理支付方法，接收订单号和支付方式参数
    public Map<String, Object> processPayment(@RequestParam String orderNo,
                                            @RequestParam String paymentMethod,
                                            HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();

        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 检查订单是否存在且属于该用户（安全处理 null 值）
            // 根据订单号查询统一订单信息
            UnifiedOrderNew order = unifiedOrderNewService.getOrderByOrderNo(orderNo);
            // 判断订单是否存在且属于当前用户
            if (order == null || order.getUserId() == null || !order.getUserId().equals(user.getId())) {
                // 如果订单不存在或不属于当前用户，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "订单不存在或无权访问");
                // 返回结果
                return result;
            }

            // 处理支付
            // 调用统一订单新服务的processPayment方法处理支付，返回是否成功
            boolean success = unifiedOrderNewService.processPayment(orderNo, paymentMethod);
            // 判断支付是否成功
            if (success) {
                // 设置返回结果为成功
                result.put("success", true);
                // 设置成功消息
                result.put("message", "支付成功");
            // 支付失败
            } else {
                // 设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "支付失败");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "支付处理失败: " + e.getMessage());
        }

        // 返回结果
        return result;
    }

    /**
     * 取消订单
     * 功能概述：处理订单取消请求，需要用户登录，验证订单归属，调用服务层取消订单
     */
    // 处理取消订单请求，映射路径"/order/cancel"，只接受POST请求
    @PostMapping("/order/cancel")
    // 返回JSON格式的响应体
    @ResponseBody
    // 取消订单处理方法，接收订单号参数
    public Map<String, Object> cancelOrder(@RequestParam String orderNo, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();

        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 调用统一订单新服务的cancelOrder方法取消订单，返回是否成功
            boolean success = unifiedOrderNewService.cancelOrder(orderNo, user.getId());
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
                result.put("message", "订单取消失败");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "订单取消失败: " + e.getMessage());
        }

        // 返回结果
        return result;
    }

    /**
     * 删除订单
     * 功能概述：处理订单删除请求，需要用户登录，验证订单归属，调用服务层删除订单
     */
    // 处理删除订单请求，映射路径"/order/delete"，只接受POST请求
    @PostMapping("/order/delete")
    // 返回JSON格式的响应体
    @ResponseBody
    // 删除订单处理方法，接收订单号参数
    public Map<String, Object> deleteOrder(@RequestParam String orderNo, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();

        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 调用统一订单新服务的deleteOrder方法删除订单，返回是否成功
            boolean success = unifiedOrderNewService.deleteOrder(orderNo, user.getId());
            // 判断删除是否成功
            if (success) {
                // 设置返回结果为成功
                result.put("success", true);
                // 设置成功消息
                result.put("message", "订单已删除");
            // 删除失败
            } else {
                // 设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "订单删除失败");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "订单删除失败: " + e.getMessage());
        }

        // 返回结果
        return result;
    }

    /**
     * 获取订单统计
     * 功能概述：获取当前用户的订单统计信息，包括订单总数、各状态订单数量等，返回JSON格式数据
     */
    // 处理获取订单统计请求，映射路径"/order/stats"，只接受GET请求
    @GetMapping("/order/stats")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取订单统计处理方法，返回订单统计信息
    public Map<String, Object> getOrderStats(HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();

        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 调用统一订单新服务的getOrderStats方法获取订单统计信息
            Map<String, Object> stats = unifiedOrderNewService.getOrderStats(user.getId());
            // 设置返回结果为成功
            result.put("success", true);
            // 将统计信息添加到返回结果中
            result.put("data", stats);
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "获取统计信息失败: " + e.getMessage());
        }

        // 返回结果
        return result;
    }

    /**
     * 测试页面
     * 功能概述：处理测试页面的请求，返回统一订单测试页面视图
     */
    // 处理测试页面请求，映射路径"/test"，只接受GET请求
    @GetMapping("/test")
    // 测试页面处理方法，返回测试页面视图
    public String testPage() {
        // 返回视图名称"testUnifiedOrderNew"，Thymeleaf会自动查找templates/testUnifiedOrderNew.html
        return "testUnifiedOrderNew";
    }
}
