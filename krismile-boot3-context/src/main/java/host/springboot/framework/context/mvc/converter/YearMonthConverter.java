package host.springboot.framework.context.mvc.converter;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * {@code YearMonth} 自定义转换器
 *
 * @param formatter 时间格式
 * @author JiYinchuan
 * @since 0.1.0
 */
public record YearMonthConverter(DateTimeFormatter formatter) implements Converter<String, YearMonth> {

    @Override
    public YearMonth convert(@NonNull String source) {
        return YearMonth.parse(source, formatter);
    }
}
