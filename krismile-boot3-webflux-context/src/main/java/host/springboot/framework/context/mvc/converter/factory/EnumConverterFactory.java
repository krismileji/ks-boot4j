package host.springboot.framework.context.mvc.converter.factory;

import host.springboot.framework.context.mvc.annotation.EnumConvertValue;
import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.exception.EnumIllegalArgumentException;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举转换工厂类
 *
 * <p>该类为处理请求参数中的枚举对应值和枚举对象进行相互转换而存在</p>
 * <hr>
 * <p>当请求参数为枚举时, 且枚举中存在标注了 {@link EnumConvertValue @EnumConvertValue} 注解的静态方法或者类实现了 {@link BaseEnum} 的枚举
 * (两者同时存在时标注了 {@code @EnumConvertValue} 注解的静态方法优先级为最高), 可以自动将该对象属性名称与请求参数进行匹配,</p>
 * <p>Warning: 底层通过 {@link ConverterFactory} 进行实现时必须指定请求参数数据类型与转换的数据类型,
 * 因此当请求参数为枚举时, 请求参数的类型必须为 {@link String},
 * 转换的枚举必须在方法上标注 {@code @EnumConvertValue} 注解或者实现 {@link BaseEnum} 接口</p>
 * <p>{@code @EnumConvertValue} 注解使用方法请参见该注解类文档</p>
 * <p>推荐自定义枚举类实现 {@link BaseEnum} 接口而不是使用 {@code @EnumConvertValue} 注解</p>
 * <hr>
 * <p>当请求参数为请求体时需要在枚举类中加上下列代码实现, 代码示例:</p>
 * <pre>{@code
 *     &#064;JsonCreator(mode = JsonCreator.Mode.DELEGATING)
 *     public static [枚举类] parse(String value) {
 *         return BaseEnum.parse(value, [枚举类].class);
 *     }
 * }</pre>
 * <hr>
 *
 * @author JiYinchuan
 * @see EnumConvertValue @EnumConvertValue
 * @see BaseEnum
 * @since 0.1.0
 */
public final class EnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    /**
     * 枚举转换缓存
     */
    private static final Map<Class<? extends Enum<?>>, EnumConverterHolder> CONVERTER_MAP = new ConcurrentHashMap<>();

    /**
     * 枚举转换工厂类
     *
     * @since 0.1.0
     */
    public EnumConverterFactory() {
    }

    @Override
    public <E extends Enum<?>> @NonNull Converter<String, E> getConverter(@NonNull Class<E> targetType) {
        EnumConverterHolder holder = CONVERTER_MAP.computeIfAbsent(targetType, EnumConverterHolder::create);
        return (Converter<String, E>) (holder.annotationConverter != null ? holder.annotationConverter : holder.baseEnumConverter);
    }

    record EnumConverterHolder(
            StringToAnnotationEnumConverter<?> annotationConverter,
            StringToBaseEnumConverter<?, ?> baseEnumConverter
    ) {

        @SuppressWarnings("unchecked")
        private static EnumConverterHolder create(Class<?> targetType) {
            final List<Method> enumConvertValueMethods = new ArrayList<>();
            final Method[] allDeclaredMethods = targetType.getDeclaredMethods();
            for (Method declaredMethod : allDeclaredMethods) {
                if (declaredMethod.getAnnotation(EnumConvertValue.class) != null) {
                    enumConvertValueMethods.add(declaredMethod);
                }
            }
            if (enumConvertValueMethods.isEmpty()) {
                if (BaseEnum.class.isAssignableFrom(targetType)) {
                    return instance((Class<BaseEnum<Object>>) targetType);
                } else {
                    return new EnumConverterHolder(null, null);
                }
            }
            if (enumConvertValueMethods.size() != 1) {
                throw new EnumIllegalArgumentException("@EnumConvertValue can only be marked on one method");
            }
            Method enumConvertValueMethod = enumConvertValueMethods.getFirst();
            boolean isStatic = Modifier.isStatic(enumConvertValueMethod.getModifiers());
            if (!isStatic) {
                throw new EnumIllegalArgumentException("@EnumConvertValue can only be marked on static method");
            }
            return new EnumConverterHolder(new StringToAnnotationEnumConverter<>(enumConvertValueMethod), null);
        }

        private static <T, E extends BaseEnum<T>> EnumConverterHolder instance(Class<E> targetType) {
            return new EnumConverterHolder(null, new StringToBaseEnumConverter<>(targetType));
        }
    }

    record StringToBaseEnumConverter<T, E extends BaseEnum<T>>(Class<E> enumType) implements Converter<T, E> {

        @Override
        public E convert(@NonNull T source) {
            return BaseEnum.parse(source, enumType);
        }
    }

    record StringToAnnotationEnumConverter<T extends Enum<?>>(Method method) implements Converter<String, T> {

        StringToAnnotationEnumConverter(Method method) {
            this.method = method;
            this.method.setAccessible(true);
        }

        @Override
        @SneakyThrows
        @SuppressWarnings("unchecked")
        public T convert(@NonNull String source) {
            if (source.isEmpty()) {
                return null;
            }
            return (T) method.invoke(null, source);
        }
    }
}
