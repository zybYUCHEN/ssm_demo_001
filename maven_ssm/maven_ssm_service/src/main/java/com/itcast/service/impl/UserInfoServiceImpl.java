package com.itcast.service.impl;

import com.github.pagehelper.PageHelper;
import com.itcast.dao.UserInfoDao;
import com.itcast.domain.Role;
import com.itcast.domain.UserInfo;
import com.itcast.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 20:56
 * @Description: 用户表的业务层接口，间接实现UserDetailsService
 */
@Service("userService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * @Author: 32725
     * @Param: [username]
     * @Return: org.springframework.security.core.userdetails.UserDetails
     * @Description: spring-security提供的登陆验证方法
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            UserInfo userInfo = userInfoDao.findUserByName(username);
            user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 1 ? true : false,
                    true, true, true, getAuthority(userInfo.getRoles()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * @Author: 32725
     * @Param: [roles]
     * @Return: java.util.List<org.springframework.security.core.authority.SimpleGrantedAuthority>
     * @Description: 把用户所有角色转换成SimpleGrantedAuthority类型并存入集合
     **/
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()));
        }
        return list;
    }

    //--------------------保存操作----------------------------//

    /**
     * @Author: 32725
     * @Param: [userInfo]
     * @Return: void
     * @Description: 保存用户
     **/
    @Override
    public void saveUser(UserInfo userInfo) throws Exception {
        userInfoDao.saveUser(userInfo);
    }

    //--------------------更新操作----------------------------//
    //--------------------删除操作----------------------------//
    //--------------------查询操作----------------------------//

    /**
     * @Author: 32725
     * @Param: [pageNum, pageSize, term]
     * @Return: java.util.List<com.itcast.domain.Product>
     * @Description: 分页查询所有用户信息，如果term不为“”，可以变更为带条件的分页查询
     **/
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserInfo> findAll(Integer pageNum, Integer pageSize, String term) throws Exception {
        //1.使用PageHelper静态方法进行物理分页
        PageHelper.startPage(pageNum, pageSize);
        //2.判断条件是否存在,存在就加上模糊查询
        if (term != null && !term.equals("")) {
            term = "%" + term + "%";
        }
        //3.执行查询方法
        return userInfoDao.findAll(term);
    }

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description: 查询用户id详情，包含用户的所有角色，以及角色对应的所有权限
     **/
    public UserInfo findUser(String id) throws Exception {
        return userInfoDao.findUser(id);
    }

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 根据用户id删除用户，先要删除用户关联表，users_role
     **/
    @Override
    public void deleteS(String id) throws Exception {
        //1.先删除关联表中用户的信息
        userInfoDao.deleteUsersAndRole(id);
        //2.删除用户表，用户的信息
        userInfoDao.deleteUser(id);
    }

    /**
     * @Author: 32725
     * @Param: [username]
     * @Return: com.itcast.domain.UserInfo
     * @Description: 根据用户名查找用户
     **/
    @Override
    public UserInfo findUserByName(String username) throws Exception {
        return userInfoDao.findUserByName(username);
    }

    /**
     * @Author: 32725
     * @Param: [uid]
     * @Return: void
     * @Description: 根据用户id删除用户上的所有权限
     **/
    @Override
    public void deleteUserRole(String uid) throws Exception {
        userInfoDao.deleteUsersAndRole(uid);
    }


}
