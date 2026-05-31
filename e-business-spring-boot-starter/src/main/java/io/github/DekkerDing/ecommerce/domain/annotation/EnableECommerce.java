package io.github.DekkerDing.ecommerce.domain.annotation;

import io.github.DekkerDing.ecommerce.config.ECommerceModuleRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用电商功能注解
 * Enable E-commerce Annotation
 * <p>
 * 添加此注解到配置类以启用电商功能
 * Add this annotation to a configuration class to enable e-commerce features
 * </p>
 * <p>
 * 示例 / Example:
 * <pre>
 * &#64;SpringBootApplication
 * &#64;EnableECommerce(modules = {EnableECommerce.Module.PRODUCT, EnableECommerce.Module.ORDER})
 * public class Application {
 *     public static void main(String[] args) {
 *         SpringApplication.run(Application.class, args);
 *     }
 * }
 * </pre>
 * </p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ECommerceModuleRegistrar.class})
public @interface EnableECommerce {

    /**
     * 要启用的模块
     * Modules to enable
     * <p>
     * 如果不指定，则启用所有模块
     * If not specified, all modules are enabled
     * </p>
     */
    Module[] modules() default {};

    /**
     * 电商模块枚举
     * E-commerce Module Enum
     */
    enum Module {
        /**
         * 商品模块
         * Product Module
         */
        PRODUCT,

        /**
         * 订单模块
         * Order Module
         */
        ORDER,

        /**
         * 支付模块
         * Payment Module
         */
        PAYMENT,

        /**
         * 用户模块
         * User Module
         */
        USER
    }
}
