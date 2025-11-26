// 定义包路径，标识该类属于com.icss.xihu.controller包
package com.icss.xihu.controller;

// 导入用户实体类
import com.icss.xihu.model.User;
// 导入用户服务接口
import com.icss.xihu.service.UserService;
// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring MVC的控制器注解
import org.springframework.stereotype.Controller;
// 导入Spring MVC的请求映射相关注解
import org.springframework.web.bind.annotation.*;
// 导入Spring MVC的模型对象，用于向视图传递数据
import org.springframework.ui.Model;

// 导入HTTP会话对象
import jakarta.servlet.http.HttpSession;
// 导入HashMap集合类
import java.util.HashMap;
// 导入Map接口
import java.util.Map;

// 标识该类为Spring MVC控制器，处理HTTP请求
@Controller
// 指定该控制器的请求路径前缀为"/user"
@RequestMapping("/user")
// 用户控制器类，处理用户相关的HTTP请求，包括登录、注册、个人信息管理等
public class UserController {

    // 自动注入用户服务，Spring容器会自动查找并注入UserService的实现类
    @Autowired
    // 用户服务对象，用于调用用户相关的业务逻辑
    private UserService userService;

    /**
     * 显示登录页面
     * 功能概述：处理登录页面的请求，返回登录页面视图
     */
    // 处理登录页面请求，映射路径"/login"
    @RequestMapping("/login")
    // 登录页面处理方法，返回login.html模板
    public String loginPage() {
        // 返回视图名称"login"，Thymeleaf会自动查找templates/login.html
        return "login";
    }

    /**
     * 显示注册页面
     * 功能概述：处理注册页面的请求，返回注册页面视图
     */
    // 处理注册页面请求，映射路径"/register"
    @RequestMapping("/register")
    // 注册页面处理方法，返回register.html模板
    public String registerPage() {
        // 返回视图名称"register"，Thymeleaf会自动查找templates/register.html
        return "register";
    }

    /**
     * 显示个人信息页面
     * 功能概述：处理个人信息页面的请求，需要用户登录，获取最新用户信息并传递给视图
     */
    // 处理个人信息页面请求，映射路径"/profile"
    @RequestMapping("/profile")
    // 个人信息页面处理方法，需要登录才能访问
    public String profilePage(HttpSession session, Model model) {
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，重定向到登录页面
            return "redirect:/user/login";
        }
        
