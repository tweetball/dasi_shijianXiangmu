// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的请求映射注解
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 美食控制器（简单版本）
 * 功能概述：处理美食首页的请求，返回美食首页视图
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 美食控制器类，处理美食相关的HTTP请求
public class FoodController {
    
    /**
     * 美食首页
     * 功能概述：处理美食首页的请求，返回美食首页视图
     */
    // 处理美食首页请求，映射路径"/food"
    @RequestMapping("/food")
    // 美食首页处理方法，返回foodIndex.html模板
    public String foodIndex() {
        // 返回视图名称"foodIndex"，Thymeleaf会自动查找templates/foodIndex.html
        return "foodIndex";
    }
}
