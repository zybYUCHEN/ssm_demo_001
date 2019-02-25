package com.itcast.dao;

import com.itcast.domain.Permission;
import com.itcast.domain.Role;
import org.apache.ibatis.annotations.Param;

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
     * @Description: 查找所有角色
     **/
    List<Role> findByUserId(String id) throws Exception;

    /**
     * @param term
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.Role>
     * @Description: 查询所有角色，延迟加载角色所有权限
     */
    List<Role> findAll(String term) throws Exception;

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 添加指定id的权限，添加数据到users_role中
     **/
    void addRole(@Param("userId") String userId, @Param("roleId") String roleId) throws Exception;

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
     * deleteUserAndRole：删除用户和角色关联表中的记录
     * deleteRoleAndPermission：删除角色和权限关联表的作用
     * deleteRole：删除角色
     **/
    void deleteUserAndRole(String id) throws Exception;

    void deleteRoleAndPermission(String id) throws Exception;

    void deleteRole(String id) throws Exception;


    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: java.util.List<com.itcast.domain.Permission>
     * @Description: 查询当前角色所有权限
     **/
    List<Permission> findAllPermissionById(String id)throws Exception;

    /**
     * @Author: 32725
     * @Param: [roleId]
     * @Return: com.itcast.domain.Role
     * @Description: 根据角色id查找角色
     **/
    Role findById(String roleId)throws Exception;
}
