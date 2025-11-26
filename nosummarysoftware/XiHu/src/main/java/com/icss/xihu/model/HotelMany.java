package com.icss.xihu.model;

import lombok.Data;
import java.util.List;

@Data
public class HotelMany {
    //一对多的效果
    //一个酒店包含多个房间
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
    //多个房间
    private List<HotelRoom> rList;
    //酒店评分
    private double score;

    @Override
    public String toString() {
        return "HotelMany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", rList=" + rList +
                ", score=" + score +
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

    public List<HotelRoom> getrList() {
        return rList;
    }

    public double getScore() {
        return score;
    }
}
