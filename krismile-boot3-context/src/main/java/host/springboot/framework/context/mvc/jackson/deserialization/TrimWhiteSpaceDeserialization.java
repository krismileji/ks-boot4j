package host.springboot.framework.context.mvc.jackson.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import host.springboot.framework.context.mvc.annotation.TrimWhiteSpace;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.Objects;

/**
 * 清除空格反序列化器
 *
 * <p>实现类说明: {@link StdSerializer} 为 jackson 推荐自定义序列化器基类,
 * {@link ContextualSerializer} 为 jackson 创建序列化程序的上下文实例以用于处理受支持类型的属性</p>
 * <p><b>Warning: </b>将 {@link TrimWhiteSpace @TrimWhiteSpace} 注解标记到请求类字段上, 只有标记了注解才会执行
 *
 * @author JiYinchuan
 * @see TrimWhiteSpace @TrimWhiteSpace
 * @since 0.1.0
 */
public final class TrimWhiteSpaceDeserialization extends StdDeserializer<String> implements ContextualDeserializer {

    /**
     * 默认字符串反序列化器
     */
    private static final JsonDeserializer<String> DEFAULT_STRING_DESERIALIZER = StringDeserializer.instance;

    /**
     * 注解
     */
    private TrimWhiteSpace annotation;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public TrimWhiteSpaceDeserialization() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, @NonNull DeserializationContext ctxt) throws IOException {
        String value = DEFAULT_STRING_DESERIALIZER.deserialize(p, ctxt);
        if (Objects.nonNull(annotation) && !value.isEmpty()) {
            return value.trim();
        }
        return value;
    }

    @Override
    public JsonDeserializer<?> createContextual(
            DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType javaType = property.getType();
        if (!javaType.getRawClass().isAssignableFrom(String.class)) {
            return ctxt.findRootValueDeserializer(javaType);
        }
        this.annotation = property.getAnnotation(TrimWhiteSpace.class);
        return this;
    }
}
