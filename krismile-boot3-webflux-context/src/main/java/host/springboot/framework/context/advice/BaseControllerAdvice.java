package host.springboot.framework.context.advice;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import host.springboot.framework3.core.exception.EnumIllegalArgumentException;
import host.springboot.framework3.core.logging.LoggingComponent;
import host.springboot.framework3.core.model.RequestInfo;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局基础 Controller 异常处理基类
 *
 * <p>该类具体实现了全局请求异常拦截, 对于不同的异常有不同的拦截并返回, 无论是自定义异常/校验异常/未知异常等都具有详细的实现,
 * 拦截后给予客户端友好返回, 同时将异常规范的写出到日志文件中, 便于项目运行中通过日志排查问题</p>
 *
 * @author JiYinchuan
 * @since 0.2.0
 */
@RestControllerAdvice
public abstract class BaseControllerAdvice implements LoggingComponent {

    /**
     * 日志标签
     */
    private static final String LOG_TAG = "KS-ControllerAdvice";

    /**
     * 正则表达式 - 匹配 EnumIllegalArgumentException 的错误信息
     */
    private static final Pattern ENUM_ILLEGAL_ARGUMENT_PRINT_PATTERN = Pattern.compile("(\\[.*])\\s");

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    protected BaseControllerAdvice() {
    }

    /**
     * 格式化异常信息并输出日志
     *
     * <ul>
     *     <li>
     *         <b>EnumIllegalArgumentException</b> -
     *         为了保证输出安全, 采用正则表达式 {@code "(\\[.*])\\s"}
     *         来匹配 {@link host.springboot.framework3.core.enumeration.BaseEnum#parse(Object, Class)}
     *         中解析异常所抛出的 {@link EnumIllegalArgumentException} 异常信息, 将异常信息的全限定名进行删除后输出
     *     </li>
     *     <li>
     *         <b>DateTimeParseException</b> - 时间解析异常直接打印原始信息
     *     </li>
     * </ul>
     *
     * @param requestUri              请求地址
     * @param requestMethod           请求方法
     * @param tp                      {@code t} 父节点, 即原始节点
     * @param t                       {@code tp} 子节点, 当子节点为 {@code null} 时则不进行递归打印
     * @param tag                     日志标签
     * @param clientIp                客户端 IP
     * @param requestInfo             自定义请求信息
     * @param originalErrorMessage    原始错误信息
     * @param isPrintStackTraceDetail 是否输出堆栈详细信息
     * @return 格式化后的错误信息
     * @see EnumIllegalArgumentException
     * @since 0.2.0
     */
    protected String printStackTraceFormat(
            @NonNull String requestUri, @NonNull String requestMethod,
            @NonNull Throwable tp, @Nullable Throwable t,
            @NonNull String tag, @NonNull String clientIp,
            @Nullable Object requestInfo, @Nullable String originalErrorMessage,
            boolean isPrintStackTraceDetail) {
        if (Objects.isNull(t)) {
            if (tp instanceof EnumIllegalArgumentException) {
                Matcher matcher = ENUM_ILLEGAL_ARGUMENT_PRINT_PATTERN.matcher(tp.getLocalizedMessage());
                originalErrorMessage = tp.getLocalizedMessage();
                while (matcher.find()) {
                    originalErrorMessage = originalErrorMessage.replace(matcher.group(), "");
                }
            } else if (isResetOriginalErrorMessage(tp)) {
                originalErrorMessage = tp.getLocalizedMessage();
            } else if (tp instanceof InvalidFormatException) {
                Object originValue = ((InvalidFormatException) tp).getValue();
                originalErrorMessage = String.format("Cannot deserialize value from \"%s\"", originValue);
            }
            Throwable printThrowable = isPrintStackTraceDetail ? tp : null;
            log().warn("[{}] {} [requestUri: {}, requestMethod: {}, clientIp, {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), tag, requestUri, requestMethod, clientIp, requestInfo,
                    tp.getLocalizedMessage(), printThrowable);
            return originalErrorMessage;
        } else {
            return this.printStackTraceFormat(requestUri, requestMethod, t, t.getCause(),
                    tag, clientIp, requestInfo, originalErrorMessage, isPrintStackTraceDetail);
        }
    }

    /**
     * 获取请求信息（响应式）
     *
     * @param exchange ServerWebExchange
     * @return 请求信息的 Mono 包装
     * @since 0.2.0
     */
    protected Mono<String> getRequestInfo(@Nullable ServerWebExchange exchange) {
        return Mono.deferContextual(ctx -> {
            RequestInfo requestInfo = ctx.getOrDefault(RequestInfo.class.getSimpleName(), null);
            return Mono.just(JSON.toJSONString(requestInfo));
        });
    }

    /**
     * 解析并打印验证错误消息（支持WebExchangeBindException）
     *
     * @param e             绑定异常
     * @param requestUri    请求地址
     * @param requestMethod 请求方法
     * @param tag           日志标签
     * @param clientIp      客户端 IP
     * @param requestInfo   自定义请求信息
     * @return 解析后的验证错误信息
     * @since 0.2.0
     */
    protected List<String> parseAndPrintBindErrorMessage(
            @NonNull WebExchangeBindException e, @NonNull String requestUri,
            @NonNull String requestMethod, @NonNull String tag,
            @NonNull String clientIp, @Nullable Object requestInfo) {
        List<String> result = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            result.add(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
            log().warn("[{}] {} [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorType: @{}, errorMessage: {}, field: {}={}]",
                    logTag(), tag, requestUri, requestMethod, clientIp, requestInfo, fieldError.getCode(),
                    fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getRejectedValue());
        }
        if (!result.isEmpty()) {
            return result;
        }

        for (ObjectError globalError : e.getGlobalErrors()) {
            result.add(String.format("%s: %s", globalError.getObjectName(), globalError.getDefaultMessage()));
            log().warn("[{}] {} [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorType: @{}, errorMessage: {}, object: {}]",
                    logTag(), tag, requestUri, requestMethod, clientIp, requestInfo, globalError.getCode(),
                    globalError.getDefaultMessage(), globalError.getObjectName());
        }
        if (!result.isEmpty()) {
            return result;
        }

        result.add(printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                tag, clientIp, requestInfo, e.getMessage(), false));
        return result;
    }

    /**
     * 判断是否需要重置原始错误信息
     *
     * @param t 异常
     * @return 是否重置原始错误信息
     * @since 0.2.0
     */
    private boolean isResetOriginalErrorMessage(@NonNull Throwable t) {
        return t instanceof JsonParseException
                || t instanceof ParseException
                || t instanceof DateTimeParseException;
    }

    @Override
    public @NonNull String logTag() {
        return LOG_TAG;
    }
}
