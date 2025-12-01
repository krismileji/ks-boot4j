package host.springboot.framework.context.chain;

import host.springboot.framework.context.aspect.RequestLogAspect;
import host.springboot.framework3.core.model.RequestInfo;
import host.springboot.framework3.core.model.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;

/**
 * 请求信息链式处理
 *
 * <p>该类提供两个方法, 分别在方法执行前和执行后自定义处理逻辑, 可用于日至记录或日至打印等自定义逻辑
 *
 * @author JiYinchuan
 * @see RequestLogAspect
 * @see DefaultRequestLogChainExecute
 * @since 0.1.0
 */
public interface RequestInfoChainExecute {

    /**
     * 默认执行顺序
     */
    int DEFAULT_EXECUTE_ORDER = 100;

    /**
     * 处理前置请求信息
     *
     * @param request     {@link HttpServletRequest}
     * @param requestInfo 请求信息
     * @since 0.1.0
     */
    default void beforeExecute(
            @NonNull HttpServletRequest request,
            @NonNull RequestInfo requestInfo) {
    }

    /**
     * 处理后置请求信息
     *
     * @param request      {@link HttpServletRequest}
     * @param requestInfo  请求信息
     * @param responseInfo 请求结果信息
     * @since 0.1.0
     */
    default void afterExecute(
            @NonNull HttpServletRequest request,
            @NonNull RequestInfo requestInfo,
            @NonNull ResponseInfo responseInfo) {
    }
}
