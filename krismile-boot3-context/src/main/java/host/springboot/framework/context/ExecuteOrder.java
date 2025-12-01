package host.springboot.framework.context;

import org.springframework.boot.web.servlet.filter.OrderedFilter;

/**
 * 执行顺序
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class ExecuteOrder {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private ExecuteOrder() {
    }

    /**
     * AOP执行顺序
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    public static final class Aop {

        /**
         * 请求日志记录执行顺序
         */
        public static final int REQUEST_LOG = 100;

        /**
         * 检查参数执行顺序
         */
        public static final int CHECK_PARAM = 200;

        /**
         * 私有构造器
         *
         * @since 0.1.0
         */
        private Aop() {
        }
    }

    /**
     * 过滤器执行顺序
     *
     * <p><b>Warning:</b> 如果过滤器包装了servlet则必须大于或小于 {@value OrderedFilter#REQUEST_WRAPPER_FILTER_MAX_ORDER},
     * <a href="https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.embedded-container.servlets-filters-listeners.beans">点击查看官方文档说明</a></p>
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    public static final class Filter {

        /**
         * 请求体包装执行顺序
         */
        public static final int REQUEST_BODY_WRAPPER = -10000;

        /**
         * XSS攻击防御包装器执行顺序
         */
        public static final int XSS_WRAPPER = -9000;

        /**
         * 私有构造器
         *
         * @since 0.1.0
         */
        private Filter() {
        }
    }
}
