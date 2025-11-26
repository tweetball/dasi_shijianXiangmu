/**
 * 地址服务接口
 * 功能概述：定义地址相关的业务逻辑方法，包括查询所有省份、根据省ID查询城市等
 */
// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入地址实体类
import com.icss.xihu.model.Address;

// 导入List集合接口
import java.util.List;

/**
 * 地址服务接口
 * 功能概述：定义地址相关的业务逻辑方法，包括查询所有省份、根据省ID查询城市等
 */
// 地址服务接口，定义地址相关的业务逻辑方法
public interface AddressService {
    /**
     * 查询所有省份
     * 功能概述：查询所有省份信息
     * @return {List<Address>} 返回所有省份的列表
     */
    // 查询所有省份方法，返回所有省份的列表
    List<Address> getProvinces();

    /**
     * 根据省ID查询城市
     * 功能概述：根据省份编号查询该省份下的所有城市信息
     * @param {Integer} provinceId - 省份编号
     * @return {List<Address>} 返回该省份下的所有城市列表
     */
    // 根据省ID查询城市方法，接收省份编号参数，返回该省份下的所有城市列表
    List<Address> getCitiesByProvinceId(Integer provinceId);
}
