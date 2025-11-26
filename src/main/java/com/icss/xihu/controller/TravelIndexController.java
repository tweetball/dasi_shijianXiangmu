// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的请求映射注解
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 旅游首页控制器（简单版本）
 * 功能概述：处理旅游首页的请求，返回旅游首页视图
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 旅游首页控制器类，处理旅游首页相关的HTTP请求
public class TravelIndexController {
    
    /**
     * 旅游首页
     * 功能概述：处理旅游首页的请求，返回旅游首页视图
     */
    // 处理旅游首页请求，映射路径"/travel"
    @RequestMapping("/travel")
    // 旅游首页处理方法，返回travelIndex.html模板
    public String travelIndex() {
        // 返回视图名称"travelIndex"，Thymeleaf会自动查找templates/travelIndex.html
        return "travelIndex";
    }
}
