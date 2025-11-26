/**
 * 旅游服务实现类
 * 功能概述：实现TravelService接口，提供旅游相关的业务逻辑实现，包括目的地查询、推荐信息查询、景点查询、酒店查询等
 */
// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入旅游Mapper接口
import com.icss.xihu.mapper.TravelMapper;
// 导入旅游目的地实体类
import com.icss.xihu.model.TravelDestination;
// 导入旅游推荐实体类
import com.icss.xihu.model.TravelRecommendation;
// 导入旅游酒店实体类
import com.icss.xihu.model.TravelHotel;
// 导入旅游服务接口
import com.icss.xihu.service.TravelService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解，标识该类为服务层组件
import org.springframework.stereotype.Service;

// 导入ArrayList类，用于创建列表
import java.util.ArrayList;
// 导入Collections类，用于集合操作
import java.util.Collections;
// 导入HashMap类，用于创建Map对象
import java.util.HashMap;
// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;
// 导入Collectors类，用于Stream API的收集操作
import java.util.stream.Collectors;

/**
 * 旅游服务实现类
 * 功能概述：实现TravelService接口，提供旅游相关的业务逻辑实现，包括目的地查询、推荐信息查询、景点查询、酒店查询等
 */
// 使用Spring的服务注解，标识该类为服务层组件，Spring容器会自动扫描并注册为Bean
@Service
// 旅游服务实现类，实现TravelService接口
public class TravelServiceImpl implements TravelService {

    // 自动注入旅游Mapper，Spring容器会自动查找并注入TravelMapper的实现类
    @Autowired
    // 旅游Mapper对象，用于调用数据库操作方法
    private TravelMapper travelMapper;

    /**
     * 获取所有省份
     * 功能概述：调用Mapper层方法，查询所有旅游目的地的省份列表
     * @return {List<TravelDestination>} 返回所有省份的列表
     */
    // 重写接口中的getAllProvinces方法
    @Override
    // 获取所有省份方法，返回所有省份的列表
    public List<TravelDestination> getAllProvinces() {
        // 调用Mapper层的getAllProvinces方法，查询所有省份信息并返回
        return travelMapper.getAllProvinces();
    }

    /**
     * 根据省份编码获取城市列表
     * 功能概述：调用Mapper层方法，根据省份编码查询该省份下的所有城市列表
     * @param {String} provinceCode - 省份编码
     * @return {List<TravelDestination>} 返回该省份下的所有城市列表
     */
    // 重写接口中的getCitiesByProvinceCode方法
    @Override
    // 根据省份编码获取城市列表方法，接收省份编码参数，返回该省份下的所有城市列表
    public List<TravelDestination> getCitiesByProvinceCode(String provinceCode) {
        // 调用Mapper层的getCitiesByProvinceCode方法，根据省份编码查询该省份下的所有城市信息并返回
        return travelMapper.getCitiesByProvinceCode(provinceCode);
    }

    /**
     * 根据编码获取目的地信息
     * 功能概述：调用Mapper层方法，根据目的地编码查询指定目的地的详细信息
     * @param {String} code - 目的地编码
     * @return {TravelDestination} 返回目的地对象，如果不存在则返回null
     */
    // 重写接口中的getDestinationByCode方法
    @Override
    // 根据编码获取目的地信息方法，接收目的地编码参数，返回目的地对象
    public TravelDestination getDestinationByCode(String code) {
        // 调用Mapper层的getDestinationByCode方法，根据目的地编码查询目的地信息并返回
        return travelMapper.getDestinationByCode(code);
    }

