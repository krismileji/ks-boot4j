package host.springboot.framework.context.mvc.converter;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * {@code MonthDay} 自定义转换器
 *
 * @param formatter 时间格式
 * @author JiYinchuan
 * @since 0.1.0
 */
public record MonthDayConverter(DateTimeFormatter formatter) implements Converter<String, MonthDay> {

    @Override
    public MonthDay convert(@NonNull String s) {
        return MonthDay.parse(s, formatter);
    }
}
