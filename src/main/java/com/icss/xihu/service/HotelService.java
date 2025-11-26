/**
 * 酒店服务接口
 * 功能概述：定义酒店相关的业务逻辑方法，包括查询所有酒店、根据ID查询酒店、多表查询等
 */
// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入酒店实体类
import com.icss.xihu.model.Hotel;
// 导入酒店多房间实体类
import com.icss.xihu.model.HotelMany;

// 导入List集合接口
import java.util.List;

/**
 * 酒店服务接口
 * 功能概述：定义酒店相关的业务逻辑方法，包括查询所有酒店、根据ID查询酒店、多表查询等
 */
// 酒店服务接口，定义酒店相关的业务逻辑方法
public interface HotelService {
    // 抽象关键字：abstract
    // 接口下不能创建普通方法
    // 接口中创建抽象方法可以省略抽象关键字
    // 查询酒店所有信息

    /**
     * 查询所有酒店
     * 功能概述：查询所有酒店信息，返回酒店列表
     * @return {List<Hotel>} 返回所有酒店的列表
     */
    // 查询所有酒店方法，返回所有酒店的列表
    public List<Hotel> findAll();
    
    /**
     * 根据ID查询酒店
     * 功能概述：根据酒店ID查询指定酒店的信息
     * @param {int} id - 酒店ID
     * @return {Hotel} 返回酒店对象，如果不存在则返回null
     */
    // 根据ID查询酒店方法，接收酒店ID参数，返回酒店对象
    public Hotel findById(int id);
    
    /**
     * 多表查询酒店及其房间信息
     * 功能概述：根据酒店ID查询酒店信息及其所有房间信息（一对多关系）
     * @param {int} id - 酒店ID
     * @return {List<HotelMany>} 返回酒店及其房间信息的列表
     */
    // 多表查询---一个酒店包含多个房间
    // 多表查询方法，接收酒店ID参数，返回酒店及其房间信息的列表
    public List<HotelMany> findByMany(int id);
    
    /**
     * 查询首页显示的酒店（前3条）
     * 功能概述：查询前3条酒店信息，用于首页展示
     * @return {List<Hotel>} 返回前3条酒店的列表
     */
    // 在酒店首页显示前两条数据
    // 查询首页显示的酒店方法，返回前3条酒店的列表
    public List<Hotel> findIndex();
    
    /**
     * 根据关键词搜索酒店
     * 功能概述：根据关键词搜索匹配的酒店信息
     * @param {String} keyword - 搜索关键词
     * @return {List<Hotel>} 返回匹配的酒店列表
     */
    // 根据关键词搜索酒店方法，接收关键词参数，返回匹配的酒店列表
    public List<Hotel> searchByKeyword(String keyword);
    
    /**
     * 获取热门酒店
     * 功能概述：查询热门酒店信息
     * @return {List<Hotel>} 返回热门酒店的列表
     */
    // 新增方法
    // 获取热门酒店方法，返回热门酒店的列表
    List<Hotel> getHotHotels();
    
    /**
     * 查询所有活跃的酒店
     * 功能概述：查询所有状态为活跃的酒店信息
     * @return {List<Hotel>} 返回所有活跃酒店的列表
     */
    // 新增方法
    // 查询所有活跃的酒店方法，返回所有活跃酒店的列表
    List<Hotel> findAllActive();
}
