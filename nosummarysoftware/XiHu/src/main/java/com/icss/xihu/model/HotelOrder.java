package com.icss.xihu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HotelOrder {
    // 订单编号
    private int id;
    // 酒店编号
    private int hid;
    // 房型编号
    private int rid;
    // 用户id
    private int uid;
    // 房型名称
    private String rname;
    // 入住日期
    private String indate;
    // 退房日期
    private String outdate;
    // 入住人数
    private int guests;
    // 联系人姓名
    private String name;
    // 联系电话
    private String tel;
    // 订单备注
    private String notes;
    // 订单状态
    private int status;

    // 指定日期格式，时区和是否忽略空值
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatTime;

    // 订单创建时间

    // 订单总价
    private double price;

    // 添加缺失的属性
    private String hotelName;  // 酒店名称
    private String hotelImg;   // 酒店图片


    // 返回格式化后的日期字符串
    public String getCreatTimeStr() {
        if (creatTime == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(creatTime);
    }


    // toString 方法也需要更新
    @Override
    public String toString() {
        return "HotelOrder{" +
                "id=" + id +
                ", hid=" + hid +
                ", rid=" + rid +
                ", uid=" + uid +
                ", rname='" + rname + '\'' +
                ", indate='" + indate + '\'' +
                ", outdate='" + outdate + '\'' +
                ", guests=" + guests +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                ", creatTime=" + creatTime +
                ", price=" + price +
                ", hotelName='" + hotelName + '\'' +  // 新增属性
                ", hotelImg='" + hotelImg + '\'' +    // 新增属性
                '}';
    }

    public int getId() {
        return id;
    }

    public int getHid() {
        return hid;
    }

    public int getRid() {
        return rid;
    }

    public int getUid() {
        return uid;
    }

    public String getRname() {
        return rname;
    }

    public String getIndate() {
        return indate;
    }

    public String getOutdate() {
        return outdate;
    }

    public int getGuests() {
        return guests;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getNotes() {
        return notes;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public double getPrice() {
        return price;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelImg() {
        return hotelImg;
    }
}