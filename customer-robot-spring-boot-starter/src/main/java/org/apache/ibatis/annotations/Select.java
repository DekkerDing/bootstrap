package org.apache.ibatis.annotations;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis Select
 * MyBatis Select 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String[] value();
}
