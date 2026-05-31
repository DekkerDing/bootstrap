package org.apache.ibatis.annotations;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis Options
 * MyBatis Options 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Options {
    boolean useCache() default true;
    boolean flushCache() default false;
    boolean useGeneratedKeys() default false;
    String keyProperty() default "";
    int timeout() default -1;
}
