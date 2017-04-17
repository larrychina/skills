package org.larry.springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Larry on 2017/3/31.
 */
@Aspect
@Component
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class) ;

    @Pointcut("execution(public * org.larry.springboot.web.*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        // url
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("url={}",request.getRequestURL());
        // http method
        logger.info("method={}",request.getMethod());
        // ip
        logger.info("ip=",request.getRemoteAddr());
        // method
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        // args
        logger.info("args={}",joinPoint.getArgs());
    }

    @After("webLog()")
    public void doAfter(){

    }

    @AfterReturning(pointcut = "webLog()",returning = "object")
    public void doAfterRunning(Object object){
        logger.info("response={}",object.toString());
    }
}
