package host.springboot.framework3.core.util;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * 验证参数断言类, 有助于在运行时更早地识别程序员错误
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class Assert {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private Assert() {
    }

    /**
     * 断言对象不为 {@code null}
     *
     * @param object       断言的对象
     * @param errorMessage 错误信息
     * @since 0.1.0
     */
    public static void notNull(@Nullable Object object, String errorMessage) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
