package host.springboot.framework.autoconfigure;

import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import host.springboot.framework3.core.constant.KrismileConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 标准配置文件
 *
 * <pre>{@code
 * # -------------------------------- KrismileConfig --------------------------------
 * krismile:
 *   web:
 *     # 是否启用自动配置, 默认为 [true]
 *     enabled: true
 *     # 时区, 默认为 [Asia/Shanghai]
 *     time-zone: Asia/Shanghai
 *     # 时间格式化类型, 默认为 [DELIMITED]
 *     time-format-type: DELIMITED
 *     # Jackson 配置
 *     jackson:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 *     # 请求日志配置
 *     request-log:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 *       # DEBUG模式下打印的请求头名称数组, 不区分大小写, 为 [null] 时将打印所有请求头
 *       debug-print-header-names:
 *         - Accept
 *         - Content-Type
 *     # 请求体包装过滤器配置
 *     request-body-wrapper:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 *     # XSS攻击防御配置
 *     xss:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@ConfigurationProperties(prefix = KsProperties.KEY)
public class KsProperties {

    /**
     * krismile 配置文件 Key
     */
    public static final String KEY = KrismileConstant.KRISMILE_LOWERCASE;

    /**
     * Web配置文件
     */
    @NestedConfigurationProperty
    private KsWebProperties web = new KsWebProperties();

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsProperties() {
    }
}
