package org.xiaoyang.framework.annotation;

import java.lang.annotation.*;

/**
 * 依赖注入注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
}
