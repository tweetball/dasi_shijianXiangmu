package com.icss.xihu.model;

import lombok.Data;

@Data
public class HotelRoom {
    private int id;
    private String name;
    private String img;
    private String content;
    private int hid;
    private double price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }

    public int getHid() {
        return hid;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "HotelRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                ", hid=" + hid +
                ", price=" + price +
                '}';
    }

}
