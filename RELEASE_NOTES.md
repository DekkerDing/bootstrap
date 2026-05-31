# Release Notes / 发布说明

## Version 1.0.0

**发布日期 / Release Date**: 2024-01-01  
**版本类型 / Version Type**: Major Release

---

## 🎉 重大发布 / Major Release

这是 Bootstrap 行业解决方案集合的首个正式发布！

This is the first official release of the Bootstrap Industry Solution Collection!

---

## 📦 包含内容 / Included Content

### 1. e-business-spring-boot-starter (电商解决方案)

完整的企业级电商业务功能 Spring Boot Starter。

Complete enterprise-level e-commerce business functionality Spring Boot Starter.

#### 核心模块 / Core Modules:
- ✅ **商品模块 / Product Module**: 商品管理、分类管理、库存控制
- ✅ **订单模块 / Order Module**: 订单创建、状态流转、金额计算
- ✅ **支付模块 / Payment Module**: 支付处理、回调管理、签名验证
- ✅ **用户模块 / User Module**: 用户管理、地址管理

#### 技术特性 / Technical Features:
- ✅ 双轨数据访问：支持 JPA 和 MyBatis
- ✅ 条件装配：基于注解和配置的灵活控制
- ✅ 模块化：各模块可独立启用/禁用
- ✅ 双语文档：完整的中英双语注释

### 2. customer-robot-spring-boot-starter (客服机器人解决方案)

完整的客服坐席机器人功能 Spring Boot Starter。

Complete customer service robot functionality Spring Boot Starter.

#### 核心模块 / Core Modules:
- ✅ **客户模块 / Customer Module**: 客户信息、状态管理
- ✅ **对话模块 / Conversation Module**: 对话管理、客服分配
- ✅ **消息模块 / Message Module**: 消息发送、已读管理
- ✅ **会话模块 / Session Module**: 会话管理、过期处理

#### 技术特性 / Technical Features:
- ✅ 双轨数据访问：支持 JPA 和 MyBatis
- ✅ 条件装配：基于注解和配置的灵活控制
- ✅ 模块化：各模块可独立启用/禁用
- ✅ 双语文档：完整的中英双语注释

---

## 🚀 新特性 / New Features

### 架构特性 / Architecture Features

1. **双轨数据访问层 / Dual-Track Data Access Layer**
   - 同时支持 JPA 和 MyBatis 两种持久化方式
   - 通过配置自动选择或指定持久化类型

2. **条件装配机制 / Conditional Assembly**
   - `@EnableECommerce` 和 `@EnableCustomerRobot` 注解支持
   - 可选择性启用特定模块
   - 自动检测 classpath 依赖

3. **模块化设计 / Modular Design**
   - 每个模块可独立启用/禁用
   - 降低依赖耦合，提高灵活性

4. **双语文档规范 / Bilingual Documentation**
   - 所有代码注释使用中英双语格式
   - 文档文件提供中英文版本

---

## 📋 依赖要求 / Dependency Requirements

### 系统要求 / System Requirements
- Java 8+
- Spring Boot 2.6.14
- MySQL 5.7+ 或 PostgreSQL 12+

### Maven 坐标 / Maven Coordinates

```xml
<dependency>
    <groupId>io.github.DekkerDing</groupId>
    <artifactId>e-business-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>io.github.DekkerDing</groupId>
    <artifactId>customer-robot-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 传递依赖 / Transitive Dependencies

- Spring Boot 2.6.14
- Spring Data JPA
- MyBatis
- MapStruct
- Validation API
- H2 (测试)

---

## 📖 文档 / Documentation

- [Getting Started Guide](../docs/getting-started.md)
- [Configuration Guide](../docs/configuration.md)
- [API Documentation](../docs/api.md)
- [Architecture Documentation](../docs/ecommerce-starter-architecture.md)

---

## 🔧 配置变更 / Configuration Changes

### 1.0.0 版本配置 / Version 1.0.0 Configuration

新增以下配置选项：

Added the following configuration options:

```yaml
e-commerce:
  persistence:
    type: auto  # 新增 / New
  modules:
    product:
      enabled: true  # 新增 / New
    order:
      enabled: true
    payment:
      enabled: true
    user:
      enabled: true
  api:
    basePath: /api  # 新增 / New
```

```yaml
customer-robot:
  persistence:
    type: auto  # 新增 / New
  modules:
    customer:
      enabled: true  # 新增 / New
    conversation:
      enabled: true
    message:
      enabled: true
    session:
      enabled: true
  api:
    basePath: /api  # 新增 / New
```

---

## 🔒 安全特性 / Security Features

- 支持配置化的数据库连接
- 支持自定义 API 基础路径
- 支持模块级别的启用/禁用控制

---

## 🐛 已知问题 / Known Issues

1. 暂不支持 Spring Boot 3.x（计划在后续版本支持）
2. MyBatis 集成测试需要额外配置 H2 数据库
3. Swagger 集成需要手动添加依赖

---

## 🔄 升级指南 / Upgrade Guide

从 0.x 版本升级到 1.0.0：

Upgrading from 0.x to 1.0.0:

1. 更新依赖版本号 / Update dependency version:
   ```xml
   <version>1.0.0</version>
   ```

2. 检查配置文件变更 / Check configuration file changes:
   - 新增 `e-commerce.persistence.type` 配置
   - 新增模块化配置选项

3. 重新编译项目 / Rebuild project:
   ```bash
   ./gradlew clean build
   ```

---

## 📞 获取帮助 / Getting Help

- 📧 提交 Issue: [GitHub Issues](https://github.com/DekkerDing/bootstrap/issues)
- 📖 查看文档: [项目文档](../docs/)
- 💬 讨论: [GitHub Discussions](https://github.com/DekkerDing/bootstrap/discussions)

---

## ✅ 致谢 / Acknowledgments

感谢所有为此版本做出贡献的开发者！

Thanks to all developers who contributed to this release!

---

## 📅 后续计划 / Future Plans

- [ ] Spring Boot 3.x 支持
- [ ] 更多数据库类型支持（Oracle、SQL Server）
- [ ] 分布式事务支持
- [ ] 更多行业解决方案 Starter

---

**🎉 感谢使用 Bootstrap 行业解决方案集合！**

**🎉 Thank you for using the Bootstrap Industry Solution Collection!**
