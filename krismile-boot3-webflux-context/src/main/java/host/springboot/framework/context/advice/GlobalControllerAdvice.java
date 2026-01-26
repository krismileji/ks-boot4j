package host.springboot.framework.context.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import host.springboot.framework.context.util.IpUtils;
import host.springboot.framework3.core.enumeration.error.ErrorCodeEnum;
import host.springboot.framework3.core.exception.ApplicationException;
import host.springboot.framework3.core.exception.StackTraceException;
import host.springboot.framework3.core.exception.ThirdPartyException;
import host.springboot.framework3.core.response.R;
import host.springboot.framework3.core.response.vo.VO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 全局 Controller 异常处理器
 *
 * @author JiYinchuan
 * @see BaseControllerAdvice
 * @since 0.2.0
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends BaseControllerAdvice {

    /**
     * 日志记录器
     *
     * @since 0.2.0
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    /**
     * 构造器
     *
     * @since 0.2.0
     */
    public GlobalControllerAdvice() {
    }

    // -------------------------------- 基础验证 --------------------------------

    /**
     * 自定义异常全局捕获（响应式）
     *
     * @param e        {@link ApplicationException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(ApplicationException.class)
    public Mono<VO<?>> generalErrorHandle(ApplicationException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 自定义异常信息 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).doOnNext(requestInfo -> {
            super.printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                    "自定义异常信息", clientIp, requestInfo, null, false);
            log().warn("[{}] -------------------------------- 自定义异常信息 -------------------------------- [ End ]", logTag());
        }).then(Mono.just(R.fail(e.getErrorEnum(), e.getUserTip())));
    }

    /**
     * 堆栈异常全局捕获（响应式）
     *
     * @param e        {@link StackTraceException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(StackTraceException.class)
    public Mono<VO<?>> stackTracerErrorHandler(StackTraceException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().error("[{}] -------------------------------- 堆栈异常信息 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).doOnNext(requestInfo -> {
            log().error("[{}] 堆栈异常信息 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, e.getLocalizedMessage(), e);
            log().error("[{}] -------------------------------- 堆栈异常信息 -------------------------------- [ End ]", logTag(), e);
        }).then(Mono.just(R.fail(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR, ApplicationException.DEFAULT_ERROR_USER_TIP)));
    }

    /**
     * 第三方异常全局捕获（响应式）
     *
     * @param e        {@link ThirdPartyException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(ThirdPartyException.class)
    public Mono<VO<?>> thirdPartyErrorHandler(ThirdPartyException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().error("[{}] -------------------------------- 第三方异常信息 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).doOnNext(requestInfo -> {
            log().error("[{}] 第三方异常信息 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorCode: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, e.getErrorCode(), e.getErrorMessage(), e);
            log().error("[{}] -------------------------------- 第三方异常信息 -------------------------------- [ End ]", logTag());
        }).then(Mono.just(R.fail(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR.getValue(),
                e.getLocalizedMessage(), ApplicationException.DEFAULT_ERROR_USER_TIP)));
    }

    /**
     * 系统异常全局捕获（响应式）
     *
     * @param e        {@link Exception}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(Throwable.class)
    public Mono<VO<?>> serverErrorHandler(Throwable e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().error("[{}] -------------------------------- 系统异常信息 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).doOnNext(requestInfo -> {
            log().error("[{}] 系统异常信息 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, e.getLocalizedMessage(), e);
            log().error("[{}] -------------------------------- 系统异常信息 -------------------------------- [ End ]", logTag(), e);
        }).then(Mono.just(R.fail(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR, ApplicationException.DEFAULT_ERROR_USER_TIP)));
    }

    // -------------------------------- 请求参数验证 --------------------------------

    /**
     * 参数转换异常全局捕获（响应式）
     *
     * @param e        {@link org.springframework.web.server.ServerWebInputException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(org.springframework.web.server.ServerWebInputException.class)
    public Mono<VO<?>> validationExceptionHandler(org.springframework.web.server.ServerWebInputException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 参数转换异常 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).map(requestInfo -> {
            String errorMessage = super.printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                    "参数转换异常", clientIp, requestInfo, e.getReason(), false);
            log().warn("[{}] -------------------------------- 参数转换异常 -------------------------------- [ End ]", logTag());
            return R.fail(ErrorCodeEnum.PARAMETER_FORMAT_NOT_MATCH.getValue(),
                    errorMessage, ApplicationException.DEFAULT_ERROR_USER_TIP);
        });
    }

    /**
     * 验证异常全局捕获（响应式）
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestParam}</b></p>
     * <p>捕获来自 {@link jakarta.validation.constraints}</p>
     *
     * @param e        {@link ValidationException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(ValidationException.class)
    public Mono<VO<?>> validationExceptionHandler(ValidationException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 验证异常 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).map(requestInfo -> {
            log().warn("[{}] 验证异常 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, e.getLocalizedMessage());
            super.printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                    "验证异常", clientIp, requestInfo, null, true);
            List<String> userTipMessages = this.parseUserTipMessages(e);
            log().warn("[{}] -------------------------------- 验证异常 -------------------------------- [ End ]", logTag());
            return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                    e.getLocalizedMessage(), String.join(",", userTipMessages));
        });
    }

    /**
     * 请求体异常全局捕获（响应式）
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestBody}</b></p>
     * <p>Also throw to {@link InvalidFormatException}</p>
     *
     * @param e        {@link org.springframework.web.server.UnsupportedMediaTypeStatusException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @see com.fasterxml.jackson.databind.DeserializationContext#weirdStringException(String, Class, String)
     * <p>Enum deserialize failed exception is unsolved</p>
     * @since 0.2.0
     */
    @ExceptionHandler(org.springframework.web.server.UnsupportedMediaTypeStatusException.class)
    public Mono<VO<?>> validationExceptionHandler(org.springframework.web.server.UnsupportedMediaTypeStatusException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 请求体异常 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).map(requestInfo -> {
            String errorMessage = super.printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                    "请求体异常", clientIp, requestInfo, e.getReason(), false);
            log().warn("[{}] 请求体异常 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, errorMessage);
            log().warn("[{}] -------------------------------- 请求体异常 -------------------------------- [ End ]", logTag());
            return R.fail(ErrorCodeEnum.PARAMETER_FORMAT_NOT_MATCH, ApplicationException.DEFAULT_ERROR_USER_TIP);
        });
    }

    /**
     * 解码异常全局捕获（响应式）
     *
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestBody}</b></p>
     *
     * @param e        {@link org.springframework.core.codec.DecodingException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(org.springframework.core.codec.DecodingException.class)
    public Mono<VO<?>> validationExceptionHandler(org.springframework.core.codec.DecodingException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 解码异常 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).map(requestInfo -> {
            String errorMessage = super.printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                    "解码异常", clientIp, requestInfo, e.getMessage(), false);
            log().warn("[{}] 解码异常 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, errorMessage);
            log().warn("[{}] -------------------------------- 解码异常 -------------------------------- [ End ]", logTag());
            return R.fail(ErrorCodeEnum.PARAMETER_FORMAT_NOT_MATCH, ApplicationException.DEFAULT_ERROR_USER_TIP);
        });
    }

    /**
     * 请求体验证全局捕获（响应式）
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestBody}</b></p>
     * <p>捕获来自 {@link jakarta.validation.constraints}</p>
     *
     * @param e        {@link org.springframework.web.bind.support.WebExchangeBindException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(org.springframework.web.bind.support.WebExchangeBindException.class)
    public Mono<VO<?>> webExchangeBindExceptionHandler(org.springframework.web.bind.support.WebExchangeBindException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 请求体验证异常 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).map(requestInfo -> {
            List<String> errorMessages = super.parseAndPrintBindErrorMessage(e, requestUri, requestMethod,
                    "请求体验证异常", clientIp, requestInfo);
            log().warn("[{}] -------------------------------- 请求体验证异常 -------------------------------- [ End ]", logTag());
            return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                    String.join(", ", errorMessages),
                    ApplicationException.DEFAULT_ERROR_USER_TIP);
        });
    }

    /**
     * 方法不被允许全局捕获（响应式）
     *
     * @param e        {@link MethodNotAllowedException}
     * @param exchange {@link ServerWebExchange}
     * @return 结果响应的 Mono 包装
     * @since 0.2.0
     */
    @ExceptionHandler(MethodNotAllowedException.class)
    public Mono<VO<?>> validationExceptionHandler(MethodNotAllowedException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.getIpv4Address(request);
        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();
        log().warn("[{}] -------------------------------- 方法不被允许 -------------------------------- [Begin]", logTag());
        return super.getRequestInfo(exchange).map(requestInfo -> {
            log().warn("[{}] 方法不被允许 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                    logTag(), requestUri, requestMethod, clientIp, requestInfo, e.getLocalizedMessage());
            super.printStackTraceFormat(requestUri, requestMethod, e, e.getCause(),
                    "方法不被允许", clientIp, requestInfo, null, false);
            log().warn("[{}] -------------------------------- 方法不被允许 -------------------------------- [ End ]", logTag());
            return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                    e.getLocalizedMessage(), ApplicationException.DEFAULT_ERROR_USER_TIP);
        });
    }

    @Override
    public @NonNull Logger log() {
        return LOGGER;
    }

    /**
     * 解析用户提示信息
     *
     * @param exception {@link ValidationException}
     * @return 解析后的用户提示信息
     * @since 0.2.0
     */
    private List<String> parseUserTipMessages(@NonNull ValidationException exception) {
        List<String> userTipMessages = new ArrayList<>();
        if (exception instanceof ConstraintViolationException e) {
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                userTipMessages.add(constraintViolation.getMessage());
            }
        } else {
            userTipMessages.add(exception.getLocalizedMessage());
        }
        return userTipMessages;
    }
}
