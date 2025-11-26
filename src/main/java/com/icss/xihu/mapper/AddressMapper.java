/**
 * 地址Mapper接口
 * 功能概述：定义地址相关的数据库操作方法，包括查询所有省份、根据省ID查询城市等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入地址实体类
import com.icss.xihu.model.Address;
// 导入MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Param注解，用于方法参数映射
import org.apache.ibatis.annotations.Param;

// 导入List集合接口
import java.util.List;

/**
 * 地址Mapper接口
 * 功能概述：定义地址相关的数据库操作方法，包括查询所有省份、根据省ID查询城市等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 地址Mapper接口，定义地址相关的数据库操作方法
public interface AddressMapper {
    /**
     * 查询所有省份
     * 功能概述：从数据库中查询所有省份信息
     * @return {List<Address>} 返回所有省份的列表
     */
    // 查询所有省份方法，返回所有省份的列表（SQL在XML映射文件中定义）
    List<Address> selectProvinces();

    /**
     * 根据省ID查询城市
     * 功能概述：根据省份编号从数据库中查询该省份下的所有城市信息
     * @param {Integer} provinceId - 省份编号
     * @return {List<Address>} 返回该省份下的所有城市列表
     */
    // 根据省ID查询城市方法，接收省份编号参数，返回该省份下的所有城市列表（SQL在XML映射文件中定义）
    List<Address> selectCitiesByProvinceId(@Param("provinceId") Integer provinceId);

    /**
     * 查询所有地址信息
     * 功能概述：从数据库中查询所有地址信息
     * @return {List<Address>} 返回所有地址的列表
     */
    // 查询所有地址信息方法，返回所有地址的列表（SQL在XML映射文件中定义）
    List<Address> selectAll();
}
