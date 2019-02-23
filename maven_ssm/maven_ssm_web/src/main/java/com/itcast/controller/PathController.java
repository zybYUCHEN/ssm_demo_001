package com.itcast.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther : 32725
 * @Date: 2019/2/19 21:18
 * @Description: 跳转到page目录下的页面
 */
@RequestMapping("/path")
public class PathController {
    @RequestMapping("/open/{pageName}")
    public String openPage(@PathVariable String pageName){
        return pageName;
    }
}
