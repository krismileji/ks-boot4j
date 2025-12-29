package host.springboot.framework.context.mvc.jackson.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import host.springboot.framework3.core.enumeration.BaseEnum;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * 自定义 {@code BaseEnum} 序列化器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class BaseEnumSerializer extends StdSerializer<BaseEnum<Object>> {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public BaseEnumSerializer() {
        super(BaseEnum.class, false);
    }

    @Override
    public void serialize(
            BaseEnum<Object> object, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(object.getValue())) {
            jsonGenerator.writeNull();
            return;
        }
        Object value = object.getValue();
        if (value instanceof String) {
            jsonGenerator.writeString((String) value);
            return;
        }
        if (value instanceof Integer) {
            jsonGenerator.writeNumber((Integer) value);
            return;
        }
        if (value instanceof Long) {
            jsonGenerator.writeNumber((Long) value);
            return;
        }
        if (value instanceof Boolean) {
            jsonGenerator.writeBoolean((Boolean) value);
            return;
        }
        if (value instanceof BigDecimal) {
            jsonGenerator.writeNumber((BigDecimal) value);
            return;
        }
        if (value instanceof BigInteger) {
            jsonGenerator.writeNumber((BigInteger) value);
            return;
        }
        if (value instanceof Double) {
            jsonGenerator.writeNumber((Double) value);
            return;
        }
        if (value instanceof Float) {
            jsonGenerator.writeNumber((Float) value);
            return;
        }
        if (value instanceof Short) {
            jsonGenerator.writeNumber((Short) value);
            return;
        }
        jsonGenerator.writeString(object.toString());
    }
}
