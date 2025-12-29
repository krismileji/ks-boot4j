package host.springboot.framework.context;

/**
 * 定义框架组件执行顺序的常量类
 * 用于控制 WebFilter、Aspect 等组件的执行优先级（响应式模式）
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
public final class ExecuteOrder {

    /**
     * 私有构造器，防止实例化
     *
     * @since 0.2.0
     */
    private ExecuteOrder() {
    }

    /**
     * AOP 切面执行顺序常量
     *
     * @author JiYinchuan
     * @since 0.2.0
     */
    public static final class Aop {

        /**
         * 请求日志记录切面的执行顺序
         *
         * @since 0.2.0
         */
        public static final int REQUEST_LOG = 100;

        /**
         * 参数校验切面的执行顺序
         *
         * @since 0.2.0
         */
        public static final int CHECK_PARAM = 200;

        /**
         * 私有构造器，防止实例化
         *
         * @since 0.2.0
         */
        private Aop() {
        }
    }

    /**
     * WebFilter执行顺序常量（响应式模式）
     *
     * <p><b>说明:</b> 响应式环境下使用 WebFilter,通过 {@code @Order} 注解或 {@code Ordered} 接口控制执行顺序。
     * 数值越小优先级越高,建议包装类过滤器使用负数以确保最先执行。</p>
     *
     * @author JiYinchuan
     * @since 0.2.0
     */
    public static final class Filter {

        /**
         * 请求体包装过滤器的执行顺序
         *
         * @since 0.2.0
         */
        public static final int REQUEST_BODY_WRAPPER = -10000;

        /**
         * XSS 攻击防御过滤器的执行顺序
         *
         * @since 0.2.0
         */
        public static final int XSS_WRAPPER = -9000;

        /**
         * 请求日志记录过滤器的执行顺序（响应式版本）
         *
         * @since 0.2.0
         */
        public static final int REQUEST_LOG = 100;

        /**
         * 私有构造器，防止实例化
         *
         * @since 0.2.0
         */
        private Filter() {
        }
    }
}
