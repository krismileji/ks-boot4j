package host.springboot.framework.mybatisplus.model.param;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import host.springboot.framework.mybatisplus.domain.BaseDO;
import host.springboot.framework3.core.util.inner.ArrayUtils;
import host.springboot.framework3.core.util.inner.CollectionUtils;
import lombok.Data;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.*;

/**
 * 基础仓储查询参数
 *
 * @param <T> 查询字段所属对象类型
 * @author JiYinchuan
 * @since 0.1.0
 */
@Data
public abstract class RepositoryQueryParam<T extends BaseDO<? extends Serializable>> {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryQueryParam.class);

    /**
     * 日志标签
     */
    private static final String LOG_TAG = "基础仓储查询参数";

    /**
     * 查询字段, 为空时查询所有
     */
    protected LinkedHashSet<SFunction<T, ?>> selectColumns;

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public RepositoryQueryParam() {
    }

    /**
     * 添加查询字段
     *
     * @param columns 查询字段
     * @since 0.1.0
     */
    @SafeVarargs
    public final void addColumns(@Nullable SFunction<@NonNull T, ?>... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            // noinspection NullableProblems
            this.addColumns(Arrays.asList(columns));
        }
    }

    /**
     * 添加查询字段
     *
     * @param columns 查询字段
     * @since 0.1.0
     */
    public final void addColumns(@Nullable Collection<@NonNull SFunction<@NonNull T, ?>> columns) {
        if (CollectionUtils.isNotEmpty(columns)) {
            if (CollectionUtils.isEmpty(this.selectColumns)) {
                this.selectColumns = new LinkedHashSet<>();
            }
            this.selectColumns.addAll(columns);
        }
    }

    /**
     * 转换动态查询字段
     *
     * @return 动态查询字段
     * @since 0.1.0
     */
    public final String[] toDynamicSelectColumns() {
        if (CollectionUtils.isEmpty(this.selectColumns)) {
            ResolvableType resolvableType = ResolvableType.forClass(this.getClass());
            Class<?> resolve = resolvableType.getGeneric(0).resolve();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(resolve);
            if (Objects.nonNull(tableInfo)) {
                List<TableFieldInfo> fieldList = tableInfo.getFieldList();
                String[] result = new String[fieldList.size()];
                for (int i = 0; i < fieldList.size(); i++) {
                    result[i] = fieldList.get(i).getProperty();
                }
                return result;
            }
        }
        return toSelectColumns();
    }

    /**
     * 转换查询字段
     *
     * @return 查询字段
     * @since 0.1.0
     */
    public final SFunction<@NonNull T, ?>[] toSelectFunctions() {
        if (CollectionUtils.isEmpty(this.selectColumns)) {
            return null;
        }
        // noinspection unchecked
        return this.selectColumns.toArray(new SFunction[0]);
    }

    /**
     * 转换查询字段
     *
     * @return 查询字段
     * @since 0.1.0
     */
    public final String[] toSelectColumns() {
        if (CollectionUtils.isEmpty(selectColumns)) {
            return null;
        }
        int index = 0;
        String[] columns = new String[selectColumns.size()];
        for (SFunction<T, ?> selectColumn : selectColumns) {
            LambdaMeta meta = LambdaUtils.extract(selectColumn);
            String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
            Class<?> instantiatedClass = meta.getInstantiatedClass();
            Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(instantiatedClass);
            ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
            Assert.notNull(columnCache, "can not find lambda cache for this property [%s] of entity [%s]",
                    fieldName, instantiatedClass.getName());
            columns[index++] = columnCache.getColumn();
        }
        return columns;
    }
}
