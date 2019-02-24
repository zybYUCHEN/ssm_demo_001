package com.itcast.dao;

import com.itcast.domain.Permission;
import com.itcast.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/22 20:32
 * @Description: 资源权限表的持久层接口
 */
public interface PermissionDao {
    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.Role>
     * @Description:  根据RoleId查找角色所有的权限
     **/
    List<Permission> findByRoleId(String id)throws Exception;


    /**
     * @Author: 32725
     * @Param: [pageNum, pageSize, term]
     * @Return: java.util.List<com.itcast.domain.UserInfo>
     * @Description: 查询所有权限
     **/
    List<UserInfo> findAll(String term)throws Exception;

    /**
    * @Author: 32725
    * @Param: [permission]
    * @Return: void
    * @Description: 添加权限
    **/
    void savePermission(Permission permission) throws Exception;

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
    * @Return: void
    * @Description: 根据权限id删除Users_Permission表中的记录
    **/
    void deleteRoleAndPermission(String id);

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: com.itcast.domain.Permission
     * @Description: 根据id查找指定权限
     **/
    Permission findOne(String id);

    /**
     * @Author: 32725
     * @Param: [id, model]
     * @Return: java.lang.String
     * @Description: 修改权限
     **/
    void update(@Param("permission") Permission permission, @Param("id") String id) throws Exception;
}
