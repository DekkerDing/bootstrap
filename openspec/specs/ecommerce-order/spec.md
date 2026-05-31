# ecommerce-order Specification

## Purpose
TBD - created by archiving change complete-both-starters-v1. Update Purpose after archive.
## Requirements
### Requirement: The system SHALL support dual persistence
The system SHALL support both JPA and MyBatis persistence frameworks. The implementation SHALL be selected automatically based on the user's classpath configuration.

#### Scenario: Auto-detect JPA environment / 自动检测 JPA 环境

- **WHEN** <!-- EN -->the application classpath contains `javax.persistence.EntityManager` or `jakarta.persistence.EntityManager`
  <!-- CN -->应用程序类路径包含 `javax.persistence.EntityManager` 或 `jakarta.persistence.EntityManager`
- **THEN** <!-- EN -->the system SHALL enable JPA-based repository implementations
  <!-- CN -->系统必须启用基于 JPA 的仓储实现
- **AND** <!-- EN -->the system SHALL NOT enable MyBatis implementations unless explicitly configured
  <!-- CN -->系统不得启用 MyBatis 实现，除非明确配置

#### Scenario: Auto-detect MyBatis environment / 自动检测 MyBatis 环境

- **WHEN** <!-- EN -->the application classpath contains `org.apache.ibatis.session.SqlSessionFactory`
  <!-- CN -->应用程序类路径包含 `org.apache.ibatis.session.SqlSessionFactory`
- **THEN** <!-- EN -->the system SHALL enable MyBatis-based mapper implementations
  <!-- CN -->系统必须启用基于 MyBatis 的 Mapper 实现
- **AND** <!-- EN -->the system SHALL NOT enable JPA implementations unless explicitly configured
  <!-- CN -->系统不得启用 JPA 实现，除非明确配置

#### Scenario: Manual override via configuration / 通过配置手动覆盖

- **WHEN** <!-- EN -->user configures `e-commerce.persistence.type` to "jpa" or "mybatis"
  <!-- CN -->用户配置 `e-commerce.persistence.type` 为 "jpa" 或 "mybatis"
- **THEN** <!-- EN -->the system SHALL override auto-detection and use the specified persistence type
  <!-- CN -->系统必须覆盖自动检测并使用指定的持久化类型
- **AND** <!-- EN -->the system SHALL ignore the other framework even if present in classpath
  <!-- CN -->系统必须忽略其他框架，即使其存在于类路径中

---

### Requirement: The system SHALL conditionally initialize based on DataSource
The system SHALL NOT initialize any persistence components unless a DataSource bean is present in the application context.

#### Scenario: Skip persistence when no DataSource / 无 DataSource 时跳过持久化

- **WHEN** <!-- EN -->no DataSource bean is configured
  <!-- CN -->没有配置 DataSource Bean
- **THEN** <!-- EN -->the system SHALL skip all repository/maker initialization
  <!-- CN -->系统必须跳过所有仓储/Mapper 初始化
- **AND** <!-- EN -->the application SHALL start successfully (repository beans will not be available)
  <!-- CN -->应用程序必须成功启动（仓储 Bean 将不可用）

#### Scenario: Initialize with DataSource / 有 DataSource 时初始化

- **WHEN** <!-- EN -->a DataSource bean is present
  <!-- CN -->存在 DataSource Bean
- **THEN** <!-- EN -->the system SHALL initialize the selected persistence layer
  <!-- CN -->系统必须初始化所选的持久化层

---

### Requirement: The system SHALL provide repository abstraction
The system SHALL provide framework-agnostic repository interfaces that business logic depends on.

#### Scenario: Business logic uses abstract interface / 业务逻辑使用抽象接口

- **WHEN** <!-- EN -->a service requires data access
  <!-- CN -->服务需要数据访问
- **THEN** <!-- EN -->it SHALL depend only on the abstract repository interface
  <!-- CN -->它必须仅依赖抽象仓储接口
- **AND** <!-- EN -->the actual implementation (JPA or MyBatis) SHALL be injected at runtime
  <!-- CN -->实际实现（JPA 或 MyBatis）必须在运行时注入

---

### Requirement: The system SHALL maintain entity layer separation
The system SHALL maintain separate entity definitions for domain model and persistence framework.

