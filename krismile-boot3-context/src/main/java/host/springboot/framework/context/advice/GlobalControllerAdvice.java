package host.springboot.framework.context.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import host.springboot.framework.context.util.IpUtils;
import host.springboot.framework3.core.enumeration.error.ErrorCodeEnum;
import host.springboot.framework3.core.exception.ApplicationException;
import host.springboot.framework3.core.exception.StackTraceException;
import host.springboot.framework3.core.exception.ThirdPartyException;
import host.springboot.framework3.core.response.R;
import host.springboot.framework3.core.response.vo.VO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 全局 Controller 异常处理
 *
 * @author JiYinchuan
 * @see BaseControllerAdvice
 * @since 0.1.0
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends BaseControllerAdvice {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public GlobalControllerAdvice() {
    }

    // -------------------------------- 基础验证 --------------------------------

    /**
     * 自定义异常全局捕获
     *
     * @param e       {@link ApplicationException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(ApplicationException.class)
    public VO<?> generalErrorHandle(ApplicationException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 自定义异常信息 -------------------------------- [Begin]", logTag());
        printStackTraceFormat(request.getRequestURI(), request.getMethod(), e, e.getCause(),
                "自定义异常信息", clientIp, getRequestInfo(request), null, false);
        log().warn("[{}] -------------------------------- 自定义异常信息 -------------------------------- [ End ]", logTag());
        return R.fail(e.getErrorEnum(), e.getUserTip());
    }

    /**
     * 堆栈异常全局捕获
     *
     * @param e       {@link StackTraceException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(StackTraceException.class)
    public VO<?> stackTracerErrorHandler(StackTraceException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().error("[{}] -------------------------------- 堆栈异常信息 -------------------------------- [Begin]", logTag());
        log().error("[{}] 堆栈异常信息 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), e.getLocalizedMessage(), e);
        log().error("[{}] -------------------------------- 堆栈异常信息 -------------------------------- [ End ]", logTag(), e);
        return R.fail(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 第三方异常全局捕获
     *
     * @param e       {@link ThirdPartyException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(ThirdPartyException.class)
    public VO<?> thirdPartyErrorHandler(ThirdPartyException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().error("[{}] -------------------------------- 第三方异常信息 -------------------------------- [Begin]", logTag());
        log().error("[{}] 第三方异常信息 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorCode: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), e.getErrorCode(), e.getErrorMessage(), e);
        log().error("[{}] -------------------------------- 第三方异常信息 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR.getValue(),
                e.getLocalizedMessage(), ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 系统异常全局捕获
     *
     * @param e       {@link Exception}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(Throwable.class)
    public VO<?> serverErrorHandler(Throwable e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().error("[{}] -------------------------------- 系统异常信息 -------------------------------- [Begin]", logTag());
        log().error("[{}] 系统异常信息 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), e.getLocalizedMessage(), e);
        log().error("[{}] -------------------------------- 系统异常信息 -------------------------------- [ End ]", logTag(), e);
        return R.fail(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    // -------------------------------- 请求参数验证 --------------------------------

    /**
     * 参数为空全局捕获
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestParam}</b></p>
     * <p>捕获来自 {@link org.springframework.web.bind.annotation.RequestParam}</p>
     *
     * @param e       {@link MissingServletRequestParameterException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public VO<?> validationExceptionHandler(MissingServletRequestParameterException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 参数为空 -------------------------------- [Begin]", logTag());
        log().warn("[{}] 参数为空 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), e.getLocalizedMessage());
        log().warn("[{}] -------------------------------- 参数为空 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.PARAMETER_EMPTY.getValue(),
                e.getLocalizedMessage(), ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 参数转换异常全局捕获
     *
     * @param e       {@link MethodArgumentTypeMismatchException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public VO<?> validationExceptionHandler(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 参数转换异常 -------------------------------- [Begin]", logTag());
        String errorMessage = super.printStackTraceFormat(request.getRequestURI(), request.getMethod(), e, e.getCause(),
                "参数转换异常", clientIp, getRequestInfo(request), e.getLocalizedMessage(), false);
        log().warn("[{}] -------------------------------- 参数转换异常 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.PARAMETER_FORMAT_NOT_MATCH.getValue(),
                errorMessage, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证异常全局捕获
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestParam}</b></p>
     * <p>捕获来自 {@link jakarta.validation.constraints}</p>
     *
     * @param e       {@link ValidationException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(ValidationException.class)
    public VO<?> validationExceptionHandler(ValidationException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 验证异常 -------------------------------- [Begin]", logTag());
        log().warn("[{}] 验证异常 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), e.getLocalizedMessage());
        super.printStackTraceFormat(request.getRequestURI(), request.getMethod(), e, e.getCause(),
                "验证异常", clientIp, getRequestInfo(request), null, true);
        List<String> userTipMessages = new ArrayList<>();
        if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                userTipMessages.add(constraintViolation.getMessage());
            }
        } else {
            userTipMessages.add(e.getLocalizedMessage());
        }
        log().warn("[{}] -------------------------------- 验证异常 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                e.getLocalizedMessage(), String.join(",", userTipMessages));
    }

    /**
     * 请求体异常全局捕获
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestBody}</b></p>
     * <p>Also throw to {@link InvalidFormatException}</p>
     *
     * @param e       {@link HttpMessageNotReadableException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @see com.fasterxml.jackson.databind.DeserializationContext#weirdStringException(String, Class, String)
     * <p>Enum deserialize failed exception is unsolved</p>
     * @since 0.1.0
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public VO<?> validationExceptionHandler(HttpMessageNotReadableException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 请求体异常 -------------------------------- [Begin]", logTag());
        String errorMessage = super.printStackTraceFormat(request.getRequestURI(), request.getMethod(), e, e.getCause(),
                "请求体异常", clientIp, getRequestInfo(request), e.getLocalizedMessage(), false);
        log().warn("[{}] 请求体异常 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), errorMessage);
        log().warn("[{}] -------------------------------- 请求体异常 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.PARAMETER_FORMAT_NOT_MATCH, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 绑定异常全局捕获
     *
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestParam}</b></p>
     *
     * @param e       {@link BindException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(BindException.class)
    public VO<?> validationExceptionHandler(BindException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 绑定异常 -------------------------------- [Begin]", logTag());
        List<String> errorMessages = parseAndPrintBindErrorMessage(e, request.getRequestURI(), request.getMethod(),
                "绑定异常", clientIp, getRequestInfo(request));
        log().warn("[{}] -------------------------------- 绑定异常 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                String.join(", ", errorMessages),
                ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 请求体验证全局捕获
     * <p>RequestType = <b>{@link org.springframework.web.bind.annotation.RequestBody}</b></p>
     * <p>捕获来自 {@link jakarta.validation.constraints}</p>
     *
     * @param e       {@link MethodArgumentNotValidException}
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public VO<?> validationExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 请求体验证异常 -------------------------------- [Begin]", logTag());
        List<String> errorMessages = parseAndPrintBindErrorMessage(e, request.getRequestURI(), request.getMethod(),
                "请求体验证异常", clientIp, getRequestInfo(request));
        log().warn("[{}] -------------------------------- 请求体验证异常 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                String.join(", ", errorMessages),
                ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 方法不被允许全局捕获
     *
     * @param e       HttpRequestMethodNotSupportedException
     * @param request {@link HttpServletRequest}
     * @return 结果响应
     * @since 0.1.0
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public VO<?> validationExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String clientIp = IpUtils.getIpv4Address(request);
        log().warn("[{}] -------------------------------- 方法不被允许 -------------------------------- [Begin]", logTag());
        log().warn("[{}] 方法不被允许 [requestUri: {}, requestMethod: {}, clientIp: {}, requestInfo: {}, errorMessage: {}]",
                logTag(), request.getRequestURI(), request.getMethod(), clientIp, getRequestInfo(request), e.getLocalizedMessage());
        super.printStackTraceFormat(request.getRequestURI(), request.getMethod(), e, e.getCause(),
                "方法不被允许", clientIp, getRequestInfo(request), null, false);
        log().warn("[{}] -------------------------------- 方法不被允许 -------------------------------- [ End ]", logTag());
        return R.fail(ErrorCodeEnum.INVALID_USER_INPUT.getValue(),
                e.getLocalizedMessage(), ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    @Override
    public @NonNull Logger log() {
        return LOGGER;
    }
}