    /**
     * 根据目的地编码获取完整的旅游推荐信息
     * 功能概述：根据目的地编码获取完整的旅游推荐信息，包括目的地信息、推荐信息（按分类分组）、酒店信息
     * @param {String} destinationCode - 目的地编码
     * @return {Map<String, Object>} 返回旅游推荐信息Map（包含目的地、景点、美食、交通、购物、酒店等）
     */
    // 重写接口中的getTravelInfoByDestinationCode方法
    @Override
    // 根据目的地编码获取完整的旅游推荐信息方法，接收目的地编码参数，返回旅游推荐信息Map
    public Map<String, Object> getTravelInfoByDestinationCode(String destinationCode) {
        // 创建结果Map对象，用于存储旅游推荐信息
        Map<String, Object> result = new HashMap<>();
        
        // 获取目的地信息
        // 调用Mapper层的getDestinationByCode方法，根据目的地编码查询目的地信息
        TravelDestination destination = travelMapper.getDestinationByCode(destinationCode);
        // 将目的地信息放入结果Map中，键为"destination"
        result.put("destination", destination);
        
        // 获取所有推荐信息
        // 调用Mapper层的getRecommendationsByDestinationCode方法，根据目的地编码查询该目的地的所有推荐信息
        List<TravelRecommendation> allRecommendations = travelMapper.getRecommendationsByDestinationCode(destinationCode);
        
        // 按分类分组
        // 使用Stream API，按推荐分类对推荐信息进行分组
        Map<String, List<TravelRecommendation>> recommendationsByCategory = allRecommendations.stream()
                .collect(Collectors.groupingBy(TravelRecommendation::getCategory));
        
        // 将景点推荐信息放入结果Map中，键为"attractions"，如果不存在则使用空列表
        result.put("attractions", recommendationsByCategory.getOrDefault("景点", Collections.emptyList()));
        // 将美食推荐信息放入结果Map中，键为"foods"，如果不存在则使用空列表
        result.put("foods", recommendationsByCategory.getOrDefault("美食", Collections.emptyList()));
        // 将交通推荐信息放入结果Map中，键为"transportation"，如果不存在则使用空列表
        result.put("transportation", recommendationsByCategory.getOrDefault("交通", Collections.emptyList()));
        // 将购物推荐信息放入结果Map中，键为"shopping"，如果不存在则使用空列表
        result.put("shopping", recommendationsByCategory.getOrDefault("购物", Collections.emptyList()));
        
        // 获取推荐酒店
        // 调用Mapper层的getTopHotels方法，查询推荐酒店列表，限制数量为6
        List<TravelHotel> hotels = travelMapper.getTopHotels(6);
        // 将酒店信息放入结果Map中，键为"hotels"
        result.put("hotels", hotels);
        
        // 返回结果Map
        return result;
    }

    /**
     * 根据目的地编码获取推荐信息
     * 功能概述：调用Mapper层方法，根据目的地编码查询该目的地的所有推荐信息列表
     * @param {String} destinationCode - 目的地编码
     * @return {List<TravelRecommendation>} 返回该目的地的所有推荐信息列表
     */
    // 重写接口中的getRecommendationsByDestinationCode方法
    @Override
    // 根据目的地编码获取推荐信息方法，接收目的地编码参数，返回该目的地的所有推荐信息列表
    public List<TravelRecommendation> getRecommendationsByDestinationCode(String destinationCode) {
        // 调用Mapper层的getRecommendationsByDestinationCode方法，根据目的地编码查询该目的地的所有推荐信息并返回
        return travelMapper.getRecommendationsByDestinationCode(destinationCode);
    }

    /**
     * 根据目的地编码和分类获取推荐信息
     * 功能概述：调用Mapper层方法，根据目的地编码和分类查询该目的地下指定分类的推荐信息列表
     * @param {String} destinationCode - 目的地编码
     * @param {String} category - 推荐分类（景点、美食、交通等）
     * @return {List<TravelRecommendation>} 返回该目的地下指定分类的推荐信息列表
     */
    // 重写接口中的getRecommendationsByDestinationAndCategory方法
    @Override
    // 根据目的地编码和分类获取推荐信息方法，接收目的地编码和分类参数，返回该目的地下指定分类的推荐信息列表
    public List<TravelRecommendation> getRecommendationsByDestinationAndCategory(String destinationCode, String category) {
        // 调用Mapper层的getRecommendationsByDestinationAndCategory方法，根据目的地编码和分类查询该目的地下指定分类的推荐信息并返回
        return travelMapper.getRecommendationsByDestinationAndCategory(destinationCode, category);
    }

    /**
     * 获取所有酒店列表
     * 功能概述：调用Mapper层方法，查询所有旅游酒店信息
     * @return {List<TravelHotel>} 返回所有酒店的列表
     */
    // 重写接口中的getAllHotels方法
    @Override
    // 获取所有酒店列表方法，返回所有酒店的列表
    public List<TravelHotel> getAllHotels() {
        // 调用Mapper层的getAllHotels方法，查询所有酒店信息并返回
        return travelMapper.getAllHotels();
    }

