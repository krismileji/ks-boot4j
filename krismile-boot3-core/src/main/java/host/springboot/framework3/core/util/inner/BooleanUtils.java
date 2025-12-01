package host.springboot.framework3.core.util.inner;

/**
 * 布尔类型工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>isTrue</b> - 检查 {@code Boolean} 类型是否为 {@code true}</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see String
 * @since 0.1.0
 */
public final class BooleanUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private BooleanUtils() {
    }

    /**
     * 检查 {@code Boolean} 类型是否为 {@code true}
     *
     * <pre>{@code
     *   BooleanUtils.isTrue(Boolean.TRUE)  = true
     *   BooleanUtils.isTrue(Boolean.FALSE) = false
     *   BooleanUtils.isTrue(null)          = false
     * }</pre>
     *
     * @param bool 待检查的布尔类型, 为 {@code null} 时返回 {@code false}
     * @return 当输入为非 null 切值为 {@code true} 时返回 true
     * @since 0.1.0
     */
    public static boolean isTrue(final Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }
}
