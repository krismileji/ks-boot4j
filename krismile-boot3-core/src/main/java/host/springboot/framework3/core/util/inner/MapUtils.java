package host.springboot.framework3.core.util.inner;

import java.util.Map;

/**
 * Map工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>isEmpty</b> - 检查Map是否为空</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see Map
 * @since 0.1.0
 */
public final class MapUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private MapUtils() {
    }

    /**
     * 检查 Map 是否为空
     * <p>
     * Null returns true.
     * </p>
     *
     * @param map Map, 可为空
     * @return Map 是否为空
     * @since 0.1.0
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
