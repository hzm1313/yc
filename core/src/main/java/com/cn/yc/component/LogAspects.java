package com.cn.yc.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by DT167 on 2018/5/24.
 */
@Aspect
@Component
public class LogAspects {
    //使用org.slf4j.Logger,这是Spring实现日志的方法
    private final static Logger logger = LoggerFactory.getLogger(LogAspects.class);

    /**
     * 定义AOP扫描路径
     * 第一个注解只扫描aopTest方法
     */
    //@Pointcut("execution(public * com.aston.reader.controller.StudentController.aopTest())")
    @Pointcut("within( com.cn.yc.*)")
    //@Pointcut("execution(* *(..)))")
    public void log(){}

    /**
     * 记录HTTP请求开始时的日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("==============================url = {} end of execution", "Before");
    }

    /**
     * 记录HTTP请求结束时的日志
     */
    @After("log()")
    public void doAfter(){
        logger.info("==============================url = {} end of execution", "After");
    }

    /**
     * 获取返回内容
     * @param object
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturn(Object object){
        logger.info("=========================url = {} end of execution", "AfterReturning");
    }
}
