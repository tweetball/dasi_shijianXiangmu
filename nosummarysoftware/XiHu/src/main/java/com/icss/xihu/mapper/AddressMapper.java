package com.icss.xihu.mapper;

import com.icss.xihu.model.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {
    // 查询所有省份（deep=0）
    @Select("SELECT * FROM address WHERE deep = 0")
    List<Address> selectProvinces();

    // 根据省 ID 查询下属市（deep=1 且 pid=省ID）
    @Select("SELECT * FROM address WHERE deep = 1 AND pid = #{provinceId}")
    List<Address> selectCitiesByProvinceId(Integer provinceId);
}