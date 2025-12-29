package host.springboot.framework.autoconfigure.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import host.springboot.framework.context.filter.DefaultCorsFilter;

/**
 * 启用全局跨域默认处理自动配置
 *
 * <p>启用此注解后, 将全局添加进行跨域配置, 开发者无需再实现跨域配置</p>
 *
 * @author JiYinchuan
 * @see DefaultCorsFilter
 * @since 0.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(DefaultCorsFilter.class)
public @interface EnableDefaultGlobalCors {
}
