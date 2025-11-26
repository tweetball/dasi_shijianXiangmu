// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入餐厅实体类
import com.icss.xihu.model.Restaurant;
// 导入餐厅订单实体类
import com.icss.xihu.model.RestaurantOrder;
// 导入餐厅评价实体类
import com.icss.xihu.model.RestaurantReview;
// 导入餐厅菜单实体类
import com.icss.xihu.model.RestaurantMenu;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 餐厅服务接口
 * 功能概述：定义餐厅相关的业务逻辑方法，包括餐厅查询、订单管理、评价管理、菜单管理等
 */
// 餐厅服务接口，定义餐厅相关的业务逻辑方法
public interface RestaurantService {

    // 餐厅查询相关
    /**
     * 查询所有餐厅
     * 功能概述：查询所有餐厅列表
     * @return 餐厅列表
     */
    // 查询所有餐厅方法，返回餐厅列表
    List<Restaurant> findAllRestaurants();
    
    /**
     * 根据ID获取餐厅
     * 功能概述：根据餐厅ID查询餐厅详细信息
     * @param id 餐厅ID
     * @return 餐厅对象，不存在返回null
     */
    // 根据ID获取餐厅方法，接收餐厅ID参数
    Restaurant getRestaurantById(Long id);
    
    /**
     * 根据菜系查询餐厅
     * 功能概述：根据菜系类型查询餐厅列表
     * @param category 菜系类型
     * @return 餐厅列表
     */
    // 根据菜系查询餐厅方法，接收菜系类型参数
    List<Restaurant> getRestaurantsByCategory(String category);
    
    /**
     * 搜索餐厅
     * 功能概述：根据关键词搜索餐厅（餐厅名称、地址等）
     * @param keyword 搜索关键词
     * @return 餐厅列表
     */
    // 搜索餐厅方法，接收搜索关键词参数
    List<Restaurant> searchRestaurants(String keyword);
    
    /**
     * 根据城市查询餐厅
     * 功能概述：根据城市名称查询餐厅列表
     * @param city 城市名称
     * @return 餐厅列表
     */
    // 根据城市查询餐厅方法，接收城市名称参数
    List<Restaurant> getRestaurantsByCity(String city);
    
    /**
     * 根据省份查询餐厅
     * 功能概述：根据省份名称查询餐厅列表
     * @param province 省份名称
     * @return 餐厅列表
     */
    // 根据省份查询餐厅方法，接收省份名称参数
    List<Restaurant> getRestaurantsByProvince(String province);
    
    /**
     * 根据价格范围查询餐厅
     * 功能概述：根据价格范围查询餐厅列表
     * @param priceRange 价格范围（如：50-100）
     * @return 餐厅列表
     */
    // 根据价格范围查询餐厅方法，接收价格范围参数
    List<Restaurant> getRestaurantsByPriceRange(String priceRange);
    
    /**
     * 根据评分查询餐厅
     * 功能概述：根据最低评分查询餐厅列表
     * @param minRating 最低评分
     * @return 餐厅列表
     */
    // 根据评分查询餐厅方法，接收最低评分参数
    List<Restaurant> getRestaurantsByRating(Double minRating);
    
    /**
     * 获取所有菜系类型
     * 功能概述：获取所有餐厅的菜系类型列表
     * @return 菜系类型列表
     */
    // 获取所有菜系类型方法，返回菜系类型列表
    List<String> getAllCategories();
    
    /**
     * 获取所有城市
     * 功能概述：获取所有餐厅所在的城市列表
     * @return 城市列表
     */
    // 获取所有城市方法，返回城市列表
    List<String> getAllCities();
    
    /**
     * 获取所有省份
     * 功能概述：获取所有餐厅所在的省份列表
     * @return 省份列表
     */
    // 获取所有省份方法，返回省份列表
    List<String> getAllProvinces();

    // 菜系统计
    /**
     * 获取菜系统计
     * 功能概述：获取各菜系类型的统计信息（数量、平均评分等）
     * @return 菜系统计列表
     */
    // 获取菜系统计方法，返回菜系统计列表
    List<Map<String, Object>> getCategoryStatistics();

    // 订单相关
    /**
     * 创建餐厅订单
     * 功能概述：创建新的餐厅订单，生成订单号
     * @param order 餐厅订单对象
     * @return 订单号
     */
    // 创建餐厅订单方法，接收餐厅订单对象参数
    String createOrder(RestaurantOrder order);
    
    /**
     * 获取用户订单列表
     * 功能概述：根据用户ID查询该用户的所有餐厅订单列表
     * @param userId 用户ID
     * @return 餐厅订单列表
     */
    // 获取用户订单列表方法，接收用户ID参数
    List<RestaurantOrder> getUserOrders(Integer userId);
    
    /**
     * 根据订单ID获取订单
     * 功能概述：根据订单ID查询餐厅订单详细信息
     * @param orderId 订单ID
     * @return 餐厅订单对象，不存在返回null
     */
    // 根据订单ID获取订单方法，接收订单ID参数
    RestaurantOrder getOrderById(Long orderId);
    
    /**
     * 根据订单号获取订单
     * 功能概述：根据订单号查询餐厅订单详细信息
     * @param orderNo 订单号
     * @return 餐厅订单对象，不存在返回null
     */
    // 根据订单号获取订单方法，接收订单号参数
    RestaurantOrder getOrderByOrderNo(String orderNo);
    
