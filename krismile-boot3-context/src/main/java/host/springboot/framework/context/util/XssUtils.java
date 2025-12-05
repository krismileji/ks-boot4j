package host.springboot.framework.context.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Xss过滤工具类
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class XssUtils {

    /**
     * 默认允许通过的安全列表
     */
    private static final Safelist DEFAULT_SAFE_LIST = Safelist.none();

    /**
     * 默认输出配置
     *
     * <p>此处禁止了格式化代码输出</p>
     */
    private static final Document.OutputSettings DEFAULT_OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    /**
     * 私有构造器
     */
    private XssUtils() {
    }

    /**
     * 清除非法标签
     *
     * @param content 需要清除的内容
     * @return 清除后的内容, 当 {@code content} 为 {@code null} 时返回原值
     * @see #clean(String, Safelist)
     * @since 0.1.0
     */
    public static String clean(@NonNull String content) {
        return clean(content, DEFAULT_SAFE_LIST);
    }

    /**
     * 清除非法标签
     *
     * @param content  需要清除的内容
     * @param safeList 需要排除的标签列表
     * @return 清除后的内容, 当 {@code content} 为 {@code null} 时返回原值
     * @see #clean(String, Safelist, Document.OutputSettings)
     * @since 0.1.0
     */
    public static String clean(@NonNull String content, @NonNull Safelist safeList) {
        return clean(content, safeList, DEFAULT_OUTPUT_SETTINGS);
    }

    /**
     * 清除非法标签
     *
     * @param content        需要清除的内容
     * @param safeList       需要排除的标签列表
     * @param outputSettings 输出结果的配置
     * @return 清除后的内容, 当 {@code content} 为 {@code null} 时返回原值
     * @since 0.1.0
     */
    public static String clean(
            @Nullable String content,
            @NonNull Safelist safeList,
            Document.@NonNull OutputSettings outputSettings) {
        if (Objects.isNull(content)) {
            return null;
        }
        return Jsoup.clean(content, "", safeList, outputSettings);
    }
}
