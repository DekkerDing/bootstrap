package io.github.DekkerDing.ecommerce.config.module;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 订单模块配置类
 * Order Module Configuration
 * <p>
 * 配置订单相关组件的装配
 * Configures the assembly of order-related components
 * </p>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "e-commerce.modules.order",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class OrderModuleConfiguration {

    public OrderModuleConfiguration() {
        // 订单模块配置 / Order module configuration
    }
}
