package host.springboot.framework.context.chain;

import host.springboot.framework3.core.logging.LoggingComponent;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.model.ResponseInfo;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认请求信息链式处理器（Reactive 版本）
 *
 * <p>该实现遵循响应式编程范式，所有处理操作均返回 {@link Mono}，确保与 WebFlux 非阻塞模型完全兼容</p>
 *
 * <p><b>核心特性：</b></p>
 * <ul>
 *     <li>非阻塞日志记录 - 使用 Mono 包装所有日志操作</li>
 *     <li>不可变性保证 - 仅读取请求信息，不修改原始对象</li>
 *     <li>DEBUG 模式支持 - 可配置打印特定请求头</li>
 * </ul>
 *
 * @param debugPrintHeaderNames DEBUG 模式打印的请求头名称
 * @author JiYinchuan
 * @since 0.2.0
 */
public record DefaultRequestLogChainExecute(
        @NonNull String[] debugPrintHeaderNames)
        implements LoggingComponent, RequestInfoChainExecute, Ordered {

    /**
     * 日志标签
     */
    private static final String LOG_TAG = "KS-RequestInfo-Execute";

    @Override
    public Mono<Void> beforeExecute(
            @NonNull ServerHttpRequest request,
            @NonNull RequestInfo requestInfo) {
        return Mono.fromRunnable(() -> {
            // 请求 URI
            String uri = requestInfo.getUri();
            // 客户端真实请求 IP 地址
            String clientIp = requestInfo.getClientIp();
            // 请求方法类型
            String requestType = requestInfo.getMethodType();
            // 完整请求方法
            String fullMethodName = requestInfo.getFullMethodName();
            // 请求的方法参数
            Object[] requestMethodArgs = requestInfo.getRequestMethodArgs();
            // 用户 ID
            String userId = requestInfo.getUserId();
            // 用户名称
            String userName = requestInfo.getUserName();

            log().info("[{}-Begin] 检测到请求 [clientIp: {}, userId: {}, username: {}, requestUri: {}, requestType: {}, method: {}, params: {}]",
                    LOG_TAG, clientIp, userId, userName, uri, requestType, fullMethodName, requestMethodArgs);
        });
    }

    @Override
    public Mono<Void> afterExecute(
            @NonNull ServerHttpRequest request,
            @NonNull RequestInfo requestInfo,
            @NonNull ResponseInfo responseInfo) {
        return Mono.fromRunnable(() -> {
            // 请求 URI
            String uri = requestInfo.getUri();
            // 客户端真实请求 IP 地址
            String clientIp = requestInfo.getClientIp();
            // 请求方法类型
            String requestType = requestInfo.getMethodType();
            // 完整请求方法
            String fullMethodName = requestInfo.getFullMethodName();
            // 请求的方法参数
            Object[] requestMethodArgs = requestInfo.getRequestMethodArgs();
            // 用户 ID
            String userId = requestInfo.getUserId();
            // 用户名称
            String userName = requestInfo.getUserName();
            // 请求结果
            Object result = responseInfo.getResult();
            // 请求执行时间
            Long executionTime = responseInfo.getExecutionTime();

            log().info("[{}-End] 检测到请求 [clientIp: {}, userId: {}, username: {}, requestUri: {}, requestType: {}, method: {}, params: {}, executeTime: {}ms]",
                    LOG_TAG, clientIp, userId, userName, uri, requestType, fullMethodName, requestMethodArgs, executionTime);

            if (isDebugEnabled()) {
                // 请求头参数
                Map<String, String> headers = parseDebugHeaders(request);
                // 操作系统
                String os = requestInfo.getOs();
                // 浏览器
                String browser = requestInfo.getBrowser();

                log().debug("[{}-End] 检测到请求 [clientIp: {}, requestUri: {}, requestType: {}, headers: {}, os: {}, browser: {}, method: {}, params: {}, executeTime: {}ms, result: {}]",
                        LOG_TAG, clientIp, uri, requestType, headers, os, browser, fullMethodName, requestMethodArgs,
                        executionTime, result);
            }
        });
    }

    @Override
    public int getOrder() {
        return DEFAULT_EXECUTE_ORDER;
    }

    /**
     * 解析 DEBUG 模式请求头
     *
     * <p>根据配置的请求头名称列表，从请求中提取对应的请求头值</p>
     * <p>仅在 DEBUG 日志级别启用时被调用</p>
     *
     * @param request {@link ServerHttpRequest} 响应式请求对象
     * @return 所有请求头参数，{@code headerNames} 不为空时则返回对应的请求头，为空时则返回空 Map
     * @since 0.2.0
     */
    private Map<String, String> parseDebugHeaders(@NonNull ServerHttpRequest request) {
        Map<String, String> headers = new HashMap<>(16);

        if (debugPrintHeaderNames == null) {
            return headers;
        }

        for (String paramHeaderName : debugPrintHeaderNames) {
            String trimmedHeaderName = paramHeaderName.trim();
            String headerValue = request.getHeaders().getFirst(trimmedHeaderName);
            if (headerValue != null) {
                headers.put(trimmedHeaderName, headerValue);
            }
        }

        return headers;
    }

    @Override
    public @NonNull String logTag() {
        return LOG_TAG;
    }
}
