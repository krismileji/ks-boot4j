package host.springboot.framework.mybatisplus.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import host.springboot.framework.mybatisplus.domain.BaseDO;
import host.springboot.framework3.core.execute.MapExecutor;
import host.springboot.framework3.core.util.inner.MapUtils;
import host.springboot.framework3.core.util.inner.SpringUtils;
import org.jspecify.annotations.NonNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 封装 MybatisServiceImpl
 *
 * <p>该类为 {@link BaseService} 实现类, 与 {@link BaseService} 联合使用, 以替代 [Mybatis] 中的 {@link ServiceImpl}
 *
 * @param <M> Mapper类
 * @param <T> 实体类
 * @author JiYinchuan
 * @see BaseService
 * @since 0.1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDO<? extends Serializable>>
        extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 构造器
     *
     * @since 0.1.0
     */
    public BaseServiceImpl() {
    }

    /**
     * 解析关联对象
     *
     * @param mapperClass Mapper类
     * @param idAndRelId  ID和关联ID映射
     * @param <ID>        ID类型
     * @param <Domain>    实体类型
     * @param <Mapper>    Mapper类型
     * @return ID和关联对象映射
     * @since 0.1.0
     */
    protected <ID extends Serializable,
            Domain extends BaseDO<ID>,
            Mapper extends BaseMapper<Domain>> Map<ID, Domain> parseRelObject(
            @NonNull Class<Mapper> mapperClass,
            @NonNull Map<@NonNull ID, @NonNull ID> idAndRelId) {
        if (MapUtils.isEmpty(idAndRelId)) {
            return new LinkedHashMap<>();
        }
        Mapper mapper = SpringUtils.getBean(mapperClass);
        Set<ID> allRelIds = new HashSet<>(idAndRelId.values());
        Map<ID, Domain> idAndData = mapper.selectByIds(allRelIds).stream()
                .collect(Collectors.toMap(BaseDO::getId, Function.identity()));
        if (MapUtils.isEmpty(idAndData)) {
            return new LinkedHashMap<>();
        }
        Map<ID, Domain> result = new LinkedHashMap<>();
        for (Map.Entry<ID, ID> entry : idAndRelId.entrySet()) {
            ID id = entry.getKey();
            ID relId = entry.getValue();
            Domain relDomain = idAndData.get(relId);
            if (Objects.nonNull(relDomain)) {
                result.put(id, relDomain);
            }
        }
        return result;
    }

    /**
     * 解析关联对象
     *
     * @param mapperClass Mapper类
     * @param idAndRelIds ID和关联ID映射
     * @param <ID>        ID类型
     * @param <Domain>    实体类型
     * @param <Mapper>    Mapper类型
     * @return ID和关联对象映射
     * @since 0.1.0
     */
    protected <ID extends Serializable,
            Domain extends BaseDO<ID>,
            Mapper extends BaseMapper<Domain>> Map<ID, List<Domain>> parseRelObjects(
            @NonNull Class<Mapper> mapperClass,
            @NonNull Map<@NonNull ID, @NonNull Set<@NonNull ID>> idAndRelIds) {
        if (MapUtils.isEmpty(idAndRelIds)) {
            return new LinkedHashMap<>();
        }
        Mapper mapper = SpringUtils.getBean(mapperClass);
        Set<ID> allRelIds = idAndRelIds.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<ID, Domain> idAndData = mapper.selectByIds(allRelIds).stream()
                .collect(Collectors.toMap(BaseDO::getId, Function.identity()));
        if (MapUtils.isEmpty(idAndData)) {
            return new LinkedHashMap<>();
        }
        Map<ID, List<Domain>> result = new LinkedHashMap<>();
        for (Map.Entry<ID, Set<ID>> entry : idAndRelIds.entrySet()) {
            ID id = entry.getKey();
            Set<ID> relIds = entry.getValue();
            for (ID relId : relIds) {
                Domain relDomain = idAndData.get(relId);
                if (Objects.nonNull(relDomain)) {
                    MapExecutor.dynamicPut(result, id, relDomain, ArrayList::new);
                }
            }
        }
        return result;
    }
}