        // 获取最新的用户信息
        // 根据用户ID从数据库查询最新的用户信息
        User currentUser = userService.findById(user.getId());
        // 将用户信息添加到模型中，供前端模板使用，键名为"user"
        model.addAttribute("user", currentUser);
        // 返回视图名称"profile"，Thymeleaf会自动查找templates/profile.html
        return "profile";
    }

    /**
     * 发送短信验证码
     * 功能概述：根据不同的业务类型（注册、登录、重置密码）发送短信验证码，并进行相应的业务校验
     */
    // 处理发送短信验证码请求，映射路径"/sendSms"
    @RequestMapping("/sendSms")
    // 返回JSON格式的响应体，而不是视图
    @ResponseBody
    // 发送短信验证码处理方法，接收手机号和业务类型参数
    public Map<String, Object> sendSmsCode(@RequestParam("phone") String phone, 
                                         @RequestParam("type") String type) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 验证手机号格式
        // 调用isValidPhone方法验证手机号格式是否正确
        if (!isValidPhone(phone)) {
            // 如果格式不正确，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "手机号格式不正确");
            // 返回结果
            return result;
        }
        
        // 如果是注册，检查手机号是否已存在
        // 判断业务类型是否为注册
        if ("register".equals(type)) {
            // 根据手机号查询用户是否已存在
            User existingUser = userService.findByPhone(phone);
            // 判断用户是否已存在
            if (existingUser != null) {
                // 如果已存在，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "手机号已注册");
                // 返回结果
                return result;
            }
        }
        
        // 如果是登录或重置密码，检查手机号是否存在
        // 判断业务类型是否为登录或重置密码
        if ("login".equals(type) || "reset".equals(type)) {
            // 根据手机号查询用户是否存在
            User existingUser = userService.findByPhone(phone);
            // 判断用户是否存在
            if (existingUser == null) {
                // 如果不存在，设置返回结果为失败
                result.put("success", false);
                // 设置错误消息
                result.put("message", "手机号未注册");
                // 返回结果
                return result;
            }
        }
        
        // 调用用户服务的sendSmsCode方法发送短信验证码
        boolean success = userService.sendSmsCode(phone, type);
        // 设置返回结果的成功标志
        result.put("success", success);
        // 根据发送结果设置相应的消息
        result.put("message", success ? "验证码发送成功" : "验证码发送失败");
        // 返回结果
        return result;
    }

    /**
     * 用户注册
     * 功能概述：处理用户注册请求，验证注册参数（用户名、密码、手机号、验证码），调用服务层完成注册
     */
    // 处理用户注册请求，映射路径"/doRegister"
    @RequestMapping("/doRegister")
    // 返回JSON格式的响应体
    @ResponseBody
    // 用户注册处理方法，接收用户名、密码、手机号和短信验证码参数
    public Map<String, Object> doRegister(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("smsCode") String smsCode) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 参数验证
        // 验证用户名是否为空或只包含空白字符
        if (username == null || username.trim().isEmpty()) {
            // 如果验证失败，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "用户名不能为空");
            // 返回结果
            return result;
        }
        
        // 验证密码是否为空或长度小于6位
        if (password == null || password.length() < 6) {
            // 如果验证失败，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "密码长度不能少于6位");
            // 返回结果
            return result;
        }
        
        // 验证手机号格式是否正确
        if (!isValidPhone(phone)) {
            // 如果验证失败，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "手机号格式不正确");
            // 返回结果
            return result;
        }
        
        // 调用用户服务的register方法完成注册
        String registerResult = userService.register(username, password, phone, smsCode);
        // 判断注册结果是否为"success"
        boolean success = "success".equals(registerResult);
        // 设置返回结果的成功标志
        result.put("success", success);
        // 根据注册结果设置相应的消息，成功则返回"注册成功"，失败则返回服务层返回的错误信息
        result.put("message", success ? "注册成功" : registerResult);
        // 返回结果
        return result;
    }

    /**
     * 密码登录
     * 功能概述：处理用户密码登录请求，验证手机号和密码，登录成功后保存用户信息到会话
     */
    // 处理密码登录请求，映射路径"/doLogin"
    @RequestMapping("/doLogin")
    // 返回JSON格式的响应体
    @ResponseBody
    // 密码登录处理方法，接收手机号和密码参数
    public Map<String, Object> doLogin(@RequestParam("phone") String phone,
                                     @RequestParam("password") String password,
                                     HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 验证手机号格式是否正确
        if (!isValidPhone(phone)) {
            // 如果验证失败，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "手机号格式不正确");
            // 返回结果
            return result;
        }
        
        // 调用用户服务的login方法进行登录验证
        User user = userService.login(phone, password);
        // 判断登录是否成功（用户对象不为null）
        if (user != null) {
            // 登录成功，将用户信息保存到会话中，键名为"user"
            session.setAttribute("user", user);
            // 设置返回结果为成功
            result.put("success", true);
            // 设置成功消息
            result.put("message", "登录成功");
            // 将用户信息添加到返回结果中
            result.put("user", user);
        // 登录失败
        } else {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "手机号或密码错误");
        }
        // 返回结果
        return result;
    }

    /**
     * 验证码登录
     * 功能概述：处理用户验证码登录请求，验证手机号和短信验证码，登录成功后保存用户信息到会话
     */
    // 处理验证码登录请求，映射路径"/doSmsLogin"
    @RequestMapping("/doSmsLogin")
    // 返回JSON格式的响应体
    @ResponseBody
    // 验证码登录处理方法，接收手机号和短信验证码参数
    public Map<String, Object> doSmsLogin(@RequestParam("phone") String phone,
                                        @RequestParam("smsCode") String smsCode,
                                        HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 验证手机号格式是否正确
        if (!isValidPhone(phone)) {
            // 如果验证失败，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "手机号格式不正确");
            // 返回结果
            return result;
        }
        
        // 调用用户服务的loginWithSmsCode方法进行验证码登录验证
        User user = userService.loginWithSmsCode(phone, smsCode);
        // 判断登录是否成功（用户对象不为null）
        if (user != null) {
            // 登录成功，将用户信息保存到会话中，键名为"user"
            session.setAttribute("user", user);
            // 设置返回结果为成功
            result.put("success", true);
            // 设置成功消息
            result.put("message", "登录成功");
            // 将用户信息添加到返回结果中
            result.put("user", user);
        // 登录失败
        } else {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "验证码错误或已过期");
        }
        // 返回结果
        return result;
    }

    /**
     * 更新用户信息
     * 功能概述：处理用户信息更新请求，需要用户登录，更新昵称和邮箱等信息，并同步更新会话中的用户信息
     */
    // 处理更新用户信息请求，映射路径"/updateProfile"
    @RequestMapping("/updateProfile")
    // 返回JSON格式的响应体
    @ResponseBody
    // 更新用户信息处理方法，接收昵称和邮箱参数（邮箱为可选）
    public Map<String, Object> updateProfile(@RequestParam("nickname") String nickname,
                                           @RequestParam(value = "email", required = false) String email,
                                           HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 从会话中获取当前登录的用户对象
        User sessionUser = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (sessionUser == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }
        
        // 根据用户ID从数据库查询用户信息
        User user = userService.findById(sessionUser.getId());
        // 判断用户是否存在
        if (user != null) {
            // 设置用户昵称
            user.setNickname(nickname);
            // 设置用户邮箱
            user.setEmail(email);
            
            // 调用用户服务的updateUser方法更新用户信息
            boolean success = userService.updateUser(user);
            // 设置返回结果的成功标志
            result.put("success", success);
            // 根据更新结果设置相应的消息
            result.put("message", success ? "更新成功" : "更新失败");
            
            // 如果更新成功，同步更新会话中的用户信息
            if (success) {
                // 更新session中的用户信息
                // 重新从数据库查询更新后的用户信息
                User updatedUser = userService.findById(user.getId());
                // 将更新后的用户信息保存到会话中
                session.setAttribute("user", updatedUser);
                // 将更新后的用户信息添加到返回结果中
                result.put("user", updatedUser);
            }
        // 用户不存在
        } else {
            // 设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "用户不存在");
        }
        
        // 返回结果
        return result;
    }

    /**
     * 修改密码
     * 功能概述：处理用户密码修改请求，需要用户登录，验证原密码后更新为新密码
     */
    // 处理修改密码请求，映射路径"/changePassword"
    @RequestMapping("/changePassword")
    // 返回JSON格式的响应体
    @ResponseBody
    // 修改密码处理方法，接收原密码和新密码参数
    public Map<String, Object> changePassword(@RequestParam("oldPassword") String oldPassword,
                                            @RequestParam("newPassword") String newPassword,
                                            HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录
        if (user == null) {
            // 如果未登录，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "请先登录");
            // 返回结果
            return result;
        }
        
        // 验证新密码长度是否小于6位
        if (newPassword.length() < 6) {
            // 如果验证失败，设置返回结果为失败
            result.put("success", false);
            // 设置错误消息
            result.put("message", "新密码长度不能少于6位");
            // 返回结果
            return result;
        }
        
        // 调用用户服务的changePassword方法修改密码
        boolean success = userService.changePassword(user.getId(), oldPassword, newPassword);
        // 设置返回结果的成功标志
        result.put("success", success);
        // 根据修改结果设置相应的消息，成功则返回"密码修改成功"，失败则返回"原密码错误"
        result.put("message", success ? "密码修改成功" : "原密码错误");
        // 返回结果
        return result;
    }

    /**
     * 用户退出
     * 功能概述：处理用户退出登录请求，清除会话中的用户信息，并重定向到首页
     */
    // 处理用户退出请求，映射路径"/logout"
    @RequestMapping("/logout")
    // 用户退出处理方法，清除会话并重定向
    public String logout(HttpSession session) {
        // 从会话中移除用户信息，键名为"user"
        session.removeAttribute("user");
        // 重定向到首页
        return "redirect:/";
    }

    /**
     * 获取登录会话状态
     * 功能概述：供前端判断用户是否已登录，返回登录状态和用户基本信息
     */
    // 处理获取会话状态请求，映射路径"/session"，只接受GET请求
    @GetMapping("/session")
    // 返回JSON格式的响应体
    @ResponseBody
    // 获取会话状态处理方法，返回当前登录状态
    public Map<String, Object> getSession(HttpSession session) {
        // 创建结果Map对象，用于封装返回数据
        Map<String, Object> result = new HashMap<>();
        // 从会话中获取当前登录的用户对象
        User user = (User) session.getAttribute("user");
        // 判断用户是否已登录（用户对象不为null表示已登录）
        boolean loggedIn = user != null;
        // 设置登录状态标志
        result.put("loggedIn", loggedIn);
        // 如果已登录，添加用户基本信息到返回结果
        if (loggedIn) {
            // 创建用户信息Map对象
            Map<String, Object> u = new HashMap<>();
            // 添加用户ID
            u.put("id", user.getId());
            // 添加用户名
            u.put("username", user.getUsername());
            // 添加用户昵称
            u.put("nickname", user.getNickname());
            // 将用户信息添加到返回结果中
            result.put("user", u);
        }
        // 返回结果
        return result;
    }

    /**
     * 验证手机号格式
     * 功能概述：验证手机号是否符合中国大陆手机号格式（1开头，第二位为3-9，共11位数字）
     */
    // 私有方法，验证手机号格式
    private boolean isValidPhone(String phone) {
        // 判断手机号不为null且符合正则表达式：1开头，第二位为3-9，后面9位为数字
        return phone != null && phone.matches("^1[3-9]\\d{9}$");
    }
} 