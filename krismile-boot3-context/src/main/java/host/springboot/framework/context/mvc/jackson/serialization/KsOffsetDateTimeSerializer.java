package host.springboot.framework.context.mvc.jackson.serialization;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.jspecify.annotations.NonNull;

import java.time.format.DateTimeFormatter;

/**
 * 自定义 {@code OffsetDateTime} 序列化器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class KsOffsetDateTimeSerializer extends OffsetDateTimeSerializer {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsOffsetDateTimeSerializer() {
    }

    /**
     * 构造器
     *
     * @param formatter 格式化规则
     * @since 0.1.0
     */
    public KsOffsetDateTimeSerializer(@NonNull DateTimeFormatter formatter) {
        super(OffsetDateTimeSerializer.INSTANCE, null, null, formatter);
    }
}
