package com.itcast.dao;

import com.itcast.domain.Role;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 21:01
 * @Description: 角色表的持久层接口
 */
public interface RoleDao {

    /**
    * @Author: 32725
    * @Param: []
    * @Return: java.util.List<com.itcast.domain.Role>
    * @Description:  查找所有角色
    **/
    List<Role> findByUserId(String id)throws Exception;

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.Role>
     * @Description: 查询所有角色，延迟加载角色所有权限
     * @param term
     * */
    List<Role> findAll(String term)throws Exception;
}
