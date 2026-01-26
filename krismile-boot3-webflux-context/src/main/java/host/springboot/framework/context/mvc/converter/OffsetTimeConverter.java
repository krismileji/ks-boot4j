package host.springboot.framework.context.mvc.converter;

import host.springboot.framework3.core.util.inner.NumberUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

/**
 * {@code OffsetDateTime} 自定义转换器
 *
 * @param formatter 时间格式
 * @param timeZone  时区
 * @author JiYinchuan
 * @since 0.1.0
 */
public record OffsetTimeConverter(
        DateTimeFormatter formatter,
        TimeZone timeZone
) implements Converter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(@NonNull String source) {
        try {
            return OffsetDateTime.parse(source, formatter);
        } catch (DateTimeParseException e) {
            if (NumberUtils.isParsable(source)) {
                return Instant.ofEpochMilli(Long.parseLong(source)).atZone(ZoneId.systemDefault()).toOffsetDateTime();
            }
            throw e;
        }
    }
}
