package com.cn.yc.web.config;


import com.cn.yc.web.ws.WechatConnector;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;

/**
 * Created by DT167 on 2017/6/2.
 */

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        WechatConnector wxServlet =new WechatConnector();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(wxServlet,"/wx/getWxMessage");

        return registrationBean;
    }
}

