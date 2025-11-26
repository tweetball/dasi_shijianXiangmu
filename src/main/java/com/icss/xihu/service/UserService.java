// 定义包路径，标识该接口属于com.icss.xihu.service包
package com.icss.xihu.service;

// 导入用户实体类
import com.icss.xihu.model.User;

/**
 * 用户服务接口
 * 功能概述：定义用户相关的业务逻辑方法，包括用户注册、登录、密码管理、短信验证码等
 */
// 用户服务接口，定义用户相关的业务逻辑方法
public interface UserService {
    
    /**
     * 发送手机验证码
     * 功能概述：根据手机号和验证码类型发送短信验证码，用于注册、登录、重置密码等场景
     * @param phone 手机号
     * @param type 验证码类型 (register=注册, login=登录, reset=重置密码)
     * @return 是否发送成功
     */
    // 发送手机验证码方法，接收手机号和验证码类型参数
    boolean sendSmsCode(String phone, String type);
    
    /**
     * 验证手机验证码
     * 功能概述：验证用户输入的短信验证码是否正确且未过期，验证成功后标记为已使用
     * @param phone 手机号
     * @param code 验证码
     * @param type 验证码类型
     * @return 是否验证成功
     */
    // 验证手机验证码方法，接收手机号、验证码和验证码类型参数
    boolean verifySmsCode(String phone, String code, String type);
    
    /**
     * 用户注册
     * 功能概述：处理用户注册请求，包括验证短信验证码、检查手机号和用户名是否已存在、密码加密、创建新用户
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     * @param smsCode 短信验证码
     * @return 注册结果（"success"表示成功，其他字符串表示失败原因）
     */
    // 用户注册方法，接收用户名、密码、手机号和短信验证码参数
    String register(String username, String password, String phone, String smsCode);
    
    /**
     * 用户登录
     * 功能概述：处理用户密码登录请求，验证手机号和密码，登录成功后更新最后登录时间
     * @param phone 手机号
     * @param password 密码
     * @return 登录的用户信息，失败返回null
     */
    // 用户登录方法，接收手机号和密码参数
    User login(String phone, String password);
    
    /**
     * 手机验证码登录
     * 功能概述：处理用户验证码登录请求，验证手机号和短信验证码，登录成功后更新最后登录时间
     * @param phone 手机号
     * @param smsCode 短信验证码
     * @return 登录的用户信息，失败返回null
     */
    // 手机验证码登录方法，接收手机号和短信验证码参数
    User loginWithSmsCode(String phone, String smsCode);
    
    /**
     * 根据ID查找用户
     * 功能概述：根据用户ID查询用户详细信息
     * @param id 用户ID
     * @return 用户信息
     */
    // 根据ID查找用户方法，接收用户ID参数
    User findById(int id);
    
    /**
     * 根据手机号查找用户
     * 功能概述：根据手机号查询用户详细信息
     * @param phone 手机号
     * @return 用户信息
     */
    // 根据手机号查找用户方法，接收手机号参数
    User findByPhone(String phone);
    
    /**
     * 更新用户信息
     * 功能概述：更新用户的个人信息，如昵称、邮箱等
     * @param user 用户信息
     * @return 是否更新成功
     */
    // 更新用户信息方法，接收用户对象参数
    boolean updateUser(User user);
    
    /**
     * 修改密码
     * 功能概述：处理用户密码修改请求，验证原密码后更新为新密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    // 修改密码方法，接收用户ID、旧密码和新密码参数
    boolean changePassword(int userId, String oldPassword, String newPassword);
    
    /**
     * 通过手机验证码重置密码
     * 功能概述：处理用户密码重置请求，验证手机号和短信验证码后更新密码
     * @param phone 手机号
     * @param smsCode 短信验证码
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    // 通过手机验证码重置密码方法，接收手机号、短信验证码和新密码参数
    boolean resetPasswordWithSmsCode(String phone, String smsCode, String newPassword);
} 