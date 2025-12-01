package host.springboot.framework.autoconfigure.web.annotation;

import host.springboot.framework.context.filter.DefaultCorsFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用全局跨域默认处理自动配置
 *
 * <p>启用此注解后, 将全局添加进行跨域配置, 开发者无需再实现跨域配置
 *
 * @author JiYinchuan
 * @see DefaultCorsFilter
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(DefaultCorsFilter.class)
public @interface EnableDefaultGlobalCors {
}
