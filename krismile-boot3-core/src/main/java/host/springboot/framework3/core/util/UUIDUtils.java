package host.springboot.framework3.core.util;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * UUID工具类
 *
 * <p>该类用于便捷的生成指定格式的 {@code uuid}
 * <ul>
 *     <li><b>randomOrigin</b> - 生成原始指定长度 {@link String} 累心的 {@code uuid}</li>
 *     <li><b>random</b> - 生成不带 {@code '-'} 符号指定长度 {@link String} 累心的 {@code uuid}</li>
 *     <li><b>randomOfInteger</b> - 生成不带 {@code '-'} 符号指定长度 {@link Integer} 累心的 {@code uuid}</li>
 *     <li><b>randomOfLang</b> - 生成不带 {@code '-'} 符号指定长度 {@link Long} 累心的 {@code uuid}</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class UUIDUtils {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private UUIDUtils() {
    }

    /**
     * 生成原始 {@link String} 格式的UUID
     *
     * @return 原始 {@link String} 格式的UUID
     * @since 0.1.0
     */
    public static @NonNull String randomOrigin() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成原始 {@link String} 格式的UUID
     *
     * @param length 长度
     * @return 原始 {@link String} 格式的UUID
     * @since 0.1.0
     */
    public static @NonNull String randomOrigin(int length) {
        String uuid = randomOrigin();
        return uuid.substring(0, Math.min(length, uuid.length()));
    }

    /**
     * 生成 {@link String} 格式的UUID
     *
     * @return {@link String} 格式的UUID
     * @since 0.1.0
     */
    public static @NonNull String random() {
        return randomOrigin().replaceAll("-", "");
    }

    /**
     * 生成指定长度 {@link String} 格式的UUID
     *
     * @param length 长度
     * @return {@link String} 格式的UUID
     * @since 0.1.0
     */
    public static @NonNull String random(int length) {
        String uuid = random();
        return uuid.substring(0, Math.min(length, uuid.length()));
    }

    /**
     * 生成 {@link String} 格式的UUID
     *
     * @return {@link Integer} 格式的UUID
     * @since 0.1.0
     */
    public static int randomOfInteger() {
        return Integer.parseInt(random());
    }

    /**
     * 生成指定长度 {@link Integer} 格式的UUID
     *
     * @param length 长度
     * @return {@link Integer} 格式的UUID
     * @since 0.1.0
     */
    public static int randomOfInteger(int length) {
        return Integer.parseInt(random(length));
    }

    /**
     * 生成 {@link String} 格式的UUID
     *
     * @return {@link Long} 格式的UUID
     * @since 0.1.0
     */
    public static long randomOfLang() {
        return Long.parseLong(random());
    }

    /**
     * 生成指定长度 {@link Long} 格式的UUID
     *
     * @param length 长度
     * @return {@link Long} 格式的UUID
     * @since 0.1.0
     */
    public static long randomOfLang(int length) {
        return Long.parseLong(random(length));
    }
}
