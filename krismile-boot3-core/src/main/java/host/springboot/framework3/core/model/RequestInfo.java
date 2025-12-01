package host.springboot.framework3.core.model;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.jspecify.annotations.NonNull;

import java.io.Serializable;

/**
 * 请求结果数据信息封装
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
@Accessors(chain = true)
@FieldNameConstants
public class RequestInfo implements Serializable {

    /**
     * 请求数据上下文
     */
    public static final TransmittableThreadLocal<RequestInfo> REQUEST_CONTEXT = new TransmittableThreadLocal<>();

    /**
     * 线程ID
     */
    private @NonNull String threadId;

    /**
     * 线程名称
     */
    private @NonNull String threadName;

    /**
     * 请求URI
     */
    private @NonNull String uri;

    /**
     * 客户端真实请求IP地址
     */
    private @NonNull String clientIp;

    /**
     * User-Agent
     */
    private @NonNull String userAgent;

    /**
     * 操作系统
     */
    private @NonNull String os;

    /**
     * 浏览器
     */
    private @NonNull String browser;

    /**
     * 请求方法类型
     */
    private @NonNull String methodType;

    /**
     * 完整请求方法
     */
    private String fullMethodName;

    /**
     * 请求方法中的参数
     */
    private Object[] requestMethodArgs;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public RequestInfo() {
    }
}
