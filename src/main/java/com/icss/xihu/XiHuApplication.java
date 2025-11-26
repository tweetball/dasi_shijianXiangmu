// 定义包路径，标识该类属于com.icss.xihu包
package com.icss.xihu;

// 导入MyBatis的MapperScan注解，用于扫描Mapper接口
import org.mybatis.spring.annotation.MapperScan;
// 导入Spring Boot的SpringApplication类，用于启动应用
import org.springframework.boot.SpringApplication;
// 导入Spring Boot的自动配置注解
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 导入Spring的定时任务启用注解
import org.springframework.scheduling.annotation.EnableScheduling;

// Spring Boot应用主类注解，包含@Configuration、@EnableAutoConfiguration、@ComponentScan
@SpringBootApplication
// 启用Spring定时任务功能
@EnableScheduling
// 指定MyBatis Mapper接口的扫描路径，自动注册Mapper接口为Spring Bean
@MapperScan("com.icss.xihu.mapper")
// 游市生活平台主启动类
public class XiHuApplication {

    // 应用程序入口方法，JVM启动时调用
    public static void main(String[] args) {
        // 启动Spring Boot应用，传入主类和命令行参数
        SpringApplication.run(XiHuApplication.class, args);
    }

}                             
