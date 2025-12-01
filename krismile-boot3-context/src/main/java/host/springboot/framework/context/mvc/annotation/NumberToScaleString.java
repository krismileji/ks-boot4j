package host.springboot.framework.context.mvc.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import host.springboot.framework.context.mvc.jackson.serialization.NumberToScaleStringSerializer;

import java.lang.annotation.*;
import java.math.RoundingMode;

/**
 * 数字类型转为带小数位的字符串注解
 *
 * <p><b>Warning: </b>该注解只能标记在字段类型为 {@link Number} 类型或其实现类上, 标记在其他类型字段上将有可能抛出异常
 * <p><b>Warning: </b>该注解中已经标注了 {@link JsonSerialize @JsonSerialize} 注解,
 * 如果在字段上单独指明了 {@link JsonSerialize @JsonSerialize} 注解则当前注解将不生效
 * <p><b>Note: </b>{@link JacksonAnnotationsInside @JacksonAnnotationsInside} 注解为标记当前注解为 Jackson 的复合注解,
 * 只有标记了该注解才会将该注解中使用到的注解添加到 Jackson 解析器中执行
 *
 * @author JiYinchuan
 * @see NumberToScaleStringSerializer
 * @since 0.1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = NumberToScaleStringSerializer.class)
public @interface NumberToScaleString {

    /**
     * 设置默认保留小数位数, 默认保留两位小数
     *
     * @return 默认保留小数位数
     * @since 0.1.0
     */
    int scale() default 2;

    /**
     * 设置保留小数的模式, 默认采用四舍五入的模式
     *
     * @return 保留小数的模式
     * @since 0.1.0
     */
    RoundingMode roundingMode() default RoundingMode.HALF_UP;

    /**
     * 输出字符串类型
     *
     * @return 输出字符串类型
     * @since 0.1.0
     */
    ToStringType toStringType() default ToStringType.TO_PLAIN_STRING;

    /**
     * 输出字符串类型
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    enum ToStringType {

        /**
         * 调用 {@code toString()} 方法
         */
        DEFAULT,

        /**
         * 调用 {@code toPlainString()} 方法
         */
        TO_PLAIN_STRING,

        /**
         * 调用 {@code toEngineeringString()} 方法
         */
        TO_ENGINEERING_STRING

    }
}