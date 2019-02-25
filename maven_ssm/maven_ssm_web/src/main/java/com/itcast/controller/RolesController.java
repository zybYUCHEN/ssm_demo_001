package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.Permission;
import com.itcast.domain.Role;
import com.itcast.service.PermissionService;
import com.itcast.service.RolesService;
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
 * @Date: 2019/2/22 21:31
 * @Description: 角色表的控制类
 */
@RequestMapping("/role")
@Controller
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @Autowired
    private PermissionService permissionService;
//--------------------------------------------------保存操作--------------------------------------------//

    /**
     * @Author: 32725
     * @Param: [role]
     * @Return: java.lang.String
     * @Description: 保存新角色
     **/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveRole(Role role) throws Exception {
        rolesService.saveRole(role);
        return "redirect:/role/find";
    }
//--------------------------------------------------更新操作--------------------------------------------//
//--------------------------------------------------删除操作--------------------------------------------//

    /**
     * @Author: 32725
     * @Param: [list]
     * @Return: java.lang.String
     * @Description:
     **/
    @RequestMapping(value = "/delete/{list}", method = RequestMethod.GET)
    public String deleteRole(@PathVariable String list) throws Exception {
        //1.判断是否有参数
        if (list == null || list.equals("")) {
            return "redirect:/role/find";
        }
        //2.处理参数
        String[] ids = list.split(",");
        //3.调用service层删除角色
        rolesService.deleteRole(ids);
        return "redirect:/role/find";
    }

//--------------------------------------------------查询操作--------------------------------------------//


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
        List<Role> list = rolesService.findAll(pageNum, pageSize, term);
        //2.使用PageInfo封装分页数据
        PageInfo pageInfo = new PageInfo(list);
        //3.添加入request域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("term", term);
        //4.跳转展示页面
        return "role-list";
    }

    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: java.lang.String
     * @Description: 修改用户权限
     * 1.查询所有权限
     * 2.回显会页面，带有多选框形式，用户拥有的权限默认被勾选
     * 3.需要给权限实体类添加标记，用于判断
     * status：0.代表用户没有的权限，1.代表用户已有的权限
     **/
    @RequestMapping("/find/{roleId}")
    public String findAllByRoleId(@PathVariable String roleId, Model model) throws Exception {
        //1.查询所有的权限
        List<Permission> psList = permissionService.findAll();
        //2.查询当前角色所拥有的权限
        Role role = rolesService.findById(roleId);
        List<Permission> permissions = role.getPermissions();
        //3.遍历所有权限，判断是否是当前用户所具备权限
        for (int i = 0; i < psList.size(); i++) {
            if (permissions!=null &&permissions.size()> 0) {
                for (int j = 0; j < permissions.size(); j++) {
                    if (psList.get(i).getPermissionName().equals(permissions.get(j).getPermissionName())) {
                        //如果包含的话修改状态为1
                        psList.get(i).setStatus(1);
                    }
                }
            }
        }
        model.addAttribute("psList", psList);
        model.addAttribute("role", role);
        return "role-permission-update";
    }
}
