package host.springboot.framework3.core.util.inner;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 集合工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-collections/">Apache's Commons Collections</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>isEmpty</b> - 检查集合是否为空</li>
 *     <li><b>isNotEmpty</b> - 检查集合是否不为空</li>
 *     <li><b>defaultCollection</b> - 如果为空则返回默认字符串</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see Collection
 * @since 0.1.0
 */
public final class CollectionUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private CollectionUtils() {
    }

    /**
     * 检查集合是否为空
     *
     * @param coll 检查的集合, 可为空
     * @return 集合是否为空
     * @since 0.1.0
     */
    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 检查集合是否不为空
     *
     * @param coll 检查的集合, 可为空
     * @return 集合是否不为空
     * @since 0.1.0
     */
    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 如果 {@code str} 为 {@code null}, 则返回 {@code defaultStr}
     *
     * @param <T>               集合类型
     * @param collection        集合, 可为空
     * @param defaultCollection 返回的默认集合
     * @return 返回传入的 {@code collection}, 如果为 {@code null} 时则返回 {@code defaultCollection}
     */
    public static <T extends Collection<?>> T defaultCollection(final T collection, final T defaultCollection) {
        return collection == null ? defaultCollection : collection;
    }

    /**
     * 如果 {@code str} 为 {@code null}, 则返回 {@code defaultStr}
     *
     * @param <T>                       集合类型
     * @param collection                集合, 可为空
     * @param defaultCollectionSupplier 返回的默认集合函数式方法
     * @return 返回传入的 {@code collection}, 如果为 {@code null} 时则返回 {@code defaultCollection}
     */
    public static <T extends Collection<?>> T defaultCollection(
            final T collection, final Supplier<T> defaultCollectionSupplier) {
        return collection == null ? defaultCollectionSupplier.get() : collection;
    }
}
