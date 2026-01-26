package host.springboot.framework3.core.page.query;

import host.springboot.framework3.core.page.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * <p>该类为分页查询基类, 所有带分页参数的请求实体继承此类, 此类也可以单独进行使用</p>
 * <p>已默认提供一个基础分页查询实体 {@link SimplePageQuery}</p>
 *
 * @author JiYinchuan
 * @see Pageable
 * @since 0.1.0
 */
@Data
@Accessors(chain = true)
@Schema(description = "分页查询基类")
public class PageQuery implements Serializable {

    /**
     * 页码
     */
    @Schema(description = "页码", defaultValue = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "页码不能为空")
    private Long pageNo;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量", defaultValue = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "每页数量不能为空")
    private Long pageSize;

    /**
     * 是否查询总数量
     */
    @Schema(description = "是否查询总数量", defaultValue = "true")
    private boolean queryTotalCount = true;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public PageQuery() {
    }

    /**
     * 构造器
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @since 0.1.0
     */
    public PageQuery(Long pageNo, Long pageSize) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }
}