    /**
     * 获取推荐酒店（限制数量）
     * 功能概述：调用Mapper层方法，查询推荐酒店信息，限制返回数量
     * @param {int} limit - 限制数量
     * @return {List<TravelHotel>} 返回推荐酒店列表
     */
    // 重写接口中的getTopHotels方法
    @Override
    // 获取推荐酒店方法，接收限制数量参数，返回推荐酒店列表
    public List<TravelHotel> getTopHotels(int limit) {
        // 调用Mapper层的getTopHotels方法，查询推荐酒店信息，限制返回数量，并返回
        return travelMapper.getTopHotels(limit);
    }

    /**
     * 获取省份对应的地图数据（用于ECharts）
     * 功能概述：调用Mapper层方法，查询所有省份信息，转换为ECharts所需的数据格式
     * @return {Map<String, Object>} 返回地图数据Map（包含success、provinces、total等）
     */
    // 重写接口中的getProvinceMapData方法
    @Override
    // 获取省份对应的地图数据方法，返回地图数据Map
    public Map<String, Object> getProvinceMapData() {
        // 使用try-catch块捕获异常
        try {
            // 调用Mapper层的getAllProvinces方法，查询所有省份信息
            List<TravelDestination> provinces = travelMapper.getAllProvinces();
            // 创建结果Map对象，用于存储地图数据
            Map<String, Object> result = new HashMap<>();
            
            // 转换为ECharts所需的数据格式
            // 使用Stream API，将省份信息转换为ECharts所需的数据格式
            List<Map<String, Object>> mapData = provinces.stream().map(province -> {
                // 创建省份数据项Map对象
                Map<String, Object> item = new HashMap<>();
                // 将省份名称放入数据项Map中，键为"name"
                item.put("name", province.getName());
                // 将省份编码放入数据项Map中，键为"code"
                item.put("code", province.getCode());
                // 将省份描述放入数据项Map中，键为"description"
                item.put("description", province.getDescription());
                // 将省份值放入数据项Map中，键为"value"，可以根据需要设置不同的值
                item.put("value", 1); // 可以根据需要设置不同的值
                // 返回数据项Map
                return item;
            }).collect(Collectors.toList());  // 收集为列表
            
            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将省份数据放入结果Map中，键为"provinces"
            result.put("provinces", mapData);
            // 将省份总数放入结果Map中，键为"total"
            result.put("total", provinces.size());
            
            // 返回结果Map
            return result;
        } catch (Exception e) {
            // 如果发生异常，创建错误Map对象
            Map<String, Object> error = new HashMap<>();
            // 将成功标识放入错误Map中，键为"success"，值为false
            error.put("success", false);
            // 将错误信息放入错误Map中，键为"message"
            error.put("message", "获取省份数据失败：" + e.getMessage());
            // 将空列表放入错误Map中，键为"provinces"
            error.put("provinces", new ArrayList<>());
            // 将总数为0放入错误Map中，键为"total"
            error.put("total", 0);
            // 返回错误Map
            return error;
        }
    }

    /**
     * 从attractions表获取所有有景点的省份
     * 功能概述：调用Mapper层方法，从景点表中查询所有不重复的省份名称
     * @return {List<String>} 返回所有省份名称的列表
     */
    // 重写接口中的getProvincesFromAddress方法
    @Override
    // 从address表获取所有省份方法，返回所有省份名称的列表
    public List<String> getProvincesFromAddress() {
        // 调用Mapper层的getProvincesFromAddress方法，从address表中查询所有不重复的省份名称并返回
        return travelMapper.getProvincesFromAddress();
    }

    /**
     * 从address表根据省份获取城市列表
     * 功能概述：调用Mapper层方法，根据省份名称从address表中查询该省份下的所有不重复的城市名称
     * @param {String} province - 省份名称
     * @return {List<String>} 返回该省份下的所有城市名称列表
     */
    // 重写接口中的getCitiesFromAddressByProvince方法
    @Override
    // 从address表根据省份获取城市列表方法，接收省份名称参数，返回该省份下的所有城市名称列表
    public List<String> getCitiesFromAddressByProvince(String province) {
        // 调用Mapper层的getCitiesFromAddressByProvince方法，根据省份名称从address表中查询该省份下的所有不重复的城市名称并返回
        return travelMapper.getCitiesFromAddressByProvince(province);
    }

