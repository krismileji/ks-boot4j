package host.springboot.framework3.core.response;

import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.enumeration.error.ErrorCodeEnum;
import host.springboot.framework3.core.page.Pageable;
import host.springboot.framework3.core.response.vo.VO;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 全局响应 VO 类
 *
 * <p>该类为全局响应VO, 所有控制器返回必须使用此类进行返回, 返回值类型参考 {@link VO} 下所有实现</p>
 *
 * @author JiYinchuan
 * @see VO
 * @since 0.1.0
 */
@Data
public final class R implements Serializable {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private R() {
    }

    // -------------------------------- OK --------------------------------

    /**
     * 成功返回
     *
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok() {
        return new VO<>(ErrorCodeEnum.OK);
    }

    /**
     * 成功返回
     *
     * @param userTip 用户提示信息
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(@Nullable String userTip) {
        return new VO<>(ErrorCodeEnum.OK, userTip);
    }

    /**
     * 成功返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage) {
        return new VO<>(errorCode, errorMessage);
    }

    /**
     * 成功返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip) {
        return new VO<>(errorCode, errorMessage, userTip);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param consumer 函数式接口
     * @return 成功返回
     * @since 0.2.0
     */
    public static VO<?> ok(@NonNull Consumer<Void> consumer) {
        return ok(null, consumer);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param userTip  用户提示信息
     * @param consumer 函数式接口
     * @return 成功返回
     * @since 0.2.0
     */
    public static VO<?> ok(
            @Nullable String userTip,
            @NonNull Consumer<Void> consumer) {
        return ok(ErrorCodeEnum.OK.getValue(), ErrorCodeEnum.OK.getReasonPhrase(), userTip, consumer);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param consumer     函数式接口
     * @return 成功返回
     * @since 0.2.0
     */
    public static VO<?> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @NonNull Consumer<Void> consumer) {
        return ok(errorCode, errorMessage, null, consumer);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param consumer     函数式接口
     * @return 成功返回
     * @since 0.2.0
     */
    public static VO<?> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            @NonNull Consumer<Void> consumer) {
        consumer.accept(null);
        return new VO<>(errorCode, errorMessage, userTip);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 成功返回
     * @since 0.2.0
     */
    public static <T> VO<T> ok(@Nullable T data) {
        return new VO<>(ErrorCodeEnum.OK, data);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param userTip 用户提示信息
     * @param data    数据
     * @param <T>     数据类型
     * @return 成功返回
     * @since 0.2.0
     */
    public static <T> VO<T> ok(
            @Nullable String userTip,
            @Nullable T data) {
        return new VO<>(ErrorCodeEnum.OK, userTip, data);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param data         数据
     * @param <T>          数据类型
     * @return 成功返回
     * @since 0.2.0
     */
    public static <T> VO<T> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable T data) {
        return new VO<>(errorCode, errorMessage, data);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param data         数据
     * @param <T>          数据类型
     * @return 成功返回
     * @since 0.1.0
     */
    public static <T> VO<T> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            @Nullable T data) {
        return new VO<>(errorCode, errorMessage, userTip, data);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param supplier 函数式接口
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(@NonNull Supplier<?> supplier) {
        return ok(null, supplier);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param userTip  用户提示信息
     * @param supplier 函数式接口
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(
            @Nullable String userTip,
            @NonNull Supplier<?> supplier) {
        return ok(ErrorCodeEnum.OK.getValue(), ErrorCodeEnum.OK.getReasonPhrase(), userTip, supplier);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param supplier     函数式接口
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @NonNull Supplier<?> supplier) {
        return ok(errorCode, errorMessage, null, supplier);
    }

    /**
     * 成功返回并执行函数式接口
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param supplier     函数式接口
     * @return 成功返回
     * @since 0.1.0
     */
    public static VO<?> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            @NonNull Supplier<?> supplier) {
        Object data = supplier.get();
        return data instanceof Pageable<?> pageable
                ? ok(errorCode, errorCode, userTip, pageable)
                : ok(errorCode, errorMessage, userTip, data);
    }

    /**
     * 分页数据成功返回
     *
     * @param pageable 分页数据
     * @param <E>      分页数据集合中元素类型
     * @param <T>      分页数据集合类型
     * @return 分页数据成功返回
     * @since 0.2.0
     */
    public static <E, T extends Collection<E>> VO<T> ok(@NonNull Pageable<T> pageable) {
        return new VO<>(ErrorCodeEnum.OK, pageable.getRecords(), pageable.convertPageDetailVO());
    }

    /**
     * 分页数据成功返回
     *
     * @param userTip  用户提示
     * @param pageable 分页数据
     * @param <E>      分页数据集合中元素类型
     * @param <T>      分页数据集合类型
     * @return 分页数据成功返回
     * @since 0.2.0
     */
    public static <E, T extends Collection<E>> VO<T> ok(@Nullable String userTip, @NonNull Pageable<T> pageable) {
        return new VO<>(ErrorCodeEnum.OK, userTip, pageable.getRecords(), pageable.convertPageDetailVO());
    }

    /**
     * 分页数据成功返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param pageable     分页数据
     * @param <E>          分页数据集合中元素类型
     * @param <T>          分页数据集合类型
     * @return 分页数据成功返回
     * @since 0.2.0
     */
    public static <E, T extends Collection<E>> VO<T> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @NonNull Pageable<T> pageable) {
        return new VO<>(errorCode, errorMessage, pageable.getRecords(), pageable.convertPageDetailVO());
    }

    /**
     * 分页数据成功返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param pageable     分页数据
     * @param <E>          分页数据集合中元素类型
     * @param <T>          分页数据集合类型
     * @return 分页数据成功返回
     * @since 0.2.0
     */
    public static <E, T extends Collection<E>> VO<T> ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            @NonNull Pageable<T> pageable) {
        return new VO<>(errorCode, errorMessage, userTip, pageable.getRecords(), pageable.convertPageDetailVO());
    }

    // -------------------------------- Fail --------------------------------

    /**
     * 失败返回
     *
     * @param baseEnum 错误枚举
     * @return 失败返回
     * @since 0.2.0
     */
    public static VO<?> fail(@NonNull BaseEnum<String> baseEnum) {
        return new VO<>(baseEnum);
    }

    /**
     * 失败返回
     *
     * @param baseEnum 错误枚举
     * @param userTip 用户提示信息
     * @return 失败返回
     * @since 0.2.0
     */
    public static VO<?> fail(@NonNull BaseEnum<String> baseEnum, @Nullable String userTip) {
        return new VO<>(baseEnum, userTip);
    }

    /**
     * 失败返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @return 失败返回
     * @since 0.2.0
     */
    public static VO<?> fail(
            @NonNull String errorCode,
            @NonNull String errorMessage) {
        return new VO<>(errorCode, errorMessage);
    }

    /**
     * 失败返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @return 失败返回
     * @since 0.2.0
     */
    public static VO<?> fail(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip) {
        return new VO<>(errorCode, errorMessage, userTip);
    }
}
