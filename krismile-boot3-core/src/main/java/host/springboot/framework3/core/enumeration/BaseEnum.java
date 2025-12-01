package host.springboot.framework3.core.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import host.springboot.framework3.core.exception.EnumIllegalArgumentException;
import host.springboot.framework3.core.util.Assert;
import host.springboot.framework3.core.util.inner.ArrayUtils;
import host.springboot.framework3.core.util.inner.NumberUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 全局枚举父接口
 *
 * <p>该类为全局枚举父接口, 为避免部分功能失效, 项目中所有的自定义枚举原则上都必须实现该接口
 * <p><b>Tip</b> - 该类提供了 {@link #additionalValue()} 和 {@link #additionalMapValue()} 用于提供附加值,
 * 开发者可选择性的重写这两个方法或任意一个方法, 便于获取每个枚举对象的附加参数值
 * <p>注意: 如果未重写方法直接调动附加值方法请注意判断 {@code null} 值, 避免出现空指针异常
 *
 * @param <T> 枚举值类型
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface BaseEnum<T> {

    /**
     * 默认解析异常
     */
    BiFunction<String, Object, EnumIllegalArgumentException> DEFAULT_PARSE_EXCEPTION = (enumName, parseValue) ->
            new EnumIllegalArgumentException("No matching [" + enumName + "] for [" + parseValue + "]");

    /**
     * 获取枚举值
     *
     * @return 枚举值
     * @since 0.1.0
     */
    @JsonValue
    T getValue();

    /**
     * 获取枚举信息
     *
     * @return 枚举信息
     * @since 0.1.0
     */
    String getReasonPhrase();


    /**
     * 附加值
     *
     * @return 附加值
     * @since 0.1.0
     */
    default String additionalValue() {
        return null;
    }

    /**
     * 附加值
     *
     * @return 附加值
     * @since 0.1.0
     */
    default Map<String, String> additionalMapValue() {
        return Collections.emptyMap();
    }

    /**
     * 动态解析枚举参数值
     *
     * <p>解析失败时将抛出异常
     *
     * @param value     参数值
     * @param enumClass 需要解析的枚举类
     * @param <T>       枚举值类型
     * @param <E>       继承 {@link BaseEnum} 的枚举类型
     * @return 解析后的枚举对象
     * @throws EnumIllegalArgumentException 解析异常
     * @see #parse(Object, Class, boolean, boolean)
     * @since 0.1.0
     */
    static <T, E extends BaseEnum<T>> E parse(
            @Nullable T value, @NonNull Class<E> enumClass)
            throws EnumIllegalArgumentException {
        return parse(value, enumClass, true, false);
    }

    /**
     * 解析枚举参数值
     *
     * <p>当 {@code value} 为 {@literal null} 时抛出 {@link #DEFAULT_PARSE_EXCEPTION} 异常</p>
     *
     * @param value     参数值
     * @param enumClass 需要解析的枚举类
     * @param <T>       枚举值类型
     * @param <E>       继承 {@link BaseEnum} 的枚举类型
     * @return 解析后的枚举对象
     * @throws EnumIllegalArgumentException 解析异常
     * @see #parse(Object, Class, boolean, boolean)
     * @since 0.1.0
     */
    static <T, E extends BaseEnum<T>> E parseThrowIfNotFound(
            @Nullable T value,
            @NonNull Class<E> enumClass)
            throws EnumIllegalArgumentException {
        return parse(value, enumClass, true, true);
    }

    /**
     * 解析枚举参数值
     *
     * @param value           参数值
     * @param enumClass       需要解析的枚举类
     * @param isDynamicParse  是否动态解析, 为动态解析时可动态解析字符串与数字类型的映射
     * @param isNotFoundThrow 是否在没有找到匹配的值时抛出异常
     * @param <T>             枚举值类型
     * @param <E>             继承 {@link BaseEnum} 的枚举类型
     * @return 解析后的枚举对象
     * @throws EnumIllegalArgumentException 解析异常
     * @since 0.1.0
     */
    @SuppressWarnings("unchecked")
    static <T, E extends BaseEnum<T>> E parse(
            @Nullable T value, @NonNull Class<E> enumClass,
            boolean isDynamicParse, boolean isNotFoundThrow)
            throws EnumIllegalArgumentException {
        Assert.notNull(enumClass, "EnumClass must not be null");
        if (!enumClass.isEnum()) {
            throw new EnumIllegalArgumentException(enumClass.getName() + " is not an Enum");
        }
        E enumValue = null;
        E[] enumConstants = enumClass.getEnumConstants();
        if (Objects.nonNull(value) && ArrayUtils.isNotEmpty(enumConstants)) {
            // 参数值为字符串时需要现去除前后空格
            if (value instanceof String) {
                value = (T) ((String) value).trim();
            }
            for (E enumConstant : enumConstants) {
                T enumConstantValue = enumConstant.getValue();
                if (Objects.isNull(enumConstantValue)) {
                    continue;
                }
                if (Objects.equals(enumConstantValue, value)) {
                    enumValue = enumConstant;
                    break;
                }
                if (isDynamicParse) {
                    // 以下是为了兼容参数值为 String 时且枚举参数为 Number 类型时的兼容情况
                    if (value instanceof String tempValue && enumConstantValue instanceof Number) {
                        if (NumberUtils.isParsable(tempValue)) {
                            if (String.valueOf(enumConstantValue).equals(tempValue)) {
                                enumValue = enumConstant;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (isNotFoundThrow && Objects.isNull(enumValue)) {
            throw DEFAULT_PARSE_EXCEPTION.apply(enumClass.getName(), value);
        }
        return enumValue;
    }
}
