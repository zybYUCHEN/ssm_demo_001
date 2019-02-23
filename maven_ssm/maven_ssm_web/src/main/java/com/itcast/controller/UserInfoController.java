package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.Permission;
import com.itcast.domain.Role;
import com.itcast.domain.UserInfo;
import com.itcast.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
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

//    public String deleteS(){
//
//    }

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
    @RequestMapping(value = "/find/{username}", method = RequestMethod.GET)
    public String findUserDetails(@PathVariable String username,Model model) throws Exception {
        //1.根据用户id查找用户详情
        UserInfo userInfo = userInfoService.findUserDetails(username);
        //2.把用户对象存入request域中
        model.addAttribute("user", userInfo);
        return "user-show";
    }

    /**
    * @Author: 32725
    * @Param: [list, session]
    * @Return: java.lang.String
    * @Description: 判断删除用户中是否包含当前用户
    **/
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public @ResponseBody String findUserById(@RequestBody String list, HttpSession session) throws Exception {

        String flag = "false";
        List<UserInfo> userInfos= null;

        //1..处理参数，切割参数
        String[] ids = list.split(",");
        //2.遍历根据id查询所有用户信息
        for (String id : ids) {
            userInfos.add(userInfoService.findUserDetails(id));
        }
        //3.获取当前正在登陆的用户的用户名
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        //4.判断删除用户中是否包含当前用户
        for (UserInfo userInfo : userInfos) {
            if (userInfo.getUsername().equals(username)){
                flag="true";
                return flag;
            }
        }

        return flag;
    }
}
