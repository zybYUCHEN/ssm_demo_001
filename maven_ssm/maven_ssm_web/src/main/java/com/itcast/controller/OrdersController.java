package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.Orders;
import com.itcast.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 9:29
 * @Description: 订单表的控制类
 */
@Controller
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

//--------------------------------------保存操作——————————————————————//


//--------------------------------------更新操作——————————————————————//


//--------------------------------------删除操作——————————————————————//


    //--------------------------------------查询操作——————————————————————//
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findAll(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer _pageSize,
                          @RequestParam(name = "term",required = false,defaultValue = "")String term,
                          Model model) throws Exception {
        //0.添加页面控制每页展示数据量pageSize功能
        Integer pageSize = 5;
        if (_pageSize != 5) {
            pageSize = _pageSize;
        }
        //1.执行业务层方法
        List<Orders> list = ordersService.findAll(pageNum, pageSize,term);
        //2.使用分页插件，封装pageBean
        PageInfo pageInfo = new PageInfo(list);
        //3.存入request域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("term", term);
        return "orders-list";
    }

}
