package org.xiaoyang.framework.proxy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    public void begin() {
    }

    public void before(Class<?> clazz, Method method, Object... params) throws Throwable {
    }

    public boolean intercept(Class<?> clazz, Method method, Object... params) throws Throwable {
        return true;
    }

    public void after(Class<?> clazz, Method method, Object... params) throws Throwable {
    }

    public void error(Class<?> clazz, Method method, Object... params) throws Throwable {
    }

    public void end() {
    }

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodArgs();

        begin();

        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            after(targetClass, targetMethod, methodParams);
            throw e;
        } finally {
            end();
        }

        return result;
    }
}
