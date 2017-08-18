package org.xiaoyang.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.xiaoyang.framework.annotation.Inject;
import org.xiaoyang.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * ioc 工具类
 */
public final class IocHelper {

    static {
        // 获取所有的bean的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            // 遍历每一个映射关系
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> clazz = entry.getKey();
                Object obj = entry.getValue();
                // 获取class中的所有字段
                Field[] fields = clazz.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(fields)) {
                    // 遍历字段，判断是否有 Inject 注解。是否需要注入
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = field.getType();// 得到成员变量的类型
                            Object beanFieldInstance = beanMap.get(beanFieldClass);// 获取bean的实例
                            if (null != beanFieldInstance) {
                                // set到对应的字段中
                                ReflectionUtil.setField(obj, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