    /**
     * 根据省份和城市获取完整的旅游信息（结合travel_destinations和address表）
     * 功能概述：根据省份和城市名称获取完整的旅游信息，包括目的地信息、推荐信息（按分类分组）、景点信息、酒店信息
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {Map<String, Object>} 返回旅游信息Map（包含目的地、景点、美食、交通、购物、酒店等）
     */
    // 重写接口中的getTravelInfoByLocation方法
    @Override
    // 根据省份和城市获取完整的旅游信息方法，接收省份和城市名称参数，返回旅游信息Map
    public Map<String, Object> getTravelInfoByLocation(String province, String city) {
        // 创建结果Map对象，用于存储旅游信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 1. 获取目的地信息（优先从city查找，如果没有则从province查找）
            // 定义目的地对象变量
            TravelDestination destination = null;
            // 如果城市名称不为空，优先从城市名称查找目的地信息
            if (city != null && !city.isEmpty()) {
                // 调用Mapper层的getDestinationByCityName方法，根据城市名称查询目的地信息
                destination = travelMapper.getDestinationByCityName(city);
            }
            // 如果目的地为空且省份名称不为空，从省份名称查找目的地信息
            if (destination == null && province != null) {
                // 调用Mapper层的getDestinationByProvinceName方法，根据省份名称查询目的地信息
                destination = travelMapper.getDestinationByProvinceName(province);
            }
            
            // 将目的地信息放入结果Map中，键为"destination"
            result.put("destination", destination);

            // 2. 获取travel_recommendations表中的推荐信息
            // 创建推荐信息列表
            List<TravelRecommendation> recommendations = new ArrayList<>();
            // 如果目的地不为空，根据目的地编码查询推荐信息
            if (destination != null) {
                // 调用Mapper层的getRecommendationsByDestinationCode方法，根据目的地编码查询该目的地的所有推荐信息
                recommendations = travelMapper.getRecommendationsByDestinationCode(destination.getCode());
            }

            // 按分类分组推荐信息
            // 使用Stream API，按推荐分类对推荐信息进行分组
            Map<String, List<TravelRecommendation>> recommendationsByCategory = recommendations.stream()
                    .collect(Collectors.groupingBy(TravelRecommendation::getCategory));

            // 将景点推荐信息放入结果Map中，键为"attractions"，如果不存在则使用空列表
            result.put("attractions", recommendationsByCategory.getOrDefault("景点", new ArrayList<>()));
            // 将美食推荐信息放入结果Map中，键为"foods"，如果不存在则使用空列表
            result.put("foods", recommendationsByCategory.getOrDefault("美食", new ArrayList<>()));
            // 将交通推荐信息放入结果Map中，键为"transportation"，如果不存在则使用空列表
            result.put("transportation", recommendationsByCategory.getOrDefault("交通", new ArrayList<>()));
            // 将购物推荐信息放入结果Map中，键为"shopping"，如果不存在则使用空列表
            result.put("shopping", recommendationsByCategory.getOrDefault("购物", new ArrayList<>()));

            // 3. 获取address表中的景点信息
            // 调用Mapper层的getAttractionsFromAddress方法，根据省份和城市名称从address表中查询该地区的景点信息
            List<Map<String, Object>> addressAttractions = travelMapper.getAttractionsFromAddress(province, city);
            // 将景点信息放入结果Map中，键为"addressAttractions"
            result.put("addressAttractions", addressAttractions);

            // 4. 获取酒店信息
            // 创建酒店列表
            List<TravelHotel> hotels = new ArrayList<>();
            // 如果目的地不为空，根据目的地编码查询酒店信息
            if (destination != null) {
                // 调用Mapper层的getHotelsByDestinationCode方法，根据目的地编码查询该目的地的所有酒店信息
                hotels = travelMapper.getHotelsByDestinationCode(destination.getCode());
            }
            // 如果酒店列表为空，查询推荐酒店列表
            if (hotels.isEmpty()) {
                // 调用Mapper层的getTopHotels方法，查询推荐酒店列表，限制数量为6
                hotels = travelMapper.getTopHotels(6);
            }
            // 将酒店信息放入结果Map中，键为"hotels"
            result.put("hotels", hotels);

            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将成功信息放入结果Map中，键为"message"
            result.put("message", "获取旅游信息成功");

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取旅游信息失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将空列表放入结果Map中，键为"addressAttractions"
            result.put("addressAttractions", new ArrayList<>());
            // 将空列表放入结果Map中，键为"hotels"
            result.put("hotels", new ArrayList<>());
        }

