package host.springboot.framework3.core.util.inner;

/**
 * 字符串工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>isBlank</b> - 检查字符串序列是否为空</li>
 *     <li><b>isNotBlank</b> - 检查字符串序列是否不为空</li>
 *     <li><b>defaultString</b> - 如果为空则返回默认字符串</li>
 *     <li><b>length</b> - 获取字符串序列的长度</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see String
 * @since 0.1.0
 */
public final class StringUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private StringUtils() {
    }

    /**
     * 检查字符串序列是否为空
     *
     * <p>空格检查使用 {@link Character#isWhitespace(char)}
     *
     * <pre>{@code
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * }</pre>
     *
     * @param cs 字符串序列, 可为空
     * @return 字符串序列是否为空
     * @since 0.1.0
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串序列是否不为空
     *
     * <p>空格检查使用 {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>{@code
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * }</pre>
     *
     * @param cs 字符串序列, 可为空
     * @return 字符串序列是否不为空
     * @since 0.1.0
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 如果 {@code str} 为 {@code null}, 则返回 {@code defaultStr}
     *
     * <pre>{@code
     * StringUtils.defaultString(null, "NULL")  = "NULL"
     * StringUtils.defaultString("", "NULL")    = ""
     * StringUtils.defaultString(" ", "EMPTY")  = "EMPTY"
     * StringUtils.defaultString("bat", "NULL") = "bat"
     * }</pre>
     *
     * @param str        字符串, 可为空
     * @param defaultStr 返回的默认字符串
     * @return 返回传入的 {@code str}, 如果为空时则返回 {@code defaultStr}
     * @see #isBlank(CharSequence)
     */
    public static String defaultString(final String str, final String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * 获取字符串序列的长度
     *
     * @param cs 字符串序列, 可为空
     * @return 字符串序列的长度, 字符串序列为空时长度为 {@code 0}
     * @since 0.1.0
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
}
