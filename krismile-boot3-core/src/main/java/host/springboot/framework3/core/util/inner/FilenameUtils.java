package host.springboot.framework3.core.util.inner;

/**
 * 文件名称工具类
 *
 * <p>该类主要用于框架内的内部使用, 完整使用推荐
 * <a href="https://commons.apache.org/proper/commons-io/">Apache's Commons IO</a>,
 * 以获取更好的使用体验</p>
 *
 * <ul>
 *     <li><b>indexOfLastSeparator</b> - 返回最后一个目录分隔符的索引</li>
 *     <li><b>getName</b> - 获取文件的简略名称</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class FilenameUtils {

    private static final int NOT_FOUND = -1;

    /**
     * Unix 分隔符
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * Windows 分隔符
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private FilenameUtils() {
    }

    /**
     * 返回最后一个目录分隔符的索引
     *
     * <p>此方法将处理 Unix 或 Windows 格式的文件, 返回最后一个正斜杠或反斜杠的位置
     * <p>无论代码运行在什么机器上, 输出都是相同的
     *
     * @param fileName 查找最后一个路径分隔符的文件名, 为 {@code null} 时返回 {@code -1}
     * @return 最后一个分隔符的索引, 如果没有这样的字符, 则为 {@code -1}
     */
    public static int indexOfLastSeparator(final String fileName) {
        if (fileName == null) {
            return NOT_FOUND;
        }
        final int lastUnixPos = fileName.lastIndexOf(UNIX_SEPARATOR);
        final int lastWindowsPos = fileName.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }

    /**
     * 获取文件的简略名称
     *
     * <p>此方法将处理 Unix 或 Windows 格式的文件, 返回最后一个正斜杠或反斜杠之后的文本
     * <pre>{@code
     * a/b/c.txt --&gt; c.txt
     * a.txt     --&gt; a.txt
     * a/b/c     --&gt; c
     * a/b/c/    --&gt; ""
     * }</pre>
     * <p>无论代码运行在什么机器上, 输出都是相同的
     *
     * @param fileName 解析的原始文件名, 为 {@code null} 时返回 {@code null}
     * @return 不带路径的文件名, 如果不存在则为空字符串, 字符串内的空字节将被删除
     */
    public static String getName(final String fileName) {
        if (fileName == null) {
            return null;
        }
        failIfNullBytePresent(fileName);
        final int index = indexOfLastSeparator(fileName);
        return fileName.substring(index + 1);
    }

    /**
     * 检查输入是否有空字节, 这是传递给文件级函数的未清理数据的标志
     *
     * <p>这可用于投毒字节攻击
     *
     * @param path 检查路径
     */
    private static void failIfNullBytePresent(final String path) {
        final int len = path.length();
        for (int i = 0; i < len; i++) {
            if (path.charAt(i) == 0) {
                throw new IllegalArgumentException("Null byte present in file/path name. There are no " +
                        "known legitimate use cases for such data, but several injection attacks may use it");
            }
        }
    }
}
