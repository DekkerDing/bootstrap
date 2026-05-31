package io.github.DekkerDing.customer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.DekkerDing.customer.repository.jpa")
@ConditionalOnProperty(prefix = "customer-robot.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class JpaRepositoryConfig {
}
