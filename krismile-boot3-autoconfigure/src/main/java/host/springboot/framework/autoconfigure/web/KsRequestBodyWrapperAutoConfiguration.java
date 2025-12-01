package host.springboot.framework.autoconfigure.web;

import host.springboot.framework.autoconfigure.web.properties.filter.KsRequestBodyWrapperProperties;
import host.springboot.framework.context.filter.RequestBodyFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 请求体包装过滤器自动配置类
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(KsRequestBodyWrapperProperties.class)
@ConditionalOnProperty(
        prefix = KsRequestBodyWrapperProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class KsRequestBodyWrapperAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsRequestBodyWrapperAutoConfiguration() {
    }

    /**
     * 请求体包装过滤器配置
     *
     * @return 请求体包装过滤器配置
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestBodyFilter.Config requestBodyFilterConfig() {
        return new RequestBodyFilter.Config();
    }

    /**
     * 请求体包装过滤器
     *
     * @param config 请求体包装过滤器配置
     * @return 请求体包装过滤器
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnMissingBean(RequestBodyFilter.class)
    public FilterRegistrationBean<RequestBodyFilter> requestBodyFilter(RequestBodyFilter.Config config) {
        FilterRegistrationBean<RequestBodyFilter> registrationBean = new FilterRegistrationBean<>();
        RequestBodyFilter requestBodyFilter = new RequestBodyFilter(config);
        registrationBean.setName("requestBodyFilter");
        registrationBean.setFilter(requestBodyFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(requestBodyFilter.getOrder());
        return registrationBean;
    }
}
