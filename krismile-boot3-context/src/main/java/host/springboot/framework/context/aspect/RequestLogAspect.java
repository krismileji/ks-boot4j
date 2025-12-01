package host.springboot.framework.context.aspect;

import host.springboot.framework.context.ExecuteOrder;
import host.springboot.framework.context.aspect.annotation.IgnoreRequestLog;
import host.springboot.framework.context.chain.RequestInfoChainExecute;
import host.springboot.framework.context.util.HttpRequestUtils;
import host.springboot.framework3.core.logging.LoggingComponent;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.model.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 请求日志切面
 *
 * @param requestInfoChainExecutes 请求信息链式处理器
 * @author JiYinchuan
 * @see IgnoreRequestLog
 * @since 0.1.0
 */
@Aspect
public record RequestLogAspect(
        @Nullable List<@NonNull RequestInfoChainExecute> requestInfoChainExecutes
) implements Ordered, LoggingComponent {

    /**
     * 请求方法切点
     *
     * @since 0.1.0
     */
    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void controllerMethodPointcut() {
    }

    /**
     * 请求方法切面
     *
     * @param point 切点
     * @return 切面执行结果
     * @throws Throwable 执行异常
     * @since 0.1.0
     */
    @Around("controllerMethodPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        if (!(point.getSignature() instanceof MethodSignature signature)) {
            logInstance().error("ProceedingJoinPoint.getSignature() is not a MethodSignature instanceof");
            return point.proceed();
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            logInstance().error("RequestContextHolder.getRequestAttributes() is not a HttpServletRequest instanceof");
            return point.proceed();
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Method method = signature.getMethod();

        RequestInfo requestInfo = HttpRequestUtils.parseInfo(request, method);
        // 请求的方法参数
        Object[] requestMethodArgs = point.getArgs();
        requestInfo.setRequestMethodArgs(requestMethodArgs);
        RequestInfo.REQUEST_CONTEXT.set(requestInfo);
        request.setAttribute(RequestInfo.class.getSimpleName(), requestInfo);

        try {
            // 判断是否进行打印日志
            if (isPrintLog(method)) {
                StopWatch executeTimeWatch = new StopWatch();
                executeTimeWatch.start();

                // 执行前置请求信息链式处理器
                if (Objects.nonNull(requestInfoChainExecutes)) {
                    requestInfoChainExecutes.forEach(item -> item.beforeExecute(request, requestInfo));
                }

                Object result = point.proceed();

                executeTimeWatch.stop();
                // 请求执行时间
                long executionTime = executeTimeWatch.getTotalTimeMillis();

                ResponseInfo responseInfo = new ResponseInfo()
                        .setResult(result).setExecutionTime(executionTime);

                // 执行后置请求信息链式处理器
                if (Objects.nonNull(requestInfoChainExecutes)) {
                    requestInfoChainExecutes.forEach(item -> item.afterExecute(request, requestInfo, responseInfo));
                }
                return result;
            }
            return point.proceed();
        } finally {
            RequestInfo.REQUEST_CONTEXT.remove();
        }
    }

    @Override
    public int getOrder() {
        return ExecuteOrder.Aop.REQUEST_LOG;
    }

    /**
     * 判断是否打印日志
     *
     * @param method 请求方法
     * @return 是否打印日志
     * @see IgnoreRequestLog
     * @since 0.1.0
     */
    private boolean isPrintLog(Method method) {
        IgnoreRequestLog methodIgnoreAnnotation = method.getAnnotation(IgnoreRequestLog.class);
        IgnoreRequestLog classIgnoreAnnotation = method.getDeclaringClass().getAnnotation(IgnoreRequestLog.class);
        return Objects.isNull(methodIgnoreAnnotation) && Objects.isNull(classIgnoreAnnotation);
    }

    @Override
    public @NonNull String logTag() {
        return "KS-AOP-Request";
    }
}
