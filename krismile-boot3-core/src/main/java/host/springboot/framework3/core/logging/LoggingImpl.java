package host.springboot.framework3.core.logging;

import host.springboot.framework3.core.model.Pair;
import host.springboot.framework3.core.util.inner.ArrayUtils;
import host.springboot.framework3.core.util.inner.CollectionUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 日志实现
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public sealed class LoggingImpl extends BaseLogging implements LoggingProvider permits LoggingRpcExecutor {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public LoggingImpl() {
    }

    /**
     * 参数数组类
     *
     * @author JiYinchuan
     * @since 0.1.0
     */
    protected static class ArgArray {

        /**
         * 原始信息
         */
        private final List<Pair<String, Object>> originalMessages = new ArrayList<>();

        /**
         * 附加信息
         */
        private final List<Pair<String, Object>> additionalMessages = new ArrayList<>();

        /**
         * 构造器
         *
         * @since 0.1.0
         */
        private ArgArray() {
        }

        /**
         * 创建当前实例
         *
         * @return 当前实例
         * @since 0.1.0
         */
        public static ArgArray builder() {
            return new ArgArray();
        }

        /**
         * 原始信息
         *
         * @param originalMessages 原始信息
         * @return 当前实例
         * @since 0.1.0
         */
        @SafeVarargs
        public final ArgArray originalMessages(@Nullable Pair<String, Object> @NonNull ... originalMessages) {
            if (ArrayUtils.isNotEmpty(originalMessages)) {
                this.originalMessages.addAll(Arrays.asList(originalMessages));
            }
            return this;
        }

        /**
         * 原始信息
         *
         * @param originalMessages 原始信息
         * @return 当前实例
         * @since 0.1.0
         */
        public final ArgArray originalMessages(
                @Nullable List<@NonNull Pair<@NonNull String, @Nullable Object>> originalMessages) {
            if (CollectionUtils.isNotEmpty(originalMessages)) {
                this.originalMessages.addAll(originalMessages);
            }
            return this;
        }

        /**
         * 附加信息
         *
         * @param additionalMessages 附加信息
         * @return 当前实例
         * @since 0.1.0
         */
        @SafeVarargs
        public final ArgArray additionalMessages(
                @NonNull Pair<@NonNull String, @Nullable Object> @NonNull ... additionalMessages) {
            if (ArrayUtils.isNotEmpty(additionalMessages)) {
                this.additionalMessages.addAll(Arrays.asList(additionalMessages));
            }
            return this;
        }

        /**
         * 附加信息
         *
         * @param additionalMessages 附加信息
         * @return 当前实例
         * @since 0.1.0
         */
        public final ArgArray additionalMessages(
                @Nullable List<@NonNull Pair<@NonNull String, @Nullable Object>> additionalMessages) {
            if (CollectionUtils.isNotEmpty(additionalMessages)) {
                this.additionalMessages.addAll(additionalMessages);
            }
            return this;
        }

        /**
         * 解析参数
         *
         * @return 解析后的参数
         * @since 0.1.0
         */
        public List<Pair<String, Object>> parse() {
            List<Pair<String, Object>> result = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(this.originalMessages)) {
                result.addAll(this.originalMessages);
            }
            if (CollectionUtils.isNotEmpty(this.additionalMessages)) {
                result.addAll(this.additionalMessages);
            }
            return result;
        }
    }
}
