package host.springboot.framework3.core.logging;

import host.springboot.framework3.core.model.Pair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.event.Level;

import java.util.List;

/**
 * 日志组件提供器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface LoggingComponentProvider extends LoggingComponent, BaseLoggingProvider {

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default String trace(@NonNull String logDetailTag) {
        return this.trace(logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default String trace(@NonNull String logDetailTag, @Nullable Throwable throwable) {
        return this.trace(logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String trace(@NonNull String logDetailTag, @Nullable Pair<String, Object> additionalMessage) {
        return this.trace(logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    @SuppressWarnings("unchecked")
    default String trace(@NonNull String logDetailTag, @Nullable Pair<String, Object> @NonNull ... additionalMessages) {
        return this.trace(logDetailTag, null, getAdditionalMessages(additionalMessages));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String trace(@NonNull String logDetailTag, @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.trace(logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String trace(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.trace(logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String trace(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(Level.TRACE, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default String debug(@NonNull String logDetailTag) {
        return this.debug(logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default String debug(@NonNull String logDetailTag, @Nullable Throwable throwable) {
        return this.debug(logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String debug(@NonNull String logDetailTag, @Nullable Pair<String, Object> additionalMessage) {
        return this.debug(logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String debug(@NonNull String logDetailTag, @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.debug(logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String debug(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.debug(logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String debug(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(Level.DEBUG, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default String info(@NonNull String logDetailTag) {
        return this.info(logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default String info(@NonNull String logDetailTag, @Nullable Throwable throwable) {
        return this.info(logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String info(@NonNull String logDetailTag, @Nullable Pair<String, Object> additionalMessage) {
        return this.info(logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String info(@NonNull String logDetailTag, @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.info(logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String info(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.info(logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String info(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(Level.INFO, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default String warn(@NonNull String logDetailTag) {
        return this.warn(logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default String warn(@NonNull String logDetailTag, @Nullable Throwable throwable) {
        return this.warn(logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String warn(@NonNull String logDetailTag, @Nullable Pair<String, Object> additionalMessage) {
        return this.warn(logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String warn(@NonNull String logDetailTag, @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.warn(logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String warn(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.warn(logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String warn(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(Level.WARN, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default String error(@NonNull String logDetailTag) {
        return this.error(logDetailTag, null, (List<Pair<String, Object>>) null);
    }


    /**
     * 日志打印
     *
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default String error(@NonNull String logDetailTag, @Nullable Throwable throwable) {
        return this.error(logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }


    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String error(@NonNull String logDetailTag, @Nullable Pair<String, Object> additionalMessage) {
        return this.error(logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String error(@NonNull String logDetailTag, @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.error(logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String error(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.error(logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String error(
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(Level.ERROR, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param level        日志级别
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default String log(@NonNull Level level, @NonNull String logDetailTag) {
        return this.log(level, logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param level        日志级别
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default String log(@NonNull Level level, @NonNull String logDetailTag, @Nullable Throwable throwable) {
        return this.log(level, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param level             日志级别
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String log(
            @NonNull Level level, @NonNull String logDetailTag,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.log(level, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param level              日志级别
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String log(
            @NonNull Level level, @NonNull String logDetailTag,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(level, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param level             日志级别
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String log(
            @NonNull Level level, @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable Pair<String, Object> additionalMessage) {
        return this.log(level, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param level              日志级别
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default String log(
            @NonNull Level level, @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        return this.log(log(), level, logTag(), logDetailTag, throwable, additionalMessages);
    }
}
