package host.springboot.framework.context.mvc.converter;

import host.springboot.framework3.core.util.inner.NumberUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code Date} 自定义转换器
 *
 * @param pattern 格式化规则
 * @author JiYinchuan
 * @since 0.1.0
 */
public record DateConverter(String pattern) implements Converter<String, Date> {

    @Override
    public Date convert(@NonNull String source) {
        try {
            return new SimpleDateFormat(pattern).parse(source);
        } catch (ParseException e) {
            if (NumberUtils.isParsable(source)) {
                return new Date((Long.parseLong(source)));
            }
            throw new RuntimeException(e);
        }
    }
}
