package host.springboot.framework3.core.page;

import host.springboot.framework3.core.page.query.PageQuery;
import host.springboot.framework3.core.response.vo.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jspecify.annotations.NonNull;

import java.io.Serializable;
import java.util.Set;

/**
 * 默认分页数据为 {@link Set} 的实现
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
public class SetPage<T> extends AbstractPageDetail<T, Set<T>> implements Serializable {

    /**
     * 数据
     */
    private Set<T> records;

    // -------------------------------- Constructor-Default --------------------------------

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @since 0.1.0
     */
    public SetPage(long pageNo, long pageSize) {
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
    public SetPage(long pageNo, long pageSize, Set<T> records) {
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
    public SetPage(long pageNo, long pageSize, Long totalCount, Set<T> records) {
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
    public SetPage(@NonNull PageQuery pageQuery) {
        super(pageQuery);
    }

    /**
     * 构造器
     *
     * @param pageQuery 分页查询对象
     * @param records   分页数据
     * @since 0.1.0
     */
    public SetPage(@NonNull PageQuery pageQuery, Set<T> records) {
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
    public SetPage(@NonNull PageQuery pageQuery, Long totalCount, Set<T> records) {
        super(pageQuery, totalCount);
        this.setRecords(records);
    }

    // -------------------------------- Constructor-PageDetailVO --------------------------------

    /**
     * 构造器
     *
     * @param pageVO 分页详情 VO
     * @since 0.1.0
     */
    public SetPage(@NonNull PageVO pageVO) {
        super(pageVO);
    }

    /**
     * 构造器
     *
     * @param pageVO 分页详情 VO
     * @param records      分页数据
     * @since 0.1.0
     */
    public SetPage(@NonNull PageVO pageVO, Set<T> records) {
        super(pageVO);
        this.setRecords(records);
    }
}
