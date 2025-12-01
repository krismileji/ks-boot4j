package host.springboot.framework3.core.page.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 简单分页查询
 *
 * <p>该类为简单分页查询实体, 包含了一个默认 {@code keyword} 字段用于查询参数
 *
 * @author JiYinchuan
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SimplePageQuery extends PageQuery {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public SimplePageQuery() {
    }
}
