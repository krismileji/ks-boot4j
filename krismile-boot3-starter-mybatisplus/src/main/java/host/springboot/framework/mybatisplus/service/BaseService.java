package host.springboot.framework.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import host.springboot.framework.mybatisplus.domain.BaseDO;
import host.springboot.framework.mybatisplus.enumeration.error.MybatisServiceErrorEnum;
import host.springboot.framework.mybatisplus.exception.MybatisServiceException;
import host.springboot.framework.mybatisplus.service.check.CheckProvider;
import host.springboot.framework3.core.exception.ApplicationException;
import host.springboot.framework3.core.page.ListPage;
import host.springboot.framework3.core.page.Pageable;
import host.springboot.framework3.core.page.query.PageQuery;
import host.springboot.framework3.core.util.Assert;
import host.springboot.framework3.core.util.inner.StringUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MybatisService基类
 *
 * <p>该类为 [MybatisService] 基类, 对 [MybatisPlus] 自带的 {@link IService} 进行了进一步的封装,
 * 增强业务效果, 采用该类的方法以替代 {@link IService} 中的方法
 *
 * @param <T> 实体类
 * @author JiYinchuan
 * @since 0.1.0
 */
public interface BaseService<T extends BaseDO<? extends Serializable>> extends IService<T>, CheckProvider {

    /**
     * 查询锁后缀
     */
    String QUERY_LOCK = " for update";

    // -------------------------------- Ignore --------------------------------

    /**
     * 忽略ID获取是否存在
     *
     * @param id ID
     * @return 是否存在 [true: 存在, false: 不存在]
     * @since 0.1.0
     */
    default boolean isExistsByIdIgnore(@Nullable Serializable id) {
        if (Objects.isNull(id)) {
            return false;
        }
        return this.countByIdValidate(id) != 0;
    }

