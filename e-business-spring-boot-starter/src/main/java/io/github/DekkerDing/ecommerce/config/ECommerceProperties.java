package io.github.DekkerDing.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 电商 Starter 配置属性类
 * E-commerce Starter Configuration Properties
 *
 * 用于配置电商模块的行为和特性
 * Configures the behavior and features of e-commerce modules
 */
@ConfigurationProperties(prefix = "e-commerce")
@Validated
public class ECommerceProperties {

    /**
     * 持久化配置
     * Persistence configuration
     */
    private Persistence persistence = new Persistence();

    /**
     * 模块配置
     * Module configuration
     */
    @Valid
    private Modules modules = new Modules();

    /**
     * API 配置
     * API configuration
     */
    private Api api = new Api();

    public Persistence getPersistence() {
        return persistence;
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    /**
     * 持久化配置
     * Persistence configuration
     */
    public static class Persistence {
        /**
         * 持久化类型：auto（自动检测）、jpa、mybatis
         * Persistence type: auto (auto-detect), jpa, mybatis
         */
        private String type = "auto";

        /**
         * 是否启用持久化
         * Whether persistence is enabled
         */
        private boolean enabled = true;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    /**
     * 模块配置
     * Module configuration
     */
    public static class Modules {
        /**
         * 商品模块配置
         * Product module configuration
         */
        @Valid
        private Product product = new Product();

        /**
         * 订单模块配置
         * Order module configuration
         */
        @Valid
        private Order order = new Order();

        /**
         * 支付模块配置
         * Payment module configuration
         */
        @Valid
        private Payment payment = new Payment();

        /**
         * 用户模块配置
         * User module configuration
         */
        @Valid
        private User user = new User();

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        /**
         * 商品模块配置
         * Product module configuration
         */
        public static class Product {
            /**
             * 是否启用商品模块
             * Whether product module is enabled
             */
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        /**
         * 订单模块配置
         * Order module configuration
         */
        public static class Order {
            /**
             * 是否启用订单模块
             * Whether order module is enabled
             */
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        /**
         * 支付模块配置
         * Payment module configuration
         */
        public static class Payment {
            /**
             * 是否启用支付模块
             * Whether payment module is enabled
             */
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        /**
         * 用户模块配置
         * User module configuration
         */
        public static class User {
            /**
             * 是否启用用户模块
             * Whether user module is enabled
             */
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }

    /**
     * API 配置
     * API configuration
     */
    public static class Api {
        /**
         * API 基础路径
         * API base path
         */
        private String basePath = "/api";

        /**
         * 是否启用 CORS
         * Whether CORS is enabled
         */
        private boolean corsEnabled = true;

        /**
         * 最大分页大小
         * Maximum page size
         */
        private int maxPageSize = 100;

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }

        public boolean isCorsEnabled() {
            return corsEnabled;
        }

        public void setCorsEnabled(boolean corsEnabled) {
            this.corsEnabled = corsEnabled;
        }

        public int getMaxPageSize() {
            return maxPageSize;
        }

        public void setMaxPageSize(int maxPageSize) {
            this.maxPageSize = maxPageSize;
        }
    }
}
