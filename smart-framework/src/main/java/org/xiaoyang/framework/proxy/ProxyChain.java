package org.xiaoyang.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodArgs;

    private List<Proxy> proxyList = new ArrayList<Proxy>();
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy,
                      Object[] methodArgs, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodArgs = methodArgs;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject, methodArgs);
        }
        return result;
    }
}
