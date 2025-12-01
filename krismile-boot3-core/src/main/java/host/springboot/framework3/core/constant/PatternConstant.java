package host.springboot.framework3.core.constant;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

/**
 * 规则常量
 *
 * <p>正则表达式
 * <p><a href="https://c.runoob.com/front-end/854/">点击查看常用正则表达式</a>
 * <ul>
 *     <li><b>{@link Regex#PHONE}</b> - 电话号码正则表达式</li>
 *     <li><b>{@link Regex#EMAIL}</b> - 邮箱正则表达式</li>
 *     <li><b>{@link Regex#ID_NUMBER}</b> - 身份证号正则表达式</li>
 *     <li><b>{@link Regex#ACCOUNT}</b> - 账号正则表达式</li>
 *     <li><b>{@link Regex#PASSWORD}</b> - 密码正则表达式</li>
 *     <li><b>{@link Regex#STRONG_PASSWORD}</b> - 强密码正则表达式</li>
 *     <li><b>{@link Regex#STRONG_PASSWORD_WITH_SPECIAL}</b> - 强密码正则表达式</li>
 * </ul>
 * <hr/>
 * <p>时间格式
 * <p>Pattern:
 * <ul>
 *     <li><b>{@link Date#NORM_DATETIME_PATTERN}</b> - yyyy-MM-dd HH:mm:ss</li>
 *     <li><b>{@link Date#NORM_DATE_PATTERN}</b> - yyyy-MM-dd</li>
 *     <li><b>{@link Date#NORM_TIME_PATTERN}</b> - HH:mm:ss</li>
 *     <li><b>{@link Date#NORM_YEAR_PATTERN}</b> - yyyy</li>
 *     <li><b>{@link Date#NORM_YEARMONTH_PATTERN}</b> - yyyy-MM</li>
 *     <li><b>{@link Date#NORM_MONTHDAY_PATTERN}</b> - MM-dd</li>
 * </ul>
 * <p>Formatter:
 * <ul>
 *     <li><b>{@link Date#NORM_DATETIME_FORMATTER}</b> - yyyy-MM-dd HH:mm:ss</li>
 *     <li><b>{@link Date#NORM_DATE_FORMATTER}</b> - yyyy-MM-dd</li>
 *     <li><b>{@link Date#NORM_TIME_FORMATTER}</b> - HH:mm:ss</li>
 *     <li><b>{@link Date#NORM_YEAR_FORMATTER}</b> - yyyy</li>
 *     <li><b>{@link Date#NORM_YEARMONTH_FORMATTER}</b> - yyyy-MM</li>
 *     <li><b>{@link Date#NORM_MONTHDAY_FORMATTER}</b> - MM-dd</li>
 *     <li><b>{@link Date#NORM_OFFSET_DATETIME_FORMATTER}</b> - yyyy-MM-dd HH:mm:ss+HH:MM:ss</li>
 *     <li><b>{@link Date#NORM_ZONED_DATETIME_FORMATTER}</b> - yyyy-MM-dd HH:mm:ss+HH:MM:ss[ZoneRegionId()]</li>
 *     <li><b>{@link Date#NORM_OFFSET_TIME_FORMATTER}</b> - HH:mm:ss+HH:MM:ss</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public abstract class PatternConstant {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    protected PatternConstant() {
    }

    /**
     * 正则表达式
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    public static abstract class Regex {

        /**
         * 电话号码正则表达式
         *
         * <p>支持手机号码/3-4位区号/7-8位直播号码/1－4位分机号
         */
        public static final String PHONE = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-" +
                "(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";

        /**
         * 邮箱正则表达式
         */
        public static final String EMAIL = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

        /**
         * 身份证号正则表达式
         */
        public static final String ID_NUMBER = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}[\\dXx]$)";

        /**
         * 账号正则表达式
         *
         * <p>字母开头, 允许 [5-16] 字节, 允许字母数字下划线
         */
        public static final String ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

        /**
         * 密码正则表达式
         *
         * <p>以字母开头, 长度在 [6~16] 之间
         */
        public static final String PASSWORD = "^[a-zA-Z]\\w{5,17}$";

        /**
         * 强密码正则表达式
         *
         * <p>以字母开头, 长度在 [8~16] 之间, 含大小写字母和数字
         */
        public static final String STRONG_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$";

        /**
         * 强密码带特殊符号正则表达式
         *
         * <p>以字母开头, 长度在 [8~16] 之间, 含大小写字母/数字/特殊字符
         */
        public static final String STRONG_PASSWORD_WITH_SPECIAL = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$";

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        protected Regex() {
        }
    }


    /**
     * 时间格式
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    public static abstract class Date {

        /**
         * 时间格式 [yyyy-MM-dd HH:mm:ss]
         */
        public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

        /**
         * 时间格式 [yyyy-MM-dd]
         */
        public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

        /**
         * 时间格式 [HH:mm:ss]
         */
        public static final String NORM_TIME_PATTERN = "HH:mm:ss";

        /**
         * 时间格式 [yyyy]
         */
        public static final String NORM_YEAR_PATTERN = "yyyy";

        /**
         * 时间格式 [yyyy-MM]
         */
        public static final String NORM_YEARMONTH_PATTERN = "yyyy-MM";

        /**
         * 时间格式 [MM-dd]
         */
        public static final String NORM_MONTHDAY_PATTERN = "MM-dd";

        /**
         * 时间格式 [yyyyMMddHHmmss]
         */
        public static final String COMPACT_DATETIME_PATTERN = "yyyyMMddHHmmss";

        /**
         * 时间格式 [yyyyMMdd]
         */
        public static final String COMPACT_DATE_PATTERN = "yyyyMMdd";

        /**
         * 时间格式 [HHmmss]
         */
        public static final String COMPACT_TIME_PATTERN = "HHmmss";

        /**
         * 时间格式 [yyyyMM]
         */
        public static final String COMPACT_YEARMONTH_PATTERN = "yyyyMM";

        /**
         * 时间格式 [MMdd]
         */
        public static final String COMPACT_MONTHDAY_PATTERN = "MMdd";

        /**
         * 时间格式 [yyyy-MM-dd HH:mm:ss]
         */
        public static final DateTimeFormatter NORM_DATETIME_FORMATTER;

        /**
         * 时间格式 [yyyy-MM-dd]
         */
        public static final DateTimeFormatter NORM_DATE_FORMATTER;

        /**
         * 时间格式 [HH:mm:ss]
         */
        public static final DateTimeFormatter NORM_TIME_FORMATTER;

        /**
         * 时间格式 [yyyy]
         */
        public static final DateTimeFormatter NORM_YEAR_FORMATTER;

        /**
         * 时间格式 [yyyy-MM]
         */
        public static final DateTimeFormatter NORM_YEARMONTH_FORMATTER;

        /**
         * 时间格式 [MM-dd]
         */
        public static final DateTimeFormatter NORM_MONTHDAY_FORMATTER;

        /**
         * 时间格式 [yyyy-MM-dd HH:mm:ss+HH:MM:ss]
         */
        public static final DateTimeFormatter NORM_OFFSET_DATETIME_FORMATTER;

        /**
         * 时间格式 [yyyy-MM-dd HH:mm:ss+HH:MM:ss[ZoneRegionId()]]
         */
        public static final DateTimeFormatter NORM_ZONED_DATETIME_FORMATTER;

        /**
         * 时间格式 [HH:mm:ss+HH:MM:ss]
         */
        public static final DateTimeFormatter NORM_OFFSET_TIME_FORMATTER;

        /**
         * 时间格式 [yyyyMMddHHmmss]
         */
        public static final DateTimeFormatter COMPACT_DATETIME_FORMATTER;

        /**
         * 时间格式 [yyyyMMdd]
         */
        public static final DateTimeFormatter COMPACT_DATE_FORMATTER;

        /**
         * 时间格式 [HHmmss]
         */
        public static final DateTimeFormatter COMPACT_TIME_FORMATTER;

        /**
         * 时间格式 [yyyyMM]
         */
        public static final DateTimeFormatter COMPACT_YEARMONTH_FORMATTER;

        /**
         * 时间格式 [MMdd]
         */
        public static final DateTimeFormatter COMPACT_MONTHDAY_FORMATTER;

        /**
         * 时间格式 [yyyyMMddHHmmss+HHMMss]
         */
        public static final DateTimeFormatter COMPACT_OFFSET_DATETIME_FORMATTER;

        /**
         * 时间格式 [yyyyMMddHHmmss+HHMMss[ZoneRegionId()]]
         */
        public static final DateTimeFormatter COMPACT_ZONED_DATETIME_FORMATTER;

        /**
         * 时间格式 [HHmmss+HHMMss]
         */
        public static final DateTimeFormatter COMPACT_OFFSET_TIME_FORMATTER;

        static {
            // HH:mm:ss
            NORM_TIME_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.HOUR_OF_DAY, 2)
                    .appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                    .appendLiteral(':')
                    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyy-MM-dd
            NORM_DATE_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)
                    .appendLiteral('-')
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .appendLiteral('-')
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyy
            NORM_YEAR_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyy-MM
            NORM_YEARMONTH_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)
                    .appendLiteral('-')
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // MM-dd
            NORM_MONTHDAY_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .appendLiteral('-')
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyy-MM-dd HH:mm:ss
            NORM_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
                    .append(NORM_DATE_FORMATTER)
                    .appendLiteral(' ')
                    .append(NORM_TIME_FORMATTER)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyy-MM-dd HH:mm:ss+HH:MM:ss
            NORM_OFFSET_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(NORM_DATETIME_FORMATTER)
                    .appendOffsetId()
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyy-MM-dd HH:mm:ss+HH:MM:ss[ZoneRegionId()]
            NORM_ZONED_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
                    .append(NORM_OFFSET_DATETIME_FORMATTER)
                    .optionalStart()
                    .appendLiteral('[')
                    .parseCaseSensitive()
                    .appendZoneRegionId()
                    .appendLiteral(']')
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // HH:mm:ss+HH:MM:ss
            NORM_OFFSET_TIME_FORMATTER = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(NORM_TIME_FORMATTER)
                    .appendOffsetId()
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);

            // yyyyMM
            COMPACT_YEARMONTH_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // MMdd
            COMPACT_MONTHDAY_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // HHmmss
            COMPACT_TIME_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.HOUR_OF_DAY, 2)
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyyMMdd
            COMPACT_DATE_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyyMMddHHmmss
            COMPACT_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
                    .append(COMPACT_DATE_FORMATTER)
                    .append(COMPACT_TIME_FORMATTER)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyyMMddHHmmss+HHMMss
            COMPACT_OFFSET_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(COMPACT_DATETIME_FORMATTER)
                    .appendOffset("+HH:MM:ss", "Z")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // yyyyMMddHHmmss+HHMMss[ZoneRegionId()]
            COMPACT_ZONED_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
                    .append(COMPACT_OFFSET_DATETIME_FORMATTER)
                    .optionalStart()
                    .appendLiteral('[')
                    .parseCaseSensitive()
                    .appendZoneRegionId()
                    .appendLiteral(']')
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            // HHmmss+HHMMss
            COMPACT_OFFSET_TIME_FORMATTER = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(COMPACT_TIME_FORMATTER)
                    .appendOffset("+HHMMss", "Z")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
        }

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        protected Date() {
        }
    }
}
