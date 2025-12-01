package host.springboot.framework.context.util;

import host.springboot.framework3.core.util.Assert;
import host.springboot.framework3.core.util.inner.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP工具类
 *
 * <p>该类提供了获取客户端IP的方法
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class IpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    /**
     * 未知IP常量
     */
    private static final String UNKNOWN = "unknown";

    /**
     * IPV4正则表达式
     */
    private static final Pattern ACCESS_IPV4_PATTERN = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})" +
            "(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");

    /**
     * 本机IPV4地址
     */
    private static final String LOCALHOST_IPV4 = "127.0.0.1";

    /**
     * 本机IPV6地址
     */
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private IpUtils() {
    }

    /**
     * 获取客户端真实IP地址
     *
     * <p>使用Nginx等反向代理软件， 则不能通过{@link HttpServletRequest#getRemoteAddr()} 获取IP地址,
     * 否则拿到的是Nginx等反向代理软件所在的IP地址, 并非真实的客户端IP
     * <p>当设置了 {@code X-Forwarded-For} 时, 如果使用了多级反向代理, {@code X-Forwarded-For} 的值并不止一个(),
     * 而是一串IP地址(逗号分割), {@code X-Forwarded-For} 中第一个非 {@code unknown} 的有效IP字符串, 则为真实IP地址
     * (客户端可以伪造 {@code X-Forwarded-For} 请求头, 需要验证IP正确性)
     * <hr>
     * <ul>
     *     <li>X-Forwarded-For: 该字段为行业统一请求头, 并非标准请求头, 用于Nginx等反向代理软件转发请求来源的IP地址</li>
     * </ul>
     *
     * @param request HttpServletRequest
     * @return 客户端真实IP地址
     * @since 0.1.0
     */
    public static @NonNull String getIpv4Address(@NonNull HttpServletRequest request) {
        Assert.notNull(request, "HttpServletRequest must not be null");

        String ip = request.getHeader("x-forwarded-for");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            // noinspection AlibabaUndefineMagicConstant
            if (ip != null && ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        } else {
            Matcher matcher = ACCESS_IPV4_PATTERN.matcher(Objects.requireNonNull(ip));
            if (matcher.find()) {
                ip = matcher.group();
            }
        }
        if (LOCALHOST_IPV4.equals(ip) || LOCALHOST_IPV6.equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    /**
     * 是否为无效IP
     *
     * @param ip IP
     * @return 是否为无效IP
     * @since 0.1.0
     */
    private static boolean isInvalidIp(@Nullable String ip) {
        return StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }
}
