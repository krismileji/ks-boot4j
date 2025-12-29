package host.springboot.framework.autoconfigure.web;

import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import host.springboot.framework.context.mvc.converter.*;
import host.springboot.framework.context.mvc.converter.factory.EnumConverterFactory;
import host.springboot.framework3.core.constant.KrismileConstant;
import host.springboot.framework3.core.constant.PatternConstant;
import host.springboot.framework3.core.enumeration.date.DateTimeFormatTypeEnum;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * 上下文自动配置类
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@AutoConfiguration(after = WebFluxAutoConfiguration.class)
@EnableConfigurationProperties(KsWebProperties.class)
@ConditionalOnProperty(
        prefix = KsWebProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class KsWebAutoConfiguration {

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    public KsWebAutoConfiguration() {
    }

    /**
     * WebFlux配置（响应式版本）
     *
     * @param ksWebProperties Web 配置文件
     * @return WebFluxConfigurer
     * @since 0.2.0
     */
    @Bean(KrismileConstant.KRISMILE_ABBREVIATION_LOWERCASE + "WebFluxConfigurer")
    public WebFluxConfigurer webFluxConfigurer(KsWebProperties ksWebProperties) {
        TimeZone timeZone = ksWebProperties.getTimeZone();
        DateTimeFormatTypeEnum formatType = ksWebProperties.getTimeFormatType();
        return new WebFluxConfigurer() {
            @Override
            public void addFormatters(@NonNull FormatterRegistry registry) {
                registry.addConverterFactory(new EnumConverterFactory());
                registry.addConverter(new DateConverter(switch (formatType) {
                    case DELIMITED -> PatternConstant.Date.NORM_DATETIME_PATTERN;
                    case COMPACT -> PatternConstant.Date.COMPACT_DATETIME_PATTERN;
                }));
                registry.addConverter(new LocalDateTimeConverter(formatType.toFormatter(LocalDateTime.class), timeZone));
                registry.addConverter(new LocalDateConverter(formatType.toFormatter(LocalDateTime.class), timeZone));
                registry.addConverter(new LocalTimeConverter(formatType.toFormatter(LocalDateTime.class), timeZone));
                registry.addConverter(new YearConverter(formatType.toFormatter(LocalDateTime.class)));
                registry.addConverter(new YearMonthConverter(formatType.toFormatter(LocalDateTime.class)));
                registry.addConverter(new MonthDayConverter(formatType.toFormatter(LocalDateTime.class)));
                registry.addConverter(new ZonedDateTimeConverter(formatType.toFormatter(LocalDateTime.class), timeZone));
                registry.addConverter(new OffsetDateTimeConverter(formatType.toFormatter(LocalDateTime.class), timeZone));
                registry.addConverter(new OffsetTimeConverter(formatType.toFormatter(LocalDateTime.class), timeZone));
            }
        };
    }
}
