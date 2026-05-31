package io.github.DekkerDing.customer.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "Customer-Robot",ignoreInvalidFields = true)
public class Customer {
}
