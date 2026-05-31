package io.github.DekkerDing.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 客服机器人配置属性类
 * Customer Robot Configuration Properties
 */
@Configuration
@EnableConfigurationProperties(CustomerProperties.class)
public class CustomerPropertiesConfig {
}
