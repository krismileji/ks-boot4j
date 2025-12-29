package host.springboot.framework.context.aspect;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;

import host.springboot.framework.context.ExecuteOrder;
import host.springboot.framework3.core.logging.LoggingComponent;
import host.springboot.framework3.core.model.CheckParamProvider;
import host.springboot.framework3.core.util.inner.ArrayUtils;

/**
 * 参数校验切面
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@Aspect
public abstract class CheckParamAspect implements Ordered, LoggingComponent {

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    protected CheckParamAspect() {
    }

    /**
     * Controller方法切点
     *
     * @since 0.2.0
     */
    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void controllerMethodPointcut() {
    }

    /**
     * 切面处理方法
     *
     * @param point 切点
     * @return 切面执行结果
     * @throws Throwable 执行异常
     * @since 0.2.0
     */
    @Around("controllerMethodPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        this.execute(point);
        return point.proceed();
    }

    /**
     * 执行参数校验
     *
     * @param point 切点
     * @param arg   参数
     * @since 0.2.0
     */
    protected void execute(@NonNull ProceedingJoinPoint point, @NonNull CheckParamProvider arg) {
        arg.check();
    }

    /**
     * 执行参数校验
     *
     * @param point 切点
     * @since 0.2.0
     */
    private void execute(@NonNull ProceedingJoinPoint point) {
        if (!(point.getSignature() instanceof MethodSignature)) {
            logInstance().error("ProceedingJoinPoint.getSignature() is not a MethodSignature instanceof");
            return;
        }
        Object[] args = point.getArgs();
        if (ArrayUtils.isEmpty(args)) {
            return;
        }
        for (Object arg : args) {
            if (Objects.nonNull(arg) && arg instanceof CheckParamProvider) {
                this.execute(point, (CheckParamProvider) arg);
            }
        }
    }

    @Override
    public int getOrder() {
        return ExecuteOrder.Aop.CHECK_PARAM;
    }

    @Override
    public @NonNull String logTag() {
        return "KS-AOP-CheckParam";
    }
}
