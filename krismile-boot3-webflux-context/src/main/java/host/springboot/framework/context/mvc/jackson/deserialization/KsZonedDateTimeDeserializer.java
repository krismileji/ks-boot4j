package host.springboot.framework.context.mvc.jackson.deserialization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义 {@code ZonedDateTime} 反序列化器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public final class KsZonedDateTimeDeserializer extends JSR310DateTimeDeserializerBase<ZonedDateTime> {

    /**
     * 构造器
     *
     * @param formatter 格式化规则
     * @since 0.1.0
     */
    public KsZonedDateTimeDeserializer(@NonNull DateTimeFormatter formatter) {
        super(ZonedDateTime.class, formatter);
    }

    /**
     * 构造器
     *
     * @param base     基础类
     * @param leniency 是否宽大处理
     * @since 0.1.0
     */
    public KsZonedDateTimeDeserializer(@NonNull KsZonedDateTimeDeserializer base, @Nullable Boolean leniency) {
        super(base, leniency);
    }

    @Override
    protected JSR310DateTimeDeserializerBase<ZonedDateTime> withDateFormat(DateTimeFormatter dtf) {
        return new KsZonedDateTimeDeserializer(dtf);
    }

    @Override
    protected JSR310DateTimeDeserializerBase<ZonedDateTime> withLeniency(Boolean leniency) {
        return new KsZonedDateTimeDeserializer(this, leniency);
    }

    @Override
    protected JSR310DateTimeDeserializerBase<ZonedDateTime> withShape(JsonFormat.Shape shape) {
        return this;
    }

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.VALUE_STRING)) {
            String string = p.getText().trim();
            if (string.isEmpty()) {
                if (!isLenient()) {
                    return _failForNotLenient(p, ctxt, JsonToken.VALUE_STRING);
                }
                return null;
            }
            try {
                return ZonedDateTime.parse(string, _formatter);
            } catch (DateTimeException e) {
                return _handleDateTimeException(ctxt, e, string);
            }
        }
        JsonToken t = p.nextToken();
        if (p.isExpectedStartArrayToken()) {
            if (t == JsonToken.END_ARRAY) {
                return null;
            }
            // noinspection AlibabaAvoidComplexCondition
            if (ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)
                    && (t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT)) {
                final ZonedDateTime parsed = deserialize(p, ctxt);
                if (p.nextToken() != JsonToken.END_ARRAY) {
                    handleMissingEndArrayForSingle(p, ctxt);
                }
                return parsed;
            }
            ctxt.reportInputMismatch(handledType(), "Unexpected token (%s) within Array", t);
        }
        if (p.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (ZonedDateTime) p.getEmbeddedObject();
        }
        if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            _throwNoNumericTimestampNeedTimeZone(p, ctxt);
        }
        return _handleUnexpectedToken(ctxt, p, "Expected array or string.");
    }
}
