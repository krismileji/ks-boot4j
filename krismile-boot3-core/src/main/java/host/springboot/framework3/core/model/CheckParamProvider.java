package host.springboot.framework3.core.model;

import host.springboot.framework3.core.util.inner.CollectionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 检查参数提供者
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface CheckParamProvider {

    /**
     * 检查
     *
     * @since 0.1.0
     */
    default void check() {

    }

    /**
     * 检查联合集合是否为 {@code null}
     *
     * @param source  数据源
     * @param sources 数据源集合
     * @param <T>     数据源类型
     * @param <C>     数据源集合类型
     * @return 是否为 {@code null}
     * @since 0.1.0
     */
    default <T, C extends Collection<T>> boolean checkAssembleCollectionNull(
            @Nullable T source, @Nullable C sources) {
        return ObjectUtils.isEmpty(source) && CollectionUtils.isEmpty(sources);
    }

    /**
     * 检查Pair自身和内部值是否为 {@code null}
     *
     * @param source 数据源
     * @return 是否为 {@code null}
     * @since 0.1.0
     */
    default boolean checkPairNull(@Nullable Pair<?, ?> source) {
        return Objects.isNull(source) || Objects.nonNull(source.left()) && Objects.nonNull(source.right());
    }
}
