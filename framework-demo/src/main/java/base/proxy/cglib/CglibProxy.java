package base.proxy.cglib;

import base.proxy.stat.iface.Hello;
import base.proxy.stat.impl.HelloImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态代理
 */
public class CglibProxy implements MethodInterceptor {

    private static CglibProxy instance = new CglibProxy();

    private CglibProxy() {
    }

    public static CglibProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Enhancer.create(clazz, this);
    }

    private void before() {
        System.out.println("Before");
    }

    private void end() {
        System.out.println("End");
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        end();
        return result;
    }

    public static void main(String[] args) {
        Hello helloProxy = CglibProxy.getInstance().getProxy(HelloImpl.class);
        helloProxy.say("World");
    }
}
