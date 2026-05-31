# Configuration Guide / 配置说明指南

本文档详细说明了 Bootstrap 行业解决方案的所有配置选项。

This document describes in detail all configuration options for the Bootstrap Industry Solution Collection.

---

## E-commerce Starter Configuration / 电商 Starter 配置

### Persistence Configuration / 持久化配置

#### 基本配置 / Basic Configuration

```yaml
e-commerce:
  # 持久化配置 / Persistence configuration
  persistence:
    # 持久化类型：jpa, mybatis, auto（默认） / Persistence type: jpa, mybatis, auto (default)
    type: auto
    # 是否启用持久化 / Whether to enable persistence
    enabled: true
```

#### 配置说明 / Configuration Description

| 属性 / Property | 类型 / Type | 默认值 / Default | 说明 / Description |
|---|---|---|---|
| `e-commerce.persistence.type` | String | `auto` | 持久化类型 / Persistence type |
| `e-commerce.persistence.enabled` | Boolean | `true` | 是否启用持久化 / Enable persistence |

---

### Module Configuration / 模块配置

```yaml
e-commerce:
  modules:
    # 商品模块 / Product module
    product:
      enabled: true
    # 订单模块 / Order module
    order:
      enabled: true
    # 支付模块 / Payment module
    payment:
      enabled: true
    # 用户模块 / User module
    user:
      enabled: true
```

#### 模块说明 / Module Description

| 模块 / Module | 功能 / Features | 依赖 / Dependencies |
|---|---|---|
| `product` | 商品管理、分类管理 / Product & category management | `persistence` |
| `order` | 订单管理、状态流转 / Order management & status flow | `product`, `persistence` |
| `payment` | 支付处理、回调管理 / Payment handling & callback | `order`, `persistence` |
| `user` | 用户管理、地址管理 / User & address management | `persistence` |

---

### API Configuration / API 配置

```yaml
e-commerce:
  api:
    # API 基础路径 / API base path
    basePath: /api
    # 是否启用 CORS / Enable CORS
    corsEnabled: true
    # 最大分页大小 / Maximum page size
    maxPageSize: 100
```

---

## Customer Robot Starter Configuration / 客服机器人 Starter 配置

### Persistence Configuration / 持久化配置

```yaml
customer-robot:
  persistence:
    type: auto
    enabled: true
```

---

### Module Configuration / 模块配置

```yaml
customer-robot:
  modules:
    # 客户模块 / Customer module
    customer:
      enabled: true
    # 对话模块 / Conversation module
    conversation:
      enabled: true
    # 消息模块 / Message module
    message:
      enabled: true
    # 会话模块 / Session module
    session:
      enabled: true
```

---

## Advanced Configuration / 高级配置

### 自定义数据源 / Custom DataSource

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/custom_database
    username: custom_user
    password: custom_password
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
```

### JPA 特定配置 / JPA Specific Configuration

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none  # 生产环境推荐使用 none / Recommended to use none in production
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
```

### MyBatis 特定配置 / MyBatis Specific Configuration

```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: io.github.DekkerDing.ecommerce.domain
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
    log-level: DEBUG
```

---

## Conditional Assembly / 条件装配

### 通过注解控制 / Control via Annotation

使用 `@EnableECommerce` 和 `@EnableCustomerRobot` 注解的模块参数：

Use module parameters of `@EnableECommerce` and `@EnableCustomerRobot` annotations:

```java
@EnableECommerce(modules = {
    EnableECommerce.Module.PRODUCT,
    EnableECommerce.Module.ORDER
})
```

### 通过配置文件控制 / Control via Configuration File

```yaml
e-commerce:
  enabled: true  # 全局开关 / Global switch
  modules:
    product:
      enabled: false  # 禁用商品模块 / Disable product module
```

---

## Example Configurations / 示例配置

### 最小化配置 / Minimal Configuration

```yaml
# application.yml
e-commerce:
  enabled: true
  persistence:
    type: auto

spring:
  datasource:
    url: jdbc:h2:mem:testdb
```

### 生产环境配置 / Production Configuration

```yaml
# application-prod.yml
e-commerce:
  enabled: true
  persistence:
    type: jpa
    enabled: true
  modules:
    product:
      enabled: true
    order:
      enabled: true
    payment:
      enabled: true
    user:
      enabled: true
  api:
    basePath: /api/v1
    corsEnabled: true
    maxPageSize: 50

spring:
  datasource:
    url: jdbc:mysql://prod-db-server:3306/ecommerce_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    hikari:
      maximum-pool-size: 30
      minimum-idle: 10
      connection-timeout: 30000

  jpa:
    hibernate:
      ddl-auto: none
    show-sql: false
```

---

## Troubleshooting / 故障排除

### 常见问题 / Common Issues

#### 1. JPA 和 MyBatis 同时启用怎么办？/ What if both JPA and MyBatis are enabled?

将 `persistence.type` 设置为 `auto`，系统会自动检测 classpath 中的依赖并选择合适的一个。

Set `persistence.type` to `auto`, the system will automatically detect dependencies in classpath and choose the appropriate one.

#### 2. 如何禁用特定模块？/ How to disable specific modules?

通过配置文件或注解两种方式：

Via configuration file or annotation:

```yaml
# 配置文件方式 / Configuration file way
e-commerce:
  modules:
    payment:
      enabled: false
```

```java
// 注解方式 / Annotation way
@EnableECommerce(modules = {
    EnableECommerce.Module.PRODUCT,
    EnableECommerce.Module.ORDER
    // 不包含 PAYMENT / Excluding PAYMENT
})
```

#### 3. 数据库连接失败怎么办？/ What if database connection fails?

检查以下配置：

Check the following configurations:

1. 数据库服务是否启动 / Database service started
2. 用户名密码是否正确 / Username and password correct
3. JDBC URL 是否正确 / JDBC URL correct
4. 驱动类是否匹配 / Driver class matches

---

更多问题请参考 [Getting Started](getting-started.md)。

For more questions, please refer to [Getting Started](getting-started.md).
