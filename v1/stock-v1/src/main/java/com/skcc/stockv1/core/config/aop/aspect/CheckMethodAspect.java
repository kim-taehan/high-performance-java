package com.skcc.stockv1.core.config.aop.aspect;

import com.skcc.stockv1.core.config.aop.annotation.CheckMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Slf4j
public class CheckMethodAspect {

    @Around("@annotation(checkMethod)")
    public Object doCheckMethod(ProceedingJoinPoint joinPoint, CheckMethod checkMethod) throws Throwable {

        Instant beforeTime = Instant.now();  // 코드 실행 전에 시간 받아오기
        String typeName = joinPoint.getSignature().getDeclaringType().getTypeName();
        String methodName = joinPoint.getSignature().getName();

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            printExLogging(beforeTime, methodName, typeName, e);
            throw e;
        } finally {
            printLogging(beforeTime, methodName, typeName);
        }
    }

    private void printLogging(Instant beforeTime, String methodName, String typeName) {
        long diffTime = getDiffTime(beforeTime);
        log.info("[CheckMethod] 수행시간={}ms, method={}, class={}", diffTime, methodName, typeName);
    }

    private void printExLogging(Instant beforeTime, String methodName, String typeName, Exception e) {
        long diffTime = getDiffTime(beforeTime);
        log.info("[CheckMethod] 수행시간={}ms, method={}, class={}", diffTime, methodName, typeName, e);
    }

    private static long getDiffTime(Instant beforeTime) {
        Instant afterTime = Instant.now();
        return Duration.between(beforeTime, afterTime).toMillis(); // 두 개의 실행 시간
    }
}
