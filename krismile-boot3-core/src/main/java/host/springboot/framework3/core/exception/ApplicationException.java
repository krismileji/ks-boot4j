package host.springboot.framework3.core.exception;

import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.util.inner.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * 全局自定义异常基类
 *
 * <p>该类为全局自定义异常基类, 为避免部分功能失效, 项目中所有的自定义异常原则上都必须继承该类</p>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {

    /**
     * 默认用户提示信息
     */
    public static final String DEFAULT_ERROR_USER_TIP = "网络开小差了, 请稍后再试 (╯﹏╰)";

    /**
     * 错误码
     */
    protected final String errorCode;

    /**
     * 错误信息
     */
    protected final String errorMessage;

    /**
     * 用户提示信息
     */
    protected final String userTip;

    /**
     * 错误枚举
     */
    protected final BaseEnum<String> errorEnum;

    /**
     * 构造器
     *
     * @param errorEnum 错误枚举
     * @see host.springboot.framework3.core.enumeration.error
     * @since 0.1.0
     */
    public ApplicationException(@NonNull BaseEnum<String> errorEnum) {
        super("errorCode: " + errorEnum.getValue() + ", errorMessage: " + errorEnum.getReasonPhrase());
        this.errorCode = errorEnum.getValue();
        this.errorMessage = errorEnum.getReasonPhrase();
        this.errorEnum = errorEnum;
        userTip = DEFAULT_ERROR_USER_TIP;
    }

    /**
     * 构造器
     *
     * @param errorEnum 错误枚举
     * @param userTip   用户提示信息
     * @since 0.1.0
     */
    public ApplicationException(@NonNull BaseEnum<String> errorEnum, @Nullable String userTip) {
        super("errorCode: " + errorEnum.getValue() + ", errorMessage: " + errorEnum.getReasonPhrase() + ", userTip: " + userTip);
        this.errorCode = errorEnum.getValue();
        this.errorMessage = errorEnum.getReasonPhrase();
        this.userTip = StringUtils.defaultString(userTip, DEFAULT_ERROR_USER_TIP);
        this.errorEnum = errorEnum;
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @since 0.1.0
     */
    public ApplicationException(@NonNull String errorCode, @NonNull String errorMessage, @Nullable String userTip) {
        super("errorCode: " + errorCode + ", errorMessage: " + errorMessage + ", userTip: " + userTip);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.userTip = StringUtils.defaultString(userTip, DEFAULT_ERROR_USER_TIP);
        this.errorEnum = new BaseEnum<>() {
            @Override
            public String getValue() {
                return errorCode;
            }

            @Override
            public String getReasonPhrase() {
                return errorMessage;
            }
        };
    }
}
