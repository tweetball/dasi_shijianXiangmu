/**
 * 酒店Mapper接口
 * 功能概述：定义酒店相关的数据库操作方法，包括查询所有酒店、根据ID查询酒店、多表查询等
 */
// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入酒店实体类
import com.icss.xihu.model.Hotel;
// 导入酒店多房间实体类
import com.icss.xihu.model.HotelMany;
// 导入酒店评价实体类
import com.icss.xihu.model.HotelReview;
// 导入MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Select注解，用于在方法上直接编写SQL查询语句
import org.apache.ibatis.annotations.Select;
// 导入MyBatis的Insert注解，用于在方法上直接编写SQL插入语句
import org.apache.ibatis.annotations.Insert;
// 导入MyBatis的Options注解，用于配置自动生成主键
import org.apache.ibatis.annotations.Options;
// 导入MyBatis的Param注解，用于指定参数名
import org.apache.ibatis.annotations.Param;

// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 酒店Mapper接口
 * 功能概述：定义酒店相关的数据库操作方法，包括查询所有酒店、根据ID查询酒店、多表查询等
 */
// 使用MyBatis的Mapper注解，标识该接口为MyBatis的Mapper接口
@Mapper
// 酒店Mapper接口，定义酒店相关的数据库操作方法
public interface HotelMapper {
    // 通过注解的方式写sql
    // 增insert 删delete  改update 查select
    // 实体类字段名字和数据库字段名字不一致时，通过as或者空格给字段取别名
    // 别名需要和实体类的字段名一致
    
    /**
     * 查询所有酒店
     * 功能概述：从数据库中查询所有酒店信息，返回酒店列表
     * @return {List<Hotel>} 返回所有酒店的列表
     */
    // 使用MyBatis的Select注解，显式查询所有字段（包括minPrice），确保字段映射正确，将varchar类型的id转换为int
    @Select("SELECT CAST(id AS UNSIGNED) as id, name, tel, img, content, address, level, score, province, city, minPrice FROM hotel")
    // 查询所有酒店方法，返回所有酒店的列表
    public List<Hotel> findAll();
    
    /**
     * 根据ID查询酒店
     * 功能概述：根据酒店ID从数据库中查询指定酒店的信息
     * @param {int} id - 酒店ID
     * @return {Hotel} 返回酒店对象，如果不存在则返回null
     */
    // 使用MyBatis的Select注解，显式查询所有字段（包括minPrice），将varchar类型的id转换为int，使用#{value}占位符接收参数
    @Select("SELECT CAST(id AS UNSIGNED) as id, name, tel, content, img, address, level, score, province, city, minPrice FROM hotel WHERE id = #{value}")
    // 根据ID查询酒店方法，接收酒店ID参数，返回酒店对象
    public Hotel findById(int id);
    
    /**
     * 多表查询酒店及其房间信息
     * 功能概述：根据酒店ID查询酒店信息及其所有房间信息（一对多关系），使用XML映射文件编写SQL
     * @param {int} id - 酒店ID
     * @return {List<HotelMany>} 返回酒店及其房间信息的列表
     */
    // 因为会用到多表进行查询，所以使用xml解析文件，写sql
    // 多表查询---一个酒店包含多个房间
    // 多表查询方法，接收酒店ID参数，返回酒店及其房间信息的列表（SQL在XML映射文件中定义）
    public List<HotelMany> findByMany(int id);
    
    /**
     * 查询首页显示的酒店（前3条）
     * 功能概述：从数据库中查询前3条酒店信息，用于首页展示
     * @return {List<Hotel>} 返回前3条酒店的列表
     */
    // 在酒店首页显示前两条数据
    // limit 5//查询前5条
    // limit n，m//从n+1开始查m条
    // 使用MyBatis的Select注解，显式查询所有字段（包括minPrice），将varchar类型的id转换为int，使用limit限制查询结果数量
    @Select("SELECT CAST(id AS UNSIGNED) as id, name, tel, content, img, address, level, score, province, city, minPrice FROM hotel LIMIT 3")
    // 查询首页显示的酒店方法，返回前3条酒店的列表
    public List<Hotel> findIndex();

