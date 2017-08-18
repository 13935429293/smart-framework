package org.xiaoyang.framework.helper;

import org.xiaoyang.framework.annotation.Controller;
import org.xiaoyang.framework.annotation.Service;
import org.xiaoyang.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有的类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取service注解的类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取controller注解的类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取service，controller注解的类
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

    /**
     * 获取制定父类（或接口）的所有子类
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySupper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz))
                classSet.add(clazz);
        }
        return classSet;
    }

    /**
     * 根据注解获取所有类的集合
     *
     * @param annotaionClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotaionClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotaionClass))
                classSet.add(clazz);
        }
        return classSet;
    }
}
