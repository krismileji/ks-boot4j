package host.springboot.framework3.core.logging;

import host.springboot.framework3.core.model.Pair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.List;

/**
 * 日志提供器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface LoggingProvider extends BaseLoggingProvider {

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag) {
        return this.trace(logger, logTag, logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable) {
        return this.trace(logger, logTag, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.trace(logger, logTag, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    @SuppressWarnings("unchecked")
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object>... additionalMessages) {
        return this.trace(logger, logTag, logDetailTag, null, getAdditionalMessages(additionalMessages));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.trace(logger, logTag, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.trace(logger, logTag, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String trace(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.log(logger, Level.TRACE, logTag, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String debug(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag) {
        return this.debug(logger, logTag, logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String debug(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable) {
        return this.debug(logger, logTag, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String debug(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.debug(logger, logTag, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String debug(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.debug(logger, logTag, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String debug(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.debug(logger, logTag, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String debug(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.log(logger, Level.DEBUG, logTag, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String info(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag) {
        return this.info(logger, logTag, logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String info(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable) {
        return this.info(logger, logTag, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String info(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.info(logger, logTag, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String info(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.info(logger, logTag, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String info(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.info(logger, logTag, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String info(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.log(logger, Level.INFO, logTag, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String warn(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag) {
        return this.warn(logger, logTag, logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String warn(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable) {
        return this.warn(logger, logTag, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String warn(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.warn(logger, logTag, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String warn(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.warn(logger, logTag, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String warn(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.warn(logger, logTag, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String warn(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.log(logger, Level.WARN, logTag, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String error(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag) {
        return this.error(logger, logTag, logDetailTag, null, (List<Pair<String, Object>>) null);
    }


    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String error(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable) {
        return this.error(logger, logTag, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }


    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String error(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.error(logger, logTag, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String error(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.error(logger, logTag, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String error(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.error(logger, logTag, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String error(
            @NonNull Logger logger,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.log(logger, Level.ERROR, logTag, logDetailTag, throwable, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param level        日志级别
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag) {
        return this.log(logger, level, logTag, logDetailTag, null, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger       日志源
     * @param level        日志级别
     * @param logTag       日志标签
     * @param logDetailTag 日志详细标签
     * @param throwable    原始异常
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable) {
        return this.log(logger, level, logTag, logDetailTag, throwable, (List<Pair<String, Object>>) null);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param level             日志级别
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.log(logger, level, logTag, logDetailTag, null, getAdditionalMessages(additionalMessage));
    }

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param level              日志级别
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        return this.log(logger, level, logTag, logDetailTag, null, additionalMessages);
    }

    /**
     * 日志打印
     *
     * @param logger            日志源
     * @param level             日志级别
     * @param logTag            日志标签
     * @param logDetailTag      日志详细标签
     * @param throwable         原始异常
     * @param additionalMessage 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    default @NonNull String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return this.log(logger, level, logTag, logDetailTag, throwable, getAdditionalMessages(additionalMessage));
    }
}
