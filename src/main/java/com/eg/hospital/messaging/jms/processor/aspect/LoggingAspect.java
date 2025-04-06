package com.eg.hospital.messaging.jms.processor.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut for all methods in the controller package.
     * Targets all classes and methods under com.eg.hospital.messaging.jms.processor.controller.
     */
    @Pointcut("within(com.eg.hospital.messaging.jms.processor.controller..*)")
    public void controllerMethods() {}

    /**
     * Logs input parameters of controller methods.
     */
    @Before("controllerMethods()")
    public void logInputParameters(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("{}.{}() called with request body: {}", className, methodName, args);
    }

    /**
     * Advice that runs before any method in the service package.
     * Logs method entry points to indicate that a service method was invoked.
     *
     * @param joinPoint provides access to method being called
     */
    @Before("execution(* com.eg.hospital.messaging.jms.processor.service..*(..))")
    public void logServiceInputs(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Entering {}.{}", className, methodName);
    }

}