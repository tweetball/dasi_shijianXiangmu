/**
 * 商品服务接口
 * 功能概述：定义商品相关的业务逻辑方法，包括分页查询、搜索、分类筛选等
 */
// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入商品实体类
import com.icss.xihu.model.Product;
// 导入Map接口
import java.util.Map;

/**
 * 商品服务接口
 * 功能概述：定义商品相关的业务逻辑方法，包括分页查询、搜索、分类筛选等
 */
// 商品服务接口，定义商品相关的业务逻辑方法
public interface ProductService {
    
    /**
     * 分页查询商品
     * 功能概述：分页查询商品信息，支持按关键词搜索和按分类筛选
     * @param {int} page - 页码（从1开始）
     * @param {int} pageSize - 每页记录数
     * @param {String} keyword - 搜索关键词（可选，用于搜索商品名称或标签）
     * @param {String} category - 商品分类（可选，用于筛选分类）
     * @return {Map<String, Object>} 返回分页结果（包含商品列表和总数）
     */
    // 分页查询商品方法，接收页码、每页记录数、搜索关键词和分类参数，返回分页结果
    Map<String, Object> getProductsWithPagination(int page, int pageSize, String keyword, String category);
    
    /**
     * 根据ID查询商品
     * 功能概述：根据商品编号查询指定商品的详细信息
     * @param {Long} id - 商品编号
     * @return {Product} 返回商品对象，如果不存在则返回null
     */
    // 根据ID查询商品方法，接收商品编号参数，返回商品对象
    Product getProductById(Long id);
}
