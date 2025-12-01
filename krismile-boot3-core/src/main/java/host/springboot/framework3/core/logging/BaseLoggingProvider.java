package host.springboot.framework3.core.logging;

import host.springboot.framework3.core.model.Pair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基础日志提供器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface BaseLoggingProvider {

    /**
     * 日志打印
     *
     * @param logger             日志源
     * @param level              日志级别
     * @param logTag             日志标签
     * @param logDetailTag       日志详细标签
     * @param throwable          原始异常
     * @param additionalMessages 附加信息
     * @return 日志字符串
     * @since 0.1.0
     */
    @NonNull
    String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages);

    /**
     * 获取附加信息
     *
     * @param additionalMessage 附加信息
     * @return 附加信息
     * @since 0.1.0
     */
    default @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> getAdditionalMessages(
            @Nullable Pair<@Nullable String, @Nullable Object> additionalMessage) {
        return Objects.nonNull(additionalMessage) ? Collections.singletonList(additionalMessage) : null;
    }

    /**
     * 获取附加信息
     *
     * @param additionalMessages 附加信息
     * @return 附加信息
     * @since 0.1.0
     */
    @SuppressWarnings("unchecked")
    default List<@NonNull Pair<@Nullable String, @Nullable Object>> getAdditionalMessages(
            @Nullable Pair<@Nullable String, @Nullable Object>... additionalMessages) {
        return Objects.nonNull(additionalMessages)
                ? Arrays.stream(additionalMessages).filter(Objects::nonNull).collect(Collectors.toList())
                : null;
    }
}
