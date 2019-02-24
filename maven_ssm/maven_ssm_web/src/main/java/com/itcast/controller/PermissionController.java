package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.UserInfo;
import com.itcast.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 11:11
 * @Description: 资源权限管理的控制层
 */
@RequestMapping("/permission")
@Controller
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
//-------------------------------------保存操作--------------------------------------------//
//-------------------------------------更新操作--------------------------------------------//
//-------------------------------------删除操作--------------------------------------------//
//-------------------------------------查询操作--------------------------------------------//

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer _pageSize,
                          @RequestParam(name = "term", required = false, defaultValue = "") String term,
                          Model model) throws Exception {
        //0.全局通过每页展示数据量来设置默认pageSize
        Integer pageSize = 5;
        if (_pageSize != 5) {
            pageSize = _pageSize;
        }
        //1.传递分页参数，currentPage当前页，默认为1，pageSize每页展示的数据条数，默认为5
        List<UserInfo> list = permissionService.findAll(pageNum, pageSize, term);
        //2.使用PageInfo封装分页数据
        PageInfo pageInfo = new PageInfo(list);
        //3.添加入request域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("term", term);
        //4.跳转展示页面
        return "permission-list";
    }
}
