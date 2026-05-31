package io.github.DekkerDing.ecommerce.config.module;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 用户模块配置类
 * User Module Configuration
 * <p>
 * 配置用户相关组件的装配
 * Configures the assembly of user-related components
 * </p>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "e-commerce.modules.user",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class UserModuleConfiguration {

    public UserModuleConfiguration() {
        // 用户模块配置 / User module configuration
    }
}
