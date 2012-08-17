package com.server.cx.util;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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
    String message;

    @Before(value = "execution( * com.server.cx.webservice.rs.server.*.*(..))",argNames = "joinPoint")
    public void beforeRun(JoinPoint joinPoint) {
        clazz = joinPoint.getTarget().getClass();
        methodName = joinPoint.getSignature().getName();
        logger = LoggerFactory.getLogger(clazz);
        messageObjects = null;
        Object[] parameters = joinPoint.getArgs();
        String[] parameterStrings = new String[]{parameters==null?"":Joiner.on(",").join(parameters)};
        messageObjects = Lists.asList(clazz.getName(), methodName,parameterStrings).toArray();
        logger.info("Usage:{}.{}.({}) start",messageObjects);
        if (stopwatch == null) {
            stopwatch = new Stopwatch();
        } else {
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
            stopwatch.reset();
        }
        stopwatch.start();
    }

    @AfterReturning(pointcut = "execution( * com.server.cx.webservice.rs.server.*.*(..))")
    public void log() {
        if(stopwatch != null && stopwatch.isRunning()){
            stopwatch.stop();
            logger.info("Execute Time:{}.{}.({}) Spend time:"+stopwatch,messageObjects);
        }
        logger.info("Usage:{}.{}.({}) end",messageObjects);
    }

    @AfterThrowing(value = "execution( * com.server.cx.webservice.rs.server.*.*(..))",throwing = "throwable",argNames = "joinPoint,throwable")
    public void logError(JoinPoint joinPoint,Throwable throwable){
        clazz = joinPoint.getTarget().getClass();
        methodName = joinPoint.getSignature().getName();
        logger = LoggerFactory.getLogger(clazz);
        logger.error("Method Execute Error:{}.{}.({})",messageObjects);
        logger.error("Detail Error:",throwable);
    }
}
