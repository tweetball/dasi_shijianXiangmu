// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入酒店实体类
import com.icss.xihu.model.Hotel;
// 导入酒店服务接口
import com.icss.xihu.service.HotelService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的模型对象，用于向视图传递数据
import org.springframework.ui.Model;
// 导入Spring MVC的请求映射注解
import org.springframework.web.bind.annotation.RequestMapping;

// 导入List集合类
import java.util.List;

// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 主控制器类，处理网站首页相关请求
public class MainController {
    
    // 自动注入酒店服务，Spring容器会自动查找并注入HotelService的实现类
    @Autowired
    // 酒店服务对象，用于调用酒店相关的业务逻辑
    private HotelService hotelService;
    
    // 处理网站首页请求，映射根路径"/"
    @RequestMapping("/")
    // 首页处理方法，返回index.html模板
    public String index(Model model){
        // 调用酒店服务的findAll方法，获取所有酒店数据
        List<Hotel> recommendedHotels = hotelService.findAll();
        // 将酒店列表添加到模型中，供前端模板使用，键名为"recommendedHotels"
        model.addAttribute("recommendedHotels", recommendedHotels);
        // 返回视图名称"index"，Thymeleaf会自动查找templates/index.html
        return "index";
    }
    
    // 处理平台首页请求，映射路径"/home"
    @RequestMapping("/home")
    // 平台首页处理方法，返回index.html模板
    public String home(Model model){
        // 调用酒店服务的findAll方法，获取所有酒店数据
        List<Hotel> recommendedHotels = hotelService.findAll();
        // 将酒店列表添加到模型中，供前端模板使用，键名为"recommendedHotels"
        model.addAttribute("recommendedHotels", recommendedHotels);
        // 返回视图名称"index"，Thymeleaf会自动查找templates/index.html
        return "index";
    }
    
    // 处理酒店模块首页请求，映射路径"/hotel"
    @RequestMapping("/hotel")
    // 酒店模块首页处理方法，重定向到酒店控制器的f2方法
    public String hotel(){
        // 返回重定向指令，跳转到酒店控制器的f2方法路径
        return "redirect:/hc/f2";
    }
}