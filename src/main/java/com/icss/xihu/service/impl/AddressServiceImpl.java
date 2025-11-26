/**
 * 地址服务实现类
 * 功能概述：实现AddressService接口，提供地址相关的业务逻辑实现，包括查询所有省份、根据省ID查询城市等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入地址Mapper接口
import com.icss.xihu.mapper.AddressMapper;
// 导入地址实体类
import com.icss.xihu.model.Address;
// 导入地址服务接口
import com.icss.xihu.service.AddressService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

// 导入List集合接口
import java.util.List;

/**
 * 地址服务实现类
 * 功能概述：实现AddressService接口，提供地址相关的业务逻辑实现，包括查询所有省份、根据省ID查询城市等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 地址服务实现类，实现AddressService接口
public class AddressServiceImpl implements AddressService {

    // 自动注入地址Mapper，Spring容器会自动查找并注入AddressMapper的实现类
    @Autowired
    // 地址Mapper对象，用于调用数据库操作方法
    private AddressMapper addressMapper;

    /**
     * 查询所有省份
     * 功能概述：调用Mapper层方法，查询所有省份信息
     * @return {List<Address>} 返回所有省份的列表
     */
    // 重写接口中的getProvinces方法
    @Override
    // 查询所有省份方法，返回所有省份的列表
    public List<Address> getProvinces() {
        // 调用Mapper层的selectProvinces方法，查询所有省份信息并返回
        return addressMapper.selectProvinces();
    }

    /**
     * 根据省ID查询城市
     * 功能概述：调用Mapper层方法，根据省份编号查询该省份下的所有城市信息
     * @param {Integer} provinceId - 省份编号
     * @return {List<Address>} 返回该省份下的所有城市列表
     */
    // 重写接口中的getCitiesByProvinceId方法
    @Override
    // 根据省ID查询城市方法，接收省份编号参数，返回该省份下的所有城市列表
    public List<Address> getCitiesByProvinceId(Integer provinceId) {
        // 调用Mapper层的selectCitiesByProvinceId方法，根据省份编号查询该省份下的所有城市信息并返回
        return addressMapper.selectCitiesByProvinceId(provinceId);
    }
}
