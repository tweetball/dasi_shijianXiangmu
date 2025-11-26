// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入缴费账单实体类
import com.icss.xihu.model.PaymentBill;
// 导入缴费类型实体类
import com.icss.xihu.model.PaymentType;
// 导入用户实体类
import com.icss.xihu.model.User;
// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入缴费服务接口
import com.icss.xihu.service.PaymentService;
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
// 导入HashMap集合类
import java.util.HashMap;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 生活缴费控制器
 * 功能概述：处理生活缴费相关的HTTP请求，包括缴费类型查询、账单查询、缴费处理等
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/payment"
@RequestMapping("/payment")
// 生活缴费控制器类，处理生活缴费相关的HTTP请求
public class PaymentController {

    // 自动注入缴费服务，Spring容器会自动查找并注入PaymentService的实现类
    @Autowired
    // 缴费服务对象，用于调用缴费相关的业务逻辑
    private PaymentService paymentService;

    // 自动注入统一订单新服务，Spring容器会自动查找并注入UnifiedOrderNewService的实现类
    @Autowired
    // 统一订单新服务对象，用于调用统一订单相关的业务逻辑
    private UnifiedOrderNewService unifiedOrderNewService;

    /**
     * 生活缴费首页
     * 功能概述：处理生活缴费首页的请求，需要用户登录，返回缴费类型列表
     */
    // 处理生活缴费首页请求，映射路径"/payment"，只接受GET请求
    @GetMapping
    // 生活缴费首页处理方法，需要登录才能访问
    public String index(HttpSession session, Model model) {
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，重定向到登录页面，并传递重定向参数
            return "redirect:/user/login?redirect=/payment";
        }

        // 获取缴费类型
        // 调用缴费服务的getAllPaymentTypes方法获取所有缴费类型列表
        List<PaymentType> paymentTypes = paymentService.getAllPaymentTypes();
        // 将缴费类型列表添加到模型中，供前端模板使用，键名为"paymentTypes"
        model.addAttribute("paymentTypes", paymentTypes);

        // 获取用户缴费账户 - 暂时注释掉，因为接口中没有这个方法
        // List<Map<String, Object>> userAccounts = paymentService.getUserPaymentAccounts(user.getId());
        // model.addAttribute("userAccounts", userAccounts);

