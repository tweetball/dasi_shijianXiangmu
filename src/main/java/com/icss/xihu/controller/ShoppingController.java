// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的请求映射注解
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 购物控制器（简单版本）
 * 功能概述：处理购物首页的请求，返回购物首页视图
 */
// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 购物控制器类，处理购物相关的HTTP请求
public class ShoppingController {
    
    /**
     * 购物首页
     * 功能概述：处理购物首页的请求，返回购物首页视图
     */
    // 处理购物首页请求，映射路径"/shopping"
    @RequestMapping("/shopping")
    // 购物首页处理方法，返回shoppingIndex.html模板
    public String shoppingIndex() {
        // 返回视图名称"shoppingIndex"，Thymeleaf会自动查找templates/shoppingIndex.html
        return "shoppingIndex";
    }
}
