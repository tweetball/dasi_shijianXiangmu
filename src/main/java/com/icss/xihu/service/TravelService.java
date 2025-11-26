// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入旅游目的地实体类
import com.icss.xihu.model.TravelDestination;
// 导入旅游推荐实体类
import com.icss.xihu.model.TravelRecommendation;
// 导入旅游酒店实体类
import com.icss.xihu.model.TravelHotel;

// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 旅游服务接口
 * 功能概述：定义旅游相关的业务逻辑方法，包括目的地查询、推荐信息查询、景点查询、酒店查询等
 */
// 旅游服务接口，定义旅游相关的业务逻辑方法
public interface TravelService {

    /**
     * 获取所有省份
     * 功能概述：获取所有旅游目的地的省份列表
     * @return 省份列表
     */
    // 获取所有省份方法，返回省份列表
    List<TravelDestination> getAllProvinces();

    /**
     * 根据省份编码获取城市列表
     * 功能概述：根据省份编码查询该省份下的城市列表
     * @param provinceCode 省份编码
     * @return 城市列表
     */
    // 根据省份编码获取城市列表方法，接收省份编码参数
    List<TravelDestination> getCitiesByProvinceCode(String provinceCode);

    /**
     * 根据编码获取目的地信息
     * 功能概述：根据目的地编码查询目的地详细信息
     * @param code 目的地编码
     * @return 目的地对象，不存在返回null
     */
    // 根据编码获取目的地信息方法，接收目的地编码参数
    TravelDestination getDestinationByCode(String code);

    /**
     * 根据目的地编码获取完整的旅游推荐信息
     * 功能概述：根据目的地编码获取完整的旅游推荐信息，包括景点、美食、交通等各类推荐
     * @param destinationCode 目的地编码
     * @return 旅游推荐信息Map（包含景点、美食、交通等）
     */
    // 根据目的地编码获取完整的旅游推荐信息方法，接收目的地编码参数
    Map<String, Object> getTravelInfoByDestinationCode(String destinationCode);

    /**
     * 根据目的地编码获取推荐信息
     * 功能概述：根据目的地编码查询该目的地的所有推荐信息列表
     * @param destinationCode 目的地编码
     * @return 推荐信息列表
     */
    // 根据目的地编码获取推荐信息方法，接收目的地编码参数
    List<TravelRecommendation> getRecommendationsByDestinationCode(String destinationCode);

    /**
     * 根据目的地编码和分类获取推荐信息
     * 功能概述：根据目的地编码和分类查询该目的地的推荐信息列表
     * @param destinationCode 目的地编码
     * @param category 推荐分类（景点、美食、交通等）
     * @return 推荐信息列表
     */
    // 根据目的地编码和分类获取推荐信息方法，接收目的地编码和分类参数
    List<TravelRecommendation> getRecommendationsByDestinationAndCategory(String destinationCode, String category);

    /**
     * 获取所有酒店列表
     * 功能概述：获取所有旅游酒店列表
     * @return 酒店列表
     */
    // 获取所有酒店列表方法，返回酒店列表
    List<TravelHotel> getAllHotels();

    /**
     * 获取推荐酒店（限制数量）
     * 功能概述：获取推荐酒店列表，限制返回数量
     * @param limit 限制数量
     * @return 酒店列表
     */
    // 获取推荐酒店方法，接收限制数量参数
    List<TravelHotel> getTopHotels(int limit);

    /**
     * 获取省份对应的地图数据（用于ECharts）
     * 功能概述：获取省份对应的地图数据，用于ECharts地图可视化
     * @return 地图数据Map
     */
    // 获取省份对应的地图数据方法，返回地图数据Map
    Map<String, Object> getProvinceMapData();

    // 基于attractions表的地区查询方法
    /**
     * 从attractions表获取所有有景点的省份
     */
    List<String> getProvincesFromAddress();

    /**
     * 从attractions表根据省份获取城市列表
     */
    List<String> getCitiesFromAddressByProvince(String province);

    /**
     * 根据省份和城市获取完整的旅游信息（结合travel_destinations和address表）
     */
    Map<String, Object> getTravelInfoByLocation(String province, String city);

    /**
     * 根据省份获取该省份的所有景点信息
     */
    Map<String, Object> getAttractionsByProvince(String province);

    /**
     * 根据省份和城市获取景点信息
     */
    Map<String, Object> getAttractionsByProvinceAndCity(String province, String city);

    /**
     * 模糊搜索景点 - 根据关键词搜索
     */
    Map<String, Object> searchAttractionsByKeyword(String keyword);

    /**
     * 模糊搜索景点 - 根据关键词和地区搜索
     */
    Map<String, Object> searchAttractionsByKeywordAndLocation(String keyword, String province, String city);

    /**
     * 获取所有景点数据（用于默认显示）
     */
    Map<String, Object> getAllAttractions(Integer limit);

    /**
     * 当选择"所有省份"时，获取所有景点数据
     */
    Map<String, Object> getAllProvincesAttractions(Integer limit);

    /**
     * 当选择某个省份但选择"所有城市"时，获取该省份下所有景点
     */
    Map<String, Object> getProvinceAllCitiesAttractions(String province, Integer limit);

    /**
     * 根据景点ID获取景点详细信息
     */
    Map<String, Object> getAttractionById(Integer id);

    /**
     * 根据景点名称获取景点详细信息
     */
    Map<String, Object> getAttractionByName(String name);
} 