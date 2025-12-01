package host.springboot.framework.mybatisplus.validation;

import host.springboot.framework.mybatisplus.service.BaseService;
import host.springboot.framework.mybatisplus.validation.annotation.DatabaseExistsById;
import host.springboot.framework3.core.util.inner.SpringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 数据库是否存在校验器
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class DatabaseExistsByIdValidator implements ConstraintValidator<DatabaseExistsById, Object> {

    /**
     * 仓储
     */
    private Class<? extends BaseService<?>> repository;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public DatabaseExistsByIdValidator() {
    }

    @Override
    public void initialize(DatabaseExistsById constraintAnnotation) {
        repository = constraintAnnotation.repository();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof Collection<?> collection) {
            if (collection.isEmpty()) {
                return true;
            }
            for (Object element : collection) {
                if (!(element instanceof Serializable)) {
                    throw new ValidationException("元素类型不支持");
                }
            }
            // noinspection unchecked
            return SpringUtils.getBean(repository).countByIdIgnore((Collection<? extends Serializable>) collection) == collection.size();
        }
        if (value instanceof Serializable) {
            return SpringUtils.getBean(repository).countByIdIgnore((Serializable) value) > 0;
        }
        throw new ValidationException("元素类型不支持");
    }
}
