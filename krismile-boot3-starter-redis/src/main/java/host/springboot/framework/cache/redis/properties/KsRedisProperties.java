package host.springboot.framework.cache.redis.properties;

import host.springboot.framework.cache.redis.enumeration.RedisCacheKeySerializerTypeEnum;
import host.springboot.framework3.core.constant.KrismileConstant;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Ks-boot4j Redis 相关配置属性
 * <p>
 * 主要用于集中管理 Redis 自动配置开关以及缓存 Key 的序列化前缀策略，
 * 便于在配置文件中按需开启或调整，不需要在代码里写死常量
 * </p>
 *
 * <p>配置示例：</p>
 * <pre>{@code
 * # -------------------------------- Redis 配置 --------------------------------
 * ks:
 *   redis:
 *     # 是否启用 Redis 自动配置，默认为 [true]
 *     enabled: true
 *     # Redis 缓存 Key 序列化类型，默认为 [ORIGIN]
 *     key-serializer-type: ORIGIN
 * }</pre>
 *
 * @author JiYinchuan
 * @see host.springboot.framework.cache.redis.enumeration.RedisCacheKeySerializerTypeEnum
 * @since 0.2.0
 */
@Data
@FieldNameConstants
@ConfigurationProperties(prefix = KsRedisProperties.KEY)
public class KsRedisProperties {

    /**
     * 配置文件前缀
     *
     * @since 0.2.0
     */
    public static final String KEY = KrismileConstant.KRISMILE_LOWERCASE + "." + "web";

    /**
     * 是否启用 Redis 自动配置
     *
     * @since 0.2.0
     */
    private Boolean enabled = true;

    /**
     * Redis 缓存 Key 序列化类型
     *
     * @since 0.2.0
     */
    private RedisCacheKeySerializerTypeEnum keySerializerType = RedisCacheKeySerializerTypeEnum.ORIGIN;

    /**
     * 构造方法，保留显式构造便于后续扩展默认值逻辑
     *
     * @since 0.2.0
     */
    public KsRedisProperties() {
    }
}
