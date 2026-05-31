# customer-robot-database-access Specification

## Purpose
TBD - created by archiving change complete-both-starters-v1. Update Purpose after archive.
## Requirements
### Requirement: The system SHALL support dual persistence
The system SHALL support both JPA and MyBatis persistence frameworks. The implementation SHALL be selected automatically based on the user's classpath configuration.

#### Scenario: Auto-detect JPA environment
- **WHEN** the application classpath contains `javax.persistence.EntityManager` or `jakarta.persistence.EntityManager`
- **THEN** the system SHALL enable JPA-based repository implementations
- **AND** the system SHALL NOT enable MyBatis implementations unless explicitly configured

#### Scenario: Auto-detect MyBatis environment
- **WHEN** the application classpath contains `org.apache.ibatis.session.SqlSessionFactory`
- **THEN** the system SHALL enable MyBatis-based mapper implementations
- **AND** the system SHALL NOT enable JPA implementations unless explicitly configured

#### Scenario: Manual override via configuration
- **WHEN** user configures `customer-robot.persistence.type` to "jpa" or "mybatis"
- **THEN** the system SHALL override auto-detection and use the specified persistence type
- **AND** the system SHALL ignore the other framework even if present in classpath

### Requirement: The system SHALL conditionally initialize based on DataSource
The system SHALL NOT initialize any persistence components unless a DataSource bean is present in the application context.

#### Scenario: Skip persistence when no DataSource
- **WHEN** no DataSource bean is configured
- **THEN** the system SHALL skip all repository/maker initialization
- **AND** the application SHALL start successfully (repository beans will not be available)

#### Scenario: Initialize with DataSource
- **WHEN** a DataSource bean is present
- **THEN** the system SHALL initialize the selected persistence layer

### Requirement: The system SHALL provide repository abstraction
The system SHALL provide framework-agnostic repository interfaces that business logic depends on.

#### Scenario: Business logic uses abstract interface
- **WHEN** a service requires data access
- **THEN** it SHALL depend only on the abstract repository interface
- **AND** the actual implementation (JPA or MyBatis) SHALL be injected at runtime

### Requirement: The system SHALL maintain entity layer separation
The system SHALL maintain separate entity definitions for domain model and persistence framework.

#### Scenario: Domain entities are framework-agnostic
- **WHEN** defining domain entities
- **THEN** they SHALL NOT contain JPA annotations (@Entity, @Table, etc.)
- **AND** they SHALL NOT contain MyBatis annotations (@Table, @Id, etc.)
- **AND** they SHALL be plain POJOs with business fields only

#### Scenario: JPA entities extend domain
- **WHEN** implementing JPA persistence
- **THEN** JPA entities SHALL extend or wrap domain entities
- **AND** JPA annotations SHALL be applied only to JPA entity classes

#### Scenario: MyBatis mappers use domain entities
- **WHEN** implementing MyBatis persistence
- **THEN** mapper methods and XML mappings SHALL work with domain entities directly
- **AND** no separate MyBatis entity class SHALL be required

### Requirement: The system SHALL provide object conversion
When using JPA with separate entity classes, the system SHALL provide automatic conversion between domain and JPA entities.

#### Scenario: Convert domain to JPA entity
- **WHEN** saving a domain entity via JPA repository
- **THEN** the system SHALL convert domain entity to JPA entity
- **AND** all fields SHALL be mapped correctly

#### Scenario: Convert JPA entity to domain
- **WHEN** reading via JPA repository
- **THEN** the system SHALL convert JPA entity back to domain entity
- **AND** the result SHALL be a pure domain object

### Requirement: The system SHALL expose configuration properties
The system SHALL expose configuration properties for controlling persistence behavior.

#### Scenario: Default configuration
- **WHEN** no persistence configuration is provided
- **THEN** the system SHALL use auto-detection to choose persistence type
- **AND** all modules SHALL be enabled by default

#### Scenario: Explicit persistence type
- **WHEN** user sets `customer-robot.persistence.type=jpa`
- **THEN** the system SHALL use JPA regardless of classpath

#### Scenario: Module-level persistence control
- **WHEN** user configures `customer-robot.persistence.enabled=false`
- **THEN** the system SHALL disable all persistence initialization

