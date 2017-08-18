package org.xiaoyang.framework.annotation;

import java.lang.annotation.*;

/**
 * 方法注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String value();
    String method() default "get";
}
