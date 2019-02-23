package com.itcast.utils;

import org.springframework.core.convert.converter.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Auther : 32725
 * @Date: 2019/2/6 19:21
 * @Description: 自定义字符串转日期类型转换器
 */
public class StringToDate implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if(source==null){
            throw new RuntimeException("传入参数为空！");
        }
        DateFormat df=simpleDateFormat(source);
        try {
            return  df.parse(source);
        } catch (Exception e) {
            throw new RuntimeException("类型转换失败！");
        }

    }

    public SimpleDateFormat simpleDateFormat(String source){
        SimpleDateFormat sdf = new SimpleDateFormat();
        if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)) { // yyyy-MM-dd
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}$", source)) { // yyyy-MM-dd HH-mm-ss
            sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        } else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", source)) { // yyyy-MM-dd HH:mm:ss
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", source)){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// yyyy-MM-dd HH:mm+++++++++++++++++++==
        }else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)) { // yyyy/MM/dd
            sdf = new SimpleDateFormat("yyyy/MM/dd");
        } else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}/\\d{2}$", source)) { // yyyy/MM/dd HH/mm/ss
            sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
        }  else if (Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)) { // yyyyMMdd
            sdf = new SimpleDateFormat("yyyyMMdd");
        }  else if (Pattern.matches("^\\d{4}\\d{2}\\d{2} \\d{2}\\d{2}\\d{2}$", source)) { // yyyyMMdd HHmmss
            sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
        } else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2}$", source)) { // yyyy.MM.dd
            sdf = new SimpleDateFormat("yyyy.MM.dd");
        }  else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2} \\d{2}\\.\\d{2}\\.\\d{2}$", source)) { // yyyy.MM.dd HH.mm.ss
            sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        }else{
            System.out.println("TypeMismatchException");
            throw new RuntimeException();
        }
        return sdf;
    }
}
