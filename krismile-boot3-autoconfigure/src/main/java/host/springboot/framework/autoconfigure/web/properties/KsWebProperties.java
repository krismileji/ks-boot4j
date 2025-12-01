package host.springboot.framework.autoconfigure.web.properties;

import host.springboot.framework.autoconfigure.KrismileProperties;
import host.springboot.framework.autoconfigure.web.properties.aspect.KsRequestLogProperties;
import host.springboot.framework.autoconfigure.web.properties.filter.KsRequestBodyWrapperProperties;
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
@ConfigurationProperties(prefix = KsWebProperties.KEY)
public class KsWebProperties {

    /**
     * 配置文件前缀
     */
    public static final String KEY = KrismileProperties.KEY + "." + "web";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * 时区
     */
    private TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * 日期时间格式化类型
     */
    private DateTimeFormatTypeEnum timeFormatType = DateTimeFormatTypeEnum.DELIMITED;

    /**
     * Jackson配置文件
     */
    @NestedConfigurationProperty
    private KsJacksonProperties jackson = new KsJacksonProperties();

    /**
     * 请求日志配置文件
     */
    @NestedConfigurationProperty
    private KsRequestLogProperties requestLog = new KsRequestLogProperties();

    /**
     * 是否启用请求体包装过滤器自动配置
     */
    @NestedConfigurationProperty
    private KsRequestBodyWrapperProperties requestBodyWrapper = new KsRequestBodyWrapperProperties();

    /**
     * XSS攻击防御配置
     */
    @NestedConfigurationProperty
    private KsXssProperties xss = new KsXssProperties();

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsWebProperties() {
    }
}
