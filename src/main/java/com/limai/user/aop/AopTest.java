package com.limai.user.aop;

import com.limai.user.annotation.LogPrint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author itw_zhangcg
 * @version 1.0
 * @className AopTest
 * @description TODO
 * @date 2018/8/2 10:12
 **/
@Aspect
@Component
public class AopTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopTest.class);

    @Pointcut("execution(* com.limai.user.controller..*(..)) && @annotation(com.limai.user.annotation.LogPrint)")
    public void aopTest(){}

    @Around("aopTest()")
    public Object doAop(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            MethodSignature methodSignature =(MethodSignature)joinPoint.getSignature();
            String methodName = methodSignature.getName();
            LOGGER.info("正在执行"+methodName+"方法！");
            Object result = joinPoint.proceed();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
