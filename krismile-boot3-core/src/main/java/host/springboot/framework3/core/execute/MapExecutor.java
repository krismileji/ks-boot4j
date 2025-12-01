package host.springboot.framework3.core.execute;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 集合执行器
 *
 * <p>-------------------------------- 方法简述 --------------------------------</p>
 * <ul>
 *     <li><b>dynamicExecute</b> - 动态执行集合值</li>
 *     <li><b>dynamicPut</b> - 动态添加集合值</li>
 *     <li><b>dynamicPutAll</b> - 动态添加集合值</li>
 *     <li><b>dynamicGet</b> - 动态获取集合值</li>
 *     <li><b>dynamicIncrement</b> - 动态递增集合值</li>
 *     <li><b>dynamicDecrement</b> - 动态递减集合值</li>
 *     <li><b>dynamicOffset</b> - 动态偏移集合值</li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
public class MapExecutor {

    /**
     * 私有构造器
     *
     * @since 0.1.0
     */
    private MapExecutor() {
    }

    // -------------------------------- Execute --------------------------------

    /**
     * 动态执行
     *
     * @param target            目标源
     * @param key               目标源键
     * @param value             目标源值
     * @param condition         执行条件
     * @param function          函数式接口
     * @param nullValueCallback 当目标源值为 {@code null} 时的回调方法
     * @param <K>               目标源键类型
     * @param <V>               内部值类型
     * @param <M>               目标源类型
     * @since 0.1.0
     */
    public static <K, V, M extends Map<K, V>> void dynamicExecute(
            M target, K key, V value,
            Function<V, Boolean> condition,
            BiFunction<V, V, V> function,
            Supplier<V> nullValueCallback) {
        if (condition.apply(value)) {
            V innerValue = target.get(key);
            if (Objects.isNull(innerValue) && Objects.nonNull(nullValueCallback)) {
                innerValue = nullValueCallback.get();
            }
            target.put(key, function.apply(value, innerValue));
        }
    }

    // -------------------------------- Put --------------------------------

    /**
     * 动态添加集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Collection<Object>>}
     *
     * @param target                      目标源
     * @param targetKey                   目标源键
     * @param innerCollectionValue        内部集合元素值
     * @param innerCollectionNullCallback 当内部集合为 {@code null} 时的回调方法
     * @param <K>                         目标源键类型
     * @param <ICV>                       内部集合元素类型
     * @param <IC>                        内部集合类型
     * @param <M>                         目标源类型
     * @since 0.1.0
     */
    public static <K, ICV,
            IC extends Collection<ICV>,
            M extends Map<K, IC>> void dynamicPut(
            M target, K targetKey, ICV innerCollectionValue,
            Supplier<IC> innerCollectionNullCallback) {
        IC collectionValue = target.get(targetKey);
        if (Objects.isNull(collectionValue)) {
            collectionValue = innerCollectionNullCallback.get();
        }
        collectionValue.add(innerCollectionValue);
        target.put(targetKey, collectionValue);
    }

    /**
     * 动态添加集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Object>>}
     *
     * @param target               目标源
     * @param targetKey            目标源键
     * @param innerMapKey          内部Map键
     * @param innerMapValue        内部Map值
     * @param innerMapNullCallback 当内部Map为 {@code null} 时的回调方法
     * @param <K>                  目标源键类型
     * @param <IMV>                内部Map值类型
     * @param <IMK>                内部Map键类型
     * @param <IM>                 内部Map类型
     * @param <M>                  目标源类型
     * @since 0.1.0
     */
    public static <K, IMV, IMK,
            IM extends Map<IMK, IMV>,
            M extends Map<K, IM>> void dynamicPut(
            M target, K targetKey, IMK innerMapKey, IMV innerMapValue,
            Supplier<IM> innerMapNullCallback) {
        IM mapValue = target.get(targetKey);
        if (Objects.isNull(mapValue)) {
            mapValue = innerMapNullCallback.get();
        }
        mapValue.put(innerMapKey, innerMapValue);
        target.put(targetKey, mapValue);
    }

    /**
     * 动态添加集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Collection<Object>>>}
     *
     * @param target                  目标源
     * @param targetKey               目标源键
     * @param innerKey                内部键
     * @param innerMapCollectionValue 内部Map集合元素值
     * @param nullValueCallback       当值为 {@code null} 时的回调方法
     * @param innerNullValueCallback  当内部值为 {@code null} 时的回调方法
     * @param <K>                     目标源键类型
     * @param <IMCV>                  内部Map集合值类型
     * @param <IMK>                   内部Map键类型
     * @param <IMC>                   内部Map集合类型
     * @param <IM>                    内部Map类型
     * @param <M>                     目标源类型
     * @since 0.1.0
     */
    public static <K, IMCV, IMK,
            IMC extends Collection<IMCV>,
            IM extends Map<IMK, IMC>,
            M extends Map<K, IM>> void dynamicPut(
            M target, K targetKey, IMK innerKey, IMCV innerMapCollectionValue,
            Supplier<IM> nullValueCallback,
            Supplier<IMC> innerNullValueCallback) {
        IM mapValue = target.get(targetKey);
        if (Objects.isNull(mapValue)) {
            mapValue = nullValueCallback.get();
        }
        IMC innerCollectionValue = mapValue.get(innerKey);
        if (Objects.isNull(innerCollectionValue)) {
            innerCollectionValue = innerNullValueCallback.get();
        }
        innerCollectionValue.add(innerMapCollectionValue);
        mapValue.put(innerKey, innerCollectionValue);
        target.put(targetKey, mapValue);
    }