    /**
     * 批量忽略ID获取是否存在
     *
     * @param ids IDs
     * @return 是否存在 [true: 存在, false: 不存在]
     * @since 0.1.0
     */
    default boolean isExistsByIdIgnore(@Nullable Collection<? extends Serializable> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return false;
        }
        return this.countByIdValidate(ids) != 0;
    }

    /**
     * 验证是否存在
     *
     * @param id      ID
     * @param userTip 用户提示
     * @since 0.1.0
     */
    default void isExistsThrow(@Nullable Serializable id, @Nullable String userTip) {
        if (Objects.isNull(id)) {
            return;
        }
        if (this.countByIdValidate(id) == 0) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, StringUtils.defaultString(userTip,
                    MybatisServiceErrorEnum.RESULT_IS_NULL.getReasonPhrase()));
        }
    }

    /**
     * 验证是否存在
     *
     * @param ids     IDs
     * @param userTip 用户提示
     * @since 0.1.0
     */
    default void isExistsThrow(@Nullable Collection<? extends Serializable> ids, @Nullable String userTip) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return;
        }
        if (this.countByIdValidate(ids) == 0) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, StringUtils.defaultString(userTip,
                    MybatisServiceErrorEnum.RESULT_IS_NULL.getReasonPhrase()));
        }
    }

    /**
     * 忽略ID获取数量
     *
     * @param id ID
     * @return 数量
     * @since 0.1.0
     */
    default long countByIdIgnore(@Nullable Serializable id) {
        if (Objects.isNull(id)) {
            return -1;
        }
        return this.countByIdValidate(id);
    }

    /**
     * 忽略ID获取数量
     *
     * @param ids ID
     * @return 数量
     * @since 0.1.0
     */
    default long countByIdIgnore(@Nullable Collection<? extends Serializable> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return -1;
        }
        return this.countByIdValidate(ids);
    }

    /**
     * 忽略ID并获取DO
     *
     * @param id ID
     * @return DO
     * @since 0.1.0
     */
    default @Nullable T getByIdIgnore(@Nullable Serializable id) {
        return this.getByIdIgnoreOpt(id).orElse(null);
    }

    /**
     * 忽略ID并获取DO
     *
     * @param id ID
     * @return DO
     * @since 0.1.0
     */
    default @NonNull Optional<@Nullable T> getByIdIgnoreOpt(@Nullable Serializable id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.getOne(Wrappers.<T>query().eq(BaseDO.FIELD_ID, id)));
    }

    /**
     * 忽略IDs并获取DO
     *
     * @param ids IDs
     * @return DOs
     * @since 0.1.0
     */
    default @NonNull List<@NonNull T> listByIdIgnore(@Nullable Collection<? extends Serializable> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return listByIds(ids);
    }

    /**
     * 忽略结果并获取DO
     *
     * @param wrapper wrapper
     * @return DOs
     * @since 0.1.0
     */
    default @NonNull List<@NonNull T> listIgnore(@NonNull Wrapper<@NonNull T> wrapper) {
        List<T> result = this.list(wrapper);
        if (Objects.isNull(result) || result.isEmpty()) {
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * 忽略PageRequest并获取Pageable
     * 默认根据 {@code BaseDO#getUpdateTime()} 进行排序
     *
     * @param pageQuery pageQuery
     * @return Pageable
     * @since 0.1.0
     */
    default @NonNull Pageable<@NonNull List<@NonNull T>> pageIgnore(@Nullable PageQuery pageQuery) {
        if (Objects.isNull(pageQuery)) {
            return new ListPage<>(new PageQuery());
        }
        Page<T> page = this.page(new Page<T>(pageQuery.getPageNo(), pageQuery.getPageSize())
                .addOrder(OrderItem.desc(BaseService.toUnderlineCase(BaseDO.FIELD_UPDATE_TIME))));
        return new ListPage<>(pageQuery, page.getTotal(), page.getRecords());
    }

    /**
     * 忽略PageRequest并获取Pageable
     *
     * @param pageQuery pageQuery
     * @return Pageable
     * @since 0.1.0
     */
    default @NonNull Pageable<@NonNull List<@NonNull T>> pageIgnore(IPage<@Nullable T> pageQuery) {
        if (Objects.isNull(pageQuery)) {
            return new ListPage<>(new PageQuery());
        }
        IPage<T> page = this.page(pageQuery);
        return new ListPage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 忽略实体类ID并保存
     *
     * @param entity 实体类
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default Boolean saveIgnore(@Nullable T entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return this.save(BaseService.clearEntityDefaultParameterAndGet(entity,
                true, true, true));
    }

    /**
     * 批量忽略验证实体类ID并保存
     *
     * @param entities 实体对象集合
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean saveIgnore(@Nullable Collection<@NonNull T> entities) {
        return this.saveIgnore(entities, 1000);
    }

    /**
     * 批量忽略验证实体类ID并保存
     *
     * @param entities  实体对象集合
     * @param batchSize 插入批次数量
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean saveIgnore(@Nullable Collection<@NonNull T> entities, int batchSize) {
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return false;
        }
        return this.saveBatch(BaseService.clearEntityDefaultParameterAndGet(entities,
                true, true, true), batchSize);
    }

    /**
     * 忽略验证实体类ID并更新
     *
     * @param entity 实体类
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean updateByIdIgnore(@Nullable T entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        return this.updateById(clearEntityDefaultParameterAndGet(entity));
    }

    /**
     * 批量验证实体类ID并更新
     *
     * @param entities 实体对象集合
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean updateByIdIgnore(@Nullable Collection<@NonNull T> entities) {
        return this.updateByIdIgnore(entities, 1000);
    }

    /**
     * 批量验证实体类ID并更新
     *
     * @param entities  实体对象集合
     * @param batchSize 插入批次数量
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean updateByIdIgnore(@Nullable Collection<@NonNull T> entities, int batchSize) {
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return false;
        }
        return this.updateBatchById(clearEntityDefaultParameterAndGet(entities), batchSize);
    }

    /**
     * 忽略实体类ID并删除
     *
     * @param id ID
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean removeByIdIgnore(@Nullable Serializable id) {
        if (Objects.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }

    /**
     * 批量忽略实体类ID并删除
     *
     * @param ids ID集合
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @since 0.1.0
     */
    default boolean removeByIdIgnore(@Nullable Collection<? extends Serializable> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return false;
        }
        return this.removeByIds(ids);
    }

    // -------------------------------- Validate --------------------------------

    /**
     * 验证ID获取是否存在
     *
     * @param id ID
     * @return 是否存在 [true: 存在, false: 不存在]
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default boolean isExistsByIdValidate(@Nullable Serializable id) throws MybatisServiceException {
        return this.isExistsByIdValidate(id, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证ID获取是否存在
     *
     * @param id           ID
     * @param errorUserTip 用户提示信息
     * @return 是否存在 [true: 存在, false: 不存在]
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default boolean isExistsByIdValidate(@Nullable Serializable id, String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(id)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.countByIdValidate(id) != 0;
    }

    /**
     * 批量验证ID获取是否存在
     *
     * @param ids IDs
     * @return 是否存在 [true: 存在, false: 不存在]
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default boolean isExistsByIdValidate(@Nullable Collection<? extends Serializable> ids) throws MybatisServiceException {
        return this.isExistsByIdValidate(ids, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 批量验证ID获取是否存在
     *
     * @param ids          IDs
     * @param errorUserTip 用户提示信息
     * @return 是否存在 [true: 存在, false: 不存在]
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default boolean isExistsByIdValidate(@Nullable Collection<? extends Serializable> ids, String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.countByIdValidate(ids) != 0;
    }

    /**
     * 验证ID获取数量
     *
     * @param id ID
     * @return 数量
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default long countByIdValidate(@Nullable Serializable id) throws MybatisServiceException {
        return this.countByIdValidate(id, null);
    }

    /**
     * 验证ID获取数量
     *
     * @param id           ID
     * @param errorUserTip 用户提示信息
     * @return 数量
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default long countByIdValidate(@Nullable Serializable id, @Nullable String errorUserTip) throws MybatisServiceException {
        if (Objects.isNull(id)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.count(Wrappers.<T>query().select(BaseDO.FIELD_ID).eq(BaseDO.FIELD_ID, id));
    }

    /**
     * 批量验证ID获取数量
     *
     * @param ids IDs
     * @return 数量
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default long countByIdValidate(@Nullable Collection<? extends Serializable> ids) throws MybatisServiceException {
        return this.countByIdValidate(ids, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 批量验证ID获取数量
     *
     * @param ids          IDs
     * @param errorUserTip 用户提示信息
     * @return 数量
     * @throws MybatisServiceException ID为空
     * @since 0.1.0
     */
    default long countByIdValidate(@Nullable Collection<? extends Serializable> ids, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.count(Wrappers.<T>query().select(BaseDO.FIELD_ID).in(BaseDO.FIELD_ID, ids));
    }

    /**
     * 验证ID并获取DO
     *
     * @param id ID
     * @return DO
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull T getByIdValidate(@Nullable Serializable id) throws MybatisServiceException {
        return this.getByIdValidate(id, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证ID并获取DO
     *
     * @param id           ID
     * @param errorUserTip 用户提示信息
     * @return DO
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull T getByIdValidate(@Nullable Serializable id, @Nullable String errorUserTip) throws MybatisServiceException {
        if (Objects.isNull(id)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        Optional<T> entity = Optional.ofNullable(this.getOne(Wrappers.<T>query().eq(BaseDO.FIELD_ID, id)));
        if (entity.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip);
        }
        return entity.get();
    }

    /**
     * 验证IDs并获取DOs
     *
     * @param ids IDs
     * @return DOs
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull List<@NonNull T> listByIdValidate(@Nullable Collection<? extends Serializable> ids) throws MybatisServiceException {
        return this.listByIdValidate(ids, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 条件构造器获取DOs
     *
     * @param wrapper wrapper
     * @return DOs
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull List<@NonNull T> listByIdValidate(@NonNull Wrapper<@NonNull T> wrapper) throws MybatisServiceException {
        return this.listValidate(wrapper, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证IDs并获取DOs
     *
     * @param ids          IDs
     * @param errorUserTip 用户提示信息
     * @return DOs
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull List<@NonNull T> listByIdValidate(@Nullable Collection<? extends Serializable> ids, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        List<T> entities = this.list(Wrappers.<T>query().in(BaseDO.FIELD_ID, ids));
        if (Objects.isNull(entities) || entities.isEmpty() || entities.size() != ids.size()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_SET_IS_NULL, errorUserTip);
        }
        return entities;
    }

    /**
     * 条件构造器获取DOs
     *
     * @param wrapper      wrapper
     * @param errorUserTip 用户提示信息
     * @return DOs
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull List<@NonNull T> listValidate(@NonNull Wrapper<@NonNull T> wrapper, @Nullable String errorUserTip)
            throws MybatisServiceException {
        List<T> entities = this.list(wrapper);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return entities;
    }

    /**
     * 验证PageRequest并获取Pageable
     * 默认根据 {@code BaseDO#getUpdateTime()} 进行排序
     *
     * @param pageQuery pageQuery
     * @return Pageable
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull Pageable<@NonNull List<@NonNull T>> pageValidate(@Nullable PageQuery pageQuery) throws MybatisServiceException {
        return this.pageValidate(pageQuery, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证PageRequest并获取Pageable
     * 默认根据 {@code BaseDO#getUpdateTime()} 进行排序
     *
     * @param pageQuery    pageQuery
     * @param errorUserTip 用户提示信息
     * @return Pageable
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull Pageable<@NonNull List<@NonNull T>> pageValidate(@Nullable PageQuery pageQuery, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(pageQuery)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        Page<T> page = this.page(new Page<T>(pageQuery.getPageNo(), pageQuery.getPageSize())
                .addOrder(OrderItem.desc(BaseService.toUnderlineCase(BaseDO.FIELD_UPDATE_TIME))));
        return new ListPage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 验证PageRequest并获取Pageable
     *
     * @param pageQuery pageQuery
     * @return Pageable
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull Pageable<@NonNull List<@NonNull T>> pageValidate(@Nullable IPage<T> pageQuery) throws MybatisServiceException {
        return this.pageValidate(pageQuery, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证PageRequest并获取Pageable
     *
     * @param pageQuery    pageQuery
     * @param errorUserTip 用户提示信息
     * @return Pageable
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull Pageable<@NonNull List<@NonNull T>> pageValidate(IPage<@Nullable T> pageQuery, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(pageQuery)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }

        IPage<T> page = this.page(pageQuery);
        return new ListPage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 验证实体类ID并保存
     *
     * @param entity 实体类
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean saveValidate(@Nullable T entity) throws MybatisServiceException {
        return this.saveValidate(entity, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证实体类ID并保存
     *
     * @param entity       实体类
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean saveValidate(@Nullable T entity, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(entity)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.save(BaseService.clearEntityDefaultParameterAndGet(entity,
                true, true, true));
    }

    /**
     * 批量验证实体类ID并保存
     *
     * @param entities 实体对象集合
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean saveValidate(@Nullable Collection<@NonNull T> entities) throws MybatisServiceException {
        return this.saveValidate(entities, 1000, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 批量验证实体类ID并保存
     *
     * @param entities     实体对象集合
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean saveValidate(@Nullable Collection<@NonNull T> entities, @Nullable String errorUserTip)
            throws MybatisServiceException {
        return this.saveValidate(entities, 1000, errorUserTip);
    }

    /**
     * 批量验证实体类ID并保存
     *
     * @param entities  实体对象集合
     * @param batchSize 插入批次数量
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean saveValidate(@Nullable Collection<@NonNull T> entities, int batchSize) throws MybatisServiceException {
        return this.saveValidate(entities, batchSize, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 批量验证实体类ID并保存
     *
     * @param entities     实体对象集合
     * @param batchSize    插入批次数量
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean saveValidate(@Nullable Collection<@NonNull T> entities, int batchSize, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(entities) || entities.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.saveBatch(BaseService.clearEntityDefaultParameterAndGet(entities,
                true, true, true), batchSize);
    }

    /**
     * 批量验证实体类ID并更新
     *
     * @param entity 实体类
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean updateByIdValidate(@Nullable T entity) throws MybatisServiceException {
        if (Objects.isNull(entity)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL);
        }
        if (this.countByIdValidate(entity.getId()) == 0) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL);
        }
        return this.updateByIdValidate(entity, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证实体类ID并更新
     *
     * @param entity       实体类
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean updateByIdValidate(@Nullable T entity, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(entity)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        if (this.countByIdValidate(entity.getId()) == 0) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip);
        }
        return this.updateById(clearEntityDefaultParameterAndGet(entity));
    }

    /**
     * 验证实体类ID并更新
     *
     * @param entities 实体对象集合
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean updateByIdValidate(@Nullable Collection<@NonNull T> entities) throws MybatisServiceException {
        return this.updateByIdValidate(entities, 1000);
    }

    /**
     * 批量验证实体类ID并更新
     *
     * @param entities     实体对象集合
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean updateByIdValidate(@Nullable Collection<@NonNull T> entities, @Nullable String errorUserTip)
            throws MybatisServiceException {
        return this.updateByIdValidate(entities, 1000, errorUserTip);
    }

    /**
     * 批量验证实体类ID并更新
     *
     * @param entities  实体对象集合
     * @param batchSize 插入批次数量
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean updateByIdValidate(@Nullable Collection<@NonNull T> entities, int batchSize)
            throws MybatisServiceException {
        return this.updateByIdValidate(entities, batchSize, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 批量验证实体类ID并更新
     *
     * @param entities     实体对象集合
     * @param batchSize    插入批次数量
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException 参数为空|参数错误
     * @since 0.1.0
     */
    default boolean updateByIdValidate(@Nullable Collection<@NonNull T> entities, int batchSize, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(entities) || entities.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        List<Serializable> ids = entities.stream().map(BaseDO::getId).collect(Collectors.toList());
        if (this.countByIdValidate(ids) != entities.size()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip);
        }
        return this.updateBatchById(clearEntityDefaultParameterAndGet(entities), batchSize);
    }

    /**
     * 验证实体类ID并删除
     *
     * @param id ID
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default boolean removeByIdValidate(@Nullable Serializable id) throws MybatisServiceException {
        return this.removeByIdValidate(id, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 验证实体类ID并删除
     *
     * @param id           ID
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default boolean removeByIdValidate(@Nullable Serializable id, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (this.countByIdValidate(id) == 0) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip);
        }
        return this.removeById(id);
    }

    /**
     * 批量验证实体类ID并删除
     *
     * @param ids ID集合
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default boolean removeByIdValidate(@Nullable Collection<? extends Serializable> ids) throws MybatisServiceException {
        return this.removeByIdValidate(ids, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 批量验证实体类ID并删除
     *
     * @param ids          ID集合
     * @param errorUserTip 用户提示信息
     * @return 操作是否成功 [null: 未执行, true: 成功, false: 失败]
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default boolean removeByIdValidate(@Nullable Collection<? extends Serializable> ids, @Nullable String errorUserTip)
            throws MybatisServiceException {
        if (Objects.isNull(ids) || this.countByIdValidate(ids) != ids.size()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip);
        }
        return this.removeByIds(ids);
    }

    // -------------------------------- QueryLock --------------------------------

    /**
     * 获取并锁定数据
     *
     * @param id ID
     * @return DO
     * @since 0.1.0
     */
    default T getAndLockIgnore(@Nullable Serializable id) {
        return this.getAndLockIgnoreOpt(id).orElse(null);
    }

    /**
     * 获取并锁定数据
     *
     * @param id ID
     * @return DO
     * @since 0.1.0
     */
    default @NonNull Optional<@Nullable T> getAndLockIgnoreOpt(@Nullable Serializable id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.getOne(Wrappers.<T>query().eq(BaseDO.FIELD_ID, id).last(QUERY_LOCK)));
    }

    /**
     * 获取并锁定数据
     *
     * @param id ID
     * @return DO
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default T getAndLockValidate(@Nullable Serializable id) throws MybatisServiceException {
        return this.getAndLockValidate(id, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 获取并锁定数据
     *
     * @param id           ID
     * @param errorUserTip 用户提示信息
     * @return DO
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default T getAndLockValidate(@Nullable Serializable id, @Nullable String errorUserTip) throws MybatisServiceException {
        if (Objects.isNull(id)) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.getAndLockIgnoreOpt(id).orElseThrow(() ->
                new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip));
    }

    /**
     * 获取并锁定数据
     *
     * @param ids ID集合
     * @return DO
     * @since 0.1.0
     */
    default @NonNull Collection<@NonNull T> getAndLockIgnore(@Nullable Collection<? extends Serializable> ids) {
        return this.getAndLockIgnoreOpt(ids).orElse(Collections.emptyList());
    }

    /**
     * 获取并锁定数据
     *
     * @param ids ID集合
     * @return DO
     * @since 0.1.0
     */
    default @NonNull Optional<@NonNull Collection<@NonNull T>> getAndLockIgnoreOpt(@Nullable Collection<? extends Serializable> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.list(Wrappers.<T>query().in(BaseDO.FIELD_ID, ids).last(QUERY_LOCK)));
    }

    /**
     * 获取并锁定数据
     *
     * @param ids ID集合
     * @return DO
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull Collection<@NonNull T> getAndLockValidate(@Nullable Collection<? extends Serializable> ids) throws MybatisServiceException {
        return this.getAndLockValidate(ids, ApplicationException.DEFAULT_ERROR_USER_TIP);
    }

    /**
     * 获取并锁定数据
     *
     * @param ids          ID集合
     * @param errorUserTip 用户提示信息
     * @return DO
     * @throws MybatisServiceException ID为空|ID错误
     * @since 0.1.0
     */
    default @NonNull Collection<@NonNull T> getAndLockValidate(@Nullable Collection<? extends Serializable> ids, @Nullable String errorUserTip) throws MybatisServiceException {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new MybatisServiceException(MybatisServiceErrorEnum.ID_IS_NULL, errorUserTip);
        }
        return this.getAndLockIgnoreOpt(ids).orElseThrow(() ->
                new MybatisServiceException(MybatisServiceErrorEnum.RESULT_IS_NULL, errorUserTip));
    }

    // -------------------------------- Util --------------------------------

    /**
     * 清除实体类默认参数值
     *
     * @param entity 实体类
     * @param <T>    实体类类型
     * @return 操作后的实体类
     * @since 0.1.0
     */
    static <T extends BaseDO<? extends Serializable>> @NonNull T clearEntityDefaultParameterAndGet(@NonNull T entity) {
        return BaseService.clearEntityDefaultParameterAndGet(entity, false, true, true);
    }

    /**
     * 批量清除实体类默认参数值
     *
     * @param entities 实体对象集合
     * @param <T>      实体类类型
     * @return 操作后的实体类
     * @since 0.1.0
     */
    static <T extends BaseDO<? extends Serializable>> @NonNull List<@NonNull T> clearEntityDefaultParameterAndGet(@NonNull Collection<@NonNull T> entities) {
        return entities.stream().map(entity -> BaseService.clearEntityDefaultParameterAndGet(entity,
                false, true, true)).collect(Collectors.toList());
    }

    /**
     * 清除实体类默认参数值
     *
     * @param entity            实体类
     * @param isClearId         是否清除ID
     * @param isClearCreateTime 是否清除创建时间
     * @param isClearModifyTime 是否清除更新时间
     * @param <T>               实体类类型
     * @return 操作后的实体类
     * @since 0.1.0
     */
    static <T extends BaseDO<? extends Serializable>> @NonNull T clearEntityDefaultParameterAndGet(
            @NonNull T entity, boolean isClearId, boolean isClearCreateTime, boolean isClearModifyTime) {
        if (isClearId) {
            entity.setId(null);
        }
        if (isClearCreateTime) {
            entity.setCreateTime(null);
        }
        if (isClearModifyTime) {
            entity.setUpdateTime(null);
        }
        return entity;
    }

    /**
     * 批量清除实体类默认参数值
     *
     * @param entities          实体对象集合
     * @param isClearId         是否清除ID
     * @param isClearCreateTime 是否清除创建时间
     * @param isClearModifyTime 是否清除更新时间
     * @param <T>               实体类类型
     * @return 清除参数后的实体对象集合
     * @since 0.1.0
     */
    static <T extends BaseDO<? extends Serializable>> @NonNull Collection<@NonNull T> clearEntityDefaultParameterAndGet(
            @NonNull Collection<@NonNull T> entities, boolean isClearId, boolean isClearCreateTime, boolean isClearModifyTime) {
        return entities.stream().map(entity -> BaseService.clearEntityDefaultParameterAndGet(entity,
                isClearId, isClearCreateTime, isClearModifyTime)).collect(Collectors.toList());
    }

    /**
     * 简易驼峰转下划线
     *
     * @param value 驼峰字符串
     * @return 下划线字符串
     * @since 0.1.0
     */
    static @NonNull String toUnderlineCase(@NonNull String value) {
        Assert.notNull(value, "UnderlineCase value must not be null");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
