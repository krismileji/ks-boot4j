package host.springboot.framework3.core.logging;

import host.springboot.framework3.core.exception.ApplicationException;
import host.springboot.framework3.core.model.Pair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 基础日志
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public sealed class BaseLogging implements LoggingProvider permits LoggingImpl, LoggingComponentExecutor {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    protected BaseLogging() {
    }

    @Override
    public @NonNull String log(
            @NonNull Logger logger,
            @NonNull Level level,
            @NonNull String logTag,
            @NonNull String logDetailTag,
            @Nullable Throwable throwable,
            @Nullable List<@NonNull Pair<@Nullable String, @Nullable Object>> additionalMessages) {
        StringJoiner joiner = new StringJoiner(", ");
        if (Objects.nonNull(additionalMessages)) {
            for (Pair<String, Object> additionalMessage : additionalMessages) {
                joiner.add(additionalMessage.left() + ": " + additionalMessage.right());
            }
        }
        String messagePattern;
        Object[] argArray;
        if (throwable instanceof ApplicationException exception) {
            messagePattern = "[{}] {} [{}, errorCode: {}, errorMessage: {}, userTip: {}]";
            argArray = new Object[]{logTag, logDetailTag, joiner.toString(),
                    exception.getErrorCode(), exception.getErrorMessage(), exception.getUserTip()};
            throwable = null;
        } else {
            messagePattern = "[{}] {} [{}]";
            argArray = new Object[]{logTag, logDetailTag, joiner.toString()};
        }
        String message = MessageFormatter.arrayFormat(messagePattern, argArray, throwable).getMessage();
        switch (level) {
            case TRACE:
                logger.trace(message, throwable);
                break;
            case DEBUG:
                logger.debug(message, throwable);
                break;
            case INFO:
                logger.info(message, throwable);
                break;
            case WARN:
                logger.warn(message, throwable);
                break;
            case ERROR:
                logger.error(message, throwable);
                break;
            default:
        }
        return message;
    }
}
