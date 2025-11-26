package com.icss.xihu.controller;

import com.icss.xihu.model.Address;
import com.icss.xihu.model.Hotel;
import com.icss.xihu.model.HotelMany;
import com.icss.xihu.model.HotelOrder;
import com.icss.xihu.service.AddressService;
import com.icss.xihu.service.HotelOrderService;
import com.icss.xihu.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/hc")//自定义映射路径
public class HotelController {

    @RequestMapping("/myHotelOrder")
    public String showMyOrders() {
        return "myHotelOrder"; // 返回视图名称
    }

    @Autowired
    private HotelOrderService hotelOrderService;

    // 新增：处理订单提交
    @RequestMapping("/bookings")
    @ResponseBody
    public Map<String, Object> createBooking(@RequestBody HotelOrder order) {
        Map<String, Object> result = new HashMap<>();
        try {
            int resultCode = hotelOrderService.createOrder(order);
            if (resultCode > 0) {
                result.put("code", 200);
                result.put("message", "预订成功");
                result.put("data", order.getId());
            } else {
                result.put("code", 500);
                result.put("message", "预订失败，请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "系统错误，预订失败");
        }
        return result;
    }

    // 获取用户订单列表
    @RequestMapping("/orders")
    @ResponseBody
    public Map<String, Object> getOrdersByUserId(@RequestParam("userId") String userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<HotelOrder> orders = hotelOrderService.getOrdersByUserId(userId);
            result.put("code", 200);
            result.put("message", "获取订单成功");
            result.put("data", orders);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "获取订单失败");
        }
        return result;
    }

    // 取消订单
    @RequestMapping("/orders/{orderId}/cancel")
    @ResponseBody
    public Map<String, Object> cancelOrder(@PathVariable("orderId") Long orderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = hotelOrderService.cancelOrder(orderId);
            if (rows > 0) {
                result.put("code", 200);
                result.put("message", "取消订单成功");
            } else {
                result.put("code", 400);
                result.put("message", "取消订单失败，订单不存在或状态不可取消");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "系统错误，取消订单失败");
        }
        return result;
    }

    // 取消订单
    @RequestMapping("/orders/{orderId}/makeSure")
    @ResponseBody
    public Map<String, Object> makeSureOrder(@PathVariable("orderId") Long orderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = hotelOrderService.makeSureOrder(orderId);
            if (rows > 0) {
                result.put("code", 200);
                result.put("message", "确认订单成功");
            } else {
                result.put("code", 400);
                result.put("message", "确认订单失败，订单不存在或状态不可取消");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "系统错误，确认订单失败");
        }
        return result;
    }

    @Autowired
    private AddressService addressService;

    // 查询所有省份
    @RequestMapping("/provinces")
    @ResponseBody
    public List<Address> getProvinces() {
        return addressService.getProvinces();
    }

    // 根据省 ID 查询城市
    @RequestMapping("/cities")
    @ResponseBody
    public List<Address> getCitiesByProvinceId(Integer provinceId) {
        return addressService.getCitiesByProvinceId(provinceId);
    }

    //调用业务层获取操作结果
    @Autowired//自动注入
    HotelService hs;
    @RequestMapping("/fAll")//方法路径
    public String findAll(Map<Object,Object> map){
        List<Hotel> list=hs.findAll();//接取返回值的快捷键ctrl+alt+v
//        System.out.println(list);
        map.put("hotelList",list);
        return "hotel";
    }



    @RequestMapping("findById")
    public String findById(@RequestParam("id") int id,Map<Object,Object> map){//@RequestParam("页面传递参数的名字")
        System.out.println(id);
        //1、接取前台传递的数据--完成
        //2、利用自动注入方式(创建的接口/实现类的对象)调用对应的功能，并接取返回值
        Hotel h = hs.findById(id);
        //3、处理返回值(省略)
        //4、创建Map/Model/ModelAndView 对象，通过对象调用对应的方法，向前台传递数据
        map.put("hotel",h);
        //5、跳转页面(只需要写页面的名字即可，前缀和后缀已经在配置文件中配置完成)
        return "hotelInfo";
    }


    @RequestMapping("findByMany")
    public String findByMany(@RequestParam("id")int id,Map<Object,Object> map){
        //1、接取前台传递的数据
        //2、利用自动注入方式创建的接口/实现类的对象调用对应的功能，并接取返回值
        List<HotelMany> list = hs.findByMany(id);
        //3、处理返回值(省略)
        //4、创建Map/Model/ModelAndView 对象，通过对象调用对应的方法，向前台传递数据
        System.out.println(list);
        map.put("m",list.get(0));
        map.put("mlist",list);
        //map.put("a","asd");
        //5、跳转页面(只需要写页面的名字即可，前缀和后缀已经在配置文件中配置完成)
        return "hotelManyInfo";
    }
    @RequestMapping("f2")
    public String findBy2(Map<Object,Object> mm){
        //通过接口对象，调用指定的功能，并接取返回值
        List<Hotel> hList = hs.findIndex();
        //创建map集合对象，通过put方法将集合传递回页面
        mm.put("hotelList",hList);
        return "hotelIndex";
    }

    // 新增搜索接口（返回JSON数据）
    @RequestMapping("/search")
    @ResponseBody // 标记为返回JSON数据
    public List<Hotel> search(@RequestParam("keyword") String keyword) {
        return hs.searchByKeyword(keyword);
    }

    // 新增：获取热门酒店
    @RequestMapping("/hotHotels")
    @ResponseBody
    public List<Hotel> getHotHotels() {
        return hs.getHotHotels();
    }

    @RequestMapping("/allHotels")
    @ResponseBody
    public List<Hotel> findAllActive(){
        return hs.findAllActive();
    }

}

