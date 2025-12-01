package host.springboot.framework.autoconfigure.web;

import host.springboot.framework.autoconfigure.web.properties.filter.KsXssProperties;
import host.springboot.framework.context.filter.XssFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * XSS攻击拦截自动配置类
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
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
     * @since 0.1.0
     */
    public KsXssFilterAutoConfiguration() {
    }

    /**
     * XSS过滤器配置
     *
     * @return XSS过滤器配置
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnMissingBean
    public XssFilter.Config xssFilterConfig() {
        return new XssFilter.Config();
    }

    /**
     * XSS过滤器
     *
     * @param config XSS过滤器配置
     * @return XSS过滤器
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnMissingBean(XssFilter.class)
    public FilterRegistrationBean<XssFilter> xssFilter(XssFilter.Config config) {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        XssFilter xssFilter = new XssFilter(config);
        registrationBean.setName("xssFilter");
        registrationBean.setFilter(xssFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(xssFilter.getOrder());
        return registrationBean;
    }
}
