package com.itcast.dao;

import com.itcast.domain.Permission;
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


}
