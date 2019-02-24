package com.itcast.service;

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
    List<UserInfo> findAll(Integer pageNum, Integer pageSize, String term)throws Exception;
}
