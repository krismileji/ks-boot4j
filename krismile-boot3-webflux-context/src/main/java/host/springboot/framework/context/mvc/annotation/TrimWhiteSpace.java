package host.springboot.framework.context.mvc.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import host.springboot.framework.context.mvc.jackson.deserialization.TrimWhiteSpaceDeserialization;

import java.lang.annotation.*;

/**
 * 清空前后空格注解
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonDeserialize(using = TrimWhiteSpaceDeserialization.class)
public @interface TrimWhiteSpace {
}
