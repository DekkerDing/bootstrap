# ecommerce-api Specification

## Purpose
TBD - created by archiving change complete-both-starters-v1. Update Purpose after archive.
## Requirements
### Requirement: RESTful API structure
The system SHALL expose all ecommerce capabilities through RESTful APIs following REST conventions.

#### Scenario: API versioning
- **WHEN** accessing the ecommerce API
- **THEN** all endpoints SHALL be available under `/api` prefix
- **AND** endpoints SHALL follow resource naming conventions (plural nouns)
- **AND** HTTP methods SHALL align with CRUD operations (GET/POST/PUT/DELETE)

#### Scenario: JSON request/response format
- **WHEN** sending requests to ecommerce APIs
- **THEN** requests SHALL use JSON content type
- **AND** responses SHALL return JSON content type
- **AND** character encoding SHALL be UTF-8

### Requirement: Standard response format
The system SHALL return standardized response formats for all API endpoints.

#### Scenario: Success response with data
- **WHEN** an API request succeeds
- **THEN** the response SHALL include HTTP status code 2xx
- **AND** response body SHALL contain the requested data
- **AND** response SHALL include appropriate headers (Content-Type, etc.)

#### Scenario: Error response
- **WHEN** an API request fails due to client error
- **THEN** the response SHALL include HTTP status code 4xx
- **AND** response body SHALL contain error details including:
  - `timestamp`: Error timestamp
  - `status`: HTTP status code
  - `error`: Error type
  - `message`: Human-readable error message
  - `path`: Request path

#### Scenario: Server error response
- **WHEN** an API request fails due to server error
- **THEN** the response SHALL include HTTP status code 5xx
- **AND** response body SHALL contain error details

### Requirement: Pagination support
List endpoints SHALL support pagination for large datasets.

#### Scenario: Default pagination
- **WHEN** requesting a list endpoint without pagination parameters
- **THEN** the system SHALL return the first page with default page size (20)
- **AND** response SHALL include pagination metadata:
  - `page`: Current page number
  - `size`: Page size
  - `total`: Total elements
  - `totalPages`: Total pages

#### Scenario: Custom pagination
- **WHEN** requesting a list endpoint with `page` and `size` parameters
- **THEN** the system SHALL return the specified page with specified size
- **AND** page size SHALL be capped at maximum (100)

#### Scenario: Pagination with sorting
- **WHEN** requesting a list endpoint with `sort` parameter (e.g., `sort=createdAt,desc`)
- **THEN** the system SHALL return results sorted by the specified field and direction

### Requirement: CORS support
The API SHALL support Cross-Origin Resource Sharing for web applications.

#### Scenario: Allow all origins in development
- **WHEN** the application is in development profile
- **THEN** CORS SHALL allow all origins
- **AND** allow common headers (Content-Type, Authorization)
- **AND** allow common methods (GET, POST, PUT, DELETE, OPTIONS)

#### Scenario: Restrict origins in production
- **WHEN** the application is in production profile
- **THEN** CORS SHALL restrict origins to configured whitelist
- **AND** reject requests from unapproved origins

### Requirement: API endpoints summary
The system SHALL provide the following API endpoints:

#### Scenario: Product endpoints
- **WHEN** the product module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/products` - Create product
  - `GET /api/products/{id}` - Get product by ID
  - `GET /api/products` - List products (with pagination and filters)
  - `PUT /api/products/{id}` - Update product
  - `DELETE /api/products/{id}` - Delete product
  - `PATCH /api/products/{id}/stock` - Update product stock
  - `POST /api/categories` - Create category
  - `GET /api/categories/{id}` - Get category by ID
  - `GET /api/categories` - List categories
  - `PUT /api/categories/{id}` - Update category
  - `DELETE /api/categories/{id}` - Delete category

#### Scenario: Order endpoints
- **WHEN** the order module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/orders` - Create order
  - `GET /api/orders/{id}` - Get order by ID
  - `GET /api/orders` - List orders (with pagination)
  - `PUT /api/orders/{id}/status` - Update order status
  - `GET /api/orders?userId={id}` - List user orders

#### Scenario: Payment endpoints
- **WHEN** the payment module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/payment/callback` - Payment callback
  - `GET /api/payment/order/{orderId}` - Get payment by order ID

#### Scenario: User endpoints
- **WHEN** the user module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/users` - Create user
  - `GET /api/users/{id}` - Get user by ID
  - `GET /api/users` - List users (with pagination and filters)
  - `PUT /api/users/{id}` - Update user
  - `DELETE /api/users/{id}` - Delete user
  - `PUT /api/users/{id}/status` - Update user status
  - `POST /api/users/{userId}/addresses` - Add user address
  - `GET /api/users/{userId}/addresses` - List user addresses
  - `GET /api/addresses/{id}` - Get address by ID
  - `PUT /api/addresses/{id}` - Update address
  - `DELETE /api/addresses/{id}` - Delete address
  - `PUT /api/addresses/{id}/default` - Set default address

### Requirement: API security
The system SHALL provide basic security features for API endpoints.

#### Scenario: Rate limiting
- **WHEN** a client exceeds rate limit
- **THEN** the system SHALL return HTTP 429 (Too Many Requests)
- **AND** response SHALL include Retry-After header

#### Scenario: Request size limit
- **WHEN** a request exceeds maximum size limit
- **THEN** the system SHALL return HTTP 413 (Payload Too Large)

### Requirement: API documentation
The system SHALL provide API documentation capabilities.

#### Scenario: Swagger integration
- **WHEN** accessing API documentation
- **THEN** Swagger UI SHALL be available at `/swagger-ui.html`
- **AND** API specification SHALL be available at `/v3/api-docs`

#### Scenario: API metadata
- **WHEN** API documentation is generated
- **THEN** each endpoint SHALL include:
  - Description
  - Request/response schemas
  - Authentication requirements (if any)
  - Error response examples

### Requirement: Conditional endpoint availability
Endpoints SHALL only be available when their corresponding modules are enabled.

#### Scenario: Disabled module returns 404
- **WHEN** a module is disabled via configuration
- **THEN** endpoints for that module SHALL return HTTP 404
- **AND** error message SHALL indicate the module is not enabled

#### Scenario: Auto-registration by module
- **WHEN** a module is enabled
- **THEN** its endpoints SHALL be automatically registered
- **AND** no additional configuration SHALL be required

