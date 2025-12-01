package host.springboot.framework3.core.execute;

import host.springboot.framework3.core.util.inner.CollectionUtils;
import host.springboot.framework3.core.util.inner.StringUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 对象执行器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class ObjectExecutor {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private ObjectExecutor() {
    }

    /**
     * 条件执行器
     *
     * @param condition 执行条件
     * @param callback  执行回调
     * @since 0.1.0
     */
    public static void execute(
            @Nullable Boolean condition,
            @NonNull Consumer<@NonNull Boolean> callback) {
        if (Objects.nonNull(condition) && condition) {
            callback.accept(true);
        }
    }

    /**
     * 条件执行器
     *
     * @param condition 执行条件
     * @param callback  执行回调
     * @since 0.1.0
     */
    public static void execute(
            @Nullable Supplier<@NonNull Boolean> condition,
            @NonNull Consumer<@NonNull Boolean> callback) {
        if (Objects.nonNull(condition) && condition.get()) {
            callback.accept(true);
        }
    }

    /**
     * 非 {@literal null} 执行器
     *
     * @param condition 非 {@literal null} 时执行
     * @param callback  执行回调
     * @since 0.1.0
     */
    public static void notNullExecute(
            @Nullable Object condition,
            @NonNull Consumer<@NonNull Boolean> callback) {
        if (Objects.nonNull(condition)) {
            callback.accept(true);
        }
    }

    /**
     * 字符串非空执行器
     *
     * @param condition 非 {@literal null} 且非空时执行
     * @param callback  执行回调
     * @since 0.1.0
     */
    public static void notBlankExecute(
            @Nullable String condition,
            @NonNull Consumer<@NonNull Boolean> callback) {
        if (StringUtils.isNotBlank(condition)) {
            callback.accept(true);
        }
    }

    /**
     * 字符串非空执行器
     *
     * @param condition 非 {@literal null} 且非空时执行
     * @param callback  执行回调
     * @since 0.1.0
     */
    public static void notEmptyExecute(
            @Nullable Collection<?> condition,
            @NonNull Consumer<@NonNull Boolean> callback) {
        if (CollectionUtils.isNotEmpty(condition)) {
            callback.accept(true);
        }
    }
}
