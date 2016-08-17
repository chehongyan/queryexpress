package com.example.myapplication.bean;

/**
 * Created by 59427 on 2016/6/29.
 */
public class ExpBean {
    private String expName;
    private String expNo;
    private String imgUrl;
    private String status;
    private int number;


    public ExpBean() {
    }

    public ExpBean(String expName, String expNo, String imgUrl, String status, int number) {
        this.expName = expName;
        this.expNo = expNo;
        this.imgUrl = imgUrl;
        this.status = status;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo;
    }

    @Override
    public String toString() {
        return "ExpBean{" +
                "expName='" + expName + '\'' +
                ", expNo='" + expNo + '\'' +
                '}';
    }
}