#### Scenario: Domain entities are framework-agnostic / 领域实体框架无关

- **WHEN** <!-- EN -->defining domain entities
  <!-- CN -->定义领域实体时
- **THEN** <!-- EN -->they SHALL NOT contain JPA annotations (@Entity, @Table, etc.)
  <!-- CN -->它们不得包含 JPA 注解（@Entity、@Table 等）
- **AND** <!-- EN -->they SHALL NOT contain MyBatis annotations (@Table, @Id, etc.)
  <!-- CN -->它们不得包含 MyBatis 注解（@Table、@Id 等）
- **AND** <!-- EN -->they SHALL be plain POJOs with business fields only
  <!-- CN -->它们必须是仅包含业务字段的纯 POJO

#### Scenario: JPA entities extend domain / JPA 实体扩展领域

- **WHEN** <!-- EN -->implementing JPA persistence
  <!-- CN -->实现 JPA 持久化时
- **THEN** <!-- EN -->JPA entities SHALL extend or wrap domain entities
  <!-- CN -->JPA 实体必须扩展或包装领域实体
- **AND** <!-- EN -->JPA annotations SHALL be applied only to JPA entity classes
  <!-- CN -->JPA 注解必须仅应用于 JPA 实体类

#### Scenario: MyBatis mappers use domain entities / MyBatis Mapper 使用领域实体

- **WHEN** <!-- EN -->implementing MyBatis persistence
  <!-- CN -->实现 MyBatis 持久化时
- **THEN** <!-- EN -->mapper methods and XML mappings SHALL work with domain entities directly
  <!-- CN -->Mapper 方法和 XML 映射必须直接使用领域实体
- **AND** <!-- EN -->no separate MyBatis entity class SHALL be required
  <!-- CN -->不需要单独的 MyBatis 实体类

---

### Requirement: The system SHALL provide object conversion
When using JPA with separate entity classes, the system SHALL provide automatic conversion between domain and JPA entities.

#### Scenario: Convert domain to JPA entity / 领域实体转换为 JPA 实体

- **WHEN** <!-- EN -->saving a domain entity via JPA repository
  <!-- CN -->通过 JPA 仓储保存领域实体时
- **THEN** <!-- EN -->the system SHALL convert domain entity to JPA entity
  <!-- CN -->系统必须将领域实体转换为 JPA 实体
- **AND** <!-- EN -->all fields SHALL be mapped correctly
  <!-- CN -->所有字段必须正确映射

#### Scenario: Convert JPA entity to domain / JPA 实体转换为领域实体

- **WHEN** <!-- EN -->reading via JPA repository
  <!-- CN -->通过 JPA 仓储读取时
- **THEN** <!-- EN -->the system SHALL convert JPA entity back to domain entity
  <!-- CN -->系统必须将 JPA 实体转换回领域实体
- **AND** <!-- EN -->the result SHALL be a pure domain object
  <!-- CN -->结果必须是纯领域对象

---

### Requirement: The system SHALL expose configuration properties
The system SHALL expose configuration properties for controlling persistence behavior.

#### Scenario: Default configuration / 默认配置

- **WHEN** <!-- EN -->no persistence configuration is provided
  <!-- CN -->未提供持久化配置时
- **THEN** <!-- EN -->the system SHALL use auto-detection to choose persistence type
  <!-- CN -->系统必须使用自动检测来选择持久化类型
- **AND** <!-- EN -->all modules SHALL be enabled by default
  <!-- CN -->所有模块必须默认启用

#### Scenario: Explicit persistence type / 明确指定持久化类型

- **WHEN** <!-- EN -->user sets `e-commerce.persistence.type=jpa`
  <!-- CN -->用户设置 `e-commerce.persistence.type=jpa`
- **THEN** <!-- EN -->the system SHALL use JPA regardless of classpath
  <!-- CN -->系统必须使用 JPA，无论类路径如何

#### Scenario: Module-level persistence control / 模块级持久化控制

- **WHEN** <!-- EN -->user configures `e-commerce.persistence.enabled=false`
  <!-- CN -->用户配置 `e-commerce.persistence.enabled=false`
- **THEN** <!-- EN -->the system SHALL disable all persistence initialization
  <!-- CN -->系统必须禁用所有持久化初始化

---

