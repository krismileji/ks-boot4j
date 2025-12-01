package host.springboot.framework.mybatisplus.exception;

import host.springboot.framework.mybatisplus.enumeration.error.MybatisServiceErrorEnum;
import host.springboot.framework3.core.exception.ApplicationException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * MybatisService自定义异常
 *
 * @author JiYinchuan
 * @see MybatisServiceErrorEnum
 * @since 0.1.0
 */
public class MybatisServiceException extends ApplicationException {

    /**
     * 构造器
     *
     * @param mybatisServiceErrorEnum 异常枚举
     * @since 0.1.0
     */
    public MybatisServiceException(@NonNull MybatisServiceErrorEnum mybatisServiceErrorEnum) {
        super(mybatisServiceErrorEnum);
    }

    /**
     * 构造器
     *
     * @param mybatisServiceErrorEnum 异常枚举
     * @param userTip                 用户提示
     * @since 0.1.0
     */
    public MybatisServiceException(@NonNull MybatisServiceErrorEnum mybatisServiceErrorEnum, @Nullable String userTip) {
        super(mybatisServiceErrorEnum, userTip);
    }
}