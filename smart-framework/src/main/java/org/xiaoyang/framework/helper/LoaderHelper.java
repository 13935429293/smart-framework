package org.xiaoyang.framework.helper;

import org.xiaoyang.framework.util.ClassUtil;

/**
 * 加载相应的helper
 */
public final class LoaderHelper {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz : classList) {
            ClassUtil.loadClass(clazz.getName());
        }
    }
}
