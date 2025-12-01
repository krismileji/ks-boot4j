package host.springboot.framework.mybatisplus.service.check;

/**
 * 检查提供者
 *
 * <p>该类主要用于统一所有的检查Service, 便于进行集成使用
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface CheckProvider extends CheckOperationService, CheckExistsService, CheckIdempotencyService {
}
