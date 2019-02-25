package com.itcast.service;

import com.itcast.domain.Permission;
import com.itcast.domain.UserInfo;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 11:15
 * @Description: 资源权限的业务层接口
 */
public interface PermissionService {

    /**
    * @Author: 32725
    * @Param: [pageNum, pageSize, term]
    * @Return: java.util.List<com.itcast.domain.UserInfo>
    * @Description: 分页查询所有权限
    **/
    List<Permission> findAll(Integer pageNum, Integer pageSize, String term)throws Exception;
    /**
    * @Author: 32725
    * @Param: []
    * @Return: java.util.List<com.itcast.domain.Permission>
    * @Description: 查询所有权限
    **/
    List<Permission> findAll()throws Exception;



    /**
    * @Author: 32725
    * @Param: [permission]
    * @Return: void
    * @Description: 添加权限
    **/
    void savePermission(Permission permission)throws Exception;

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 根据权限id删除权限
     **/
    void deletePermission(String id) throws Exception;
    /**
    * @Author: 32725
    * @Param: [id]
    * @Return: com.itcast.domain.Permission
    * @Description: 根据id查找指定权限
    **/
    Permission findOne(String id)throws Exception;

    /**
     * @Author: 32725
     * @Param: [id, model]
     * @Return: java.lang.String
     * @Description: 修改权限
     **/
    void update(Permission permission, String id) throws Exception;
}
