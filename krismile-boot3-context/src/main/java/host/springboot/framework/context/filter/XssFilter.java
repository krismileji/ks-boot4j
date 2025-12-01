package host.springboot.framework.context.filter;

import host.springboot.framework.context.ExecuteOrder;
import host.springboot.framework.context.filter.wrapper.XssWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.filter.OrderedFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * XSS攻击防御Filter
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class XssFilter implements OrderedFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(XssFilter.class);

    private static final String LOG_TAG = "KS-Filter-XSS";

    /**
     * 执行顺序
     */
    public static final int EXECUTE_ORDER = REQUEST_WRAPPER_FILTER_MAX_ORDER + ExecuteOrder.Filter.XSS_WRAPPER;

    /**
     * 执行条件
     */
    private final Predicate<HttpServletRequest> executePredicate;

    /**
     * 排除的参数名后缀
     */
    private final List<String> excludeParamEndWith;

    /**
     * 安全标签白名单
     */
    private final Safelist safeList;

    /**
     * 输出配置
     */
    private final Document.OutputSettings outputSettings;

    /**
     * 构造器
     *
     * @param config 配置信息
     * @since 0.1.0
     */
    public XssFilter(@NonNull Config config) {
        this.executePredicate = config.getExecutePredicate();
        this.excludeParamEndWith = config.getExcludeParamEndWith();
        this.safeList = config.getSafeList();
        this.outputSettings = config.getOutputSettings();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest tmpServletRequest = servletRequest;
        if (servletRequest instanceof HttpServletRequest request) {
            if (executePredicate.test(request)) {
                tmpServletRequest = new XssWrapper(request, excludeParamEndWith, safeList, outputSettings);
                LOGGER.debug("[{}] The HttpServletRequest has been Wrapped.", LOG_TAG);
            }
        }
        filterChain.doFilter(tmpServletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return EXECUTE_ORDER;
    }

    /**
     * XSS攻击防御Filter配置
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    @Data
    @Accessors(chain = true)
    public static class Config {

        /**
         * 执行条件
         */
        private Predicate<HttpServletRequest> executePredicate = request -> true;

        /**
         * 排除的参数名后缀
         */
        private List<String> excludeParamEndWith = new ArrayList<>();

        /**
         * 安全标签白名单
         */
        private Safelist safeList = Safelist.none();

        /**
         * 输出配置
         */
        private Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        public Config() {
        }
    }
}
