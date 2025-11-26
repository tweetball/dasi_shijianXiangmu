// 定义包路径，标识该接口属于com.icss.xihu.mapper包
package com.icss.xihu.mapper;

// 导入用户实体类
import com.icss.xihu.model.User;
// 导入短信验证码实体类
import com.icss.xihu.model.SmsCode;
// 导入MyBatis的Mapper注解
import org.apache.ibatis.annotations.Mapper;
// 导入MyBatis的Select注解，用于查询操作
import org.apache.ibatis.annotations.Select;
// 导入MyBatis的Insert注解，用于插入操作
import org.apache.ibatis.annotations.Insert;
// 导入MyBatis的Update注解，用于更新操作
import org.apache.ibatis.annotations.Update;
// 导入MyBatis的Param注解，用于参数绑定
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 * 功能概述：定义用户相关的数据库操作方法，包括用户查询、插入、更新，以及短信验证码的增删改查
 */
// 标识该接口为MyBatis Mapper接口，会被MyBatis扫描并注册
@Mapper
// 用户Mapper接口，定义用户相关的数据库操作方法
public interface UserMapper {
    
    // 用户相关操作
    /**
     * 根据手机号查询用户
     * 功能概述：根据手机号查询用户信息，只查询状态为1（正常）的用户
     * @param phone 手机号
     * @return 用户对象，不存在返回null
     */
    // 根据手机号查询用户方法，接收手机号参数
    @Select("SELECT * FROM user WHERE phone = #{phone} AND status = 1")
    // 根据手机号查询用户，返回用户对象
    User findByPhone(String phone);
    
    /**
     * 根据用户名查询用户
     * 功能概述：根据用户名查询用户信息，只查询状态为1（正常）的用户
     * @param username 用户名
     * @return 用户对象，不存在返回null
     */
    // 根据用户名查询用户方法，接收用户名参数
    @Select("SELECT * FROM user WHERE username = #{username} AND status = 1")
    // 根据用户名查询用户，返回用户对象
    User findByUsername(String username);
    
    /**
     * 根据ID查询用户
     * 功能概述：根据用户ID查询用户详细信息
     * @param id 用户ID
     * @return 用户对象，不存在返回null
     */
    // 根据ID查询用户方法，接收用户ID参数
    @Select("SELECT * FROM user WHERE id = #{id}")
    // 根据用户ID查询用户，返回用户对象
    User findById(int id);
    
    /**
     * 插入新用户
     * 功能概述：插入新用户到数据库，包括用户名、密码、手机号、昵称等信息
     * @param user 用户对象
     * @return 受影响的行数
     */
    // 插入新用户方法，接收用户对象参数
    @Insert("INSERT INTO user(username, password, phone, nickname, status, create_time, update_time) " +
            "VALUES(#{username}, #{password}, #{phone}, #{nickname}, #{status}, #{createTime}, #{updateTime})")
    // 插入新用户到数据库，返回受影响的行数
    int insertUser(User user);
    
    /**
     * 更新用户信息
     * 功能概述：更新用户的个人信息，包括昵称、邮箱、头像等
     * @param user 用户对象
     * @return 受影响的行数
     */
    // 更新用户信息方法，接收用户对象参数
    @Update("UPDATE user SET nickname = #{nickname}, email = #{email}, avatar = #{avatar}, " +
            "update_time = #{updateTime} WHERE id = #{id}")
    // 更新用户信息，返回受影响的行数
    int updateUser(User user);
    
    /**
     * 更新最后登录时间
     * 功能概述：更新用户的最后登录时间，用于记录用户登录历史
     * @param id 用户ID
     * @param lastLoginTime 最后登录时间
     * @return 受影响的行数
     */
    // 更新最后登录时间方法，接收用户ID和最后登录时间参数
    @Update("UPDATE user SET last_login_time = #{lastLoginTime} WHERE id = #{id}")
    // 更新最后登录时间，返回受影响的行数
    int updateLastLoginTime(@Param("id") int id, @Param("lastLoginTime") java.time.LocalDateTime lastLoginTime);
    
    /**
     * 更新用户密码
     * 功能概述：更新用户的密码，用于密码修改功能
     * @param id 用户ID
     * @param password 新密码（已加密）
     * @param updateTime 更新时间
     * @return 受影响的行数
     */
    // 更新用户密码方法，接收用户ID、新密码和更新时间参数
    @Update("UPDATE user SET password = #{password}, update_time = #{updateTime} WHERE id = #{id}")
    // 更新用户密码，返回受影响的行数
    int updatePassword(@Param("id") int id, @Param("password") String password, 
                      @Param("updateTime") java.time.LocalDateTime updateTime);
    
    // 验证码相关操作
    /**
     * 插入短信验证码
     * 功能概述：插入新的短信验证码到数据库，包括手机号、验证码、类型、创建时间、过期时间等
     * @param smsCode 短信验证码对象
     * @return 受影响的行数
     */
    // 插入短信验证码方法，接收短信验证码对象参数
    @Insert("INSERT INTO sms_code(phone, code, type, create_time, expire_time, status) " +
            "VALUES(#{phone}, #{code}, #{type}, #{createTime}, #{expireTime}, #{status})")
    // 插入短信验证码到数据库，返回受影响的行数
    int insertSmsCode(SmsCode smsCode);
    
    /**
     * 查找有效的短信验证码
     * 功能概述：根据手机号和类型查找有效的短信验证码（未使用且未过期）
     * @param phone 手机号
     * @param type 验证码类型（register=注册，login=登录，reset=重置密码）
     * @return 短信验证码对象，不存在返回null
     */
    // 查找有效的短信验证码方法，接收手机号和验证码类型参数
    @Select("SELECT * FROM sms_code WHERE phone = #{phone} AND type = #{type} AND status = 0 " +
            "AND expire_time > NOW() ORDER BY create_time DESC LIMIT 1")
    // 查找有效的短信验证码，返回短信验证码对象
    SmsCode findValidSmsCode(@Param("phone") String phone, @Param("type") String type);
    
    /**
     * 标记短信验证码为已使用
     * 功能概述：将短信验证码标记为已使用（status = 1），防止重复使用
     * @param id 短信验证码ID
     * @return 受影响的行数
     */
    // 标记短信验证码为已使用方法，接收短信验证码ID参数
    @Update("UPDATE sms_code SET status = 1 WHERE id = #{id}")
    // 标记短信验证码为已使用，返回受影响的行数
    int useSmsCode(int id);
    
    /**
     * 使短信验证码失效
     * 功能概述：将指定手机号和类型的未使用验证码标记为已失效（status = 2），用于发送新验证码时使旧验证码失效
     * @param phone 手机号
     * @param type 验证码类型
     * @return 受影响的行数
     */
    // 使短信验证码失效方法，接收手机号和验证码类型参数
    @Update("UPDATE sms_code SET status = 2 WHERE phone = #{phone} AND type = #{type} AND status = 0")
    // 使短信验证码失效，返回受影响的行数
    int expireSmsCodesByPhone(@Param("phone") String phone, @Param("type") String type);
} 