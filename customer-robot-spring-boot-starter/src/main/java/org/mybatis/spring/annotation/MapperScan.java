package org.mybatis.spring.annotation;

import java.lang.annotation.*;

/**
 * Stub annotation for MyBatis MapperScan
 * MyBatis MapperScan 注解的存根
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MapperScan {
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
