package com.itcast.domain;

import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 20:42
 * @Description: 角色表的实体类
 */
public class Role implements Serializable {

    private String id; //角色id
    private String roleName;  //角色名称
    private String roleDesc;    //角色描述

    //添加的属性，用于用户角色的修改，0代表当前用户没有该权限，1表示有
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //角色和权限的关系：多对多
    private List<Permission> permissions;
    //角色和用户的关系：多对多
    private List<User> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
