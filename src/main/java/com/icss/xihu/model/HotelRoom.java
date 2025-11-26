/**
 * 酒店房间实体类
 * 功能概述：封装酒店房间信息，对应数据库中的hotel_room表，包含房间的基本信息、价格等
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入Lombok的Data注解，用于自动生成getter/setter、toString、equals、hashCode等方法
import lombok.Data;

/**
 * 酒店房间实体类
 * 功能概述：封装酒店房间信息，对应数据库中的hotel_room表，包含房间的基本信息、价格等
 */
// 使用Lombok的Data注解，自动生成getter/setter、toString、equals、hashCode等方法
@Data
// 酒店房间实体类，封装酒店房间信息
public class HotelRoom {
    // 房间编号，对应数据库中的id字段，主键，自增
    private int id;
    // 房间名称，对应数据库中的name字段，房间的名称（如"标准间"、"豪华间"等）
    private String name;
    // 房间图片，对应数据库中的img字段，房间图片的URL路径
    private String img;
    // 房间介绍，对应数据库中的content字段，房间的详细介绍
    private String content;
    // 酒店编号，对应数据库中的hid字段，关联酒店表的id字段（外键）
    private int hid;
    // 房间价格，对应数据库中的price字段，房间的价格（元/晚）
    private double price;
    // 房间是否满人，对应数据库中的full字段，0=未满人，1=已满人
    private int full;

    /**
     * 获取房间编号
     * 功能概述：返回房间的唯一标识ID
     * @return {int} 返回房间编号
     */
    // 获取房间编号方法，返回房间的唯一标识ID
    public int getId() {
        // 返回房间编号
        return id;
    }

    /**
     * 获取房间名称
     * 功能概述：返回房间的名称
     * @return {String} 返回房间名称
     */
    // 获取房间名称方法，返回房间的名称
    public String getName() {
        // 返回房间名称
        return name;
    }

    /**
     * 获取房间图片
     * 功能概述：返回房间图片的URL路径
     * @return {String} 返回房间图片URL
     */
    // 获取房间图片方法，返回房间图片的URL路径
    public String getImg() {
        // 返回房间图片URL
        return img;
    }

    /**
     * 获取房间介绍
     * 功能概述：返回房间的详细介绍
     * @return {String} 返回房间介绍
     */
    // 获取房间介绍方法，返回房间的详细介绍
    public String getContent() {
        // 返回房间介绍
        return content;
    }

    /**
     * 获取酒店编号
     * 功能概述：返回房间所属的酒店编号
     * @return {int} 返回酒店编号
     */
    // 获取酒店编号方法，返回房间所属的酒店编号
    public int getHid() {
        // 返回酒店编号
        return hid;
    }

    /**
     * 获取房间价格
     * 功能概述：返回房间的价格
     * @return {double} 返回房间价格（元/晚）
     */
    // 获取房间价格方法，返回房间的价格
    public double getPrice() {
        // 返回房间价格
        return price;
    }

    /**
     * 重写toString方法
     * 功能概述：将房间对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回房间对象的字符串表示
     */
    // 重写Object类的toString方法，返回房间对象的字符串表示
    @Override
    // toString方法，将房间对象转换为字符串格式
    public String toString() {
        // 返回房间对象的字符串表示，包含所有字段的值
        return "HotelRoom{" +
                "id=" + id +                                    // 房间编号
                ", name='" + name + '\'' +                     // 房间名称
                ", img='" + img + '\'' +                       // 房间图片
                ", content='" + content + '\'' +                // 房间介绍
                ", hid=" + hid +                                // 酒店编号
                ", price=" + price +                            // 房间价格
                '}';
    }

}
