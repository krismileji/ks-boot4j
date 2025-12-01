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
 * 是否存在检查Service
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface CheckExistsService extends BaseCheckService {

    /**
     * 默认结果为空异常对象
     */
    Function<String, MybatisServiceException> DEFAULT_RESULT_NULL_EXCEPTION =
            userTip -> new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, userTip);

    /**
     * 是否存在检查
     *
     * @param operateResult 是否存在结果
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default void checkExists(@Nullable Boolean operateResult) throws MybatisServiceException {
        dynamicThrow(operateResult, () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(null));
    }

    /**
     * 是否存在检查
     *
     * @param operateResult 是否存在结果
     * @param userTip       用户提示信息
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default void checkExists(@Nullable Boolean operateResult, @Nullable String userTip) throws MybatisServiceException {
        dynamicThrow(operateResult, () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(userTip));
    }

    /**
     * 是否存在检查
     *
     * @param result 结果
     * @param e      当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkExists(
            @Nullable Boolean result,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(result, e);
    }

    /**
     * 是否存在检查
     *
     * @param resultCount 结果数量
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default void checkExists(@Nullable Long resultCount) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount > 0 : null,
                () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(null));
    }

    /**
     * 是否存在检查
     *
     * @param resultCount 结果数量
     * @param userTip     用户提示信息
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default void checkExists(@Nullable Long resultCount, @Nullable String userTip) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount > 0 : null,
                () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(userTip));
    }

    /**
     * 是否存在检查
     *
     * @param resultCount 结果数量
     * @param e           当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkExists(
            @Nullable Long resultCount,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount > 0 : null, e);
    }

    /**
     * 是否存在检查
     *
     * @param resultCount 结果数量
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default void checkExists(@Nullable Integer resultCount) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount > 0 : null,
                () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(null));
    }

    /**
     * 是否存在检查
     *
     * @param resultCount 结果数量
     * @param userTip     用户提示信息
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default void checkExists(@Nullable Integer resultCount, @Nullable String userTip) throws MybatisServiceException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount > 0 : null,
                () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(userTip));
    }

    /**
     * 是否存在检查
     *
     * @param resultCount 结果数量
     * @param e           当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default void checkExists(
            @Nullable Integer resultCount,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        dynamicThrow(Objects.nonNull(resultCount) ? resultCount > 0 : null, e);
    }

    /**
     * 是否存在检查
     *
     * @param result 结果
     * @param <T>    结果类型
     * @return 结果
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default <T> @NonNull T checkExistsAndGet(@Nullable T result) throws MybatisServiceException {
        return getAndDynamicThrow(result, () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(null));
    }

    /**
     * 是否存在检查
     *
     * @param result  结果
     * @param userTip 用户提示信息
     * @param <T>     结果类型
     * @return 结果
     * @throws MybatisServiceException 结果为空
     * @since 0.1.0
     */
    default <T> @NonNull T checkExistsAndGet(@Nullable T result, @Nullable String userTip) throws MybatisServiceException {
        return getAndDynamicThrow(result, () -> DEFAULT_RESULT_NULL_EXCEPTION.apply(userTip));
    }

    /**
     * 是否存在检查
     *
     * @param result 结果
     * @param <T>    结果类型
     * @param e      当结果为 {@code null} 或 {@code false} 时抛出的结果, 不能为空
     * @return 结果
     * @throws ApplicationException 自定义异常
     * @since 0.1.0
     */
    default <T> @NonNull T checkExistsAndGet(
            @Nullable T result,
            @NonNull Supplier<@NonNull ApplicationException> e)
            throws ApplicationException {
        return getAndDynamicThrow(result, e);
    }
}
