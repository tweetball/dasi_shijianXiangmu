package com.icss.xihu.mapper;

import com.icss.xihu.model.Hotel;
import com.icss.xihu.model.HotelMany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HotelMapper {
    //通过注解的方式写sql
    //增insert 删delete  改update 查select
    //实体类字段名字和数据库字段名字不一致时，通过as或者空格给字段取别名
    //别名需要和实体类的字段名一致
    @Select("select id,name,tel,img,content,address,level,score,province,city from hotel")
    public List<Hotel> findAll();
    @Select("select id as id,name,tel,content,img,address,level,score,province,city from hotel where id = #{value}")
    public Hotel findById(int id);
    //因为会用到多表进行查询，所以使用xml解析文件，写sql
    //多表查询---一个酒店包含多个房间
    public List<HotelMany> findByMany(int id);
    //在酒店首页显示前两条数据
    //limit 5//查询前5条
    //limit n，m//从n+1开始查m条
    @Select("select id as id,name,tel,content,img,address,level,score,province,city from hotel limit 3")
    public List<Hotel> findIndex();

    public List<Hotel> searchByKeyword(String keyword);
    // 新增方法
    List<Hotel> getHotHotels();
    // 新增方法
    List<Hotel> findAllActive();

}
