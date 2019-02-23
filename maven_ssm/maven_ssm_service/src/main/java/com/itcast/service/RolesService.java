package com.itcast.service;

import com.itcast.domain.Role;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/22 21:43
 * @Description: 角色表的业务层接口
 */
public interface RolesService {

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description: 查找所有角色，以及角色所有的权限
     **/
    List<Role> findAll(Integer pageNum,Integer pageSize,String term) throws Exception;

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 不分页查找所有角色
    **/
    List<Role> findAll() throws Exception;

    /**
    * @Author: 32725
    * @Param: [id]
    * @Return: void
    * @Description: 添加指定id的权限，添加数据到users_role中
    **/
    void addRole(String userId,String roleId)throws Exception;
}
