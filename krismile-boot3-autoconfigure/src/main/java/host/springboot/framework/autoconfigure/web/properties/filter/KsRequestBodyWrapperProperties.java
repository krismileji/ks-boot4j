package host.springboot.framework.autoconfigure.web.properties.filter;

import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 请求体包装配置文件
 *
 * <pre>{@code
 * # -------------------------------- KrismileConfig --------------------------------
 * krismile:
 *   web:
 *     request-body-wrapper:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@ConfigurationProperties(prefix = KsRequestBodyWrapperProperties.KEY)
public class KsRequestBodyWrapperProperties {

    /**
     * 配置文件前缀
     */
    public static final String KEY = KsWebProperties.KEY + "." + "request-body-wrapper";

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsRequestBodyWrapperProperties() {
    }
}
