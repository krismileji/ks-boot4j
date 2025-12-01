package host.springboot.framework.mybatisplus.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础DO
 *
 * @param <ID> ID类型
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
public abstract class BaseDO<ID> implements Serializable {

    /**
     * ID字段名称
     */
    public static final String FIELD_ID = "id";

    /**
     * 创建时间字段名称
     */
    public static final String FIELD_CREATE_TIME = "createTime";

    /**
     * 创建人字段名称
     */
    public static final String FIELD_CREATE_USER = "createUser";

    /**
     * 最后修改时间字段名称
     */
    public static final String FIELD_UPDATE_TIME = "updateTime";

    /**
     * 最后修改人字段名称
     */
    public static final String FIELD_UPDATE_USER = "updateUser";

    /**
     * 逻辑删除字段名称
     */
    public static final String FIELD_LOGIC_DELETE = "logicDelete";

    /**
     * 版本字段名称
     */
    public static final String FIELD_VERSION = "version";

    /**
     * 设置ID
     *
     * @param id ID
     * @since 0.1.0
     */
    public abstract void setId(ID id);

    /**
     * 获取ID
     *
     * @return ID
     * @since 0.1.0
     */
    public abstract ID getId();

    /**
     * 创建时间
     *
     * <p>新增时将自动填充当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT, insertStrategy = FieldStrategy.NOT_NULL)
    public LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    public String createUser;

    /**
     * 最后修改时间
     *
     * <p>新增与修改时自动填充当前时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE,
            insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    public LocalDateTime updateTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    public String updateUser;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public BaseDO() {
    }
}
