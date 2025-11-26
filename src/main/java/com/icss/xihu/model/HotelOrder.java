// 定义包路径，标识该类属于com.icss.xihu.model包
package com.icss.xihu.model;

// 导入Jackson的JsonFormat注解，用于JSON日期格式化
import com.fasterxml.jackson.annotation.JsonFormat;
// 导入Lombok的Data注解，自动生成getter/setter等方法
import lombok.Data;

// 导入SimpleDateFormat类，用于日期格式化
import java.text.SimpleDateFormat;
// 导入Date类，用于处理日期时间
import java.util.Date;

/**
 * 酒店订单实体类
 * 功能概述：表示系统中的酒店订单信息，包括酒店信息、房型信息、入住退房日期、联系人信息、订单状态等
 */
// 使用Lombok的Data注解，自动生成getter/setter、toString、equals、hashCode等方法
@Data
// 酒店订单实体类，对应数据库中的hotel_order表
public class HotelOrder {
    // 订单编号，主键，自增
    private int id;
    // 订单号，唯一标识，格式：HOTEL + 时间戳 + 随机数
    private String orderNo;
    // 统一订单号，关联unified_order表的order_no字段
    private String unifiedOrderNo;
    // 酒店编号，关联hotel表的id字段
    private int hid;
    // 房型编号，关联hotel_room表的id字段
    private int rid;
    // 用户ID，关联user表的id字段
    private int uid;
    // 房型名称，用于显示
    private String rname;
    // 入住日期，格式：yyyy-MM-dd
    private String indate;
    // 退房日期，格式：yyyy-MM-dd
    private String outdate;
    // 入住人数，记录订单的入住人数
    private int guests;
    // 联系人姓名，用于联系和确认订单
    private String name;
    // 联系电话，用于联系和确认订单
    private String tel;
    // 订单备注，用户填写的特殊要求或备注信息
    private String notes;
    // 订单状态，0=待支付，1=已支付，2=已取消
    private int status;

    // 指定日期格式，时区和是否忽略空值
    // 使用JsonFormat注解，指定JSON序列化时的日期格式为"yyyy-MM-dd HH:mm:ss"，时区为GMT+8
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    // 创建时间，记录订单创建的时间
    private Date creatTime;

    // 订单总价，使用double类型存储订单总金额
    private double price;

    // 添加缺失的属性
    // 酒店名称，用于显示，从hotel表关联查询得到
    private String hotelName;
    // 酒店图片URL，用于显示，从hotel表关联查询得到
    private String hotelImg;

    /**
     * 获取格式化后的创建时间字符串
     * 功能概述：将创建时间格式化为"yyyy-MM-dd HH:mm:ss"格式的字符串，用于前端显示
     * @return 格式化后的创建时间字符串，如果创建时间为空则返回空字符串
     */
    // 返回格式化后的日期字符串方法，返回格式化后的创建时间字符串
    public String getCreatTimeStr() {
        // 判断创建时间是否为空
        if (creatTime == null) {
            // 如果为空，返回空字符串
            return "";
        }
        // 创建SimpleDateFormat对象，指定日期格式为"yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化创建时间并返回
        return sdf.format(creatTime);
    }

    /**
     * 重写toString方法
     * 功能概述：返回酒店订单对象的字符串表示，用于日志输出和调试
     * @return 酒店订单对象的字符串表示
     */
    // toString方法也需要更新，返回酒店订单对象的字符串表示
    @Override
    // toString方法，返回酒店订单对象的字符串表示
    public String toString() {
        // 返回酒店订单对象的字符串表示，包含ID、酒店ID、房型ID、用户ID、房型名称、入住退房日期、入住人数、联系人信息、订单状态、创建时间、价格、酒店名称和图片
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
                ", hotelName='" + hotelName + '\'' +
                ", hotelImg='" + hotelImg + '\'' +
                '}';
    }

    /**
     * 获取订单ID
     * @return 订单ID
     */
    // 获取订单ID方法，返回订单ID
    public int getId() {
        // 返回订单ID
        return id;
    }

    /**
     * 获取酒店ID
     * @return 酒店ID
     */
    // 获取酒店ID方法，返回酒店ID
    public int getHid() {
        // 返回酒店ID
        return hid;
    }

    /**
     * 获取房型ID
     * @return 房型ID
     */
    // 获取房型ID方法，返回房型ID
    public int getRid() {
        // 返回房型ID
        return rid;
    }

    /**
     * 获取用户ID
     * @return 用户ID
     */
    // 获取用户ID方法，返回用户ID
    public int getUid() {
        // 返回用户ID
        return uid;
    }

    /**
     * 获取房型名称
     * @return 房型名称
     */
    // 获取房型名称方法，返回房型名称
    public String getRname() {
        // 返回房型名称
        return rname;
    }

    /**
     * 获取入住日期
     * @return 入住日期
     */
    // 获取入住日期方法，返回入住日期
    public String getIndate() {
        // 返回入住日期
        return indate;
    }

    /**
     * 获取退房日期
     * @return 退房日期
     */
    // 获取退房日期方法，返回退房日期
    public String getOutdate() {
        // 返回退房日期
        return outdate;
    }

    /**
     * 获取入住人数
     * @return 入住人数
     */
    // 获取入住人数方法，返回入住人数
    public int getGuests() {
        // 返回入住人数
        return guests;
    }

    /**
     * 获取联系人姓名
     * @return 联系人姓名
     */
    // 获取联系人姓名方法，返回联系人姓名
    public String getName() {
        // 返回联系人姓名
        return name;
    }

    /**
     * 获取联系电话
     * @return 联系电话
     */
    // 获取联系电话方法，返回联系电话
    public String getTel() {
        // 返回联系电话
        return tel;
    }

    /**
     * 获取订单备注
     * @return 订单备注
     */
    // 获取订单备注方法，返回订单备注
    public String getNotes() {
        // 返回订单备注
        return notes;
    }

    /**
     * 获取订单状态
     * @return 订单状态（0=待支付，1=已支付，2=已取消）
     */
    // 获取订单状态方法，返回订单状态
    public int getStatus() {
        // 返回订单状态
        return status;
    }

    /**
     * 获取创建时间
     * @return 创建时间
     */
    // 获取创建时间方法，返回创建时间
    public Date getCreatTime() {
        // 返回创建时间
        return creatTime;
    }

    /**
     * 获取订单总价
     * @return 订单总价
     */
    // 获取订单总价方法，返回订单总价
    public double getPrice() {
        // 返回订单总价
        return price;
    }

    /**
     * 获取酒店名称
     * @return 酒店名称
     */
    // 获取酒店名称方法，返回酒店名称
    public String getHotelName() {
        // 返回酒店名称
        return hotelName;
    }

    /**
     * 获取酒店图片URL
     * @return 酒店图片URL
     */
    // 获取酒店图片URL方法，返回酒店图片URL
    public String getHotelImg() {
        // 返回酒店图片URL
        return hotelImg;
    }
} 