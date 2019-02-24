package com.itcast.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 13:08
 * @Description: 日志表的实体类
 */
public class SysLog implements Serializable {
    private String id; //主键 无意义uuid
    private Date visitTime;//访问时间
    private String visitTimeStr;//访问时间的字符串表示
    private String username;    //访问者用户名
    private String ip;  //访问者ip
    private String url; //访问的资源路径
    private Long executionTime; //执行时长
    private String method;  //访问的方法

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitTimeStr() {
        if (visitTime!=null){
            visitTimeStr=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(visitTime);
        }
        return visitTimeStr;
    }

    public void setVisitTimeStr(String visitTimeStr) {
        this.visitTimeStr = visitTimeStr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
