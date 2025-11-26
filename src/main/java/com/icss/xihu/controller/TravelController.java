// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入旅游目的地实体类
import com.icss.xihu.model.TravelDestination;
// 导入统一订单新实体类
import com.icss.xihu.model.UnifiedOrderNew;
// 导入旅游订单实体类
import com.icss.xihu.model.TravelOrder;
// 导入旅游服务接口
import com.icss.xihu.service.TravelService;
// 导入统一订单新服务接口
import com.icss.xihu.service.UnifiedOrderNewService;
// 导入旅游订单服务接口
import com.icss.xihu.service.TravelOrderService;
// 导入字符集修复工具类
import com.icss.xihu.util.CharsetFixUtil;
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

// 导入Collections工具类
import java.util.Collections;
// 导入HashMap集合类
import java.util.HashMap;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 旅游控制器
 * 功能概述：处理旅游相关的HTTP请求，包括景点查询、旅游信息获取、旅游订单创建等
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/travel"
@RequestMapping("/travel")
// 旅游控制器类，处理旅游相关的HTTP请求
public class TravelController {

    // 自动注入旅游服务，Spring容器会自动查找并注入TravelService的实现类
    @Autowired
    // 旅游服务对象，用于调用旅游相关的业务逻辑
    private TravelService travelService;
    
    // 自动注入字符集修复工具，Spring容器会自动查找并注入CharsetFixUtil的实现类
    @Autowired
    // 字符集修复工具对象，用于修复数据库字符集乱码问题
    private CharsetFixUtil charsetFixUtil;

    // 自动注入旅游订单服务，Spring容器会自动查找并注入TravelOrderService的实现类
    @Autowired
    // 旅游订单服务对象，用于调用旅游订单相关的业务逻辑
    private TravelOrderService travelOrderService;
    
    // 自动注入统一订单新服务，Spring容器会自动查找并注入UnifiedOrderNewService的实现类
    @Autowired
    // 统一订单新服务对象，用于调用统一订单相关的业务逻辑
    private UnifiedOrderNewService unifiedOrderNewService;

    /**
     * 旅游首页 - 显示中国地图
     * 功能概述：处理旅游首页的请求，返回旅游首页视图，显示中国地图
     */
    // 处理旅游首页请求，映射路径"/travel"，只接受GET请求
    @GetMapping
    // 旅游首页处理方法，返回travel.html模板
    public String travelIndex(Model model) {
        // 返回视图名称"travel"，Thymeleaf会自动查找templates/travel.html
        return "travel";
    }

