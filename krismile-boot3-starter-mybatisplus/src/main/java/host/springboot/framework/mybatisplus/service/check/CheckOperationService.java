package host.springboot.framework.mybatisplus.service.check;

import host.springboot.framework.mybatisplus.enumeration.error.MybatisServiceErrorEnum;
import host.springboot.framework.mybatisplus.exception.MybatisServiceException;
import host.springboot.framework3.core.exception.ApplicationException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 操作检查Service
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface CheckOperationService extends BaseCheckService {

    /**
     * 默认操作异常对象
     */
    Function<String, MybatisServiceException> DEFAULT_OPERATION_ERROR_EXCEPTION =
            userTip -> new MybatisServiceException(MybatisServiceErrorEnum.OPERATION_ERROR, userTip);

    /**
     * 通用结果检查
     *
     * @param operateResult 操作结果
     * @throws MybatisServiceException 执行操作失败
     * @since 0.1.0
     */
    default void checkOperation(@Nullable Boolean operateResult) throws MybatisServiceException {
        dynamicThrow(operateResult, () -> DEFAULT_OPERATION_ERROR_EXCEPTION.apply(null));
    }

    /**
     * 通用结果检查
     *
     * @param operateResult 操作结果
     * @param userTip       默认用户提示信息
     * @throws MybatisServiceException 执行操作失败
     * @since 0.1.0
     */
    default void checkOperation(
            @Nullable Boolean operateResult,
            @Nullable String userTip)
            throws MybatisServiceException {
        dynamicThrow(operateResult, () -> DEFAULT_OPERATION_ERROR_EXCEPTION.apply(userTip));
    }

    /**
     * 通用结果检查
     *
     * @param operateResult 操作结果
     * @param e             当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkOperation(
            @Nullable Boolean operateResult,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(operateResult, e);
    }
}
