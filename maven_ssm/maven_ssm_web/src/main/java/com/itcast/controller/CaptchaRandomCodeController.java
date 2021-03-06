package com.itcast.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;


/**
 * @Auther : 32725
 * @Date: 2019/2/27 12:57
 * @Description: 随机验证码控制层
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaRandomCodeController {
    private Producer captchaProducer = null;

    @Autowired
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @RequestMapping(value = "/image")
    public void captchaHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 禁止server端缓存
        response.setDateHeader("Expires", 0);
        // 设置标准的 HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 设置IE扩展 HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // 设置标准 HTTP/1.0 不缓存图片
        response.setHeader("Pragma", "no-cache");
        //设置返回数据MIME类型
        response.setContentType("image/jpeg");
        //为图片创建文本副本
        String capText = captchaProducer.createText();
        //将文本放入session域中
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY, capText);
        //创建带文本的图片，验证码图片
        BufferedImage image = captchaProducer.createImage(capText);
        //获取响应流
        ServletOutputStream os = response.getOutputStream();
        //输出图片
        ImageIO.write(image, "jpg", os);
        //释放资源
        os.flush();
        os.close();
        System.out.println("Session 验证码是：" + request.getSession().getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY));
    }
}
