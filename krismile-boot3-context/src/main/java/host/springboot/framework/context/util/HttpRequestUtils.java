package host.springboot.framework.context.util;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.util.Assert;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求工具类
 *
 * <p>该类主要用于处理请求相关数据
 * <ul>
 *     <li><b>parseInfo</b> - 解析请求相关信息</li>
 *     <li><b>isJsonRequest</b> - 判断是否为JSON请求</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class HttpRequestUtils {

    /**
     * 方法参数发现器
     */
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private HttpRequestUtils() {
    }

    /**
     * 解析请求相关信息
     *
     * @param request HttpServletRequest
     * @return RequestInfo
     * @since 0.1.0
     */
    public static @NonNull RequestInfo parseInfo(@NonNull HttpServletRequest request) {
        return parseInfo(request, null);
    }

    /**
     * 解析请求相关信息
     *
     * @param request HttpServletRequest
     * @param method  请求方法
     * @return RequestInfo
     * @since 0.1.0
     */
    public static @NonNull RequestInfo parseInfo(@NonNull HttpServletRequest request, @Nullable Method method) {
        Assert.notNull(request, "HttpServletRequest must not be null");

        String threadId = Long.toString(Thread.currentThread().threadId());
        String threadName = Thread.currentThread().getName();
        String uri = request.getRequestURI();
        String clientIp = IpUtils.getIpv4Address(request);

        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String os = null;
        String browser = null;
        UserAgent userAgentData = UserAgentUtil.parse(userAgent);
        if (Objects.nonNull(userAgentData)) {
            os = userAgentData.getOs().toString();
            browser = userAgentData.getBrowser().toString();
        }

        String userId = request.getHeader(RequestInfo.Fields.userId);
        String userName = request.getHeader(RequestInfo.Fields.userName);

        String methodType = request.getMethod();
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
     * @param request HttpServletRequest
     * @return 是否为JSON请求 [true: 是, false: 否]
     * @since 0.1.0
     */
    public static boolean isJsonRequest(@NonNull HttpServletRequest request) {
        Assert.notNull(request, "HttpServletRequest must not be null");

        String contentTypeInHeaderValue = request.getHeader(HttpHeaders.CONTENT_TYPE);
        return Objects.nonNull(contentTypeInHeaderValue)
                && contentTypeInHeaderValue.contains(MediaType.APPLICATION_JSON_VALUE);
    }
}
