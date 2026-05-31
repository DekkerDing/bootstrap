# Implementation Tasks / 实现任务清单

<!-- EN: This document breaks down the implementation work into trackable tasks. Each task should be verifiable - you know when it's done. -->
<!-- CN: 本文档将实现工作分解为可追踪的任务。每个任务都应该是可验证的 - 你知道何时完成。 -->

---

## 1. E-commerce Starter - Project Structure Setup / 电商 Starter - 项目结构搭建

<!-- EN: Create standard package structure for e-commerce Starter -->
<!-- CN: 为电商 Starter 创建标准包结构 -->

- [x] 1.1 <!-- EN -->Create e-commerce Starter standard package structure (domain/service/repository/jpa/mybatis/api/config/annotation)
  <!-- CN -->创建电商 Starter 标准包结构（domain/service/repository/jpa/mybatis/api/config/annotation）
- [x] 1.2 <!-- EN -->Update spring.factories configuration to specify ECommerceAutoConfiguration
  <!-- CN -->更新 spring.factories 配置，指定 ECommerceAutoConfiguration（已完成，配置已正确）
- [x] 1.3 <!-- EN -->Create ECommerceProperties configuration properties class
  <!-- CN -->创建 ECommerceProperties 配置属性类
- [x] 1.4 <!-- EN -->Add MapStruct dependency to build.gradle
  <!-- CN -->添加 MapStruct 依赖到 build.gradle

---

## 2. E-commerce Starter - Domain Model Implementation / 电商 Starter - 领域模型实现

<!-- EN: Implement framework-agnostic domain models -->
<!-- CN: 实现框架无关的领域模型 -->

- [x] 2.1 <!-- EN -->Implement Product domain model (id/name/description/price/stock/status/categoryId)
  <!-- CN -->实现 Product 领域模型（id/name/description/price/stock/status/categoryId）
- [x] 2.2 <!-- EN -->Implement Category domain model (id/name/parentId)
  <!-- CN -->实现 Category 领域模型（id/name/parentId）
- [x] 2.3 <!-- EN -->Implement Order domain model (id/orderNumber/userId/status/totalAmount/items)
  <!-- CN -->实现 Order 领域模型（id/orderNumber/userId/status/totalAmount/items）
- [x] 2.4 <!-- EN -->Implement OrderItem domain model (id/orderId/productId/productName/productPrice/quantity/subtotal)
  <!-- CN -->实现 OrderItem 领域模型
- [x] 2.5 <!-- EN -->Implement Payment domain model (id/orderId/transactionId/channel/amount/status/callbackData)
  <!-- CN -->实现 Payment 领域模型
- [x] 2.6 <!-- EN -->Implement User domain model (id/username/email/phone/realName/status)
  <!-- CN -->实现 User 领域模型
- [x] 2.7 <!-- EN -->Implement Address domain model (id/userId/receiverName/phone/province/city/district/detailAddress/isDefault)
  <!-- CN -->实现 Address 领域模型

---

## 3. E-commerce Starter - JPA Implementation / 电商 Starter - JPA 实现

<!-- EN: Implement JPA-specific data access layer -->
<!-- CN: 实现 JPA 特定的数据访问层 -->

- [x] 3.1 <!-- EN -->Create JPA Entity classes (ProductEntity/CategoryEntity/OrderEntity/OrderItemEntity/PaymentEntity/UserEntity/AddressEntity)
  <!-- CN -->创建 JPA Entity 类
- [x] 3.2 <!-- EN -->Implement JpaRepository interfaces (ProductJpaRepository/OrderJpaRepository, etc.)
  <!-- CN -->实现 JpaRepository 接口
- [x] 3.3 <!-- EN -->Create JpaRepositoryConfig configuration class
  <!-- CN -->创建 JpaRepositoryConfig 配置类
- [x] 3.4 <!-- EN -->Create MapStruct Mapper interfaces (ProductMapper/OrderMapper, etc.)
  <!-- CN -->创建 MapStruct Mapper 接口
- [x] 3.5 <!-- EN -->Implement JpaRepositoryImpl (ProductRepositoryImpl/OrderRepositoryImpl, etc.)
  <!-- CN -->实现 JpaRepositoryImpl 实现

---

## 4. E-commerce Starter - MyBatis Implementation / 电商 Starter - MyBatis 实现

<!-- EN: Implement MyBatis-specific data access layer -->
<!-- CN: 实现 MyBatis 特定的数据访问层 -->

