package host.springboot.framework3.core.response;

import host.springboot.framework3.core.enumeration.BaseEnum;
import host.springboot.framework3.core.enumeration.error.ErrorCodeEnum;
import host.springboot.framework3.core.page.Pageable;
import host.springboot.framework3.core.response.vo.BaseVO;
import host.springboot.framework3.core.response.vo.MultiVO;
import host.springboot.framework3.core.response.vo.PageVO;
import host.springboot.framework3.core.response.vo.SingleVO;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * 全局响应VO类
 *
 * <p>该类为全局响应VO, 所有控制器返回必须使用此类进行返回, 返回值类型参考 {@link BaseVO} 下所有实现
 *
 * @author JiYinchuan
 * @see BaseVO
 * @see SingleVO
 * @see MultiVO
 * @see PageVO
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
     * 基础成功返回
     *
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok() {
        return new BaseVO(ErrorCodeEnum.OK);
    }

    /**
     * 基础成功返回
     *
     * @param userTip 用户提示信息
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(@Nullable String userTip) {
        return new BaseVO(ErrorCodeEnum.OK, userTip);
    }

    /**
     * 基础成功返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(
            @NonNull String errorCode,
            @NonNull String errorMessage) {
        return new BaseVO(errorCode, errorMessage);
    }

    /**
     * 基础成功返回
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip) {
        return new BaseVO(errorCode, errorMessage, userTip);
    }

    /**
     * 基础成功返回并执行函数式接口
     *
     * @param supplier 函数式接口
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(@NonNull Supplier<?> supplier) {
        supplier.get();
        return new BaseVO(ErrorCodeEnum.OK);
    }

    /**
     * 基础成功返回并执行函数式接口
     *
     * @param supplier 函数式接口
     * @param userTip  用户提示信息
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(
            @NonNull Supplier<?> supplier,
            @Nullable String userTip) {
        supplier.get();
        return new BaseVO(ErrorCodeEnum.OK, userTip);
    }

    /**
     * 基础成功返回并执行函数式接口
     *
     * @param supplier     函数式接口
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(
            @NonNull Supplier<?> supplier,
            @NonNull String errorCode,
            @NonNull String errorMessage) {
        supplier.get();
        return new BaseVO(errorCode, errorMessage);
    }

    /**
     * 基础成功返回并执行函数式接口
     *
     * @param supplier     函数式接口
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @return 基础成功返回
     * @since 0.1.0
     */
    public static BaseVO ok(
            @NonNull Supplier<?> supplier,
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip) {
        supplier.get();
        return new BaseVO(errorCode, errorMessage, userTip);
    }

    // -------------------------------- OK-Single --------------------------------

    /**
     * 单条数据成功返回
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 单条数据成功返回
     * @since 0.1.0
     */
    public static <T> SingleVO<T> okSingle(T data) {
        return new SingleVO<>(ErrorCodeEnum.OK, data);
    }

    /**
     * 单条数据成功返回
     *
     * @param userTip 用户提示信息
     * @param data    数据
     * @param <T>     数据类型
     * @return 单条数据成功返回
     * @since 0.1.0
     */
    public static <T> SingleVO<T> okSingle(
            @Nullable String userTip,
            T data) {
        return new SingleVO<>(ErrorCodeEnum.OK, userTip, data);
    }

    // -------------------------------- OK-Multi --------------------------------

    /**
     * 多条数据成功返回
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 多条数据成功返回
     * @since 0.1.0
     */
    public static <T> MultiVO<T> okMulti(Collection<T> data) {
        return new MultiVO<>(ErrorCodeEnum.OK, data);
    }

    /**
     * 多条数据成功返回
     *
     * @param userTip 用户提示信息
     * @param data    数据
     * @param <T>     数据类型
     * @return 多条数据成功返回
     * @since 0.1.0
     */
    public static <T> MultiVO<T> okMulti(
            @Nullable String userTip,
            Collection<T> data) {
        return new MultiVO<>(ErrorCodeEnum.OK, userTip, data);
    }

    // -------------------------------- OK-Page --------------------------------

    /**
     * 分页数据成功返回
     *
     * @param pageable 分页数据
     * @param <E>      分页数据集合中元素类型
     * @param <T>      分页数据集合类型
     * @return 分页数据成功返回
     * @since 0.1.0
     */
    public static <E, T extends Collection<E>> PageVO<E> okPage(@NonNull Pageable<T> pageable) {
        return new PageVO<>(ErrorCodeEnum.OK, pageable.convertPageDetailVO(), pageable.getRecords());
    }

    /**
     * 分页数据成功返回
     *
     * @param userTip  用户提示信息
     * @param pageable 分页数据
     * @param <E>      分页数据集合中元素类型
     * @param <T>      分页数据集合类型
     * @return 分页数据成功返回
     * @since 0.1.0
     */
    public static <E, T extends Collection<E>> PageVO<E> okPage(
            @Nullable String userTip,
            @NonNull Pageable<T> pageable) {
        return new PageVO<>(ErrorCodeEnum.OK, userTip, pageable.convertPageDetailVO(), pageable.getRecords());
    }

    // -------------------------------- Base --------------------------------

    /**
     * 基础自定义响应
     *
     * @param baseEnum 响应枚举
     * @return 基础返回
     * @since 0.1.0
     */
    public static BaseVO base(@NonNull BaseEnum<@NonNull String> baseEnum) {
        return new BaseVO(baseEnum);
    }

    /**
     * 基础自定义响应
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @return 基础返回
     * @since 0.1.0
     */
    public static BaseVO base(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable String userTip) {
        return new BaseVO(baseEnum, userTip);
    }

    /**
     * 基础自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @return 基础返回
     * @since 0.1.0
     */
    public static BaseVO base(
            @NonNull String errorCode,
            @NonNull String errorMessage) {
        return new BaseVO(errorCode, errorMessage);
    }

    /**
     * 基础自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @return 基础返回
     * @since 0.1.0
     */
    public static BaseVO base(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip) {
        return new BaseVO(errorCode, errorMessage, userTip);
    }

    // -------------------------------- Single --------------------------------

    /**
     * 单条数据自定义响应
     *
     * @param baseEnum 响应枚举
     * @param data     数据
     * @param <T>      数据类型
     * @return 单条数据
     * @since 0.1.0
     */
    public static <T> SingleVO<T> single(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            T data) {
        return new SingleVO<>(baseEnum, data);
    }

    /**
     * 单条数据自定义响应
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param data     数据
     * @param <T>      数据类型
     * @return 单条数据
     * @since 0.1.0
     */
    public static <T> SingleVO<T> single(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable String userTip,
            T data) {
        return new SingleVO<>(baseEnum, userTip, data);
    }

    /**
     * 单条数据自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param data         数据
     * @param <T>          数据类型
     * @return 单条数据
     * @since 0.1.0
     */
    public static <T> SingleVO<T> single(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            T data) {
        return new SingleVO<>(errorCode, errorMessage, data);
    }

    /**
     * 单条数据自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param data         数据
     * @param <T>          数据类型
     * @return 单条数据
     * @since 0.1.0
     */
    public static <T> SingleVO<T> single(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            T data) {
        return new SingleVO<>(errorCode, errorMessage, userTip, data);
    }

    // -------------------------------- Multi --------------------------------

    /**
     * 多条数据自定义响应
     *
     * @param baseEnum 响应枚举
     * @param data     数据
     * @param <E>      数据类型
     * @return 多条数据自定义响应
     * @since 0.1.0
     */
    public static <E> MultiVO<E> multi(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            Collection<E> data) {
        return new MultiVO<>(baseEnum, data);
    }

    /**
     * 多条数据自定义响应
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param data     数据
     * @param <E>      数据类型
     * @return 多条数据自定义响应
     * @since 0.1.0
     */
    public static <E> MultiVO<E> multi(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable String userTip,
            Collection<E> data) {
        return new MultiVO<>(baseEnum, userTip, data);
    }

    /**
     * 多条数据自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param data         数据
     * @param <E>          数据类型
     * @return 多条数据自定义响应
     * @since 0.1.0
     */
    public static <E> MultiVO<E> multi(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            Collection<E> data) {
        return new MultiVO<>(errorCode, errorMessage, data);
    }

    /**
     * 多条数据自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param data         数据
     * @param <E>          数据类型
     * @return 多条数据自定义响应
     * @since 0.1.0
     */
    public static <E> MultiVO<E> multi(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            Collection<E> data) {
        return new MultiVO<>(errorCode, errorMessage, userTip, data);
    }

    // -------------------------------- Page --------------------------------

    /**
     * 分页数据自定义响应
     *
     * @param baseEnum 响应枚举
     * @param pageable 分页数据
     * @param <E>      分页数据集合中元素类型
     * @param <T>      分页数据集合类型
     * @return 分页数据自定义响应
     */
    public static <E, T extends Collection<E>> PageVO<E> page(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @NonNull Pageable<T> pageable) {
        return new PageVO<>(baseEnum, pageable.convertPageDetailVO(), pageable.getRecords());
    }

    /**
     * 分页数据自定义响应
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param pageable 分页数据
     * @param <E>      分页数据集合中元素类型
     * @param <T>      分页数据集合类型
     * @return 分页数据自定义响应
     */
    public static <E, T extends Collection<E>> PageVO<E> page(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable String userTip,
            @NonNull Pageable<T> pageable) {
        return new PageVO<>(baseEnum, userTip, pageable.convertPageDetailVO(), pageable.getRecords());
    }

    /**
     * 分页数据自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param pageable     分页数据
     * @param <E>          分页数据集合中元素类型
     * @param <T>          分页数据集合类型
     * @return 分页数据自定义响应
     */
    public static <E, T extends Collection<E>> PageVO<E> page(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @NonNull Pageable<T> pageable) {
        return new PageVO<>(errorCode, errorMessage, pageable.convertPageDetailVO(), pageable.getRecords());
    }

    /**
     * 分页数据自定义响应
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param pageable     分页数据
     * @param <E>          分页数据集合中元素类型
     * @param <T>          分页数据集合类型
     * @return 分页数据自定义响应
     */
    public static <E, T extends Collection<E>> PageVO<E> page(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            @NonNull Pageable<T> pageable) {
        return new PageVO<>(errorCode, errorMessage, userTip, pageable.convertPageDetailVO(), pageable.getRecords());
    }
}
