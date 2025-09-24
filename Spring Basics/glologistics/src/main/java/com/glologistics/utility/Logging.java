package com.glologistics.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class Logging {
    private static final Logger logger = LogManager.getLogger(Logging.class);

    // Pointcut for all service methods
    @Pointcut("execution(* com.glologistics.service.*.*(..))")
    public void serviceLayerMethods() {}

    // Before advice - logs method entry
    @Before("serviceLayerMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        logger.debug("Entering method: {}.{} with arguments: {}",
                className, methodName, Arrays.toString(args));
    }

    // After returning advice - logs successful method execution
    @AfterReturning(pointcut = "serviceLayerMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.debug("Exiting method: {}.{} with result: {}",
                className, methodName, result);
    }

    // After throwing advice - logs exceptions
    @AfterThrowing(pointcut = "serviceLayerMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.error("Exception in method: {}.{} - Error: {}",
                className, methodName, exception.getMessage());
    }

    // Around advice for performance monitoring on critical methods
    @Around("execution(* com.glologistics.service.OrderService.generateOrder(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("Method {} executed in {} ms",
                joinPoint.getSignature().getName(), executionTime);

        return result;
    }
}
