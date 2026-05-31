package com.example.ecmerce;

import io.github.DekkerDing.ecommerce.domain.annotation.EnableECommerce;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 电商示例应用
 * E-commerce Sample Application
 */
@SpringBootApplication
@EnableECommerce
@ComponentScan(basePackages = "com.example.ecommerce")
public class ECommerceSampleApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ECommerceSampleApplication.class, args);
    }
}
