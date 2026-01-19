package host.springboot.framework.cache.redis;

import host.springboot.framework.cache.redis.enumeration.RedisCacheKeySerializerTypeEnum;
import host.springboot.framework.cache.redis.properties.KsRedisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Ks-boot4j Redis 缓存自动配置
 * <p>
 * 在 Spring Boot 默认 Redis 自动配置的基础上，补充了一些常用约定：
 * </p>
 * <ul>
 *     <li>统一创建可复用的 {@link RedisTemplate}</li>
 *     <li>提供字符串、JSON 及带类型信息的序列化器 Bean</li>
 *     <li>支持原始 Key 与带应用名前缀的 Key 两种模式</li>
 *     <li>通过配置开关控制是否启用 Redis 自动配置</li>
 * </ul>
 * 配置细节可以在 {@link KsRedisProperties} 中调整，方便按环境或项目需求做差异化设置
 *
 * @author JiYinchuan
 * @see RedisTemplate
 * @see RedisSerializer
 * @see KsRedisProperties
 * @see KsRedisConfiguration
 * @since 0.2.0
 */
@AutoConfiguration(before = RedisAutoConfiguration.class)
@ConditionalOnClass(RedisConnectionFactory.class)
@EnableCaching
@EnableConfigurationProperties({CacheProperties.class, KsRedisProperties.class})
@ConditionalOnProperty(
        prefix = KsRedisProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@Import(KsRedisConfiguration.class)
public class KsRedisAutoConfiguration {

    /**
     * 构造方法，保留显式构造便于后续扩展初始化逻辑
     *
     * @since 0.2.0
     */
    public KsRedisAutoConfiguration() {
    }

    /**
     * 创建 RedisTemplate
     * <p>
     * 会根据配置项 {@code krismile.redis.key-serializer-type} 选择合适的 Key / Value 序列化器：
     * </p>
     * <ul>
     *     <li>{@code ORIGIN}：使用普通字符串 Key 与普通 JSON Value</li>
     *     <li>{@code APPLICATION}：使用带应用名前缀的 Key，与包含类型信息的 JSON Value</li>
     * </ul>
     *
     * @param redisConnectionFactory   Redis 连接工厂
     * @param ksRedisProperties        Redis 相关配置属性
     * @param originKeySerializer      普通 Key 序列化器
     * @param applicationKeySerializer 应用级 Key 序列化器
     * @param originValueSerializer    普通 Value 序列化器
     * @param classValueSerializer     带类型信息的 Value 序列化器
     * @return 配置完成的 RedisTemplate
     * @since 0.2.0
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory,
            KsRedisProperties ksRedisProperties,
            @Qualifier(KsRedisConfiguration.REDIS_KEY_SERIALIZER_BEAN_NAME) RedisSerializer<String> originKeySerializer,
            @Qualifier(KsRedisConfiguration.REDIS_APPLICATION_KEY_SERIALIZER_BEAN_NAME) RedisSerializer<String> applicationKeySerializer,
            @Qualifier(KsRedisConfiguration.REDIS_VALUE_SERIALIZER_BEAN_NAME) RedisSerializer<Object> originValueSerializer,
            @Qualifier(KsRedisConfiguration.REDIS_WITH_CLASS_VALUE_SERIALIZER_BEAN_NAME) RedisSerializer<Object> classValueSerializer) {
        boolean isApplicationMode = RedisCacheKeySerializerTypeEnum.APPLICATION
                == ksRedisProperties.getKeySerializerType();

        RedisSerializer<String> keySerializer = isApplicationMode
                ? applicationKeySerializer
                : originKeySerializer;

        RedisSerializer<Object> valueSerializer = isApplicationMode
                ? classValueSerializer
                : originValueSerializer;

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }
}