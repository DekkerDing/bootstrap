# Getting Started / 快速开始指南

## Introduction / 简介

欢迎使用 Bootstrap 行业解决方案集合！本指南将帮助您快速开始使用我们的 Spring Boot Starter 组件。

Welcome to the Bootstrap Industry Solution Collection! This guide will help you quickly get started with our Spring Boot Starter components.

---

## Project Overview / 项目概述

本项目提供两个独立的 Spring Boot Starter：

This project provides two independent Spring Boot Starters:

- **e-business-spring-boot-starter** / **电商解决方案 Starter**：完整的电商业务功能
- **customer-robot-spring-boot-starter** / **客服机器人解决方案 Starter**：完整的客服坐席机器人功能

---

## Quick Start / 快速开始

### 1. Add Dependency / 添加依赖

在您的 Spring Boot 项目的 `build.gradle` 或 `pom.xml` 中添加相应的依赖：

Add the corresponding dependency to your Spring Boot project's `build.gradle` or `pom.xml`:

#### Gradle:
```gradle
// 电商 Starter / E-commerce Starter
implementation 'io.github.DekkerDing:e-business-spring-boot-starter:1.0.0'

// 客服机器人 Starter / Customer Robot Starter
implementation 'io.github.DekkerDing:customer-robot-spring-boot-starter:1.0.0'
```

#### Maven:
```xml
<!-- 电商 Starter / E-commerce Starter -->
<dependency>
    <groupId>io.github.DekkerDing</groupId>
    <artifactId>e-business-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- 客服机器人 Starter / Customer Robot Starter -->
<dependency>
    <groupId>io.github.DekkerDing</groupId>
    <artifactId>customer-robot-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

### 2. Enable Module / 启用模块

在您的配置类上添加相应的 `@Enable` 注解：

Add the corresponding `@Enable` annotation to your configuration class:

#### 启用电商功能 / Enable E-commerce:
```java
import io.github.DekkerDing.ecommerce.domain.annotation.EnableECommerce;

@SpringBootApplication
@EnableECommerce
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### 启用客服机器人 / Enable Customer Robot:
```java
import io.github.DekkerDing.customer.domain.annotation.EnableCustomerRobot;

@SpringBootApplication
@EnableCustomerRobot
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

### 3. Configure Database / 配置数据库

在 `application.yml` 或 `application.properties` 中配置数据库连接：

Configure database connection in `application.yml` or `application.properties`:

#### application.yml:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

# 选择持久化类型 / Choose persistence type
e-commerce:
  persistence:
    type: jpa  # 可选: jpa, mybatis, auto / Optional: jpa, mybatis, auto
  modules:
    product:
      enabled: true
    order:
      enabled: true
    payment:
      enabled: true
    user:
      enabled: true
```

---

### 4. Run Database Scripts / 运行数据库脚本

在数据库中执行相应的建表脚本：

Execute the table creation scripts in your database:

#### MySQL:
```bash
mysql -u root -p your_database < src/main/resources/sql/mysql/schema-mysql.sql
mysql -u root -p your_database < src/main/resources/sql/data-sample.sql
```

#### PostgreSQL:
```bash
psql -U postgres -d your_database -f src/main/resources/sql/postgresql/schema-postgres.sql
psql -U postgres -d your_database -f src/main/resources/sql/data-sample.sql
```

---

## First API Call / 第一次 API 调用

现在您可以开始使用 API 了！

Now you can start using the APIs!

### 示例：创建商品 / Example: Create Product

```bash
curl -X POST http://localhost:8080/api/products \\
  -H "Content-Type: application/json" \\
  -d '{
    "name": "MacBook Pro",
    "description": "High-performance laptop",
    "price": 1299.99,
    "stock": 50,
    "status": "ACTIVE",
    "categoryId": 1
  }'
```

### 示例：创建客服对话 / Example: Create Customer Conversation

```bash
curl -X POST http://localhost:8080/api/conversations \\
  -H "Content-Type: application/json" \\
  -d '{
    "customerId": 1,
    "title": "产品咨询",
    "priority": "NORMAL"
  }'
```

---

## Module Configuration / 模块配置

### 选择性启用模块 / Selectively Enable Modules

您可以选择性地启用特定模块：

You can selectively enable specific modules:

```java
@EnableECommerce(modules = {
    EnableECommerce.Module.PRODUCT,
    EnableECommerce.Module.ORDER
})
```

### 切换持久化方式 / Switch Persistence Type

#### 使用 JPA / Use JPA:
```yaml
e-commerce:
  persistence:
    type: jpa
```

#### 使用 MyBatis / Use MyBatis:
```yaml
e-commerce:
  persistence:
    type: mybatis
```

#### 自动检测 / Auto Detection:
```yaml
e-commerce:
  persistence:
    type: auto  # 自动根据 classpath 检测 / Auto-detect based on classpath
```

---

## Next Steps / 下一步

- 查看 [配置说明文档](configuration.md) 了解更多配置选项
- 查看 [API 文档](api.md) 了解所有可用的 API
- 查看示例应用了解完整的使用案例

---

## Need Help? / 需要帮助？

如有问题，请提交 Issue 或联系维护团队。

If you have questions, please submit an Issue or contact the maintenance team.
