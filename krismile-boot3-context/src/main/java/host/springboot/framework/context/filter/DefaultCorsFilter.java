package host.springboot.framework.context.filter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.Duration;

/**
 * 默认跨域过滤器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class DefaultCorsFilter extends CorsFilter implements FactoryBean<CorsFilter>, PriorityOrdered {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public DefaultCorsFilter() {
        super(ConfigurationSource.instance());
    }

    @Override
    public CorsFilter getObject() {
        return new DefaultCorsFilter();
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultCorsFilter.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 默认CORS配置源
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    public static class ConfigurationSource extends UrlBasedCorsConfigurationSource {

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        private ConfigurationSource(CorsConfiguration corsConfiguration) {
            this.registerCorsConfiguration("/**", corsConfiguration);
        }

        /**
         * 获取默认跨域配置源实例
         *
         * @return 默认跨域配置源实例
         * @since 0.1.0
         */
        public static ConfigurationSource instance() {
            return new ConfigurationSource(defaultCorsConfiguration());
        }

        /**
         * 默认CORS配置
         *
         * @return 默认CORS配置
         * @since 0.1.0
         */
        public static CorsConfiguration defaultCorsConfiguration() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            // 允许的域, 为 [*] 时Cookie无法使用
            corsConfiguration.addAllowedOriginPattern("*");
            // 允许的请求方式
            corsConfiguration.addAllowedMethod(HttpMethod.GET);
            corsConfiguration.addAllowedMethod(HttpMethod.HEAD);
            corsConfiguration.addAllowedMethod(HttpMethod.POST);
            corsConfiguration.addAllowedMethod(HttpMethod.PUT);
            corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
            corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
            corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
            corsConfiguration.addAllowedMethod(HttpMethod.TRACE);
            // 允许的头信息
            corsConfiguration.addAllowedHeader("*");
            // 预检请求的有效期
            corsConfiguration.setMaxAge(Duration.ofHours(1));
            // 是否支持安全证书
            corsConfiguration.setAllowCredentials(true);
            return corsConfiguration;
        }
    }
}
