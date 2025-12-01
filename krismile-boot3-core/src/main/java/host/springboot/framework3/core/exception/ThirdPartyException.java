package host.springboot.framework3.core.exception;

import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.util.inner.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * 第三方自定义异常
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ThirdPartyException extends RuntimeException {

    /**
     * 错误码
     */
    protected final Object errorCode;

    /**
     * 错误信息
     */
    protected final String errorMessage;

    /**
     * 错误信息
     */
    protected final String userTip;

    /**
     * 构造器
     *
     * @param baseEnum 枚举基类
     * @since 0.1.0
     */
    public ThirdPartyException(@NonNull BaseEnum<Object> baseEnum) {
        super("第三方服务异常: [errorCode: " + baseEnum.getValue() + ", errorMessage: " + baseEnum.getReasonPhrase() + "]");
        this.errorCode = baseEnum.getValue();
        this.errorMessage = baseEnum.getReasonPhrase();
        this.userTip = ApplicationException.DEFAULT_ERROR_USER_TIP;
    }

    /**
     * 构造器
     *
     * @param baseEnum 枚举基类
     * @param userTip  用户提示信息
     * @since 0.1.0
     */
    public ThirdPartyException(@NonNull BaseEnum<Object> baseEnum, @Nullable String userTip) {
        super("第三方服务异常: [errorCode: " + baseEnum.getValue() + ", errorMessage: " + baseEnum.getReasonPhrase() + "]");
        this.errorCode = baseEnum.getValue();
        this.errorMessage = baseEnum.getReasonPhrase();
        this.userTip = StringUtils.defaultString(userTip, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @since 0.1.0
     */
    public ThirdPartyException(@NonNull Object errorCode, @NonNull String errorMessage) {
        super("第三方服务异常: [errorCode: " + errorCode + ", errorMessage: " + errorMessage + "]");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.userTip = ApplicationException.DEFAULT_ERROR_USER_TIP;
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @since 0.1.0
     */
    public ThirdPartyException(@NonNull Object errorCode, @NonNull String errorMessage, @Nullable String userTip) {
        super("第三方服务异常: [errorCode: " + errorCode + ", errorMessage: " + errorMessage + "]");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.userTip = StringUtils.defaultString(userTip, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }
}
