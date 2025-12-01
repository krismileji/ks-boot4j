package host.springboot.framework.mybatisplus.validation.annotation;

import host.springboot.framework.mybatisplus.service.BaseService;
import host.springboot.framework.mybatisplus.validation.DatabaseExistsByIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数据库是否存在校验
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DatabaseExistsByIdValidator.class)
public @interface DatabaseExistsById {

    /**
     * 仓储
     *
     * @return 仓储
     * @since 0.1.0
     */
    Class<? extends BaseService<?>> repository();

    /**
     * 错误信息
     *
     * @return 错误信息
     * @since 0.1.0
     */
    String message() default "数据不存在";

    /**
     * 分组
     *
     * @return 分组
     * @since 0.1.0
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     *
     * @return 负载
     * @since 0.1.0
     */
    Class<? extends Payload>[] payload() default {};

}
