package org.xiaoyang.framework.aspcet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xiaoyang.framework.annotation.Aspect;
import org.xiaoyang.framework.annotation.Controller;
import org.xiaoyang.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;
    @Override
    public void before(Class<?> clazz, Method method, Object... params) throws Throwable {
        LOGGER.info("================= begin ==================");
        LOGGER.info(String.format("class: %s", clazz.getName()));
        LOGGER.info(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> clazz, Method method, Object... params) throws Throwable {
        LOGGER.info(String.format("time : %dms", System.currentTimeMillis() - begin));
        LOGGER.info("================= end ==================");
    }
}