    // -------------------------------- Put All --------------------------------

    /**
     * 动态添加集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Collection<Object>>}
     *
     * @param target                      目标源
     * @param targetKey                   目标源键
     * @param innerCollectionValue        内部集合值
     * @param innerCollectionNullCallback 当内部集合为 {@code null} 时的回调方法
     * @param <K>                         目标源键类型
     * @param <ICV>                       内部集合元素类型
     * @param <IC>                        内部集合类型
     * @param <M>                         目标源类型
     * @since 0.1.0
     */
    public static <K, ICV,
            IC extends Collection<ICV>,
            M extends Map<K, IC>> void dynamicPutAll(
            M target, K targetKey, IC innerCollectionValue,
            Supplier<IC> innerCollectionNullCallback) {
        IC collectionValue = target.get(targetKey);
        if (Objects.isNull(collectionValue)) {
            collectionValue = innerCollectionNullCallback.get();
        }
        collectionValue.addAll(innerCollectionValue);
        target.put(targetKey, collectionValue);
    }

    /**
     * 动态添加集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Collection<Object>>>}
     *
     * @param target                         目标源
     * @param targetKey                      目标源键
     * @param innerMapKey                    内部Map键
     * @param innerMapCollectionValue        内部Map集合元素值
     * @param innerMapNullCallback           当内部Map为 {@code null} 时的回调方法
     * @param innerMapCollectionNullCallback 当内部Map集合为 {@code null} 时的回调方法
     * @param <K>                            目标源键类型
     * @param <IMCV>                         内部Map集合元素类型
     * @param <IMK>                          内部Map键类型
     * @param <IMC>                          内部Map集合类型
     * @param <IM>                           内部Map类型
     * @param <M>                            目标源类型
     * @since 0.1.0
     */
    public static <K, IMCV, IMK,
            IMC extends Collection<IMCV>,
            IM extends Map<IMK, IMC>,
            M extends Map<K, IM>> void dynamicPutAll(
            M target, K targetKey, IMK innerMapKey, IMC innerMapCollectionValue,
            Supplier<IM> innerMapNullCallback,
            Supplier<IMC> innerMapCollectionNullCallback) {
        IM mapValue = target.get(targetKey);
        if (Objects.isNull(mapValue)) {
            mapValue = innerMapNullCallback.get();
        }
        IMC innerCollectionValue = mapValue.get(innerMapKey);
        if (Objects.isNull(innerCollectionValue)) {
            innerCollectionValue = innerMapCollectionNullCallback.get();
        }
        innerCollectionValue.addAll(innerMapCollectionValue);
        mapValue.put(innerMapKey, innerCollectionValue);
        target.put(targetKey, mapValue);
    }

    // -------------------------------- Get --------------------------------

    /**
     * 动态获取集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Object>>}
     *
     * @param target      目标源
     * @param targetKey   目标源键
     * @param innerMapKey 内部Map键
     * @param <K>         目标源键类型
     * @param <IMV>       内部Map值类型
     * @param <IMK>       内部Map键类型
     * @param <IM>        内部Map类型
     * @param <M>         目标源类型
     * @return 内部Map值
     * @since 0.1.0
     */
    public static <K, IMV, IMK,
            IM extends Map<IMK, IMV>,
            M extends Map<K, IM>> IMV dynamicGet(
            M target, K targetKey, IMK innerMapKey) {
        IM mapValue = target.get(targetKey);
        if (Objects.isNull(mapValue)) {
            return null;
        }
        return mapValue.get(innerMapKey);
    }

    /**
     * 动态获取集合值
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Collection<Object>>>}
     *
     * @param target               目标源
     * @param targetKey            目标源键
     * @param innerMapKey          内部Map键
     * @param innerMapNullCallback 当内部Map为 {@code null} 时的回调方法
     * @param <K>                  目标源键类型
     * @param <IMCV>               内部Map集合值元素类型
     * @param <IMK>                内部Map键类型
     * @param <IMC>                内部Map集合类型
     * @param <IM>                 内部Map类型
     * @param <M>                  目标源类型
     * @return 内部Map值
     * @since 0.1.0
     */
    public static <K, IMCV, IMK,
            IMC extends Collection<IMCV>,
            IM extends Map<IMK, IMC>,
            M extends Map<K, IM>> IMC dynamicGet(
            M target, K targetKey, IMK innerMapKey,
            Supplier<IMC> innerMapNullCallback) {
        IM mapValue = target.get(targetKey);
        if (Objects.isNull(mapValue)) {
            return innerMapNullCallback.get();
        }
        IMC innerCollectionValue = mapValue.get(innerMapKey);
        if (Objects.isNull(innerCollectionValue)) {
            return innerMapNullCallback.get();
        }
        return innerCollectionValue;
    }

