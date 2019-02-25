package com.itcast.service;

import com.itcast.domain.Permission;
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
    List<Role> findAll(Integer pageNum, Integer pageSize, String term) throws Exception;

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
    void addRole(String userId, String roleId) throws Exception;

    /**
     * @Author: 32725
     * @Param: [role]
     * @Return: java.lang.String
     * @Description: 保存新角色
     **/
    void saveRole(Role role) throws Exception;

    /**
     * @Author: 32725
     * @Param: [ids]
     * @Return: void
     * @Description: 删除指定的角色
     **/
    void deleteRole(String[] ids) throws Exception;

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 根据权限id删除用户的权限
     **/
    void deleteRoleAndPermission(String id) throws Exception;

    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: java.util.List<com.itcast.domain.Permission>
     * @Description: 查询当前角色所有权限
     **/
    List<Permission> findAllPermissionById(String roleId) throws Exception;

    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: com.itcast.domain.Role
     * @Description: 根据角色id查找角色
     **/
    Role findById(String roleId) throws Exception;

    /**
     * @Author: 32725
     * @Param: [s]
     * @Return: void
     * @Description: 给指定的用户添加权限
     **/
    void addPermission(String roleId, String permissionId) throws Exception;
}
