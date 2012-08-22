package com.server.cx.util;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.server.cx.service.cx.impl.BasicService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: yanjianzou
 * Date: 12-8-16
 * Time: 下午4:18
 * FileName:Logging
 */
@Aspect
public class Logging {

    @Autowired
    private BasicService basicService;
    private Stopwatch stopwatch;
    private Logger logger;
    private Class clazz;
    private String methodName;
    private Object[] messageObjects;

    @Before(value = "execution( * com.server.cx.webservice.rs.server.*.*(..))",argNames = "joinPoint")
    public void beforeRun(JoinPoint joinPoint) {
        initializeContext(joinPoint);
        initializeMessageObjects(joinPoint);
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
        logger.info("Longing ID"+stopwatch.hashCode());
    }

    @AfterReturning(pointcut = "execution( * com.server.cx.webservice.rs.server.*.*(..))",argNames = "joinPoint")
    public void log(JoinPoint joinPoint) {
        initializeContext(joinPoint);
        initializeMessageObjects(joinPoint);
        if(stopwatch != null && stopwatch.isRunning()){
            stopwatch.stop();
            logger.info("Execute Time:{}.{}.({}) Spend time:"+stopwatch,messageObjects);
            stopwatch.reset();
        }
        logger.info("Usage:{}.{}.({}) end",messageObjects);
    }

    @AfterThrowing(value = "execution( * com.server.cx.service.cx.*.*(..))",throwing = "throwable",argNames = "joinPoint,throwable")
    public void logError(JoinPoint joinPoint,Throwable throwable){
        initializeContext(joinPoint);
        initializeMessageObjects(joinPoint);
        logger.error("Service Method Execute Error:{}.{}.({})",messageObjects);
        logger.error("Detail Error:",throwable);
    }

    private void initializeContext(JoinPoint joinPoint) {
        clazz = joinPoint.getTarget().getClass();
        methodName = joinPoint.getSignature().getName();
        logger = LoggerFactory.getLogger(clazz);
    }

    private void initializeMessageObjects(JoinPoint joinPoint) {
        messageObjects = null;
        String[] parameterStrings = getArgumentValues(joinPoint);
        messageObjects = Lists.asList(clazz.getName(), methodName, parameterStrings).toArray();
    }

    private String[] getArgumentValues(JoinPoint joinPoint) {

        Object[] parameters = joinPoint.getArgs();
        return new String[]{parameters == null ? "" : Joiner.on(",").skipNulls().join(parameters)};
    }
}
