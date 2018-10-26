package com.cn.yc;

import com.cn.yc.service.QqRoobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by DT167 on 2017/6/2.
 */

@SpringBootApplication
//@EnableAutoConfiguration
@EnableScheduling
//@ComponentScan(basePackages={"**.*.service","**.*.service.impl","**.*.controller","**.*.ws","**.*.springboot"})
public class Startup implements  WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Autowired
    private QqRoobotService qqRoobotService ;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Startup.class, args);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory configurableServletWebServerFactory) {
        configurableServletWebServerFactory.setPort(80);
    }
}
