// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入统一订单实体类（旧版）
import com.icss.xihu.model.UnifiedOrder;
// 导入用户实体类
import com.icss.xihu.model.User;
// 导入统一订单服务接口（旧版）
import com.icss.xihu.service.UnifiedOrderService;
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
// 导入HashMap集合类
import java.util.HashMap;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 统一支付控制器（旧版）
 * 功能概述：处理统一支付相关的HTTP请求（旧版），包括订单中心、支付结算、订单管理等
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/unified"
@RequestMapping("/unified")
// 统一支付控制器类（旧版），处理统一支付相关的HTTP请求
public class UnifiedPaymentController {

    // 自动注入统一订单服务（旧版），Spring容器会自动查找并注入UnifiedOrderService的实现类
    @Autowired
    // 统一订单服务对象（旧版），用于调用统一订单相关的业务逻辑
    private UnifiedOrderService unifiedOrderService;

    /**
     * 统一支付页面（旧版）
     * 功能概述：处理统一支付页面的请求，需要用户登录，验证订单归属，返回支付页面视图
     */
    // 处理统一支付页面请求，映射路径"/payment/checkout"，只接受GET请求
    @GetMapping("/payment/checkout")
    // 统一支付页面处理方法，接收订单号参数
    public String checkoutPage(@RequestParam("orderNo") String orderNo, 
                               HttpSession session, Model model) {
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，重定向到登录页面，并传递重定向参数和订单号
            return "redirect:/user/login?redirect=/unified/payment/checkout?orderNo=" + orderNo;
        }

        // 获取订单信息
        // 根据订单号查询统一订单信息（旧版）
        UnifiedOrder order = unifiedOrderService.getOrderByOrderNo(orderNo);
        // 判断订单是否存在
        if (order == null) {
            // 如果订单不存在，重定向到订单中心页面，并传递错误信息
            return "redirect:/unified-new/orders?error=订单不存在";
        }

        // 验证订单归属
        // 判断订单的用户ID是否为空或与当前登录用户ID不匹配
        if (order.getUserId() == null || !order.getUserId().equals(user.getId())) {
            // 如果订单不属于当前用户，重定向到订单中心页面，并传递错误信息
            return "redirect:/unified-new/orders?error=无权访问该订单";
        }

