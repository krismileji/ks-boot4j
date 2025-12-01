package host.springboot.framework.context.mvc.jackson.serialization;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
import org.jspecify.annotations.NonNull;

import java.time.format.DateTimeFormatter;

/**
 * 自定义 {@code OffsetTime} 序列化器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class KsOffsetTimeSerializer extends OffsetTimeSerializer {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public KsOffsetTimeSerializer() {
    }

    /**
     * 构造器
     *
     * @param formatter 格式化规则
     * @since 0.1.0
     */
    public KsOffsetTimeSerializer(@NonNull DateTimeFormatter formatter) {
        super(OffsetTimeSerializer.INSTANCE, null, formatter);
    }
}
