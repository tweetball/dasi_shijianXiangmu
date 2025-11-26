/**
 * 酒店多房间实体类
 * 功能概述：封装酒店信息及其多个房间信息（一对多关系），用于多表查询结果
 */
// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入Lombok的Data注解，用于自动生成getter/setter、toString、equals、hashCode等方法
import lombok.Data;
// 导入List集合接口
import java.util.List;

/**
 * 酒店多房间实体类
 * 功能概述：封装酒店信息及其多个房间信息（一对多关系），用于多表查询结果
 */
// 使用Lombok的Data注解，自动生成getter/setter、toString、equals、hashCode等方法
@Data
// 酒店多房间实体类，封装酒店信息及其多个房间信息
public class HotelMany {
    // 一对多的效果
    // 一个酒店包含多个房间
    
    // 酒店编号，对应数据库中的id字段，主键，自增
    private int id;
    // 酒店名称，对应数据库中的name字段，酒店的名称
    private String name;
    // 酒店介绍，对应数据库中的content字段，酒店的详细介绍
    private String content;
    // 酒店图片，对应数据库中的img字段，酒店封面图片的URL路径
    private String img;
    // 酒店电话，对应数据库中的tel字段，酒店的联系电话
    private String tel;
    // 酒店地址，对应数据库中的address字段，酒店的详细地址
    private String address;
    // 酒店评分，对应数据库中的score字段，酒店的用户评分（0-5分）
    private double score;
    // 多个房间，对应数据库中的房间表，一个酒店包含多个房间（一对多关系）
    private List<HotelRoom> rList;

    /**
     * 获取酒店编号
     * 功能概述：返回酒店的唯一标识ID
     * @return {int} 返回酒店编号
     */
    // 获取酒店编号方法，返回酒店的唯一标识ID
    public int getId() {
        // 返回酒店编号
        return id;
    }

    /**
     * 获取酒店名称
     * 功能概述：返回酒店的名称
     * @return {String} 返回酒店名称
     */
    // 获取酒店名称方法，返回酒店的名称
    public String getName() {
        // 返回酒店名称
        return name;
    }

    /**
     * 获取酒店介绍
     * 功能概述：返回酒店的详细介绍
     * @return {String} 返回酒店介绍
     */
    // 获取酒店介绍方法，返回酒店的详细介绍
    public String getContent() {
        // 返回酒店介绍
        return content;
    }

    /**
     * 获取酒店图片
     * 功能概述：返回酒店封面图片的URL路径
     * @return {String} 返回酒店图片URL
     */
    // 获取酒店图片方法，返回酒店封面图片的URL路径
    public String getImg() {
        // 返回酒店图片URL
        return img;
    }

    /**
     * 获取酒店电话
     * 功能概述：返回酒店的联系电话
     * @return {String} 返回酒店电话
     */
    // 获取酒店电话方法，返回酒店的联系电话
    public String getTel() {
        // 返回酒店电话
        return tel;
    }

    /**
     * 获取酒店地址
     * 功能概述：返回酒店的详细地址
     * @return {String} 返回酒店地址
     */
    // 获取酒店地址方法，返回酒店的详细地址
    public String getAddress() {
        // 返回酒店地址
        return address;
    }

    /**
     * 获取酒店评分
     * 功能概述：返回酒店的用户评分
     * @return {double} 返回酒店评分（0-5分）
     */
    // 获取酒店评分方法，返回酒店的用户评分
    public double getScore() {
        // 返回酒店评分
        return score;
    }

    /**
     * 获取房间列表
     * 功能概述：返回该酒店的所有房间列表
     * @return {List<HotelRoom>} 返回房间列表
     */
    // 获取房间列表方法，返回该酒店的所有房间列表
    public List<HotelRoom> getrList() {
        // 返回房间列表
        return rList;
    }

    /**
     * 重写toString方法
     * 功能概述：将酒店多房间对象转换为字符串格式，用于日志输出和调试
     * @return {string} 返回酒店多房间对象的字符串表示
     */
    // 重写Object类的toString方法，返回酒店多房间对象的字符串表示
    @Override
    // toString方法，将酒店多房间对象转换为字符串格式
    public String toString() {
        // 返回酒店多房间对象的字符串表示，包含所有字段的值
        return "HotelMany{" +
                "id=" + id +                                    // 酒店编号
                ", name='" + name + '\'' +                     // 酒店名称
                ", content='" + content + '\'' +               // 酒店介绍
                ", img='" + img + '\'' +                       // 酒店图片
                ", tel='" + tel + '\'' +                       // 酒店电话
                ", address='" + address + '\'' +               // 酒店地址
                ", score=" + score +                           // 酒店评分
                ", rList=" + rList +                           // 房间列表
                '}';
    }

}
