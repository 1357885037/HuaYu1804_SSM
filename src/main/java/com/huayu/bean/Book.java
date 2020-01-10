package com.huayu.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "book")
public class Book implements Serializable {

    @TableId(value = "number" ,type = IdType.UUID)
    private  String number;
    private String name;
    private String zuoze;
    private double price;
    private String jianjie;
    private Integer nid;
    private String datebook;

    @TableField( exist = false)
    private Neibie leibie;

    private  String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Neibie getLeibie() {
        return leibie;
    }

    public void setLeibie(Neibie leibie) {
        this.leibie = leibie;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZuoze() {
        return zuoze;
    }

    public void setZuoze(String zuoze) {
        this.zuoze = zuoze;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getDatebook() {
        return datebook;
    }

    public void setDatebook(String datebook) {
        this.datebook = datebook;
    }
}
