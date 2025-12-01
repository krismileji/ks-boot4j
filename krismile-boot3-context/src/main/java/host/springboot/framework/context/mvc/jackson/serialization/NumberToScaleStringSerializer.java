package host.springboot.framework.context.mvc.jackson.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import host.springboot.framework.context.mvc.annotation.NumberToScaleString;
import host.springboot.framework3.core.util.inner.NumberUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * 自定义数字转带小数位字符串序列化器
 *
 * <p>实现类说明: {@link StdSerializer} 为 jackson 推荐自定义序列化器基类,
 * {@link ContextualSerializer} 为 jackson 创建序列化程序的上下文实例以用于处理受支持类型的属性
 * <p><b>Warning: </b>因该自定义序列化器为 {@link NumberToScaleString @NumberToScaleString} 注解标记到请求类字段上,
 * 即只有标记了注解才会执行该类, 所以在获取注解失败时将抛出未知异常信息 {@value UNKNOWN_ERROR_MESSAGE}
 *
 * @author JiYinchuan
 * @see NumberToScaleString @NumberToScaleString
 * @since 0.1.0
 */
public class NumberToScaleStringSerializer extends StdSerializer<Object> implements ContextualSerializer {

    /**
     * 未知异常错误信息
     */
    private static final String UNKNOWN_ERROR_MESSAGE = "自定义数字转带小数位字符串未知异常";

    /**
     * 注解
     */
    private NumberToScaleString annotation;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public NumberToScaleStringSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (object instanceof Number number) {
            if (object instanceof BigDecimal) {
                jsonGenerator.writeString(toScaleString((BigDecimal) number));
            } else if (object instanceof BigInteger) {
                jsonGenerator.writeString(toScaleString(new BigDecimal((BigInteger) number)));
            } else if (object instanceof Long) {
                jsonGenerator.writeString(toScaleString(BigDecimal.valueOf(number.longValue())));
            } else if (object instanceof Double) {
                jsonGenerator.writeString(toScaleString(BigDecimal.valueOf(number.doubleValue())));
            } else if (object instanceof Float) {
                jsonGenerator.writeString(toScaleString(BigDecimal.valueOf(number.floatValue())));
            } else if (object instanceof Integer || object instanceof Byte || number instanceof Short) {
                jsonGenerator.writeString(toScaleString(BigDecimal.valueOf(number.intValue())));
            } else {
                jsonGenerator.writeString(toScaleString(new BigDecimal(number.toString())));
            }
        } else if (object instanceof String string) {
            if (!NumberUtils.isParsable(string)) {
                throw new RuntimeException(String.format("[%s] 不是一个有效的数字", object));
            }
            jsonGenerator.writeString(toScaleString(new BigDecimal(string)));
        } else {
            throw new RuntimeException(String.format("[%s] 不可标记在非数字类型上", "@NumberToStringScale"));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        this.annotation = property.getAnnotation(NumberToScaleString.class);
        return this;
    }

    /**
     * 格式化输出字符串
     *
     * @param originalValue 原始值
     * @return 格式化后的字符串
     * @since 0.1.0
     */
    private String toScaleString(BigDecimal originalValue) {
        if (Objects.nonNull(annotation)) {
            BigDecimal bigDecimal = originalValue.setScale(annotation.scale(), annotation.roundingMode());
            switch (annotation.toStringType()) {
                case DEFAULT:
                    return bigDecimal.toString();
                case TO_PLAIN_STRING:
                    return bigDecimal.toPlainString();
                case TO_ENGINEERING_STRING:
                    return bigDecimal.toEngineeringString();
                default:
            }
        }
        throw new RuntimeException(UNKNOWN_ERROR_MESSAGE);
    }
}
