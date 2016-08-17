package com.example.myapplication.bean;

/**
 * Created by 59427 on 2016/6/30.
 */
public class ExpressBean {
    String expName;
    String phone;
    String imgUrl;

    public ExpressBean() {
    }

    public ExpressBean(String expName, String phone, String imgUrl) {
        this.expName = expName;
        this.phone = phone;
        this.imgUrl = imgUrl;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ExpressBean{" +
                "expName='" + expName + '\'' +
                ", phone='" + phone + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
