package host.springboot.framework.context.filter;

import host.springboot.framework.context.ExecuteOrder;
import host.springboot.framework.context.util.XssUtils;
import host.springboot.framework3.core.util.inner.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * XSS 攻击防御 WebFilter
 *
 * <p>通过 {@link ServerHttpRequestDecorator} 装饰器模式实现“无感清洗”，
 * 在获取查询参数和请求头时自动返回清洗后的值，开发者无需感知 XSS 清洗过程</p>
 *
 * <p><b>核心特性：</b></p>
 * <ul>
 *     <li>自动清洗查询参数的键和值</li>
 *     <li>自动清洗请求头的键和值</li>
 *     <li>支持配置排除参数后缀列表</li>
 *     <li>基于 Jsoup 的安全标签白名单机制</li>
 *     <li>懒加载优化，按需清洗</li>
 * </ul>
 *
 * <p><b>使用方式：</b></p>
 * <pre>{@code
 * // Controller 中无需任何特殊处理
 * @GetMapping("/api/test")
 * public Mono<String> test(@RequestParam String name) {
 *     // name 已自动完成 XSS 清洗
 *     return Mono.just("Hello " + name);
 * }
 * }</pre>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
public class XssFilter implements WebFilter, Ordered {

    /**
     * 日志记录器
     *
     * @since 0.2.0
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XssFilter.class);

    /**
     * 日志标签
     *
     * @since 0.2.0
     */
    private static final String LOG_TAG = "KS-Filter-XSS";

    /**
     * 过滤器执行顺序
     *
     * @since 0.2.0
     */
    public static final int EXECUTE_ORDER = ExecuteOrder.Filter.XSS_WRAPPER;

    /**
     * 过滤器执行条件断言
     *
     * @since 0.2.0
     */
    private final Predicate<ServerHttpRequest> executePredicate;

    /**
     * 需要排除清洗的参数名后缀列表（不可变）
     *
     * @since 0.2.0
     */
    private final List<String> excludeParamSuffixes;

    /**
     * Jsoup 安全标签白名单配置
     *
     * @since 0.2.0
     */
    private final Safelist safeList;

    /**
     * Jsoup 文档输出配置
     *
     * @since 0.2.0
     */
    private final Document.OutputSettings outputSettings;

    /**
     * 构造器
     *
     * @param config XSS 过滤器配置对象
     * @since 0.2.0
     */
    public XssFilter(@NonNull Config config) {
        this.executePredicate = config.getExecutePredicate();
        List<String> suffixes = config.getExcludeParamEndWith();
        this.excludeParamSuffixes = Objects.nonNull(suffixes) && !suffixes.isEmpty()
                ? List.copyOf(suffixes)
                : Collections.emptyList();
        this.safeList = config.getSafeList();
        this.outputSettings = config.getOutputSettings();
    }

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 判断是否需要执行 XSS 清洗
        if (executePredicate.test(request)) {
            try {
                // 创建装饰器，重写 getQueryParams() 方法
                ServerHttpRequest decoratedRequest = new XssRequestDecorator(
                        request,
                        excludeParamSuffixes,
                        safeList,
                        outputSettings
                );

                // 使用装饰后的请求替换原请求
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(decoratedRequest)
                        .build();

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("[{}] Request decorated for XSS cleaning", LOG_TAG);
                }

                return chain.filter(mutatedExchange);
            } catch (Exception e) {
                LOGGER.error("[{}] Failed to decorate request, proceeding with original", LOG_TAG, e);
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return EXECUTE_ORDER;
    }

    /**
     * XSS 请求装饰器
     *
     * <p>通过装饰模式重写 {@link #getQueryParams()} 和 {@link #getHeaders()} 方法，
     * 在获取参数和请求头时自动进行 XSS 清洗</p>
     *
     * <p><b>核心机制：</b></p>
     * <ul>
     *     <li>继承 {@link ServerHttpRequestDecorator} 实现装饰器模式</li>
     *     <li>重写 {@code getQueryParams()} 返回清洗后的查询参数</li>
     *     <li>重写 {@code getHeaders()} 返回清洗后的请求头</li>
     *     <li>懒加载机制，仅在首次访问时清洗</li>
     *     <li>支持按后缀排除指定参数</li>
     * </ul>
     */
    private static class XssRequestDecorator extends ServerHttpRequestDecorator {
    
        private final List<String> excludeParamSuffixes;
        private final Safelist safeList;
        private final Document.OutputSettings outputSettings;
    
        /**
         * 缓存清洗后的查询参数（懒加载）
         */
        private MultiValueMap<String, String> cachedCleanedParams;
    
        /**
         * 缓存清洗后的请求头（懒加载）
         */
        private HttpHeaders cachedCleanedHeaders;

        /**
         * 构造器
         *
         * @param delegate 被装饰的 ServerHttpRequest 对象
         * @param excludeParamSuffixes 排除清洗的参数名后缀列表
         * @param safeList Jsoup 安全标签白名单
         * @param outputSettings Jsoup 文档输出配置
         */
        public XssRequestDecorator(
                @NonNull ServerHttpRequest delegate,
                @NonNull List<String> excludeParamSuffixes,
                @NonNull Safelist safeList,
                Document.@NonNull OutputSettings outputSettings) {
            super(delegate);
            this.excludeParamSuffixes = excludeParamSuffixes;
            this.safeList = safeList;
            this.outputSettings = outputSettings;
        }

