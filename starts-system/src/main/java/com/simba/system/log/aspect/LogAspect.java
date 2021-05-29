package com.simba.system.log.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.simba.common.constant.ApiConstant;
import com.simba.common.utils.AddressUtil;
import com.simba.system.entity.OperLog;
import com.simba.system.log.annotation.Log;
import com.simba.system.manager.AsyncManager;
import com.simba.system.manager.factory.AsyncFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * 操作日志记录处理
 *
 * @author chenjun
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private static long startTime = 0;
    private static long endTime = 0;
    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.simba.system.log.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void boBefore(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Method Name : [{} :{}] ---> AOP before ",startTime,className+"."+methodName+"()");
    }

    @After("logPointCut()")
    public void doAfter(JoinPoint joinPoint) {
        endTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Method Name : [{} :{}] ---> AOP after ",endTime,className+"."+methodName+"()");
    }

    /**
     * 处理完请求后执行
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        endTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Method Name : [{} :{}] ---> AOP AfterReturning ",endTime,className+"."+methodName+"()");
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * @param joinPoint 切点
     * @param exception 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        endTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Method Name : [{} :{}] ---> AOP AfterThrowing ",endTime,className+"."+methodName+"()");
        handleLog(joinPoint, exception, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception exception, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            HttpServletRequest request = ((ServletRequestAttributes) Objects
                    .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) Objects
                    .requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            String ip = ServletUtil.getClientIP(request);
            Long requestStartTime = (Long) request.getAttribute(ApiConstant.API_BEGIN_TIME);
            OperLog operLog = new OperLog();
            // 请求的地址
            operLog.setOperIp(ip);
            operLog.setOperLocation(AddressUtil.getCityInfo(ip));
            // 请求url
            operLog.setOperUrl(URLUtil.getPath(request.getRequestURI()));
            //动态从Shiro或者security取都行
            operLog.setOperName(getUsername()+"|"+getClientId());
            log.debug("测试获取返回：[{}]",response.toString());
            //异常信息
            if (ObjectUtil.isNull(exception)) {
                operLog.setStatus(1);
                operLog.setResultMsg("[返回信息:]"+JSONUtil.toJsonStr(jsonResult));
            } else {
                //log.info("======测试获取返回：[{}]",JSONUtil.toJsonStr(response.getTrailerFields()));
                operLog.setStatus(0);
                operLog.setResultMsg("[报错种类:]"+exception.getClass().getName()+"=[报错信息:]"+exception.getMessage());
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setActionMethod(className + "." + methodName + "()");
            Long excuteTime = endTime-startTime;
            operLog.setExecuteTime(excuteTime);
            // 设置请求方式
            operLog.setMethod(request.getMethod());
            // 处理设置注解上的参数
            setControllerMethodDescription(request,controllerLog, operLog);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("前置通知异常");
            log.error("异常信息:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void setControllerMethodDescription(HttpServletRequest request,Log log, OperLog operLog) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(request,operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(HttpServletRequest request,OperLog operLog) throws Exception {
        Map<String, String[]> map = request.getParameterMap();
        operLog.setOperParam(JSONUtil.toJsonStr(map));
    }

    /**
     * 获取客户端
     * @return clientId
     */
    public String getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            log.debug("getClientId操作日志中获取授权信息:{}",JSONUtil.toJsonStr(authentication));
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        return null;
    }

    /**
     * 获取用户名称
     * @return username
     */
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isNull(authentication)) {
            return null;
        }
        log.debug("getUsername操作日志中获取授权信息:{}",JSONUtil.toJsonStr(authentication));
        return authentication.getName();
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    @SneakyThrows
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        log.info("Method Name : [{}] ---> AOP AnnotationLog ",className+"."+methodName+"()");
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}