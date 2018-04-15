package com.afagoal.aspect;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseStateConstant;
import com.afagoal.dao.behavior.UserBehaviorLogDao;
import com.afagoal.entity.behavior.UserBehaviorLog;
import com.afagoal.entity.system.SysUser;
import com.afagoal.security.SecurityContext;
import com.afagoal.utils.web.IPUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by BaoCai on 18/4/15.
 * Description:
 */
@Aspect
@Component
public class UserBehaviorAspect implements InitializingBean {

    @Autowired
    private UserBehaviorLogDao userBehaviorLogDao;

    private static ThreadPoolExecutor threadPoolExecutor;


    @Pointcut(("@annotation(com.afagoal.annotation.BehaviorLog)"))
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint point, long time) {
        UserBehaviorLog log = new UserBehaviorLog();
        SysUser user = SecurityContext.currentUser();
        log.setUserName(user.getUserName());
        log.setState(BaseStateConstant.DEFAULT_STATE);
        log.setUserId(user.getId());
        log.setUsingTime(time);

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String className = point.getTarget().getClass().getName();
        log.setMethod(className + "." + method.getName() + "()");

        if (method.isAnnotationPresent(BehaviorLog.class)) {
            BehaviorLog annotation = method.getAnnotation(BehaviorLog.class);
            log.setOperation(annotation.value());
        } else {
            BehaviorLog annotation = point.getTarget().getClass().getAnnotation(BehaviorLog.class);
            String operation = annotation.value() + method.getName() + "()";
            log.setOperation(operation);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.setOperateIp(IPUtils.getIpAddr(request));
        log.setCreatedAt(LocalDateTime.now());
        log.setCreatedBy("系统自动生成");

        threadPoolExecutor.execute(new LogTask(log));
    }

    private class LogTask implements Runnable {
        private UserBehaviorLog log;

        public LogTask(UserBehaviorLog log) {
            this.log = log;
        }

        @Override
        public void run() {
            userBehaviorLogDao.add(log);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        threadPoolExecutor = new ThreadPoolExecutor(4, 4, 100, TimeUnit.SECONDS, new LinkedBlockingDeque(500));
    }
}