        /**
         * 重写此方法，返回清洗后的查询参数
         *
         * <p>Spring WebFlux 的参数绑定机制会调用此方法获取查询参数，
         * 因此 {@code @RequestParam} 注解会自动获取到清洗后的值</p>
         */
        @Override
        public @NonNull MultiValueMap<String, String> getQueryParams() {
            // 懒加载，只在第一次调用时清洗
            if (Objects.isNull(cachedCleanedParams)) {
                cachedCleanedParams = this.cleanQueryParams(super.getQueryParams());
            }
            return cachedCleanedParams;
        }

        /**
         * 重写此方法，返回清洗后的请求头
         *
         * <p>Spring WebFlux 的请求头获取会调用此方法，实现请求头的自动清洗</p>
         */
        @Override
        public @NonNull HttpHeaders getHeaders() {
            // 懒加载，只在第一次调用时清洗
            if (Objects.isNull(cachedCleanedHeaders)) {
                cachedCleanedHeaders = this.cleanHeaders(super.getHeaders());
            }
            return cachedCleanedHeaders;
        }

    /**
     * 清洗查询参数
     *
     * @param originalParams 原始参数
     * @return 清洗后的参数
     * @since 0.2.0
     */
        private @NonNull MultiValueMap<String, String> cleanQueryParams(
                @NonNull MultiValueMap<String, String> originalParams) {
            if (originalParams.isEmpty()) {
                return new LinkedMultiValueMap<>(0);
            }

            MultiValueMap<String, String> cleanedParams = new LinkedMultiValueMap<>(originalParams.size());

            originalParams.forEach((paramName, paramValues) -> {
                if (shouldExcludeParam(paramName)) {
                    cleanedParams.put(paramName, paramValues);
                } else {
                    String cleanedParamName = cleanParam(paramName);
                    List<String> cleanedValues = cleanParamValues(paramValues);
                    cleanedParams.put(cleanedParamName, cleanedValues);
                }
            });

            return cleanedParams;
        }

        /**
         * 清洗请求头
         *
         * @param originalHeaders 原始请求头
         * @return 清洗后的请求头
         * @since 0.2.0
         */
        private @NonNull HttpHeaders cleanHeaders(@NonNull HttpHeaders originalHeaders) {
            if (originalHeaders.isEmpty()) {
                return HttpHeaders.EMPTY;
            }

            HttpHeaders cleanedHeaders = new HttpHeaders();

            originalHeaders.forEach((headerName, headerValues) -> {
                String cleanedHeaderName = this.cleanParam(headerName);
                List<String> cleanedValues = this.cleanParamValues(headerValues);
                cleanedHeaders.put(cleanedHeaderName, cleanedValues);
            });

            return HttpHeaders.readOnlyHttpHeaders(cleanedHeaders);
        }

        /**
         * 判断参数名是否需要排除清洗
         *
         * @param paramName 参数名
         * @return {@code true} 如果参数名在排除列表中
         * @since 0.2.0
         */
        private boolean shouldExcludeParam(@NonNull String paramName) {
            if (excludeParamSuffixes.isEmpty()) {
                return false;
            }
            for (String suffix : excludeParamSuffixes) {
                if (paramName.endsWith(suffix)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 清洗单个参数名或请求头名
         *
         * @param param 参数名或请求头名
         * @return 清洗后的参数名
         * @since 0.2.0
         */
        private @NonNull String cleanParam(@Nullable String param) {
            if (Objects.isNull(param)) {
                return "";
            }
            String cleaned = XssUtils.clean(param, safeList, outputSettings);
            return Objects.nonNull(cleaned) ? cleaned : "";
        }

        /**
         * 清洗参数值列表
         *
         * @param values 参数值列表
         * @return 清洗后的参数值列表
         * @since 0.2.0
         */
        private @NonNull List<String> cleanParamValues(@Nullable List<String> values) {
            if (Objects.isNull(values) || values.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> cleanedValues = new ArrayList<>(values.size());
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    String cleaned = XssUtils.clean(value, safeList, outputSettings);
                    cleanedValues.add(Objects.nonNull(cleaned) ? cleaned : "");
                } else {
                    cleanedValues.add(value);
                }
            }
            return cleanedValues;
        }
    }

    /**
     * XSS 攻击防御过滤器配置类
     *
     * <p>用于配置 XSS 过滤器的执行条件、排除参数、安全白名单等</p>
     *
     * @author JiYinchuan
     * @since 0.2.0
     */
    @Data
    @Accessors(chain = true)
    public static class Config {

        /**
         * 过滤器执行条件断言（默认对所有请求生效）
         *
         * @since 0.2.0
         */
        private Predicate<ServerHttpRequest> executePredicate = request -> true;

        /**
         * 需要排除清洗的参数名后缀列表
         *
         * <p>示例：配置 {@code "Html"} 后缀，则 {@code contentHtml} 参数不会被清洗。</p>
         *
         * @since 0.2.0
         */
        private List<String> excludeParamEndWith = new ArrayList<>();

        /**
         * Jsoup安全标签白名单（默认不允许任何HTML标签）
         *
         * <p>可使用 {@link Safelist#basic()}、{@link Safelist#relaxed()} 等预设配置</p>
         *
         * @since 0.2.0
         */
        private Safelist safeList = Safelist.none();

        /**
         * Jsoup文档输出配置（默认禁用格式化输出）
         *
         * @since 0.2.0
         */
        private Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

        /**
         * 无参构造器
         *
         * @since 0.2.0
         */
        public Config() {
        }
    }
}
