package com.cn.yc.ws;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

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

