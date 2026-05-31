package io.github.DekkerDing.ecommerce.config;

import io.github.DekkerDing.ecommerce.domain.annotation.EnableECommerce;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 电商自动配置类
 * E-commerce Auto Configuration
 * <p>
 * 根据配置和注解自动装配电商相关组件
 * Automatically assemble e-commerce components based on configuration and annotations
 * </p>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "e-commerce",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
@Import({
    JpaRepositoryConfig.class,
    MybatisConfig.class
})
@ComponentScan(basePackages = {
    "io.github.DekkerDing.ecommerce.service",
    "io.github.DekkerDing.ecommerce.repository",
    "io.github.DekkerDing.ecommerce.api"
})
public class ECommerceAutoConfiguration {

    public ECommerceAutoConfiguration() {
        // 自动配置构造函数 / Auto configuration constructor
    }
}
