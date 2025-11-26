/**
 * 餐厅服务实现类
 * 功能概述：实现RestaurantService接口，提供餐厅相关的业务逻辑实现，包括餐厅查询、订单管理、评价管理、菜单管理等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入餐厅Mapper接口
import com.icss.xihu.mapper.RestaurantMapper;
// 导入餐厅实体类
import com.icss.xihu.model.Restaurant;
// 导入餐厅订单实体类
import com.icss.xihu.model.RestaurantOrder;
// 导入餐厅评价实体类
import com.icss.xihu.model.RestaurantReview;
// 导入餐厅菜单实体类
import com.icss.xihu.model.RestaurantMenu;
// 导入餐厅服务接口
import com.icss.xihu.service.RestaurantService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

// 导入BigDecimal类，用于精确的金额计算
import java.math.BigDecimal;
// 导入LocalDateTime类，用于表示日期和时间
import java.time.LocalDateTime;
// 导入DateTimeFormatter类，用于格式化日期和时间
import java.time.format.DateTimeFormatter;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;
// 导入Random类，用于生成随机数
import java.util.Random;

/**
 * 餐厅服务实现类
 * 功能概述：实现RestaurantService接口，提供餐厅相关的业务逻辑实现，包括餐厅查询、订单管理、评价管理、菜单管理等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 餐厅服务实现类，实现RestaurantService接口
public class RestaurantServiceImpl implements RestaurantService {

    // 自动注入餐厅Mapper，Spring容器会自动查找并注入RestaurantMapper的实现类
    @Autowired
    // 餐厅Mapper对象，用于调用数据库操作方法
    private RestaurantMapper restaurantMapper;

    /**
     * 查询所有餐厅
     * 功能概述：调用Mapper层方法，查询所有状态为启用的餐厅信息
     * @return {List<Restaurant>} 返回所有餐厅的列表
     */
    // 重写接口中的findAllRestaurants方法
    @Override
    // 查询所有餐厅方法，返回所有餐厅的列表
    public List<Restaurant> findAllRestaurants() {
        // 调用Mapper层的findAllRestaurants方法，查询所有餐厅信息并返回
        return restaurantMapper.findAllRestaurants();
    }

    /**
     * 根据ID获取餐厅
     * 功能概述：调用Mapper层方法，根据餐厅编号查询指定餐厅的详细信息
     * @param {Long} id - 餐厅编号
     * @return {Restaurant} 返回餐厅对象，如果不存在则返回null
     */
    // 重写接口中的getRestaurantById方法
    @Override
    // 根据ID获取餐厅方法，接收餐厅编号参数，返回餐厅对象
    public Restaurant getRestaurantById(Long id) {
        // 调用Mapper层的findRestaurantById方法，根据餐厅编号查询餐厅信息并返回
        return restaurantMapper.findRestaurantById(id);
    }

    /**
     * 根据菜系查询餐厅
     * 功能概述：调用Mapper层方法，根据菜系类型查询该菜系下的所有餐厅信息
     * @param {String} category - 菜系类型
     * @return {List<Restaurant>} 返回该菜系下的所有餐厅列表
     */
    // 重写接口中的getRestaurantsByCategory方法
    @Override
    // 根据菜系查询餐厅方法，接收菜系类型参数，返回该菜系下的所有餐厅列表
    public List<Restaurant> getRestaurantsByCategory(String category) {
        // 调用Mapper层的searchRestaurants方法，传入null作为关键词，传入category作为菜系类型，查询餐厅列表并返回
        return restaurantMapper.searchRestaurants(null, category);
    }

    /**
     * 搜索餐厅
     * 功能概述：调用Mapper层方法，根据关键词搜索匹配的餐厅信息
     * @param {String} keyword - 搜索关键词
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 重写接口中的searchRestaurants方法
    @Override
    // 搜索餐厅方法，接收搜索关键词参数，返回匹配的餐厅列表
    public List<Restaurant> searchRestaurants(String keyword) {
        // 调用Mapper层的searchRestaurants方法，传入keyword作为关键词，传入null作为菜系类型，查询餐厅列表并返回
        return restaurantMapper.searchRestaurants(keyword, null);
    }

    /**
     * 根据城市查询餐厅
     * 功能概述：调用Mapper层方法，根据城市名称查询该城市下的所有餐厅信息（当前实现为临时实现）
     * @param {String} city - 城市名称
     * @return {List<Restaurant>} 返回该城市下的所有餐厅列表
     */
    // 重写接口中的getRestaurantsByCity方法
    @Override
    // 根据城市查询餐厅方法，接收城市名称参数，返回该城市下的所有餐厅列表（当前实现为临时实现）
    public List<Restaurant> getRestaurantsByCity(String city) {
        // 调用Mapper层的searchRestaurants方法，传入null作为关键词和菜系类型，查询餐厅列表并返回（临时实现）
        return restaurantMapper.searchRestaurants(null, null);
    }

    /**
     * 根据省份查询餐厅
     * 功能概述：调用Mapper层方法，根据省份名称查询该省份下的所有餐厅信息（当前实现为临时实现）
     * @param {String} province - 省份名称
     * @return {List<Restaurant>} 返回该省份下的所有餐厅列表
     */
    // 重写接口中的getRestaurantsByProvince方法
    @Override
    // 根据省份查询餐厅方法，接收省份名称参数，返回该省份下的所有餐厅列表（当前实现为临时实现）
    public List<Restaurant> getRestaurantsByProvince(String province) {
        // 调用Mapper层的searchRestaurants方法，传入null作为关键词和菜系类型，查询餐厅列表并返回（临时实现）
        return restaurantMapper.searchRestaurants(null, null);
    }

    /**
     * 根据价格范围查询餐厅
     * 功能概述：调用Mapper层方法，根据价格范围查询匹配的餐厅信息（当前实现为临时实现）
     * @param {String} priceRange - 价格范围
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 重写接口中的getRestaurantsByPriceRange方法
    @Override
    // 根据价格范围查询餐厅方法，接收价格范围参数，返回匹配的餐厅列表（当前实现为临时实现）
    public List<Restaurant> getRestaurantsByPriceRange(String priceRange) {
        // 调用Mapper层的searchRestaurants方法，传入null作为关键词和菜系类型，查询餐厅列表并返回（临时实现）
        return restaurantMapper.searchRestaurants(null, null);
    }

    /**
     * 根据评分查询餐厅
     * 功能概述：调用Mapper层方法，根据最低评分查询评分大于等于该值的所有餐厅信息（当前实现为临时实现）
     * @param {Double} minRating - 最低评分
     * @return {List<Restaurant>} 返回匹配的餐厅列表
     */
    // 重写接口中的getRestaurantsByRating方法
    @Override
    // 根据评分查询餐厅方法，接收最低评分参数，返回匹配的餐厅列表（当前实现为临时实现）
    public List<Restaurant> getRestaurantsByRating(Double minRating) {
        // 调用Mapper层的searchRestaurants方法，传入null作为关键词和菜系类型，查询餐厅列表并返回（临时实现）
        return restaurantMapper.searchRestaurants(null, null);
    }

    /**
     * 获取所有菜系类型
     * 功能概述：调用Mapper层方法，查询所有不重复的菜系类型名称
     * @return {List<String>} 返回所有菜系类型名称的列表
     */
    // 重写接口中的getAllCategories方法
    @Override
    // 获取所有菜系类型方法，返回所有菜系类型名称的列表
    public List<String> getAllCategories() {
        // 调用Mapper层的findAllCuisineTypes方法，查询所有菜系类型名称并返回
        return restaurantMapper.findAllCuisineTypes();
    }

    /**
     * 获取所有城市
     * 功能概述：调用Mapper层方法，查询所有餐厅所在的不重复的城市名称
     * @return {List<String>} 返回所有城市名称的列表
     */
    // 重写接口中的getAllCities方法
    @Override
    // 获取所有城市方法，返回所有城市名称的列表
    public List<String> getAllCities() {
        // 调用Mapper层的searchRestaurants方法，查询所有餐厅信息，使用Stream API提取城市名称，去重后收集为列表并返回
        return restaurantMapper.searchRestaurants(null, null).stream()
                .map(Restaurant::getCity)  // 提取餐厅的城市名称
                .distinct()                 // 去重
                .collect(java.util.stream.Collectors.toList());  // 收集为列表
    }

    /**
     * 获取所有省份
     * 功能概述：调用Mapper层方法，查询所有餐厅所在的不重复的省份名称
     * @return {List<String>} 返回所有省份名称的列表
     */
    // 重写接口中的getAllProvinces方法
    @Override
    // 获取所有省份方法，返回所有省份名称的列表
    public List<String> getAllProvinces() {
        // 调用Mapper层的searchRestaurants方法，查询所有餐厅信息，使用Stream API提取省份名称，去重后收集为列表并返回
        return restaurantMapper.searchRestaurants(null, null).stream()
                .map(Restaurant::getProvince)  // 提取餐厅的省份名称
                .distinct()                     // 去重
                .collect(java.util.stream.Collectors.toList());  // 收集为列表
    }

    /**
     * 获取菜系统计
     * 功能概述：调用Mapper层方法，统计各菜系类型的餐厅数量
     * @return {List<Map<String, Object>>} 返回菜系统计结果列表（包含菜系类型和数量）
     */
    // 重写接口中的getCategoryStatistics方法
    @Override
    // 获取菜系统计方法，返回菜系统计结果列表
    public List<Map<String, Object>> getCategoryStatistics() {
        // 调用Mapper层的getCuisineCategoryCounts方法，统计各菜系类型的餐厅数量并返回
        return restaurantMapper.getCuisineCategoryCounts();
    }

    /**
     * 创建餐厅订单
     * 功能概述：创建新的餐厅订单，生成订单号，计算预估金额，验证订单数据，插入订单记录
     * @param {RestaurantOrder} order - 餐厅订单对象
     * @return {String} 返回订单号
     * @throws IllegalArgumentException 如果订单数据验证失败，抛出异常
     */
    // 重写接口中的createOrder方法
    @Override
    // 创建餐厅订单方法，接收餐厅订单对象参数，返回订单号
    public String createOrder(RestaurantOrder order) {
        // 生成订单号
        // 调用generateOrderNo方法，生成唯一的订单号
        String orderNo = generateOrderNo();
        // 将生成的订单号设置到订单对象中
        order.setOrderNo(orderNo);
        
        // 计算预估金额（如果已设置则使用，否则根据人数计算）
        // 如果订单的预估金额为空，则根据人数计算预估金额
        if (order.getEstimatedAmount() == null) {
            // 如果没有设置预估金额，根据人数计算
            // 获取订单的人数
            Integer peopleCount = order.getPeopleCount();
            // 如果人数为空或小于等于0，则设置为默认值1人
            if (peopleCount == null || peopleCount <= 0) {
                peopleCount = 1; // 默认1人
            }
            // 调用calculateEstimatedAmount方法，根据餐厅编号和人数计算预估金额
            BigDecimal estimatedAmount = calculateEstimatedAmount(order.getRestaurantId(), peopleCount);
            // 将计算的预估金额设置到订单对象中
            order.setEstimatedAmount(estimatedAmount);
        }
        
        // 设置总金额（如果未设置，使用预估金额）
        // 如果订单的总金额为空，则使用预估金额作为总金额
        if (order.getTotalAmount() == null) {
            order.setTotalAmount(order.getEstimatedAmount());
        }
        
        // 验证订单数据
        // 调用validateOrderData方法，验证订单数据的有效性
        if (!validateOrderData(order)) {
            // 如果验证失败，抛出IllegalArgumentException异常
            throw new IllegalArgumentException("订单数据验证失败");
        }
        
        // 插入订单
        // 调用Mapper层的insertRestaurantOrder方法，将订单信息插入数据库
        restaurantMapper.insertRestaurantOrder(order);
        // 返回订单号
        return orderNo;
    }

    /**
     * 获取用户订单列表
     * 功能概述：调用Mapper层方法，根据用户编号查询该用户的所有餐厅订单信息
     * @param {Integer} userId - 用户编号
     * @return {List<RestaurantOrder>} 返回该用户的所有订单列表
     */
    // 重写接口中的getUserOrders方法
    @Override
    // 获取用户订单列表方法，接收用户编号参数，返回该用户的所有订单列表
    public List<RestaurantOrder> getUserOrders(Integer userId) {
        // 调用Mapper层的findOrdersByUserId方法，将用户编号转换为Long类型，查询该用户的所有订单信息并返回
        return restaurantMapper.findOrdersByUserId(userId.longValue());
    }

    /**
     * 根据订单ID获取订单
     * 功能概述：调用Mapper层方法，根据订单编号查询指定餐厅订单的详细信息
     * @param {Long} orderId - 订单编号
     * @return {RestaurantOrder} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的getOrderById方法
    @Override
    // 根据订单ID获取订单方法，接收订单编号参数，返回订单对象
    public RestaurantOrder getOrderById(Long orderId) {
        // 调用Mapper层的findOrderById方法，根据订单编号查询订单信息并返回
        return restaurantMapper.findOrderById(orderId);
    }

    /**
     * 根据订单号获取订单
     * 功能概述：调用Mapper层方法，根据订单号查询指定餐厅订单的详细信息（当前实现为临时实现）
     * @param {String} orderNo - 订单号
     * @return {RestaurantOrder} 返回订单对象，如果不存在则返回null
     */
    // 重写接口中的getOrderByOrderNo方法
    @Override
    // 根据订单号获取订单方法，接收订单号参数，返回订单对象（当前实现为临时实现）
    public RestaurantOrder getOrderByOrderNo(String orderNo) {
        // 调用Mapper层的findOrderById方法，传入固定值1L作为订单编号，查询订单信息并返回（临时实现）
        return restaurantMapper.findOrderById(1L); // 临时实现
    }

    /**
     * 更新订单状态
     * 功能概述：将字符串状态转换为整数状态，调用Mapper层方法，更新餐厅订单的状态
     * @param {Long} orderId - 订单编号
     * @param {String} status - 订单状态（字符串格式：pending/paid/cancelled/completed 或 待支付/已支付/已取消/已完成）
     * @return {boolean} 返回是否更新成功（true-成功，false-失败）
     */
    // 重写接口中的updateOrderStatus方法
    @Override
    // 更新订单状态方法，接收订单编号和订单状态参数，返回是否更新成功
    public boolean updateOrderStatus(Long orderId, String status) {
        // 使用try-catch块捕获异常
        try {
            // 将字符串状态转换为整数状态
            // 定义整数状态变量
            Integer orderStatus;
            // 如果状态字符串为空，则设置为默认值0（待支付）
            if (status == null) {
                orderStatus = 0; // 默认待支付
            } else {
                // 根据状态字符串的小写形式，使用switch语句转换为对应的整数状态
                switch (status.toLowerCase()) {
                    case "pending":      // 如果状态为"pending"
                    case "待支付":        // 或"待支付"
                        orderStatus = 0;  // 设置为0（待支付）
                        break;
                    case "paid":         // 如果状态为"paid"
                    case "已支付":        // 或"已支付"
                        orderStatus = 1;  // 设置为1（已支付）
                        break;
                    case "cancelled":    // 如果状态为"cancelled"
                    case "已取消":        // 或"已取消"
                        orderStatus = 2;  // 设置为2（已取消）
                        break;
                    case "completed":    // 如果状态为"completed"
                    case "已完成":        // 或"已完成"
                        orderStatus = 3;  // 设置为3（已完成）
                        break;
                    default:             // 如果状态不匹配任何已知状态
                        orderStatus = 0;  // 设置为默认值0（待支付）
                        break;
                }
            }
            // 调用Mapper层的updateOrderStatus方法，更新订单状态
            restaurantMapper.updateOrderStatus(orderId, orderStatus);
            // 返回true表示更新成功
            return true;
        } catch (Exception e) {
            // 如果发生异常，返回false表示更新失败
            return false;
        }
    }

    /**
     * 取消订单
     * 功能概述：调用updateOrderStatus方法，将订单状态更新为已取消
     * @param {Long} orderId - 订单编号
     * @return {boolean} 返回是否取消成功（true-成功，false-失败）
     */
    // 重写接口中的cancelOrder方法
    @Override
    // 取消订单方法，接收订单编号参数，返回是否取消成功
    public boolean cancelOrder(Long orderId) {
        // 调用updateOrderStatus方法，将订单状态更新为"cancelled"（已取消）
        return updateOrderStatus(orderId, "cancelled");
    }

    /**
     * 添加评价
     * 功能概述：验证评价数据的有效性，调用Mapper层方法，插入餐厅评价记录
     * @param {RestaurantReview} review - 餐厅评价对象
     * @return {boolean} 返回是否添加成功（true-成功）
     * @throws IllegalArgumentException 如果评价数据验证失败，抛出异常
     */
    // 重写接口中的addReview方法
    @Override
    // 添加评价方法，接收餐厅评价对象参数，返回是否添加成功
    public boolean addReview(RestaurantReview review) {
        // 验证评价数据
        // 调用validateReviewData方法，验证评价数据的有效性
        if (!validateReviewData(review)) {
            // 如果验证失败，抛出IllegalArgumentException异常
            throw new IllegalArgumentException("评价数据验证失败");
        }
        
        // 调用Mapper层的insertRestaurantReview方法，将评价信息插入数据库
        restaurantMapper.insertRestaurantReview(review);
        // 返回true表示添加成功
        return true;
    }

    /**
     * 获取餐厅评价列表
     * 功能概述：调用Mapper层方法，根据餐厅编号查询该餐厅的所有评价信息
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<RestaurantReview>} 返回该餐厅的所有评价列表
     */
    // 重写接口中的getRestaurantReviews方法
    @Override
    // 获取餐厅评价列表方法，接收餐厅编号参数，返回该餐厅的所有评价列表
    public List<RestaurantReview> getRestaurantReviews(Long restaurantId) {
        // 调用Mapper层的findReviewsByRestaurantId方法，根据餐厅编号查询该餐厅的所有评价信息并返回
        return restaurantMapper.findReviewsByRestaurantId(restaurantId);
    }

    /**
     * 分页获取餐厅评价列表
     * 功能概述：计算偏移量，调用Mapper层方法，根据餐厅编号分页查询该餐厅的评价信息
     * @param {Long} restaurantId - 餐厅编号
     * @param {int} page - 页码（从1开始）
     * @param {int} size - 每页数量
     * @return {List<RestaurantReview>} 返回该餐厅的评价列表
     */
    // 重写接口中的getRestaurantReviewsWithPagination方法
    @Override
    // 分页获取餐厅评价列表方法，接收餐厅编号、页码和每页数量参数，返回该餐厅的评价列表
    public List<RestaurantReview> getRestaurantReviewsWithPagination(Long restaurantId, int page, int size) {
        // 计算偏移量（跳过的记录数）= (页码 - 1) × 每页数量
        int offset = (page - 1) * size;
        // 调用Mapper层的findReviewsByRestaurantIdWithPagination方法，根据餐厅编号、偏移量和每页数量查询该餐厅的评价信息并返回
        return restaurantMapper.findReviewsByRestaurantIdWithPagination(restaurantId, offset, size);
    }

    /**
     * 获取餐厅评分统计
     * 功能概述：调用Mapper层方法，统计餐厅的平均评分和评价数量，封装为Map返回
     * @param {Long} restaurantId - 餐厅编号
     * @return {Map<String, Object>} 返回评分统计结果（包含平均评分和评价数量）
     */
    // 重写接口中的getRestaurantRatingStats方法
    @Override
    // 获取餐厅评分统计方法，接收餐厅编号参数，返回评分统计结果
    public Map<String, Object> getRestaurantRatingStats(Long restaurantId) {
        // 创建结果Map对象，用于存储评分统计结果
        Map<String, Object> stats = new java.util.HashMap<>();
        // 调用Mapper层的getAverageRatingForRestaurant方法，获取餐厅的平均评分
        Double avgRating = restaurantMapper.getAverageRatingForRestaurant(restaurantId);
        // 调用Mapper层的getReviewCountByRestaurantId方法，获取餐厅的评价数量
        int reviewCount = restaurantMapper.getReviewCountByRestaurantId(restaurantId);
        // 将平均评分放入结果Map中，键为"averageRating"，如果平均评分为空则使用0.0
        stats.put("averageRating", avgRating != null ? avgRating : 0.0);
        // 将评价数量放入结果Map中，键为"reviewCount"
        stats.put("reviewCount", reviewCount);
        // 返回评分统计结果Map
        return stats;
    }

    /**
     * 获取餐厅评价数量
     * 功能概述：调用Mapper层方法，根据餐厅编号统计该餐厅的评价数量
     * @param {Long} restaurantId - 餐厅编号
     * @return {int} 返回评价数量
     */
    // 重写接口中的getRestaurantReviewCount方法
    @Override
    // 获取餐厅评价数量方法，接收餐厅编号参数，返回评价数量
    public int getRestaurantReviewCount(Long restaurantId) {
        // 调用Mapper层的getReviewCountByRestaurantId方法，根据餐厅编号统计该餐厅的评价数量并返回
        return restaurantMapper.getReviewCountByRestaurantId(restaurantId);
    }

    /**
     * 生成订单号
     * 功能概述：生成唯一的餐厅订单号，格式为：FOOD + 时间戳（yyyyMMddHHmmss）+ 4位随机数
     * @return {String} 返回订单号字符串
     */
    // 重写接口中的generateOrderNo方法
    @Override
    // 生成订单号方法，返回订单号字符串
    public String generateOrderNo() {
        // 生成格式：FOOD + 时间戳 + 随机数
        // 获取当前日期和时间，格式化为"yyyyMMddHHmmss"格式的字符串
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 生成4位随机数（0-9999），格式化为4位数字字符串（不足4位前面补0）
        String random = String.format("%04d", new Random().nextInt(10000));
        // 拼接订单号：FOOD + 时间戳 + 随机数，并返回
        return "FOOD" + timestamp + random;
    }

    /**
     * 计算预估金额
     * 功能概述：根据餐厅编号和人数计算预估消费金额，解析餐厅的价格范围，计算人均价格，乘以人数得到总金额
     * @param {Long} restaurantId - 餐厅编号
     * @param {Integer} peopleCount - 人数
     * @return {BigDecimal} 返回预估金额（元）
     */
    // 重写接口中的calculateEstimatedAmount方法
    @Override
    // 计算预估金额方法，接收餐厅编号和人数参数，返回预估金额
    public BigDecimal calculateEstimatedAmount(Long restaurantId, Integer peopleCount) {
        // 获取餐厅信息
        // 调用Mapper层的findRestaurantById方法，根据餐厅编号查询餐厅信息
        Restaurant restaurant = restaurantMapper.findRestaurantById(restaurantId);
        // 如果餐厅不存在，返回0
        if (restaurant == null) {
            return BigDecimal.ZERO;
        }
        
        // 解析价格范围，计算人均价格
        // 获取餐厅的价格范围字符串
        String priceRange = restaurant.getPriceRange();
        // 如果价格范围为空或空字符串，返回默认人均100元乘以人数
        if (priceRange == null || priceRange.isEmpty()) {
            return BigDecimal.valueOf(100); // 默认人均100元
        }
        
        // 使用try-catch块捕获解析异常
        try {
            // 解析价格范围，如 "100-150元/人"
            // 使用"-"分割价格范围字符串
            String[] parts = priceRange.split("-");
            // 如果分割后的数组长度大于等于2，说明有最低价和最高价
            if (parts.length >= 2) {
                // 提取最低价字符串，去除所有非数字字符
                String minPriceStr = parts[0].replaceAll("[^0-9]", "");
                // 提取最高价字符串，去除所有非数字字符
                String maxPriceStr = parts[1].replaceAll("[^0-9]", "");
                
                // 将最低价字符串转换为整数
                int minPrice = Integer.parseInt(minPriceStr);
                // 将最高价字符串转换为整数
                int maxPrice = Integer.parseInt(maxPriceStr);
                // 计算平均价格 = (最低价 + 最高价) / 2
                int avgPrice = (minPrice + maxPrice) / 2;
                
                // 返回平均价格乘以人数的总金额
                return BigDecimal.valueOf(avgPrice * peopleCount);
            }
        } catch (Exception e) {
            // 解析失败，使用默认价格
        }
        
        // 如果 peopleCount 为 null，使用默认值 1
        // 如果人数为空或小于等于0，设置为默认值1人
        if (peopleCount == null || peopleCount <= 0) {
            peopleCount = 1;
        }
        // 返回默认人均100元乘以人数
        return BigDecimal.valueOf(100 * peopleCount);
    }

    /**
     * 验证订单数据
     * 功能概述：验证餐厅订单数据的有效性，检查必填字段（用户编号、餐厅编号、联系人姓名、联系人电话）是否有效
     * @param {RestaurantOrder} order - 餐厅订单对象
     * @return {boolean} 返回是否验证通过（true-通过，false-不通过）
     */
    // 重写接口中的validateOrderData方法
    @Override
    // 验证订单数据方法，接收餐厅订单对象参数，返回是否验证通过
    public boolean validateOrderData(RestaurantOrder order) {
        // 如果订单对象为空，返回false
        if (order == null) {
            return false;
        }
        
        // 基础字段验证
        // 如果用户编号为空或小于等于0，返回false
        if (order.getUserId() == null || order.getUserId() <= 0) {
            return false;
        }
        
        // 如果餐厅编号为空或小于等于0，返回false
        if (order.getRestaurantId() == null || order.getRestaurantId() <= 0) {
            return false;
        }
        
        // 外卖订单验证：只需要联系人信息，不需要预订日期和人数
        // 如果联系人姓名为空或去除空格后为空字符串，返回false
        if (order.getContactName() == null || order.getContactName().trim().isEmpty()) {
            return false;
        }
        
        // 如果联系人电话为空或去除空格后为空字符串，返回false
        if (order.getContactPhone() == null || order.getContactPhone().trim().isEmpty()) {
            return false;
        }
        
        // 外卖订单不需要验证 bookingDate 和 peopleCount（已设置默认值）
        // 所有验证通过，返回true
        return true;
    }

    /**
     * 验证评价数据
     * 功能概述：验证餐厅评价数据的有效性，检查必填字段（餐厅编号、用户编号、评分、评价内容）是否有效，评分范围是否在0-5之间
     * @param {RestaurantReview} review - 餐厅评价对象
     * @return {boolean} 返回是否验证通过（true-通过，false-不通过）
     */
    // 重写接口中的validateReviewData方法
    @Override
    // 验证评价数据方法，接收餐厅评价对象参数，返回是否验证通过
    public boolean validateReviewData(RestaurantReview review) {
        // 如果评价对象为空，返回false
        if (review == null) {
            return false;
        }
        
        // 如果餐厅编号为空或小于等于0，返回false
        if (review.getRestaurantId() == null || review.getRestaurantId() <= 0) {
            return false;
        }
        
        // 如果用户编号为空或小于等于0，返回false
        if (review.getUserId() == null || review.getUserId() <= 0) {
            return false;
        }
        
        // 如果评分为空或小于等于0或大于5，返回false
        if (review.getRating() == null || review.getRating().compareTo(BigDecimal.ZERO) <= 0 || 
            review.getRating().compareTo(BigDecimal.valueOf(5)) > 0) {
            return false;
        }
        
        // 如果评价内容为空或去除空格后为空字符串，返回false
        if (review.getContent() == null || review.getContent().trim().isEmpty()) {
            return false;
        }
        
        // 所有验证通过，返回true
        return true;
    }

    /**
     * 获取餐厅菜单
     * 功能概述：调用Mapper层方法，根据餐厅编号查询该餐厅的所有可用菜单信息
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<RestaurantMenu>} 返回该餐厅的所有菜单列表
     */
    // 重写接口中的getRestaurantMenu方法
    @Override
    // 获取餐厅菜单方法，接收餐厅编号参数，返回该餐厅的所有菜单列表
    public List<RestaurantMenu> getRestaurantMenu(Long restaurantId) {
        // 调用Mapper层的findMenuByRestaurantId方法，根据餐厅编号查询该餐厅的所有菜单信息并返回
        return restaurantMapper.findMenuByRestaurantId(restaurantId);
    }

    /**
     * 根据菜系获取餐厅菜单
     * 功能概述：调用Mapper层方法，根据餐厅编号和菜系类型查询该餐厅指定菜系下的所有可用菜单信息
     * @param {Long} restaurantId - 餐厅编号
     * @param {String} category - 菜系类型
     * @return {List<RestaurantMenu>} 返回该餐厅指定菜系下的所有菜单列表
     */
    // 重写接口中的getRestaurantMenuByCategory方法
    @Override
    // 根据菜系获取餐厅菜单方法，接收餐厅编号和菜系类型参数，返回该餐厅指定菜系下的所有菜单列表
    public List<RestaurantMenu> getRestaurantMenuByCategory(Long restaurantId, String category) {
        // 调用Mapper层的findMenuByRestaurantIdAndCategory方法，根据餐厅编号和菜系类型查询该餐厅指定菜系下的所有菜单信息并返回
        return restaurantMapper.findMenuByRestaurantIdAndCategory(restaurantId, category);
    }

    /**
     * 获取餐厅菜单菜系类型
     * 功能概述：调用Mapper层方法，根据餐厅编号查询该餐厅菜单的所有不重复的菜系类型名称
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<String>} 返回该餐厅菜单的所有菜系类型名称列表
     */
    // 重写接口中的getRestaurantMenuCategories方法
    @Override
    // 获取餐厅菜单菜系类型方法，接收餐厅编号参数，返回该餐厅菜单的所有菜系类型名称列表
    public List<String> getRestaurantMenuCategories(Long restaurantId) {
        // 调用Mapper层的findMenuCategoriesByRestaurantId方法，根据餐厅编号查询该餐厅菜单的所有菜系类型名称并返回
        return restaurantMapper.findMenuCategoriesByRestaurantId(restaurantId);
    }

    /**
     * 获取餐厅招牌菜
     * 功能概述：调用Mapper层方法，根据餐厅编号查询该餐厅的所有招牌菜信息
     * @param {Long} restaurantId - 餐厅编号
     * @return {List<RestaurantMenu>} 返回该餐厅的所有招牌菜列表
     */
    // 重写接口中的getRestaurantSignatureMenu方法
    @Override
    // 获取餐厅招牌菜方法，接收餐厅编号参数，返回该餐厅的所有招牌菜列表
    public List<RestaurantMenu> getRestaurantSignatureMenu(Long restaurantId) {
        // 调用Mapper层的findSignatureMenuByRestaurantId方法，根据餐厅编号查询该餐厅的所有招牌菜信息并返回
        return restaurantMapper.findSignatureMenuByRestaurantId(restaurantId);
    }
}
