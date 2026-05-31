package org.apache.ibatis.annotations;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis Update
 * MyBatis Update 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Update {
    String[] value();
}
