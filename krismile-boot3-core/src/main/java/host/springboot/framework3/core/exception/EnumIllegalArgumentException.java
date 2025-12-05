package host.springboot.framework3.core.exception;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * 枚举非法论证异常
 *
 * <p>主要用于自定义枚举转换时发生的异常</p>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class EnumIllegalArgumentException extends IllegalArgumentException {

    /**
     * 构造器
     *
     * @param message 错误信息
     * @since 0.1.0
     */
    public EnumIllegalArgumentException(@NonNull String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param message 错误信息
     * @param cause   异常
     * @since 0.1.0
     */
    public EnumIllegalArgumentException(@NonNull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param cause 异常
     * @since 0.1.0
     */
    public EnumIllegalArgumentException(@Nullable Throwable cause) {
        super(cause);
    }
}
