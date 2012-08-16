package com.server.cx.util;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: yanjianzou
 * Date: 12-8-16
 * Time: 下午4:18
 * FileName:Logging
 */
@Aspect
public class Logging {

    private Stopwatch stopwatch;
    private Logger logger;
    private Class clazz;
    private String methodName;
    private Object[] messageObjects;

    @Before(value = "execution( * com.server.cx.webservice.rs.server.*.*(..))",argNames = "joinPoint")
    public void beforeRun(JoinPoint joinPoint) {
        clazz = joinPoint.getTarget().getClass();
        methodName = joinPoint.getSignature().getName();
        logger = LoggerFactory.getLogger(clazz);
        messageObjects = null;
        Object[] parameters = joinPoint.getArgs();
        messageObjects = Lists.asList(clazz.getName(), methodName,parameters).toArray();
        logger.info("Class:{} method:{} args:{}", messageObjects);

        if (stopwatch == null) {
            stopwatch = new Stopwatch();
        } else {
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
            stopwatch.reset();
            stopwatch.start();
        }

    }

    @AfterReturning(pointcut = "execution( * com.server.cx.webservice.rs.server.*.*(..))")
    public void log() {
        if(stopwatch != null && stopwatch.isRunning()){
            stopwatch.stop();
            logger.info("Class:{}.method:{} Spend time:"+stopwatch,clazz.getName(),methodName);
        }
        logger.info(" leaving Class:{}.method:{}",clazz.getName(),methodName);
    }
}
