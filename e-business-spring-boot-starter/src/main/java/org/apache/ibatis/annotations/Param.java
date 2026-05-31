package org.apache.ibatis.annotations;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis Param
 * MyBatis Param 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}
