package com.itcast.controller;

import com.itcast.domain.SysLog;
import com.itcast.service.LoggerExportService;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

    @RequestMapping(value = "/xml",method = RequestMethod.GET)
    public String loggerExport(Model model) throws Exception {
        //1.获取所有日志信息
        List<SysLog> logList = loggerExportService.findAllSysLog();
        //2.编辑文件名
        String fileName = "日志详细记录"+System.currentTimeMillis()+".xls";
        //3.编辑工作表的名称，相当于一本书的一页纸
        String sheetName = "日志详细记录";
        //4.编辑表格标题
        String [] title = new String[]{"id","visitTimeStr","username","ip;","url","executionTime","method"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Workbook workbook = Workbook.getWorkbook(new File("日志"));
        Sheet sheet = workbook.getSheet(sheetName);
        new Label()

        return null;
    }

}






