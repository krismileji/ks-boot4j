package host.springboot.framework.autoconfigure.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import host.springboot.framework.autoconfigure.web.properties.filter.KsXssProperties;
import host.springboot.framework.context.filter.XssFilter;

/**
 * XSS 攻击拦截自动配置类
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableConfigurationProperties(KsXssProperties.class)
@ConditionalOnProperty(
        prefix = KsXssProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class KsXssFilterAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    public KsXssFilterAutoConfiguration() {
    }

    /**
     * XSS 过滤器配置
     *
     * @return XSS 过滤器配置
     * @since 0.2.0
     */
    @Bean
    @ConditionalOnMissingBean
    public XssFilter.Config xssFilterConfig() {
        return new XssFilter.Config();
    }

    /**
     * XSS过滤器（响应式版本）
     *
     * @param config XSS 过滤器配置
     * @return XSS 过滤器
     * @since 0.2.0
     */
    @Bean
    @ConditionalOnMissingBean(XssFilter.class)
    public XssFilter xssFilter(XssFilter.Config config) {
        return new XssFilter(config);
    }
}
