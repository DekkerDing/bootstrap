## Context / 背景

<!-- EN: Project background and current state -->
<!-- CN: 项目背景和当前状态 -->

### 项目背景 / Project Background

<!-- EN: Bootstrap project is a collection of industry solutions designed to provide one-stop industry solution capabilities. Currently contains two Starters: -->
<!-- CN: Bootstrap 项目是一个行业解决方案集合，旨在提供一站式的行业解决方案能力。当前包含两个 Starter： -->

Bootstrap project is a collection of industry solutions designed to provide one-stop industry solution capabilities. Currently contains two Starters:

1. **e-business-spring-boot-starter** - <!-- EN -->E-commerce solution <!-- CN -->电商解决方案
2. **customer-robot-spring-boot-starter** - <!-- EN -->Customer service robot solution <!-- CN -->客服机器人解决方案

<!-- EN: Project positioning: "Solution Supermarket", each Starter is an independent industry solution, users can introduce on-demand -->
<!-- CN: 项目定位为"解决方案超市"，每个 Starter 都是独立的行业解决方案，用户可以按需引入使用 -->

Project positioning: "Solution Supermarket", each Starter is an independent industry solution, users can introduce on-demand

### 当前状态 / Current State

<!-- EN: Both Starters are currently in initial skeleton state -->
<!-- CN: 两个 Starter 目前处于初始骨架状态 -->

Both Starters are currently in initial skeleton state:

- <!-- EN -->Basic AutoConfiguration and @Enable annotations created <!-- CN -->基础的 AutoConfiguration 和 @Enable 注解已创建
- <!-- EN -->Domain models (ECommerce, Customer) are empty configuration classes <!-- CN -->领域模型（ECommerce、Customer）为空配置类
- <!-- EN -->spring.factories configuration incomplete (customer-robot-spring-boot-starter) <!-- CN -->spring.factories 配置不完整
- <!-- EN -->Missing business logic implementation <!-- CN -->缺少业务逻辑实现
- <!-- EN -->Missing data access layer implementation <!-- CN -->缺少数据访问层实现
- <!-- EN -->Missing REST API layer <!-- CN -->缺少 REST API 层

### 约束条件 / Constraints

1. <!-- EN -->**Database compatibility**: Must support both JPA and MyBatis, cannot force users to use specific framework <!-- CN -->**数据库兼容性**：必须同时支持 JPA 和 MyBatis，不能强制用户使用特定框架
2. <!-- EN -->**Spring Boot version**: Based on Spring Boot 2.6.14, Java 8 <!-- CN -->**Spring Boot 版本**：基于 Spring Boot 2.6.14，Java 8
3. <!-- EN -->**Independent deployment**: Each Starter completely independent, no cross-Starter dependencies <!-- CN -->**独立部署**：每个 Starter 完全独立，无跨 Starter 依赖
4. <!-- EN -->**On-demand loading**: Support module-level feature switches <!-- CN -->**按需加载**：支持模块级别的功能开关
5. <!-- EN -->**Out-of-the-box**: Users only need to add dependency + @Enable annotation to use <!-- CN -->**开箱即用**：用户只需引入依赖 + 添加 @Enable 注解即可使用

### 利益相关者 / Stakeholders

- <!-- EN -->Project maintainers: Need clear module division and maintainable code structure <!-- CN -->项目维护者：需要清晰的模块划分和可维护的代码结构
- <!-- EN -->End users: Need simple, easy-to-use, out-of-the-box solutions <!-- CN -->最终用户：需要简单易用、开箱即用的解决方案
- <!-- EN -->Customers: May use JPA or MyBatis, cannot force choice <!-- CN -->客户：可能使用 JPA 或 MyBatis，不能强制选择

---

## Goals / Non-Goals / 目标与非目标

### Goals / 目标

<!-- EN: What this design aims to achieve -->
<!-- CN: 本设计旨在实现 -->

