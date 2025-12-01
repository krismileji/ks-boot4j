package host.springboot.framework3.core.response.vo;

import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.response.R;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;

/**
 * 单条数据VO
 *
 * <p>该类为单条数据VO, 用于单条数据返回的情况下使用, 使用 {@link R} 中相关方法进行返回
 *
 * @param <T> 数据类型
 * @author JiYinchuan
 * @see R
 * @since 0.1.0
 */
@Data
@FieldNameConstants
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class SingleVO<T> extends BaseVO implements Serializable {

    /**
     * 数据
     */
    private T data;

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param data     数据
     * @since 0.1.0
     */
    public SingleVO(@NonNull BaseEnum<@NonNull String> baseEnum, T data) {
        super(baseEnum);
        this.data = data;
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param data     数据
     * @since 0.1.0
     */
    public SingleVO(@NonNull BaseEnum<@NonNull String> baseEnum, @Nullable String userTip, T data) {
        super(baseEnum, userTip);
        this.data = data;
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param data         数据
     * @since 0.1.0
     */
    public SingleVO(@NonNull String errorCode, @NonNull String errorMessage, T data) {
        super(errorCode, errorMessage);
        this.data = data;
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param data         数据
     * @since 0.1.0
     */
    public SingleVO(@NonNull String errorCode, @NonNull String errorMessage, @Nullable String userTip, T data) {
        super(errorCode, errorMessage, userTip);
        this.data = data;
    }
}
