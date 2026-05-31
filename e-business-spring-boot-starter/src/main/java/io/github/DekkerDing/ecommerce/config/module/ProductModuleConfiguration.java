package io.github.DekkerDing.ecommerce.config.module;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 商品模块配置类
 * Product Module Configuration
 * <p>
 * 配置商品相关组件的装配
 * Configures the assembly of product-related components
 * </p>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "e-commerce.modules.product",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class ProductModuleConfiguration {

    public ProductModuleConfiguration() {
        // 商品模块配置 / Product module configuration
    }
}
