// 定义包路径，标识该类属于com.icss.xihu.service.impl包
package com.icss.xihu.service.impl;

// 导入用户Mapper接口
import com.icss.xihu.mapper.UserMapper;
// 导入用户实体类
import com.icss.xihu.model.User;
// 导入短信验证码实体类
import com.icss.xihu.model.SmsCode;
// 导入用户服务接口
import com.icss.xihu.service.UserService;
// 导入短信服务接口
import com.icss.xihu.service.SmsService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的服务注解
import org.springframework.stereotype.Service;
// 导入Spring的工具类，用于MD5加密
import org.springframework.util.DigestUtils;

// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;
// 导入Random类，用于生成随机数
import java.util.Random;

/**
 * 用户服务实现类
 * 功能概述：实现用户相关的业务逻辑，包括用户注册、登录、密码管理、短信验证码等
 */
// 标识该类为Spring服务类，会被Spring容器扫描并注册为Bean
@Service
// 用户服务实现类，实现UserService接口
public class UserServiceImpl implements UserService {

    // 自动注入用户Mapper，Spring容器会自动查找并注入UserMapper的实现类
    @Autowired
    // 用户Mapper对象，用于调用数据库操作方法
    private UserMapper userMapper;
    
    // 自动注入短信服务，Spring容器会自动查找并注入SmsService的实现类
    @Autowired
    // 短信服务对象，用于调用短信发送相关的业务逻辑
    private SmsService smsService;

    /**
     * 发送手机验证码
     * 功能概述：根据手机号和验证码类型发送短信验证码，包括使旧验证码失效、生成新验证码、保存到数据库、调用短信服务发送
     */
    // 实现发送手机验证码方法
    @Override
    // 发送手机验证码方法，接收手机号和验证码类型参数
    public boolean sendSmsCode(String phone, String type) {
        // 使用try-catch捕获异常
        try {
            // 使旧的验证码失效
            // 调用用户Mapper的expireSmsCodesByPhone方法，使该手机号和类型的旧验证码失效
            userMapper.expireSmsCodesByPhone(phone, type);
            
            // 生成6位随机验证码
            // 调用generateSmsCode方法生成6位随机验证码
            String code = generateSmsCode();
            
            // 保存验证码到数据库
            // 创建短信验证码对象
            SmsCode smsCode = new SmsCode(phone, code, type);
            // 调用用户Mapper的insertSmsCode方法，将验证码保存到数据库，返回受影响的行数
            int result = userMapper.insertSmsCode(smsCode);
            
            // 判断验证码是否保存成功（受影响行数大于0表示成功）
            if (result > 0) {
                // 调用短信服务发送验证码
                // 调用短信服务的sendSmsCode方法发送验证码，返回是否发送成功
                boolean smsResult = smsService.sendSmsCode(phone, code, type);
                // 判断短信是否发送成功
                if (smsResult) {
                    // 打印成功信息到控制台
                    System.out.println("验证码发送成功: " + phone + " (" + type + ")");
                    // 返回true表示发送成功
                    return true;
                // 短信发送失败
                } else {
                    // 打印失败信息到控制台
                    System.err.println("验证码发送失败: " + phone);
                    // 发送失败时，将验证码标记为失效
                    // 调用用户Mapper的expireSmsCodesByPhone方法，使该验证码失效
                    userMapper.expireSmsCodesByPhone(phone, type);
                    // 返回false表示发送失败
                    return false;
                }
            }
            // 验证码保存失败，返回false
            return false;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常信息到控制台
            System.err.println("发送验证码异常: " + e.getMessage());
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示发送失败
            return false;
        }
    }

    /**
     * 验证手机验证码
     * 功能概述：验证用户输入的短信验证码是否正确且未过期，验证成功后标记为已使用
     */
    // 实现验证手机验证码方法
    @Override
    // 验证手机验证码方法，接收手机号、验证码和验证码类型参数
    public boolean verifySmsCode(String phone, String code, String type) {
        // 使用try-catch捕获异常
        try {
            // 调用用户Mapper的findValidSmsCode方法，查找该手机号和类型的有效验证码
            SmsCode smsCode = userMapper.findValidSmsCode(phone, type);
            // 判断验证码是否存在且与用户输入的验证码一致
            if (smsCode != null && smsCode.getCode().equals(code)) {
                // 标记验证码为已使用
                // 调用用户Mapper的useSmsCode方法，将验证码标记为已使用
                userMapper.useSmsCode(smsCode.getId());
                // 返回true表示验证成功
                return true;
            }
            // 验证码不存在或不一致，返回false
            return false;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示验证失败
            return false;
        }
    }

