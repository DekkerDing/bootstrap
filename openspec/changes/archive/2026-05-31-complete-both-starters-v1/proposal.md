## Why

<!-- EN: This project is a collection of industry solutions designed to provide one-stop industry solution capabilities. -->
<!-- CN: 该项目是一个行业解决方案集合，旨在提供一站式的行业解决方案能力。 -->

<!-- EN: Currently, both Starters (e-commerce and customer service robot) are in initial skeleton state, lacking complete business functionality implementation and flexible data access layer support. -->
<!-- CN: 当前两个 Starter（电商和客服机器人）处于初始骨架状态，缺少完整的业务功能实现和灵活的数据访问层支持。 -->

<!-- EN: To achieve the goal of "add dependency + @Enable annotation for out-of-the-box use", we need to complete the v1 version of basic functionality, including dual-track database support (JPA + MyBatis), conditional assembly mechanism, and end-to-end implementation of core business processes. -->
<!-- CN: 为了实现"引入依赖 + @Enable 注解即可开箱即用"的目标，需要完善 v1 版本的基础功能，包括双轨数据库支持（JPA + MyBatis）、条件装配机制、以及核心业务流程的端到端实现。 -->

**中文说明**：
本变更旨在完善两个行业解决方案 Starter 的 v1 版本，实现"开箱即用"的目标。核心是实现双轨数据库支持，让用户可以根据自己的技术栈选择 JPA 或 MyBatis，同时通过条件装配实现自动检测和模块化加载。

---

## What Changes

### 电商 Starter (e-business-spring-boot-starter) / E-commerce Starter

<!-- EN: Data access layer refactoring: Implement dual-track support for JPA and MyBatis, automatically detect user environment through conditional assembly and enable corresponding implementation -->
<!-- CN: 数据访问层重构：实现 JPA 和 MyBatis 双轨支持，通过条件装配自动检测用户环境并启用对应实现 -->
- **数据访问层重构** / Data Access Layer Refactoring: 实现 JPA 和 MyBatis 双轨支持 / Implement dual-track support for JPA and MyBatis

<!-- EN: Enable mechanism enhancement: Implement @EnableECommerce annotation, support module-level on-demand loading (product, order, payment, user) -->
<!-- CN: Enable 机制完善：实现 @EnableECommerce 注解，支持模块级按需加载 -->
- **Enable 机制完善** / Enable Mechanism Enhancement: 模块级按需加载 / Module-level on-demand loading

<!-- EN: Core business modules -->
<!-- CN: 核心业务模块 -->
- **核心业务模块** / Core Business Modules:
  - 商品模块 / Product Module: 商品 CRUD、分类管理 / Product CRUD, category management
  - 订单模块 / Order Module: 创建订单、状态流转、订单查询 / Order creation, status flow, order query
  - 支付模块 / Payment Module: 支付回调处理、支付状态查询 / Payment callback, payment status query
  - 用户模块 / User Module: 基础用户信息、地址管理 / User info, address management

- **REST API 层** / REST API Layer: 提供完整的 RESTful 接口 / Provide complete RESTful interfaces
- **数据库脚本** / Database Scripts: 提供 MySQL/PostgreSQL 建表脚本 / Provide MySQL/PostgreSQL table creation scripts
- **配置体系** / Configuration System: 支持 YAML 配置驱动的模块开关 / Support YAML configuration-driven module switches

### 客服机器人 Starter (customer-robot-spring-boot-starter) / Customer Service Robot Starter

<!-- EN: Same structure as e-commerce starter, adapted for customer service robot domain -->
<!-- CN: 与电商 Starter 相同结构，适配客服机器人领域 -->
- **数据访问层重构** / Data Access Layer Refactoring: JPA + MyBatis 双轨支持 / Dual-track support
- **Enable 机制完善** / Enable Mechanism Enhancement: 模块级按需加载 / Module-level on-demand loading

- **核心业务模块** / Core Business Modules:
  - 对话管理模块 / Conversation Module: 会话创建、消息收发、会话历史 / Session creation, message exchange, conversation history
  - 知识匹配模块 / Knowledge Module: FAQ 管理、关键词匹配、自动回复 / FAQ management, keyword matching, auto-reply
  - 路由规则模块 / Routing Module: 规则配置、条件匹配、转接坐席 / Rule configuration, condition matching, agent transfer
  - 坐席管理模块 / Agent Module: 坐席状态、会话分配、转接功能 / Agent status, session assignment, transfer functionality

