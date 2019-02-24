package com.itcast.controller;

import com.itcast.domain.SysLog;
import com.itcast.service.LoggerExportService;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping(value = "/xml", method = RequestMethod.GET)
    public String loggerExport(Model model, @RequestHeader("referer") String referer) throws Exception {
        //1.获取所有日志信息
        List<SysLog> sysLogs = loggerExportService.findAllSysLog();
        //2.编辑文件名
        String fileName = "日志详细记录" + System.currentTimeMillis() + ".xls";
        //3.编辑工作表的名称，相当于一本书的一页纸
        String sheetName = "日志详细记录";
        //4.编辑表格标题
        String[] title = new String[]{ "用户名", "IP地址", "访问时间","执行时间","请求资源路径",  "访问的方法名"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        //5.遍历标题，输出标题
        WritableWorkbook book = null;
        try {
            // 打开文件
            book = Workbook.createWorkbook(new File("D:/"+fileName));
            // 生成名为"学生"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet(sheetName, 0);

            for (int j = 0; j < title.length; j++) {
                sheet.addCell(new jxl.write.Label(j, 0, title[j]));
            }
            if (sysLogs != null && !sysLogs.isEmpty()) {
                for (int i = 1; i <=sysLogs.size(); i++) {
                    sheet.addCell(new jxl.write.Label(0, i, sysLogs.get(i).getUsername()));
                    sheet.addCell(new jxl.write.Label(1, i, sysLogs.get(i).getIp()));
                    sheet.addCell(new jxl.write.Label(2, i, sysLogs.get(i).getVisitTimeStr()));
                    sheet.addCell(new jxl.write.Number(3, i, sysLogs.get(i).getExecutionTime()));
                    sheet.addCell(new jxl.write.Label(4, i, sysLogs.get(i).getUrl()));
                    sheet.addCell(new Label(5, i, sysLogs.get(i).getMethod()));
//                    if (i==sysLogs.size()){
//                        sheet.addCell(new Label(0, i+1, "导出操作执行时间："+format));
//                    }
                }
            }

            // 写入数据并关闭文件
            book.write();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "redirect:"+referer;
    }

}






