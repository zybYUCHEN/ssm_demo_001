package com.itcast.service.impl;

import com.itcast.dao.SysLogDao;
import com.itcast.domain.SysLog;
import com.itcast.service.LoggerExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    @Autowired
    private SysLogDao sysLogDao;
    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.SysLog>
     * @Description: 获取所有日志
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<SysLog> findAllSysLog() throws Exception {

        List<SysLog> list = sysLogDao.findAll("");
        return list;
    }
}