- **REST API 层** / REST API Layer: 完整的 RESTful 接口 / Complete RESTful interfaces
- **数据库脚本** / Database Scripts: MySQL/PostgreSQL 支持 / MySQL/PostgreSQL support
- **配置体系** / Configuration System: YAML 配置驱动 / YAML configuration-driven

### 通用改进 / General Improvements

<!-- EN: Conditional assembly strategy: Implement three-layer control (auto-assembly → Enable annotation → configuration file) -->
<!-- CN: 条件装配策略：实现三层控制（自动装配 → Enable 注解 → 配置文件） -->
- **条件装配策略** / Conditional Assembly Strategy: 三层控制 / Three-layer control

<!-- EN: Test system: Unit tests + integration tests + sample applications -->
<!-- CN: 测试体系：单元测试 + 集成测试 + 示例应用 -->
- **测试体系** / Test System: 单元测试 + 集成测试 + 示例应用 / Unit tests + integration tests + sample apps

<!-- EN: Documentation: Getting Started, configuration guide, API documentation -->
<!-- CN: 文档完善：Getting Started、配置说明、API 文档 -->
- **文档完善** / Documentation: Getting Started、配置说明、API 文档 / Getting Started, configuration guide, API docs

---

## Capabilities / 能力定义

### New Capabilities / 新增能力

<!-- EN: E-commerce Starter capabilities -->
<!-- CN: 电商 Starter 能力 -->
- `ecommerce-database-access`: <!-- EN -->Data access capability for e-commerce Starter, supporting dual-track JPA and MyBatis implementation <!-- CN -->电商 Starter 的数据访问能力，支持 JPA 和 MyBatis 双轨实现
- `ecommerce-product`: <!-- EN -->Product management capability, including product CRUD and category management <!-- CN -->电商商品管理能力，包括商品 CRUD 和分类管理
- `ecommerce-order`: <!-- EN -->Order management capability, including order creation, status flow, and query <!-- CN -->电商订单管理能力，包括订单创建、状态流转和查询
- `ecommerce-payment`: <!-- EN -->Payment processing capability, including payment callback and status query <!-- CN -->电商支付处理能力，包括支付回调和状态查询
- `ecommerce-user`: <!-- EN -->User management capability, including user info and address management <!-- CN -->电商用户管理能力，包括用户信息和地址管理
- `ecommerce-api`: <!-- EN -->REST API interface capability for e-commerce <!-- CN -->电商 REST API 接口能力

<!-- EN: Customer service robot Starter capabilities -->
<!-- CN: 客服机器人 Starter 能力 -->
- `customer-robot-database-access`: <!-- EN -->Data access capability for customer service robot Starter, supporting dual-track JPA and MyBatis implementation <!-- CN -->客服机器人 Starter 的数据访问能力，支持 JPA 和 MyBatis 双轨实现
- `customer-robot-conversation`: <!-- EN -->Conversation management capability, including session and message management <!-- CN -->客服机器人对话管理能力，包括会话和消息管理
- `customer-robot-knowledge`: <!-- EN -->Knowledge matching capability, including FAQ management and auto-reply <!-- CN -->客服机器人知识匹配能力，包括 FAQ 管理和自动回复
- `customer-robot-routing`: <!-- EN -->Routing rule capability, including rule configuration and agent transfer <!-- CN -->客服机器人路由规则能力，包括规则配置和转接
- `customer-robot-agent`: <!-- EN -->Agent management capability, including agent status and session assignment <!-- CN -->客服机器人坐席管理能力，包括坐席状态和会话分配
- `customer-robot-api`: <!-- EN -->REST API interface capability for customer service robot <!-- CN -->客服机器人 REST API 接口能力

### Modified Capabilities / 修改的能力

<!-- EN: No existing capabilities are modified, this is a brand new implementation. -->
<!-- CN: 无现有能力被修改，这是全新实现。 -->

无 / None

---

## Impact / 影响范围

### Code Structure / 代码结构

