package host.springboot.framework3.core.response.vo;

import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.enumeration.error.ErrorCodeEnum;
import host.springboot.framework3.core.response.R;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;

/**
 * VO
 *
 * <p>该类为全局VO响应基类, 提供了默认响应参数, 用于无数据返回的情况下使用, 使用 {@link R} 中相关方法进行返回
 *
 * @param <T> 数据类型
 * @author JiYinchuan
 * @see R
 * @since 0.1.0
 */
@Data
@FieldNameConstants
public class VO<T> implements Serializable {

    /**
     * 默认成功用户提示信息
     */
    public static final String DEFAULT_SUCCESS_USER_TIP = ErrorCodeEnum.OK.getReasonPhrase();

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 用户提示信息
     */
    private String userTip;

    /**
     * 数据
     */
    private T data;

    /**
     * 分页数据
     */
    private PageVO page;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public VO() {
        this.userTip = DEFAULT_SUCCESS_USER_TIP;
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @since 0.1.0
     */
    public VO(@NonNull BaseEnum<@NonNull String> baseEnum) {
        this(baseEnum, DEFAULT_SUCCESS_USER_TIP);
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @since 0.1.0
     */
    public VO(@NonNull BaseEnum<@NonNull String> baseEnum, @Nullable String userTip) {
        this(baseEnum, userTip, null);
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param data     数据
     * @since 0.2.0
     */
    public VO(@NonNull BaseEnum<@NonNull String> baseEnum, @Nullable T data) {
        this(baseEnum, DEFAULT_SUCCESS_USER_TIP, data);
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param data     数据
     * @since 0.2.0
     */
    public VO(@NonNull BaseEnum<@NonNull String> baseEnum, @Nullable String userTip, @Nullable T data) {
        this(baseEnum.getValue(), baseEnum.getReasonPhrase(), userTip, data);
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param data     数据
     * @param page     分页数据
     * @since 0.2.0
     */
    public VO(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable T data, @Nullable PageVO page) {
        this(baseEnum, DEFAULT_SUCCESS_USER_TIP, data, page);
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param data     数据
     * @param page     分页数据
     * @since 0.2.0
     */
    public VO(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable String userTip, @Nullable T data,
            @Nullable PageVO page) {
        this(baseEnum.getValue(), baseEnum.getReasonPhrase(), userTip, data, page);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @since 0.1.0
     */
    public VO(@NonNull String errorCode, @NonNull String errorMessage) {
        this(errorCode, errorMessage, DEFAULT_SUCCESS_USER_TIP);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @since 0.1.0
     */
    public VO(@NonNull String errorCode, @NonNull String errorMessage, @Nullable String userTip) {
        this(errorCode, errorMessage, userTip, null);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param data         数据
     * @since 0.2.0
     */
    public VO(@NonNull String errorCode, @NonNull String errorMessage, @Nullable T data) {
        this(errorCode, errorMessage, DEFAULT_SUCCESS_USER_TIP, data, null);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param data         数据
     * @since 0.2.0
     */
    public VO(
            @NonNull String errorCode, @NonNull String errorMessage,
            @Nullable String userTip, @Nullable T data) {
        this(errorCode, errorMessage, userTip, data, null);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param data         数据
     * @param page         分页数据
     * @since 0.2.0
     */
    public VO(
            @NonNull String errorCode, @NonNull String errorMessage,
            @Nullable T data, @Nullable PageVO page) {
        this(errorCode, errorMessage, DEFAULT_SUCCESS_USER_TIP, data, page);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param data         数据
     * @param page         分页数据
     * @since 0.2.0
     */
    public VO(
            @NonNull String errorCode, @NonNull String errorMessage,
            @Nullable String userTip, @Nullable T data,
            @Nullable PageVO page) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.userTip = userTip;
        this.data = data;
        this.page = page;
    }
}