        // 返回视图名称"paymentIndex"，Thymeleaf会自动查找templates/paymentIndex.html
        return "paymentIndex";
    }

    /**
     * 获取用户账单列表
     * 功能概述：根据用户ID和账单状态（可选）查询账单列表，支持按状态筛选，返回JSON格式数据
     */
    // 处理获取用户账单列表请求，映射路径"/bills"，只接受GET请求
    @GetMapping("/bills")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取用户账单列表处理方法，接收账单状态筛选参数（可选，0=未支付，1=已支付）
    public Map<String, Object> getBills(HttpSession session,
                                        @RequestParam(required = false) Integer status) {
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

        // 声明账单列表变量
        List<PaymentBill> bills;
        // 判断状态参数是否不为空
        if (status != null) {
            // 如果状态不为空，调用缴费服务的getBillsByUserIdAndStatus方法，根据用户ID和状态查询账单列表
            bills = paymentService.getBillsByUserIdAndStatus(user.getId(), status);
        // 如果状态为空
        } else {
            // 调用缴费服务的getBillsByUserId方法，根据用户ID查询所有账单列表
            bills = paymentService.getBillsByUserId(user.getId());
        }
        // 设置返回结果为成功
        result.put("success", true);
        // 将账单列表添加到返回结果中
        result.put("list", bills);
        // 返回结果
        return result;
    }

    /**
     * 获取账单详情
     * 功能概述：根据账单ID查询账单详细信息，需要用户登录，返回JSON格式数据
     */
    // 处理获取账单详情请求，映射路径"/bill/{billId}"，使用路径变量
    @GetMapping("/bill/{billId}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取账单详情处理方法，接收账单ID路径变量
    public Map<String, Object> getBillDetail(@PathVariable Integer billId, HttpSession session) {
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

        // 调用缴费服务的getBillById方法，根据账单ID查询账单信息
        PaymentBill bill = paymentService.getBillById(billId);
        // 判断账单是否存在
        if (bill != null) {
            // 如果账单存在，设置返回结果为成功
            result.put("success", true);
            // 将账单信息添加到返回结果中
            result.put("bill", bill);
        // 如果账单不存在
        } else {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "账单不存在或无权访问");
        }
        // 返回结果
        return result;
    }

    /**
     * 处理缴费
     * 功能概述：处理生活缴费请求，包括验证用户登录、检查账单状态、创建或复用统一订单并建立关联关系
     */
    // 处理缴费请求，映射路径"/pay"，只接受POST请求
    @PostMapping("/pay")
    // 返回JSON格式的响应体
    @ResponseBody
    // 处理缴费方法，接收请求体中的参数和会话对象
    public Map<String, Object> processPayment(@RequestBody Map<String, Object> params, HttpSession session) {
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

        // 从请求参数中获取账单ID
        Integer billId = (Integer) params.get("billId");
        // 判断账单ID是否为空
        if (billId == null) {
            // 如果账单ID为空，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "账单ID不能为空");
            // 返回结果
            return result;
        }

        // 使用try-catch捕获异常
        try {
            // 1. 获取账单信息
            // 调用缴费服务的getBillById方法，根据账单ID查询账单信息
            PaymentBill bill = paymentService.getBillById(billId);
            // 判断账单是否存在
            if (bill == null) {
                // 如果账单不存在，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "账单不存在");
                // 返回结果
                return result;
            }

            // 2. 检查账单是否已支付
            // 判断账单状态是否为已支付（1表示已支付）
            if (bill.getBillStatus() == 1) {
                // 如果账单已支付，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "该账单已支付，无需重复支付");
                // 返回结果
                return result;
            }

            // 3. 检查是否已存在待支付的订单（避免重复创建）
            // 调用统一订单新服务的findUnpaidOrderByModuleOrderId方法，查找是否存在待支付的订单
            UnifiedOrderNew existingOrder = unifiedOrderNewService.findUnpaidOrderByModuleOrderId(
                billId, "PAYMENT"
            );
            
            // 声明统一订单号变量
            String unifiedOrderNo;
            // 判断是否已存在待支付订单
            if (existingOrder != null) {
                // 如果已存在待支付订单，直接返回该订单号
                // 获取已存在订单的订单号
                unifiedOrderNo = existingOrder.getOrderNo();
                // 设置提示消息
                result.put("message", "订单已存在，跳转到支付页面");
            // 如果不存在待支付订单
            } else {
                // 如果不存在待支付订单，创建新订单
                // 构建订单标题，格式为"缴费类型名称+缴费"
                String orderTitle = bill.getPaymentTypeName() + "缴费";
                // 设置订单描述
                String orderDescription = "生活缴费订单 - " + bill.getPaymentTypeName();
                
                // 调用统一订单新服务的createOrder方法创建统一订单
                unifiedOrderNo = unifiedOrderNewService.createOrder(
                    user.getId(),  // 用户ID
                    "PAYMENT",  // 订单类型为生活缴费订单
                    billId,  // 模块订单ID，即账单ID
                    orderTitle,  // 订单标题
                    orderDescription,  // 订单描述
                    bill.getBillAmount()  // 订单金额
                );
                
                // 判断统一订单是否创建成功
                if (unifiedOrderNo == null) {
                    // 如果创建失败，设置返回结果为失败
                    result.put("success", false);
                    // 设置错误消息
                    result.put("message", "订单创建失败");
                    // 返回结果
                    return result;
                }
                // 设置提示消息
                result.put("message", "订单创建成功");
            }
            
            // 设置返回结果为成功
            result.put("success", true);
            // 返回统一订单号
            result.put("orderNo", unifiedOrderNo);
            // 返回支付页面的重定向URL，包含统一订单号参数
            result.put("redirectUrl", "/unified-new/payment/checkout?orderNo=" + unifiedOrderNo);
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "系统错误，请稍后重试");
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    /**
     * 获取缴费统计信息
     * 功能概述：获取当前用户的缴费统计信息，包括缴费总额、缴费次数等，返回JSON格式数据
     */
    // 处理获取缴费统计信息请求，映射路径"/stats"，只接受GET请求
    @GetMapping("/stats")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取缴费统计信息处理方法，返回缴费统计信息
    public Map<String, Object> getPaymentStats(HttpSession session) {
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
            // 设置错误码
            result.put("code", "NOT_LOGIN");
            // 返回结果
            return result;
        }

        // 调用缴费服务的getPaymentStats方法获取缴费统计信息
        Map<String, Object> stats = paymentService.getPaymentStats(user.getId());
        // 设置返回结果为成功
        result.put("success", true);
        // 将统计信息添加到返回结果中
        result.put("stats", stats);
        // 返回结果
        return result;
    }
}

