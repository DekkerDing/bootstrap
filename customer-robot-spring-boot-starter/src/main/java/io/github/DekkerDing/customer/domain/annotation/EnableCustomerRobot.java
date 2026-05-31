package io.github.DekkerDing.customer.domain.annotation;

import io.github.DekkerDing.customer.config.CustomerModuleRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用客服机器人功能注解
 * Enable Customer Robot Annotation
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({CustomerModuleRegistrar.class})
public @interface EnableCustomerRobot {

    /**
     * 要启用的模块
     * Modules to enable
     */
    Module[] modules() default {};

    enum Module {
        /**
         * 客户模块
         * Customer Module
         */
        CUSTOMER,

        /**
         * 对话模块
         * Conversation Module
         */
        CONVERSATION,

        /**
         * 消息模块
         * Message Module
         */
        MESSAGE,

        /**
         * 会话模块
         * Session Module
         */
        SESSION
    }
}
