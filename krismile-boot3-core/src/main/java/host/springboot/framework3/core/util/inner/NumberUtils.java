package host.springboot.framework3.core.util.inner;

/**
 * 数字工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>isParsable</b> - 是否为可解析的数字</li>
 *     <li><b>withDecimalsParsing</b> - 带小数进行解析是否为可解析的数字</li>
 * </ul>
 *
 * @author JiYinchuan
 * @see String
 * @since 0.1.0
 */
public final class NumberUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private NumberUtils() {
    }

    /**
     * 是否为可解析的数字
     *
     * <p>该方法不支持十六进制与科学计数法
     *
     * @param str 需要判断的字符串
     * @return [true: 可解析, false: 不可解析]
     * @since 0.1.0
     */
    public static boolean isParsable(final String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        if (str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            return withDecimalsParsing(str, 1);
        }
        return withDecimalsParsing(str, 0);
    }

    /**
     * 带小数进行解析是否为可解析的数字
     *
     * @param str      需要解析的字符串
     * @param beginIdx 开始索引
     * @return 是否为数字
     * @since 0.1.0
     */
    private static boolean withDecimalsParsing(final String str, final int beginIdx) {
        int decimalPoints = 0;
        for (int i = beginIdx; i < str.length(); i++) {
            final boolean isDecimalPoint = str.charAt(i) == '.';
            if (isDecimalPoint) {
                decimalPoints++;
            }
            if (decimalPoints > 1) {
                return false;
            }
            if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
