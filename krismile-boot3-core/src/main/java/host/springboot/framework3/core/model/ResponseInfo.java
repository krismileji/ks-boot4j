package host.springboot.framework3.core.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;

/**
 * 请求响应信息封装
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@Accessors(chain = true)
public class ResponseInfo implements Serializable {

    /**
     * 响应结果
     */
    private @Nullable Object result;

    /**
     * 请求执行时间
     */
    private long executionTime;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public ResponseInfo() {
    }
}
