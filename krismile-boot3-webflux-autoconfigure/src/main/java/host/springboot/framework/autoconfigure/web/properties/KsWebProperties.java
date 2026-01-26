package host.springboot.framework.autoconfigure.web.properties;

import host.springboot.framework.autoconfigure.KsProperties;
import host.springboot.framework.autoconfigure.web.properties.aspect.KsRequestLogProperties;
import host.springboot.framework.autoconfigure.web.properties.filter.KsXssProperties;
import host.springboot.framework.autoconfigure.web.properties.jackson.KsJacksonProperties;
import host.springboot.framework3.core.enumeration.date.DateTimeFormatTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.TimeZone;

/**
 * 上下文配置文件
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
 *     # XSS攻击防御配置
 *     xss:
 *       # 是否启用自动配置, 默认为 [true]
 *       enabled: true
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = KsWebProperties.KEY)
public class KsWebProperties {

    /**
     * 配置文件前缀
     *
     * @since 0.2.0
     */
    public static final String KEY = KsProperties.KEY + "." + "web";

    /**
     * 是否启用
     *
     * @since 0.2.0
     */
    private Boolean enabled = true;

    /**
     * 时区
     *
     * @since 0.2.0
     */
    private TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * 日期时间格式化类型
     *
     * @since 0.2.0
     */
    private DateTimeFormatTypeEnum timeFormatType = DateTimeFormatTypeEnum.DELIMITED;

    /**
     * Jackson配置文件
     *
     * @since 0.2.0
     */
    @NestedConfigurationProperty
    private KsJacksonProperties jackson = new KsJacksonProperties();

    /**
     * 请求日志配置文件
     *
     * @since 0.2.0
     */
    @NestedConfigurationProperty
    private KsRequestLogProperties requestLog = new KsRequestLogProperties();

    /**
     * XSS攻击防御配置
     *
     * @since 0.2.0
     */
    @NestedConfigurationProperty
    private KsXssProperties xss = new KsXssProperties();

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    public KsWebProperties() {
    }
}
