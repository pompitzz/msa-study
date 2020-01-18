package me.sun.springbootstudy.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {


    @Pointcut("execution(* me.sun.springbootstudy.web..*Controller.*(..))")
    public void getControllerMethods() {
    }

    ;

    @Around("getControllerMethods()")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println();
        log.info("실행 시간 측정 시작 ::: ");

        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long proceedTime = System.currentTimeMillis() - start;

        log.info("Target ::: " + pjp.getTarget());
        log.info("실행 시간(ms) ::: " + proceedTime + "   ///   실행 시간(s) ::: " + (proceedTime / 1000));
        return proceed;
    }

}
