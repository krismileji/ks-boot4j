package host.springboot.framework3.core.enumeration.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import host.springboot.framework3.core.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

/**
 * ThreadLocal异常枚举
 *
 * <ul>
 *     <li><b>K0001</b> - 框架执行异常
 *         <ul>
 *             <li><b>K0100</b> - ThreadLocal异常</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AllArgsConstructor
public enum FrameworkErrorEnum implements BaseEnum<String> {

    /**
     * 框架执行异常
     */
    FRAMEWORK_EXECUTION_ERROR("K0001", "框架执行异常"),

    /**
     * ThreadLocal异常
     */
    THREAD_LOCAL_ERROR("K0100", "ThreadLocal异常"),

    /**
     * 设置的键为空
     */
    KEY_EMPTY("K0110", "设置的键为空"),

    /**
     * 设置的值为空
     */
    VALUE_EMPTY("K0120", "设置的值为空"),

    /**
     * 获取数据结果为空
     */
    RESULT_EMPTY("K0130", "获取数据结果为空");

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

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 通过枚举值解析枚举
     *
     * @param value 枚举值
     * @return 枚举
     * @since 0.1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static @Nullable FrameworkErrorEnum parse(@Nullable String value) {
        return BaseEnum.parse(value, FrameworkErrorEnum.class);
    }
}
