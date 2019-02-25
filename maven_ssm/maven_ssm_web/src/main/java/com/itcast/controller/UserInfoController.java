package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.Role;
import com.itcast.domain.UserInfo;
import com.itcast.service.RolesService;
import com.itcast.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/22 16:02
 * @Description: 处理用户相关操作的web层
 */
@RequestMapping("/user")
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//-----------------------------------------保存操作---------------------------------------------//


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(UserInfo userInfo) throws Exception {
        //1.调用BCryptPasswordEncoder对密码进行编码
        String password = userInfo.getPassword();
        String encode = passwordEncoder.encode(password);
        userInfo.setPassword(encode);
        //2.调用service层方法，执行保存操作
        userInfoService.saveUser(userInfo);
        return "redirect:/user/find";
    }


    //-----------------------------------------更新操作---------------------------------------------//
//-----------------------------------------删除操作---------------------------------------------//
    @RequestMapping(value = "/delete/{list}/{status}", method = RequestMethod.GET)
    public String deleteS(@PathVariable String list, @PathVariable int status, HttpSession session) throws Exception {
        //1.处理用户id
        String[] ids = list.split(",");
        //2.定义一个标签，决定跳转页面
        boolean flag = false;
        //3.取出当前用户id
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String infoId = userInfo.getId();
        //4.遍历数组
        for (String id : ids) {
            //4.1判断是否包含当前用户id
            if (id.equals(infoId)) {
                if (status == 1) { //标记为1则删除
                    userInfoService.deleteS(id);
                    flag = true;
                }
            } else {
                userInfoService.deleteS(id);
            }
        }
        if (flag) {
            session.invalidate();
            return "redirect:/login.jsp";
        }
        return "redirect:/user/find";
    }

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description:
     **/
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String updateNo(@PathVariable String id,HttpSession session) throws Exception {
        //0.定义一个标志：1为是当前正在登陆的用户，0为不是
        String flag = "0";
        //1.获取当前正在登陆的用户的用户名
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        //2.根据当前用户名查找用户完整信息
        UserInfo userInfo = userInfoService.findUserByName(username);
        //3.获取当前用户id
        String infoId = userInfo.getId();
        //4.判断是否在修改自己的角色，true为是
        if (id.equals(infoId)){
            flag="1";
        }
        return flag;
    }

    /**
     * @Author: 32725
     * @Param: [list]
     * @Return: java.lang.String
     * @Description:
     **/
    @RequestMapping(value = "/update/{uid}/{list}",method = RequestMethod.GET)
    public String updateRole(@PathVariable String uid,@PathVariable String list) throws Exception {
        //1.不管是否勾选了角色，都先执行清除操作，把用户的所有角色都删除
            userInfoService.deleteUserRole(uid);
        //2.没有勾选角色的话，直接跳转到
        if (list.equals("no")){
            return "redirect:/user/find";
        }
        //2.切割参数
        String[] ids = list.split(",");
        //3.遍历角色id数组，先删除，后添加
        for (String id : ids) {
            rolesService.addRole(uid,id);
        }
        return "redirect:/user/find";
    }
//-----------------------------------------查询操作---------------------------------------------//

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
        List<UserInfo> list = userInfoService.findAll(pageNum, pageSize, term);
        //2.使用PageInfo封装分页数据
        PageInfo pageInfo = new PageInfo(list);
        //3.添加入request域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("term", term);
        //4.跳转展示页面
        return "user-list";
    }

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description: 查询用户详情，包含用户的所有角色，以及角色对应的所有权限
     **/
    @RequestMapping(value = "/find/{id}/{pageName}", method = RequestMethod.GET)
    public String findUser(@PathVariable String id, @PathVariable String pageName, Model model, HttpSession session) throws Exception {
        //1.根据用户id查找用户
        UserInfo user = userInfoService.findUser(id);
        List<Role> userRoles = user.getRoles();
        //2.查找所有权限
        List<Role> roles = rolesService.findAll();
        //3.当前用户没有角色
        if (userRoles==null||userRoles.size()==0){
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return pageName;
        }
        //3.判断当前用户拥有哪些角色
        for (Role role : roles) {
            for (Role userRole : userRoles) {
                if (role.getRoleName().equals(userRole.getRoleName())) {
                    role.setStatus(1);
                }
            }
        }
        //3.把用户对象存入request域中
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return pageName;
    }

    /**
     * @Author: 32725
     * @Param: [list, session]
     * @Return: java.lang.String
     * @Description: 判断删除用户中是否包含当前用户
     **/
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public @ResponseBody
    String findUserById(@RequestBody String list, HttpSession session) throws Exception {

        //1.ajax请求参数如果包含字符需要手动使用URLDecoder解码
        String _ids = URLDecoder.decode(list, "utf-8");
        String[] split = _ids.split("=");//切割json数据
        String flag = "0";
        List<UserInfo> userInfos = new ArrayList<>();
        //2..处理参数，切割参数
        String[] ids = split[1].split(",");
        //3.遍历根据id查询所有用户信息
        for (String id : ids) {
            userInfos.add(userInfoService.findUser(id));
        }
        //4.获取当前正在登陆的用户的用户名
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        //4.判断删除用户中是否包含当前用户
        for (UserInfo userInfo : userInfos) {
            if (userInfo.getUsername().equals(username)) {
                flag = "1";
                session.setAttribute("userInfo", userInfo);//把当前用户存入session
                return flag;
            }
        }
        return flag;
    }

}
