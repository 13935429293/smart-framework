package org.xiaoyang.framework.helper;

import org.xiaoyang.framework.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean 助手
 */
public final class BeanHelper {

    /**
     * 定义bean的映射（存放bean类与bean实例的映射）
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<Class<?>, Object>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz : classSet) {
            Object obj = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, obj);
        }
    }

    /**
     * 获取bean map
     *
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz))
            throw new RuntimeException("can not get bean by class: " + clazz);
        return (T) BEAN_MAP.get(clazz);
    }

    /**
     * 设置bean
     *
     * @param clazz
     * @param object
     */
    public static void setBean(Class<?> clazz, Object object) {
        BEAN_MAP.put(clazz, object);
    }
}
