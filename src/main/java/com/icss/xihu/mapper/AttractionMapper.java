/**
 * 景点Mapper接口
 * 功能概述：定义景点相关的数据库操作方法，支持多表连查，包括景点查询、分类查询、推荐查询等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入景点相关的实体类
import com.icss.xihu.model.*;
// 导入MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Param注解，用于方法参数映射
import org.apache.ibatis.annotations.Param;

// 导入List集合接口
import java.util.List;

/**
 * 景点Mapper接口 - 支持多表连查
 * 功能概述：定义景点相关的数据库操作方法，支持多表连查，包括景点查询、分类查询、推荐查询等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 景点Mapper接口，支持多表连查
public interface AttractionMapper {

    // 基础查询
    /**
     * 获取所有景点分类
     * 功能概述：从数据库中查询所有景点分类信息
     * @return {List<AttractionCategory>} 返回所有景点分类的列表
     */
    // 获取所有景点分类方法，返回所有景点分类的列表（SQL在XML映射文件中定义）
    List<AttractionCategory> getAllCategories();

    /**
     * 获取所有省份（去重）
     * 功能概述：从数据库中查询所有不重复的省份名称
     * @return {List<String>} 返回所有省份名称的列表
     */
    // 获取所有省份方法，返回所有不重复的省份名称列表（SQL在XML映射文件中定义）
    List<String> getAllProvinces();

    /**
     * 根据省份获取城市列表
     * 功能概述：根据省份名称从数据库中查询该省份下的所有城市名称
     * @param {String} province - 省份名称
     * @return {List<String>} 返回该省份下的所有城市名称列表
     */
    // 根据省份获取城市列表方法，接收省份名称参数，返回该省份下的所有城市名称列表（SQL在XML映射文件中定义）
    List<String> getCitiesByProvince(@Param("province") String province);

    // 多表连查景点信息
    /**
     * 获取带完整信息的景点列表（包含分类、图片、标签）
     * 功能概述：从数据库中查询所有景点信息，包含分类、图片、标签等关联信息
     * @return {List<Attraction>} 返回带完整信息的景点列表
     */
    // 获取带完整信息的景点列表方法，返回包含分类、图片、标签等关联信息的景点列表（SQL在XML映射文件中定义）
    List<Attraction> getAttractionsWithDetails();

    /**
     * 根据省份获取景点列表（含完整信息）
     * 功能概述：根据省份名称从数据库中查询该省份下的所有景点信息，包含分类、图片、标签等关联信息
     * @param {String} province - 省份名称
     * @return {List<Attraction>} 返回该省份下的所有景点列表
     */
    // 根据省份获取景点列表方法，接收省份名称参数，返回该省份下的所有景点列表（SQL在XML映射文件中定义）
    List<Attraction> getAttractionsByProvince(@Param("province") String province);

    /**
     * 根据省份和城市获取景点列表（含完整信息）
     * 功能概述：根据省份和城市名称从数据库中查询该地区的所有景点信息，包含分类、图片、标签等关联信息
     * @param {String} province - 省份名称
     * @param {String} city - 城市名称
     * @return {List<Attraction>} 返回该地区的所有景点列表
     */
    // 根据省份和城市获取景点列表方法，接收省份和城市名称参数，返回该地区的所有景点列表（SQL在XML映射文件中定义）
    List<Attraction> getAttractionsByProvinceAndCity(
            @Param("province") String province,  // 省份名称参数
            @Param("city") String city            // 城市名称参数
    );

    /**
     * 根据分类获取景点列表（含完整信息）
     * 功能概述：根据分类编号从数据库中查询该分类下的所有景点信息，包含分类、图片、标签等关联信息
     * @param {Long} categoryId - 分类编号
     * @return {List<Attraction>} 返回该分类下的所有景点列表
     */
    // 根据分类获取景点列表方法，接收分类编号参数，返回该分类下的所有景点列表（SQL在XML映射文件中定义）
    List<Attraction> getAttractionsByCategory(@Param("categoryId") Long categoryId);

    /**
     * 获取推荐景点列表（根据推荐分数排序，含完整信息）
     * 功能概述：从数据库中查询推荐景点信息，根据推荐分数排序，包含分类、图片、标签等关联信息
     * @param {Integer} limit - 查询数量限制
     * @return {List<Attraction>} 返回推荐景点列表
     */
    // 获取推荐景点列表方法，接收查询数量限制参数，返回推荐景点列表（SQL在XML映射文件中定义）
    List<Attraction> getRecommendedAttractions(@Param("limit") Integer limit);

    /**
     * 根据推荐类型获取景点
     * 功能概述：根据推荐类型从数据库中查询该推荐类型下的所有景点信息
     * @param {String} recommendType - 推荐类型
     * @return {List<Attraction>} 返回该推荐类型下的所有景点列表
     */
    // 根据推荐类型获取景点方法，接收推荐类型参数，返回该推荐类型下的所有景点列表（SQL在XML映射文件中定义）
    List<Attraction> getAttractionsByRecommendType(@Param("recommendType") String recommendType);

    /**
     * 搜索景点（根据名称或描述）
     * 功能概述：根据关键词从数据库中搜索匹配的景点信息，支持按名称或描述模糊搜索
     * @param {String} keyword - 搜索关键词
     * @return {List<Attraction>} 返回匹配的景点列表
     */
    // 搜索景点方法，接收搜索关键词参数，返回匹配的景点列表（SQL在XML映射文件中定义）
    List<Attraction> searchAttractions(@Param("keyword") String keyword);

    // 景点详情查询
    /**
     * 根据ID获取景点详情（含所有关联信息）
     * 功能概述：根据景点编号从数据库中查询指定景点的详细信息，包含分类、图片、标签、推荐等所有关联信息
     * @param {Long} id - 景点编号
     * @return {Attraction} 返回景点对象，如果不存在则返回null
     */
    // 根据ID获取景点详情方法，接收景点编号参数，返回景点的详细信息（SQL在XML映射文件中定义）
    Attraction getAttractionDetailById(@Param("id") Long id);

    /**
     * 根据景点ID获取图片列表
     * 功能概述：根据景点编号从数据库中查询该景点的所有图片信息
     * @param {Long} attractionId - 景点编号
     * @return {List<AttractionImage>} 返回该景点的所有图片列表
     */
    // 根据景点ID获取图片列表方法，接收景点编号参数，返回该景点的所有图片列表（SQL在XML映射文件中定义）
    List<AttractionImage> getImagesByAttractionId(@Param("attractionId") Long attractionId);

    /**
     * 根据景点ID获取标签列表
     * 功能概述：根据景点编号从数据库中查询该景点的所有标签信息
     * @param {Long} attractionId - 景点编号
     * @return {List<AttractionTag>} 返回该景点的所有标签列表
     */
    // 根据景点ID获取标签列表方法，接收景点编号参数，返回该景点的所有标签列表（SQL在XML映射文件中定义）
    List<AttractionTag> getTagsByAttractionId(@Param("attractionId") Long attractionId);

    /**
     * 根据景点ID获取推荐信息
     * 功能概述：根据景点编号从数据库中查询该景点的所有推荐信息
     * @param {Long} attractionId - 景点编号
     * @return {List<AttractionRecommendation>} 返回该景点的所有推荐信息列表
     */
    // 根据景点ID获取推荐信息方法，接收景点编号参数，返回该景点的所有推荐信息列表（SQL在XML映射文件中定义）
    List<AttractionRecommendation> getRecommendationsByAttractionId(@Param("attractionId") Long attractionId);

    // 统计查询
    /**
     * 获取热门景点（根据浏览次数和点赞数）
     * 功能概述：从数据库中查询热门景点信息，根据浏览次数和点赞数排序
     * @param {Integer} limit - 查询数量限制
     * @return {List<Attraction>} 返回热门景点列表
     */
    // 获取热门景点方法，接收查询数量限制参数，返回热门景点列表（SQL在XML映射文件中定义）
    List<Attraction> getPopularAttractions(@Param("limit") Integer limit);

    /**
     * 根据标签获取景点
     * 功能概述：根据标签名称从数据库中查询该标签下的所有景点信息
     * @param {String} tagName - 标签名称
     * @return {List<Attraction>} 返回该标签下的所有景点列表
     */
    // 根据标签获取景点方法，接收标签名称参数，返回该标签下的所有景点列表（SQL在XML映射文件中定义）
    List<Attraction> getAttractionsByTag(@Param("tagName") String tagName);

    /**
     * 获取不同推荐类型的景点统计
     * 功能概述：从数据库中统计不同推荐类型的景点数量
     * @return {List<Map<String, Object>>} 返回推荐类型统计结果列表
     */
    // 获取不同推荐类型的景点统计方法，返回推荐类型统计结果列表（SQL在XML映射文件中定义）
    List<java.util.Map<String, Object>> getRecommendationStats();

    // 更新操作
    /**
     * 增加景点浏览次数
     * 功能概述：根据景点编号增加该景点的浏览次数（+1）
     * @param {Long} id - 景点编号
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 增加景点浏览次数方法，接收景点编号参数，增加该景点的浏览次数（SQL在XML映射文件中定义）
    int increaseViewCount(@Param("id") Long id);

    /**
     * 增加景点点赞数
     * 功能概述：根据景点编号增加该景点的点赞数（+1）
     * @param {Long} id - 景点编号
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 增加景点点赞数方法，接收景点编号参数，增加该景点的点赞数（SQL在XML映射文件中定义）
    int increaseLikeCount(@Param("id") Long id);
}
