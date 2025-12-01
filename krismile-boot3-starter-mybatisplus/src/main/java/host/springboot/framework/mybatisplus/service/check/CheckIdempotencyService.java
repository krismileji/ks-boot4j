package host.springboot.framework.mybatisplus.service.check;

import host.springboot.framework.mybatisplus.enumeration.error.MybatisServiceErrorEnum;
import host.springboot.framework.mybatisplus.exception.MybatisServiceException;
import host.springboot.framework3.core.exception.ApplicationException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 幂等性检查Service
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface CheckIdempotencyService extends BaseCheckService {

    /**
     * 默认幂等性重复异常对象
     */
    Function<String, MybatisServiceException> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION =
            userTip -> new MybatisServiceException(MybatisServiceErrorEnum.IDEMPOTENT_REPETITION, userTip);

    /**
     * 检查幂等性
     *
     * @param isExists 是否存在
     * @throws MybatisServiceException 幂等性重复
     * @since 0.1.0
     */
    default void checkIdempotency(@Nullable Boolean isExists) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(isExists) ? !isExists : null,
                () -> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION.apply(null));
    }

    /**
     * 检查幂等性
     *
     * @param isExists 结果数量
     * @param userTip  用户提示信息
     * @throws MybatisServiceException 幂等性重复
     * @since 0.1.0
     */
    default void checkIdempotency(@Nullable Boolean isExists, @Nullable String userTip) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(isExists) ? !isExists : null,
                () -> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION.apply(userTip));
    }

    /**
     * 检查幂等性
     *
     * @param isExists 结果数量
     * @param e        当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkIdempotency(
            @Nullable Boolean isExists,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(Objects.nonNull(isExists) ? !isExists : null, e);
    }

    /**
     * 检查幂等性
     *
     * @param resultCount 结果数量
     * @throws MybatisServiceException 幂等性重复
     * @since 0.1.0
     */
    default void checkIdempotency(@Nullable Long resultCount) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount < 1 : null,
                () -> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION.apply(null));
    }

    /**
     * 检查幂等性
     *
     * @param resultCount 结果数量
     * @param userTip     用户提示信息
     * @throws MybatisServiceException 幂等性重复
     * @since 0.1.0
     */
    default void checkIdempotency(@Nullable Long resultCount, @Nullable String userTip) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount < 1 : null,
                () -> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION.apply(userTip));
    }

    /**
     * 检查幂等性
     *
     * @param resultCount 结果数量
     * @param e           当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkIdempotency(
            @Nullable Long resultCount,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount < 1 : null, e);
    }

    /**
     * 检查幂等性
     *
     * @param resultCount 结果数量
     * @throws MybatisServiceException 幂等性重复
     * @since 0.1.0
     */
    default void checkIdempotency(@Nullable Integer resultCount) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount < 1 : null,
                () -> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION.apply(null));
    }

    /**
     * 检查幂等性
     *
     * @param resultCount 结果数量
     * @param userTip     用户提示信息
     * @throws MybatisServiceException 幂等性重复
     * @since 0.1.0
     */
    default void checkIdempotency(
            @Nullable Integer resultCount,
            @Nullable String userTip)
            throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount < 1 : null,
                () -> DEFAULT_IDEMPOTENT_REPETITION_EXCEPTION.apply(userTip));
    }

    /**
     * 检查幂等性
     *
     * @param resultCount 结果数量
     * @param e           当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkIdempotency(
            @Nullable Integer resultCount,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount < 1 : null, e);
    }
}
