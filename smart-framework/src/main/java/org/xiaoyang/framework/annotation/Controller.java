package org.xiaoyang.framework.annotation;

import java.lang.annotation.*;

/**
 * 控制器注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
}
