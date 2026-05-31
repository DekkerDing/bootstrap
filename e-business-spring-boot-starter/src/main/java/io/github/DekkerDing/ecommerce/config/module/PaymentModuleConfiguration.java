package io.github.DekkerDing.ecommerce.config.module;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 支付模块配置类
 * Payment Module Configuration
 * <p>
 * 配置支付相关组件的装配
 * Configures the assembly of payment-related components
 * </p>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "e-commerce.modules.payment",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class PaymentModuleConfiguration {

    public PaymentModuleConfiguration() {
        // 支付模块配置 / Payment module configuration
    }
}
