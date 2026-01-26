package host.springboot.framework3.core.response.vo;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 分页详情 VO
 *
 * @author JiYinchuan
 * @see PageVO
 * @since 0.1.0
 */
@Data
@FieldNameConstants
public class PageVO implements Serializable {

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
    public PageVO() {
    }

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @since 0.1.0
     */
    public PageVO(Long pageNo, Long pageSize) {
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
    public PageVO(Long pageNo, Long pageSize, Long totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
}