1. <!-- EN -->**Implement dual-track database support**: Support both JPA and MyBatis, auto-detect user environment and enable corresponding implementation <!-- CN -->**实现双轨数据库支持**：同时支持 JPA 和 MyBatis，自动检测用户环境并启用对应实现
2. <!-- EN -->**Complete Enable mechanism**: Implement three-layer control (auto-assembly → Enable annotation → configuration file) <!-- CN -->**完善 Enable 机制**：实现三层控制
3. <!-- EN -->**Implement v1 core business functionality** <!-- CN -->**实现 v1 核心业务功能**：
   - <!-- EN -->E-commerce: Product, Order, Payment, User management <!-- CN -->电商：商品、订单、支付、用户管理
   - <!-- EN -->Customer robot: Conversation, Knowledge, Routing, Agent management <!-- CN -->客服机器人：对话、知识、路由、坐席管理
4. <!-- EN -->**Provide REST API**: Complete RESTful interfaces <!-- CN -->**提供 REST API**：完整的 RESTful 接口
5. <!-- EN -->**Provide database scripts**: MySQL/PostgreSQL table creation scripts <!-- CN -->**提供数据库脚本**：MySQL/PostgreSQL 建表脚本
6. <!-- EN -->**Establish test system**: Unit tests, integration tests, sample applications <!-- CN -->**建立测试体系**：单元测试、集成测试、示例应用

### Non-Goals / 非目标

<!-- EN: What is explicitly out of scope -->
<!-- CN: 明确不包含的内容 -->

1. <!-- EN -->**Frontend implementation**: v1 does not include frontend pages and components <!-- CN -->**前端实现**：v1 不包含前端页面和组件
2. <!-- EN -->**Cache integration**: v1 does not integrate Redis and other cache solutions <!-- CN -->**缓存集成**：v1 不集成 Redis 等缓存方案
3. <!-- EN -->**Message queue**: v1 does not integrate asynchronous message processing <!-- CN -->**消息队列**：v1 不集成异步消息处理
4. <!-- EN -->**Monitoring and metrics**: v1 does not include Prometheus monitoring integration <!-- CN -->**监控和指标**：v1 不包含 Prometheus 等监控集成
5. <!-- EN -->**Security authentication**: v1 does not implement user authentication and authorization <!-- CN -->**安全认证**：v1 不实现用户认证和鉴权
6. <!-- EN -->**Multi-tenancy support**: v1 does not implement multi-tenant isolation <!-- CN -->**多租户支持**：v1 不实现多租户隔离
7. <!-- EN -->**Internationalization and localization**: v1 only supports Chinese <!-- CN -->**国际化和本地化**：v1 只支持中文

---

## Decisions / 设计决策

<!-- EN: Key design decisions and rationale -->
<!-- CN: 关键设计决策及其理由 -->

### 决策 1：双轨数据库支持架构 / Decision 1: Dual-Track Database Support Architecture

**选择 / Choice**：抽象接口层 + 双实现模式（JPA + MyBatis）+ 条件装配自动选择 / Abstract interface layer + dual implementation pattern (JPA + MyBatis) + conditional assembly auto-selection

**架构设计 / Architecture Design**：

