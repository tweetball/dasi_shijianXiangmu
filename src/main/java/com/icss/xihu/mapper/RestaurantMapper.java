/**
 * 餐厅Mapper接口
 * 功能概述：定义餐厅相关的数据库操作方法，包括餐厅查询、订单操作、评价操作、菜单操作等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入餐厅实体类
import com.icss.xihu.model.Restaurant;
// 导入餐厅订单实体类
import com.icss.xihu.model.RestaurantOrder;
// 导入餐厅评价实体类
import com.icss.xihu.model.RestaurantReview;
// 导入餐厅菜单实体类
import com.icss.xihu.model.RestaurantMenu;
// 导入MyBatis的所有注解
import org.apache.ibatis.annotations.*;

// 导入List集合接口
import java.util.List;
// 导入Map集合接口
import java.util.Map;

/**
 * 餐厅数据访问层
 * 功能概述：定义餐厅相关的数据库操作方法，包括餐厅查询、订单操作、评价操作、菜单操作等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 餐厅Mapper接口，定义餐厅相关的数据库操作方法
public interface RestaurantMapper {

    // 餐厅相关查询
    /**
     * 查询所有餐厅
     * 功能概述：从数据库中查询所有状态为启用的餐厅信息，按评分和创建时间倒序排列
     * @return {List<Restaurant>} 返回所有餐厅的列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，查询状态为启用的餐厅，按评分和创建时间倒序排列
    @Select("SELECT * FROM restaurant WHERE status = 1 ORDER BY rating DESC, create_time DESC")
    // 查询所有餐厅方法，返回所有餐厅的列表
    List<Restaurant> findAllRestaurants();

    /**
     * 根据ID查询餐厅
     * 功能概述：根据餐厅编号从数据库中查询指定餐厅的信息
     * @param {Long} id - 餐厅编号
     * @return {Restaurant} 返回餐厅对象，如果不存在则返回null
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM restaurant WHERE id = #{id}")
    // 根据ID查询餐厅方法，接收餐厅编号参数，返回餐厅对象
    Restaurant findRestaurantById(@Param("id") Long id);

    /**
     * 根据分类查询餐厅
     * 功能概述：根据分类从数据库中查询该分类下的所有状态为启用的餐厅信息，按评分倒序排列
     * @param {String} category - 餐厅分类
     * @return {List<Restaurant>} 返回该分类下的所有餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按分类和状态查询，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE category = #{category} AND status = 1 ORDER BY rating DESC")
    // 根据分类查询餐厅方法，接收分类参数，返回该分类下的所有餐厅列表
    List<Restaurant> findByCategory(@Param("category") String category);

    /**
     * 根据关键词搜索餐厅
     * 功能概述：根据关键词从数据库中搜索匹配的餐厅信息，支持按名称、描述或特色模糊搜索，按评分倒序排列
     * @param {String} keyword - 搜索关键词
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，在名称、描述或特色中模糊搜索，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') OR features LIKE CONCAT('%', #{keyword}, '%')) AND status = 1 ORDER BY rating DESC")
    // 根据关键词搜索餐厅方法，接收搜索关键词参数，返回匹配的餐厅列表
    List<Restaurant> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 搜索餐厅（支持关键词和菜系类型筛选）
     * 功能概述：从数据库中搜索餐厅信息，支持按关键词和菜系类型筛选，按评分倒序排列
     * @param {String} keyword - 搜索关键词（可选）
     * @param {String} cuisineType - 菜系类型（可选）
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，支持关键词和菜系类型筛选，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE status = 1 " +
            "AND (#{keyword} IS NULL OR name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') OR features LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND (#{cuisineType} IS NULL OR category = #{cuisineType}) " +
            "ORDER BY rating DESC")
    // 搜索餐厅方法，接收搜索关键词和菜系类型参数，返回匹配的餐厅列表
    List<Restaurant> searchRestaurants(@Param("keyword") String keyword, @Param("cuisineType") String cuisineType);

    /**
     * 根据城市查询餐厅
     * 功能概述：根据城市名称从数据库中查询该城市下的所有状态为启用的餐厅信息，按评分倒序排列
     * @param {String} city - 城市名称
     * @return {List<Restaurant>} 返回该城市下的所有餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按城市和状态查询，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE city = #{city} AND status = 1 ORDER BY rating DESC")
    // 根据城市查询餐厅方法，接收城市名称参数，返回该城市下的所有餐厅列表
    List<Restaurant> findByCity(@Param("city") String city);

    /**
     * 根据省份查询餐厅
     * 功能概述：根据省份名称从数据库中查询该省份下的所有状态为启用的餐厅信息，按评分倒序排列
     * @param {String} province - 省份名称
     * @return {List<Restaurant>} 返回该省份下的所有餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按省份和状态查询，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE province = #{province} AND status = 1 ORDER BY rating DESC")
    // 根据省份查询餐厅方法，接收省份名称参数，返回该省份下的所有餐厅列表
    List<Restaurant> findByProvince(@Param("province") String province);

    /**
     * 根据价格范围查询餐厅
     * 功能概述：根据价格范围从数据库中查询匹配的餐厅信息，按评分倒序排列
     * @param {String} priceRange - 价格范围
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，在价格范围中模糊搜索，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE price_range LIKE CONCAT('%', #{priceRange}, '%') AND status = 1 ORDER BY rating DESC")
    // 根据价格范围查询餐厅方法，接收价格范围参数，返回匹配的餐厅列表
    List<Restaurant> findByPriceRange(@Param("priceRange") String priceRange);

    /**
     * 根据评分查询餐厅
     * 功能概述：根据最低评分从数据库中查询评分大于等于该值的所有状态为启用的餐厅信息，按评分倒序排列
     * @param {Double} minRating - 最低评分
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按最低评分和状态查询，按评分倒序排列
    @Select("SELECT * FROM restaurant WHERE rating >= #{minRating} AND status = 1 ORDER BY rating DESC")
    // 根据评分查询餐厅方法，接收最低评分参数，返回匹配的餐厅列表
    List<Restaurant> findByRating(@Param("minRating") Double minRating);

    /**
     * 查询所有分类
     * 功能概述：从数据库中查询所有不重复的餐厅分类名称
     * @return {List<String>} 返回所有分类名称的列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用DISTINCT去重，按分类名称排序
    @Select("SELECT DISTINCT category FROM restaurant WHERE status = 1 ORDER BY category")
    // 查询所有分类方法，返回所有分类名称的列表
    List<String> findAllCategories();

    /**
     * 查询所有菜系类型
     * 功能概述：从数据库中查询所有不重复的菜系类型名称（分类不为空）
     * @return {List<String>} 返回所有菜系类型名称的列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用DISTINCT去重，过滤空值，按分类名称排序
    @Select("SELECT DISTINCT category FROM restaurant WHERE status = 1 AND category IS NOT NULL ORDER BY category")
    // 查询所有菜系类型方法，返回所有菜系类型名称的列表
    List<String> findAllCuisineTypes();

    /**
     * 查询所有城市
     * 功能概述：从数据库中查询所有不重复的城市名称
     * @return {List<String>} 返回所有城市名称的列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用DISTINCT去重，按城市名称排序
    @Select("SELECT DISTINCT city FROM restaurant WHERE status = 1 ORDER BY city")
    // 查询所有城市方法，返回所有城市名称的列表
    List<String> findAllCities();

    /**
     * 查询所有省份
     * 功能概述：从数据库中查询所有不重复的省份名称
     * @return {List<String>} 返回所有省份名称的列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用DISTINCT去重，按省份名称排序
    @Select("SELECT DISTINCT province FROM restaurant WHERE status = 1 ORDER BY province")
    // 查询所有省份方法，返回所有省份名称的列表
    List<String> findAllProvinces();

    // 菜系统计
    /**
     * 获取分类统计
     * 功能概述：从数据库中统计各分类的餐厅数量
     * @return {List<Map<String, Object>>} 返回分类统计结果列表（包含分类名称和数量）
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用GROUP BY分组统计，按数量倒序排列
    @Select("SELECT category, COUNT(*) as count FROM restaurant WHERE status = 1 GROUP BY category ORDER BY count DESC")
    // 获取分类统计方法，返回分类统计结果列表
    List<Map<String, Object>> getCategoryStatistics();

    /**
     * 获取菜系分类数量统计
     * 功能概述：从数据库中统计各菜系分类的餐厅数量
     * @return {List<Map<String, Object>>} 返回菜系分类统计结果列表（包含分类名称和数量）
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用GROUP BY分组统计，按数量倒序排列
    @Select("SELECT category, COUNT(*) as count FROM restaurant WHERE status = 1 GROUP BY category ORDER BY count DESC")
    // 获取菜系分类数量统计方法，返回菜系分类统计结果列表
    List<Map<String, Object>> getCuisineCategoryCounts();

    // 订单相关操作
    /**
     * 插入订单
     * 功能概述：向数据库中插入一条餐厅订单记录
     * @param {RestaurantOrder} order - 餐厅订单对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入订单信息
    @Insert("INSERT INTO restaurant_order (order_no, user_id, restaurant_id, restaurant_name, booking_date, booking_time, people_count, contact_name, contact_phone, special_requirements, estimated_amount, status) " +
            "VALUES (#{orderNo}, #{userId}, #{restaurantId}, #{restaurantName}, #{bookingDate}, #{bookingTime}, #{peopleCount}, #{contactName}, #{contactPhone}, #{specialRequirements}, #{estimatedAmount}, #{status})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给order对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入订单方法，接收餐厅订单对象参数，返回插入的记录数
    int insertOrder(RestaurantOrder order);

    /**
     * 插入餐厅订单
     * 功能概述：向数据库中插入一条餐厅订单记录（包含总金额和订单状态）
     * @param {RestaurantOrder} order - 餐厅订单对象
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入订单信息（包含总金额和订单状态）
    @Insert("INSERT INTO restaurant_order (order_no, user_id, restaurant_id, restaurant_name, people_count, contact_name, contact_phone, special_requirements, estimated_amount, total_amount, order_status, create_time, update_time) " +
            "VALUES (#{orderNo}, #{userId}, #{restaurantId}, #{restaurantName}, #{peopleCount}, #{contactName}, #{contactPhone}, #{specialRequirements}, #{estimatedAmount}, #{totalAmount}, #{orderStatus}, #{createTime}, #{updateTime})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给order对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入餐厅订单方法，接收餐厅订单对象参数
    void insertRestaurantOrder(RestaurantOrder order);
    
    /**
     * 更新统一订单号（关联统一订单）
     * 功能概述：根据订单编号更新餐厅订单的统一订单号，用于关联统一订单表
     * @param {Long} orderId - 订单编号
     * @param {String} unifiedOrderNo - 统一订单号
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新统一订单号
    @Update("UPDATE restaurant_order SET unified_order_no = #{unifiedOrderNo} WHERE id = #{orderId}")
    // 更新统一订单号方法，接收订单编号和统一订单号参数，返回更新的记录数
    int updateUnifiedOrderNo(@Param("orderId") Long orderId, @Param("unifiedOrderNo") String unifiedOrderNo);

    /**
     * 根据用户ID查询订单
     * 功能概述：根据用户编号从数据库中查询该用户的所有餐厅订单信息，按创建时间倒序排列
     * @param {Long} userId - 用户编号
     * @return {List<RestaurantOrder>} 返回该用户的所有订单列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按用户编号查询，按创建时间倒序排列
    @Select("SELECT * FROM restaurant_order WHERE user_id = #{userId} ORDER BY create_time DESC")
    // 根据用户ID查询订单方法，接收用户编号参数，返回该用户的所有订单列表
    List<RestaurantOrder> findOrdersByUserId(@Param("userId") Long userId);

    /**
     * 根据订单ID查询订单
     * 功能概述：根据订单编号从数据库中查询指定餐厅订单的信息
     * @param {Long} orderId - 订单编号
     * @return {RestaurantOrder} 返回订单对象，如果不存在则返回null
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM restaurant_order WHERE id = #{orderId}")
    // 根据订单ID查询订单方法，接收订单编号参数，返回订单对象
    RestaurantOrder findOrderById(@Param("orderId") Long orderId);

    /**
     * 更新订单状态
     * 功能概述：根据订单编号更新餐厅订单的状态
     * @param {Long} orderId - 订单编号
     * @param {Integer} orderStatus - 订单状态
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新订单状态
    @Update("UPDATE restaurant_order SET order_status = #{orderStatus} WHERE id = #{orderId}")
    // 更新订单状态方法，接收订单编号和订单状态参数，返回更新的记录数
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") Integer orderStatus);

    /**
     * 根据订单号查询订单
     * 功能概述：根据订单号从数据库中查询指定餐厅订单的信息
     * @param {String} orderNo - 订单号
     * @return {RestaurantOrder} 返回订单对象，如果不存在则返回null
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用WHERE条件查询
    @Select("SELECT * FROM restaurant_order WHERE order_no = #{orderNo}")
    // 根据订单号查询订单方法，接收订单号参数，返回订单对象
    RestaurantOrder findOrderByOrderNo(@Param("orderNo") String orderNo);

    // 评价相关操作
    /**
     * 插入评价
     * 功能概述：向数据库中插入一条餐厅评价记录
     * @param {RestaurantReview} review - 餐厅评价对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入评价信息
    @Insert("INSERT INTO restaurant_review (restaurant_id, user_id, username, user_avatar, rating, content, images) " +
            "VALUES (#{restaurantId}, #{userId}, #{username}, #{userAvatar}, #{rating}, #{content}, #{images})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给review对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入评价方法，接收餐厅评价对象参数，返回插入的记录数
    int insertReview(RestaurantReview review);

    /**
     * 插入餐厅评价
     * 功能概述：向数据库中插入一条餐厅评价记录（包含创建时间）
     * @param {RestaurantReview} review - 餐厅评价对象
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入评价信息（包含创建时间）
    @Insert("INSERT INTO restaurant_review (restaurant_id, user_id, username, user_avatar, rating, content, images, create_time) " +
            "VALUES (#{restaurantId}, #{userId}, #{username}, #{userAvatar}, #{rating}, #{content}, #{images}, #{createTime})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给review对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入餐厅评价方法，接收餐厅评价对象参数
    void insertRestaurantReview(RestaurantReview review);

    /**
     * 根据餐厅ID查询评价
     * 功能概述：根据餐厅编号从数据库中查询该餐厅的所有评价信息，按创建时间倒序排列
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<RestaurantReview>} 返回该餐厅的所有评价列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按餐厅编号查询，按创建时间倒序排列
    @Select("SELECT * FROM restaurant_review WHERE restaurant_id = #{restaurantId} ORDER BY create_time DESC")
    // 根据餐厅ID查询评价方法，接收餐厅编号参数，返回该餐厅的所有评价列表
    List<RestaurantReview> findReviewsByRestaurantId(@Param("restaurantId") Long restaurantId);

    /**
     * 获取餐厅评分统计
     * 功能概述：根据餐厅编号从数据库中统计该餐厅的平均评分和评价数量
     * @param {Long} restaurantId - 餐厅编号
     * @return {Map<String, Object>} 返回评分统计结果（包含平均评分和评价数量）
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用AVG和COUNT函数统计
    @Select("SELECT AVG(rating) as avgRating, COUNT(*) as reviewCount FROM restaurant_review WHERE restaurant_id = #{restaurantId}")
    // 获取餐厅评分统计方法，接收餐厅编号参数，返回评分统计结果
    Map<String, Object> getRestaurantRatingStats(@Param("restaurantId") Long restaurantId);

    /**
     * 根据餐厅ID分页查询评价
     * 功能概述：根据餐厅编号从数据库中分页查询该餐厅的评价信息，按创建时间倒序排列
     * @param {Long} restaurantId - 餐厅编号
     * @param {int} offset - 偏移量（跳过的记录数）
     * @param {int} limit - 每页记录数
     * @return {List<RestaurantReview>} 返回该餐厅的评价列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按餐厅编号查询，使用LIMIT和OFFSET实现分页，按创建时间倒序排列
    @Select("SELECT * FROM restaurant_review WHERE restaurant_id = #{restaurantId} ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    // 根据餐厅ID分页查询评价方法，接收餐厅编号、偏移量和每页记录数参数，返回该餐厅的评价列表
    List<RestaurantReview> findReviewsByRestaurantIdWithPagination(@Param("restaurantId") Long restaurantId, 
                                                                    @Param("offset") int offset, 
                                                                    @Param("limit") int limit);

    /**
     * 根据餐厅ID统计评价数量
     * 功能概述：根据餐厅编号从数据库中统计该餐厅的评价数量
     * @param {Long} restaurantId - 餐厅编号
     * @return {int} 返回评价数量
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用COUNT(*)统计评价数量
    @Select("SELECT COUNT(*) FROM restaurant_review WHERE restaurant_id = #{restaurantId}")
    // 根据餐厅ID统计评价数量方法，接收餐厅编号参数，返回评价数量
    int getReviewCountByRestaurantId(@Param("restaurantId") Long restaurantId);

    /**
     * 获取餐厅平均评分
     * 功能概述：根据餐厅编号从数据库中计算该餐厅的平均评分
     * @param {Long} restaurantId - 餐厅编号
     * @return {Double} 返回平均评分
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用AVG函数计算平均评分
    @Select("SELECT AVG(rating) FROM restaurant_review WHERE restaurant_id = #{restaurantId}")
    // 获取餐厅平均评分方法，接收餐厅编号参数，返回平均评分
    Double getAverageRatingForRestaurant(@Param("restaurantId") Long restaurantId);

    // 菜单相关操作
    /**
     * 根据餐厅ID查询菜单
     * 功能概述：根据餐厅编号从数据库中查询该餐厅的所有可用菜单信息，按招牌菜、分类和名称排序
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<RestaurantMenu>} 返回该餐厅的所有菜单列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按餐厅编号和可用状态查询，按招牌菜、分类和名称排序
    @Select("SELECT * FROM restaurant_menu WHERE restaurant_id = #{restaurantId} AND is_available = 1 ORDER BY is_signature DESC, category, name")
    // 根据餐厅ID查询菜单方法，接收餐厅编号参数，返回该餐厅的所有菜单列表
    List<RestaurantMenu> findMenuByRestaurantId(@Param("restaurantId") Long restaurantId);

    /**
     * 根据餐厅ID和分类查询菜单
     * 功能概述：根据餐厅编号和分类从数据库中查询该餐厅指定分类下的所有可用菜单信息，按招牌菜和名称排序
     * @param {Long} restaurantId - 餐厅编号
     * @param {String} category - 菜单分类
     * @return {List<RestaurantMenu>} 返回该餐厅指定分类下的所有菜单列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按餐厅编号、分类和可用状态查询，按招牌菜和名称排序
    @Select("SELECT * FROM restaurant_menu WHERE restaurant_id = #{restaurantId} AND category = #{category} AND is_available = 1 ORDER BY is_signature DESC, name")
    // 根据餐厅ID和分类查询菜单方法，接收餐厅编号和分类参数，返回该餐厅指定分类下的所有菜单列表
    List<RestaurantMenu> findMenuByRestaurantIdAndCategory(@Param("restaurantId") Long restaurantId, @Param("category") String category);

    /**
     * 根据餐厅ID查询菜单分类
     * 功能概述：根据餐厅编号从数据库中查询该餐厅的所有不重复的菜单分类名称
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<String>} 返回该餐厅的所有菜单分类名称列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用DISTINCT去重，按分类名称排序
    @Select("SELECT DISTINCT category FROM restaurant_menu WHERE restaurant_id = #{restaurantId} AND is_available = 1 ORDER BY category")
    // 根据餐厅ID查询菜单分类方法，接收餐厅编号参数，返回该餐厅的所有菜单分类名称列表
    List<String> findMenuCategoriesByRestaurantId(@Param("restaurantId") Long restaurantId);

    /**
     * 根据餐厅ID查询招牌菜
     * 功能概述：根据餐厅编号从数据库中查询该餐厅的所有招牌菜信息，按名称排序
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<RestaurantMenu>} 返回该餐厅的所有招牌菜列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按餐厅编号、招牌菜标识和可用状态查询，按名称排序
    @Select("SELECT * FROM restaurant_menu WHERE restaurant_id = #{restaurantId} AND is_signature = 1 AND is_available = 1 ORDER BY name")
    // 根据餐厅ID查询招牌菜方法，接收餐厅编号参数，返回该餐厅的所有招牌菜列表
    List<RestaurantMenu> findSignatureMenuByRestaurantId(@Param("restaurantId") Long restaurantId);
    
    /**
     * 插入订单详情
     * 功能概述：向数据库中插入一条餐厅订单详情记录
     * @param {RestaurantOrderDetail} detail - 餐厅订单详情对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入订单详情信息
    @Insert("INSERT INTO restaurant_order_detail (order_id, menu_id, menu_name, menu_price, quantity, subtotal) " +
            "VALUES (#{orderId}, #{menuId}, #{menuName}, #{menuPrice}, #{quantity}, #{subtotal})")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给detail对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入订单详情方法，接收餐厅订单详情对象参数，返回插入的记录数
    int insertOrderDetail(com.icss.xihu.model.RestaurantOrderDetail detail);
    
    /**
     * 根据订单ID查询订单详情
     * 功能概述：根据订单编号从数据库中查询该订单的所有详情信息，按详情编号排序
     * @param {Integer} orderId - 订单编号
     * @return {List<RestaurantOrderDetail>} 返回该订单的所有详情列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按订单编号查询，按详情编号排序
    @Select("SELECT * FROM restaurant_order_detail WHERE order_id = #{orderId} ORDER BY id")
    // 根据订单ID查询订单详情方法，接收订单编号参数，返回该订单的所有详情列表
    List<com.icss.xihu.model.RestaurantOrderDetail> findDetailsByOrderId(@Param("orderId") Integer orderId);
}
