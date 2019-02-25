package com.itcast.controller;

import com.itcast.domain.SysLog;
import com.itcast.service.LoggerExportService;
import com.itcast.utils.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;


/**
 * @Auther : 32725
 * @Date: 2019/2/24 16:25
 * @Description: 导出日志至excel表格
 */
@RequestMapping("/export")
@Controller
public class LoggerExportController {
    //获取内容的接口
    @Autowired
    private LoggerExportService loggerExportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/xml", method = RequestMethod.GET)
    public void loggerExport(HttpServletResponse response) throws Exception {
        //1.获取日志数据
        List<SysLog> sysLogs = loggerExportService.findAllSysLog();
        //2.编写唯一文件名，中文文件名必须使用此句话
        String filename = new String(("访问日志" + System.currentTimeMillis() + ".xls").getBytes(), "iso-8859-1");
        //3.把响应内容的MIME类型设置为二进制流，浏览器下载文件时会用到这个
        response.setContentType("application/OCTET-STREAM;charset=UTF-8");
        //4.Content-Disposition 响应给浏览器的内容的处置方式 ，attachment告诉浏览器已附件形式打开文件，可以弹出保存弹框
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        //5.表头
        String[] headers = {"日志ID","访问时间","访问时间字符格式","用户名","IP地址" , "访问资源路径","持续时间", "访问路径"};//表格的标题栏

        try {
            //创建工具类
            ExportExcel<SysLog> ex = new ExportExcel<SysLog>();
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            //调用方法，处理表格
            ex.exportExcel("日志1", headers, sysLogs, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}