        // 返回结果Map
        return result;
    }

    /**
     * 根据省份获取该省份的所有景点信息
     * 功能概述：根据省份名称获取该省份的所有景点信息，包括省份信息、城市列表、景点信息、推荐信息
     * @param {String} province - 省份名称
     * @return {Map<String, Object>} 返回景点信息Map（包含省份、城市、景点、推荐等）
     */
    // 重写接口中的getAttractionsByProvince方法
    @Override
    // 根据省份获取该省份的所有景点信息方法，接收省份名称参数，返回景点信息Map
    public Map<String, Object> getAttractionsByProvince(String province) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 1. 获取省份信息
            // 调用Mapper层的getDestinationByProvinceName方法，根据省份名称查询省份信息
            TravelDestination provinceDestination = travelMapper.getDestinationByProvinceName(province);
            // 将省份信息放入结果Map中，键为"province"
            result.put("province", provinceDestination);

            // 2. 获取该省份下的所有城市
            // 调用Mapper层的getCitiesFromAddressByProvince方法，根据省份名称从景点表中查询该省份下的所有不重复的城市名称
            List<String> cities = travelMapper.getCitiesFromAddressByProvince(province);
            // 将城市列表放入结果Map中，键为"cities"
            result.put("cities", cities);

            // 3. 获取该省份的所有景点（从address表）
            // 调用Mapper层的getAttractionsFromAddress方法，根据省份名称从address表中查询该省份的所有景点信息（城市为null）
            List<Map<String, Object>> attractions = travelMapper.getAttractionsFromAddress(province, null);
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);

            // 4. 获取travel_recommendations表中的推荐（如果有的话）
            // 创建推荐信息列表
            List<TravelRecommendation> recommendations = new ArrayList<>();
            // 如果省份信息不为空，根据省份编码和分类查询推荐信息
            if (provinceDestination != null) {
                // 调用Mapper层的getRecommendationsByDestinationAndCategory方法，根据省份编码和分类查询该省份的景点推荐信息
                recommendations = travelMapper.getRecommendationsByDestinationAndCategory(
                    provinceDestination.getCode(), "景点");
            }
            // 将推荐信息放入结果Map中，键为"recommendations"
            result.put("recommendations", recommendations);

            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取省份景点失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 根据省份和城市获取景点信息
     * 功能概述：根据省份和城市名称获取该地区的景点信息，包括城市信息、景点信息、推荐信息
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {Map<String, Object>} 返回景点信息Map（包含城市、景点、推荐等）
     */
    // 重写接口中的getAttractionsByProvinceAndCity方法
    @Override
    // 根据省份和城市获取景点信息方法，接收省份和城市名称参数，返回景点信息Map
    public Map<String, Object> getAttractionsByProvinceAndCity(String province, String city) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 1. 获取城市信息
            // 调用Mapper层的getDestinationByCityName方法，根据城市名称查询城市信息
            TravelDestination cityDestination = travelMapper.getDestinationByCityName(city);
            // 将城市信息放入结果Map中，键为"city"
            result.put("city", cityDestination);

            // 2. 获取该城市的景点（从address表）
            // 调用Mapper层的getAttractionsFromAddress方法，根据省份和城市名称从address表中查询该地区的景点信息
            List<Map<String, Object>> attractions = travelMapper.getAttractionsFromAddress(province, city);
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);

            // 3. 获取travel_recommendations表中的推荐
            // 创建推荐信息列表
            List<TravelRecommendation> recommendations = new ArrayList<>();
            // 如果城市信息不为空，根据省份和城市名称查询推荐信息
            if (cityDestination != null) {
                // 调用Mapper层的getAttractionsByProvinceAndCity方法，根据省份和城市名称查询该地区的推荐信息
                recommendations = travelMapper.getAttractionsByProvinceAndCity(province, city);
            }
            // 将推荐信息放入结果Map中，键为"recommendations"
            result.put("recommendations", recommendations);

            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取城市景点失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 模糊搜索景点 - 根据关键词搜索
     * 功能概述：根据关键词从数据库中搜索匹配的景点信息，支持按名称、描述、特色模糊搜索
     * @param {String} keyword - 搜索关键词
     * @return {Map<String, Object>} 返回搜索结果Map（包含success、message、attractions、total、keyword等）
     */
    // 重写接口中的searchAttractionsByKeyword方法
    @Override
    // 模糊搜索景点方法（根据关键词搜索），接收搜索关键词参数，返回搜索结果Map
    public Map<String, Object> searchAttractionsByKeyword(String keyword) {
        // 创建结果Map对象，用于存储搜索结果
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 如果关键词为空或去除空格后为空字符串，返回错误信息
            if (keyword == null || keyword.trim().isEmpty()) {
                // 将成功标识放入结果Map中，键为"success"，值为false
                result.put("success", false);
                // 将错误信息放入结果Map中，键为"message"
                result.put("message", "搜索关键词不能为空");
                // 将空列表放入结果Map中，键为"attractions"
                result.put("attractions", new ArrayList<>());
                // 将总数为0放入结果Map中，键为"total"
                result.put("total", 0);
                // 返回结果Map
                return result;
            }

            // 使用模糊搜索查询景点
            // 调用Mapper层的searchAttractionsByKeyword方法，根据关键词从数据库中搜索匹配的景点信息
            List<Map<String, Object>> attractions = travelMapper.searchAttractionsByKeyword(keyword.trim());
            
            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将成功信息放入结果Map中，键为"message"
            result.put("message", "搜索成功");
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());
            // 将搜索关键词放入结果Map中，键为"keyword"
            result.put("keyword", keyword.trim());

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "搜索失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 模糊搜索景点 - 根据关键词和地区搜索
     * 功能概述：根据关键词和地区从数据库中搜索匹配的景点信息，支持按名称、描述、特色模糊搜索，并按地区筛选
     * @param {String} keyword - 搜索关键词
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {Map<String, Object>} 返回搜索结果Map（包含success、message、attractions、total、keyword、province、city等）
     */
    // 重写接口中的searchAttractionsByKeywordAndLocation方法
    @Override
    // 模糊搜索景点方法（根据关键词和地区搜索），接收搜索关键词、省份和城市名称参数，返回搜索结果Map
    public Map<String, Object> searchAttractionsByKeywordAndLocation(String keyword, String province, String city) {
        // 创建结果Map对象，用于存储搜索结果
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 使用组合条件搜索景点
            // 调用Mapper层的searchAttractionsByKeywordAndLocation方法，根据关键词和地区从数据库中搜索匹配的景点信息
            List<Map<String, Object>> attractions = travelMapper.searchAttractionsByKeywordAndLocation(
                keyword != null ? keyword.trim() : null,  // 如果关键词不为空，去除空格，否则为null
                province,                                 // 省份名称
                city                                      // 城市名称
            );
            
            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将成功信息放入结果Map中，键为"message"
            result.put("message", "搜索成功");
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());
            // 将搜索关键词放入结果Map中，键为"keyword"（如果关键词不为空，去除空格，否则为空字符串）
            result.put("keyword", keyword != null ? keyword.trim() : "");
            // 将省份名称放入结果Map中，键为"province"
            result.put("province", province);
            // 将城市名称放入结果Map中，键为"city"
            result.put("city", city);

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "搜索失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 获取所有景点数据（用于默认显示）
     * 功能概述：从数据库中查询所有景点信息，限制返回数量，用于默认显示
     * @param {Integer} limit - 查询数量限制
     * @return {Map<String, Object>} 返回景点信息Map（包含success、message、attractions、total等）
     */
    // 重写接口中的getAllAttractions方法
    @Override
    // 获取所有景点数据方法（用于默认显示），接收查询数量限制参数，返回景点信息Map
    public Map<String, Object> getAllAttractions(Integer limit) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 获取所有景点数据
            // 调用Mapper层的getAllAttractions方法，从数据库中查询所有景点信息，限制返回数量
            List<Map<String, Object>> attractions = travelMapper.getAllAttractions(limit);
            
            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将成功信息放入结果Map中，键为"message"
            result.put("message", "获取所有景点成功");
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取所有景点失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 当选择"所有省份"时，获取所有景点数据
     * 功能概述：从数据库中查询所有省份的所有景点信息，限制返回数量，用于选择"所有省份"时的显示
     * @param {Integer} limit - 查询数量限制
     * @return {Map<String, Object>} 返回景点信息Map（包含success、message、attractions、total等）
     */
    // 重写接口中的getAllProvincesAttractions方法
    @Override
    // 当选择"所有省份"时获取所有景点数据方法，接收查询数量限制参数，返回景点信息Map
    public Map<String, Object> getAllProvincesAttractions(Integer limit) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 获取所有省份的所有景点数据
            // 调用Mapper层的getAllProvincesAttractions方法，从数据库中查询所有省份的所有景点信息，限制返回数量
            List<Map<String, Object>> attractions = travelMapper.getAllProvincesAttractions(limit);
            
            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将成功信息放入结果Map中，键为"message"
            result.put("message", "获取所有省份景点成功");
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取所有省份景点失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 当选择某个省份但选择"所有城市"时，获取该省份下所有景点
     * 功能概述：根据省份名称从数据库中查询该省份下的所有景点信息，限制返回数量，用于选择"所有城市"时的显示
     * @param {String} province - 省份名称
     * @param {Integer} limit - 查询数量限制
     * @return {Map<String, Object>} 返回景点信息Map（包含success、message、attractions、total等）
     */
    // 重写接口中的getProvinceAllCitiesAttractions方法
    @Override
    // 当选择某个省份但选择"所有城市"时获取该省份下所有景点方法，接收省份名称和查询数量限制参数，返回景点信息Map
    public Map<String, Object> getProvinceAllCitiesAttractions(String province, Integer limit) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 获取指定省份下所有城市的景点数据
            // 调用Mapper层的getProvinceAllCitiesAttractions方法，根据省份名称从数据库中查询该省份下的所有景点信息，限制返回数量
            List<Map<String, Object>> attractions = travelMapper.getProvinceAllCitiesAttractions(province, limit);
            
            // 将成功标识放入结果Map中，键为"success"，值为true
            result.put("success", true);
            // 将成功信息放入结果Map中，键为"message"（包含省份名称）
            result.put("message", "获取" + province + "所有城市景点成功");
            // 将景点信息放入结果Map中，键为"attractions"
            result.put("attractions", attractions);
            // 将景点总数放入结果Map中，键为"total"
            result.put("total", attractions.size());

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"（包含省份名称）
            result.put("message", "获取" + province + "所有城市景点失败：" + e.getMessage());
            // 将空列表放入结果Map中，键为"attractions"
            result.put("attractions", new ArrayList<>());
            // 将总数为0放入结果Map中，键为"total"
            result.put("total", 0);
        }

        // 返回结果Map
        return result;
    }

    /**
     * 根据景点ID获取景点详细信息
     * 功能概述：根据景点编号从数据库中查询指定景点的详细信息
     * @param {Integer} id - 景点编号
     * @return {Map<String, Object>} 返回景点信息Map（包含success、message、attraction等）
     */
    // 重写接口中的getAttractionById方法
    @Override
    // 根据景点ID获取景点详细信息方法，接收景点编号参数，返回景点信息Map
    public Map<String, Object> getAttractionById(Integer id) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 如果景点编号为空或小于等于0，返回错误信息
            if (id == null || id <= 0) {
                // 将成功标识放入结果Map中，键为"success"，值为false
                result.put("success", false);
                // 将错误信息放入结果Map中，键为"message"
                result.put("message", "景点ID无效");
                // 返回结果Map
                return result;
            }

            // 根据ID获取景点详细信息
            // 调用Mapper层的getAttractionById方法，根据景点编号从数据库中查询指定景点的详细信息
            Map<String, Object> attraction = travelMapper.getAttractionById(id);
            
            // 如果景点信息不为空且不为空Map，返回成功信息
            if (attraction != null && !attraction.isEmpty()) {
                // 将成功标识放入结果Map中，键为"success"，值为true
                result.put("success", true);
                // 将景点信息放入结果Map中，键为"attraction"
                result.put("attraction", attraction);
                // 将成功信息放入结果Map中，键为"message"
                result.put("message", "获取景点详情成功");
            } else {
                // 如果景点信息为空，返回错误信息
                result.put("success", false);
                // 将错误信息放入结果Map中，键为"message"（包含景点编号）
                result.put("message", "未找到ID为 " + id + " 的景点");
            }

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取景点详情失败：" + e.getMessage());
        }

        // 返回结果Map
        return result;
    }

    /**
     * 根据景点名称获取景点详细信息
     * 功能概述：根据景点名称从数据库中查询指定景点的详细信息，并补充一些默认信息（如果数据库中没有的话）
     * @param {String} name - 景点名称
     * @return {Map<String, Object>} 返回景点信息Map（包含success、message、attraction等）
     */
    // 重写接口中的getAttractionByName方法
    @Override
    // 根据景点名称获取景点详细信息方法，接收景点名称参数，返回景点信息Map
    public Map<String, Object> getAttractionByName(String name) {
        // 创建结果Map对象，用于存储景点信息
        Map<String, Object> result = new HashMap<>();
        
        // 使用try-catch块捕获异常
        try {
            // 如果景点名称为空或去除空格后为空字符串，返回错误信息
            if (name == null || name.trim().isEmpty()) {
                // 将成功标识放入结果Map中，键为"success"，值为false
                result.put("success", false);
                // 将错误信息放入结果Map中，键为"message"
                result.put("message", "景点名称不能为空");
                // 返回结果Map
                return result;
            }

            // 根据名称获取景点详细信息
            // 调用Mapper层的getAttractionByName方法，根据景点名称从数据库中查询指定景点的详细信息
            Map<String, Object> attraction = travelMapper.getAttractionByName(name.trim());
            
            // 如果景点信息不为空且不为空Map，返回成功信息并补充默认信息
            if (attraction != null && !attraction.isEmpty()) {
                // 将成功标识放入结果Map中，键为"success"，值为true
                result.put("success", true);
                // 将景点信息放入结果Map中，键为"attraction"
                result.put("attraction", attraction);
                // 将成功信息放入结果Map中，键为"message"
                result.put("message", "获取景点详情成功");
                
                // 补充一些默认信息（如果数据库中没有的话）
                // 如果景点信息中没有"view_count"字段或值为null，设置默认浏览次数（1000-6000之间的随机数）
                if (!attraction.containsKey("view_count") || attraction.get("view_count") == null) {
                    attraction.put("view_count", (int)(Math.random() * 5000) + 1000);
                }
                // 如果景点信息中没有"like_count"字段或值为null，设置默认点赞数（100-600之间的随机数）
                if (!attraction.containsKey("like_count") || attraction.get("like_count") == null) {
                    attraction.put("like_count", (int)(Math.random() * 500) + 100);
                }
                // 如果景点信息中没有"rating"字段或值为null，设置默认评分（4.0-5.0之间的随机数，保留1位小数）
                if (!attraction.containsKey("rating") || attraction.get("rating") == null) {
                    attraction.put("rating", String.format("%.1f", 4.0 + Math.random()));
                }
                // 如果景点信息中没有"price_range"字段或值为null，设置默认价格范围为"免费"
                if (!attraction.containsKey("price_range") || attraction.get("price_range") == null) {
                    attraction.put("price_range", "免费");
                }
                // 如果景点信息中没有"opening_hours"字段或值为null，设置默认开放时间为"全天开放"
                if (!attraction.containsKey("opening_hours") || attraction.get("opening_hours") == null) {
                    attraction.put("opening_hours", "全天开放");
                }
                // 如果景点信息中没有"features"字段或值为null，设置默认特色为"风景优美,值得推荐,历史悠久"
                if (!attraction.containsKey("features") || attraction.get("features") == null) {
                    attraction.put("features", "风景优美,值得推荐,历史悠久");
                }
                // 如果景点信息中没有"tips"字段或值为null，设置默认提示为"建议提前规划行程，注意安全，尊重当地文化。"
                if (!attraction.containsKey("tips") || attraction.get("tips") == null) {
                    attraction.put("tips", "建议提前规划行程，注意安全，尊重当地文化。");
                }
                
            } else {
                // 如果景点信息为空，返回错误信息
                result.put("success", false);
                // 将错误信息放入结果Map中，键为"message"（包含景点名称）
                result.put("message", "未找到名为 \"" + name + "\" 的景点");
            }

        } catch (Exception e) {
            // 如果发生异常，将成功标识放入结果Map中，键为"success"，值为false
            result.put("success", false);
            // 将错误信息放入结果Map中，键为"message"
            result.put("message", "获取景点详情失败：" + e.getMessage());
        }

        // 返回结果Map
        return result;
    }
}
