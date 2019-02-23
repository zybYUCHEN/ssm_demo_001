package com.itcast.domain;

import java.io.Serializable;

/**
 * @Auther : 32725
 * @Date: 2019/2/20 13:45
 * @Description: 修改选中商品的状态
 */
public class UpdateStatus implements Serializable {
    private Integer status; //要修改的商品的id
    private String id;//商品要改成的状态

    public UpdateStatus(String id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
