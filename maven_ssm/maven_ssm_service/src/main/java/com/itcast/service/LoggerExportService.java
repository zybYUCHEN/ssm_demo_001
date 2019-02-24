package com.itcast.service;

import com.itcast.domain.SysLog;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 17:02
 * @Description:
 */
public interface LoggerExportService {
    /**
    * @Author: 32725
    * @Param: []
    * @Return: java.util.List<com.itcast.domain.SysLog>
    * @Description: 获取所有日志
    **/
    List<SysLog> findAllSysLog() throws Exception;
}
