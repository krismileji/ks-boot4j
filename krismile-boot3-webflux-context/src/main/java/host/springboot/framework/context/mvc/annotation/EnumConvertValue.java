package host.springboot.framework.context.mvc.annotation;

import host.springboot.framework.context.mvc.converter.factory.EnumConverterFactory;

import java.lang.annotation.*;

/**
 * 枚举值转换注解
 *
 * <p>在枚举类中静态方法上标注此注解时, 会通过 {@link EnumConverterFactory} 进行 {@code Convert} 转换, 主要用于请求参数反序列化等场景</p>
 * <p>注意: 此注解必须在枚举类方法中标注, 标注的方法必须为静态方法, 形参限制为一个 {@link String} 类型, 返回值为当前枚举对象</p>
 * <hr>
 * <p>静态方法代码示例(方法名可自定义):
 * <pre>{@code
 *     &#064;EnumConvertValue
 *     public static [枚举类] convertValue() {
 *         return [枚举值];
 *     }
 * }</pre>
 *
 * @author JiYinchuan
 * @see EnumConverterFactory
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface EnumConvertValue {
}
