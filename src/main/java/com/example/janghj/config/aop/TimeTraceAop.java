package com.example.janghj.config.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log4j2
public class TimeTraceAop {

    // AOP 적용 위치(web package 내부 클래스)
    @Around("execution(* *..web.*.*(..))")
    public Object calculateExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        // 측정 시작
        sw.start();

        // AOP 적용 메소드 실행
        Object result = pjp.proceed();

        // 측정 종료
        sw.stop();
        long executionTime = sw.getTotalTimeMillis();

        // AOP가 적용된 메소드 위치 출력용
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String task = className + "." + methodName;

        log.debug("[TimeTraceAop] " + task + "-->" + executionTime + "(ms)");
        return result;
    }
}
