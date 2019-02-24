package com.itcast.service.impl;

import com.itcast.domain.SysLog;
import com.itcast.service.LoggerExportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 17:02
 * @Description:
 */
@Service
@Transactional
public class LoggerExportServiceImpl implements LoggerExportService {
    @Override
    public List<SysLog> findAllSysLog() {
        return null;
    }
}
