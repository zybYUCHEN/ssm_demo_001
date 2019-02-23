package com.itcast.service.impl;

import com.github.pagehelper.PageHelper;
import com.itcast.dao.OrdersDao;
import com.itcast.domain.Orders;
import com.itcast.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 9:45
 * @Description: 订单表的业务层接口实现类
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;


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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Orders> findAll(Integer pageNum, Integer pageSize, String term) throws Exception {
        //1.使用pageHelper物理分页插件
        PageHelper.startPage(pageNum, pageSize);
        //2.处理条件
        if (term != "" && term != null) {
            term = "%" + term + "%";
        }
        //3.执行mybatis查询语句
        return ordersDao.findAll(term);
    }

}
