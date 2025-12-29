package host.springboot.framework.context.util;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.util.Assert;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 请求工具类
 *
 * <p>该类主要用于处理请求相关数据</p>
 * <ul>
 *     <li><b>parseInfo</b> - 解析请求相关信息</li>
 *     <li><b>isJsonRequest</b> - 判断是否为JSON请求</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
public class HttpRequestUtils {

    /**
     * 方法参数发现器
     */
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 私有构造器，防止实例化
     *
     * @since 0.2.0
     */
    private HttpRequestUtils() {
    }

    /**
     * 解析请求相关信息
     *
     * @param request ServerHttpRequest
     * @return RequestInfo
     * @since 0.2.0
     */
    public static @NonNull RequestInfo parseInfo(@NonNull ServerHttpRequest request) {
        return parseInfo(request, null);
    }

    /**
     * 解析请求相关信息
     *
     * @param request ServerHttpRequest
     * @param method  请求方法，用于解析方法参数名
     * @return RequestInfo
     * @since 0.2.0
     */
    public static @NonNull RequestInfo parseInfo(@NonNull ServerHttpRequest request, @Nullable Method method) {
        Assert.notNull(request, "ServerHttpRequest must not be null");

        String threadId = Long.toString(Thread.currentThread().threadId());
        String threadName = Thread.currentThread().getName();
        String uri = request.getURI().getPath();
        String clientIp = IpUtils.getIpv4Address(request);

        String userAgent = getFirstHeader(request, HttpHeaders.USER_AGENT);
        String os = null;
        String browser = null;
        UserAgent userAgentData = UserAgentUtil.parse(userAgent);
        if (Objects.nonNull(userAgentData)) {
            os = userAgentData.getOs().toString();
            browser = userAgentData.getBrowser().toString();
        }

        String userId = getFirstHeader(request, RequestInfo.Fields.userId);
        String userName = getFirstHeader(request, RequestInfo.Fields.userName);

        String methodType = request.getMethod().name();

        RequestInfo requestInfo = new RequestInfo()
                .setThreadId(threadId)
                .setThreadName(threadName)
                .setUri(uri)
                .setClientIp(clientIp)
                .setUserAgent(userAgent)
                .setOs(os)
                .setBrowser(browser)
                .setMethodType(methodType)
                .setUserId(userId)
                .setUserName(userName);

        if (Objects.nonNull(method)) {
            String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
            String parameterFullNames = "";
            if (Objects.nonNull(parameterNames)) {
                parameterFullNames = String.join(", ", parameterNames);
            }
            String fullMethodName = String.format("%s.%s(%s)", method.getDeclaringClass().getName(), method.getName(),
                    String.join(", ", parameterFullNames));
            requestInfo.setFullMethodName(fullMethodName);
        }
        return requestInfo;
    }

    /**
     * 判断是否为JSON请求
     *
     * @param request ServerHttpRequest
     * @return 是否为JSON请求 [true: 是, false: 否]
     * @since 0.2.0
     */
    public static boolean isJsonRequest(@NonNull ServerHttpRequest request) {
        Assert.notNull(request, "ServerHttpRequest must not be null");

        String contentTypeInHeaderValue = getFirstHeader(request, HttpHeaders.CONTENT_TYPE);
        return Objects.nonNull(contentTypeInHeaderValue)
                && contentTypeInHeaderValue.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 获取请求头的第一个值
     *
     * @param request ServerHttpRequest
     * @param headerName 请求头名称
     * @return 请求头值
     * @since 0.2.0
     */
    private static @Nullable String getFirstHeader(@NonNull ServerHttpRequest request, @NonNull String headerName) {
        List<String> headerValues = request.getHeaders().get(headerName);
        return Objects.nonNull(headerValues) && !headerValues.isEmpty() ? headerValues.getFirst() : null;
    }

    /**
     * 解析请求参数（响应式）
     *
     * <p>从 Query 参数、FormData 中提取参数值，按照方法参数顺序组装成 Object[] 数组</p>
     * <p><b>重要说明：</b>始终返回 {@code Mono.just(Object[])}，即使参数为空也返回空数组，
     * 确保下游 flatMap 操作符能够正常执行，避免参数信息丢失</p>
     *
     * @param exchange ServerWebExchange（非空）
     * @param method   请求方法（可为 null）
     * @return 请求参数数组的 Mono 包装，始终返回有值的 Mono（可能是空数组）
     * @since 0.2.0
     */
    public static @NonNull Mono<Object[]> parseRequestArgs(
            @NonNull ServerWebExchange exchange,
            @Nullable Method method) {
        Assert.notNull(exchange, "ServerWebExchange must not be null");
        
        if (Objects.isNull(method)) {
            return Mono.just(new Object[0]);
        }

        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        if (Objects.isNull(parameterNames) || parameterNames.length == 0) {
            return Mono.just(new Object[0]);
        }

        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        
        // 尝试从 FormData 中获取参数（仅适用于 application/x-www-form-urlencoded）
        return exchange.getFormData()
                .defaultIfEmpty(new LinkedMultiValueMap<>())
                .map(formData -> extractParameterValues(queryParams, formData, parameterNames))
                .onErrorResume(e -> {
                    // FormData 解析失败时，降级为仅使用 Query 参数
                    return Mono.just(extractParameterValues(queryParams, null, parameterNames));
                });
    }

    /**
     * 从参数映射中按顺序提取参数值
     *
     * @param queryParams    Query 参数映射
     * @param formData       FormData 参数映射（可为 null）
     * @param parameterNames 方法参数名称数组
     * @return 按方法参数顺序组装的参数值数组
     */
    private static @NonNull Object[] extractParameterValues(
            @NonNull MultiValueMap<String, String> queryParams,
            @Nullable MultiValueMap<String, String> formData,
            @NonNull String[] parameterNames) {
        // 合并 Query 和 FormData 参数
        MultiValueMap<String, String> allParams = new LinkedMultiValueMap<>(queryParams);
        if (Objects.nonNull(formData)) {
            allParams.addAll(formData);
        }

        // 按照参数名称顺序提取参数值
        Object[] args = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            List<String> values = allParams.get(parameterNames[i]);
            if (Objects.nonNull(values) && !values.isEmpty()) {
                args[i] = values.size() == 1 ? values.getFirst() : values;
            }
        }
        return args;
    }
}