```
┌─────────────────────────────────────────────────────────────┐
│           Data Access Layer Architecture / 数据访问层架构    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐  │
│  │  Service Layer (业务逻辑层 / Business Logic Layer)   │  │
│  │  • Only depends on abstract Repository interface     │  │
│  │  • 只依赖抽象 Repository 接口                         │  │
│  │  • Completely unaware of underlying implementation  │  │
│  │  • 完全不感知底层实现                                 │  │
│  └────────────────────┬────────────────────────────────┘  │
│                       │                                     │
│                       ▼                                     │
│  ┌─────────────────────────────────────────────────────┐  │
│  │  Repository Interface (抽象接口层 / Abstract Layer)    │  │
│  │  • ProductRepository, OrderRepository, etc.          │  │
│  │  • Pure interfaces, no framework annotations         │  │
│  │  • 纯接口，无框架注解                                 │  │
│  └──────────┬────────────────────────┬───────────────────┘  │
│             │                        │                      │
│      ┌──────┴──────┐         ┌─────┴─────┐               │
│      ▼             ▼         ▼           ▼               │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐               │
│  │  JPA     │  │ MyBatis  │  │ 领域模型 │               │
│  │  JPA     │  │ MyBatis  │  │ Domain   │               │
│  │ 实现     │  │ 实现     │  │ (POJO)   │               │
│  └──────────┘  └──────────┘  └──────────┘               │
│       │            │                                           │
│       ▼            ▼                                           │
│  ┌──────────┐  ┌──────────┐                                  │
│  │JPA Entity│  │MyBatis   │                                  │
│  │(有注解)  │  │Mapper    │                                  │
│  └──────────┘  └──────────┘                                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**选择理由 / Rationale**：

1. <!-- EN -->**User-transparent**: Auto-detect user environment through conditional assembly, no additional configuration needed <!-- CN -->**用户无感知**：通过条件装配自动检测用户环境，无需额外配置
2. <!-- EN -->**Flexible switching**: Users can manually switch implementation through configuration <!-- CN -->**灵活切换**：用户可以通过配置手动切换实现
3. <!-- EN -->**Code reuse**: Service layer completely reused, only data layer implementation differs <!-- CN -->**代码复用**：Service 层完全复用，只是数据层实现不同
4. <!-- EN -->**Easy to extend**: Can add other implementations in future (e.g., MongoDB) <!-- CN -->**易于扩展**：未来可以添加其他实现（如 MongoDB）

**替代方案考虑 / Alternative Approaches**：

| 方案 / Approach | 优点 / Pros | 缺点 / Cons | 不选择原因 / Reason |
|-----------------|-------------|-------------|---------------------|
| 分裂版本 / Split version | 实现简单 / Simple to implement | 维护成本翻倍，用户选择困惑 / Double maintenance cost, user confusion | 增加维护负担 / Increases maintenance burden |
| 混合共存 / Coexistence | 零配置 / Zero configuration | 同时启用可能冲突 / Conflict when both enabled | 需要复杂的冲突解决逻辑 / Requires complex conflict resolution |
| **抽象接口 / Abstract interface** | **核心统一、可插拔 / Unified core, pluggable** | **增加一层抽象 / Adds abstraction layer** | **推荐方案 / Recommended** |

### 决策 2：条件装配策略 / Decision 2: Conditional Assembly Strategy

**选择 / Choice**：三层控制 - 自动装配检测 → Enable 注解确认 → 配置文件精细控制 / Three-layer control - Auto-assembly detection → Enable annotation confirmation → Configuration file fine control

**装配决策树 / Assembly Decision Tree**：

```
                  Startup Assembly Decision Process / 启动时装配决策流程
                          │
         ┌────────────────┼────────────────┐
         ▼                ▼                ▼
   ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
   │Dependencies  │ │@Enable       │ │Module        │
   │Present?      │ │Present?      │ │Enabled?      │
   │依赖是否存在?│ │@Enable存在?  │ │模块配置开启? │
   │classpath检测│ │注解检测      │ │配置文件检测  │
   └──────────────┘ └──────────────┘ └──────────────┘
         │                │                │
         └────────────────┼────────────────┘
                          ▼
                    All True / 三者都为真 ──────▶ Assemble Module / 装配该模块
                          │
                          ▼
                    No / 否 ──────▶ Skip Assembly / 跳过装配
```

**实现细节 / Implementation Details**：

```java
// Level 1: Auto-assembly entry (spring.factories)
// Level 1: 自动装配入口（spring.factories）
@Configuration
@ConditionalOnClass(EnableECommerce.class)  // Dependencies present / 依赖存在
public class ECommerceAutoConfiguration {
    // Only register metadata, do not assemble specific Beans
    // 只做元数据注册，不装配具体 Bean
}

// Level 2: Enable annotation
// Level 2: Enable 注解
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ECommerceModuleRegistrar.class)
public @interface EnableECommerce {
    Module[] modules() default {};  // Empty means all / 空表示全部
}

