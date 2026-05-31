# Bootstrap Industry Solution Collection

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java Version](https://img.shields.io/badge/java-8+-brightgreen.svg)](https://www.oracle.com/java/technologies/javase/javase8/)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-2.6.14-brightgreen.svg)](https://spring.io/projects/spring-boot)

> 开箱即用的行业解决方案 Spring Boot Starter 集合<br>
> Ready-to-use industry solution Spring Boot Starter collection

---

## 📋 项目简介 / Project Overview

Bootstrap 是一个为 Java 企业级应用设计的行业解决方案集合，旨在通过提供开箱即用的 Spring Boot Starter 组件，简化项目开发复杂度，提高开发效率。

Bootstrap is a collection of industry solutions designed for enterprise Java applications, aiming to simplify project development complexity and improve development efficiency by providing ready-to-use Spring Boot Starter components.

### 🎯 核心特性 / Core Features

- **📦 模块化设计** / **Modular Design**: 每个行业解决方案独立打包，按需引入
- **🔌 双轨数据访问** / **Dual-track Data Access**: 支持 JPA 和 MyBatis，自动检测
- **🎛️ 条件装配** / **Conditional Assembly**: 基于注解和配置的灵活控制
- **📝 双语文档** / **Bilingual Documentation**: 完整的中英双语文档
- **✅ 开箱即用** / **Ready-to-use**: 引入依赖 + 添加注解即可使用

---

## 🚀 快速开始 / Quick Start

### Maven / Gradle

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

### 启用模块 / Enable Modules

```java
@SpringBootApplication
@EnableECommerce
@EnableCustomerRobot
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

详细文档请查看：[Getting Started Guide](docs/getting-started.md)

For detailed documentation, see: [Getting Started Guide](docs/getting-started.md)

---

## 📦 模块介绍 / Module Introduction

### 1. e-business-spring-boot-starter

完整的电商业务解决方案，提供商品、订单、支付、用户等核心功能。

Complete e-commerce business solution with core features like products, orders, payments, and users.

#### 核心功能 / Core Features:
- 📦 **商品管理 / Product Management**: 商品 CRUD、分类管理、库存控制
- 📋 **订单管理 / Order Management**: 订单创建、状态流转、金额计算
- 💳 **支付管理 / Payment Management**: 支付处理、回调管理、签名验证
- 👤 **用户管理 / User Management**: 用户信息、地址管理

**REST API 基础路径**: `/api`

### 2. customer-robot-spring-boot-starter

完整的客服坐席机器人解决方案，提供客户、对话、消息、会话等核心功能。

Complete customer service robot solution with core features like customers, conversations, messages, and sessions.

#### 核心功能 / Core Features:
- 👥 **客户管理 / Customer Management**: 客户信息、状态管理
- 💬 **对话管理 / Conversation Management**: 对话创建、客服分配、对话关闭
- 📨 **消息管理 / Message Management**: 消息发送、已读状态、消息历史
- 🔐 **会话管理 / Session Management**: 登录会话、会话验证、过期处理

**REST API 基础路径**: `/api`

---

## 🏗️ 架构设计 / Architecture Design

本项目采用分层架构设计，确保代码的可维护性和扩展性。

This project adopts a layered architecture design to ensure code maintainability and extensibility.

```
┌─────────────────────────────────────────────────────────┐
│                    REST API Layer                       │
│                  (Controller + @RestController)            │
├─────────────────────────────────────────────────────────┤
│                  Service Layer                          │
│                  (Business Logic + @Service)              │
├─────────────────────────────────────────────────────────┤
│                Repository Abstraction Layer               │
│              (Framework-agnostic Interfaces)                │
├──────────────────────┬────────────────────────────────┤
│                       │                                       │
│   JPA Implementation    │       MyBatis Implementation           │
│  (@Entity + JpaRepository) │     (@Mapper + XML)               │
├──────────────────────┴────────────────────────────────┤
│              Database / Database                             │
│              (MySQL / PostgreSQL)                          │
└─────────────────────────────────────────────────────────┘
```

---

## 📖 文档 / Documentation

- [Getting Started](docs/getting-started.md) - 快速开始指南
- [Configuration Guide](docs/configuration.md) - 配置说明
- [API Documentation](docs/api.md) - API 接口文档
- [Architecture Documentation](docs/ecommerce-starter-architecture.md) - 架构设计文档

---

## 🔧 配置示例 / Configuration Examples

### 最小化配置 / Minimal Configuration

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb

e-commerce:
  enabled: true
  persistence:
    type: auto
```

### 生产环境配置 / Production Configuration

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

e-commerce:
  persistence:
    type: jpa
  modules:
    product:
      enabled: true
    order:
      enabled: true
```

---

## 🧪 测试 / Testing

```bash
# 运行测试 / Run tests
./gradlew test

# 运行特定测试 / Run specific tests
./gradlew test --tests io.github.DekkerDing.ecommerce.service.ProductServiceTest
```

---

## 📝 开发规范 / Development Standards

本项目遵循以下开发规范：

This project follows the following development standards:

1. **📝 双语文档规范** / **Bilingual Documentation**: 所有注释使用中英双语格式
2. **📂 目录结构规范** / **Directory Structure**: 严格按照分层架构组织代码
3. **🔧 依赖管理规范** / **Dependency Management**: 使用 MapStruct 处理对象映射
4. **✅ 代码质量规范** / **Code Quality**: 遵循阿里巴巴 Java 开发手册

---

## 🌟 Star History

[![Star History Chart](https://api.star-history.com/#app-DekkerDing/repo-bootstrap)]

---

## 📄 License

本项目采用 Apache License 2.0 开源协议。

This project is licensed under the Apache License 2.0.

---

## 🤝 Contributing

欢迎贡献代码！请查看 [CONTRIBUTING.md](CONTRIBUTING.md) 了解详情。

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details.

---

## 📧 Contact

如有问题或建议，请提交 Issue。

If you have questions or suggestions, please submit an Issue.

- **作者 / Author**: DekkerDing
- **邮箱 / Email**: [提交 Issue](https://github.com/DekkerDing/bootstrap/issues)

---

## 🙏 Acknowledgments

感谢以下开源项目的启发和支持：

Thanks to the following open source projects for inspiration and support:

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis](https://mybatis.org/)
- [MapStruct](https://mapstruct.org/)

---

**⭐ 如果这个项目对你有帮助，请给一个 Star！**

**⭐ If this project helps you, please give it a Star!**
