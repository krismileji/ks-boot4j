package host.springboot.framework.mybatisplus;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus自动配置
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
@AutoConfiguration(before = MybatisPlusAutoConfiguration.class)
@ImportAutoConfiguration(KsMybatisPlusMetaObjectHandler.class)
public class KsMybatisPlusAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsMybatisPlusAutoConfiguration() {
    }
}
