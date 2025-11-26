// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;

/**
 * 短信验证码实体类
 * 功能概述：表示系统中的短信验证码信息，包括手机号、验证码、类型、创建时间、过期时间、状态等
 */
// 短信验证码实体类，对应数据库中的sms_code表
public class SmsCode {
    // 验证码ID，主键，自增
    private int id;
    // 手机号，用于接收验证码
    private String phone;
    // 验证码，6位随机数字
    private String code;
    // 验证码类型，register=注册，login=登录，reset=重置密码
    private String type;
    // 创建时间，记录验证码创建的时间
    private LocalDateTime createTime;
    // 过期时间，记录验证码过期的时间（创建后5分钟）
    private LocalDateTime expireTime;
    // 验证码状态，0=未使用，1=已使用，2=已过期
    private int status;

    /**
     * 无参构造方法
     * 功能概述：创建空的短信验证码对象，用于MyBatis等框架的反序列化
     */
    // 无参构造方法，用于创建空的短信验证码对象
    public SmsCode() {}

    /**
     * 有参构造方法
     * 功能概述：创建短信验证码对象，设置手机号、验证码、类型，并初始化创建时间、过期时间（5分钟后）和状态（未使用）
     * @param phone 手机号
     * @param code 验证码（6位随机数字）
     * @param type 验证码类型（register=注册，login=登录，reset=重置密码）
     */
    // 有参构造方法，接收手机号、验证码和类型参数
    public SmsCode(String phone, String code, String type) {
        // 设置手机号
        this.phone = phone;
        // 设置验证码
        this.code = code;
        // 设置验证码类型
        this.type = type;
        // 设置创建时间为当前时间
        this.createTime = LocalDateTime.now();
        // 设置过期时间为当前时间加5分钟（5分钟过期）
        this.expireTime = LocalDateTime.now().plusMinutes(5);
        // 设置状态为未使用（0）
        this.status = 0;
    }

    // Getter和Setter方法
    /**
     * 获取验证码ID
     * @return 验证码ID
     */
    // 获取验证码ID方法，返回验证码ID
    public int getId() {
        // 返回验证码ID
        return id;
    }

    /**
     * 设置验证码ID
     * @param id 验证码ID
     */
    // 设置验证码ID方法，接收验证码ID参数
    public void setId(int id) {
        // 设置验证码ID
        this.id = id;
    }

    /**
     * 获取手机号
     * @return 手机号
     */
    // 获取手机号方法，返回手机号
    public String getPhone() {
        // 返回手机号
        return phone;
    }

    /**
     * 设置手机号
     * @param phone 手机号
     */
    // 设置手机号方法，接收手机号参数
    public void setPhone(String phone) {
        // 设置手机号
        this.phone = phone;
    }

    /**
     * 获取验证码
     * @return 验证码（6位随机数字）
     */
    // 获取验证码方法，返回验证码
    public String getCode() {
        // 返回验证码
        return code;
    }

    /**
     * 设置验证码
     * @param code 验证码（6位随机数字）
     */
    // 设置验证码方法，接收验证码参数
    public void setCode(String code) {
        // 设置验证码
        this.code = code;
    }

    /**
     * 获取验证码类型
     * @return 验证码类型（register=注册，login=登录，reset=重置密码）
     */
    // 获取验证码类型方法，返回验证码类型
    public String getType() {
        // 返回验证码类型
        return type;
    }

    /**
     * 设置验证码类型
     * @param type 验证码类型（register=注册，login=登录，reset=重置密码）
     */
    // 设置验证码类型方法，接收验证码类型参数
    public void setType(String type) {
        // 设置验证码类型
        this.type = type;
    }

    /**
     * 获取创建时间
     * @return 创建时间
     */
    // 获取创建时间方法，返回创建时间
    public LocalDateTime getCreateTime() {
        // 返回创建时间
        return createTime;
    }

    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    // 设置创建时间方法，接收创建时间参数
    public void setCreateTime(LocalDateTime createTime) {
        // 设置创建时间
        this.createTime = createTime;
    }

    /**
     * 获取过期时间
     * @return 过期时间
     */
    // 获取过期时间方法，返回过期时间
    public LocalDateTime getExpireTime() {
        // 返回过期时间
        return expireTime;
    }

    /**
     * 设置过期时间
     * @param expireTime 过期时间
     */
    // 设置过期时间方法，接收过期时间参数
    public void setExpireTime(LocalDateTime expireTime) {
        // 设置过期时间
        this.expireTime = expireTime;
    }

    /**
     * 获取验证码状态
     * @return 验证码状态（0=未使用，1=已使用，2=已过期）
     */
    // 获取验证码状态方法，返回验证码状态
    public int getStatus() {
        // 返回验证码状态
        return status;
    }

    /**
     * 设置验证码状态
     * @param status 验证码状态（0=未使用，1=已使用，2=已过期）
     */
    // 设置验证码状态方法，接收验证码状态参数
    public void setStatus(int status) {
        // 设置验证码状态
        this.status = status;
    }

    /**
     * 重写toString方法
     * 功能概述：返回短信验证码对象的字符串表示，用于日志输出和调试
     * @return 短信验证码对象的字符串表示
     */
    // 重写toString方法，返回短信验证码对象的字符串表示
    @Override
    // toString方法，返回短信验证码对象的字符串表示
    public String toString() {
        // 返回短信验证码对象的字符串表示，包含ID、手机号、验证码、类型、创建时间、过期时间和状态
        return "SmsCode{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", status=" + status +
                '}';
    }
} 