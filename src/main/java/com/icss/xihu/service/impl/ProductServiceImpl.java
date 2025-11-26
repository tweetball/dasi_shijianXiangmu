/**
 * 商品服务实现类
 * 功能概述：实现ProductService接口，提供商品相关的业务逻辑实现，包括分页查询、搜索、分类筛选等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入商品Mapper接口
import com.icss.xihu.mapper.ProductMapper;
// 导入商品实体类
import com.icss.xihu.model.Product;
// 导入商品服务接口
import com.icss.xihu.service.ProductService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

// 导入HashMap类，用于创建Map对象
import java.util.HashMap;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 商品服务实现类
 * 功能概述：实现ProductService接口，提供商品相关的业务逻辑实现，包括分页查询、搜索、分类筛选等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 商品服务实现类，实现ProductService接口
public class ProductServiceImpl implements ProductService {

    // 自动注入商品Mapper，Spring容器会自动查找并注入ProductMapper的实现类
    @Autowired
    // 商品Mapper对象，用于调用数据库操作方法
    private ProductMapper productMapper;

    /**
     * 分页查询商品
     * 功能概述：分页查询商品信息，支持按关键词搜索和按分类筛选，返回分页结果
     * @param {int} page - 页码（从1开始）
     * @param {int} pageSize - 每页记录数
     * @param {String} keyword - 搜索关键词（可选，用于搜索商品名称或标签）
     * @param {String} category - 商品分类（可选，用于筛选分类）
     * @return {Map<String, Object>} 返回分页结果（包含商品列表、总数、当前页码、每页记录数、总页数）
     */
    // 重写接口中的getProductsWithPagination方法
    @Override
    // 分页查询商品方法，接收页码、每页记录数、搜索关键词和分类参数，返回分页结果
    public Map<String, Object> getProductsWithPagination(int page, int pageSize, String keyword, String category) {
        // 计算偏移量（跳过的记录数）= (页码 - 1) × 每页记录数
        int offset = (page - 1) * pageSize;
        
        // 调用Mapper层的findAllProductsWithPagination方法，查询商品列表
        List<Product> products = productMapper.findAllProductsWithPagination(offset, pageSize, keyword, category);
        // 调用Mapper层的countAllProducts方法，统计商品总数
        int total = productMapper.countAllProducts(keyword, category);
        
        // 创建结果Map对象，用于存储分页结果
        Map<String, Object> result = new HashMap<>();
        // 将商品列表放入结果Map中，键为"list"
        result.put("list", products);
        // 将商品总数放入结果Map中，键为"total"
        result.put("total", total);
        // 将当前页码放入结果Map中，键为"page"
        result.put("page", page);
        // 将每页记录数放入结果Map中，键为"pageSize"
        result.put("pageSize", pageSize);
        // 计算总页数 = 向上取整(总数 ÷ 每页记录数)，并将总页数放入结果Map中，键为"totalPages"
        result.put("totalPages", (int) Math.ceil((double) total / pageSize));
        
        // 返回分页结果Map
        return result;
    }

    /**
     * 根据ID查询商品
     * 功能概述：根据商品编号查询指定商品的详细信息
     * @param {Long} id - 商品编号
     * @return {Product} 返回商品对象，如果不存在则返回null
     */
    // 重写接口中的getProductById方法
    @Override
    // 根据ID查询商品方法，接收商品编号参数，返回商品对象
    public Product getProductById(Long id) {
        // 调用Mapper层的findProductById方法，根据商品编号查询商品信息并返回
        return productMapper.findProductById(id);
    }
}
