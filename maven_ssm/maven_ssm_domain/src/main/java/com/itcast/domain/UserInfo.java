package com.itcast.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 20:41
 * @Description: 用户表的实体类
 */
public class UserInfo implements Serializable,Cloneable {

    private String id; //用户的id
    private String username; //用户名
    private String email;   //用户邮箱
    private String password; //用户密码
    private String phoneNum;//用户手机号
    private Integer status; //用户状态 0：未激活 1：已激活
    private String statusStr; //用户状态字符串表示

    //用户和角色的关联关系：多对多
    private List<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        if (status!=null){
            if (status==0){
                statusStr="未激活";
            }else if (status==1){
                statusStr="已激活";
            }
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
