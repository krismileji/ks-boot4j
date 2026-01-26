package host.springboot.framework.context.advice.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import host.springboot.framework.context.advice.GlobalControllerAdvice;

/**
 * 启用全局 Controller 异常处理自动配置
 *
 * <p>启用此注解后, 请求中所有未捕获的异常将全部被统一拦截, 友好的将错误码/错误信息/用户提示信息返回给客户端</p>
 * <p><b>Warning:</b> 当手动捕获异常后将无法进行异常拦截. 在项目开发过程中建议将异常直接抛出, 由系统自动拦截返回, 而不是手动捕获手动返回</p>
 *
 * @author JiYinchuan
 * @see GlobalControllerAdvice
 * @since 0.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(GlobalControllerAdvice.class)
public @interface EnableGlobalControllerAdvice {
}
