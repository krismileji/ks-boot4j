package host.springboot.framework.context.chain;

import host.springboot.framework3.core.logging.LoggingComponent;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.model.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认请求信息链式处理器
 *
 * @param debugPrintHeaderNames DEBUG模式打印的请求头名称
 * @author JiYinchuan
 * @since 0.1.0
 */
public record DefaultRequestLogChainExecute(
        @NonNull String[] debugPrintHeaderNames)
        implements LoggingComponent, RequestInfoChainExecute, Ordered {

    /**
     * 日志标签
     */
    private static final String LOG_TAG = "KS-RequestInfo-Execute";

    @Override
    public void beforeExecute(
            @NonNull HttpServletRequest request,
            @NonNull RequestInfo requestInfo) {
        // 请求URI
        // noinspection DuplicatedCode
        String uri = requestInfo.getUri();
        // 客户端真实请求IP地址
        String clientIp = requestInfo.getClientIp();
        // 请求方法类型
        String requestType = requestInfo.getMethodType();
        // 完整请求方法
        String fullMethodName = requestInfo.getFullMethodName();
        // 请求的方法参数
        Object[] requestMethodArgs = requestInfo.getRequestMethodArgs();
        // 用户ID
        String userId = requestInfo.getUserId();
        // 用户名称
        String userName = requestInfo.getUserName();

        log().info("[{}-Begin] 检测到请求 [clientIp: {}, userId: {}, username: {}, requestUri: {}, requestType: {}, method: {}, params: {}]",
                LOG_TAG, clientIp, userId, userName, uri, requestType, fullMethodName, requestMethodArgs);
    }

    @Override
    public void afterExecute(
            @NonNull HttpServletRequest request,
            @NonNull RequestInfo requestInfo,
            @NonNull ResponseInfo responseInfo) {
        // 请求URI
        // noinspection DuplicatedCode
        String uri = requestInfo.getUri();
        // 客户端真实请求IP地址
        String clientIp = requestInfo.getClientIp();
        // 请求方法类型
        String requestType = requestInfo.getMethodType();
        // 完整请求方法
        String fullMethodName = requestInfo.getFullMethodName();
        // 请求的方法参数
        Object[] requestMethodArgs = requestInfo.getRequestMethodArgs();
        // 用户ID
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
    }

    @Override
    public int getOrder() {
        return DEFAULT_EXECUTE_ORDER;
    }

    /**
     * 解析Debug模式请求头
     *
     * @param request {@link HttpServletRequest}
     * @return 所有请求头参数, {@code headerNames} 不为空时则返回对应的请求头, 为空时则返回所有
     * @since 0.1.0
     */
    private Map<String, String> parseDebugHeaders(@NonNull HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>(16);
        Enumeration<String> tmpHeaderNames = request.getHeaderNames();
        while (tmpHeaderNames.hasMoreElements()) {
            String headName = tmpHeaderNames.nextElement();
            boolean isMatch = false;
            for (String paramHeaderName : debugPrintHeaderNames) {
                if (headName.equalsIgnoreCase(paramHeaderName.trim())) {
                    isMatch = true;
                    break;
                }
            }
            if (!isMatch) {
                continue;
            }
            String headerValue = request.getHeader(headName);
            headers.put(headName, headerValue);
        }
        return headers;
    }

    @Override
    public @NonNull String logTag() {
        return LOG_TAG;
    }
}