// Level 3: Module configuration
// Level 3: 模块配置
@Configuration
@ConditionalOnProperty(name = "e-commerce.modules.product.enabled",
                      havingValue = "true", matchIfMissing = true)
public class ProductModuleConfiguration {
    // Product module Beans / 商品模块 Bean
}
```

**选择理由 / Rationale**：

1. <!-- EN -->**Progressive control**: From coarse-grained to fine-grained, meeting different needs <!-- CN -->**渐进式控制**：从粗粒度到细粒度，满足不同需求
2. <!-- EN -->**Default-friendly**: matchIfMissing = true, reduces configuration burden <!-- CN -->**默认友好**：matchIfMissing = true，减少配置负担
3. <!-- EN -->**Observability**: At startup, can clearly see which modules are enabled <!-- CN -->**可观测性**：启动时可以清楚看到哪些模块被启用

### 决策 3：模块划分与包结构 / Decision 3: Module Division and Package Structure

**选择 / Choice**：按功能模块划分，每个模块内部按层次组织 / Divide by functional modules, each module organized by layer

**电商 Starter 包结构 / E-commerce Starter Package Structure**：

```
e-business-spring-boot-starter/
├── domain/                           # Domain Layer (framework-agnostic) / 领域层（框架无关）
│   ├── product/
│   │   ├── Product.java             # Product domain model / 商品领域模型
│   │   └── Category.java
│   ├── order/
│   │   ├── Order.java
│   │   └── OrderItem.java
│   ├── payment/
│   │   └── Payment.java
│   └── user/
│       ├── User.java
│       └── Address.java
│
├── service/                          # Business Logic Layer / 业务逻辑层
│   ├── product/
│   │   ├── ProductService.java      # Interface / 接口
│   │   └── ProductServiceImpl.java  # Implementation / 实现
│   ├── order/
│   │   ├── OrderService.java
│   │   └── OrderServiceImpl.java
│   ├── payment/
│   │   └── PaymentService.java
│   └── user/
│       └── UserService.java
│
├── repository/                       # Abstract Data Access Interface / 抽象数据访问接口
│   ├── ProductRepository.java
│   ├── OrderRepository.java
│   ├── PaymentRepository.java
│   └── UserRepository.java
│
├── jpa/                              # JPA Implementation / JPA 实现
│   ├── entity/
│   │   ├── ProductEntity.java       # JPA annotations / JPA 注解
│   │   ├── OrderEntity.java
│   │   └── ...
│   ├── repository/
│   │   ├── ProductJpaRepository.java
│   │   └── OrderJpaRepository.java
│   └── config/
│       └── JpaRepositoryConfig.java
│
├── mybatis/                          # MyBatis Implementation / MyBatis 实现
│   ├── mapper/
│   │   ├── ProductMapper.java
│   │   └── OrderMapper.java
│   ├── xml/
│   │   ├── ProductMapper.xml
│   │   └── OrderMapper.xml
│   └── config/
│       └── MybatisConfig.java
│
├── api/                              # REST API Layer / REST API 层
│   ├── ProductController.java
│   ├── OrderController.java
│   ├── PaymentController.java
│   └── UserController.java
│
├── config/                           # Configuration Classes / 配置类
│   ├── ECommerceAutoConfiguration.java
│   ├── ECommerceProperties.java
│   └── module/
│       ├── ProductModuleConfiguration.java
│       ├── OrderModuleConfiguration.java
│       ├── PaymentModuleConfiguration.java
│       └── UserModuleConfiguration.java
│
├── annotation/                        # Annotations / 注解
│   └── EnableECommerce.java
│
└── resources/
    ├── META-INF/
    │   └── spring.factories
    ├── sql/
    │   ├── schema-mysql.sql
    │   ├── schema-postgres.sql
    │   └── data-sample.sql
    └── application-default.yml