        // 将订单信息添加到模型中，供前端模板使用，键名为"order"
        model.addAttribute("order", order);
        // 返回视图名称"paymentCheckout"，Thymeleaf会自动查找templates/paymentCheckout.html
        return "paymentCheckout";
    }

    /**
     * 处理支付（旧版）
     * 功能概述：处理订单支付请求，需要用户登录，验证订单归属，调用服务层完成支付处理
     */
    // 处理支付请求，映射路径"/payment/process"，只接受POST请求
    @PostMapping("/payment/process")
    // 返回JSON格式的响应体
    @ResponseBody
    // 处理支付方法，接收请求体中的参数和会话对象
    public Map<String, Object> processPayment(@RequestBody Map<String, String> params, 
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

        // 从请求参数中获取订单号
        String orderNo = params.get("orderNo");
        // 从请求参数中获取支付方式
        String paymentMethod = params.get("paymentMethod");

        // 验证订单归属
        // 根据订单号查询统一订单信息（旧版）
        UnifiedOrder order = unifiedOrderService.getOrderByOrderNo(orderNo);
        // 判断订单是否存在且属于当前用户
        if (order == null || !order.getUserId().equals(user.getId())) {
            // 如果订单不存在或不属于当前用户，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "订单不存在或无权访问");
            // 返回结果
            return result;
        }

        // 处理支付
        // 调用统一订单服务的processPayment方法处理支付，返回是否成功
        boolean success = unifiedOrderService.processPayment(orderNo, paymentMethod);
        // 判断支付是否成功
        if (success) {
            // 设置返回结果为成功
            result.put("success", true);
            // 设置成功消息
            result.put("message", "支付成功");
            // 返回订单号
            result.put("orderNo", orderNo);
        // 支付失败
        } else {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "支付失败，请重试");
        }

        // 返回结果
        return result;
    }

    /**
     * 支付成功页面（旧版）
     * 功能概述：处理支付成功页面的请求，根据订单号查询订单信息并传递给视图
     */
    // 处理支付成功页面请求，映射路径"/payment/success"，只接受GET请求
    @GetMapping("/payment/success")
    // 支付成功页面处理方法，接收订单号参数
    public String paymentSuccess(@RequestParam("orderNo") String orderNo, Model model) {
        // 根据订单号查询统一订单信息（旧版）
        UnifiedOrder order = unifiedOrderService.getOrderByOrderNo(orderNo);
        // 将订单信息添加到模型中，供前端模板使用，键名为"order"
        model.addAttribute("order", order);
        // 返回视图名称"paymentSuccess"，Thymeleaf会自动查找templates/paymentSuccess.html
        return "paymentSuccess";
    }

    /**
     * 我的订单页面（旧版）
     * 功能概述：处理订单中心页面的请求，需要用户登录，返回订单中心视图
     */
    // 处理订单中心页面请求，映射路径"/order/center"，只接受GET请求
    @GetMapping("/order/center")
    // 订单中心页面处理方法，需要登录才能访问
    public String orderCenter(HttpSession session, Model model) {
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，重定向到登录页面，并传递重定向参数
            return "redirect:/user/login?redirect=/unified-new/orders";
        }

        // 返回视图名称"orderCenter"，Thymeleaf会自动查找templates/orderCenter.html
        return "orderCenter";
    }

    /**
     * 获取订单列表（旧版）
     * 功能概述：根据用户ID、订单类型和支付状态查询订单列表，支持筛选，返回JSON格式数据
     */
    // 处理获取订单列表请求，映射路径"/order/list"，只接受GET请求
    @GetMapping("/order/list")
    // 返回JSON格式的响应体
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

        // 调用统一订单服务的getOrdersByUserId方法，根据用户ID、订单类型和支付状态查询订单列表
        List<UnifiedOrder> orders = unifiedOrderService.getOrdersByUserId(
            user.getId(), orderType, paymentStatus);

        // 设置返回结果为成功
        result.put("success", true);
        // 将订单列表添加到返回结果中
        result.put("list", orders);
        // 返回结果
        return result;
    }

    /**
     * 获取订单详情（旧版）
     * 功能概述：根据订单号查询订单详细信息，需要用户登录，验证订单归属，返回JSON格式数据
     */
    // 处理获取订单详情请求，映射路径"/order/{orderNo}/detail"，使用路径变量
    @GetMapping("/order/{orderNo}/detail")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取订单详情处理方法，接收订单号路径变量
    public Map<String, Object> getOrderDetail(@PathVariable String orderNo, HttpSession session) {
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

        // 根据订单号查询统一订单信息（旧版）
        UnifiedOrder order = unifiedOrderService.getOrderByOrderNo(orderNo);
        // 判断订单是否存在且属于当前用户
        if (order == null || !order.getUserId().equals(user.getId())) {
            // 如果订单不存在或不属于当前用户，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "订单不存在或无权访问");
            // 返回结果
            return result;
        }

        // 设置返回结果为成功
        result.put("success", true);
        // 将订单信息添加到返回结果中
        result.put("order", order);
        // 返回结果
        return result;
    }

    /**
     * 取消订单（旧版）
     * 功能概述：处理订单取消请求，需要用户登录，验证订单归属，调用服务层取消订单
     */
    // 处理取消订单请求，映射路径"/order/cancel"，只接受POST请求
    @PostMapping("/order/cancel")
    // 返回JSON格式的响应体
    @ResponseBody
    // 取消订单处理方法，接收请求体中的参数和会话对象
    public Map<String, Object> cancelOrder(@RequestBody Map<String, String> params,
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

        // 从请求参数中获取订单号
        String orderNo = params.get("orderNo");

        // 验证订单归属
        // 根据订单号查询统一订单信息（旧版）
        UnifiedOrder order = unifiedOrderService.getOrderByOrderNo(orderNo);
        // 判断订单是否存在且属于当前用户
        if (order == null || !order.getUserId().equals(user.getId())) {
            // 如果订单不存在或不属于当前用户，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "订单不存在或无权访问");
            // 返回结果
            return result;
        }

        // 调用统一订单服务的cancelOrder方法取消订单，返回是否成功
        boolean success = unifiedOrderService.cancelOrder(orderNo);
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
            result.put("message", "取消订单失败");
        }

        // 返回结果
        return result;
    }

    /**
     * 删除订单（旧版）
     * 功能概述：处理订单删除请求，需要用户登录，验证订单归属，调用服务层删除订单
     */
    // 处理删除订单请求，映射路径"/order/delete"，只接受POST请求
    @PostMapping("/order/delete")
    // 返回JSON格式的响应体
    @ResponseBody
    // 删除订单处理方法，接收请求体中的参数和会话对象
    public Map<String, Object> deleteOrder(@RequestBody Map<String, String> params,
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

        // 从请求参数中获取订单号
        String orderNo = params.get("orderNo");
        // 调用统一订单服务的deleteOrder方法删除订单，返回是否成功
        boolean success = unifiedOrderService.deleteOrder(orderNo, user.getId());

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
            result.put("message", "删除订单失败");
        }

        // 返回结果
        return result;
    }

    /**
     * 获取订单统计（旧版）
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
            // 设置错误码
            result.put("code", "NOT_LOGIN");
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }

        // 调用统一订单服务的getOrderStats方法获取订单统计信息
        Map<String, Object> stats = unifiedOrderService.getOrderStats(user.getId());
        // 设置返回结果为成功
        result.put("success", true);
        // 将统计信息添加到返回结果中
        result.put("stats", stats);
        // 返回结果
        return result;
    }

    /**
     * 测试页面（旧版）
     * 功能概述：处理测试页面的请求，返回统一订单测试页面视图
     */
    // 处理测试页面请求，映射路径"/test"，只接受GET请求
    @GetMapping("/test")
    // 测试页面处理方法，返回测试页面视图
    public String testPage() {
        // 返回视图名称"testUnifiedOrder"，Thymeleaf会自动查找templates/testUnifiedOrder.html
        return "testUnifiedOrder";
    }

    /**
     * 登录测试页面（旧版）
     * 功能概述：处理登录测试页面的请求，返回登录测试页面视图
     */
    // 处理登录测试页面请求，映射路径"/test-login"，只接受GET请求
    @GetMapping("/test-login")
    // 登录测试页面处理方法，返回登录测试页面视图
    public String testLoginPage() {
        // 返回视图名称"testLogin"，Thymeleaf会自动查找templates/testLogin.html
        return "testLogin";
    }

    /**
     * 简单测试页面（旧版）
     * 功能概述：处理简单测试页面的请求，返回简单测试页面视图
     */
    // 处理简单测试页面请求，映射路径"/simple-test"，只接受GET请求
    @GetMapping("/simple-test")
    // 简单测试页面处理方法，返回简单测试页面视图
    public String simpleTestPage() {
        // 返回视图名称"simpleTest"，Thymeleaf会自动查找templates/simpleTest.html
        return "simpleTest";
    }

    /**
     * 订单中心测试页面（旧版）
     * 功能概述：处理订单中心测试页面的请求，返回订单中心测试页面视图
     */
    // 处理订单中心测试页面请求，映射路径"/test-order-center"，只接受GET请求
    @GetMapping("/test-order-center")
    // 订单中心测试页面处理方法，返回订单中心测试页面视图
    public String testOrderCenter() {
        // 返回视图名称"testOrderCenter"，Thymeleaf会自动查找templates/testOrderCenter.html
        return "testOrderCenter";
    }
}

