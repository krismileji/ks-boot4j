package host.springboot.framework3.core.logging;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

/**
 * 日志组件执行器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class LoggingComponentExecutor extends BaseLogging implements LoggingComponentProvider {

    /**
     * 日志组件
     */
    private final LoggingComponent component;

    /**
     * 构造器
     *
     * @param component 日志组件
     * @since 0.1.0
     */
    public LoggingComponentExecutor(@NonNull LoggingComponent component) {
        this.component = component;
    }

    @Override
    public @NonNull Logger log() {
        return component.log();
    }

    @Override
    public @NonNull String logTag() {
        return component.logTag();
    }
}