<!-- EN: New directories under e-business-spring-boot-starter/src/main/java/io/github/DekkerDing/ecommerce/ -->
<!-- CN: 新增电商 Starter 代码目录 -->
- 新增 `e-business-spring-boot-starter/src/main/java/io/github/DekkerDing/ecommerce/` 下的目录:
  - `service/` - <!-- EN -->Business logic layer <!-- CN -->业务逻辑层
  - `repository/` - <!-- EN -->Data access abstraction interface <!-- CN -->数据访问抽象接口
  - `api/` - <!-- EN -->REST API controllers <!-- CN -->REST API 控制器
  - `jpa/` - <!-- EN -->JPA implementation <!-- CN -->JPA 实现
  - `mybatis/` - <!-- EN -->MyBatis implementation <!-- CN -->MyBatis 实现
  - `config/` - <!-- EN -->Configuration classes (conditional assembly) <!-- CN -->配置类（条件装配）

<!-- EN: Similar structure for customer service robot Starter -->
<!-- CN: 客服机器人 Starter 相同结构 -->
- 新增 `customer-robot-spring-boot-starter/` 类似目录结构

<!-- EN: Modify existing AutoConfiguration and @Enable* annotation implementations -->
<!-- CN: 修改现有的 AutoConfiguration 和 @Enable* 注解实现 -->
- 修改现有的 `AutoConfiguration` 和 `@Enable*` 注解实现

### Dependencies / 依赖变更

<!-- EN: Introduce Spring Boot dependencies for conditional assembly (already present) -->
<!-- CN: 引入条件装配所需的 Spring Boot 依赖（已存在） -->
- 引入条件装配所需的 Spring Boot 依赖（已存在）

<!-- EN: Introduce optional MyBatis and JPA dependencies -->
<!-- CN: 引入可选的 MyBatis 和 JPA 依赖 -->
- 引入 MyBatis 相关依赖（可选）
- 引入 JPA 相关依赖（可选）

<!-- EN: Introduce MapStruct for object conversion -->
<!-- CN: 引入 MapStruct 用于对象转换 -->
- 引入 MapStruct 用于对象转换

<!-- EN: Introduce testing dependencies -->
<!-- CN: 引入测试依赖 -->
- 引入测试依赖

### Configuration / 配置变更

<!-- EN: New application.yml configuration items -->
<!-- CN: 新增 application.yml 配置项 -->
- 新增 `application.yml` 配置项:
  - `e-commerce.modules.*.enabled` - <!-- EN -->E-commerce module switches <!-- CN -->电商模块开关
  - `e-commerce.persistence.type` - <!-- EN -->E-commerce persistence type (auto/jpa/mybatis) <!-- CN -->电商持久化类型
  - `customer-robot.modules.*.enabled` - <!-- EN -->Customer robot module switches <!-- CN -->客服机器人模块开关
  - `customer-robot.persistence.type` - <!-- EN -->Customer robot persistence type <!-- CN -->客服机器人持久化类型

### API Changes / API 变更

<!-- EN: Add approximately 24 REST API endpoints (about 12 per Starter) -->
<!-- CN: 新增约 24 个 REST API 端点（每个 Starter 约 12 个） -->
- 新增约 24 个 REST API 端点（每个 Starter 约 12 个）

<!-- EN: No breaking changes, pure additions -->
<!-- CN: 无破坏性变更，纯新增 -->
- 无破坏性变更，纯新增

### Compatibility / 兼容性

- Spring Boot 2.6.x
- Java 8
- 支持 MySQL 和 PostgreSQL / Support MySQL and PostgreSQL

---

## Design Thinking / 设计思考

**中文说明**：
本提案的核心设计思想是"双轨支持 + 自动检测 + 模块化"。通过抽象接口层隔离业务逻辑和数据访问实现，让用户可以选择 JPA 或 MyBatis。通过条件装配实现自动检测，用户无需配置即可使用。通过模块化设计，用户可以按需启用功能模块，避免不必要的资源消耗。

**EN Explanation**:
The core design philosophy of this proposal is "dual-track support + auto-detection + modularity". Through an abstract interface layer isolating business logic from data access implementation, users can choose between JPA or MyBatis. Through conditional assembly for auto-detection, users can use it without configuration. Through modular design, users can enable functionality modules on-demand, avoiding unnecessary resource consumption.
