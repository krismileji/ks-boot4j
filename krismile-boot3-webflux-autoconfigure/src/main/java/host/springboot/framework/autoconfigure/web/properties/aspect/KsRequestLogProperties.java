package host.springboot.framework.autoconfigure.web.properties.aspect;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import lombok.Data;

/**
 * 请求日志配置文件
 *
 * <pre>{@code
 * # -------------------------------- KrismileConfig --------------------------------
 * krismile:
 *   web:
 *     request-log:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 *       # DEBUG模式下打印的请求头名称数组, 不区分大小写, 为 [null] 时将打印所有请求头
 *       debug-print-header-names:
 *         - Accept
 *         - Content-Type
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = KsRequestLogProperties.KEY)
public class KsRequestLogProperties {

    /**
     * 配置文件前缀
     *
     * @since 0.2.0
     */
    public static final String KEY = KsWebProperties.KEY + "." + "request-log";

    /**
     * 是否启用
     *
     * @since 0.2.0
     */
    private boolean enabled = true;

    /**
     * DEBUG模式下打印的请求头名称数组, 不区分大小写, 为 {@code null} 时将打印所有请求头
     *
     * @since 0.2.0
     */
    private String[] debugPrintHeaderNames = {HttpHeaders.ACCEPT, HttpHeaders.CONTENT_TYPE};

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    public KsRequestLogProperties() {
    }
}
