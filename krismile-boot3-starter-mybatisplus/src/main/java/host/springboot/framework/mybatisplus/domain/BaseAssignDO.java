package host.springboot.framework.mybatisplus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基础Long类型雪花ID DO
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseAssignDO extends BaseDO<Long> implements Serializable {

    /**
     * 唯一ID
     *
     * <p>默认使用雪花ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public BaseAssignDO() {
    }
}
