package org.apache.ibatis.annotations;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis ResultMap
 * MyBatis ResultMap 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResultMap {
    String value();
}
