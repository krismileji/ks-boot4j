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

        // 检查是否需要忽略日志记录（通过 @IgnoreRequestLog 注解）
        return shouldIgnoreRequestLog(exchange)
                .flatMap(shouldIgnore -> {
                    if (shouldIgnore) {
                        // 忽略日志记录，直接继续过滤器链
                        return chain.filter(exchange);
                    }

                    // 尝试从 Exchange 获取 HandlerMethod
                    Object handler = exchange.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
                    Method method = null;
                    if (handler instanceof HandlerMethod handlerMethod) {
                        method = handlerMethod.getMethod();
                    }

                    // 解析请求信息
                    RequestInfo requestInfo = HttpRequestUtils.parseInfo(request, method);

                    // 解析请求参数：结合 Query 参数和 FormData（如果存在）
                    return HttpRequestUtils.parseRequestArgs(exchange, method)
                            .flatMap(requestArgs -> {
                                requestInfo.setRequestMethodArgs(requestArgs);

                                // 将 RequestInfo 存入 Exchange 属性，供其他组件访问
                                exchange.getAttributes().put(RequestInfo.class.getSimpleName(), requestInfo);

                                StopWatch stopWatch = new StopWatch();
                                stopWatch.start();

                                // 执行前置请求信息链式处理器
                                return this.executeBeforeChain(request, requestInfo)
                                        .then(chain.filter(exchange))
                                        .doOnSuccess(unused -> {
                                            // 请求成功完成后的处理
                                            stopWatch.stop();
                                            long executionTime = stopWatch.getTotalTimeMillis();

                                            // 构造响应信息（WebFlux 中无法直接获取响应结果，仅记录执行时间）
                                            ResponseInfo responseInfo = new ResponseInfo()
                                                    .setExecutionTime(executionTime);

                                            // 执行后置请求信息链式处理器（注意：这里是同步执行，因为 doOnSuccess 不支持返回 Mono）
                                            this.executeAfterChain(request, requestInfo, responseInfo).subscribe();
                                        })
                                        .doOnError(throwable -> {
                                            // 请求执行出错时的处理
                                            stopWatch.stop();
                                            long executionTime = stopWatch.getTotalTimeMillis();

                                            ResponseInfo responseInfo = new ResponseInfo()
                                                    .setExecutionTime(executionTime);

                                            // 执行后置处理链（错误情况）
                                            this.executeAfterChain(request, requestInfo, responseInfo).subscribe();
                                        })
                                        .contextWrite(context -> context.put(RequestInfo.class.getSimpleName(), requestInfo));
                            });
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

    /**
     * 判断是否应该忽略请求日志记录
     *
     * <p>通过检查 Handler 方法或类上的 {@link IgnoreRequestLog} 注解来判断</p>
     * <p>利用 WebFlux 在 Exchange 属性中存储的 Handler 信息</p>
     *
     * @param exchange 服务器交换对象
     * @return Mono&lt;Boolean&gt; true 表示应该忽略日志，false 表示应该记录日志
     */
    private @NonNull Mono<Boolean> shouldIgnoreRequestLog(@NonNull ServerWebExchange exchange) {
        // 从 Exchange 属性中获取 HandlerMethod（由 DispatcherHandler 设置）
        Object handler = exchange.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            // 如果不是 HandlerMethod 类型，默认不忽略
            return Mono.just(false);
        }

        // 检查方法级别的 @IgnoreRequestLog 注解
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(IgnoreRequestLog.class)) {
            return Mono.just(true);
        }

        // 检查类级别的 @IgnoreRequestLog 注解
        Class<?> beanType = handlerMethod.getBeanType();
        boolean hasClassAnnotation = beanType.isAnnotationPresent(IgnoreRequestLog.class);
        return Mono.just(hasClassAnnotation);
    }
}
