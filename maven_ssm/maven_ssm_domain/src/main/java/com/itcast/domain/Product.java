package com.itcast.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther : 32725
 * @Date: 2019/2/19 16:37
 * @Description: 商品表的实体类
 */
public class Product implements Serializable {
    private String id; // 主键
    private String productNum; // 编号 唯一
    private String productName; // 产品名称
    private String cityName; // 出发城市
    private Date departureTime; // 出发时间
    private String departureTimeStr;  //对出发时间进行格式化
    private double productPrice; // 产品价格
    private String productDesc; // 产品描述
    private Integer productStatus; // 状态 0 关闭 1 开启
    private String productStatusStr; //对状态进行格式化，0关闭，1开启


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getDepartureTime() {

        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {

        this.departureTime = departureTime;
    }

    public String getDepartureTimeStr() {
        if (departureTime!=null){
            departureTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(departureTime);
        }
        return departureTimeStr;
    }

    public void setDepartureTimeStr(String departureTimeStr) {
       this.departureTimeStr = departureTimeStr;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatusStr() {
            if (productStatus == 1) {
                this.departureTimeStr = "开启";
            } else {
                this.departureTimeStr = "关闭";
            }
        return departureTimeStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr =productStatusStr;
    }
}
