package com.itcast.service;

import com.itcast.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 20:53
 * @Description: 用户表的业务层接口，继承UserDetailsService接口
 */
public interface UserInfoService extends UserDetailsService {
    //--------------------保存操作----------------------------//

    /**
    * @Author: 32725
    * @Param: [userInfo]
    * @Return: void
    * @Description: 保存用户
    **/
    void saveUser(UserInfo userInfo) throws Exception;

    //--------------------更新操作----------------------------//
    //--------------------删除操作----------------------------//
    //--------------------查询操作----------------------------//
    /**
    * @Author: 32725
    * @Param: [pageNum, pageSize, term]
    * @Return: java.util.List<com.itcast.domain.Product>
    * @Description: 分页查询所有用户信息，如果term不为“”，可以变更为带条件的分页查询
    **/
    List<UserInfo> findAll(Integer pageNum, Integer pageSize, String term) throws Exception;


    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description: 查询用户详情，包含用户的所有角色，以及角色对应的所有权限
     **/
    UserInfo findUserDetails(String id) throws Exception;
}
