package io.github.DekkerDing.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 客服机器人配置属性类
 * Customer Robot Configuration Properties
 */
@ConfigurationProperties(prefix = "customer-robot")
@Validated
public class CustomerProperties {

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
        private String type = "auto";
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
        @Valid
        private Customer customer = new Customer();

        @Valid
        private Conversation conversation = new Conversation();

        @Valid
        private Message message = new Message();

        @Valid
        private Session session = new Session();

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Conversation getConversation() {
            return conversation;
        }

        public void setConversation(Conversation conversation) {
            this.conversation = conversation;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public static class Customer {
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        public static class Conversation {
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        public static class Message {
            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        public static class Session {
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
        private String basePath = "/api";
        private boolean corsEnabled = true;

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
    }
}