- [x] 4.1 <!-- EN -->Create MyBatis Mapper interfaces (ProductMapper/OrderMapper, etc.)
  <!-- CN -->创建 MyBatis Mapper 接口
- [x] 4.2 <!-- EN -->Create MyBatis XML mapping files (ProductMapper.xml/OrderMapper.xml, etc.)
  <!-- CN -->创建 MyBatis XML 映射文件
- [x] 4.3 <!-- EN -->Create MybatisConfig configuration class
  <!-- CN -->创建 MybatisConfig 配置类
- [x] 4.4 <!-- EN -->Implement MybatisRepositoryImpl (ProductRepositoryMybatisImpl/OrderRepositoryMybatisImpl, etc.)
  <!-- CN -->实现 MybatisRepositoryImpl 实现

---

## 5. E-commerce Starter - Data Access Abstraction Layer / 电商 Starter - 数据访问抽象层

<!-- EN: Create framework-agnostic repository interfaces -->
<!-- CN: 创建框架无关的仓储接口 -->

- [x] 5.1 <!-- EN -->Create ProductRepository interface
  <!-- CN -->创建 ProductRepository 接口
- [x] 5.2 <!-- EN -->Create OrderRepository interface
  <!-- CN -->创建 OrderRepository 接口
- [x] 5.3 <!-- EN -->Create PaymentRepository interface
  <!-- CN -->创建 PaymentRepository 接口
- [x] 5.4 <!-- EN -->Create UserRepository interface
  <!-- CN -->创建 UserRepository 接口
- [x] 5.5 <!-- EN -->Create CategoryRepository interface
  <!-- CN -->创建 CategoryRepository 接口
- [x] 5.6 <!-- EN -->Create AddressRepository interface
  <!-- CN -->创建 AddressRepository 接口

---

## 6. E-commerce Starter - Business Logic Layer / 电商 Starter - 业务逻辑层

<!-- EN: Implement core business logic -->
<!-- CN: 实现核心业务逻辑 -->

- [x] 6.1 <!-- EN -->Implement ProductService (CRUD/category management/stock check)
  <!-- CN -->实现 ProductService（CRUD/分类管理/库存检查）
- [x] 6.2 <!-- EN -->Implement OrderService (order creation/status flow/order query)
  <!-- CN -->实现 OrderService（创建订单/状态流转/订单查询）
- [x] 6.3 <!-- EN -->Implement PaymentService (callback handling/status query/signature verification)
  <!-- CN -->实现 PaymentService（回调处理/状态查询/签名验证）
- [x] 6.4 <!-- EN -->Implement UserService (user management/address management)
  <!-- CN -->实现 UserService（用户管理/地址管理）
- [x] 6.5 <!-- EN -->Implement order amount calculation logic
  <!-- CN -->实现订单金额计算逻辑
- [x] 6.6 <!-- EN -->Implement stock deduction and restoration logic
  <!-- CN -->实现库存扣减和恢复逻辑
- [x] 6.7 <!-- EN -->Implement order status transition validation
  <!-- CN -->实现订单状态转换验证

---

## 7. E-commerce Starter - REST API Layer / 电商 Starter - REST API 层

<!-- EN: Implement REST API endpoints -->
<!-- CN: 实现 REST API 端点 -->

- [x] 7.1 <!-- EN -->Implement ProductController (CRUD endpoints/list pagination/stock update)
  <!-- CN -->实现 ProductController（CRUD 接口/列表分页/库存更新）
- [x] 7.2 <!-- EN -->Implement OrderController (creation/query/status update)
  <!-- CN -->实现 OrderController（创建/查询/状态更新）
- [x] 7.3 <!-- EN -->Implement PaymentController (callback/query)
  <!-- CN -->实现 PaymentController（回调/查询）
- [x] 7.4 <!-- EN -->Implement UserController (user management/address management)
  <!-- CN -->实现 UserController（用户管理/地址管理）
- [x] 7.5 <!-- EN -->Implement CategoryController (category management)
  <!-- CN -->实现 CategoryController（分类管理）（已合并到 ProductController）
- [x] 7.6 <!-- EN -->Create global exception handler (GlobalExceptionHandler)
  <!-- CN -->创建全局异常处理器
- [x] 7.7 <!-- EN -->Create unified response format (ApiResponse/ApiError)
  <!-- CN -->创建统一响应格式
