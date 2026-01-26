package host.springboot.framework.context.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略请求日志记录
 *
 * <p>当在类或方法上标注该注解时，请求日志记录将跳过该类或方法的日志打印。</p>
 *
 * <p><b>使用场景：</b></p>
 * <ul>
 *     <li>健康检查接口（如 /health、/actuator/*）</li>
 *     <li>静态资源请求</li>
 *     <li>高频轮询接口</li>
 *     <li>不需要审计的内部接口</li>
 * </ul>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * // 1. 类级别：忽略整个 Controller 的所有方法
 * @IgnoreRequestLog
 * @RestController
 * @RequestMapping("/internal")
 * public class InternalController {
 *     // 所有方法都不会记录日志
 * }
 *
 * // 2. 方法级别：仅忽略特定方法
 * @RestController
 * public class UserController {
 *
 *     @IgnoreRequestLog
 *     @GetMapping("/health")
 *     public Mono<String> health() {
 *         return Mono.just("OK");
 *     }
 *
 *     @GetMapping("/user/{id}")
 *     public Mono<User> getUser(@PathVariable Long id) {
 *         // 此方法会正常记录日志
 *         return userService.findById(id);
 *     }
 * }
 * }</pre>
 *
 * @author JiYinchuan
 * @see host.springboot.framework.context.filter.RequestLogFilter WebFlux 响应式环境
 * @since 0.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface IgnoreRequestLog {
}
