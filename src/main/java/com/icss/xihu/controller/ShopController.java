// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入商品实体类
import com.icss.xihu.model.Product;
// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入购物订单实体类
import com.icss.xihu.model.ShopOrder;
// 导入购物订单详情实体类
import com.icss.xihu.model.ShopOrderDetail;
// 导入购物订单Mapper接口
import com.icss.xihu.mapper.ShopOrderMapper;
// 导入商品服务接口
import com.icss.xihu.service.ProductService;
// 导入统一订单新服务接口
import com.icss.xihu.service.UnifiedOrderNewService;
// 导入购物订单服务接口
import com.icss.xihu.service.ShopOrderService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
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

// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为空（根路径）
@RequestMapping
// 购物控制器类，处理购物相关的HTTP请求，包括商品查询、购物车、订单结算等
public class ShopController {

    // 自动注入商品服务，Spring容器会自动查找并注入ProductService的实现类
    @Autowired
    // 商品服务对象，用于调用商品相关的业务逻辑
    private ProductService productService;

    // 自动注入统一订单新服务，Spring容器会自动查找并注入UnifiedOrderNewService的实现类
    @Autowired
    // 统一订单新服务对象，用于调用统一订单相关的业务逻辑
    private UnifiedOrderNewService unifiedOrderNewService;

    // 自动注入购物订单服务，Spring容器会自动查找并注入ShopOrderService的实现类
    @Autowired
    // 购物订单服务对象，用于调用购物订单相关的业务逻辑
    private ShopOrderService shopOrderService;

    // 自动注入购物订单Mapper，Spring容器会自动查找并注入ShopOrderMapper的实现类
    @Autowired
    // 购物订单Mapper对象，用于直接调用数据库操作方法
    private ShopOrderMapper shopOrderMapper;

    /**
     * 显示购物首页
     * 功能概述：处理购物首页的请求，返回购物首页视图
     */
    // 处理购物首页请求，映射路径"/shopping"，只接受GET请求
    @GetMapping("/shopping")
    // 购物首页处理方法，返回shoppingIndex.html模板
    public String shoppingIndex() {
        // 返回视图名称"shoppingIndex"，Thymeleaf会自动查找templates/shoppingIndex.html
        return "shoppingIndex";
    }

