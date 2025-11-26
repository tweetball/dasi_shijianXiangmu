/**
 * 旅游模块Mapper接口
 * 功能概述：定义旅游相关的数据库操作方法，包括目的地查询、推荐查询、酒店查询、景点查询等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入旅游目的地实体类
import com.icss.xihu.model.TravelDestination;
// 导入旅游推荐实体类
import com.icss.xihu.model.TravelRecommendation;
// 导入旅游酒店实体类
import com.icss.xihu.model.TravelHotel;
// 导入MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Param注解，用于方法参数映射
import org.apache.ibatis.annotations.Param;

// 导入List集合接口
import java.util.List;
// 导入Map集合接口
import java.util.Map;

/**
 * 旅游模块Mapper接口
 * 功能概述：定义旅游相关的数据库操作方法，包括目的地查询、推荐查询、酒店查询、景点查询等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 旅游模块Mapper接口，定义旅游相关的数据库操作方法
public interface TravelMapper {

    // 目的地相关查询
    /**
     * 获取所有省份
     * 功能概述：从数据库中查询所有省份信息（级别为1的目的地）
     * @return {List<TravelDestination>} 返回所有省份的列表
     */
    // 获取所有省份方法，返回所有省份的列表（SQL在XML映射文件中定义）
    List<TravelDestination> getAllProvinces();

    /**
     * 根据省份编码获取城市列表
     * 功能概述：根据省份编码从数据库中查询该省份下的所有城市信息（级别为2的目的地）
     * @param {String} provinceCode - 省份编码
     * @return {List<TravelDestination>} 返回该省份下的所有城市列表
     */
    // 根据省份编码获取城市列表方法，接收省份编码参数，返回该省份下的所有城市列表（SQL在XML映射文件中定义）
    List<TravelDestination> getCitiesByProvinceCode(@Param("provinceCode") String provinceCode);

    /**
     * 根据编码获取目的地信息
     * 功能概述：根据目的地编码从数据库中查询指定目的地的信息
     * @param {String} code - 目的地编码
     * @return {TravelDestination} 返回目的地对象，如果不存在则返回null
     */
    // 根据编码获取目的地信息方法，接收目的地编码参数，返回目的地对象（SQL在XML映射文件中定义）
    TravelDestination getDestinationByCode(@Param("code") String code);

    // 推荐相关查询
    /**
     * 根据目的地编码获取推荐信息
     * 功能概述：根据目的地编码从数据库中查询该目的地的所有推荐信息
     * @param {String} destinationCode - 目的地编码
     * @return {List<TravelRecommendation>} 返回该目的地的所有推荐信息列表
     */
    // 根据目的地编码获取推荐信息方法，接收目的地编码参数，返回该目的地的所有推荐信息列表（SQL在XML映射文件中定义）
    List<TravelRecommendation> getRecommendationsByDestinationCode(@Param("destinationCode") String destinationCode);

    /**
     * 根据目的地编码和分类获取推荐信息
     * 功能概述：根据目的地编码和分类从数据库中查询该目的地下指定分类的推荐信息
     * @param {String} destinationCode - 目的地编码
     * @param {String} category - 推荐分类
     * @return {List<TravelRecommendation>} 返回该目的地下指定分类的推荐信息列表
     */
    // 根据目的地编码和分类获取推荐信息方法，接收目的地编码和分类参数，返回该目的地下指定分类的推荐信息列表（SQL在XML映射文件中定义）
    List<TravelRecommendation> getRecommendationsByDestinationAndCategory(
            @Param("destinationCode") String destinationCode,  // 目的地编码参数
            @Param("category") String category                // 推荐分类参数
    );

    // 酒店相关查询
    /**
     * 获取所有酒店列表
     * 功能概述：从数据库中查询所有酒店信息
     * @return {List<TravelHotel>} 返回所有酒店的列表
     */
    // 获取所有酒店列表方法，返回所有酒店的列表（SQL在XML映射文件中定义）
    List<TravelHotel> getAllHotels();

    /**
     * 获取推荐酒店（限制数量）
     * 功能概述：从数据库中查询推荐酒店信息，限制返回数量
     * @param {int} limit - 查询数量限制
     * @return {List<TravelHotel>} 返回推荐酒店列表
     */
    // 获取推荐酒店方法，接收查询数量限制参数，返回推荐酒店列表（SQL在XML映射文件中定义）
    List<TravelHotel> getTopHotels(@Param("limit") int limit);

    /**
     * 根据目的地编码获取酒店
     * 功能概述：根据目的地编码从数据库中查询该目的地的所有酒店信息
     * @param {String} destinationCode - 目的地编码
     * @return {List<TravelHotel>} 返回该目的地的所有酒店列表
     */
    // 根据目的地编码获取酒店方法，接收目的地编码参数，返回该目的地的所有酒店列表（SQL在XML映射文件中定义）
    List<TravelHotel> getHotelsByDestinationCode(@Param("destinationCode") String destinationCode);

    // 基于attractions表的地区查询方法
    /**
     * 从address表获取所有省份（去重）
     * 功能概述：从address表中查询所有不重复的省份名称（deep=0），不限制是否有景点数据
     * @return {List<String>} 返回所有省份名称的列表
     */
    // 从address表获取所有省份方法，返回所有不重复的省份名称列表（SQL在XML映射文件中定义）
    List<String> getProvincesFromAddress();

    /**
     * 从address表根据省份获取城市列表（去重）
     * 功能概述：根据省份名称从address表中查询该省份下的所有不重复的城市名称（deep=1），不限制是否有景点数据
     * @param {String} province - 省份名称
     * @return {List<String>} 返回该省份下的所有城市名称列表
     */
    // 从address表根据省份获取城市列表方法，接收省份名称参数，返回该省份下的所有不重复的城市名称列表（SQL在XML映射文件中定义）
    List<String> getCitiesFromAddressByProvince(@Param("province") String province);

    /**
     * 根据省份名称获取目的地信息（从travel_destinations表）
     * 功能概述：根据省份名称从目的地表中查询指定目的地的信息
     * @param {String} provinceName - 省份名称
     * @return {TravelDestination} 返回目的地对象，如果不存在则返回null
     */
    // 根据省份名称获取目的地信息方法，接收省份名称参数，返回目的地对象（SQL在XML映射文件中定义）
    TravelDestination getDestinationByProvinceName(@Param("provinceName") String provinceName);

    /**
     * 根据城市名称获取目的地信息（从travel_destinations表）
     * 功能概述：根据城市名称从目的地表中查询指定目的地的信息
     * @param {String} cityName - 城市名称
     * @return {TravelDestination} 返回目的地对象，如果不存在则返回null
     */
    // 根据城市名称获取目的地信息方法，接收城市名称参数，返回目的地对象（SQL在XML映射文件中定义）
    TravelDestination getDestinationByCityName(@Param("cityName") String cityName);

    /**
     * 根据省份和城市名称获取景点推荐（关联address表）
     * 功能概述：根据省份和城市名称从数据库中查询该地区的景点推荐信息，关联address表
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {List<TravelRecommendation>} 返回该地区的景点推荐列表
     */
    // 根据省份和城市名称获取景点推荐方法，接收省份和城市名称参数，返回该地区的景点推荐列表（SQL在XML映射文件中定义）
    List<TravelRecommendation> getAttractionsByProvinceAndCity(
            @Param("province") String province,  // 省份名称参数
            @Param("city") String city            // 城市名称参数
    );

    /**
     * 根据地区获取address表中的景点
     * 功能概述：根据省份和城市名称从address表中查询该地区的景点信息
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {List<Map<String, Object>>} 返回该地区的景点信息列表（Map格式）
     */
    // 根据地区获取address表中的景点方法，接收省份和城市名称参数，返回该地区的景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> getAttractionsFromAddress(
            @Param("province") String province,  // 省份名称参数
            @Param("city") String city            // 城市名称参数
    );

    /**
     * 从attractions表根据地区获取景点
     * 功能概述：根据省份和城市名称从景点表中查询该地区的景点信息
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {List<Map<String, Object>>} 返回该地区的景点信息列表（Map格式）
     */
    // 从attractions表根据地区获取景点方法，接收省份和城市名称参数，返回该地区的景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> getAttractionsByLocation(
            @Param("province") String province,  // 省份名称参数
            @Param("city") String city            // 城市名称参数
    );

    /**
     * 模糊搜索景点 - 根据关键词搜索景点名称、描述、特色
     * 功能概述：根据关键词从数据库中搜索匹配的景点信息，支持按名称、描述、特色模糊搜索
     * @param {String} keyword - 搜索关键词
     * @return {List<Map<String, Object>>} 返回匹配的景点信息列表（Map格式）
     */
    // 模糊搜索景点方法，接收搜索关键词参数，返回匹配的景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> searchAttractionsByKeyword(@Param("keyword") String keyword);

    /**
     * 模糊搜索景点 - 根据关键词和地区搜索
     * 功能概述：根据关键词和地区从数据库中搜索匹配的景点信息，支持按名称、描述、特色模糊搜索，并按地区筛选
     * @param {String} keyword - 搜索关键词
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {List<Map<String, Object>>} 返回匹配的景点信息列表（Map格式）
     */
    // 模糊搜索景点方法（根据关键词和地区），接收搜索关键词、省份和城市名称参数，返回匹配的景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> searchAttractionsByKeywordAndLocation(
            @Param("keyword") String keyword,    // 搜索关键词参数
            @Param("province") String province,  // 省份名称参数
            @Param("city") String city            // 城市名称参数
    );

    /**
     * 获取所有景点数据（用于默认显示）
     * 功能概述：从数据库中查询所有景点信息，限制返回数量，用于默认显示
     * @param {Integer} limit - 查询数量限制
     * @return {List<Map<String, Object>>} 返回景点信息列表（Map格式）
     */
    // 获取所有景点数据方法，接收查询数量限制参数，返回景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> getAllAttractions(@Param("limit") Integer limit);

    /**
     * 当选择"所有省份"时，获取所有景点数据
     * 功能概述：从数据库中查询所有景点信息，限制返回数量，用于选择"所有省份"时的显示
     * @param {Integer} limit - 查询数量限制
     * @return {List<Map<String, Object>>} 返回景点信息列表（Map格式）
     */
    // 当选择"所有省份"时获取所有景点数据方法，接收查询数量限制参数，返回景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> getAllProvincesAttractions(@Param("limit") Integer limit);

    /**
     * 当选择某个省份但选择"所有城市"时，获取该省份下所有景点
     * 功能概述：根据省份名称从数据库中查询该省份下的所有景点信息，限制返回数量，用于选择"所有城市"时的显示
     * @param {String} province - 省份名称
     * @param {Integer} limit - 查询数量限制
     * @return {List<Map<String, Object>>} 返回该省份下的所有景点信息列表（Map格式）
     */
    // 当选择某个省份但选择"所有城市"时获取该省份下所有景点方法，接收省份名称和查询数量限制参数，返回该省份下的所有景点信息列表（SQL在XML映射文件中定义）
    List<Map<String, Object>> getProvinceAllCitiesAttractions(@Param("province") String province, @Param("limit") Integer limit);

    // 数据插入方法（用于后台管理）
    /**
     * 插入目的地信息
     * 功能概述：向数据库中插入一条目的地信息记录
     * @param {TravelDestination} destination - 目的地对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 插入目的地信息方法，接收目的地对象参数，返回插入的记录数（SQL在XML映射文件中定义）
    int insertDestination(TravelDestination destination);

    /**
     * 插入推荐信息
     * 功能概述：向数据库中插入一条推荐信息记录
     * @param {TravelRecommendation} recommendation - 推荐对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 插入推荐信息方法，接收推荐对象参数，返回插入的记录数（SQL在XML映射文件中定义）
    int insertRecommendation(TravelRecommendation recommendation);

    /**
     * 插入酒店信息
     * 功能概述：向数据库中插入一条酒店信息记录
     * @param {TravelHotel} hotel - 酒店对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 插入酒店信息方法，接收酒店对象参数，返回插入的记录数（SQL在XML映射文件中定义）
    int insertHotel(TravelHotel hotel);

    /**
     * 根据景点ID获取景点详细信息
     * 功能概述：根据景点编号从数据库中查询指定景点的详细信息
     * @param {Integer} id - 景点编号
     * @return {Map<String, Object>} 返回景点详细信息（Map格式），如果不存在则返回null
     */
    // 根据景点ID获取景点详细信息方法，接收景点编号参数，返回景点详细信息（SQL在XML映射文件中定义）
    Map<String, Object> getAttractionById(@Param("id") Integer id);

    /**
     * 根据景点名称获取景点详细信息
     * 功能概述：根据景点名称从数据库中查询指定景点的详细信息
     * @param {String} name - 景点名称
     * @return {Map<String, Object>} 返回景点详细信息（Map格式），如果不存在则返回null
     */
    // 根据景点名称获取景点详细信息方法，接收景点名称参数，返回景点详细信息（SQL在XML映射文件中定义）
    Map<String, Object> getAttractionByName(@Param("name") String name);
}
