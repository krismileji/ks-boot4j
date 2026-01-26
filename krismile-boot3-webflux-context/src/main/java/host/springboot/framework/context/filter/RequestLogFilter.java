package host.springboot.framework.context.filter;

import host.springboot.framework.context.ExecuteOrder;
import host.springboot.framework.context.aspect.annotation.IgnoreRequestLog;
import host.springboot.framework.context.chain.RequestInfoChainExecute;
import host.springboot.framework.context.util.HttpRequestUtils;
import host.springboot.framework3.core.logging.LoggingComponent;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.model.ResponseInfo;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 请求日志 WebFilter (响应式版本)
 *
 * <p>本过滤器用于在响应式 Web 环境中记录请求日志，替代 Servlet 环境的 {@code @Aspect} 实现。</p>
 *
 * <p><b>核心特性：</b></p>
 * <ul>
 *     <li>与 Spring WebFlux 响应式流完全兼容</li>
 *     <li>支持异步非阻塞日志记录</li>
 *     <li>通过 {@link RequestInfoChainExecute} 执行前置和后置处理链</li>
 *     <li>自动记录请求执行时间</li>
 *     <li>将 {@link RequestInfo} 存入 Reactor Context，供下游组件访问</li>
 *     <li>支持 {@link IgnoreRequestLog} 注解，可在类或方法级别忽略日志记录</li>
 * </ul>
 *
 * <p><b>设计说明：</b></p>
 * <ul>
 *     <li>WebFlux 环境中无法使用 {@code RequestContextHolder}，需通过 Reactor Context 传递请求信息</li>
 *     <li>使用 {@link WebFilter} 而非 {@code @Aspect}，因为 AOP 在响应式流中无法正确拦截异步操作</li>
 *     <li>通过 {@code doOnSuccess} 和 {@code doFinally} 实现后置处理和资源清理</li>
 * </ul>
 *
 * <p><b>使用方式：</b></p>
 * <pre>{@code
 * @Bean
 * public RequestLogFilter requestLogFilter(
 *         List<RequestInfoChainExecute> chainExecutes) {
 *     return new RequestLogFilter(chainExecutes);
 * }
 * }</pre>
 *
 * <p><b>注解支持：</b></p>
 * <p>使用 {@link IgnoreRequestLog} 注解可忽略日志记录：</p>
 * <pre>{@code
 * @IgnoreRequestLog // 类级别：忽略整个 Controller 的所有方法
 * @RestController
 * public class UserController {
 *
 *     @IgnoreRequestLog // 方法级别：仅忽略该方法
 *     @GetMapping("/health")
 *     public Mono<String> health() {
 *         return Mono.just("OK");
 *     }
 * }
 * }</pre>
 *
 * @param requestInfoChainExecutes 请求信息链式处理器列表
 * @author JiYinchuan
 * @since 0.2.0
 */
public record RequestLogFilter(
        @Nullable List<@NonNull RequestInfoChainExecute> requestInfoChainExecutes
) implements WebFilter, Ordered, LoggingComponent {

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
    
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
    
        // 先执行过滤器链,让 DispatcherHandler 设置 HandlerMethod
        return chain.filter(exchange)
                .doOnEach(signal -> {
                    // 在请求完成后检查是否应该忽略日志记录
                    if (!signal.isOnComplete() && !signal.isOnError()) {
                        return;
                    }
    
                    Object handler = exchange.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
                    Method method = null;
                    if (handler instanceof HandlerMethod handlerMethod) {
                        method = handlerMethod.getMethod();
    
                        // 检查方法级别或类级别的 @IgnoreRequestLog 注解
                        if (method.isAnnotationPresent(IgnoreRequestLog.class) ||
                                handlerMethod.getBeanType().isAnnotationPresent(IgnoreRequestLog.class)) {
                            return;
                        }
                    }
    
                    stopWatch.stop();
                    long executionTime = stopWatch.getTotalTimeMillis();
    
                    // 解析请求信息
                    RequestInfo requestInfo = HttpRequestUtils.parseInfo(request, method);
    
                    // 解析请求参数
                    HttpRequestUtils.parseRequestArgs(exchange, method)
                            .flatMap(requestArgs -> {
                                requestInfo.setRequestMethodArgs(requestArgs);
    
                                // 将 RequestInfo 存入 Exchange 属性,供其他组件访问
                                exchange.getAttributes().put(RequestInfo.class.getSimpleName(), requestInfo);
    
                                ResponseInfo responseInfo = new ResponseInfo()
                                        .setExecutionTime(executionTime);
    
                                // 执行前置和后置请求信息链式处理器
                                return this.executeBeforeChain(request, requestInfo)
                                        .then(this.executeAfterChain(request, requestInfo, responseInfo));
                            })
                            .contextWrite(context -> context.put(RequestInfo.class.getSimpleName(), requestInfo))
                            .subscribe();
                })
                .contextWrite(context -> {
                    // 预先创建基础 RequestInfo 供其他组件在请求处理期间使用
                    RequestInfo requestInfo = HttpRequestUtils.parseInfo(request, null);
                    exchange.getAttributes().put(RequestInfo.class.getSimpleName(), requestInfo);
                    return context.put(RequestInfo.class.getSimpleName(), requestInfo);
                });
    }

    /**
     * 执行前置请求信息链式处理器
     *
     * @param request     服务器请求对象
     * @param requestInfo 请求信息
     * @return Mono&lt;Void&gt; 异步操作
     */
    private @NonNull Mono<Void> executeBeforeChain(
            @NonNull ServerHttpRequest request,
            @NonNull RequestInfo requestInfo) {
        if (Objects.isNull(requestInfoChainExecutes) || requestInfoChainExecutes.isEmpty()) {
            return Mono.empty();
        }
        // 串联所有前置处理链
        return Mono.when(requestInfoChainExecutes.stream()
                .map(executor -> executor.beforeExecute(request, requestInfo))
                .toList()
        );
    }

    /**
     * 执行后置请求信息链式处理器
     *
     * @param request      服务器请求对象
     * @param requestInfo  请求信息
     * @param responseInfo 响应信息
     * @return Mono&lt;Void&gt; 异步操作
     */
    private @NonNull Mono<Void> executeAfterChain(
            @NonNull ServerHttpRequest request,
            @NonNull RequestInfo requestInfo,
            @NonNull ResponseInfo responseInfo) {
        if (Objects.isNull(requestInfoChainExecutes) || requestInfoChainExecutes.isEmpty()) {
            return Mono.empty();
        }
        // 串联所有后置处理链
        return Mono.when(requestInfoChainExecutes.stream()
                .map(executor -> executor.afterExecute(request, requestInfo, responseInfo))
                .toList()
        );
    }

    @Override
    public int getOrder() {
        return ExecuteOrder.Filter.REQUEST_LOG;
    }

    @Override
    public @NonNull String logTag() {
        return "KS-Filter-RequestLog";
    }
}
