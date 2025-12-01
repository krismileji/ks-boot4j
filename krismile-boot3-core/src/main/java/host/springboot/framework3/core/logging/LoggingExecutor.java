package host.springboot.framework3.core.logging;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * 日志执行器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class LoggingExecutor {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private LoggingExecutor() {
    }

    // -------------------------------- 基础实例 --------------------------------

    /**
     * 普通日志实例
     *
     * @return 普通日志实例
     * @since 0.1.0
     */
    public static LoggingProvider instance() {
        return new LoggingImpl();
    }

    /**
     * 组件日志实例
     *
     * @param loggingComponent 日志组件
     * @return 组件日志实例
     * @since 0.1.0
     */
    public static LoggingComponentProvider instance(@NonNull LoggingComponent loggingComponent) {
        return new LoggingComponentExecutor(loggingComponent);
    }

    // -------------------------------- RPC --------------------------------

    /**
     * RPC日志执行器
     *
     * @param param    RPC参数
     * @param response RPC响应
     * @return RPC日志执行器
     * @since 0.1.0
     */
    public static LoggingRpcExecutor rpc(@Nullable Object param, @Nullable Object response) {
        return new LoggingRpcExecutor(param, response);
    }
}
