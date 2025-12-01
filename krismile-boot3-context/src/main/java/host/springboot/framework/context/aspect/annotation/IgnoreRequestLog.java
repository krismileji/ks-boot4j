package host.springboot.framework.context.aspect.annotation;

import host.springboot.framework.context.aspect.RequestLogAspect;

import java.lang.annotation.*;

/**
 * 忽略请求日志AOP记录
 *
 * <p>当在类或方法上标注该注解时, 进行日志打印将判断是否忽略当前类或方法的日志打印
 *
 * @author JiYinchuan
 * @see RequestLogAspect
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface IgnoreRequestLog {
}
