package host.springboot.framework.autoconfigure.web.properties.jackson;

import org.springframework.boot.context.properties.ConfigurationProperties;

import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import lombok.Data;

/**
 * Jackson配置
 *
 * <pre>{@code
 * # -------------------------------- KrismileConfig --------------------------------
 * krismile:
 *   web:
 *     jackson:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = KsJacksonProperties.KEY)
public class KsJacksonProperties {

    /**
     * 配置文件前缀
     *
     * @since 0.2.0
     */
    public static final String KEY = KsWebProperties.KEY + "." + "jackson";

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
    public KsJacksonProperties() {
    }
}
