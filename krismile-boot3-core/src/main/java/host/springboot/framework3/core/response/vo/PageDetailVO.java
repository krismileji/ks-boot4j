package host.springboot.framework3.core.response.vo;

import host.springboot.framework3.core.response.R;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 分页详情VO
 *
 * <p>该类为 {@link PageVO} 中分页详情数据抽离, 禁止直接使用此类用于返回, 需要配合 {@link PageVO}, 并通过 {@link R} 进行返回
 *
 * @author JiYinchuan
 * @see PageVO
 * @since 0.1.0
 */
@Data
@FieldNameConstants
public class PageDetailVO implements Serializable {

    /**
     * 页码
     */
    private Long pageNo;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 数据总条数
     */
    private Long totalCount;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public PageDetailVO() {
    }

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @since 0.1.0
     */
    public PageDetailVO(Long pageNo, Long pageSize) {
        this(pageNo, pageSize, null);
    }

    /**
     * 构造器
     *
     * @param pageNo     页码
     * @param pageSize   每页数量
     * @param totalCount 数据总条数
     * @since 0.1.0
     */
    public PageDetailVO(Long pageNo, Long pageSize, Long totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
}
