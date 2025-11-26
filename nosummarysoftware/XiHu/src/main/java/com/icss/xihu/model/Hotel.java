package com.icss.xihu.model;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Hotel {
    //封装hotel的属性
    //需要和数据库中的字段数量和类型相同，含义一样
    //酒店编号
    private int id;
    //酒店名称
    private String name;
    //酒店介绍
    private String content;
    //酒店图片
    private String img;
    //酒店电话
    private String tel;
    //酒店地址
    private String address;
    //酒店星级
    private String level;
    //酒店评分
    private double score;
    //省
    private String province;
    //市
    private String city;
    //最低价格
    private double minPrice;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", level='" + level + '\'' +
                ", score=" + score +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", minPrice=" + minPrice +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getLevel() {
        return level;
    }

    public double getScore() {
        return score;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public double getMinPrice() {
        return minPrice;
    }
}
