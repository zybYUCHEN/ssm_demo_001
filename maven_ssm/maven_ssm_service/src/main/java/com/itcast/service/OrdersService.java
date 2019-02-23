package com.itcast.service;

import com.itcast.domain.Orders;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 9:43
 * @Description: 订单表的业务层接口
 */
public interface OrdersService {

//--------------------------------------保存操作——————————————————————//


//--------------------------------------更新操作——————————————————————//


//--------------------------------------删除操作——————————————————————//


//--------------------------------------查询操作——————————————————————//

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.Orders>
     * @Description: 查询所有订单信息包含商品信息：一对一关系，立即加载
     **/
    List<Orders> findAll(Integer pageNum,Integer pageSize,String term)throws Exception;

}
