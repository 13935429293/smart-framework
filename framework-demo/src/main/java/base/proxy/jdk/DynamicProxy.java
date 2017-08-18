package base.proxy.jdk;

import base.proxy.stat.iface.Hello;
import base.proxy.stat.impl.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类，需要有接口
 */
public class DynamicProxy implements InvocationHandler {

    /**
     * 目标对象
     */
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    private void before() {
        System.out.println("Before");
    }

    private void end() {
        System.out.println("End");
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        end();

        return result;
    }

    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(hello);

        Hello helloProxy = dynamicProxy.getProxy();

        helloProxy.say("World");
    }
}
