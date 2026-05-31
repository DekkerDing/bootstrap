package io.github.DekkerDing.ecommerce.config;

import io.github.DekkerDing.ecommerce.domain.annotation.EnableECommerce;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 电商模块注册器
 * E-commerce Module Registrar
 * <p>
 * 根据 @EnableECommerce 注解的参数注册相应的模块
 * Registers corresponding modules based on @EnableECommerce annotation parameters
 * </p>
 */
public class ECommerceModuleRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取注解属性 / Get annotation attributes
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableECommerce.class.getName());

        if (attributes != null && attributes.containsKey("modules")) {
            Object[] modulesArray = (Object[]) attributes.get("modules");

            if (modulesArray.length == 0) {
                // 如果没有指定模块，则启用所有模块
                // If no modules specified, enable all modules
                registerAllModules(registry);
            } else {
                // 只注册指定的模块
                // Only register specified modules
                registerSpecifiedModules(registry, modulesArray);
            }
        } else {
            // 默认启用所有模块
            // Enable all modules by default
            registerAllModules(registry);
        }
    }

    /**
     * 注册所有模块
     * Register all modules
     */
    private void registerAllModules(BeanDefinitionRegistry registry) {
        // 商品模块 / Product module
        registerModule(registry, "product");

        // 订单模块 / Order module
        registerModule(registry, "order");

        // 支付模块 / Payment module
        registerModule(registry, "payment");

        // 用户模块 / User module
        registerModule(registry, "user");
    }

    /**
     * 注册指定的模块
     * Register specified modules
     */
    private void registerSpecifiedModules(BeanDefinitionRegistry registry, Object[] modules) {
        for (Object module : modules) {
            String moduleName = module.toString().toLowerCase();
            switch (moduleName) {
                case "product":
                    registerModule(registry, "product");
                    break;
                case "order":
                    registerModule(registry, "order");
                    break;
                case "payment":
                    registerModule(registry, "payment");
                    break;
                case "user":
                    registerModule(registry, "user");
                    break;
            }
        }
    }

    /**
     * 注册单个模块
     * Register single module
     */
    private void registerModule(BeanDefinitionRegistry registry, String moduleName) {
        // 这里可以添加模块特定的 Bean 定义
        // Add module-specific Bean definitions here
        // 目前仅作标记，实际的 Bean 由条件装配处理
        // Currently just marking, actual beans handled by conditional assembly

        // 记录模块启用日志 / Log module enablement
        System.out.println("Registering E-commerce module: " + moduleName);
    }
}