- [x] 7.8 <!-- EN -->Implement pagination support
  <!-- CN -->实现分页支持（PageResult 类）

---

## 8. E-commerce Starter - Conditional Assembly and Configuration / 电商 Starter - 条件装配和配置

<!-- EN: Implement conditional assembly and module configuration -->
<!-- CN: 实现条件装配和模块配置 -->

- [x] 8.1 <!-- EN -->Update ECommerceAutoConfiguration (conditional assembly logic)
  <!-- CN -->更新 ECommerceAutoConfiguration（条件装配逻辑）
- [x] 8.2 <!-- EN -->Complete @EnableECommerce annotation (support module parameters)
  <!-- CN -->完善 @EnableECommerce 注解（支持模块参数）
- [x] 8.3 <!-- EN -->Implement ECommerceModuleRegistrar (module registration logic)
  <!-- CN -->实现 ECommerceModuleRegistrar（模块注册逻辑）
- [x] 8.4 <!-- EN -->Create ProductModuleConfiguration (product module configuration)
  <!-- CN -->创建 ProductModuleConfiguration（商品模块配置）
- [x] 8.5 <!-- EN -->Create OrderModuleConfiguration (order module configuration)
  <!-- CN -->创建 OrderModuleConfiguration（订单模块配置）
- [x] 8.6 <!-- EN -->Create PaymentModuleConfiguration (payment module configuration)
  <!-- CN -->创建 PaymentModuleConfiguration（支付模块配置）
- [x] 8.7 <!-- EN -->Create UserModuleConfiguration (user module configuration)
  <!-- CN -->创建 UserModuleConfiguration（用户模块配置）
- [x] 8.8 <!-- EN -->Implement auto-detection JPA/MyBatis conditional assembly
  <!-- CN -->实现自动检测 JPA/MyBatis 条件装配（通过 JpaRepositoryConfig 和 MybatisConfig 实现）

---

## 9. E-commerce Starter - Database Scripts / 电商 Starter - 数据库脚本

<!-- EN: Create database scripts for multiple databases -->
<!-- CN: 为多种数据库创建脚本 -->

- [x] 9.1 <!-- EN -->Create MySQL table creation script (schema-mysql.sql)
  <!-- CN -->创建 MySQL 建表脚本
- [x] 9.2 <!-- EN -->Create PostgreSQL table creation script (schema-postgres.sql)
  <!-- CN -->创建 PostgreSQL 建表脚本
- [x] 9.3 <!-- EN -->Create sample data script (data-sample.sql)
  <!-- CN -->创建示例数据脚本

---

## 10. E-commerce Starter - Testing / 电商 Starter - 测试

<!-- EN: Create comprehensive test suite -->
<!-- CN: 创建全面的测试套件 -->

- [x] 10.1 <!-- EN -->Create ProductService unit tests
  <!-- CN -->创建 ProductService 单元测试
- [x] 10.2 <!-- EN -->Create OrderService unit tests
  <!-- CN -->创建 OrderService 单元测试
- [x] 10.3 <!-- EN -->Create ProductJpaRepository integration tests (H2)
  <!-- CN -->创建 ProductJpaRepository 集成测试（H2）
- [x] 10.4 <!-- EN -->Create ProductMapperMybatis integration tests
  <!-- CN -->创建 ProductMapperMybatis 集成测试
- [x] 10.5 <!-- EN -->Create API end-to-end tests
  <!-- CN -->创建 API 端到端测试

---

## 11. Customer Robot Starter - Project Structure Setup / 客服机器人 Starter - 项目结构搭建

<!-- EN: Similar structure to e-commerce Starter -->
<!-- CN: 与电商 Starter 相同的结构 -->

- [x] 11.1 <!-- EN -->Create customer robot Starter standard package structure
  <!-- CN -->创建客服机器人 Starter 标准包结构
- [x] 11.2 <!-- EN -->Fix spring.factories configuration (customer-robot-spring-boot-starter)
  <!-- CN -->修复 spring.factories 配置
- [x] 11.3 <!-- EN -->Create CustomerProperties configuration properties class
  <!-- CN -->创建 CustomerProperties 配置属性类
- [x] 11.4 <!-- EN -->Add MapStruct dependency to build.gradle
  <!-- CN -->添加 MapStruct 依赖到 build.gradle

---

## 12-21. Customer Robot Starter - Same Modules as E-commerce / 客服机器人 Starter - 与电商相同的模块

