package host.springboot.framework3.core.enumeration.date;

import com.fasterxml.jackson.annotation.JsonCreator;
import host.springboot.framework3.core.constant.PatternConstant;
import host.springboot.framework3.core.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 日期时间格式化类型枚举
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AllArgsConstructor
public enum DateTimeFormatTypeEnum implements BaseEnum<String> {

    /**
     * 分割模式
     *
     * <ul>
     *     <li><b>datetime</b> - yyyy-MM-dd HH:mm:ss</li>
     *     <li><b>date</b> - yyyy-MM-dd</li>
     *     <li><b>time</b> - HH:mm:ss</li>
     * </ul>
     */
    DELIMITED("DELIMITED", "分割模式"),

    /**
     * 紧凑模式
     *
     * <ul>
     *     <li><b>datetime</b> - yyyyMMddHHmmss</li>
     *     <li><b>date</b> - yyyyMMdd</li>
     *     <li><b>time</b> - HHmmss</li>
     * </ul>
     */
    COMPACT("COMPACT", "紧凑模式");

    /**
     * 枚举值
     */
    private final String value;

    /**
     * 枚举信息
     */
    private final String reasonPhrase;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    /**
     * 转换为格式化器
     *
     * @param temporal 时间类型
     * @return 格式化器
     * @throws IllegalArgumentException 当参数 {@code temporal} 无效时抛出
     * @since 0.1.0
     */
    public DateTimeFormatter toFormatter(Class<? extends TemporalAccessor> temporal) throws IllegalArgumentException {
        if (temporal == LocalDateTime.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_DATETIME_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_DATETIME_FORMATTER;
            };
        } else if (temporal == LocalDate.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_DATE_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_DATE_FORMATTER;
            };
        } else if (temporal == LocalTime.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_TIME_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_TIME_FORMATTER;
            };
        } else if (temporal == Year.class) {
            return switch (this) {
                case DELIMITED, COMPACT -> PatternConstant.Date.NORM_YEAR_FORMATTER;
            };
        } else if (temporal == YearMonth.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_YEARMONTH_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_YEARMONTH_FORMATTER;
            };
        } else if (temporal == MonthDay.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_MONTHDAY_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_MONTHDAY_FORMATTER;
            };
        } else if (temporal == OffsetDateTime.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_OFFSET_DATETIME_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_OFFSET_DATETIME_FORMATTER;
            };
        } else if (temporal == ZonedDateTime.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_ZONED_DATETIME_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_ZONED_DATETIME_FORMATTER;
            };
        } else if (temporal == OffsetTime.class) {
            return switch (this) {
                case DELIMITED -> PatternConstant.Date.NORM_OFFSET_TIME_FORMATTER;
                case COMPACT -> PatternConstant.Date.COMPACT_OFFSET_TIME_FORMATTER;
            };
        }
        throw new IllegalArgumentException("Invalid temporal type.");
    }

    /**
     * 通过枚举值解析枚举
     *
     * @param value 枚举值
     * @return 枚举
     * @since 0.1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static @Nullable DateTimeFormatTypeEnum parse(@Nullable String value) {
        return BaseEnum.parse(value, DateTimeFormatTypeEnum.class);
    }
}
