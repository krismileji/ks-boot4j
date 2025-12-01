package host.springboot.framework.mybatisplus.model.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import host.springboot.framework3.core.page.ListPage;
import host.springboot.framework3.core.page.Pageable;
import host.springboot.framework3.core.page.query.PageQuery;
import lombok.experimental.Accessors;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * 数据库分页
 *
 * @param <T> 分页数据类型
 * @author JiYinchuan
 * @since 0.1.0
 */
@Accessors(chain = true)
public class DaoPage<T> extends Page<T> {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public DaoPage() {
    }

    /**
     * 构造器
     *
     * @param pageQuery 分页查询参数
     * @since 0.1.0
     */
    public DaoPage(@NonNull PageQuery pageQuery) {
        super(pageQuery.getPageNo(), pageQuery.getPageSize(), pageQuery.isQueryTotalCount());
    }

    /**
     * 构造器
     *
     * @param pageNo   当前页
     * @param PageSize 每页数量
     * @since 0.1.0
     */
    public DaoPage(long pageNo, long PageSize) {
        super(pageNo, PageSize, 0);
    }

    /**
     * 构造器
     *
     * @param pageNo     当前页
     * @param PageSize   每页数量
     * @param totalCount 总数
     * @since 0.1.0
     */
    public DaoPage(long pageNo, long PageSize, long totalCount) {
        super(pageNo, PageSize, totalCount, true);
    }

    /**
     * 构造器
     *
     * @param pageNo          当前页
     * @param PageSize        每页数量
     * @param queryTotalCount 是否查询总数
     * @since 0.1.0
     */
    public DaoPage(long pageNo, long PageSize, boolean queryTotalCount) {
        super(pageNo, PageSize, 0, queryTotalCount);
    }

    /**
     * 构造器
     *
     * @param pageNo          当前页
     * @param PageSize        每页数量
     * @param totalCount      总数
     * @param queryTotalCount 是否查询总数
     * @since 0.1.0
     */
    public DaoPage(long pageNo, long PageSize, long totalCount, boolean queryTotalCount) {
        super(pageNo, PageSize, totalCount, queryTotalCount);
    }

    /**
     * 转换为Pageable
     *
     * @return Pageable
     * @since 0.1.0
     */
    public Pageable<List<T>> toPageable() {
        return this.searchCount()
                ? new ListPage<>(getCurrent(), getSize(), getTotal(), getRecords())
                : new ListPage<>(getCurrent(), getSize(), getRecords());
    }
}
