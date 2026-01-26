package host.springboot.framework.cache.redis;

import host.springboot.framework.cache.redis.enumeration.RedisCacheKeySerializerTypeEnum;
import host.springboot.framework.cache.redis.properties.KsRedisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import reactor.core.publisher.Flux;

/**
 * Ks-boot4j Redis 响应式缓存自动配置
 * <p>
 * 基于 Spring WebFlux 和 Reactor 提供响应式 Redis 操作支持。
 * 在 {@link KsRedisAutoConfiguration} 之后加载，复用其序列化器配置，
 * 自动创建 {@link ReactiveRedisTemplate} 用于异步非阻塞的 Redis 操作。
 * </p>
 * <ul>
 *     <li>支持响应式编程模型（基于 Reactor）</li>
 *     <li>统一创建可复用的 {@link ReactiveRedisTemplate}</li>
 *     <li>复用同步模式下的序列化器配置</li>
 *     <li>支持原始 Key 与带应用名前缀的 Key 两种模式</li>
 * </ul>
 * <p>
 * 仅在 WebFlux 环境下生效，需要引入 {@code spring-boot-starter-data-redis-reactive} 依赖
 * </p>
 *
 * @author JiYinchuan
 * @see ReactiveRedisTemplate
 * @see KsRedisAutoConfiguration
 * @see KsRedisConfiguration
 * @since 0.2.0
 */
@AutoConfiguration(after = KsRedisAutoConfiguration.class)
@ConditionalOnClass({ReactiveRedisConnectionFactory.class, ReactiveRedisTemplate.class, Flux.class})
public class KsRedisReactiveAutoConfiguration {

    /**
     * 构造方法，保留显式构造便于后续扩展初始化逻辑
     *
     * @since 0.2.0
     */
    public KsRedisReactiveAutoConfiguration() {
    }

    /**
     * 创建 ReactiveRedisTemplate
     * <p>
     * 根据配置项 {@code krismile.redis.key-serializer-type} 选择合适的 Key / Value 序列化器：
     * </p>
     * <ul>
     *     <li>{@code ORIGIN}：使用普通字符串 Key</li>
     *     <li>{@code APPLICATION}：使用带应用名前缀的 Key</li>
     * </ul>
     *
     * @param reactiveRedisConnectionFactory 响应式 Redis 连接工厂
     * @param ksRedisProperties              Redis 相关配置属性
     * @param originKeySerializer            普通 Key 序列化器
     * @param applicationKeySerializer       应用级 Key 序列化器
     * @param originValueSerializer          普通 Value 序列化器
     * @return 配置完成的 ReactiveRedisTemplate
     * @since 0.2.0
     */
    @Bean
    @ConditionalOnMissingBean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
            KsRedisProperties ksRedisProperties,
            @Qualifier(KsRedisConfiguration.REDIS_KEY_SERIALIZER_BEAN_NAME) RedisSerializer<String> originKeySerializer,
            @Qualifier(KsRedisConfiguration.REDIS_APPLICATION_KEY_SERIALIZER_BEAN_NAME) RedisSerializer<String> applicationKeySerializer,
            @Qualifier(KsRedisConfiguration.REDIS_VALUE_SERIALIZER_BEAN_NAME) RedisSerializer<Object> originValueSerializer) {
        boolean isApplicationMode = RedisCacheKeySerializerTypeEnum.APPLICATION
                == ksRedisProperties.getKeySerializerType();

        RedisSerializer<String> keySerializer = isApplicationMode
                ? applicationKeySerializer
                : originKeySerializer;

        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
                .<String, Object>newSerializationContext()
                .key(keySerializer)
                .value(originValueSerializer)
                .hashKey(keySerializer)
                .hashValue(originValueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, serializationContext);
    }
}
