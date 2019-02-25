package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.Permission;
import com.itcast.domain.UserInfo;
import com.itcast.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String savePermission(Permission permission) throws Exception {

        permissionService.savePermission(permission);
        return "redirect:/permission/find";
    }

//-------------------------------------更新操作--------------------------------------------//
    /**
    * @Author: 32725
    * @Param: [id, model]
    * @Return: java.lang.String
    * @Description: 修改权限，回显数据
    **/
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable String id,Model model)throws Exception{
        //1.根据id查找指定权限
        Permission permission = permissionService.findOne(id);
        //2.存入request域中
        model.addAttribute("permission", permission);
        return "permission-show";
    }
    /**
    * @Author: 32725
    * @Param: [id, model]
    * @Return: java.lang.String
    * @Description: 修改权限
    **/
    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public String updatePermission(@PathVariable String id,Permission permission)throws Exception{
        permissionService.update(permission,id);
        return "redirect:/permission/find";
    }

//-------------------------------------删除操作--------------------------------------------//
    @RequestMapping("/delete/{id}")
    public String deletePermission(@PathVariable String id) throws Exception {
        permissionService.deletePermission(id);
        return "redirect:/permission/find";
    }

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
        List<Permission> list = permissionService.findAll(pageNum, pageSize, term);
        //2.使用PageInfo封装分页数据
        PageInfo pageInfo = new PageInfo(list);
        //3.添加入request域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("term", term);
        //4.跳转展示页面
        return "permission-list";
    }
}