```

**客服机器人 Starter 包结构 / Customer Robot Starter Package Structure**（类似 / Similar）：

```
customer-robot-spring-boot-starter/
├── domain/
│   ├── conversation/
│   │   ├── Conversation.java
│   │   └── Message.java
│   ├── knowledge/
│   │   ├── FAQ.java
│   │   └── FAQCategory.java
│   ├── routing/
│   │   └── RoutingRule.java
│   └── agent/
│       └── Agent.java
│
├── service/      # Similar structure to e-commerce / 与电商类似结构
├── repository/
├── jpa/
├── mybatis/
├── api/
├── config/
├── annotation/
└── resources/
```

**选择理由 / Rationale**：

1. <!-- EN -->**By functional module**: Facilitates on-demand loading and independent testing <!-- CN -->**按功能模块**：便于按需加载和独立测试
2. <!-- EN -->**By layer organization**: Clear dependency relationships, easy to maintain <!-- CN -->**按层次组织**：清晰的依赖关系，便于维护
3. <!-- EN -->**Symmetrical structure**: Two Starters have same structure, reduces learning cost <!-- CN -->**对称结构**：两个 Starter 结构一致，降低学习成本

### 决策 4：对象转换策略 / Decision 4: Object Conversion Strategy

**选择 / Choice**：使用 MapStruct 进行 JPA Entity 和 Domain Model 之间的转换 / Use MapStruct for conversion between JPA Entity and Domain Model

**实现方式 / Implementation**：

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(Product domain);
    Product toDomain(ProductEntity entity);
    List<Product> toDomainList(List<ProductEntity> entities);
}
```

**选择理由 / Rationale**：

1. <!-- EN -->**Compile-time generation**: Good performance, no runtime overhead <!-- CN -->**编译期生成**：性能好，无运行时开销
2. <!-- EN -->**Type-safe**: Compile-time checking, avoids runtime errors <!-- CN -->**类型安全**：编译期检查，避免运行时错误
3. <!-- EN -->**Concise code**: No need to manually write conversion logic <!-- CN -->**代码简洁**：不需要手写转换逻辑

**替代方案 / Alternative Approaches**：

| 方案 / Approach | 优点 / Pros | 缺点 / Cons |
|-----------------|-------------|-------------|
| ModelMapper | Runtime flexible, no compilation / 运行时灵活，无需编译 | Poor performance, no compile-time checking / 性能较差，无编译期检查 |
| Manual conversion / 手动转换 | Complete control / 完全控制 | Verbose code, high maintenance cost / 代码冗长，维护成本高 |
| **MapStruct** | **Good performance, type-safe / 性能好，类型安全** | **Requires compile-time generation / 需要编译期生成** |

### 决策 5：配置属性设计 / Decision 5: Configuration Properties Design

**选择 / Choice**：使用 @ConfigurationProperties + 层次化配置 / Use @ConfigurationProperties + hierarchical configuration

**电商配置结构 / E-commerce Configuration Structure**：

```yaml
e-commerce:
  # Persistence configuration / 持久化配置
  persistence:
    type: auto  # auto/jpa/mybatis
    enabled: true

  # Module switches / 模块开关
  modules:
    product:
      enabled: true
    order:
      enabled: true
    payment:
      enabled: true
    user:
      enabled: true

  # API configuration / API 配置
  api:
    base-path: /api
    cors-enabled: true
    max-page-size: 100
```

**选择理由 / Rationale**：

1. <!-- EN -->**Hierarchical**: Clear configuration, easy to understand <!-- CN -->**层次化**：配置清晰，易于理解
2. <!-- EN -->**Type-safe**: @ConfigurationProperties provides type binding and validation <!-- CN -->**类型安全**：@ConfigurationProperties 提供类型绑定和验证
3. <!-- EN -->**IDE support**: IDE can provide auto-completion <!-- CN -->**IDE 支持**：IDE 可以提供自动完成

### 决策 6：API 响应格式 / Decision 6: API Response Format

**选择 / Choice**：统一的响应格式 + 错误处理 / Unified response format + error handling

**成功响应 / Success Response**：

