// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入地址实体类
import com.icss.xihu.model.Address;
// 导入酒店实体类
import com.icss.xihu.model.Hotel;
// 导入酒店多房型实体类
import com.icss.xihu.model.HotelMany;
// 导入酒店订单实体类
import com.icss.xihu.model.HotelOrder;
// 导入酒店评价实体类
import com.icss.xihu.model.HotelReview;
// 导入用户实体类
import com.icss.xihu.model.User;
// 导入酒店Mapper接口
import com.icss.xihu.mapper.HotelMapper;
// 导入地址服务接口
import com.icss.xihu.service.AddressService;
// 导入酒店订单服务接口
import com.icss.xihu.service.HotelOrderService;
// 导入酒店服务接口
import com.icss.xihu.service.HotelService;
// 导入统一订单新服务接口
import com.icss.xihu.service.UnifiedOrderNewService;
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
// 指定该控制器的请求路径前缀为"/hc"，自定义映射路径
@RequestMapping("/hc")
// 酒店控制器类，处理酒店相关的HTTP请求，包括酒店查询、预订、订单管理等
public class HotelController {

    /**
     * 显示我的酒店订单页面
     * 功能概述：处理我的酒店订单页面的请求，返回订单列表页面视图
     */
    // 处理我的酒店订单页面请求，映射路径"/myHotelOrder"
    @RequestMapping("/myHotelOrder")
    // 显示我的订单页面处理方法，返回myHotelOrder.html模板
    public String showMyOrders() {
        // 返回视图名称"myHotelOrder"，Thymeleaf会自动查找templates/myHotelOrder.html
        return "myHotelOrder";
    }

    // 自动注入酒店订单服务，Spring容器会自动查找并注入HotelOrderService的实现类
    @Autowired
    // 酒店订单服务对象，用于调用酒店订单相关的业务逻辑
    private HotelOrderService hotelOrderService;

    // 自动注入统一订单新服务，Spring容器会自动查找并注入UnifiedOrderNewService的实现类
    @Autowired
    // 统一订单新服务对象，用于调用统一订单相关的业务逻辑
    private UnifiedOrderNewService unifiedOrderNewService;

    // 自动注入酒店服务，Spring容器会自动查找并注入HotelService的实现类
    @Autowired
    // 酒店服务对象，用于调用酒店相关的业务逻辑
    private HotelService hotelService;
    
    // 自动注入酒店Mapper，用于直接操作数据库
    @Autowired
    // 酒店Mapper对象，用于调用数据库操作方法
    private HotelMapper hotelMapper;

