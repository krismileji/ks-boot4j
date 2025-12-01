package host.springboot.framework.context.mvc.converter;

import host.springboot.framework3.core.util.inner.NumberUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

/**
 * {@code LocalDateTime} 自定义转换器
 *
 * @param formatter 时间格式
 * @param timeZone  时区
 * @author JiYinchuan
 * @since 0.1.0
 */
public record LocalDateTimeConverter(
        DateTimeFormatter formatter,
        TimeZone timeZone
) implements Converter<String, LocalDateTime> {


    @Override
    public LocalDateTime convert(@NonNull String source) {
        try {
            return LocalDateTime.parse(source, formatter);
        } catch (DateTimeParseException e) {
            if (NumberUtils.isParsable(source)) {
                return Instant.ofEpochMilli(Long.parseLong(source)).atZone(timeZone.toZoneId()).toLocalDateTime();
            }
            throw e;
        }
    }
}
