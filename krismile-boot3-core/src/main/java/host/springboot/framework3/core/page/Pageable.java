package host.springboot.framework3.core.page;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import host.springboot.framework3.core.response.vo.PageDetailVO;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

/**
 * 标准分页接口
 *
 * <p>该接口为业务标准分页接口, 已默认提供实现类如下:</p>
 * <ul>
 *     <li><b>{@link AbstractPageDetail}</b> - 分页参数抽象类, 定义了分页相关的参数</li>
 *     <li><b>{@link ListPage}</b> - 最终实现对象, 继承至 {@link AbstractPageDetail}, 其分页数据为 {@link java.util.List}</li>
 *     <li><b>{@link SetPage}</b> - 最终实现对象, 继承至 {@link AbstractPageDetail}, 其分页数据为 {@link java.util.Set}</li>
 * </ul>
 *
 * @param <T> 分页数据类型
 * @author JiYinchuan
 * @see AbstractPageDetail
 * @see ListPage
 * @see SetPage
 * @since 0.1.0
 */
public interface Pageable<T extends Collection<?>> {

    /**
     * 默认页码
     */
    long DEFAULT_PAGE_NO = 1;

    /**
     * 默认每页数量
     */
    long DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认最大每页数量
     */
    long DEFAULT_MAX_PAGE_SIZE = 1000;

    /**
     * 默认数据总条数
     */
    long DEFAULT_TOTAL_COUNT = 0;

    /**
     * 页码
     *
     * @return 页码
     * @since 0.1.0
     */
    long getPageNo();

    /**
     * 每页记录数
     *
     * @return 每页记录数
     * @since 0.1.0
     */
    long getPageSize();

    /**
     * 总记录数
     *
     * @return 总记录数
     * @since 0.1.0
     */
    Long getTotalCount();

    /**
     * 总页数
     *
     * @return 总页数
     * @since 0.1.0
     */
    Long getTotalPage();

    /**
     * 是否第一页
     *
     * @return 是否第一页
     * @since 0.1.0
     */
    boolean isFirstPage();

    /**
     * 是否最后一页
     *
     * @return 是否最后一页
     * @since 0.1.0
     */
    Boolean isLastPage();

    /**
     * 返回下页的页号
     *
     * @return 返回下页的页号
     * @since 0.1.0
     */
    long getNextPage();

    /**
     * 返回上页的页号
     *
     * @return 返回上页的页号
     * @since 0.1.0
     */
    long getPrePage();

    /**
     * 分页数据
     *
     * @return 分页数据
     * @since 0.1.0
     */
    T getRecords();

    /**
     * 返回数据库查询起始索引位置
     *
     * @return 数据库查询起始索引位置
     * @since 0.1.0
     */
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    default long getOffset() {
        return this.getPageNo() > 0 ? (this.getPageNo() - 1) * this.getPageSize() : 0;
    }

    /**
     * 获取分页详情VO
     *
     * @return 分页详情VO
     * @since 0.1.0
     */
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    default @NonNull PageDetailVO convertPageDetailVO() {
        return new PageDetailVO(this.getPageNo(), this.getPageSize(), this.getTotalCount());
    }
}