    /**
     * 根据关键词搜索酒店
     * 功能概述：根据关键词从数据库中搜索匹配的酒店信息，使用XML映射文件编写SQL
     * @param {String} keyword - 搜索关键词
     * @return {List<Hotel>} 返回匹配的酒店列表
     */
    // 根据关键词搜索酒店方法，接收关键词参数，返回匹配的酒店列表（SQL在XML映射文件中定义）
    public List<Hotel> searchByKeyword(String keyword);
    
    /**
     * 获取热门酒店
     * 功能概述：从数据库中查询热门酒店信息，使用XML映射文件编写SQL
     * @return {List<Hotel>} 返回热门酒店的列表
     */
    // 新增方法
    // 获取热门酒店方法，返回热门酒店的列表（SQL在XML映射文件中定义）
    List<Hotel> getHotHotels();
    
    /**
     * 查询所有活跃的酒店
     * 功能概述：从数据库中查询所有状态为活跃的酒店信息，使用XML映射文件编写SQL
     * @return {List<Hotel>} 返回所有活跃酒店的列表
     */
    // 新增方法
    // 查询所有活跃的酒店方法，返回所有活跃酒店的列表（SQL在XML映射文件中定义）
    List<Hotel> findAllActive();

    /**
     * 插入酒店评价
     * 功能概述：向数据库中插入一条酒店评价记录
     * @param {HotelReview} review - 酒店评价对象
     * @return {int} 返回插入的记录数（通常为1）
     */
    // 使用MyBatis的Insert注解，直接在方法上编写SQL插入语句，插入评价信息
    @Insert("INSERT INTO hotel_review (hotel_id, user_id, username, user_avatar, rating, content, images, create_time) " +
            "VALUES (#{hotelId}, #{userId}, #{username}, #{userAvatar}, #{rating}, #{content}, #{images}, NOW())")
    // 使用MyBatis的Options注解，配置自动生成主键，将生成的主键值赋给review对象的id字段
    @Options(useGeneratedKeys = true, keyProperty = "id")
    // 插入评价方法，接收酒店评价对象参数，返回插入的记录数
    int insertReview(HotelReview review);

    /**
     * 根据酒店ID查询评价
     * 功能概述：根据酒店编号从数据库中查询该酒店的所有评价信息，按创建时间倒序排列
     * @param {Integer} hotelId - 酒店编号
     * @return {List<HotelReview>} 返回该酒店的所有评价列表
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，按酒店编号查询，按创建时间倒序排列
    @Select("SELECT * FROM hotel_review WHERE hotel_id = #{hotelId} ORDER BY create_time DESC")
    // 根据酒店ID查询评价方法，接收酒店编号参数，返回该酒店的所有评价列表
    List<HotelReview> findReviewsByHotelId(@Param("hotelId") Integer hotelId);

    /**
     * 获取酒店评分统计
     * 功能概述：根据酒店编号从数据库中统计该酒店的平均评分和评价数量
     * @param {Integer} hotelId - 酒店编号
     * @return {Map<String, Object>} 返回评分统计结果（包含平均评分和评价数量）
     */
    // 使用MyBatis的Select注解，直接在方法上编写SQL查询语句，使用AVG和COUNT函数统计
    @Select("SELECT AVG(rating) as avgRating, COUNT(*) as reviewCount FROM hotel_review WHERE hotel_id = #{hotelId}")
    // 获取酒店评分统计方法，接收酒店编号参数，返回评分统计结果
    Map<String, Object> getHotelRatingStats(@Param("hotelId") Integer hotelId);
    
    /**
     * 更新酒店评分
     * 功能概述：根据酒店编号更新酒店表中的评分字段
     * @param {Integer} hotelId - 酒店编号
     * @param {Double} score - 新的评分值
     * @return {int} 返回更新的记录数（通常为1）
     */
    // 使用MyBatis的Update注解，直接在方法上编写SQL更新语句，更新酒店评分
    @org.apache.ibatis.annotations.Update("UPDATE hotel SET score = #{score} WHERE id = #{hotelId}")
    // 更新酒店评分方法，接收酒店编号和评分参数，返回更新的记录数
    int updateHotelScore(@Param("hotelId") Integer hotelId, @Param("score") Double score);

}
