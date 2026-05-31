package com.example.robot;

import io.github.DekkerDing.customer.domain.annotation.EnableCustomerRobot;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 客服机器人示例应用
 * Customer Robot Sample Application
 */
@SpringBootApplication
@EnableCustomerRobot
public class CustomerRobotSampleApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(CustomerRobotSampleApplication.class, args);
    }
}
