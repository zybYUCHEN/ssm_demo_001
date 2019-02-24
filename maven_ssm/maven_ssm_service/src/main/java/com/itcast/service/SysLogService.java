package com.itcast.service;

import com.itcast.domain.SysLog;
import com.itcast.domain.UserInfo;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 13:16
 * @Description: 日志的业务层接口
 */
public interface SysLogService {

    /**
    * @Author: 32725
    * @Param: [pageNum, pageSize, term]
    * @Return: java.util.List<com.itcast.domain.UserInfo>
    * @Description: 分页查看日志
    **/
    List<UserInfo> findAll(Integer pageNum, Integer pageSize, String term) throws Exception;

    /**
    * @Author: 32725
    * @Param: [sysLog]
    * @Return: void
    * @Description: 保存操作日志
    **/
    void save(SysLog sysLog);
}
