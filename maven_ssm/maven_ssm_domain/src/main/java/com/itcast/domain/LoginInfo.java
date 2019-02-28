package com.itcast.domain;

import java.io.Serializable;

/**
 * @Auther : 32725
 * @Date: 2019/2/27 20:14
 * @Description:
 */
public class LoginInfo implements Serializable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public  void setMsg(String msg) {
        this.msg = msg;
    }
}
