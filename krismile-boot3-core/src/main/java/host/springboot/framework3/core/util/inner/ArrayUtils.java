package host.springboot.framework3.core.util.inner;

import java.lang.reflect.Array;

/**
 * 数组工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>getLength</b> - 获取数组的长度</li>
 *     <li><b>isEmpty</b> - 查数组是否为空或为 {@code null}</li>
 *     <li><b>isNotEmpty</b> - 检查数组是否不为空或不为 {@code null}.</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see String
 * @since 0.1.0
 */
public final class ArrayUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private ArrayUtils() {
    }

    /**
     * 获取数组的长度
     *
     * <p>当数组为 {@code null} 时, 返回 {@code 0}
     *
     * <pre>{@code
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * }</pre>
     *
     * @param array 数组, 可为空或 {@code null}
     * @return 数组的长度, 当数组为 {@code null} 时, 返回 {@code 0}
     * @throws IllegalArgumentException 当参数不是数组时
     * @since 0.1.0
     */
    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    /**
     * 检查数组是否为空或为 {@code null}
     *
     * @param array 待检查的数组, 可为空或 {@code null}
     * @return 当数组为空或为 {@code null} 时返回 {@code true}
     * @since 0.1.0
     */
    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    /**
     * 检查数组是否不为空或不为 {@code null}.
     *
     * @param <T>   数据中元素类型
     * @param array 待检查的数组, 可为空或 {@code null}
     * @return 当数组为不空且不为 {@code null} 时返回 {@code true}
     * @since 0.1.0
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }
}
