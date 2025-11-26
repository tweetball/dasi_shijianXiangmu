/**
 * 酒店服务实现类
 * 功能概述：实现HotelService接口，提供酒店相关的业务逻辑实现，包括查询所有酒店、根据ID查询酒店、多表查询等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入酒店Mapper接口
import com.icss.xihu.mapper.HotelMapper;
// 导入酒店实体类
import com.icss.xihu.model.Hotel;
// 导入酒店多房间实体类
import com.icss.xihu.model.HotelMany;
// 导入酒店服务接口
import com.icss.xihu.service.HotelService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

// 导入List集合接口
import java.util.List;

/**
 * 酒店服务实现类
 * 功能概述：实现HotelService接口，提供酒店相关的业务逻辑实现，包括查询所有酒店、根据ID查询酒店、多表查询等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 酒店服务实现类，实现HotelService接口
public class HotelServiceImpl implements HotelService {
    // 实现类实现接口时必须重写接口中的抽象方法
    
    // 自动注入酒店Mapper，Spring容器会自动查找并注入HotelMapper的实现类
    @Autowired
    // 酒店Mapper对象，用于调用数据库操作方法
    HotelMapper hm;
    
    /**
     * 查询所有酒店
     * 功能概述：调用Mapper层方法，查询所有酒店信息
     * @return {List<Hotel>} 返回所有酒店的列表
     */
    // 重写接口中的findAll方法
    @Override
    // 查询所有酒店方法，返回所有酒店的列表
    public List<Hotel> findAll() {
        // 调用Mapper层的findAll方法，查询所有酒店信息并返回
        return hm.findAll();
    }

    /**
     * 根据ID查询酒店
     * 功能概述：调用Mapper层方法，根据酒店ID查询指定酒店的信息
     * @param {int} id - 酒店ID
     * @return {Hotel} 返回酒店对象，如果不存在则返回null
     */
    // 重写接口中的findById方法
    @Override
    // 根据ID查询酒店方法，接收酒店ID参数，返回酒店对象
    public Hotel findById(int id) {
        // 调用Mapper层的findById方法，根据酒店ID查询酒店信息并返回
        return hm.findById(id);
    }

    /**
     * 多表查询酒店及其房间信息
     * 功能概述：调用Mapper层方法，根据酒店ID查询酒店信息及其所有房间信息（一对多关系）
     * @param {int} id - 酒店ID
     * @return {List<HotelMany>} 返回酒店及其房间信息的列表
     */
    // 重写接口中的findByMany方法
    @Override
    // 多表查询方法，接收酒店ID参数，返回酒店及其房间信息的列表
    public List<HotelMany> findByMany(int id) {
        // 调用Mapper层的findByMany方法，根据酒店ID查询酒店及其房间信息并返回
        return hm.findByMany(id);
    }

    /**
     * 查询首页显示的酒店（前3条）
     * 功能概述：调用Mapper层方法，查询前3条酒店信息，用于首页展示
     * @return {List<Hotel>} 返回前3条酒店的列表
     */
    // 重写接口中的findIndex方法
    @Override
    // 查询首页显示的酒店方法，返回前3条酒店的列表
    public List<Hotel> findIndex() {
        // 调用Mapper层的findIndex方法，查询前3条酒店信息并返回
        return hm.findIndex();
    }

    /**
     * 根据关键词搜索酒店
     * 功能概述：调用Mapper层方法，根据关键词搜索匹配的酒店信息，自动添加通配符
     * @param {String} keyword - 搜索关键词
     * @return {List<Hotel>} 返回匹配的酒店列表
     */
    // 重写接口中的searchByKeyword方法
    @Override
    // 根据关键词搜索酒店方法，接收关键词参数，返回匹配的酒店列表
    public List<Hotel> searchByKeyword(String keyword) {
        // 调用Mapper层的searchByKeyword方法，在关键词前后添加通配符%，实现模糊搜索
        return hm.searchByKeyword("%" + keyword + "%");
    }

    /**
     * 获取热门酒店
     * 功能概述：调用Mapper层方法，查询热门酒店信息
     * @return {List<Hotel>} 返回热门酒店的列表
     */
    // 重写接口中的getHotHotels方法
    @Override
    // 获取热门酒店方法，返回热门酒店的列表
    public List<Hotel> getHotHotels() {
        // 调用Mapper层的getHotHotels方法，查询热门酒店信息并返回
        return hm.getHotHotels();
    }

    /**
     * 查询所有活跃的酒店
     * 功能概述：调用Mapper层方法，查询所有状态为活跃的酒店信息
     * @return {List<Hotel>} 返回所有活跃酒店的列表
     */
    // 重写接口中的findAllActive方法
    @Override
    // 查询所有活跃的酒店方法，返回所有活跃酒店的列表
    public List<Hotel> findAllActive() {
        // 调用Mapper层的findAllActive方法，查询所有活跃酒店信息并返回
        return hm.findAllActive();
    }

}
