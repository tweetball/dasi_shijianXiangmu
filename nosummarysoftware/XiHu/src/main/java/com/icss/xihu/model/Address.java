package com.icss.xihu.model;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Address {
    private int id;
    private int pid;
    private String deep;
    private String name;
    private String ext_name;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", pid=" + pid +
                ", deep='" + deep + '\'' +
                ", name='" + name + '\'' +
                ", ext_name='" + ext_name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public String getDeep() {
        return deep;
    }

    public String getName() {
        return name;
    }

    public String getExt_name() {
        return ext_name;
    }
}
