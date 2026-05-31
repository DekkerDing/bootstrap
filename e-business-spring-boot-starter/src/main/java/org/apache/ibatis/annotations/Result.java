package org.apache.ibatis.annotations;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis Result
 * MyBatis Result 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Result {
    String property() default "";
    String column() default "";
    String id() default "";
}