    /**
     * 用户注册
     * 功能概述：处理用户注册请求，包括验证短信验证码、检查手机号和用户名是否已存在、密码加密、创建新用户
     */
    // 实现用户注册方法
    @Override
    // 用户注册方法，接收用户名、密码、手机号和短信验证码参数
    public String register(String username, String password, String phone, String smsCode) {
        // 使用try-catch捕获异常
        try {
            // 验证短信验证码
            // 调用verifySmsCode方法验证短信验证码是否正确
            if (!verifySmsCode(phone, smsCode, "register")) {
                // 如果验证失败，返回错误信息
                return "验证码错误或已过期";
            }
            
            // 检查手机号是否已注册
            // 调用用户Mapper的findByPhone方法，根据手机号查询用户是否已存在
            User existingUser = userMapper.findByPhone(phone);
            // 判断用户是否已存在
            if (existingUser != null) {
                // 如果用户已存在，返回错误信息
                return "手机号已注册";
            }
            
            // 检查用户名是否已存在
            // 调用用户Mapper的findByUsername方法，根据用户名查询用户是否已存在
            User existingUsername = userMapper.findByUsername(username);
            // 判断用户名是否已存在
            if (existingUsername != null) {
                // 如果用户名已存在，返回错误信息
                return "用户名已存在";
            }
            
            // 密码加密
            // 使用MD5算法对密码进行加密，将密码转换为字节数组后加密
            String encodedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            
            // 创建新用户
            // 创建用户对象，传入用户名、加密后的密码和手机号
            User newUser = new User(username, encodedPassword, phone);
            // 默认昵称为用户名
            newUser.setNickname(username);
            
            // 调用用户Mapper的insertUser方法，将新用户保存到数据库，返回受影响的行数
            int result = userMapper.insertUser(newUser);
            // 判断用户是否创建成功（受影响行数大于0表示成功）
            if (result > 0) {
                // 返回"success"表示注册成功
                return "success";
            // 用户创建失败
            } else {
                // 返回错误信息
                return "注册失败，请重试";
            }
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回错误信息
            return "系统错误，请稍后重试";
        }
    }

    /**
     * 用户登录
     * 功能概述：处理用户密码登录请求，验证手机号和密码，登录成功后更新最后登录时间
     */
    // 实现用户登录方法
    @Override
    // 用户登录方法，接收手机号和密码参数
    public User login(String phone, String password) {
        // 使用try-catch捕获异常
        try {
            // 调用用户Mapper的findByPhone方法，根据手机号查询用户信息
            User user = userMapper.findByPhone(phone);
            // 判断用户是否存在
            if (user != null) {
                // 使用MD5算法对输入的密码进行加密
                String encodedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
                // 判断加密后的密码是否与数据库中的密码一致
                if (encodedPassword.equals(user.getPassword())) {
                    // 更新最后登录时间
                    // 调用用户Mapper的updateLastLoginTime方法，更新用户的最后登录时间为当前时间
                    userMapper.updateLastLoginTime(user.getId(), LocalDateTime.now());
                    // 不返回密码，将密码字段设置为null
                    user.setPassword(null);
                    // 返回用户信息
                    return user;
                }
            }
            // 用户不存在或密码错误，返回null
            return null;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回null表示登录失败
            return null;
        }
    }

    /**
     * 手机验证码登录
     * 功能概述：处理用户验证码登录请求，验证手机号和短信验证码，登录成功后更新最后登录时间
     */
    // 实现手机验证码登录方法
    @Override
    // 手机验证码登录方法，接收手机号和短信验证码参数
    public User loginWithSmsCode(String phone, String smsCode) {
        // 使用try-catch捕获异常
        try {
            // 验证短信验证码
            // 调用verifySmsCode方法验证短信验证码是否正确
            if (!verifySmsCode(phone, smsCode, "login")) {
                // 如果验证失败，返回null
                return null;
            }
            
            // 调用用户Mapper的findByPhone方法，根据手机号查询用户信息
            User user = userMapper.findByPhone(phone);
            // 判断用户是否存在
            if (user != null) {
                // 更新最后登录时间
                // 调用用户Mapper的updateLastLoginTime方法，更新用户的最后登录时间为当前时间
                userMapper.updateLastLoginTime(user.getId(), LocalDateTime.now());
                // 不返回密码，将密码字段设置为null
                user.setPassword(null);
                // 返回用户信息
                return user;
            }
            // 用户不存在，返回null
            return null;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回null表示登录失败
            return null;
        }
    }

