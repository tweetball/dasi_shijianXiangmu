package com.icss.xihu.service;

import com.icss.xihu.model.Hotel;
import com.icss.xihu.model.HotelMany;

import java.util.List;

public interface HotelService {
    //抽象关键字：abstract
    //接口下不能创建普通方法
    //接口中创建抽象方法可以省略抽象关键字
    //查询酒店所有信息

    public List<Hotel> findAll();
    public Hotel findById(int id);
    //多表查询---一个酒店包含多个房间
    public List<HotelMany> findByMany(int id);
    //在酒店首页显示前两条数据
    public List<Hotel> findIndex();
    public List<Hotel> searchByKeyword(String keyword);
    // 新增方法
    List<Hotel> getHotHotels();
    // 新增方法
    List<Hotel> findAllActive();
}
