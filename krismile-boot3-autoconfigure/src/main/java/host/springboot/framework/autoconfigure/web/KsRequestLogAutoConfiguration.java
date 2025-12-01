package host.springboot.framework.autoconfigure.web;

import host.springboot.framework.autoconfigure.web.properties.aspect.KsRequestLogProperties;
import host.springboot.framework.context.aspect.RequestLogAspect;
import host.springboot.framework.context.chain.DefaultRequestLogChainExecute;
import host.springboot.framework.context.chain.RequestInfoChainExecute;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 请求日志自动配置类
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AutoConfiguration(after = AopAutoConfiguration.class)
@EnableConfigurationProperties({KsRequestLogProperties.class})
@ConditionalOnProperty(
        prefix = KsRequestLogProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class KsRequestLogAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsRequestLogAutoConfiguration() {
    }

    /**
     * 请求日志链执行
     *
     * @param ksRequestLogProperties 请求日志配置文件
     * @return RequestInfoChainExecute
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestInfoChainExecute requestInfoChainExecute(KsRequestLogProperties ksRequestLogProperties) {
        return new DefaultRequestLogChainExecute(ksRequestLogProperties.getDebugPrintHeaderNames());
    }

    /**
     * 请求日志切面
     *
     * @param requestInfoChainExecutes 请求日志链执行
     * @return RequestLogAspect
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestLogAspect requestLogAop(List<RequestInfoChainExecute> requestInfoChainExecutes) {
        return new RequestLogAspect(requestInfoChainExecutes);
    }
}
