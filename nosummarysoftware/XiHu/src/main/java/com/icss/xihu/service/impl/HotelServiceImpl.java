package com.icss.xihu.service.impl;

import com.icss.xihu.mapper.HotelMapper;
import com.icss.xihu.model.Hotel;
import com.icss.xihu.model.HotelMany;
import com.icss.xihu.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HotelServiceImpl implements HotelService {
//实现类实现接口时必须重写接口中的抽象方法
    @Autowired//自动注入
    HotelMapper hm;
    @Override
    public List<Hotel> findAll() {
        return hm.findAll();
    }

    @Override
    public Hotel findById(int id) {
        return hm.findById(id);
    }

    @Override
    public List<HotelMany> findByMany(int id) {
        return hm.findByMany(id);
    }

    @Override
    public List<Hotel> findIndex() {
        return hm.findIndex();
    }

    @Override
    public List<Hotel> searchByKeyword(String keyword) {
        return hm.searchByKeyword("%" + keyword + "%");
    }

    @Override
    public List<Hotel> getHotHotels() {
        return hm.getHotHotels();
    }

    @Override
    public List<Hotel> findAllActive() {
        return hm.findAllActive();
    }


}
