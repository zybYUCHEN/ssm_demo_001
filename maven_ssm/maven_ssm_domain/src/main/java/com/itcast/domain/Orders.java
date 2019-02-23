package com.itcast.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 9:06
 * @Description: 订单表的实体类
 */
public class Orders implements Serializable {

    private String id; //订单表的id
    private String orderNum; //订单编号
    private Date orderTime; //下单时间
    private String orderTimeStr; //订单的下单时间的字符串表示形式格式为： yyyy-MM-dd hh:mm
    private Integer orderStatus;    //订单的状态 订单状态(0 未支付 1 已支付)
    private String orderStatusStr; //订单状态的字符串表示形式
    private Integer peopleCount;    //出行人数
    private Integer payType;    //支付方式：支付方式(0 支付宝 1 微信 2其它)
    private String payTypeStr;  //支付方式的字符串表示形式
    private String orderDesc;   //订单描述
    private String productId; //外键，商品表的id
    private String memberId; //外键，会员表的id

    //订单与商品的关联注入：对一关系
    private Product product;


    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTimeStr() {
        if (orderTime!=null){
            orderTimeStr= new SimpleDateFormat("yyyy-MM-dd hh:mm").format(orderTime);
        }
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }


    public String getOrderStatusStr() {
        if (orderStatus!=null){
            if (orderStatus==1){
                orderStatusStr="已支付";
            }else if(orderStatus==0) {
                orderStatusStr="未支付";
            }
        }
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeStr() {
        if (payType!=null){
            if (payType==0){
                payTypeStr="支付宝";
            }else if (payType==1){
                payTypeStr="微信";
            }else if (payType==2){
                payTypeStr="其他";
            }
        }
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