```java
@Data
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private PaginationMeta pagination;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data, null);
    }
}
```

**错误响应 / Error Response**：

```java
@Data
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
```

### 决策 7：数据库脚本管理 / Decision 7: Database Script Management

**选择 / Choice**：提供多数据库支持，使用版本化命名 / Provide multi-database support, use versioned naming

**脚本结构 / Script Structure**：

```
src/main/resources/sql/
├── mysql/
│   ├── V1__init_schema.sql      # Initial schema / 初始表结构
│   ├── V2__add_indexes.sql      # Index optimization / 索引优化
│   └── V3__add_payment.sql       # Payment table / 支付表
└── postgresql/
    ├── V1__init_schema.sql
    ├── V2__add_indexes.sql
    └── V3__add_payment.sql
```

---

## Risks / Trade-offs / 风险与权衡

### 风险 1：JPA 和 MyBatis 同时启用可能导致冲突 / Risk 1: JPA and MyBatis Both Enabled May Cause Conflict

**描述 / Description**：<!-- EN -->When both JPA and MyBatis dependencies are introduced in user project, may not know which implementation will be enabled <!-- CN -->用户项目中同时引入 JPA 和 MyBatis 依赖时，可能不知道哪个实现会被启用

**缓解措施 / Mitigation**：

1. <!-- EN -->**Startup warning**: Print warning when both are detected <!-- CN -->**启动时警告**：检测到两者都存在时，打印警告信息
2. <!-- EN -->**Explicit configuration**: Provide configuration to force selection `e-commerce.persistence.type=jpa` <!-- CN -->**明确配置**：提供配置项强制选择
3. <!-- EN -->**Documentation**: Explain how to handle this situation in docs <!-- CN -->**文档说明**：在文档中明确说明如何处理这种情况

### 风险 2：Entity 转换性能开销 / Risk 2: Entity Conversion Performance Overhead

**描述 / Description**：<!-- EN -->When using JPA, conversion between Entity and Domain Model may affect performance <!-- CN -->使用 JPA 时，需要进行 Entity 和 Domain Model 之间的转换，可能影响性能

**缓解措施 / Mitigation**：

1. <!-- EN -->**Use MapStruct**: Compile-time generation, performance close to hand-written <!-- CN -->**使用 MapStruct**：编译期生成转换代码，性能接近手写
2. <!-- EN -->**Cache common objects**: Cache frequently accessed objects <!-- CN -->**缓存常用对象**：对频繁访问的对象进行缓存
3. <!-- EN -->**Performance monitoring**: Provide performance metrics <!-- CN -->**性能监控**：提供性能指标，便于发现问题

---

## Migration Plan / 迁移计划

### 部署步骤 / Deployment Steps

1. **Code Implementation / 代码实现**
   - <!-- EN -->Implement business logic by module <!-- CN -->按模块实现业务逻辑
   - <!-- EN -->Implement dual-track JPA and MyBatis support <!-- CN -->实现 JPA 和 MyBatis 双轨支持
   - <!-- EN -->Implement REST API <!-- CN -->实现 REST API

2. **Test Validation / 测试验证**
   - <!-- EN -->Unit tests cover core business logic <!-- CN -->单元测试覆盖核心业务逻辑
   - <!-- EN -->Integration tests verify database operations <!-- CN -->集成测试验证数据库操作
   - <!-- EN -->Sample app verifies end-to-end flow <!-- CN -->示例应用验证端到端流程

3. **Documentation / 文档完善**
   - <!-- EN -->Getting Started guide <!-- CN -->Getting Started 指南
   - <!-- EN -->Configuration guide <!-- CN -->配置说明文档
   - <!-- EN -->API documentation (Swagger) <!-- CN -->API 文档

4. **Release / 发布**
   - <!-- EN -->Publish to Maven repository <!-- CN -->发布到 Maven 仓库
   - <!-- EN -->Mark version as 1.0.0 <!-- CN -->标记版本为 1.0.0

### 回滚策略 / Rollback Strategy