    /**
     * 获取商品列表（分页）
     * 功能概述：根据分页参数、关键词和分类查询商品列表，支持分页、搜索和分类筛选
     */
    // 处理获取商品列表请求，映射路径"/shop/products"，只接受GET请求
    @GetMapping("/shop/products")
    // 返回JSON格式的响应体，而不是视图
    @ResponseBody
    // 获取商品列表处理方法，接收分页参数、关键词和分类参数
    public Map<String, Object> list(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "12") int pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String category) {
        // 调用商品服务的getProductsWithPagination方法，获取分页商品列表
        return productService.getProductsWithPagination(page, pageSize, keyword, category);
    }

    /**
     * 获取商品详情（JSON）
     * 功能概述：根据商品ID查询商品详细信息，返回JSON格式数据
     */
    // 处理获取商品详情请求，映射路径"/shop/product/{id}"，只接受GET请求，使用路径变量
    @GetMapping("/shop/product/{id}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取商品详情处理方法，接收商品ID路径变量
    public Product detail(@PathVariable Long id) {
        // 调用商品服务的getProductById方法，根据商品ID查询商品信息
        return productService.getProductById(id);
    }

    /**
     * 显示商品详情页面
     * 功能概述：处理商品详情页面的请求，返回商品详情页面视图，前端通过异步接口获取商品数据
     */
    // 处理商品详情页面请求，映射路径"/shopping/product/{id}"，只接受GET请求，使用路径变量
    @GetMapping("/shopping/product/{id}")
    // 商品详情页面处理方法，接收商品ID路径变量
    public String productDetailPage(@PathVariable long id) {
        // 前端通过 /shop/product/{id} 异步获取并渲染
        // 返回视图名称"productDetail"，Thymeleaf会自动查找templates/productDetail.html
        return "productDetail";
    }

    /**
     * 购物订单结算
     * 功能概述：处理购物车结算请求，包括验证用户登录、计算总金额、创建购物订单、创建订单详情、创建统一订单并建立关联关系
     */
    // 处理购物订单结算请求，映射路径"/shop/order/checkout"，只接受POST请求
    @PostMapping("/shop/order/checkout")
    // 返回JSON格式的响应体
    @ResponseBody
    // 购物订单结算处理方法，接收请求体中的购物车数据和会话对象
    public Map<String, Object> checkout(@RequestBody Map<String, Object> payload, HttpSession session) {
        // 创建响应Map对象，用于封装返回数据
        Map<String, Object> resp = new HashMap<>();
        // 从会话中获取当前登录的用户对象
        Object user = session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            resp.put("success", false);
            // 设置错误码
            resp.put("code", "NOT_LOGIN");
            // 设置错误消息
            resp.put("message", "请先登录");
            // 返回结果
            return resp;
        }

        // 使用try-catch捕获异常
        try {
            // 获取用户ID
            // 将用户对象转换为User类型，并获取用户ID
            Integer userId = ((com.icss.xihu.model.User) user).getId();
            
            // 从请求体中获取购物车商品列表
            // 抑制未检查类型转换警告
            @SuppressWarnings("unchecked")
            // 从payload中获取items字段，并转换为List<Map<String, Object>>类型
            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
            // 判断购物车是否为空
            if (items == null || items.isEmpty()) {
                // 如果购物车为空，设置返回结果为失败
                resp.put("success", false);
                // 设置错误消息
                resp.put("message", "购物车为空");
                // 返回结果
                return resp;
            }

            // 1. 先计算总金额（根据订单详情计算）
            // 初始化总金额为0.0
            double calculatedTotalAmount = 0.0;
            // 遍历购物车中的每个商品
            for (Map<String, Object> item : items) {
                // 获取商品价格对象
                Object priceObj = item.get("price");
                // 获取商品数量对象
                Object qtyObj = item.get("qty");
                // 判断价格或数量是否为空
                if (priceObj == null || qtyObj == null) {
                    // 如果信息不完整，设置返回结果为失败
                    resp.put("success", false);
                    // 设置错误消息
                    resp.put("message", "商品信息不完整：缺少价格或数量");
                    // 返回结果
                    return resp;
                }
                // 将价格对象转换为double类型
                double price = Double.parseDouble(priceObj.toString());
                // 将数量对象转换为int类型
                int quantity = Integer.parseInt(qtyObj.toString());
                // 累加每个商品的小计（价格 * 数量）到总金额
                calculatedTotalAmount += price * quantity;
            }
            
            // 2. 创建购物订单（先创建订单，总金额初始为0，创建详情后更新）
            // 创建购物订单对象
            ShopOrder shopOrder = new ShopOrder();
            // 设置用户ID
            shopOrder.setUserId(userId);
            // 设置订单总金额为0（初始为0，创建详情后更新）
            shopOrder.setTotalAmount(java.math.BigDecimal.valueOf(0));
            // 设置订单状态为0（待支付）
            shopOrder.setOrderStatus(0);
            
            // 从 payload 中获取收货信息（如果有）
            // 获取收货地址
            Object shippingAddress = payload.get("shippingAddress");
            // 获取收货电话
            Object shippingPhone = payload.get("shippingPhone");
            // 获取收货人姓名
            Object shippingName = payload.get("shippingName");
            
            // 如果收货地址不为空，则设置到订单对象中
            if (shippingAddress != null) {
                // 将收货地址转换为字符串并设置到订单对象中
                shopOrder.setShippingAddress(shippingAddress.toString());
            }
            // 如果收货电话不为空，则设置到订单对象中
            if (shippingPhone != null) {
                // 将收货电话转换为字符串并设置到订单对象中
                shopOrder.setShippingPhone(shippingPhone.toString());
            }
            // 如果收货人姓名不为空，则设置到订单对象中
            if (shippingName != null) {
                // 将收货人姓名转换为字符串并设置到订单对象中
                shopOrder.setShippingName(shippingName.toString());
            }
            
            // 2. 创建购物订单（先创建订单，总金额初始为0，创建详情后更新）
            // 调用购物订单服务的createOrder方法创建订单，返回受影响的行数
            int result = shopOrderService.createOrder(shopOrder);
            
            // 判断订单创建是否成功（受影响行数大于0表示成功）
            if (result <= 0) {
                // 如果创建失败，设置返回结果为失败
                resp.put("success", false);
                // 设置错误消息
                resp.put("message", "购物订单创建失败");
                // 返回结果
                return resp;
            }
            
            // 获取插入后的订单ID（MyBatis会自动设置到shopOrder.id中）
            // 从订单对象中获取自动生成的订单ID
            Integer shopOrderId = shopOrder.getId();
            // 判断订单ID是否有效
            if (shopOrderId == null || shopOrderId <= 0) {
                // 如果订单ID无效，设置返回结果为失败
                resp.put("success", false);
                // 设置错误消息
                resp.put("message", "获取订单ID失败");
                // 返回结果
                return resp;
            }
            
            // 3. 创建订单详情（记录买了什么商品）
            // 遍历购物车中的每个商品，创建订单详情
            for (Map<String, Object> item : items) {
                // 获取商品ID对象
                Object idObj = item.get("id");
                // 获取商品名称对象
                Object nameObj = item.get("name");
                // 获取商品价格对象
                Object priceObj = item.get("price");
                // 获取商品数量对象
                Object qtyObj = item.get("qty");
                
                // 判断商品信息是否完整
                if (idObj == null || nameObj == null || priceObj == null || qtyObj == null) {
                    // 如果信息不完整，设置返回结果为失败
                    resp.put("success", false);
                    // 设置错误消息
                    resp.put("message", "商品信息不完整：缺少必要字段");
                    // 返回结果
                    return resp;
                }
                
                // 创建购物订单详情对象
                ShopOrderDetail detail = new ShopOrderDetail();
                // 设置订单号（使用订单号关联，而不是订单ID）
                detail.setOrderNo(shopOrder.getOrderNo());
                // 设置商品ID（将ID对象转换为int类型）
                detail.setProductId(Integer.parseInt(idObj.toString()));
                // 设置商品名称（将名称对象转换为字符串）
                detail.setProductName(nameObj.toString());
                // 将价格对象转换为double类型
                double price = Double.parseDouble(priceObj.toString());
                // 将数量对象转换为int类型
                int quantity = Integer.parseInt(qtyObj.toString());
                // 设置商品单价（转换为BigDecimal类型）
                detail.setProductPrice(java.math.BigDecimal.valueOf(price));
                // 设置商品数量
                detail.setQuantity(quantity);
                // 设置商品小计（价格 * 数量，转换为BigDecimal类型）
                detail.setSubtotal(java.math.BigDecimal.valueOf(price * quantity));
                
                // 调用购物订单服务的createOrderDetail方法创建订单详情
                shopOrderService.createOrderDetail(detail);
            }
            
            // 4. 更新订单总金额（根据订单详情计算的总金额）
            // 调用购物订单服务的updateTotalAmount方法，更新订单总金额为计算出的总金额
            shopOrderService.updateTotalAmount(shopOrderId, java.math.BigDecimal.valueOf(calculatedTotalAmount));
            
            // 每次结算都创建新的统一订单（不从购物车复用旧订单）
            // 设置订单标题
            String orderTitle = "购物订单";
            // 设置订单描述
            String orderDescription = "购物商城订单";
            
            // 调用统一订单新服务的createOrder方法创建统一订单
            String unifiedOrderNo = unifiedOrderNewService.createOrder(
                userId,  // 用户ID
                "SHOPPING",  // 订单类型为购物订单
                shopOrderId,  // 模块订单ID，即购物订单的ID
                orderTitle,  // 订单标题
                orderDescription,  // 订单描述
                java.math.BigDecimal.valueOf(calculatedTotalAmount)  // 订单金额
            );
            
            // 判断统一订单是否创建成功
            if (unifiedOrderNo == null) {
                // 如果创建失败，设置返回结果为失败
                resp.put("success", false);
                // 设置错误消息
                resp.put("message", "统一订单创建失败");
                // 返回结果
                return resp;
            }
            
            // 6. 更新购物订单的统一订单号（建立关联）
            // 调用购物订单Mapper的updateUnifiedOrderNo方法，将统一订单号关联到购物订单
            shopOrderMapper.updateUnifiedOrderNo(shopOrderId, unifiedOrderNo);
            
            // 设置返回结果为成功
            resp.put("success", true);
            // 返回统一订单号
            resp.put("orderNo", unifiedOrderNo);
            // 返回订单总金额
            resp.put("amount", calculatedTotalAmount);
            // 返回支付页面的重定向URL，包含统一订单号参数
            resp.put("redirectUrl", "/unified-new/payment/checkout?orderNo=" + unifiedOrderNo);
            
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            resp.put("success", false);
            // 设置错误消息，包含异常信息
            resp.put("message", "订单创建失败：" + e.getMessage());
        }
        
        // 返回结果
        return resp;
    }
}


