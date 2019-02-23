package com.itcast.dao;

import com.itcast.domain.Product;
import com.itcast.domain.UserInfo;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 20:57
 * @Description: 用户表的持久层接口
 */
public interface UserInfoDao {

    //----------------保存操作------------------//

    /**
     * @Author: 32725
     * @Param: [userInfo]
     * @Return: void
     * @Description: 保存用户
     **/
    void saveUser(UserInfo userInfo)throws Exception;

    //----------------更新操作------------------//
    //----------------删除操作------------------//
    //----------------查询操作------------------//
    /**
    * @Author: 32725
    * @Param: [username]
    * @Return: com.itcast.domain.UserInfo
    * @Description: 根据用户id查找用户，以及用户的角色,以及角色对应的所有权限
    **/
    UserInfo findUser(String id)throws Exception;

    /**
    * @Author: 32725
    * @Param: [term]
    * @Return: java.util.List<com.itcast.domain.Product>
    * @Description:  根据条件查询所有用户信息
    **/
    List<UserInfo> findAll(String term) throws Exception;

    /**
    * @Author: 32725
    * @Param: [username]
    * @Return: com.itcast.domain.UserInfo
    * @Description: 据用户名查找用户
    **/
    UserInfo findUserByName(String username)throws Exception;

    /**
    * @Author: 32725
    * @Param: [id]
    * @Return: void
    * @Description: 根据用户id删除关联表中用户数据，就是删除用户的权限
    **/
    void deleteUsersAndRole(String id)throws Exception;
    /**
    * @Author: 32725
    * @Param: [id]
    * @Return: void
    * @Description: 根据用户id删除用户表中用户的信息
    **/
    void deleteUser(String id)throws Exception;
}