- <!-- EN -->**Code rollback**: Use Git version control, can rollback anytime <!-- CN -->**代码回滚**：使用 Git 版本控制，可随时回滚
- <!-- EN -->**Database rollback**: Provide rollback scripts <!-- CN -->**数据库回滚**：提供回滚脚本
- <!-- EN -->**Configuration rollback**: Users can disable new features through configuration <!-- CN -->**配置回滚**：用户可以通过配置禁用新功能

---

## Open Questions / 开放性问题

1. <!-- EN -->**Whether to provide Spring Boot 3.x support?** Currently based on Spring Boot 2.6.14, whether to support 3.x simultaneously? May need to create separate branch <!-- CN -->**是否需要提供 Spring Boot 3.x 支持？**当前基于 Spring Boot 2.6.14，是否需要同时支持 3.x？可能需要创建独立的分支

2. <!-- EN -->**Whether to integrate Flyway/Liquibase for database version management?** v1 can provide only SQL scripts, future versions can integrate automatic migration tools <!-- CN -->**是否需要集成 Flyway/Liquibase 进行数据库版本管理？**v1 可以只提供 SQL 脚本，后续版本可以集成自动迁移工具

---

## 附录：核心类关系图 / Appendix: Core Class Relationship Diagram

### 电商 Starter 核心类关系 / E-commerce Starter Core Class Relationship

```
┌─────────────────────────────────────────────────────────────┐
│              E-commerce Starter Class Relationships /        │
│                   电商 Starter 类关系图                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────────┐         ┌──────────────────┐         │
│  │ProductController │────────▶│ ProductService   │         │
│  └──────────────────┘         └────────┬─────────┘         │
│                                       │                     │
│                                       ▼                     │
│                              ┌─────────────────┐            │
│                              │ProductRepository│            │
│                              │   (interface)   │            │
│                              └────────┬────────┘            │
│                                       │                     │
│                          ┌────────────┴────────────┐         │
│                          ▼                         ▼         │
│                   ┌───────────┐            ┌───────────┐    │
│                   │JPA Impl   │            │MyBatis   │    │
│                   │ProductJPA│            │Product   │    │
│                   │Repository│            │Mapper    │    │
│                   └───────────┘            └───────────┘    │
│                          │                         │         │
│                          ▼                         ▼         │
│                   ┌───────────┐            ┌───────────┐    │
│                   │Product    │            │Product    │    │
│                   │Entity     │            │(Domain)   │    │
│                   └───────────┘            └───────────┘    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 调用链示例：创建订单 / Call Chain Example: Create Order

```
User Request / 用户请求
    │
    ▼
POST /api/orders
    │
    ▼
OrderController.createOrder()
    │
    ├──▶ OrderService.createOrder()
    │        │
    │        ├──▶ ProductService.checkStock()  [Check Stock / 检查库存]
    │        │        │
    │        │        └──▶ ProductRepository.findById()
    │        │
    │        ├──▶ UserRepository.findById()     [Get User / 获取用户]
    │        │
    │        ├──▶ OrderRepository.save()       [Save Order / 保存订单]
    │        │        │
    │        │        └──▶ JPA/MyBatis Implementation
    │        │
    │        └──▶ ProductRepository.decreaseStock()  [Decrease Stock / 扣减库存]
    │
    └──▶ Return Order Response / 返回订单响应
```

---

## 设计思考总结 / Design Thinking Summary

**中文说明**：
本设计的核心思想是"双轨支持 + 自动检测 + 模块化"。通过抽象接口层隔离业务逻辑和数据访问实现，让用户可以选择 JPA 或 MyBatis。通过条件装配实现自动检测，用户无需配置即可使用。通过模块化设计，用户可以按需启用功能模块。

**EN Explanation**:
The core philosophy of this design is "dual-track support + auto-detection + modularity". Through an abstract interface layer isolating business logic from data access implementation, users can choose between JPA or MyBatis. Through conditional assembly for auto-detection, users can use it without configuration. Through modular design, users can enable functionality modules on-demand.
