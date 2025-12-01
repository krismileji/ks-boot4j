package host.springboot.framework3.core.page;

import host.springboot.framework3.core.page.query.PageQuery;
import host.springboot.framework3.core.response.vo.PageDetailVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jspecify.annotations.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * 默认分页数据为 {@link List} 的实现
 *
 * @param <T> 分页数据类型
 * @author JiYinchuan
 * @see AbstractPageDetail
 * @see Pageable
 * @since 0.1.0
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListPage<T> extends AbstractPageDetail<T, List<T>> implements Serializable {

    /**
     * 数据
     */
    private List<T> records;

    // -------------------------------- Constructor-Default --------------------------------

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @since 0.1.0
     */
    public ListPage(long pageNo, long pageSize) {
        super(pageNo, pageSize);
    }

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param records  分页数据
     * @since 0.1.0
     */
    public ListPage(long pageNo, long pageSize, List<T> records) {
        super(pageNo, pageSize);
        this.setRecords(records);
    }

    /**
     * 构造器
     *
     * @param pageNo     页码
     * @param pageSize   每页数量
     * @param totalCount 数据总条数
     * @param records    分页数据
     * @since 0.1.0
     */
    public ListPage(long pageNo, long pageSize, Long totalCount, List<T> records) {
        super(pageNo, pageSize, totalCount);
        this.setRecords(records);
    }

    // -------------------------------- Constructor-PageQuery --------------------------------

    /**
     * 构造器
     *
     * @param pageQuery 分页查询对象
     * @since 0.1.0
     */
    public ListPage(@NonNull PageQuery pageQuery) {
        super(pageQuery);
    }

    /**
     * 构造器
     *
     * @param pageQuery 分页查询对象
     * @param records   分页数据
     * @since 0.1.0
     */
    public ListPage(@NonNull PageQuery pageQuery, List<T> records) {
        super(pageQuery);
        this.setRecords(records);
    }

    /**
     * 构造器
     *
     * @param pageQuery  分页查询对象
     * @param totalCount 数据总条数
     * @param records    分页数据
     * @since 0.1.0
     */
    public ListPage(@NonNull PageQuery pageQuery, Long totalCount, List<T> records) {
        super(pageQuery, totalCount);
        this.setRecords(records);
    }

    // -------------------------------- Constructor-PageDetailVO --------------------------------

    /**
     * 构造器
     *
     * @param pageDetailVO 分页详情VO
     * @since 0.1.0
     */
    public ListPage(@NonNull PageDetailVO pageDetailVO) {
        super(pageDetailVO);
    }

    /**
     * 构造器
     *
     * @param pageDetailVO 分页详情VO
     * @param records      分页数据
     * @since 0.1.0
     */
    public ListPage(@NonNull PageDetailVO pageDetailVO, List<T> records) {
        super(pageDetailVO);
        this.setRecords(records);
    }
}
