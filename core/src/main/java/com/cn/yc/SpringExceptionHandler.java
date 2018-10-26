package com.cn.yc;

import com.cn.yc.exception.ApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DT167 on 2018/7/11.
 */
@EnableWebMvc
@ControllerAdvice
public class SpringExceptionHandler {
    @ExceptionHandler(value = ClassCastException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("eror");
        return mav;
    }

    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    public ModelAndView ArrayIndexOutOfBoundsException(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e.getMessage() +"---");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("eror");
        return mav;
    }
}
