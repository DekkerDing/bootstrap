package io.github.DekkerDing.ecommerce.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Repository 配置类
 * JPA Repository Configuration Class
 * <p>
 * 当配置中选择 JPA 作为持久化方式时，启用 JPA Repository 支持
 * Enables JPA Repository support when JPA is selected as persistence type
 * </p>
 */
@Configuration
@EnableJpaRepositories(basePackages = "io.github.DekkerDing.ecommerce.repository.jpa")
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class JpaRepositoryConfig {
}
