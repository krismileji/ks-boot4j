package host.springboot.framework.mybatisplus;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import host.springboot.framework3.core.util.inner.StringUtils;
import jakarta.servlet.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * Druid广告拦截配置
 *
 * <p>该过滤器主要用于去掉 [druid] 网页中的底部广告内容
 *
 * @author JiYinchuan
 * @see DruidAdvertFilter
 * @since 0.1.0
 */
@ConditionalOnClass(DruidDataSourceAutoConfigure.class)
@AutoConfiguration(after = DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class DruidAdvertAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public DruidAdvertAutoConfiguration() {
    }

    /**
     * 注册Druid广告过滤器
     *
     * @param properties DruidStatProperties
     * @return Druid广告过滤器
     * @since 0.1.0
     */
    @Bean
    @ConditionalOnBean(DruidStatProperties.class)
    public FilterRegistrationBean<DruidAdvertFilter> removeDruidAdvert(DruidStatProperties properties) {
        // 获取Web监控页面参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js配置路径
        String pattern = StringUtils.isNotBlank(config.getUrlPattern()) ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

        FilterRegistrationBean<DruidAdvertFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DruidAdvertFilter());
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }

    /**
     * Druid广告过滤器
     *
     * <p>该过滤器主要用于去掉 [druid] 网页中的底部广告内容
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    public static class DruidAdvertFilter implements Filter {

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        public DruidAdvertFilter() {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            chain.doFilter(request, response);
            // 重置缓冲区
            response.resetBuffer();
            String filePath = "support/http/resources/js/common.js";
            // 获取common.js
            String text = Utils.readFromResource(filePath);
            // 正则替换banner, 除去底部广告信息
            text = text.replaceAll("<a.*?banner\"></a><br/>", "");
            text = text.replaceAll("powered.*?shrek.wang</a>", "");
            response.getWriter().write(text);
        }
    }
}
