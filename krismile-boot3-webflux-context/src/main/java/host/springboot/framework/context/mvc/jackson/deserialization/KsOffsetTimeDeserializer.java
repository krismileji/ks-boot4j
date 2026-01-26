package host.springboot.framework.context.mvc.jackson.deserialization;

import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import org.jspecify.annotations.NonNull;

import java.time.format.DateTimeFormatter;

/**
 * 自定义 {@code OffsetTime} 反序列化器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class KsOffsetTimeDeserializer extends OffsetTimeDeserializer {

    /**
     * 构造器
     *
     * @param formatter 格式化规则
     * @since 0.1.0
     */
    public KsOffsetTimeDeserializer(@NonNull DateTimeFormatter formatter) {
        super(formatter);
    }
}
