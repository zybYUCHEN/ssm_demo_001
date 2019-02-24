package com.itcast.dao;

import com.itcast.domain.SysLog;
import com.itcast.domain.UserInfo;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 13:20
 * @Description: 业务操作的持久层接口
 */
public interface SysLogDao {

    /**
     * @Author: 32725
     * @Param: [pageNum, pageSize, term]
     * @Return: java.util.List<com.itcast.domain.UserInfo>
     * @Description: 分页查询所有权限
     **/
    List<UserInfo> findAll(String term) throws Exception;

    /**
     * @Author: 32725
     * @Param: [sysLog]
     * @Return: void
     * @Description: 保存操作日志
     **/
    void save(SysLog sysLog);
}
