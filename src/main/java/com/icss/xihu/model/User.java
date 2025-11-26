// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入LocalDateTime类，用于处理日期时间
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 功能概述：表示系统中的用户信息，包括用户基本信息、登录信息、状态信息等
 */
// 用户实体类，对应数据库中的user表
public class User {
    // 用户ID，主键，自增
    private int id;
    // 用户名，用于登录和显示
    private String username;
    // 密码，已加密存储（MD5）
    private String password;
    // 手机号，用于登录和接收短信验证码
    private String phone;
    // 邮箱，可选字段
    private String email;
    // 昵称，用于显示
    private String nickname;
    // 头像URL，可选字段
    private String avatar;
    // 用户状态，0-禁用，1-正常
    private int status;
    // 创建时间，记录用户注册时间
    private LocalDateTime createTime;
    // 更新时间，记录用户信息最后更新时间
    private LocalDateTime updateTime;
    // 最后登录时间，记录用户最后登录时间
    private LocalDateTime lastLoginTime;

    /**
     * 无参构造方法
     * 功能概述：创建空的用户对象，用于MyBatis等框架的反序列化
     */
    // 无参构造方法，用于创建空的用户对象
    public User() {}

    /**
     * 有参构造方法
     * 功能概述：创建用户对象，设置用户名、密码、手机号，并初始化状态为正常（1）和时间戳
     * @param username 用户名
     * @param password 密码（已加密）
     * @param phone 手机号
     */
    // 有参构造方法，接收用户名、密码和手机号参数
    public User(String username, String password, String phone) {
        // 设置用户名
        this.username = username;
        // 设置密码（已加密）
        this.password = password;
        // 设置手机号
        this.phone = phone;
        // 设置状态为正常（1）
        this.status = 1;
        // 设置创建时间为当前时间
        this.createTime = LocalDateTime.now();
        // 设置更新时间为当前时间
        this.updateTime = LocalDateTime.now();
    }

    // Getter和Setter方法
    /**
     * 获取用户ID
     * 功能概述：获取用户的主键ID
     * @return 用户ID
     */
    // 获取用户ID方法，返回用户ID
    public int getId() {
        // 返回用户ID
        return id;
    }

    /**
     * 设置用户ID
     * 功能概述：设置用户的主键ID
     * @param id 用户ID
     */
    // 设置用户ID方法，接收用户ID参数
    public void setId(int id) {
        // 设置用户ID
        this.id = id;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    // 获取用户名方法，返回用户名
    public String getUsername() {
        // 返回用户名
        return username;
    }

    /**
     * 设置用户名
     * @param username 用户名
     */
    // 设置用户名方法，接收用户名参数
    public void setUsername(String username) {
        // 设置用户名
        this.username = username;
    }

    /**
     * 获取密码
     * @return 密码（已加密）
     */
    // 获取密码方法，返回密码（已加密）
    public String getPassword() {
        // 返回密码（已加密）
        return password;
    }

    /**
     * 设置密码
     * @param password 密码（已加密）
     */
    // 设置密码方法，接收密码参数（已加密）
    public void setPassword(String password) {
        // 设置密码（已加密）
        this.password = password;
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
     * 获取邮箱
     * @return 邮箱
     */
    // 获取邮箱方法，返回邮箱
    public String getEmail() {
        // 返回邮箱
        return email;
    }

    /**
     * 设置邮箱
     * @param email 邮箱
     */
    // 设置邮箱方法，接收邮箱参数
    public void setEmail(String email) {
        // 设置邮箱
        this.email = email;
    }

    /**
     * 获取昵称
     * @return 昵称
     */
    // 获取昵称方法，返回昵称
    public String getNickname() {
        // 返回昵称
        return nickname;
    }

    /**
     * 设置昵称
     * @param nickname 昵称
     */
    // 设置昵称方法，接收昵称参数
    public void setNickname(String nickname) {
        // 设置昵称
        this.nickname = nickname;
    }

    /**
     * 获取头像URL
     * @return 头像URL
     */
    // 获取头像URL方法，返回头像URL
    public String getAvatar() {
        // 返回头像URL
        return avatar;
    }

    /**
     * 设置头像URL
     * @param avatar 头像URL
     */
    // 设置头像URL方法，接收头像URL参数
    public void setAvatar(String avatar) {
        // 设置头像URL
        this.avatar = avatar;
    }

    /**
     * 获取用户状态
     * @return 用户状态（0-禁用，1-正常）
     */
    // 获取用户状态方法，返回用户状态
    public int getStatus() {
        // 返回用户状态（0-禁用，1-正常）
        return status;
    }

    /**
     * 设置用户状态
     * @param status 用户状态（0-禁用，1-正常）
     */
    // 设置用户状态方法，接收用户状态参数
    public void setStatus(int status) {
        // 设置用户状态（0-禁用，1-正常）
        this.status = status;
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
     * 获取更新时间
     * @return 更新时间
     */
    // 获取更新时间方法，返回更新时间
    public LocalDateTime getUpdateTime() {
        // 返回更新时间
        return updateTime;
    }

    /**
     * 设置更新时间
     * @param updateTime 更新时间
     */
    // 设置更新时间方法，接收更新时间参数
    public void setUpdateTime(LocalDateTime updateTime) {
        // 设置更新时间
        this.updateTime = updateTime;
    }

    /**
     * 获取最后登录时间
     * @return 最后登录时间
     */
    // 获取最后登录时间方法，返回最后登录时间
    public LocalDateTime getLastLoginTime() {
        // 返回最后登录时间
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     * @param lastLoginTime 最后登录时间
     */
    // 设置最后登录时间方法，接收最后登录时间参数
    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        // 设置最后登录时间
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 重写toString方法
     * 功能概述：返回用户对象的字符串表示，用于日志输出和调试
     * @return 用户对象的字符串表示
     */
    // 重写toString方法，返回用户对象的字符串表示
    @Override
    // toString方法，返回用户对象的字符串表示
    public String toString() {
        // 返回用户对象的字符串表示，包含ID、用户名、手机号、邮箱、昵称、状态和创建时间
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
} 