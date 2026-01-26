package host.springboot.framework.autoconfigure.web.properties.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import lombok.Data;

/**
 * XSS攻击防御配置文件
 *
 * <pre>{@code
 * # -------------------------------- KrismileConfig --------------------------------
 * krismile:
 *   web:
 *     xss:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = KsXssProperties.KEY)
public class KsXssProperties {

    /**
     * 配置文件前缀
     *
     * @since 0.2.0
     */
    public static final String KEY = KsWebProperties.KEY + "." + "xss";

    /**
     * 是否启用
     *
     * @since 0.2.0
     */
    private boolean enabled = true;

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    public KsXssProperties() {
    }
}
