# customer-robot-api Specification

## Purpose
TBD - created by archiving change complete-both-starters-v1. Update Purpose after archive.
## Requirements
### Requirement: RESTful API structure
The system SHALL expose all customer robot capabilities through RESTful APIs following REST conventions.

#### Scenario: API versioning
- **WHEN** accessing the customer robot API
- **THEN** all endpoints SHALL be available under `/api` prefix
- **AND** endpoints SHALL follow resource naming conventions (plural nouns)
- **AND** HTTP methods SHALL align with CRUD operations (GET/POST/PUT/DELETE)

#### Scenario: JSON request/response format
- **WHEN** sending requests to customer robot APIs
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

#### Scenario: Conversation endpoints
- **WHEN** the conversation module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/conversations` - Create conversation
  - `GET /api/conversations/{id}` - Get conversation by ID
  - `GET /api/conversations` - List conversations (with pagination and filters)
  - `PUT /api/conversations/{id}/close` - Close conversation
  - `PUT /api/conversations/{id}/reopen` - Reopen closed conversation
  - `PUT /api/conversations/{id}/assign` - Assign conversation to agent
  - `DELETE /api/conversations/{id}/assign` - Unassign conversation
  - `POST /api/conversations/{id}/messages` - Send message to conversation
  - `GET /api/conversations/{id}/messages` - Get conversation messages
  - `PUT /api/messages/{id}/read` - Mark message as read
  - `GET /api/conversations/{id}/unread-count` - Get unread message count
  - `POST /api/conversations/{id}/tags` - Add tag to conversation
  - `DELETE /api/conversations/{id}/tags/{tag}` - Remove tag from conversation

#### Scenario: Knowledge endpoints
- **WHEN** the knowledge module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/faqs` - Create FAQ
  - `GET /api/faqs/{id}` - Get FAQ by ID
  - `GET /api/faqs` - List FAQs
  - `PUT /api/faqs/{id}` - Update FAQ
  - `DELETE /api/faqs/{id}` - Delete FAQ
  - `GET /api/faqs/search` - Search FAQs by keyword/category
  - `POST /api/faqs/{id}/feedback` - Submit FAQ feedback
  - `GET /api/faqs/{id}/stats` - Get FAQ statistics
  - `POST /api/faq-categories` - Create FAQ category
  - `GET /api/faq-categories` - List FAQ categories
  - `GET /api/faq-categories/{id}/faqs` - Get FAQs by category
  - `GET /api/faqs/export` - Export FAQs
  - `POST /api/faqs/import` - Import FAQs

#### Scenario: Routing endpoints
- **WHEN** the routing module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/routing-rules` - Create routing rule
  - `GET /api/routing-rules/{id}` - Get routing rule by ID
  - `GET /api/routing-rules` - List routing rules
  - `PUT /api/routing-rules/{id}` - Update routing rule
  - `DELETE /api/routing-rules/{id}` - Delete routing rule
  - `POST /api/routing-rules/test` - Test routing rule
  - `GET /api/routing-rules/{id}/stats` - Get routing rule statistics

#### Scenario: Agent endpoints
- **WHEN** the agent module is enabled
- **THEN** the following endpoints SHALL be available:
  - `POST /api/agents` - Create agent
  - `GET /api/agents/{id}` - Get agent by ID
  - `GET /api/agents` - List agents
  - `PUT /api/agents/{id}` - Update agent
  - `DELETE /api/agents/{id}` - Delete agent
  - `PUT /api/agents/{id}/status` - Update agent status
  - `PUT /api/agents/{id}/capacity` - Update agent capacity
  - `POST /api/agents/{id}/skills` - Add agent skill
  - `DELETE /api/agents/{id}/skills/{skill}` - Remove agent skill
  - `GET /api/agents/{id}/conversations` - Get agent conversations
  - `GET /api/agents/{id}/conversation-count` - Get agent active conversation count
  - `GET /api/agents/{id}/stats` - Get agent statistics
  - `PUT /api/agents/{id}/schedule` - Set agent working hours
  - `POST /api/agent-groups` - Create agent group
  - `GET /api/agent-groups/{id}/agents` - Get group agents
  - `POST /api/conversations/{id}/transfer` - Transfer conversation
  - `POST /api/conversations/{id}/conference` - Conference with agents

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

### Requirement: WebSocket support for real-time messaging
The system SHALL support WebSocket connections for real-time conversation updates.

#### Scenario: WebSocket connection endpoint
- **WHEN** a client connects to `/ws/conversations/{conversationId}`
- **AND** provides valid authentication
- **THEN** the system SHALL establish a WebSocket connection
- **AND** send real-time message updates

#### Scenario: Message broadcast
- **WHEN** a new message is created in a conversation
- **THEN** the system SHALL broadcast the message to all connected WebSocket clients
- **AND** clients SHALL receive the message without needing to poll

#### Scenario: Connection heartbeat
- **WHEN** a WebSocket connection is established
- **THEN** the system SHALL send periodic heartbeat messages
- **AND** close inactive connections after timeout

### Requirement: Agent authentication
The system SHALL support agent authentication for accessing APIs.

#### Scenario: Agent login
- **WHEN** a POST request is sent to `/api/agents/login` with valid credentials
- **THEN** the system SHALL return an authentication token
- **AND** return HTTP 200

#### Scenario: Protected endpoint access
- **WHEN** accessing a protected endpoint without authentication
- **THEN** the system SHALL return HTTP 401

#### Scenario: Agent logout
- **WHEN** a POST request is sent to `/api/agents/logout`
- **THEN** the system SHALL invalidate the authentication token
- **AND** return HTTP 200

