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
import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页VO
 *
 * <p>该类为分页VO, 用于分页数据返回的情况下使用, 分页详情数据请参考 {@link PageDetailVO} 说明, 使用 {@link R} 中相关方法进行返回
 *
 * @param <T> 分页数据类型
 * @author JiYinchuan
 * @see PageDetailVO
 * @see R
 * @since 0.1.0
 */
@Data
@FieldNameConstants
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PageVO<T> extends BaseVO implements Serializable {

    /**
     * 分页详情数据
     */
    private PageDetailVO detail;

    /**
     * 分页数据
     */
    private Collection<T> data;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public PageVO() {
        data = new ArrayList<>();
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param detail   分页详情数据
     * @param data     分页数据
     * @since 0.1.0
     */
    public PageVO(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @NonNull PageDetailVO detail,
            Collection<T> data) {
        super(baseEnum);
        this.detail = detail;
        this.data = data;
    }

    /**
     * 构造器
     *
     * @param baseEnum 响应枚举
     * @param userTip  用户提示信息
     * @param detail   分页详情数据
     * @param data     分页数据
     * @since 0.1.0
     */
    public PageVO(
            @NonNull BaseEnum<@NonNull String> baseEnum,
            @Nullable String userTip,
            @NonNull PageDetailVO detail,
            Collection<T> data) {
        super(baseEnum, userTip);
        this.detail = detail;
        this.data = data;
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param detail       分页详情数据
     * @param data         分页数据
     * @since 0.1.0
     */
    public PageVO(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @NonNull PageDetailVO detail,
            Collection<T> data) {
        super(errorCode, errorMessage);
        this.detail = detail;
        this.data = data;
    }

    /**
     * 构造器
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param userTip      用户提示信息
     * @param detail       分页详情数据
     * @param data         分页数据
     * @since 0.1.0
     */
    public PageVO(
            @NonNull String errorCode,
            @NonNull String errorMessage,
            @Nullable String userTip,
            @NonNull PageDetailVO detail,
            Collection<T> data) {
        super(errorCode, errorMessage, userTip);
        this.detail = detail;
        this.data = data;
    }
}
