package host.springboot.framework.autoconfigure;

import host.springboot.framework3.core.util.inner.SpringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 基础自动配置类
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KrismileProperties.class)
@Import(SpringUtils.class)
public class KrismileAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KrismileAutoConfiguration() {
    }
}
