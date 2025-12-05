package host.springboot.framework.autoconfigure.web.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import host.springboot.framework.autoconfigure.web.KsWebAutoConfiguration;
import host.springboot.framework.autoconfigure.web.properties.KsWebProperties;
import host.springboot.framework.context.mvc.jackson.deserialization.KsOffsetDateTimeDeserializer;
import host.springboot.framework.context.mvc.jackson.deserialization.KsOffsetTimeDeserializer;
import host.springboot.framework.context.mvc.jackson.deserialization.KsZonedDateTimeDeserializer;
import host.springboot.framework.context.mvc.jackson.serialization.BaseEnumSerializer;
import host.springboot.framework.context.mvc.jackson.serialization.KsOffsetDateTimeSerializer;
import host.springboot.framework.context.mvc.jackson.serialization.KsOffsetTimeSerializer;
import host.springboot.framework3.core.constant.PatternConstant;
import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.enumeration.date.DateTimeFormatTypeEnum;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ClassUtils;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Jackson自动配置
 *
 * <p>该类自动配置了 Jackson 的常用功能</p>
 * 已默认配置序列化/反序列化规则如下:
 * <ul>
 *     <li>{@link BigInteger} auto mapping {@link String}</li>
 *     <li>{@link Long} auto mapping {@link String}</li>
 *     <li>{@link Enum} auto mapping {@link String}</li>
 *     <li>{@link BaseEnum} auto mapping {@link BaseEnum#getValue()}</li>
 *     <li>{@link Date} auto mapping if not configured {@code yyyy-MM-dd HH:mm:ss}</li>
 *     <li>{@link LocalDateTime} auto mapping if not configured {@code yyyy-MM-dd HH:mm:ss}</li>
 *     <li>{@link LocalDate} auto mapping {@code yyyy-MM-dd}</li>
 *     <li>{@link LocalTime} auto mapping {@code HH:mm:ss}</li>
 *     <li>{@link Year} auto mapping {@code yyyy}</li>
 *     <li>{@link YearMonth} auto mapping {@code yyyy-MM}</li>
 *     <li>{@link MonthDay} auto mapping {@code MM-dd}</li>
 *     <li>{@link ZonedDateTime} auto mapping {@code yyyy-MM-dd HH:mm:ss+HH:MM:ss[ZoneRegionId()]}</li>
 *     <li>{@link OffsetDateTime} auto mapping {@code yyyy-MM-dd HH:mm:ss+HH:MM:ss}</li>
 *     <li>{@link OffsetTime} auto mapping {@code HH:mm:ss+HH:MM:ss}</li>
 * </ul>
 * <p>该类采用 {@link Jackson2ObjectMapperBuilderCustomizer} 的原因是默认 Jackson 已存在一些默认配置内容,
 * 通过此种方式可以实现在不修改默认配置的情况下新增我们自定义的配置</p>
 *
 * @param jacksonProperties Jackson配置文件
 * @param ksWebProperties   KsWeb配置文件
 * @author JiYinchuan
 * @see KsWebAutoConfiguration
 * @since 0.1.0
 */
public record JacksonObjectMapperBuilder(
        @Nullable JacksonProperties jacksonProperties,
        @NonNull KsWebProperties ksWebProperties
) implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(Enum.class, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(BaseEnum.class, new BaseEnumSerializer());

        DateTimeFormatTypeEnum formatType = this.ksWebProperties.getTimeFormatType();

        jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat(switch (formatType) {
            case DELIMITED -> PatternConstant.Date.NORM_DATETIME_PATTERN;
            case COMPACT -> PatternConstant.Date.COMPACT_DATETIME_PATTERN;
        }));
        jacksonObjectMapperBuilder.serializerByType(LocalTime.class,
                new LocalTimeSerializer(formatType.toFormatter(LocalTime.class)));
        jacksonObjectMapperBuilder.serializerByType(LocalDate.class,
                new LocalDateSerializer(formatType.toFormatter(LocalDate.class)));
        jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class,
                new LocalDateTimeSerializer(formatType.toFormatter(LocalDateTime.class)));
        jacksonObjectMapperBuilder.serializerByType(Year.class,
                new YearSerializer(formatType.toFormatter(Year.class)));
        jacksonObjectMapperBuilder.serializerByType(YearMonth.class,
                new YearMonthSerializer(formatType.toFormatter(YearMonth.class)));
        jacksonObjectMapperBuilder.serializerByType(MonthDay.class,
                new MonthDaySerializer(formatType.toFormatter(MonthDay.class)));
        jacksonObjectMapperBuilder.serializerByType(ZonedDateTime.class,
                new ZonedDateTimeSerializer(formatType.toFormatter(ZonedDateTime.class)));
        jacksonObjectMapperBuilder.serializerByType(OffsetDateTime.class,
                new KsOffsetDateTimeSerializer(formatType.toFormatter(OffsetDateTime.class)));
        jacksonObjectMapperBuilder.serializerByType(OffsetTime.class,
                new KsOffsetTimeSerializer(formatType.toFormatter(OffsetTime.class)));

        jacksonObjectMapperBuilder.deserializerByType(LocalTime.class,
                new LocalTimeDeserializer(formatType.toFormatter(LocalTime.class)));
        jacksonObjectMapperBuilder.deserializerByType(LocalDate.class,
                new LocalDateDeserializer(formatType.toFormatter(LocalDate.class)));
        jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class,
                new LocalDateTimeDeserializer(formatType.toFormatter(LocalDateTime.class)));
        jacksonObjectMapperBuilder.deserializerByType(Year.class,
                new YearDeserializer(formatType.toFormatter(Year.class)));
        jacksonObjectMapperBuilder.deserializerByType(YearMonth.class,
                new YearMonthDeserializer(formatType.toFormatter(YearMonth.class)));
        jacksonObjectMapperBuilder.deserializerByType(MonthDay.class,
                new MonthDayDeserializer(formatType.toFormatter(MonthDay.class)));
        jacksonObjectMapperBuilder.deserializerByType(ZonedDateTime.class,
                new KsZonedDateTimeDeserializer(formatType.toFormatter(ZonedDateTime.class)));
        jacksonObjectMapperBuilder.deserializerByType(OffsetDateTime.class,
                new KsOffsetDateTimeDeserializer(formatType.toFormatter(OffsetDateTime.class)));
        jacksonObjectMapperBuilder.deserializerByType(OffsetTime.class,
                new KsOffsetTimeDeserializer(formatType.toFormatter(OffsetTime.class)));

        configureDateFormat(jacksonObjectMapperBuilder);
    }

    /**
     * 配置时间格式
     *
     * @param jacksonObjectMapperBuilder ObjectMapper实例构建器
     * @since 0.1.0
     */
    private void configureDateFormat(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        String defaultDatePattern = PatternConstant.Date.NORM_DATETIME_PATTERN;
        if (Objects.isNull(this.jacksonProperties)) {
            return;
        }
        TimeZone timeZone = this.jacksonProperties.getTimeZone();
        if (Objects.isNull(timeZone)) {
            timeZone = ksWebProperties.getTimeZone();
        }
        String dateFormat = this.jacksonProperties.getDateFormat();
        if (Objects.isNull(dateFormat)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultDatePattern);
            simpleDateFormat.setTimeZone(timeZone);
            jacksonObjectMapperBuilder.dateFormat(simpleDateFormat);
        } else {
            try {
                Class<?> dateFormatClass = ClassUtils.forName(dateFormat, null);
                jacksonObjectMapperBuilder.dateFormat((DateFormat) BeanUtils.instantiateClass(dateFormatClass));
            } catch (ClassNotFoundException ex) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                simpleDateFormat.setTimeZone(timeZone);
                jacksonObjectMapperBuilder.dateFormat(simpleDateFormat);
                jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class,
                        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateFormat)));
                jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class,
                        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateFormat)));
            }
        }
        jacksonObjectMapperBuilder.timeZone(timeZone);
    }
}