    /**
     * 景点详情页面
     * 功能概述：处理景点详情页面的请求，接收景点名称或ID参数，传递给视图供前端处理
     */
    // 处理景点详情页面请求，映射路径"/attraction-detail"，只接受GET请求
    @GetMapping("/attraction-detail")
    // 景点详情页面处理方法，接收景点名称和ID参数（均为可选）
    public String attractionDetail(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "id", required = false) Integer id,
                                   Model model) {
        // 将参数传递给页面，以便JavaScript处理
        // 将景点名称添加到模型中，供前端模板使用，键名为"attractionName"
        model.addAttribute("attractionName", name);
        // 将景点ID添加到模型中，供前端模板使用，键名为"attractionId"
        model.addAttribute("attractionId", id);
        // 返回视图名称"attraction-detail"，Thymeleaf会自动查找templates/attraction-detail.html
        return "attraction-detail";
    }

    /**
     * 获取省份地图数据（用于ECharts）
     * 功能概述：获取所有省份的地图数据，用于ECharts地图可视化，返回JSON格式数据
     */
    // 处理获取省份地图数据请求，映射路径"/api/provinces"，只接受GET请求
    @GetMapping("/api/provinces")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取省份地图数据处理方法，返回省份地图数据
    public Map<String, Object> getProvinceMapData() {
        // 使用try-catch捕获异常
        try {
            // 调用旅游服务的getProvinceMapData方法获取省份地图数据
            return travelService.getProvinceMapData();
        // 捕获所有异常
        } catch (Exception e) {
            // 创建错误结果Map对象
            Map<String, Object> error = new HashMap<>();
            // 设置返回结果为失败
            error.put("success", false);
            // 设置错误消息，包含异常信息
            error.put("message", "获取省份数据失败：" + e.getMessage());
            // 返回错误结果
            return error;
        }
    }

    /**
     * 根据省份编码获取城市列表
     * 功能概述：根据省份编码查询该省份下的所有城市列表，同时返回省份信息，返回JSON格式数据
     */
    // 处理根据省份编码获取城市列表请求，映射路径"/api/cities/{provinceCode}"，使用路径变量
    @GetMapping("/api/cities/{provinceCode}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 根据省份编码获取城市列表处理方法，接收省份编码路径变量
    public Map<String, Object> getCitiesByProvinceCode(@PathVariable String provinceCode) {
        // 使用try-catch捕获异常
        try {
            // 调用旅游服务的getCitiesByProvinceCode方法，根据省份编码查询城市列表
            List<TravelDestination> cities = travelService.getCitiesByProvinceCode(provinceCode);
            // 调用旅游服务的getDestinationByCode方法，根据省份编码查询省份信息
            TravelDestination province = travelService.getDestinationByCode(provinceCode);
            
            // 创建结果Map对象，用于封装返回数据
            Map<String, Object> result = new HashMap<>();
            // 设置返回结果为成功
            result.put("success", true);
            // 将省份信息添加到返回结果中
            result.put("province", province);
            // 将城市列表添加到返回结果中
            result.put("cities", cities);
            // 设置城市总数
            result.put("total", cities.size());
            
            // 返回结果
            return result;
        // 捕获所有异常
        } catch (Exception e) {
            // 创建错误结果Map对象
            Map<String, Object> error = new HashMap<>();
            // 设置返回结果为失败
            error.put("success", false);
            // 设置错误消息，包含异常信息
            error.put("message", "获取城市数据失败：" + e.getMessage());
            // 返回错误结果
            return error;
        }
    }

    /**
     * 根据目的地编码获取完整的旅游信息
     * 功能概述：根据目的地编码查询该目的地的完整旅游信息，包括景点、酒店、美食等，返回JSON格式数据
     */
    // 处理根据目的地编码获取旅游信息请求，映射路径"/api/destination/{destinationCode}"，使用路径变量
    @GetMapping("/api/destination/{destinationCode}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 根据目的地编码获取旅游信息处理方法，接收目的地编码路径变量
    public Map<String, Object> getTravelInfo(@PathVariable String destinationCode) {
        // 使用try-catch捕获异常
        try {
            // 调用旅游服务的getTravelInfoByDestinationCode方法，根据目的地编码查询完整旅游信息
            Map<String, Object> travelInfo = travelService.getTravelInfoByDestinationCode(destinationCode);
            // 设置返回结果为成功
            travelInfo.put("success", true);
            // 返回旅游信息
            return travelInfo;
        // 捕获所有异常
        } catch (Exception e) {
            // 创建错误结果Map对象
            Map<String, Object> error = new HashMap<>();
            // 设置返回结果为失败
            error.put("success", false);
            // 设置错误消息，包含异常信息
            error.put("message", "获取旅游信息失败：" + e.getMessage());
            // 返回错误结果
            return error;
        }
    }

    /**
     * 根据目的地编码和分类获取推荐信息
     * 功能概述：根据目的地编码和分类（可选）查询推荐信息，支持按分类筛选，返回JSON格式数据
     */
    // 处理根据目的地编码和分类获取推荐信息请求，映射路径"/api/recommendations/{destinationCode}"，使用路径变量
    @GetMapping("/api/recommendations/{destinationCode}")
    // 返回JSON格式的响应体
    @ResponseBody
    // 根据目的地编码和分类获取推荐信息处理方法，接收目的地编码路径变量和分类参数（可选）
    public Map<String, Object> getRecommendations(
            @PathVariable String destinationCode,
            @RequestParam(required = false) String category) {
        // 使用try-catch捕获异常
        try {
            // 创建结果Map对象，用于封装返回数据
            Map<String, Object> result = new HashMap<>();
            
            // 判断分类参数是否不为空
            if (category != null && !category.isEmpty()) {
                // 如果分类不为空，调用旅游服务的getRecommendationsByDestinationAndCategory方法，根据目的地编码和分类查询推荐信息
                result.put("recommendations", 
                    travelService.getRecommendationsByDestinationAndCategory(destinationCode, category));
            // 如果分类为空
            } else {
                // 调用旅游服务的getRecommendationsByDestinationCode方法，根据目的地编码查询推荐信息
                result.put("recommendations", 
                    travelService.getRecommendationsByDestinationCode(destinationCode));
            }
            
            // 设置返回结果为成功
            result.put("success", true);
            // 返回结果
            return result;
        // 捕获所有异常
        } catch (Exception e) {
            // 创建错误结果Map对象
            Map<String, Object> error = new HashMap<>();
            // 设置返回结果为失败
            error.put("success", false);
            // 设置错误消息，包含异常信息
            error.put("message", "获取推荐信息失败：" + e.getMessage());
            // 返回错误结果
            return error;
        }
    }

    /**
     * 获取酒店列表
     * 功能概述：获取热门酒店列表，支持限制返回数量，返回JSON格式数据
     */
    // 处理获取酒店列表请求，映射路径"/api/hotels"，只接受GET请求
    @GetMapping("/api/hotels")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取酒店列表处理方法，接收限制数量参数（默认为10）
    public Map<String, Object> getHotels(@RequestParam(defaultValue = "10") int limit) {
        // 使用try-catch捕获异常
        try {
            // 创建结果Map对象，用于封装返回数据
            Map<String, Object> result = new HashMap<>();
            // 设置返回结果为成功
            result.put("success", true);
            // 调用旅游服务的getTopHotels方法获取热门酒店列表，并添加到返回结果中
            result.put("hotels", travelService.getTopHotels(limit));
            // 返回结果
            return result;
        // 捕获所有异常
        } catch (Exception e) {
            // 创建错误结果Map对象
            Map<String, Object> error = new HashMap<>();
            // 设置返回结果为失败
            error.put("success", false);
            // 设置错误消息，包含异常信息
            error.put("message", "获取酒店信息失败：" + e.getMessage());
            // 返回错误结果
            return error;
        }
    }

    /**
     * 修复数据库字符集乱码问题
     * 功能概述：修复数据库中可能存在的字符集乱码问题，返回JSON格式数据
     */
    // 处理修复数据库字符集乱码问题请求，映射路径"/api/fix-charset"，只接受GET请求
    @GetMapping("/api/fix-charset")
    // 返回JSON格式的响应体
    @ResponseBody
    // 修复数据库字符集乱码问题处理方法
    public Map<String, Object> fixCharsetIssues() {
        // 使用try-catch捕获异常
        try {
            // 调用字符集修复工具的fixCharsetIssues方法修复数据库字符集乱码问题
            charsetFixUtil.fixCharsetIssues();
            // 创建结果Map对象，用于封装返回数据
            Map<String, Object> result = new HashMap<>();
            // 设置返回结果为成功
            result.put("success", true);
            // 设置成功消息
            result.put("message", "数据库字符集修复完成！");
            // 返回结果
            return result;
        // 捕获所有异常
        } catch (Exception e) {
            // 创建错误结果Map对象
            Map<String, Object> error = new HashMap<>();
            // 设置返回结果为失败
            error.put("success", false);
            // 设置错误消息，包含异常信息
            error.put("message", "修复失败：" + e.getMessage());
            // 返回错误结果
            return error;
        }
    }

    // 基于attractions表的地区查询接口
    /**
     * 获取所有省份（从address表）
     * 功能概述：从address表中获取所有省份数据（deep=0），不限制是否有景点数据
     */
    @GetMapping("/api/addressProvinces")
    @ResponseBody
    public Map<String, Object> getProvincesFromAddress() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<String> provinces = travelService.getProvincesFromAddress();
            result.put("success", true);
            result.put("provinces", provinces);
            result.put("total", provinces.size());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取省份失败：" + e.getMessage());
            result.put("provinces", Collections.emptyList());
        }
        return result;
    }

    /**
     * 根据省份获取城市列表（从address表）
     * 功能概述：从address表中根据省份名称获取该省份下的所有城市数据（deep=1），不限制是否有景点数据
     */
    @GetMapping("/api/addressCities")
    @ResponseBody
    public Map<String, Object> getCitiesByProvince(@RequestParam("province") String province) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<String> cities = travelService.getCitiesFromAddressByProvince(province);
            result.put("success", true);
            result.put("cities", cities);
            result.put("total", cities.size());
            result.put("province", province);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取城市失败：" + e.getMessage());
            result.put("cities", Collections.emptyList());
        }
        return result;
    }

    /**
     * 根据省份和城市获取完整旅游信息
     */
    @GetMapping("/api/locationInfo")
    @ResponseBody
    public Map<String, Object> getTravelInfoByLocation(
            @RequestParam("province") String province,
            @RequestParam(value = "city", required = false) String city) {
        return travelService.getTravelInfoByLocation(province, city);
    }

    /**
     * 根据省份获取所有景点
     */
    @GetMapping("/api/provinceAttractions")
    @ResponseBody
    public Map<String, Object> getAttractionsByProvince(@RequestParam("province") String province) {
        return travelService.getAttractionsByProvince(province);
    }

    /**
     * 根据省份和城市获取景点
     */
    @GetMapping("/api/cityAttractions")
    @ResponseBody
    public Map<String, Object> getAttractionsByCity(
            @RequestParam("province") String province,
            @RequestParam("city") String city) {
        return travelService.getAttractionsByProvinceAndCity(province, city);
    }

    /**
     * 模糊搜索景点 - 根据关键词搜索
     */
    @GetMapping("/api/searchAttractions")
    @ResponseBody
    public Map<String, Object> searchAttractionsByKeyword(@RequestParam("keyword") String keyword) {
        return travelService.searchAttractionsByKeyword(keyword);
    }

    /**
     * 模糊搜索景点 - 根据关键词和地区搜索
     */
    @GetMapping("/api/searchAttractionsWithLocation")
    @ResponseBody
    public Map<String, Object> searchAttractionsByKeywordAndLocation(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city) {
        return travelService.searchAttractionsByKeywordAndLocation(keyword, province, city);
    }

    /**
     * 获取所有景点数据（默认显示）
     */
    @GetMapping("/api/allAttractions")
    @ResponseBody
    public Map<String, Object> getAllAttractions(
            @RequestParam(value = "limit", required = false) Integer limit) {
        return travelService.getAllAttractions(limit);
    }

    /**
     * 当选择"所有省份"时，获取所有景点数据
     */
    @GetMapping("/api/allProvincesAttractions")
    @ResponseBody
    public Map<String, Object> getAllProvincesAttractions(
            @RequestParam(value = "limit", defaultValue = "200") Integer limit) {
        return travelService.getAllProvincesAttractions(limit);
    }

    /**
     * 当选择某个省份但选择"所有城市"时，获取该省份下所有景点
     */
    @GetMapping("/api/provinceAllCitiesAttractions")
    @ResponseBody
    public Map<String, Object> getProvinceAllCitiesAttractions(
            @RequestParam("province") String province,
            @RequestParam(value = "limit", defaultValue = "100") Integer limit) {
        return travelService.getProvinceAllCitiesAttractions(province, limit);
    }

    /**
     * 获取景点详细信息
     */
    @GetMapping("/api/attractionDetail")
    @ResponseBody
    public Map<String, Object> getAttractionDetail(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (name == null && id == null) {
                result.put("success", false);
                result.put("message", "缺少景点名称或ID参数");
                return result;
            }
            
            Map<String, Object> attractionDetail;
            if (id != null) {
                attractionDetail = travelService.getAttractionById(id);
            } else {
                attractionDetail = travelService.getAttractionByName(name);
            }
            
            if (attractionDetail != null && !attractionDetail.isEmpty()) {
                result.put("success", true);
                result.put("attraction", attractionDetail);
                result.put("message", "获取景点详情成功");
            } else {
                result.put("success", false);
                result.put("message", "未找到对应的景点信息");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取景点详情失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 创建旅游订单
     * 功能概述：处理旅游订单创建请求，包括验证用户登录、创建旅游订单、创建统一订单并建立关联关系
     */
    // 处理创建旅游订单请求，映射路径"/order/book"，只接受POST请求
    @PostMapping("/order/book")
    // 返回JSON格式的响应体
    @ResponseBody
    // 创建旅游订单处理方法，接收请求体中的订单数据和会话对象
    public Map<String, Object> bookTravel(@RequestBody Map<String, Object> payload, HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch捕获异常
        try {
            // 检查用户登录状态
            // 从会话中获取当前登录的用户对象
            Object user = session.getAttribute("user");
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
            
            // 将用户对象转换为User类型，并获取用户ID
            Integer userId = ((com.icss.xihu.model.User) user).getId();
            
            // 获取订单参数
            // 从请求体中获取景点ID，并转换为Integer类型
            Integer attractionId = Integer.parseInt(payload.get("attractionId").toString());
            // 从请求体中获取景点名称，并转换为String类型
            String attractionName = payload.get("attractionName").toString();
            // 从请求体中获取参观日期，并转换为String类型
            String visitDate = payload.get("visitDate").toString();
            // 从请求体中获取门票数量，并转换为Integer类型
            Integer ticketCount = Integer.parseInt(payload.get("ticketCount").toString());
            // 从请求体中获取门票价格，并转换为Double类型
            Double ticketPrice = Double.parseDouble(payload.get("ticketPrice").toString());
            
            // 计算总金额
            // 将门票数量乘以门票价格得到总金额
            double totalAmount = ticketCount * ticketPrice;
            
            // 创建旅游订单
            // 创建旅游订单对象
            TravelOrder travelOrder = new TravelOrder();
            // 设置用户ID
            travelOrder.setUserId(userId);
            // 设置景点ID
            travelOrder.setAttractionId(attractionId);
            // 设置景点名称
            travelOrder.setAttractionName(attractionName);
            // 设置参观日期（将字符串解析为LocalDate对象）
            travelOrder.setVisitDate(java.time.LocalDate.parse(visitDate));
            // 设置门票数量
            travelOrder.setTicketCount(ticketCount);
            // 设置订单总金额（转换为BigDecimal类型）
            travelOrder.setTotalAmount(java.math.BigDecimal.valueOf(totalAmount));
            // 设置订单状态为0（待支付）
            travelOrder.setOrderStatus(0);
            
            // 调用旅游订单服务的createOrder方法创建旅游订单，返回订单ID
            int travelOrderId = travelOrderService.createOrder(travelOrder);
            
            // 每次预订都创建新的统一订单（不从详情页复用旧订单）
            // 构建订单标题，格式为"景点名称+门票预订"
            String orderTitle = attractionName + "门票预订";
            // 设置订单描述
            String orderDescription = "旅游景点门票订单";
            
            // 调用统一订单新服务的createOrder方法创建统一订单
            String unifiedOrderNo = unifiedOrderNewService.createOrder(
                userId,  // 用户ID
                "TRAVEL",  // 订单类型为旅游订单
                travelOrderId,  // 模块订单ID，即旅游订单的ID
                orderTitle,  // 订单标题
                orderDescription,  // 订单描述
                java.math.BigDecimal.valueOf(totalAmount)  // 订单金额
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
            
            // 设置返回结果为成功
            result.put("success", true);
            // 返回统一订单号
            result.put("orderNo", unifiedOrderNo);
            // 返回订单总金额
            result.put("amount", totalAmount);
            // 返回支付页面的重定向URL，包含统一订单号参数
            result.put("redirectUrl", "/unified-new/payment/checkout?orderNo=" + unifiedOrderNo);
            
        // 捕获所有异常
        } catch (Exception e) {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息，包含异常信息
            result.put("message", "订单创建失败：" + e.getMessage());
        }
        
        // 返回结果
        return result;
    }
} 