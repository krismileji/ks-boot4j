package host.springboot.framework.cache.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import host.springboot.framework.cache.redis.enumeration.RedisCacheKeySerializerTypeEnum;
import host.springboot.framework.cache.redis.properties.KsRedisProperties;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.util.Objects;

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
 * @see RedisApplicationKeySerializer
 * @since 0.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@ConditionalOnBean(RedisConnectionFactory.class)
@EnableCaching
@EnableConfigurationProperties({CacheProperties.class, KsRedisProperties.class})
@ConditionalOnProperty(
        prefix = KsRedisProperties.KEY,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class KsRedisAutoConfiguration {

    /**
     * Redis Key 序列化器 Bean 名称
     *
     * @since 0.2.0
     */
    public static final String REDIS_KEY_SERIALIZER_BEAN_NAME = "redisSerializer";

    /**
     * Redis 应用级 Key 序列化器 Bean 名称
     *
     * @since 0.2.0
     */
    public static final String REDIS_APPLICATION_KEY_SERIALIZER_BEAN_NAME = "applicationKeyRedisSerializer";

    /**
     * Redis Value 序列化器 Bean 名称
     *
     * @since 0.2.0
     */
    public static final String REDIS_VALUE_SERIALIZER_BEAN_NAME = "redisValueSerializer";

    /**
     * Redis 带类型信息的 Value 序列化器 Bean 名称
     *
     * @since 0.2.0
     */
    public static final String REDIS_WITH_CLASS_VALUE_SERIALIZER_BEAN_NAME = "redisWithClassValueSerializer";

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
            @Qualifier(REDIS_KEY_SERIALIZER_BEAN_NAME) RedisSerializer<String> originKeySerializer,
            @Qualifier(REDIS_APPLICATION_KEY_SERIALIZER_BEAN_NAME) RedisSerializer<String> applicationKeySerializer,
            @Qualifier(REDIS_VALUE_SERIALIZER_BEAN_NAME) RedisSerializer<Object> originValueSerializer,
            @Qualifier(REDIS_WITH_CLASS_VALUE_SERIALIZER_BEAN_NAME) RedisSerializer<Object> classValueSerializer) {
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

    /**
     * 获取 Redis Key 序列化器
     * <p>
     * 默认使用字符串序列化器，适合大多数场景的 Key 序列化
     * </p>
     *
     * @return Redis Key 序列化器
     * @since 0.2.0
     */
    @Bean(REDIS_KEY_SERIALIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_KEY_SERIALIZER_BEAN_NAME)
    public RedisSerializer<String> redisKeySerializer() {
        return RedisSerializer.string();
    }

    /**
     * 获取带有应用名称前缀的 Redis Key 序列化器
     * <p>
     * 会自动在 Key 前添加应用名称前缀，格式为：{@code applicationName::key}，
     * 便于区分不同应用或环境下的缓存数据
     * </p>
     *
     * @param applicationName 应用名称，从 {@code spring.application.name} 配置项获取
     * @return 带有应用名称前缀的 Redis Key 序列化器
     * @since 0.2.0
     */
    @Bean(REDIS_APPLICATION_KEY_SERIALIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_APPLICATION_KEY_SERIALIZER_BEAN_NAME)
    public RedisSerializer<String> applicationKeyRedisSerializer(
            @Value("${spring.application.name:}") String applicationName) {
        return new RedisApplicationKeySerializer(applicationName);
    }

    /**
     * 获取 Redis Value 序列化器
     * <p>
     * 使用 Jackson JSON 序列化器，不包含类型信息，适合结构比较简单、类型固定的场景
     * </p>
     *
     * @param objectMappers ObjectMapper 提供者
     * @return Redis Value 序列化器
     * @since 0.2.0
     */
    @Bean(REDIS_VALUE_SERIALIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_VALUE_SERIALIZER_BEAN_NAME)
    public RedisSerializer<Object> redisValueSerializer(ObjectProvider<ObjectMapper> objectMappers) {
        return new GenericJackson2JsonRedisSerializer(objectMappers.getIfAvailable(ObjectMapper::new).copy());
    }

    /**
     * 获取带有类型信息的 Redis Value 序列化器
     * <p>
     * 使用 Jackson JSON 序列化器，在序列化结果中写入类型信息，
     * 适合需要按原始类型反序列化的复杂对象场景
     * </p>
     *
     * @param objectMappers ObjectMapper 提供者
     * @return 带有类型信息的 Redis Value 序列化器
     * @since 0.2.0
     */
    @Bean(REDIS_WITH_CLASS_VALUE_SERIALIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_WITH_CLASS_VALUE_SERIALIZER_BEAN_NAME)
    public static RedisSerializer<Object> redisWithClassValueSerializer(ObjectProvider<ObjectMapper> objectMappers) {
        ObjectMapper objectMapper = objectMappers.getIfAvailable(ObjectMapper::new);
        return new GenericJackson2JsonRedisSerializer(objectMapper.copy()
                .activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
        );
    }

    /**
     * 带有应用名称前缀的 Redis Key 序列化器
     * <p>
     * 继承自 {@link StringRedisSerializer}，在序列化时自动添加应用名前缀，
     * Key 格式为：{@code applicationName::originalKey}
     * </p>
     *
     * @author JiYinchuan
     * @since 0.2.0
     */
    public static class RedisApplicationKeySerializer extends StringRedisSerializer {

        /**
         * 应用名称
         */
        private final @Nullable String applicationName;

        /**
         * 构造方法
         *
         * @param applicationName 应用名称
         * @since 0.2.0
         */
        public RedisApplicationKeySerializer(@Nullable String applicationName) {
            this.applicationName = applicationName;
        }

        /**
         * 构造方法
         *
         * @param charset         字符集
         * @param applicationName 应用名称
         * @since 0.2.0
         */
        public RedisApplicationKeySerializer(@NonNull Charset charset, @Nullable String applicationName) {
            super(charset);
            this.applicationName = applicationName;
        }

        /**
         * 获取带应用名称前缀的完整 Key
         * <p>
         * 如果 key 为 {@code null} 或应用名称为空，则直接返回原始 key；
         * 如果 key 已包含应用名前缀，则不再重复追加；
         * 否则按 {@code applicationName::key} 的格式拼接前缀
         * </p>
         *
         * @param key 原始 Key
         * @return 完整 Key（带应用名称前缀）
         * @since 0.2.0
         */
        private String getFullKey(String key) {
            if (Objects.isNull(key)) {
                return null;
            }
            if (Objects.isNull(applicationName)) {
                return key;
            }
            String prefix = applicationName.toLowerCase();
            return key.startsWith(prefix) ? key : (prefix + CacheKeyPrefix.SEPARATOR + key);
        }
    }
}