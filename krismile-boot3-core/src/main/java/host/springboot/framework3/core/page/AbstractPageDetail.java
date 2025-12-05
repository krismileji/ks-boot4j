package host.springboot.framework3.core.page;

import host.springboot.framework3.core.page.query.PageQuery;
import host.springboot.framework3.core.response.vo.PageDetailVO;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 分页参数抽象类, 定义了默认的分页参数
 *
 * <p>框架默认提供了实现类, 如不满足使用需要可以自行继承该类:</p>
 * <ul>
 *     <li><b>{@link ListPage}</b> - 默认分页对象, 其分页数据为 {@link java.util.List}</li>
 *     <li><b>{@link SetPage}</b> - 默认分页对象, 其分页数据为 {@link java.util.Set}</li>
 * </ul>
 *
 * @param <E> 集合元素类型
 * @param <C> 集合类型
 * @author JiYinchuan
 * @see Pageable
 * @see ListPage
 * @see SetPage
 * @since 0.1.0
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractPageDetail<E, C extends Collection<E>> implements Pageable<C>, Serializable {

    /**
     * 页码
     */
    private long pageNo;

    /**
     * 每页数量
     */
    private long pageSize;

    /**
     * 数据总条数
     */
    private Long totalCount;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    protected AbstractPageDetail() {
    }

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @since 0.1.0
     */
    protected AbstractPageDetail(long pageNo, long pageSize) {
        this();
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.adjustPageNo();
    }

    /**
     * 构造器
     *
     * @param pageNo     页码
     * @param pageSize   每页数量
     * @param totalCount 数据总条数
     * @since 0.1.0
     */
    protected AbstractPageDetail(long pageNo, long pageSize, Long totalCount) {
        this();
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        this.adjustPageNo();
    }

    /**
     * 构造器
     *
     * @param pageQuery 分页查询对象
     * @since 0.1.0
     */
    protected AbstractPageDetail(PageQuery pageQuery) {
        this();
        this.setPageNo(pageQuery.getPageNo());
        this.setPageSize(pageQuery.getPageSize());
        this.adjustPageNo();
    }

    /**
     * 构造器
     *
     * @param pageQuery  分页查询对象
     * @param totalCount 数据总条数
     * @since 0.1.0
     */
    protected AbstractPageDetail(PageQuery pageQuery, Long totalCount) {
        this();
        this.setPageNo(pageQuery.getPageNo());
        this.setPageSize(pageQuery.getPageSize());
        this.setTotalCount(totalCount);
        this.adjustPageNo();
    }

    /**
     * 构造器
     *
     * @param pageDetailVO 分页详情VO
     * @since 0.1.0
     */
    protected AbstractPageDetail(PageDetailVO pageDetailVO) {
        this();
        this.setPageNo(pageDetailVO.getPageNo());
        this.setPageSize(pageDetailVO.getPageSize());
        this.setTotalCount(pageDetailVO.getTotalCount());
        this.adjustPageNo();
    }

    @Override
    public Long getTotalCount() {
        return this.totalCount;
    }

    @Override
    public long getPageSize() {
        return this.pageSize;
    }

    @Override
    public long getPageNo() {
        return this.pageNo;
    }

    @Override
    public Long getTotalPage() {
        if (Objects.isNull(this.totalCount)) {
            return null;
        }
        long totalPage = this.totalCount / this.pageSize;
        if (totalPage == 0 || (this.totalCount % this.pageSize != 0)) {
            totalPage++;
        }
        return totalPage;
    }

    @Override
    public boolean isFirstPage() {
        return this.pageNo <= 1;
    }

    @Override
    public Boolean isLastPage() {
        if (Objects.isNull(this.totalCount)) {
            return null;
        }
        return this.pageNo >= this.getTotalPage();
    }

    @Override
    public long getNextPage() {
        if (this.isLastPage()) {
            return this.pageNo;
        } else {
            return this.pageNo + 1;
        }
    }

    @Override
    public long getPrePage() {
        if (this.isFirstPage()) {
            return this.pageNo;
        } else {
            return this.pageNo - 1;
        }
    }

    /**
     * 设置数据总条数
     *
     * @param totalCount 数据总条数
     * @return 当前实例
     * @since 0.1.0
     */
    public AbstractPageDetail<E, C> setTotalCount(long totalCount) {
        this.totalCount = Math.max(totalCount, 0L);
        this.adjustPageNo();
        return this;
    }

    /**
     * 设置每页数量
     *
     * @param pageSize 每页数量
     * @return 当前实例
     * @since 0.1.0
     */
    public AbstractPageDetail<E, C> setPageSize(long pageSize) {
        this.pageSize = Math.min(pageSize, DEFAULT_MAX_PAGE_SIZE);
        this.adjustPageNo();
        return this;
    }

    /**
     * 设置页码
     *
     * @param pageNo 分页页码
     * @return 当前实例
     * @since 0.1.0
     */
    public AbstractPageDetail<E, C> setPageNo(long pageNo) {
        this.pageNo = Math.max(pageNo, DEFAULT_PAGE_NO);
        this.adjustPageNo();
        return this;
    }

    /**
     * 调整页码，使不超过最大页数
     *
     * @since 0.1.0
     */
    private void adjustPageNo() {
        if (this.pageNo == 1) {
            return;
        }
        Long totalPage = this.getTotalPage();
        if (Objects.isNull(totalPage)) {
            return;
        }
        if (this.pageNo > totalPage) {
            this.pageNo = totalPage;
        }
    }
}
