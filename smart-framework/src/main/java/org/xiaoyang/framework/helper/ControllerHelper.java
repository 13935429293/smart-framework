package org.xiaoyang.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.xiaoyang.framework.annotation.Action;
import org.xiaoyang.framework.bean.Handler;
import org.xiaoyang.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * controller 工具类
 *
 *  把 Request 与 Handler 对应 映射
 *  通过 request 获取 handler 对象
 */
public final class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> clazz : controllerClassSet) {
                Method[] methods = clazz.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String requestPath = action.value();
                            String requestMethod = action.method();
                            Request request = new Request(requestPath, requestMethod);
                            Handler handler = new Handler(clazz, method);
                            ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestPath, String requestMethod) {
        Request request = new Request(requestPath, requestMethod);
        return ACTION_MAP.get(request);
    }
}
