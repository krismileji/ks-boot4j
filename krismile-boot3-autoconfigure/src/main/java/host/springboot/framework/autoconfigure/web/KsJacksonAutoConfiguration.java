package host.springboot.framework.autoconfigure.web;

import host.springboot.framework.autoconfigure.web.jackson.JacksonObjectMapperBuilder;
import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import host.springboot.framework.autoconfigure.web.properties.jackson.KsJacksonProperties;
import host.springboot.framework3.core.constant.KrismileConstant;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson 自动配置类
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(
        prefix = KsJacksonProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties({KsWebProperties.class, KsJacksonProperties.class})
public class KsJacksonAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsJacksonAutoConfiguration() {
    }

    /**
     * Jackson 配置
     *
     * @param jacksonProperties Jackson 配置文件
     * @param ksWebProperties   Web 配置文件
     * @return Jackson配置
     * @since 0.2.0
     */
    @Bean(KrismileConstant.KRISMILE_ABBREVIATION_LOWERCASE + "Jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(
            ObjectProvider<JacksonProperties> jacksonProperties,
            KsWebProperties ksWebProperties) {
        return new JacksonObjectMapperBuilder(jacksonProperties.getIfAvailable(), ksWebProperties);
    }
}
