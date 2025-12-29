package host.springboot.framework.context.chain;

import org.jspecify.annotations.NonNull;
import org.springframework.http.server.reactive.ServerHttpRequest;

import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.model.ResponseInfo;
import reactor.core.publisher.Mono;

/**
 * 请求信息链式处理（Reactive 版本）
 *
 * <p>该接口提供响应式的请求处理链，使用 Mono 包装处理逻辑，确保与 WebFlux 响应式流完全兼容</p>
 * <p>在方法执行前后提供自定义处理逻辑，可用于日志记录、监控等场景</p>
 *
 * <p><b>注意事项：</b></p>
 * <ul>
 *     <li>所有处理方法返回 {@link Mono}&lt;Void&gt;，支持异步非阻塞操作</li>
 *     <li>遵循响应式编程不可变性原则，不直接修改请求对象</li>
 *     <li>避免在处理链中执行阻塞操作</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see host.springboot.framework.context.filter.RequestLogFilter
 * @see DefaultRequestLogChainExecute
 * @since 0.2.0
 */
public interface RequestInfoChainExecute {

    /**
     * 默认执行顺序
     */
    int DEFAULT_EXECUTE_ORDER = 100;

    /**
     * 处理前置请求信息（响应式）
     *
     * <p>在业务方法执行前调用，返回 Mono 以支持异步操作</p>
     * <p>默认实现返回空的 Mono，子类可覆盖以添加自定义逻辑</p>
     *
     * @param request     {@link ServerHttpRequest} 响应式请求对象
     * @param requestInfo 请求信息（不可变）
     * @return {@link Mono}&lt;Void&gt; 异步完成信号
     * @since 0.2.0
     */
    default Mono<Void> beforeExecute(
            @NonNull ServerHttpRequest request,
            @NonNull RequestInfo requestInfo) {
        return Mono.empty();
    }

    /**
     * 处理后置请求信息（响应式）
     *
     * <p>在业务方法执行后调用，返回 Mono 以支持异步操作</p>
     * <p>默认实现返回空的 Mono，子类可覆盖以添加自定义逻辑</p>
     *
     * @param request      {@link ServerHttpRequest} 响应式请求对象
     * @param requestInfo  请求信息（不可变）
     * @param responseInfo 响应信息（不可变）
     * @return {@link Mono}&lt;Void&gt; 异步完成信号
     * @since 0.2.0
     */
    default Mono<Void> afterExecute(
            @NonNull ServerHttpRequest request,
            @NonNull RequestInfo requestInfo,
            @NonNull ResponseInfo responseInfo) {
        return Mono.empty();
    }
}
