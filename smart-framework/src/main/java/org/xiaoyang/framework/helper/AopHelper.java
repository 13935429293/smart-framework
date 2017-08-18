package org.xiaoyang.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xiaoyang.framework.annotation.Aspect;
import org.xiaoyang.framework.proxy.AspectProxy;
import org.xiaoyang.framework.proxy.Proxy;
import org.xiaoyang.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop init failure", e);
        }

    }

    /**
     * 创建目标类与代理类集合的映射关系
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

    /**
     * 创建代理类和目标类集合的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySupper(AspectProxy.class);
        if (CollectionUtils.isNotEmpty(proxyClassSet)) {
            for (Class<?> proxyClazz : proxyClassSet) {
                if (proxyClazz.isAnnotationPresent(Aspect.class)) {
                    Aspect annotation = proxyClazz.getAnnotation(Aspect.class);
                    Set<Class<?>> targetClassSet = createTargetClassSet(annotation);
                    proxyMap.put(proxyClazz, targetClassSet);
                }
            }
        }
        return proxyMap;
    }

    /**
     * 获取所有的切面的目标类的集合
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> result = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(aspect))
            result.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        return result;
    }
}