    // -------------------------------- Increment Decrement --------------------------------

    /**
     * 动态递增
     *
     * <p>适用数据结构 - {@code Map<Object, Integer>}
     *
     * @param target    目标源
     * @param targetKey 键
     * @param <K>       目标源键类型
     * @param <M>       目标源类型
     * @since 0.1.0
     */
    public static <K, M extends Map<K, Integer>> void dynamicIncrement(M target, K targetKey) {
        dynamicOffset(target, targetKey, 1);
    }

    /**
     * 动态递增
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Integer>>}
     *
     * @param target               目标源
     * @param targetKey            键
     * @param innerMapKey          内部Map键
     * @param innerMapNullCallback 当内部Map为 {@code null} 时的回调方法
     * @param <K>                  目标源键类型
     * @param <IMK>                内部Map键类型
     * @param <IM>                 内部Map类型
     * @param <M>                  目标源类型
     * @since 0.1.0
     */
    public static <K, IMK,
            IM extends Map<IMK, Integer>,
            M extends Map<K, IM>> void dynamicIncrement(
            M target, K targetKey, IMK innerMapKey,
            Supplier<IM> innerMapNullCallback) {
        dynamicOffset(target, targetKey, innerMapKey, 1, innerMapNullCallback);
    }

    /**
     * 动态递减
     *
     * <p>适用数据结构 - {@code Map<Object, Integer>}
     *
     * @param target    目标源
     * @param targetKey 键
     * @param <K>       目标源键类型
     * @param <M>       目标源类型
     * @since 0.1.0
     */
    public static <K, M extends Map<K, Integer>> void dynamicDecrement(M target, K targetKey) {
        dynamicOffset(target, targetKey, -1);
    }

    /**
     * 动态递减
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Integer>>}
     *
     * @param target               目标源
     * @param targetKey            键
     * @param innerMapKey          内部Map键
     * @param innerMapNullCallback 当内部Map为 {@code null} 时的回调方法
     * @param <K>                  目标源键类型
     * @param <IMK>                内部Map键类型
     * @param <IM>                 内部Map类型
     * @param <M>                  目标源类型
     * @since 0.1.0
     */
    public static <K, IMK,
            IM extends Map<IMK, Integer>,
            M extends Map<K, IM>> void dynamicDecrement(
            M target, K targetKey, IMK innerMapKey,
            Supplier<IM> innerMapNullCallback) {
        dynamicOffset(target, targetKey, innerMapKey, -1, innerMapNullCallback);
    }

    /**
     * 动态递增或递减
     *
     * <p>适用数据结构 - {@code Map<Object, Integer>}
     *
     * @param target    目标源
     * @param targetKey 键
     * @param value     递增或递减的值, 递减时小于 {@code 0}
     * @param <K>       目标源键类型
     * @param <M>       目标源类型
     * @since 0.1.0
     */
    public static <K, M extends Map<K, Integer>> void dynamicOffset(M target, K targetKey, int value) {
        Integer number = target.get(targetKey);
        if (Objects.isNull(number)) {
            number = 0;
        }
        target.put(targetKey, number + value);
    }

    /**
     * 动态递增或递减
     *
     * <p>适用数据结构 - {@code Map<Object, Map<Object, Integer>>}
     *
     * @param target               目标源
     * @param targetKey            键
     * @param innerMapKey          内部Map键
     * @param value                递增或递减的值, 递减时小于 {@code 0}
     * @param innerMapNullCallback 当内部Map为 {@code null} 时的回调方法
     * @param <K>                  目标源键类型
     * @param <IMK>                内部Map键类型
     * @param <IM>                 内部Map类型
     * @param <M>                  目标源类型
     * @since 0.1.0
     */
    public static <K, IMK,
            IM extends Map<IMK, Integer>,
            M extends Map<K, IM>> void dynamicOffset(
            M target, K targetKey, IMK innerMapKey, int value,
            Supplier<IM> innerMapNullCallback) {
        IM mapValue = target.get(targetKey);
        if (Objects.isNull(mapValue)) {
            mapValue = innerMapNullCallback.get();
        }
        Integer number = mapValue.get(innerMapKey);
        if (Objects.isNull(number)) {
            number = 0;
        }
        mapValue.put(innerMapKey, number + value);
        target.put(targetKey, mapValue);
    }
}
