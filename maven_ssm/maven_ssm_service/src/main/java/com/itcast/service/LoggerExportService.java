package com.itcast.service;

import com.itcast.domain.SysLog;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 17:02
 * @Description:
 */
public interface LoggerExportService {
    List<SysLog> findAllSysLog();
}
