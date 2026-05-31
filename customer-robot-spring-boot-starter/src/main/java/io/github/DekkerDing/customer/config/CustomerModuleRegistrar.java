package io.github.DekkerDing.customer.config;

import io.github.DekkerDing.customer.domain.annotation.EnableCustomerRobot;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 客服机器人模块注册器
 * Customer Robot Module Registrar
 */
public class CustomerModuleRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableCustomerRobot.class.getName());

        if (attributes != null && attributes.containsKey("modules")) {
            Object[] modulesArray = (Object[]) attributes.get("modules");

            if (modulesArray.length == 0) {
                registerAllModules(registry);
            } else {
                registerSpecifiedModules(registry, modulesArray);
            }
        } else {
            registerAllModules(registry);
        }
    }

    private void registerAllModules(BeanDefinitionRegistry registry) {
        registerModule(registry, "customer");
        registerModule(registry, "conversation");
        registerModule(registry, "message");
        registerModule(registry, "session");
    }

    private void registerSpecifiedModules(BeanDefinitionRegistry registry, Object[] modules) {
        for (Object module : modules) {
            String moduleName = module.toString().toLowerCase();
            switch (moduleName) {
                case "customer":
                    registerModule(registry, "customer");
                    break;
                case "conversation":
                    registerModule(registry, "conversation");
                    break;
                case "message":
                    registerModule(registry, "message");
                    break;
                case "session":
                    registerModule(registry, "session");
                    break;
            }
        }
    }

    private void registerModule(BeanDefinitionRegistry registry, String moduleName) {
        System.out.println("Registering Customer Robot module: " + moduleName);
    }
}