    /**
     * 更新订单状态
     * 功能概述：更新餐厅订单的状态（待支付、已支付、已取消、已完成等）
     * @param orderId 订单ID
     * @param status 订单状态
     * @return 是否更新成功
     */
    // 更新订单状态方法，接收订单ID和订单状态参数
    boolean updateOrderStatus(Long orderId, String status);
    
    /**
     * 取消订单
     * 功能概述：取消餐厅订单，更新订单状态为已取消
     * @param orderId 订单ID
     * @return 是否取消成功
     */
    // 取消订单方法，接收订单ID参数
    boolean cancelOrder(Long orderId);

    // 评价相关
    /**
     * 添加评价
     * 功能概述：添加餐厅评价，记录用户对餐厅的评分和评论
     * @param review 餐厅评价对象
     * @return 是否添加成功
     */
    // 添加评价方法，接收餐厅评价对象参数
    boolean addReview(RestaurantReview review);
    
    /**
     * 获取餐厅评价列表
     * 功能概述：根据餐厅ID查询该餐厅的所有评价列表
     * @param restaurantId 餐厅ID
     * @return 餐厅评价列表
     */
    // 获取餐厅评价列表方法，接收餐厅ID参数
    List<RestaurantReview> getRestaurantReviews(Long restaurantId);
    
    /**
     * 分页获取餐厅评价列表
     * 功能概述：根据餐厅ID分页查询该餐厅的评价列表
     * @param restaurantId 餐厅ID
     * @param page 页码（从1开始）
     * @param size 每页数量
     * @return 餐厅评价列表
     */
    // 分页获取餐厅评价列表方法，接收餐厅ID、页码和每页数量参数
    List<RestaurantReview> getRestaurantReviewsWithPagination(Long restaurantId, int page, int size);
    
    /**
     * 获取餐厅评分统计
     * 功能概述：获取餐厅的评分统计信息（平均评分、评分分布等）
     * @param restaurantId 餐厅ID
     * @return 评分统计数据Map
     */
    // 获取餐厅评分统计方法，接收餐厅ID参数
    Map<String, Object> getRestaurantRatingStats(Long restaurantId);
    
    /**
     * 获取餐厅评价数量
     * 功能概述：获取餐厅的评价总数
     * @param restaurantId 餐厅ID
     * @return 评价数量
     */
    // 获取餐厅评价数量方法，接收餐厅ID参数
    int getRestaurantReviewCount(Long restaurantId);

    // 菜单相关
    /**
     * 获取餐厅菜单
     * 功能概述：根据餐厅ID查询该餐厅的菜单列表
     * @param restaurantId 餐厅ID
     * @return 餐厅菜单列表
     */
    // 获取餐厅菜单方法，接收餐厅ID参数
    List<RestaurantMenu> getRestaurantMenu(Long restaurantId);
    
    /**
     * 根据菜系获取餐厅菜单
     * 功能概述：根据餐厅ID和菜系类型查询该餐厅的菜单列表
     * @param restaurantId 餐厅ID
     * @param category 菜系类型
     * @return 餐厅菜单列表
     */
    // 根据菜系获取餐厅菜单方法，接收餐厅ID和菜系类型参数
    List<RestaurantMenu> getRestaurantMenuByCategory(Long restaurantId, String category);
    
    /**
     * 获取餐厅菜单菜系类型
     * 功能概述：获取餐厅菜单的所有菜系类型列表
     * @param restaurantId 餐厅ID
     * @return 菜系类型列表
     */
    // 获取餐厅菜单菜系类型方法，接收餐厅ID参数
    List<String> getRestaurantMenuCategories(Long restaurantId);
    
    /**
     * 获取餐厅招牌菜
     * 功能概述：获取餐厅的招牌菜列表（推荐菜品）
     * @param restaurantId 餐厅ID
     * @return 餐厅菜单列表（招牌菜）
     */
    // 获取餐厅招牌菜方法，接收餐厅ID参数
    List<RestaurantMenu> getRestaurantSignatureMenu(Long restaurantId);

    // 业务逻辑方法
    /**
     * 生成订单号
     * 功能概述：生成唯一的餐厅订单号
     * @return 订单号
     */
    // 生成订单号方法，返回订单号字符串
    String generateOrderNo();
    
    /**
     * 计算预估金额
     * 功能概述：根据餐厅ID和人数计算预估消费金额
     * @param restaurantId 餐厅ID
     * @param peopleCount 人数
     * @return 预估金额
     */
    // 计算预估金额方法，接收餐厅ID和人数参数
    BigDecimal calculateEstimatedAmount(Long restaurantId, Integer peopleCount);
    
    /**
     * 验证订单数据
     * 功能概述：验证餐厅订单数据的有效性（必填字段、数据格式等）
     * @param order 餐厅订单对象
     * @return 是否验证通过
     */
    // 验证订单数据方法，接收餐厅订单对象参数
    boolean validateOrderData(RestaurantOrder order);
    
    /**
     * 验证评价数据
     * 功能概述：验证餐厅评价数据的有效性（必填字段、评分范围等）
     * @param review 餐厅评价对象
     * @return 是否验证通过
     */
    // 验证评价数据方法，接收餐厅评价对象参数
    boolean validateReviewData(RestaurantReview review);
}
