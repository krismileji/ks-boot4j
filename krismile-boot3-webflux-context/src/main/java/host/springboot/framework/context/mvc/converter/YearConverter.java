package host.springboot.framework.context.mvc.converter;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * {@code Year} 自定义转换器
 *
 * @param formatter 时间格式
 * @author JiYinchuan
 * @since 0.1.0
 */
public record YearConverter(DateTimeFormatter formatter) implements Converter<String, Year> {

    @Override
    public Year convert(@NonNull String source) {
        return Year.parse(source, formatter);
    }
}
