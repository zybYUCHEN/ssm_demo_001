package com.itcast.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/21 20:42
 * @Description: 资源权限表的实体类
 */
public class Permission implements Serializable {

    private String id; //权限id
    private String permissionName; //权限名称
    private String url; //资源路径

    //权限和角色的关联：多对多
    private List<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
