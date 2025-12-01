package host.springboot.framework3.core.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * 日志提供者
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface LoggingComponent {

    /**
     * 获取日志对象实例
     *
     * @return 日志对象实例
     * @since 0.1.0
     */
    @JsonIgnore
    default @NonNull Logger log() {
        return LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 获取日志标签
     *
     * @return 日志标签
     * @since 0.1.0
     */
    @JsonIgnore
    @NonNull
    String logTag();

    /**
     * 获取日志打印级别
     *
     * @return 日志打印级别
     * @since 5.241201.0
     */
    @JsonIgnore
    default @NonNull Level logLevel() {
        Logger log = log();
        if (log.isTraceEnabled()) {
            return Level.TRACE;
        }
        if (log.isDebugEnabled()) {
            return Level.DEBUG;
        }
        if (log.isInfoEnabled()) {
            return Level.INFO;
        }
        if (log.isWarnEnabled()) {
            return Level.WARN;
        }
        if (log.isErrorEnabled()) {
            return Level.ERROR;
        }
        return Level.INFO;
    }

    /**
     * 是否启用TRACE模式
     *
     * @return 是否启用TRACE模式
     * @since 5.241201.0
     */
    @JsonIgnore
    default boolean isTraceEnabled() {
        return logLevel().toInt() <= Level.TRACE.toInt();
    }

    /**
     * 是否启用DEBUG模式
     *
     * @return 是否启用DEBUG模式
     * @since 5.241001.0
     */
    @JsonIgnore
    default boolean isDebugEnabled() {
        return logLevel().toInt() <= Level.DEBUG.toInt();
    }

    /**
     * 是否启用WARN模式
     *
     * @return 是否启用WARN模式
     * @since 5.241201.0
     */
    @JsonIgnore
    default boolean isWarnEnabled() {
        return logLevel().toInt() <= Level.WARN.toInt();
    }

    /**
     * 是否启用ERROR模式
     *
     * @return 是否启用ERROR模式
     * @since 5.241201.0
     */
    @JsonIgnore
    default boolean isErrorEnabled() {
        return logLevel().toInt() <= Level.ERROR.toInt();
    }

    /**
     * 日志实例
     *
     * @return 日志实例
     * @since 0.1.0
     */
    @JsonIgnore
    default LoggingComponentProvider logInstance() {
        return LoggingExecutor.instance(this);
    }
}
