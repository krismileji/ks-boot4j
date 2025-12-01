package host.springboot.framework.mybatisplus.enumeration.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import host.springboot.framework3.core.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

/**
 * MybatisPlus异常枚举
 *
 * @author JiYinchuan
 * @see host.springboot.framework.mybatisplus.exception.MybatisServiceException
 * @since 0.1.0
 */
@AllArgsConstructor
public enum MybatisServiceErrorEnum implements BaseEnum<String> {

    /**
     * MybatisService异常
     */
    MYBATIS_SERVICE_ERROR("M0100", "MybatisService异常"),

    /**
     * ID为空
     */
    ID_IS_NULL("M0101", "ID为空"),

    /**
     * 结果为空
     */
    RESULT_IS_NULL("M0102", "结果为空"),

    /**
     * 结果集为空
     */
    RESULT_SET_IS_NULL("M0103", "结果集为空"),

    /**
     * 执行操作失败
     */
    OPERATION_ERROR("M0110", "执行操作失败"),

    /**
     * ID异常
     */
    ID_ERROR("M0111", "ID异常"),

    /**
     * 幂等性重复
     */
    IDEMPOTENT_REPETITION("M0120", "幂等性重复");

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
    public static @Nullable MybatisServiceErrorEnum parse(@Nullable String value) {
        return BaseEnum.parse(value, MybatisServiceErrorEnum.class);
    }
}