    /**
     * 创建酒店预订订单
     * 功能概述：处理酒店预订请求，包括生成订单号、创建酒店订单、创建订单详情、创建统一订单并建立关联关系
     */
    // 处理酒店预订请求，映射路径"/bookings"
    @RequestMapping("/bookings")
    // 返回JSON格式的响应体，而不是视图
    @ResponseBody
    // 创建酒店预订订单处理方法，接收酒店订单对象和会话对象
    public Map<String, Object> createBooking(@RequestBody HotelOrder order, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        // 使用try-catch捕获异常
        try {
            // 1. 获取用户信息
            // 从会话中获取当前登录的用户对象
            User user = (User) session.getAttribute("user");
            // 判断用户是否已登录
            if (user == null) {
                // 如果未登录，设置返回码为401（未授权）
                result.put("code", 401);
                // 设置错误消息
                result.put("message", "请先登录");
                // 返回结果
                return result;
            }
            
            // 2. 生成订单号
            // 调用酒店订单服务的generateOrderNo方法生成唯一的订单号
            String orderNo = hotelOrderService.generateOrderNo();
            // 将生成的订单号设置到订单对象中
            order.setOrderNo(orderNo);
            // 将当前登录用户的ID设置到订单对象中
            order.setUid(user.getId());
            
            // 3. 创建酒店订单
            // 调用酒店订单服务的createOrder方法创建酒店订单，返回受影响的行数
            int resultCode = hotelOrderService.createOrder(order);
            // 判断订单创建是否成功（受影响行数大于0表示成功）
            if (resultCode > 0) {
                // 获取创建后的酒店订单ID（自动生成的主键）
                Integer hotelOrderId = order.getId();
                
                // 4. 获取酒店信息（用于创建订单详情）
                // 根据酒店ID查询酒店信息
                Hotel hotel = hotelService.findById(order.getHid());
                // 判断酒店是否存在，存在则使用酒店名称，不存在则使用默认值
                String hotelName = hotel != null ? hotel.getName() : "未知酒店";
                
                // 5. 计算入住天数
                // 将入住日期字符串解析为LocalDate对象
                java.time.LocalDate checkInDate = java.time.LocalDate.parse(order.getIndate());
                // 将离店日期字符串解析为LocalDate对象
                java.time.LocalDate checkOutDate = java.time.LocalDate.parse(order.getOutdate());
                // 计算两个日期之间的天数差
                int nights = (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                // 如果计算出的天数小于等于0，则至少设置为1晚
                if (nights <= 0) nights = 1;
                
                // 6. 计算房型单价（总价 / 入住天数）
                // 将总价除以入住天数得到每晚的单价
                double roomPrice = order.getPrice() / nights;
                
                // 7. 创建订单详情
                // 创建酒店订单详情对象
                com.icss.xihu.model.HotelOrderDetail detail = new com.icss.xihu.model.HotelOrderDetail();
                // 设置订单号
                detail.setOrderNo(orderNo);
                // 设置酒店ID
                detail.setHotelId(order.getHid());
                // 设置酒店名称
                detail.setHotelName(hotelName);
                // 设置房型名称
                detail.setRoomType(order.getRname());
                // 设置入住日期
                detail.setCheckInDate(checkInDate);
                // 设置离店日期
                detail.setCheckOutDate(checkOutDate);
                // 设置入住天数
                detail.setNights(nights);
                // 设置入住人数
                detail.setGuests(order.getGuests());
                // 设置房型单价（转换为BigDecimal类型）
                detail.setRoomPrice(java.math.BigDecimal.valueOf(roomPrice));
                // 设置订单小计（总价，转换为BigDecimal类型）
                detail.setSubtotal(java.math.BigDecimal.valueOf(order.getPrice()));
                
                // 调用酒店订单服务的createOrderDetail方法创建订单详情
                hotelOrderService.createOrderDetail(detail);
                
                // 8. 每次预订都创建新的统一订单（不从详情页复用旧订单）
                // 构建订单标题，格式为"房型名称+酒店预订"
                String orderTitle = order.getRname() + "酒店预订";
                // 设置订单描述
                String orderDescription = "酒店预订订单";
                
                // 调用统一订单新服务的createOrder方法创建统一订单
                String unifiedOrderNo = unifiedOrderNewService.createOrder(
                    user.getId(),  // 用户ID
                    "HOTEL",  // 订单类型为酒店订单
                    hotelOrderId,  // 模块订单ID，即酒店订单的ID
                    orderTitle,  // 订单标题
                    orderDescription,  // 订单描述
                    java.math.BigDecimal.valueOf(order.getPrice())  // 订单金额
                );
                
                // 判断统一订单是否创建成功
                if (unifiedOrderNo == null) {
                    // 如果创建失败，设置返回码为500（服务器错误）
                    result.put("code", 500);
                    // 设置错误消息
                    result.put("message", "统一订单创建失败");
                    // 返回结果
                    return result;
                }
                
                // 9. 更新酒店订单的统一订单号（建立关联）
                // 调用酒店订单服务的updateUnifiedOrderNo方法，将统一订单号关联到酒店订单
                hotelOrderService.updateUnifiedOrderNo(hotelOrderId, unifiedOrderNo);
                
                // 设置返回码为200（成功）
                result.put("code", 200);
                // 返回酒店订单号（order_no）
                result.put("data", orderNo);
                // 也返回orderNo字段（兼容前端）
                result.put("orderNo", orderNo);
                // 返回统一订单号
                result.put("unifiedOrderNo", unifiedOrderNo);
                // 返回支付页面的重定向URL，包含统一订单号参数
                result.put("redirectUrl", "/unified-new/payment/checkout?orderNo=" + unifiedOrderNo);
            // 订单创建失败
            } else {
                // 设置返回码为500（服务器错误）
                result.put("code", 500);
                // 设置错误消息
                result.put("message", "预订失败，请稍后再试");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
            // 设置返回码为500（服务器错误）
            result.put("code", 500);
            // 设置错误消息，包含异常信息
            result.put("message", "系统错误，预订失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }

    /**
     * 获取用户订单列表
     * 功能概述：根据用户ID查询该用户的所有酒店订单列表
     */
    // 处理获取用户订单列表请求，映射路径"/orders"
    @RequestMapping("/orders")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取用户订单列表处理方法，接收用户ID参数
    public Map<String, Object> getOrdersByUserId(@RequestParam("userId") String userId) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        // 使用try-catch捕获异常
        try {
            // 调用酒店订单服务的getOrdersByUserId方法，根据用户ID查询订单列表
            List<HotelOrder> orders = hotelOrderService.getOrdersByUserId(userId);
            // 设置返回码为200（成功）
            result.put("code", 200);
            // 设置成功消息
            result.put("message", "获取订单成功");
            // 将订单列表添加到返回结果中
            result.put("data", orders);
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
            // 设置返回码为500（服务器错误）
            result.put("code", 500);
            // 设置错误消息
            result.put("message", "获取订单失败");
        }
        // 返回结果
        return result;
    }

    /**
     * 取消订单
     * 功能概述：根据订单ID取消指定的酒店订单，更新订单状态为已取消
     */
    // 处理取消订单请求，映射路径"/orders/{orderId}/cancel"，使用路径变量
    @RequestMapping("/orders/{orderId}/cancel")
    // 返回JSON格式的响应体
    @ResponseBody
    // 取消订单处理方法，接收订单ID路径变量
    public Map<String, Object> cancelOrder(@PathVariable("orderId") Long orderId) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        // 使用try-catch捕获异常
        try {
            // 调用酒店订单服务的cancelOrder方法取消订单，返回受影响的行数
            int rows = hotelOrderService.cancelOrder(orderId);
            // 判断取消是否成功（受影响行数大于0表示成功）
            if (rows > 0) {
                // 设置返回码为200（成功）
                result.put("code", 200);
                // 设置成功消息
                result.put("message", "取消订单成功");
            // 取消失败
            } else {
                // 设置返回码为400（客户端错误）
                result.put("code", 400);
                // 设置错误消息
                result.put("message", "取消订单失败，订单不存在或状态不可取消");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
            // 设置返回码为500（服务器错误）
            result.put("code", 500);
            // 设置错误消息
            result.put("message", "系统错误，取消订单失败");
        }
        // 返回结果
        return result;
    }

    /**
     * 确认订单
     * 功能概述：根据订单ID确认指定的酒店订单，更新订单状态为已确认
     */
    // 处理确认订单请求，映射路径"/orders/{orderId}/makeSure"，使用路径变量
    @RequestMapping("/orders/{orderId}/makeSure")
    // 返回JSON格式的响应体
    @ResponseBody
    // 确认订单处理方法，接收订单ID路径变量
    public Map<String, Object> makeSureOrder(@PathVariable("orderId") Long orderId) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        // 使用try-catch捕获异常
        try {
            // 调用酒店订单服务的makeSureOrder方法确认订单，返回受影响的行数
            int rows = hotelOrderService.makeSureOrder(orderId);
            // 判断确认是否成功（受影响行数大于0表示成功）
            if (rows > 0) {
                // 设置返回码为200（成功）
                result.put("code", 200);
                // 设置成功消息
                result.put("message", "确认订单成功");
            // 确认失败
            } else {
                // 设置返回码为400（客户端错误）
                result.put("code", 400);
                // 设置错误消息
                result.put("message", "确认订单失败，订单不存在或状态不可取消");
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息到控制台
            e.printStackTrace();
            // 设置返回码为500（服务器错误）
            result.put("code", 500);
            // 设置错误消息
            result.put("message", "系统错误，确认订单失败");
        }
        // 返回结果
        return result;
    }

    // 自动注入地址服务，Spring容器会自动查找并注入AddressService的实现类
    @Autowired
    // 地址服务对象，用于调用地址相关的业务逻辑
    private AddressService addressService;

    /**
     * 查询所有省份
     * 功能概述：获取系统中所有省份的列表，用于地址选择
     */
    // 处理查询所有省份请求，映射路径"/provinces"
    @RequestMapping("/provinces")
    // 返回JSON格式的响应体
    @ResponseBody
    // 查询所有省份处理方法，返回省份列表
    public List<Address> getProvinces() {
        // 调用地址服务的getProvinces方法获取所有省份列表
        return addressService.getProvinces();
    }

    /**
     * 根据省ID查询城市
     * 功能概述：根据省份ID查询该省份下的所有城市列表，用于级联地址选择
     */
    // 处理根据省ID查询城市请求，映射路径"/cities"
    @RequestMapping("/cities")
    // 返回JSON格式的响应体
    @ResponseBody
    // 根据省ID查询城市处理方法，接收省份ID参数
    public List<Address> getCitiesByProvinceId(Integer provinceId) {
        // 调用地址服务的getCitiesByProvinceId方法，根据省份ID查询城市列表
        return addressService.getCitiesByProvinceId(provinceId);
    }

    /**
     * 查询所有酒店
     * 功能概述：获取系统中所有酒店的列表，用于酒店列表页面展示
     */
    // 调用业务层获取操作结果
    // 自动注入酒店服务，Spring容器会自动查找并注入HotelService的实现类
    @Autowired
    // 酒店服务对象，用于调用酒店相关的业务逻辑（使用简写变量名hs）
    HotelService hs;
    // 处理查询所有酒店请求，映射路径"/fAll"
    @RequestMapping("/fAll")
    // 查询所有酒店处理方法，接收Map对象用于传递数据到视图
    public String findAll(Map<Object,Object> map){
        // 调用酒店服务的findAll方法获取所有酒店列表
        List<Hotel> list=hs.findAll();
        // 将酒店列表添加到Map中，键名为"hotelList"，供前端模板使用
        map.put("hotelList",list);
        // 返回视图名称"hotel"，Thymeleaf会自动查找templates/hotel.html
        return "hotel";
    }



    /**
     * 根据ID查询酒店详情
     * 功能概述：根据酒店ID查询单个酒店的详细信息，用于酒店详情页面展示
     */
    // 处理根据ID查询酒店详情请求，映射路径"findById"
    @RequestMapping("findById")
    // 根据ID查询酒店详情处理方法，接收酒店ID参数和Map对象用于传递数据到视图
    public String findById(@RequestParam("id") int id,Map<Object,Object> map){
        // 打印酒店ID到控制台，用于调试
        System.out.println(id);
        // 1、接取前台传递的数据--完成
        // 2、利用自动注入方式(创建的接口/实现类的对象)调用对应的功能，并接取返回值
        // 调用酒店服务的findById方法，根据酒店ID查询酒店信息
        Hotel h = hs.findById(id);
        // 查询酒店评论列表
        List<HotelReview> reviews = hotelMapper.findReviewsByHotelId(id);
        // 查询酒店评分统计（平均分和评论数）
        Map<String, Object> ratingStats = hotelMapper.getHotelRatingStats(id);
        // 处理评分统计数据，确保格式正确
        if (ratingStats != null) {
            // 处理平均分（可能是BigDecimal类型）
            Object avgRatingObj = ratingStats.get("avgRating");
            if (avgRatingObj != null) {
                Double avgRating;
                if (avgRatingObj instanceof java.math.BigDecimal) {
                    avgRating = ((java.math.BigDecimal) avgRatingObj).doubleValue();
                } else if (avgRatingObj instanceof Number) {
                    avgRating = ((Number) avgRatingObj).doubleValue();
                } else {
                    avgRating = Double.parseDouble(avgRatingObj.toString());
                }
                // 更新酒店的评分
                h.setScore(avgRating);
                // 更新ratingStats中的avgRating为Double类型，方便前端使用
                ratingStats.put("avgRating", avgRating);
            }
            // 处理评论数（可能是Long类型）
            Object reviewCountObj = ratingStats.get("reviewCount");
            if (reviewCountObj != null && reviewCountObj instanceof Number) {
                ratingStats.put("reviewCount", ((Number) reviewCountObj).longValue());
            }
        }
        // 3、处理返回值(省略)
        // 4、创建Map/Model/ModelAndView 对象，通过对象调用对应的方法，向前台传递数据
        // 将酒店信息添加到Map中，键名为"hotel"，供前端模板使用
        map.put("hotel",h);
        // 将评论列表添加到Map中，键名为"reviews"，供前端模板使用
        map.put("reviews", reviews != null ? reviews : new java.util.ArrayList<>());
        // 将评分统计添加到Map中，键名为"ratingStats"，供前端模板使用
        map.put("ratingStats", ratingStats != null ? ratingStats : new java.util.HashMap<>());
        // 5、跳转页面(只需要写页面的名字即可，前缀和后缀已经在配置文件中配置完成)
        // 返回视图名称"hotelInfo"，Thymeleaf会自动查找templates/hotelInfo.html
        return "hotelInfo";
    }

    /**
     * 根据ID查询酒店多房型信息
     * 功能概述：根据酒店ID查询该酒店的所有房型信息，用于酒店多房型详情页面展示
     */
    // 处理根据ID查询酒店多房型信息请求，映射路径"findByMany"
    @RequestMapping("findByMany")
    // 根据ID查询酒店多房型信息处理方法，接收酒店ID参数和Map对象用于传递数据到视图
    public String findByMany(@RequestParam("id")int id,Map<Object,Object> map){
        // 1、接取前台传递的数据
        // 2、利用自动注入方式创建的接口/实现类的对象调用对应的功能，并接取返回值
        // 调用酒店服务的findByMany方法，根据酒店ID查询该酒店的所有房型列表
        List<HotelMany> list = hs.findByMany(id);
        // 3、处理返回值(省略)
        // 4、创建Map/Model/ModelAndView 对象，通过对象调用对应的方法，向前台传递数据
        // 打印房型列表到控制台，用于调试
        System.out.println(list);
        
        // 检查列表是否为空，避免IndexOutOfBoundsException
        // 判断列表不为null且不为空
        if (list != null && !list.isEmpty()) {
            // 将第一个房型信息添加到Map中，键名为"m"，供前端模板使用
            map.put("m", list.get(0));
            // 将整个房型列表添加到Map中，键名为"mlist"，供前端模板使用
            map.put("mlist", list);
        // 如果列表为空
        } else {
            // 如果没有找到酒店，可以重定向到酒店列表页面或显示错误信息
            // 将错误信息添加到Map中
            map.put("error", "未找到指定的酒店信息");
            // 重定向到酒店首页
            return "redirect:/hc/f2";
        }
        // 5、跳转页面(只需要写页面的名字即可，前缀和后缀已经在配置文件中配置完成)
        // 返回视图名称"hotelManyInfo"，Thymeleaf会自动查找templates/hotelManyInfo.html
        return "hotelManyInfo";
    }

    /**
     * 查询所有酒店（酒店首页）
     * 功能概述：获取系统中所有酒店的列表，用于酒店首页展示
     */
    // 处理查询所有酒店请求，映射路径"f2"
    @RequestMapping("f2")
    // 查询所有酒店处理方法，接收Map对象用于传递数据到视图
    public String findBy2(Map<Object,Object> mm){
        // 通过接口对象，调用指定的功能，并接取返回值
        // 调用酒店服务的findAll方法获取所有酒店列表
        List<Hotel> hList = hs.findAll();
        // 创建map集合对象，通过put方法将集合传递回页面
        // 将酒店列表添加到Map中，键名为"hotelList"，供前端模板使用
        mm.put("hotelList",hList);
        // 返回视图名称"hotelIndex"，Thymeleaf会自动查找templates/hotelIndex.html
        return "hotelIndex";
    }

    /**
     * 搜索酒店
     * 功能概述：根据关键词搜索酒店，支持酒店名称模糊匹配，返回JSON格式的酒店列表
     */
    // 新增搜索接口（返回JSON数据）
    // 处理搜索酒店请求，映射路径"/search"
    @RequestMapping("/search")
    // 标记为返回JSON数据，而不是视图
    @ResponseBody
    // 搜索酒店处理方法，接收关键词参数
    public List<Hotel> search(@RequestParam("keyword") String keyword) {
        // 调用酒店服务的searchByKeyword方法，根据关键词搜索酒店列表
        return hs.searchByKeyword(keyword);
    }

    /**
     * 获取热门酒店
     * 功能概述：获取系统中的热门酒店列表，通常按评分或预订量排序，返回JSON格式数据
     */
    // 新增：获取热门酒店
    // 处理获取热门酒店请求，映射路径"/hotHotels"
    @RequestMapping("/hotHotels")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取热门酒店处理方法，返回热门酒店列表
    public List<Hotel> getHotHotels() {
        // 调用酒店服务的getHotHotels方法获取热门酒店列表
        return hs.getHotHotels();
    }

    /**
     * 查询所有活跃酒店
     * 功能概述：获取系统中所有状态为活跃的酒店列表，返回JSON格式数据
     */
    // 处理查询所有活跃酒店请求，映射路径"/allHotels"
    @RequestMapping("/allHotels")
    // 返回JSON格式的响应体
    @ResponseBody
    // 查询所有活跃酒店处理方法，返回活跃酒店列表
    public List<Hotel> findAllActive(){
        // 调用酒店服务的findAllActive方法获取所有活跃酒店列表
        return hs.findAllActive();
    }

    /**
     * 获取酒店房间信息
     * 功能概述：根据酒店ID获取该酒店的所有房型信息，返回JSON格式数据，用于AJAX异步加载
     */
    // 获取酒店房间信息 - 返回JSON数据
    // 处理获取酒店房间信息请求，映射路径"/api/rooms/{hotelId}"，使用路径变量
    @RequestMapping("/api/rooms/{hotelId}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取酒店房间信息处理方法，接收酒店ID路径变量
    public List<HotelMany> getHotelRooms(@PathVariable("hotelId") int hotelId) {
        // 调用酒店服务的findByMany方法，根据酒店ID查询该酒店的所有房型列表
        return hs.findByMany(hotelId);
    }

    /**
     * 提交酒店评论
     * 功能概述：接收前端提交的酒店评论信息，保存到数据库
     */
    // 处理提交酒店评论请求，映射路径"/review/submit"，使用POST方法
    @RequestMapping(value = "/review/submit", method = RequestMethod.POST)
    // 返回JSON数据
    @ResponseBody
    // 提交酒店评论处理方法，接收评论信息参数和HTTP会话对象
    public Map<String, Object> submitReview(@RequestParam("hotelId") Integer hotelId,
                                           @RequestParam("rating") Double rating,
                                           @RequestParam("content") String content,
                                           HttpSession session) {
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();
        try {
            // 从会话中获取当前登录用户
            User user = (User) session.getAttribute("user");
            // 判断用户是否登录
            if (user == null) {
                // 如果用户未登录，返回错误信息
                result.put("success", false);
                result.put("message", "请先登录");
                return result;
            }
            
            // 创建评论对象
            HotelReview review = new HotelReview();
            review.setHotelId(hotelId);
            review.setUserId(user.getId());
            review.setUsername(user.getUsername());
            review.setUserAvatar(user.getAvatar());
            review.setRating(new java.math.BigDecimal(rating));
            review.setContent(content);
            review.setImages(""); // 暂时不支持图片
            
            // 插入评论到数据库
            int rows = hotelMapper.insertReview(review);
            // 判断插入是否成功
            if (rows > 0) {
                // 插入成功后，重新计算并更新酒店的评分
                Map<String, Object> ratingStats = hotelMapper.getHotelRatingStats(hotelId);
                if (ratingStats != null && ratingStats.get("avgRating") != null) {
                    // 处理平均分（可能是BigDecimal类型）
                    Object avgRatingObj = ratingStats.get("avgRating");
                    Double avgRating;
                    if (avgRatingObj instanceof java.math.BigDecimal) {
                        avgRating = ((java.math.BigDecimal) avgRatingObj).doubleValue();
                    } else if (avgRatingObj instanceof Number) {
                        avgRating = ((Number) avgRatingObj).doubleValue();
                    } else {
                        avgRating = Double.parseDouble(avgRatingObj.toString());
                    }
                    // 更新酒店表中的评分字段
                    hotelMapper.updateHotelScore(hotelId, avgRating);
                }
                // 插入成功，返回成功信息
                result.put("success", true);
                result.put("message", "评论提交成功");
            } else {
                // 插入失败，返回错误信息
                result.put("success", false);
                result.put("message", "评论提交失败");
            }
        } catch (Exception e) {
            // 捕获异常，返回错误信息
            result.put("success", false);
            result.put("message", "评论提交失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }
    
    /**
     * 获取酒店评论列表
     * 功能概述：根据酒店ID获取该酒店的所有评论信息，返回JSON格式数据
     */
    // 处理获取酒店评论列表请求，映射路径"/review/list"
    @RequestMapping("/review/list")
    // 返回JSON数据
    @ResponseBody
    // 获取酒店评论列表处理方法，接收酒店ID参数
    public List<HotelReview> getReviewList(@RequestParam("hotelId") Integer hotelId) {
        // 调用Mapper层方法，查询酒店评论列表并返回
        return hotelMapper.findReviewsByHotelId(hotelId);
    }

}

