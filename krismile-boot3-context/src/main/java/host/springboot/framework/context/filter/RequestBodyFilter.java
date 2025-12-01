package host.springboot.framework.context.filter;

import host.springboot.framework.context.ExecuteOrder;
import host.springboot.framework.context.filter.wrapper.RequestBodyWrapper;
import host.springboot.framework.context.util.HttpRequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.filter.OrderedFilter;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * 请求体包装过滤器
 *
 * @param executePredicate 执行条件
 * @author JiYinchuan
 * @since 0.1.0
 */
public record RequestBodyFilter(Predicate<HttpServletRequest> executePredicate) implements OrderedFilter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestBodyFilter.class);

    /**
     * 日志标签
     */
    private static final String LOG_TAG = "KS-Filter-RequestBody";

    /**
     * 执行顺序
     *
     * <p>此处 {@link ExecuteOrder.Filter#REQUEST_BODY_WRAPPER} 必须为负数, 因为必须小于 {@value REQUEST_WRAPPER_FILTER_MAX_ORDER} 值
     */
    public static final int EXECUTE_ORDER = REQUEST_WRAPPER_FILTER_MAX_ORDER + ExecuteOrder.Filter.REQUEST_BODY_WRAPPER;

    /**
     * 构造器
     *
     * @param executePredicate 配置
     * @since 0.1.0
     */
    public RequestBodyFilter(@NonNull Config executePredicate) {
        this(executePredicate.getExecutePredicate());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        ServletRequest tmpServletRequest = servletRequest;
        if (servletRequest instanceof HttpServletRequest request) {
            if (executePredicate.test(request)) {
                tmpServletRequest = new RequestBodyWrapper(request);
                LOGGER.trace("[{}] The HttpServletRequest has been Wrapped.", LOG_TAG);
            }
        }
        filterChain.doFilter(tmpServletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return EXECUTE_ORDER;
    }

    /**
     * 请求体包装过滤器配置
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    @Data
    public static class Config {

        /**
         * 执行条件
         */
        private final Predicate<HttpServletRequest> executePredicate = HttpRequestUtils::isJsonRequest;

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        public Config() {
        }
    }
}
