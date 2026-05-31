package io.github.DekkerDing.customer.config;

import io.github.DekkerDing.customer.domain.annotation.EnableCustomerRobot;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 客服机器人模块注册器
 * Customer Robot Module Registrar
 */
public class CustomerModuleRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        EnableCustomerRobot enableRobot = importingClassMetadata.getAnnotation(EnableCustomerRobot.class);

        if (enableRobot != null) {
            EnableCustomerRobot.Module[] modules = enableRobot.modules();

            if (modules.length == 0) {
                registerAllModules(registry);
            } else {
                registerSpecifiedModules(registry, modules);
            }
        }
    }

    private void registerAllModules(BeanDefinitionRegistry registry) {
        registerModule(registry, "customer");
        registerModule(registry, "conversation");
        registerModule(registry, "message");
        registerModule(registry, "session");
    }

    private void registerSpecifiedModules(BeanDefinitionRegistry registry, EnableCustomerRobot.Module[] modules) {
        for (EnableCustomerRobot.Module module : modules) {
            switch (module) {
                case CUSTOMER:
                    registerModule(registry, "customer");
                    break;
                case CONVERSATION:
                    registerModule(registry, "conversation");
                    break;
                case MESSAGE:
                    registerModule(registry, "message");
                    break;
                case SESSION:
                    registerModule(registry, "session");
                    break;
            }
        }
    }

    private void registerModule(BeanDefinitionRegistry registry, String moduleName) {
        System.out.println("Registering Customer Robot module: " + moduleName);
    }
}