    /**
     * 根据ID查找用户
     * 功能概述：根据用户ID查询用户详细信息，不返回密码字段
     */
    // 实现根据ID查找用户方法
    @Override
    // 根据ID查找用户方法，接收用户ID参数
    public User findById(int id) {
        // 使用try-catch捕获异常
        try {
            // 调用用户Mapper的findById方法，根据用户ID查询用户信息
            User user = userMapper.findById(id);
            // 判断用户是否存在
            if (user != null) {
                // 不返回密码，将密码字段设置为null
                user.setPassword(null);
            }
            // 返回用户信息（如果不存在则返回null）
            return user;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回null表示查询失败
            return null;
        }
    }

    /**
     * 根据手机号查找用户
     * 功能概述：根据手机号查询用户详细信息，不返回密码字段
     */
    // 实现根据手机号查找用户方法
    @Override
    // 根据手机号查找用户方法，接收手机号参数
    public User findByPhone(String phone) {
        // 使用try-catch捕获异常
        try {
            // 调用用户Mapper的findByPhone方法，根据手机号查询用户信息
            User user = userMapper.findByPhone(phone);
            // 判断用户是否存在
            if (user != null) {
                // 不返回密码，将密码字段设置为null
                user.setPassword(null);
            }
            // 返回用户信息（如果不存在则返回null）
            return user;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回null表示查询失败
            return null;
        }
    }

    /**
     * 更新用户信息
     * 功能概述：更新用户的个人信息，如昵称、邮箱等，并更新修改时间
     */
    // 实现更新用户信息方法
    @Override
    // 更新用户信息方法，接收用户对象参数
    public boolean updateUser(User user) {
        // 使用try-catch捕获异常
        try {
            // 设置更新时间为当前时间
            user.setUpdateTime(LocalDateTime.now());
            // 调用用户Mapper的updateUser方法更新用户信息，返回受影响的行数，判断是否大于0
            return userMapper.updateUser(user) > 0;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示更新失败
            return false;
        }
    }

    /**
     * 修改密码
     * 功能概述：处理用户密码修改请求，验证原密码后更新为新密码
     */
    // 实现修改密码方法
    @Override
    // 修改密码方法，接收用户ID、旧密码和新密码参数
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // 使用try-catch捕获异常
        try {
            // 调用用户Mapper的findById方法，根据用户ID查询用户信息
            User user = userMapper.findById(userId);
            // 判断用户是否存在
            if (user != null) {
                // 使用MD5算法对旧密码进行加密
                String encodedOldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
                // 判断加密后的旧密码是否与数据库中的密码一致
                if (encodedOldPassword.equals(user.getPassword())) {
                    // 使用MD5算法对新密码进行加密
                    String encodedNewPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
                    // 调用用户Mapper的updatePassword方法更新密码，返回受影响的行数，判断是否大于0
                    return userMapper.updatePassword(userId, encodedNewPassword, LocalDateTime.now()) > 0;
                }
            }
            // 用户不存在或原密码错误，返回false
            return false;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示修改失败
            return false;
        }
    }

    /**
     * 通过手机验证码重置密码
     * 功能概述：处理用户密码重置请求，验证手机号和短信验证码后更新密码
     */
    // 实现通过手机验证码重置密码方法
    @Override
    // 通过手机验证码重置密码方法，接收手机号、短信验证码和新密码参数
    public boolean resetPasswordWithSmsCode(String phone, String smsCode, String newPassword) {
        // 使用try-catch捕获异常
        try {
            // 验证短信验证码
            // 调用verifySmsCode方法验证短信验证码是否正确
            if (!verifySmsCode(phone, smsCode, "reset")) {
                // 如果验证失败，返回false
                return false;
            }
            
            // 调用用户Mapper的findByPhone方法，根据手机号查询用户信息
            User user = userMapper.findByPhone(phone);
            // 判断用户是否存在
            if (user != null) {
                // 使用MD5算法对新密码进行加密
                String encodedPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
                // 调用用户Mapper的updatePassword方法更新密码，返回受影响的行数，判断是否大于0
                return userMapper.updatePassword(user.getId(), encodedPassword, LocalDateTime.now()) > 0;
            }
            // 用户不存在，返回false
            return false;
        // 捕获所有异常
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 返回false表示重置失败
            return false;
        }
    }

    /**
     * 生成6位随机验证码
     * 功能概述：生成一个6位随机数字验证码，范围在100000-999999之间
     */
    // 私有方法，生成6位随机验证码
    private String generateSmsCode() {
        // 创建Random对象，用于生成随机数
        Random random = new Random();
        // 生成100000到999999之间的随机数（6位数字）
        int code = 100000 + random.nextInt(900000);
        // 将随机数转换为字符串并返回
        return String.valueOf(code);
    }
} 