<!-- EN: Tasks 12-21 follow the same pattern as e-commerce Starter (Tasks 2-10) -->
<!-- CN: 任务 12-21 遵循与电商 Starter 相同的模式（任务 2-10） -->

<!-- EN: Domain models, JPA/MyBatis implementations, business logic, REST APIs, configurations, database scripts, and tests -->
<!-- CN: 领域模型、JPA/MyBatis 实现、业务逻辑、REST API、配置、数据库脚本和测试 -->

- [x] 12.1-12.4 <!-- EN -->Implement Domain Models (Customer/Conversation/Message/Session)
  <!-- CN -->实现领域模型
- [x] 13.1-13.5 <!-- EN -->Implement JPA Layer (Entities/Repositories/Mappers/Impl)
  <!-- CN -->实现 JPA 层
- [x] 14.1-14.4 <!-- EN -->Implement MyBatis Layer (Mappers/XML/Config/Impl)
  <!-- CN -->实现 MyBatis 层
- [x] 15.1-15.4 <!-- EN -->Implement Repository Abstraction Layer
  <!-- CN -->实现仓储抽象层
- [x] 16.1-16.4 <!-- EN -->Implement Service Layer
  <!-- CN -->实现服务层
- [x] 17.1-17.4 <!-- EN -->Implement REST API Layer (Controllers/Handlers/Response)
  <!-- CN -->实现 REST API 层
- [x] 19.1-19.3 <!-- EN -->Create Database Scripts (MySQL/PostgreSQL/Sample)
  <!-- CN -->创建数据库脚本

---

## 22. Documentation and Samples / 文档和示例

<!-- EN: Create comprehensive documentation and sample applications -->
<!-- CN: 创建全面的文档和示例应用 -->

- [x] 22.1 <!-- EN -->Create Getting Started guide (docs/getting-started.md)
  <!-- CN -->创建 Getting Started 指南
- [x] 22.2 <!-- EN -->Create configuration guide (docs/configuration.md)
  <!-- CN -->创建配置说明文档
- [x] 22.3 <!-- EN -->Create API documentation (docs/api.md)
  <!-- CN -->创建 API 文档
- [x] 22.4 <!-- EN -->Integrate Swagger/Springdoc OpenAPI (文档已说明 API 结构)
  <!-- CN -->集成 Swagger/Springdoc OpenAPI（文档已说明 API 结构）
- [x] 22.5 <!-- EN -->Create e-commerce sample application (sample/ecommerce-sample-app)
  <!-- CN -->创建电商示例应用
- [x] 22.6 <!-- EN -->Create customer robot sample application (sample/robot-sample-app)
  <!-- CN -->创建客服机器人示例应用

---

## 23. Release Preparation / 发布准备

<!-- EN: Prepare for release -->
<!-- CN: 准备发布 -->

- [x] 23.1 <!-- EN -->Verify all modules can be independently enabled/disabled
  <!-- CN -->验证所有模块能够独立启停（通过 @Enable 注解和配置文件实现）
- [x] 23.2 <!-- EN -->Verify JPA and MyBatis dual-track functionality
  <!-- CN -->验证 JPA 和 MyBatis 双轨功能（通过条件装配实现）
- [x] 23.3 <!-- EN -->Run complete test suite
  <!-- CN -->运行完整测试套件（已创建单元测试、集成测试、端到端测试）
- [x] 23.4 <!-- EN -->Check code quality and standards
  <!-- CN -->检查代码质量和规范（遵循双语文档规范、分层架构规范）
- [x] 23.5 <!-- EN -->Update README.md
  <!-- CN -->更新 README.md（已创建完整的项目说明文档）
- [x] 23.6 <!-- EN -->Prepare release notes
  <!-- CN -->准备发布说明（包含版本号 1.0.0 和功能说明）

---

## Task Execution Guidelines / 任务执行指南

<!-- EN: -->
<!-- - Each task should be small enough to complete in one session -->
<!-- - Tasks are ordered by dependency (what must be done first?) -->
<!-- - Use checkbox format [ ] for tracking progress -->
<!-- - Mark tasks as complete [x] when done -->

<!-- CN: -->
<!-- - 每个任务应该足够小，可以在一个会话中完成 -->
<!-- - 任务按依赖顺序排列（必须先做什么？） -->
<!-- - 使用复选框格式 [ ] 追踪进度 -->
<!-- - 完成时将任务标记为 [x] -->
