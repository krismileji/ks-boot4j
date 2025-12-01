package host.springboot.framework3.core.logging;

import host.springboot.framework3.core.model.Pair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.List;

/**
 * RPC日志执行器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class LoggingRpcExecutor extends LoggingImpl {

    /**
     * RPC参数
     */
    private final Object param;

    /**
     * RPC响应
     */
    private final Object response;

    /**
     * 构造器
     *
     * @param param    RPC参数
     * @param response RPC响应
     * @since 0.1.0
     */
    public LoggingRpcExecutor(Object param, Object response) {
        this.param = param;
        this.response = response;
    }

    @Override
    public @NonNull String log(
            @NonNull Logger logger, @NonNull Level level, @NonNull String logTag,
            @NonNull String logDetailTag, @Nullable Throwable throwable,
            @Nullable List<Pair<String, Object>> additionalMessages) {
        ArgArray argArray = ArgArray.builder();
        argArray.originalMessages(additionalMessages);
        argArray.additionalMessages(Pair.of("param", param), Pair.of("response", response));
        return super.log(logger, level, logTag, logDetailTag, throwable, argArray.parse());
    }
}
