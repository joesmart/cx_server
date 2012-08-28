package com.server.cx.util;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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


    private void startStopwatch() {
        if (stopwatch == null) {
            stopwatch = new Stopwatch();
        } else {
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
        }
        stopwatch.start();
    }

    private void stopStopwatch() {
        if(stopwatch != null && stopwatch.isRunning()){
            stopwatch.stop();
            logger.info("Execute:{}.{}.({}) Spend time:"+stopwatch,messageObjects);
            stopwatch.reset();
        }
    }

    @AfterThrowing(value = "execution( * com.server.cx.service.cx.*.*(..))",throwing = "throwable",argNames = "joinPoint,throwable")
    public void logError(JoinPoint joinPoint,Throwable throwable){
        initializeContext(joinPoint);
        initializeMessageObjects(joinPoint);
        logger.error("Service Method Execute Error:{}.{}.({})", messageObjects);
        logger.error(throwable.getMessage());
        logger.error("Detail Error:",throwable);
    }

    @Around(value = "execution( * com.server.cx.webservice.rs.server.*.*(..))",argNames = "joinPoint")
    public Object estimateTime(ProceedingJoinPoint joinPoint) throws Throwable {
        initializeContext(joinPoint);
        initializeMessageObjects(joinPoint);
        logger.info("Usage:{}.{}.({}) start", messageObjects);
        startStopwatch();
        Object object = joinPoint.proceed();
        stopStopwatch();
        logger.info("Usage:{}.{}.({}) end",messageObjects);
        return object;
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
