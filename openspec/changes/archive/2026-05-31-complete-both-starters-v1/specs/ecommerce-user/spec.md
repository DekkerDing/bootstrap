## ADDED Requirements

### Requirement: User profile management
The system SHALL support basic user profile CRUD operations.

#### Scenario: Create a new user
- **WHEN** a POST request is sent to `/api/users` with valid user data
- **THEN** the system SHALL create a new user record
- **AND** return HTTP 201 with the created user ID
- **AND** the user SHALL be persisted in the database

#### Scenario: Get user by ID
- **WHEN** a GET request is sent to `/api/users/{id}` with a valid user ID
- **THEN** the system SHALL return the user profile
- **AND** return HTTP 200

#### Scenario: Get non-existent user
- **WHEN** a GET request is sent to `/api/users/{id}` with a non-existent ID
- **THEN** the system SHALL return HTTP 404

#### Scenario: Update user profile
- **WHEN** a PUT request is sent to `/api/users/{id}` with updated user data
- **THEN** the system SHALL update the user record
- **AND** return HTTP 200 with the updated user data

#### Scenario: Delete user
- **WHEN** a DELETE request is sent to `/api/users/{id}`
- **THEN** the system SHALL delete the user record
- **AND** return HTTP 204

#### Scenario: Delete user with existing orders
- **WHEN** a DELETE request is sent to `/api/users/{id}` where the user has existing orders
- **THEN** the system SHALL return HTTP 400 with error message
- **AND** the user SHALL NOT be deleted

### Requirement: User fields
The user entity SHALL contain all necessary business fields.

#### Scenario: User has required fields
- **WHEN** creating a user
- **THEN** the system SHALL accept and store the following fields:
  - `id`: Unique user identifier (auto-generated)
  - `username`: Unique username (required)
  - `email`: Email address (required, unique)
  - `phone`: Phone number (optional)
  - `realName`: Real name (optional)
  - `status`: User status (ACTIVE/INACTIVE/SUSPENDED, default ACTIVE)
  - `createdAt`: Account creation timestamp (auto-generated)
  - `updatedAt`: Last update timestamp (auto-updated)

### Requirement: User validation
The system SHALL validate user data before creation and update.

#### Scenario: Validate required fields
- **WHEN** creating a user without username or email
- **THEN** the system SHALL return HTTP 400 with validation error details
- **AND** the user SHALL NOT be created

#### Scenario: Validate unique username
- **WHEN** creating a user with an existing username
- **THEN** the system SHALL return HTTP 400 with validation error
- **AND** the user SHALL NOT be created

#### Scenario: Validate unique email
- **WHEN** creating a user with an existing email
- **THEN** the system SHALL return HTTP 400 with validation error
- **AND** the user SHALL NOT be created

#### Scenario: Validate email format
- **WHEN** creating a user with invalid email format
- **THEN** the system SHALL return HTTP 400 with validation error

### Requirement: User address management
The system SHALL support managing multiple delivery addresses for each user.

#### Scenario: Add user address
- **WHEN** a POST request is sent to `/api/users/{userId}/addresses` with valid address data
- **THEN** the system SHALL create a new address for the user
- **AND** return HTTP 201 with the created address ID

#### Scenario: List user addresses
- **WHEN** a GET request is sent to `/api/users/{userId}/addresses`
- **THEN** the system SHALL return all addresses for the user
- **AND** return HTTP 200

#### Scenario: Get specific address
- **WHEN** a GET request is sent to `/api/addresses/{id}`
- **THEN** the system SHALL return the address details
- **AND** return HTTP 200

#### Scenario: Update address
- **WHEN** a PUT request is sent to `/api/addresses/{id}` with updated address data
- **THEN** the system SHALL update the address
- **AND** return HTTP 200

#### Scenario: Delete address
- **WHEN** a DELETE request is sent to `/api/addresses/{id}`
- **THEN** the system SHALL delete the address
- **AND** return HTTP 204

#### Scenario: Set default address
- **WHEN** a PUT request is sent to `/api/addresses/{id}/default`
- **THEN** the system SHALL set the address as default for the user
- **AND** unset any previous default address
- **AND** return HTTP 200

### Requirement: Address fields
The address entity SHALL contain all necessary delivery information.

#### Scenario: Address has required fields
- **WHEN** creating an address
- **THEN** the system SHALL accept and store the following fields:
  - `id`: Unique address identifier (auto-generated)
  - `userId`: Reference to the user (required)
  - `receiverName`: Name of the receiver (required)
  - `phone`: Contact phone number (required)
  - `province`: Province/state (required)
  - `city`: City (required)
  - `district`: District/area (required)
  - `detailAddress`: Detailed street address (required)
  - `postalCode`: Postal code (optional)
  - `isDefault`: Whether this is the default address (default false)
  - `createdAt`: Creation timestamp (auto-generated)
  - `updatedAt`: Last update timestamp (auto-updated)

### Requirement: User module enablement
The user module SHALL be configurable via properties and annotations.

#### Scenario: Enable via @EnableECommerce
- **WHEN** @EnableECommerce annotation is present without module specification
- **THEN** the user module SHALL be enabled by default

#### Scenario: Disable via configuration
- **WHEN** `e-commerce.modules.user.enabled=false` is configured
- **THEN** the user module SHALL NOT be initialized
- **AND** user-related APIs SHALL return HTTP 404

### Requirement: User order association
The system SHALL maintain proper associations between users and orders.

#### Scenario: Orders belong to users
- **WHEN** querying a user's profile
- **THEN** the system SHALL optionally include associated orders
- **AND** orders SHALL be filterable via `/api/users/{userId}/orders`

#### Scenario: User can access own orders only
- **WHEN** listing orders with userId parameter
- **THEN** only orders belonging to that user SHALL be returned
- **AND** users SHALL NOT access other users' orders

### Requirement: User status management
The system SHALL support user status transitions.

#### Scenario: Activate user
- **WHEN** a PUT request is sent to `/api/users/{id}/status` with status "ACTIVE"
- **THEN** the system SHALL update the user status to ACTIVE
- **AND** the user SHALL be able to place orders
- **AND** return HTTP 200

#### Scenario: Suspend user
- **WHEN** a PUT request is sent to `/api/users/{id}/status` with status "SUSPENDED"
- **THEN** the system SHALL update the user status to SUSPENDED
- **AND** the user SHALL NOT be able to place new orders
- **AND** return HTTP 200

#### Scenario: Deactivate user
- **WHEN** a PUT request is sent to `/api/users/{id}/status` with status "INACTIVE"
- **THEN** the system SHALL update the user status to INACTIVE
- **AND** the user SHALL NOT be able to log in or place orders
- **AND** return HTTP 200

### Requirement: User search
The system SHALL support searching and filtering users.

#### Scenario: Search users by username
- **WHEN** a GET request is sent to `/api/users?username={keyword}`
- **THEN** the system SHALL return users whose username contains the keyword
- **AND** return HTTP 200

#### Scenario: Search users by email
- **WHEN** a GET request is sent to `/api/users?email={keyword}`
- **THEN** the system SHALL return users whose email contains the keyword
- **AND** return HTTP 200

#### Scenario: Filter users by status
- **WHEN** a GET request is sent to `/api/users?status={status}`
- **THEN** the system SHALL return users with the specified status
- **AND** return HTTP 200
