package host.springboot.framework.cache.redis.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import host.springboot.framework3.core.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

/**
 * Redis 缓存 Key 序列化前缀类型枚举
 * <p>
 * 主要用于区分：
 * <ul>
 *     <li>原始 Key，不做额外前缀拼接；</li>
 *     <li>应用级 Key，会带上应用名前缀，方便多应用或多环境隔离。</li>
 * </ul>
 * 配置时可以根据业务需要选择合适的策略，避免不同系统之间的 Key 冲突
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@AllArgsConstructor
public enum RedisCacheKeySerializerTypeEnum implements BaseEnum<String> {

    /**
     * 原始 Key，不加任何应用前缀
     */
    ORIGIN("ORIGIN", "Origin Key"),

    /**
     * 应用级 Key，会在原始 Key 前拼接应用标识
     */
    APPLICATION("APPLICATION", "Application Key");

    /**
     * 枚举值
     */
    private final String value;

    /**
     * 枚举信息
     */
    private final String reasonPhrase;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 通过枚举值解析枚举
     *
     * @param value 枚举值
     * @return 枚举
     * @since 0.2.0
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static @Nullable RedisCacheKeySerializerTypeEnum parse(@Nullable String value) {
        return BaseEnum.parse(value, RedisCacheKeySerializerTypeEnum.class);
    }
}
