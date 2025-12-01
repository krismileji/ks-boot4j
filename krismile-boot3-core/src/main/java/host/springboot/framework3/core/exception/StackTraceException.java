package host.springboot.framework3.core.exception;

import org.jspecify.annotations.NonNull;

/**
 * 堆栈自定义异常
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class StackTraceException extends RuntimeException {

    /**
     * 构造器
     *
     * @param message 错误信息
     * @since 0.1.0
     */
    public StackTraceException(@NonNull String message) {
        super(message);
    }
}
