/**
 * 商品Mapper接口
 * 功能概述：定义商品相关的数据库操作方法，包括分页查询、搜索、分类筛选等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入商品实体类
import com.icss.xihu.model.Product;
// 导入MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Param注解，用于方法参数映射
import org.apache.ibatis.annotations.Param;
// 导入MyBatis的Select注解，用于在方法上直接编写SQL查询语句
import org.apache.ibatis.annotations.Select;

// 导入List集合接口
import java.util.List;
// 导入Map集合接口
import java.util.Map;

/**
 * 商品Mapper接口
 * 功能概述：定义商品相关的数据库操作方法，包括分页查询、搜索、分类筛选等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 商品Mapper接口，定义商品相关的数据库操作方法
public interface ProductMapper {

    /**
     * 分页查询商品
     * 功能概述：从数据库中分页查询商品信息，按ID倒序排列
     * @param {int} offset - 偏移量（跳过的记录数）
     * @param {int} limit - 每页记录数
     * @return {List<Map<String, Object>>} 返回商品信息列表（Map格式）
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用LIMIT和OFFSET实现分页
    @Select("SELECT * FROM product ORDER BY id DESC LIMIT #{limit} OFFSET #{offset}")
    // 分页查询商品方法，接收偏移量和每页记录数参数，返回商品信息列表
    List<Map<String, Object>> findPage(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计商品总数
     * 功能概述：从数据库中统计商品的总数量
     * @return {int} 返回商品总数
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用COUNT(*)统计总数
    @Select("SELECT COUNT(*) FROM product")
    // 统计商品总数方法，返回商品总数
    int countAll();

    /**
     * 根据ID查询商品
     * 功能概述：根据商品编号从数据库中查询指定商品的信息
     * @param {long} id - 商品编号
     * @return {Map<String, Object>} 返回商品信息（Map格式），如果不存在则返回null
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM product WHERE id = #{id}")
    // 根据ID查询商品方法，接收商品编号参数，返回商品信息
    Map<String, Object> findById(@Param("id") long id);

    /**
     * 分页查询商品（支持搜索和分类筛选）
     * 功能概述：从数据库中分页查询商品信息，支持按关键词搜索和按分类筛选，按ID倒序排列
     * @param {int} offset - 偏移量（跳过的记录数）
     * @param {int} limit - 每页记录数
     * @param {String} keyword - 搜索关键词（可选，用于搜索商品名称或标签）
     * @param {String} category - 商品分类（可选，用于筛选分类）
     * @return {List<Product>} 返回商品信息列表
     */
    // 分页查询商品方法（支持搜索和分类筛选），接收偏移量、每页记录数、搜索关键词和分类参数，返回商品信息列表
    // 使用MyBatis的Select注解和动态SQL（<script>标签），根据条件动态生成SQL语句
    @Select("<script>" +
            "SELECT * FROM product " +                    // 查询商品表的所有字段
            "<where>" +                                   // 动态WHERE子句开始
            "<if test='keyword != null and keyword != \"\"'>" +  // 如果关键词不为空
            "AND (name LIKE CONCAT('%', #{keyword}, '%') OR tags LIKE CONCAT('%', #{keyword}, '%'))" +  // 在商品名称或标签中模糊搜索
            "</if>" +                                     // 条件判断结束
            "<if test='category != null and category != \"\"'>" +  // 如果分类不为空
            "AND category = #{category}" +                // 按分类精确匹配
            "</if>" +                                     // 条件判断结束
            "</where>" +                                  // 动态WHERE子句结束
            "ORDER BY id DESC LIMIT #{limit} OFFSET #{offset}" +  // 按ID倒序排列，使用LIMIT和OFFSET实现分页
            "</script>")                                  // 动态SQL结束
    // 分页查询商品方法（支持搜索和分类筛选），接收偏移量、每页记录数、搜索关键词和分类参数，返回商品信息列表
    List<Product> findAllProductsWithPagination(@Param("offset") int offset, 
                                                @Param("limit") int limit,
                                                @Param("keyword") String keyword,
                                                @Param("category") String category);

    /**
     * 统计商品总数（支持搜索和分类筛选）
     * 功能概述：从数据库中统计商品的总数量，支持按关键词搜索和按分类筛选
     * @param {String} keyword - 搜索关键词（可选，用于搜索商品名称或标签）
     * @param {String} category - 商品分类（可选，用于筛选分类）
     * @return {int} 返回商品总数
     */
    // 统计商品总数方法（支持搜索和分类筛选），接收搜索关键词和分类参数，返回商品总数
    // 使用MyBatis的Select注解和动态SQL（<script>标签），根据条件动态生成SQL语句
    @Select("<script>" +
            "SELECT COUNT(*) FROM product " +            // 统计商品表的总数
            "<where>" +                                   // 动态WHERE子句开始
            "<if test='keyword != null and keyword != \"\"'>" +  // 如果关键词不为空
            "AND (name LIKE CONCAT('%', #{keyword}, '%') OR tags LIKE CONCAT('%', #{keyword}, '%'))" +  // 在商品名称或标签中模糊搜索
            "</if>" +                                     // 条件判断结束
            "<if test='category != null and category != \"\"'>" +  // 如果分类不为空
            "AND category = #{category}" +                // 按分类精确匹配
            "</if>" +                                     // 条件判断结束
            "</where>" +                                  // 动态WHERE子句结束
            "</script>")                                  // 动态SQL结束
    // 统计商品总数方法（支持搜索和分类筛选），接收搜索关键词和分类参数，返回商品总数
    int countAllProducts(@Param("keyword") String keyword, @Param("category") String category);

    /**
     * 根据ID查询单个商品
     * 功能概述：根据商品编号从数据库中查询指定商品的信息
     * @param {Long} id - 商品编号
     * @return {Product} 返回商品对象，如果不存在则返回null
     */
    // 根据ID查询单个商品方法，接收商品编号参数，返回商品对象
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM product WHERE id = #{id}")
    // 根据ID查询单个商品方法，接收商品编号参数，返回商品对象
    Product findProductById(@Param("id") Long id);
}
